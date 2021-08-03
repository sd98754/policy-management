import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PolicyDetailsResponse } from '../model/PolicyDetailsResponse';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-policy',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css']
})
export class PolicyComponent implements OnInit {

  message: any;
  policyDetailsResponse: PolicyDetailsResponse;

  constructor(private service:Service, private route: ActivatedRoute, private router:Router) { }

  ngOnInit() {
    let consumerId = this.route.snapshot.params.consumerId;
    let policyId = this.route.snapshot.params.policyId;
    console.log(policyId)
    this.service.viewPolicy(consumerId, policyId).subscribe(data =>{
      this.policyDetailsResponse = data
      console.log(this.policyDetailsResponse)},
      error => {
        this.message = error.error.message
    })
  }

  onLogout(){
    this.router.navigate(["/login"])
  }

  onBack(){
    this.router.navigate(["/home"])
  }

}
