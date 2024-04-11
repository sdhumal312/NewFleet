$(function() {
	function a(a, b) {
		$("#dueAmountRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dueAmountRange").daterangepicker( {
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}
);

$(document).ready(function() {
	$("#driverId").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getDriverALLListOfCompany.in",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                        	text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
	                        }
	                    })
	                }
	            }
	        }
   }); 
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["driverId"] 				=  $('#driverId').val();
				jsonObject["dueAmountRange"] 	  	=  $('#dueAmountRange').val();
				
				if($('#showbilltypeDropdown').val() == true || $('#showbilltypeDropdown').val() == "true"){
			
					if($('#billselectionId').val()  ==null){
						showMessage('info', ' Not Authourized User to check & payment - Due Amount ! ');
						return false;
					}
					if($('#billselectionId').val() =="B_INCOME"){
						jsonObject["billselectionId"]       =  1;
					}else if($('#billselectionId').val() =="E_INCOME"){
						jsonObject["billselectionId"]       =  2;
					}
				}
				
				if($('#dueAmountRange').val() == ""){
					showMessage('errors', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "getDueAmountPaymentReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setDueAmountPaymentReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

});

function setDueAmountPaymentReport(data) {
	console.log("duepayment...",data);
	$("#reportHeader").html("Tripsheet DueAmount Payment Report");
	$("#dueAmountTable").empty();
	
	var thead = $('<thead style="background-color: aqua;">');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');
	var th9		= $('<th>');
	var th10	= $('<th>');
	var th11	= $('<th>');
	var th12	= $('<th>');
	var th13	= $('<th>');
	//var th11	= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Tripsheet No."));
	tr1.append(th3.append("Name"));
	tr1.append(th4.append("Approx Date"));
	tr1.append(th5.append("Due Date"));
	tr1.append(th6.append("Description"));
	tr1.append(th7.append("Reference"));
	tr1.append(th8.append("Payment Mode"));
	tr1.append(th9.append("Payment Type"));
	tr1.append(th12.append("Transaction Mode"));
	tr1.append(th13.append("Transaction No."));
	tr1.append(th10.append("Due Amount"));
	tr1.append(th11.append("Paid Amount"));
	//tr1.append(th11.append("Balance Amount"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.dueAmountPaymentList != undefined && data.dueAmountPaymentList.length > 0) {
		
		var dueAmountPaymentList = data.dueAmountPaymentList;
	
		for(var i = 0; i < dueAmountPaymentList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');
			var td11	= $('<td>');
			var td12	= $('<td>');
			var td13	= $('<td>');
			//var td11	= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet?tripSheetID='+dueAmountPaymentList[i].tripSheetID+'" target="_blank">TS-'+dueAmountPaymentList[i].tripSheetNumber+'</a>'));
			tr1.append(td3.append(dueAmountPaymentList[i].driver_firstname + " "+dueAmountPaymentList[i].driver_Lastname));
			tr1.append(td4.append(dueAmountPaymentList[i].approximateDateStr)); 
			tr1.append(td5.append(dueAmountPaymentList[i].dueDateStr)); 
			tr1.append(td6.append(dueAmountPaymentList[i].remark));
			tr1.append(td7.append(dueAmountPaymentList[i].reference));
			
			if(dueAmountPaymentList[i].paymentModeId == 1){
				tr1.append(td8.append('<span class="label label-default label-warning">'+dueAmountPaymentList[i].paymentModeName));
			} else {
				tr1.append(td8.append('<span class="label label-default label-success">'+dueAmountPaymentList[i].paymentModeName));
			}
			
			tr1.append(td9.append(dueAmountPaymentList[i].paymentTypeName));
			tr1.append(td12.append((dueAmountPaymentList[i].transactionName)));
			tr1.append(td13.append((dueAmountPaymentList[i].transactionNo)));
			tr1.append(td10.append(dueAmountPaymentList[i].dueAmount));
			tr1.append(td11.append((dueAmountPaymentList[i].paidAmount).toFixed(2)));
			console.log("dueAmountPaymentList[i].transactionName",dueAmountPaymentList[i].transactionName," dueAmountPaymentList[i].transactionNo",dueAmountPaymentList[i].transactionNo)
			//tr1.append(td11.append(dueAmountPaymentList[i].balanceAmount));

			tbody.append(tr1);
		}
		
		$("#dueAmountTable").append(thead);
		$("#dueAmountTable").append(tbody);
		$("#ResultContent").removeClass("hide");
		
	} else {
		showMessage('info', 'No Record Found!')
	}

}
