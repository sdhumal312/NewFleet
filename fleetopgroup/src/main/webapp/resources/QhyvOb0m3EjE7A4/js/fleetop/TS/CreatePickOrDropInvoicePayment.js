var data = [];
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}
);

$(document).ready(function() {
    
    $("#partyId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartyListByName.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                    	console.log('data ', e);
                        return {
                            text: e.corporateName,
                            slug: e.slug,
                            id: e.corporateAccountId,
                            mobileNumber : e.mobileNumber,
                            gstNumber 	 : e.gstNumber,
                            partyRate    : e.perKMRate,
                            partyAddress : e.address
                        }
                    })
                }
            }
        }
    })
})

$(document).ready(
	function($) {
		$('button[type=submit]').click(function(e) {
			e.preventDefault();

			showLayer();
			var jsonObject				= new Object();

			jsonObject["partyId"] 	  	=  $('#partyId').val();
			
			if($('#partyId').val() <= 0){					
				showMessage('errors', 'Please Select Party Name !');
				hideLayer();
				return false;
			}
			
			$.ajax({
				
				url: "getInvoicePaymentList.do",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					setInvoicePaymentList(data);
					hideLayer();
				},
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});


		});

});

function setInvoicePaymentList(response) {
	var invoicePaymentList					= response.invoicePaymentList;
	var paymentTypeList						= response.paymentTypeList;
	var paymentModeList						= response.paymentMode;

	if(response.invoicePaymentList != undefined && response.invoicePaymentList.length > 0){
		
		$('#contentBody').removeClass('hide');
		$('#settle-border-boxshadow').removeClass('hide');
		$('#settleTable').removeClass('hide');
		
		$('#settleDetails').empty();
		
		for(var i = 0; i < invoicePaymentList.length; i++) {

			if(invoicePaymentList != undefined) {
				
				var tripsheetPickAndDropInvoiceSummaryId  = invoicePaymentList[i].tripsheetPickAndDropInvoiceSummaryId;
				
				var invoiceNum			= '<a href="showPickAndDropInvoice?invoiceSummaryId='+invoicePaymentList[i].tripsheetPickAndDropInvoiceSummaryId+'" target="_blank">IN-'+invoicePaymentList[i].invoiceNumber+'</a>';
				var partyName 			= invoicePaymentList[i].partyName;
				var invoiceDate 		= invoicePaymentList[i].invoiceDateStr;
				var amount 				= invoicePaymentList[i].totalAmount;
				var balanceAmount 		= invoicePaymentList[i].balanceAmount;
				
				var tableRow				= createRowInTable('tr_' + tripsheetPickAndDropInvoiceSummaryId, '', 'height: 35px;');

				var checkBoxCol				= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var invoiceNumCol			= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var partyNameCol			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var invoiceDateCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var amountCol				= createColumnInRow(tableRow, '', '', '3%', '', '', '');
				var balanceAmountCol		= createColumnInRow(tableRow, '', '', '3%', '', '', '');
				var paymentTypeCol			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var transactionCol			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var paymentModeCol			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var paymentDateCol			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var paidAmntCol 			= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var balanceAmntHideCol		= createColumnInRow(tableRow, '', 'hide', '', '', '', '');

				appendValueInTableCol(checkBoxCol, createCheckBoxField(tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(invoiceNumCol, invoiceNum);
				appendValueInTableCol(partyNameCol, partyName);
				appendValueInTableCol(invoiceDateCol, invoiceDate);
				appendValueInTableCol(amountCol, amount);
				appendValueInTableCol(balanceAmountCol, balanceAmount);
				appendValueInTableCol(paymentTypeCol, createPaymentTypeField(paymentTypeList, tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(transactionCol, createTransactionNoField(0, tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(paymentModeCol, createPaymentModeField(paymentModeList, tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(paymentDateCol, createCheckDateField(tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(paidAmntCol, createReceiveAmountFeildSettle(0, tripsheetPickAndDropInvoiceSummaryId));
				appendValueInTableCol(balanceAmntHideCol, createBalanceAmountHidenField(balanceAmount, tripsheetPickAndDropInvoiceSummaryId));
			
				appendRowInTable('settleDetails', tableRow);
				
			}
			
		}
		
	}else{
		$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No Pick Or Drop Invoice Payment Found Found !')
	}
	
	function createCheckBoxField(tripsheetPickAndDropInvoiceSummaryId) {
		var checkBoxField			= $("<input/>", { 
			type		: 'checkbox', 
			id			: 'checkBox_' + tripsheetPickAndDropInvoiceSummaryId, 
			name		: 'checkValue',
			});

		return checkBoxField;
	}
	
	function createPaymentTypeField(paymentTypeList, tripsheetPickAndDropInvoiceSummaryId) {
		
		var paymentTypeSel = $('<select id="paymentType_'+ tripsheetPickAndDropInvoiceSummaryId +'" name="paymentType_'+ tripsheetPickAndDropInvoiceSummaryId +'" class="form-text col-xs-12" />');
		paymentTypeSel.append($("<option>").attr('value', 0).text('---Select Type---'));

		$(paymentTypeList).each(function() {
			paymentTypeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentTypeSel;
	}
	
	function createTransactionNoField(transNo, tripsheetPickAndDropInvoiceSummaryId) {
		var isReadOnly		= false;
		
		var receivedAmountFeild		= $("<input/>", { 
						type		: 'text', 
						id			: 'transactionNo_'+ tripsheetPickAndDropInvoiceSummaryId, 
						class		: 'form-text text-right col-xs-8 receiveAmt', 
						name		: 'transactionNo_'+ tripsheetPickAndDropInvoiceSummaryId, 
						value 		: transNo, 
						});
		
		return receivedAmountFeild;
	}
	
	function createPaymentModeField(paymentModeList, tripsheetPickAndDropInvoiceSummaryId) {
		
		console.log("paymentModeList...",paymentModeList);
		
		var paymentModeSel = $('<select id="paymentMode_'+ tripsheetPickAndDropInvoiceSummaryId +'" name="paymentMode_'+ tripsheetPickAndDropInvoiceSummaryId +'" class="form-text col-xs-12" onchange="paidAmountAction('+ tripsheetPickAndDropInvoiceSummaryId +', this);" />');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Mode---'));

		$(paymentModeList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentModeId).text(this.paymentModeName));
		});
		
		return paymentModeSel;
	}
	
	$('.paidDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
	
	$(function() {
		$( '.paidDate' ).datepicker({
			maxDate		: new Date(),
			dateFormat	: 'dd/mm/yyyy',
			autoclose: true
		});
	});
	
	function createCheckDateField(tripsheetPickAndDropInvoiceSummaryId) {
		var checkDateFeild			= $("<input/>", { 
			type		: 'text', 
			id			: 'paidDate_'+ tripsheetPickAndDropInvoiceSummaryId, 
			class		: 'form-text paidDate', 
			name		: 'paidDate_'+ tripsheetPickAndDropInvoiceSummaryId, 
			placeholder	: 'Paid Date / Txn Date' });

		return checkDateFeild;
	}
	
	function createReceiveAmountFeildSettle(receiveAmount, tripsheetPickAndDropInvoiceSummaryId) {
		var isReadOnly		= false;
		
		var receivedAmountFeild		= $("<input/>", { 
						type		: 'text', 
						id			: 'receiveAmtSettle_'+ tripsheetPickAndDropInvoiceSummaryId, 
						class		: 'form-text text-right col-xs-8 receiveAmt', 
						name		: 'receiveAmtSettle_'+ tripsheetPickAndDropInvoiceSummaryId, 
						value 		: receiveAmount, 
						placeholder	: 'Receive Amount',
						});
		
		return receivedAmountFeild;
	}
	
	function createBalanceAmountHidenField(balanceAmount, tripsheetPickAndDropInvoiceSummaryId) {
		var balanceField	= $("<input/>", { 
						type		: 'hidden', 
						id			: 'balanceAmount_' + tripsheetPickAndDropInvoiceSummaryId, 
						class		: 'form-control', 
						name		: 'balanceAmount_' + tripsheetPickAndDropInvoiceSummaryId, 
						value 		: balanceAmount, 
			});
		
		return balanceField;
	}
	
}

function paidAmountAction(tripsheetPickAndDropInvoiceSummaryId, obj) {
	
	if( $('#paymentMode_'+tripsheetPickAndDropInvoiceSummaryId).val() == 4){
		console.log("olaaa...");
		$('#receiveAmtSettle_'+tripsheetPickAndDropInvoiceSummaryId).val( $('#balanceAmount_'+tripsheetPickAndDropInvoiceSummaryId).val() );
		$('#receiveAmtSettle_'+tripsheetPickAndDropInvoiceSummaryId).attr('readonly', true);
	} else {
		$('#receiveAmtSettle_'+tripsheetPickAndDropInvoiceSummaryId).attr('readonly', false);
		$('#receiveAmtSettle_'+tripsheetPickAndDropInvoiceSummaryId).val(0);
	}
			
}

function validateBeforeSaveSettle() {
	
	var validatePayment = true;
	
	$("input[name=checkValue]").each(function(){
		var selectId 								= this.id;
		var mySplitResult 							= selectId.split("_");
		var tripsheetPickAndDropInvoiceSummaryId	= mySplitResult[1];
		
        if( $('#checkBox_'+tripsheetPickAndDropInvoiceSummaryId).prop("checked") == true ){
        	
        	
        	var pmtType			= $("#paymentType_" + tripsheetPickAndDropInvoiceSummaryId).val();
        	var pmtMode			= $("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).val();
        	var pmtDate			= $("#paidDate_" + tripsheetPickAndDropInvoiceSummaryId).val();
        	var amountBalance   = $("#balanceAmount_" + tripsheetPickAndDropInvoiceSummaryId).val();
        	var paidAmount		= $("#receiveAmtSettle_" + tripsheetPickAndDropInvoiceSummaryId).val();
        	
        	
        	if(Number(pmtType) <= 0){
        		showMessage('errors', 'Please Select Payment Type First !')
        		 $("#paymentType_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		 validatePayment = false;
        		 return validatePayment;
        	}
        	
        	if(Number(pmtMode) <= 0){
        		showMessage('errors', 'Please Select Payment Mode First !')
        		 $("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        	if( pmtDate == null || pmtDate.trim() == ''){
        		showMessage('errors', 'Please Enter Payment Date !')
        		$("#paidDate_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        	if(Number(paidAmount) <= 0) {
        		showMessage('errors', 'Paid Amount cannot be less than 0 !')
        		$("#receiveAmtSettle_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        	if(Number(paidAmount) > Number(amountBalance)){
        		showMessage('errors', 'Received Amount cannot be greater than '+amountBalance+' !')
        		$("#receiveAmtSettle_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        	if( (Number(pmtMode) == 2) && (Number(paidAmount) == Number(amountBalance)) ){
        		showMessage('errors', 'This payment should be Full payemnt. Please change payment type to Full !')
        		 $("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        	if( (Number(pmtMode) == 4) && (Number(paidAmount) != Number(amountBalance)) ){
        		showMessage('errors', 'This payment should be Partial or Negotiate payemnt. Please change payment type to Partial or Negotiate !')
        		 $("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).focus();
        		validatePayment = false;
        		return validatePayment;
        	}
        	
        } else {
        	
        }
	});
	
	if(validatePayment){
		return true;
	} else {
		return false;
	}
	
}

$(document).ready(
		function($) {
			$('button[id=UpSaveButton]').click(function(e) {
				e.preventDefault();
				
				console.log('saving invoice payment details.........')
				
				var jsonObject					= new Object();
				var atleastOneSelected			= false;
				
				var invoiceSummaryIdArr 		= new Array();
				var paymentTypeArr 				= new Array();
				var transactionNoArr 			= new Array();
				var paymentModeArr 				= new Array();
				var paidDateArr 				= new Array();
				var receiveAmountArr 			= new Array();
				
				
				$("input[name=checkValue]").each(function(){
					var selectId 								= this.id;
					var mySplitResult 							= selectId.split("_");
					var tripsheetPickAndDropInvoiceSummaryId	= mySplitResult[1];
					
		            if( $('#checkBox_'+tripsheetPickAndDropInvoiceSummaryId).prop("checked") == true ){
		            	
		            	invoiceSummaryIdArr.push(tripsheetPickAndDropInvoiceSummaryId);
		            	paymentTypeArr.push($("#paymentType_" + tripsheetPickAndDropInvoiceSummaryId).val());
		            	transactionNoArr.push($("#transactionNo_" + tripsheetPickAndDropInvoiceSummaryId).val());
		            	paymentModeArr.push($("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).val());
		            	paidDateArr.push($("#paidDate_" + tripsheetPickAndDropInvoiceSummaryId).val());
		            	receiveAmountArr.push($("#receiveAmtSettle_" + tripsheetPickAndDropInvoiceSummaryId).val());
		            	
		            	atleastOneSelected = true;
		            	
		            	var pmtType			= $("#paymentType_" + tripsheetPickAndDropInvoiceSummaryId).val();
		            	var pmtMode			= $("#paymentMode_" + tripsheetPickAndDropInvoiceSummaryId).val();
		            	var pmtDate			= $("#paidDate_" + tripsheetPickAndDropInvoiceSummaryId).val();
		            	var amountBalance   = $("#balanceAmount_" + tripsheetPickAndDropInvoiceSummaryId).val();
		            	var paidAmount		= $("#receiveAmtSettle_" + tripsheetPickAndDropInvoiceSummaryId).val();
		            	
		            }
				});
				
				
				if(!atleastOneSelected){
					showMessage('errors', 'Please Select one Invoice To Make Payment !');
					return false;
				}
				
				if(!validateBeforeSaveSettle()){
					console.log("yes...")
					return false;
				}
				
				console.log("no...")
				
				var array	 = new Array();
				
				for(var i =0 ; i< invoiceSummaryIdArr.length; i++){
					var invoicePaymentDetails	= new Object();
					
					invoicePaymentDetails.invoiceSummaryId 		 = invoiceSummaryIdArr[i];
					invoicePaymentDetails.paymentType 			 = paymentTypeArr[i];
					invoicePaymentDetails.transNo 			 	 = transactionNoArr[i];
					invoicePaymentDetails.paymentMode 		     = paymentModeArr[i];
					invoicePaymentDetails.paidDate 				 = paidDateArr[i];
					invoicePaymentDetails.paidAmount 			 = receiveAmountArr[i];
					
					array.push(invoicePaymentDetails);
				}
				
				
				jsonObject.invoicePaymentDetails = JSON.stringify(array);
				console.log("jsonObject...",jsonObject);
				
				showLayer();
				$.ajax({
			             url: "savePickOrDropInvoicePayment",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	 console.log("paymentdata.......",data);
			             
			             	if(data.paymentAlreadyDone != undefined && data.paymentAlreadyDone == true) {
			            		hideLayer();
			            		showMessage('info', 'Payment is Already Made for this Invoice.')
			            	}
			             	
			             	if(data.balanceIsZero != undefined && data.balanceIsZero == true) {
			            		hideLayer();
			            		showMessage('info', 'You are proceesing more payment than balance amount hence payment failed.')
			            	}
			             	
			             	if(data.changePayTypeToFull != undefined && data.changePayTypeToFull == true) {
			             		hideLayer();
			             		showMessage('info', 'This payment should be Full payemnt. Please change payment type to Full !')
			             	}
			             	
			             	if(data.changePayTypeToPartialOrNegotiate != undefined && data.changePayTypeToPartialOrNegotiate == true) {
			             		hideLayer();
			             		showMessage('info', 'This payment should be Partial or Negotiate payemnt. Please change payment type to Partial or Negotiate !')
			             	}
			            	
			            	if(data.InvoicePayment != undefined && data.InvoicePayment == true) {
			            		hideLayer();
			            		window.location.replace("createPickOrDropInvoicePayment");
			            		showMessage('success', 'Invoice Payment Done Successfully.')
			            	}
			            	 
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});
			
});