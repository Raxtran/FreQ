import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Requestes } from '../Services/services';

@Component({
  selector: 'app-users-categoria',
  templateUrl: './users-categoria.component.html',
  styleUrls: ['./users-categoria.component.css']
})
export class UsersCategoriaComponent implements OnInit {

  Usuarios ;


  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute) { }

  
  ngOnInit() {

    let Id;
    this.activatedRoute.params.subscribe((params: Params) => {
      Id = params['id'];
    });
    this.getUser(Id);
  }
  getUser(Id) {
    this.httpc.getUsersQueDominanLaCategoria(Id).subscribe(res => {
      this.Usuarios = res;
      console.log(this.Usuarios);

    });
  }
}
