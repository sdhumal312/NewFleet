$(document).ready(function() {
	$("#vehicleId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }),$("#incomeId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getTripIncomeByName.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.incomeName, slug: a.slug, id: a.incomeID
                        }
                    }
                    )
                }
            }
        }
    });
	
});
$(function() {
    function a(a, b) {
        $("#dateRangeReport").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
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


$(document).ready(
		function($) {
			$('button[type=submit]').click(function() {
				showLayer();
				var jsonObject		= new Object();		
				var dateRange		= $('#dateRangeReport').val().split("to");
				
				var startDate		= dateRange[0];
				var endDate			= dateRange[1];
				
				var newStartDate 	= startDate.split("-").reverse().join("-").trim();
				var newEndDate 	 	= endDate.split("-").reverse().join("-").trim();
				
			
				jsonObject["tripincomeID"] 					= $('#incomeId').val();
				jsonObject["vehicleId"] 					= $('#vehicleId').val();
				jsonObject["startDate"] 					= startDate;
				jsonObject["endDate"] 						= endDate;
				
				$.ajax({					
					url: "getTripIncomeReportByIncomeName",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {	
						hideLayer();
						renderReportData(data)
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

				
			});

		});

function renderReportData(resultData) {
	if(resultData.tripsheetIncomeList != undefined && resultData.tripsheetIncomeList.length > 0){
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Income Name Wise Summary Report</b>');
		
	//	var incomeName		= $( "#incomeName option:selected" ).text();
		var incomeName		= $('#incomeId').select2('data');	
		var vehicle 		= $('#vehicleId').select2('data');
		var dateRange		= $('#dateRangeReport').val().split("to");
		
		var startDate		= dateRange[0];
		var endDate			= dateRange[1];
		
		var newStartDate 	= startDate.split("-").reverse().join("-").trim();
		var newEndDate 	 	= endDate.split("-").reverse().join("-").trim();
		
		
		if(incomeName){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Income Name : '+incomeName.text+' </td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Income Name : ALL </td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		if(vehicle){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle : '+vehicle.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Vehicle : ALL </td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
	
		
		
		if(dateRange){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Date Range : '+newStartDate.split("-").reverse().join("-")+' to ' +newEndDate.split("-").reverse().join("-")+  '</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		 $('#selectedReportDetails').tooltip({
		        tooltipClass: "your_class-Name",
		 });
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				/*if(serviceType == 2 && (bObj.tableDtoName == 'startDateStr' || bObj.tableDtoName == 'endDateStr')){
					bObj.show = false;
				}else if(serviceType == 3 && (bObj.tableDtoName == 'invoiceDateStr' || bObj.tableDtoName == 'invoiceNumber')){
					
				}*/
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.tripsheetIncomeList, columnConfiguration, tableProperties);
		
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

function show_SE_WO(gridObj, dataView, row){
	var transactionNumber = dataView.transactionNumber;
	var win;
	
	if(transactionNumber.includes("WO")){
		 win = window.open('showWorkOrder?woId='+dataView.transactionId, '_blank');
	}else if(transactionNumber.includes("SE")){
		 win = window.open('ServiceEntriesParts.in?SEID='+dataView.transactionId, '_blank');
	}
	
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}

function showVehicle(gridObj, dataView, row){
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}

function showTrip(gridObj, dataView, row){
	var win = window.open('showTripSheet.in?tripSheetID='+dataView.tripSheetId, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}


function showTaskName(gridObj, dataView, column){
	
	
	/*$(document).on('mouseenter', ".slick-cell", function () {
	    $(this).css("background-color", "red");
		$(this).tooltip("show" ,dataView.jobSubType );
	    
	}).on('mouseleave', ".slick-cell", function () {
	    $(this).css("background-color", "white");
	});*/
	

	
	
	

/*$(document).on('mouseenter', ".slick-row", function () {
    $(this).css("background-color", "red");
}).on('mouseleave', ".slick-row", function () {
    $(this).css("background-color", "white");
});
*/
}

function showToolTip(data){
	$(document).on('mouseenter', ".slick-cell", function () {
		showMessage1('info',$(this).html())
	    
	}).on('mouseleave', ".slick-cell", function () {
			hideAllMessages();
	});
}






