package com.infobase.desafio.azul.services;


import com.infobase.desafio.azul.entities.Cliente;
import com.infobase.desafio.azul.repositories.ClienteRepository;
import com.infobase.desafio.azul.utils.ViaCepUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Api
@Path("/clientes")
@Stateless
@Local
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional(Transactional.TxType.SUPPORTS)
@Slf4j
public class ClienteServiceBean {

    @Inject
    private ClienteRepository repository;

    private ViaCepUtils cepUtils = new ViaCepUtils();

    
    @ApiOperation(value = "Busca clientes por email", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    @GET()
    @Path("/search/findByEmail/{email}")
    public Response findByEmail(@NotNull
                                @PathParam("email")
                                @ApiParam(name = "email", value = "E-mail do cliente", required = true) final String email) {

        log.info("executando a pesquisa de clientes por email");

        if( log.isDebugEnabled() ) {
            log.debug("pesquisando cliente pelo email {}", email);
        }

        var cliente = repository.findByEmail(email);
        return createResponse(cliente);
    }

    
    @ApiOperation(value = "Lista todos os clientes", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class)
    })
    @GET()
    public Response listAll() {
        var clientes = repository.listAll();

        return Response.status(Status.OK).entity(clientes).build();
    }

    
    @GET()
    @Path("/{id}")
    @ApiOperation(value = "Busca cliente pelo id", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public Response findById(@NotNull
                             @PathParam("id")
                             @ApiParam(name = "id", value = "Id do cliente", required = true, type = "long") final Long id) {


        log.info("executando a pesquisa de cliente pelo id");

        if (log.isDebugEnabled()) {
            log.debug("tentando pesquisar cliente pela chave {}", id);
        }
        var cliente = repository.findById(id);

        return createResponse(cliente);

    }

    private Response createResponse(final Cliente cliente) {
        if( cliente == null ) {
            log.debug("cliente não encontrado");
            return Response.status(Status.NOT_FOUND).build();
        }

        if (log.isDebugEnabled()) {
            log.debug("cliente encontrado {}", cliente);
        }

        return Response.status(Status.OK).entity(cliente).build();
    }

    
    @POST
    @ApiOperation(value = "Cria um novo cliente no banco de dados", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente criado com sucesso", response = Cliente.class),
            @ApiResponse(code = 406, message = "Não foi possível salvar o cliente")
    })
    @Transactional(Transactional.TxType.REQUIRED)
    public Response add(final Cliente cliente) {

        log.info("executando o método de adição do cliente");
        if( log.isDebugEnabled() ) {
            log.debug("adicionando o cliente {}", cliente);
        }

        if( log.isDebugEnabled() ) {
            log.debug("verificando se já existe na base algum cliente com o email {}", cliente.getEmail());
        }

        if( repository.findByEmailOrCPF(cliente.getEmail(), cliente.getCpf()) == null) {
            log.info("cliente novo");
            var endereco = cepUtils.findByCEP(cliente.getEndereco().getCep());
            if( endereco != null ) {
                endereco.setComplemento(cliente.getEndereco().getComplemento());
                endereco.setNumero(cliente.getEndereco().getNumero());
                cliente.setEndereco(endereco);
                var saved = repository.add(cliente);
                return Response.status(Status.CREATED).entity(saved).build();
            } else {
                log.info("cep informado não existe");
                return Response.status(Status.NOT_FOUND).entity("CEP inválido").build();
            }
        } else {
            log.info("ja existe outro cliente com o mesmo email informado");
            return Response.status(Status.NOT_ACCEPTABLE).entity("Já existe outro cliente com o email informado").build();
        }
    }
}
