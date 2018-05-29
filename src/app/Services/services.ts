import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class Requestes {

    private usuario_activo;


    constructor(
        private httpclient: HttpClient
    ) { }

    getUserId(Id) {
        return this.httpclient.get("http://localhost:4567/User/" + Id);

    }
    getAllCategorias() {
        return this.httpclient.get("http://localhost:4567/Categorias");
    }
    getTop(id) {
        return this.httpclient.get("http://localhost:4567/Tops/" + id);
    }
    getCategoriaDominante(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/Usuario/" + Id);

    }
    getPreguntasDeUsuarioSR(Id) {
        return this.httpclient.get("http://localhost:4567/Preguntas/SinRespuesta/" + Id);

    } getPreguntasDeUsuarioCR(Id) {
        return this.httpclient.get("http://localhost:4567/Preguntas/ConRespuesta/" + Id);

    }

    getUsersQueDominanLaCategoria(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/" + Id);
    }
    getTopComentarios(Id) {
        return this.httpclient.get("http://localhost:4567/TopPreguntas/" + Id);
    }



    postComentario(Remitente, Categoria, Texto, Destinatario) {

      /*  let httpParams = new httpParams().append("Remitente", Remitente).append("Categoria", Categoria).append("Texto", Texto).append("Destinatario", Destinatario);

        return this.httpclient.post("http://localhost:4567/users/post",httpParams) */
    }

    getUsuarioConectado() {
        return this.usuario_activo;
    }
    setUsuarioConectado(Usuario, Contraseña) {

        //Comparar usuario y contraseña




        //En el caso de que todo este bien
       
        this.usuario_activo = Usuario;


    }
    exitUsuarioConectado(){
        this.usuario_activo = undefined;
        
    }

}