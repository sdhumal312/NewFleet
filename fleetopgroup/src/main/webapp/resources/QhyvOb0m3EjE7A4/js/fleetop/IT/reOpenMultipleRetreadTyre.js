function reOpenRetreadTyre(TRID){
	
	var NEW_TYRE		= 1;
	var RETREAD_TYRE	= 2;

	showLayer();
	var jsonObject			= new Object();
	
	var array	 			= new Array();
	var tyreNumber 			= new Array();
	var tyreType	   		= new Array();
	
	var TRID				= TRID;// inventoryTyreRetreadID 
	
	$("input[name=tyreNumbers]").each(function(){// actual tyre id
		tyreNumber.push($(this).val());
		tyreType.push(RETREAD_TYRE);
	});
	
	jsonObject["TRID"]						= TRID;  // use for updation 
	

	for(var i =0 ; i< tyreNumber.length; i++){
		var tyreExpenseDetailsList					= new Object();
		tyreExpenseDetailsList.tyreId				= tyreNumber[i];
		tyreExpenseDetailsList.tyreType				= tyreType[i];
		
		array.push(tyreExpenseDetailsList);
	}
	jsonObject.tyreExpenseDetailsList = JSON.stringify(array);
	
	
	$.ajax({
		url: "reOpenTyreRetreadAndDeleteTyreExpense",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			showMessage('success', 'Removed Tyre Expenses Successfully!');
			window.location.replace("ShowRetreadTyre?RID=" + TRID + "");
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

	

}