package net.raxtran.FreQ;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.JsonObject;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {

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

		enableCORS();
		SqlConnector sql = new SqlConnector();



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
		
		post("/users/post", (req, res) ->{
	

			String[] sub = req.body().split("=");
			String[] Datos = new String[4];
			int n= 0;
			for(int i = 1; i<sub.length; i++) {
				
				String[] b = sub[i].split("&");
				Datos[n] = b[0];
				n++;
			}
			
		return sql.insertPregunta(Datos);
		}, new JsonTransform());
		
		
		get("/Login/:id", (request, response) -> {
			String pswd = sql.isUserOk(request.params(":id"));
			return pswd;
		}, new JsonTransform());
		
	}
}
