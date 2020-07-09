import {Component, OnInit} from '@angular/core';
import {ReportService} from './report.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  public questions = [];

  constructor(private reportService: ReportService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.reportService.getReport(id).subscribe((result) => {
        this.questions = result.sort((obj1, obj2) => {
          if (obj1.question.number > obj2.question.number) {
            return 1;
          }

          if (obj1.question.number < obj2.question.number) {
            return -1;
          }

          return 0;
        });


      });
    });

  }

}
