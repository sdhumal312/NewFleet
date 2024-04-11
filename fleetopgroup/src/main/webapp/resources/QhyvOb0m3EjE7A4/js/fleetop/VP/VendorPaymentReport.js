$(document).ready(function() {
	$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	})
});

jQuery(document).ready(
		function($) {

			$("#btn-search").click(function(event) {
				showLayer();
				var jsonObject			= new Object();

				jsonObject["vendorId"]		= $("#selectVendor").val();
				jsonObject["dateRange"]		= $("#LocWorkOrder").val();										
				

				$.ajax({
					url: "vendorPaymentWS/getVendorPaymentReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setVendorPaymentReportData(data);
					},
					error: function (e) {
						console.log("Error : " , e);
					}
				});
				setTimeout(function(){ hideLayer(); }, 500);
			});

		});

function setVendorPaymentReportData(data) {
	if(data.vendorPaymentList != undefined) {

		var vendorPayment = null;
		var openingAmount = 0;
		var creditAmount  = 0;
		var debitAmount   = 0;
		var closingAmount = 0;
		var totalAmount	  = 0;
		var grandTotalAmount = 0;
		
		var vendorPaymentReportIdentity=$("#vendorPaymentReportIdentity").val();
		
		

		$("#reportHeader").html("Vendor Payment Report");

		$("#advanceTable").empty();
		vendorPayment	= data.vendorPaymentList;

		var tbody = $('<tbody>');
		var tr1 = $('<tr>');

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
		
		if(vendorPaymentReportIdentity=='true'){
		var th11	= $('<th>');
		}

		tr1.append(th1.append("Sr No"));
		tr1.append(th2.append("Vendor Name"));
		tr1.append(th3.append("Invoice No"));
		tr1.append(th4.append("Invoice Date"));
		tr1.append(th5.append("Payment Term"));
		tr1.append(th6.append("Transaction Number"));
		tr1.append(th7.append("Payment Date"));
		tr1.append(th8.append("Debit Amount"));
		tr1.append(th9.append("Credit Amount"));
		tr1.append(th10.append("GST Amount"));
		
		if(vendorPaymentReportIdentity=='true'){
		tr1.append(th11.append("Total  Amount"));
		}
		tbody.append(tr1);
		
		var tr0 = $('<tr style="background-color: #FDB666">');
		
		var td1		= $('<td colspan="7">');
		var td2		= $('<td style="text-align : right">');
		var td3		= $('<td style="text-align : right">');
		var td4		= $('<td style="text-align : right">');
		
		tr0.append(td1.append("OPENING BALANCE"));
		openingAmount	= data.vendorPayment.openingAmount;
		tr0.append(td2.append(0));					
		tr0.append(td3.append(openingAmount));
		tr0.append(td4.append(""));					
		
		tbody.append(tr0);
		
		var sr = 1;
		for(var i = 0; i < vendorPayment.length; i++) {

			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td style="text-align : right">');
			var td9		= $('<td style="text-align : right">');
			var td10	= $('<td style="text-align : right">');
			
			if(vendorPaymentReportIdentity=='true'){
			var td11	= $('<td style="text-align : right">');
			}
			tr.append(td1.append(sr));
			tr.append(td2.append(vendorPayment[i].vendorName));
			tr.append(td3.append(vendorPayment[i].invoiceNumber));
			tr.append(td4.append(vendorPayment[i].invoiceDateStr));
			tr.append(td5.append(vendorPayment[i].paymentTypeStr));
			if(vendorPayment[i].paymentTypeId == 7) {				
				tr.append(td6.append(vendorPayment[i].chequeNumber));				
			} else if(vendorPayment[i].cashVoucherNumber != null) {
				tr.append(td6.append(vendorPayment[i].cashVoucherNumber));				
			} else {
				tr.append(td6.append(""));								
			}
			tr.append(td7.append(vendorPayment[i].paymentDateStr));
			if(vendorPayment[i].transactionTypeId == 1) {
				tr.append(td8.append(0));					
				tr.append(td9.append(vendorPayment[i].transactionAmount));
				creditAmount += vendorPayment[i].transactionAmount;
			} else {
				tr.append(td8.append(vendorPayment[i].transactionAmount));					
				tr.append(td9.append(0));		
				debitAmount += vendorPayment[i].transactionAmount;
			}
			tr.append(td10.append(vendorPayment[i].gstAmount));	
			
			
			
			if(vendorPaymentReportIdentity=='true')
			{
					if(vendorPayment[i].transactionTypeId == 1){				
									totalAmount=vendorPayment[i].transactionAmount+vendorPayment[i].gstAmount;									
									tr.append(td11.append(totalAmount));
									grandTotalAmount += totalAmount;
					}
					else{				
									totalAmount=vendorPayment[i].transactionAmount+vendorPayment[i].gstAmount;									
									tr.append(td11.append(totalAmount));
									grandTotalAmount += totalAmount;
					}
			}

			sr++;

			tbody.append(tr);
		}

		var tr2 = $('<tr style="background-color: #FDB666">');

		var td1		= $('<td colspan="2">');
		var td2		= $('<td colspan="5">');
		var td3		= $('<td style="text-align : right">');
		var td4		= $('<td style="text-align : right">');
		var td5		= $('<td style="text-align : right">');
		var td6		= $('<td style="text-align : right">');

		closingAmount   = (openingAmount + creditAmount) - debitAmount;
		
		tr2.append(td1.append("CLOSING BALANCE"));
		tr2.append(td2.append("(OPENING BALANCE + CREDIT AMOUNT) - DEBIT AMOUNT"));
		
		if(closingAmount > 0) {
			tr2.append(td3.append(0));
			tr2.append(td4.append(closingAmount));			
		} else {
			tr2.append(td3.append(closingAmount));
			tr2.append(td4.append(0));						
		}
		tr2.append(td5.append(""));
		
		if(vendorPaymentReportIdentity=='true'){
			tr2.append(td6.append(grandTotalAmount));	
		}
		
		
		

		tbody.append(tr2);

		$("#advanceTable").append(tbody);

		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}