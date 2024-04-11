
select * from vehiclepurchase;

#update vehiclepurchase v inner join vehiclepurchaseinfotype vp on vp.vPurchaseInfoType = v.name AND vp.company_Id = v.companyId SET v.vPurchaseTypeId = vp.ptid;

update vehiclepurchase v inner join  vehiclepurchase v2 on v.id = v2.id set v.uploaddateOn = (select STR_TO_DATE((v2.uploaddate) ,'%d-%m-%Y %T'));