var e = 25,
t = $(".input_fields_wrap"),
c = $(".add_field_button"),
n = 1;
$(document).ready(function() {
    $(c).click(function(c) {
    	 if($("#config").val() == true || $("#config").val() == 'true' ){
    		 addFixRateIncome();
    	 }else{
    		 addIncome();
    	 }
    }), $(t).on("click", ".remove_field", function(e) {
    	removeIncome();
    }), $.getJSON("tripIncomeList.in", function(e) {
        $("#Income").empty(), $("#Income").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Income").append($("<option>").text(t.incomeName).attr("value", t.incomeID))
        })
    }),$.getJSON("getVehicleTripRoute.in", function(e) {
        $("#route").empty(), $("#route").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#route").append($("<option>").text(t.routeName).attr("value", t.routeID))
        })
    })
});

function addIncome(){
		e > n
        n++;
        $(t).append('<div><div class="row1"><div class="col-md-3"><select class="form-text select2" name="incomeName" id="task' + n + '" required="required"></select></div><div class="col-md-2"><input type="number" class="form-text" name="Amount" id="Amount' + n + '" min="0"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"  required="required" placeholder="Amount"></div><div class="col-md-2"><input type="text" class="form-text" name="incomeRefence" value="0" placeholder="Reference"></div> <div class="col-md-2"><input type="checkbox" value="0" id="excludeNo'+n+'" name="isDriverExcluded" class="form-check-input"> Exclude</div> </div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("tripIncomeList.in", function(e) {
            $("#task" + n).empty(), $("#task" + n).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#task" + n).append($("<option>").text(t.incomeName).attr("value", t.incomeID))
            })
        });
        $(".select2").select2()
}

function removeIncome(){
	$(".remove_field").each(function(i){
		if(n - 2 == i)
		$(this).parent("div").remove(), n--;
		
	});
}

var allowShortCut = $('#allowShortCut').val();

if(allowShortCut == 'true'){
	document.addEventListener('keyup', doc_keyUp, false); 	// register the handler 

	function doc_keyUp(e){
		if(e.altKey && e.which == 78) {						// this would test for whichever key is 78(N) and the alt key at the same time
			if(allowShortCut){
				addIncome();
				return false;
			}
		}
		else if(e.altKey && e.which == 82){
			if(allowShortCut){
				removeIncome();
				return false;
			}
		}

	}
} else {
	document.removeEventListener("keyup", doc_keyUp); 
}

$(document).ready(function(){
	getIncomeDetails();
});

