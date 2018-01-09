package br.edu.devmedia.resource;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

import javax.ws.rs.ext.Provider;
/**
 * Interface que contém configurações de definição/descrição da API
 * @author Allan
 *
 */
@SwaggerDefinition(
        info = @Info(
                description = "Lembretes API",
                version = "V1.0.0",
                title = "API construída no curso de Jersey v2 da Devmedia",
                termsOfService = "share and care",
                contact = @Contact(name = "Allan", email = "aml3@cin.ufpe.br", url = "http://github.com/allan-mlpe"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org")
                ),
        consumes = {"application/json" },
        produces = {"application/json" },
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS}
)
@Provider //anotação requerida para que o jersey processe essa interface e as definições do swagger sejam consideradas
public interface ApiDefinition {}
