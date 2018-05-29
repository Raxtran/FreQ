package net.raxtran.FreQ;

public class Pregunta {

	int id;
	String Texto;
	int Likes;
	int Usefull;
	int Dislikes;
	String Categoria;
	String UserPreg;
	String UserAnws;
	
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
	
	
}
