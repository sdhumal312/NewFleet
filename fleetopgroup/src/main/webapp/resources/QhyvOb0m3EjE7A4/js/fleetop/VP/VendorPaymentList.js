function getPageWiseVendorPaymentDetails(pageNumber) {
	showLayer();
	var jsonObject					= new Object();
	var vehicleGroup = $('#vehicleGroup').val();
	
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "vendorPaymentWS/getPageWiseVendorPaymentDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (data) {
			
			setVendorPaymentList(data);
		},
		error: function (e) {
			console.log("Error : " , e);
		}
	});
	setTimeout(function(){ hideLayer(); }, 500);	
}

function setVendorPaymentList(data) {
	
	$("#VendorPaymentCount").html(data.VendorPaymentCount);
	
	$("#VendorPaymentTable").empty();
	var vehicleGroup = $('#vehicleGroup').val();
	
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
	
	var th12	= $('<th class="fit ar">');
	var th13	= $('<th>');

	tr1.append(th1.append("Payment No"));
	tr1.append(th2.append("Vendor Name"));
	tr1.append(th3.append("Invoice No"));
	tr1.append(th4.append("Invoice Date"));
	tr1.append(th5.append("Payment Term"));
	tr1.append(th6.append("Voucher / Cheque No"));
	tr1.append(th7.append("Cheque Date"));
	tr1.append(th8.append("Payment Date"));
	tr1.append(th9.append("Debit Amount"));
	tr1.append(th10.append("Credit Amount"));
	tr1.append(th11.append("GST"));
	
	if(vehicleGroup==="true")
	{
			tr1.append(th12.append("Group"));
	}
	
	tr1.append(th13.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vendorPayment != undefined || data.vendorPayment != null) {
		var vendorPayment = data.vendorPayment;
		
		
		for(var i = 0; i < vendorPayment.length; i++) {
			
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
			var td12	= $('<td class="fit ar">');
			var td13	= $('<td>');
			
			tr1.append(td1.append(vendorPayment[i].vendorPaymentNo));
			var curl = "ShowVendor.in?vendorId="+vendorPayment[i].vendorId+"&page="+data.SelectPage
			tr1.append(td2.append('<a href="' + curl + '" value="'+vendorPayment[i].vendorName+'">'+vendorPayment[i].vendorName+'</a><br>'));
			tr1.append(td3.append(vendorPayment[i].invoiceNumber));
			tr1.append(td4.append(vendorPayment[i].invoiceDateStr));
			tr1.append(td5.append(vendorPayment[i].paymentTypeStr));
			if(vendorPayment[i].paymentTypeId == 1) {
				tr1.append(td6.append(vendorPayment[i].cashVoucherNumber));				
			} else {
				tr1.append(td6.append(vendorPayment[i].chequeNumber));								
			}
			tr1.append(td7.append(vendorPayment[i].chequeDateStr));
			tr1.append(td8.append(vendorPayment[i].paymentDateStr));
			if(vendorPayment[i].transactionTypeId == 1) {				
				tr1.append(td9.append(0));
				tr1.append(td10.append(vendorPayment[i].transactionAmount));
			} else {
				tr1.append(td9.append(vendorPayment[i].transactionAmount));				
				tr1.append(td10.append(0));
			}
			tr1.append(td11.append(vendorPayment[i].gstAmount));
			
			if (vehicleGroup==="true")
			{
				tr1.append(td12.append(vendorPayment[i].vehicleGroup));
			}
		
			var curl = "editVendorPayment.in?vendorPaymentId="+vendorPayment[i].vendorPaymentId;
			tr1.append(td13.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteVendorPayment('+vendorPayment[i].vendorPaymentId+','+data.SelectPage+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));
			tbody.append(tr1);
		}
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseVendorPaymentDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseVendorPaymentDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseVendorPaymentDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseVendorPaymentDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1 ||data.deploymentLog.totalPages != undefined) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseVendorPaymentDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseVendorPaymentDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
	
}

function deleteVendorPayment(vendorPaymentId, pageNumber) {

	if (confirm("Are you sure?") == true) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["vendorPaymentId"]			= vendorPaymentId;

		$.ajax({
			url: "vendorPaymentWS/deleteVendorPayment.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				getPageWiseVendorPaymentDetails(pageNumber);
			},
			error: function (e) {
				console.log("Error : " , e);
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
	}
}