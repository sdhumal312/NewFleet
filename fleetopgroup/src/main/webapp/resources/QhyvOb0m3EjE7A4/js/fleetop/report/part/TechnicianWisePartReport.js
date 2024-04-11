var data = [];

//Technician Name DropDown
$(document).ready(function() {
	$("#technicianId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getTechinicianWorkOrder.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                        	text: a.driver_firstname + " - " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    )
});

//Parts Number DropDown
$(document).ready(function() {
	$("#searchpartPO").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchMasterPart.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partnumber+" - "+a.partname+" - "+a.category+" - "+a.make, slug: a.slug, id: a.partid
                        }
                    }
                    )
                }
            }
        }
    }
    )
});

//Date range DropDown
$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
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
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["technicianId"] 	  	=  $('#technicianId').val();
				jsonObject["partId"] 			=  $('#searchpartPO').val();
				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				
				if($('#TripCollectionExpenseRange').val() <= 0){					
					showMessage('info', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					
					url: "PartWS/getTechnicianWisePartReport",
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
	if(resultData.technician != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Technician Wise Part Report</b>');
		
		var data = $('#technicianId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Technician : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		
		var data = $('#searchpartPO').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Part Number/Name : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var date =  $('#TripCollectionExpenseRange').val();
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
		
		setSlickData(resultData.technician, columnConfiguration, tableProperties);
		
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

	var win = window.open('showVehicle.in?vid='+dataView.vehicle_vid, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}

}

function showWorkOrder(gridObj, dataView, row){
	
	var win;
	win = window.open('showWorkOrder?woId='+dataView.workorders_id, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}

