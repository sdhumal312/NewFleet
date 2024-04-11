$(document).ready(function(){
	$('#status').select2({
		paceholder: 'All'
	})
	$('#renwalStatus').select2({
		paceholder: 'All'
	})
});

$(function(){
	$('button[type=submit]').click(function(e){
	showLayer();
	e.preventDefault();
	
	/*if($('#dateRange').val().trim() == ''){
		showMesage('info','Please Enter date to process further');
		hideLayer();
		return false;
	}
	var date =$('#dateRange').val().trim().split(' to ');
	var dateRange = $('#dateRange').val();*/
	var jsonObject = new Object();
	
	jsonObject['status'] 	= $('#status').val();
	jsonObject['renwalStatus'] 	= $('#renwalStatus').val();
	//jsonObject['fromDate']  = date[0].split('-').reverse().join('-');
	//jsonObject['toDate']    = date[1].split('-').reverse().join('-');	

	$.ajax({
		url: 'getDriverDetailsReport',
		type: 'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			renderReportData(data);
		},
		error : function(e){
			console.log("error ",e);
			showMessage('errors','Some error Occured ! ');
			hideLayer();
		}
	})		
	})
});


function renderReportData(resultData,statusId) {
	if(resultData.list != undefined){
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		var statusId=$('#status').select2('data');
		if (statusId.id > 0) {
			var tr = ' <tr data-object-id="">'
				+ '<td class="fit"> Status : ' + statusId.text + '</td>'
				+ '</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var renwalStatus=$('#renwalStatus').select2('data');
		if (renwalStatus.id > 0) {
			var tr = ' <tr data-object-id="">'
				+ '<td class="fit">DL Status : ' + renwalStatus.text + '</td>'
				+ '</tr>';
			$('#selectedReportDetails').append(tr);
		}
		//var tr = ' <tr data-object-id="">'
			//+ '<td class="fit"> Date Range : ' + dateRange + '</td>'
			//+ '</tr>';
		//$('#selectedReportDetails').append(tr);

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
		setSlickData(resultData.list, columnConfiguration, tableProperties);
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
		hideLayer();
	}else{
		$('#ResultContent').hide();
		$('#printBtn').addClass('hide');
		showMessage('info', 'No record found !');
		hideLayer();
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