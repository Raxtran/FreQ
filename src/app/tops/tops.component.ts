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
  private activaComentario: Boolean = false;

  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    let Id;
    this.activatedRoute.params.subscribe((params: Params) => {
      Id = params['id'];
    });

    if (!this.activaComentario) {
      this.getTop(Id);
    }
    else {
      this.getTopComentarios();
    }



  }
  getTop(Id) {
    this.activaComentario = false;

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

  getTopComentarios() {
    this.activaComentario = true;
    $(".com-user").css("background-color","#96EFFF")

    console.log("asdasd");


  }
}
