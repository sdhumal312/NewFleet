select * from vehicledocument;

#update vehicledocument  v inner join vehicledoctype d on d.vDocType = v.name and d.company_Id = v.companyId SET v.docTypeId = d.dtid;

#update vehicledocument v inner join  vehicledocument v2 on v.id = v2.id set v.created = (select STR_TO_DATE((v2.uploaddate) ,'%d-%m-%Y %T'));