var global = null;
$(document).ready(
		function($) {
			showLayer();
			var lorryHireDetailsId 			 = Number($('#lorryHireDetailsId').val());
			var jsonObject					 = new Object();
			jsonObject["lorryHireDetailsId"] =  lorryHireDetailsId;
			showLayer();
			$.ajax({
	             url: "ShowLorryHireDetailsById",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	                 renderData(data);
	                 global =data;
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 window.location.replace("viewVendorLorryHireDetails.in");
	            	 hideLayer();
	             }
		});
			
});




	

//var vendorIdentity=null;

function renderData(data){
	if(data.lorryHireDetails != null){
		$('#lorryHireDetailsNumber').html('<b>'+data.lorryHireDetails.lorryHireDetailsNumber+'</b>');
		$('#vehicle').html('<a href="showVehicle?vid='+data.lorryHireDetails.vid+'">'+data.lorryHireDetails.vehicle_registration+'</a>');
		$('#vendor').html('<a href="ShowVendor.in?vendorId='+data.lorryHireDetails.vendorId+'">'+data.lorryHireDetails.vendorName+'</a>');
		$('#hireDate').html(data.lorryHireDetails.hireDateStr);
		$('#lorryHire').html(data.lorryHireDetails.lorryHire);
		$('#advanceAmount').html(data.lorryHireDetails.advanceAmount);
		$('#paidAmount').html(data.lorryHireDetails.paidAmount);
		$('#balanceAmount').html(data.lorryHireDetails.balanceAmount);
		$('#balance').html(data.lorryHireDetails.balanceAmount);
		$('#createdOn').html(data.lorryHireDetails.createdOnStr);
		$('#lastUpdated').html(data.lorryHireDetails.lastModifiedOnStr);
		$('#createdBy').html(data.lorryHireDetails.createdBy);
		$('#lastupdatedBy').html(data.lorryHireDetails.lastModifiedBy);
		$('#vendorId').html(data.lorryHireDetails.vendorId);
		
		$('#incomeName').html(data.lorryHireDetails.incomeName);
		$('#remark').html(data.lorryHireDetails.remark);
		$('#tripSheetNumber').html('<a href="showTripSheet?tripSheetID='+data.lorryHireDetails.tripSheetId+'" target="_blank">'+data.lorryHireDetails.tripSheetNumber+'</a>');
		$('#driverName').html(data.lorryHireDetails.driverName);
		
		setChargesDetailsData(data);
		
	}else{
		showMessage('info', 'no data found!');
	}
}
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function setChargesDetailsData(data){
	if(data.hireToChargesList != null && data.hireToChargesList.length > 0){
		$('#dataTable').show();
		var srNo = 1, total = 0, subTotal = 0;
		for(var i = 0; i< data.hireToChargesList.length; i++){
			var hireToChargesList = data.hireToChargesList[i];
			
			total 	 += hireToChargesList.amount;
			// subTotal += clothInventoryDetailsDtos.total;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+hireToChargesList.chargeName+'</td>'
				+'<td>'+hireToChargesList.amount+'</td>'
				if(data.lorryHireDetails.paymentStatusId == 1){
					tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteLorryHireChargesDetails('+hireToChargesList.hireToChargesId+');">Delete</a<td>'
			     }else{
			    	 $('#delete').hide();
			     }
				+'</tr>';
			$('#batteryAmountBody').append(tr);
			srNo++;
			
		}
		
		$('#subTotal').html(total);
		
	}
}


	$(document).ready(function($) {
			var deleted = getUrlParameter('deleted');
			if(deleted == true || deleted == 'true'){
				showMessage('success', 'Data Deleted Successfully !');
			}
	});

	
	function deleteLorryHireChargesDetails(hireToChargesId){
		if(confirm("are you sure to delete ?")){
			showLayer();
			var jsonObject			= new Object();
			jsonObject["hireToChargesId"] 	  =  hireToChargesId;
			jsonObject["lorryHireDetailsId"]  =  Number($('#lorryHireDetailsId').val());
			
			$.ajax({
		             url: "deleteLorryHireChargesDetails",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("ShowLorryHireDetails.in?ID="+Number($('#lorryHireDetailsId').val())+"&delete="+true+"");
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
		}
		
	}
	
	function deleteLorryHireInvoice(hireToChargesId){
		
		if(confirm("are you sure to delete ?")){
			showLayer();
			var jsonObject			= new Object();
			jsonObject["lorryHireDetailsId"]  =  Number($('#lorryHireDetailsId').val());
			
			
			$.ajax({
		             url: "deleteLorryHireInvoice",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("viewVendorLorryHireDetails.in");
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
		}
		
		
	}
	
function AddCharge(){
		$('#myModal').modal('show');
}

$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="Amount" id ="Amount' + a + '" min="0" required="required" placeholder="Amount"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
            $("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#task" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense").empty(), $("#Expense").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    })
});


function saveExpenseDetails(){
	
	var noExpense = false;
	$('select[name*=expenseName]' ).each(function(){
		var lorryExpenseVal = $("#"+$( this ).attr( "id" )).val();
		if(lorryExpenseVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noExpense = true;
			showMessage('errors', 'Please Enter Expense!');
			return false;
		}
	});
	
	var noAmount = false;
	$('input[name*=Amount]' ).each(function(){
		var lorryAmountVal = $("#"+$( this ).attr( "id" )).val();
		if(lorryAmountVal <= 0){
			$("#"+$( this ).attr( "id" )).focus();
			noAmount = true;
			showMessage('errors', 'Please Enter Amount!');
			return false;
		}
	});
	
	if(noExpense || noAmount){
		return false;
	}
	
	var jsonObject 	= new Object();
	var expenseType	= new Array();
	var	amountType	= new Array();

	jsonObject["Expense"]				= $('#Expense').val();
	jsonObject["routeAmount"]		    = $('#routeAmount').val();
	jsonObject["lorryHireDetailsId"]	= $('#lorryHireDetailsId').val();
	
	
	$("input[name=Amount]").each(function(){
		amountType.push($(this).val());
	});
	
	
	
	$("select[name=expenseName]").each(function(){
		expenseType.push($(this).val());
	});
	
	
	
	var array = new Array();
	
	for (var i=0; i<expenseType.length; i++){
		var AddMoreChargesForLorryHire = new Object();
		AddMoreChargesForLorryHire.Expense  = expenseType[i];
		AddMoreChargesForLorryHire.routeAmount	 = amountType[i];
		array.push(AddMoreChargesForLorryHire);
	}
	jsonObject.ExpenseTransfer = JSON.stringify(array);
	
	showLayer();
	$.ajax({
        url: "saveExpenseDetailsInfo",//imp
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	location.reload();
        	showMessage('info', 'Charges added successfully!')
        	hideLayer();
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
	});
		
	
}

// devy latest
$(document).ready(function($) {
	
		showLayer();
		var jsonObject					= new Object();
			
		jsonObject["lorryHireDetailsId"] = $("#lorryHireDetailsId").val();
		
		$.ajax({
			url: "getVendorPaymentInformation.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setVendorPaymentInformation(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
}
);

//next

function setVendorPaymentInformation(data) {
	
	var lorryHireDetailsList	= null;
	if(data.lorryHireDetailsList != undefined) {
		$("#reportHeader").html("Payment Information");

		$("#vendorPaymentTable").empty();
		lorryHireDetailsList	= data.lorryHireDetailsList;
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
		th2.append('Payment Mode');
		th3.append('Remark');
		th4.append('Transaction Number');
		th5.append('Transaction Date');
		th6.append('Received As');
		th7.append('Received Amount');
		
		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		
		thead.append(tr1);
		
		//var totalExpenseAmount=0;

		for(var i = 0; i < lorryHireDetailsList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			
			
			td1.append(i+1);
			td2.append(lorryHireDetailsList[i].paymentMode);   
			td3.append(lorryHireDetailsList[i].remark);
			td4.append(lorryHireDetailsList[i].transactionNumber);
			td5.append(lorryHireDetailsList[i].transactionDateStr);
			td6.append(lorryHireDetailsList[i].paymentStatus);
			td7.append(lorryHireDetailsList[i].receiveAmount);

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			
			tbody.append(tr);
		}
		

		$("#vendorPaymentTable").append(thead);
		$("#vendorPaymentTable").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		//showMessage('info','Total Payment Still Pending !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}

	

	
