package com.infobase.desafio.azul;

import com.infobase.desafio.azul.services.ClienteServiceBean;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class ApplicationConf extends Application {

    public ApplicationConf() {
        BeanConfig conf = new BeanConfig();
        conf.setTitle("Desafio API");
        conf.setDescription("Azul Seguros - Desafio");
        conf.setVersion("1.0.0");
        conf.setHost("localhost:8080");
        conf.setBasePath("/desafio/api");
        conf.setSchemes(new String[] { "http" });
        conf.setResourcePackage("com.infobase.desafio.azul");
        conf.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new HashSet<>();
        resources.add(ClienteServiceBean.class);
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);

        return resources;
    }
}
