var TRIPSHEET_CREATED = 1;
var TRIPSHEET_OPEN = 2;
var TRIPSHEET_CLOSED = 3;
var TRIPSHEET_ACCOUNTCLOSED = 4;
var TRIPSHEET_TOTALRUN_KM = 5;
var TRIP_LEFT_TO_CLOSE = 6;
var DISPATCHED_TRIP = 7;
var SAVED_TRIP = 8;

var open;
var close;
var accountClose;

var startDate;
var endDate;
var compId;


/*$(document).ready(function() {
	getTripSheetData();
	
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
	
	getTripSheetData();
	
});

function getTripSheetData(){
	
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
		url: "getTripSheetData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			open = data.tripSheetCountsInOpenState;
			close = data.tripSheetClosedCount;
			accountClose = data.tripSheetCountsInAccountClosedState;
			
			$("#tripDate").text('Trip Details between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
			
			$("#createdCount").text(data.tripsheetCreatedCount);
			$("#openCount").text(data.tripSheetCountsInOpenState);
			$("#closedCount").text(data.tripSheetClosedCount);
			$("#accountClosedCount").text(data.tripSheetCountsInAccountClosedState);
			$("#totalRunCount").text(data.totalRunCount+" "+"KM");
			
			$("#advance").text(addCommas(data.tripAdvance.toFixed(2))).addClass("inr-sign");
			$("#income").text(addCommas(data.tripIncome.toFixed(2))).addClass("inr-sign");
			$("#expense").text(addCommas(data.tripExpense.toFixed(2))).addClass("inr-sign");
			
			$("#todaysTripOpenStatusCount").text(data.todaysTripOpenStatusCount);
			$("#tripSheetDispatchedCount").text(data.tripSheetDispatchedCount);
			$("#tripSheetSavedCount").text(data.tripSheetSavedCount);
			$("#oldestTripNumber").text("TS"+"-"+data.tripSheetNumber);
			$("#oldestDays").text(data.dayDiff+" "+"Days");
			$('#oldestTrip').attr('href', "showTripSheet.in?tripSheetID="+data.oldestTripOpenId+"");
			$('#closedCount1').text(data.tripClosedToday);
			
			showGraph();
			
			if($('#chartContainer').hasClass('hide')){
				$('#chartContainer').removeClass('hide');
				$('#tripSheetTableDataDetails').addClass('hide');
				$('#tsTable').addClass('hide');
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
	          data: [open, close, accountClose],
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
	          'Open',
	          'Closed',
	          'Account Closed'
	        ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "TripSheet Status",
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


function showTripDetails(type){
	
	if(!$('#chartContainer').hasClass('hide')){
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		$('#printBtn').removeClass('hide');
		
		getTripSheetTableData(type);
		$('#tripSheetTableDataDetails').removeClass('hide');
		$('#tsTable').removeClass('hide');
		
	} else {
		getTripSheetTableData(type);
		/*$('#chartContainer').removeClass('hide');
		$('#tripSheetTableDataDetails').addClass('hide');
		$('#tsTable').addClass('hide');
		$('#graphButton').addClass('hide');*/
	}
	
}

