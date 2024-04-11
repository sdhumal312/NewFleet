function addTripCheckPointParameter(){
	showLayer();
	var jsonObject			= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "/getAllTripCheckPointParameter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			renderTripCheckPointParameter(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function renderTripCheckPointParameter(data){
	$("#tripCheckPointParameterTable").empty();
	var tripCheckPointParameterList = data.allTripCheckPointParameter;
	
	if(tripCheckPointParameterList != undefined && tripCheckPointParameterList != null && tripCheckPointParameterList.length > 0){
		for(var index = 0 ; index < tripCheckPointParameterList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar>"+ tripCheckPointParameterList[index].tripCheckParameterName  +"</td>");
			columnArray.push("<td class='fit ar'>" + tripCheckPointParameterList[index].description +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='deleteTripCheckPointParameter("+tripCheckPointParameterList[index].tripCheckPointParameterId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#tripCheckPointParameterTable').append("<tr id='tripCheckPointParameterId"+tripCheckPointParameterList[index].tripCheckPointParameterId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			hideLayer();
			showMessage('info','No Record Found!')
		}
	$("#addTripCheckPointParameterModal").modal('show');
	hideLayer();
	}
function saveTripCheckPointParameter(){

	showLayer();
	var jsonObject			= new Object();

	jsonObject["checkPointParameterName"]		= $('#checkPointParameterName').val();
	jsonObject["description"]					= $('#addDescription').val();
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["userId"]						= $('#userId').val();
	
	if(!validateTripCheckPointsParameter()){
		return flase;
	}
	
	$.ajax({
		url: "/saveTripCheckPointParameter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && (data.alreadyExist == true || data.alreadyExist == 'true')){
				showMessage('info', 'Already Exist');
				hideLayer();
			}else{
				location.reload()
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

}
function validateTripCheckPointsParameter(){
	if($('#checkPointParameterName').val() == "" || $('#checkPointParameterName').val() == undefined ){
		showMessage('info','Please Select CheckPoint Parameter Name');
		hideLayer();
		return false;
	}
	return true;
}

function deleteTripCheckPointParameter(tripCheckPointParameterId){
	
	var jsonObject			= new Object();

	jsonObject["tripCheckPointParameterId"]		= tripCheckPointParameterId;
	jsonObject["companyId"]						= $('#companyId').val();
	
	$.ajax({
		url: "/deleteTripCheckPointParameter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.parameterUsed != undefined && (data.parameterUsed == true || data.parameterUsed == 'true')){
				showMessage('info', 'Already Inspected Hence You Can Not Delete The Parameter');
				hideLayer();
			}else{
				location.reload()
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}