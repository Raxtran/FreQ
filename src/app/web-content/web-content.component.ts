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

    this.usuario_activo = localStorage.getItem("usuario_activo");
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
      this.httpc.setUsuarioQuePregunta("Anon");
    }
    else {
      this.httpc.setUsuarioQuePregunta(localStorage.getItem("usuario_activo"));
    }
  }
  //Cambia los colores y el texto al hacer clic en con respuesta o en sin respeusta
  selectPreguntas() {
    if (this.hayRespuestas == "Sin respuesta") {
      $(".CRoSR").css("background-color", "rgb(106, 199, 63)");
      this.hayRespuestas = "Con respuesta";

    }
    else {
      $(".CRoSR").css("background-color", "rgb(253, 143, 70)");
      this.hayRespuestas = "Sin respuesta";

    }
    this.getPreguntas();
  }
  //recibe preguntas...
  getPreguntas() {

    if (this.hayRespuestas == "Con respuesta") {
      this.httpc.getPreguntasDeUsuarioSR(this.Usuario.id).subscribe(res => {
        this.Preguntas = res;
      });

    }
    else if (this.hayRespuestas == "Sin respuesta") {
      this.httpc.getPreguntasDeUsuarioCR(this.Usuario.id).subscribe(res => {
        this.Preguntas = res;
      });
    }
    else {
      this.httpc.getPreguntasDeUsuarioCR(this.Usuario.id).subscribe(res => {
        this.Preguntas = res;
        $(".CRoSR").css("background-color", "rgb(253, 143, 70)");
        this.hayRespuestas = "Sin respuesta";
      });
    }
  }
  postComent(Remitente, Destinatario) {

    var error;
    var categoria = $(".radio:checked").val();
    var text = $(".inputtext").val();
    this.usuario_activo = localStorage.getItem("usuario_activo");

    if (categoria == undefined) {
      error = "Selecciona categoria";
    } else if (text == "") {
      error = "Escribe algo!";
    } else if (this.esanon == "") {
      error = "Selecciona como quieres ser identificado";
    }
    else {

      this.httpc.postComentario(this.httpc.getUsuarioQuePregunta(), categoria, text, this.Usuario.Username, this.httpc.getToken()).subscribe(res => {
        this.preguntaStatus = res;
        this.getPreguntas();

      });
    }
    if (error != undefined) {
      alert(error);
    }

  }
  updateVotacionPregunta(Tipo, preguntaId){

    this.httpc.updateVotacionP(this.httpc.getUsuarioConectado(),Tipo,preguntaId ).subscribe(res => {
      this.getPreguntas();
    });

  }
  updateVotacionRespuesta(Tipo, preguntaId){

    this.httpc.updateVotacionR(this.httpc.getUsuarioConectado(),Tipo,preguntaId ).subscribe(res => {
      this.getPreguntas();
    });

  }
}
