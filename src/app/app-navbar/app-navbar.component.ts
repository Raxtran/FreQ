import { Component, OnInit } from '@angular/core';
import { Requestes } from '../Services/services';
import { Router, ActivatedRoute, Params } from '@angular/router';
import * as $ from 'jquery';
import * as sha1 from 'js-sha1';

@Component({
  selector: 'app-navbar',
  templateUrl: './app-navbar.component.html',
  styleUrls: ['./app-navbar.component.css']
})
export class AppNavbarComponent implements OnInit {

  constructor(private httpc: Requestes, private activatedRoute: ActivatedRoute, private router: Router) { }

  private usuario_activo;

  ngOnInit() {
    this.usuario_activo = localStorage.getItem("usuario_activo");

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

      this.httpc.setUsuarioConectado(User).subscribe(res => {
      var pwd = res;


      if(pwd == sha1(Pass) ){
        this.httpc.setUserActivo(User);
        this.usuario_activo = this.httpc.getUsuarioConectado();
        
      }else{
        alert("¡El usuario o la contraseña son como clarita!");
      }
    
      });


    }
  }
  exitUsuario() {
    this.httpc.exitUsuarioConectado();
    this.usuario_activo = this.httpc.getUsuarioConectado();
    localStorage.removeItem("usuario_activo");
  }
  
}
