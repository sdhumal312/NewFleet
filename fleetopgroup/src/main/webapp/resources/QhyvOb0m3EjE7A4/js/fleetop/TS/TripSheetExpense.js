var e = 25, tripSheetConfig = null ; expenseLimitHM = null;
var addPFDetails = false;
t = $(".input_fields_wrap"),
n = $(".add_field_button"),
a = 1;
$(document).ready(function() {
    $(n).click(function() {
    	if($('#showCreditAndVendorAtExpense').val() == 'true' || $('#showCreditAndVendorAtExpense').val() == true){
    		addExpense1();
    		
    	}else{
    		addExpense();
    	}
    }), $(t).on("click", ".remove_field", function(e) {
    	removeExpense();
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense").empty(), $("#Expense").append($("<option value='0'>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    }),  $.getJSON("getTripDriverList.in?tripsheetId="+ $('#tripSheetId').val(), function(e) {
		console.log("list "+ e)
        $("#driverId").empty(), $("#driverId").append($("<option value='0'>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#driverId").append($("<option>").text(t.driver_firstname).attr("value", t.driver_id))
        })
    }),
    	
    	$("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchList.in?Action=FuncionarioSelect2",
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
                            text: a.vendorName + " - " + a.vendorLocation,
                            slug: a.slug,
                            id: a.vendorId
                        }
                    })
                }
            }
        }
    }),$("#tallyCompanyId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
                            text: a.companyName ,
                            slug: a.slug,
                            id: a.tallyCompanyId
                        }
                    })
                }
            }
        }
    });
    
});

function addExpense(){
	
	// e > a && (a++, $(t).append('<div><input type="hidden" name="isCredit" id="isCredit' + a + '" value="false"><input type="hidden" name="vendorId" id="vendorId' + a + '" value="false"><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" onchange="getExpenseMaxLimit(' + a + ');" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" readonly="readonly" name="Amount" min="0" id="Amount' + a + '" required="required" placeholder="Amount" onkeyup="return validateExpenseRange('+a+');" ></div><div class="col-md-3"><input type="text" class="form-text" name="expenseRefence" value="0" placeholder="Reference"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
	 e > a && (a++, $(t).append('<div><input type="hidden" name="isCredit" id="isCredit' + a + '" value="false"><input type="hidden" name="vendorId" id="vendorId' + a + '" value="false"><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" onchange="getExpenseMaxLimit(' + a + ');" required="required"></select></div><div class="col-md-2" id="parentDiv"><select class="form-text select2" name="driverId" id="driver' + a + '" " required="required"></select></div><div class="col-md-2"><input type="number" class="form-text" readonly="readonly" name="Amount" onkeypress="return isNumberKeyWithDecimal(event,this.id);" min="0" id="Amount' + a + '" required="required" placeholder="Amount" onkeyup="return validateExpenseRange('+a+');"></div><div class="col-md-2"><input type="text" class="form-text" name="expenseRefence" value="0" placeholder="Reference"></div><div class="col-md-1"><input type="file" class="doc" name="input-file-preview" id="tripExpenseDocument'+a+'" /></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
		$("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
			$("#task" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
		})
		 if(tripSheetConfig.addMultipleTripExpenseDocument == false || tripSheetConfig.addMultipleTripExpenseDocument == 'false'){
			 $('#tripExpenseDocument'+a).addClass('hide') 
		   }
	}),
	$.getJSON("getTripDriverList.in?tripsheetId="+ $('#tripSheetId').val(), function(e) {
		$("#driver" + a).empty(), $("#driver" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
			$("#driver" + a).append($("<option>").text(t.driver_firstname).attr("value", t.driver_id))
		})
		if($("#IncdriverSalary").val() == "false" || $("#IncdriverSalary").val() == false)
		{
  			$('#parentDiv').remove();
 		}
	}),
	 $(".select2").select2())
}


