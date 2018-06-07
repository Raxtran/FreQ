package net.raxtran.FreQ;

public  class User {
	
	private int id;
	private String Username; 
	private String Contraseña;
	private String Bio; 
	private String Picture; 
	private String Banner; 
	private int Likes;
	private int Usefull; 
	private int Dislikes;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getContraseña() {
		return Contraseña;
	}
	public void setContraseña(String contraseña) {
		Contraseña = contraseña;
	}
	public String getBio() {
		return Bio;
	}
	public void setBio(String bio) {
		Bio = bio;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public String getBanner() {
		return Banner;
	}
	public void setBanner(String banner) {
		Banner = banner;
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
	@Override
	public  String toString() {
		return "User [Username=" + Username + ", Likes=" + Likes + ", Usefull=" + Usefull + ", Dislikes=" + Dislikes
				+ "]";
	}
	
	
}
