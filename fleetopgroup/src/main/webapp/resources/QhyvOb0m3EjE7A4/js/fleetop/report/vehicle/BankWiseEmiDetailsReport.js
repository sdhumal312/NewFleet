
$("#bankId").select2();
$(document).ready(function() {
        $.getJSON("getAllBankNameist", {
            ModelType: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">--Please Select Bank--</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].bankId + '">' + a[d].bankName + "</option>";
            b += "</option>", $("#bankId").html(b)
        })
})

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();				
				jsonObject["bankId"] 						= $('#bankId').val();
				
				$.ajax({					
					url: "getBankWiseVehicleEmiDetails",
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
	if(resultData.vehicleEmiDetailsList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Bank Wise Vehicle EMI Details Report </b>');
		
		var data = $('#bankId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Bank: '+data.text+'</td>'
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
		
		setSlickData(resultData.vehicleEmiDetailsList, columnConfiguration, tableProperties);
		
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






