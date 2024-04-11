
$(document).ready(function() {
	
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		
		getWOactivityCount();
		
	}, 500);
});


function dateChange(){
	showLayer();
	getWOactivityCount();
}


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});


function getWOactivityCount(){
	showLayer();
	if(!validateOnchange()){
		return false;
	}
	
	//$('#woActivityDataBody').empty();
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
		url : "getWOActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			$(".main-body").show();
			$("#woCreateCount").text(data.workOrderCreateCount);
			$("#woUpdatedCount").text(data.workOrderUpdateCount);
			$("#woDeletedCount").text(data.workOrderDeleteCount);
			$("#woCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#woModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#woDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
			$('#userActivityListTable').hide();
			$('#ResultContent').hide();
			backGroundColour(0);
			//getActivityData(1);
			hideLayer();
		}
	})
}
function getActivityData(activityType){
	backGroundColour(activityType);
	showLayer();
	$('#woActivityDataBody').empty();
	if(!validateOnchange()){
		return false;
	}
	
	$('#userActivityListTable').hide();
	$('#ResultContent').hide();
	
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
			url : "getActivityWOData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				$('#tableHead').show();
				$('#showUserName').show();
				hideLayer();
				if(data.woCreatedData != undefined && data.woCreatedData.length > 0){
						if($('#subscribe').val() == 0){
							showLayer();
							$('#userActivityListTable').hide();
							$('#ResultContent').show();
							$("#reportHeader").html("Work Order Create List");
							renderReportData(data.woCreatedData,data.tableConfig,activityType)
						}else{
							showLayer();
							$('#tableCaption').text("");
							$('#tableCaption').text("Work Order Create List");
							$('#ResultContent').hide();
							$('#userActivityListTable').show()
							setWOActivityData(data.woCreatedData,activityType)
							
						}
					
				}else if(data.woUpdatedData != undefined && data.woUpdatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide()
						$('#ResultContent').show();
						$("#reportHeader").html("Work Order Updated List");
						renderReportData(data.woUpdatedData,data.tableConfig,activityType)
					}else{
						showLayer();
						$('#tableCaption').text("");
						$('#tableCaption').text("Work Order Updated List");
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						setWOActivityData(data.woUpdatedData,activityType)
					}
					
					
				}else if(data.woDeletedData != undefined && data.woDeletedData.length > 0){
					
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide()
						$('#ResultContent').show();
						$("#reportHeader").html("Work Order Deleted List");
						renderReportData(data.woDeletedData,data.tableConfig,activityType)
					}else{
						showLayer();
						console.log("hey there");
						$('#tableCaption').text("");
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("Work Order Deleted List");
						setWOActivityData(data.woDeletedData,activityType)
					}
					
				}	
				else{
					hideLayer();
					$('#tableHead').hide();
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#woActivityDataBody').empty();
					$('#myGrid').empty();
					$('#ResultContent').hide();
					$('#userActivityListTable').hide()
					hideLayer();
				}
			},
			error: function (e) {
			hideLayer();
			$('#woActivityDataBody').empty();
			$('#myGrid').empty();
			$('#ResultContent').hide();
			$('#userActivityListTable').hide()
			
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function setWOActivityData(data,activityType){
	showLayer();
	var woList 			= data;
	var totalCost		= 0;
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#woActivityDataBody').empty();
	for(var index = 0 ; index < woList.length; index++){
		var columnArray = new Array();
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit ar'>" + woList[index].workorders_Numbers +"</td>");
		columnArray.push("<td class='fit'ar'>" + woList[index].start_date  +"</td>");
		columnArray.push("<td class='fit ar'>" + woList[index].due_date +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+woList[index].vehicle_vid+"' target='_blank'>"+ woList[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit ar'>" + woList[index].created +"</td>");
		columnArray.push("<td class='fit ar'>" + woList[index].lastupdated +"</td>");
		columnArray.push("<td class='fit ar'>" + woList[index].totalworkorder_cost +"</td>");
		
		$('#woActivityDataBody').append("<tr id='fid"+woList[index].workorders_Number+"' >" + columnArray.join(' ') + "</tr>");
		hideLayer();
		totalCost+=woList[index].totalworkorder_cost;
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='5'></td>");
	columnArray1.push("<td class='fit ar' colspan='1' align='centre'>Total</td>");
	columnArray1.push("<td class='fit ar' colspan='1'>"+totalCost.toFixed(2)+"</td>");
	$('#woActivityDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
	
}

function renderReportData(resultData,tableConfig,activityType) {
	
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




