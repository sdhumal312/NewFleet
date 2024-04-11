function addMantisIncome(tripSheetId, vid, vehicle_registration, opentripDate, closetripDate){
		var jsonObject			= new Object();
		
		var VehicleNumber = vehicle_registration;
		
		
		jsonObject["FromDate"] 	  			= opentripDate;
		jsonObject["ToDate"] 	  			= closetripDate;
		jsonObject["VehicleNumber"] 		= VehicleNumber.replace(/-/g,'');
		jsonObject["tripSheetId"] 			= tripSheetId;
		jsonObject["vid"] 					= vid;
		jsonObject["companyId"] 			= $('#companyId').val();
		jsonObject["userId"] 				= $('#userId').val();
		jsonObject["BusNumber"] 			= vehicle_registration;
		
		
		showLayer();
		$.ajax({
	             url: "/restservices/addMantisTripSheetIncome",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.noRecordFound){
	            		 showMessage('info', 'No Record Found !');
	            		 hideLayer();
	            		 return false;
	            	 }
	            	 
	            	 if(data.alreadyAdded){
	            		 showMessage('info','Income Data Already Added !');
	            		 hideLayer();
	            	 }else if(data.ticketIncomeAdded){
	            		 showMessage('success','Income Data Added Successfully!');
	            		 if(data.flavor == 1){
	            			 window.location.replace("showTripSheet.in?tripSheetID="+tripSheetId+"&ticketIncomeAdded="+true+"");
	            		 }else if(data.flavor == 3){
	            			 window.location.replace("showTripDaily.in?ID="+tripSheetId+"&ticketIncomeAdded="+true+"");
	            		 }
	            		  
	            	 }else if(data.noRecordFound){
	            		 showMessage('info', 'No Record Found !');
	            		 hideLayer();
	            	 }else if(data.error){
	            		 showMessage('errors', 'Some error occured!');
	            		 hideLayer();
	            	 }else {
	            		 hideLayer();
	            	 }
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	
}

$(document).ready(function() {
	var allowTollApiIntegration = $('#allowTollApiIntegration').val();
	if(allowTollApiIntegration == true || allowTollApiIntegration == 'true'){
		$('#tollApi').show();
	}
});

function PopUp(tripSheetId) {

		if(tripSheetId > 0){
			
			var jsonObject	= new Object();
			jsonObject["tripSheetId"] =  tripSheetId;
			
			showLayer();
			$.ajax({
			url: "getTollExpensesDetailsList",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			setModelDataTollExpense(data);
			hideLayer();
			},
			error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
			}
			});
			}
		
	}

function setModelDataTollExpense(data) {
	var expenseName	= null;
	if(data.ExpenseName != undefined) {
		$("#modelBodyTollExpense").html("Toll Expense Report");

		$("#modelBodyExpense").empty();
		expenseName	= data.ExpenseName;
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
		

		th1.append('Sr No');
		th2.append('Plaza Code');
		th3.append('Plaza Name');
		th4.append('Transaction Reference Number');
		th5.append('Date');
		th6.append('Fast Tag Bank');
		th7.append('Amount');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);

		thead.append(tr1);
		
		var totalExpenseAmount=0;

		for(var i = 0; i < expenseName.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			

			
			//td2.append(tripCollectionExpenseName[i].tripdailynumber);
			/*td2.append('<a href="showTripDaily?ID='+tripCollectionExpenseName[i].tripDailysheetID+'" >'+tripCollectionExpenseName[i].tripdailynumber+'</a>');
			td3.append('<a href="showVehicle?vid='+tripCollectionExpenseName[i].vid+'" >'+tripCollectionExpenseName[i].vehicle_registration+'</a>');*/
			
			td1.append(i+1);
			td2.append(expenseName[i].plazaCode); 
			td3.append(expenseName[i].plazaName);
			td4.append(expenseName[i].transactionReferenceNumber);
			td5.append(expenseName[i].transactionDateTimeStr);
			td6.append(expenseName[i].fastTagBankName);
			td7.append(expenseName[i].transactionAmount);
			
			totalExpenseAmount += expenseName[i].transactionAmount;

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);

			tbody.append(tr);
		}
		
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="6">');
		var td2		= $('<td>');
		


		td1.append("Total :");
		td2.append(totalExpenseAmount.toFixed(2));
		

		tr2.append(td1);
		tr2.append(td2);
	

		tbody.append(tr2);
		
		$('#getExpensesModel').modal('show');
		$("#modelBodyExpense").append(thead);
		$("#modelBodyExpense").append(tbody);
		
		/*$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");*/
	} else {
		showMessage('info','No record found !');
		/*$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");*/
	}
	setTimeout(function(){ hideLayer(); }, 500);
}


