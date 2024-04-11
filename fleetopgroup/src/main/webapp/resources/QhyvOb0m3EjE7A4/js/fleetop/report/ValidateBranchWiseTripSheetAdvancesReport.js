function validateBranchWiseTripSheetAdvancesReport()
{
	if(Number($('#branchnamelist').val()) <= 0){
		showMessage('info','Please Select Branch Name!');
		return false;
	}	
	if(Number($('#workOrderGroup').val()) <= 0){
		if($('#validateGroup').val() == true || $('#validateGroup').val() == 'true'){
			$('#workOrderGroup').val("0");
			return true;
		}
		showMessage('info','Please Select Group!');
		return false;
	}
}