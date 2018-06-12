import {
  Component,
  OnInit
} from '@angular/core';
import {
  BrowserModule
} from '@angular/platform-browser';
import { Requestes } from '../Services/services';
@Component({
  selector: 'categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.css']
})
export class CategoriaComponent implements OnInit {

  Categorias;

  constructor(private httpc: Requestes) {

  }
  //Al inciar categorias tiene el valor del archivo json 
  ngOnInit() {
    this.getAllCategorias();

  }
  getAllCategorias() {
    this.httpc.getAllCategorias().subscribe(res => {this.Categorias = res; console.log(this.Categorias)});
  }
}