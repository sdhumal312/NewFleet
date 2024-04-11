select * from driverdocument;

#update driverdocument v inner join  driverdocument v2 on v.driver_documentid = v2.driver_documentid set v.driver_docFromDate = (select STR_TO_DATE((v2.driver_docFrom) ,'%d-%m-%Y %T'));

#update driverdocument v inner join  driverdocument v2 on v.driver_documentid = v2.driver_documentid set v.driver_docToDate = (select STR_TO_DATE((v2.driver_docTo) ,'%d-%m-%Y %T'));

#update driverdocument v inner join  driverdocument v2 on v.driver_documentid = v2.driver_documentid set v.uploaddate = (select STR_TO_DATE((v2.driver_uploaddate) ,'%d-%m-%Y %T'));

#update driverdocument v inner join  driverdocument v2 on v.driver_documentid = v2.driver_documentid set v.revdate = (select STR_TO_DATE((v2.driver_revdate) ,'%d-%m-%Y %T'));