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

  Categorias;
  private Usuario;
  Preguntas;
  exists: boolean = false;
  private hayRespuestas;
  private preguntaStatus;
  private usuario_activo;
  private esanon;
  private Id;

  ngOnInit() {

    this.usuario_activo = localStorage.getItem("usuario_activo");
    this.esanon = "";
    this.activatedRoute.params.subscribe((params: Params) => {
      this.Id = params['id'];
    });

    this.getUser(this.Id);
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
        this.getPreguntas(0);
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
    this.getPreguntas(0);
  }
  //recibe preguntas...
  getPreguntas(Type) {

    if (this.hayRespuestas == "Con respuesta") {
      this.httpc.getPreguntasDeUsuarioSR(this.Usuario.id,Type).subscribe(res => {
        this.Preguntas = res;
      });

    }
    else if (this.hayRespuestas == "Sin respuesta") {
      this.httpc.getPreguntasDeUsuarioCR(this.Usuario.id,Type).subscribe(res => {
        this.Preguntas = res;
      });
    }
    else {
      this.httpc.getPreguntasDeUsuarioCR(this.Usuario.id,Type).subscribe(res => {
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
        if(this.preguntaStatus){
          this.getPreguntas(0);
          $(".inputtext").val("");
        }

      });
    }
    if (error != undefined) {
      alert(error);
    }

  }
  updateVotacionPregunta(Tipo, preguntaId) {

    this.httpc.updateVotacionP(this.httpc.getUsuarioConectado(), Tipo, preguntaId,this.httpc.getToken()).subscribe(res => {
    this.getPreguntas(0);
    });

  }
  updateVotacionRespuesta(Tipo, preguntaId) {

    this.httpc.updateVotacionR(this.httpc.getUsuarioConectado(), Tipo, preguntaId,this.httpc.getToken()).subscribe(res => {
      this.getPreguntas(0);
    });

  }
  //Para poder responder a una pregunta
  responderShowUp() {

    if(this.Id == this.usuario_activo){
      //Hace que aparezca la respuesta
      $('.escribeRespuestaContainer').fadeToggle('slow');
      $(this).toggleClass('green');
      $(document).mouseup(function (e) {
        var container = $(".escribeRespuestaContainer");

        if (!container.is(e.target) //Si ya no haces click en el contenedor...
          && container.has(e.target).length === 0) // ... o dentro de el
        {
          container.fadeOut(500, function () { $(this).hide(); });
          $('.Pregunta').removeClass('green');
        }
      });
    
  }
  }
  postRespuesta(preguntaId, I) {
    var respuestaText = $('#' + I).val();

    if (respuestaText != "") {


      this.httpc.postResponderComentario(this.httpc.getUsuarioConectado(), preguntaId, respuestaText, this.httpc.getToken()).subscribe(res => {
        var params = res;
        if (params) {

          $("#pregunta_" + I).fadeOut(500, function () { $(this).remove(); });
          $("#respuesta_" + I).fadeOut(500, function () { $(this).remove(); });


        }
      });

    } else {

    }


  }
}
