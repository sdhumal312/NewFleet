var showTotalExpenseAmount 	= 0;

$(document).ready(function() {
	var modalId				= 0; // on page load show Expense amount
	getAllExpensesByTyreId($("#tyreId").val(),modalId)
});

function getAllExpensesByTyreId(tyreId,modalId){// this will also call on hyperLink on that time modalId will be 1 to show popup
	showLayer();
	var jsonObject					= new Object();
	jsonObject["tyreId"]			= tyreId;
	
	$.ajax({
		url: "getAllExpensesByTyreId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllExpensesByTyreId(data,modalId);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setAllExpensesByTyreId(data,modalId){
	var totalExpenseAmount = 0;
	
	if(data.tyreExpenseDetailsList != undefined && data.tyreExpenseDetailsList != null){
		var tyreExpenseDetailsList = data.tyreExpenseDetailsList;
		$("#showTyreExpenseDetailsTable").empty();
		$('#searchData').show();
		$('#countDiv').show();
		
		var thead 	= $('<thead class="thead-dark">');
		
		var tr1 	= $('<tr>');//expense 
		var tr2 	= $('<tr class="success">'); //Invoice
		var tr3 	= $('<tr class="info">'); //total
		
		var td31 	= $('<td colspan="3">');	
		var td32 	= $('<td colspan="4">');	
		
		var td21	= $('<td>');
		var td22	= $('<td>');
		var td23	= $('<td class="fit ar">');
		var td24	= $('<td>');
		var td25	= $('<td>');
		var td26	= $('<td>');
		var td27	= $('<td>');
		
		tr2.append(td21.append("0"));                  
		tr2.append(td22.append("INVOICE DETAILS"));        
		tr2.append(td23.append("--"));                  
		tr2.append(td24.append(data.invoiceAmount));    
		tr2.append(td25.append());    
		tr2.append(td26.append());  
		tr2.append(td27.append());  

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th class="fit ar">');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');

		tr1.append(th1.append("No"));
		tr1.append(th2.append("Expense Name"));
		tr1.append(th3.append("DESCRIPTION"));
		tr1.append(th4.append("EXPENSE AMOUNT"));
		tr1.append(th5.append("Vendor"));
		tr1.append(th6.append("Document"));
		tr1.append(th7.append("ACTION"));

		thead.append(tr1);
		
		var tbody = $('<tbody class="thead-light">');
		
		tbody.append(tr2); // append invoice details
		for(var i = 0; i < tyreExpenseDetailsList.length; i++) {
			
			if(tyreExpenseDetailsList[i].totalTyreExpenseAmount != null)
				{
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td>');
				var td5		= $('<td>');
				var td6		= $('<td>');
				var td7		= $('<td>');
				
				tr1.append(td1.append(i + 1));
				tr1.append(td2.append(tyreExpenseDetailsList[i].tyreExpenseName));
				tr1.append(td3.append(tyreExpenseDetailsList[i].description));
				tr1.append(td4.append(tyreExpenseDetailsList[i].totalTyreExpenseAmount));
				tr1.append(td5.append(tyreExpenseDetailsList[i].vendorName));
				if(tyreExpenseDetailsList[i].tyreExpenseDetailsDocumentId > 0){
					tr1.append(td6.append('<a href="download/tyreExpenseDetailsDoc/'+tyreExpenseDetailsList[i].tyreExpenseDetailsDocumentId+'" <span class="fa fa-download"> Doc</span> </a>'));
				}else{
					tr1.append(td6.append(""));
				}
				
				totalExpenseAmount += tyreExpenseDetailsList[i].totalTyreExpenseAmount;
				
				if(tyreExpenseDetailsList[i].tyreExpenseDetailsId > 0){
					
					tr1.append(td7.append(
							'<div class="btn-group">'
							+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
							+'<ul class="dropdown-menu pull-right">'
							/*+'<li><a href="#" class="confirmation" onclick="editTyreExpensName('+tyreExpenseDetailsList[i].tyreExpenseDetailsId+')"><i class="fa fa-edit"></i> Edit</a></li>'*/
							+'<li><a href="#" class="confirmation" onclick="deleteTyreExpensDetails('+tyreExpenseDetailsList[i].tyreExpenseDetailsId+')"><span class="fa fa-trash"></span> Delete</a></li>'
							+'</ul>'
							+'</div>'
					));
				}
				tbody.append(tr1);
				}
		}
		td31.append("Total :")
		
		td32.append(totalExpenseAmount+data.invoiceAmount)
		tr3.append(td31);
		tr3.append(td32);
		
		tbody.append(tr3);
	
		$("#showTyreExpenseDetailsTable").append(thead);
		$("#showTyreExpenseDetailsTable").append(tbody);
		
		if(modalId > 0){ 
			$("#showTyreExpenseModal").modal('show');
		}
		
		showTotalExpenseAmount = totalExpenseAmount+data.invoiceAmount;
		$("#totalTyreExpense").html(showTotalExpenseAmount);
		
		var tyreUsage = $("#tyreUsage").val();
		if(tyreUsage > 0){
			var costPerKm = totalExpenseAmount/tyreUsage;
		}else{
			var costPerKm = 0;
		}
		
		$("#costPerKm").html(costPerKm);
			

	}
//	else{
//		showMessage('info','No Record Found')
//		return false;
//	}
}


function deleteTyreExpensDetails(tyreExpenseDetailsId){
	
	if (confirm('Are you sure you want to Delete this Expense?')) {
	
		if(tyreExpenseDetailsId != undefined && tyreExpenseDetailsId > 0){
			
			var jsonObject					= new Object();
			
			jsonObject["tyreExpenseDetailsId"]					= tyreExpenseDetailsId;
			
			$.ajax({
				url: "deleteTyreExpenseDetails",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Delete Tyre Expense Successfully!');
					hideLayer();
					location.reload(); 
					return false;
					
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	  
	} 
	
	else {
		window.location.replace("TyreInventory/1");
	}
}




