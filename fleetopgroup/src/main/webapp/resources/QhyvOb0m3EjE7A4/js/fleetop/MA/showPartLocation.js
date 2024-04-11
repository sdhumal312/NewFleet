function getSubLocations(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["mainLocationId"]	= $('#mainLocationId').val();
	$.ajax({
		url: "PartWS/getSubLocationByMainLocationId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setSubLocations(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setSubLocations (data){
	
	$("#subLocationModelTable").empty();
	
	var partSubLocationsList = data.partSubLocationsList;
	if(partSubLocationsList != null && partSubLocationsList.length > 0){
		for(var index = 0 ; index < partSubLocationsList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4><input type='hidden' name='partlocation_id' > "+ partSubLocationsList[index].partlocation_name  +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='editPartLocations.in?partlocation_id="+partSubLocationsList[index].partlocation_id+"' class='confirmation'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='deletePartLocations.in?partlocation_id="+partSubLocationsList[index].partlocation_id+"' class='confirmation' ><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#subLocationModelTable').append("<tr id='penaltyID"+partSubLocationsList[index].partlocation_id+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		$("#showSubPartLocation").modal('show');
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}