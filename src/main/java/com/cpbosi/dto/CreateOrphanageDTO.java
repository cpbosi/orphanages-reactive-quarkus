package com.cpbosi.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * DTO that contains all the informations needed to create, except id that is created by BD
 */
public class CreateOrphanageDTO {

    @Schema(description = "Orphanage's name using Camel Case", required = true )
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}