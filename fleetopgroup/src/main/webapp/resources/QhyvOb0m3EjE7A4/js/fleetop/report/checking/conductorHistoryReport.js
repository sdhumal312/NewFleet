var data = [];

$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
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
	$("#conductorId").select2( {
		 minimumInputLength: 3,
         minimumResultsForSearch: 10,
         ajax: {
             url:"getConductorList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                 return {
                     term: a
                 }
             }
             , results:function(a) {
                 return {
                     results:$.map(a, function(a) {
                         return {
                             text: a.driver_empnumber+" "+a.driver_firstname +" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
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
				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				jsonObject["conductorId"] 		=  $('#conductorId').val();
				
				
				$.ajax({
					
					url: "getConductorHistoryReport",
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
	if(resultData.conductorHistory != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Conductor History Report</b>');
		
		var data = $('#conductorId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit">Conductor : '+data.text+'</td>'
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
		
		setSlickData(resultData.conductorHistory, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}


