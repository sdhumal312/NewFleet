var WORKORDER_CREATED 		= 0;
var WORKORDER_OPEN 			= 1;
var WORKORDER_IN_PROCESS 	= 2;
var WORKORDER_HOLD 			= 3;
var WORKORDER_CLOSED 		= 4;
var WORKORDER_0_TO_7 		= 5;
var WORKORDER_8_TO_15 		= 6;
var WORKORDER_15UP 			= 7;

$(function() {
	function a(a, b) {
		$("#dateRange").val($('#preDateRange').val());
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
	getAllserviceEntryCountData();
}

);


function getAllserviceEntryCountData(){
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
		url: "getAllSECountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAllSECountData(data, startDate, endDate);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
	
	
}

function setAllSECountData(data, startDate, endDate){
	console.log("dataKKKKKK",startDate)
	
	$("#serviceDate").text('Service Entry Details between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
	
	$('#SECreatedCounts').text(data.SECreatedCounts);
	$('#SEAllOpenCounts').text(data.SEAllOpenCounts);
	$('#SEOpenCounts').text(data.SEOpenCounts);
	$('#SEProcessCount').text(data.SEProcessCounts);
	$('#SECloseCounts').text(data.SECloseCounts);
	
	$('#duePaymentCount').text(data.duePaymentCount);
	$('#duePaymentAmount').text(addCommas(data.duePaymentAmount.toFixed(2))).addClass("inr-sign");
	
	$('#from7Days').text(data.from7Days);
	$('#from15Days').text(data.from15Days);
	$('#from30Days').text(data.from30Days);
	
	$('#dateRange').val(startDate+' to '+endDate);
	
	showPiChart();
}



function showPiChart(){
	var openCount		=	Number($('#SEOpenCounts').text());
	var processCount	=	Number($('#SEProcessCount').text());
	var closeCount		=	Number($('#SECloseCounts').text());
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
	      gradientStrokeYellow.addColorStop(0, 'rgba(178,238,174,1)');
	      gradientStrokeYellow.addColorStop(1, 'rgba(167,233,148,1)');
	      var gradientLegendYellow = 'linear-gradient(to right, rgba(178,238,174,1), rgba(167,233,148,1))'; 

	     var trafficChartData = {
	        datasets: [{
	        	  data: [openCount, processCount, closeCount],
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
	            'In Process',
	            'Close'
	          ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "ServiceEntries Status",
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
	
}

function showTripDetails(type){
	
	$('#chartContainer').addClass('hide');
	$('#graphButton').removeClass('hide');
	$('#printBtn').removeClass('hide');
	
	getSETableData(type);
	$('#serviceEntrryTableDataDetails').removeClass('hide');
	$('#tsTable').removeClass('hide');
	
	/*if(!$('#chartContainer').hasClass('hide')){
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		
		getWorkOrderTableData(type);
		$('#serviceEntrryTableDataDetails').removeClass('hide');
		$('#tsTable').removeClass('hide');
		
	} else {
		$('#chartContainer').removeClass('hide');
		$('#serviceEntrryTableDataDetails').addClass('hide');
		$('#tsTable').addClass('hide');
		$('#graphButton').addClass('hide');
	}*/
	
}

function backToGraph(){
	
	$('#serviceEntrryTableDataDetails').addClass('hide');
	$('#tsTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}

function getSETableData(type){
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
		url: "getServiceEntryTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setSETableData(data,type);	
			hideLayer();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
}

function setSETableData(data){
	console.log("data",data)
		
		$("#serviceEntrryTableDataDetails").empty();
		
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
		tr1.append(th2.append("SE-No"));
		tr1.append(th3.append("Vehicle"));
		tr1.append(th4.append("Vendor"));
		tr1.append(th5.append("Invoice Date"));
		tr1.append(th6.append("Amount"));
		tr1.append(th7.append("Status"));
		

		thead.append(tr1);
		
		var tbody = $('<tbody>');
		if(data.serviceEntry != undefined && data.serviceEntry.length > 0) {
			
			var serviceEntry = data.serviceEntry;
		
			for(var i = 0; i < serviceEntry.length; i++) {
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td width="3%">');
				var td2		= $('<td width="10%">');
				var td3		= $('<td class="fit ar" width="20%">');
				var td4		= $('<td class="fit ar" width="20%">');
				var td5		= $('<td class="fit ar" width="20%">');
				var td6		= $('<td width="10%">');
				var td7		= $('<td width="10%">');
				
				
				tr1.append(td1.append(i + 1));
				tr1.append(td2.append('<a href="ServiceEntriesParts.in?SEID='+serviceEntry[i].serviceEntries_id+'" target="_blank" >'+"SE-"+serviceEntry[i].serviceEntries_Number));
				tr1.append(td3.append('<a href="showVehicle.in?vid='+serviceEntry[i].vid+'" target="_blank" >'+serviceEntry[i].vehicle_registration));
				tr1.append(td4.append(serviceEntry[i].vendor_name));
				tr1.append(td5.append(serviceEntry[i].invoiceDate));
				tr1.append(td6.append(serviceEntry[i].totalserviceROUND_cost));
				
				if(serviceEntry[i].serviceEntries_statusId == 1){
					tr1.append(td7.append('<span class="label bg-gradient-secondary">'+serviceEntry[i].serviceEntries_status));
				}
				if(serviceEntry[i].serviceEntries_statusId == 2){
					tr1.append(td7.append('<span class="label bg-gradient-info">'+serviceEntry[i].serviceEntries_status));
				}
				if(serviceEntry[i].serviceEntries_statusId == 3){
					tr1.append(td7.append('<span class="label bg-gradient-success">'+serviceEntry[i].serviceEntries_status));
				}
				

				tbody.append(tr1);
			}
		}else{
			showMessage('info','No record found !')
		}
		
		$("#serviceEntrryTableDataDetails").append(thead);
		$("#serviceEntrryTableDataDetails").append(tbody);
		
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

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}