$(function() {
	function a(a, b) {
		$("#dueAmountRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dueAmountRange").daterangepicker( {
		format : 'DD-MM-YYYY',
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
	$("#driverId").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getDriverALLListOfCompany.in",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                        	text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
	                        }
	                    })
	                }
	            }
	        }
   }); 
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["driverId"] 				=  $('#driverId').val();
				jsonObject["dueAmountRange"] 	  	=  $('#dueAmountRange').val();
				
				if($('#showbilltypeDropdown').val() == true || $('#showbilltypeDropdown').val() == "true"){	
					if($('#billselectionId').val()  ==null){
						showMessage('info', ' Not Authourized User to check & payment - Due Amount ! ');
						return false;
					}
					if($('#billselectionId').val() =="B_INCOME"){
						jsonObject["billselectionId"]       =  1;
					}else if($('#billselectionId').val() =="E_INCOME"){
						jsonObject["billselectionId"]       =  2;
					}
				}
				
				if($('#dueAmountRange').val() == ""){
					showMessage('errors', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "getDueAmountReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setDueAmountReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

});

function setDueAmountReport(data) {

	$("#reportHeader").html("Tripsheet Due Amount Report");
	$("#dueAmountTable").empty();
	
	var thead = $('<thead style="background-color: aqua;">');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');
	var th9		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Tripsheet No."));
	tr1.append(th3.append("Name"));
	tr1.append(th4.append("Approx Date"));
	tr1.append(th5.append("Due Date"));
	tr1.append(th6.append("Description"));
	tr1.append(th7.append("Due Amount"));
	tr1.append(th8.append("Balance Amount"));
	tr1.append(th9.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.dueAmountList != undefined && data.dueAmountList.length > 0) {
		
		var dueAmountList = data.dueAmountList;
	
		for(var i = 0; i < dueAmountList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet?tripSheetID='+dueAmountList[i].tripSheetID+'" target="_blank">TS-'+dueAmountList[i].tripSheetNumber+'</a>'));
			tr1.append(td3.append(dueAmountList[i].driver_firstname + "-"+dueAmountList[i].driver_Lastname));
			tr1.append(td4.append(dueAmountList[i].approximateDateStr)); 
			tr1.append(td5.append(dueAmountList[i].dueDateStr)); 
			tr1.append(td6.append(dueAmountList[i].remark));
			tr1.append(td7.append(dueAmountList[i].dueAmount));
			tr1.append(td8.append(dueAmountList[i].balanceAmount.toFixed(2)));
			tr1.append(td9.append("<a class='btn btn-info' onclick='makePayment("+dueAmountList[i].tripsheetDueAmountId+");'><span class='fa fa-inr'></span>Payment</a>"));

			tbody.append(tr1);
		}
		
		$("#dueAmountTable").append(thead);
		$("#dueAmountTable").append(tbody);
		$("#ResultContent").removeClass("hide");
		
	} else {
		showMessage('info', 'No Record Found!')
	}

}