function getIncomeDetails(){
	$("#Amount").val(0);
	$("#netAmount").val(0);
	
	var jsonObject							= new Object();

	jsonObject["incomeID"]					= $("#Income").val();
if($("#Income").val() > 0){
	$.ajax({
		url: "getTripIncomeById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.tripIncome.gst != null && data.tripIncome.commission != null ){
				$("#gst").val(data.tripIncome.gst);
				$("#commission").val(data.tripIncome.commission);
				$("#fixRate").val(data.tripIncome.gst + data.tripIncome.commission);
			}else{
				$("#gst").val(0.0);
				$("#commission").val(0.0);
				$("#fixRate").val(0.0);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}	


}
function getMulIncomeDetails(id){
	$("#Amount"+id).val(0);
	$("#netAmount"+id).val(0);
	
	var jsonObject							= new Object();
	jsonObject["incomeID"]					= $("#task"+id).val();
		
	$.ajax({
		url: "getTripIncomeById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.tripIncome.gst != null || data.tripIncome.gst > 0 ){
				$("#gst"+id).val(data.tripIncome.gst);
				$("#commission"+id).val(data.tripIncome.commission);
				$("#fixRate"+id).val(data.tripIncome.gst + data.tripIncome.commission);
			}else{
				$("#gst"+id).val(0.0);
				$("#commission"+id).val(0.0);
				$("#fixRate"+id).val(0.0);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function calculateAmount(obj){
	var id 			= obj.id ;
	var amount		= $("#"+id).val()
	var tax			= $("#fixRate").val();
	var rate 		= amount * tax/100;
	var finalAmount	= amount - rate;
	
	 $("#netAmount").val(finalAmount);
	
}
function calculateMulAmount(obj,num){
	var id 				= obj.id ;
	var amount			= $("#"+id).val()
	var tax				= $("#fixRate"+num).val();
	var rate 			= amount * tax/100;
	var finalAmount		= amount - rate;
	
	$("#netAmount"+num).val(finalAmount);
	
}


function addFixRateIncome(){
	e > n
    n++;
    $(t).append('<div class="panel">'
    				+'<div class="row">'
    					+'<label class="L-size control-label" id="name"> Income Name :</label>'
						+'<div class="I-size"><select class="form-text select2" name="incomeName" id="task' + n + '" required="required" onchange="getMulIncomeDetails('+n+');"></select></div>'
    				+'</div> <br> '	
    				+'<div class="row">'
    					+'<label class="L-size control-label" id="name" > Route Name :</label>'
    					+'<div class="I-size"><select class="form-text select2" name="route" id="route' + n + '" "></select></div>'
    				+'</div> <br> '	
    				+'<div class="row" style="padding-left: 45px;">'
    					+'<div class="col-md-1">'
    						+'<label class="control-label">GST :</label>'
    					+'</div>'	
    					+'<div class="col-md-1">'
    						+'<label class="control-label">commission:</label>'
    					+'</div>'	
    					+'<div class="col-md-2">'
    						+'<label class="control-label">Amount :</label>'
    					+'</div>'	
    					+'<div class="col-md-3">'
    						+'<label class="control-label">Net Amount :</label>'
    					+'</div>'	
    				+'</div>'	
    				+'<div class="row" style="padding-left: 45px;">'
    				+'<input type="hidden" class="form-text fixRate" name="fixRate" id="fixRate' + n + '" min="0" required="required" readOnly="readOnly">'
    					+'<div class="col-md-1">'
    						+'<input type="text" class="form-text fixRate" name="gst" id="gst' + n + '" min="0" required="required" readOnly="readOnly">'
    					+'</div>'	
    					+'<div class="col-md-1">'
    						+'<input type="text" class="form-text fixRate" name="commission" id="commission' + n + '" min="0" required="required" readOnly="readOnly">'
    					+'</div>'	
    					+'<div class="col-md-2">'
    						+'<input type="number" class="form-text" name="Amount" id="Amount' + n + '" min="0" required="required" onkeyup="calculateMulAmount(this,'+n+');" placeholder="Amount" onkeypress="return isNumberKeyWithDecimal(event,this);">'
    					+'</div>'	
    					+'<div class="col-md-3">'
    						+'<input type="text" class="form-text netAmount" name="netAmount" id="netAmount' + n + '" min="0" required="required" placeholder="Amount" readOnly="readOnly">'
    					+'</div>'	
    				+'</div> <br> '	
    				+'<div class="row" style="padding-left: 45px;">'
						+'<div class="col-md-3">'
							+'<label class="control-label">Reference :</label>'
						+'</div>'	
						+'<div class="col-md-3">'
							+'<label class="control-label">Date :</label>'
						+'</div>'	
					+'</div>'	
					+'<div class="row" style="padding-left: 45px;">'
						+'<div class="col-md-3">'
							+'<input type="text" class="form-text" name="incomeRefence" value="0" placeholder="Reference">'
						+'</div>'	
						+'<div class="col-md-3 input-group input-append date id="opendDate_'+n+'"">'
		        			+'<input type="text" class="form-text" readonly="\'readonly\'" name="incomeDate" id="tripsheetIncomeDate'+n+'" required="required" data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
		        			+'<div class="input-group-addon add-on"> <span class="fa fa-calendar"></span></div>'
		        		+'</div>'	
					+'</div> <br> '
					+'<div class="row">'
						+'<label class="L-size control-label" id="name" style="text-align-last: center;"> Remark :</label>'
						+'<div class="I-size"><input type="text" class="form-text" id="remark'+n+'" maxlength="249" name="remark" placeholder="Enter description" /></div>'
					+'</div><br> '
					+'<a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("tripIncomeList.in", function(e) {
				+'</div>'
        $("#task" + n).empty(), $("#task" + n).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#task" + n).append($("<option>").text(t.incomeName).attr("value", t.incomeID))
        })
    }), $.getJSON("getVehicleTripRoute.in", function(e) {
        $("#route" + n).empty(), $("#route" + n).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#route" + n).append($("<option>").text(t.routeName).attr("value", t.routeID))
        })
    }) 
    $(".select2").select2()
    
    var currentDate = new Date();
	$('input[name*=incomeDate]').each(function(obj){
		$(this).datepicker({
			autoclose: !0,
			todayHighlight: !0,
			format: "dd-mm-yyyy"
		});
		
	});	
	$('input[name*=incomeDate]').each(function(obj){
		$(this).datepicker("setDate", currentDate)});
}


$(document).ready(function() {
	var currentDate = new Date();
	$('input[name*=incomeDate]').each(function(obj){
		$(this).datepicker({
			autoclose: !0,
			todayHighlight: !0,
			format: "dd-mm-yyyy"
		});
		
	});	
	$('input[name*=incomeDate]').each(function(obj){
		$(this).datepicker("setDate", currentDate)});
});


 function showAddIncome (){
		$("#Income").val(0);
		$("#route").val(0);
		$("#Amount").val(0);
		$("#netAmount").val(0);
		$("#incomeRefence").val(0);
		$("#remark").val('');
		
	removeIncome();
	 $("#addFixRateIncome").modal('show')
	 
 }

