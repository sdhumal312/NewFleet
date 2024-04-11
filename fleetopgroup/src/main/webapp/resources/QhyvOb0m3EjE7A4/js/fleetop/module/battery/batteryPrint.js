
function getBatteryInventoryPrint1(batteryId) {
	
	
	var jsonObject			= new Object();
	

	jsonObject["batteryId"]		= batteryId;
	
	$.ajax({
		
		url: "BatteryWS/getBatteryInventoryPrint.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.company_id != undefined) {
				
				$("#tableContain").load( "html/BatteryPrint.html", function() {
					setBatteryPrint(data);
					window.setTimeout(printAfterDelay, 1000);
					
				});				
			}
		},
		error: function (e) {
			console.log("error : " , e);
		}
	});
}

function setBatteryPrint(data) {
	var companyDetails 	= data.companyDetails;
	var Battery 		= data.Battery;
	var workOrderTasks 	= data.WorkOrderTasks;
	
	$(".batteryNumber").html(Battery.batterySerialNumber);
	
	$(".batteryamount").html(Battery.batteryAmount);
	
	$(".manufacturername").html(Battery.manufacturerName);
	
	$(".vehiclename").html(Battery.vehicle_registration);

	$(".batterymodel").html(Battery.batteryType);
	
	$(".openodometer").html(Battery.openOdometer);
	
	$(".batterycapacity").html(Battery.batteryCapacity);
	
	$(".closeodometer").html(Battery.closedOdometer);
	
	$(".locationname").html(Battery.locationName);
	
	$(".batterypurchasedate").html(Battery.purchaseDate);
	
	$(".status").html(Battery.batteryStatus);
	
	$(".batteryassigndate").html(Battery.asignedDate);
	
	
	//5 Elements pending task by yogi
	
	
	$(".warrantystatus").html(Battery.warrantyStatus);
	$(".warrantyperiod").html(Battery.warrantyPeriodStr);	
	$(".warrantycounter").html(Battery.availableWarrantyType);
	$(".costperday").html(Battery.costPerDay);
	$(".costperodometer").html(Battery.costPerOdometer);
	
	
	/*$(".engineNo").html(workOrder.totalbatteryrunning);*/
	/*$(".fuel").html(workOrder.scrapedby);
	$(".odometer").html(workOrder.scrapeddate);*/
	
/*	
	for(var i = 0; i < workOrderTasks.length; i++) {
			var tableRow		= createRow(i,'');

			var srNoCol			= createColumnInRow(tableRow, '', 'alignCenter', '10%', 'left', '', '');
			var subJobTypeCol	= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var remarkCol		= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');

			appendValueInTableCol(srNoCol,  (i+1));
			appendValueInTableCol(subJobTypeCol,  (workOrderTasks[i].job_subtypetask));
			appendValueInTableCol(remarkCol,  (workOrderTasks[i].jobTypeRemark));

			$('.workOrdersTasks').append(tableRow);
	}*/
	
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

