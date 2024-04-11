#ALTER TABLE blog ADD INDEX(blogID, auth_id);

alter table Fuel ADD INDEX(vid);

alter table vehicle ADD INDEX(vehicleGroupId);
alter table vehicle ADD INDEX(routeID);
alter table vehicle ADD INDEX(vehicleTypeId);
CREATE INDEX vg_per_id ON vehiclegrouppermision (vg_per_id) USING BTREE;
CREATE INDEX user_id ON vehiclegrouppermision (user_id) USING BTREE;

alter table Driver ADD INDEX(vehicleGroupId);
alter table Driver ADD INDEX(tripSheetID);
alter table Driver ADD INDEX(driJobId);


alter table renewalreminder ADD INDEX(vid);
alter table renewalreminder ADD INDEX(renewalTypeId);
alter table renewalreminder ADD INDEX(renewal_Subid);
alter table renewalreminder ADD INDEX(renewal_approvedID);


alter table servicereminder ADD INDEX(vid);
alter table servicereminder ADD INDEX(serviceTypeId);
alter table servicereminder ADD INDEX(serviceSubTypeId);


alter table ServiceEntries ADD INDEX(vid);
alter table ServiceEntries ADD INDEX(vendor_id);



alter table inventory ADD INDEX(partid);
alter table inventory ADD INDEX(locationId);
alter table inventory ADD INDEX(vendor_id);


alter table inventorylocation ADD INDEX(partid);
alter table inventorylocation ADD INDEX(locationId);


alter table inventoryall ADD INDEX(partid);
alter table inventoryall ADD INDEX(inventory_all_id);


alter table tripsheet ADD INDEX(vid);
alter table tripsheet ADD INDEX(routeID);
alter table tripsheet ADD INDEX(tripFristDriverID);
alter table tripsheet ADD INDEX(dispatchedLocationId);













