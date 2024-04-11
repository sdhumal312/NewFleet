select * from drivercomment;

#update drivercomment d inner join User u on u.email = d.createdBy AND u.company_id = d.companyId SET d.createdById = u.id;