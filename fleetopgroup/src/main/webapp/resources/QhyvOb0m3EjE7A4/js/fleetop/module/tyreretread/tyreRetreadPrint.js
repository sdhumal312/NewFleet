
function getTyreRetreadPrint1(TRID) {
	console.log(TRID)

	
	var jsonObject			= new Object();
	console.log(jsonObject)

	jsonObject["TRID"]		= TRID;
	console.log("1",jsonObject)
	$.ajax({
		
		url: "TyreRetreadWS/getTyreRetreadPrint.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.company_id != undefined) {
				console.log("data....",data)
				
				$("#tableContain").load( "html/TyreRetreadPrint.html", function() {
					setTyreRetreadPrint(data);
					window.setTimeout(printAfterDelay, 1000);
					console.log("2"+data)
				});				
			}
		},
		error: function (e) {
			console.log("error : " , e);
		}
	});
}

function setTyreRetreadPrint(data) {

	var companyDetails 	= data.companyDetails;
	var InventoryTyreRetread 		= data.InventoryTyreRetread;
	var InventoryTyreRetread_Amount	=data.InventoryTyreRetread_Amount;
	/*var workOrderTasks 	= data.WorkOrderTasks;
	*/
	/*$(".status").html(InventoryTyreRetread.tr_STATUS);
	$(".retreadtyre").html(InventoryTyreRetread.trnumber);
	$(".retreaddate").html(InventoryTyreRetread.tr_OPEN_DATE);
	$(".invoicenumber").html(InventoryTyreRetread.tr_INVOICE_NUMBER);

	$(".tyrecost").html(InventoryTyreRetreadAmount.retread_COST);
	$(".discount").html(InventoryTyreRetreadAmount.retread_DISCOUNT);
	$(".GST").html(InventoryTyreRetreadAmount.retread_TAX);
	$(".total").html(InventoryTyreRetreadAmount.retread_AMOUNT);
	$(".intialnote").html(InventoryTyreRetread.tr_DESCRIPTION);
	$(".recievednote").html(InventoryTyreRetread.tr_RE_DESCRIPTION);
	$(".subtotal").html(InventoryTyreRetread.TR_AMOUNT);
	$(".retreadtotal").html(InventoryTyreRetread.tr_ROUNT_AMOUNT);*/
	/*$(".engineNo").html(workOrder.totalbatteryrunning);*/
	/*$(".fuel").html(workOrder.scrapedby);
	$(".odometer").html(workOrder.scrapeddate);*/
	
	$(".intialnote").html(InventoryTyreRetread.tr_DESCRIPTION);
	$(".recievednote").html(InventoryTyreRetread.tr_RE_DESCRIPTION);
	$(".subtotal").html(InventoryTyreRetread.tr_AMOUNT);
	$(".retreadtotal").html(InventoryTyreRetread.tr_ROUNT_AMOUNT);
	
	
	for(var i = 0; i < InventoryTyreRetread_Amount.length; i++) {
			var tableRow		= createRow(i,'');

			/*var srNoCol			= createColumnInRow(tableRow, '', 'alignCenter', '10%', 'left', '', '');*/
			var status			= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var retreadtyre		= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');

			var tyrecost		= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var GST				= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var Discount		= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');
			var total			= createColumnInRow(tableRow, '', '', '20%', 'left', '', '');


			/*appendValueInTableCol(srNoCol,  (i+1));*/
			appendValueInTableCol(status,  (InventoryTyreRetread_Amount[i].tra_STATUS));
			appendValueInTableCol(retreadtyre,  (InventoryTyreRetread_Amount[i].tyre_NUMBER));
			appendValueInTableCol(tyrecost,  (InventoryTyreRetread_Amount[i].retread_COST));
			appendValueInTableCol(GST,  (InventoryTyreRetread_Amount[i].retread_TAX));
			appendValueInTableCol(Discount,  (InventoryTyreRetread_Amount[i].retread_DISCOUNT));
			appendValueInTableCol(total,  (InventoryTyreRetread_Amount[i].retread_AMOUNT));
		

			$('.InventoryTyreRetread_Amount').append(tableRow);
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

