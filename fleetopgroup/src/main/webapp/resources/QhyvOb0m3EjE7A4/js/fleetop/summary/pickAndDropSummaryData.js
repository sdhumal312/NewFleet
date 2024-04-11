
var startDate;
var endDate;
var compId;

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
	
	getAllPickAndDropCountData();
}

);

function getAllPickAndDropCountData(){
	
	var dateShow = $('#dateRange').val();
	var jsonObject	= new Object();
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
		url: "getPickAndDropDataCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setPickAndDropDataCount(data, startDate, endDate);
			$('#dateRange').val(startDate+' to '+endDate);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
	
}

function setPickAndDropDataCount(data, startDate, endDate){
	
	$("#pickDropDate").text('Pick And Drop Details between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
	
	$('#pickDropCreatedCounts').text(data.pickDropCreatedCounts);
	$('#vehiclesWithPickDrop').text(data.vehiclesWithPickDrop);
	$('#vehiclesWithoutPickDrop').text(data.vehiclesWithoutPickDrop);
	$("#totalAmount").text(addCommas(data.totalAmount.toFixed(2))).addClass("inr-sign");
	$('#totalPickUps').text(data.totalPickUps);
	$('#totalDrops').text(data.totalDrops);
	
	showPiChart();
}

function showPiChart(){
	var totalPickUps		=	Number($('#totalPickUps').text());
	var totalDrops			=	Number($('#totalDrops').text());
	
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
	

	     var trafficChartData = {
	        datasets: [{
	        	  data: [totalPickUps, totalDrops],
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
	            'Pick Ups',
	            'Drops'
	          ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "Pick And Drop Status",
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


function backToGraph(){
	
	$('#rrTableDataDetails').addClass('hide');
	$('#rrTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
}


function showPickDropDetails(type){
	
	if(!$('#chartContainer').hasClass('hide')){
		
		$('#chartContainer').addClass('hide');
		$('#graphButton').removeClass('hide');
		$('#printBtn').removeClass('hide');
		
		getPickDropTableData(type);
			
		$('#rrTableDataDetails').removeClass('hide');
		$('#rrTable').removeClass('hide');
		
	} else {
		
		getPickDropTableData(type);
		
	}
	
}

function getPickDropTableData(type){
	
	var jsonObject	= new Object();
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	
	showLayer();
	$.ajax({
		url: "getPickAndDropTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(type == 0 || type == 4 || type == 5){
				hideLayer();
				pickDropCreatedDetails(data, type);
			} else if(type == 1){
				hideLayer();
				vehicleWithPickDropDetails(data, type);
			} else if(type == 2){
				hideLayer();
				vehicleWithoutPickDropDetails(data, type);
			} else {
				hideLayer();
				totalExpense(data, type);	
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function pickDropCreatedDetails(data, type) {
	$("#rrTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th0		= $('<th>');
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');

	tr1.append(th0.append("SR-No"));
	tr1.append(th1.append("TS-No"));
	tr1.append(th2.append("Vehicle No"));
	tr1.append(th3.append("Party Name"));
	tr1.append(th4.append("Driver"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Status"));
	tr1.append(th7.append("Location"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.pickAndDrop != undefined && data.pickAndDrop.length > 0) {
		
		var pickAndDrop = data.pickAndDrop;
	
		for(var i = 0; i < pickAndDrop.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td0		= $('<td>');
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			
			tr1.append(td0.append(i + 1));
			tr1.append(td1.append('<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+pickAndDrop[i].tripsheetPickAndDropId+'" target="_blank">TS-'+pickAndDrop[i].tripSheetNumber+'</a>'));
			tr1.append(td2.append(pickAndDrop[i].vehicleRegistration));
			
			if(pickAndDrop[i].vendorId > 0){
				tr1.append(td3.append(pickAndDrop[i].vendorName));
			} else {
				tr1.append(td3.append(pickAndDrop[i].newVendorName));
			}
			
			tr1.append(td4.append(pickAndDrop[i].driverName));
			tr1.append(td5.append(pickAndDrop[i].journeyDateStr2));
			tr1.append(td6.append(pickAndDrop[i].pickOrDropStatusStr));
			
			if(pickAndDrop[i].pickOrDropId > 0){
				tr1.append(td7.append(pickAndDrop[i].locationName));
			} else {
				tr1.append(td7.append(pickAndDrop[i].newPickOrDropLocationName));
			}
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);
	
}

function vehicleWithPickDropDetails(data, type){
	
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
	if(data.pickAndDrop != undefined && data.pickAndDrop.length > 0) {
		
		var pickAndDrop = data.pickAndDrop;
	
		for(var i = 0; i < pickAndDrop.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showVehicle.in?vid='+pickAndDrop[i].vid+'" target="_blank" >'+pickAndDrop[i].vehicleRegistration));
			tr1.append(td3.append(pickAndDrop[i].tripsheetPickAndDropId));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

}

function vehicleWithoutPickDropDetails(data, type){
	
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
	if(data.pickAndDrop != undefined && data.pickAndDrop.length > 0) {
		
		var pickAndDrop = data.pickAndDrop;
	
		for(var i = 0; i < pickAndDrop.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td width="3%">');
			var td2		= $('<td width="20%">');
			var td3		= $('<td class="fit ar" width="10%">');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showVehicle.in?vid='+pickAndDrop[i].vid+'" target="_blank" >'+pickAndDrop[i].vehicle_registration));
			tr1.append(td3.append(0));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);

}

function totalExpense(data, type) {
	$("#rrTableDataDetails").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	
	tr1.append(th6.append("SR-No"));
	tr1.append(th7.append("TS-No"));
	tr1.append(th8.append("Rate/KM"));
	tr1.append(th9.append("Usage KM"));
	tr1.append(th10.append("Amount"));
	tr1.append(th11.append("Advance"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.pickAndDrop != undefined && data.pickAndDrop.length > 0) {
		
		var pickAndDrop = data.pickAndDrop;
	
		for(var i = 0; i < pickAndDrop.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			
			tr1.append(td6.append(i + 1));
			tr1.append(td7.append('<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+pickAndDrop[i].tripsheetPickAndDropId+'" target="_blank">TS-'+pickAndDrop[i].tripSheetNumber+'</a>'));
			tr1.append(td8.append(pickAndDrop[i].rate));
			tr1.append(td9.append(pickAndDrop[i].tripUsageKM));
			tr1.append(td10.append((pickAndDrop[i].amount).toFixed(2)));	
			tr1.append(td11.append((pickAndDrop[i].tripTotalAdvance).toFixed(2)));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#rrTableDataDetails").append(thead);
	$("#rrTableDataDetails").append(tbody);
	
}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
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


