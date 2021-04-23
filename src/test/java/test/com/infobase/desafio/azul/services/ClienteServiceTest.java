package test.com.infobase.desafio.azul.services;

import com.infobase.desafio.azul.entities.Cliente;
import com.infobase.desafio.azul.entities.embeddables.Endereco;
import com.infobase.desafio.azul.repositories.ClienteRepository;
import com.infobase.desafio.azul.services.ClienteServiceBean;
import com.infobase.desafio.azul.utils.ViaCepUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteServiceBean toTest;

    @Mock
    private ClienteRepository repository;

    @Mock
    private ViaCepUtils cepUtils;

    @Test
    public void findById() {
        given(repository.findById(any(Long.class))).willReturn(new Cliente());

        final Response response = toTest.findById(1L);
        assertNotNull(response);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void notFoundById() {
        given(repository.findById(any(Long.class))).willReturn(null);

        final Response response = toTest.findById(1L);
        assertNotNull(response);

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void findByEmail() {
        given(repository.findByEmail(any(String.class))).willReturn(new Cliente());
        final Response response = toTest.findByEmail("teste@teste.com");

        assertNotNull(response);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void notFoundByEmail() {
        given(repository.findByEmail(any(String.class))).willReturn(null);
        final Response response = toTest.findByEmail("teste@teste.com");

        assertNotNull(response);

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void listAll() {
        final Collection<Cliente> clientes = mock(Collection.class);
        given(repository.listAll()).willReturn(clientes);

        final Response response = toTest.listAll();
        assertNotNull(response);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldSaveCliente() {
        final Cliente cliente = mock(Cliente.class);
        final Endereco endereco = mock(Endereco.class);

        given(cliente.getEmail()).willReturn("teste@teste.com");
        given(repository.findByEmail(any(String.class))).willReturn(null);

        given(endereco.getCep()).willReturn("teste");

        given(cliente.getEndereco()).willReturn(endereco);

        given(cepUtils.findByCEP(any(String.class))).willReturn(endereco);

        given(repository.add(cliente)).willReturn(cliente);

        final Response response = toTest.add(cliente);
        assertNotNull(response);

        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(cliente, response.getEntity());
    }

    @Test
    public void shouldntSaveClienteEnderecoNotFound() {
        final Cliente cliente = mock(Cliente.class);
        final Endereco endereco = mock(Endereco.class);

        given(cliente.getEmail()).willReturn("teste@teste.com");
        given(repository.findByEmail(any(String.class))).willReturn(null);

        given(endereco.getCep()).willReturn("teste");

        given(cliente.getEndereco()).willReturn(endereco);

        given(cepUtils.findByCEP(any(String.class))).willReturn(null);

        final Response response = toTest.add(cliente);
        assertNotNull(response);

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    @Test
    public void shouldntSaveEmailExisted() {
        final Cliente cliente = mock(Cliente.class);

        given(cliente.getEmail()).willReturn("teste@teste.com");
        given(repository.findByEmail(any(String.class))).willReturn(cliente);

        final Response response = toTest.add(cliente);
        assertNotNull(response);

        assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
    }
}