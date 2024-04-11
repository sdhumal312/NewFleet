function validateBankAccount(){
	console.log('validating Bank Account...');
	if($('#bankId').val() == 0){
		$("#bankId").select2('focus');
		showMessage('errors','Please Select Bank!');
		return false;
	}
	if($('#name').val() == undefined ||$('#name').val() == '' || $('#name').val() == null){
		$("#name").focus();
		showMessage('errors','Please Enter Branch Name!');
		return false;
	}
	if($('#accountNumber').val() == undefined ||$('#accountNumber').val() == '' || $('#accountNumber').val() == null){
		$("#accountNumber").focus();
		showMessage('errors','Please Enter Account Number!');
		return false;
	}
	return true;
}