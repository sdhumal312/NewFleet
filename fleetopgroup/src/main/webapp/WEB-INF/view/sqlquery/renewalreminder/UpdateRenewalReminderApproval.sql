

#update RenewalReminderApproval r inner join User U ON U.firstName = r.approvalCreated_By and U.company_id = r.companyId SET approvalCreated_ById = U.id;
#update RenewalReminderApproval r inner join User U ON U.firstName = r.approvalPayment_By and U.company_id = r.companyId SET approvalPayment_ById = U.id;

#update RenewalReminderApproval r inner join User U ON U.email = r.createdBy and U.company_id = r.companyId SET createdById = U.id;
#update RenewalReminderApproval r inner join User U ON U.email = r.lastModifiedBy and U.company_id = r.companyId SET lastModifiedById = U.id;