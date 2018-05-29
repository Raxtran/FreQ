import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class Requestes {

    public usuario_activo;


    constructor(
        private httpclient: HttpClient
    ) { }

    //Consigues el perfil del usuario
    getUserId(Id) {
        return this.httpclient.get("http://localhost:4567/User/" + Id);

    }
    //Consigues todas las categorias
    getAllCategorias() {
        return this.httpclient.get("http://localhost:4567/Categorias");
    }
    //Devuelve todos los tops segun si le pasas likes, dislikes o usefull
    getTop(id) {

        return this.httpclient.get("http://localhost:4567/Tops/" + id);
    }
    //Devuelve las categorias de X usuario
    getCategoriaDominante(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/Usuario/" + Id);

    }
    //Devuelve las preguntas del usuario SIN respuesta
    getPreguntasDeUsuarioSR(Id) {
        return this.httpclient.get("http://localhost:4567/Preguntas/SinRespuesta/" + Id);

    }
    //Devuelve las preguntas del usuario CON respuesta
    getPreguntasDeUsuarioCR(Id) {
        return this.httpclient.get("http://localhost:4567/Preguntas/ConRespuesta/" + Id);

    }
    //Devuelve los usuarios que dominan X categoria
    getUsersQueDominanLaCategoria(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/" + Id);
    }
    //Devuelve todos los comentarios con mas puntos segun la categoria
    getTopComentarios(Id) {
        return this.httpclient.get("http://localhost:4567/TopPreguntas/" + Id);
    }
    //Permite escribir un comentario
    postComentario(Remitente, Categoria, Texto, Destinatario) {

        let httpParams = new HttpParams();
          httpParams = httpParams.set("Remitente", Remitente);
          httpParams = httpParams.set("Categoria", Categoria);
          httpParams = httpParams.set("Texto", Texto);
          httpParams = httpParams.set("Destinatario", Destinatario);
  
          return this.httpclient.post("http://localhost:4567/users/post",httpParams) 
    }
    //Devuelve el nombre de usuario conectado actualmente
    getUsuarioConectado() {
        return this.usuario_activo;
    }
    //Define el usuario que esta conectado actualmente
    setUsuarioConectado(Usuario) {

        return this.httpclient.get("http://localhost:4567/Login/" + Usuario)

    }
    //Sale de la sesión actual
    exitUsuarioConectado() {
        this.usuario_activo = undefined;

    }
    setUserActivo(User) {
        this.usuario_activo = User;
        localStorage.setItem("usuario_activo", User);
    }
    setUsuarioQuePregunta(User){
        localStorage.setItem("usuario_que_pregunta", User);
    }
    getUsuarioQuePregunta(){
        return localStorage.getItem("usuario_que_pregunta")    
    }
}