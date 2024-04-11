var NOT_APPROVED		= 1;
var APPROVED			= 2;
var OPEN				= 3;
var IN_PROGRESS			= 4;
var CANCELLED			= 5;
var REJECTED			= 6;

var startDate;
var endDate;
var compId;

var renewalCreated      = 0;
var todaysRenewal       = 0;


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
	
	getRenewalReminderData();
	
});

function getRenewalReminderData(){
	
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
		url: "getRenewalReminderData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			renewalCreated = data.renewalCreatedOnVehicle;
			todaysRenewal  = data.renewalNotCreatedOnVehicle;
				
			$("#totalVehicleCount").text(data.totalVehicleCount);
			$("#renewalCreatedCount").text(data.renewalCreatedOnVehicle);
			$("#todaysRenewalCount").text(data.renewalNotCreatedOnVehicle);
			$("#mandatoryCount").text(data.mandateAndNonMandate);
			
			$("#totalDueSoonCount").text(data.totalDueSoonCount);
			$("#totalRenewalExpense").text(addCommas(data.totalRenewalExpense.toFixed(2)));
			$("#thisMonth").text( displayMonth(data.thisMonth)+' Renewal Count & Expense' );
			$("#nextMonth").text( displayMonth(data.nextMonth)+' Renewal Count & Expense' );
			$("#thisMonthCount").text(data.thisMonthRenewalCount);
			$("#thisMonthAmount").text(addCommas(data.thisMonthRenewalAmount.toFixed(2))).addClass("inr-sign");
			$("#nextMonthCount").text(data.nextMonthRenewalCount);
			$("#nextMonthAmount").text(addCommas(data.nextMonthRenewalAmount.toFixed(2))).addClass("inr-sign");
			
			$("#totalOverDueCount").text(data.totalOverDueCount);
			$("#totalSevenDaysCount").text(data.totalSevenDaysCount);
			$("#totalFifteenDaysCount").text(data.totalFifteenDaysCount);
			$("#totalFifteenPlusDaysCount").text(data.totalFifteenPlusDaysCount);
			
			showGraph();
			
			if($('#chartContainer').hasClass('hide')){
				$('#chartContainer').removeClass('hide');
				$('#rrTableDataDetails').addClass('hide');
				$('#rrTable').addClass('hide');
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
	          data: [renewalCreated, todaysRenewal],
	          backgroundColor: [
	        	gradientStrokeBlue,
	        	gradientStrokeRed
	          ],
	          
	          hoverBackgroundColor: [
	        	gradientStrokeBlue,
	        	gradientStrokeRed
	          ],
	          
	          borderColor: [
	        	gradientStrokeBlue,
	        	gradientStrokeRed
	          ],
	          
	          borderWidth: [1, 1]
	        }],
	    
	        // These labels appear in the legend and in the tooltips when hovering different arcs
	        labels: [
	          'No. Of Vehicles With Renewal Reminder',
	          'No. Of Vehicles Without Renewal Reminder'
	        ]
	      };


	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "Renewal Reminder Status",
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

function showRRDetails(type){
	
	if(!$('#chartContainer').hasClass('hide')){
		
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		$('#printBtn').removeClass('hide');
		
		getRRTableData(type);
		
		if(type ==1){
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
		} else if(type ==2){
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
		}else if(type == 11){
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
		} else {
			
			$('#rrTableDataDetails1').removeClass('hide');
			$('#rrTable1').removeClass('hide');
			
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
		}
		
	} else {
		
		getRRTableData(type);
		
		if(type ==1){
			
			$('#rrTableDataDetails1').addClass('hide');
			$('#rrTable1').addClass('hide');
			
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
			
			getRRTableData(type);
			
		} else if(type ==2){
			
			$('#rrTableDataDetails1').addClass('hide');
			$('#rrTable1').addClass('hide');
			
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
			
			getRRTableData(type);
			
		} else if(type == 11){
			
			$('#rrTableDataDetails1').addClass('hide');
			$('#rrTable1').addClass('hide');
			
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
			
			getRRTableData(type);
			
		} else {
			
			$('#rrTableDataDetails').removeClass('hide');
			$('#rrTable').removeClass('hide');
			
			$('#rrTableDataDetails1').removeClass('hide');
			$('#rrTable1').removeClass('hide');
			
			getRRTableData(type);
		}
		
	}
	
}

function backToGraph(){
	
	$('#rrTableDataDetails').addClass('hide');
	$('#rrTable').addClass('hide');
	$('#rrTableDataDetails1').addClass('hide');
	$('#rrTable1').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}

