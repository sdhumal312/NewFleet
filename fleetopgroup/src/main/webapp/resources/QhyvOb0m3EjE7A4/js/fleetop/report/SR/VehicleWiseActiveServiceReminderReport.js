$(function() {
    function a(a, b) {
        $("#dateRangeReport").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
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
				if($('#workOrderGroup1').val() == undefined || $('#workOrderGroup1').val() == ""){
					showMessage('info','Please Select Group')
					hideLayer();
					return false;
				}
				
				var jsonObject		= new Object();		
				jsonObject["vehicleGroup"] 					= $('#workOrderGroup1').val();
				jsonObject["vehicleId"] 					= $('#vehicleId').val();
				
				
				$.ajax({					
					url: "getVehicleWiseActiveServiceReminderReport",
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
	if(resultData.serviceReminderList != undefined && resultData.serviceReminderList.length > 0){
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Vehicle Wise Active Service Reminder Report</b>');
		
		var vehicleGroup 	= $('#workOrderGroup1').select2('data');
		var vehicle 		= $('#vehicleId').select2('data');
		if(vehicleGroup){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle Group : '+vehicleGroup.text+'</td>'
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
		
		setSlickData(resultData.serviceReminderList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
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








