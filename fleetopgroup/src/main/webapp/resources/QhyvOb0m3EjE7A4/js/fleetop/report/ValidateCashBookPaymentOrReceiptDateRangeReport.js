function validateCashBookPaymentOrReceiptDateRangeReport()
{
	if(Number($('#CashBookNameDate').val()) <= 0){
		showMessage('info','Please Select Cash Book Info!');
		return false;
	}	
}