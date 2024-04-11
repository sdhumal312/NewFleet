select distinct(pcName) from partcategories order by pcName;

select * from partcategories;

#update partcategories set markForDelete = 1 where companyId <> 1;

select CONCAT('UPDATE masterParts  SET categoryId = ',p1.pcid,' where categoryId = ',p2.pcid,';') from partcategories p1 
inner join partcategories p2 on p2.pcName = p1.pcName
where p1.companyId = 1 and p1.pcid <> p2.pcid order by p1.pcName

