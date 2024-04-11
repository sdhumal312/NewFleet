var CREATION_DATE_TYPE 		= 1;
var TRANSACTION_DATE_TYPE	= 2;
var dashboardCount 			= 0;

var CONSUMPTION_TYPE_FUEL				= 1;    
var CONSUMPTION_TYPE_UREA				= 2;    
var CONSUMPTION_TYPE_UPHOLSTERY			= 3;    
var CONSUMPTION_TYPE_WO					= 4;    
var CONSUMPTION_TYPE_SE					= 5;    
var CONSUMPTION_TYPE_TYRE				= 6;    
var CONSUMPTION_TYPE_BATTERY			= 7;    
var CONSUMPTION_TYPE_REFRESHMENT		= 8;    

$(document).ready(function() {
	
	$("#fuelConsumptionDiv").hide();
	$("#ureaConsumptionDiv").hide();
	$("#upholsteryConsumptionDiv").hide();
	$("#partConsumptionDiv").hide();
	$("#batteryConsumptionDiv").hide();
	$("#tyreConsumptionDiv").hide();
	$("#refreshmentConsumptionDiv").hide();
	
	getCompany();
	
	setTimeout(function(){
		getAllConsumptionCount(CREATION_DATE_TYPE);
	}, 500);
	
});

function getCompany(){
	
	$.getJSON("getCompanyInformationDetails.in", function(e) {
		companyList	= e;//To get All Company Name 
		$("#companyId").empty();
		$("#companyId").append($("<option>").text("Please Select Company ").attr("value",0));
		$('#companyId').select2();

		for(var k = 0; k <companyList.length; k++){
			$("#companyId").append($("<option>").text(companyList[k].company_name).attr("value", companyList[k].company_id));
		}

	});	
	
	if($('#companyDropdownConfig').val() == 'true' || $('#companyDropdownConfig').val() == true){
		$('#companyRow').show();
	}else{
		$('#companyRow').hide();
	}
	if($("#dateType").val() == ""){
		getAllConsumptionCount(CREATION_DATE_TYPE);
	}else{
		getAllConsumptionCount($("#dateType").val());
	}
}

