import {Component, OnInit} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng';
import {QuestionService} from './question.service';
import {SessionService} from '../services/session.service';
import {switchMap} from 'rxjs/operators';
import {AnswerService} from '../services/answer.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss'],
  providers: [MessageService]
})
export class QuestionComponent implements OnInit {

  public question: any;
  selectOptionsMulti: any;
  selectOptionsSingle: any;
  msgs: any;
  index = 1;
  currentSession: any;


  constructor(private confirmationService: ConfirmationService,
              private questionService: QuestionService,
              private sessionService: SessionService,
              private answerService: AnswerService,
              private messageService: MessageService) {
  }

  ngOnInit() {


    this.loadQuestion();

    this.msgs = [];

  }

  private loadQuestion() {
    this.sessionService.getSession().pipe(
      switchMap((session) => {
        this.currentSession = session;
        return this.questionService.getQuestion(session.lastIndex);
      })
    ).subscribe((res) => {
      this.msgs = [];
      this.question = res;
    });
  }

  confirm() {
    this.msgs = [];
    const size = this.question.rightAnswer.options.length;

    let correct;
    let selectionOption = null;
    if (size === 1) {
      correct = this.selectOptionsSingle === this.question.rightAnswer.options[0];
      this.checkResult(correct);

      selectionOption = this.selectOptionsSingle;
    } else {

      const right = this.question.rightAnswer.options;
      const select = this.selectOptionsMulti;
      correct = right.length === select.length && right.sort().every((value, index) => {
        return value === select.sort()[index];
      });

      this.checkResult(correct);
      selectionOption = this.selectOptionsMulti.toString();
    }


    const answer = {
      index: this.currentSession.lastIndex,
      chapter: this.question.chapter,
      number: this.question.number,
      selectOption: selectionOption,
      'correct': correct
    };


    this.answerService.saveAnswer(answer).subscribe(() => {
      //this.messageService.clear();
      this.messageService.add({severity:'success', summary: 'Salvo', detail:'Resposta salva!', key:'ans'});
    });
  }

  private checkResult(correct: boolean) {
    const severityOption = correct ? 'success' : 'error';
    const result = {
      severity: severityOption,
      summary: this.question.rightAnswer.options,
      detail: this.question.rightAnswer.description
    };
    this.msgs.push(result);
  }

  next() {
    this.currentSession.lastIndex = this.currentSession.lastIndex + 1;
    this.sessionService.saveSession(this.currentSession).subscribe((session) => {
        this.index = session.lastIndex;
        this.loadQuestion();
      }
    );
  }

  previous() {
    this.currentSession.lastIndex = this.currentSession.lastIndex - 1;
    this.sessionService.saveSession(this.currentSession).subscribe((session: any) => {
        this.index = session.lastIndex;
        this.loadQuestion();
      }
    );
  }
}
