var PAYMENT_TYPE_CASH = 1;
var PAYMENT_TYPE_CREDIT = 2;
var PAYMENT_TYPE_NEFT = 3;
var PAYMENT_TYPE_RTGS = 4;
var PAYMENT_TYPE_IMPS = 5;
var PAYMENT_TYPE_DD = 6;
var PAYMENT_TYPE_CHEQUE = 7;
var PAYMENT_TYPE_BANK_DRAFT = 8;
var PAYMENT_TYPE_COD = 9;
var PAYMENT_TYPE_ON_ACCOUNT = 10;
var PAYMENT_TYPE_UPI = 11;
var PAYMENT_TYPE_CARD = 12;
let allowBankPaymentDetails = false;
let validateBankPaymentDetails = false;
let buttonCreated = false;

let transactionArray = [PAYMENT_TYPE_NEFT,PAYMENT_TYPE_RTGS,PAYMENT_TYPE_IMPS,PAYMENT_TYPE_DD,PAYMENT_TYPE_CHEQUE,PAYMENT_TYPE_BANK_DRAFT,PAYMENT_TYPE_UPI,PAYMENT_TYPE_CARD];


$(document).ready(function(){
		$.ajax({
		url : "CompanyRestControllerWS/getCompanyConfiguration",
		type : "GET",
		success : function(data){
			if(data != null ){
			if(data.allowBankPaymentDetails != undefined){
			allowBankPaymentDetails = data.allowBankPaymentDetails;
				$('#allowBankPaymentDetails').val(allowBankPaymentDetails);	
				if(allowBankPaymentDetails){
					var dateStr = convertDate(new Date());
					$('#bankTransactionDate').val(dateStr);
					$('#chequeTransactionDate').val(dateStr);
					try {
						prepareSelectTwos();
					} catch (e) {
						console.log("error :", e);
					}
					try{
						appendLinkOnViewPage();	
					}catch(e){
						console.log("error :",e);
					}
				}
			}
			if(data.allowBankPaymentDetails != null && data.allowBankPaymentDetails != undefined)
			validateBankPaymentDetails =data.validateBankPaymentDetails;
			}
		}
	});

})
function prepareSelectTwos(){
 $.getJSON("getAllBankNameist", {
         ajax: "true"
     }, function(a) {
         for (var b = '<option value="0"> </option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].bankId + '">' + a[d].bankName + "</option>";
         b += "</option>", $("#partyBankId").html(b)
     });
	
	$("#bankAccountId").select2({
		minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	    url:"getBankAccountList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	        return {
	            term: a
	        }
	    }
	    , results:function(a) {
	        return {
	            results:$.map(a, function(a) {
	                return {
	                    text: a.name+" "+a.accountNumber+" ( "+a.bankName+" ) ",
	                    slug: a.slug, 
	                    id: a.bankAccountId
	                }
	            }
	            )
	        }
	    }
	}
	});
}

$('#renPT_option,#renPT_option1,#paymentTypeId,#Approval_option,#terms').change(function() {
	if (allowBankPaymentDetails) {
		reset();
		if (transactionArray.includes(Number($(this).val()))) {
			getPaymentModalByPaymentType($(this).val());
			$('#paymentDetails').modal('show');
			if(!buttonCreated){
			var test = $('<button/>', {
				id : 'bankPaymentDetailsButton',
				type: 'button',
				text: 'View Details',
				class: 'btn btn-primary',
				click: function(){showPaymentModal()}
			});
			$('#paymentDiv').append(test);
			buttonCreated = true;
			}
		}else{
			reset();
			$('#bankPaymentTypeId').val($(this).val());
			buttonCreated = false;
			$('#bankPaymentDetailsButton').remove();
		}
	}
});

$('#addButton').click(function(){
	return validateBankPayment($('#bankPaymentTypeId').val());
});

function getPaymentModalByPaymentType(paymentTypeId) {
	$('#bankPaymentTypeId').val(paymentTypeId);
	if (paymentTypeId == PAYMENT_TYPE_NEFT || paymentTypeId == PAYMENT_TYPE_RTGS || paymentTypeId == PAYMENT_TYPE_IMPS) {
		$('#upiDiv').hide();
		$('#chequeDiv').hide();
	} else if (paymentTypeId == PAYMENT_TYPE_UPI) {
		$('#upiDiv').show();
		$('#chequeDiv').hide();
	} else if (paymentTypeId == PAYMENT_TYPE_DD || paymentTypeId == PAYMENT_TYPE_CHEQUE || paymentTypeId == PAYMENT_TYPE_BANK_DRAFT || paymentTypeId ==  PAYMENT_TYPE_CARD) {
		$('#chequeDiv').show();
		$('#upiDiv').hide();
	}
}


function reset(){
$('#bankPaymentTypeId').val(0);
$('#bankTransactionNumber').val('');	
$('#payerName').val('');
$('#partyAccountNumber').val('');	
$('#chequeGivenBy').val('');	
$('#upiId').val('');	
$('#mobileNumber').val('');	
$('#bankAccountId').select2('val','');
$("#partyBankId").val('');
}

