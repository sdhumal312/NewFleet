
function getConsumptionData(dateType,pageNumber){
	var jsonObject								= new Object();
	
	var dateRange 	= $('#dateRange').val().split("to");
	
	if( dateRange != undefined && dateRange != "" && dateRange[0] != undefined && dateRange[1] != undefined){
		var startDateArr	= dateRange[0].split("-");
		var endDateArr		= dateRange[1].split("-");
		
		var finalStartDate	= startDateArr[2].trim()+"-"+startDateArr[1].trim()+"-"+startDateArr[0].trim();
		var finalEndDate	= endDateArr[2].trim()+"-"+endDateArr[1].trim()+"-"+endDateArr[0].trim();
		
		jsonObject["startDate"]		= finalStartDate.trim();
		jsonObject["endDate"]		= finalEndDate.trim();
		
	}
	if($('#companyDropdownConfig').val() == 'true' || $('#companyDropdownConfig').val() == true){
		jsonObject["companyId"]		= $("#companyId").val();
	}else{
		jsonObject["companyId"]		= $("#defaultCompanyId").val();
	}
	jsonObject["dateType"]			= dateType;
	jsonObject["consumptionType"]	= $("#consumptionType").val();;
	jsonObject["pageNumber"]		= pageNumber;
	
	showLayer();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "getConsumptionSummaryData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.fuelList != undefined && data.fuelList.length > 0){
				setFuelConsumptionData(data);
			}else{
				showMessage('info','No Record Found')
				$('#consumptionDataBody').empty();
				$("#entryCount").html(0);
				$("#totalConsumption").html(0);
				$("#totalAmount").html(0);
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setFuelConsumptionData(data){
	var fuelList 			= data.fuelList;
	var totalAmount			= 0;
	var totalConsumption	= 0;
	console.log("fuelList",fuelList)
	$('#consumptionDataBody').empty();
	$("#entryCount").html(0);
	$("#totalConsumption").html(0);
	$("#totalAmount").html(0);
	for(var index = 0 ; index < fuelList.length; index++){
		
		var columnArray = new Array();
		columnArray.push("<td class='fit'ar><a href='showFuel?FID="+fuelList[index].fuel_id+"' target='_blank'>"+ fuelList[index].fuelNumber  +"</a></td>");
		columnArray.push("<td class='fit ar'>" + fuelList[index].fuel_date +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+fuelList[index].vid+"' target='_blank'>"+ fuelList[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit'ar'>" + fuelList[index].fuel_liters.toFixed(2)  +"</td>");
		columnArray.push("<td class='fit ar'>" + fuelList[index].fuel_price.toFixed(2) +"</td>");
		columnArray.push("<td class='fit ar'>" + addCommas(fuelList[index].fuel_amount.toFixed(2)) +"</td>");
		
		$('#consumptionDataBody').append("<tr id='fid"+fuelList[index].fuel_id+"' >" + columnArray.join(' ') + "</tr>");
		totalAmount 		+= fuelList[index].fuel_amount;
		totalConsumption 	+= fuelList[index].fuel_liters;
		hideLayer();
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='3'>Total</td>");
	columnArray1.push("<td class='fit ar' colspan='2'>"+addCommas(totalConsumption.toFixed(2))+"</td>");
	columnArray1.push("<td class='fit ar'>"+addCommas(totalAmount.toFixed(2))+"</td>");
	
	$('#consumptionDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
	$("#entryCount").html(addCommas(data.fuelEntryCount));
	$("#totalConsumption").html(addCommas(totalConsumption.toFixed(2)));
	$("#totalAmount").html(addCommas(totalAmount.toFixed(2)));
	columnArray = [];
	

	/*$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getConsumptionData('+(data.dateType)+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getConsumptionData('+(data.dateType)+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}

	for (i = data.beginIndex; i <= data.endIndex; i++) {
		if(i == data.currentIndex) {
			$("#navigationBar").append('<li class="active"><a href="#" onclick="getConsumptionData('+(data.dateType)+','+i+')">'+i+'</a></li>');	    	
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getConsumptionData('+(data.dateType)+','+i+')">'+i+'</a></li>');	    	
		}
	} 

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getConsumptionData('+(data.dateType)+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getConsumptionData('+(data.dateType)+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}*/
}
