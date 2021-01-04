package com.cpbosi.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * DTO that contains only the informations possible to update
 */
public class UpdateOrphanageDTO {
    
    @Schema(description = "Orphanage's name using Camel Case", required = true )
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}