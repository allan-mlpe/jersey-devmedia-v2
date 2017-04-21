package br.edu.devmedia.exception;

/**
 * Entidade que será convertida para JSON e retornada pelos serviços em casos de
 * erros ocorridos nas requisições
 * 
 * @author Allan
 *
 */
public class Error {

	/**
	 * Status do erro
	 */
	private int status;

	/**
	 * Mensagem customizada do erro
	 */
	private String message;

	public Error() {

	}

	public Error(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
