$(document).ready(function() {
	getTripSheetPickAndDropDetails(1);
});

function getTripSheetPickAndDropDetails(pageNumber) {
	
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;
	jsonObject["vid"]				= $('#vid').val();
	
	console.log("ola..",jsonObject);

	$.ajax({
		url: "getVehicleWiseTripSheetPickAndDropDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data : ', data);
			setTripSheetPickAndDropDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setTripSheetPickAndDropDetails(data) {
	console.log('data123 : ', data);
	$("#VendorPaymentTable").empty();
	
	var editPermision = false;
	if($("#editPermision").val() != undefined && $("#editPermision").val() == "true"){
		editPermision = true;
	}
	
	var deletePermision = false;
	if($("#deletePermision").val() != undefined && $("#deletePermision").val() == "true"){
		deletePermision = true;
	}
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	if(editPermision){
		var th12	= $('<th class="fit ar">');
	}
	if(deletePermision){
		var th13	= $('<th class="fit ar">');
	}

	tr1.append(th1.append("Tripsheet No"));
	tr1.append(th2.append("Vehicle No"));
	tr1.append(th3.append("Party Name"));
	tr1.append(th4.append("Driver"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Pick/Drop Status"));
	tr1.append(th7.append("Pick/Drop Point"));
	tr1.append(th8.append("Rate/KM"));
	tr1.append(th9.append("Usage KM"));
	tr1.append(th10.append("Amount"));
	tr1.append(th11.append("Advance"));
	if(editPermision){
		tr1.append(th12.append("Edit"));
	}
	if(deletePermision){
		tr1.append(th13.append("Delete"));
	}

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.PickOrDrop != undefined && data.PickOrDrop.length > 0) {
		
		var PickOrDrop = data.PickOrDrop;
	
		for(var i = 0; i < PickOrDrop.length; i++) {
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
			if(editPermision){
				var td12	= $('<td class="fit ar">');
			}
			if(deletePermision){
				var td13	= $('<td class="fit ar">');
			}
			
			tr1.append(td1.append('<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+PickOrDrop[i].tripsheetPickAndDropId+'" target="_blank">TS-'+PickOrDrop[i].tripSheetNumber+'</a>'));
			tr1.append(td2.append(PickOrDrop[i].vehicleRegistration));
			
			if(PickOrDrop[i].vendorId > 0){
				tr1.append(td3.append(PickOrDrop[i].vendorName));
			} else {
				tr1.append(td3.append(PickOrDrop[i].newVendorName));
			}
			
			tr1.append(td4.append(PickOrDrop[i].driverName));
			tr1.append(td5.append(PickOrDrop[i].journeyDateStr2));
			tr1.append(td6.append(PickOrDrop[i].pickOrDropStatusStr));
			
			if(PickOrDrop[i].pickOrDropId > 0){
				tr1.append(td7.append(PickOrDrop[i].locationName));
			} else {
				tr1.append(td7.append(PickOrDrop[i].newPickOrDropLocationName));
			}
			
			tr1.append(td8.append(PickOrDrop[i].rate));
			tr1.append(td9.append(PickOrDrop[i].tripUsageKM));
			tr1.append(td10.append((PickOrDrop[i].amount).toFixed(2)));	
			tr1.append(td11.append((PickOrDrop[i].tripTotalAdvance).toFixed(2)));
			if(editPermision){
				tr1.append(td12.append(('<a href="editPickAndDropTrip.in?editPickAndDropId='+PickOrDrop[i].tripsheetPickAndDropId+'" target="_blank"><i class="fa fa-edit"></i>Edit</a>')));	
			}
			if(deletePermision){
				tr1.append(td13.append(('<a href="#" class="confirmation" onclick="deleteTripsheetPickAndDrop('+PickOrDrop[i].tripsheetPickAndDropId+')"><span class="fa fa-trash"></span> Delete</a>')));	
			}
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getTripSheetPickAndDropDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getTripSheetPickAndDropDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getTripSheetPickAndDropDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getTripSheetPickAndDropDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetPickAndDropDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetPickAndDropDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function searchTripsheet(){
	
	if($("#tripStutes").val()  != null && $("#tripStutes").val().trim() != ''){
		showLayer();
		var jsonObject						= new Object();
		jsonObject["tripsheetNumber"]		= $("#tripStutes").val();

		$.ajax({
			url: "searchTripsheetNumber",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log('data : ', data);
				
				if(data.tripsheetNoFound != undefined && data.tripsheetNoFound == true){
					window.location.replace("showDispatchedPickAndDropTrip?dispatchPickAndDropId="+data.tripsheetPickAndDropId+"");
				} else {
					hideLayer();
					showMessage('info', 'Tripsheet Number Not Found. Please Enter correct Tripsheet Number');
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
		
	} else {
		showMessage('info', 'Please Enter correct Tripsheet Number');
	}
	
}

function deleteTripsheetPickAndDrop(dispatchPickAndDropId) {
	
	console.log("dispatchPickAndDropId...",dispatchPickAndDropId);
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject							= new Object();
		jsonObject["tripsheetPickAndDropId"]	= dispatchPickAndDropId;
		
		$.ajax({
			url: "deleteTripsheetPickAndDrop.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				window.location.replace("newTripSheetPickAndDrop");
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
	
}