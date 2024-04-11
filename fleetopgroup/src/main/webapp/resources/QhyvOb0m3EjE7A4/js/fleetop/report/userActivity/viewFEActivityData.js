
$(document).ready(function() {
	
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		
		getFEactivityCount();
		
	}, 500);
});


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});

function dateChange(){
	showLayer();
	getFEactivityCount();
	
}


function getFEactivityCount(){
	showLayer();
	
	if(!validateOnchange()){
		return false;
	}
	
	$('#feActivityDataBody').empty();
	
	var jsonObject = new Object();
	
	jsonObject["userId"] = $('#subscribe').val();
	
	console.log(jsonObject);
	
	var dateRange 	= $('#dateRange').val().split("to");
	
	if( dateRange != undefined && dateRange != "" && dateRange[0] != undefined && dateRange[1] != undefined){
		var startDateArr	= dateRange[0].split("-");
		var endDateArr		= dateRange[1].split("-");
		
		var finalStartDate	= startDateArr[2].trim()+"-"+startDateArr[1].trim()+"-"+startDateArr[0].trim();
		var finalEndDate	= endDateArr[2].trim()+"-"+endDateArr[1].trim()+"-"+endDateArr[0].trim();
		
		jsonObject["startDate"]		= finalStartDate.trim();
		jsonObject["endDate"]		= finalEndDate.trim();
		
	}
	$.ajax({
		url : "getFuelEntryActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			
			console.log("Se data",data);
			
			$(".main-body").show();
			
			$("#feCreateCount").text(data.fuelEntryCreatedCount);
			$("#feUpdatedCount").text(data.fuelEntryUpdatedCount);
			$("#feDeletedCount").text(data.fuelEntryDeletedCount);
			$("#feCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#feModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#feDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
				hideLayer();
			backGroundColour(0);
			$('#ResultContent').hide();
			$('#userActivityListTable').hide();
			//getActivityData(1);
			
		},
	error : function(e){
		hideLayer();
		showMessage('errors', 'Some Error Occred!');
	}
	})
}
function getActivityData(activityType){
	backGroundColour(activityType);
	$('#feActivityDataBody').empty();
	if(!validateOnchange()){
		return false;
	}
	$('#ResultContent').hide();
	$('#userActivityListTable').hide();
	showLayer();
	$('#showUserName').show();
	var jsonObject = new Object();	
	jsonObject["userId"] = $('#subscribe').val();
	
	
	var dateRange 	= $('#dateRange').val().split("to");
	
	if( dateRange != undefined && dateRange != "" && dateRange[0] != undefined && dateRange[1] != undefined){
		var startDateArr	= dateRange[0].split("-");
		var endDateArr		= dateRange[1].split("-");
		
		var finalStartDate	= startDateArr[2].trim()+"-"+startDateArr[1].trim()+"-"+startDateArr[0].trim();
		var finalEndDate	= endDateArr[2].trim()+"-"+endDateArr[1].trim()+"-"+endDateArr[0].trim();
		
		jsonObject["startDate"]		= finalStartDate.trim();
		jsonObject["endDate"]		= finalEndDate.trim();
	}
		jsonObject["activityType"]	= activityType;
	
		$.ajax({
			url : "getActivityFEData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				console.log("Here is the data ",data)
				if(data.feCreatedData != undefined && data.feCreatedData.length > 0){
					
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Fuel Entries Created List");
						renderReportData(data.feCreatedData,data.tableConfig,activityType);
					}else{
						showLayer();
					$('#ResultContent').hide();
					$('#userActivityListTable').show()
					$('#tableCaption').text("");
					$('#tableCaption').text("Fuel Entries Created List");
					setFEActivityData(data.feCreatedData,activityType)
					}
				}else if(data.feUpdatedData != undefined && data.feUpdatedData.length > 0){
					
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Fuel Entries Updated List");
						renderReportData(data.feUpdatedData,data.tableConfig,activityType);
					}else{
						
						showLayer();
					$('#ResultContent').hide();
					$('#userActivityListTable').show()
					$('#tableCaption').text("Fuel Entries Updated List");
					setFEActivityData(data.feUpdatedData,activityType)
					}
				}else if(data.feDeletedData != undefined && data.feDeletedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Fuel Entries Deleted List");
						renderReportData(data.feDeletedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("Fuel Entries Deleted List");
						setFEActivityData(data.feDeletedData,activityType)
					}
				}	
				else{
					hideLayer();
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#feActivityDataBody').empty();
				}
			},
			error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function setFEActivityData(data,activityType){
	showLayer();
	var list 			= data;
	var volumeTotal = 0;
	var amountTotal = 0;
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#feActivityDataBody').empty();
	for(var index = 0 ; index < list.length; index++){
		var columnArray = new Array();
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit'ar'>" + list[index].fuelNumber  +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+list[index].vid+"' target='_blank'>"+ list[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit'ar'>" + list[index].vendor_name   +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].fuel_date  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].created  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].lastupdated  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].fuel_liters +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].fuel_amount +"</td>");
		volumeTotal+=list[index].fuel_liters;
		amountTotal+=list[index].fuel_amount;
		$('#feActivityDataBody').append("<tr id='fid"+list[index].fuel_Number+"' >" + columnArray.join(' ') + "</tr>");
		hideLayer();
	}
	
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='5'></td>");
	columnArray1.push("<td class='fit ar' colspan='1' align='centre'>Total : </td>");
	columnArray1.push("<td class='fit ar' colspan='1'>"+volumeTotal.toFixed(2)+"</td>");
	columnArray1.push("<td class='fit ar' colspan='1'>"+amountTotal.toFixed(2)+"</td>");
	$('#feActivityDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");

}

function renderReportData(resultData,tableConfig,activityType) {
	
	console.log("reached here ");
		if(resultData != undefined && resultData.length > 0 ){
			var columnConfiguration ;
			var tableProperties;
			$('#ResultContent').show();
			$("#companyTable tr").remove();
			$("#selectedReportDetails tr").remove();
			$('#myGrid').empty();
			console.log("tableConfig before if  ");
			if(tableConfig != undefined) {
				console.log("tableConfig after if  ");
				var ColumnConfig = tableConfig.columnConfiguration;
				var columnKeys	= _.keys(ColumnConfig);
				var bcolConfig		= new Object();
				for (var i = 0; i < columnKeys.length; i++) {
					var bObj	= ColumnConfig[columnKeys[i]];
					
					console.log("bObj ",bObj);

					if (bObj.show != undefined && bObj.show == true) {
						bcolConfig[columnKeys[i]] = bObj;
					}
				}

				columnConfiguration	= _.values(bcolConfig);
				tableProperties	= tableConfig.tableProperties;

			}

			setSlickData(resultData, columnConfiguration, tableProperties);
			$('#gridContainer').show();
			$('#printBtn').removeClass('hide');
			hideLayer();
		}else{
			hideLayer();
			$('#ResultContent').hide();
			$('#printBtn').addClass('hide');
			showMessage('info', 'No record found !');
		}
		}

