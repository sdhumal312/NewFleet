
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
	jsonObject["consumptionType"]	= $("#consumptionType").val();
	jsonObject["pageNumber"]		= pageNumber;
	
	
	showLayer();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "getConsumptionSummaryData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.upholsteryList != undefined && data.upholsteryList.length >0 ){
				setUpholsteryConsumptionData(data);
			}else{
				showMessage('info','No Record Found')
				$('#consumptionDataBody').empty();
				$("#entryCount").html(0);
				$("#totalConsumption").html(0);
				$("#totalRemove").html(0);
				
				hideLayer();
			}
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
}

function setUpholsteryConsumptionData(data){
	var upholsteryList = data.upholsteryList;
	console.log("upholsteryList",upholsteryList)
	var totalAmount			= 0;
	var totalConsumption	= 0;
	var totalRemove			= 0;
	$('#consumptionDataBody').empty();
	$("#entryCount").html(0);
	$("#totalConsumption").html(0);
	$("#totalRemove").html(0);
	
	for(var index = 0 ; index < upholsteryList.length; index++){
		
		var columnArray = new Array();
		columnArray.push("<td class='fit ar'>" + upholsteryList[index].clothTypeName +"</td>");
		columnArray.push("<td class='fit'ar>"+ upholsteryList[index].stockTypeName  +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+upholsteryList[index].vid+"' target='_blank'>"+ upholsteryList[index].vehicleRegistration  +"</a></td>");
		columnArray.push("<td class='fit ar'>" + (upholsteryList[index].asignedQuantity).toFixed(2) +"</td>");
		columnArray.push("<td class='fit ar'>" + (upholsteryList[index].removedQuantity).toFixed(2) +"</td>");
		columnArray.push("<td class='fit'ar'>" + upholsteryList[index].createdOnStr  +"</td>");
		
		$('#consumptionDataBody').append("<tr id='vehicleClothInventoryHistoryId"+upholsteryList[index].vehicleClothInventoryHistoryId+"' >" + columnArray.join(' ') + "</tr>");
		
		totalConsumption 	+= upholsteryList[index].asignedQuantity;
		totalRemove 	+= upholsteryList[index].removedQuantity;
		hideLayer();
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='3'>Total</td>");
	columnArray1.push("<td class='fit ar'>"+totalConsumption.toFixed(2)+"</td>");
	columnArray1.push("<td class='fit ar' colspan='2'>"+totalRemove.toFixed(2)+"</td>");
	
	
	$('#consumptionDataBody').append("<tr  class='bg-gradient-blueThree card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
	$("#entryCount").html(data.entryCount);
	$("#totalConsumption").html(totalConsumption.toFixed(2));
	$("#totalRemove").html(totalRemove.toFixed(2));
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


