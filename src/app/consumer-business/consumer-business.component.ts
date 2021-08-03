import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ConsumerBusinessDetails } from '../model/ConsumerBusinessDetails';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-consumer-business',
  templateUrl: './consumer-business.component.html',
  styleUrls: ['./consumer-business.component.css']
})
export class ConsumerBusinessComponent implements OnInit {

  message: any;
  consumerBusinessDetails: ConsumerBusinessDetails;

  constructor(private service:Service, private route: ActivatedRoute, private router:Router) { }

  ngOnInit() {
    let consumerId = this.route.snapshot.params.consumerId;
    this.service.viewConsumerBusiness(consumerId).subscribe(data =>{
      this.consumerBusinessDetails = data
      console.log(this.consumerBusinessDetails)},
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
