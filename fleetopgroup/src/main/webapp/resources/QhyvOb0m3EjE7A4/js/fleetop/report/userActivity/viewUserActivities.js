var ACTIVITY_TYPE_WO = 1;
var ACTIVITY_TYPE_SE = 2;
var	ACTIVITY_TYPE_TS = 3;
var	ACTIVITY_TYPE_FE = 4;
var	ACTIVITY_TYPE_RR = 5;
var	ACTIVITY_TYPE_ISSUES = 6;
var	ACTIVITY_TYPE_PO = 7 ;
var	ACTIVITY_TYPE_DSE = 8 ;


$(document).ready(function(){
	$('#fuelEntriesDiv').hide();
	$('#workOrderActivityDiv').hide();
	$('#ServiceEntryActivityDiv').hide();
	$('#TripSheetActivityDiv').hide();
	$('#rrActivityDiv').hide();
	$('#IssuesActivityDiv').hide();
	$('#poActivityDiv').hide();
	$('#dseActivityDiv').hide();
	
	    setTimeout(function() {
	    	  $("#subscribe").select2("data",{
	  	    	id : $('#userId').val(),
	  	    	text : $('#userName').val()
	  	     })
	  	     	   getActivityCount()
		}, 500);
	
})


function getActivityCount(){
	
	if(!validateOnchange()){
		return false;
	}
	
	var jsonObject = new Object();
	
	jsonObject["userId"] = $('#subscribe').val();
	
	console.log(jsonObject);
	
	var dateRange 	= $('#dateRange').val().split("to");
	
	if( dateRange != undefined && dateRange != "" && dateRange[0] != undefined && dateRange[1] != undefined){
		var startDateArr	= dateRange[0].split("-");
		var endDateArr		= dateRange[1].split("-");
		
		var finalStartDate	= startDateArr[2].trim()+"-"+startDateArr[1].trim()+"-"+startDateArr[0].trim();
		var finalEndDate	= endDateArr[2].trim()+"-"+endDateArr[1].trim()+"-"+endDateArr[0].trim();
		
		jsonObject["startDate"]		= finalStartDate.trim();
		jsonObject["endDate"]		= finalEndDate.trim();
		
	}
		
	$.ajax({
		url : "getWOActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#workOrderActivityDiv').hide();
			$('#woActivityGif').show();
		
		},
		success : function(data){
			
			console.log(data);
			$("#woCreateCount").text(data.workOrderCreateCount);
			$("#woUpdatedCount").text(data.workOrderUpdateCount);
			$("#woDeletedCount").text(data.workOrderDeleteCount);
			$('#woActivityGif').hide();
			$('#workOrderActivityDiv').show();
			$("#workOrderActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_WO+')"/>')
			
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
		
	})
	
	$.ajax({
		url : "getSEActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#ServiceEntryActivityDiv').hide();
			$('#seActivityGif').show();
		},
		success : function(data){
			console.log(data);
			$("#CreateSECount").text(data.serviceEntryCreatedCount);
			$("#UpdatedSECount").text(data.serviceEntryUpdatedCount);
			$("#DeletedSECount").text(data.serviceEntryDeletedCount);
			$('#seActivityGif').hide();
			$('#ServiceEntryActivityDiv').show();
			
			$("#ServiceEntryActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_SE+')"/>');
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
	
		$.ajax({
		url : "getTripSheetActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			console.log("i am in before sebd");
			$('#TripSheetActivityDiv').hide();
			$('#tsActivityGif').show();
		},
		success : function(data){
			console.log(data);
			
			$("#CreateTSCount").text(data.tripSheetCreateCount);
			$("#UpdatedTSCount").text(data.tripSheetUpdatedCount);
			$("#DeletedTSCount").text(data.tripSheetDeletedCount);
			$('#tsActivityGif').hide();
			$('#TripSheetActivityDiv').show();
			$("#TripSheetActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_TS+')" />');
			
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
		$.ajax({
		url : "getFuelEntryActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#fuelEntriesDiv').hide();
			$('#fuelActivityGif').show();
		},
		success : function(data){
			console.log(data);
			
			$("#CreateFECount").text(data.fuelEntryCreatedCount);
			$("#UpdatedFECount").text(data.fuelEntryUpdatedCount);
			$("#DeletedFECount").text(data.fuelEntryDeletedCount);
			$('#fuelActivityGif').hide();
			$('#fuelEntriesDiv').show();
			$("#fuelEntriesDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_FE+')"/>')
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
	
		$.ajax({
		url : "getRRActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#rrActivityDiv').hide();
			$('#rrActivityGif').show();
		},
		success : function(data){
			$("#CreateRRCount").text(data.rrCreatedCount);
			$("#UpdatedRRCount").text(data.rrUpdatedCount);
			$("#DeletedRRCount").text(data.rrDeletedCount);
			$('#rrActivityGif').hide();
			$('#rrActivityDiv').show();
			$("#rrActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_RR+')"/>')
			
			
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
	
	$.ajax({
		url : "getIssuesActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#IssuesActivityDiv').hide();
			$('#IssuesActivityGif').show();
		},
		success : function(data){
			console.log("here is the daya " , data);
			$("#CreateIssuesCount").text(data.issuesCreatedCount);
			$("#UpdatedIssuesCount").text(data.issuesUpdatedCount);
			$("#DeletedIssuesCount").text(data.issuesDeletedCount);
			$('#IssuesActivityGif').hide();
			$('#IssuesActivityDiv').show();
			$("#IssuesActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_ISSUES+')"/>')
			
			
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
		$.ajax({
		url : "getPurchaseOActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#poActivityDiv').hide();
			$('#PoActivityGif').show();
		},
		success : function(data){
			$("#CreatePoCount").text(data.CreatePoCount);
			$("#UpdatedPoCount").text(data.UpdatedPoCount);
			$("#DeletedPoCount").text(data.DeletedPoCount);
			$('#PoActivityGif').hide();
			$('#poActivityDiv').show();
			$("#poActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_PO+')"/>')
			
			
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
		
	})
	
	$.ajax({
		url : "getDSEActivityCount",
		type : "POST",
		datatype : "json",
		data : jsonObject,
		beforeSend : function(){
			$('#dseActivityDiv').hide();
			$('#dseActivityGif').show();
		},
		success : function(data){
			$("#CreateDSECount").text(data.CreatePoCount);
			$("#UpdatedDSECount").text(data.UpdatedPoCount);
			$("#DeletedDSECount").text(data.DeletedPoCount);
			$('#dseActivityGif').hide();
			$('#dseActivityDiv').show();
			$("#dseActivityDiv").wrap('<a href="#" onclick="activityData('+ACTIVITY_TYPE_DSE+')"/>')
		},
		error : function (e){
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
	})
}


function activityData(activityType){
	if(!validateOnchange()){
		return false;
	}
	var dateRange = $('#dateRange').val().split(' to ');
	var startDate =dateRange[0];
	var endDate =dateRange[1];
	var user =$('#subscribe').val()+","+$('#subscribe').select2('data').text;
	var finalValue= ""+startDate+","+endDate+","+user+","+activityType+"";
	
	window.location.replace("viewActivityData.in?data="+finalValue);
}

