package test.com.infobase.desafio.azul.integration;

import com.infobase.desafio.azul.entities.Cliente;
import com.infobase.desafio.azul.entities.embeddables.Endereco;
import com.infobase.desafio.azul.repositories.ClienteRepository;
import com.infobase.desafio.azul.services.ClienteServiceBean;
import lombok.var;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.com.infobase.desafio.azul.services.interfaces.ClienteServiceProxy;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClienteServiceITest {

    private final ResteasyClient REST_CLIENT = new ResteasyClientBuilder().build();

    private ClienteServiceProxy service;

    @BeforeEach
    public void before(){
        service = REST_CLIENT.target(UriBuilder.fromPath("http://localhost:8080/desafio/api/clientes")).
                proxy(ClienteServiceProxy.class);
    }

    @Test
    public void shouldAdd() throws IOException {

        var cliente = new Cliente("Marcelo" , "teste@teste.com", "123456789",
                new Endereco("950", "APTO 403 BLOCO 2", "22770235"));

        Response response = service.add(cliente);

        assertEquals(Response.Status.CREATED , Response.Status.fromStatusCode(response.getStatus()));
        var created = (Cliente)response.readEntity(Cliente.class);
        assertNotNull(created.getId());
    }

    @Test
    public void shouldntAddWithSameEmail() {
        var cliente = new Cliente("Marcelo" , "could@teste.com", "123456789",
                new Endereco("950", "APTO 403 BLOCO 2", "22770235"));

        var response = service.add(cliente);

        assertEquals(Response.Status.CREATED , Response.Status.fromStatusCode(response.getStatus()));
        var created = (Cliente)response.readEntity(Cliente.class);
        assertNotNull(created.getId());

        response = service.add(cliente);
        assertEquals(Response.Status.NOT_ACCEPTABLE , Response.Status.fromStatusCode(response.getStatus()));
    }

    @Test
    public void shouldByEmail() throws IOException {
        var cliente = new Cliente("Marcelo" , "founded@teste.com", "123456789",
                new Endereco("950", "APTO 403 BLOCO 2", "22770235"));

        Response response = service.add(cliente);

        assertEquals(Response.Status.CREATED , Response.Status.fromStatusCode(response.getStatus()));

        var founded = service.findByEmail(cliente.getEmail());

        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(response.getStatus()));

    }
}
