/**
 * Created by luizsilva on 7/9/17.
 */
import {RouterModule, Routes} from "@angular/router"
import {ListComponent} from "./list/list.component";
import {AddComponent} from "./add/add.component";
import {DeleteComponent} from "./delete/delete.component";
import {UpdateComponent} from "./update/update.component";
import {RetrieveComponent} from "./retrieve/retrieve.component";
const TODO_ROUTES: Routes=[
  {path:'', redirectTo:'/list', pathMatch:'full'},
  {path:'list',component:ListComponent},
  {path:'add',component:AddComponent},
  {path:'delete',component:DeleteComponent},
  {path:'update',component:UpdateComponent},
  {path:'retrieve',component:RetrieveComponent},
];
export const routing = RouterModule.forRoot(TODO_ROUTES);
