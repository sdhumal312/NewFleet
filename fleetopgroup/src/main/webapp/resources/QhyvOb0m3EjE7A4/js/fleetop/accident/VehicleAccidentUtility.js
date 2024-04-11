function deleteAccidentDetails(accidentDetailsId){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["accidentId"]			= accidentDetailsId;

	$.ajax({
		url: "deleteAccidentDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.error != undefined){
				showMessage('info', data.error);
			}else{
				showMessage('success', 'Data Deleted Successfully !');
				location.reload();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function accidentEntriesSearch(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["accidentNumber"]	= $('#accidentSearchNumber').val();
	
	$.ajax({
		url: "searchAccidentByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.encreptedId != undefined){
				window.location.replace("showVehicleAccidentDetails?id="+data.encreptedId+"");
			}else{
				showMessage('errors', 'No Record Found !');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			console.log(e);
			showMessage('errors', 'Some Error Occurred!');
		}
	});



}
function accidentEntriesSearchOnEnter(e){

	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		accidentEntriesSearch();
	}

}