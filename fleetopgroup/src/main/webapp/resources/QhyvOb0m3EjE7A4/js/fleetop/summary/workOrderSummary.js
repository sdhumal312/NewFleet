var WORKORDER_CREATED 		= 0;
var WORKORDER_OPEN 			= 1;
var WORKORDER_IN_PROCESS 	= 2;
var WORKORDER_HOLD 			= 3;
var WORKORDER_CLOSED 		= 4;
var WORKORDER_0_TO_7 		= 5;
var WORKORDER_8_TO_15 		= 6;
var WORKORDER_15UP 			= 7;
$(document).ready(
		function($) {
		var preDate =	$('#preDateRange').val();
		$('#dateRange').val(preDate);
			getAllWorkOrderCountData();
		});

$(function() {
	function a(a, b) {
	//	$("#dateRange").val(b.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
		$("#dateRange").val($('#preDateRange').val())
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
	
	}
	, a);
	
	getAllWorkOrderCountData();
	
}

);


function getAllWorkOrderCountData(){
	
	var dateShow = $('#dateRange').val();
	var jsonObject	= new Object();
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0];
	var endDate		= dateRange[1];
	var compId		= getUrlParameter('DateRange').split("to")[2];
	$('#companyId').val(compId);
	
	$("#cancel, #cancel1").click(function(){
 		location.replace('viewSummary?DateRange='+dateShow);
 	});
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "getAllWorkOrderCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAllWorkOrderCountData(data, startDate, endDate);
			$('#dateRange').val(startDate+' to '+endDate);
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
	
	
}

