
#alter table InventoryRequisition modify column REQUITED_LOCATION varchar(250);

#alter table inventory modify column partname varchar(150);
#alter table inventory modify column partnumber varchar(25);


alter table inventorytransfer modify column transfer_partname varchar(150);
alter table inventorytransfer modify column transfer_partnumber varchar(50);
alter table inventorytransfer modify column TRANSFER_STATUS varchar(50);

#update InventoryRequisition I inner join User U ON U.email = I.CREATEDBY AND I.COMPANY_ID = U.company_id SET I.CREATEDBYID = U.id;

#update InventoryRequisition I inner join User U ON U.email = I.LASTMODIFIEDBY AND I.COMPANY_ID = U.company_id SET I.LASTMODIFIEDBYID = U.id;