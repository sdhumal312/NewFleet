var fuelEntries = 0;
var activeVehicles = 0;

var		FUELENTRIES_CREATED			= 1;
var		FUELENTRIES_NOT_CREATED		= 2;
var		KMPL_NOT_CREATED			= 3;
var		FUEL_DETAILS				= 4;
var		BELOW_RANGE				 	= 5;
var		BETWEEN_RANGE			 	= 6;
var		ABOVE_RANGE				 	= 7;

var startDate;
var endDate;
var compId;

var belowRange 	 = 0;
var betweenRange = 0;
var aboveRange 	 = 0;

/*$(document).ready(function() {
	getFuelData();
});*/

$(function() {
	function a(a, b) {
		
		$("#dateRange").val($('#preDateRange').val());
	}
	a(moment(), moment()), $("#dateRange").daterangepicker({
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	
	}, a);
	
	getFuelData();
	
});


function getFuelData(){
	
	var jsonObject	= new Object();
	var dateShow 	= $('#dateRange').val();
	var dateRange 	= $('#dateRange').val().split("to");
	 	startDate	= dateRange[0];
	 	endDate		= dateRange[1];
	 	compId		= getUrlParameter('DateRange').split("to")[2];
	 	$('#companyId').val(compId);
	 	
	 	$("#cancel, #cancel1").click(function(){
	 		location.replace('viewSummary?DateRange='+dateShow);
	 	});
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= $('#companyId').val();
	
	
	showLayer();
	$.ajax({
		url: "getFuelData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			fuelEntries = data.fuelCreatedCount;
			activeVehicles = data.activeVehicleCount;
			
			$("#fuelDate").text('Fuel Entries Created between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
			
			$("#totalVehicleCount").text(data.totalVehicleCount);
			$("#feCreatedOnVehicles").text(data.feCreatedOnVehicles);
			$("#feNotCreatedOnVehicles").text(data.feNotCreatedOnVehicles);
			$("#countOfvehiclesWithoutKMPL").text(data.countOfvehiclesWithoutKMPL);
			$("#fuelCreatedCount").text(data.fuelCreatedCount);
			$("#belowRange").text(data.belowRange);
			$("#betweenRange").text(data.betweenRange);
			$("#aboveRange").text(data.aboveRange);
			
			var totFlCost = addCommas(data.todaysTotalFuelCost.toFixed(2));    
			$("#todaysTotalFuelCost").text(totFlCost).addClass("inr-sign");    
			$("#todaysTotalFuelLiter").text(addCommas(data.todaysTotalFuelLiter.toFixed(2)));
			$("#todaysAverageFuelPrice").text(addCommas(data.todaysAverageFuelPrice.toFixed(2))+'/L').addClass("inr-sign");
			
			$("#activeVehicleCount").text(data.activeVehicleCount);
			
			belowRange 	 = data.belowRange;
			betweenRange = data.betweenRange;
			aboveRange 	 = data.aboveRange;
			
			showGraph();
			
			if($('#chartContainer').hasClass('hide')){
				$('#chartContainer').removeClass('hide');
				$('#fuelTableDataDetails').addClass('hide');
				$('#fTable').addClass('hide');
				$('#graphButton').addClass('hide');
			}
			
			hideLayer();
			
			$('#dateRange').val(startDate+' to '+endDate);
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function showGraph(){
	
	$(function(){
		
	      //get the doughnut chart canvas
	      //var ctx1 = $("#doughnut-chartcanvas-1");
	      /*document.getElementById("chartContainer").innerHTML = '&nbsp;';
	  	  document.getElementById("chartContainer").innerHTML = '<canvas id="doughnut-chartcanvas-1"></canvas>';*/
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
	      gradientStrokeYellow.addColorStop(0, 'rgba(178,238,174,1)');
	      gradientStrokeYellow.addColorStop(1, 'rgba(167,233,148,1)');
	      var gradientLegendYellow = 'linear-gradient(to right, rgba(178,238,174,1), rgba(167,233,148,1))'; 

	     var trafficChartData = {
	        datasets: [{
	          data: [belowRange, betweenRange, aboveRange ],
	          backgroundColor: [
	            gradientStrokeGreen,
	            gradientStrokeBlue,
	            gradientStrokeRed
	          ],
	          
	          hoverBackgroundColor: [
	        	 gradientStrokeGreen,
		         gradientStrokeBlue,
		         gradientStrokeRed
	          ],
	          
	          borderColor: [
	        	gradientStrokeGreen,
		        gradientStrokeBlue,
		        gradientStrokeRed
	          ],
	          
	          borderWidth: [1, 1, 1]
	        }],
	    
	        // These labels appear in the legend and in the tooltips when hovering different arcs
	        labels: [
	          'Below KM/L',
	          'Between KM/L',
	          'Above KM/L'
	        ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "Fuel Entries Status",
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


function showFuelDetails(type){
	
	if(!$('#chartContainer').hasClass('hide')){
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		$('#printBtn').removeClass('hide');
		
		getFuelTableData(type);
		$('#fuelTableDataDetails').removeClass('hide');
		$('#fTable').removeClass('hide');
		
	} else {
		getFuelTableData(type);
		
	}
	
}

function backToGraph(){
	
	$('#fuelTableDataDetails').addClass('hide');
	$('#fTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}

function getFuelTableData(type){
	
	var jsonObject	= new Object();
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	
	showLayer();
	$.ajax({
		url: "getFuelTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			showFuelSummaryData(data,type);	
			hideLayer();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function showFuelSummaryData(data,type){
	
	if(type == FUELENTRIES_CREATED)
		feCreatedDetails(data,type)
		
	if(type == FUELENTRIES_NOT_CREATED)
		feNotCreatedDetails(data,type)
		
	if(type == KMPL_NOT_CREATED)
		kmplNotCreatedDetails(data,type)	
	
	if(type == FUEL_DETAILS)
		fuelDetails(data,type)
	
	if(type == BELOW_RANGE)
		rangeDetails(data,type)
	
	if(type == BETWEEN_RANGE)
		rangeDetails(data,type)
	
	if(type == ABOVE_RANGE)
		rangeDetails(data,type)
	
	/*if(type == ACTIVE_VEHICLES)
	activeVehicleDetails(data,type)*/
	
}

function feCreatedDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
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
	if(data.fuel != undefined && data.fuel.length > 0) {
		
		var fuel = data.fuel;
	
		for(var i = 0; i < fuel.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="VehicleFuelDetails/1.in?vid='+fuel[i].vid+'" target="_blank" >'+fuel[i].vehicle_registration));
			tr1.append(td3.append(fuel[i].countOfFEOnEachVehicle));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

}

function feNotCreatedDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
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
	if(data.fuel != undefined && data.fuel.length > 0) {
		
		var fuel = data.fuel;
	
		for(var i = 0; i < fuel.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="VehicleFuelDetails/1.in?vid='+fuel[i].vid+'" target="_blank" >'+fuel[i].vehicle_registration));
			tr1.append(td3.append(fuel[i].vehicle_Status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

}

function kmplNotCreatedDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Expected Mileage From"));
	tr1.append(th4.append("Expected Mileage To"));
	tr1.append(th5.append("Status"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.fuel != undefined && data.fuel.length > 0) {
		
		var fuel = data.fuel;
	
		for(var i = 0; i < fuel.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="15%">');
			var td3		= $('<td class="fit ar" width="10%">');
			var td4		= $('<td class="fit ar" width="10%">');
			var td5		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showVehicle.in?vid='+fuel[i].vid+'" target="_blank" >'+fuel[i].vehicle_registration));
			tr1.append(td3.append(fuel[i].vehicle_ExpectedMileage));
			tr1.append(td4.append(fuel[i].vehicle_ExpectedMileage_to));
			tr1.append(td5.append(fuel[i].vehicle_Status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

}


function fuelDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
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
	tr1.append(th2.append("F-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Amount"));
	tr1.append(th5.append("Price"));
	tr1.append(th6.append("Liter"));
	tr1.append(th7.append("Fuel Date"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.fuel != undefined && data.fuel.length > 0) {
		
		var fuel = data.fuel;
	
		for(var i = 0; i < fuel.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			var td7		= $('<td width="15%">');

			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showFuel.in?FID='+fuel[i].fuel_id+'" target="_blank" >'+fuel[i].fuel_Number));
			tr1.append(td3.append(fuel[i].vehicle_registration));
			tr1.append(td4.append(fuel[i].fuel_amount));
			tr1.append(td5.append(fuel[i].fuel_price));
			tr1.append(td6.append(fuel[i].fuel_liters));
			tr1.append(td7.append(fuel[i].fuel_date));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

}

function rangeDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Expected KM/L"));
	tr1.append(th4.append("Current KM/L"));
	tr1.append(th5.append("Fuel Date"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.fuel != undefined && data.fuel.length > 0) {
		
		var fuel = data.fuel;
	
		for(var i = 0; i < fuel.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showVehicle.in?vid='+fuel[i].vid+'" target="_blank" >'+fuel[i].vehicle_registration));
			tr1.append(td3.append(fuel[i].vehicle_ExpectedMileage+" "+'-'+" "+fuel[i].vehicle_ExpectedMileage_to));
			tr1.append(td4.append(fuel[i].fuel_kml));
			tr1.append(td5.append(fuel[i].fuel_date));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

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

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}

/*function activeVehicleDetails(data,type){
	
	$("#fuelTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Current Odometer"));
	tr1.append(th4.append("Created Date"));
	tr1.append(th5.append("Status"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vehicle != undefined && data.vehicle.length > 0) {
		
		var vehicle = data.vehicle;
	
		for(var i = 0; i < vehicle.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');

			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showVehicle.in?vid='+vehicle[i].vid+'" target="_blank" >'+vehicle[i].vehicle_registration));
			tr1.append(td3.append(vehicle[i].vehicle_Odometer));
			tr1.append(td4.append(vehicle[i].created));
			tr1.append(td5.append(vehicle[i].vehicle_Status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#fuelTableDataDetails").append(thead);
	$("#fuelTableDataDetails").append(tbody);

}*/

