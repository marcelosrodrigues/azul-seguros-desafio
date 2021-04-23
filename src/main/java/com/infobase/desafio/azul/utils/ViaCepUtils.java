package com.infobase.desafio.azul.utils;

import com.infobase.desafio.azul.entities.embeddables.Endereco;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static java.lang.String.format;

@Slf4j
public class ViaCepUtils {
    private final ResteasyClient REST_CLIENT = new ResteasyClientBuilder().build();

    public Endereco findByCEP(final String cep) {

        log.info("pesquisando cep pela código {}", cep);

        final WebTarget target = REST_CLIENT.target(format("http://viacep.com.br/ws/%s/json/", cep));
        final Response response = target.request(MediaType.APPLICATION_JSON).get();

        log.info("status retornado {}", response.getStatus());

        if( Status.fromStatusCode(response.getStatus()) == Status.OK ) {
            final Endereco endereco = response.readEntity(Endereco.class);

            if (log.isDebugEnabled()) {
                log.debug("endereço encontrado {}", endereco);
            }
            return endereco;
        } else {
            return null;
        }


    }
}
