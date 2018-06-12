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

  usuario_activo;

  ngOnInit() {
    this.usuario_activo = localStorage.getItem("usuario_activo");
    
    //Jquery para el navbar, asi se guarda la barra sola cada vez que se redimensiona
    $( window ).resize(function() {
      if($(window).width() > 981 ){
        var x = document.getElementById("collapsableBar");
        x.className = "centered";
        
      }
      else {
        var x = document.getElementById("collapsableBar");
        x.className = "centered collapse";
        
      }
    });
  }

  loginShowUp() {

    //Hace que aparezca el login
    $('.login').fadeToggle('slow');
    $(this).toggleClass('green');

    $(document).mouseup(function (e) {
      var container = $(".login");

      if (!container.is(e.target) //Si ya no haces click en el contenedor...
        && container.has(e.target).length === 0) // ... o dentro de el
      {
        container.fadeOut(500,function(){$(this).hide();});
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

      this.httpc.login(User, sha1(Pass)).subscribe(res => {
        var token = res;
        if (token != null) {
          this.httpc.setToken(token);
          this.httpc.setUsuarioConectado(User);
          this.usuario_activo = this.httpc.getUsuarioConectado();

        } else {
          alert("¡El usuario o la contraseña son como clarita!");
        }
      });
    }
  }
  exitUsuario() {
    this.httpc.logout(this.httpc.getUsuarioConectado(), this.httpc.getToken()).subscribe(res =>{
      var logoutStatus = res;
      console.log(logoutStatus)
      if(logoutStatus){
        this.httpc.exitUsuarioConectado();
        this.httpc.delToken();
        this.usuario_activo = this.httpc.getUsuarioConectado();
      }else{
        alert("Buen intento....")
      }

    }) ;
  }
  toolBar(){
    var x = document.getElementById("collapsableBar");
    if (x.className === "centered") {
        x.className += " collapse";
    } else {
        x.className = "centered";
    }
  }

}
