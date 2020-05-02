import {Component, OnInit} from '@angular/core';
import {ConfirmationService} from 'primeng';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  public question: any;
  selectOptions: any;
  msgs: any;

  constructor(private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.msgs = [];
    this.question = {
      'chapter': 1,
      'number': 1,
      'asking': 'Which of the following statements regarding S3 storage classes is true?',
      'answerOption': [{
        'option': 'A',
        'description': 'The availability of S3 and S3-IA is the same.'
      }, {
        'option': 'B',
        'description': 'The durability of S3 and S3-IA is the same.'
      }, {
        'option': 'C',
        'description': 'The latency of S3 and Glacier is the same.'
      }, {
        'option': 'D',
        'description': 'The latency of S3 is greater than that of Glacier.'
      }],
      'rightAnswer': {
        'domain': 1,
        'number': 1,
        'options': ['B', 'C'],
        'description': 'This is a common question on AWS exams, and relates to your understanding of the various S3 classes. S3 and S3-IA have the same durability, but the availability of S3 is one 9 greater than S3-IA. S3 has 99.99 availability, while S3-IA has 99.9 availability. Glacier has much greater first-byte latency than S3, so both C and D are false.'
      }
    };
  }

  confirm() {
    this.msgs = [];


    const selection = this.selectOptions.split(',');
    if (this.question.rightAnswer.options.length !== selection.length) {
      this.confirmationService.confirm({
        message: 'Você deve selecionar ' + this.question.rightAnswer.options.length + ' respostas',
        header: 'Aviso',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
          this.checkResult();
        },
        reject: () => {}
      });
    }


  }

  private checkResult() {
    const sucess = 'success';
    const error = 'error';

    const result = {severity: sucess, summary: this.question.rightAnswer.options, detail: this.question.rightAnswer.description};
    this.msgs.push(result);
  }
}
