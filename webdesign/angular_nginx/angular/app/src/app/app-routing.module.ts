import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UsersComponent} from "./junk/users/users.component";
import {HomeComponent} from "./framework/home/home.component";
import {ServersComponent} from "./framework/servers/servers.component";
import {SecurityMasterComponent} from "./finance/security-master/security-master.component";
import {SyndicatedLoanComponent} from "./finance/syndicated-loan/syndicated-loan.component";
import {ReconciliationComponent} from "./finance/reconciliation/reconciliation.component";
import {MarketRiskComponent} from "./finance/market-risk/market-risk.component";
import {CreditRiskComponent} from "./finance/credit-risk/credit-risk.component";
import {DocumentManagementComponent} from "./shared_services/document-management/document-management.component";
import {WealthManagementComponent} from "./finance/wealth-management/wealth-management.component";


const routes: Routes = [
  { path: '',  component: HomeComponent },
  { path: 'users',  component: UsersComponent },
  { path: 'servers',  component: ServersComponent },
  { path: 'smf',  component: SecurityMasterComponent },
  { path: 'syndicated_loan',  component: SyndicatedLoanComponent },
  { path: 'recon',  component: ReconciliationComponent },
  { path: 'market_risk',  component: MarketRiskComponent },
  { path: 'credit_risk',  component: CreditRiskComponent },
  { path: 'docs',  component: DocumentManagementComponent },
  { path: 'wealth',  component: WealthManagementComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
