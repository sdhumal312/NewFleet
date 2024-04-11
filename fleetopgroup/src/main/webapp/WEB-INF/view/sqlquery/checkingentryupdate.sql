select * from vehiclecheckingdetails order by checkingId desc;

#update VehicleCheckingDetails vd inner join Driver d on d.driver_id = vd.checkingInspectorId SET vd.vehicleGroupId = d.vehicleGroupId;