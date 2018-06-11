import { CategoriaComponent } from './categoria/categoria.component';
import { WebContentComponent } from './web-content/web-content.component';
import { TopsComponent} from './tops/tops.component';
import { RouterModule, Routes } from '@angular/router';
import { UsersCategoriaComponent } from './users-categoria/users-categoria.component';
import { StartPageComponent } from './start-page/start-page.component';
import { PoliticaDePrivacidadComponent } from './politica-de-privacidad/politica-de-privacidad.component';
import { PopularesComponent } from './populares/populares.component';

const appRoutes: Routes = [
    { path: 'Categorias', component: CategoriaComponent },
    { path: 'User/:id',      component: WebContentComponent },
    { path: 'Categorias/Populares',      component: PopularesComponent },
    { path: 'Tops/:id',      component: TopsComponent },
    { path: 'Categorias/:id',      component: UsersCategoriaComponent },
    { path: '',      component: StartPageComponent },
    { path: 'Terms',      component: PoliticaDePrivacidadComponent }
  ];
  
  export const appRouting = RouterModule.forRoot(appRoutes);