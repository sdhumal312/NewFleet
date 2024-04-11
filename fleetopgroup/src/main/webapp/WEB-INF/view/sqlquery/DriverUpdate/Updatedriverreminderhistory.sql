select * from driverreminderhistory;

#update driverreminderhistory v inner join  driverreminderhistory v2 on v.driver_rhid = v2.driver_rhid set v.driver_rhfromDate = (select STR_TO_DATE((v2.driver_rhfrom) ,'%d-%m-%Y %T'));

#update driverreminderhistory v inner join  driverreminderhistory v2 on v.driver_rhid = v2.driver_rhid set v.driver_rhtoDate = (select STR_TO_DATE((v2.driver_rhto) ,'%d-%m-%Y %T'));