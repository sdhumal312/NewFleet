var startDate;
var endDate;
var compId;

var serviceCreated      = 0;
var todaysService       = 0;

/*$(document).ready(function() {
	getRenewalReminderData();
});*/

$(function() {
	function a(a, b) {
		
		$("#dateRange").val($('#preDateRange').val());
	}
	a(moment(), moment()), $("#dateRange").daterangepicker({
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	
	}, a);
	
	getServiceReminderData();
	
});

function getServiceReminderData(){
	
	var jsonObject	= new Object();
	var dateShow 	= $('#dateRange').val();
	var dateRange 	= $('#dateRange').val().split("to");
	 	startDate	= dateRange[0];
	 	endDate		= dateRange[1];
	 	compId		= getUrlParameter('DateRange').split("to")[2];
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	showLayer();
	$.ajax({
		url: "getServiceReminderData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			serviceCreated = data.serviceReminderCreated;
			todaysService  = data.serviceReminderNotCreated;
				
			$("#totalVehicleCount").text(data.totalVehicleCount);
			$("#serviceCreatedCount").text(data.serviceReminderCreated);
			$("#todaysServiceCount").text(data.serviceReminderNotCreated);
			$("#totalDueSoonCount").text(data.totalDueSoonCount);
			$("#totalOverDueCount").text(data.totalOverDueCount);
			$("#totalSevenDaysCount").text(data.totalSevenDaysCount);
			$("#totalFifteenDaysCount").text(data.totalFifteenDaysCount);
			$("#totalFifteenPlusDaysCount").text(data.totalFifteenPlusDaysCount);
			
			showGraph();
			
			if($('#chartContainer').hasClass('hide')){
				$('#chartContainer').removeClass('hide');
				$('#srTableDataDetails').addClass('hide');
				$('#srTable').addClass('hide');
				$('#graphButton').addClass('hide');
			}
			
			hideLayer();
			
			$('#dateRange').val(dateShow);
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function showGraph(){

$(function(){

		  var ctx = document.getElementById('doughnut-chartcanvas-1').getContext("2d");

		  var gradientStrokeBlue = ctx.createLinearGradient(0, 0, 0, 181);
	      gradientStrokeBlue.addColorStop(0, 'rgba(54, 215, 232, 1)');
	      gradientStrokeBlue.addColorStop(1, 'rgba(177, 148, 250, 1)');
	      var gradientLegendBlue = 'linear-gradient(to right, rgba(54, 215, 232, 1), rgba(177, 148, 250, 1))';

	      var gradientStrokeRed = ctx.createLinearGradient(0, 0, 0, 50);
	      gradientStrokeRed.addColorStop(0, 'rgba(255, 191, 150, 1)');
	      gradientStrokeRed.addColorStop(1, 'rgba(254, 112, 150, 1)');
	      var gradientLegendRed = 'linear-gradient(to right, rgba(255, 191, 150, 1), rgba(254, 112, 150, 1))';

	      var gradientStrokeGreen = ctx.createLinearGradient(0, 0, 0, 300);
	      gradientStrokeGreen.addColorStop(0, 'rgba(6, 185, 157, 1)');
	      gradientStrokeGreen.addColorStop(1, 'rgba(132, 217, 210, 1)');
	      var gradientLegendGreen = 'linear-gradient(to right, rgba(6, 185, 157, 1), rgba(132, 217, 210, 1))'; 
	      
	      var gradientStrokeYellow = ctx.createLinearGradient(0, 0, 0, 300);
	      gradientStrokeYellow.addColorStop(0, 'rgb(255,153,0,1)');
	      gradientStrokeYellow.addColorStop(1, 'rgb(255,153,0,1)');
	      var gradientLegendYellow = 'linear-gradient(to right, rgba(255,153,0,1), rgba(255,153,0,1))'; 

	     var trafficChartData = {
	        datasets: [{
	          data: [serviceCreated, todaysService],
	          backgroundColor: [
	        	gradientStrokeRed,
	            gradientStrokeBlue
	          ],
	          
	          hoverBackgroundColor: [
	        	gradientStrokeRed,
	            gradientStrokeBlue
	          ],
	          
	          borderColor: [
	        	gradientStrokeRed,
	            gradientStrokeBlue
	          ],
	          
	          borderWidth: [1, 1]
	        }],
	    
	        // These labels appear in the legend and in the tooltips when hovering different arcs
	        labels: [
	          'No of Vehicles With SR',
	          'No of Vehicles Without SR'
	        ]
	      };


	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "Service Reminder Status",
	    	            fontSize: 18,
	    	            fontColor: "#111"
	    	        },
	    	        legend: {
	    	            display: true,
	    	            position: "bottom",
	    	            labels: {
	    	                fontColor: "#333",
	    	                fontSize: 15
	    	            }
	    	        }
	    	    };

	    //create Chart class object
	    var chart1 = new Chart(ctx, {
	        type: "doughnut",
	        data: trafficChartData,
	        options: options
	    });
		
	    
	});

}

function showSRDetails(type){
	
	if(!$('#chartContainer').hasClass('hide')){
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		$('#printBtn').removeClass('hide');
		
		getSRTableData(type);
		$('#srTableDataDetails').removeClass('hide');
		$('#srTable').removeClass('hide');
			
		/*if(type ==1){
			$('#srTableDataDetails1').removeClass('hide');
			$('#srTable1').removeClass('hide');
		} else if(type ==2){
			$('#srTableDataDetails2').removeClass('hide');
			$('#srTable2').removeClass('hide');
		} else {
			$('#srTableDataDetails').removeClass('hide');
			$('#srTable').removeClass('hide');
		}*/
		
	} else {
		
		getSRTableData(type);
		
		/*if(type ==1){
			$('#srTableDataDetails').addClass('hide');
			$('#srTable').addClass('hide');
			
			$('#srTableDataDetails2').addClass('hide');
			$('#srTable2').addClass('hide');
			
			$('#srTableDataDetails1').removeClass('hide');
			$('#srTable1').removeClass('hide');
			
			getSRTableData(type);
			
		} else if(type ==2){
			$('#srTableDataDetails').addClass('hide');
			$('#srTable').addClass('hide');
			
			$('#srTableDataDetails1').addClass('hide');
			$('#srTable1').addClass('hide');
			
			$('#srTableDataDetails2').removeClass('hide');
			$('#srTable2').removeClass('hide');
			
			getSRTableData(type);
			
		} else {
			
			$('#srTableDataDetails1').addClass('hide');
			$('#srTable1').addClass('hide');
			
			$('#srTableDataDetails2').addClass('hide');
			$('#srTable2').addClass('hide');
			
			$('#srTableDataDetails').removeClass('hide');
			$('#srTable').removeClass('hide');
			
			getSRTableData(type);
		}*/
		
	}
	
}

function backToGraph(){
	
	$('#srTableDataDetails').addClass('hide');
	$('#srTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}

function getSRTableData(type){
	
	var jsonObject	= new Object();
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	
	showLayer();
	$.ajax({
		url: "getServiceReminderTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(type == 1){
				hideLayer();
				srCreatedDetails(data,type);
			} else if (type == 2){
				hideLayer();
				srNotCreatedDetails(data,type);
			} else {
				hideLayer();
				srDetails(data,type);	
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function srDetails(data,type){
	
	$("#srTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	var th7		= $('<th>');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("SR-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Current Odometer"));
	tr1.append(th5.append("Service Odometer"));
	tr1.append(th6.append("Threshold Date"));
	tr1.append(th7.append("Service Date"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminder != undefined && data.serviceReminder.length > 0) {
		
		var serviceReminder = data.serviceReminder;
	
		for(var i = 0; i < serviceReminder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="15%">');
			var td5		= $('<td class="fit ar" width="15%">');
			var td6		= $('<td class="fit ar" width="15%">');
			var td7		= $('<td class="fit ar" width="15%">');

			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="ShowService.in?service_id='+serviceReminder[i].service_id+'" target="_blank" >'+serviceReminder[i].service_Number));
			tr1.append(td3.append(serviceReminder[i].vehicle_registration));
			tr1.append(td4.append(serviceReminder[i].vehicle_currentOdometer));
			tr1.append(td5.append(serviceReminder[i].meter_serviceodometer));
			tr1.append(td6.append(serviceReminder[i].timeServiceThresholdDateStr));
			tr1.append(td7.append(serviceReminder[i].timeServiceDate));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#srTableDataDetails").append(thead);
	$("#srTableDataDetails").append(tbody);

}

function srCreatedDetails(data,type){
	
	$("#srTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Count"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminder != undefined && data.serviceReminder.length > 0) {
		console.log("A..")
		var serviceReminder = data.serviceReminder;
	
		for(var i = 0; i < serviceReminder.length; i++) {
			console.log("B..")
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="VehicleServiceDetails.in?vid='+serviceReminder[i].vid+'" target="_blank" >'+serviceReminder[i].vehicle_registration));
			tr1.append(td3.append(serviceReminder[i].countOfSROnEachVehicle));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#srTableDataDetails").append(thead);
	$("#srTableDataDetails").append(tbody);

}

function srNotCreatedDetails(data,type){
	
	$("#srTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Status"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminder != undefined && data.serviceReminder.length > 0) {
		
		var serviceReminder = data.serviceReminder;
	
		for(var i = 0; i < serviceReminder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="VehicleServiceDetails.in?vid='+serviceReminder[i].vid+'" target="_blank" >'+serviceReminder[i].vehicle_registration));
			tr1.append(td3.append(serviceReminder[i].vehicle_Status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#srTableDataDetails").append(thead);
	$("#srTableDataDetails").append(tbody);

}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}