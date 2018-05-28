import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class Requestes {


    constructor(
        private httpclient: HttpClient
    ) { }
    
    getUserId(Id){
        return this.httpclient.get("http://localhost:4567/User/"+Id);
        
    }
    getAllCategorias(){
        return this.httpclient.get("http://localhost:4567/Categorias");
    }
    getTop(id){
        return this.httpclient.get("http://localhost:4567/Tops/"+id);
    }
    getCategoriaDominante(Id) {
        return this.httpclient.get("http://localhost:4567/Categorias/Usuario/"+Id);

    }
    getPreguntasDeUsuarioSR(Id){
        return this.httpclient.get("http://localhost:4567/Preguntas/SinRespuesta/"+Id);

    }  getPreguntasDeUsuarioCR(Id){
        return this.httpclient.get("http://localhost:4567/Preguntas/ConRespuesta/"+Id);

    }

    getUsersQueDominanLaCategoria(Id){
        return this.httpclient.get("http://localhost:4567/Categorias/"+Id);
    }
}