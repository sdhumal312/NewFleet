function validateVendorPaymentReport()
{
	if(Number($('#selectVendor').val()) <= 0){
		showMessage('info','Please Select Vendor !');
		return false;
	}	
}
