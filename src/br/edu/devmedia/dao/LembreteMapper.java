package br.edu.devmedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.edu.devmedia.domain.Lembrete;
import br.edu.devmedia.exception.ApiException;

public class LembreteMapper {
	
	/**
	 * Realiza uma busca na base de dados
	 * @param lembrete lembrete que deseja-se buscar
	 * @return o lembrete com ID indicado caso exista no banco de dados ou null caso contrário
	 * @throws ApiException lançada em caso de erros na busca no banco de dados
	 */
	public Lembrete select(Lembrete lembrete) throws ApiException {
		Lembrete lemb = null;
		try {
			String query = "SELECT * FROM lembrete WHERE id_lembrete=?";
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, lembrete.getId());
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				lemb = new Lembrete(
							rs.getInt("id_lembrete"),
							rs.getString("titulo"),
							rs.getString("descricao")
						);
			}
			
		} catch(Exception e) {
			throw new ApiException(500, e.getMessage());
		}
		return lemb;
	}
	
	/**
	 * Insere um lembrete na base de dados
	 * @param lembrete lembrete a ser inserido no banco de dados
	 * @return o lembrete inserido no banco de dados ou null caso contrário
	 * @throws ApiException lançada em caso de erros na inserção no banco de dados
	 */
	public Lembrete insert(Lembrete lembrete) throws ApiException {
		try {
			String statement = "INSERT INTO lembrete(titulo, descricao) VALUES(?, ?)";
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			PreparedStatement st = con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, lembrete.getTitulo());
			st.setString(2, lembrete.getDescricao());
			
			st.execute();
			
			ResultSet rs = st.getGeneratedKeys();
			
			if(rs.next()) {
				int idGenerated = rs.getInt(1);
				lembrete.setId(idGenerated);
			}
			
		} catch(Exception e) {
			throw new ApiException(500, e.getMessage());
		}
		
		//podemos retornar a instância que foi inserida fazendo um select no banco
		//return this.select(lembrete);
		
		return lembrete;
	}
	
	/**
	 * Atualiza um lembrete na base de dados
	 * @param lembrete a ser atualizado na base de dados
	 * @return lembrete atualizado
	 * @throws ApiException lançada quando o lembrete não existe no banco de dados ou quando há qualquer erro no banco de dados
	 */
	public Lembrete update(Lembrete lembrete) throws ApiException {
		Lembrete lemb = null;
		try {
			String statement = "UPDATE lembrete SET titulo=?, descricao=? WHERE id_lembrete=?";
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			PreparedStatement st = con.prepareStatement(statement);
			st.setString(1, lembrete.getTitulo());
			st.setString(2, lembrete.getDescricao());
			st.setInt(3, lembrete.getId());
			
			st.execute();
			
			//getUpdateCount retorna a quantidade de linhas que 
			//foram modificadas após a execução do comando update
			if(st.getUpdateCount() == 0) {
				throw new ApiException(404, "O lembrete informado não existe");
			}
			
		} catch(ApiException ae) {
			throw ae;
		} catch(Exception e) {
			throw new ApiException(500, "Erro não especificado. " + e.getMessage());
		}
		
		lemb = select(lembrete);
		
		return lemb;
	}
	
	/**
	 * Deleta um lembrete na base de dados
	 * @param lembrete lembrete a ser excluído da base de dados ou quando há qualquer erro no banco de dados
	 * @return null caso o lembrete tenha sido excluído
	 * @throws ApiException lançada quando o lembrete não existe no banco de dados 
	 */
	public Lembrete delete(Lembrete lembrete) throws ApiException {	
		try {
			String statement = "DELETE FROM lembrete WHERE id_lembrete=?";
			
			Connection con = DatabaseConnectionFactory.getConnection();
			
			PreparedStatement st = con.prepareStatement(statement);
			
			st.execute();
			
			//checa a quantidade de linhas modificadas
			if(st.getUpdateCount() == 0) {
				throw new ApiException(404, "O lembrete informado não existe.");
			}
			
		} catch(ApiException e) {
			
		} catch(Exception e) {
			throw new ApiException(500, "Erro não especificado. " + e.getMessage());
		}
		
		return null;
	}
}
