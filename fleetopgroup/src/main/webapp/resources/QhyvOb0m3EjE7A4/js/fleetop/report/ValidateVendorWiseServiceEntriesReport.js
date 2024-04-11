function validateVendorWiseServiceEntriesReport()
{
	if(Number($('#ServiceVendorList').val()) <= 0){
		showMessage('info','Please Select Vendor !');
		return false;
	}	
}
