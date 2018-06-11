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
		//Devuelve un usuario a traves de su id 
		get("/User/:id", (request, response) -> {
			User usuario = sql.getUser(request.params(":id"));

			return usuario;
		}, new JsonTransform());
		//Devuelve todos los tops segun su id (likes, dislikes o usefull)
		get("/Tops/:id", (request, response) -> {

			List<User> usuarios = sql.getTops(request.params(":id"));
			return usuarios;
		}, new JsonTransform());
		//Devuelve todas las categorias
		get("/Categorias", (request, response) -> {

			List<Categoria> categorias = sql.getCategorias();
			return categorias;
		}, new JsonTransform());
		//Devuelve todas las categorias con el id especificado
		get("/Categorias/:id", (request, response) -> {

			List<User> usuarios = sql.getUsersCategoria(request.params(":id"));
			return usuarios;
		}, new JsonTransform());
		//Devuelve los usuarios con mas preguntas respondidas
		get("/Populares", (request, response) -> {

			List<User> usuarios = sql.getUsersPopulares();
			return usuarios;
		}, new JsonTransform());
		//Devuelve las categorias dominantes de un usuario
		get("/Categorias/Usuario/:id", (request, response) -> {

			List<Categoria> categorias = sql.getCategoriaDominante(request.params(":id"));
			return categorias;
		}, new JsonTransform());
		//Deavuelve las preguntas sin respuesta de un usuario en concreto
		get("/Preguntas/SinRespuesta/:user/:type", (request, response) -> {

			List<Pregunta> pregunta = sql.getPreguntasSR(request.params(":user"),request.params(":type"));
			return pregunta;
		}, new JsonTransform());
		//Devuelve las preguntas con respuesta de un usuario en concreto
		get("/Preguntas/ConRespuesta/:user/:type", (request, response) -> {

			List<PreguntaCR> pregunta = sql.getPreguntasCR(request.params(":user"),request.params(":type"));
			return pregunta;
		}, new JsonTransform());
		//Devuelve las preguntas ordenadas segun su top (likes, dislikes, usefull)
		get("/TopPreguntas/:id", (request, response) -> {

			List<Pregunta> pregunta = sql.getTopPreguntaCategoria(request.params(":id"));
			return pregunta;
		}, new JsonTransform());
		//Postea una pregunta
		post("/users/post", (req, res) -> {
			Pregunta datosPregunta = mapper.readValue(req.body(), Pregunta.class);
			
			return sql.insertPregunta(datosPregunta);
		}, new JsonTransform());
		//Postea una respuesta
		post("/users/post/anwser", (req,res) -> {
			Pregunta datosRespuesta = mapper.readValue(req.body(), Pregunta.class);
			return sql.insertRespuesta(datosRespuesta);
		});
		//Permite hacer log-in a un usuario
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
		//Permite deslogearse a un usuario
		post("/Logout", (request,response) ->{
			
			User usuario = mapper.readValue(request.body(), User.class);
			
			Boolean logOutStatus = sql.logout(usuario);
			
			return logOutStatus;
		});
		//Permite subir las votaciones de una pregunta
		put("/updateVotacionP", (request, response) -> {
		
			Voto votacion = mapper.readValue(request.body(), Voto.class);

			return sql.updateVotacionesP(votacion);

		});
		//Permite subir las votaciones de una respuesta
		put("/updateVotacionR", (request, response) -> {
			Voto votacion = mapper.readValue(request.body(), Voto.class);

			return sql.updateVotacionesR(votacion);
		});
	}
}
