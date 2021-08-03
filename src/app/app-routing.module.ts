import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ConsumerBusinessComponent } from './consumer-business/consumer-business.component';
import { PropertyComponent } from './property/property.component';
import { PolicyComponent } from './policy/policy.component';


const routes: Routes = [
  {
    path:"",
    redirectTo:"login",
    pathMatch:"full"
  },
  
  {
    path:"login",
    component:LoginComponent
  },

  {
    path:"home",
    component:HomeComponent
  },

  {
    path:"viewConsumerBusiness/:consumerId",
    component:ConsumerBusinessComponent
  },

  {
    path:"viewBusinessProperty/:consumerId/:propertyId",
    component:PropertyComponent
  },

  {
    path:"viewPolicy/:consumerId/:policyId",
    component:PolicyComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
