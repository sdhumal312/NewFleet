function TcktIncmApi(ticketIncomeApiId) {
		if(ticketIncomeApiId != undefined && ticketIncomeApiId > 0){
			var jsonObject	= new Object();
			jsonObject["ticketIncomeApiId"] =  ticketIncomeApiId;
			
			showLayer();
			$.ajax({
				url: "getTicketIncomeApiDeatils",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if($('#allowBitlaApiIncome').val() == 'true' || $('#allowBitlaApiIncome').val() == true){
						setBitlaIncomeApiDeatils(data);setMantisIncomeDeatils
					}else if($('#allowMantisDispatchIncome').val() == 'true' || $('#allowMantisDispatchIncome').val() == true){
						setMantisIncomeDeatils(data);
					}else{
						setTicketIncomeApiDeatils(data);
					}
					hideLayer();
					
				},
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});
		}	
	}

function setTicketIncomeApiDeatils(data) {
	var TicketIncomeApi	= null;
	
	if(data.TicketIncomeApi != undefined) {
		$("#modelBodyloadingSheetIncome").empty();
		TicketIncomeApi	= data.TicketIncomeApi;
		var chartDateTime = new Date(TicketIncomeApi.chartDate);
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10	= $('<th>');
		var th11	= $('<th>');
		

		th1.append('Sr No');
		th2.append('Route Name');
		th3.append('Branch Amt');
		th4.append('Guest Amt');
		th5.append('OfflineAgent Amt');
		th6.append('OnlineAgents Amt');
		th7.append('BranchSeat Count');
		th8.append('GuestSeat Count');
		th9.append('OffAgentSeat Count');
		th10.append('OnlAgentsSeat Count');
		th11.append('Chart Date&Time');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);
		tr1.append(th11);

		thead.append(tr1);
		
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');
			var td11	= $('<td>');

			
			td1.append(1);
			td2.append(TicketIncomeApi.routeName); 
			td3.append(TicketIncomeApi.branchAmt);
			td4.append(TicketIncomeApi.guestAmt);
			td5.append(TicketIncomeApi.offlineAgentAmt);
			td6.append(TicketIncomeApi.onlineAgentsAmt);
			td7.append(TicketIncomeApi.branchSeatcount);
			td8.append(TicketIncomeApi.guestSeatcount);
			td9.append(TicketIncomeApi.offAgentSeatcount);
			td10.append(TicketIncomeApi.onlAgentsSeatcount);
			td11.append(chartDateTime);
			

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
			tr.append(td10);
			tr.append(td11);

			tbody.append(tr);
		
		
			$("#modelBodyloadingSheetIncome").append(thead);
			$("#modelBodyloadingSheetIncome").append(tbody);
			$('#loadingSheetIncome').modal('show');
		
	} else {
		
		showMessage('info','No record found !');
	}
}

function setBitlaIncomeApiDeatils(data){

	var TicketIncomeApi	= null;
	
	if(data.TicketIncomeApi != undefined) {
		$("#modelBodyloadingSheetIncome").empty();
		TicketIncomeApi	= data.TicketIncomeApi;
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		

		th1.append('Sr No');
		th2.append('Trip Count');
		th3.append('Total Amount');

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);

		thead.append(tr1);
		
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');

			
			td1.append(1);
			td2.append(TicketIncomeApi.noOfTrip); 
			td3.append(TicketIncomeApi.branchAmt);
			

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);

			tbody.append(tr);
		
		
			$("#modelBodyloadingSheetIncome").append(thead);
			$("#modelBodyloadingSheetIncome").append(tbody);
			$('#loadingSheetIncome').modal('show');
		
	} else {
		
		showMessage('info','No record found !');
	}

}

function setMantisIncomeDeatils(data){

	var TicketIncomeApi	= null;
	
	if(data.TicketIncomeApi != undefined) {
		$("#modelBodyloadingSheetIncome").empty();
		TicketIncomeApi	= data.TicketIncomeApi;
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		

		th1.append('Sr No');
		th2.append('Actual weight');
		th3.append('Charged weight');
		th4.append('Quantity');
		th5.append('Net Amount');
		th6.append('Less Commission');

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);

		thead.append(tr1);
		
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');

			
			td1.append(1);
			td2.append(TicketIncomeApi.actualweight); 
			td3.append(TicketIncomeApi.chargedWeight);
			td4.append(TicketIncomeApi.quantity);
			td5.append(TicketIncomeApi.netAmount);
			td6.append(TicketIncomeApi.lessCommission);
			

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);

			tbody.append(tr);
		
		
			$("#modelBodyloadingSheetIncome").append(thead);
			$("#modelBodyloadingSheetIncome").append(tbody);
			$('#loadingSheetIncome').modal('show');
		
	} else {
		
		showMessage('info','No record found !');
	}

}

function closeTripSheetAcc(){

	if($('#allowTallyIntegration').val() == 'true' || $('#allowTallyIntegration').val() == true){
		if($('#tripVoucherDate').val() == '' && ($('#hideVoucherDate').val() == 'false' || $('#hideVoucherDate').val() == false) ){
			showMessage('info', 'Voucher Date is not available for trip please save voucher date first !');
			return false;
		}
		if(Number($('#tallyCompanyId').val()) <= 0   &&  ($('#hideTallyCompany').val() == 'false' || $('#hideTallyCompany').val() == false)){
			showMessage('info', 'Please add tally company first to account close !');
			return false;
		}
	}
	if(($('#allowAccountCloseWithoutIncome').val() == 'false' || $('#allowAccountCloseWithoutIncome').val() == false) && (Number($('#tripsheetTotalIncome').val()) <= 0)){
		showMessage('info', 'No Income Found Hence You Can Not Close Account to This Trip!');
		return false;
	}
	
	if(confirm('Are you sure? Close Account')){
		if( $('#directAccountClose').val() == 'true' ||  $('#directAccountClose').val() == true){
			  var jsonObject			= new Object();
				jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
				jsonObject["companyId"] 	  		=  $('#companyId').val();
				jsonObject["userId"] 	  			=  $('#userId').val();
				jsonObject["accountClosedAmount"] 	=  Number($('#tripsheetTotalIncome').val()) - Number($('#tripTotalexpense').val());
				jsonObject["accountClosedRef"] 	  	=  $('#tripSheetId').val();
				
				closeTripAccount(jsonObject);
		}else{
			return true;
		}
		
    }
	
	return false;
}