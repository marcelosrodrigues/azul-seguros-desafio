package test.com.infobase.desafio.azul.repositories;

import com.infobase.desafio.azul.entities.Cliente;
import com.infobase.desafio.azul.entities.embeddables.Endereco;
import com.infobase.desafio.azul.repositories.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.lang.reflect.Field;
import java.util.Collection;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositoryTest {

    private EntityManager entityManager;

    private ClienteRepository repository = new ClienteRepository();
    private EntityTransaction transaction;

    @BeforeEach
    public void beforeEach() throws Exception {
        this.entityManager = Persistence.createEntityManagerFactory("desafio").createEntityManager();
        this.transaction = entityManager.getTransaction();
        transaction.begin();
        Field entityManager = repository.getClass().getDeclaredField("entityManager");
        entityManager.setAccessible(true);
        entityManager.set(repository, this.entityManager);
    }

    @AfterEach
    public void afterEach() {
        this.transaction.commit();
    }

    @Test
    public void shouldSave() {

        final Cliente cliente = new Cliente("teste" , "teste@teste.com",
                new Endereco("rua", "numero", "complemento",
                        "bairro", "cep", "estado", "cidade", "ibge", "gia", "ddd", "siafi"));

        final Cliente saved = repository.add(cliente);
        assertNotNull(saved.getId());

    }

    @Test
    public void findById() {

        final Cliente cliente = new Cliente("teste" , "teste@teste.com",
                new Endereco("rua", "numero", "complemento",
                        "bairro", "cep", "estado", "cidade", "ibge", "gia", "ddd", "siafi"));

        this.entityManager.persist(cliente);

        final Cliente saved = this.repository.findById(cliente.getId());
        assertNotNull(saved);

        assertEquals(cliente, saved);

    }

    @Test
    public void shouldReturnNullValue() {
        final Cliente saved = this.repository.findById(1L);

        assertNull(saved);
    }

    @Test
    public void shouldFoundByEmail() {

        final Cliente cliente = new Cliente("teste" , "teste@teste.com",
                new Endereco("rua", "numero", "complemento",
                        "bairro", "cep", "estado", "cidade", "ibge", "gia", "ddd", "siafi"));

        this.entityManager.persist(cliente);

        final Cliente saved = this.repository.findByEmail(cliente.getEmail());
        assertNotNull(saved);

        assertEquals(cliente, saved);
    }

    @Test
    public void shouldNotFoundByEmail() {
        final Cliente saved = this.repository.findByEmail("");
        assertNull(saved);
    }

    @Test
    public void listAll() {

        for( int i = 0 ; i < 10 ; i++) {
            final Cliente cliente = new Cliente("teste" , format("teste%s@teste.com", i),
                    new Endereco("rua", "numero", "complemento",
                            "bairro", "cep", "estado", "cidade", "ibge", "gia", "ddd", "siafi"));

            this.entityManager.persist(cliente);
        }

        Collection<Cliente> clientes = repository.listAll();
        assertNotNull(clientes);

    }

}