package br.edu.devmedia.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import br.edu.devmedia.dao.DatabaseConnectionFactory;

/**
 * Retorna listas limitadas a um certo número de entidades lembrete.
 * 
 * Esta classe se encontra no pacote de domínio porque representa uma regra de negócio:
 * a aplicação não deve retornar mais do que um número X de registros nas buscas.
 * @author Allan
 *
 */
public class LembreteRepository {

	/**
	 * Número máximo de lembretes retornados por página
	 */
	public static final int PAGE_LENGTH = 5;
	
	public List<Lembrete> getByRange(int page) {
		
		List<Lembrete> lista = new Vector<Lembrete>();
		
		try {
			String query = "SELECT * FROM lembrete LIMIT ?, ?";
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			//definição de qual será o primeiro endereço da página
			page = (page - 1) * PAGE_LENGTH;
			
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, page); //primeira instância da página
			st.setInt(2, PAGE_LENGTH); //quantidade de resultados por página a partir da primeira instância
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id_lembrete");
				String titulo = rs.getString("titulo");
				String descricao = rs.getString("descricao");
				
				Lembrete lembrete = new Lembrete(id, titulo, descricao);
				
				lista.add(lembrete);
			}
			
		} catch(Exception e) {
			
		}
		
		return lista;
	}
	
	public List<Lembrete> getAll() {
		List<Lembrete> lista = new Vector<Lembrete>();
		
		try {
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			//limita o resultado da query a 5 instâncias do bd
			String query = "SELECT * FROM lembrete LIMIT " + PAGE_LENGTH;
			
			PreparedStatement st = con.prepareStatement(query);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id_lembrete");
				String titulo = rs.getString("titulo");
				String descricao = rs.getString("descricao");
				
				Lembrete lembrete = new Lembrete(id, titulo, descricao);
				
				lista.add(lembrete);
			}
			
		} catch(Exception e) {
			
		}
		
		return lista;
	}
}
