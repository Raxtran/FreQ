import { Component, OnInit } from '@angular/core';
import { Requestes } from '../Services/services';
import { Router, ActivatedRoute, Params } from '@angular/router';
import * as $ from 'jquery';

@Component({
  selector: 'app-navbar',
  templateUrl: './app-navbar.component.html',
  styleUrls: ['./app-navbar.component.css']
})
export class AppNavbarComponent implements OnInit {

  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute, private router: Router) { }

  private usuario_activo;

  ngOnInit() {
    this.usuario_activo = this.httpc.getUsuarioConectado();
  }

  loginShowUp() {


    $('.login').fadeToggle('slow');
    $(this).toggleClass('green');

    $(document).mouseup(function (e) {
      var container = $(".login");

      if (!container.is(e.target) //Si ya no haces click en el contenedor...
        && container.has(e.target).length === 0) // ... o dentro de el
      {
        container.hide();
        $('#loginform').removeClass('green');
      }
    });
  }
  goLogin() {
    var User = $("#username").val();
    var Pass = $("#password").val();
    if (User == '') {
      alert("¡Escribe tu usuario!")
    } else if (Pass == '') {
      alert("¡Escribe tu contraseña!");
    } else {
      this.httpc.setUsuarioConectado(User, Pass);
      this.usuario_activo = this.httpc.getUsuarioConectado();
    }
  }
  exitUsuario() {
    this.httpc.exitUsuarioConectado();
    this.usuario_activo = this.httpc.getUsuarioConectado();
    console.log("exit ok")
  }
  
}
