 function validateAddCashBook(){
	  	$('#saveCashBook').hide();
	  	
	  	if($('#CASH_VOUCHER_NO').val() == undefined || $('#CASH_VOUCHER_NO').val() == null || $('#CASH_VOUCHER_NO').val().trim() == ''){
	  		showMessage('info','Please Enter Voucher Number !');
			$('#saveCashBook').show();
			return false;
	  	}
	  	
		if($('#dateOfPayment').val() == undefined || $('#dateOfPayment').val() == null || $('#dateOfPayment').val().trim() == ''){
			$('#dateOfPayment').focus();
			showMessage('info','Please Select Date!');
			$('#saveCashBook').show();
			return false;
		}
		
		if(Number($('#CASH_AMOUNT').val()) <= 0){
			if($("#allowZeroAmountCashBook").val() == "false"  || $("#allowZeroAmountCashBook").val() == false){
				$('#CASH_AMOUNT').focus();
				showMessage('info','Please Enter Cash Amount!');
				$('#saveCashBook').show();
				return false;
			}
			else{
				$('#saveCashBook').show();
				return true;
			}
		}	
		if($('#CASH_PAID_RECEIVED').val() == undefined || $('#CASH_PAID_RECEIVED').val() == null || $('#CASH_PAID_RECEIVED').val().trim() == ''){
			$('#CASH_PAID_RECEIVED').focus();
			showMessage('info','Please Enter '+$('#paid').text()+' !');
			$('#saveCashBook').show();
			return false;
		}	
		if(Number($('#NatureDebitPayment').val()) <= 0){
			showMessage('info','Please Enter Payment Type!');
			$('#saveCashBook').show();
			return false;
		}				
		if(Number($('#NatureCreditPayment').val()) <= 0){
			showMessage('info','Please Enter Receipt Type!');
			$('#saveCashBook').show();
			return false;
		}	
		
		return true;
}  

 
