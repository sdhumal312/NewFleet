function IsVOUCHER(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        i = n > 47 && 58 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVOUCHER").innerHTML = "Special Characters allowed", document.getElementById("errorVOUCHER").style.display = i ? "none" : "inline", i
}

function IsPAIDRECEIVED(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode;
    t > 31 && 33 > t || t > 39 && 42 > t || t >= 45 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && (e.charCode, e.keyCode)
}

function IsPAIDBY(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode;
    t > 31 && 33 > t || t > 39 && 42 > t || t >= 45 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && (e.charCode, e.keyCode)
}

function isNumberKey(evt)
{	
	console.log("inside function")
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function click_Debit() {
    document.getElementById("HideDibit").style.display = "block";
    $("#EnterPayment").html('\x3c!-- payment Tyre --\x3e\t\t\t\t\t\t\t\t\t\t\t\t<input type="hidden" name="PAYMENT_TYPE_ID"\t\t\t\t\t\t\t\t\t\t\t\t\trequired="required" value="1">'), $("#paid").html('Paid To : <abbr title="required">*</abbr>'), document.getElementById("paymentDebit").style.display = "block", $("#paymentCredit").html(""), document.getElementById("Credit").className += " disabled"
}

function click_Credit() {
    document.getElementById("HideDibit").style.display = "block";
    $("#EnterPayment").html('\x3c!-- payment Tyre --\x3e\t\t\t\t\t\t\t\t\t\t\t\t<input type="hidden" name="PAYMENT_TYPE_ID"\t\t\t\t\t\t\t\t\t\t\t\t\trequired="required" value="2">'), $("#paid").html('Received From :<abbr title="required">*</abbr>'), document.getElementById("paymentCredit").style.display = "block", $("#paymentDebit").html(""), document.getElementById("Debit").className += " disabled"
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
}), $(document).ready(function() {
    $("#NatureDebitPayment").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getCashExpense.in?Action=FuncionarioSelect2",
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
                        return {
                            text: e.expenseName,
                            slug: e.slug,
                            id: e.expenseID
                        }
                    })
                }
            }
        }
    }), $("#NatureCreditPayment").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getCashIncome.in?Action=FuncionarioSelect2",
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
                        return {
                            text: e.incomeName,
                            slug: e.slug,
                            id: e.incomeID
                        }
                    })
                }
            }
        }
    }), $(document).ready(function() {
        $("#CashBookNumber").select2(), $("#tagPicker").select2({
            closeOnSelect: !1
        }),$("#CashBookNumber").on("change", function() {
        	 getCashBookVoucherNumber($("#CashBookNumber").val());
        }),$('#voucherType').on('change', function () {
        	toggleVoucherType();
        });
        $('#voucherType').bootstrapToggle('on')
        getCashBookVoucherNumber($("#CashBookNumber").val());
    })
});

function getCashBookVoucherNumber(cashBookId){
	var jsonObject				= new Object();
	jsonObject["cashBookId"] 	=  cashBookId;
	var isAutoVoucherNumber = $('#isAutoVoucherNumber').val();
	if(isAutoVoucherNumber == 'true' || isAutoVoucherNumber == true){
		if($('#voucherType').prop("checked")){
			showLayer();
			$.ajax({
				url: "getCashBookVoucherNumber",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					$('#CASH_VOUCHER_NO').val(data.voucherNumber);
					$('#CASH_VOUCHER_NO').prop('readonly', true);
					hideLayer();
				},
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});
		}
	}
	
}
function toggleVoucherType(){
	var auto = $('#voucherType').prop("checked");
	if(!auto){
		 $('#CASH_VOUCHER_NO').val('');
		 $('#CASH_VOUCHER_NO').prop('readonly', false);
		 $('#voucherType').val(false);
	}else{
		$('#voucherType').val(true);
		getCashBookVoucherNumber($("#CashBookNumber").val());
	}
}