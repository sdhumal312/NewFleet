function getPageWiseBookingDetails(pageNumber) {
	
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;
	
	  showLayer();
	$.ajax({
		url: "getBusBookingDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (data) {
			setBusBookingList(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
		}
	});
}

function setBusBookingList(data) {
	if(data.vendorLorryHireList != undefined && data.vendorLorryHireList.length > 0){
		$("#VendorPaymentCount").html(data.vendorLorryHireCount);
		
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

		tr1.append(th1.append("Booking Number"));
		tr1.append(th2.append("Booking Ref"));
		tr1.append(th3.append("Party Name"));
		tr1.append(th4.append("Trip Start Date"));
		tr1.append(th5.append("Trip End Date"));
		tr1.append(th6.append("PickUp Address"));
		tr1.append(th7.append("TripSheet"));
		tr1.append(th8.append("Delete"));

		thead.append(tr1);
		
		var tbody = $('<tbody>');
		if(data.vendorLorryHireList != undefined || data.vendorLorryHireList != null) {
			var vendorLorryHireList = data.vendorLorryHireList;
			
			
			for(var i = 0; i < vendorLorryHireList.length; i++) {
				
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
				/*var td11	= $('<td class="fit ar">');
				var td12	= $('<td class="fit ar">');
				var td13	= $('<td>');*/
				

				var showUrl = "showBusBookingDetails.in?Id="+vendorLorryHireList[i].busBookingDetailsId;
				tr1.append(td1.append('<a target="_blank" href='+showUrl+'>BB-'+vendorLorryHireList[i].busBookingNumber+'</a>'));
				tr1.append(td2.append(vendorLorryHireList[i].bookingRefNumber));
				tr1.append(td3.append(vendorLorryHireList[i].corporateName));
				tr1.append(td4.append(vendorLorryHireList[i].tripStartDateStr));
				tr1.append(td5.append(vendorLorryHireList[i].tripEndDateStr));
				tr1.append(td6.append(vendorLorryHireList[i].pickUpAddress));
				if(vendorLorryHireList[i].tripSheetNumber != undefined && vendorLorryHireList[i].tripSheetNumber != null && vendorLorryHireList[i].tripSheetNumber > 0){
					var curl = "showTripSheet.in?tripSheetID="+vendorLorryHireList[i].tripSheetId;
					tr1.append(td7.append('<a target="_blank" href='+curl+'>TS-'+vendorLorryHireList[i].tripSheetNumber+'</a>'));
					tr1.append(td8.append('--'));
				}else{
					var curl = "addTripSheetEntries.in?busBookingDetailsId="+vendorLorryHireList[i].busBookingDetailsId;
					
					tr1.append(td7.append('<a href="'+curl+'"><i class="fa fa-edit"></i> Create TripSheet</a>'));
					tr1.append(td8.append('<a href="#" class="confirmation" onclick="deleteBusBooking('+vendorLorryHireList[i].busBookingDetailsId+');"><span class="fa fa-trash"></span> Delete</a>'));
					
				}
				
				/*tr1.append(td8.append(vendorLorryHireList[i].pickUpAddress));
				
				if(vendorLorryHireList[i].tripSheetNumber != undefined && vendorLorryHireList[i].tripSheetNumber != null && vendorLorryHireList[i].tripSheetNumber > 0){
					var curl = "showTripSheet.in?tripSheetID="+vendorLorryHireList[i].tripSheetId;
					tr1.append(td7.append('<a target="_blank" href='+curl+'>TS-'+vendorLorryHireList[i].tripSheetNumber+'</a>'));
				}else{
					var curl = "addTripSheetEntries.in?busBookingDetailsId="+vendorLorryHireList[i].busBookingDetailsId;
					tr1.append(td7.append(
							'<div class="btn-group">'
							+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
							+'<ul class="dropdown-menu pull-right">'
							+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Create TripSheet</a></li>'
							+'<li><a href="#" class="confirmation" onclick="deleteBusBooking('+vendorLorryHireList[i].busBookingDetailsId+','+data.SelectPage+')"><span class="fa fa-trash"></span> Delete</a></li>'
							+'</ul>'
							+'</div>'
					));
				}*/
				
				tbody.append(tr1);
			}
		}
		
		$("#VendorPaymentTable").append(thead);
		$("#VendorPaymentTable").append(tbody);
		
		$("#navigationBar").empty();
		
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBookingDetails(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBookingDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseBookingDetails('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBookingDetails('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1 ||data.deploymentLog.totalPages != undefined) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBookingDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBookingDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
	}
	
	
}

function deleteBusBooking(busBookingDetailsId) {
	if (confirm("Are you sure?") == true) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["busBookingDetailsId"]			= busBookingDetailsId;

		$.ajax({
			url: "deleteBusBooking.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage('success', 'Data Deleted Successfully !');
				getPageWiseBookingDetails(1);
			},
			error: function (e) {
				console.log("Error : " , e);
			}
		});
	}
	
}