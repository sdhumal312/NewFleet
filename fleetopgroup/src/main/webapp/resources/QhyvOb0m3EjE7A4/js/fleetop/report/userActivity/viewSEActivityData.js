
$(document).ready(function() {
	
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		
		getSEactivityCount();
		
	}, 500);
});


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});



function dateChange(){
	getSEactivityCount();
}

function getSEactivityCount(){
	showLayer();
	if(!validateOnchange()){
		return false;
	}
	
	$('#seActivityDataBody').empty();
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
	
	$.ajax({
		url : "getSEActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			$("#seCreateCount").text(data.serviceEntryCreatedCount);
			$("#seUpdatedCount").text(data.serviceEntryUpdatedCount);
			$("#seDeletedCount").text(data.serviceEntryDeletedCount);
			$("#seCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#seModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#seDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
			hideLayer();
			$('#userActivityListTable').hide();
			$('#ResultContent').hide();
			backGroundColour(0);
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
	if(!validateOnchange()){
		return false;
	}
	$('#seActivityDataBody').empty();
	$('#showUserName').show();
	$('#userActivityListTable').hide();
	$('#ResultContent').hide();
	showLayer();
	
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
			url : "getActivitySEData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				if(data.seCreatedData != undefined && data.seCreatedData.length > 0){
						if($('#subscribe').val() == 0){
							showLayer();
							$('#userActivityListTable').hide();
							$('#ResultContent').show();
							$("#reportHeader").html("Service Entries Create List");
							renderReportData(data.seCreatedData,data.tableConfig,activityType);
						}else{
							
							showLayer();
							$('#ResultContent').hide();
							$('#userActivityListTable').show()
							$('#tableCaption').text("Service Entries Create List");
							setSEActivityData(data.seCreatedData,activityType)
						}
				}else if(data.seUpdatedData != undefined && data.seUpdatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Service Entries Updated List");
						renderReportData(data.seUpdatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("Service Entries Updated List");
						setSEActivityData(data.seUpdatedData,activityType)
					}
				}else if(data.seDeletedData != undefined && data.seDeletedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Service Entries Deleted List");
						renderReportData(data.seDeletedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
					$('#tableCaption').text("Service Entries Deleted List");
					setSEActivityData(data.seDeletedData,activityType)
					}
				}	
				else{
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#seActivityDataBody').empty();
					$('#myGrid').empty();
					hideLayer();
				}
			},
			error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		})
}

function setSEActivityData(data,activityType){
	showLayer();
	var list 			= data;
	var totalCost = 0;
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#seActivityDataBody').empty();
	for(var index = 0 ; index < list.length; index++){
		var columnArray = new Array();
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit ar'>" +list[index].serviceEntriesNumberstr +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+list[index].vid+"' target='_blank'>"+ list[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit'ar'>" + list[index].vendor_name  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].invoiceNumber +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].created +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].lastupdated +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].totalserviceROUND_cost +"</td>");
		totalCost+=list[index].totalserviceROUND_cost;
		$('#seActivityDataBody').append("<tr id='fid"+list[index].serviceEntries_Number+"' >" + columnArray.join(' ') + "</tr>");
		hideLayer();
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='5'></td>");
	columnArray1.push("<td class='fit ar' colspan='1' align='centre'>Total</td>");
	columnArray1.push("<td class='fit ar' colspan='1'>"+totalCost.toFixed(2)+"</td>");
	$('#seActivityDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
	
	hideLayer();
}

function renderReportData(resultData,tableConfig,activityType) {
	
		if(resultData != undefined && resultData.length > 0 ){
			var columnConfiguration ;
			var tableProperties;
			$('#ResultContent').show();
			$("#companyTable tr").remove();
			$("#selectedReportDetails tr").remove();
			$('#myGrid').empty();
			if(tableConfig != undefined) {
				var ColumnConfig = tableConfig.columnConfiguration;
				var columnKeys	= _.keys(ColumnConfig);
				var bcolConfig		= new Object();
				for (var i = 0; i < columnKeys.length; i++) {
					var bObj	= ColumnConfig[columnKeys[i]];
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

