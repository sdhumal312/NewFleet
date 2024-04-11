$(document).ready(function() {
	$('#partLocationLoStock').select2();
});

function validateLowStockLevel(){
	if(Number($('#partLocationLoStock').val()) <=0){
		showMessage('info', 'Please select part location !');
		return false;
	}
	if(Number($('#lowstocklevel').val()) <=0){
		showMessage('info', 'Please enter low stock level !');
		return false;
	}
	if(Number($('#reorderquantity').val()) <=0){
		showMessage('info', 'Please enter re-order quantity !');
		return false;
	}
	return true;
}

function removeLowStockDetails(lowStockInventoryId){
	var jsonObject			= new Object();
	jsonObject["lowStockInventoryId"]	= lowStockInventoryId;
	
	$.ajax({
		url: "removeLowStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.removed != undefined && data.removed){
				showMessage('success', 'Data Deleted successfully !');
				location.replace('showMasterParts.in?partid='+$('#partid').val());
			}
		},
		error: function (e) {
			hideLayer();
			$('#savePartRow').show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}