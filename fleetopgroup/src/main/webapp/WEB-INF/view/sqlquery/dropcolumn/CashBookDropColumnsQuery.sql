-- CashBook table
ALTER TABLE CashBook DROP COLUMN CASH_PAYMENT_TYPE;
ALTER TABLE CashBook DROP COLUMN CASH_STATUS;
ALTER TABLE CashBook DROP COLUMN CASH_APPROVAL_STATUS;

-- CashBookBalance table
ALTER TABLE CashBookBalance DROP COLUMN CASH_STATUS;
ALTER TABLE CashBookBalance DROP COLUMN CASH_BOOK_MAIN;