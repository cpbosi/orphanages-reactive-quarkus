package com.cpbosi.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.cpbosi.dto.CreateOrphanageDTO;
import com.cpbosi.dto.UpdateOrphanageDTO;
import com.cpbosi.pojo.Orphanage;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
public class OrphanageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrphanageService.class);

    @Inject
    PgPool client;

    // Just to initialize DB because i'm not using flyway
    @Inject
    @ConfigProperty(name = "learning.system.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @PostConstruct
    void config() {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        client.query("DROP TABLE IF EXISTS orphanage").execute()
                .flatMap(r -> client.query("CREATE TABLE orphanage (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
                .flatMap(r -> client.query("INSERT INTO orphanage (id, name) VALUES (991, 'Casa Lar')").execute())
                .flatMap(r -> client.query("INSERT INTO orphanage (id, name) VALUES (992, 'Lar Recanto do Carinho')").execute())
                .await().indefinitely();
    }

    public Multi<Orphanage> listOrphanages() {
        return this.client
                .preparedQuery("SELECT id, name FROM orphanage ORDER BY name ASC")
                .execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set.value()))
                .onItem().transform(Orphanage::from)
                .onFailure().invoke(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    public Uni<Long> createOrphanage(CreateOrphanageDTO orphanage) {
        return this.client
                .preparedQuery("INSERT INTO orphanage (name) VALUES ($1) RETURNING id")
                .execute(Tuple.of(orphanage.getName()))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }
    
    public Uni<Boolean> deleteOrphanage(Long id) {
        return this.client
                .preparedQuery("DELETE FROM orphanage WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1); 
    }

	public Uni<Boolean> updateOrphanage(Long id, UpdateOrphanageDTO orphanage) {
		return this.client
                .preparedQuery("UPDATE orphanage SET name = $2 WHERE id = $1")
                .execute(Tuple.of(id, orphanage.getName()))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1); 
	}

}
