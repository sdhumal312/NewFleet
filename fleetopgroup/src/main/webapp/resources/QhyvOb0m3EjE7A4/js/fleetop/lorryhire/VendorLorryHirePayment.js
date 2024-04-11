var grandTotal;
var grandTotalBalanceAmount;
var totalReceivedAmount;

$(document).ready(function() {
	
	var vendorId 	= $('#vendorId').val();
	var loryHireId 	= $('#loryHireId').val();
	
	if(Number(vendorId) > 0){
		showLayer();
		var jsonObject					= new Object();
		jsonObject["vendorId"]					= vendorId; 
		jsonObject["lorryHireDetailsId"]		= loryHireId; 
		getVendorLorryHirePaymentData(jsonObject);
	}
	
    $("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
});


function setRequestData(){
	var vendorId = $('#selectVendor').val();
	
	if(Number(vendorId) <= 0){
		showMessage('info','Please select Vendor !');
		return false;
	}
	
	showLayer();
	var jsonObject				= new Object();
	
	jsonObject["vendorId"]		= vendorId; 
	
	getVendorLorryHirePaymentData(jsonObject);
}

function getVendorLorryHirePaymentData(jsonObject){
	
	$.ajax({
		url: "getVendorLorryHirePaymentData.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			setLorryHireDateForPayment(response);
			if(response.vendor != undefined){
				$('#selectVendor').select2('data', {
				id : response.vendor.vendorId,
				text : response.vendor.vendorName
		 });
		 $("#selectVendor").select2('readonly', true);
			}
			hideLayer();
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
}


function setLorryHireDateForPayment(response) {
	var lorryHireDetailsList		= response.lorryHireDetailsList;
	var paymentTypeList				= response.paymentTypeList;
	var PaymentStatusConstants		= response.PaymentStatusConstants;
	if(response.lorryHireDetailsList != undefined && response.lorryHireDetailsList.length > 0){
		$('#middle-border-boxshadow').removeClass('hide');
		$('#billDetails').empty();
		
		var grandTotalBalanceAmount	= 0,totalReceivedAmount = 0;
		for(var i = 0; i < lorryHireDetailsList.length; i++) {
			var lorryHire		= lorryHireDetailsList[i];

			if(lorryHire != undefined) {
				var srNo					= i + 1;
				var lorryHireDetailsNumber	= lorryHire.lorryHireDetailsNumber;
				var lorryHireDetailsId		= lorryHire.lorryHireDetailsId;
				var hireDateStr				= lorryHire.hireDateStr;
				var paidAmount				= lorryHire.paidAmount;
				var advanceAmount			= lorryHire.advanceAmount;
				var balanceAmount			= lorryHire.balanceAmount;
				var paymentStatus			= lorryHire.paymentStatus;
				var vehicle_registration	= lorryHire.vehicle_registration;
				var lorryHires				= lorryHire.lorryHire;
				var otherCharges			= lorryHire.otherCharges;
				
				grandTotalBalanceAmount		+= balanceAmount;
				totalReceivedAmount			+= paidAmount;
				
				var tableRow				= createRowInTable('tr_' + lorryHireDetailsId, '', 'height: 35px;');

				var srNoCol					= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var hiddenCol				= createColumnInRow(tableRow, '', 'hide', '', '', '', '');
				var detailsNumber			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var dateTimeCol				= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				
				var paymentModeCol			= createColumnInRow(tableRow, '', '', '9%', '', '', '');
				var remarkCol				= createColumnInRow(tableRow, '', '', '10%', '', '', '');
				var paymentStatusCol		= createColumnInRow(tableRow, '', '', '10%', '', '', '');
				var lorryHireCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var advanceAmountCol		= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var otherChargesCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var paidAmountCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var balanceAmountCol		= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var receiveAmountCol 		= createColumnInRow(tableRow, '', '', '9%', '', '', '');
				

				appendValueInTableCol(srNoCol, srNo);
				appendValueInTableCol(hiddenCol, createReceivedAmtLimitFeild(balanceAmount, lorryHireDetailsId));
				
				appendValueInTableCol(detailsNumber, lorryHireDetailsNumber);
				appendValueInTableCol(dateTimeCol, hireDateStr);
				
				appendValueInTableCol(paymentModeCol, createPaymentModeSelection(paymentTypeList, lorryHireDetailsId));
				appendValueInTableCol(remarkCol, createRemarkField(lorryHireDetailsId));
				appendValueInTableCol(remarkCol, createCheckNumberField(lorryHireDetailsId));
				appendValueInTableCol(remarkCol, createCheckDateField(lorryHireDetailsId));
				appendValueInTableCol(lorryHireCol, lorryHires);
				appendValueInTableCol(advanceAmountCol, advanceAmount);
				appendValueInTableCol(otherChargesCol, otherCharges);
				appendValueInTableCol(paidAmountCol, paidAmount);
				appendValueInTableCol(balanceAmountCol, createBalanceAmountFeild(balanceAmount, lorryHireDetailsId));
				appendValueInTableCol(receiveAmountCol, createReceiveAmountFeild(0, lorryHireDetailsId));
				appendValueInTableCol(paymentStatusCol, createPaymentTypeSelection(lorryHireDetailsId));
			
				appendRowInTable('billDetails', tableRow);
				
			}
			
		}
		$('#pendingAmount').html(grandTotalBalanceAmount);
		$('.chequeDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
		
		$( function() {
			$( '.chequeDate' ).datepicker({
				maxDate		: new Date(),
				dateFormat	: 'dd/mm/yyyy',
				autoclose: true
			});
		} );
		
		/*$( function() {
			$( '.chequeDate' ).datepicker({
				maxDate		: new Date(),
				showAnim	: "fold",
				dateFormat	: 'dd-mm-yyyy'
			});
		} );*/
	}else{
		$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No record found !')
	}


	//craeteRowForGrandTotal(grandTotal, totalReceivedAmount, grandTotalBalanceAmount);
	
	function createPaymentModeSelection(paymentTypeConstantsList, lorryHireDetailsId) {
		var paymentModeSel = $('<select id="paymentMode_'+ lorryHireDetailsId +'" name="paymentMode_'+ lorryHireDetailsId +'" class="form-text col-xs-12" onchange="hideShowChequeDetails('+ lorryHireDetailsId +', this);"/>');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Mode---'));

		$(paymentTypeConstantsList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentModeSel;
	}

}

function hideShowChequeDetails(lorryHireDetailsId, obj) {
	/*var objName 		= obj.name;
	var mySplitResult 	= objName.split("_");
	if(obj.value != 1 && obj.value != 0) {
		$('#chequeNumber_'+lorryHireDetailsId).removeClass('hide');
		$('#chequeDate_'+lorryHireDetailsId).removeClass('hide');
	}else { 
		$('#chequeNumber_'+lorryHireDetailsId).addClass('hide');
		$('#chequeDate_'+lorryHireDetailsId).addClass('hide');
	
	}*/
	
	$('#chequeNumber_'+lorryHireDetailsId).removeClass('hide');
	$('#chequeDate_'+lorryHireDetailsId).removeClass('hide');
			
}



function createRemarkField(lorryHireDetailsId) {
	var remarkFeild			= $("<input/>", { 
				type		: 'text', 
				id			: 'remark_' + lorryHireDetailsId, 
				class		: 'form-text', 
				name		: 'remark_' + lorryHireDetailsId, 
				placeholder	: 'Remark'});
	
	return remarkFeild;
}

function createCheckNumberField(lorryHireDetailsId) {
	var checkNumberFeild		= $("<input/>", { 
		type			: 'text', 
		id				: 'chequeNumber_' + lorryHireDetailsId, 
		class			: 'form-text hide', 
		name			: 'chequeNumber_' + lorryHireDetailsId, 
		placeholder		: 'Txn No.'});

	return checkNumberFeild;
}

function createBankNameField(lorryHireDetailsId) {
	var bankNameFeild		= $("<input/>", { 
		type			: 'text', 
		id				: 'bankName_' + lorryHireDetailsId, 
		class			: 'form-text hide', 
		name			: 'bankName_' + lorryHireDetailsId, 
		style			: "text-transform : UPPERCASE",
		placeholder		: 'Bank Name'});

	return bankNameFeild;
}

function createCheckDateField(lorryHireDetailsId) {
	var checkDateFeild			= $("<input/>", { 
		type		: 'text', 
		id			: 'chequeDate_' + lorryHireDetailsId, 
		class		: 'form-text hide chequeDate', 
		name		: 'chequeDate_' + lorryHireDetailsId, 
		placeholder	: 'Cheque Date / Txn Date' });

	return checkDateFeild;
}

function createPaymentTypeSelection(lorryHireDetailsId) {
	var paymentStatusSel = $('<select id="paymentStatus_'+ lorryHireDetailsId +'" name="paymentStatus_'+ lorryHireDetailsId +'" class="form-text col-xs-12" onchange="setReceiveAmountOnPaymentStatus(this);"/>');
	paymentStatusSel.append($("<option>").attr('value', 0).text('---Select Type---'));

		paymentStatusSel.append($("<option>").attr('value', 2).text('Clear Payment'));
		paymentStatusSel.append($("<option>").attr('value', 3).text('Partial Payment'));
		paymentStatusSel.append($("<option>").attr('value', 4).text('Negotiated'));
	
	return paymentStatusSel;
}

function setReceiveAmountOnPaymentStatus(obj) {
	var objName 		= obj.name;
	var mySplitResult 	= objName.split("_");
	var lorryHireDetailsId			= mySplitResult[1];
	
	/*if(validatePaymentSelection(lorryHireDetailsId)) {
		return false;
	}*/
	
	var paymentStatus		= $('#paymentStatus_' + lorryHireDetailsId).val();
	var totalAmount			= $('#grandTotal_' + lorryHireDetailsId).val();
	var receivedAmount		= $('#receiveAmt_' + lorryHireDetailsId).val();
	var balanceAmount		= $('#balanceAmt_' + lorryHireDetailsId).val();
	var receiveAmountLimit	= $('#receivedAmtLimit_' + lorryHireDetailsId).val();
	//var balanceAmount	= 0;
	

	if(paymentStatus == 2) {
		
		$('#balanceAmt_' + lorryHireDetailsId).val(0);
		if(balanceAmount < 0) {
			$('#receiveAmt_' + lorryHireDetailsId).val(-balanceAmount);
		} else {
			$('#receiveAmt_' + lorryHireDetailsId).val(balanceAmount);
		}
		
		$('#receiveAmt_' + lorryHireDetailsId).prop("readonly", true);
		
	} else {
		if($('#receiveAmt_' + lorryHireDetailsId).val() > 0) {
			$('#balanceAmt_' + lorryHireDetailsId).val(receiveAmountLimit);
			$('#receiveAmt_' + lorryHireDetailsId).val(0);
		}
		$('#receiveAmt_' + lorryHireDetailsId).prop("readonly", false);
	}
	
	//calTotalAmounts();
}

function calTotalAmounts() {
	var totalAmount			= 0;
	var totalReceiveAmount	= 0;
	var totalBalanceAmount 	= 0;
	var totalTdsAmount		= 0;
	
	for(var i = 0; i < lorryHireDetailsIdArrlist.length; i++) {
		if($('#grandTotal_' + lorryHireDetailsIdArrlist[i]).val() > 0) {
			totalAmount 		= Number(totalAmount) + Number($('#grandTotal_' + lorryHireDetailsIdArrlist[i]).val());
		}
		
		if($('#receiveAmt_' + lorryHireDetailsIdArrlist[i]).val() > 0) {
			totalReceiveAmount 	= Number(totalReceiveAmount) + Number($('#receiveAmt_' + lorryHireDetailsIdArrlist[i]).val());
		}
		
		if($('#balanceAmt_' + lorryHireDetailsIdArrlist[i]).val() > 0) {
			totalBalanceAmount 	= Number(totalBalanceAmount) + Number($('#balanceAmt_' + lorryHireDetailsIdArrlist[i]).val());
		}
		
		if($('#tdsAmt_' + lorryHireDetailsIdArrlist[i]).val() > 0) {
			totalTdsAmount 		= Number(totalTdsAmount) + Number($('#tdsAmt_' + lorryHireDetailsIdArrlist[i]).val());
		}
	}	
	
	$('#totalAmountCol').html(totalAmount);
	$('#totalReceiveAmtCol').html(totalReceiveAmount);
	$('#totalBalanceAmtCol').html(totalBalanceAmount);
	$('#totalTDSAmtCol').html(totalTdsAmount);
}
function createReceiveAmountFeild(receiveAmount, lorryHireDetailsId) {
	var isReadOnly		= false;
	
	var receivedAmountFeild		= $("<input/>", { 
					type		: 'text', 
					id			: 'receiveAmt_' + lorryHireDetailsId, 
					class		: 'form-text text-right col-xs-8 receiveAmt', 
					name		: 'receiveAmt', 
					value 		: receiveAmount, 
					readonly	: isReadOnly,
					placeholder	: 'Receive Amount',
					onfocus		: 'resetTextFeild(this, 0);',
					onblur 		: "resetTextFeild(this, 0);clearIfNotNumeric(this, 0);validateReceiveAmount(this);",
					onkeypress 	: "return noNumbers(event ,this);",
					onkeyup 	: "validateReceiveAmount(this);"});
	
	return receivedAmountFeild;
}

function createReceivedAmtLimitFeild(receivedAmtLimit, lorryHireDetailsId) {
	var receivedAmtLimitFeild	= $("<input/>", { 
					type		: 'hidden', 
					id			: 'receivedAmtLimit_' + lorryHireDetailsId, 
					class		: 'form-control', 
					name		: 'receivedAmtLimit_' + lorryHireDetailsId, 
					value 		: receivedAmtLimit, 
					placeholder	: 'Remark'});
	
	return receivedAmtLimitFeild;
}

function createBalanceAmountFeild(balanceAmount, lorryHireDetailsId) {
	var balanceAmountFeild		= $("<input/>", { 
					type		: 'text', 
					id			: 'balanceAmt_' + lorryHireDetailsId, 
					class		: 'form-text col-xs-8 text-right', 
					name		: 'balanceAmt_' + lorryHireDetailsId, 
					value 		: balanceAmount, 
					readonly 	: 'readonly',
					maxlength 	: '7',
					placeholder	: 'Balance Amount'});
	
	return balanceAmountFeild;
}
function validateReceiveAmount(obj) {
	var objName 		= obj.id;
	var objVal			= 0;
	var splitVal 		= objName.split("_");
	var lorryHireDetailsId			= splitVal[1];
	
	if(!validateBeforeSave(obj)) {
		$('#receiveAmt_' + lorryHireDetailsId).val(0);
		return false;
	}
	
	
	
}

function validateBeforeSave(obj) {

	var objName				= obj.id;
	var mySplitResult 		= objName.split("_");
	var lorryHireDetailsId	= mySplitResult[1];
	var receiveAmount		= $("#receiveAmt_" + lorryHireDetailsId).val();
	var paymentMode			= $("#paymentMode_" + lorryHireDetailsId).val();
	var balanceAmt 			= $("#balanceAmt_" + lorryHireDetailsId).val();
	var paymentStatus		= $('#paymentStatus_' + lorryHireDetailsId).val();
	var receiveLimit		= $('#receivedAmtLimit_' + lorryHireDetailsId).val();
	
	if(Number(paymentMode) <= 0){
		showMessage('errors', 'Please Select Payment Mode First !')
		 $("#paymentMode_" + lorryHireDetailsId).focus();
		return false;
	}else if(Number(paymentMode) != 1){
		if($('#chequeNumber_' + lorryHireDetailsId).val() == null || $('#chequeNumber_' + lorryHireDetailsId).val().trim() == ''){
			$('#chequeNumber_' + lorryHireDetailsId).focus();
			showMessage('errors', 'Please enter '+getPaymentModeName(Number(paymentMode))+' number !')
			return false;
		}
	}
	
	if(Number(paymentStatus) <= 0){
		showMessage('errors', 'Please Select Received As First !')
		return false;
	}
	
	
	if(Number(receiveAmount) > Number(receiveLimit)){
		showMessage('info', 'Received Amount cannot be greater than '+receiveLimit+' !')
		return false;
	}else if(Number(receiveAmount) == Number(receiveLimit)){
		showMessage('info', 'Please Change Received As To Clear Payment !')
		return false;
	}
	
	return true;
}

function getPaymentModeName(paymentMode){
	var name = '';
	switch(paymentMode) {
	  case 1:
		  name = 'CASH'
	    break;
	  case 2:
		   name = 'CREDIT';
	    break;
	  case 3:
		   name = 'NEFT';
	    break;
	  case 4:
		   name = 'RTGS';
	    break;
	  case 5:
		   name = 'IMPS';
	    break;
	  case 6:
		   name = 'DD';
	    break;
	  case 7:
		   name = 'CHEQUE';
	    break;
	  case 8:
		   name = 'BANK DRAFT';
	    break;
	  case 9:
		   name = 'COD';
	    break;
	  default:
	}
	
	return name;
}

$(document).ready(
		function($) {
			$('button[id=UpSaveButton]').click(function(e) {
				e.preventDefault();
				var anyselected	= false;
				
				$('input[name*=receiveAmt]' ).each(function(){
					var receiveValue = $("#"+$( this ).attr( "id" )).val();
					if(Number(receiveValue) > 0){
						 anyselected	= true;
					}
				});
				
				if(!anyselected){
					showMessage('errors', 'Please enter details for atleast one Bill !');
					return false;
				}
				
				var jsonObject				= new Object();
				
				var receiveAmtArr 			= new Array();
				var lorryHireDetailsIdArr 	= new Array();
				var paymentModeArr			= new Array();
				var remarkArr				= new Array();
				var txnNoArr				= new Array();
				var txnDateArr				= new Array();
				var paymentTypeArr			= new Array();
				

				jsonObject["vendorId"] 	=  $('#selectVendor').val();
				
				$("input[name=receiveAmt]").each(function(){
					var selectId = this.id;
					var mySplitResult 		= selectId.split("_");
					var lorryHireDetailsId	= mySplitResult[1];
					var receiveAmt	= Number($('#receiveAmt_'+lorryHireDetailsId).val());
					if(receiveAmt > 0){
						receiveAmtArr.push(receiveAmt);
						lorryHireDetailsIdArr.push(lorryHireDetailsId);
						paymentModeArr.push($("#paymentMode_" + lorryHireDetailsId).val());
						remarkArr.push($("#remark_" + lorryHireDetailsId).val());
						txnNoArr.push($("#chequeNumber_" + lorryHireDetailsId).val());
						txnDateArr.push($("#chequeDate_" + lorryHireDetailsId).val());
						paymentTypeArr.push($("#paymentStatus_" + lorryHireDetailsId).val());
					}
					
				});
				
				
				
				var array	 = new Array();
				
				var totalAmount = 0;
				for(var i =0 ; i< receiveAmtArr.length; i++){
					var lorryHirePayment	= new Object();
					
					lorryHirePayment.receiveAmt				= receiveAmtArr[i];
					lorryHirePayment.lorryHireDetailsId		= lorryHireDetailsIdArr[i];
					lorryHirePayment.paymentMode			= paymentModeArr[i];
					lorryHirePayment.remark					= remarkArr[i];
					lorryHirePayment.txnNo					= txnNoArr[i];
					lorryHirePayment.txnDate				= txnDateArr[i];
					lorryHirePayment.paymentType			= paymentTypeArr[i];
					
					totalAmount +=  receiveAmtArr[i];
					
					array.push(lorryHirePayment);
				}
				jsonObject.lorryHirePaymentDetails = JSON.stringify(array);
				
				jsonObject["totalAmount"] 	=  totalAmount;
				
				showLayer();
				$.ajax({
			             url: "savLorryHirePaymentDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	if(!data.sequenceNotFound) {
			            		 window.location.replace("makeLorryHirePayment.in?saved=true");
			            	} else {
			            		showMessage('errors', 'Sequence Counter not found , Please contact to System Administrator !');			            		
			            	}
			            	 hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});
				
			var saved	= getUrlParameter('saved');
			if(saved == 'true' || saved == true){
				showMessage('success', 'Data Saved Succesfully !');
			}
			
		});
