
$(document).ready(function() {
	
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		
		getTSactivityCount();
		
	}, 500);
});


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});



function dateChange(){
	showLayer();
	getTSactivityCount();
	
}


function getTSactivityCount(){
	showLayer();
	
	$('#tsActivityDataBody').empty();
	if(!validateOnchange()){
		return false;
	}
	
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
		url : "getTripSheetActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			$(".main-body").show();
			$("#tsCreateCount").text(data.tripSheetCreateCount);
			$("#tsUpdatedCount").text(data.tripSheetUpdatedCount);
			$("#tsDeletedCount").text(data.tripSheetDeletedCount);
			$("#tsCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#tsModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#tsDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
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
	$('#userActivityListTable').hide();
	$('#ResultContent').hide();
	showLayer();
	
	$('#tsActivityDataBody').empty();
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
			url : "getActivityTSData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				
				$('#userActivityListTable').hide();
				$('#ResultContent').hide();
				
				if(data.tsCreatedData != undefined && data.tsCreatedData.length > 0){
					
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Trip Sheet Create List");
						renderReportData(data.tsCreatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("");
						$('#tableCaption').text("Trip Sheet Create List");
						setTSActivityData(data.tsCreatedData,activityType)
					}
					
				}else if(data.tsUpdatedData != undefined && data.tsUpdatedData.length > 0){
					
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Trip Sheet Updated List");
						renderReportData(data.tsUpdatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("");
						$('#tableCaption').text("Trip Sheet Updated List");
						setTSActivityData(data.tsUpdatedData,activityType)
					}
					
				}else if(data.tsDeletedData != undefined && data.tsDeletedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Trip Sheet Updated List");
						renderReportData(data.tsDeletedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("");
						$('#tableCaption').text("Trip Sheet Deleted List");
						setTSActivityData(data.tsDeletedData,activityType)
					}
				}	
				else{
					hideLayer();
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#tsActivityDataBody').empty();
					$('#myGrid').empty();
				}
			},
			error: function (e) {
			hideLayer();
			$('#tsActivityDataBody').empty();
			$('#myGrid').empty();
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function setTSActivityData(data,activityType){
	var list 			= data;
	showLayer();
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#tsActivityDataBody').empty();
	for(var index = 0 ; index < list.length; index++){
		var columnArray = new Array();
		
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit ar'>" +list[index].tripSheetNumberStr +"</td>");
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+list[index].vid+"' target='_blank'>"+ list[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit ar'>" + list[index].tripOpenDate +' to '+list[index].closetripDate +"</td>");
		columnArray.push("<td class='fit'ar'>" + list[index].vehicle_Group   +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].routeName  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].created  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].lastupdated  +"</td>");
		$('#tsActivityDataBody').append("<tr id='fid"+list[index].serviceEntries_Number+"' >" + columnArray.join(' ') + "</tr>");
		hideLayer();
	}
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
