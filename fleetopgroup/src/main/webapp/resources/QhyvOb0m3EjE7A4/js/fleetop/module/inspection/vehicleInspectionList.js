
function skipInspection(vehicleDailyInspectionId){
	$('#skipRemark').modal('show');
	$('#dailyInspectionId').val(vehicleDailyInspectionId);
}

function skipInspectionWithRemark(){
	var remark = $('#InRemark').val() ;
	
	if(remark.trim() == ''){
		showMessage('info', "Please enter remark to process !");
		return false;
	}
	
	$('#skipRemark').modal('hide');
	showLayer();
	var jsonObject = new Object();
	jsonObject["vehicleDailyInspectionId"]	= $('#dailyInspectionId').val();
	jsonObject["InRemark"] 					= remark;
	
	$.ajax({
		url: "skipInspection",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.success == true || data.success === 'true'){
				showMessage('success', "Inspection Skiped Succesfully .");
				
				setTimeout(function(){
					location.reload();
				},1000)
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
	});
	
}