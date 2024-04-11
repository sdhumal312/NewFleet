var finalCompId = 0;

$(function() {
	function a(a, b) {
		$("#dateRange").val(b.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	
	}, a);
	
});

$(document).ready(function() {
	var dateRange = getUrlParameter('DateRange');
	
	if(dateRange != undefined && dateRange != null && dateRange.trim() != ''){
		var dates 	= dateRange.split("to");
		startDate	= dates[0];
	 	endDate		= dates[1];
		
		$('#dateRange').val(startDate+' to '+endDate);
	}
	
	$("#companyId").empty();
		getCompany();

		getAllCountData();
});



function getCompany(){
	
	$.getJSON("getCompanyDetailsList.in", function(e) {
		
		companyList = e;//To get All Company Name
		
		var companyId = $('#compId').val();
		var companyName = 	$('#compName').val();
	
		$("#companyId").empty();
		$('#companyId').select2();
		
		var companyId 	= $('#compId').val();
		var companyName = 	$('#compName').val();
		
		
		$('#companyId').select2('data', {
			id : companyId,
			text : companyName
			});
		
		$("#companyId").append($("<option>").text("All ").attr("value",0));
		
		for(var k = 0; k <companyList.length; k++){
		$("#companyId").append($("<option>").text(companyList[k].company_name).attr("value", companyList[k].company_id));
		
		}
		
	});
	
	var SHOW_COMPANYLIST_DASHBOARD = $('#SHOW_COMPANYLIST_DASHBOARD').val(); 
	if(SHOW_COMPANYLIST_DASHBOARD == 'true' || SHOW_COMPANYLIST_DASHBOARD == true){
		$('#companyRow').show();
	}else{
		$('#companyRow').hide();
	}
}   




