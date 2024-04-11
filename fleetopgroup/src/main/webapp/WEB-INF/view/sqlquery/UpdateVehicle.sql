
#update vehicle v inner join User u on u.email = v.createdBy and u.company_id = v.company_Id set v.createdById = u.id;

#update vehicle v inner join User u on u.email = v.lastModifiedBy and u.company_id = v.company_Id set v.lastModifiedById = u.id;

alter table VehicleMandatory modify column MANDATORY_NAME varchar(150);
alter table VehicleMandatory modify column VEHICLE_REGISTRATION varchar(25);

