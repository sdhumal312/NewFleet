-- VehicleMandatory table
ALTER TABLE VehicleMandatory DROP COLUMN VEHICLE_REGISTRATION;
ALTER TABLE VehicleMandatory DROP COLUMN MANDATORY_NAME;

UPDATE `VehicleMandatory` INNER JOIN renewalsubtype AS R ON R.renewal_SubType = `MANDATORY_NAME` SET `MANDATORY_RENEWAL_SUBID`= R.renewal_Subid WHERE R.companyId = `COMPANY_ID`