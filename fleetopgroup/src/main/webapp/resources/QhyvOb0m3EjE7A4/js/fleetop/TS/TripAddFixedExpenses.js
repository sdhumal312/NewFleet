function addFixedExpenses(routeId, tripSheetId){
	var jsonObject			= new Object();
	
	jsonObject["routeId"] 								=  routeId;
	jsonObject["tripSheetId"] 							=  tripSheetId;
	
	showLayer();
	$.ajax({
             url: "tripDailySheetWS/getFixedExpenseDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 console.log('data ',data);
            	 setModelData(data);
            	 hideLayer();
            	 //location.reload();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
            	 $('#fixedExpensesModel').modal('hide');
             }
	});
}
function setModelData(data){
	 $('#modelBody').html('');
	 console.log("routeList data ",data.routeList);
	 if(data.routeList != undefined){
		 for(var i = 0 ; i< data.routeList.length; i++){
			 var modelBody = "";
			 modelBody = '<div class="row1">'
				 +'<label class="col-md-3 col-sm-3 col-xs-12 control-label">'+data.routeList[i].expenseName+' :</label>'
				 +'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" class="form-text" value="'+data.routeList[i].expenseAmount+'" name="expenses" id="expenses'+i+'">'
				 +'<input type="hidden" class="form-text" value="'+data.routeList[i].expenseId+'" name="expenseId" id="expenseId'+i+'"><input type="hidden" class="form-text" value="'+data.routeList[i].tripExpenseID+'" name="tripExpenseID" id="tripExpenseID'+i+'"></div></div><br/><br/>';
			 $('#modelBody').append(modelBody);
			 
		 }
		 var hiddenFields	= '<input type="hidden" id="tripSheetId" value="'+data.tripSheetId+'" /><input type="hidden" id="routeId" value="'+data.routeId+'" />';
		 $('#modelBody').append(hiddenFields);
		 $('#fixedExpensesModel').modal('show');
	 }else{
		 showMessage('info','No fixed Expenses Found !');
	 }
}

function updateFixedExpenses(){
var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 							=  $('#tripSheetId').val();
	jsonObject["routeId"] 							=  $('#routeId').val();
	var expenses	 = new Array();
	var expenseId	 = new Array();
	var tripExpenseID	= new Array();
	$("input[name=expenses]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			expenses.push($(this).val());
		}
	});
	$("input[name=tripExpenseID]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			tripExpenseID.push($(this).val());
		}
	});
	$("input[name=expenseId]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			expenseId.push($(this).val());
		}
	});
	var array	 = new Array();
	for(var i =0 ; i< expenses.length; i++){
		var expensesDetails	= new Object();
		expensesDetails.expenses		= expenses[i];
		expensesDetails.expenseId		= expenseId[i];
		expensesDetails.tripExpenseID	= tripExpenseID[i];
		array.push(expensesDetails);
	}
	jsonObject.expensesDetails = JSON.stringify(array);
	
	console.log('jsonObject ',jsonObject);
	
	
	showLayer();
	$.ajax({
        url: "tripDailySheetWS/updateFixedExpenses",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        window.location.replace("showTripDaily.in?ID="+data.tripSheetId+"&updateExpense="+true+"");
       	 hideLayer();
       	 //location.reload();
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
       	 $('#fixedExpensesModel').modal('hide');
        }
});
}