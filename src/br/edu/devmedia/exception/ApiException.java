package br.edu.devmedia.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Extensão da classe WebApplicationException que receberá uma Response customizada para casso de erros no serviço
 * @author Allan
 *
 */
public class ApiException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;
	
	public ApiException(Integer code, String message) {
		//o construtor da superclasse WebApplicationException
		//espra receber um objeto Response como parâmetro
		super(Response.status(code)
				.entity(new Error(code, message)) //criamos um objeto Error com os parâmetros do construtor
				.build());
	}
	
}