function addExpense1(){
			
	 e > a && (a++, $(t).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row5" style="text-align:left; padding-left: 51px; padding-bottom: 6px;"> <label for="manufacurer">Is Credit:<abbr title="required">*</abbr></label><input type="checkbox" name="creditCheckBox" id="creditCheckBox' + a + '" onClick ="setCredit2(creditCheckBox'+ a +', isCredit'+ a +')"><input type="hidden" name="isCredit"id="isCredit' + a + '"value="false"></div> <div class="row1"><label class="L-size control-label"for="manufacurer">Vendor Name:<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden"id="vendorId' + a + '"name="vendorId"style="width: 100%;" value="0" placeholder="Please Enter Vendor Name"/></div></div><div class="row"><label class="L-size control-label"id="Type">Expense Name:<abbr title="required">*</abbr></label><div class="I-size"><select class="select2"style="width: 100%;" name="expenseName"id="Expense' + a + '"required="required" onchange="getExpenseMaxLimit(' + a + ');"></select></div></div><br/><div class="row"><label class="L-size control-label"for="manufacurer">Tally Company Name:<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden"id="tallyCompanyId' + a + '"name="tallyCompanyId"style="width: 100%;" placeholder="Please Enter Tally Company Name"/></div></div><br/><br/><div class="row"><div class="col-md-3"><label for="manufacurer">Amount:<abbr title="required">*</abbr></label><input type="number"class="form-text"name="Amount"id="Amount' + a + '" placeholder="Amount"min="0"required="required" onkeyup="return validateExpenseRange('+a+');"></div><div class="col-md-3"><label for="manufacurer">Reference:<abbr title="required">*</abbr></label><input type="text"class="form-text"name="expenseRefence" placeholder="Reference"value="0"></div></div><br/><div class="row"><label class="L-size control-label"for="manufacurer">Remark:</label><div class="I-size"><input type="text"class="form-text"id="description' + a + '" maxlength="249"name="description" placeholder="Enter Remark"/></div></div><br/><div class="row1" class="form-group"><label class="L-size control-label" for="renewalpaidDate">Voucher Date :<abbr title="required">*</abbr></label><div class="I-size"><div class="input-group input-append date" id="paidDate '+a+'"><input type="text" class="form-text" name="voucherDate" id="voucherDate '+ a+ ' " data-inputmask=""alias": "dd-mm-yyyy"" data-mask="" /> <span class="input-group-addon add-on"><span class="fa fa-calendar"></span></span></div></div></div></div></div><a href="#"class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i>Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
		$("#Expense" + a).empty(), $("#Expense" + a).append($("<option value='0'>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
			$("#Expense" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
		})
	}),$(document).ready(function() {
		   $("#vendorId" + a + "").select2({
		        minimumInputLength: 3,
		        minimumResultsForSearch: 10,
		        ajax: {
		            url: "getVendorSearchList.in?Action=FuncionarioSelect2",
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
		                            text: a.vendorName + " - " + a.vendorLocation,
		                            slug: a.slug,
		                            id: a.vendorId
		                        }
		                    })
		                }
		            }
		        }
		    }),$("#tallyCompanyId" + a + "").select2({
		        minimumInputLength: 3,
		        minimumResultsForSearch: 10,
		        ajax: {
		            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
		                            text: a.companyName ,
		                            slug: a.slug,
		                            id: a.tallyCompanyId
		                        }
		                    })
		                }
		            }
		        }
		    });
	 }));
}

function removeExpense(){
	$(".remove_field").each(function(i){
		if(a - 2 == i)
		$(this).parent("div").remove(), a--;
	});
}

function expensePopUp(tripSheetId, expenseId) {

	if(tripSheetId > 0){
		
		var jsonObject	= new Object();
		jsonObject["tripSheetId"] =  tripSheetId;
		jsonObject["expenseId"] =  expenseId;
				
		showLayer();
		$.ajax({
			url: "getExpenseCombineDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			setModelExpenseCombineDeatils(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
	
}

function setModelExpenseCombineDeatils(data) {
var expenseList	= null;
if(data.ExpenseList != undefined) {
	$("#modelBodyTollExpense").html("Toll Expense Report");

	$("#modelBodyExpenseDetails").empty();
	expenseList	= data.ExpenseList;
	var thead = $('<thead style="background-color: aqua;">');
	var tbody = $('<tbody>');
	var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	

	th1.append('Sr No');
	th2.append('Expense Name');
	th3.append('Type');
	th4.append('Place');
	th5.append('Ref');
	th6.append('Expense Date');
	th7.append('Amount');
	

	tr1.append(th1);
	tr1.append(th2);
	tr1.append(th3);
	tr1.append(th4);
	tr1.append(th5);
	tr1.append(th6);
	tr1.append(th7);

	thead.append(tr1);
	
	var totalExpenseAmount=0;

	for(var i = 0; i < expenseList.length; i++ ) {
		var tr = $('<tr>');

		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		
		
		td1.append(i+1);
		td2.append(expenseList[i].expenseName); 
		if(expenseList[i].expenseFixed == 'FIXED'){
			td3.append('<a class="label label-success"</a>'+expenseList[i].expenseFixed);
		} else {
			td3.append('<a class="label label-warning"</a>'+expenseList[i].expenseFixed);
		}
		td4.append(expenseList[i].expensePlace);
		td5.append(expenseList[i].expenseRefence);
		td6.append(expenseList[i].createdStr);
		td7.append(expenseList[i].expenseAmount);
		
		totalExpenseAmount += expenseList[i].expenseAmount;

		tr.append(td1);
		tr.append(td2);
		tr.append(td3);
		tr.append(td4);
		tr.append(td5);
		tr.append(td6);
		tr.append(td7);

		tbody.append(tr);
	}
	
	var tr2 = $('<tr>');

	var td1		= $('<td colspan="6">');
	var td2		= $('<td>');
	


	td1.append("Total :");
	td2.append(totalExpenseAmount.toFixed(2));
	

	tr2.append(td1);
	tr2.append(td2);


	tbody.append(tr2);
	
	$('#ExpenseCombineDetails').modal('show');
	$("#modelBodyExpenseDetails").append(thead);
	$("#modelBodyExpenseDetails").append(tbody);
	
	/*$("#ResultContent").removeClass("hide");
	$("#printBtn").removeClass("hide");
	$("#exportExcelBtn").removeClass("hide");*/
} else {
	showMessage('info','No record found !');
	/*$("#ResultContent").addClass("hide");
	$("#printBtn").addClass("hide");
	$("#exportExcelBtn").addClass("hide");*/
}
setTimeout(function(){ hideLayer(); }, 500);
}

var allowShortCut = $('#allowShortCut').val();

if(allowShortCut == 'true'){
	document.addEventListener('keyup', doc_keyUp, false); 	// register the handler 
	
	function doc_keyUp(e) {
	    
		if(e.altKey && e.which == 78) {						// this would test for whichever key is 78(N) and the alt key at the same time
			addExpense();
			return false;
		}
		else if(e.altKey && e.which == 82){
			removeExpense();
			return false;
		}
	}
	
} else {
	document.removeEventListener("keyup", doc_keyUp); 
}



$(document).ready(function() {
   $("#vendorId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchList.in?Action=FuncionarioSelect2",
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
                            text: a.vendorName + " - " + a.vendorLocation,
                            slug: a.slug,
                            id: a.vendorId
                        }
                    })
                }
            }
        }
    });
});
function setCredit(creditCheckBoxId, creditId){
	if(!$('#'+creditCheckBoxId).prop("checked")){
		$('#'+creditId).val(false);
	}else{
		$('#'+creditId).val(true);
	}
}
function setCredit2(creditCheckBoxId, creditId){
	if(!$('#'+creditCheckBoxId.id).prop("checked")){
		$('#'+creditId.id).val(false);
	}else{
		$('#'+creditId.id).val(true);
	}
}

