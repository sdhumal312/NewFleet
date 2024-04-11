
$(document).ready(function() {
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		getIssuesactivityCount();
	}, 500);
});

function dateChange(){
	getIssuesactivityCount()
}


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});


function getIssuesactivityCount(){
	showLayer();
	if(!validateOnchange()){
		return false;
	}
	$('#IssueActivityDataBody').empty();
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
		url : "getIssuesActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			$(".main-body").show();
			$("#issuesCreateCount").text(data.issuesCreatedCount);
			$("#issuesUpdatedCount").text(data.issuesUpdatedCount);
			$("#issuesDeletedCount").text(data.issuesDeletedCount);
			$("#issuesCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#issuesModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#issuesDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
			$('#ResultContent').hide();
			$('#userActivityListTable').hide();
			backGroundColour(0);
			hideLayer();
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
	$('#showUserName').show();
	$('#IssueActivityDataBody').empty();
	if(!validateOnchange()){
		return false;
	}
	showLayer();
	var jsonObject = new Object();	
	jsonObject["userId"] = $('#subscribe').val();
	
	$('#ResultContent').hide();
	$('#userActivityListTable').hide();
	
	
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
			url : "getActivityIssuesData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				console.log("Here is the data ",$('#subscribe').val(),data)
				if(data.issuesCreatedData != undefined && data.issuesCreatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Issues Created List");
						renderReportData(data.issuesCreatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
					$('#tableCaption').text("");
					$('#tableCaption').text("Issues Created List");
					setIssueActivityData(data.issuesCreatedData,activityType)
					}
					
				}else if(data.issuesUpdatedData != undefined && data.issuesUpdatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Issues Updated List");
						renderReportData(data.issuesUpdatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("");
						$('#tableCaption').text("Issues Updated List");
					setIssueActivityData(data.issuesUpdatedData,activityType)
					}
				}else if(data.issuesDeletedData != undefined && data.issuesDeletedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Issues Deleted List");
						renderReportData(data.issuesDeletedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("");
						$('#tableCaption').text("Issues Deleted List");
						setIssueActivityData(data.issuesDeletedData,activityType)
					}
				}	
				else{
					hideLayer();
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#IssueActivityDataBody').empty();
				}
			},
			error: function (e) {
			hideLayer();
	
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function setIssueActivityData(data,activityType){
	showLayer();
	var list 			= data;
	var renewalAmount = 0;
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#IssueActivityDataBody').empty();
	for(var index = 0 ; index < list.length; index++){
		var columnArray = new Array();
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit'ar'>" +list[index].issuesNumberStr  +"</td>");	
		columnArray.push("<td class='fit'ar'>" + list[index].issues_SUMMARY   +"</td>");
		columnArray.push("<td class='fit'ar>"+ list[index].issues_VEHICLE_REGISTRATION  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].issues_TYPE  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].issues_ASSIGN_TO_NAME +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].issues_STATUS +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].created_DATE +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].lastupdated_DATE +"</td>");
		
		$('#IssueActivityDataBody').append("<tr id='fid"+list[index].renewal_R_Number+"' >" + columnArray.join(' ') + "</tr>");
		hideLayer();
	}

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
