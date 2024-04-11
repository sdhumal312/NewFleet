var ISSUE_CREATED 		= 0;
var ISSUE_OPEN 			= 1;
var ISSUE_IN_PROCESS 	= 2;
var ISSUE_HOLD 			= 3;
var ISSUE_CLOSED 		= 4;
var ISSUE_0_TO_7 		= 5;
var ISSUE_8_TO_15 		= 6;
var ISSUE_15UP 			= 7;

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
	getAllIssueCountData();
}

);

function getAllIssueCountData(date){
	
	var dateShow = $('#dateRange').val();
	var jsonObject	= new Object();
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0].trim();
	var endDate		= dateRange[1].trim();
	var compId		= getUrlParameter('DateRange').split("to")[2];
	$('#companyId').val(compId);
	
	$("#cancel, #cancel1").click(function(){
 		location.replace('viewSummary?DateRange='+dateShow);
 	});
	
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= $('#companyId').val();
	jsonObject["group"]			= $('#SelectGroup').val();
	
	showLayer();
	$.ajax({
		url: "getAllIssueCountData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setAllIssueCountData(data, startDate, endDate);
			$('#dateRange').val(startDate+' to '+endDate);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
	
}

function setAllIssueCountData(data, startDate, endDate){
	console.log(":::",data)
	
	$("#issueDate").text('Issue Details between '+startDate+' & '+endDate+' ('+data.noOfDaysInDateRange+'Days) !');
	
	$('#issueCreatedCounts').text(data.issueCreatedCounts);
	$('#issueOpenCounts').text(data.issueOpenCounts);
	$('#issueProcessCount').text(data.issueProcessCounts);
	$('#issueResolveCounts').text(data.issueResolveCounts);
	$('#issueRejectedCounts').text(data.issueRejectedCounts);
	$('#issueCloseCounts').text(data.issueCloseCounts);
	
	$('#issueAllOpenCounts').text(data.issueAllOpenCounts);
	$('#issueOverDueCounts').text(data.issueOverDueCounts);
	$('#from7Days').text(data.from7Days);
	$('#from15Days').text(data.from15Days);
	$('#from30Days').text(data.from30Days);
	backToGraph();
}

function showPiChart(){
	var openCount		=	Number($('#issueOpenCounts').text());
	var processCount	=	Number($('#issueProcessCount').text());
	var closeCount		=	Number($('#issueCloseCounts').text());
	var resolvedCount	=	Number($('#issueResolveCounts').text());
	var rejectedCount	=	Number($('#issueRejectedCounts').text());
	var holdCount		=	Number($('#issueHoldCount').text());
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
	      
	      var gradientStrokeRandom = ctx.createLinearGradient(0, 0, 0, 150);
	      gradientStrokeRandom.addColorStop(0, 'rgb(0,255,255)');
	      gradientStrokeRandom.addColorStop(1, 'rgb(0,255,255)');
	      var gradientLegendRandom = 'linear-gradient(to right, rgb(0,255,255), rgb(0,255,255))'; 

	     var trafficChartData = {
	        datasets: [{
	        	  data: [openCount, processCount, closeCount, resolvedCount, rejectedCount],
	          backgroundColor: [
	        	gradientStrokeGreen,
	            gradientStrokeBlue,
	            gradientStrokeRed,
	            gradientStrokeYellow,
	            gradientStrokeRandom
	          ],
	          
	          hoverBackgroundColor: [
	        	  gradientStrokeGreen,
		            gradientStrokeBlue,
		            gradientStrokeRed,
		            gradientStrokeYellow,
		            gradientStrokeRandom
	          ],
	          
	          borderColor: [
	        	  gradientStrokeGreen,
		            gradientStrokeBlue,
		            gradientStrokeRed,
		            gradientStrokeYellow,
		            gradientStrokeRandom
	          ],
	          
	          borderWidth: [1, 1, 1, 1, 1]
	        }],
	    
	        // These labels appear in the legend and in the tooltips when hovering different arcs
	        labels: [
	            'Open',
	            'In Process',
	            'Close',
	            'Resolved',
	            'Rejected'
	          ]
	      };

	     var options = {
	    	        responsive: true,
	    	        title: {
	    	            display: true,
	    	            position: "top",
	    	            text: "Issue Status",
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
	
	getIssueTableData(type);
	$('#issueTableDataDetails').removeClass('hide');
	$('#tsTable').removeClass('hide');
	
}

function backToGraph(){
	
	$('#issueTableDataDetails').addClass('hide');
	$('#tsTable').addClass('hide');
	$('#graphButton').addClass('hide');
	$('#printBtn').addClass('hide');
	$('#chartContainer').removeClass('hide');
	showPiChart();
	
}

function getIssueTableData(type){
	var jsonObject	= new Object();
	
	var dateRange 	= $('#dateRange').val().split("to");
	var startDate	= dateRange[0];
	var endDate		= dateRange[1];
	var compId		= getUrlParameter('DateRange').split("to")[2];
	
	jsonObject["type"]			= type;
	jsonObject["startDate"]		= startDate;
	jsonObject["endDate"]		= endDate;
	jsonObject["compId"]		= compId;
	jsonObject["vGroup"]		= $('#SelectGroup').val();
	
	showLayer();
	$.ajax({
		url: "getIssueTableData",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setIssueTableData(data,type);	
			hideLayer();
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			
		}
	});
}

function setIssueTableData(data){
	console.log("data",data)
		
		$("#issueTableDataDetails").empty();
		
		var thead = $('<thead>');
		var tr1 = $('<tr width="100%">');

		var th1		= $('<th style="width: 10%;">');
		var th2		= $('<th style="width: 10%;" >');
		var th3		= $('<th style="width: 20%;" class="fit ar">');
		var th4		= $('<th  style="width: 20%;" class="fit ar">');
		var th5		= $('<th  style="width: 20%;" class="fit ar">');
		var th6		= $('<th  style="width: 10%;">');
		var th7		= $('<th  style="width: 10%;">');

		tr1.append(th1.append("No"));
		tr1.append(th2.append("Issue No"));
		tr1.append(th3.append("Vehicle/Driver"));
		tr1.append(th4.append("VGroup"));
		tr1.append(th5.append("Issue Reported Date"));
		tr1.append(th6.append("Status"));
		tr1.append(th7.append("Age"));

		thead.append(tr1);
		
		var tbody = $('<tbody>');
		if(data.issue != undefined && data.issue.length > 0) {
			
			var issue = data.issue;
		
			for(var i = 0; i < issue.length; i++) {
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td width="3%">');
				var td2		= $('<td width="10%">');
				var td3		= $('<td class="fit ar" width="20%">');
				var td4		= $('<td class="fit ar" width="20%">');
				var td5		= $('<td class="fit ar" width="15%">');
				var td6		= $('<td width="10%">');
				var td7		= $('<td width="10%">');
				
				tr1.append(td1.append(i + 1));
				tr1.append(td2.append('<a href="showIssues.in?Id='+issue[i].issues_ID_ENCRYPT+'" target="_blank" data-toggle="tooltip" data-placement="top" title="'+issue[i].issues_SUMMARY+'" >'+"I-"+issue[i].issues_NUMBER));
				tr1.append(td3.append('<a href="showVehicle.in?vid='+issue[i].issues_VID+'" target="_blank" >'+issue[i].issues_VEHICLE_REGISTRATION +'</br>'+ issue[i].issues_DRIVER_NAME));
				tr1.append(td4.append(issue[i].issues_VEHICLE_GROUP));
				tr1.append(td5.append(issue[i].issues_REPORTED_DATE));
				if(issue[i].issues_STATUS_ID == 1){
					tr1.append(td6.append('<span class="label bg-gradient-secondary">'+issue[i].issues_STATUS));
				}
				if(issue[i].issues_STATUS_ID == 2){
					tr1.append(td6.append('<span class="label bg-gradient-success">'+issue[i].issues_STATUS));
				}
				if(issue[i].issues_STATUS_ID == 3){
					tr1.append(td6.append('<span class="label bg-gradient-info">'+issue[i].issues_STATUS));
				}
				if(issue[i].issues_STATUS_ID == 4){
					tr1.append(td6.append('<span class="label bg-gradient-success">'+issue[i].issues_STATUS));
				}
				if(issue[i].issues_STATUS_ID == 5){
					tr1.append(td6.append('<span class="label bg-gradient-primary">'+issue[i].issues_STATUS));
				}
				if(issue[i].issues_STATUS_ID == 6){
					tr1.append(td6.append('<span class="label bg-gradient-info">'+issue[i].issues_STATUS));
				}
				
				if(issue[i].issues_STATUS_ID == 1 || issue[i].issues_STATUS_ID == 3 || issue[i].issues_STATUS_ID == 6){
					tr1.append(td7.append(issue[i].ageing));
				} else {
					tr1.append(td7.append("-"));
				}

				tbody.append(tr1);
			}
		}else{
			showMessage('info','No record found !')
		}
		
		$("#issueTableDataDetails").append(thead);
		$("#issueTableDataDetails").append(tbody);
		
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


