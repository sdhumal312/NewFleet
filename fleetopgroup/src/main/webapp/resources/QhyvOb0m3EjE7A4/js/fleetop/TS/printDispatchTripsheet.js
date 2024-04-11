function getDispatchTripsheetDetails(tripsheetId) {
	var jsonObject			= new Object();
	jsonObject["tripsheetId"]		= tripsheetId;
	
	$.ajax({
		url: "getDispatchTripsheetDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			if(data.company_id != undefined) {
				
				$("#tableContain").load( "html/dispatchTripsheetPrint.html", function() {
					setDispatchTripsheet(data);
					window.setTimeout(printAfterDelay, 1000);
					
				});				
			}
		},
		error: function (e) {
			console.log("error : " , e);
		}
	});
}

function setDispatchTripsheet(data) {
	var companyDetails 	= data.companyDetails;
	console.log("companyDetails",companyDetails)
	var tripsheetDetails 		= data.tripsheetDetails;
	
	$(".companyName").html(companyDetails.company_name);
	
	$(".companyAdd").html(companyDetails.company_address);
	
	$(".tripsheetNo").html(tripsheetDetails.tripSheetNumber);
	
	$(".vehicleNo").html(tripsheetDetails.vehicle_registration);
	
	$(".fromDate").html(tripsheetDetails.tripOpenDate);
	
	$(".toDate").html(tripsheetDetails.tripEndDateTimeStr);

	$(".fromDestination").html(tripsheetDetails.dispatchedLocation);
	
	$(".toDestination").html(tripsheetDetails.batteryCapacity);
	
	$(".startingKm").html(tripsheetDetails.tripOpeningKM);
	
	$(".closingKm").html(tripsheetDetails.locationName);
	console.log('advanceAmt : ', tripsheetDetails.tripTotalAdvance);
	
	$(".advanceAmt").html(tripsheetDetails.tripTotalAdvance);
	
	$(".driverName").html(tripsheetDetails.tripFristDriverName);
	
	if(tripsheetDetails.tripSecDriverName != "null null" || tripsheetDetails.tripSecDriverName != null || tripsheetDetails.tripSecDriverName != undefined){
		$(".helperName").html(tripsheetDetails.tripSecDriverName);
	}else{
		$(".helperName").html('');
	}

		
	
}

function printAfterDelay() {
	window.print();
	//window.close();
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