function makePayment(tripsheetDueAmountId){
	$("#addExpense").addClass('hide');
	
	var jsonObject							= new Object();
	jsonObject["tripsheetDueAmountId"] 	  	=  tripsheetDueAmountId;
	
	showLayer();
	$.ajax({
		url: "getDueAmountPaymentById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			setDueAmountPaymentById(response);
			hideLayer();
			$('#duePayment').modal('show');
			$("#saveSettlement").show();	 	 
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
}

function setDueAmountPaymentById(response) {
	var dueAmountDetails			= response.dueAmountDetails;
	var paymentModeList				= response.paymentMode;
	var paymentTypeList				= response.paymentType;
	var tripExpenseList				= response.TripExpenseList;
	var TransactionType				= response.TransactionType;
	var temp 						= 1;
	
	if(dueAmountDetails != undefined){
		
		$("#showTripNumber").text("TS-"+dueAmountDetails.tripSheetNumber);
		$("#createdDate").text(dueAmountDetails.tripsheetCreatedStr);
		$("#vehicleRegistration").text(dueAmountDetails.vehicle_registration);
		$("#route").text(dueAmountDetails.routeName);
		$("#tripOpenDate").text(dueAmountDetails.tripsheetCreatedStr);
		$("#vehicleGroup").text(dueAmountDetails.vehicleGroup);
		$("#driver").text(dueAmountDetails.driver_firstname);
		
		$("#tripsheetDueAmountId").val(dueAmountDetails.tripsheetDueAmountId);
		$("#tripsheetId").val(dueAmountDetails.tripSheetID);
		$("#amountDue").val(dueAmountDetails.dueAmount);
		$("#amountBalance").val(dueAmountDetails.balanceAmount);
		
		$('#settle-border-boxshadow').removeClass('hide');
		$('#settleDetails').empty();
		
		$('.typePayment').addClass('hide');
		$('.transactionType').addClass('hide');
		$('.transactionNo').addClass('hide');
		$('#paymentTypeSettle').addClass('hide');
		$('#transactionModeSettle').addClass('hide');
		
		$('.typeExpense').addClass('hide');
		$('#paymentTypeExpenseSettle').addClass('hide');
		$(".newRow").remove()
		$("#DuedriverId").val(dueAmountDetails.driver_id);
			
			var tripsheetDueAmountId			= dueAmountDetails.tripsheetDueAmountId;
				
			var srNoSettle						= 1;
			var driverName						= dueAmountDetails.driver_firstname;
			var dueDate							= dueAmountDetails.dueDateStr;
			var dueAmount						= dueAmountDetails.dueAmount;
			var balanceAmount					= dueAmountDetails.balanceAmount;
			
			var tableRow						= createRowInTable('tr_' + tripsheetDueAmountId, '', 'height: 35px;');

			var srNoColSettle					= createColumnInRow(tableRow, '', '', '2%', '', '', '');
			var driverNameSettle				= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var dueDateSettle					= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var dueAmountSettle					= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var balanceAmountSettle				= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var paymentModeSettle				= createColumnInRow(tableRow, '', '', '9%', '', '', '');
			var paymentTypeSettle				= createColumnInRow(tableRow, '', 'hide typePayment', '10%', '', '', '');
			var paymentTypeExpenseSettle		= createColumnInRow(tableRow, '', 'hide typeExpense', '10%', '', '', '');
			var transactionTypeSettle    		= createColumnInRow(tableRow, '', 'hide transactionType', '10%', '', '', '');
			var transactionIdSettle    			= createColumnInRow(tableRow, '', 'hide transactionNo fit ar', '10%', '', '', '');
			var paidDateColSettle 				= createColumnInRow(tableRow, '', '', '8%', '', '', '');
			var paidAmntColSettle 				= createColumnInRow(tableRow, '', '', '8%', '', '', '');
			var referenceSettle 				= createColumnInRow(tableRow, '', '', '8%', '', '', '');
			appendValueInTableCol(srNoColSettle, srNoSettle);
			appendValueInTableCol(driverNameSettle, driverName);
			appendValueInTableCol(dueDateSettle, dueDate);
			appendValueInTableCol(dueAmountSettle, dueAmount);
			appendValueInTableCol(balanceAmountSettle, balanceAmount);
			appendValueInTableCol(paymentModeSettle, createPaymentModeSelectionSettle(paymentModeList));
			appendValueInTableCol(paymentTypeSettle, createPaymentTypeSelectionSettle(paymentTypeList));
			appendValueInTableCol(paymentTypeExpenseSettle, createPaymentTypeExpenseSettle(tripExpenseList));
			appendValueInTableCol(transactionTypeSettle, createTransactionModeSelectionSettle(TransactionType));
			appendValueInTableCol(transactionIdSettle, createTransactionNo());
			
			appendValueInTableCol(paidDateColSettle, createCheckDateField());
			appendValueInTableCol(paidAmntColSettle, createReceiveAmountFeildSettle(0));
			appendValueInTableCol(referenceSettle, createReference());
		
			appendRowInTable('settleDetails', tableRow);
		
	}else{
		$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No Due Amount To Be Paid Details Found !')
	}
	setPaidDate()
	
	
		/*$('.paidDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
		
		$( function() {
			console.log("setting date")
			$( '.paidDate').datepicker({
				maxDate		: new Date(),
				dateFormat	: 'dd/mm/yyyy',
				autoclose: true
			});
		} );*/
	
	function createPaymentModeSelectionSettle(paymentModeList) {
		
		var paymentModeSel = $('<select id="paymentModeSettle" name="paymentModeSettle" class="form-text col-xs-12" onchange="hideShowAmountOrExpenseDetails(this);"  />');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Mode---'));

		$(paymentModeList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentModeSel;
	}
	
	function createTransactionModeSelectionSettle(TransactionType) {
		
		var transactionModeSel = $('<select id="transactionModeSettle" name="transactionModeSettle" class="form-text hide col-xs-12 fit ar"  />');
		transactionModeSel.append($("<option>").attr('value', 0).text('Select Mode'));

		$(TransactionType).each(function() {
			transactionModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return transactionModeSel;
	}
	
	function createPaymentTypeSelectionSettle(paymentTypeList) {
		
		var paymentModeSel = $('<select id="paymentTypeSettle" name="paymentTypeSettle" class="form-text hide col-xs-12" />');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Type---'));
		
		$(paymentTypeList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentModeSel;
	}
	
	function createPaymentTypeExpenseSettle(tripExpenseList) {
		
		var paymentModeSel = $('<select id="paymentTypeExpenseSettle" name="paymentTypeExpenseSettle" class="form-text col-xs-12" />');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Expense---'));
		
		$(tripExpenseList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.expenseID).text(this.expenseName));
		});
		
		return paymentModeSel;
	}
	
	function createCheckDateField() {
		var checkDateFeild			= $("<input/>", { 
			type		: 'text', 
			id			: 'paidDate', 
			class		: 'form-text paidDate', 
			name		: 'paidDate', 
			placeholder	: 'Paid Date / Txn Date' });

		return checkDateFeild;
	}
	
	function createReference() {
		var referenceFeild			= $("<input/>", { 
			type		: 'text', 
			id			: 'reference', 
			class		: 'form-text reference', 
			name		: 'reference', 
			placeholder	: 'Reference' });

		return referenceFeild;
	}
	
	function removeIcon(id){
		console.log(id);
		var removeFiled = $("<i>",{
			id			: '',
			class 		: 'fa fa-remove fa-2x',
			style		: 'margin-left: 90%;',
			click: function() {$('#tr_'+id).remove();}
		});
		return removeFiled;
	}
	
	$('#addExpense').off('click').on('click', function() {	
		var tableRow2						= createRowInTable('tr_' + temp, 'newRow', 'height: 35px;');
		
		var removeExp						= createColumnInRow(tableRow2, '', '', '', '', '', '6');
		var paymentTypeExpenseSettle2		= createColumnInRow(tableRow2, '', '', '10%', '', '', '');
		var paidDateColSettle2			    = createColumnInRow(tableRow2, '', '', '8%', '', '', '');
		var paidAmntColSettle2 				= createColumnInRow(tableRow2, '', '', '8%', '', '', '');
		var referenceSettle2 				= createColumnInRow(tableRow2, '', '', '8%', '', '', '');
		
		appendValueInTableCol(removeExp,removeIcon(temp));
		appendValueInTableCol(paymentTypeExpenseSettle2, createPaymentTypeExpenseSettle(tripExpenseList));
		appendValueInTableCol(paidDateColSettle2, createCheckDateField());
		
		appendValueInTableCol(paidAmntColSettle2, createReceiveAmountFeildSettle(0));
		appendValueInTableCol(referenceSettle2, createReference());
			
		appendRowInTable('settleDetails', tableRow2);
		setPaidDate();
		temp++;
	});
	
	
	function setPaidDate(){
		$(".paidDate").each(function(){
			$('.paidDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
			
			$( function() {
				$('.paidDate').datepicker({
					maxDate		: new Date(),
					dateFormat	: 'dd/mm/yyyy',
					autoclose: true
				});
			});
		});
	}
}

function createTransactionNo() {
	var referenceFeild			= $("<input/>", { 
		type		: 'text', 
		id			: 'transactionNum', 
		class		: 'form-text fit ar transactionNo', 
		name		: 'transactionNum', 
		placeholder	: 'Transaction No.' });

	return referenceFeild;
}



function hideShowAmountOrExpenseDetails(obj) {
	
	var pmtMode			= $("#paymentModeSettle").val();
	
	if(pmtMode == 1){
		$('.typePayment').removeClass('hide');
		$('#paymentTypeSettle').removeClass('hide');
		$('.transactionType').removeClass('hide');
		$('.transactionNo').removeClass('hide');
		$('#transactionModeSettle').removeClass('hide');
		$('.typeExpense').addClass('hide');
		$('#paymentTypeExpenseSettle').addClass('hide');
		$("#addExpense").addClass('hide');
		$(".newRow").remove();
		
	} else if (pmtMode == 2){
		$('.typeExpense').removeClass('hide');
		$('#paymentTypeExpenseSettle').removeClass('hide');
		$('.transactionType').addClass('hide');
		$('.transactionNo').addClass('hide');
//		$('#paymentTypeExpenseSettle').addClass('hide');
		$('#transactionModeSettle').addClass('hide');
		
		$('.typePayment').addClass('hide');
		$('#paymentTypeSettle').addClass('hide');
		$('#addExpense').removeClass('hide');
		
	} else {
		
		$('.typePayment').addClass('hide');
		$('#paymentTypeSettle').addClass('hide');
		$('.transactionType').addClass('hide');
		$('.transactionNo').addClass('hide');
		$('#transactionModeSettle').addClass('hide');
		$('#transactionType').addClass('hide');
		
		$('.typeExpense').addClass('hide');
		$('#paymentTypeExpenseSettle').addClass('hide');
		$(".newRow").remove();
	}
			
}

function createReceiveAmountFeildSettle(receiveAmount) {
	var isReadOnly		= false;
	
	var receivedAmountFeild		= $("<input/>", { 
					type		: 'text', 
					id			: 'receiveAmtSettle', 
					class		: 'form-text text-right col-xs-8 receiveAmt', 
					name		: 'receiveAmtSettle', 
					value 		: receiveAmount, 
					readonly	: isReadOnly,
					placeholder	: 'Receive Amount',
					onkeypress	: 'return isNumberKeyWithDecimal(event,this.id)',
					onfocus		: 'resetTextFeild(this, 0);',
					});
	
	return receivedAmountFeild;
}

function validateBeforeSaveSettle() {
	
	var pmtMode			= $("#paymentModeSettle").val();
	var pmtType			= $("#paymentTypeSettle").val();
	var pmtExpType		= $("#paymentTypeExpenseSettle").val();
	var pmtAmount		= $('#receiveAmtSettle').val();
	var	amountDue		= $("#amountDue").val();
	var amountBalance   = $("#amountBalance").val();
	var transactionMode = $("#transactionModeSettle").val();
	var totalReceived	= 0;
	 var isValid = true;
	
	if(Number(pmtMode) <= 0){
		showMessage('errors', 'Please Select Payment Mode First !')
		 $("#paymentModeSettle").focus();
		 $("#saveSettlement").show();	
		return false;
	}
	
	if(pmtMode == 1){
		if(Number(pmtType) <= 0){
			showMessage('errors', 'Please Select Payment Type First !')
			 $("#paymentTypeSettle").focus();
			 $("#saveSettlement").show();	
			return false;
		}
	}
	
	if(pmtMode == 1){
		if(Number(transactionMode) <= 0){
			showMessage('errors', 'Please Select Transaction Mode First !')
			 $("#transactionModeSettle").focus();
			 $("#saveSettlement").show();	
			return false;
		}
	}
	
	if(pmtMode == 2){
		$('select[name=paymentTypeExpenseSettle]').each(function(){
			var expenseVal= $(this).val()
			if(Number(expenseVal) <= 0){
				showMessage('errors', 'Please Select Expense Type First !')
				isValid = false; 
				$("#saveSettlement").show();	
				return false;
			}
		
		});
	}
	if(!isValid){
		$("#saveSettlement").show();	
		return isValid
	}
	
	if( (Number(pmtMode) == 1) && (Number(pmtType) == 2) && (Number(pmtAmount) == Number(amountBalance)) ){
		showMessage('errors', 'This payment should be Full payemnt. Please change payment type from Partial to Full !')
		 $("#paymentTypeSettle").focus();
		 $("#saveSettlement").show();	
		return false;
	}
	
	$("input[name=paidDate]").each(function(){
	    if ($(this).val() == null || $(this).val().trim() == '') {
	        $(this).focus(); // Changed from $(this).val().focus() to $(this).focus()
	        isValid = false;
	        $("#saveSettlement").show();	
	        showMessage('errors', 'Please Enter Payment Date !');
	        return false;
	    }
	});

	
	if(!isValid){
		$("#saveSettlement").show();	
		return isValid
	}
	$("input[name=receiveAmtSettle]").each(function(){
		var Amount = parseFloat($(this).val());
		totalReceived +=Amount;
		if(Amount == "" || Amount == undefined || Amount <= 0){
			showMessage('errors', 'Received Amount cannot be less than 0 !');
			isValid = false; 
			$("#saveSettlement").show();	
			return false;
		}
	});	
	
	if(!isValid){
		$("#saveSettlement").show();	
		return isValid
	}

	if(Number(totalReceived) > Number(amountBalance)){
		$("#saveSettlement").show();	
		showMessage('errors', 'Received Amount cannot be greater than '+amountBalance+' !')
		return false;
	}
	return isValid;
}

$(document).ready(
		function($) {
			$('button[id=saveSettlement]').click(function(e) {
				e.preventDefault();
				$("#saveSettlement").hide();
				
				if(!validateBeforeSaveSettle()){
					return false;
				}
				var jsonObject			= new Object();
				
				jsonObject["tripsheetDueAmountId"]    			=  $('#tripsheetDueAmountId').val();
				jsonObject["paymentModeSettle"] 				=  $('#paymentModeSettle').val();
				jsonObject["paymentTypeSettle"] 				=  $('#paymentTypeSettle').val();
				jsonObject["paymentTypeExpenseSettle"] 			=  $('#paymentTypeExpenseSettle').val();
				jsonObject["paidDate"] 							=  $('#paidDate').val();
				jsonObject["receiveAmtSettle"] 					=  $('#receiveAmtSettle').val();
				jsonObject["amountDue"] 						=  $('#amountDue').val();
				jsonObject["amountBalance"] 					=  $('#amountBalance').val();
				jsonObject["tripsheetId"] 						=  $('#tripsheetId').val();
				jsonObject["reference"] 						=  $('#reference').val();
				jsonObject["transactionMode"] 					=  $('#transactionModeSettle').val();
				jsonObject["transactionNo"] 					=  $('#transactionNum').val();
				jsonObject["driverId"]							=  $("#DuedriverId").val();
				jsonObject["validateDoublePost"] 	 			=  true;
				jsonObject["unique-one-time-token"] 			=  $('#accessToken').val();
				
				
				if($('#showbilltypeDropdown').val() == true || $('#showbilltypeDropdown').val() == "true"){
					
					if($('#billselectionId').val()  ==null){
						showMessage('info', ' Not Authourized User to check & payment - Due Amount ! ');
					
						return false;
					}
					if($('#billselectionId').val() =="B_INCOME"){
						jsonObject["billselectionId"]       =  1;
					}else if($('#billselectionId').val() =="E_INCOME"){
						jsonObject["billselectionId"]       =  2;
					}
				}
				
			if($('#paymentModeSettle').val() == 2){	
				var expenseArr   = new Array();
				var paidDateArr	 = new Array();
				var amountArr    = new Array();
				var refArr       = new Array();
				
				$("select[name=paymentTypeExpenseSettle]").each(function(){
					expenseArr.push($(this).val());
				});
				$("input[name=paidDate]").each(function(){
					paidDateArr.push($(this).val());
				});
				$("input[name=receiveAmtSettle]").each(function(){
					amountArr.push($(this).val());
				});
				$("input[name=reference]").each(function(){
					refArr.push($(this).val());
				});
				
				var array	 = new Array();
				for(var i =0 ; i< expenseArr.length; i++){
					var expenseDetails				= new Object();
					expenseDetails.expenseNameId			= expenseArr[i];
					expenseDetails.expenseAmount			= amountArr[i];
					expenseDetails.reference				= refArr[i];
					expenseDetails.padiDate					= paidDateArr[i];
					array.push(expenseDetails);
				}
				jsonObject.expenseArry = JSON.stringify(array);
			 }	
				
				$.ajax({
			             url: "saveDueAmountPaymentDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	  showMessage('success', 'Due Amount Paid Succesfully !');
			            	  $('#accessToken').val(data.accessToken);
			            	  $('#duePayment').modal('hide');
			            	 getDueDetails();
			            	
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
				
			});

});

function getDueDetails(){
	
	var jsonObject			= new Object();

	jsonObject["driverId"] 				=  $('#driverId').val();
	jsonObject["dueAmountRange"] 	  	=  $('#dueAmountRange').val();
	
	    if($('#showbilltypeDropdown').val() == true || $('#showbilltypeDropdown').val() == "true"){
			console.log("inside if user ")
			if($('#billselectionId').val()  ==null){
				showMessage('info', ' Not Authourized User to check & payment - Due Amount ! ');
			
				return false;
			}
			if($('#billselectionId').val() =="B_INCOME"){
				jsonObject["billselectionId"]       =  1;
			}else if($('#billselectionId').val() =="E_INCOME"){
				jsonObject["billselectionId"]       =  2;
			}
		}
	
	showLayer();
	$.ajax({
		url: "getDueAmountReport",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setDueAmountReport(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
}