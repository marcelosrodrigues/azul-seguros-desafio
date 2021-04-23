package com.infobase.desafio.azul.repositories;

import com.infobase.desafio.azul.entities.Cliente;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Stateless
@Local
@Transactional(Transactional.TxType.SUPPORTS)
@Slf4j
public class ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Cliente findById(@NotNull final Long id) {

        log.info("pesquisando o cliente pelo id");
        if( log.isDebugEnabled()){
            log.debug("pesquisando o cliente pelo id {}", id);
        }

        final Cliente cliente = this.entityManager.find(Cliente.class, id);

        if( log.isDebugEnabled() ){
            log.debug("cliente encontrado {}", cliente);
        }

        return cliente;
    }

    public Cliente findByEmail(@NotNull String email) {

        log.info("pesquisando cliente pelo email");
        if( log.isDebugEnabled() ) {
            log.debug("pesquisando cliente pelo email {}", email);
        }

        try {
            var cliente = this.entityManager.createQuery("SELECT c FROM Cliente c where c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if( log.isDebugEnabled() ){
                log.debug("cliente {} encontrado", cliente);
            }
            return cliente;
        }catch(NoResultException e){
            if( log.isDebugEnabled() ){
                log.debug("cliente com email {} não encontrado", email);
            }
            return null;
        }
    }

    public Collection<Cliente> listAll() {
        log.info("listando todos os clientes");
        return this.entityManager.createQuery("SELECT c FROM Cliente c ORDER BY c.id", Cliente.class).getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Cliente add(@NotNull final Cliente cliente) {

        log.info("salvando o cliente no banco de dados");

        if(log.isDebugEnabled() ){
            log.debug("tentando adicionar o cliente {} no banco de dados", cliente);
        }
        this.entityManager.persist(cliente);

        if(log.isDebugEnabled() ){
            log.debug("cliente {} salvo no banco de dados", cliente);
        }

        return cliente;
    }

    public Cliente findByEmailOrCPF(final String email, final String cpf) {

        log.info("pesquisando cliente pelo cpf e email");

        if( log.isDebugEnabled() ){
            log.debug("tentando pesquisa 1 cliente pelo email {} ou cpf {}", email, cpf);
        }

        var cliente = this.entityManager.createQuery("SELECT c FROM Cliente c where c.email = :email or c.cpf = :cpf", Cliente.class)
                    .setParameter("email" , email)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

        if( log.isDebugEnabled() ){
            log.debug("cliente encontrado {}", cliente);
        }

        return cliente;
    }
}
