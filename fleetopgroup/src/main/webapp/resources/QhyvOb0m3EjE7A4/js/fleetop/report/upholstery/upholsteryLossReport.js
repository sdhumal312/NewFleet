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

//Drop Down for Vehicle And Driver
$(document).ready(function() {

	$("#ReportSelectVehicle").select2( {
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
	}
	),

	$("#driverAllList").select2( {
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
	                        text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
	                    }
	                }
	                )
	            }
	        }
	    }
	}
	)
    
});

/**On Submit***/
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				
				var jsonObject						= new Object();
				
				jsonObject["vid"] 						=  $('#ReportSelectVehicle').val();
				jsonObject["driverId"] 					=  $('#driverAllList').val();
				jsonObject["laundryDateRange"] 	 	 	=  $('#laundryDateRange').val(); 
				
				if($('#laundryDateRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();				
				
				$.ajax({
					url: "getUpholsteryLossReport",
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
	if(resultData.Loss != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Upholstery Loss Report</b>');
		var defaultVal ="All"
		
		var data 	= $('#ReportSelectVehicle').select2('data');
		var data1 	= $('#driverAllList').select2('data');//yy
		
		
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit"> Vehicle : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}
		
		
		if(data1){
			var tr =' <tr data-object-id="">'
					+'<td class="fit"> Driver : '+data1.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit"> Driver : '+defaultVal+'</td>'
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
		
		setSlickData(resultData.Loss, columnConfiguration, tableProperties);
		
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






