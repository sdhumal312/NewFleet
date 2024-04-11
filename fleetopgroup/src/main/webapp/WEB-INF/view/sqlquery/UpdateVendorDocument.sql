

#update VendorDocument s inner join User u on u.email = s.CREATEDBY AND u.company_id = s.COMPANY_ID SET s.CREATEDBYID = u.id;

#update VendorDocument s inner join User u on u.email = s.LASTMODIFIEDBY AND u.company_id = s.COMPANY_ID SET s.LASTMODIFIEDBYID = u.id;