$(function() {
    function a(a, b) {
        $("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
    	maxDate: new Date(),
		format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
});


function selectDateType(dateType){
	if(dateType == 1){
		$('#creationDateId').addClass('btn-success').removeClass('btn-default');
		$('#transactionDateId').addClass('btn-default').removeClass('btn-success');
		
	}else if(dateType == 2){
		$('#transactionDateId').addClass('btn-success').removeClass('btn-default');
		$('#creationDateId').addClass('btn-default').removeClass('btn-success');
	}
	
	$("#dateType").val(dateType);
	getAllConsumptionCount(dateType);
}

function dateChange(){
	if($("#dateType").val() == ""){
		getAllConsumptionCount(CREATION_DATE_TYPE);
	}else{
		getAllConsumptionCount($("#dateType").val());
	}
}
   

function getAllConsumptionCount(dateType){
	
	var jsonObject	= new Object();
	var startDateArr	 		= new Array();
	var endDateArr	 			= new Array();
	
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
	jsonObject["dateType"]		= dateType;
	
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "getFuelConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#fuelConsumptionGif").show();
			$("#fuelConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.fuelConsumptionCount != null || data.fuelConsumptionCount != undefined){
				$('#fuelEntryCount').text(addCommas(data.fuelEntryCount));
				$('#fuelConsumptionCount').text(addCommas(data.fuelConsumptionCount.toFixed(2)));
			}else{
				$('#fuelEntryCount').text(0);
				$('#fuelConsumptionCount').text(0);
			}
			$("#fuelConsumptionGif").hide();
			$("#fuelConsumptionDiv").show();
			
			$('#fuelConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_FUEL + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getUreaConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#ureaConsumptionGif").show();
			$("#ureaConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.ureaConsumptionCount != null || data.ureaConsumptionCount != undefined){
				$('#ureaEntryCount').text(addCommas(data.ureaEntryCount));
				$('#ureaConsumptionCount').text(addCommas(data.ureaConsumptionCount.toFixed(2)));
			}else{
				$('#ureaEntryCount').text(0);
				$('#ureaConsumptionCount').text(0);
			}
			$("#ureaConsumptionGif").hide();
			$("#ureaConsumptionDiv").show();
			
			$('#ureaConsumptionDiv').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_UREA + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getUpholsteryConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#upholsteryConsumptionGif").show();
			$("#upholsteryConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.upholsteryConsumptionCount != null || data.upholsteryConsumptionCount != undefined){
				$('#upholteryEntryCount').text(addCommas(data.upholteryEntryCount));
				$('#upholsteryConsumptionCount').text(addCommas(data.upholsteryConsumptionCount.toFixed(2)));
			}else{
				$('#upholteryEntryCount').text(0);
				$('#upholsteryConsumptionCount').text(0);
			}
			$("#upholsteryConsumptionGif").hide();
			$("#upholsteryConsumptionDiv").show();
			
			$('#upholsteryConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_UPHOLSTERY + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getPartConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#partConsumptionGif").show();
			$("#partConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.totalPartConsumptionCount != null || data.totalPartConsumptionCount != undefined){
				$('#partConsumptionCount').text(data.totalPartConsumptionCount);
			}else{
				$('#partConsumptionCount').text(0);
			}
			$('#WO_PartEntryCount').text(addCommas(data.WO_PartEntryCount));
			$('#SE_PartEntryCount').text(addCommas(data.SE_PartEntryCount));
			
			$('#WO_PartConsumptionCount').text(addCommas(data.WO_PartConsumptionCount.toFixed(2)));
			$('#SE_PartConsumptionCount').text(addCommas(data.SE_PartConsumptionCount.toFixed(2)));
			
			$('#workOrderConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_WO + ','+dateType+')" />');
			$('#serviceEntryConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_SE + ','+dateType+')" />');
			
			$("#partConsumptionGif").hide();
			$("#partConsumptionDiv").show();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getTyreConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#tyreConsumptionGif").show();
			$("#tyreConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.tyreConsumptionCount != null || data.tyreConsumptionCount != undefined){
				$('#tyreConsumptionCount').text(addCommas(data.tyreConsumptionCount));
			}
			$("#tyreConsumptionGif").hide();
			$("#tyreConsumptionDiv").show();
			
			$('#tyreConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_TYRE + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getBatteryConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#batteryConsumptionGif").show();
			$("#batteryConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.batteryConsumptionCount != null || data.batteryConsumptionCount != undefined){
				$('#batteryConsumptionCount').text(addCommas(data.batteryConsumptionCount));
			}
			$("#batteryConsumptionGif").hide();
			$("#batteryConsumptionDiv").show();
			
			$('#batteryConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_BATTERY + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
	$.ajax({
		url: "getRefreshmentConsumptionCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$("#refreshmentConsumptionGif").show();
			$("#refreshmentConsumptionDiv").hide();
		},
		success: function (data) {
			dashboardCount++;
			if(data.refreshmentConsumptionCount != null || data.refreshmentConsumptionCount != undefined){
				$('#refreshmentEntryCount').text(addCommas(data.refreshmentEntryCount));
				$('#refreshmentConsumptionCount').text(addCommas(data.refreshmentConsumptionCount.toFixed(2)));
			}else{
				$('#refreshmentEntryCount').text(0);
				$('#refreshmentConsumptionCount').text(0);
			}
			$("#refreshmentConsumptionGif").hide();
			$("#refreshmentConsumptionDiv").show();
			
			$('#refreshmentConsumptionDiv ').wrap('<a href="#" onclick="viewConsumptionSummaryData('+ CONSUMPTION_TYPE_REFRESHMENT + ','+dateType+')" />');
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
		}
	});
	
}


$("#partConsumptionDiv").click(function(){
	$("#WO_SE_Modal").modal('show');
});

function viewConsumptionSummaryData(consumptionType,dateType){
	
	var jsonObject	= new Object();
	
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0];
	var endDate		= dateRange[1];
	
	if($('#companyDropdownConfig').val() == 'true' || $('#companyDropdownConfig').val() == true){
		companyId		= $("#companyId").val();
	}else{
		companyId		= $("#defaultCompanyId").val();
	}
	userId				= $("#userId").val();
	
	var finalString=""+ companyId +","+userId+","+ startDate +","+endDate+","+dateType+","+consumptionType+"";
	
	window.location.replace("viewConsumptionSummaryData.in?data="+finalString);
	
}

/*$(function() {
	  $("#employeeLink").on("click",function(e) {
	    e.preventDefault(); // cancel the link itself
	    $.post(this.href,function(data) {
	      $("#someContainer").html(data);
	    });
	  });
	});*/


/*function showBarGraph(){
	if(dashboardCount != 7){
		return;
	}
	var fuelConsumptionCount 				= Number($('#fuelConsumptionCount').text());
	var ureaConsumptionCount 				= Number($('#ureaConsumptionCount').text());
	var upholsteryConsumptionCount 			= Number($('#upholsteryConsumptionCount').text());
	var partConsumptionCount 				= Number($('#partConsumptionCount').text());
	var tyreConsumptionCount 				= Number($('#tyreConsumptionCount').text());
	var batteryConsumptionCount 			= Number($('#batteryConsumptionCount').text());
	var refreshmentConsumptionCount 		= Number($('#refreshmentConsumptionCount').text());
	
	document.getElementById("chartContainer").innerHTML = '&nbsp;';
	document.getElementById("chartContainer").innerHTML = '<canvas id="bar-chartcanvas"></canvas>';
	
	var ctx1 = document.getElementById('bar-chartcanvas').getContext("2d");

	var gradientFuel = ctx1.createLinearGradient(0, 0, 0, 181);
	gradientFuel.addColorStop(0, 'rgb(255,191,150,1)');
	gradientFuel.addColorStop(1, 'rgb(254,112,150,1)');
    
    var gradientUrea = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientUrea.addColorStop(0, 'rgb(144,202,249,1)');
    gradientUrea.addColorStop(1, 'rgb(4,126,223,1)');
    
    var gradientUpholstery = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientUpholstery.addColorStop(0, 'rgb(132,217,210,1)');
    gradientUpholstery.addColorStop(1, 'rgb(7,205,174,1)');
    
    var gradientPart = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientPart.addColorStop(0, 'rgb(218,140,255,1)');
    gradientPart.addColorStop(1, 'rgb(154,85,255,1)');
    
    var gradientTyre = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientTyre.addColorStop(0, 'rgb(255,51,0,1)');
    gradientTyre.addColorStop(1, 'rgb(255,51,0,1)');
    
    var gradientBattery = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientBattery.addColorStop(0, 'rgb(51,204,255,1)');
    gradientBattery.addColorStop(1, 'rgb(255,153,204,1)');
   
    var gradientRefreshment = ctx1.createLinearGradient(0, 0, 0, 181);
    gradientRefreshment.addColorStop(0, 'rgb(255,247,32,1)');
    gradientRefreshment.addColorStop(1, 'rgb(60,213,0,1)');

	var data = {
		labels : ["TripSheet", "Fuel", "WorkOrders", "ServiceReminders", "RenewalReminders", "Issues", "ServiceEntries"],
		
		datasets : [
			{
				label : "Summary Status",
				data : [fuelConsumptionCount, ureaConsumptionCount, upholsteryConsumptionCount, partConsumptionCount, tyreConsumptionCount, batteryConsumptionCount, refreshmentConsumptionCount],
				backgroundColor : [
					gradientFuel,
					gradientUrea,
					gradientUpholstery,
					gradientPart,
					gradientTyre,
					gradientBattery,
					gradientRefreshment
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
	

	}*/


function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}