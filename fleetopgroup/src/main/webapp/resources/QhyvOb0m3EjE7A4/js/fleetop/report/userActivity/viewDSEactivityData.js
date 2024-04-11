
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
		url : "getDSEActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		success : function(data){
			$(".main-body").show();
			
			$("#CreatePoCount").text(data.CreatePoCount);
			$("#UpdatedPoCount").text(data.UpdatedPoCount);
			$("#DeletedPoCount").text(data.DeletedPoCount);
			
			$("#poCreatedDiv").wrap('<a href="#" onclick="getActivityData(1)"/>')
			$("#poModifiedDiv").wrap('<a href="#" onclick="getActivityData(2)"/>')
			$("#poDeletedDiv").wrap('<a href="#" onclick="getActivityData(3)"/>')
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
	var userId = $('#subscribe').val();
	jsonObject["userId"] = userId;
	
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
			url : "getActivityWiseDSEData",
			type : "POST",
			datatype : "json",
			data : jsonObject,
			success : function(data){
				if(data.tableData != undefined && data.tableData.length > 0){
						showLayer();
						$('#ResultContent').show();
						if(activityType == 1){
							$("#reportHeader").html("Dealer Service Created List");
						}else if(activityType == 2){
							$("#reportHeader").html("Dealer Service Updated List");
						}else if(activityType == 3){
							$("#reportHeader").html("Dealer Service Deleted List");
						}
						renderReportData(data.tableData,data.tableConfig,activityType,userId);
				}
				hideLayer();
			},
			error: function (e) {
			hideLayer();
	
			showMessage('errors', 'Some Error Occred!');
		}
		})
		
		
	
}

function renderReportData(resultData,tableConfig,activityType,userId) {
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
						if(userId >0 && bObj.tableDtoName == 'createdBy'){
							bObj.show = false;	
						}
						if(activityType == 3 && bObj.tableDtoName =='lastUpdatedDate'){
							bObj.labelid ='Deleted on';
						}
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
