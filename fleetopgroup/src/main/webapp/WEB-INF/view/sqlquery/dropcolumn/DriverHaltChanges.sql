ALTER TABLE `DriverHalt` CHANGE `DRIVER_NAME` `DRIVER_NAME` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL;
ALTER TABLE `drivers` RENAME TO Driver;

ALTER TABLE `Issues` CHANGE `ISSUES_STATUS` `ISSUES_STATUS` VARCHAR(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL;



