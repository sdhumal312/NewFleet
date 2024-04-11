$(document).ready(function() {
	$("#vid").select2( {
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
			
			var jsonObject						= new Object();
			
			jsonObject["manufacturer"] 				=  $('#manufacurer').val();
			jsonObject["vid"] 						=  $('#vid').val();
			
			if(Number($('#manufacurer').val()) <= 0){
				showMessage('info','Please Select Tyre Manufacturer!');
				hideLayer();
				return false;
				
			}	
			showLayer();
			
			$.ajax({
				url: "getTyreExpenseDetailsReport",
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
	
	if(resultData.tyreExpenseDetailsList != undefined){    
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Tyre Expense Details Report</b>');
		var defaultVal ="All"
		
		var data 	= $('#manufacurer').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Manufacturer : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit"> Vehicle : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
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
		
		setSlickData(resultData.tyreExpenseDetailsList, columnConfiguration, tableProperties);
		
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






