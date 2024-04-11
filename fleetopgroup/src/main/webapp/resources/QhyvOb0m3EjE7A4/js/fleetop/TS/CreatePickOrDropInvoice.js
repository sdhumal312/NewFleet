var data = [];
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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
    
    $("#partyId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartyListByName.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                    	console.log('data ', e);
                        return {
                            text: e.corporateName,
                            slug: e.slug,
                            id: e.corporateAccountId,
                            mobileNumber : e.mobileNumber,
                            gstNumber 	 : e.gstNumber,
                            partyRate    : e.perKMRate,
                            partyAddress : e.address
                        }
                    })
                }
            }
        }
    })
})

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject				= new Object();

				jsonObject["partyId"] 	  	=  $('#partyId').val();
				
				if($('#partyId').val() <= 0){					
					showMessage('errors', 'Please Select Party Name !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					
					url: "getInvoiceList.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setInvoiceList(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});

function setInvoiceList(response) {
	var invoiceList					= response.invoiceList;

	if(response.invoiceList != undefined && response.invoiceList.length > 0){
		
		$('#contentBody').removeClass('hide');
		$('#settle-border-boxshadow').removeClass('hide');
		$('#settleTable').removeClass('hide');
		
		$('#settleDetails').empty();
		
		for(var i = 0; i < invoiceList.length; i++) {

			if(invoiceList != undefined) {
				
				var tripsheetPickAndDropId  = invoiceList[i].tripsheetPickAndDropId;
				
				var tripsheetNum			= '<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+invoiceList[i].tripsheetPickAndDropId+'" target="_blank">TS-'+invoiceList[i].tripSheetNumber+'</a>';
				var vehicleNumber 			= invoiceList[i].vehicleRegistration;
				var vendorName 				= invoiceList[i].vendorName;
				var driverName 				= invoiceList[i].driverName;
				var journeyDateStr2 		= invoiceList[i].journeyDateStr2;
				var pickOrDropStatusStr 	= invoiceList[i].pickOrDropStatusStr;
				var locationName 			= invoiceList[i].locationName;
				var rate 					= invoiceList[i].rate;
				var tripUsageKM 			= invoiceList[i].tripUsageKM;
				var amount 					= invoiceList[i].amount;
				var tripTotalAdvance 		= invoiceList[i].tripTotalAdvance;
				var partyId 				= invoiceList[i].vendorId;
				
				var tableRow				= createRowInTable('tr_' + tripsheetPickAndDropId, '', 'height: 35px;');

				var checkBoxCol				= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var tripsheetNumCol			= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var vehicleNumberCol		= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var vendorNameCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var driverNameCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var journeyDateStr2Col		= createColumnInRow(tableRow, '', '', '7%', '', '', '');
				var pickOrDropStatusStrCol	= createColumnInRow(tableRow, '', '', '5%', '', '', '');
				var locationNameCol			= createColumnInRow(tableRow, '', '', '6%', '', '', '');
				var rateCol 				= createColumnInRow(tableRow, '', '', '4%', '', '', '');
				var tripUsageKMCol			= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var amountCol				= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var tripTotalAdvanceCol		= createColumnInRow(tableRow, '', '', '2%', '', '', '');
				var partyIdHideCol			= createColumnInRow(tableRow, '', 'hide', '', '', '', '');

				appendValueInTableCol(checkBoxCol, createCheckBoxField(tripsheetPickAndDropId));
				appendValueInTableCol(tripsheetNumCol, tripsheetNum);
				appendValueInTableCol(vehicleNumberCol, vehicleNumber);
				appendValueInTableCol(vendorNameCol, vendorName);
				appendValueInTableCol(driverNameCol, driverName);
				appendValueInTableCol(journeyDateStr2Col, journeyDateStr2);
				appendValueInTableCol(pickOrDropStatusStrCol, pickOrDropStatusStr);
				appendValueInTableCol(locationNameCol, locationName);
				appendValueInTableCol(rateCol, rate);
				appendValueInTableCol(tripUsageKMCol, createKmFeild(tripUsageKM, tripsheetPickAndDropId));
				appendValueInTableCol(amountCol, createAmountFeild(amount, tripsheetPickAndDropId));
				appendValueInTableCol(tripTotalAdvanceCol, createAdvanceFeild(tripTotalAdvance, tripsheetPickAndDropId));
				appendValueInTableCol(partyIdHideCol, createPartyIdFeild(partyId, tripsheetPickAndDropId));
			
				appendRowInTable('settleDetails', tableRow);
				
			}
			
		}
		
	}else{
		$('#middle-border-boxshadow').addClass('hide');
		showMessage('info', 'No Pick Or Drop Tripsheet Found Found !')
	}
	
	function createCheckBoxField(tripsheetPickAndDropId) {
		var checkBoxField			= $("<input/>", { 
			type		: 'checkbox', 
			id			: 'checkBox_' + tripsheetPickAndDropId, 
			name		: 'checkValue',
			});

		return checkBoxField;
	}
	
	function createPartyIdFeild(partyId, tripsheetPickAndDropId) {
		var partyFeild	= $("<input/>", { 
						type		: 'hidden', 
						id			: 'partyId_' + tripsheetPickAndDropId, 
						class		: 'form-control', 
						name		: 'partyId_' + tripsheetPickAndDropId, 
						value 		: partyId, 
			});
		
		return partyFeild;
	}
	
	function createKmFeild(tripUsageKM, tripsheetPickAndDropId) {
		var kmFeild	= $("<input/>", { 
			id			: 'tripUsageKM_' + tripsheetPickAndDropId, 
			class		: 'form-control', 
			readonly 	: 'readonly',
			name		: 'tripUsageKM_' + tripsheetPickAndDropId, 
			value 		: tripUsageKM, 
		});
		
		return kmFeild;
	}
	
	function createAmountFeild(amount, tripsheetPickAndDropId) {
		var amountFeild	= $("<input/>", { 
			id			: 'amount_' + tripsheetPickAndDropId, 
			class		: 'form-control', 
			readonly 	: 'readonly',
			name		: 'amount_' + tripsheetPickAndDropId, 
			value 		: amount, 
		});
		
		return amountFeild;
	}
	
	function createAdvanceFeild(tripTotalAdvance, tripsheetPickAndDropId) {
		var advanceField	= $("<input/>", { 
			id			: 'advance_' + tripsheetPickAndDropId, 
			class		: 'form-control', 
			readonly 	: 'readonly',
			name		: 'advance_' + tripsheetPickAndDropId, 
			value 		: tripTotalAdvance, 
		});
		
		return advanceField;
	}
}

$(document).ready(
		function($) {
			$('button[id=UpSaveButton]').click(function(e) {
				e.preventDefault();
				
				console.log('saving invoice details.........')
				
				var jsonObject					= new Object();
				var atleastOneSelected			= false;
				
				var tripsheetPickOrDropIdArr 	= new Array();
				var partyIdArr 					= new Array();
				var kmArr 						= new Array();
				var amountArr 					= new Array();
				var advanceArr 					= new Array();
				
				var invoiceDate = $('#invoiceDate').val();
				
				if($("#invoiceDate").val() == ""){
					$("#invoiceDate").focus();
					showMessage('errors','Please select Date !');
					return false;
				}
				
				$("input[name=checkValue]").each(function(){
					var selectId 					= this.id;
					var mySplitResult 				= selectId.split("_");
					var tripsheetPickAndDropId		= mySplitResult[1];
					
		            if( $('#checkBox_'+tripsheetPickAndDropId).prop("checked") == true ){
		            	
		            	tripsheetPickOrDropIdArr.push(tripsheetPickAndDropId);
		            	partyIdArr.push($("#partyId_" + tripsheetPickAndDropId).val());
		            	kmArr.push($("#tripUsageKM_" + tripsheetPickAndDropId).val());
		            	amountArr.push($("#amount_" + tripsheetPickAndDropId).val());
		            	advanceArr.push($("#advance_" + tripsheetPickAndDropId).val());
		            	
		            	atleastOneSelected = true;
		            }
				});
				
				
				if(!atleastOneSelected){
					showMessage('errors', 'Please Select one Tripseet To Create Invoice !');
					return false;
				}
				
				var array	 = new Array();
				
				for(var i =0 ; i< tripsheetPickOrDropIdArr.length; i++){
					var invoiceDetails	= new Object();
					
					invoiceDetails.tripsheetPickAndDropId = tripsheetPickOrDropIdArr[i];
					invoiceDetails.partyId 				  = partyIdArr[i];
					invoiceDetails.usageKm 				  = kmArr[i];
					invoiceDetails.amount 				  = amountArr[i];
					invoiceDetails.advance 				  = advanceArr[i];
					
					array.push(invoiceDetails);
				}
				
				jsonObject.invoiceDate = invoiceDate;
				jsonObject.invoiceDetails = JSON.stringify(array);
				console.log("jsonObject...",jsonObject);
				
				showLayer();
				$.ajax({
			             url: "savePickOrDropInvoice",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	if(data.sequenceNotFound != undefined && data.sequenceNotFound == true) {
			            		hideLayer();
			            		showMessage('errors', 'Sequence not found! Please contact system administrator. ')
			            	}
			            	
			            	if(data.invoiceCreated != undefined && data.invoiceCreated == true) {
			            		hideLayer();
			            		window.location.replace("showPickAndDropInvoice?invoiceSummaryId="+data.invoiceSummaryId+"");
			            		showMessage('success', 'Invoice Created.')
			            	}
			            	 
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});
			
});





/*function setInvoiceList(data) {
	
	console.log("data...",data);
	
	$("#VendorPaymentTable").empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th0		= $('<th>');
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

	tr1.append(th0.append("Select"));
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
	if(data.invoiceList != undefined && data.invoiceList.length > 0) {
		
		var invoiceList = data.invoiceList;
	
		for(var i = 0; i < invoiceList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td0		= $('<td>');
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
			
			tr1.append(td0.append());
			tr1.append(td1.append('<a href="showDispatchedPickAndDropTrip?dispatchPickAndDropId='+invoiceList[i].tripsheetPickAndDropId+'" target="_blank">TS-'+invoiceList[i].tripSheetNumber+'</a>'));
			tr1.append(td2.append(invoiceList[i].vehicleRegistration));
			tr1.append(td3.append(invoiceList[i].vendorName));
			tr1.append(td4.append(invoiceList[i].driverName));
			tr1.append(td5.append(invoiceList[i].journeyDateStr2));
			tr1.append(td6.append(invoiceList[i].pickOrDropStatusStr));
			tr1.append(td7.append(invoiceList[i].locationName));
			tr1.append(td8.append(invoiceList[i].rate));
			tr1.append(td9.append(invoiceList[i].tripUsageKM));
			tr1.append(td10.append((invoiceList[i].amount).toFixed(2)));	
			tr1.append(td11.append((invoiceList[i].tripTotalAdvance).toFixed(2)));
			
			tbody.append(tr1);
		}
		
		$("#VendorPaymentTable").append(thead);
		$("#VendorPaymentTable").append(tbody);
		
	}else{
		showMessage('info','No record found !')
	}
}
*/
