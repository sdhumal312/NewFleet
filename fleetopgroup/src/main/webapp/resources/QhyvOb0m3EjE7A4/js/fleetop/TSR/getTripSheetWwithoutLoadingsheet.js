var data = [];


$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["vehicleGroupId"] 	  	=  $('#vehicleGrpId7').val();
				jsonObject["vehicleId"] 	  		=  $('#Vehicleofdepot7').val();
				jsonObject["dateRange"] 	  	=  $('#dispatchDateRange').val();
				
				$.ajax({
					url: "getTripsheetDetailsithoutLoadingSheet",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});
			
$(function() {
    function a(a, b) {
        $("#dispatchDateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dispatchDateRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        },
        dateLimit: {
		    'months': 1,
		    'days': -1
		}
    }
    , a)
}

);

		});

//slickgreed

function renderReportData(resultData) {
	
	if(resultData.tripSheetList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		$('#reportHeader').html('<b>TripSheet Loading Sheet Details Report</b>');
		
		
		
		var group = $('#vehicleGrpId7').select2('data');
		console.log('group : ', group);
		if(Number($('#vehicleGrpId7').val()) > 0){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle Group : '+group.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		var date =  $('#dispatchDateRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date  : '+date+'</td>'
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
		
		setSlickData(resultData.tripSheetList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
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

function showVehicleDetails(gridObj, dataView, row){
	console.log('row : ', row);
}

function showTripSheetNumberDetails(gridObj, dataView, row){
	console.log('row ', row);
	
}

function showWayBillPopup(){
		var jsonObject			= new Object();
		jsonObject["dateRange"] 	  	=  $('#dispatchDateRange').val();
				
				$.ajax({
					url: "getTripsheetDetailsithoutLoadingSheet",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});
}

function showLSDetails(tripSheetId){
	
	var jsonObject			= new Object();
		jsonObject["tripsheetId"] 	  	=  tripSheetId;
				showLayer();
				$.ajax({
					url: "showLSDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderDispatchDetailsData(data);
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});
}

function renderDispatchDetailsData(resultData) {
	
	if(resultData.dispatchList != undefined){
		//setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#dispatchPopup').empty();
		
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
		
		//setSlickData(resultData.dispatchList, columnConfiguration, tableProperties);
		
		
		setLoadingSheetPopup(resultData.dispatchListHM, resultData.dispatchList);
		
		hideLayer();
		
		//$('#dispatchContainer').show();
		$('#loadingSheetDetailsModal').modal('show');
						
					//	setTimeout(function(){
				//			dataView.expandAllGroups();
					//		hideLayer();
				//		},1000);

	}else{
	    showMessage('info', 'No record found !');
	    $('#loadingSheetDetailsModal').modal('hide');
	    hideLayer();
	}

}

function setLoadingSheetPopup(dispatchListHM, dispatchList){
	if(dispatchListHM != undefined){
		$('#dispatchTableBody').empty();
		var tr = '';
		var tripFreightTotal	= 0;
		var tripBookingTotal	= 0;
		$.each(dispatchListHM, function (key , values) {
			var freightTotal = 0;
			var grandTotal	= 0;
				tr = '<tr>'
						+'<td colspan="5"> <a href="#" style="font-size:16px;">LS Number : '+key+'</a></td>'
				     +'</tr>'
				$('#dispatchTableBody').append(tr);
				for(var i = 0; i < values.length ; i++){
					
					freightTotal	+= values[i].freight;
					grandTotal		+= values[i].bookingTotal;
					
					tr = '<tr>'
							+'<td>'+values[i].lsNumber+'</td>'
							+'<td>'+values[i].tripDateTimeStr+'</td>'
							+'<td>'+values[i].wayBillNumber+'</td>'
							+'<td>'+values[i].freight+'</td>'
							+'<td>'+values[i].bookingTotal+'</td>'
						+'</tr>'
						
						$('#dispatchTableBody').append(tr);
				}
				tr = '<tr>'
						+'<td></td>'
						+'<td></td>'
						+'<td></td>'
						+'<td><b>'+freightTotal+'</b></td>'
						+'<td><b>'+grandTotal+'</b></td>'
				     +'</tr>'
				$('#dispatchTableBody').append(tr);
				
				tripFreightTotal += freightTotal;
				tripBookingTotal += grandTotal;
		});
		
		tr = '<tr>'
						+'<td colspan="3"><b>TripSheet Totals : </b></td>'
						+'<td style="font-size: 15px;"><b>'+tripFreightTotal+'</b></td>'
						+'<td style="font-size: 15px;"><b>'+tripBookingTotal+'</b></td>'
				     +'</tr>'
				$('#dispatchTableBody').append(tr);
	}
}

