var data = [];

//Expense Name DropDown
$(document).ready(function() {
	   $("#ReportExpenseName").select2( {
	        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	            url:"getSearchExpenseName.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a
	                }
	            }
	            , results:function(a) {
	                return {
	                    results:$.map(a, function(a) {
	                        return {
	                            text: a.expenseName, slug: a.slug, id: a.expenseID
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    } 
	    ),$("#driverId").select2( {
	        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
	            url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a
	                }
	            }
	            , results:function(a) {
	                return {
	                    results:$.map(a, function(a) {
	                        return {
	                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_fathername+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    }
	    );
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["ReportExpenseName"] 	  	=  $('#ReportExpenseName').val();	
				if($("#dateRangeConfig").val() == true || $("#dateRangeConfig").val() == 'true'){
					jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				}else{
					jsonObject["dateRange"] 	  	=  $('#TRIP_DATE_RANGE').val();
				}
				jsonObject["dateRangeConfig"] 	  	=  $('#dateRangeConfig').val();
				jsonObject["vehicleId"] 	  		=  $('#vehicleId').val();
				jsonObject["driverId"] 	  			=  $('#driverId').val();
				jsonObject["driJobId"] 	  			=  $('#driJobId').val();
				
				
				if($('#TRIP_DATE_RANGE').val() <= 0){					
					showMessage('info', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					
					
					url: "getCreateDayWiseExpenseReport.do",
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

		});

//slickgreed

function renderReportData(resultData) {
	if(resultData.expense != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Create Day Wise Expense Report</b>');
		
		var data = $('#ReportExpenseName').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Expense Name : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var driver = $('#driverId').select2('data');
		if($('#driverInputFieldInDateWise').val() == true || $('#driverInputFieldInDateWise').val() == 'true'){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Driver Name : '+driver.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		/*var date =  $('#TripCollectionExpenseRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date  : '+date+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}*/
		
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
		
		setSlickData(resultData.expense, columnConfiguration, tableProperties);
		
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

	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}

}

function showTripSheetNumberDetails(gridObj, dataView, row){
	
	var win;
	win = window.open('showTripSheet.in?tripSheetID='+dataView.tripSheetID, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}

