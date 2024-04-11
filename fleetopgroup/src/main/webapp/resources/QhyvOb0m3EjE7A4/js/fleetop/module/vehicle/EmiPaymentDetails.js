$(document).ready(function() {
	getVehicleWiseEmiPaidList();
	getVehicleWiseEmiPaymentPendingList();
});

function getVehicleWiseEmiPaidList(){
	
	var vid 			= $('#vehicleId').val();
	var vehEmiDetailsId = $('#vehEmiDetailsId').val();
	
	var jsonObject					= new Object();
	
	jsonObject["vid"]				= vid;
	jsonObject["vehEmiDetailsId"]	= vehEmiDetailsId; 
	
	showLayer();
	$.ajax({
		url: "getVehicleWiseEmiPaidList.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			
			setVehicleWiseEmiPaidList(response);
				 hideLayer();
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
}

function setVehicleWiseEmiPaidList(data) {
	
	$("#VendorPaymentTable2").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Account / Bank Name"));
	tr1.append(th3.append("Loan Amount"));
	tr1.append(th4.append("EMI Loan Date"));
	tr1.append(th5.append("Monthly EMI Amount"));
	tr1.append(th6.append("EMI Paid Amount"));
	tr1.append(th7.append("Payment Mode"));
	tr1.append(th8.append("Remark"));
	tr1.append(th9.append("EMI Paid Date"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vehicleEmiPaymentDetailList != undefined && data.vehicleEmiPaymentDetailList.length > 0) {
		$('#top-border-boxshadow').removeClass('hide');
		var vehicleEmiPaymentDetailList = data.vehicleEmiPaymentDetailList;
	
		for(var i = 0; i < vehicleEmiPaymentDetailList.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<th class="fit ar">');
			var td7		= $('<th class="fit ar">');
			var td8		= $('<th class="fit ar">');
			var td9		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(vehicleEmiPaymentDetailList[i].branchName+"_"+vehicleEmiPaymentDetailList[i].accountNumber+" ("+vehicleEmiPaymentDetailList[i].abbreviation+" )"));
			tr1.append(td3.append(vehicleEmiPaymentDetailList[i].loanAmount));
			tr1.append(td4.append(vehicleEmiPaymentDetailList[i].emiLoanDateStr));
			tr1.append(td5.append(vehicleEmiPaymentDetailList[i].monthlyEmiAmount));
			tr1.append(td6.append(vehicleEmiPaymentDetailList[i].emiPaidAmount));
			tr1.append(td7.append(vehicleEmiPaymentDetailList[i].paymentTypeName));
			tr1.append(td8.append(vehicleEmiPaymentDetailList[i].emiPaidRemark));
			tr1.append(td9.append(vehicleEmiPaymentDetailList[i].emiPaidDateStr));

			tbody.append(tr1);
		}
	}else{
		 $('#top-border-boxshadow').addClass('hide');
		//showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable2").append(thead);
	$("#VendorPaymentTable2").append(tbody);
	
}

function getVehicleWiseEmiPaymentPendingList(){
	
	var vid 			= $('#vehicleId').val();
	var vehEmiDetailsId = $('#vehEmiDetailsId').val();
	
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["vid"]				= vid; 
	jsonObject["vehEmiDetailsId"]	= vehEmiDetailsId; 
	
	$.ajax({
		url: "getVehicleWiseEmiPaymentPendingList.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			console.log("response...",response);
			setVehicleWiseEmiPaymentPendingList(response);
				 hideLayer();
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
}

function setVehicleWiseEmiPaymentPendingList(response) {
	var finalList					= response.finalList;
	var paymentTypeList				= response.paymentTypeList;

	if(response.finalList != undefined && response.finalList.length > 0){
		$('#middle-border-boxshadow').removeClass('hide');
		$('#billDetails').empty();
		
		for(var i = 0; i < finalList.length; i++) {

			if(finalList != undefined) {
				
				var srNo					= i + 1;
				var vehicleEmiDetailsId		= finalList[i].vehicleEmiDetailsId;
				var bandAndAccountDetails	= finalList[i].branchName+"_"+finalList[i].accountNumber+" ("+finalList[i].abbreviation+" )";
				var loanAmount				= finalList[i].loanAmount;
				var emiDate					= finalList[i].loanEmiDate;
				var monthlyEmiAmount		= finalList[i].monthlyEmiAmount;
				var balanceAmount			= finalList[i].loanAmount - finalList[i].downPaymentAmount;
				
				var tableRow				= createRowInTable('tr_' + vehicleEmiDetailsId, '', 'height: 35px;');

				var srNoCol					= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var bankName				= createColumnInRow(tableRow, '', '', '10%', '', '', '');
				var loanAmountCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var emiDateCol				= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var monthlyEmiAmntCol		= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var paymentModeCol			= createColumnInRow(tableRow, '', '', '9%', '', '', '');
				var remarkCol				= createColumnInRow(tableRow, '', '', '10%', '', '', '');
				var paidEmiAmntCol 			= createColumnInRow(tableRow, '', '', '8%', '', '', '');
				var hiddenCol				= createColumnInRow(tableRow, '', 'hide', '', '', '', '');
				var hiddenColEmiPayId		= createColumnInRow(tableRow, '', 'hide', '', '', '', '');
				

				appendValueInTableCol(srNoCol, srNo);
				appendValueInTableCol(bankName, bandAndAccountDetails);
				appendValueInTableCol(loanAmountCol, loanAmount);
				appendValueInTableCol(emiDateCol, createEMIDateFeild(emiDate, srNo));
				appendValueInTableCol(monthlyEmiAmntCol, createMonthlyEMIAmountFeild(monthlyEmiAmount, srNo));
				
				appendValueInTableCol(paymentModeCol, createPaymentModeSelection(paymentTypeList, srNo));
				appendValueInTableCol(remarkCol, createRemarkField(srNo));
				appendValueInTableCol(remarkCol, createCheckNumberField(srNo));
				appendValueInTableCol(remarkCol, createCheckDateField(srNo));
				appendValueInTableCol(paidEmiAmntCol, createReceiveAmountFeild(0, srNo));
				
				appendValueInTableCol(hiddenCol, createReceivedAmtLimitFeild(balanceAmount, srNo));
				appendValueInTableCol(hiddenCol, createEmiPaymentIdField(vehicleEmiDetailsId, srNo));
			
				appendRowInTable('billDetails', tableRow);
				
			}
			
		}
		
		$('.chequeDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
		
		$( function() {
			$( '.chequeDate' ).datepicker({
				maxDate		: new Date(),
				dateFormat	: 'dd/mm/yyyy',
				autoclose: true
			});
		} );
		
		
	}else{
		$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No EMI To Be Paid Details Found !')
	}
	
	function createPaymentModeSelection(paymentTypeConstantsList, srNo) {
		
		var paymentModeSel = $('<select id="paymentMode_'+ srNo +'" name="paymentMode_'+ srNo +'" class="form-text col-xs-12" onchange="hideShowChequeDetails('+ srNo +', this);"/>');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Mode---'));

		$(paymentTypeConstantsList).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentModeSel;
	}

}

function hideShowChequeDetails(vehicleEmiDetailsId, obj) {
	
	$('#chequeNumber_'+vehicleEmiDetailsId).removeClass('hide');
	$('#chequeDate_'+vehicleEmiDetailsId).removeClass('hide');
			
}


function createRemarkField(serialNumber) {
	var remarkFeild			= $("<input/>", { 
				type		: 'text', 
				id			: 'remark_' + serialNumber, 
				class		: 'form-text', 
				name		: 'remark_' + serialNumber, 
				placeholder	: 'Remark'});
	
	return remarkFeild;
}

function createCheckNumberField(serialNumber) {
	var checkNumberFeild		= $("<input/>", { 
		type			: 'text', 
		id				: 'chequeNumber_' + serialNumber, 
		class			: 'form-text hide', 
		name			: 'chequeNumber_' + serialNumber, 
		placeholder		: 'Txn No.'});

	return checkNumberFeild;
}

function createBankNameField(serialNumber) {
	var bankNameFeild		= $("<input/>", { 
		type			: 'text', 
		id				: 'bankName_' + serialNumber, 
		class			: 'form-text hide', 
		name			: 'bankName_' + serialNumber, 
		style			: "text-transform : UPPERCASE",
		placeholder		: 'Bank Name'});

	return bankNameFeild;
}

function createCheckDateField(serialNumber) {
	var checkDateFeild			= $("<input/>", { 
		type		: 'text', 
		id			: 'chequeDate_' + serialNumber, 
		class		: 'form-text hide chequeDate', 
		name		: 'chequeDate_' + serialNumber, 
		placeholder	: 'Cheque Date / Txn Date' });

	return checkDateFeild;
}

function createEMIDateFeild(emiDate, serialNumber) {
	var emiDateFeild		= $("<input/>", { 
		type		: 'text', 
		id			: 'emiDate_' + serialNumber, 
		class		: 'form-text col-xs-8 text-right', 
		name		: 'emiDate_' + serialNumber, 
		value 		: emiDate, 
		readonly 	: 'readonly',
		maxlength 	: '7',
		placeholder	: 'Monthly Emi Amont'});
	
	return emiDateFeild;
}

function createMonthlyEMIAmountFeild(monthlyEmiAmount, serialNumber) {
	var balanceAmountFeild		= $("<input/>", { 
					type		: 'text', 
					id			: 'monthlyEmiAmount_' + serialNumber, 
					class		: 'form-text col-xs-8 text-right', 
					name		: 'monthlyEmiAmount_' + serialNumber, 
					value 		: monthlyEmiAmount, 
					readonly 	: 'readonly',
					maxlength 	: '7',
					placeholder	: 'Monthly Emi Amont'});
	
	return balanceAmountFeild;
}

function createEmiPaymentIdField(vehicleEmiDetailsId, serialNumber) {
	var balanceAmountFeild		= $("<input/>", { 
		type		: 'text', 
		id			: 'vehicleEmiDetailsId_' + serialNumber, 
		class		: 'form-text col-xs-8 text-right', 
		name		: 'vehicleEmiDetailsId_' + serialNumber, 
		value 		: vehicleEmiDetailsId, 
		readonly 	: 'readonly',
		maxlength 	: '7',
		placeholder	: 'Monthly Emi Amont'});
	
	return balanceAmountFeild;
}


function createReceiveAmountFeild(receiveAmount, serialNumber) {
	var isReadOnly		= false;
	
	var receivedAmountFeild		= $("<input/>", { 
					type		: 'text', 
					id			: 'receiveAmt_' + serialNumber, 
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

function createReceivedAmtLimitFeild(receivedAmtLimit, serialNumber) {
	var receivedAmtLimitFeild	= $("<input/>", { 
					type		: 'hidden', 
					id			: 'receivedAmtLimit_' + serialNumber, 
					class		: 'form-control', 
					name		: 'receivedAmtLimit_' + serialNumber, 
					value 		: receivedAmtLimit, 
					placeholder	: 'Remark'});
	
	return receivedAmtLimitFeild;
}

function validateReceiveAmount(obj) {
	
	var objName 		= obj.id;
	var objVal			= 0;
	var splitVal 		= objName.split("_");
	var serialNumber	= splitVal[1];
	
	if(!validateBeforeSave(obj)) {
		$('#receiveAmt_' + serialNumber).val(0);
		return false;
	}
	
}

function validateBeforeSave(obj) {

	var objName				= obj.id;
	var mySplitResult 		= objName.split("_");
	var serialNumber		= mySplitResult[1];
	
	var receiveAmount		= $("#receiveAmt_" + serialNumber).val();
	var paymentMode			= $("#paymentMode_" + serialNumber).val();
	var receiveLimit		= $('#receivedAmtLimit_' + serialNumber).val();
	
	if(Number(paymentMode) <= 0){
		
		showMessage('errors', 'Please Select Payment Mode First !')
		 $("#paymentMode_" + serialNumber).focus();
		return false;
		
	}else if(Number(paymentMode) != 1){
		
		if($('#chequeNumber_' + serialNumber).val() == null || $('#chequeNumber_' + serialNumber).val().trim() == ''){
			$('#chequeNumber_' + serialNumber).focus();
			showMessage('errors', 'Please enter '+getPaymentModeName(Number(paymentMode))+' number !')
			return false;
		}
		
		if($('#chequeDate_' + serialNumber).val() == null || $('#chequeDate_' + serialNumber).val().trim() == ''){
			$('#chequeDate_' + serialNumber).focus();
			showMessage('errors', 'Please enter '+getPaymentModeName(Number(paymentMode))+' Payment Date !')
			return false;
		}
	}
	
	if(Number(receiveAmount) > Number(receiveLimit)){
		showMessage('info', 'Received Amount cannot be greater than '+receiveLimit+' !')
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
		   name = 'NEFT';
	    break;
	  case 3:
		   name = 'RTGS';
	    break;
	  case 4:
		   name = 'IMPS';
	    break;
	  case 5:
		   name = 'DD';
	    break;
	  case 6:
		   name = 'CHEQUE';
	    break;
	  case 7:
		   name = 'BANK DRAFT';
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
				console.log('saving payment details.........')
				
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
				
				var vehicleEmiDetailsIdArr 	= new Array();
				var paymentModeArr			= new Array();
				var remarkArr				= new Array();
				var txnNoArr				= new Array();
				var txnDateArr				= new Array();
				var emiDateArr				= new Array();
				var monthlyEmiAmountArr		= new Array();
				var receiveAmtArr 			= new Array();

				var vid = $('#vehicleId').val();
				
				$("input[name=receiveAmt]").each(function(){
					
					var selectId 			= this.id;
					var mySplitResult 		= selectId.split("_");
					var srNo				= mySplitResult[1];
					var receiveAmt			= Number($('#receiveAmt_'+srNo).val());
					
					if(receiveAmt > 0){
						
						vehicleEmiDetailsIdArr.push($("#vehicleEmiDetailsId_" + srNo).val());
						paymentModeArr.push($("#paymentMode_" + srNo).val());
						remarkArr.push($("#remark_" + srNo).val());
						txnNoArr.push($("#chequeNumber_" + srNo).val());
						txnDateArr.push($("#chequeDate_" + srNo).val());
						emiDateArr.push($("#emiDate_" + srNo).val());
						monthlyEmiAmountArr.push($("#monthlyEmiAmount_" + srNo).val());
						receiveAmtArr.push(receiveAmt);
					}
					
				});
				
				var array	 = new Array();
				
				for(var i =0 ; i< receiveAmtArr.length; i++){
					var vehicleEmiDetails	= new Object();
					
					vehicleEmiDetails.vehicleEmiDetailsId	= vehicleEmiDetailsIdArr[i];
					vehicleEmiDetails.paymentMode			= paymentModeArr[i];
					vehicleEmiDetails.remark				= remarkArr[i];
					vehicleEmiDetails.txnNo					= txnNoArr[i];
					vehicleEmiDetails.txnDate				= txnDateArr[i];
					vehicleEmiDetails.emiLoanDate			= emiDateArr[i];
					vehicleEmiDetails.monthlyEmiAmount		= monthlyEmiAmountArr[i];
					vehicleEmiDetails.receiveAmt			= receiveAmtArr[i];
					
					array.push(vehicleEmiDetails);
					
				}
				
				jsonObject.vid = vid;
				jsonObject.emiPaymentDetails = JSON.stringify(array);
				
				showLayer();
				$.ajax({
			             url: "saveVehicleEmiPaymentDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	if(data.emiPaidSuccessfully) {
			            		 //window.location.replace("emiPaymentDetails.in?vid="+vid);
			            		getVehicleWiseEmiPaidList();
			            		getVehicleWiseEmiPaymentPendingList();
			            	}
			            	 hideLayer();
			            	 showMessage('success', 'Emi Paid Succesfully !');
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});
			
		});

function preEmiSettle(){
	
	var vid 			= $('#vehicleId').val();
	var vehEmiDetailsId = $('#vehEmiDetailsId').val();
	
	var jsonObject					= new Object();
	
	jsonObject["vid"]				= vid;
	jsonObject["vehEmiDetailsId"]	= vehEmiDetailsId; 
	
	showLayer();
	$.ajax({
		url: "getPreEmiSettlementDetail.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			console.log("settle",response)
			setPreEmiSettlementDetail(response);
			hideLayer();
			$('#preEmiSettlement').modal('show');	 
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
}

function setPreEmiSettlementDetail(response) {
	var vehicleEmiDetail			= response.vehicleEmiDetail;
	var paymentTypeListSettle		= response.paymentTypeList;
	
	if(response.vehicleEmiDetail != undefined){
		
		$('#settle-border-boxshadow').removeClass('hide');
		$('#settleDetails').empty();
				
			var srNoSettle						= 1;
			var vehicleEmiDetailsIdSettle		= vehicleEmiDetail.vehicleEmiDetailsId;
			var bandAndAccountDetailsSettle		= vehicleEmiDetail.branchName+"_"+vehicleEmiDetail.accountNumber+" ("+vehicleEmiDetail.abbreviation+" )";
			var loanAmountSettle				= vehicleEmiDetail.loanAmount;
			var downPaymentAmountSettle			= vehicleEmiDetail.downPaymentAmount;
			var monthlyEmiAmountSettle			= vehicleEmiDetail.monthlyEmiAmount;
			var balanceAmountSettle				= vehicleEmiDetail.loanAmount - vehicleEmiDetail.downPaymentAmount;
			
			var tableRow						= createRowInTable('tr_' + vehicleEmiDetailsIdSettle, '', 'height: 35px;');

			var srNoColSettle					= createColumnInRow(tableRow, '', '', '2%', '', '', '');
			var bankNameSettle					= createColumnInRow(tableRow, '', '', '10%', '', '', '');
			var loanAmountColSettle				= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var downPaymentAmntColSettle		= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var monthlyEmiAmntColSettle			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
			var paymentModeColSettle			= createColumnInRow(tableRow, '', '', '9%', '', '', '');
			var remarkColSettle					= createColumnInRow(tableRow, '', '', '10%', '', '', '');
			var paidEmiAmntColSettle 			= createColumnInRow(tableRow, '', '', '8%', '', '', '');
			var hiddenColSettle					= createColumnInRow(tableRow, '', 'hide', '', '', '', '');
			var hiddenColEmiPayIdSettle			= createColumnInRow(tableRow, '', 'hide', '', '', '', '');

			appendValueInTableCol(srNoColSettle, srNoSettle);
			appendValueInTableCol(bankNameSettle, bandAndAccountDetailsSettle);
			appendValueInTableCol(loanAmountColSettle, loanAmountSettle);
			appendValueInTableCol(downPaymentAmntColSettle, downPaymentAmountSettle);
			appendValueInTableCol(monthlyEmiAmntColSettle, createMonthlyEMIAmountFeildSettle(monthlyEmiAmountSettle));
			
			appendValueInTableCol(paymentModeColSettle, createPaymentModeSelectionSettle(paymentTypeListSettle));
			appendValueInTableCol(remarkColSettle, createRemarkFieldSettle());
			appendValueInTableCol(remarkColSettle, createCheckNumberFieldSettle());
			appendValueInTableCol(remarkColSettle, createCheckDateFieldSettle());
			appendValueInTableCol(paidEmiAmntColSettle, createReceiveAmountFeildSettle(0));
			
			appendValueInTableCol(hiddenColSettle, createReceivedAmtLimitFeildSettle(balanceAmountSettle));
			appendValueInTableCol(hiddenColEmiPayIdSettle, createEmiPaymentIdFieldSettle(vehicleEmiDetailsIdSettle));
		
			appendRowInTable('settleDetails', tableRow);
		
		$('.chequeDate').val(dateWithDateFormatForCalender(new Date(), "/")); //dateFormatForCalender defined in genericfunctions.js file
		
		$( function() {
			$( '.chequeDate' ).datepicker({
				maxDate		: new Date(),
				dateFormat	: 'dd/mm/yyyy',
				autoclose: true
			});
		});
		
	}else{
		/*$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No EMI To Be Paid Details Found !')*/
	}
	
	function createPaymentModeSelectionSettle(paymentTypeListSettle) {
		
		var paymentModeSel = $('<select id="paymentModeSettle" name="paymentModeSettle" class="form-text col-xs-12" onchange="hideShowChequeDetailsSettle(this);"/>');
		paymentModeSel.append($("<option>").attr('value', 0).text('---Select Mode---'));

		$(paymentTypeListSettle).each(function() {
			paymentModeSel.append($("<option>").attr('value', this.paymentTypeId).text(this.paymentTypeName));
		});
		
		return paymentModeSel;
	}

}

function hideShowChequeDetailsSettle(obj) {
	
	$('#chequeNumberSettle').removeClass('hide');
	$('#chequeDateSettle').removeClass('hide');
			
}

function createMonthlyEMIAmountFeildSettle(monthlyEmiAmountSettle) {
	var monthlyEMI		= $("<input/>", { 
					type		: 'text', 
					id			: 'monthlyEmiAmountSettle', 
					class		: 'form-text col-xs-8 text-right', 
					name		: 'monthlyEmiAmountSettle', 
					value 		: monthlyEmiAmountSettle, 
					readonly 	: 'readonly',
					maxlength 	: '7',
					placeholder	: 'Monthly Emi Amont'});
	
	return monthlyEMI;
}

function createRemarkFieldSettle() {
	var remarkFeild			= $("<input/>", { 
				type		: 'text', 
				id			: 'remarkSettle', 
				class		: 'form-text', 
				name		: 'remarkSettle', 
				placeholder	: 'Remark'});
	
	return remarkFeild;
}

function createCheckNumberFieldSettle() {
	var checkNumberFeild		= $("<input/>", { 
		type			: 'text', 
		id				: 'chequeNumberSettle', 
		class			: 'form-text hide', 
		name			: 'chequeNumberSettle', 
		placeholder		: 'Txn No.'});

	return checkNumberFeild;
}

function createCheckDateFieldSettle() {
	var checkDateFeild			= $("<input/>", { 
		type		: 'text', 
		id			: 'chequeDateSettle', 
		class		: 'form-text hide chequeDate', 
		name		: 'chequeDateSettle', 
		placeholder	: 'Cheque Date / Txn Date' });

	return checkDateFeild;
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
					onfocus		: 'resetTextFeild(this, 0);',
					onblur 		: "resetTextFeild(this, 0);clearIfNotNumeric(this, 0);validateReceiveAmountSettle(this);",
					onkeypress 	: "return noNumbers(event ,this);",
					onkeyup 	: "validateReceiveAmountSettle(this);"});
	
	return receivedAmountFeild;
}

function createReceivedAmtLimitFeildSettle(receivedAmtLimit) {
	var receivedAmtLimitFeild	= $("<input/>", { 
					type		: 'hidden', 
					id			: 'receivedAmtLimitSettle', 
					class		: 'form-control', 
					name		: 'receivedAmtLimitSettle', 
					value 		: receivedAmtLimit, 
					placeholder	: 'Remark'});
	
	return receivedAmtLimitFeild;
}

function createEmiPaymentIdFieldSettle(vehicleEmiDetailsIdSettle) {
	var balanceAmountFeild		= $("<input/>", { 
		type		: 'text', 
		id			: 'vehicleEmiDetailsIdSettlement', 
		class		: 'form-text col-xs-8 text-right', 
		name		: 'vehicleEmiDetailsIdSettlement', 
		value 		: vehicleEmiDetailsIdSettle, 
		readonly 	: 'readonly',
		maxlength 	: '7',
		placeholder	: 'Monthly Emi Amont'});
	
	return balanceAmountFeild;
}

function validateReceiveAmountSettle(obj) {
	
	if(!validateBeforeSaveSettle()) {
		$('#receiveAmtSettle').val(0);
		return false;
	}
	
}

function validateBeforeSaveSettle() {
	
	var receiveAmount		= $("#receiveAmtSettle").val();
	var paymentMode			= $("#paymentModeSettle").val();
	var receiveLimit		= $('#receivedAmtLimitSettle').val();
	
	if(Number(paymentMode) <= 0){
		
		showMessage('errors', 'Please Select Payment Mode First !')
		 $("#paymentModeSettle").focus();
		return false;
		
	}else if(Number(paymentMode) != 1){
		
		if($('#chequeNumberSettle').val() == null || $('#chequeNumberSettle').val().trim() == ''){
			$('#chequeNumberSettle').focus();
			showMessage('errors', 'Please Enter '+getPaymentModeNameSettle(Number(paymentMode))+' Number !')
			return false;
		}
		
		if($('#chequeDateSettle').val() == null || $('#chequeDateSettle').val().trim() == ''){
			$('#chequeDateSettle').focus();
			showMessage('errors', 'Please Enter '+getPaymentModeNameSettle(Number(paymentMode))+' Payment Date !')
			return false;
		}
	}
	
	if(Number(receiveAmount) > Number(receiveLimit)){
		showMessage('info', 'Received Amount cannot be greater than '+receiveLimit+' !')
		return false;
	}
	
	return true;
}

function getPaymentModeNameSettle(paymentMode){
	var name = '';
	switch(paymentMode) {
	  case 1:
		  name = 'CASH'
	    break;
	  case 2:
		   name = 'NEFT';
	    break;
	  case 3:
		   name = 'RTGS';
	    break;
	  case 4:
		   name = 'IMPS';
	    break;
	  case 5:
		   name = 'DD';
	    break;
	  case 6:
		   name = 'CHEQUE';
	    break;
	  case 7:
		   name = 'BANK DRAFT';
	    break;
	 
	  default:
	}
	
	return name;
}

$(document).ready(
		function($) {
			$('button[id=saveSettlement]').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();
				
				jsonObject["vehicleId"]    						=  $('#vehicleId').val();
				jsonObject["vehicleEmiDetailsIdSettlement"] 	=  $('#vehicleEmiDetailsIdSettlement').val();
				jsonObject["paymentModeSettle"] 				=  $('#paymentModeSettle').val();
				jsonObject["remarkSettle"] 						=  $('#remarkSettle').val();
				jsonObject["chequeNumberSettle"] 				=  $('#chequeNumberSettle').val();
				jsonObject["chequeDateSettle"] 					=  $('#chequeDateSettle').val();
				jsonObject["monthlyEmiAmountSettle"] 			=  $('#monthlyEmiAmountSettle').val();
				jsonObject["receiveAmtSettle"] 					=  $('#receiveAmtSettle').val();
				
				if(!validateBeforeSaveSettle()){
					showMessage('errors', 'Please Enter All Required Fields !');
					return false;
				}
				
				if(Number($('#receiveAmtSettle').val()) <= 0){
					showMessage('errors', 'Received Amount should be greater than 0 !')
					return false;
				}
				 
				showLayer();
				$.ajax({
			             url: "savePreEmiSettlementDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	 $('#preEmiSettlement').modal('hide');
			            	 getVehicleWiseEmiPaidList();
			            	 getVehicleWiseEmiPaymentPendingList();
			            	 hideLayer();
			            	 
			            	 showMessage('success', 'Pre Emi Settlement Paid Succesfully !');
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
				
			});

});
