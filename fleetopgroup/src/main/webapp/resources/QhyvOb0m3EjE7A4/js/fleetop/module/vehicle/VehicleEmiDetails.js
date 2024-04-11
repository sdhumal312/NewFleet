$(document).ready(function() {
	
		showLayer();
		var jsonObject		= new Object();

		jsonObject["vid"] 	=  $('#vehicleId').val();
		
		$.ajax({
	             url: "getVehicleEmiDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 
	                setEmiDetails(data);
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	             }
		});
		hideLayer();
});

$("#emiPayment").attr("href", "emiPaymentDetails.in?vid="+$('#vehicleId').val());

function setEmiDetails(data) {
	
	$("#VendorPaymentTable").empty();
	
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
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	var th12	= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Bank/Account Name"));
	tr1.append(th3.append("Loan Amount"));
	tr1.append(th4.append("DownPayment Amount"));
	tr1.append(th5.append("Monthly EMI"));
	tr1.append(th6.append("Tenure Type"));
	tr1.append(th7.append("Tenure "));
	tr1.append(th8.append("Loan StartDate"));
	tr1.append(th9.append("Loan EndDate"));
	tr1.append(th10.append("Interest Rate"));
	tr1.append(th11.append("Remark"));
	tr1.append(th12.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vehicleEmiDetailDto != undefined && data.vehicleEmiDetailDto.length > 0) {
		
		var vehicleEmiDetailDto = data.vehicleEmiDetailDto;
	
		for(var i = 0; i < vehicleEmiDetailDto.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			var td12	= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="emiPaymentDetails?vid='+vehicleEmiDetailDto[i].vid+'_'+vehicleEmiDetailDto[i].vehicleEmiDetailsId+'"  target="_blank">'+vehicleEmiDetailDto[i].branchName+"_"+vehicleEmiDetailDto[i].accountNumber+" ("+vehicleEmiDetailDto[i].abbreviation+" )" ));
			tr1.append(td3.append(vehicleEmiDetailDto[i].loanAmount));
			tr1.append(td4.append(vehicleEmiDetailDto[i].downPaymentAmount));
			tr1.append(td5.append(vehicleEmiDetailDto[i].monthlyEmiAmount));
			tr1.append(td6.append(vehicleEmiDetailDto[i].tenureTypeStr));
			tr1.append(td7.append(vehicleEmiDetailDto[i].tenure));
			tr1.append(td8.append(vehicleEmiDetailDto[i].loanStartDateStr));
			tr1.append(td9.append(vehicleEmiDetailDto[i].loanEndDateStr));
			tr1.append(td10.append(vehicleEmiDetailDto[i].interestRate));
			tr1.append(td11.append(vehicleEmiDetailDto[i].remark));
			
			if($("#vehicleMonthlyEMIPayment").val() == true || $("#vehicleMonthlyEMIPayment").val() == "true"){
			//	$("#emiPaymentAction_"+vehicleEmiDetailDto[i].vehicleEmiDetailsId).removeClass("hide");
				var emiAction = '<li><a id="emiPaymentAction_'+vehicleEmiDetailDto[i].vehicleEmiDetailsId+'"  href="emiPaymentDetails?vid='+vehicleEmiDetailDto[i].vid+'_'+vehicleEmiDetailDto[i].vehicleEmiDetailsId+'"  target="_blank"> <span class="fa fa-rupee"></span> EMI Payment</a></li>'
			}else{
				var emiAction = '<li><a id="emiPaymentAction_'+vehicleEmiDetailDto[i].vehicleEmiDetailsId+'" class="hide" href="emiPaymentDetails?vid='+vehicleEmiDetailDto[i].vid+'_'+vehicleEmiDetailDto[i].vehicleEmiDetailsId+'"  target="_blank"> <span class="fa fa-rupee"></span> EMI Payment</a></li>'
			}
			tr1.append(td12.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="editEMIDetails('+vehicleEmiDetailDto[i].vehicleEmiDetailsId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteEMIDetails('+vehicleEmiDetailDto[i].vehicleEmiDetailsId+','+vehicleEmiDetailDto[i].downPaymentAmount+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+ emiAction
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

function editEMIDetails(vehicleEmiDetailsId){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["vehicleEmiDetailsId"]	= vehicleEmiDetailsId;
	

	$.ajax({
		url: "getVehicleEmiDetailsById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.vehicleEmiDetailDto != undefined && data.vehicleEmiDetailDto != null){
				
				$('#loanAmountEdit').val(data.vehicleEmiDetailDto.loanAmount);
				$('#downPaymentAmountEdit').val(data.vehicleEmiDetailDto.downPaymentAmount);
				$('#monthlyEmiAmountEdit').val(data.vehicleEmiDetailDto.monthlyEmiAmount);
				$('#bankEditId').val(data.vehicleEmiDetailDto.bankName);
				$('#bankAccountIdEdit').val(data.vehicleEmiDetailDto.branchName+"_"+data.vehicleEmiDetailDto.accountNumber+" ("+data.vehicleEmiDetailDto.abbreviation+" )");
				$('#tenureEdit').val(data.vehicleEmiDetailDto.tenure);
				$('#interestRateEdit').val(data.vehicleEmiDetailDto.interestRate);
				$('#remarkEdit').val(data.vehicleEmiDetailDto.remark);
				$('#loanStartDateEdit').val(data.vehicleEmiDetailDto.loanStartDateStr);
				$('#loanEndDateEdit').val(data.vehicleEmiDetailDto.loanEndDateStr);
				$('#bankAccount').val(data.vehicleEmiDetailDto.bankAccountId);
				$('#vehicleEmiDetailsId').val(data.vehicleEmiDetailDto.vehicleEmiDetailsId);
			}
			
			$('#editEmiDetails').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}
var bankId = 0;

$("#bankId").change(function(){
$("#bankAccountId").select2("val", "");
 bankId = $("#bankId").val();
}); 

$(document).ready(function() {
	$("#bankId").select2();
	$.getJSON("getAllBankNameist", {
        ModelType: $(this).val(),
        ajax: "true"
    }, function(a) {
        for (var b = '<option value="0">--Please Select Bank--</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].bankId + '">' + a[d].bankName + "</option>";
        b += "</option>", $("#bankId").html(b)
    }), $("#bankAccountId").select2( {
    	minimumInputLength:0, minimumResultsForSearch:10, ajax: {
        url:"getBankAccountListByBankId.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
            return {
                term: a,
                text: bankId
            }
        }
        , results:function(a) {
            return {
                results:$.map(a, function(a) {
                    return {
                        text: a.name+" "+a.accountNumber+" ( "+a.bankName+" ) ", slug: a.slug, id: a.bankAccountId
                    }
                }
                )
            }
        }
    }
})
});

$(document).ready(
		function($) {
			$('button[id=save]').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();

				jsonObject["loanStartDate"] 				=  $('#loanStartDate').val();
				jsonObject["loanEndDate"] 	 				=  $('#loanEndDate').val();
				jsonObject["bankAccountId"] 				=  $('#bankAccountId').val();
				jsonObject["loanAmount"] 					=  $('#loanAmount').val();
				jsonObject["downPaymentAmount"] 			=  $('#downPaymentAmount').val();
				jsonObject["monthlyEmiAmount"] 				=  $('#monthlyEmiAmount').val();
				jsonObject["interestRate"] 					=  $('#interestRate').val();
				jsonObject["tenureType"] 					=  $('#tenureType').val();
				jsonObject["tenure"] 						=  $('#tenure').val();
				jsonObject["remark"] 						=  $('#remark').val();
				jsonObject["vid"] 							=  $('#vehicleId').val();
				jsonObject["vehicleMonthlyEMIPayment"] 		=  $('#vehicleMonthlyEMIPayment').val();
				
				 if(!validateEmiDetails()){
					 return false;
				 }
				 
				showLayer();
				$('#addTripExpense').modal('hide');
				$.ajax({
			             url: "saveVehicleEmiDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	 showMessage('success', "EMI created Successfully");
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
				setTimeout(function(){ hideLayer(); location.reload(true); }, 1500);
				
			});

});

function validateEmiDetails(){
	
	if($('#bankAccountId').val() == '' || $('#bankAccountId').val() <= 0){
		$("#bankAccountId").select2('focus');
		showMessage('errors', 'Please Select Banker Name/ account !');
		return false;
	}
	
	if($('#loanAmount').val() <= 0){
		$("#loanAmount").focus();
		showMessage('errors', 'Please Enter Loan Amount !');
		return false;
	}
	
	if($('#downPaymentAmount').val() == '' || $('#downPaymentAmount').val() < 0){
		$("#downPaymentAmount").focus();
		showMessage('errors', 'Please Enter DownPayment Amount !');
		return false;
	}
	
	if($('#monthlyEmiAmount').val() <= 0){
		$("#monthlyEmiAmount").focus();
		showMessage('errors', 'Please Enter monthly Emi Amount!');
		return false;
	}
	
	if($('#interestRate').val() == '' || $('#interestRate').val() < 0){
		$("#interestRate").focus();
		showMessage('errors', 'Please Enter Interest Rate !');
		return false;
	}
	
	if($('#loanStartDate').val() == '' || $('#loanStartDate').val() <= 0){
		$("#loanStartDate").focus();
		showMessage('errors', 'Please Enter Loan Start Date');
		return false;
	}
	
	if($('#loanEndDate').val() == '' || $('#loanEndDate').val() <= 0){
		$("#loanEndDate").focus();
		showMessage('errors', 'Please Enter Loan End Date');
		return false;
	}
	
	if($('#tenure').val() <= 0){
		$("#tenure").focus();
		showMessage('errors', 'Please Enter Tenure!');
		return false;
	}
	
	
	return true;
}

function validateEmiDetailsEdit(){
	
	
	if($('#loanAmountEdit').val() <= 0){
		$("#loanAmountEdit").focus();
		showMessage('errors', 'Please Enter Loan Amount !');
		return false;
	}
	
	if($('#downPaymentAmountEdit').val() == '' || $('#downPaymentAmountEdit').val() < 0){
		$("#downPaymentAmountEdit").focus();
		showMessage('errors', 'Please Enter DownPayment Amount !');
		return false;
	}
	
	if($('#monthlyEmiAmountEdit').val() <= 0){
		$("#monthlyEmiAmountEdit").focus();
		showMessage('errors', 'Please Enter monthly Emi Amount!');
		return false;
	}
	
	if($('#interestRateEdit').val() == '' || $('#interestRateEdit').val() < 0){
		$("#interestRateEdit").focus();
		showMessage('errors', 'Please Enter Interest Rate !');
		return false;
	}
	
	if($('#loanStartDateEdit').val() == '' || $('#loanStartDateEdit').val() <= 0){
		$("#loanStartDateEdit").focus();
		showMessage('errors', 'Please Enter Loan Start Date');
		return false;
	}
	
	if($('#loanEndDateEdit').val() == '' || $('#loanEndDateEdit').val() <= 0){
		$("#loanEndDateEdit").focus();
		showMessage('errors', 'Please Enter Loan End Date');
		return false;
	}
	
	if($('#tenureEdit').val() <= 0){
		$("#tenureEdit").focus();
		showMessage('errors', 'Please Enter Tenure!');
		return false;
	}
	
	return true;
}

$(document).ready(
		function($) {
			$('button[id=editSave]').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();

				jsonObject["loanStartDate"] 			=  $('#loanStartDateEdit').val();
				jsonObject["loanEndDate"] 	 			=  $('#loanEndDateEdit').val();
				jsonObject["bankAccountId"] 			=  $('#bankAccount').val();
				jsonObject["loanAmount"] 				=  $('#loanAmountEdit').val();
				jsonObject["downPaymentAmount"] 		=  $('#downPaymentAmountEdit').val();
				jsonObject["monthlyEmiAmount"] 			=  $('#monthlyEmiAmountEdit').val();
				jsonObject["interestRate"] 				=  $('#interestRateEdit').val();
				jsonObject["tenureType"] 				=  $('#tenureType').val();
				jsonObject["tenure"] 					=  $('#tenureEdit').val();
				jsonObject["remark"] 					=  $('#remarkEdit').val();
				jsonObject["vid"] 						=  $('#vehicleId').val();
				jsonObject["vehicleEmiDetailsId"] 		=  $('#vehicleEmiDetailsId').val();
				jsonObject["vehicleMonthlyEMIPayment"] 	=  $('#vehicleMonthlyEMIPayment').val();
				jsonObject["isEMIEdit"]					= true;
				
				if(!validateEmiDetailsEdit()){
					return false;
				}
				
				showLayer();
				$('#editEmiDetails').modal('hide');
				$.ajax({
			             url: "updateVehicleEmiDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	 if(data.EMIPaymentStarted != undefined && data.EMIPaymentStarted == true){
			      				$('#editEmiDetails').modal('hide');
			      				showMessage('errors', 'EMI Payment Already Started, Cannot Edit EMI Details Now !');
			      				hideLayer();
			      				return false;
			      			}
			            	 
			                showMessage('success', data.success);
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
				setTimeout(function(){ hideLayer(); location.reload(true); }, 1500);
			});

});


function deleteEMIDetails(vehicleEmiDetailsId,downpaymentAmount) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["vehicleEmiDetailsId"]	= vehicleEmiDetailsId;
		jsonObject["vid"] 						=  $('#vehicleId').val();
		jsonObject["downpaymentAmount"]	= downpaymentAmount;
		
		
		$.ajax({
            url: "deleteVehicleEmiDetailsById",
            type: "POST",
            dataType: 'json',
            data: jsonObject,
            success: function (data) {
           	 
           	 showMessage('success', 'Emi Deleted Successfully');
            },
            error: function (e) {
           	 showMessage('errors', 'Some error occured!')
            }
	});

	setTimeout(function(){ hideLayer(); location.reload(true); }, 500);
	} 
	
}


 $(document).ready(function() {
    $("#loanEndEdit").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    }), $("#loanStartEdit").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    })
});
 
 function emiPayDetails(vehicleEmiDetailsId){
	 
		showLayer();
		var jsonObject					= new Object();

		jsonObject["vid"] 					=  $('#vehicleId').val();
		jsonObject["vehicleEmiDetailsId"]	= vehicleEmiDetailsId;
		
		$.ajax({
            url: "deleteVehicleEmiDetailsById",
            type: "POST",
            dataType: 'json',
            data: jsonObject,
            success: function (data) {
           	 
           	 showMessage('success', 'Emi Deleted Successfully');
            },
            error: function (e) {
           	 showMessage('errors', 'Some error occured!')
            }
	});

	setTimeout(function(){ hideLayer(); location.reload(true); }, 1500);
		
	 
 }