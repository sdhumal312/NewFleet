
$(document).ready(
		function($) {
			$('#btn-save-ds').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();			
					
				jsonObject["driverId"] 					= $('#driverId').val();
				jsonObject["dateRange"] 				= $('#dateRangeReport').val();
			
				if($('#driverId').val() == ""){
					jsonObject["driverId"] 					= "0";
				}
				$.ajax({					
					url: "/DriverSalaryReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {		
						console.log("data d = "+JSON.stringify(data));				
						renderReportData(data);						
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

		});

   $("#driverId").select2( {
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
                           text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
                       }
                   }
                   )
               }
           }
       }
    });
    
});


function renderReportData(data) {
	if(data.driverSalaryReportDtoList != undefined && data.driverSalaryReportDtoList != null){
		setHeaderData(data.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<p style="font-size: 20px; font-weight:bold;"><b>Driver Salary Report</b></p>');
		
		/*var tr =' <tr data-object-id="">'
					+'<td class="fit"> Total Balance (Debit - Credit) : '+data.balance+'</td>'
				+'</tr>';
		$('#selectedReportDetails').append(tr);*/
		
		if(data.tableConfig != undefined) {
			var ColumnConfig = data.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  data.tableConfig.tableProperties;
			
		}
		
		setSlickData(data.driverSalaryReportDtoList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		//$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	  //  $('#printBtn').addClass('hide');     
	    showMessage('info', 'No record found !');
	}

}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){   // check krna hai yeh  
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');  
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}
