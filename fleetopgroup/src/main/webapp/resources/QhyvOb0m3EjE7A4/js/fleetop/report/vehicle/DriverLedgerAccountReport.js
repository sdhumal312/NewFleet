$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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
});

$(document).ready(function() {
	
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

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();				
				jsonObject["driverId"] 					= $('#driverId').val();
				jsonObject["dateRange"] 				= $('#dateRange').val();
				
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('errors','Please Select Date')
					hideLayer();
					return false;
				}

				$.ajax({					
					url: "getDriverLedgerAccountReport",
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
		$('#myGrid').empty();
	
	if(resultData.driverLedeger != undefined && resultData.driverLedeger.length > 0){
		
		//setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		
		
		$('#reportHeader').html('<b>Driver Ledger Account Report</b>');
		
		
	//	$('#selectedReportDetails').append(tr);
		
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
		
		setSlickData(resultData.driverLedeger, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}



