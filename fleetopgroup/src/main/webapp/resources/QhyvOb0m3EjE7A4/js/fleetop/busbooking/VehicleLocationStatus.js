var busLocationIdToUpdate	= 0;

function saveLocation() {
	if(!validateLocationDetails())
		return false;
		
	var jsonObject = new Object();
	
	jsonObject["busBookingDate"]		= $('#locationDate').val();
	jsonObject["vehicleId"]				= $('#vehicleId').val();
	jsonObject["driveId"]				= $('#driverAllList').val();
	jsonObject["sourceLocationId"]		= $('#warehouselocation').val();
	jsonObject["destinationLocationId"]	= $('#Partwarehouselocation').val();
	
	$.ajax({
		type: "POST",
		url: "saveBusLocationData",
		data: jsonObject,
		success: function (data) {
			if(data && data.message) {
				showMessage('errors', data.message);
				return;
			}
			
			if(data && data.success) {
				showMessage('success', 'Bus Created Successfuly');
				//$("#select2-chosen-1").html("Vehicle No");
				$('#vehicleId').val('');
				$('#driverAllList').val('');
				$('#warehouselocation').val(0);
				$('#Partwarehouselocation').val(0);
				
				setTimeout(function() {
					location.reload();
				}, 200);
				
			}
		}, error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
	
function validateLocationDetails(){
	if($('#locationDate').val() == undefined || Number($('#locationDate').val()) ==  0){
		showMessage('errors','Please Select Date !');
		$('#locationDate').focus();
		return false;
	}
	
	if($('#vehicleId').val() == undefined || Number($('#vehicleId').val()) ==  0){
		showMessage('errors','Please Select Vehicle No !');
		$('#vehicleId').focus();
		return false;
	}


	if($('#driverAllList').val() == undefined || ($('#driverAllList').val()) ==  0){
		showMessage('errors','Please Select Driver NAME !');
		$('#driverAllList').focus();
		return false;
	}
	
	if($('#warehouselocation').val() == undefined || ($('#warehouselocation').val()) ==  0){
		showMessage('errors','Please Select Source Location !');
		$('#warehouselocation').focus();
		return false;
	}
	
	if($('#Partwarehouselocation').val() == undefined || ($('#Partwarehouselocation').val()) ==  0){
		showMessage('errors','Please Select Destination Location !');
		$('#Partwarehouselocation').focus();
		return false;
	}
	
	return true;
}

$(function() {
    function a(a, b) {
        $("#dateRangeReport").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
});

function searchData() {
	var dateRange		= $('#dateRangeReport').val().split("to");
	var startDate		= dateRange[0];
	var endDate			= dateRange[1];
	
	var jsonObject	= new Object();
					
	jsonObject["startDate"] 					= startDate;
	jsonObject["endDate"] 						= endDate;
	
	$.ajax({					
					url: "getBusLocationReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						var dateWiseMap 	= data.dateWiseInMap;
						var dateWiseOutMap	= data.dateWiseOutMap;
						
						if((dateWiseMap == undefined || dateWiseMap == null) && (dateWiseOutMap == undefined || dateWiseOutMap == null)) {
							showMessage('info', 'Data not found');
							$('#tabledata').addClass('hide');
							return;
						}
						
						if(dateWiseMap != undefined && dateWiseMap != null)
							setRenderReportData(data);	
						
						if(dateWiseOutMap != undefined && dateWiseOutMap != null)
							setRenderReporOutData(data);
							
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});
}

function setRenderReportData(data){
	$("#multipleTable").empty();
	$("#busLocationTable").empty();
	
	var dateWiseMap 	= data.dateWiseInMap;
	
	$('#tabledata').removeClass('hide');
	
	var i = 0;
	
	for (let key in dateWiseMap) {
		var busLocationList	= dateWiseMap[key];
		
		var table = $('<table id = "busLocationTable_' + i + '" class = "table table-hover table-bordered"></table>');
	
		var columnArray = new Array();
		
		columnArray.push("<th class='fit ar' colspan = '2'>Date : " + key + "</th>");
		columnArray.push("<th class='fit ar' colspan = '3'>Status : IN</th>");
		
		$(table).append("<tr>" + columnArray.join(' ') + "</tr>");
		
		var columnArray = new Array();
		
		columnArray.push("<th class='fit ar'>Vehicle No</th>");
		columnArray.push("<th class='fit ar'>From</th>");
		columnArray.push("<th class='fit ar'>To</th>");
		columnArray.push("<th class='fit ar'>Driver</th>");
		columnArray.push("<th class='fit ar'></th>");
		
		$(table).append("<tr>" + columnArray.join(' ') + "</tr>");
		
		for(var index = 0 ; index < busLocationList.length; index++){
			var columnArray = new Array();
				
			columnArray.push("<td class='fit ar'>  "+ busLocationList[index].vehicleNumber  +"</td>");
			columnArray.push("<td class='fit ar'> "+ busLocationList[index].sourceLocationName  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ busLocationList[index].destinationLocationName  +"</td>");
			columnArray.push("<td class='fit ar'>"+ busLocationList[index].driverName  +"</td>");
			columnArray.push("<td class='fit ar'><button id='myBtn' class='btn btn-primary' onclick = 'outVehicle(" + busLocationList[index].busLocationId + ")'>Out</button></td>");
			
			$(table).append("<tr id='penaltyID"+busLocationList[index].vehicleNumberMatserId+"' >" + columnArray.join(' ') + "</tr>");
		}
		
		$('#multipleTable').append(table);
		
		i++;
	}
}

 function setRenderReporOutData(data){
	
	var dateWiseOutMap	= data.dateWiseOutMap;
	
	$('#tabledata').removeClass('hide');
	
	var i = 0;
	
	for (let key in dateWiseOutMap) {
		var busLocationList	= dateWiseOutMap[key];
		
		var table = $('<table id = "busLocationTable_' + i + '" class = "table table-hover table-bordered"></table>');
	
		var columnArray = new Array();
		
		columnArray.push("<th class='fit ar' colspan = '2'>Date : " + key + "</th>");
		columnArray.push("<th class='fit ar' colspan = '2'>Status : OUT</th>");
		
		$(table).append("<tr>" + columnArray.join(' ') + "</tr>");
		
		var columnArray = new Array();
		
		columnArray.push("<th class='fit ar'>Vehicle No</th>");
		columnArray.push("<th class='fit ar'>From</th>");
		columnArray.push("<th class='fit ar'>To</th>");
		columnArray.push("<th class='fit ar'>Driver</th>");
		
		$(table).append("<tr>" + columnArray.join(' ') + "</tr>");
		
		for(var index = 0 ; index < busLocationList.length; index++){
			var columnArray = new Array();
				
			columnArray.push("<td class='fit ar'>  "+ busLocationList[index].vehicleNumber  +"</td>");
			columnArray.push("<td class='fit ar'> "+ busLocationList[index].destinationLocationName  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ busLocationList[index].sourceLocationName  +"</td>");
			columnArray.push("<td class='fit ar'>"+ busLocationList[index].driverName  +"</td>");
			
			$(table).append("<tr id='penaltyID"+busLocationList[index].vehicleNumberMatserId+"' >" + columnArray.join(' ') + "</tr>");
		}
		
		$('#multipleTable').append(table);
		
		i++;
	}
}

 
function outVehicle(busLocationId) {
	busLocationIdToUpdate	= busLocationId;
	$("#outBusLocationPanel").modal({});
}

function updateOutDateTime() {
	var jsonObject = new Object();
	
	jsonObject["busOutDateTime"]	= $('#dealerServiceSearchDate').val();
	jsonObject["busLocationId"]		= busLocationIdToUpdate;
	
	console.log(jsonObject)
	
	$.ajax({
		type: "POST",
		url: "updateBuslocationOutTime",
		data: jsonObject,
		success: function (data) {
			if(data && data.success) {
				showMessage('success', 'Out time Updated Successfuly');
				$("#outBusLocationPanel").modal('hide');
				busLocationIdToUpdate	= 0;
				
				setTimeout(function() {
					searchData();
				}, 100);
			}
		}, error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}