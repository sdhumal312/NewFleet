-- renewalreminder table
ALTER TABLE renewalreminder DROP COLUMN vehicle_registration;
ALTER TABLE renewalreminder DROP COLUMN renewal_type;
ALTER TABLE renewalreminder DROP COLUMN renewal_subtype;
ALTER TABLE renewalreminder DROP COLUMN renewal_paymentType;
ALTER TABLE renewalreminder DROP COLUMN renewal_status;
#alter table renewalreminder drop column renewal_paidby;
#alter table renewalreminder drop column renewal_approvedby;
#alter table renewalreminder drop column createdBy;
#alter table renewalreminder drop column lastModifiedBy;
#alter table renewalreminder drop column renewal_dateofRenewal;



ALTER TABLE RenewalReminderDocument DROP COLUMN 	status;