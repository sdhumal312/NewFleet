function validateCashBookStatusReport()
{
	if(Number($('#CashBookNameDate2').val()) <= 0){
		showMessage('info','Please Enter Cash Book Info !');
		return false;
	}	
}