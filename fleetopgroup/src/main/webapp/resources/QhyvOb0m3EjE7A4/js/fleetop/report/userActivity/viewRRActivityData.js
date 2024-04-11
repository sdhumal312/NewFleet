
$(document).ready(function() {
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+" to "+$("#endDate").val());
		$('#subscribe').select2("data",{
			id: $('#userId').val(),
			text: $('#userName').val()
		});
		getRRactivityCount();
	}, 500);
});

function dateChange(){
	showLayer();
	getRRactivityCount()
}


$("#cancel,#cancel1").click(function(){
	window.location.replace("viewUserActivity");
	});


function getRRactivityCount(){
	showLayer();
	if(!validateOnchange()){
		return false;
	}
	
	$('#rrActivityDataBody').empty();
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
		url : "getRRActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			
			console.log("Se data",data);
			
			$(".main-body").show();
			
			$("#rrCreateCount").text(data.rrCreatedCount);
			$("#rrUpdatedCount").text(data.rrUpdatedCount);
			$("#rrDeletedCount").text(data.rrDeletedCount);
			$("#rrCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#rrModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#rrDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
		
			//getActivityData(1);
			$('#userActivityListTable').hide();
			$('#ResultContent').hide();
			backGroundColour(0);
			hideLayer();
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
	
	$('#rrActivityDataBody').empty();
	if(!validateOnchange()){
		return false;
	}
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
			url : "getActivityRRData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				console.log("Here is the data ",data)
				if(data.rrCreatedData != undefined && data.rrCreatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Renewal Reminder Create List");
						renderReportData(data.rrCreatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
						$('#tableCaption').text("Renewal Reminder Create List");
					setRRActivityData(data.rrCreatedData,activityType)
					}
					
				}else if(data.rrUpdatedData != undefined && data.rrUpdatedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Renewal Reminder Updated List");
						renderReportData(data.rrUpdatedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
					$('#tableCaption').text("Renewal Reminder Updated List");
					setRRActivityData(data.rrUpdatedData,activityType)
					}
				}else if(data.rrDeletedData != undefined && data.rrDeletedData.length > 0){
					if($('#subscribe').val() == 0){
						showLayer();
						$('#userActivityListTable').hide();
						$('#ResultContent').show();
						$("#reportHeader").html("Renewal Reminder Deleted List");
						renderReportData(data.rrDeletedData,data.tableConfig,activityType);
					}else{
						showLayer();
						$('#ResultContent').hide();
						$('#userActivityListTable').show()
					$('#tableCaption').text("Renewal Reminder Deleted List");
					setRRActivityData(data.rrDeletedData,activityType)
					}
				}	
				else{
					hideLayer();
					$('#tableCaption').text("");
					$('#showUserName').hide();
					$('#rrActivityDataBody').empty();
				}
			},
			error: function (e) {
			hideLayer();
	
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function setRRActivityData(data,activityType){
	showLayer();
	var list 			= data;
	var renewalAmount = 0;
	$('#showUserName').text('by '+$('#subscribe').select2('data').text);
	$('#rrActivityDataBody').empty();
	for(var index = 0 ; index < list.length; index++){
		var columnArray = new Array();
		if(activityType == 3){
			$('#deletedColoumName').text("Deleted Date");
		}else{
			$('#deletedColoumName').text("Last Updated Date");
		}
		columnArray.push("<td class='fit'ar'>" + list[index].renewal_number +"</td>");	
		columnArray.push("<td class='fit'ar><a href='showVehicle?vid="+list[index].vid+"' target='_blank'>"+ list[index].vehicle_registration  +"</a></td>");
		columnArray.push("<td class='fit'ar'>" + list[index].vehicleStatus   +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].vehicleGroup  +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].renewal_type +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].renewal_dateofRenewal +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].created +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].lastupdated +"</td>");
		columnArray.push("<td class='fit ar'>" + list[index].renewal_Amount +"</td>");
		
				renewalAmount+=list[index].renewal_Amount;
		$('#rrActivityDataBody').append("<tr id='fid"+list[index].renewal_R_Number+"' >" + columnArray.join(' ') + "</tr>");
		
	}
	var columnArray1 = new Array();
	columnArray1.push("<td class='fit ar' colspan='7'></td>");
	columnArray1.push("<td class='fit ar' colspan='1' align='centre'>Total :</td>");
	columnArray1.push("<td class='fit ar' colspan='1'>"+renewalAmount.toFixed(2)+"</td>");
	$('#rrActivityDataBody').append("<tr class='bg-gradient-blueOne card-img-holder text-white'> Total: " + columnArray1.join(' ') + "</tr>");
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

