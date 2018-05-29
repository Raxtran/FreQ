import { CategoriaComponent } from './categoria/categoria.component';
import { WebContentComponent } from './web-content/web-content.component';
import { TopsComponent} from './tops/tops.component';
import { RouterModule, Routes } from '@angular/router';
import { UsersCategoriaComponent } from './users-categoria/users-categoria.component';
import { LoginComponent } from './login/login.component';

const appRoutes: Routes = [
    { path: 'Categorias', component: CategoriaComponent },
    { path: 'User/:id',      component: WebContentComponent },
    { path: 'Tops/:id',      component: TopsComponent },
    { path: 'Categorias/:id',      component: UsersCategoriaComponent },
    { path: 'Login',      component: LoginComponent },

  ];
  
  export const appRouting = RouterModule.forRoot(appRoutes);