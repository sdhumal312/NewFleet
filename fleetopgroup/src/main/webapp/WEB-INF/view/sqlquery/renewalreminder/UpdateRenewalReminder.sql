select * from renewalreminder;

#update renewalreminder r inner join User U ON U.firstName = r.renewal_paidby and U.company_id = r.companyId SET renewal_paidbyId = U.id;
#update renewalreminder r inner join User U ON U.firstName = r.renewal_approvedby and U.company_id = r.companyId SET renewal_approvedbyId = U.id;

#update renewalreminder r inner join User U ON U.email = r.createdBy and U.company_id = r.companyId SET createdById = U.id;
#update renewalreminder r inner join User U ON U.email = r.lastModifiedBy and U.company_id = r.companyId SET lastModifiedById = U.id;