function backToGraph(){
	
	$('#tripSheetTableDataDetails').addClass('hide');
	$('#tsTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}
$("#newCard").click(function(){
	$("#newCloseCard").val(true);
	showTripDetails(3);
});



function getTripSheetTableData(type){
	
	var jsonObject	= new Object();
	
	if($("#newCloseCard").val()== "true" || $("#newCloseCard").val() == true){
	 	jsonObject["newClosed"]		= true;
	}
		
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;

	
	showLayer();
	$.ajax({
	url: "getTripSheetTableData",  
	type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {

			tripSheetDetails(data,type);	
			hideLayer();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function tripSheetDetails(data,type){
	
	if(type == TRIPSHEET_CREATED){
		tsCreated(data);
	}
	
	if(type == TRIPSHEET_OPEN){
		tsOpen(data);
	}
	
	if(type == TRIPSHEET_CLOSED){
		tsClosed(data);
	}
	
	if(type == TRIPSHEET_ACCOUNTCLOSED){
		tsAccntClosed(data);
	}
	
	if(type == TRIPSHEET_TOTALRUN_KM){
		tsRunKM(data);
	}
	
	if(type == TRIP_LEFT_TO_CLOSE){
		tsMissedClosing(data);
	}
	
	if(type == DISPATCHED_TRIP){
		tsMissedClosing(data);
	}
	
	if(type == SAVED_TRIP){
		tsMissedClosing(data);
	}
	
}

function tsCreated(data){
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Route"));
	tr1.append(th5.append("Open & Closed Date"));
	tr1.append(th6.append("Status"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripSheetCreated != undefined && data.tripSheetCreated.length > 0) {
		
		var tripSheetCreated = data.tripSheetCreated;
	
		for(var i = 0; i < tripSheetCreated.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripSheetCreated[i].tripSheetID+'" target="_blank" >'+tripSheetCreated[i].tripSheetNumber));
			tr1.append(td3.append(tripSheetCreated[i].vehicle_registration));
			tr1.append(td4.append(tripSheetCreated[i].routeName));
			tr1.append(td5.append(tripSheetCreated[i].tripOpenDate+'</br>'+tripSheetCreated[i].closetripDate));
			
			if(tripSheetCreated[i].tripStutesId == TRIPSHEET_CREATED || tripSheetCreated[i].tripStutesId == TRIPSHEET_OPEN)
			tr1.append(td6.append('<span class="label bg-gradient-primary">'+tripSheetCreated[i].tripSheetCurrentStatus));
			
			if(tripSheetCreated[i].tripStutesId == TRIPSHEET_CLOSED)
				tr1.append(td6.append('<span class="label bg-gradient-success">'+tripSheetCreated[i].tripSheetCurrentStatus));
			
			if(tripSheetCreated[i].tripStutesId == TRIPSHEET_ACCOUNTCLOSED)
				tr1.append(td6.append('<span class="label bg-gradient-warning">'+tripSheetCreated[i].tripSheetCurrentStatus));
			

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);

}

function tsOpen(data){
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	
	
	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Route"));
	tr1.append(th5.append("Open & Closed Date"));
	tr1.append(th6.append("Status"));
	
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripSheetOpen != undefined && data.tripSheetOpen.length > 0) {
		
		var tripSheetOpen = data.tripSheetOpen;
		
		for(var i = 0; i < tripSheetOpen.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripSheetOpen[i].tripSheetID+'" target="_blank" >'+tripSheetOpen[i].tripSheetNumber));
			tr1.append(td3.append(tripSheetOpen[i].vehicle_registration));
			tr1.append(td4.append(tripSheetOpen[i].routeName));
			tr1.append(td5.append(tripSheetOpen[i].tripOpenDate+'</br>'+tripSheetOpen[i].closetripDate));
			tr1.append(td6.append('<span class="label bg-gradient-primary">'+tripSheetOpen[i].tripSheetCurrentStatus));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);

	
}

function tsClosed(data){
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	
	
	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Route"));
	tr1.append(th5.append("Open & Closed Date"));
	tr1.append(th6.append("Status"));
	
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripSheetClosed != undefined && data.tripSheetClosed.length > 0) {
		
		var tripSheetClosed = data.tripSheetClosed;
		
		for(var i = 0; i < tripSheetClosed.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripSheetClosed[i].tripSheetID+'" target="_blank" >'+tripSheetClosed[i].tripSheetNumber));
			tr1.append(td3.append(tripSheetClosed[i].vehicle_registration));
			tr1.append(td4.append(tripSheetClosed[i].routeName));
			tr1.append(td5.append(tripSheetClosed[i].tripOpenDate+'</br>'+tripSheetClosed[i].closetripDate));
			tr1.append(td6.append('<span class="label bg-gradient-success">'+tripSheetClosed[i].tripSheetCurrentStatus));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);
	
	$("#newCloseCard").val(false);
	
}

function tsAccntClosed(data){
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	
	
	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Route"));
	tr1.append(th5.append("Open & Closed Date"));
	tr1.append(th6.append("Status"));
	
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripSheetAccountClosed != undefined && data.tripSheetAccountClosed.length > 0) {
		
		var tripSheetAccountClosed = data.tripSheetAccountClosed;
		
		for(var i = 0; i < tripSheetAccountClosed.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripSheetAccountClosed[i].tripSheetID+'" target="_blank" >'+tripSheetAccountClosed[i].tripSheetNumber));
			tr1.append(td3.append(tripSheetAccountClosed[i].vehicle_registration));
			tr1.append(td4.append(tripSheetAccountClosed[i].routeName));
			tr1.append(td5.append(tripSheetAccountClosed[i].tripOpenDate+'</br>'+tripSheetAccountClosed[i].closetripDate));
			tr1.append(td6.append('<span class="label bg-gradient-warning">'+tripSheetAccountClosed[i].tripSheetCurrentStatus));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);
	
	
}

