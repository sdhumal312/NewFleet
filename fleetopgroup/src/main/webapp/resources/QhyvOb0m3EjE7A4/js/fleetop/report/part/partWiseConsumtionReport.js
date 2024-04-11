var data = [];

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

$(document).ready(function() {
	$("#searchpartPO").select2( {
        minimumInputLength:2, minimumResultsForSearch:10,multiple:!0, 
        ajax: {
            url:"getSearchMasterPart.in", 
            dataType:"json", 
            type:"POST", 
            contentType:"application/json",
            quietMillis:50, 
            data:function(a) {
            	
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
    ),$("#ReportSelectVehicle").select2( {
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
    })
});

$(document).ready(
		
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject					= new Object();
			
				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				jsonObject["serviceTypeId"] 	=  $('#serviceTypeId').val();
				jsonObject["partId"] 			=  $('#searchpartPO').val();
				jsonObject["vid"] 				=  $('#ReportSelectVehicle').val();
				
			
				$.ajax({
					
					url: "PartWS/getPartWiseConsumptionReport",
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

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		//$('#companyTable').show();
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

function renderReportData(resultData) {
	
	if(resultData.tasksToPartsDtos != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Part Wise Consumption Report</b>');
		
		
		
		var type = $("#serviceTypeId option:selected").text();
		if(type){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Service Type  : '+type+'</td>'
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
		var flagForPartNo	= false;
		
		if($('#searchpartPO').val().split(",").length == 1 && $('#searchpartPO').val() != ""){
			var flagForPartNo	= true;
			var data = $('#searchpartPO').select2('data');
			if(data != undefined){
				var tr =' <tr data-object-id="">'
					+'<td class="fit"> Part Number : '+data[0].text+'</td>'
					+'</tr>';
				$('#selectedReportDetails').append(tr);
			}
		
		}
		
		
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				if(columnKeys[i] == "Part_Number" && flagForPartNo){
					bObj.show	= false;
				}
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.tasksToPartsDtos, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
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
	if(dataView.serviceTypeId == 1){
		win = window.open('showWorkOrder?woId='+dataView.workorders_id, '_blank');
	}else if(dataView.serviceTypeId == 2){
		
		win = window.open('ServiceEntries_COMPLETE.in?serviceEntries_id='+dataView.workorders_id, '_blank');
	}
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}