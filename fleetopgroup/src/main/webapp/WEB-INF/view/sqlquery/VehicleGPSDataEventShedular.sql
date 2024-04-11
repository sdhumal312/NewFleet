DELIMITER $$

CREATE 
	EVENT `VehicleGPSDataEventShedular` 
	ON SCHEDULE EVERY 1 day STARTS '2019-01-04 02:00:00' ON  COMPLETION PRESERVE ENABLE 
	DO BEGIN
	 
     SET SQL_SAFE_UPDATES = 0;
     
     delete from VehicleGPSData where dateTime < (SELECT DATE_SUB(current_timestamp(), INTERVAL 15 DAY));
     
	END  $$

DELIMITER ;