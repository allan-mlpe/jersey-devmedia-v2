package br.edu.devmedia.domain;

import java.util.List;

/**
 * Esta classe serve para encapsular o retorno dos serviços a fim de evitar o
 * uso da classe GenericEntity necessária para o retorno de um ArrayList ou
 * Vector, por exemplo.
 * 
 * Um exemplo prático do uso de uma GenericEntity pode ser encontrado no método
 * listarNotas() da classe NotaService da versão 1 do curso.
 * 
 * @author Allan
 *
 */
public class Pagina {

	private List<Lembrete> dados;

	public Pagina() {

	}

	public Pagina(List<Lembrete> dados) {
		this.dados = dados;
	}

	public List<Lembrete> getDados() {
		return dados;
	}

	public void setDados(List<Lembrete> dados) {
		this.dados = dados;
	}
}
