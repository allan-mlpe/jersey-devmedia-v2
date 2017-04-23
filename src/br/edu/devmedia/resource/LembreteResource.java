package br.edu.devmedia.resource;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.devmedia.dao.LembreteMapper;
import br.edu.devmedia.domain.Lembrete;
import br.edu.devmedia.domain.LembreteRepository;
import br.edu.devmedia.domain.Pagina;
import br.edu.devmedia.exception.ApiException;

@Path("/lembrete")
public class LembreteResource {

	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("page") String page) throws ApiException {

		LembreteRepository lr = new LembreteRepository();
		List<Lembrete> result = null;
		Pagina resultPage = null;

		// se a página é nula ou não foi especificada retornamos os 5 primeiros
		// lembretes pelo método getAll()
		if (page == null || (page.isEmpty())) {
			result = lr.getAll();
			resultPage = new Pagina(result);

			// podemos declarar a entity diretamente dentro do .ok()
			return Response.ok(resultPage).build();
		}

		/*
		 * como o método this.get está lançando ApiException, nós podemos criar
		 * aqui um erro customizado e lançá-lo para que o próprio Jersey trate a
		 * sua conversão para JSON e apresentação como retorno para a requisição
		 * do serviço.
		 * 
		 * Vale lembrar que ApiException herda de WebApplicationException e
		 * recebe um objeto Response como parâmetro.
		 * 
		 * As próximas duas estruturas condicionais lançam a ApiException com
		 * uma mensagem customizada que será convertida para JSON pelo e
		 * retornada para o usuário como responsa da requisição, tudo isso feito
		 * pelo próprio Jersey.
		 */
		if (page == "0") {
			throw new ApiException(400,
					"A página não pode ser igual a 0. Informe um valor maior ou igual a 1.");
		}

		if (Pattern.matches("^\\d+", page)) {
			throw new ApiException(400,
					"Um valor inválido foi fornecido para um ou mais parâmetros");
		}

		//happy path
		int pageNum = Integer.parseInt(page);
		result = lr.getByRange(pageNum);
		resultPage = new Pagina(result);

		// também podemos declarar explicitamente a entity após o .ok()
		return Response.ok().entity(resultPage).build();
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response getById(@PathParam("id") int id) throws ApiException {
		Lembrete lembResult;
		
		if(id == 0) {
			throw new ApiException(400, 
					"O ID deve ser maior que zero.");
		}
		
		LembreteMapper mapper = new LembreteMapper();
		Lembrete lemb = new Lembrete();
		lemb.setId(id);
		
		
		lembResult = mapper.select(lemb);
		
		if(lembResult == null) {
			throw new ApiException(204, 
					"Não existe lembrete com o ID especificado.");
		}
		
		return Response.ok(lembResult).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response insert(Lembrete lembrete) throws ApiException {
		
		return null;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response update() throws ApiException {
		
		return null;
	}
	
	@DELETE
	public Response remove() throws ApiException {
		
		return null;
	}
}
