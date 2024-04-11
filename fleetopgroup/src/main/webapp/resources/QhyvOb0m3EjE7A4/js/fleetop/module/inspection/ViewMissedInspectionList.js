$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}),

$(document).ready(function() {
	$('#ReportSelectVehicle').select2();
	$('#reportDate').select2();
});

$(document).ready(
		function($) {
			$('button[id=btn-save]').click(function(e) {
				e.preventDefault();

				var jsonObject					= new Object();
				jsonObject["vid"] 	  			=  $('#ReportSelectVehicle').val();
				jsonObject["dateRange"] 	  	=  $('#reportDate').val();
				
				
				if(Number($('#ReportSelectVehicle').val()) <= 0){
					showMessage('errors', 'Please Select Vehicle !');
					return false;
				}
				
				if(Number($('#reportDate').val()) <= 0){
					$('#reportDate').focus();
					showMessage('errors', 'Please Select Date!');
					return false;
				}
				
				showLayer();
				$.ajax({
			             url: "getMissedInspectionListByVehicle",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                setMissedInspectionListByVehicle(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
			});

		});

function setMissedInspectionListByVehicle(data) {
	$("#VendorPaymentTable").empty();

	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');

	tr1.append(th1.append("Sr No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("View"));
	tr1.append(th4.append("Inspect"));
	tr1.append(th5.append("Edit"));
	tr1.append(th6.append("Status"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.missedInspectionByVehicle != undefined && data.missedInspectionByVehicle.length > 0) {
		
		var missedInspectionByVehicle = data.missedInspectionByVehicle;
	
		for(var i = 0; i < missedInspectionByVehicle.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			
			tr1.append(td1.append(i + 1));
			
			var curl = "showVehicle.in?vid="+missedInspectionByVehicle[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+missedInspectionByVehicle[i].vehicle_registration+'</a><br>'));
			
			if(missedInspectionByVehicle[i].completionDetailsId != undefined && missedInspectionByVehicle[i].completionDetailsId != null){
				var curl1 = "viewInspectVehicleDetails?vid="+missedInspectionByVehicle[i].vid+"&ID="+missedInspectionByVehicle[i].completionDetailsId
				tr1.append(td3.append('<a class="btn btn-success" href="' + curl1 + '">View</a><br>'));
			} else {
				tr1.append(td3.append(""));
			}
			
			if(missedInspectionByVehicle[i].inspectionStatusId != undefined && missedInspectionByVehicle[i].inspectionStatusId != 2){
				var curl2 = "inspectVehicle?vid="+missedInspectionByVehicle[i].vid+"&ID="+missedInspectionByVehicle[i].vehicleInspctionSheetAsingmentId+"&DR="+data.inspectionDate
				tr1.append(td4.append('<a class="btn btn-btn-info" href="' + curl2 + '">Inspect</a><br>'));
			} else {
				tr1.append(td4.append("INSPECTED"));
			}
			
			if(missedInspectionByVehicle[i].inspectionStatusId != undefined && missedInspectionByVehicle[i].inspectionStatusId != 2){
				tr1.append(td5.append(""));
			} else {
				var curl3 = "editInspectionDetails?vid="+missedInspectionByVehicle[i].vid+"&ID="+missedInspectionByVehicle[i].vehicleInspctionSheetAsingmentId
				tr1.append(td5.append('<a class="btn btn-btn-info" href="' + curl3 + '">Edit</a><br>'));
			}
			
			tr1.append(td6.append((missedInspectionByVehicle[i].inspectionStatusName)));	

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
}

function getMissedDatesForVehicle(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["vid"] 	  			=  $('#ReportSelectVehicle').val();
	$('#reportDate').select2('data', null);
	$.ajax({
             url: "getMissedInspectedDatesByVId",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 var options = '<option value="">Please select</option>';
            	 for(var i =0; i< data.dateLisst.length; i++){
            		 options += '<option value="'+data.dateLisst[i]+'">'+data.dateLisst[i]+'</option>';
            	 }
            	 $('#reportDate').html(options);
                hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
             }
	});
}