function getPaymentDetails(moduleId, moduleIdentifier, fromShow) {
	if (allowBankPaymentDetails) {
		var object = new Object();
		object.moduleId = moduleId;
		object.moduleIdentifier = moduleIdentifier;
		$.ajax({
			url: "getBankPaymentDetails",
			type: "POST",
			dataType: "json",
			data: object,
			success: function(data) {
				setPaymentDetails(data, fromShow);
			}, error: function(e) {
				console.log("e ", e);
			}
		});
	}
}

function setPaymentDetails(data,fromShow){
	if(data != null && data!= undefined && transactionArray.includes(Number(data.bankPaymentTypeId))){
	getPaymentModalByPaymentType(data.bankPaymentTypeId);
	$('#bankAccountId').select2('data',{id : data.bankAccountId, text:data.bankAccountNumber});
	$('#bankTransactionNumber').val(data.transactionNumber);
	$('#payerName').val(data.payerName);
	$('#bankTransactionDate').val(data.transactionDateStr);
	$('#partyAccountNumber').val(data.partyAccountNumber);
	$('#chequeGivenBy').val(data.chequeGivenBy);
	$('#upiId').val(data.upiId);
	$('#mobileNumber').val(data.mobileNumber);
	$('#partyBankId').val(data.partyBankId);
	$('#chequeTransactionDate').val(data.chequeTransactionDateStr)
	
	if(fromShow){
	$('#addButton').hide();
	disableAllFields();
	$('#paymentDetails').modal('show');
	}
	}else{
		showMessage("info","Payment Details Not found !! ");
	}
}
function disableAllFields(){
	$('#bankTransactionNumber').prop("readonly",true);
	$('#payerName').prop("readonly",true);
	$('#upiId').prop("readonly",true);
	$('#mobileNumber').prop("readonly",true);
	$('#partyAccountNumber').prop("readonly",true);
	$('#chequeGivenBy').prop("readonly",true);
	$('#bankTransactionDate').prop("readonly",true);
	$('#chequeTransactionDate').prop("readonly",true);
	$("#bankAccountId").select2('readonly',true);
	$('#partyBankId').prop('disabled', true);  
}
function prepareObject(jsonObject){
	var object = new Object();
	object.transactionNumber = $('#bankTransactionNumber').val();
	object.payerName = $('#payerName').val();
	object.upiId = $('#upiId').val();
	object.mobileNumber = $('#mobileNumber').val();
	object.partyAccountNumber = $('#partyAccountNumber').val();
	object.chequeGivenBy = $('#chequeGivenBy').val();
	object.transactionDate = $('#bankTransactionDate').val();
	object.bankAccountId = $('#bankAccountId').val();
	object.partyBankId = $('#partyBankId').val();
	object.chequeTransactionDate = $('#chequeTransactionDate').val();
	
	jsonObject.bankPaymentTypeId = $('#bankPaymentTypeId').val();
	jsonObject.allowBankPaymentDetails = allowBankPaymentDetails;
	jsonObject.bankPaymentDetails = JSON.stringify(object);
	return jsonObject;
}

function validateBankPayment(paymentTypeId) {
	if (Number(paymentTypeId) > 0) {
		if (validateBankPaymentDetails && transactionArray.includes(Number(paymentTypeId))) {
			if (Number($('#bankAccountId').val()) <= 0) {
				showMessage('info', 'select bank account to process');
				hideLayer();
				showPaymentModal();
				return false;
			}
			if ($('#bankTransactionNumber').val() == undefined || $('#bankTransactionNumber').val() == '') {
				$('#bankTransactionNumber').focus()
				showMessage('info', 'Enter transaction Number to process');
				hideLayer();
				showPaymentModal();
				return false;
			}
			if ($('#bankTransactionDate').val() == '') {
				showMessage('info', 'Enter paid Date to process');
				hideLayer();
				showPaymentModal();
				return false;
			}
		}
	}
	return true;
}

function showPaymentModal() {
	$('#paymentDetails').modal('show');
}

function setPaymentDetailsLink(moduleId, moduleIdentifier,paymentType){
	if(transactionArray.includes(paymentType)){
	setTimeout(function(){
		if(allowBankPaymentDetails)
	$('#paymentTypeSpan').wrap('<a href="javascript:void(0)" onclick="getPaymentDetails('+moduleId+','+moduleIdentifier+',true)" />');
	},800)		
	}
}

function appendLinkOnViewPage() {
	setTimeout(function() {
		let paymentType = Number($('#modulePaymentTypeId').val());
		if (transactionArray.includes(paymentType)) {
			let moduleId = Number($('#moduleId').val());
			let moduleIdentifier = Number($('#moduleIdentifier').val());
			if (paymentType > 0) {
				$('#paymentTypeSpan').wrap('<a href="javascript:void(0)" onclick="getPaymentDetails(' + moduleId + ',' + moduleIdentifier + ',true)" />');
			}
		}
	}, 800)
}

function convertDate(inputFormat) {
  function pad(s) { return (s < 10) ? '0' + s : s; }
  var d = new Date(inputFormat)
  return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('-')
}
