import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class Requestes {

    private usuario_activo;


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

      /*  let httpParams = new httpParams().append("Remitente", Remitente).append("Categoria", Categoria).append("Texto", Texto).append("Destinatario", Destinatario);

        return this.httpclient.post("http://localhost:4567/users/post",httpParams) */
    }
    //Devuelve el nombre de usuario conectado actualmente
    getUsuarioConectado() {
        return this.usuario_activo;
    }
    //Define el usuario que esta conectado actualmente
    setUsuarioConectado(Usuario, Contraseña) {

        //Comparar usuario y contraseña
        var userIsOk ;
        //En el caso de que todo este bien

        if(userIsOk){
        this.usuario_activo = Usuario;
        }

    }
    //Sale de la sessión actual
    exitUsuarioConectado(){
        this.usuario_activo = undefined;
        
    }

}