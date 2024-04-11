
select * from vehiclephoto;

#update vehiclephoto  v inner join vehiclephotype d on d.vPhoType = v.name and d.company_Id = v.companyId SET v.photoTypeId = d.ptid;


#update vehiclephoto v inner join  vehiclephoto v2 on v.id = v2.id set v.uploaddateOn = (select STR_TO_DATE((v2.uploaddate) ,'%d-%m-%Y %T'));
