import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BusinessPropertyDetails } from '../model/BusinessPropertyDetails';
import { BusinessPropertyRequest } from '../model/BusinessPropertyRequest';
import { ConsumerBusinessDetails } from '../model/ConsumerBusinessDetails';
import { ConsumerBusinessRequest } from '../model/ConsumerBusinessRequest';
import { CreatePolicyRequest } from '../model/CreatePolicyRequest';
import { IssuePolicyRequest } from '../model/IssuePolicyRequest';
import { Property } from '../model/Property';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  consumerBusinessCreate: string;
  consumerBusinessUpdate: string;
  businessPropertyCreate: string;
  businessPropertyUpdate: string;
  property: Property;
  quotes: string;
  createPolicyMessage: string;
  issuePolicyMessage: string;

  constructor(private service:Service, private router:Router) { }

  ngOnInit() {
  }

  createConsumerBusinessSubmit(consumerBusinessRequest: ConsumerBusinessRequest){
    this.service.createConsumerBusiness(consumerBusinessRequest).subscribe(data => {
      this.consumerBusinessCreate = localStorage.getItem("createCBmessage");
    })
  }

  updateConsumerBusinessSubmit(consumerBusinessDetails: ConsumerBusinessDetails){
    this.service.updateConsumerBusiness(consumerBusinessDetails).subscribe(data =>{
      this.consumerBusinessUpdate = localStorage.getItem("updateCBmessage")
    })
  }

  createBusinessPropertySubmit(businessPropertyRequest: BusinessPropertyRequest){
    this.service.createBusinessProperty(businessPropertyRequest).subscribe(data =>{
      this.businessPropertyCreate = localStorage.getItem("createBPmessage")
    })
  }

  updateBusinessPropertySubmit(businessPropertyDetails: BusinessPropertyDetails){
    this.service.updateBusinessProperty(businessPropertyDetails).subscribe(data =>{
      this.businessPropertyUpdate = localStorage.getItem("updateBPmessage")
    })
  }

  getQuotesSubmit(businessValue: number, propertyValue: number, propertyType: string){
    this.service.getQuotes(businessValue, propertyValue, propertyType).subscribe(data =>{
      this.quotes = localStorage.getItem("viewQuotes")
    })
  }

  createPolicySubmit(createPolicyRequest: CreatePolicyRequest){
    this.service.createPolicy(createPolicyRequest).subscribe(data =>{
      this.createPolicyMessage = localStorage.getItem("createPolicy")
    })
  }

  issuePolicySubmit(issuePolicyRequest: IssuePolicyRequest){
    this.service.issuePolicy(issuePolicyRequest).subscribe(data =>{
      this.issuePolicyMessage = localStorage.getItem("issuePolicy")
    })
  }

  onLogout(){
    this.router.navigate(["/login"])
  }

  viewConsumerBusinessSubmit(consumerId: number){
    this.router.navigate(["/viewConsumerBusiness/"+consumerId])
  }

  viewBusinessPropertySubmit(consumerId: number, propertyId:number){
    this.router.navigate(["/viewBusinessProperty/"+consumerId+"/"+propertyId])
  }

  viewPolicySubmit(consumerId: number, policyId: number){
    this.router.navigate(["/viewPolicy/"+consumerId+"/"+policyId])
  }
  
}
