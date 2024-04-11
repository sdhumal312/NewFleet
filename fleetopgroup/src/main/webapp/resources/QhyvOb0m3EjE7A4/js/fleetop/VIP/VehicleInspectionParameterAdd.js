var vid;
var inspectionsheetId;
function showVehicles(vid,inspectionsheetId){	
	$('#myModal').modal('show');
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["vid"]		= vid;
		jsonObject["inspectionsheetId"]		= inspectionsheetId;
		console.log("jsonObject ",jsonObject)
		$.ajax({
			url: "getVehicleInspectionParameter.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data",data)
				setInspectionParameterInfo(data);
			},
			error: function (e) {
				hideLayer();
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
}


function setInspectionParameterInfo(data) {
	console.log('data',data)
	var inspectionInformation	= null;
	if(data.InspectionInformation != undefined) {
		$("#reportHeader").html("Vehicle Assigned to Inspection Sheet");

		$("#inspectNoOfVehicleToSheet").empty();
		inspectionInformation	= data.InspectionInformation;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		

		th1.append('Sr No');
		th2.append('Vehicle Assigned');
		
		tr1.append(th1);
		tr1.append(th2);
		
		thead.append(tr1);	

		for(var i = 0; i < inspectionInformation.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');			

			td1.append(i+1);
			td2.append(inspectionInformation[i].vehicle_registration);			

			tr.append(td1);
			tr.append(td2);			

			tbody.append(tr);
		}		
		
		$("#inspectNoOfVehicleToSheet").append(thead);
		$("#inspectNoOfVehicleToSheet").append(tbody);		
		
	} else {
		showMessage('info','No record found !');
		
	}
	setTimeout(function(){ hideLayer(); }, 500);
}


