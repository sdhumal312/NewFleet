select * from driverphoto;


#update driverphoto v inner join  driverphoto v2 on v.driver_photoid = v2.driver_photoid set v.uploaddate = (select STR_TO_DATE((v2.driver_uploaddate) ,'%d/%m/%Y %T'));