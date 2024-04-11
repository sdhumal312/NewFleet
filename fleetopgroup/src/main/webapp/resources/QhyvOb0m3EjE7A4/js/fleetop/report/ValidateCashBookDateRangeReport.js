function validateCashBookDateRangeReport()
{
	if(Number($('#CashBookNameList').val()) <= 0){
		showMessage('info','Please Select Cash Book No!');
		return false;
	}	
	if(Number($('#dateOfPayment').val()) <= 0){
		showMessage('info','Please Select Date!');
		return false;
	}
	
}