$(document).ready(function() {
	var invoiceSummaryId = $('#invoiceSummaryId').val();
	getInvoiceDetails(invoiceSummaryId);
});

function getInvoiceDetails(invoiceSummaryId) {
	
	showLayer();
	var jsonObject							= new Object();
	jsonObject["invoiceSummaryId"]			= invoiceSummaryId;

	$.ajax({
		url: "getInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInvoiceList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setInvoiceList(data) {

	console.log("invoice...",data);
	
	$("#partyName").text(data.partyName);
	$("#invoiceNo").text(data.invoiceNumber);  
	$("#invoiceDate").text(data.invoiceDate);
	$("#mobileNo").text(data.mobileNo);
	$("#address").text(data.address);
	$("#gstNo").text(data.gstNo);
	$("#amount").text(inWords(data.amount));
	
	$("#VendorPaymentTable").empty();
	var thead = $('<thead style="background-color: aqua;">');
	var tr1 = $('<tr>');
	
	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	
	tr1.append(th1.append("Tripsheet No"));
	tr1.append(th2.append("Vehicle No"));
	tr1.append(th3.append("Party Name"));
	tr1.append(th4.append("Driver"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Pick/Drop Status"));
	tr1.append(th7.append("Pick/Drop Point"));
	tr1.append(th8.append("Rate/KM"));
	tr1.append(th9.append("Usage KM"));
	tr1.append(th10.append("Amount"));
	tr1.append(th11.append("Advance"));
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.invoiceData != undefined && data.invoiceData.length > 0) {
		
		var invoiceData = data.invoiceData;
	
		for(var i = 0; i < invoiceData.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			
			tr1.append(td1.append('<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+invoiceData[i].tripsheetPickAndDropId+'" target="_blank">TS-'+invoiceData[i].tripSheetNumber+'</a>'));
			tr1.append(td2.append(invoiceData[i].vehicleRegistration));
			tr1.append(td3.append(invoiceData[i].vendorName));
			tr1.append(td4.append(invoiceData[i].driverName));
			tr1.append(td5.append(invoiceData[i].journeyDateStr2));
			tr1.append(td6.append(invoiceData[i].pickOrDropStatusStr));
			tr1.append(td7.append(invoiceData[i].locationName));
			tr1.append(td8.append(invoiceData[i].rate));
			tr1.append(td9.append(invoiceData[i].tripUsageKM));
			tr1.append(td10.append((invoiceData[i].amount).toFixed(2)));	
			tr1.append(td11.append((invoiceData[i].tripTotalAdvance).toFixed(2)));
			
			tbody.append(tr1);
		}
		
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="8">');
		//var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');

		td1.append("Total :");
		//td2.append(data.rate);
		td3.append(data.km.toFixed(2));
		td4.append(data.amount.toFixed(2));
		td5.append(data.advance.toFixed(2));

		tr2.append(td1);
		//tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		tr2.append(td5);

		tbody.append(tr2);
		
		$("#VendorPaymentTable").append(thead);
		$("#VendorPaymentTable").append(tbody);
		
	}else{
		showMessage('info','No record found !')
	}
	
}


var a = ['','one ','two ','three ','four ', 'five ','six ','seven ','eight ','nine ','ten ','eleven ','twelve ','thirteen ','fourteen ','fifteen ','sixteen ','seventeen ','eighteen ','nineteen '];
var b = ['', '', 'twenty','thirty','forty','fifty', 'sixty','seventy','eighty','ninety'];

function inWords (num) {
    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return; var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'only ' : '';
    return str;
}

function deleteInvoice(invoiceSummaryId){
	
	if (confirm('Are you sure, you want to Delete this Invoice? Please note that all the tripsheet in this invoice would be eligible for new invoice creation.')) {
	
		if(invoiceSummaryId != undefined && invoiceSummaryId > 0){
			
			var jsonObject					= new Object();
			
			jsonObject["invoiceSummaryId"]	= invoiceSummaryId;
			console.log("jsonObject....",jsonObject);
			
			$.ajax({
				url: "deleteInvoice",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					
					if(data.paymentDone != undefined && data.paymentDone == true){
						hideLayer();
						showMessage('info', 'Invoice Payment is been done for this Invoice Hence can not Delete this Invoice !');
					}
					
					if(data.invoiceDeleted != undefined && data.invoiceDeleted == true){
						hideLayer();
						showMessage('success', 'Invoice Deleted Successfully !');
						window.location.replace("createPickOrDropInvoice");
					}
					
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	  
	}
}