package com.cpbosi.doc;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
    description = "Aplicacao desenvolvida para aprofundar o conhecimento reativo do quarkus.", 
    title = "Be the hero versao quarkus reativo", 
    version = "0.0.1",
    contact = @Contact(name = "Clairson Patrick Bosi", email = "cpbosi@gmail.com")
    ))
public class MyApplication extends Application{
    
}
