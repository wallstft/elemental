import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServerComponent } from './framework/server/server.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LogInterceptor} from "./interceptors/log.interceptor";
import { AuthComponent } from './shared_services/auth/auth.component';
import { UsersComponent } from './junk/users/users.component';
import { HomeComponent } from './framework/home/home.component';
import { ServersComponent } from './framework/servers/servers.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NeoTableComponent } from './framework/neo-table/neo-table.component';
import { SideNavComponent } from './junk/side-nav/side-nav.component';
import { SideTopNavComponent } from './junk/side-top-nav/side-top-nav.component';
import { TopNavBarComponent } from './framework/top-nav-bar/top-nav-bar.component';
import { TabsComponent } from './framework/tabs/tabs.component';
import { MatSideNavbarComponent } from './framework/mat-side-navbar/mat-side-navbar.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {A11yModule} from "@angular/cdk/a11y";
import {ClipboardModule} from "@angular/cdk/clipboard";
import {ScrollingModule} from "@angular/cdk/scrolling";
import {PortalModule} from "@angular/cdk/portal";
import {OverlayModule} from "@angular/cdk/overlay";
import {MatTreeModule} from "@angular/material/tree";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatSliderModule} from "@angular/material/slider";
import {MatSelectModule} from "@angular/material/select";
import {MatNativeDateModule, MatRippleModule} from "@angular/material/core";
import {MatRadioModule} from "@angular/material/radio";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatMenuModule} from "@angular/material/menu";
import {MatListModule} from "@angular/material/list";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatDividerModule} from "@angular/material/divider";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatDialogModule} from "@angular/material/dialog";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatStepperModule} from "@angular/material/stepper";
import {MatChipsModule} from "@angular/material/chips";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatBottomSheetModule} from "@angular/material/bottom-sheet";
import {MatBadgeModule} from "@angular/material/badge";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatButtonModule} from "@angular/material/button";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {CdkTreeModule} from "@angular/cdk/tree";
import {CdkTableModule} from "@angular/cdk/table";
import { SecurityMasterComponent } from './finance/security-master/security-master.component';
import { SyndicatedLoanComponent } from './finance/syndicated-loan/syndicated-loan.component';
import { WealthManagementComponent } from './finance/wealth-management/wealth-management.component';
import { DocumentManagementComponent } from './shared_services/document-management/document-management.component';
import { MarketRiskComponent } from './finance/market-risk/market-risk.component';
import { CreditRiskComponent } from './finance/credit-risk/credit-risk.component';
import { ReconciliationComponent } from './finance/reconciliation/reconciliation.component';
import { LayoutTemplateComponent } from './framework/layout-template/layout-template.component';
import { NgtExampleComponent } from './framework/ngt-example/ngt-example.component';



@NgModule({
  declarations: [
    AppComponent,
    ServerComponent,
    AuthComponent,
    UsersComponent,
    HomeComponent,
    ServersComponent,
    NeoTableComponent,
    SideNavComponent,
    SideTopNavComponent,
    TopNavBarComponent,
    TabsComponent,
    MatSideNavbarComponent,
    SecurityMasterComponent,
    SyndicatedLoanComponent,
    WealthManagementComponent,
    DocumentManagementComponent,
    MarketRiskComponent,
    CreditRiskComponent,
    ReconciliationComponent,
    LayoutTemplateComponent,
    NgtExampleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    MatSidenavModule,

    A11yModule,
    ClipboardModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    OverlayModule,
    PortalModule,
    ScrollingModule,

  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: LogInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
