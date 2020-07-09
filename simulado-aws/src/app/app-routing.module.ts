import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {QuestionComponent} from './question/question.component';
import {ReportComponent} from './report/report.component';


const routes: Routes = [
  { path: 'question', component: QuestionComponent },
  { path: 'report/:id', component: ReportComponent },
  { path: '',   redirectTo: '/question', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
