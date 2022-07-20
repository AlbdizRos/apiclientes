package br.com.cotiinformatica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Cliente;

public interface IClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("select c from Cliente c where lower(c.nome) like lower(concat('%', :param1,'%'))")
	List<Cliente> findByNome(@Param("param1") String nome);
}
