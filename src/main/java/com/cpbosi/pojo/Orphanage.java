package com.cpbosi.pojo;

import io.vertx.mutiny.sqlclient.Row;
import static java.util.Objects.isNull;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

public class Orphanage implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 5368947260912728003L;
    private Long id;
    private String name;

    public Orphanage() {
    }

    private Orphanage(String name) {
        this.name = name;
    }

    private Orphanage(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Orphanage of(Long id, String name) {
		checkArgument(!isNull(id));
		checkArgument(!isNull(name));
		return new Orphanage(id, name);
    }
    
    public static Orphanage of(String name) {
		checkArgument(!isNull(name));
		return new Orphanage(name);
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Orphanage from(Row row){
        return new Orphanage(row.getLong("id"), row.getString("name"));
    }

    @Override
    public String toString() {
        return "Orphanage: " + this.getName();
    }

}