function tsRunKM(data){
	
	showMessage('info','Usage KM will be calculated only for Tripsheet with Closed or Account Close Status !')
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th>');
	
	
	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Open & Closed Date"));
	tr1.append(th5.append("Open Odometer"));
	tr1.append(th6.append("Close Odometer"));
	tr1.append(th7.append("TripUsage KM"));
	tr1.append(th8.append("Status"));
	
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripSheetUsageKM != undefined && data.tripSheetUsageKM.length > 0) {
		
		var tripSheetUsageKM = data.tripSheetUsageKM;
		
		for(var i = 0; i < tripSheetUsageKM.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="17%">');
			var td4		= $('<td class="fit ar" width="25%">');
			var td5		= $('<td class="fit ar" width="23%">');
			var td6		= $('<td class="fit ar" width="23%">');
			var td7		= $('<td width="15%">');
			var td8		= $('<td width="15%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripSheetUsageKM[i].tripSheetID+'" target="_blank" >'+tripSheetUsageKM[i].tripSheetNumber));
			tr1.append(td3.append(tripSheetUsageKM[i].vehicle_registration));
			tr1.append(td4.append(tripSheetUsageKM[i].tripOpenDate+'</br>'+tripSheetUsageKM[i].closetripDate));
			tr1.append(td5.append(tripSheetUsageKM[i].tripOpeningKM));
			tr1.append(td6.append(tripSheetUsageKM[i].tripClosingKM));
			
			if(tripSheetUsageKM[i].tripUsageKM != 0){
				tr1.append(td7.append(tripSheetUsageKM[i].tripUsageKM));
			} else {
				tr1.append(td7.append('<span class="label bg-gradient-warning">'+tripSheetUsageKM[i].tripUsageKM));
			}
			
			if(tripSheetUsageKM[i].tripStutesId == TRIPSHEET_CLOSED)
				tr1.append(td8.append('<span class="label bg-gradient-success">'+tripSheetUsageKM[i].tripSheetCurrentStatus));
			
			if(tripSheetUsageKM[i].tripStutesId == TRIPSHEET_ACCOUNTCLOSED)
				tr1.append(td8.append('<span class="label bg-gradient-warning">'+tripSheetUsageKM[i].tripSheetCurrentStatus));
			
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);
	
	
}

function tsMissedClosing(data){
	
	$("#tripSheetTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("TS-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Route"));
	tr1.append(th5.append("Open & Closed Date"));
	tr1.append(th6.append("Status"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tripMissedClosing != undefined && data.tripMissedClosing.length > 0) {
		
		var tripMissedClosing = data.tripMissedClosing;
	
		for(var i = 0; i < tripMissedClosing.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+tripMissedClosing[i].tripSheetID+'" target="_blank" >'+tripMissedClosing[i].tripSheetNumber));
			tr1.append(td3.append(tripMissedClosing[i].vehicle_registration));
			tr1.append(td4.append(tripMissedClosing[i].routeName));
			tr1.append(td5.append(tripMissedClosing[i].tripOpenDate+'</br>'+tripMissedClosing[i].closetripDate));
			
			if(tripMissedClosing[i].tripStutesId == TRIPSHEET_CREATED || tripMissedClosing[i].tripStutesId == TRIPSHEET_OPEN)
			tr1.append(td6.append('<span class="label bg-gradient-primary">'+tripMissedClosing[i].tripSheetCurrentStatus));
			
			if(tripMissedClosing[i].tripStutesId == TRIPSHEET_CLOSED)
				tr1.append(td6.append('<span class="label bg-gradient-success">'+tripMissedClosing[i].tripSheetCurrentStatus));
			
			if(tripMissedClosing[i].tripStutesId == TRIPSHEET_ACCOUNTCLOSED)
				tr1.append(td6.append('<span class="label bg-gradient-warning">'+tripMissedClosing[i].tripSheetCurrentStatus));
			

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#tripSheetTableDataDetails").append(thead);
	$("#tripSheetTableDataDetails").append(tbody);

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
};

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}

