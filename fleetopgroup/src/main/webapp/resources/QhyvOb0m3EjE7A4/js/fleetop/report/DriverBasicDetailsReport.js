
$(document).ready(function() {
	$("#addDetailTypeId").select2({
		ajax : {
            url:"driverBasicDetailsTypeAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.driverBasicDetailsTypeName, slug: t.slug, id: t.driverBasicDetailsTypeId
                        }
                    }
                    )
                }
            }
        }
	
	});
	
});



$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();				
				jsonObject["driJobId"] 						= $('#driJobId').val();
				jsonObject["driverTypeList"] 				= $('#driverTypeList').val();
				jsonObject["addDetailTypeId"] 				= $('#addDetailTypeId').val();
				jsonObject["date"] 							= $('#dateRangeReport').val();
				
				console.log("******")
					
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}

				$.ajax({					
					url: "getDriverBasicDetailsReport",
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

		},
		
	
		
		$("#driverTypeList").select2( {
	        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
	            url:"getDriverTypeListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a,
	                    driverType : $('#driJobId').val()
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
	    }
	    )
		
		
		);






function renderReportData(resultData) {
	
	$('#invoicePurchase').show();

	$('#dateRangeIdentity').html(resultData.date);
	
	
	if(resultData.driverBasicDetailsList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("Driver Basic Details Report");
		
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

		setSlickData(resultData.driverBasicDetailsList, columnConfiguration, tableProperties);
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


