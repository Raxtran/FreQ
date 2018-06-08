package net.raxtran.FreQ;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlConnector {

	private Connection conn;
	private String Bdd = "jdbc:mysql://hl20.dinaserver.com/freq";
	private String BddUser = "raxtran";
	private String BddPwd = "pth34cc93";
	
	public SqlConnector() {
		try {
		conn = DriverManager.getConnection(Bdd, BddUser, BddPwd);
		}catch(SQLException e) {
			System.out.println("Error conectandose a la base de datos -> "+e.getMessage());
		}
	}

	public User getUser(String Id)  {
		String query = "SELECT * FROM User WHERE Username = ?";
		User usuario = new User();
		try {
		PreparedStatement p = this.conn.prepareStatement(query);

		p.setString(1, Id);
		ResultSet rs = p.executeQuery();

		if (rs.next()) {

			usuario.setUsername(rs.getString("Username"));
			usuario.setPicture(rs.getString("Picture"));
			usuario.setBio(rs.getString("Bio"));
			usuario.setLikes(rs.getInt("Likes"));
			usuario.setDislikes(rs.getInt("Dislikes"));
			usuario.setUsefull(rs.getInt("Usefull"));
			usuario.setId(rs.getInt("Id"));

		}
		}catch(SQLException e) {		
			// Cuando intentan acceder a las preguntas de un usuario de manera ilícita
			System.out.println("Intentan acceder a un usuario inexistente de manera deshonesta ("+e.getMessage()+") ");	
			
		}
		return usuario;

	}

	public List<Categoria> getCategorias() {

		List<Categoria> categorias = new ArrayList<>();
		String query = "SELECT * FROM Categoria";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			ResultSet rs = p.executeQuery();
	
			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setId(rs.getInt("Id"));
				categoria.setImagen(rs.getString("Imagen"));
				categoria.setNombre(rs.getString("Nombre"));
	
				categorias.add(categoria);
	
			}
		}catch(SQLException e){
			// Cuando intentan acceder a las preguntas de un usuario de manera ilícita
			System.out.println("Bé... es lamentable, però... han conseguido cojer todas las categorias... de manera deshonesta ("+e.getMessage()+") ");
		}
		return categorias;

	}

	public List<User> getTops(String id)  {

		List<User> usuarios = new ArrayList<>();

		if (id.equals("Likes") || id.equals("Dislikes") || id.equals("Usefull")) {
			
			String query = "SELECT * from User order by "+id+" DESC limit 10";
			try {
				PreparedStatement p = conn.prepareStatement(query);
				
				ResultSet rs = p.executeQuery();
	
				while (rs.next()) {
					User usuario = new User();
					usuario.setUsername(rs.getString("Username"));
					usuario.setPicture(rs.getString("Picture"));
					usuario.setLikes(rs.getInt("Likes"));
					usuario.setDislikes(rs.getInt("Dislikes"));
					usuario.setUsefull(rs.getInt("Usefull"));
					usuario.setId(rs.getInt("Id"));
					usuario.setBanner(rs.getString("Banner"));
	
					usuarios.add(usuario);
				}
			}catch(SQLException e) {
				// Han traspasado el if de seguridad, intentan acceder a tops aun no publicados
				System.out.println("Estan intentando acceder a tops no publicados.... de manera deshonesta ("+e.getMessage()+") ");
			}
			return usuarios;

		}
		return usuarios;
	}

	public List<Categoria> getCategoriaDominante(String Id) {

		List<Categoria> Categorias = new ArrayList<>();
		String query = 
				"SELECT * FROM Categoria_user cu "
				+ "inner join Categoria c on c.Id = cu.Categoria "
				+ "WHERE Usuario = ? ";
		try {
		PreparedStatement p = this.conn.prepareStatement(query);

		p.setInt(1, Integer.parseInt(Id));
		ResultSet rs = p.executeQuery();

		while (rs.next()) {
			Categoria Categoria = new Categoria();

			Categoria.setId(rs.getInt("Categoria"));
			Categoria.setNombre(rs.getString("Nombre"));

			Categorias.add(Categoria);
		}
		}catch(SQLException e) {
			// Cuando intentan acceder a las categorias que domina un usuario de manera deshonesta
			System.out.println("Intentan acceder a las categorias dominantes de un usuario de manera deshonesta ("+e.getMessage()+") ");			
		}
		return Categorias;

	}

	public List<Pregunta> getPreguntasSR(String Id) throws SQLException {

		List<Pregunta> Preguntas = new ArrayList<>();
		String query = 
				"SELECT p.Id as Id, p.Likes, p.Dislikes, p.Usefull, p.Texto, c.Nombre, u.Username "
				+ "FROM Pregunta p "
				+ "inner join Categoria c on c.Id = p.Categoria "
				+ "left join User u on u.Id = p.UserPreg "
				+ "left join Respuesta r on r.Pregunta_id = p.Id "
				+ "WHERE p.UserAnws = ? AND r.Pregunta_id is Null "
				+ "order by p.Id DESC ";
		try {
		PreparedStatement p = this.conn.prepareStatement(query);

		p.setInt(1, Integer.parseInt(Id));
		ResultSet rs = p.executeQuery();

		while (rs.next()) {

			Pregunta pregunta = new Pregunta();

			pregunta.setId(rs.getInt("Id"));
			pregunta.setCategoria(rs.getString("Nombre"));
			pregunta.setLikes(rs.getInt("Likes"));
			pregunta.setUsefull(rs.getInt("Usefull"));
			pregunta.setDislikes(rs.getInt("Dislikes"));
			pregunta.setTexto(rs.getString("Texto"));

			if (rs.getString("Username") == null) {
				pregunta.setUserPreg("Anon");
			} else {
				pregunta.setUserPreg(rs.getString("Username"));
			}
			Preguntas.add(pregunta);

		}
		}catch(SQLException e) {
			// Cuando intentan acceder a las preguntas de un usuario de manera ilícita
			System.out.println("Intentan acceder a las preguntas no respondidas de un usuario de manera deshonesta ("+e.getMessage()+") ");			
		}
		return Preguntas;
	}

	public List<PreguntaCR> getPreguntasCR(String Id) {
		List<PreguntaCR> Preguntas = new ArrayList<>();
		String query = 
				"SELECT p.Id as Id, p.Likes, p.Dislikes, p.Usefull, p.Texto, c.Nombre, u.Username, r.Texto as rText, r.Likes as rLikes , r.Dislikes as rDislikes, r.Usefull as rUseful "
						+ "from Pregunta p "
						+ "inner join Categoria c on c.Id = p.Categoria "
						+ "left join User u on u.Id = p.UserPreg "
						+ "inner join Respuesta r on r.Pregunta_id = p.Id "
						+ "WHERE p.UserAnws = ? "
						+ "order by p.Id DESC ";
		try {
			PreparedStatement p = this.conn.prepareStatement(query);
	
			p.setInt(1, Integer.parseInt(Id));
			ResultSet rs = p.executeQuery();
	
			while (rs.next()) {
	
				PreguntaCR pregunta = new PreguntaCR();
	
				pregunta.setId(rs.getInt("Id"));
				pregunta.setCategoria(rs.getString("Nombre"));
				pregunta.setLikes(rs.getInt("Likes"));
				pregunta.setUsefull(rs.getInt("Usefull"));
				pregunta.setDislikes(rs.getInt("Dislikes"));
				pregunta.setTexto(rs.getString("Texto"));
				pregunta.setrLikes(rs.getInt("rLikes"));
				pregunta.setrDislikes(rs.getInt("rDislikes"));
				pregunta.setrUseful(rs.getInt("rUseful"));
				pregunta.setrText(rs.getString("rText"));
				if (rs.getString("Username") == null) {
					pregunta.setUserPreg("Anon");
				} else {
					pregunta.setUserPreg(rs.getString("Username"));
				}
				Preguntas.add(pregunta);
			}
		}catch(SQLException e) {
			// Cuando intantan acceder a las preguntas de un usuario de manera ilícita
			System.out.println("Intentan acceder a las preguntas respondidas de un usuario de manera deshonesta ("+e.getMessage()+") ");
		}
		return Preguntas;
	}

	public List<User> getUsersCategoria(String Id) {

		List<User> Usuarios = new ArrayList<>();
		String query = "SELECT u.Id, u.Username, c.Nombre, u.Picture, u.Likes, u.Dislikes, u.Usefull "
					+ "FROM Categoria_user cu "
					+ "inner join Categoria c on c.id = cu.Categoria "
					+ "inner join User u on u.id = cu.Usuario "
					+ "where c.Nombre = ? ";
		try {

			PreparedStatement p = this.conn.prepareStatement(query);

			p.setString(1, Id);
			ResultSet rs = p.executeQuery();
	
			while (rs.next()) {
				User usuario = getAllUserDataById(rs.getInt("u.Id"));
				Usuarios.add(usuario);
			}
		} catch (SQLException e) {
			// Cuando la categoria no existe
			System.out.println("Intentan acceder a una categoria que no existen ("+e.getMessage()+") ");
		}
		return Usuarios;

	}

	public List<Pregunta> getTopPreguntaCategoria(String topType) {
		List<Pregunta> Preguntas = new ArrayList<>();
		String query = "Select p.Likes, p.Dislikes as Dislikes, p.Usefull as Usefull, p.Texto, c.Nombre, Q.Username as uPreg,A.Username as uAnws "
				+ "from Pregunta p inner join Categoria c on c.Id = p.Categoria "
				+ "left join User Q on Q.Id = p.UserPreg "
				+ "inner join User A on A.Id = p.UserAnws "
				+ "order by "+topType+" DESC limit 10";

		try {
			PreparedStatement p = this.conn.prepareStatement(query);
	
			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				Pregunta pregunta = new Pregunta();

				pregunta.setCategoria(rs.getString("Nombre"));
				pregunta.setLikes(rs.getInt("Likes"));
				pregunta.setUsefull(rs.getInt("Usefull"));
				pregunta.setDislikes(rs.getInt("Dislikes"));
				pregunta.setTexto(rs.getString("Texto"));
				pregunta.setUserPreg(rs.getString("uPreg"));
				pregunta.setUserAnws(rs.getString("uAnws"));

				Preguntas.add(pregunta);

			}
		} catch (SQLException e) {
			// Cuando el top no existe
			System.out.println("Intentan acceder a un top de comentarios que no existe ("+e.getMessage()+") ");
		}

		return Preguntas;

	}

	public String insertPregunta(Pregunta datosPregunta)  {
		// REMITENTE - CATEGORIA- TEXTO - DESTINATARIO - Token

		int IdCategoria = 0;
		int remitente = -1;
		int destinatario = 0;
		String query = "Select Id from Categoria where nombre = ?";
		PreparedStatement p;
				
		if(datosPregunta.getToken().equals(getToken(datosPregunta.getUserPreg())) || datosPregunta.getUserPreg().equals("Anon") ) {
			try {
				// Id de categoria a traves del nombre de la categoria
				p = this.conn.prepareStatement(query);
				p.setString(1, datosPregunta.getCategoria());
				ResultSet rs = p.executeQuery();
				rs.next();
				IdCategoria = rs.getInt("Id");
			}catch(SQLException e) {		
				// Intentan poner una categoria de un comentario de manera ilícita
				System.out.println("Intentan poner una categoria que el usuario no domina de manera deshonesta ("+e.getMessage()+") ");	
				
			}
			// Si el usuario remitente que llega no es Anon, busca su id
			if (!datosPregunta.getUserPreg().equals("Anon")) {
				remitente = getUserIdByUsername(datosPregunta.getUserPreg());
			}
	
			destinatario = getUserIdByUsername(datosPregunta.getUserAnws());
			
			try {
				// Insertar los datos en pregunta
				query = "Insert into Pregunta(UserPreg,Categoria,Texto,UserAnws) " + "Values(?,?,?,?)";
				p = this.conn.prepareStatement(query);
				// Si el remitente es -1 (No a buscado el id), pone en null el id usuario quepregunta
				if (remitente == -1) {
					p.setNull(1, java.sql.Types.INTEGER);
				} else {
					p.setInt(1, remitente);
		
				}
				// Id de la categoria
				p.setInt(2, IdCategoria);
				// Texto
				p.setString(3, datosPregunta.getTexto());
				// Destinatario
				p.setInt(4, destinatario);
				p.executeUpdate();
	
			}catch(SQLException e) {
				// Error insertando la pregunta
				System.out.println("Error insertando una pregunta con el mensaje ("+e.getMessage()+"), vigila a tus usuarios");
			}
		}
		else {
			System.out.print("Intentan postear una pregunta de manera extraña desde el usuario "+
					datosPregunta.getUserPreg()+", el cual posee este token "+datosPregunta.getToken());
		}
		return "It's ok";

	}

	public String login(User usuario) {
		String query = "Select contraseña from User where Username = ?";
		String pswd = null;
		Boolean authStatus = false;
		String token = null;
		
		try {
			PreparedStatement p = this.conn.prepareStatement(query);
	
			p.setString(1, usuario.getUsername());
			ResultSet rs = p.executeQuery();
	
			if (rs.next()) {
				pswd = rs.getString("Contraseña");
				//Si las contraseñas son iguales devuelve true
				if(pswd.equals(usuario.getContraseña())) {
					
					authStatus = setToken(usuario.getUsername(),createToken());
				}
				//Si a podido insertar un token
				if(authStatus) {
					token = getToken(usuario.getUsername());
				}
	
			}
		}catch(SQLException e) {
			// Error al recibir/Enviar una contraseña
			System.out.println("Error al enviar/recibir una contraseña.... ("+e.getMessage()+")");
		}

		return token;
	}
	public Boolean logout(User usuario) {
		String username = usuario.getUsername();
		String token = usuario.getToken();
		Boolean status = false;
		String query = "Select * FROM Token where Username = ?";
		
		try {
			PreparedStatement p = this.conn.prepareStatement(query);
			p.setString(1, username);

			ResultSet rs = p.executeQuery();
			
			if(rs.next() && rs.getString("Token").equals(token)) {
				status = true;
			}
			else {
				status = false;
			}
			
		}catch(SQLException e) {
			System.out.println("El token o el usuario es "+e.getMessage() );
		}
		return status;
	}
	
	public String updateVotacionesP(Voto votacion) {
		String query = "select Pregunta "
				+ "from Usuario_Vota_Pregunta "
				+ "where Usuario = ? and Pregunta = ?";
		
		int userID;
		PreparedStatement p;
		
		try {
			userID = getUserIdByUsername(votacion.getUsuario());
	
			p = this.conn.prepareStatement(query);
			
	
			p.setInt(1, userID);
			p.setInt(2, votacion.getPregunta());
			System.out.println(votacion);
			ResultSet rs = p.executeQuery();
				
			// Si NO existe ya un registro de voto, que lo INSERTE
			if (!rs.next()) {
				
				query = "Select "+votacion.getTipo()+" from Pregunta where id = ?";
				String update = "update Pregunta set "+votacion.getTipo()+" = ? where Id = "+votacion.getPregunta();
				
				Integer remitente = getWhoIdByPreguntaId(votacion.getPregunta(), "UserPreg");
			
				updateVotacion(votacion,query,update);

				//Si el remitente no es null que le sume el voto a su cuenta
				if(remitente != 0) {
					updateVotosUsuario(votacion,remitente);
				}			
				//Con esto define que este usuario ya a votado la pregunta
				query ="INSERT INTO Usuario_Vota_Pregunta VALUES ("+userID+","+votacion.getPregunta()+")";
				p = this.conn.prepareStatement(query);
				p.executeUpdate();	
			}
		} catch (SQLException e) {
			// Error al votar una pregunta 
			System.out.print("Error al votar una pregunta "+(e.getMessage()));
		}
		return "it's ok";
	}
	public String updateVotacionesR(Voto votacion) throws SQLException {
		
		String query = "select Respuesta "
				+ "from Usuario_Vota_Respuesta "
				+ "where Usuario = ? and Respuesta = ?";
		
		int userID = getUserIdByUsername(votacion.getUsuario());

		PreparedStatement p = this.conn.prepareStatement(query);

		p.setInt(1, userID);
		p.setInt(2, votacion.getPregunta());
		System.out.println(votacion);
		ResultSet rs = p.executeQuery();
		
		// Si NO existe ya un registro de voto, que lo INSERTE
		if (!rs.next()) {
			
			query = "Select "+votacion.getTipo()+" from Respuesta where Pregunta_id = ?";
			String update = "update Respuesta set "+votacion.getTipo()+" = ? where Pregunta_id = "+votacion.getPregunta();

			updateVotacion(votacion,query,update);
			Integer remitente = getWhoIdByPreguntaId(votacion.getPregunta(),"UserAnws");

			updateVotosUsuario(votacion,remitente);
			
			query ="INSERT INTO Usuario_Vota_Respuesta VALUES ("+userID+","+votacion.getPregunta()+")";
			p = this.conn.prepareStatement(query);
			p.executeUpdate();	
		}
		return "It's ok";

	}
	public List<User> getUsersPopulares(){
		String query = "SELECT UserAnws, count(id) as cont " + 
				"FROM Pregunta " + 
				"group by UserAnws " + 
				"ORDER BY cont " + 
				"DESC limit 10";
		List<User> usuarios = new ArrayList<>();
		
		try {
			PreparedStatement p = this.conn.prepareStatement(query);
			//adsjipsdfjopsdjopsdfjopsdf
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				User usuario = getAllUserDataById(rs.getInt("UserAnws"));
				usuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			// Error en populares
			System.out.println("Error recopilando los usuarios mas populares... "+e.getMessage());
		}
		
		return usuarios;
		
	}
	private User getAllUserDataById(int idUser) {
		String query = "Select * from User where id = ?";
		User usuario = new User();		
		try {
			
		PreparedStatement p = this.conn.prepareStatement(query);
		p.setInt(1,idUser);
		
		ResultSet rs = p.executeQuery();
		rs.next();
				
		usuario.setUsername(rs.getString("Username"));
		usuario.setPicture(rs.getString("Picture"));
		usuario.setLikes(rs.getInt("Likes"));
		usuario.setDislikes(rs.getInt("Dislikes"));
		usuario.setUsefull(rs.getInt("Usefull"));
		usuario.setId(rs.getInt("Id"));
		usuario.setBanner(rs.getString("Banner"));
		} catch (SQLException e) {
			// Error reCojiendo usuarios
			System.out.println("Error devolviendo un objeto usuario "+e.getMessage());
		}
		
		return 	usuario;
	}
	private void updateVotosUsuario(Voto votacion, Integer remitente) {

		//Recoje la votacion del usuario
		String query = "SELECT "+votacion.getTipo()+" FROM User where Id = "+remitente;
		String update = "update User set "+votacion.getTipo()+" = ? where Id = "+remitente;

		try {
			PreparedStatement p = this.conn.prepareStatement(query);
			
			ResultSet userUpdate = p.executeQuery();
			userUpdate.next();
			int votosParaActualizar = userUpdate.getInt(votacion.getTipo());
			//Suma 1 a el tipo de votacion indicada
			votosParaActualizar++;

			p = this.conn.prepareStatement(update);
			p.setInt(1,votosParaActualizar);
			p.executeUpdate();
			
		} catch (SQLException e) {
			// Error haciend update en los votos del usuario
			System.out.println("Error actualizando la votacion de un usuario, para mas información -> "+e.getMessage());
		}
		
	}
	private void updateVotacion(Voto votacion, String query,String update) {
		
		int puntuacionDeVotacion = 0;
		//Seleciona la cantidad de likes/usefull/Dislikes de la pregunta especificada
		PreparedStatement p;
		try {
			p = this.conn.prepareStatement(query);
				
			p.setInt(1, votacion.getPregunta());
			ResultSet puntuacionActual = p.executeQuery();
			puntuacionActual.next();
			
			puntuacionDeVotacion = puntuacionActual.getInt(votacion.getTipo());
			puntuacionDeVotacion ++;
			//hace un update de Pregunta/Respuesta y pone el valor nuevo
			p = this.conn.prepareStatement(update);
			p.setInt(1,puntuacionDeVotacion);
			p.executeUpdate();
			//Devuelve el remitente de la pregunta			
			
		} catch (SQLException e) {
			// Error haciendo
			System.out.print("Error haciendo un update de votacion en updatevotacion, "+e.getMessage());
		}
		
	}
	//Devuelve quien es por el id de la pregunta
	private int getWhoIdByPreguntaId(int idPregunta,String whoUpvote) {
		String query = "select "+whoUpvote+" from Pregunta where id = "+idPregunta;
		try {
		PreparedStatement p = this.conn.prepareStatement(query);
		ResultSet rs = p.executeQuery();
		rs.next();
		
		return rs.getInt(whoUpvote);
		} catch (SQLException e) {
			// Error recojiendo el id
			System.out.print("Error recojiendo un id a través de la pregunta, "+e.getMessage());
			return 0;
		}
	}
	//Devuelve el usuario por su nombre
	private int getUserIdByUsername(String Username) {

		String query = "select Id from User where Username = ? ";
		try {
		PreparedStatement p = this.conn.prepareStatement(query);

		p.setString(1, Username);
		
		ResultSet rs = p.executeQuery();
		rs.next();
		return rs.getInt("Id");
		} catch (SQLException e) {
			// Error recojiendo el id del usuario a traves de su nombre
			System.out.print("Error devolviendo el id del usuario a traves de su nombre, "+e.getMessage());
			return 0;
		}
	}
	private Boolean setToken(String Username, String createToken) {
		
		String query = "Select Token from Token where Username = ?";

		String insert = "INSERT INTO Token(Username, Token) VALUES (?,?)";
		
		PreparedStatement p;
		
		try {

			p = this.conn.prepareStatement(query);

			p.setString(1,Username);

			ResultSet rs = p.executeQuery();
			//Si ya hay un token que devuelva true
			if(rs.next()) {
				return true;
			}
			//Si no tiene token que lo cree, y que devuelva true
			else {
				p = this.conn.prepareStatement(insert);
	
				p.setString(1,Username);
				p.setString(2, createToken);
				p.executeUpdate();
				return true;
		
			}
		
		} catch (SQLException e) {
			// Error insertando el token
			System.out.println("Error insertando token... "+e.getMessage());
			return null;

		}
		
	}
	private String getToken(String username) {
		// Devolverá token
		String query = "Select Token from Token where Username = ?";
		
		PreparedStatement p;
		String token = null;
		try {
			
			p = this.conn.prepareStatement(query);
			p.setString(1,username);
			
			ResultSet rs = p.executeQuery();

			rs.next();
			
			token = rs.getString("Token");

		}catch(SQLException e) {
			System.out.println("Error recibiendo el token "+e.getMessage());
		}
		return token ;
	}
	private String createToken() {
		String token = UUID.randomUUID().toString();
		return token;
	}

}