function setAllWorkOrderCountData(data, startDate, endDate){
	$("#woDate").text('WorkOrder Details between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
	
	$('#workOrderCreatedCounts').text(data.workOrderCreatedCounts);
	
	$('#workOrderOpenCounts').text(data.workOrderOpenCounts);
	$('#workOrderProcessCount').text(data.workOrderProcessCounts);
	$('#workOrderHoldCount').text(data.workOrderHoldCounts);
	$('#workOrderCloseCounts').text(data.workOrderCloseCounts);
	
	$('#workOrderAllOpenCounts').text(data.workOrderAllOpenCounts);
	$('#from7Days').text(data.from7Days);
	$('#from15Days').text(data.from15Days);
	$('#from30Days').text(data.from30Days);
	
	getAllLocationWiseWorkOrderCount(startDate, endDate);
	showPiChart();
}

function getAllLocationWiseWorkOrderCount(startDate, endDate){
	
	var jsonObject	= new Object();
	var compId		= getUrlParameter('DateRange').split("to")[2];
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	showLayer();
	$.ajax({
		url: "getAllLocationWiseWorkOrderCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAllLocationWiseWorkOrderCount(data);
			hideLayer();
			//console.log("darte",data)
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
}

function setAllLocationWiseWorkOrderCount(data){
	
	$("#locationTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');

	tr1.append(th1.append('Sr.No'));
	tr1.append(th2.append('Loction'));
	tr1.append(th3.append('Open & InProcess Count'));
	tr1.append(th4.append('Completed Count'));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.locationWiseWOCount != undefined && data.locationWiseWOCount.length > 0) {
		
		var locationWiseWOCount =  data.locationWiseWOCount;
	
		for(var i = 0; i < locationWiseWOCount.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="2%">');
			var td2		= $('<td width="10%">');
			var td3		= $('<td class="fit ar" width="5%">');
			var td4		= $('<td class="fit ar" width="5%">');
			
			tr1.append(td1.append('<span class="label bg-gradient-primary" style="font-size: 12px">'+(i + 1)));
			tr1.append(td2.append('<span class="label bg-gradient-success style="font-size: 14px">'+locationWiseWOCount[i].workorders_location));
			tr1.append(td3.append(locationWiseWOCount[i].workOrderOpenCount != null ? '<span class="label bg-gradient-warning" style="font-size: 12px">'+locationWiseWOCount[i].workOrderOpenCount : 0));
			tr1.append(td4.append(locationWiseWOCount[i].workOrderCloseCount != null ? '<span class="label bg-gradient-info" style="font-size: 12px">'+locationWiseWOCount[i].workOrderCloseCount : 0));

			tbody.append(tr1);
		}
	}else{
		//showMessage('info','No record found !')
	}
	
	$("#locationTable").append(thead);
	$("#locationTable").append(tbody);

}


function showPiChart(){
	var openCount		=	Number($('#workOrderOpenCounts').text());
	var processCount	=	Number($('#workOrderProcessCount').text());
	var holdCount		=	Number($('#workOrderHoldCount').text());
	var closeCount		=	Number($('#workOrderCloseCounts').text());
	$(function(){
		
	      //get the doughnut chart canvas
	      var ctx1 = $("#doughnut-chartcanvas-1");

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
	      gradientStrokeYellow.addColorStop(0, 'rgba(247, 202, 24, 1)');
	      gradientStrokeYellow.addColorStop(1, 'rgba(247, 202, 24, 1)');
	      var gradientLegendYellow = 'linear-gradient(to right, rgba(247, 202, 24, 1), rgba(247, 202, 24, 1))'; 

	     var trafficChartData = {
	        datasets: [{
	        	  data: [openCount, processCount, holdCount, closeCount],
	          backgroundColor: [
	        	  gradientStrokeGreen,
		          gradientStrokeBlue,
		          gradientStrokeRed,
		          gradientStrokeYellow
	          ],
	          
	          hoverBackgroundColor: [
	        	  gradientStrokeGreen,
		          gradientStrokeBlue,
		          gradientStrokeRed,
		          gradientStrokeYellow
	          ],
	          
	          borderColor: [
	        	  gradientStrokeGreen,
		          gradientStrokeBlue,
		          gradientStrokeRed,
		          gradientStrokeYellow
	          ],
	          
	          borderWidth: [1, 1, 1]
	        }],
	    
	        // These labels appear in the legend and in the tooltips when hovering different arcs
	        labels: [
	            'Open',
	            'In Process',
	            'Hold',
	            'Completed'
	          ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "WorkOrders Status",
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
	    var chart1 = new Chart(ctx1, {
	        type: "doughnut",
	        data: trafficChartData,
	        options: options
	    });
		
	    
	});
	hideLayer();
}

function showTripDetails(type){
	
	$('#chartContainer').addClass('hide');
	$('#graphButton').removeClass('hide');
	$('#printBtn').removeClass('hide');
	
	getWorkOrderTableData(type);
	$('#workOrdersTableDataDetails').removeClass('hide');
	$('#tsTable').removeClass('hide');
	
}

function backToGraph(){
	
	$('#workOrdersTableDataDetails').addClass('hide');
	$('#tsTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}

function getWorkOrderTableData(type){
	var jsonObject	= new Object();
	
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0];
	var endDate		= dateRange[1];
	var compId		= getUrlParameter('DateRange').split("to")[2];
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	showLayer();
	$.ajax({
		url: "getWorkOrderTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setWorkOrderTableData(data,type);	
			hideLayer();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
}

function setWorkOrderTableData(data,type){
	console.log("data",data)
		
		$("#workOrdersTableDataDetails").empty();
		
		var thead = $('<thead>');
		var tr1 = $('<tr>');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th class="fit ar">');
		var th5		= $('<th class="fit ar">');
		var th6		= $('<th class="fit ar">');
		var th7		= $('<th>');
		var th8		= $('<th>');

		tr1.append(th1.append("No"));
		tr1.append(th2.append("WO-No"));
		tr1.append(th3.append("Vehicle"));
		tr1.append(th4.append("Location"));
		tr1.append(th5.append("Open & Closed Date"));
		tr1.append(th6.append("Amount"));
		tr1.append(th7.append("Status"));
		tr1.append(th8.append("Age"));

		thead.append(tr1);
		
		var tbody = $('<tbody>');
		if(data.workOrder != undefined && data.workOrder.length > 0) {
			
			var workOrder = data.workOrder;
		
			for(var i = 0; i < workOrder.length; i++) {
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td width="3%">');
				var td2		= $('<td width="10%">');
				var td3		= $('<td class="fit ar" width="15%">');
				var td4		= $('<td class="fit ar" width="20%">');
				var td5		= $('<td class="fit ar" width="20%">');
				var td6		= $('<td width="10%">');
				var td7		= $('<td width="10%">');
				var td8		= $('<td width="5%">');
				
				tr1.append(td1.append(i + 1));
				tr1.append(td2.append('<a href="showWorkOrder?woId='+workOrder[i].workorders_id+'" target="_blank" >'+"WO-"+workOrder[i].workorders_Number));
				tr1.append(td3.append('<a href="showVehicle.in?vid='+workOrder[i].vehicle_vid+'" target="_blank" >'+workOrder[i].vehicle_registration));
				tr1.append(td4.append(workOrder[i].workorders_location));
				tr1.append(td5.append(workOrder[i].start_date+'</br>'+workOrder[i].completed_date));
				tr1.append(td6.append(workOrder[i].totalworkorder_cost));
				
				if(workOrder[i].workorders_statusId == 1){
					tr1.append(td7.append('<span class="label bg-gradient-secondary">'+workOrder[i].workorders_status));
				}
				if(workOrder[i].workorders_statusId == 2){
					tr1.append(td7.append('<span class="label bg-gradient-info">'+workOrder[i].workorders_status));
				}
				if(workOrder[i].workorders_statusId == 3){
					tr1.append(td7.append('<span class="label bg-gradient-warning">'+workOrder[i].workorders_status));
				}
				if(workOrder[i].workorders_statusId == 4){
					tr1.append(td7.append('<span class="label bg-gradient-success">'+workOrder[i].workorders_status));
				}
				
				if(workOrder[i].workorders_statusId != 4){
					tr1.append(td8.append(workOrder[i].ageing));
				} else {
					tr1.append(td8.append("-"));
				}

				tbody.append(tr1);
			}
		}else{
			showMessage('info','No record found !')
		}
		
		$("#workOrdersTableDataDetails").append(thead);
		$("#workOrdersTableDataDetails").append(tbody);
		
		hideLayer();
	
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

/*function setAllLocationWiseWorkOrderCount(data){

$("#locationTable").empty();
var locationWiseWOCount =  data.locationWiseWOCount;
var thead 	= $('<thead>');
var tr 		= $('<tr>');
var tbody 	= $('<tbody>');
var subtrmain = $('<tr id="mainTr">');


setTimeout( function (){
	console.log("locationWiseWOCount",locationWiseWOCount)
	
	if(locationWiseWOCount != "" ){
		for(var i = 0; i < locationWiseWOCount.length; i++){
			
			var th 			= $('<th class="card-body title" colspan="2" width="500px"">');
			var th1 		= $('<th class="card-body" colspan="2" valign="top">');
			th.append(locationWiseWOCount[i].workorders_location);
			tr.append(th);
			
			var subTable = $('<table id="subTable_'+i+'" width="100%" >');

			var subTr	 = $('<tr>');
			var subTh1	 = $('<th style="font-size: 15px; text-align: center;">');
			var subTh2	 = $('<th style="font-size: 15px; text-align: center;" >');
			
			subTh1.append("OPEN")
			subTh2.append("CLOSE")
			
			subTr.append(subTh1);
			subTr.append(subTh2);
			
			subTable.append(subTr);
			th1.append(subTable);
			subtrmain.append(th1);
			
			var tr1 	= $('<tr>');
			var td 		= $('<td class="count-size">');
			var td1 	= $('<td class="count-size">');
			console.log("locationWiseWOCount[i].workOrderOpenCount",locationWiseWOCount[i].workOrderOpenCount)
			td.append(locationWiseWOCount[i].workOrderOpenCount != null ? locationWiseWOCount[i].workOrderOpenCount : 0);
			td1.append(locationWiseWOCount[i].workOrderCloseCount != null ? locationWiseWOCount[i].workOrderCloseCount : 0);
			tr1.append(td);
			tr1.append(td1);
			console.log(" i",i,locationWiseWOCount[i].workOrderCloseCount,$("#subTable_"+i))
			$("#subTable_"+i).append(tr1)
		}
	}else{
		for(var i = 0; i<partLocation.length; i++){
			var tr1 	= $('<tr class="mb-9" >');
			var td 		= $('<td>');
			var td1 	= $('<td>');
			td.append(0);
			td1.append(0);
			tr1.append(td);
			tr1.append(td1);
			$("#subTable_"+i).append(tr1)
		}
	}
},100);
thead.append(tr);
thead.append(subtrmain);
$("#locationTable").append(thead);
$("#locationTable").append(tbody);
}*/

/*function setAllLocationWiseWorkOrderCount(data){
var partLocation 				= data.partLocation;
var locationWiseOpenWOCount 	= data.locationWiseOpenWOCount;
var locationWiseOpenWOCountHM 	= data.locationWiseOpenWOCountHM;
console.log("locationWiseOpenWOCount",locationWiseOpenWOCount);
console.log("locationWiseOpenWOCountHM",locationWiseOpenWOCountHM);
var locationWiseCloseWOCount 	= data.locationWiseCloseWOCount;
var locations					= Object.keys(locationWiseOpenWOCountHM);
var thead 	= $('<thead>');
var tbody 	= $('<tbody>');
var subTable	= $('<table>')
var subTH1		= $('<th class="mb-9">')
var subTH2		= $('<th class="mb-9">')

subTH1.append("Open")
subTH2.append("Close")

subTable.append(subTH1);
subTable.append(subTH2);

var tr 		= $('<tr>');
var tr1 	= $('<tr>');
for(var i = 0; i < locations.length; i++){
	var th 		= $('<th class="mb-9" >');
	
	th.append(locationWiseOpenWOCountHM[locations[i]].workorders_location);
	console.log("thead >>>",locationWiseOpenWOCountHM[locations[i]])
	
	tr.append(th);
	thead.append(tr);
	
	var td		= $('<td class="mb-10" >');
	td.append(subTable);
	var subTD1		= $('<td>')
	var subTD2		= $('<td>')
	
	subTD1.append(locationWiseOpenWOCountHM[locations[i]].workOrderOpenCount);
	subTD2.append(locationWiseOpenWOCountHM[locations[i]].workOrderCloseCount);
	
	tr1.append(subTD1)
	tr1.append(subTD2)
	
	subTable.append(tr1)
	tbody.append(subTable);
}
	

console.log("thead",thead)
$("#locationTable").append(thead);
$("#locationTable").append(tbody);


}*/

