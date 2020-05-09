import {Component, OnInit} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng';
import {QuestionService} from './question.service';
import {SessionService} from '../services/session.service';
import {switchMap} from 'rxjs/operators';
import {AnswerService} from '../services/answer.service';
import {StatisticsService} from '../services/statistics.service';

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
  gotoIndex: any;
  showSidebar = false;
  public statistics: any;
  //fallback
  private totalQuestion = 1005;


  constructor(private confirmationService: ConfirmationService,
              private questionService: QuestionService,
              private sessionService: SessionService,
              private answerService: AnswerService,
              private messageService: MessageService,
              private statisticsService: StatisticsService) {
  }

  ngOnInit() {


    this.loadQuestion();

    this.questionService.getCount().subscribe((quantidadeTotal: any) => {
        this.totalQuestion = quantidadeTotal;
    });

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
      this.selectOptionsMulti = null;
      this.selectOptionsSingle = null;
      this.question = res;
      this.gotoIndex = this.question.index;
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

    }, (error) => {
      this.messageService.add({severity: 'error', summary: 'Erro ao salvar', detail: error, key: 'ans'});
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

  goTo() {
    this.currentSession.lastIndex = this.gotoIndex;
    this.sessionService.saveSession(this.currentSession).subscribe((session: any) => {
        this.index = session.lastIndex;
        this.loadQuestion();
      }
    );
  }

  showStatistics() {

    this.statisticsService.getStatistics().subscribe((statistics) => {

      this.statistics = statistics;
      this.statistics.forEach(stat => {
        stat.percent = (stat.correct * 100) / stat.answers;
      });
      this.showSidebar = true;
    });


  }

  random() {
    this.gotoIndex = this.randomInt(1, this.totalQuestion);
    this.goTo();
  }

  randomWrong() {
    this.questionService.getIndexWrong().subscribe((quantidadeTotal: any) => {
      this.gotoIndex = this.randomInt(1, this.totalQuestion);
      this.goTo();
    });

  }

  randomInt(min, max) {
    return min + Math.floor((max - min) * Math.random());
  }

}
