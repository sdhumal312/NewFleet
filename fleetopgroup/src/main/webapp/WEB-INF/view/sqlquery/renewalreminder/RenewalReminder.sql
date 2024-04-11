
update renewalreminder INNER JOIN User ON renewalreminder.createdBy = User.email AND renewalreminder.`companyId` = User.company_id SET renewalreminder.`createdById`= User.id;

update renewalreminder INNER JOIN User ON renewalreminder.`lastModifiedBy`= User.email AND renewalreminder.`companyId` = User.company_id SET renewalreminder.`lastModifiedById`= User.id;

update renewalreminder INNER JOIN User ON renewalreminder.`renewal_approvedby`= User.email AND renewalreminder.`companyId` = User.company_id SET renewalreminder.`renewal_approvedbyId`= User.id;

update renewalreminder INNER JOIN User ON renewalreminder.`renewal_paidby`= User.email AND renewalreminder.`companyId` = User.company_id SET renewalreminder.`renewal_paidbyId`= User.id;