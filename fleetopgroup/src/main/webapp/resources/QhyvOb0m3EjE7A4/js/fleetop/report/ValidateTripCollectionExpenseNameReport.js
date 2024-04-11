function validateTripCollectionExpenseNameReport()
{
	if(Number($('#ReportExpenseName').val()) <= 0){
		showMessage('info','Please Select Expense Name!');
		return false;
	}	
	
	return true;
}