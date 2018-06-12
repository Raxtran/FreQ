import { Component, OnInit } from '@angular/core';
import { Requestes } from '../Services/services';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { forEach } from '@angular/router/src/utils/collection';
import * as $ from 'jquery';

@Component({
  selector: 'tops',
  templateUrl: './tops.component.html',
  styleUrls: ['./tops.component.css']
})
export class TopsComponent implements OnInit {

  private Usuarios;
  private Categorias = [];
  activaTop: Boolean = true;
  Comentarios;
  usuario_activo;
  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {  
    let Id;
    this.activatedRoute.params.subscribe((params: Params) => {
      Id = params['id'];
      this.initializerTops(Id);
    });

  }

  initializerTops(Id) {
  
    if (this.activaTop) {
      this.getTop(Id);
    } else {
      this.getTopComentarios(Id);
    }
  }
  changeColor(id) {

    //If top
    if (id == 1) {
      this.activaTop = true;
      $("#Comentarios").css("background-color", "#008db1");
      $("#Usuarios").css("background-color", "#EB9100");
    } else {

      this.activaTop = false;
      $("#Comentarios").css("background-color", "#EB9100");
      $("#Usuarios").css("background-color", "#008db1");
    }
    this.ngOnInit();
  }
  getTop(Id) {
    $("#Usuarios").css("background-color", "#EB9100");

    this.httpc.getTop(Id).subscribe(res => {
      this.Usuarios = res;
      this.getDomCategorias(this.Usuarios);

    });
  }
  getDomCategorias(Usuarios) {

    this.Usuarios.forEach(Usuario => {
      this.httpc.getCategoriaDominante(Usuario.id).subscribe(res => {
        this.Categorias.push(res);

      });

    });
  }
  getTopComentarios(Id) {
    this.httpc.getTopComentarios(Id).subscribe(res => {
      this.Comentarios = res;
    });
  }

  

}
