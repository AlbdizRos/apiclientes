package responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteGetResponse {
	
	private Integer idCliente;
	private String nome;
	private String cpf;
	private String email;

}
