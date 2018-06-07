package net.raxtran.FreQ;

public class Voto {
	
	private String Usuario;
	private int Pregunta;
	private String Tipo;
	
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	public int getPregunta() {
		return Pregunta;
	}
	public void setPregunta(int pregunta) {
		Pregunta = pregunta;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	@Override
	public String toString() {
		return "Voto [Usuario=" + Usuario + ", Pregunta=" + Pregunta + ", Tipo=" + Tipo + "]";
	}

}