var flag = false;
var dashboardCount = 0;
function getAllCountData(){
	
	finalCompId = $("#compId").val();
	
	if($("#companyId").val() > 0){
		finalCompId = $("#companyId").val();
	}
	
	dashboardCount = 0;
	var dateShow = $('#dateRange').val();
	
	showLayer();
	
	var jsonObject	= new Object();
	
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0];
	var endDate		= dateRange[1];
	var compId		= finalCompId;
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= finalCompId;
	hideLayer();
	
	$.ajax({
		url: "getTripSheetCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#tripsheet1").hide();
			$("#tripsheet2").show();
		},
		success: function (data) {
			dashboardCount++;
			setTripsheetCountData(data);
			//setAllCountData(data);
			$('#dateRange').val(dateShow);
			$("#tripsheet2").hide();
			$("#tripsheet1").show();
			
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getFuelCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#fuel1").hide();
			$("#fuel2").show();
		},
		success: function (data) {
			dashboardCount++;
			setFuelCountData(data);
			//setAllCountData(data);
			$('#dateRange').val(dateShow);
			$("#fuel2").hide();
			$("#fuel1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getWorkOrderCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#workOrder1").hide();
			$("#workOrder2").show();
		},
		success: function (data) {
			dashboardCount++;
			setWorkOrderCountData(data);
			//setAllCountData(data);
			$('#dateRange').val(dateShow);
			$("#workOrder2").hide();
			$("#workOrder1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getServiceReminderCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#serviceReminder1").hide();
			$("#serviceReminder2").show();
		},
		success: function (data) {
			dashboardCount++;
			setServiceReminderCountData(data);
			//setAllCountData(data);
			$('#dateRange').val(dateShow);
			$("#serviceReminder2").hide();
			$("#serviceReminder1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getRRCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#renwalReminder1").hide();
			$("#renwalReminder2").show();
		},
		success: function (data) {
			dashboardCount++;
			setRRCountData(data);
			//setAllCountData(data);
			$('#dateRange').val(dateShow);
			$("#renwalReminder2").hide();
			$("#renwalReminder1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getIssueCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#issue1").hide();
			$("#issue2").show();
		},
		success: function (data) {
			dashboardCount++;
			setIssueCountData(data);
			//setCountData(data);
			$('#dateRange').val(dateShow);
			$("#issue2").hide();
			$("#issue1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getServiceEntryCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#serviceEntry1").hide();
			$("#serviceEntry2").show();
		},
		success: function (data) {
			dashboardCount++;
			setServiceEntryCountData(data);
			//setCountData(data);
			$('#dateRange').val(dateShow);
			$("#serviceEntry2").hide();
			$("#serviceEntry1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getPickAndDropCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#pickDrop1").hide();
			$("#pickDrop2").show();
		},
		success: function (data) {
			dashboardCount++;
			setPickAndDropCountData(data);
			//setCountData(data);
			$('#dateRange').val(dateShow);
			$("#pickDrop2").hide();
			$("#pickDrop1").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "tyreStockCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#showTyreSummary").hide();
			$("#tyreDivLoading").show();
		},
		success: function (data) {
			console.log("counttt",data)
			dashboardCount++;
			$("#tyreStockCount").html(data.tyreStockCount);
			$("#tyreDivLoading").hide();
			$("#showTyreSummary").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$("#workOrdersCount").attr("href", "getWorkOrderSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);// href dynamic Data to controller
	$("#issueCountDetails").attr("href", "getIssueSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);// href dynamic Data to controller
	$("#serviceEntryCountDetails").attr("href", "getServiceEntrySummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);// href dynamic Data to controller
	$("#tsCount").attr("href", "getTripSheetSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);
	$("#rrCount").attr("href", "getRenewalReminderSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);
	$("#srCount").attr("href", "getServiceReminderSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);
	$("#fCount").attr("href", "getFuelSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);
	$("#pickAndDropCountDetails").attr("href", "getPickAndDropSummaryData.in?DateRange="+$('#dateRange').val()+"to"+finalCompId);
	$("#viewTyreSummary").attr("href", "viewTyreSummary");
}

function setTripsheetCountData(data){
	if(data.tripSheetCount != null || data.tripSheetCount != undefined){
		$('#tripsheetCount').text(data.tripSheetCount);
	}else{
		$('#tripsheetCount').text("0");
	}
}	
function setFuelCountData(data){
	if(data.fuelCount != null || data.fuelCount != undefined){
		$('#fuelCount').text(data.fuelCount);
	}else{
		$('#fuelCount').text("0");
	}
	showBarGraph();
}	
function setWorkOrderCountData(data){
	if(data.workOrderCounts != null || data.workOrderCounts != undefined){
		$('#workOrderCount').text(data.workOrderCounts);
	}else{
		$('#workOrderCount').text("0");
	}
	showBarGraph();
}	
function setServiceReminderCountData(data){
	if(data.serviceReminderCount != null || data.serviceReminderCount != undefined){
		$('#serviceEntriesCount').text(data.serviceReminderCount);
	}else{
		$('#serviceEntriesCount').text("0");
	}
	showBarGraph();
}	
function setRRCountData(data){
	if(data.renewalCounts != null || data.renewalCounts != undefined){
		$('#RRCount').text(data.renewalCounts);
	}else{
		$('#RRCount').text("0");
	}
	showBarGraph();
}	
function setIssueCountData(data){
	console.log("ALL",data)
	if(data.issuesCount != null || data.issuesCount != undefined){
		$('#issueCount').text(data.issuesCount);
		
	}else{
		$('#issueCount').text("0");
	}
	showBarGraph();
}

function setServiceEntryCountData(data){
	console.log("setIssueCountData",data)
	if(data.serviceEntryCount != null || data.serviceEntryCount != undefined){
		$('#serviceEntryCount').text(data.serviceEntryCount);
		
	}else{
		$('#serviceEntryCount').text("0");
	}
	showBarGraph();
}

function setPickAndDropCountData(data){
	console.log("pickdata..",data);
	if(data.pickAndDropCount != null || data.pickAndDropCount != undefined){
		$('#pickAndDropCount').text(data.pickAndDropCount);
	}else{
		$('#pickAndDropCount').text("0");
	}
	showBarGraph();
}

function showBarGraph(){
	if(dashboardCount != 7){
		return;
	}
	var workOrderCount 		= Number($('#workOrderCount').text());
	var tripsheetCount 		= Number($('#tripsheetCount').text());
	var fuelCount 			= Number($('#fuelCount').text());
	var serviceEntriesCount = Number($('#serviceEntriesCount').text());
	var RRCount 			= Number($('#RRCount').text());
	var issueCount 			= Number($('#issueCount').text());
	var SECount 			= Number($('#serviceEntryCount').text());
	
	document.getElementById("chartContainer").innerHTML = '&nbsp;';
	document.getElementById("chartContainer").innerHTML = '<canvas id="bar-chartcanvas"></canvas>';
	
	var ctx1 = document.getElementById('bar-chartcanvas').getContext("2d");

	var gradientTripSheet = ctx1.createLinearGradient(0, 0, 0, 181);
	gradientTripSheet.addColorStop(0, 'rgb(255,191,150,1)');
	gradientTripSheet.addColorStop(1, 'rgb(254,112,150,1)');
    var gradientLegendTripSheet = 'linear-gradient(to right, #ffbf96, #fe7096) !important';
    
    var gradientFuel = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientFuel.addColorStop(0, 'rgb(144,202,249,1)');
    gradientFuel.addColorStop(1, 'rgb(4,126,223,1)');
    var gradientLegendFuel = 'linear-gradient(to right, #90caf9, #047edf) !important';
    
    var gradientWorkOrders = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientWorkOrders.addColorStop(0, 'rgb(132,217,210,1)');
    gradientWorkOrders.addColorStop(1, 'rgb(7,205,174,1)');
    var gradientLegendWorkOrders = 'linear-gradient(to right, #84d9d2, #07cdae) !important';
    
    var gradientServiceReminders = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientServiceReminders.addColorStop(0, 'rgb(218,140,255,1)');
    gradientServiceReminders.addColorStop(1, 'rgb(154,85,255,1)');
    var gradientLegendServiceReminders = 'linear-gradient(to right, #da8cff, #9a55ff) !important';
    
    var gradientRenewalReminders = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientRenewalReminders.addColorStop(0, 'rgb(255,51,0,1)');
    gradientRenewalReminders.addColorStop(1, 'rgb(255,51,0,1)');
    var gradientLegendRenewalReminders = 'linear-gradient(to right, #ff3300, #ff66ff) !important';
    
    var gradientIssues = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientIssues.addColorStop(0, 'rgb(51,204,255,1)');
    gradientIssues.addColorStop(1, 'rgb(255,153,204,1)');
    var gradientLegendIssues = 'linear-gradient(to right, #33ccff, #ff99cc) !important';
   
    var gradientSE = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientSE.addColorStop(0, 'rgb(255,247,32,1)');
    gradientSE.addColorStop(1, 'rgb(60,213,0,1)');
    var gradientLegendSE = 'linear-gradient(to right, #fff720, #3cd500) !important';

	var data = {
		labels : ["TripSheet", "Fuel", "WorkOrders", "ServiceReminders", "RenewalReminders", "Issues", "ServiceEntries"],
		
		datasets : [
			{
				label : "Summary Status",
				data : [tripsheetCount, fuelCount, workOrderCount, serviceEntriesCount, RRCount, issueCount, SECount],
				backgroundColor : [
					gradientTripSheet,
					gradientFuel,
					gradientWorkOrders,
					gradientServiceReminders,
					gradientRenewalReminders,
					gradientIssues,
					gradientSE
				],
				borderColor : [
					"#111",
					"#111",
					"#111",
					"#111",
					"#111",
					"#111",
					"#111"
				],
				borderWidth : 0.5
			}
			
		]
	};
	var options = {
		title : {
			display : true,
			position : "top",
			text : "Summary Status",
			fontSize : 17,
			fontColor : "#111"
		},
		legend : {
			display : false
		},
		scales : {
			yAxes : [{
				ticks : {
					min : 0
				}
			}],
			
			xAxes: [{
                barThickness: 35,  // number (pixels) or 'flex'
                maxBarThickness: 40 // number (pixels)
            }]
		}
	};
	var chart = new Chart( ctx1, {
		type : "bar",
		data : data,
		options : options
	});
	

	}

function getWorkOrderSummaryData(){
	
}

/*function getWorkOrderController(){
	console.log("giiiiiiii",$('#dbDate').val());
	console.log("giiiiiiii",$('#designDate').text());
	jsonObject = new Object();
	var dbDate			=  $('#dbDate').val();
	var designDate		=  $('#designDate').text();
	
	window.open('/getWorkOrderSummaryData?dbDate='+dbDate+', designDate='+designDate);
	$.ajax({
		url: "getWorkOrderSummaryData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAllCountData(data);
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}*/