function saveTripSheetIncome(){
	//$('#saveIncome').hide();
	if(!validateIncome()){
		$('#saveIncome').show();
		return false;
	}
	
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		hideLayer();
		return false;
	}
	
	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	 		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	 		=  $('#companyId').val();
	jsonObject["userId"] 	 			=  $('#userId').val();
	jsonObject["isMultiple"] 	 		=  true;
	
	/*jsonObject["expenseNameId"] 	 	=  $('#Expense').val();
	jsonObject["expenseAmount"] 	 	=  $('#Amount').val();
	jsonObject["expenseRef"] 	 		=  $('#expenseRefence').val();*/
	
	var incomeArr = new Array();
	var amountArr = new Array();
	var refArr = new Array();
	var routeArr = new Array();
	var incomeDateArr = new Array();
	var netAmountArr = new Array();
	var remarkArr = new Array();
	var gstArr = new Array();
	var commissionArr = new Array();
	var driverExlArr = new Array();
	
	
	$("select[name=incomeName]").each(function(){
		console.log('Income ',$(this).val());
		incomeArr.push($(this).val());
	});
	
	$("input[name=Amount]").each(function(){
		amountArr.push($(this).val());
	});
	$("input[name=incomeRefence]").each(function(){
		refArr.push($(this).val());
	});
	$("input[name=route]").each(function(){
		routeArr.push($(this).val());
	});
	$("input[name=incomeDate]").each(function(){
		incomeDateArr.push($(this).val());
	});
	$("input[name=netAmount]").each(function(){
		netAmountArr.push($(this).val());
	});
	$("input[name=remark]").each(function(){
		remarkArr.push($(this).val());
	});
	$("input[name=gst]").each(function(){
		gstArr.push($(this).val());
	});
	$("input[name=commission]").each(function(){
		commissionArr.push($(this).val());
	});
	$("input[name=isDriverExcluded]").each(function(){
		
		if(document.getElementById(this.id).checked){
			driverExlArr.push(true);
		}else{
			driverExlArr.push(false);
		}
	});
	
	$('.btn-group').each(function(i, btn){
		 alert($('.btn-group > .btn.active').text());

    });
	
	
	var array	 = new Array();
	for(var i =0 ; i< incomeArr.length; i++){
		var incomeDetails				= new Object();
		
		incomeDetails.incomeNameId		= incomeArr[i];
		incomeDetails.incomeAmount		= amountArr[i];
		incomeDetails.incomeRef			= refArr[i];
		incomeDetails.routeId			= routeArr[i];
		incomeDetails.netAmount			= netAmountArr[i];
		incomeDetails.remark			= remarkArr[i];
		incomeDetails.gst				= gstArr[i];
		incomeDetails.commission		= commissionArr[i];
		incomeDetails.incomeDate		= incomeDateArr[i];
		incomeDetails.driverExcluded	= driverExlArr[i];
		array.push(incomeDetails);
	}
	jsonObject.incomeDetails = JSON.stringify(array);
	jsonObject.paymetTypeId	 = $('#renPT_option').val();
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	
	
	showLayer();
	$.ajax({
		url: "saveTripSheetIncomeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Data saved successfuelly !');
			location.replace('showTripSheet?tripSheetID='+$('#tripSheetId').val());
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			$('#expenseSave').show();
			hideLayer();
		}
	});

}

 function validateIncome(){
 	var noIncomeName	= false;
 	var noAmount	= false;
 	
 	$('select[name*=incomeName]').each(function(){
 		var incomeVal = $("#"+$( this ).attr( "id" )).val();
 		if(incomeVal <= 0){
 			noIncomeName	= true;
 			showMessage('errors', 'Please Select Income Types!');
 			return false;
 		}
 	
 	});
 	
 	$('input[name*=Amount]').each(function(){
 		var amount = $("#"+$( this ).attr( "id" )).val();
 		if(amount <= 0){
 			noAmount	= true;
 			showMessage('errors', 'Please Enter Amount !');
 			return false;
 		}
 	
 	});
 	
 	if($("#remark").val() == ''){
 		showMessage('info','Please Enter Remark')
 		return false;
 	}
 	
 	if(noAmount || noIncomeName){
 		return false;
 	}
 	
 	return true;
 }
 
 function removeTripSheetIncome(tripIncomeId){
	 var jsonObject			= new Object();
		jsonObject["tripsheetId"] 	 		=  $('#tripSheetId').val();
		jsonObject["companyId"] 	 		=  $('#companyId').val();
		jsonObject["userId"] 	 			=  $('#userId').val();
		jsonObject["tripIncomeId"] 	 		= tripIncomeId;
	 showLayer();
		$.ajax({
			url: "removeTripSheetIncomeDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage('success','Data saved successfuelly !');
				location.replace('addIncome.in?tripSheetID='+$('#tripSheetId').val());
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				$('#expenseSave').show();
				hideLayer();
			}
		});
 }