import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class Requestes {


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
    getPreguntasDeUsuarioSR(Id,Type) {
        return this.httpclient.get("http://localhost:4567/Preguntas/SinRespuesta/" + Id+"/"+Type);

    }
    //Devuelve las preguntas del usuario CON respuesta
    getPreguntasDeUsuarioCR(Id,Type) {
        return this.httpclient.get("http://localhost:4567/Preguntas/ConRespuesta/" + Id+"/"+Type);

    }
    //Devuelve los usuarios que dominan X categoria
    getUsersQueDominanLaCategoria(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/" + Id);
    }
    //Devuelve todos los comentarios con mas puntos segun la categoria
    getTopComentarios(Id) {
        return this.httpclient.get("http://localhost:4567/TopPreguntas/" + Id);
    }
    //Permite escribir una pregunta
    postComentario(Remitente, Categoria, Texto, Destinatario, Token) {
        let params = { userPreg : Remitente, categoria: Categoria, texto:Texto, userAnws: Destinatario, token:Token  };
        
        return this.httpclient.post("http://localhost:4567/users/post",params) 
    }
    //Permite responder una pregunta
    postResponderComentario(Usuario, preguntaId, texto, token){
        let params = { userAnws: Usuario, id: preguntaId, texto : texto, token: token };

        return this.httpclient.post("http://localhost:4567/users/post/anwser", params);

    }
    //Define el usuario conectado
    setUsuarioQuePregunta(User){
        localStorage.setItem("usuario_que_pregunta", User);
    }
    //Devuelve el nombre de usuario conectado actualmente
    getUsuarioConectado() {
        return  localStorage.getItem("usuario_activo");
    }
    //Devuelve el token del usuario que esta tratando de conectarse actualmente si esta todo correcto
    login(Usuario,pass) {
        let params = {username: Usuario, contraseña:  pass}
        return this.httpclient.post("http://localhost:4567/Login", params)
    }
    //Borra el token del usuario
    logout(Usuario, token){
        let params = {username : Usuario, token : token}
        return this.httpclient.post("http://localhost:4567/Logout", params)
    }
    //Sale de la sesión actual
    exitUsuarioConectado() {
        localStorage.removeItem("usuario_activo")
        
    }
    setUsuarioConectado(User) {
        localStorage.setItem("usuario_activo", User);
    }

    setToken(token){
        localStorage.setItem("token", token);
    }
    getToken(){
        return localStorage.getItem("token");
    }
    delToken(){
        localStorage.removeItem("token");
    }
   
    getUsuarioQuePregunta(){
        return localStorage.getItem("usuario_que_pregunta")    
    }
    updateVotacionP(User,Tipo,Pregunta,Token){
        
        let params = { usuario : User, pregunta: Pregunta, tipo: Tipo, token:Token  };

        return this.httpclient.put("http://localhost:4567/updateVotacionP", params)
    }
    updateVotacionR(User,Tipo,Pregunta,Token){
        let params = { usuario : User, pregunta: Pregunta, tipo: Tipo, token:Token  };

        return this.httpclient.put("http://localhost:4567/updateVotacionR", params)
    }
    getUsersPopu(){
        return this.httpclient.get("http://localhost:4567/Populares")
    }
}