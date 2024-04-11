function addIVCargoLOadingSheetDetails(tripSheetId, vid, vehicle_registration, opentripDate, closetripDate){
		var jsonObject			= new Object();
		var VehicleNumber = vehicle_registration;
		
		var closeDate = closetripDate.split("-").reverse().join("-");
		jsonObject["fromDate"] 	  			= opentripDate;
		jsonObject["toDate"] 	  			= closeDate+' 23:59:59';
		jsonObject["vehicleNumber"] 					= VehicleNumber;
		jsonObject["tripSheetId"] 						=  tripSheetId;
		jsonObject["vid"] 								=  vid;
		jsonObject["vehicle_registration"] 				=  VehicleNumber;
		
		showLayer();
		$.ajax({
	             url: "getIVLoadingSheetDataForTrip",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 console.log('data : ',data);
	            	 showMessage('success', 'Data added successfully !');
	            	 showLayer();
	            	 window.location.replace("showTripSheet.in?tripSheetID="+tripSheetId);
	            	
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	
}

function getLoadingSheetIncomeDetails(dispatchLedgerId, tripSheetId){
	var jsonObject			= new Object();
	jsonObject["dispatchLedgerId"] 								=  dispatchLedgerId;
	jsonObject["tripSheetId"] 								=  tripSheetId;
	showLayer();
	$.ajax({
             url: "getLoadingSheetIncomeDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setModalData(data);
            	 hideLayer();

             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}


function setModalData(data){
	var loadingSheetToTripSheetList	= null;
	if(data.loadingSheetToTripSheetList != undefined) {

		$("#modelBodyloadingSheetIncome").empty();
		loadingSheetToTripSheetList	= data.loadingSheetToTripSheetList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7 	= $('<th>');
		var th8 	= $('<th>');

		th1.append('Sr No');
		th2.append('WayBill No');
		th3.append('LS Number');
		
		if($("#showLsSourceAndDestination").val() == "true")
		{
			th4.append("LS SourceBranch");
			th5.append("LS DestinationBranch");
		}
		th6.append('Freight');
		th7.append('Booking Total');
		th8.append('WayBill Type');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		
		if($("#showLsSourceAndDestination").val() == "true")
		{
			tr1.append(th4);
		    tr1.append(th5);
		}
		
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		
		thead.append(tr1);
		
		var totalIncomeAmount = 0;
		var totalFrieghtAmount = 0;

		for(var i = 0; i < loadingSheetToTripSheetList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			
			td1.append(i+1);
			td2.append(loadingSheetToTripSheetList[i].wayBillNumber); 
			td3.append(loadingSheetToTripSheetList[i].lsNumber);
			
			if($("#showLsSourceAndDestination").val() == "true")
			{
				td4.append(loadingSheetToTripSheetList[i].lsSourceBranch);
				td5.append(loadingSheetToTripSheetList[i].lsDestinationBranch)
			}
			
			td6.append(loadingSheetToTripSheetList[i].freight);
			td7.append(loadingSheetToTripSheetList[i].bookingTotal);
			td8.append(getWayBillTypeStr(loadingSheetToTripSheetList[i].waybillTypeId));
			
			totalIncomeAmount += loadingSheetToTripSheetList[i].bookingTotal;
			totalFrieghtAmount += loadingSheetToTripSheetList[i].freight;

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			
			if($("#showLsSourceAndDestination").val() == "true")
			{
				tr.append(td4);
				tr.append(td5);
			}
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			
			tbody.append(tr);
		}
		
		var tr2 = $('<tr>');
		if($("#showLsSourceAndDestination").val() == "true")
		{
			var td1		= $('<td colspan="5">');
		}
		else
		{
			var td1		= $('<td colspan="3">');
		}
		
		var td2		= $('<td>');
		var td3		= $('<td>');
		


		td1.append("Total :");
		td2.append(totalFrieghtAmount.toFixed(2));
		td3.append(totalIncomeAmount.toFixed(2));
		

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
	

		tbody.append(tr2);
		
		$("#modelBodyloadingSheetIncome").append(thead);
		$("#modelBodyloadingSheetIncome").append(tbody);
		$('#loadingSheetIncome').modal('show');
		
	} else {
		showMessage('info','No record found !');
		
	}
}
function showVoucherDateModal(){
	console.log('inside shoe voucher ....');
	$('#voucherDateModal').modal('show');
}

function saveVoucherDate(){

	if($('#voucherDate').val() == undefined || $('#voucherDate').val() == null || $('#voucherDate').val().trim() == ''){
		showMessage('info', 'Please select voucher date !');
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 		=  $('#tripSheetId').val();
	jsonObject["voucherDate"] 		=  $('#voucherDate').val();
	
	
	showLayer();
	$.ajax({
             url: "saveVoucherDate",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'Data added successfully !');
            	 showLayer();
            	 window.location.replace("showTripSheet.in?tripSheetID="+data.tripSheetId);
            	
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}
function getWayBillTypeStr(wayBillTypeId){
	if(wayBillTypeId == 1){
		return 'Paid';
	}else if(wayBillTypeId == 2){
		return 'ToPay';
	}else if(wayBillTypeId == 3){
		return 'FOC';
	}else if(wayBillTypeId == 4){
		return 'TBB';
	}else{
		return '--';
	}
}