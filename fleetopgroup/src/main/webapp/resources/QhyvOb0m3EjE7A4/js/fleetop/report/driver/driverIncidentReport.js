let COMMENT ='COMMENT';
let ACCIDENT ='ACCIDENT';
let FUEL ='FUEL';
let ISSUE='ISSUE';
let BREAKDOWN='BREAKDOWN';

$(document).ready(function(){
	$('#status').select2();
})


$(function() {
	$('button[type=submit]').click(function(e) {
		showLayer();
		e.preventDefault();

		let status = $('#status').val();
		let dateRange = $('#dateRange').val().trim();
		let dateArray = dateRange.split(' to ');
		let fromDate = dateArray[0].split('-').reverse().join('-');
		let toDate = dateArray[1].split('-').reverse().join('-');
		let object = new Object();
		object['status'] = status;
		object['fromDate'] = fromDate;
		object['toDate'] = toDate;

		$.ajax({
			url: "getDriverIncidentReport",
			type: 'POST',
			dataType: 'json',
			data: object,
			success: function(data) {
				renderReportData(data, dateRange,fromDate,toDate);
			},
			error: function(e) {
				console.log("error : ", e);
				hideLayer();
			}
		})

	})
});


$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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
}

);


function renderReportData(resultData,dateRange,fromDate,toDate) {
	hideLayer();
	if(resultData.list != undefined){
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		var tr = ' <tr data-object-id="">'
			+ '<td class="fit"> Date Range : ' + dateRange + '</td>'
			+ '</tr>';
		$('#selectedReportDetails').append(tr);
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);

		$("#reportHeader").html("Driver Basic Incident Details");
		
		if(resultData.tableConfig != undefined) {

			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
	
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;

		}
		setSlickData(resultData.list, columnConfiguration, tableProperties);
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
		hideLayer();
	}else{
		$('#ResultContent').hide();
		$('#printBtn').addClass('hide');
		showMessage('info', 'No record found !');
		hideLayer();
		}
	}
	function getDriverWiseIncidentDetails(type,driverId,driver){
		showLayer();
		let fromDate = $('#fromDate').val();
		let toDate = $('#toDate').val();
		let companyId = $('#companyId').val();
		let object = new Object();
		$('#tableHEaderName').text(driver);
		object['driverId'] = driverId;
		object['fromDate'] = fromDate;
		object['toDate'] = toDate;
		object['incidentType'] = type;
		object['companyId'] = companyId;

		
			$.ajax({
			url: "getDriverWiseIncidentDetails",
			type: 'POST',
			dataType: 'json',
			data: object,
			success: function(data) {
				setIncidentList(data,type);
			},
			error: function(e) {
				console.log("error : ", e);
				hideLayer();
			}
		})

	}
		
function setIncidentList(data,incidentType){
	$('#taskTable').empty();
		if(incidentType == COMMENT){
			setCommentList(data)	
		}else if(incidentType==ACCIDENT){
			setAccidentDataList(data);
		}else if (incidentType==ISSUE || incidentType == BREAKDOWN){
			setIssueDetails(data);
		}else if(incidentType==FUEL){
			setFuelDetails(data);
		}
		hideLayer();
		$('#taskDetails').modal('show');
}

function setCommentList(data){
	var list =data.list;
	if(list != undefined && list.length >0){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th3.append("Title/Name"));
	tr1.append(th2.append("Comment"));
	tr1.append(th4.append("Comment on "));
	tr1.append(th1.append("Comment by "));
	thead.append(tr1);
	
	var num = 1;
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td4		= $('<td>');
		var td2		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		
		tr1.append(td1.append(num));
		tr1.append(td4.append(task.driver_title));
		tr1.append(td5.append(task.driver_comment));
		tr1.append(td6.append(task.creationDate));
		tr1.append(td2.append(task.createdBy));
		tbody.append(tr1);
		num++;
		
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	}else{
		showMessage('info',"No data Found");
		hideLayer();
	}
}

function setAccidentDataList(data){
		var list =data.list;
	if(list != undefined && list.length >0){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th1.append("Vehicle"));
	tr1.append(th3.append("Accident Date"));
	tr1.append(th2.append("Location"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		tr1.append(td1.append('<a href="showVehicleAccidentDetails?id='+task.encriptedId+'" target="_Blank">'+'AD-'+task.accidentDetailsNumber+'</a>'));
		tr1.append(td3.append(task.vehicleNumber));
		tr1.append(td4.append(task.accidentDateTimeStr));
		tr1.append(td5.append(task.location));
		tbody.append(tr1);
	}
	
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);	
	}else{
		showMessage('info','No Data Found');
	}
}


function setIssueDetails(data){
	var list =data.list;
	if(list != undefined && list.length > 0){
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th1.append("Reported On"));
	tr1.append(th2.append("Status"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		tr1.append(td1.append(task.issuesNumberStr));
		tr1.append(td3.append(task.issues_REPORTED_DATE));
		tr1.append(td4.append(task.issues_STATUS));
		tbody.append(tr1);
	}
	
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
		
	}
	
}

function setFuelDetails(data){
	
var list =data.list;
	$('#taskTable').empty();
	if(list != undefined && list.length > 0){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th4.append("Vehicle"));
	tr1.append(th1.append("Fuel Date "));
	tr1.append(th2.append("Volume"));
	tr1.append(th3.append("KM/L"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		tr1.append(td1.append('<a href="showFuel.in?FID='+task.fuel_id+'" target="_Blank">'+''+task.fuelNumber+'</a>'));
		tr1.append(td5.append(task.vehicle_registration));
		tr1.append(td2.append(task.fuel_date));
		tr1.append(td3.append(task.fuel_liters));
		tr1.append(td4.append(task.fuel_kml));
		tbody.append(tr1);
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	}
}