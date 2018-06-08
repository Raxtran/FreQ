package net.raxtran.FreQ;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import static spark.Spark.*;

public class App {

	static ObjectMapper mapper = new ObjectMapper();

	private static void enableCORS() {

		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Request-Method", "GET");
			response.header("Access-Control-Allow-Headers", "*");
			response.type("application/json");
		});
	}

	public static void main(String[] args) throws SQLException {
		SqlConnector sql = new SqlConnector();

		enableCORS();
		get("/User/:id", (request, response) -> {
			User usuario = sql.getUser(request.params(":id"));

			return usuario;
		}, new JsonTransform());

		get("/Tops/:id", (request, response) -> {

			List<User> usuarios = sql.getTops(request.params(":id"));
			return usuarios;
		}, new JsonTransform());

		get("/Categorias", (request, response) -> {

			List<Categoria> categorias = sql.getCategorias();
			return categorias;
		}, new JsonTransform());

		get("/Categorias/:id", (request, response) -> {

			List<User> usuarios = sql.getUsersCategoria(request.params(":id"));
			return usuarios;
		}, new JsonTransform());
		get("/Populares", (request, response) -> {

			List<User> usuarios = sql.getUsersPopulares();
			return usuarios;
		}, new JsonTransform());

		get("/Categorias/Usuario/:id", (request, response) -> {

			List<Categoria> categorias = sql.getCategoriaDominante(request.params(":id"));
			return categorias;
		}, new JsonTransform());

		get("/Preguntas/SinRespuesta/:id", (request, response) -> {

			List<Pregunta> pregunta = sql.getPreguntasSR(request.params(":id"));
			return pregunta;
		}, new JsonTransform());

		get("/Preguntas/ConRespuesta/:id", (request, response) -> {

			List<PreguntaCR> pregunta = sql.getPreguntasCR(request.params(":id"));
			return pregunta;
		}, new JsonTransform());
		get("/TopPreguntas/:id", (request, response) -> {

			List<Pregunta> pregunta = sql.getTopPreguntaCategoria(request.params(":id"));
			return pregunta;
		}, new JsonTransform());

		post("/users/post", (req, res) -> {
			Pregunta datosPregunta = mapper.readValue(req.body(), Pregunta.class);
			
			return sql.insertPregunta(datosPregunta);
		}, new JsonTransform());

		post("/Login", (request, response) -> {
			try {
			User usuario = mapper.readValue(request.body(), User.class);
			String token = sql.login(usuario);

			return token;
			}
			catch(UnrecognizedPropertyException e ) {
				System.out.print("Error parsing usuario.... algun campo no esta donde toca, concretamente "+e.getMessage());
				return "Error";
				
			}
		}, new JsonTransform());
		post("/Logout", (request,response) ->{
			
			User usuario = mapper.readValue(request.body(), User.class);
			
			Boolean logOutStatus = sql.logout(usuario);
			
			return logOutStatus;
		});
		put("/updateVotacionP", (request, response) -> {
		
			Voto votacion = mapper.readValue(request.body(), Voto.class);

			return sql.updateVotacionesP(votacion);

		});
		
		put("/updateVotacionR", (request, response) -> {
			Voto votacion = mapper.readValue(request.body(), Voto.class);

			return sql.updateVotacionesR(votacion);
		});
	}
}
