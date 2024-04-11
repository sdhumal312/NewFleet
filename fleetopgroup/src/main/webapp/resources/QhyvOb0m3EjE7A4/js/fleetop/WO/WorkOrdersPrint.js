function getCompanyWiseWorkOrderDetails(workorders_id) {
	var jsonObject			= new Object();

	jsonObject["workorders_id"]		= workorders_id;

	$.ajax({
		url: "workOrderWS/getWorkOrderPrint.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.company_id != undefined) {
				$("#tableContain").load( "html/"+data.company_id+"_workorderprint.html", function() {
					setWorkOrderPrint(data);
					window.setTimeout(printAfterDelay, 1000);
				});				
			}
		},
		error: function (e) {
			console.log("error : " , e);
		}
	});
}

function setWorkOrderPrint(data) {
    data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	var companyDetails 	= data.companyDetails;
	var workOrder 		= data.WorkOrder;
	var workOrderTasks 	= data.WorkOrderTasks;
	
	$(".companyName").html(companyDetails.company_name);
	$(".companyAddress").html(companyDetails.company_address);
	$(".companyMobile").html(companyDetails.company_mobilenumber);
	$(".companyEmail").html(companyDetails.company_email);

	$(".assigneeName").html(workOrder.assignee);
	$(".createdBy").html(workOrder.createdBy);
	$(".workOrderNo").html(workOrder.workorders_Number);
	$(".startDateTime").html(workOrder.start_date);
	$(".dueDateTime").html(workOrder.due_date);
	$(".completeDateTime").html(workOrder.completed_date);
	$(".registrationNo").html(workOrder.vehicle_registration);
	$(".chasisNo").html(workOrder.vehicle_chasis);
	$(".engineNo").html(workOrder.vehicle_engine);
	$(".fuel").html(workOrder.workorders_diesel);
	$(".odometer").html(workOrder.vehicle_Odometer);
	$(".route").html(workOrder.workorders_route);
	$(".driverName").html(workOrder.workorders_drivername);
	$(".driverNumber").html(workOrder.workorders_driver_number);
	$(".workOrderStatus").html(workOrder.workorders_status);
	$(".workOrderCost").html(workOrder.totalworkorder_cost);
	$(".initialNotes").html(workOrder.initial_note);
	
	for(var i = 0; i < workOrderTasks.length; i++) {
			var tableRow		= createRow(i,'');

			var srNoCol			= createColumnInRow(tableRow, '', 'alignCenter', '10%', 'left', '', '');
			var subJobTypeCol	= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var remarkCol		= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');

			appendValueInTableCol(srNoCol,  (i+1));
			appendValueInTableCol(subJobTypeCol,  (workOrderTasks[i].job_subtypetask));
			appendValueInTableCol(remarkCol,  (workOrderTasks[i].jobTypeRemark));

			$('.workOrdersTasks').append(tableRow);
	}
	
}

function printAfterDelay() {
	window.print();
	window.close();
}

/*
 * create new row with parent id which have to append it and some general configuration.
 */
function createRow(Id,Style){
	var newRow 	=  $('<tr/>');
	newRow.attr({
		id : Id
		,style : Style
	});
	return newRow;
}

/*
 * Append value in column of table
 */
function appendValueInTableCol(col, value) {
	$(col).append(value);
}

/*
 * create new column with parent id which have to append it and some general configuration.
 */
function createColumnInRow(tableRow, Id, Class, Width, Align, Style, Collspan) {
	var newCol 	=  $('<td/>');
	
	newCol.attr({
		id 			: Id,
		class		: Class,
		width		: Width,
		align		: Align,
		colspan 	: Collspan,
		style		: Style
	});
	
	$(tableRow).append(newCol);
	
	return newCol;
}