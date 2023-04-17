package opiniones.graphql;

import javax.servlet.annotation.WebServlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;
import opiniones.servicio.ServicioOpinionesGraphQL;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
      
    	 return new GraphQLSchemaGenerator()
    		        .withOperationsFromSingletons(
    		            new ServicioOpinionesGraphQL()
    		            )
    		        .generate();
    }
}