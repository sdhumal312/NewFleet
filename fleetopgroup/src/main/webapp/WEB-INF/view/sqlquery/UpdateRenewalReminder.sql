select * from renewalreminder;

#update renewalreminder v inner join  renewalreminder v2 on v.renewal_id = v2.renewal_id set v.dateofRenewal = (select STR_TO_DATE((v2.renewal_dateofRenewal) ,'%d-%m-%Y %T')) where v2.renewal_dateofRenewal IS NOT NULL;

#update RenewalReminderDocument v inner join User u on u.email = v.createdBy and u.company_id = v.companyId set v.createdById = u.id;

#update RenewalReminderDocument v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.companyId set v.lastModifiedById = u.id;

#update RenewalReminderAppDocument v inner join User u on u.email = v.createdBy and u.company_id = v.companyId set v.createdById = u.id;

#update RenewalReminderAppDocument v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.companyId set v.lastModifiedById = u.id;

#update renewalreminderhistory r inner join renewaltype rt on rt.renewal_Type = r.renewalhis_type AND rt.companyId = r.companyId SET r.renewalhis_typeId = rt.renewal_id;

#update renewalreminderhistory r inner join renewalsubtype rt on rt.renewal_SubType = r.renewalhis_subtype AND rt.companyId = r.companyId SET r.renewalhis_subtypeId = rt.renewal_Subid;

#update renewalreminderhistory v inner join  renewalreminderhistory v2 on v.renewalhis_id = v2.renewalhis_id set v.his_dateofRenewal = (select STR_TO_DATE((v2.renewalhis_dateofRenewal) ,'%d-%m-%Y %T')) where v2.renewalhis_dateofRenewal IS NOT NULL;

#update renewalreminderhistory v inner join User u on u.firstName = v.renewalhis_paidby and u.company_id = v.companyId set v.renewalhis_paidbyId = u.id;

#update renewalreminderhistory v inner join User u on u.email = v.createdBy and u.company_id = v.companyId set v.createdById = u.id;

#update renewalreminderhistory v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.companyId set v.lastModifiedById = u.id;


#update renewalreminderhistory SET renewalhis_paymentTypeId = 1 where renewalhis_paymentType = 'CASH';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 2 where renewalhis_paymentType = 'CREDIT';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 3 where renewalhis_paymentType = 'NEFT';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 4 where renewalhis_paymentType = 'RTGS';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 5 where renewalhis_paymentType = 'IMPS';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 6 where renewalhis_paymentType = 'DD';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 7 where renewalhis_paymentType = 'CHEQUE';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 8 where renewalhis_paymentType = 'BANK DRAFT';
#update renewalreminderhistory SET renewalhis_paymentTypeId = 9 where renewalhis_paymentType = 'COD';



#update renewaltype v inner join User u on u.email = v.createdBy and u.company_id = v.companyId set v.createdById = u.id;
#update renewaltype v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.companyId set v.lastModifiedById = u.id;

#update renewalsubtype v inner join User u on u.email = v.createdBy and u.company_id = v.companyId set v.createdById = u.id;
#update renewalsubtype v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.companyId set v.lastModifiedById = u.id;


