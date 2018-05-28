import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CategoriaComponent } from './categoria/categoria.component';
import { AppNavbarComponent } from './app-navbar/app-navbar.component';
import { WebContentComponent } from './web-content/web-content.component';
import { HttpClientModule } from '@angular/common/http';
import { Requestes } from './Services/services';
import { RouterModule, Routes } from '@angular/router';
import { appRouting } from './app.routes';
import { TopsComponent } from './tops/tops.component';
import { UsersCategoriaComponent } from './users-categoria/users-categoria.component';

@NgModule({
  declarations: [
    AppComponent,
    CategoriaComponent,
    AppNavbarComponent,
    WebContentComponent,
    TopsComponent,
    UsersCategoriaComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    appRouting
  ],
  providers: [Requestes],
  bootstrap: [AppComponent]
})
export class AppModule { }
