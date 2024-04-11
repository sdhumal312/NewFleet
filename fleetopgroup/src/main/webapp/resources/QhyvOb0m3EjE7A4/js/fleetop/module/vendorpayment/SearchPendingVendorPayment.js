var dataTableInitialized = false;
$(function() {
	function a(a, b) {
		$("#dateRangeReport").val(b.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	
	}, a);
	
});
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
				
				if(Number($("#selectVendor").val()) <= 0){
					$('#selectVendor').select2('focus');
					showMessage('info', 'Please select vendor !');
					return false;
				}
				
				showLayer();
				if(dataTableInitialized){
					$('#advanceTable').dataTable().fnDestroy();
				}
				
				var jsonObject			= new Object();

				jsonObject["vendorId"]		= $("#selectVendor").val();
				if(!$('#byDateCheckBox').prop('checked')){
					jsonObject["dateRange"]		= $("#dateRangeReport").val();
				}

				$.ajax({
					url: "getPendingVendorPaymentList.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setVendorPaymentReportData(data);
						hideLayer(); 
					},
					error: function (e) {
						console.log("Error : " , e);
					}
				});
			});

		});

function setVendorPaymentReportData(data) {
	if(data.pendingPaymentList != undefined) {
		var vendorPayment = null;
		var openingAmount = 0;
		var creditAmount  = 0;
		var debitAmount   = 0;
		var closingAmount = 0;
		var totalAmount	  = 0;
		var grandTotalAmount = 0;
		
		var vendorPaymentReportIdentity = $("#vendorPaymentReportIdentity").val();
		
		$("#reportHeader").html("Pending Vendor Payment List");

		$("#advanceTableHead").empty();
		$("#advanceTableBody").empty();
		vendorPayment	= data.pendingPaymentList;

		var tbody = $('<tbody>');
		var tr1 = $('<tr>');

		var th0		= $('<th>');
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
		
		tr1.append(th0.append("Select"));
		tr1.append(th1.append("ID"));
		tr1.append(th2.append("Vendor/Vehicle"));
		tr1.append(th3.append("Invoice No"));
		tr1.append(th4.append("Invoice Date"));
		tr1.append(th5.append("Doc"));
		tr1.append(th6.append("Cost"));
		tr1.append(th7.append("Status"));
		
		
		$('#advanceTableHead').append(tr1);
		
		var sr = 1;
		var vdata = $('#selectVendor').select2('data');
		var totalPendingAmount = 0;
		
		
		$('#vendorId').val(vdata.id);
		
		for(var i = 0; i < vendorPayment.length; i++) {

			var tr = $('<tr>');

			var td0		= $('<td>');
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
			
			tr.append(td0.append('<input type="hidden" name="pendingPaymentId" value="'+vendorPayment[i].pendingPaymentId+'" ><input name="pendingList" onclick="calculateSelectedAmount();" type="checkbox"  id="tra_'+vendorPayment[i].txnTypeId+'_'+vendorPayment[i].transactionId+'" />'));
			tr.append(td1.append(getTransactionNumberUrl(vendorPayment[i])));
			if(vendorPayment[i].vehicleNumber == '--'){
				
				tr.append(td2.append('<a target="_blank" href="ShowVendor.in?vendorId='+vdata.id+'">'+vdata.text+'</a>'));
			}else{
				tr.append(td2.append('<a target="_blank" href="showVehicle?vid='+vendorPayment[i].vid+'">'+vendorPayment[i].vehicleNumber+'</a>'));
			}
			
			tr.append(td3.append(vendorPayment[i].invoiceNumber));
			tr.append(td4.append(vendorPayment[i].transactionDateStr));
			var url = downloadInvoiceDoc(vendorPayment[i]);
			tr.append(td5.append(url));
			tr.append(td9.append('<a href="#" id="balanceAmount_'+vendorPayment[i].txnTypeId+'_'+vendorPayment[i].transactionId+'" >'+vendorPayment[i].balanceAmount+'</a>'));
			tr.append(td10.append(vendorPayment[i].paymentStatus));	
			
			sr++;
			totalPendingAmount += vendorPayment[i].balanceAmount;

			$('#advanceTableBody').append(tr);
		}
		
		$('#totalAmount').html('Total Pending Amount : <i class="fa fa-inr"></i> '+numberWithCommas(totalPendingAmount.toFixed()));
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		dataTableInitialized = true;
		
		$("#advanceTable").DataTable({
			sScrollX:"100%",bScrollcollapse:!0,dom:"Blfrtip",buttons:["excel","print"],order:[[0,"desc"]],aLengthMenu: [
		        [25, 50, 100, 200, -1],
		        [25, 50, 100, 200, "All"]
		    ],
		    iDisplayLength: -1
		});

	}else{
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
		
}
function toggleDateDateOption(){
	if($('#byDateCheckBox').prop('checked')){
		$('#dateRangeDiv').hide();
	}else{
		$('#dateRangeDiv').show();
	}
	
}
function downloadInvoiceDoc(vendorPayment){
	var url = '';
	if(vendorPayment.documentId != undefined && vendorPayment.documentId > 0){
		if(vendorPayment.txnTypeId == 1){
			url = '<a target="_blank" href="download/FuelDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 2){
			url = '<a target="_blank" href="download/serviceDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 3){
			url = '<a target="_blank" href="download/PurchaseorderDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 4){
			url = '<a target="_blank" href="download/TyreInventoryDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 5){
			url = '<a  href="#">--</a>';
		}else if(vendorPayment.txnTypeId == 6){
			url = '<a target="_blank" href="download/BatteryInvoiceDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 7){
			url = '<a target="_blank" href="download/PartDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 8){
			url = '<a target="_blank" href="download/ClothInvoiceDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 9){
			url = '<a target="_blank" href="download/UreaInvoiceDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}else if(vendorPayment.txnTypeId == 10){
			url = '<a href="#">--</a>';
		}else if(vendorPayment.txnTypeId == 11){
			url = '<a target="_blank" href="download/FuelInvoiceDocument/'+vendorPayment.documentId+'.in"><i class="fa fa-download"></i> Download</a>';
		}
	}
	
	return url;
}
function getTransactionNumberUrl(vendorPayment){

	var url = '';
		if(vendorPayment.txnTypeId == 1){
			url = '<a target="_blank" href="showFuel.in?FID='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 2){
			url = '<a target="_blank" href="showServiceEntryDetails?serviceEntryId='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 3){
			url = '<a target="_blank" href="PurchaseOrders_Parts.in?ID='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 4){
			url = '<a target="_blank" href="showTyreInventory.in?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 5){
			url = '<a target="_blank" href="ShowRetreadTyre?RID='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 6){
			url = '<a target="_blank" href="showBatteryInvoice?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 7){
			url = '<a target="_blank" href="showInvoice.in?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 8){
			url = '<a target="_blank" href="showClothInvoice?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 9){
			url = '<a target="_blank" href="showUreaInvoice?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 10){
			url = '<a target="_blank" href="showLaundryInvoice?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}else if(vendorPayment.txnTypeId == 11){
			url = '<a target="_blank" href="showFuelInvoice?Id='+vendorPayment.transactionId+'">'+vendorPayment.transactionNumber+'</a>';
		}
	
	return url;

}

function createVendorApproval(approvalType){
	$('#createApproval').hide();
	var anySelected = false;
	$("input[name=pendingList]").each(function(){
		if($('#'+this.id+'').prop('checked')){ 
			anySelected = true;
	    }
	});
	
	if(!anySelected){
		$('#createApproval').show();
		showMessage('info', 'Please select any invoice to approve !');
		return false;
	}
	var jsonObject			= new Object();
	var transactionIdArr = new Array();
	var transactionTypeArr = new Array();
	var invoiceChecked = '';
	
	$("input[name=pendingList]").each(function(){
		if($('#'+this.id+'').prop('checked')){ 
			invoiceChecked =  this.id;
			transactionTypeArr.push(invoiceChecked.split('_')[1]);
			transactionIdArr.push(invoiceChecked.split('_')[2]);
	    }
	});
	
	var array	 = new Array();
	
	for(var i =0 ; i< transactionIdArr.length; i++){
		var invoiceDetails	= new Object();
		
		invoiceDetails.transactionId			= transactionIdArr[i];
		invoiceDetails.transactionTypeId		= transactionTypeArr[i];
		
		array.push(invoiceDetails);
	}
	jsonObject.selectedInvoice 	= JSON.stringify(array);
	jsonObject["vendorId"] 		=  $('#vendorId').val();
	jsonObject["approvalType"] 	=  approvalType;
	
	 showLayer();
			$.ajax({
		        url: "createVendorApproval",
		        type: "POST",
		        dataType: 'json',
		        data: jsonObject,
		        success: function (data) {
		       	 showMessage('success', 'Approval Created Successfully !')
		       	 if(data.approval.approvalId != undefined){
		       		 if(approvalType == 1){
		       			location.replace('AddServiceApproval.in?approvalId='+data.approval.approvalId); 
		       		 }else{
		       			location.replace('approvedPayment.in?approvalId='+data.approval.approvalId);
		       		 }
		       		
		       	 }
		       	
		       	 hideLayer();
		        },
		        error: function (e) {
		        	$('#createApproval').show();
		       	 showMessage('errors', 'Some error occured!');
		       	 hideLayer();
		        }
			});
}


function calculateSelectedAmount(){
	var selectedAmount = 0;
	var invoiceCheckedArr = [];
	$("input[name=pendingList]").each(function(){
		if($('#'+this.id+'').prop('checked')){ 
			console.log('invoiceChecked ', invoiceChecked)
			var invoiceChecked =  this.id;
			invoiceCheckedArr = invoiceChecked.split('_');
			
			selectedAmount +=	Number($('#balanceAmount_'+invoiceCheckedArr[1]+'_'+invoiceCheckedArr[2]+'').text());
	    }
	});
	console.log('selectedAmount ', selectedAmount);
	$('#selectedAmount').html('Selected Amount : <i class="fa fa-inr"></i>  '+numberWithCommas(selectedAmount.toFixed(2)));
}