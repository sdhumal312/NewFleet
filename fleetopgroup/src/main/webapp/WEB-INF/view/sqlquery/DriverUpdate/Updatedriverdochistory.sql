 
#update driverdochistory v inner join  driverdochistory v2 on v.driver_doHisid = v2.driver_doHisid set v.driver_docHisFromDate = (select STR_TO_DATE((v2.driver_docHisFrom) ,'%d-%m-%Y %T'));
#update driverdochistory v inner join  driverdochistory v2 on v.driver_doHisid = v2.driver_doHisid set v.driver_docHisToDate = (select STR_TO_DATE((v2.driver_docHisTo) ,'%d-%m-%Y %T'));
#update driverdochistory v inner join  driverdochistory v2 on v.driver_doHisid = v2.driver_doHisid set v.uploaddate = (select STR_TO_DATE((v2.driver_uploaddate) ,'%d-%m-%Y %T'));
