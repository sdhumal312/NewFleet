select * from partmanufacturer;

#update partmanufacturer set markForDelete = 1 where companyId <> 1; 

select CONCAT('UPDATE masterParts  SET makerId = ',p1.pmid,' where makerId = ',p2.pmid,';') from partmanufacturer p1 
inner join partmanufacturer p2 on p2.pmName = p1.pmName
where p1.companyId = 1 and p1.pmid <> p2.pmid order by p1.pmName


