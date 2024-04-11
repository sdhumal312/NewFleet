$(document).ready(function() {
	var tripsheetPickAndDropId = $('#dispatchPickAndDropId').val();
	getTripsheetPickDropDispatchDetails(tripsheetPickAndDropId);
});

function getTripsheetPickDropDispatchDetails(tripsheetPickAndDropId) {
	
	showLayer();
	var jsonObject							= new Object();
	jsonObject["tripsheetPickAndDropId"]	= tripsheetPickAndDropId;

	$.ajax({
		url: "getTripsheetPickDropDispatchDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTripsheetPickDropDispatchDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTripsheetPickDropDispatchDetails(data){
	
	console.log("data...",data);
	
	$('#tripNumber').html('Trip Number : TS-'+data.pickOrDropDetails.tripSheetNumber);
	$('#createdDate').html('Journey Date : '+data.pickOrDropDetails.journeyDateStr);
	$('#vehicle').html(data.pickOrDropDetails.vehicleRegistration);
	$('[data-selector="driver"]').html(data.pickOrDropDetails.driverName);
	$('[data-selector="status"]').html(data.pickOrDropDetails.pickOrDropStatusStr);
	
	if(data.pickOrDropDetails.vendorId > 0){
		$('[data-selector="vendor"]').html(data.pickOrDropDetails.vendorName);
	} else {
		$('[data-selector="vendor"]').html(data.pickOrDropDetails.newVendorName);
	}
	
	if(data.pickOrDropDetails.pickOrDropId > 0){
		$('[data-selector="location"]').html(data.pickOrDropDetails.locationName);
	} else {
		$('[data-selector="location"]').html(data.pickOrDropDetails.newPickOrDropLocationName);
	}
	
	$('[data-selector="rate"]').html(data.pickOrDropDetails.rate);
	$('[data-selector="totalKm"]').html(data.pickOrDropDetails.tripUsageKM);
	$('[data-selector="totalAmount"]').html(data.pickOrDropDetails.amount);
	$('[data-selector="advance"]').html(data.pickOrDropDetails.tripTotalAdvance);
	$('[data-selector="remark"]').html(data.pickOrDropDetails.remark);
	
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