package test.com.infobase.desafio.azul.services.interfaces;


import com.infobase.desafio.azul.entities.Cliente;
import com.infobase.desafio.azul.repositories.ClienteRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClienteServiceProxy {

    @ApiOperation(value = "Busca clientes por email", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    @GET()
    @Path("/search/findByEmail/{email}")
    public Response findByEmail(@NotNull
                                @PathParam("email")
                                @ApiParam(name = "email", value = "E-mail do cliente", required = true) final String email);

    
    @ApiOperation(value = "Lista todos os clientes", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class)
    })
    @GET()
    public Response listAll();

    
    @GET()
    @Path("/{id}")
    @ApiOperation(value = "Busca cliente pelo id", produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado", response = Cliente.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public Response findById(@NotNull
                             @PathParam("id")
                             @ApiParam(name = "id", value = "Id do cliente", required = true, type = "long") final Long id);

    @POST
    @ApiOperation(value = "Cria um novo cliente no banco de dados", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente criado com sucesso", response = Cliente.class),
            @ApiResponse(code = 406, message = "Não foi possível salvar o cliente")
    })
    public Response add(final Cliente cliente) ;
}
