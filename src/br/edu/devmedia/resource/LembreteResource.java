package br.edu.devmedia.resource;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.devmedia.domain.Lembrete;
import br.edu.devmedia.domain.LembreteRepository;
import br.edu.devmedia.domain.Pagina;
import br.edu.devmedia.exception.ApiException;

@Path("/lembrete")
public class LembreteResource {

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

}
