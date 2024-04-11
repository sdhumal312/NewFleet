//penalty1	accid 2 wo dse 3  issue 4 break 5 milage 6 consump 7

let inspectionPenalty = 1;
let accident = 2;
let woDse = 3;
let vehicleIssue = 4;
let breakDown = 5
let fuelMilage = 6;
let fuelConsumption = 7;

$(document).ready(function(){
$("#branchList").select2( {
        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
            url:"getBranchListsearch", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.branch_name, slug: e.slug, id: e.branch_id
                        }
                    }
                    )
                }
            }
        }
    }
    )
})


$(function() {
	$('button[type=submit]').click(function(e) {
		showLayer();
		e.preventDefault();

		let vId = $('#vehicleId').val();
		let vTypeId = $('#VehicleTypeSelect').val();
		let vLocation = $('#branchList').val();
		let dateRange = $('#dateRange').val().trim();
		let dateArray = dateRange.split(' to ');
		let fromDate = dateArray[0].split('-').reverse().join('-');
		let toDate = dateArray[1].split('-').reverse().join('-');
		let object = new Object();
		object['vId'] = vId;
		object['vTypeId'] = vTypeId;
		object['vLocation'] = vLocation;
		object['fromDate'] = fromDate;
		object['toDate'] = toDate;

		$.ajax({
			url: "getVehicleIncidentReport",
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

		$("#reportHeader").html("Vehicles Basic Incident Details ");
		
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
	function getVehicleWiseIncidentDetails(type,vid,vNumber){
		showLayer();
		let fromDate = $('#fromDate').val();
		let toDate = $('#toDate').val();
		let companyId = $('#companyId').val();
		let object = new Object();
		$('#tableHEaderName').text(vNumber);
		object['vid'] = vid;
		object['fromDate'] = fromDate;
		object['toDate'] = toDate;
		object['incidentType'] = type;
		object['companyId'] = companyId;

		
			$.ajax({
			url: "getVehicleWiseIncidentDetails",
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
//	let inspectionPenalty = 1;
//let accident = 2;
//let woDse = 3;
//let vehicleIssue = 4;
//let breakDown = 5
//let fuelMilage = 6;
//let fuelConsumption = 7;
	$('#taskTable2').empty();
	$('#taskTable').empty();
		if(incidentType == inspectionPenalty){
		setInspectionList(data)	
		}else if(incidentType==accident){
			setAccidentDataList(data);
		}else if (incidentType==woDse){
			setwoDseList(data);
		}else if (incidentType==vehicleIssue || incidentType == breakDown){
			setIssueDetails(data);
		}else if(incidentType==fuelMilage || incidentType==fuelConsumption){
			setFuelDetails(data,incidentType);
		}
		hideLayer();
		$('#taskDetails').modal('show');
}

function setInspectionList(data){
	$('#taskTable').empty();
	var list =data.list;
	if(list != undefined && list.length >0){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
//	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
//	tr1.append(th1.append("Vehicle"));
	tr1.append(th3.append("Inspection Date"));
	tr1.append(th2.append("Completion Date"));
	tr1.append(th4.append("Penalty Amount"));
	thead.append(tr1);
	
	var num = 1;
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		
		tr1.append(td1.append(num));
		tr1.append(td4.append(task.inspectionDateStr));
		tr1.append(td5.append(task.completionDateTimeStr));
		tr1.append(td6.append(task.totalPenalty));
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
		$('#taskTable').empty();
		var list =data.list;
	if(list != undefined && list.length >0){

	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th1.append("Driver Name"));
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
		tr1.append(td3.append(task.driverName));
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


function setwoDseList(data){
var dseList = data.list;
var woList = data.workOrderList;
	setWOList(woList);
	setDSEList(dseList);
}
function setWOList(list){
	if(list != undefined && list.length > 0){
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
	tr1.append(th1.append("Start Date"));
	tr1.append(th3.append("Due Date"));
	tr1.append(th2.append("Cost"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		//tr1.append(td1.append('<a href="showWorkOrder?woId='+task.workorders_id+'" target="_Blank">'+'WO-'+tasks.accidentDetailsNumber+'</a>'));
		tr1.append(td1.append(task.workorders_Numbers));
		tr1.append(td3.append(task.start_date));
		tr1.append(td4.append(task.due_date));
		tr1.append(td5.append(task.totalworkorder_cost));
		tbody.append(tr1);
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
		
	}
}
function setDSEList(list){
	if(list != undefined && list.length > 0){
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th5 =	$('<th>');
	tr1.append(th5.append("No."));
	tr1.append(th1.append("Invoice Date"));
	tr1.append(th2.append("Cost"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	for(var i = 0; i < list.length; i++) {
		var task =list[i];
		var tr1 = $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		tr1.append(td1.append(task.dealerServiceEntriesNumberStr));
		tr1.append(td3.append(task.invoiceDateStr));
		tr1.append(td4.append(task.totalInvoiceCost));
		tbody.append(tr1);
	}
	$('#taskTable2').append(thead);
	$('#taskTable2').append(tbody);
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
		//tr1.append(td1.append('<a href="showWorkOrder?woId='+task.workorders_id+'" target="_Blank">'+'WO-'+tasks.accidentDetailsNumber+'</a>'));
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
	var th5 =	$('<th>');
	
	tr1.append(th5.append("No."));
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
		tr1.append(td1.append('<a href="showFuel.in?FID='+task.fuel_id+'" target="_Blank">'+''+task.fuelNumber+'</a>'));
		tr1.append(td2.append(task.fuel_date));
		tr1.append(td3.append(task.fuel_liters));
		tr1.append(td4.append(task.fuel_kml));
		tbody.append(tr1);
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	}
}