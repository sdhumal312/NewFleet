select * from masterParts;

#update masterParts mp inner join User U ON mp.createdBy = U.email AND mp.companyId = U.company_id SET mp.createdById = U.id;

#update masterParts mp inner join User U ON mp.lastModifiedBy = U.email AND mp.companyId = U.company_id SET mp.lastModifiedById = U.id;