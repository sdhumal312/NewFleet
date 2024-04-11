$(document).ready(function() {
   getTripsheetWeightDetails();
   
   $("#SaveWeight").on('keypress click', function (e) {
	       saveTripSheetWeight();
	  });
}); 

function saveTripSheetWeight()
{
	if(!validateWeight()){
		$('#SaveWeight').show();
		return false;
	}
	var jsonObject = new Object();
	jsonObject["tripsheetId"]  = $("#tripSheetId").val();
	jsonObject["companyId"]	 = $("#companyId").val();
	jsonObject["routeName"]	 = $("#routename").val();
	jsonObject["scaleWeight"]  = $("#scaleWeight").val();
	jsonObject["actualWeight"] = $("#actualWeight").val();
	showLayer();
		$.ajax({
				type: "POST",
				url : "SaveTripsheetWeight",
				dataType: 'json',
				data: jsonObject,
				success : function(data) {
				  showMessage('success','Data saved successfully !');
				  location.replace('addTripsheetWeight.in?tripSheetID='+$('#tripSheetId').val());
			},
			error : function(e){
				showMessage('errors', 'Some error occured!')
			    hideLayer();
			}
		});
}
function validateWeight()
{
	if($("#routename").val() == "" || $("#routename").val() == undefined)
	{
		showMessage('errors', 'Please Enter Route Name!');	
		return false	
	}
	if($("#scaleWeight").val() == "" || $("#scaleWeight").val() == undefined)
	{
		showMessage('errors', 'Please Enter Scale Weight!');
		return false;
		
	}
	if($("#actualWeight").val() == "" || $("#actualWeight").val() == undefined)
	{
		showMessage('errors', 'Please Enter Actual Weight!');
		return false;
	}
	return true;
}

function getTripsheetWeightDetails()
{
	
	var jsonObject				= new Object();
	jsonObject["tripSheetId"] 	= $('#tripSheetId').val();
	
	showLayer();
	
	$.ajax({
		url: "getTripSheetRouteWiseWeightList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTripSheetRouteWiseWeightList(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});
}
		
function setTripSheetRouteWiseWeightList(data)
{
	if(data.TripSheetRouteWiseWight != undefined && data.TripSheetRouteWiseWight.length > 0){
		$('#tripsheetWeight').show();
		$("#tripweightbody tr").remove();
		
		var srNo = 1;
		var diff ;
		for(var i=0; i<data.TripSheetRouteWiseWight.length; i++)
		{
			var TripWeight = data.TripSheetRouteWiseWight[i];
			diff = TripWeight.actualWeight - TripWeight.scaleWeight;
			
			var tr = '<tr>'
				+ '<td class="fit" value" '+srNo+'">'+ srNo+ '</td>'
				+ '<td>'+TripWeight.routeName + '</td>'
				+ '<td>'+TripWeight.actualWeight + '</td>'
				+ '<td>'+TripWeight.scaleWeight+ '</td>'
				+ '<td>'+diff + '</td>'
				+ '<td><a href="#"><font color="red" onclick="removeTripWeight('+TripWeight.routeWiseTripSheetWeightId+')"><i class="fa fa-times"> Remove</i></font> </a></td>'
				+ '</tr>';
		  $("#tripweightbody").append(tr);
		  srNo ++;
		  diff= 0;
		}
	}
}
function removeTripWeight(routeWiseTripSheetWeightId){
	showLayer();
	var jsonObject				= new Object();
	jsonObject["tripsheetId"] 	= $('#tripSheetId').val();
	jsonObject["companyId"] 	= $('#companyId').val();
	jsonObject["routeWiseTripSheetWeightId"] = routeWiseTripSheetWeightId;
	
	$.ajax({
		url: "removeTripWeight",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Data removed successfuelly !');
			getTripsheetWeightDetails();
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});

}