function expensePopUp(tripSheetId, expenseId) {

	if(tripSheetId > 0){
		
		var jsonObject	= new Object();
		jsonObject["tripSheetId"] =  tripSheetId;
		jsonObject["expenseId"] =  expenseId;
				
		showLayer();
		$.ajax({
			url: "getExpenseCombineDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			setModelExpenseCombineDeatils(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
	
}

function setModelExpenseCombineDeatils(data) {
var expenseList	= null;
if(data.ExpenseList != undefined) {
	$("#modelBodyTollExpense").html("Toll Expense Report");

	$("#modelBodyExpenseDetails").empty();
	expenseList	= data.ExpenseList;
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
	

	th1.append('Sr No');
	th2.append('Expense Name');
	th3.append('Type');
	th4.append('Place');
	th5.append('Ref');
	th6.append('Expense Date');
	th7.append('Amount');
	

	tr1.append(th1);
	tr1.append(th2);
	tr1.append(th3);
	tr1.append(th4);
	tr1.append(th5);
	tr1.append(th6);
	tr1.append(th7);

	thead.append(tr1);
	
	var totalExpenseAmount=0;

	for(var i = 0; i < expenseList.length; i++ ) {
		var tr = $('<tr>');

		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		

		
		//td2.append(tripCollectionExpenseName[i].tripdailynumber);
		/*td2.append('<a href="showTripDaily?ID='+tripCollectionExpenseName[i].tripDailysheetID+'" >'+tripCollectionExpenseName[i].tripdailynumber+'</a>');
		td3.append('<a href="showVehicle?vid='+tripCollectionExpenseName[i].vid+'" >'+tripCollectionExpenseName[i].vehicle_registration+'</a>');*/
		
		td1.append(i+1);
		td2.append(expenseList[i].expenseName); 
		if(expenseList[i].expenseFixed == 'FIXED'){
			td3.append('<a class="label label-success"</a>'+expenseList[i].expenseFixed);
		} else {
			td3.append('<a class="label label-warning"</a>'+expenseList[i].expenseFixed);
		}
		td4.append(expenseList[i].expensePlace);
		td5.append(expenseList[i].expenseRefence);
		td6.append(expenseList[i].createdStr);
		td7.append(expenseList[i].expenseAmount);
		
		totalExpenseAmount += expenseList[i].expenseAmount;

		tr.append(td1);
		tr.append(td2);
		tr.append(td3);
		tr.append(td4);
		tr.append(td5);
		tr.append(td6);
		tr.append(td7);

		tbody.append(tr);
	}
	
	var tr2 = $('<tr>');

	var td1		= $('<td colspan="6">');
	var td2		= $('<td>');
	


	td1.append("Total :");
	td2.append(totalExpenseAmount.toFixed(2));
	

	tr2.append(td1);
	tr2.append(td2);


	tbody.append(tr2);
	
	$('#ExpenseCombineDetails').modal('show');
	$("#modelBodyExpenseDetails").append(thead);
	$("#modelBodyExpenseDetails").append(tbody);
	
	/*$("#ResultContent").removeClass("hide");
	$("#printBtn").removeClass("hide");
	$("#exportExcelBtn").removeClass("hide");*/
} else {
	showMessage('info','No record found !');
	/*$("#ResultContent").addClass("hide");
	$("#printBtn").addClass("hide");
	$("#exportExcelBtn").addClass("hide");*/
}
setTimeout(function(){ hideLayer(); }, 500);
}
