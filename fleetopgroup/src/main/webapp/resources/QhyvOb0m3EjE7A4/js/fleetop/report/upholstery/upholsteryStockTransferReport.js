$(document).ready(function() {
	$("#clothTypes").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getClothTypesList.in?Action=FuncionarioSelect2",
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
	                            text: a.clothTypeName,
	                            slug: a.slug,
	                            id: a.clothTypesId
	                        }
	                    })
	                }
	            }
	        }
    }),$("#fromWarehouselocation,#toWarehouselocation").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                            text: a.partlocation_name,
                            slug: a.slug,
                            id: a.partlocation_id
                        }
                    })
                }
            }
        }
    })
    
});
	
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
});
	
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();	
				
				jsonObject["fromWarehouselocation"] 		= $('#fromWarehouselocation').val();
				jsonObject["toWarehouselocation"] 			= $('#toWarehouselocation').val();
				jsonObject["clothTypes"] 					= $('#clothTypes').val();
				jsonObject["status"] 						= $('#statusOfUpholstery').val();
				jsonObject["dateRange"] 					= $('#dateRange').val();

				if($('#dateRange').val() == null || $('#dateRange').val() == undefined || $('#dateRange').val() == ""){
					showMessage('errors','Please Select Date');
					hideLayer();
					return false;
				}
				
				$.ajax({					
					url: "getUpholsteryStockTransferReport",
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



function renderReportData(resultData) {
	if(resultData.stockTransferUpholstery != undefined){
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Upholstery Stock Transfer Report</b>');
		var defaultVal = "ALL"
		var data = $('#fromWarehouselocation').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">From Warehouse Location: '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit">From Warehouse Location: '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data = $('#toWarehouselocation').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">To Warehouse Location: '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit">To Warehouse Location: '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		
		var data = $('#clothTypes').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Upholstery Types : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Upholstery Types : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		/*var data = $('#statusOfUpholstery').val();
		if(data != undefined && data != null ){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Upholstery Status: '+data.html+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Upholstery Status : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}*/
		
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
		
		setSlickData(resultData.stockTransferUpholstery, columnConfiguration, tableProperties);
		
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


function showVehicle(gridObj, dataView, row){
	console.log("dataView",dataView)
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}
	


