var data = [];
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		format:'DD-MM-YYYY',
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

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();

				jsonObject["vendorType"] 	=  $('#vendorType').val();				
				jsonObject["vendorId"] 	  	=  $('#selectVendor').val();
				jsonObject["status"] 	  	=  $('#status').val();
				jsonObject["dateRange"] 	=  $('#dateRange').val();
				
				if($('#selectVendor').val() ==  "" || $('#selectVendor').val() == null || $('#selectVendor').val() == undefined){
					showMessage('info','Please Select Vendor !')
					hideLayer();
					return false;
				}
				
				if($('#dateRange').val() == undefined || $('#dateRange').val() == null || $('#dateRange').val().trim() == ''){					
					showMessage('errors', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "vendorPaymentWS/getVendorWisePaymentStatusReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						if($('#status').val() == 1){
							renderReportNotPaidData(data);
							hideLayer();
						}
						
						if($('#status').val() == 2){
							renderReportApprovedData(data);
							hideLayer();
						}
						
						if($('#status').val() == 3){
							renderReportPaidData(data);
							hideLayer();
						}
						
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

	});

function renderReportNotPaidData(resultData) {
	
	if(resultData.notPaidList != undefined){
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		$('#reportHeader').html('<b>Vendor Wise Payment Status Report</b>');
		
		var tr =' <tr data-object-id="">'
			+'<td class="fit"> You Have To Pay : \u20B9'+resultData.TotalShow+'</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr);
		
		var tr0 =' <tr data-object-id="">'
			+'<td class="fit"> Vendor Type : '+getVendorType($('#vendorType').val())+' </td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr0);
		
		var data = $('#selectVendor').select2('data');
		if(data){			
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vendor : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var tr1 =' <tr data-object-id="">'
			+'<td class="fit"> Status : Not Paid</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr1);
		
		var tr2 =' <tr data-object-id="">'
			+'<td class="fit"> Payment Type : Credit</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr2);
		
		var date =  $('#dateRange').val();
		
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date Range : '+date+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.notPaidList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
		//$('#exportExcelBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}


function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}

function renderReportApprovedData(resultData) {
	
	if(resultData.vendor != undefined){
		
		console.log("resultData.vendor",resultData.vendor);
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();		
		
		$('#reportHeader').html('<b>Vendor Wise Payment Status Report</b>');
		
		var tr =' <tr data-object-id="">'
			+'<td class="fit"> You Have To Pay : \u20B9'+resultData.TotalShow+'</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr);
		
		var tr0 =' <tr data-object-id="">'
			+'<td class="fit"> Vendor Type : '+getVendorType($('#vendorType').val())+' </td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr0);
		
		var data = $('#selectVendor').select2('data');
		if(data){			
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vendor : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var tr1 =' <tr data-object-id="">'
			+'<td class="fit"> Status : Approved</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr1);
		
		var date =  $('#dateRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date Range : '+date+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.vendor, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}
}


function renderReportPaidData(resultData) {
	
	if(resultData.vendor != undefined){
		
		console.log("resultData.vendor",resultData.vendor);
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();		
		
		$('#reportHeader').html('<b>Vendor Wise Payment Status Report</b>');
		
		var tr =' <tr data-object-id="">'
			+'<td class="fit"> You Have To Pay : \u20B9'+resultData.TotalShow+'</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr);
		
		var tr0 =' <tr data-object-id="">'
			+'<td class="fit"> Vendor Type : '+getVendorType($('#vendorType').val())+' </td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr0);
		
		var data = $('#selectVendor').select2('data');
		if(data){			
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vendor : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var tr1 =' <tr data-object-id="">'
			+'<td class="fit"> Status : Paid</td>'
		+'</tr>';
		$('#selectedReportDetails').append(tr1);
		
		var date =  $('#dateRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date Range : '+date+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.vendor, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}
}

function showSerialNumberDetails(gridObj, dataView, row){
	
	var vendorType = $('#vendorType').val();
	
	if(vendorType == 1){
		var win = window.open('showFuel.in?FID='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
		
	} else if(vendorType == 2){
		var win = window.open('showServiceEntryDetails?serviceEntryId='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
		
	} else if(vendorType == 3){
		
	} else if(vendorType == 4){
		var win = window.open('showTyreInventory.in?Id='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
		
	} else if(vendorType == 5){
		var win = window.open('ShowRetreadTyre?RID='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
		
	} else if(vendorType == 6){
		var win = window.open('showBatteryInvoice?Id='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}	
		
	} else if(vendorType == 7){
		var win = window.open('showInvoice.in?Id='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
		
	} else if(vendorType == 8){
		var win = window.open('showClothInvoice?Id='+dataView.serialNumberId, '_blank');
		if (win) {
		    win.focus();
		} else {
		    alert('Please allow popups for this website');
		}
	}

}

function showApprovedAndPaidDetails(gridObj, dataView, row){
	var win = window.open('ShowApprovalPayment.in?approvalId='+dataView.approvalId, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}


function getVendorType(vendorType){
	
	var name = "";
	
	if(vendorType == 1){
		name = "Fuel";
	} else if(vendorType == 2){
		name = "Service Entries";
	} else if(vendorType == 3){
		name = "Purchase Order";
	} else if(vendorType == 4){
		name = "Tyre Inventory Entries";
	} else if(vendorType == 5){
		name = "Tyre Retread Bill Entries";
	} else if(vendorType == 6){
		name = "Battery Inventory Entries";
	} else if(vendorType == 7){
		name = "Part Inventory Entries";
	} else if(vendorType == 8){
		name = "Upholstery Inventory Entries";
	}
	
	return name;
}

