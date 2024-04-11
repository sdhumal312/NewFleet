$(document).ready(function() {
	showLayer();
	var jsonObject			= new Object();
	jsonObject["batteryId"] =  $('#batteryId').val();
	$.ajax({
             url: "getBatteryInfo",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setBatteryInfoData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
});
function setBatteryInfoData(data){
	if(data.battery != undefined && data.battery != null){
		var battery = data.battery;
		if(battery.batteryUsesStatusId == 1){
			var span = '<span class="label label-pill label-success">'+battery.batteryUsesStatus+'</span>';
		}else{
			var span = '<span class="label label-pill label-warning">'+battery.batteryUsesStatus+'</span>';
		}
		$('#batteryNo').html(span + ' '+ battery.batterySerialNumber);
		$('#manufacturer').html(battery.manufacturerName);
		$('#batteryType').html(battery.batteryType);
		$('#capacity').html(battery.batteryCapacity);
		$('#location').html(battery.locationName);
		$('#scrapedBy').html(battery.batteryScrapBy);
		$('#scrapedDate').html(battery.scrapedDate);
		$('#batteryAmount').html(battery.batteryAmount.toFixed(2));
		$('#vehicleRegistration').html(battery.vehicle_registration);
		$('#openOdodmeter').html(battery.openOdometer);
		$('#closedOdometer').html(battery.closedOdometer);
		$('#purchaseDate').html(battery.purchaseDate);
		$('#batteryAsignDate').html(battery.asignedDate);
		$('#runningKM').html(battery.batteryUsesOdometer);
		$('#createdBy').html(battery.createdBy);
		$('#createdOn').html(battery.creationDate);
		$('#lastUpdatedBy').html(battery.lastmodifiedBy);
		$('#lastUpdatedOn').html(battery.lastModifiedDate);
		if(battery.batteryStatusId == 1){
			 $("#status").addClass("label label-pill label-success");
		}else if(battery.batteryStatusId == 3){
			 $("#status").addClass("label label-pill label-danger");
			 $("#editBatteryNumber").addClass("hide");
		}else{
			 $("#status").addClass("label label-pill label-warning");
		}
		$('#status').html(battery.batteryStatus);
		if(battery.dismountBatteryStatus != undefined && battery.dismountBatteryStatus != ""){
			$('#dismountStatus').html(battery.dismountBatteryStatus);
			$("#dismountStatus").addClass("label label-pill label-warning");
		}
		
		$('#batterySerial').val(battery.batterySerialNumber);
		$('#batteryInvoiceId').val(battery.batteryInvoiceId);
		if(battery.warrantyStatus == 'Under warranty'){
			 $("#warrantyStatus").addClass("label label-pill label-success");
		}else{
			 $("#warrantyStatus").addClass("label label-pill label-warning");
		}
		$('#warrantyStatus').html(battery.warrantyStatus);
		$('#warrantyPeriod').html(battery.warrantyPeriodStr);
		$('#warrantyCounter').html(battery.availableWarrantyType);
		$('#costPerDay').html(battery.costPerDay);
		$('#costPerOdometer').html(battery.costPerOdometer);
		$('#usesNoOfTime').html(battery.usesNoOfTime);
		
		$('#Sublocation').html(battery.subLocation);
		
		$('#history').append('<a href="#" onclick ="viewBatteryHistory('+battery.batteryId+');">View History</a>');
	}
}

function viewBatteryHistory(batteryId){
	showLayer();
	var jsonObject			= new Object();
	jsonObject["batteryId"] =  $('#batteryId').val();
	$.ajax({
             url: "viewBatteryHistory",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 renderModelData(data)
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}

$(document).ready(
		function($) {
			$('button[id=updateSerialNumber]').click(function(e) {
				e.preventDefault();
			showLayer();
			var jsonObject			= new Object();
			jsonObject["batteryId"] 			=  $('#batteryId').val();
			jsonObject["batterySerialNumber"] 	=  $('#batterySerial').val();
			jsonObject["batteryInvoiceId"]		=  $('#batteryInvoiceId').val();
			
			$.ajax({
		             url: "updateBatterySerialNumber",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	if(data.already == true || data.already == "true"){
		            		hideLayer();
		            		showMessage('info','Duplicate Battery Serial Number Found');
		            		return false;
		            	}else{
		            		$('#editBatterySerialNumber').modal('hide');
		            		$('#batteryNo').html($('#batterySerial').val());
		            		showMessage('info','Updated successfully ');
		            		hideLayer();
		            	}
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
		});
	});

function renderModelData(data){
	if(data.batteryHistoryList != undefined && data.batteryHistoryList.length > 0){
		$('#dataTable').show();
		$("#tableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
	    var position = "";
		for(var i = 0; i< data.batteryHistoryList.length; i++){
			
			var batteryHistory = data.batteryHistoryList[i];
			if(batteryHistory.vehicle_registration == null){
				vehicleUrl	= '--';
			}else{
				vehicleUrl	= '<a target="_blank" href="showVehicle?vid='+batteryHistory.vid+'">'+""+batteryHistory.vehicle_registration+'</a>';
			}
			if(batteryHistory.layoutPosition != 0){
				position = 'Battery - '+batteryHistory.layoutPosition;
			}else{
				position = '--';
			}
			var tr =' <tr>'
					+'<td class="fit">'+batteryHistory.batteryAsignDateStr+'</td>'
					+'<td>'+batteryHistory.batterySerialNumber+'</td>'
					+'<td>'+vehicleUrl+'</td>'
					+'<td>'+position+'</td>'
					+'<td>'+batteryHistory.batteryStatus+'</td>'
					+'<td>'+batteryHistory.openOdometer+'</td>'
					+'<td>'+batteryHistory.batteryUseage+'</td>'
					+'<td>'+batteryHistory.usesNoOfDay+'</td>'
					+'<td>'+batteryHistory.batteryComment+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
		$('#editrenewalPeriod').modal('show');
	}else{
		$('#dataTable').hide();
		showMessage('info', 'No record found !');
	}
}

