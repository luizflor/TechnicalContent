/**
 * Created by luizsilva on 7/9/17.
 */
import {RouterModule, Routes} from '@angular/router';
import {ListComponent} from './list/list.component';
import {AddComponent} from './add/add.component';
import {DeleteComponent} from './delete/delete.component';
import {UpdateComponent} from './update/update.component';
import {RetrieveComponent} from './retrieve/retrieve.component';
import {LogginComponent} from './loggin/loggin.component';
import {AuthGuard} from './auth/AuthGuard';

const TODO_ROUTES: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'list', component: ListComponent, canActivate: [AuthGuard]},
  {path: 'add', component: AddComponent, canActivate: [AuthGuard]},
  {path: 'delete', component: DeleteComponent, canActivate: [AuthGuard]},
  {path: 'update', component: UpdateComponent, canActivate: [AuthGuard]},
  {path: 'retrieve', component: RetrieveComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LogginComponent},
];
export const routing = RouterModule.forRoot(TODO_ROUTES);
