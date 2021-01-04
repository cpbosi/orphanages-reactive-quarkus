package com.cpbosi.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cpbosi.dto.CreateOrphanageDTO;
import com.cpbosi.dto.UpdateOrphanageDTO;
import com.cpbosi.pojo.Orphanage;
import com.cpbosi.service.OrphanageService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * OrphanageResource
 */
@Path("/orphanages")
@Tag(name = "orphanages")
public class OrphanageResource {

    @Inject OrphanageService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        description = "Retrieve the Orphanages Lists in all the situations (logical deleted or not).", 
        summary = "Retrieve All Orphanages")
    public Multi<Orphanage> listOrphanages(){
        return service.listOrphanages();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        description = "Insert a new Orphanage... more informations.", 
        summary = "Save an Orphanage")
    @APIResponse(responseCode = "201",  description = "Orphanage created successfully")
    @APIResponse(responseCode = "404",  description = "Error on create orphanage")
    public Uni<Response> createOrphanage(@RequestBody CreateOrphanageDTO orphanage){
        return service.createOrphanage(orphanage)
                    .onItem().transform(orphanageId -> orphanageId > 0 ? Status.CREATED : Status.BAD_REQUEST)
                    .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{id}")
    @Tag(name = "orphanages")
    @Operation(
        description = "Delete a orphanage by id.", 
        summary = "Delete orphanage")
    @APIResponse(responseCode = "204",  description = "Orphanage deleted successfully")
    @APIResponse(responseCode = "404",  description = "Error on delete orphanage")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteOrphanage(@PathParam("id") Long id) {
        return service.deleteOrphanage(id)
                    .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                    .onItem().transform(status -> Response.status(status).build());
    }

    @PUT
    @Path("{id}")
    @Tag(name = "orphanages")
    @Operation(
        description = "Update orphanage info by id.", 
        summary = "Update orphanage")
    @APIResponse(responseCode = "200",  description = "Orphanage updated successfully")
    @APIResponse(responseCode = "404",  description = "Error on update orphanage")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateOrphanage(@PathParam("id") Long id, @RequestBody UpdateOrphanageDTO orphanage) {
        return service.updateOrphanage(id, orphanage)
                    .onItem().transform(updated -> updated ? Status.OK : Status.NOT_FOUND)
                    .onItem().transform(status -> Response.status(status).build());
    }
}