var mountStatus = 6;
var disMountStatus = 7;
var RotationStatus = 9;
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
			if(data.tyreList != undefined){
				setTyreConsumptionData(data);
			}else{
				showMessage('info','No Record Found')
				$('#consumptionDataBody').empty();
				$("#totalConsumption").html(0);
				$("#totalDisMount").html(0);
				$("#totalRotation").html(0);
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

function setTyreConsumptionData(data){
	var tyreList = data.tyreList;
	console.log("tyreList",tyreList)
	var totalAmount = 0;
	var totalMount = 0;
	var totalDisMount = 0;
	var totalRotation = 0;
	$('#consumptionDataBody').empty();
	$("#totalConsumption").html(0);
	$("#totalAmount").html(0);
	for(var index = 0 ; index < tyreList.length; index++){
		
		var columnArray = new Array();
		columnArray.push("<td class='fit'ar><a href='showTyreInfo?Id="+tyreList[index].tyre_ID+"' target='_blank'>"+ tyreList[index].tyre_NUMBER  +"</a></td>");
		columnArray.push("<td class='fit'ar'>" + tyreList[index].tyre_STATUS  +"</td>");
		columnArray.push("<td class='fit'ar'>" + tyreList[index].tyreModel  +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+tyreList[index].vehicle_ID+"' target='_blank'>"+ tyreList[index].vehicle_REGISTRATION  +"</a></td>");
		columnArray.push("<td class='fit ar'>" + tyreList[index].tyre_ASSIGN_DATE +"</td>");
		columnArray.push("<td class='fit ar'>" + addCommas((tyreList[index].tyre_COST).toFixed(2)) +"</td>");
		
		$('#consumptionDataBody').append("<tr id='fid"+tyreList[index].tyre_ID+"' >" + columnArray.join(' ') + "</tr>");
		totalAmount+= tyreList[index].tyre_COST;
		console.log("dsfs",tyreList[index].tyre_STATUS_ID)
		
		if(tyreList[index].tyre_STATUS_ID ==  mountStatus){
			totalMount += 1;
		}else if(tyreList[index].tyre_STATUS_ID ==  disMountStatus){
			totalDisMount += 1;
		}else if(tyreList[index].tyre_STATUS_ID ==  RotationStatus){
			totalRotation += 1;
		}
		
		hideLayer();
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='5'>Total</td>");
	columnArray1.push("<td class='fit ar'>"+totalAmount.toFixed(2)+"</td>");
	
	$('#consumptionDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
//	$("#totalConsumption").html(addCommas(data.totalConsumptionCount));
	$("#totalConsumption").html(addCommas(totalMount));
	$("#totalDisMount").html(addCommas(totalDisMount));
	$("#totalRotation").html(addCommas(totalRotation));
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
	}
*/
	}