function getPendingRenewal(){
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	 		 		=  $('#vid').val();
	jsonObject["companyId"] 		 	=  $('#companyId').val();
	//
	showLayer();
	$.ajax({
		url: "/restservices/getRenewalReminderPending",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderModelData(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function getPendingServiceEntries(count){

	var jsonObject			= new Object();
	
	jsonObject["vid"] 	 		 		=  $('#vid').val();
	jsonObject["companyId"] 		 	=  $('#companyId').val();
	//
	showLayer();
	$.ajax({
		url: "/restservices/getServiceEntriesPending",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderSEModelData(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}
function renderSEModelData(data){
	if(data.serviceList != null && data.serviceList.length > 0){
		$('#sdataTable').show();
		$("#stableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
	    var exeOptions = "<option value='0'>Select Expense</option>";
	    if(data.expenseList != undefined){
	    	for(var i = 0; i < data.expenseList.length; i++){
	    		exeOptions += '<option value="'+data.expenseList[i].expenseID+'">'+data.expenseList[i].expenseName+'</option>';
	    	}
	    }
	    
		for(var i = 0; i< data.serviceList.length; i++){
			
			var reminder = data.serviceList[i];
			if(reminder.serviceEntries_id != null){
				curl = '<a target="_blank" href="ServiceEntriesParts.in?SEID='+reminder.serviceEntries_id+'">'+"SE-"+reminder.serviceEntries_Number+'</a>';
			}
			vehicleUrl	= '<a target="_blank" href="showVehicle?vid='+reminder.vid+'">'+""+reminder.vehicle_registration+'</a>';
			var tr =' <tr>'
				    +'<td><input type ="checkbox" name="serviceId" id="'+reminder.serviceEntries_id+'"></td>'
					+'<td>'+curl+'</td>'
					+'<td>'+vehicleUrl+'</td>'
					+'<td id="serviceCost'+reminder.serviceEntries_id+'">'+reminder.totalserviceROUND_cost+'</td>'
					+'<td>'+reminder.vendor_name+'<input type="hidden" id="vendorId'+reminder.serviceEntries_id+'" value="'+reminder.vendor_id+'"></td>'
					+'<td>'+reminder.service_paymentType+'<input type="hidden" id="paymentType'+reminder.serviceEntries_id+'" value="'+reminder.service_paymentTypeId+'"></td>'
					+'<td>'+reminder.tallyCompanyName+'<input type="hidden" id="tallyCompany'+reminder.serviceEntries_id+'" value="'+reminder.tallyCompanyId+'"></td>'
					+'<td><select class="form-text" name="expenseId" id="expenseId'+reminder.serviceEntries_id+'" required="required"> </select></td>'
				+'</tr>';
			$('#stableBody').append(tr);
			srNo++;
			$('#expenseId'+reminder.serviceEntries_id+'').html(exeOptions);
		}
		$('#serviceEntiresPending').modal('show');
	}else{
		$('#sdataTable').hide();
		showMessage('info', 'No record found !');
	}
}

function renderModelData(data){
	if(data.renewalList != null && data.renewalList.length > 0){
		$('#dataTable').show();
		$("#tableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
	    
	    var options = "<option value='0'>Select Tally Company</option>";
	    var exeOptions = "<option value='0'>Select Expense</option>";
	    if(data.companyList != undefined){
	    	for(var i = 0; i < data.companyList.length; i++){
	    		options += '<option value="'+data.companyList[i].tallyCompanyId+'">'+data.companyList[i].companyName+'</option>';
	    	}
	    }
	    if(data.expenseList != undefined){
	    	for(var i = 0; i < data.expenseList.length; i++){
	    		exeOptions += '<option value="'+data.expenseList[i].expenseID+'">'+data.expenseList[i].expenseName+'</option>';
	    	}
	    }
	    
		for(var i = 0; i< data.renewalList.length; i++){
			
			var reminder = data.renewalList[i];
			if(reminder.renewal_id != null){
				curl = '<a target="_blank" href="showRenewalReminder.in?renewal_id='+reminder.renewal_id+'">'+"RR-"+reminder.renewal_R_Number+'</a>';
			}
			vehicleUrl	= '<a target="_blank" href="showVehicle?vid='+reminder.vid+'">'+""+reminder.vehicle_registration+'</a>';
			var tr =' <tr>'
				    +'<td><input type ="checkbox" name="renewal_id" id="'+reminder.renewal_id+'"></td>'
					+'<td>'+curl+'</td>'
					+'<td>'+vehicleUrl+'</td>'
					+'<td >'+reminder.renewal_type+'</td>'
					+'<td>'+reminder.renewal_subtype+'</td>'
					+'<td id="renewalAmt'+reminder.renewal_id+'">'+reminder.renewal_Amount+'</td>'
					+'<td>'+reminder.vendorName+'<input type="hidden" id="vendorId'+reminder.renewal_id+'" value="'+reminder.vendorId+'"></td>'
					+'<td>'+reminder.renewal_paymentType+'<input type="hidden" id="paymentType'+reminder.renewal_id+'" value="'+reminder.paymentTypeId+'"></td>'
					+'<td><select class="form-text" name="tallyCompany" id="tallyCompany'+reminder.renewal_id+'" required="required"> </select></td>'
					+'<td><select class="form-text" name="expenseId" id="expenseId'+reminder.renewal_id+'" required="required"> </select></td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
			$('#tallyCompany'+reminder.renewal_id+'').html(options);
			$('#expenseId'+reminder.renewal_id+'').html(exeOptions);
		}
		$('#renewalPending').modal('show');
	}else{
		$('#dataTable').hide();
		showMessage('info', 'No record found !');
	}
}

function addCheckbox(name) {
	   var container = $('#cblist');
	   var inputs = container.find('input');
	   var id = inputs.length+1;

	   $('<input />', { type: 'checkbox', id: 'cb'+id, value: name }).appendTo(container);
	   $('<label />', { 'for': 'cb'+id, text: name }).appendTo(container);
	}

function createTallySelectIuput(a){
	var tallyInput = '<input type="hidden"id="tallyCompanyId' + a + '"name="tallyCompanyId"style="width: 100%;" placeholder="Please Enter Tally Company Name"/>';
	return tallyInput;
}

function saveServiceExpenseToTrip(){
	$('#saveServiceExpense').hide();
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	 		 		=  $('#vid').val();
	jsonObject["companyId"] 		 	=  $('#companyId').val();
	jsonObject["userId"] 		 		=  $('#userId').val();
	jsonObject["tripSheetId"] 		 	=  $('#tripSheetId').val();
	
	var serviceEntriesIds = new Array();
	var serviceCostArr	  = new Array();
	var tallyCompanyArr	  = new Array();
	var noOfSelect = 0;
	
	$("input[name=serviceId]").each(function(){
		if($(this).prop("checked")){
			serviceEntriesIds.push(this.id);
			noOfSelect++;
		}
	});
	
	
	if(noOfSelect == 0){
		showMessage('info', 'Please select atleast one service entry !');
		$('#saveServiceExpense').show();
		return false;
	}
	var noTallyCompany = false;
	var array	 = new Array();
	for (var i = 0; i < serviceEntriesIds.length; i++) {
	    var serviceId = Number(serviceEntriesIds[i]);
	    if(Number($('#expenseId'+serviceId+'').val()) == 0 ){
	    	noTallyCompany = true;
	    	showMessage('info', 'Please select expense head !');
	    	$('#saveServiceExpense').show();
	    	return false;
	    }
	    
	    var serviceEntriesObj	= new Object();
	    serviceEntriesObj.serviceEntriesId	= serviceId;
	    serviceEntriesObj.serviceCost		= $('#serviceCost'+serviceId+'').html();
	    serviceEntriesObj.tallyCompanyId	= $('#tallyCompany'+serviceId+'').val();
	    serviceEntriesObj.vendorId			= $('#vendorId'+serviceId+'').val();
	    serviceEntriesObj.paymentTypeId		= $('#paymentType'+serviceId+'').val();
	    serviceEntriesObj.expenseId			= $('#expenseId'+serviceId+'').val();
		
	    array.push(serviceEntriesObj);
	}
	
	jsonObject.seDetails = JSON.stringify(array);
	
	if(noTallyCompany){
		return false;
	}
	showLayer();
	$.ajax({
		url: "/restservices/saveServiceExpenseToTrip",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Date Saved Successfully !')
			window.location.replace("showTripSheet?tripSheetID="+data.tripSheetId+"");
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
function saveRenewalPendingDetails(){

	$('#renewalSave').hide();
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	 		 		=  $('#vid').val();
	jsonObject["companyId"] 		 	=  $('#companyId').val();
	jsonObject["userId"] 		 		=  $('#userId').val();
	jsonObject["tripSheetId"] 		 	=  $('#tripSheetId').val();
	
	var serviceEntriesIds = new Array();
	var serviceCostArr	  = new Array();
	var tallyCompanyArr	  = new Array();
	var noOfSelect = 0;
	
	$("input[name=renewal_id]").each(function(){
		if($(this).prop("checked")){
			serviceEntriesIds.push(this.id);
			noOfSelect++;
		}
	});
	
	
	if(noOfSelect == 0){
		showMessage('info', 'Please select atleast one renewal !');
		$('#renewalSave').show();
		return false;
	}
	var noTallyCompany = false;
	var array	 = new Array();
	for (var i = 0; i < serviceEntriesIds.length; i++) {
	    var serviceId = Number(serviceEntriesIds[i]);
	    if(Number($('#tallyCompany'+serviceId+'').val()) == 0 ){
	    	noTallyCompany = true;
	    	showMessage('info', 'Please select tally company of selected Renewal !');
	    	$('#renewalSave').show();
	    	return false;
	    }
	    if(Number($('#expenseId'+serviceId+'').val()) == 0 ){
	    	noTallyCompany = true;
	    	showMessage('info', 'Please Select Expense Head !');
	    	$('#renewalSave').show();
	    	return false;
	    }
	    
	    var serviceEntriesObj	= new Object();
	    serviceEntriesObj.renewalId			= serviceId;
	    serviceEntriesObj.renewalAmt		= $('#renewalAmt'+serviceId+'').html();
	    serviceEntriesObj.tallyCompanyId	= $('#tallyCompany'+serviceId+'').val();
	    serviceEntriesObj.expenseId			= $('#expenseId'+serviceId+'').val();
	    serviceEntriesObj.vendorId			= $('#vendorId'+serviceId+'').val();
	    serviceEntriesObj.paymentTypeId		= $('#paymentType'+serviceId+'').val();
		
	    array.push(serviceEntriesObj);
	}
	
	jsonObject.seDetails = JSON.stringify(array);
	
	if(noTallyCompany){
		return false;
	}
	
	
	showLayer();
	$.ajax({
		url: "/restservices/saveRenewalPendingDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Date Saved Successfully !')
			window.location.replace("showTripSheet?tripSheetID="+data.tripSheetId+"");
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}
function openRemarkUpdateModal(tripSheetExpenseId){
	$('#updateRemark').val($('#remark_'+tripSheetExpenseId).html());
	$('#tripSheetExpenseId').val(tripSheetExpenseId);
	$('#expenseRemark').modal('show');
}
function updateExpenseRemark(){
	var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 	 		=  $('#tripSheetId').val();
	jsonObject["remark"] 		 		=  $('#updateRemark').val();
	jsonObject["tripSheetExpenseId"] 	=  $('#tripSheetExpenseId').val();
	
	$('#expenseRemark').modal('hide');
	
	showLayer();
	$.ajax({
		url: "updateExpenseRemark",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.success != undefined && data.success){
				showMessage('success','Data Saved Successfully !');
				window.location.replace("showTripSheet?tripSheetID="+data.tripSheetId+"");
			}else{
				showMessage('errors', 'Some error occured!');
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
function getTripSheetExpenseList(){

	var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 	 		=  $('#tripSheetId').val();
	
	showLayer();
	$.ajax({
		url: "getTripSheetExpenseList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data ', data)
			setTripSheetExpenseList(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
function setTripSheetExpenseList(data){
	if(data.TripSheetExpense != undefined && data.TripSheetExpense.length > 0){
		$('#expenseTotalTab').show();
		$('#trip_Expense_Table').show();
		$("#tripExpenseBody tr").remove();
		var srNo = 1;
		var expenseTotal = 0;
		 for(var i = 0; i< data.TripSheetExpense.length; i++){
			var TripSheetExpense = data.TripSheetExpense[i];
			expenseTotal += TripSheetExpense.expenseAmount;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+TripSheetExpense.expenseName+'</td>';
			if(!tripSheetConfig.showCreditAndVendorAtExpense){
					tr +=	'<td>'+TripSheetExpense.expenseFixed+'</td>'
					+'<td>'+TripSheetExpense.expensePlace+'</td>'
					+'<td>'+TripSheetExpense.expenseRefence+'</td>';
				}
		   tr += '<td>'+TripSheetExpense.expenseAmount+'</td>';
		   if(tripSheetConfig.showCreditAndVendorAtExpense){
				tr +=	'<td>'+TripSheetExpense.vendorName+'</td>'
				+'<td><a data-toggle="tip" data-original-title="click to edit" id="remark_'+TripSheetExpense.tripExpenseID+'" href="#" onclick="openRemarkUpdateModal('+TripSheetExpense.tripExpenseID+');">'+TripSheetExpense.remark+'</a></td>';
			
			}
		   if(tripSheetConfig.addTripExpenseDocument == true || tripSheetConfig.addTripExpenseDocument == 'true'){
			   if(TripSheetExpense.tripSheetExpense_document == true || TripSheetExpense.tripSheetExpense_document == 'true'){
				tr +=	'<td><a href="download/TripsheetExpenseDocument/'+TripSheetExpense.tripSheetExpense_document_id+'"> <span class="fa fa-download"></span></a></td>';
			   }else{
				   tr +=	'<td></td>'; 
			   }
			}
		   else if(tripSheetConfig.addMultipleTripExpenseDocument == true  || tripSheetConfig.addMultipleTripExpenseDocument == 'true'){
			   if(TripSheetExpense.tripSheetExpense_document == true || TripSheetExpense.tripSheetExpense_document == 'true'){	
				   tr +=	'<td><a href="download/TripsheetExpenseDocument/'+TripSheetExpense.tripSheetExpense_document_id+'"> <span class="fa fa-download"></span></a></td>';
			   }else{
				   tr +=	'<td></td>'; 
			   }
		   }
		   else{
			   tr +=	''; 
		   }
		   if(!TripSheetExpense.avoidToDelete){
				tr += '<td><a href="#" data-toggle="tip" data-original-title="click to remove"><font color="red" onclick="removeTripExpense('+TripSheetExpense.tripExpenseID+');"><i class="fa fa-times"> Remove</i></font> </a></td>';
			}else{
				tr +=  '<td><a href="#" data-toggle="tip" data-original-title="Do Nothing">--</td>';
			}
		   tr += '</tr>';
			$('#tripExpenseBody').append(tr);
			srNo++;
		}
		 $('#expenseTotal').html((expenseTotal).toFixed(2));
	}else{
		$('#expenseTotalTab').hide();
		$('#trip_Expense_Table').hide();
	}
}
function setConfiguration(tripConfiguration){
	tripSheetConfig = JSON.parse(tripConfiguration);
	getTripSheetExpenseList();
}

function saveMultipleTripSheetExpense(){

	
	if(!validateExpense()){
		return false;
	}
	
	if (validateBankPaymentDetails && $('#paidDateMaxTodate').val() == '') {
		showMessage('info', 'Enter paid Date to Process');
		hideLayer();
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
	jsonObject["paidDate"] 	 			=  $('#paidDateMaxTodate').val();
	jsonObject["isMultiple"] 	 		=  true;
	jsonObject["validateDoublePost"] 	 =  true;
	jsonObject["unique-one-time-token"]  =  $('#accessToken').val()
	
	/*jsonObject["expenseNameId"] 	 	=  $('#Expense').val();
	jsonObject["expenseAmount"] 	 	=  $('#Amount').val();
	jsonObject["expenseRef"] 	 		=  $('#expenseRefence').val();*/
	
	var expenseArr = new Array();
	var amountArr = new Array();
	var refArr = new Array();
	var docArr	= new Array();
	var driverIdArr = new Array();
	
	$("select[name=expenseName]").each(function(){
		expenseArr.push($(this).val());
	});
	
	$("input[name=Amount]").each(function(){
		amountArr.push($(this).val());
	});
	$("input[name=expenseRefence]").each(function(){
		refArr.push($(this).val());
	});
	$("input[name=input-file-preview]").each(function(){
		docArr.push($(this).val());
	});
	$("select[name=driverId]").each(function(){
		driverIdArr.push($(this).val());
	});
	var array	 = new Array();
	for(var i =0 ; i< expenseArr.length; i++){
		var expenseDetails				= new Object();
		
		expenseDetails.expenseNameId			= expenseArr[i];
		expenseDetails.expenseAmount			= amountArr[i];
		expenseDetails.expenseRef				= refArr[i];
		expenseDetails.tripSheetExpenseDocId 	= docArr[i];
		expenseDetails.driverId 				= driverIdArr[i];
		
		array.push(expenseDetails);
	}
	jsonObject.expenseDetails = JSON.stringify(array);
	jsonObject.paymetTypeId		= $('#renPT_option').val();
	
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("tripSheetExpenseData", JSON.stringify(jsonObject));
	
	/*showLayer();
	$.ajax({
		url: "saveTripSheetExpenseDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {*/
    
    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveTripSheetExpenseDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			showMessage('success','Data saved successfuelly !');
			console.log("data.outOfRange",data.outOfRange)
			if(data.outOfRange ==  true){
				showMessage('info','You Are Exceeding The Max Limit Of Expense Amount Please Contact System Admin')
			}
			location.replace('addExpense.in?tripSheetID='+$('#tripSheetId').val());
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			$('#expenseSave').show();
			hideLayer();
		}
	});

}

function saveTripSheetExpense(){
	
	if(!validateExpense()){
		$('#expenseSave').show();
		return false;
	}
	if(!validatePFESIAmount()){
		$('#expenseSave').show();
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	 		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	 		=  $('#companyId').val();
	jsonObject["userId"] 	 			=  $('#userId').val();
	
	jsonObject["expenseNameId"] 	 	=  $('#Expense').val();
	jsonObject["expenseAmount"] 	 	=  $('#Amount').val();
	jsonObject["expenseRef"] 	 		=  $('#expenseRefence').val();
	jsonObject["tripExpenseDocument"] 	=  $('#tripExpenseDocument').val();
	jsonObject["validateDoublePost"] 	 =  true;
	jsonObject["unique-one-time-token"]  =  $('#accessToken').val();
	
	if($('#showPFAmount').val() == 'true' || $('#showPFAmount').val() == true && addPFDetails){
		jsonObject["pfAmount"]  			=  $('#pfAmount').val();
		jsonObject["esiAmount"]  			=  $('#esiAmount').val();
		jsonObject["balanceAmount"]  			=  $('#balanceAmount').val();
		
	}
	
	
	if(tripSheetConfig.showCreditAndVendorAtExpense){
		jsonObject["Credit"] 	 			=  $('#isCredit').val();
		jsonObject["vendorId"] 	 			=  $('#vendorId').val();
		jsonObject["tallyCompanyId"] 	 	=  $('#tallyCompanyId').val();
		jsonObject["description"] 	 		=  $('#description').val();
		jsonObject["voucherDateStr"] 	 	=  $('#voucherDateStr').val();
		
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("tripSheetExpenseData", JSON.stringify(jsonObject));
	
	showLayer();
	$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "saveTripSheetExpenseDetails",
			data: data,
			processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
			success: function (data) {
			//resetTripSheetExpenseFeilds();
			//$('#addManufacturer').modal('hide');
			showMessage('success','Data saved successfully !');
			location.replace('addExpense.in?tripSheetID='+$('#tripSheetId').val());
			//getTripSheetExpenseList(data);
			//hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
			$('#expenseSave').show();
		}
	});
}

function validateExpense(){
	var noExpenseName	= false;
	var noAmount	= false;
	var noVendor	= false;
	var tallyComp	= false;
	var noVoucherDate = false;
	$('#expenseSave').hide();
	
	
	$('select[name*=expenseName]').each(function(){
		var expenseVal = $("#"+$( this ).attr( "id" )).val();
		if(expenseVal <= 0){
			 noExpenseName	= true;
			showMessage('errors', 'Please Select Expense Name!');
			$('#expenseSave').show();
			return false;
		}
	
	});
	
	if($('#showCreditAndVendorAtExpense').val() == 'true' || $('#showCreditAndVendorAtExpense').val() == true){
		
		$('input[name*=vendorId]').each(function(){
			var vendorId = $("#"+$( this ).attr( "id" )).val();
			var vendorIds = this.id.split("Id");
			if(vendorId <= 0){
				if(vendorIds[1] != undefined && $('#isCredit'+vendorIds[1]).val() == 'true'){
					noVendor	= true;
					showMessage('errors', 'Please Select Vendor !');
					$('#expenseSave').show();
					return false;
				}
			}
		});
	}
	
	$("input[name=Amount]").each(function(){
		var Amount = $(this).val();
		if(Amount == "" || Amount == undefined || Amount == 0){
			showMessage('info', 'Please Enter Amount!');
			$('#expenseSave').show();
			noAmount = true;
			return false;
		}
	});
	
	if(noAmount || noExpenseName || noVendor || tallyComp || noVoucherDate){
		$('#expenseSave').show();
		return false;
	}
	return true;
}

function removeTripExpense(tripExpenseID){

	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	 		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	 		=  $('#companyId').val();
	jsonObject["userId"] 	 			=  $('#userId').val();
	jsonObject["tripExpenseID"] 	 	=  tripExpenseID;
	
	
	showLayer();
	$.ajax({
		url: "removeTripSheetExpenseDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Data removed successfuelly !');
			getTripSheetExpenseList(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}
function resetTripSheetExpenseFeilds(){
	$('#Expense').val(0);
	$('#Amount').val(0);
	$('#description').val('');
	$('#expenseRefence').val('');
	$('#creditCheckBox').prop('checked', false);
	$("#vendorId").select2("val", "");
	$("#tallyCompanyId").select2("val", "");
	$('#isCredit').val(false);
}

function getExpenseMaxLimit(a){
	var showPFAmount = $('#showPFAmount').val();
	$('#pfAmount').val('');
	$('#esiAmount').val('');
	$('#balanceAmount').val('');
	if(showPFAmount == 'true' || showPFAmount == true){
		var dailyAllowanceIds = $('#dailyAllowanceIds').val();
		var dailyAllowanceIdArr = dailyAllowanceIds.split(',');
		
		for(var i=0; i<dailyAllowanceIdArr.length; i++){
			if(dailyAllowanceIdArr[i] == Number($('#Expense').val())){
				addPFDetails = true;
				break;
			}else{
				addPFDetails = false;
			}
		}
		
		if(!addPFDetails){
			$('#showPFAmountRow').hide();
			
		}else{
			$('#showPFAmountRow').show();
		}
	}
	
	if(a == undefined){
		a = " ";
	}
	$("#Amount"+a).val("");
	$("#Amount"+a).removeAttr('readonly');
}


function setExpenseLimit(expenseLimit){
	if(expenseLimit != "" && expenseLimit != undefined ){
		expenseLimitHM = JSON.parse(expenseLimit);
	}
}

function validatePFESIAmount(){
	if(addPFDetails){
		if(Number($('#Amount').val()) <= 0){
			showMessage('info', 'Please enter expense amount first !');
			return false;
		}
		var pfPercentage = Number($('#pfPercentage').val());
		var esiPercetage = Number($('#esiPercetage').val());
		var expenseAmt =	Number($('#Amount').val());
		
		var pfAmount = (expenseAmt * pfPercentage / 100).toFixed(2);
		var esiAmount = (expenseAmt * esiPercetage / 100).toFixed(2);
		
		if(Number($('#pfAmount').val()) > 0 && Number($('#pfAmount').val()) != pfAmount){
			$('#pfAmount').val(pfAmount);
			showMessage('info', 'You Cannot Change PF Amount , Either it will be zero or according to formula !');
			return false;
		}
		if(Number($('#esiAmount').val()) > 0 && Number($('#esiAmount').val()) != esiAmount){
			$('#esiAmount').val(esiAmount);
			showMessage('info', 'You Cannot Change ESI Amount , Either it will be zero or according to formula !');
			return false;
		}
		
		if((Number($('#esiAmount').val()) == 0 &&  Number($('#pfAmount').val()) != 0) || Number($('#pfAmount').val()) == 0 &&  Number($('#esiAmount').val()) != 0){
			showMessage('info', 'You have to make both PF and ESI amount to zero OR leave it according to formula !');
			return false;
		}
	}
	
	return true;
}

function calculateESIPF(){
	
	if(addPFDetails){
		var pfPercentage = Number($('#pfPercentage').val());
		var esiPercetage = Number($('#esiPercetage').val());
		var expenseAmt =	Number($('#Amount').val());
		
		if(expenseAmt > 0){
			$('#pfAmount').val((expenseAmt * pfPercentage / 100).toFixed(2));
			$('#esiAmount').val((expenseAmt * esiPercetage / 100).toFixed(2));
			$('#balanceAmount').val((expenseAmt - Number($('#pfAmount').val()) - Number($('#esiAmount').val())).toFixed(2));
		}
	}
}

function validateExpenseRange(a){
	calculateESIPF();
	
	if(a == undefined){
		a = " ";
	}
	if($("#Expense"+a).val() != undefined){
		var expenseId  			= $("#Expense"+a).val();
	}else{
		var expenseId  			= $("#task"+a).val();
	}
	
	var	expenseAmount 		= Number($("#Amount"+a).val());
	var	maxlimitConfig		= $("#maxlimitConfig").val();
	var	expenseOutOfRange	= $("#expenseOutOfRange").val();
	
	var expenseArr = new Array();
	var amountArr = new Array();
	
	$("select[name=expenseName]").each(function(){
		expenseArr.push($(this).val());
	});
	$("input[name=Amount]").each(function(){
		amountArr.push($(this).val());
	});
	
	var tempAmt = 0;
	for(var i= 0 ; i < expenseArr.length; i++){
		expenseId1 	= expenseArr[i];
		amount		= amountArr[i];
		
		if((a != undefined )&&( a > 0 || a != "" ) && (expenseId.includes(expenseId1)) ){
			tempAmt += Number(amount);
			if(expenseLimitHM != null){
				if( i > 0  && Number(expenseLimitHM[expenseId]) > 0 && tempAmt > Number(expenseLimitHM[expenseId]) && (maxlimitConfig == true || maxlimitConfig == 'true') && (expenseOutOfRange == false || expenseOutOfRange == 'false') ){
					$("#Amount"+a).val(0);
					showMessage('info','You Are Exceeding The Max Limit Of Expense Amount Please Contact System Admin')
					return false;
				}
			}
		
		}
		
	}
	if(expenseLimitHM != undefined ){
		if((expenseLimitHM[expenseId] > 0) && (expenseAmount > Number(expenseLimitHM[expenseId])) && (maxlimitConfig == true || maxlimitConfig == 'true') && (expenseOutOfRange == false || expenseOutOfRange == 'false')){
			$("#Amount"+a).val(expenseLimitHM[expenseId]);
			showMessage('info','You Are Exceeding The Max Limit Of Expense Amount Please Contact System Admin')
			return false;
		}
	}
}

$(document).ready(function () {
	   
	  $("#expenseSave").on('keypress click', function (e) {
		       
		       e.preventDefault(); //   *******************stop submit the form, we will post it manually.
		       if($('#showCreditAndVendorAtExpense').val() == 'true' || $('#showCreditAndVendorAtExpense').val() == true){
		    	   saveTripSheetExpense();
		       }else{
		    	   saveMultipleTripSheetExpense();
		       }
		   });

		}); 
