#update cashbook set CASH_APPROVAL_STATUS_ID = 1 where CASH_APPROVAL_STATUS = 'NOT APPROVED';
#update cashbook set CASH_APPROVAL_STATUS_ID = 2 where CASH_APPROVAL_STATUS = 'APPROVED';
#update cashbook set CASH_STATUS_ID = 1 where CASH_STATUS = 'OPEN';
#update cashbook set CASH_STATUS_ID = 2 where CASH_STATUS = 'CLOSED';
#update cashbook set PAYMENT_TYPE_ID = 1 WHERE CASH_PAYMENT_TYPE = 'DEBIT';
#update cashbook set PAYMENT_TYPE_ID = 2 WHERE CASH_PAYMENT_TYPE = 'CREDIT';

#update CashBookBalance set CASH_STATUS_ID = 1 where CASH_STATUS = 'OPEN';
#update CashBookBalance set CASH_STATUS_ID = 2 where CASH_STATUS = 'CLOSED';

#update CashBookBalance set CASH_BOOK_MAIN_ID = 1 where CASH_BOOK_MAIN = 'MAIN';
#update CashBookBalance set CASH_BOOK_MAIN_ID = 2 where CASH_BOOK_MAIN = 'MAIN-CASH-BOOK';

#ALTER TABLE `CashBookBalance` CHANGE `CASH_BOOK_MAIN` `CASH_BOOK_MAIN` VARCHAR(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL;
