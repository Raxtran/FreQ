import { Component, OnInit } from '@angular/core';
import { Requestes } from '../Services/services';

@Component({
  selector: 'app-populares',
  templateUrl: './populares.component.html',
  styleUrls: ['./populares.component.css']
})
export class PopularesComponent implements OnInit {

  constructor(private httpc: Requestes) { }
  Usuarios;
  Categorias = [];

  ngOnInit() {
    this.httpc.getUsersPopu().subscribe(res => {
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
}
