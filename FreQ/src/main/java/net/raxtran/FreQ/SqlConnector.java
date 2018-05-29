package net.raxtran.FreQ;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlConnector {

	Connection conn;

	public SqlConnector() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://172.17.0.1/proyecto", "root", "ies2010");
	}

	public User getUser(String Id) throws SQLException {

		User usuario = new User();

		PreparedStatement p = this.conn.prepareStatement("SELECT * FROM User WHERE Username = ?");

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
		return usuario;

	}

	public List<Categoria> getCategorias() throws SQLException {

		List<Categoria> categorias = new ArrayList<>();

		PreparedStatement p = conn.prepareStatement("SELECT * FROM Categoria");
		ResultSet rs = p.executeQuery();

		while (rs.next()) {
			Categoria categoria = new Categoria();
			categoria.setId(rs.getInt("Id"));
			categoria.setImagen(rs.getString("Imagen"));
			categoria.setNombre(rs.getString("Nombre"));

			categorias.add(categoria);

		}
		return categorias;

	}

	public List<User> getTops(String id) throws SQLException {

		List<User> usuarios = new ArrayList<>();

		if (id.equals("Likes") || id.equals("Dislikes") || id.equals("Usefull")) {

			PreparedStatement p = conn.prepareStatement("select * from User order by " + id + " DESC limit 10");

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
			return usuarios;

		}
		return usuarios;
	}

	public List<Categoria> getCategoriaDominante(String Id) throws SQLException {

		List<Categoria> Categorias = new ArrayList<>();

		PreparedStatement p = this.conn.prepareStatement("SELECT * FROM Categoria_user cu "
				+ "inner join Categoria c on c.Id = cu.Categoria  " + "WHERE Usuario = ? ");

		p.setInt(1, Integer.parseInt(Id));
		ResultSet rs = p.executeQuery();

		while (rs.next()) {
			Categoria Categoria = new Categoria();

			Categoria.setId(rs.getInt("Categoria"));
			Categoria.setNombre(rs.getString("Nombre"));

			Categorias.add(Categoria);
		}

		return Categorias;

	}

	public List<Pregunta> getPreguntasSR(String Id) throws SQLException {

		List<Pregunta> Preguntas = new ArrayList<>();

		PreparedStatement p = this.conn
				.prepareStatement("SELECT p.Likes, p.Dislike, p.Useful, p.Texto, c.Nombre, u.Username "
						+ "FROM Pregunta p " + "inner join Categoria c on c.Id = p.Categoria "
						+ "inner join User u on u.Id = p.UserPreg " + "left join Respuesta r on r.Pregunta_id = p.Id "
						+ "WHERE p.UserAnws = ? AND r.Pregunta_id is Null");

		p.setInt(1, Integer.parseInt(Id));
		ResultSet rs = p.executeQuery();

		while (rs.next()) {

			Pregunta pregunta = new Pregunta();

			pregunta.setCategoria(rs.getString("Nombre"));
			pregunta.setLikes(rs.getInt("Likes"));
			pregunta.setUsefull(rs.getInt("Useful"));
			pregunta.setDislikes(rs.getInt("Dislike"));
			pregunta.setTexto(rs.getString("Texto"));
			pregunta.setUserPreg(rs.getString("Username"));

			Preguntas.add(pregunta);

		}

		return Preguntas;
	}

	public List<PreguntaCR> getPreguntasCR(String Id) throws SQLException {

		List<PreguntaCR> Preguntas = new ArrayList<>();

		PreparedStatement p = this.conn.prepareStatement(
				"SELECT p.Likes, p.Dislike, p.Useful, p.Texto, c.Nombre, u.Username, r.Texto as rText, r.Likes as rLikes , r.Dislikes as rDislikes, r.Useful as rUseful "
						+ "from Pregunta p " + "inner join Categoria c on c.Id = p.Categoria "
						+ "inner join User u on u.Id = p.UserPreg " + "inner join Respuesta r on r.Pregunta_id = p.Id "
						+ "WHERE p.UserAnws = ?");

		p.setInt(1, Integer.parseInt(Id));
		ResultSet rs = p.executeQuery();

		while (rs.next()) {

			PreguntaCR pregunta = new PreguntaCR();

			pregunta.setCategoria(rs.getString("Nombre"));
			pregunta.setLikes(rs.getInt("Likes"));
			pregunta.setUsefull(rs.getInt("Useful"));
			pregunta.setDislikes(rs.getInt("Dislike"));
			pregunta.setTexto(rs.getString("Texto"));
			pregunta.setUserPreg(rs.getString("Username"));
			pregunta.setrLikes(rs.getInt("rLikes"));
			pregunta.setrDislikes(rs.getInt("rDislikes"));
			pregunta.setrUseful(rs.getInt("rUseful"));
			pregunta.setrText(rs.getString("rText"));
			Preguntas.add(pregunta);

		}

		return Preguntas;
	}

	public List<User> getUsersCategoria(String Id) throws SQLException {

		List<User> Usuarios = new ArrayList<>();

		PreparedStatement p = this.conn
				.prepareStatement("SELECT u.Username, c.Nombre, u.Picture, u.Likes, u.Dislikes, u.Usefull "
						+ "FROM Categoria_user cu " + "inner join Categoria c  " + "on c.id = cu.Categoria "
						+ "inner join User u " + "on u.id = cu.Usuario " + "where c.Nombre = ? ");

		p.setString(1, Id);
		ResultSet rs = p.executeQuery();

		while (rs.next()) {
			User usuario = new User();

			usuario.setUsername(rs.getString("Username"));
			usuario.setPicture(rs.getString("Picture"));
			usuario.setLikes(rs.getInt("Likes"));
			usuario.setDislikes(rs.getInt("Dislikes"));
			usuario.setUsefull(rs.getInt("Usefull"));
			System.out.println(usuario);

			Usuarios.add(usuario);
		}

		return Usuarios;

	}

	public List<Pregunta> getTopPreguntaCategoria(String Id) throws SQLException {
		List<Pregunta> Preguntas = new ArrayList<>();

		PreparedStatement p = this.conn.prepareStatement(
				"Select p.Likes, p.Dislike as Dislikes, p.Useful as Usefull, p.Texto, c.Nombre, Q.Username as uPreg,A.Username as uAnws \n"
						+ " from Pregunta p\n" + "inner join Categoria c on c.Id = p.Categoria \n"
						+ "inner join User Q on Q.Id = p.UserPreg \n" + "inner join User A on A.Id = p.UserAnws \n"
						+ "order by " + Id + " DESC limit 10;");
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

		return Preguntas;

	}

	public String insertPregunta(String[] Datos) throws SQLException {

		PreparedStatement p = this.conn.prepareStatement("Select Id from Categoria where nombre = ?");
		p.setString(1, Datos[1]);
		ResultSet rs = p.executeQuery();
		rs.next();
		int IdCategoria = rs.getInt("Id");
		

		p = this.conn.prepareStatement("Select Id from User where Username = ?");
		p.setString(1, Datos[0]);
		ResultSet rs1 = p.executeQuery();
		rs1 = p.executeQuery();
		rs1.next();
		int remitente = rs1.getInt("Id");
		
		p = this.conn.prepareStatement("Select Id from User where Username = ?");
		p.setString(1, Datos[3]);
		ResultSet rs2 = p.executeQuery();
		rs2.next();
		int destinatario = rs2.getInt("Id");

		// REMITENTE - CATEGORIA- TEXTO - DESTINATARIO
		p = this.conn.prepareStatement("Insert into Pregunta(UserPreg,Categoria,Texto,UserAnws) " + "Values(?,?,?,?)");

		p.setInt(1, remitente);
		p.setInt(2, IdCategoria);
		p.setString(3, Datos[2]);
		p.setInt(4, destinatario);
		p.executeUpdate();
		
		
		return "Mensaje enviado con exito";

	}

	public String isUserOk(String user) throws SQLException {

		PreparedStatement p = this.conn.prepareStatement("Select Contraseña from User where Username = ?");

		p.setString(1, user);
		ResultSet rs = p.executeQuery();
		String pswd = null;

		if (rs.next()) {
			pswd = rs.getString("Contraseña");

		}

		return pswd;
	}

}
