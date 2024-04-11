$(document).ready(function(e){
$('#reportLabel').select2();
});
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}
				var jsonObject								= new Object();				
				jsonObject["reportLabel"] 					= $('#reportLabel').val();
				var dateRangeVal = $('#dateRange').val();
				var datearray = dateRangeVal.split(" to "); 
				var fromDate = (datearray[0].trim()).split("-").reverse().join("-");
				var toDate = (datearray[1].trim()).split("-").reverse().join("-");
				
				jsonObject["fromDate"] 					= fromDate;
				jsonObject["toDate"] 					= toDate;
					
				$.ajax({					
					url: "getRequisitionReportDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {						
						
						hideLayer();
						renderReportData(data,dateRangeVal);						
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});
		
		
function renderReportData(resultData,dateRangeVal) {
	$('#dateRangeval').html(dateRangeVal);
	$('#dateInfo').show();
	if(resultData.list != undefined){
		
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("REQUISITION REPORT .");
		
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

		setSlickData(resultData.list, columnConfiguration, tableProperties);
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		$('#ResultContent').hide();
		$('#printBtn').addClass('hide');
		showMessage('info', 'No record found !');
	}
	}
	
	
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		maxDate: new Date(),
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
}

);

		