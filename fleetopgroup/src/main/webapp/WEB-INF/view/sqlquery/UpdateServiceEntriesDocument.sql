
select * from ServiceEntriesDocument;

update ServiceEntriesDocument s inner join User u on u.email = s.createdBy AND u.company_id = s.companyId SET s.createdById = u.id;

update ServiceEntriesDocument s inner join User u on u.email = s.lastModifiedBy AND u.company_id = s.companyId SET s.lastModifiedById = u.id;