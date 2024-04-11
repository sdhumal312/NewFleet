

#update driverdoctype d inner join User u on u.email = d.createdBy AND u.company_id = d.company_Id SET d.createdById = u.id;
#update driverdoctype d inner join User u on u.email = d.lastModifiedBy AND u.company_id = d.company_Id SET d.lastModifiedById = u.id;