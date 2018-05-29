import { Component, OnInit } from '@angular/core';
import { Requestes } from '../Services/services';
import { Router, ActivatedRoute, Params } from '@angular/router';
import * as $ from 'jquery';


@Component({
  selector: 'web-content',
  templateUrl: './web-content.component.html',
  styleUrls: ['./web-content.component.css']
})
export class WebContentComponent implements OnInit {

  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute, private router: Router) { }

  private Categorias;
  private Usuario;
  private Preguntas;
  private exists: boolean = false;
  private hayRespuestas;
  private preguntaStatus;
  private usuario_activo;
  private esanon;

  ngOnInit() {

    this.esanon = "";
    let Id;
    this.activatedRoute.params.subscribe((params: Params) => {
      Id = params['id'];
    });

    this.getUser(Id);


  }
  getUser(Id) {

    this.httpc.getUserId(Id).subscribe(res => {
      this.Usuario = res;
      if (this.Usuario.Username == Id) {
        this.exists = true;
        //Get categorias dominadas
        this.httpc.getCategoriaDominante(this.Usuario.id).subscribe(res => {
          this.Categorias = res;

        });
        //Get preguntas hacia él

        this.getPreguntas();


      }
    });

  }
  whotext() {



    if (this.esanon == 1) {

    }
    else {

    }
  }
  getPreguntas() {

    if (this.hayRespuestas == "Sin respuesta") {
      this.httpc.getPreguntasDeUsuarioSR(this.Usuario.id).subscribe(res => {

        this.Preguntas = res;
        this.hayRespuestas = "Con respuesta";

      });
      $(".CRoSR").css("background-color", "rgb(106, 199, 63)")
    }
    else {
      this.httpc.getPreguntasDeUsuarioCR(this.Usuario.id).subscribe(res => {
        this.Preguntas = res;
        this.hayRespuestas = "Sin respuesta";

      });
      $(".CRoSR").css("background-color", "rgb(253, 143, 70)")

    }
  }
  postComent(Remitente, Destinatario) {
    var error;

    var categoria = $(".radio:checked").val();
    var text = $(".inputtext").val();
    this.usuario_activo = this.httpc.getUsuarioConectado();




    if (categoria == undefined) {
      error = "Selecciona categoria";

      alert(error);

    } else if (text == "") {
      error = "Escribe algo!";
    } else if (this.esanon == "") {
      error = "Selecciona como quieres ser identificado";
    }
    else {
      /* this.httpc.postComentario(this.usuario_activo, categoria, text, this.Usuario.Username).subscribe(res => {
         this.preguntaStatus = res;
 
       });*/
    }
    if (error != undefined) {
      alert(error);
    }



  }


}
