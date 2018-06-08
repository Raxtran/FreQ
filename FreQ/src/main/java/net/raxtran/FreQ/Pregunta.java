package net.raxtran.FreQ;

public class Pregunta {

	private int id;
	private String Texto;
	private int Likes;
	private int Usefull;
	private int Dislikes;
	private String Categoria;
	private String UserPreg;
	private String UserAnws;
	private String token;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTexto() {
		return Texto;
	}
	public void setTexto(String texto) {
		Texto = texto;
	}
	public int getLikes() {
		return Likes;
	}
	public void setLikes(int likes) {
		Likes = likes;
	}
	public int getUsefull() {
		return Usefull;
	}
	public void setUsefull(int usefull) {
		Usefull = usefull;
	}
	public int getDislikes() {
		return Dislikes;
	}
	public void setDislikes(int dislikes) {
		Dislikes = dislikes;
	}
	public String getCategoria() {
		return Categoria;
	}
	public void setCategoria(String categoria) {
		Categoria = categoria;
	}
	public String getUserPreg() {
		return UserPreg;
	}
	public void setUserPreg(String userPreg) {
		UserPreg = userPreg;
	}
	public String getUserAnws() {
		return UserAnws;
	}
	public void setUserAnws(String string) {
		UserAnws = string;
	}
	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", Texto=" + Texto + ", Likes=" + Likes + ", Usefull=" + Usefull + ", Dislikes="
				+ Dislikes + ", Categoria=" + Categoria + ", UserPreg=" + UserPreg + ", UserAnws=" + UserAnws + "]";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
