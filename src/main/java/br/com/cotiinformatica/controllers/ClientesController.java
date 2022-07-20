package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.requests.ClientePostRequest;
import br.com.cotiinformatica.requests.ClientePutRequest;
import responses.ClienteGetResponse;

@Transactional //habilitar o uso de repositorios da JPA
@Controller
public class ClientesController {
	
	@Autowired //autoinicialização
	private IClienteRepository clienteRepository;
	
	@RequestMapping(value = "/api/clientes", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ClientePostRequest request){
		
		try {
			
			
			Cliente cliente= new Cliente();
			
			cliente.setNome(request.getNome());
			cliente.setCpf(request.getCpf());
			cliente.setEmail(request.getEmail());
			
			
			clienteRepository.save(cliente);
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body("Cliente cadastrado com sucesso.");
		}
		catch(Exception e){
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
				
	}
	
	@RequestMapping(value = "/api/clientes", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ClientePutRequest request){
		
		try {
			
			//consultar o cliente no banco de dados através do ID
			Optional<Cliente> consulta = clienteRepository.findById(request.getIdCliente());
			
			//verificar se o fornecedor não foi encontrado
			if(consulta.isEmpty()) {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado.");
			}
			
				Cliente cliente = new Cliente();
				
				cliente.setIdCliente(request.getIdCliente());
				cliente.setNome(request.getNome());
				cliente.setCpf(request.getCpf());
				cliente.setEmail(request.getEmail());
				
				clienteRepository.save(cliente);
			
				return ResponseEntity
					.status(HttpStatus.OK).body("Cliente atualizado com sucesso.");
		}
		catch(Exception e) {
				
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/api/clientes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable ("id") Integer id){
		
		try {
			
			//consultando o cliente no banco de dados através do ID
			Optional<Cliente> consulta = clienteRepository.findById(id);
			
			//verificando se o cliente foi encontrado
			if(consulta.isPresent()) {
				
				Cliente cliente = consulta.get(); //capturando o cliente
				clienteRepository.delete(cliente); //excluindo o cliente	
				
				return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso.");
			}
			else {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Cliente não encontrado, verifique o ID informado.");
			}
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());	
		}
		
	}
	
	@RequestMapping(value = "/api/clientes", method = RequestMethod.GET)
	public ResponseEntity<List<ClienteGetResponse>> getAll() {
		
		try {
			
			//obtendo uma lista de clientes do banco de dados
			List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
			
			List<ClienteGetResponse> lista = new ArrayList<ClienteGetResponse>();
			
			//percorrer os clientes obtidos no banco de dados
			for(Cliente cliente : clientes) {
				
				ClienteGetResponse response = new ClienteGetResponse();
				
				response.setIdCliente(cliente.getIdCliente());
				response.setNome(cliente.getNome());
				response.setCpf(cliente.getCpf());
				response.setEmail(cliente.getEmail());
				
				lista.add(response);
			}
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(lista);
			}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	

	@RequestMapping(value = "/api/clientes/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteGetResponse> getById(@PathVariable("id") Integer id) {
		
		try {
			
			//consultando 1 cliente atraves do ID
			Optional<Cliente> consulta = clienteRepository.findById(id);
			
			//verificando se o cliente foi encontrado
			if(consulta.isPresent()) {
				
				//capturando o cliente obtido na consulta
				Cliente cliente = consulta.get();
				
				ClienteGetResponse response = new ClienteGetResponse();
				
				response.setIdCliente(cliente.getIdCliente());
				response.setNome(cliente.getNome());
				response.setCpf(cliente.getCpf());
				response.setEmail(cliente.getEmail());
				
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(response);
			}
			else {
				
				return ResponseEntity
						.status(HttpStatus.NO_CONTENT)
						.body(null);
			}
		
		}
		catch(Exception e) {
		
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(null);
		
		}
	}
	
	@RequestMapping(value = "/api/clientes/obter-por-nome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<List<ClienteGetResponse>> getByNome(@PathVariable("nome") String nome) {
		
		try {
			
			List<Cliente> clientes = clienteRepository.findByNome(nome);
			List<ClienteGetResponse> lista = new ArrayList<ClienteGetResponse>();

			for(Cliente cliente : clientes) {
				
				ClienteGetResponse response = new ClienteGetResponse();
				response.setIdCliente(cliente.getIdCliente());
				response.setNome(cliente.getNome());
				response.setCpf(cliente.getCpf());
				response.setEmail(cliente.getEmail());
				
				lista.add(response);				
			}
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(lista);
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}		
	}
	
}	
	



		
		
	