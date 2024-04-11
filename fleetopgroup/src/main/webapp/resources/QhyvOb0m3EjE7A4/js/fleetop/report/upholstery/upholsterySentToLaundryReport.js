var data = [];
//var gridObject;
//var slickGridWrapper3;

//Drop Down for Date Range Input Field
$(function() {
	function a(a, b) {
		$("#laundryDateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#laundryDateRange").daterangepicker( {
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
)
;

//Drop Down for Warehouse Location,Upholstery Type and Laundry Vendor
$(document).ready(function() {
	
	 $("#warehouselocation").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        multiple:!0,
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
	    }),
	    
	$("#clothTypes").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        multiple:!0,
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
    }),
    
    $("#laundryVendor").select2( {
   	 minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getLaundryVendorSearchList.in?Action=FuncionarioSelect2",
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
                            text: a.vendorName + " - " + a.vendorLocation,
                            slug: a.slug,
                            id: a.vendorId
                        }
                    })
                }
            }
        }
   })
  
    
});

/**On Submit***/
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				
				var jsonObject						= new Object();
				jsonObject["warehouselocation"] 		=  $('#warehouselocation').val();
				jsonObject["clothTypes"] 				=  $('#clothTypes').val();
				jsonObject["laundryVendor"] 			=  $('#laundryVendor').val();
				jsonObject["laundryDateRange"] 	 	 	=  $('#laundryDateRange').val(); 
				
				console.log("jsonObject ",jsonObject)
				
				if($('#laundryDateRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();				
				
				$.ajax({
					url: "getUpholsterySentToLaundryReport",
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
	if(resultData.Laundry != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Upholstery Sent To Laundry Report</b>');
		var defaultVal ="All"
		
		var data = $('#laundryVendor').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vendor : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit"> Vendor : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}
		
		
		var date =  $('#laundryDateRange').val();
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
		
		setSlickData(resultData.Laundry, columnConfiguration, tableProperties);
		
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






