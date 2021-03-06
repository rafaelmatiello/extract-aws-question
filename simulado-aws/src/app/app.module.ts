import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuestionComponent } from './question/question.component';
import {CheckboxModule} from 'primeng/checkbox';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import {RadioButtonModule} from 'primeng/radiobutton';
import {ButtonModule} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {DialogModule} from 'primeng/dialog';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import {HttpClientModule} from '@angular/common/http';
import {ToastModule} from 'primeng/toast';
import {InputTextModule} from 'primeng/inputtext';
import {SidebarModule} from 'primeng/sidebar';
import {TableModule} from 'primeng/table';
import { ReportComponent } from './report/report.component';

@NgModule({
  declarations: [
    AppComponent,
    QuestionComponent,
    ReportComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    CheckboxModule,
    RadioButtonModule,
    ButtonModule,
    CardModule,
    MessagesModule,
    MessageModule,
    DialogModule,
    ConfirmDialogModule,
    HttpClientModule,
    ToastModule,
    InputTextModule,
    SidebarModule,
    TableModule

  ],
  providers: [ConfirmationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
