
select * from VehicleComment;

#update VehicleComment v inner join User U ON U.email = v.CREATED_EMAIL SET v.CREATEDBYID = U.id;