function getRRTableData(type){
	
	console.log("type...",type);
	
	var jsonObject	= new Object();
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	showLayer();
	$.ajax({
		url: "getRenewalReminderTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(type == 1){
				hideLayer();
				rrCreatedDetails(data,type);
			} else if (type == 2){
				hideLayer();
				rrNotCreatedDetails(data,type);
			} else if (type == 11){
				hideLayer();
				rrMandatoryAndNonMandatoryDetails(data,type);
			} else {
				hideLayer();
				rrDetails(data,type);	
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function rrDetails(data,type){
	
	groupByRenewalType(data,type);
	
	$("#rrTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');
	

	tr1.append(th1.append("No"));
	tr1.append(th2.append("RR-No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Renewal From"));
	tr1.append(th5.append("Renewal To"));
	tr1.append(th6.append("Renewal Type"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Status"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.renewalReminder != undefined && data.renewalReminder.length > 0) {
		
		var renewalReminder = data.renewalReminder;
	
		for(var i = 0; i < renewalReminder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="20%">');
			var td4		= $('<td class="fit ar" width="20%">');
			var td5		= $('<td class="fit ar" width="20%">');
			var td6		= $('<td width="20%">');
			var td7		= $('<td width="15%">');
			var td8		= $('<td width="15%">');

			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showRenewalReminder.in?renewal_id='+renewalReminder[i].renewal_id+'" target="_blank" >'+renewalReminder[i].renewal_R_Number));
			tr1.append(td3.append(renewalReminder[i].vehicle_registration));
			tr1.append(td4.append(renewalReminder[i].renewal_from));
			tr1.append(td5.append(renewalReminder[i].renewal_to));
			tr1.append(td6.append(renewalReminder[i].renewal_type));
			tr1.append(td7.append(renewalReminder[i].renewal_Amount));
			
			
			if(renewalReminder[i].renewal_staus_id == NOT_APPROVED )
				tr1.append(td8.append('<span class="label bg-gradient-warning">'+renewalReminder[i].renewal_status));
			
			if(renewalReminder[i].renewal_staus_id == APPROVED)
				tr1.append(td8.append('<span class="label bg-gradient-success">'+renewalReminder[i].renewal_status));
			
			if(renewalReminder[i].renewal_staus_id == OPEN)
				tr1.append(td8.append('<span class="label bg-gradient-secondary">'+renewalReminder[i].renewal_status));
			
			if(renewalReminder[i].renewal_staus_id == IN_PROGRESS)
				tr1.append(td8.append('<span class="label bg-gradient-info">'+renewalReminder[i].renewal_status));
			
			if(renewalReminder[i].renewal_staus_id == CANCELLED)
				tr1.append(td8.append('<span class="label bg-gradient-danger">'+renewalReminder[i].renewal_status));
			
			if(renewalReminder[i].renewal_staus_id == REJECTED)
				tr1.append(td8.append('<span class="label bg-gradient-primary">'+renewalReminder[i].renewal_status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

}

function groupByRenewalType(data,type){
	
	$("#rrTableDataDetails1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("RenewalType"));
	tr1.append(th3.append("Count"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.renewalReminderGroupByType != undefined && data.renewalReminderGroupByType.length > 0) {
		
		var renewalReminderGroupByType = data.renewalReminderGroupByType;
	
		for(var i = 0; i < renewalReminderGroupByType.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(renewalReminderGroupByType[i].renewal_type));
			tr1.append(td3.append(renewalReminderGroupByType[i].renewal_id));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails1").append(thead);
	$("#rrTableDataDetails1").append(tbody);

}

function rrCreatedDetails(data,type){
	
	$("#rrTableDataDetails").empty();
	
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
	if(data.renewalReminder != undefined && data.renewalReminder.length > 0) {
		
		var renewalReminder = data.renewalReminder;
	
		for(var i = 0; i < renewalReminder.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="ViewVehicleDocument.in?vehid='+renewalReminder[i].vid+'" target="_blank" >'+renewalReminder[i].vehicle_registration));
			tr1.append(td3.append(renewalReminder[i].countOfSROnEachVehicle));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

}

function rrNotCreatedDetails(data,type){
	
	$("#rrTableDataDetails").empty();
	
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
	if(data.renewalReminder != undefined && data.renewalReminder.length > 0) {
		
		var renewalReminder = data.renewalReminder;
	
		for(var i = 0; i < renewalReminder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="ViewVehicleDocument.in?vehid='+renewalReminder[i].vid+'" target="_blank" >'+renewalReminder[i].vehicle_registration));
			tr1.append(td3.append(renewalReminder[i].vehicle_Status));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

}

function rrMandatoryAndNonMandatoryDetails(data,type){
	
	var mandatoryCount = 0;
	if(data.mandatoryCount != null){
		mandatoryCount = data.mandatoryCount;
	}
	
	$("#rrTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Mandatory Count"));
	tr1.append(th4.append("Total Count"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.renewalReminder != undefined && data.renewalReminder.length > 0) {
		
		var renewalReminder = data.renewalReminder;
	
		for(var i = 0; i < renewalReminder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td width="10%">');
			var td4		= $('<td width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="ViewVehicleDocument.in?vehid='+renewalReminder[i].vid+'" target="_blank" >'+renewalReminder[i].vehicle_registration));
			tr1.append(td3.append(mandatoryCount));
			tr1.append(td4.append(renewalReminder[i].countOfSROnEachVehicle));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

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

function displayMonth(a){
	
	var name = '';
	switch(a) {
	  case 1:
		  name = 'JANUARY'
	    break;
	  case 2:
		   name = 'FEBRUARY';
	    break;
	  case 3:
		   name = 'MARCH';
	    break;
	  case 4:
		   name = 'APRIL';
	    break;
	  case 5:
		   name = 'MAY';
	    break;
	  case 6:
		   name = 'JUNE';
	    break;
	  case 7:
		   name = 'JULY';
	    break;
	  case 8:
		   name = 'AUGUST';
	    break;
	  case 9:
		   name = 'SEPTEMBER';
	    break;
	  case 10:
		  name = 'OCTOBER';
		  break;
	  case 11:
		  name = 'NOVEMBER';
		  break;
	  case 12:
		  name = 'DECEMBER';
		  break;
	  default:
	}
	
	return name;
	
}

