$(document).ready(function() {
	showDueSoonServiceList(1);
});



function showDueSoonServiceList(){
	var jsonObject						= new Object();
	
	showLayer();
	$.ajax({
		url: "getDueSoonServiceListGroupBySProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderReportData(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


function renderReportData(resultData) {

	if(resultData.serviceReminderList != undefined && resultData.serviceReminderList.length > 0){
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$('#myGrid').empty();
		
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
		$('#exampleModalLong1').modal('show');
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}
