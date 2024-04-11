


#update driverTrainingtype d inner join User u on u.email = d.createdBy AND u.company_id = d.companyId SET d.createdById = u.id;

#update driverTrainingtype d inner join User u on u.email = d.lastModifiedBy AND u.company_id = d.companyId SET d.lastModifiedById = u.id;