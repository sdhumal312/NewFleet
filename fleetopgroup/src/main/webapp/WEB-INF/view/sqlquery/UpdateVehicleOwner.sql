
select * from VehicleOwner;

#update VehicleOwner V inner join User U ON U.email = V.CREATEDBY AND U.company_id = V.COMPANY_ID SET V.CREATEDBYID = U.id;

#update VehicleOwner V inner join User U ON U.email = V.LASTMODIFIEDBY AND U.company_id = V.COMPANY_ID SET V.LASTMODIFIEDBYID = U.id;