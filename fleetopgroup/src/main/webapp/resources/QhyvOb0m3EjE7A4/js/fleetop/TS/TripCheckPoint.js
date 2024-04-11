function addTripCheckPoints(){
	showLayer();
	var jsonObject			= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["routeId"]			= $('#routeId').val();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "/getRouteWiseTripCheckPoint",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			renderTripCheckPoint(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}
function renderTripCheckPoint(data){
	$("#tripCheckPointTable").empty();
	var routeWiseTripCheckPointList = data.routeWiseTripCheckPointList;
	if(routeWiseTripCheckPointList != undefined && routeWiseTripCheckPointList != null && routeWiseTripCheckPointList.length > 0){
		for(var index = 0 ; index < routeWiseTripCheckPointList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar>"+ routeWiseTripCheckPointList[index].tripCheckPointName  +"</td>");
			columnArray.push("<td class='fit ar'>" + routeWiseTripCheckPointList[index].description +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='deleteTripCheckPoint("+routeWiseTripCheckPointList[index].tripCheckPointId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#tripCheckPointTable').append("<tr id='tripCheckPointParameterId"+routeWiseTripCheckPointList[index].tripCheckPointId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			hideLayer();
			showMessage('info','No Record Found!')
		}
	hideLayer();
	$("#addTripCheckPointModal").modal('show')
	}
function saveTripCheckPoints(){

	showLayer();
	var jsonObject			= new Object();

	jsonObject["routeId"]			= $('#routeId').val();
	jsonObject["checkPointName"]	= $('#checkPointName').val();
	jsonObject["description"]		= $('#addDescription').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	
	if(!validateTripCheckPoints()){
		return flase;
	}
	
	$.ajax({
		url: "/saveTripCheckPoint",
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
function validateTripCheckPoints(){
	if($('#checkPointName').val() == "" || $('#checkPointName').val() == undefined ){
		showMessage('info','Please Select CheckPoint Name');
		hideLayer();
		return false;
	}
	return true;
}

function deleteTripCheckPoint(tripCheckPointId){
	
	var jsonObject			= new Object();

	jsonObject["tripCheckPointId"]		= tripCheckPointId;
	jsonObject["companyId"]			= $('#companyId').val();
	
	$.ajax({
		url: "/deleteTripCheckPoint",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.checkPointUsed != undefined && (data.checkPointUsed == true || data.checkPointUsed == 'true')){
				showMessage('info', 'Already Inspected Hence You Can Not Delete The CheckPoint');
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