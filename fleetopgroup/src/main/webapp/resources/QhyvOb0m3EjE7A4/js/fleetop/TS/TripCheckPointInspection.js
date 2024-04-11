$(document).ready(function() {
	$.getJSON("tripCheckPointParameterAutocomplete.in", function(z) {
		
		parameterList	= z;//To get All Company Name 
		$("#parameter").empty();
		$("#parameter").select2();
		$("#parameter").append($("<option>").text("Please Select Parameter ").attr("value",""));
		for(var k = 0; k <parameterList.length; k++){
			$("#parameter").append($("<option>").text(parameterList[k].tripCheckParameterName).attr("value", parameterList[k].tripCheckPointParameterId));
		}

	});
});

$(document).ready(function() {
	var jsonObject			= new Object();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();

	$.ajax({
		url: "getTripSheetByTripsheetId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderTripDetails(data);
			getCheckPointsByRouteId(data);
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
});

function renderTripDetails(data){
	var tripsheet 	= data.tripsheet;
	
	$("#tripsheetNumber").text(tripsheet.tripSheetNumber);
	$("#createDate").text(tripsheet.dispatchedByTime);
	$("#vehicle").text(tripsheet.vehicle_registration);
	$("#routeName").text(tripsheet.routeName);
	$("#tripOpenDate").text(tripsheet.tripOpenDate);
	$("#tripCloseDate").text(tripsheet.closetripDate);
	$("#vehicleGroup").text(tripsheet.dispatchedLocation);
	$("#firstDriver").text(tripsheet.tripFristDriverName+" "+tripsheet.tripFristDriverLastName+" - "+tripsheet.tripFristDriverFatherName);
	$("#secondDriver").text(tripsheet.tripSecDriverName+" "+tripsheet.tripSecDriverLastName+" - "+tripsheet.tripSecDriverFatherName);
	$("#cleaner").text(tripsheet.tripCleanerName);
	$("#openingKM").text(tripsheet.tripOpeningKM);
	$("#closingKM").text(tripsheet.tripClosingKM);
	$("#routeId").val(tripsheet.routeID);
}

function getCheckPointsByRouteId(data){
	
	var tripsheet 				= data.tripsheet;
	var jsonObject				= new Object();
	
	jsonObject["routeId"] 	  	=  tripsheet.routeID;
	jsonObject["companyId"] 	=  $("#companyId").val();

	$.ajax({
        url: "getRouteWiseTripCheckPoint",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	renderTripCheckPoints(data);
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
});
}

function renderTripCheckPoints(data){
	$("#tripCheckPointTable").empty();
	var routeWiseTripCheckPointList = data.routeWiseTripCheckPointList;
	if(routeWiseTripCheckPointList != undefined && routeWiseTripCheckPointList != null && routeWiseTripCheckPointList.length > 0){
		for(var index = 0 ; index < routeWiseTripCheckPointList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar>"+ routeWiseTripCheckPointList[index].tripCheckPointName  +"</td>");
			columnArray.push('<td class="fit ar" style="vertical-align: middle;" ><a href="#" class="confirmation" onclick="inspectTripCheckPointParameter('+routeWiseTripCheckPointList[index].tripCheckPointId+',\'' + routeWiseTripCheckPointList[index].tripCheckPointName + '\');"> Inspect</a> &nbsp;&nbsp;&nbsp<a href="#" class="confirmation" onclick="getInspectedTripCheckPointParameter('+routeWiseTripCheckPointList[index].tripCheckPointId+',\'' + routeWiseTripCheckPointList[index].tripCheckPointName + '\');"> Show</a>  </td>');
			
			$('#tripCheckPointTable').append("<tr id='tripCheckPointParameterId"+routeWiseTripCheckPointList[index].tripCheckPointId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			hideLayer();
			showMessage('info','No Record Found!')
		}
	hideLayer();
}


$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div class="box">'
			+'<div class="row">'
				+'<div class="col-sm-8 col-md-1" style="width:9.33%"> <label>Parameter :</label> </div>'
				+'<div class=" col-sm-8 col-md-3"><select style="width: 100%;" id="parameter'+d+'" name="parameterName" placeholder="Please Select Parameter"></select> </div>'
				+'<div class="col-sm-8 col-md-3">'
					+'<label>Pass </label>'
					+'<input type="radio" class="testResult" value="true" name="result'+d+'" id="pass'+d+'" checked>&emsp;&emsp;'
					+'<label>Fail </label>'
					+'<input type="radio" class="testResult" value="false" name="result'+d+'" id="fail'+d+'">'
				+'</div>'
				+'<div class="showMulDoc hide">'
					+'<div class="col-sm-8 col-md-1" style="width:4.33%"> <label>Doc </label></div> &emsp;'
					+'<div class="col-sm-8 col-md-2"> <input type="file" name="input-file-preview" id="parameterDoc'+a+'" /> </div>'
				+'</div>'	
			+'</div>'
			+'<a href="#"class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i>Remove</a></font>'
			+'</div><br>'), 
       $(document).ready(function() {
			$.getJSON("tripCheckPointParameterAutocomplete.in", function(z) {
				
				parameterList	= z;//To get All Company Name 
				$("#parameter"+d).empty();
				$("#parameter"+d).select2();
				$("#parameter"+d).append($("<option>").text("Please Select Parameter ").attr("value",""));
				for(var k = 0; k <parameterList.length; k++){
					$("#parameter"+d).append($("<option>").text(parameterList[k].tripCheckParameterName).attr("value", parameterList[k].tripCheckPointParameterId));
				}

			});
		}))
	    if($("#checkPointParameterDocumnetConfig").val() == true || $("#checkPointParameterDocumnetConfig").val() == 'true'){
	    	$(".showMulDoc").removeClass('hide');
		}
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
   
});



function inspectTripCheckPointParameter(tripCheckPointId,tripCheckPointName){
	$("#tripCheckPointId").val(tripCheckPointId);
	$("#tripCheckPointName").text(tripCheckPointName);
	$("#inspectCheckPointModal").modal('show');
}

function getInspectedTripCheckPointParameter(tripCheckPointId,tripCheckPointName){
	var jsonObject						= new Object();
	$("#tripCheckPointId1").val(tripCheckPointId);
	$("#tripCheckPointName1").text(tripCheckPointName);
	jsonObject["tripCheckPointId"] 	  	=  tripCheckPointId;
	jsonObject["tripSheetId"] 	  	=  $('#tripsheetId').val();;
	jsonObject["companyId"] 			=  $("#companyId").val();
	$("#InspectedTripCheckPointParameterModal").modal('show');
	$.ajax({
        url: "getInspectedTripCheckPointParameter",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	renderInspectedCheckPointParameter(data);
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
});
}

function renderInspectedCheckPointParameter(data){
	$("#InspectedTripCheckPointParameterTable").empty();
	var tripCheckPointParameterInspectionList = data.tripCheckPointParameterInspectionList;
	if(tripCheckPointParameterInspectionList != undefined && tripCheckPointParameterInspectionList != null && tripCheckPointParameterInspectionList.length > 0){
		for(var index = 0 ; index < tripCheckPointParameterInspectionList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar>"+ tripCheckPointParameterInspectionList[index].tripCheckPointParameterName  +"</td>");
			columnArray.push("<td class='fit'ar>"+ tripCheckPointParameterInspectionList[index].checkPointParameterInspectStatus  +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='removeInspectedParameter("+tripCheckPointParameterInspectionList[index].tripCheckPointParameterInspectionId+","+tripCheckPointParameterInspectionList[index].tripCheckPointInspectionId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#InspectedTripCheckPointParameterTable').append("<tr id='tripCheckPointParameterId"+tripCheckPointParameterInspectionList[index].tripCheckPointParameterInspectionId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			hideLayer();
			showMessage('info','No Record Found!')
		}
	hideLayer();

}


$(document).ready(
		function($) {
			$('button[id=inspectTripCheckPointParameter]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject			= new Object();
				var array	 			= new Array();
				var resultArr 			= new Array();
				var parameterIdArr 		= new Array();
				var resultArr 			= new Array();
				var documentArr 		= new Array();
				
				$("select[name=parameterName]").each(function(){
					parameterIdArr.push($(this).val());
				});
				$('input[name*=result]:checked').each(function(){
					resultArr.push($(this).val());
				});
				
				var documentArr 		= new Array();
				$("input[name=input-file-preview]").each(function(){
					documentArr.push($(this).val());
				});
				

				for(var i =0 ; i< parameterIdArr.length; i++){
					var parameter					= new Object();
					
					
					if(parameterIdArr[i] == "" || parameterIdArr[i] == undefined){
						showMessage('info','Please Select Parameter');
						hideLayer();
						return false;
					}
					
					parameter.tripCheckPointParameterId							 = parameterIdArr[i];                                
					parameter.checkPointParameterInspectStatusId				 = resultArr[i];   
					parameter.checkPointParameterPhoto							 = documentArr[i];   
				                                                                       
					array.push(parameter);
				}
				
				
				jsonObject.parameter 			= JSON.stringify(array);
				jsonObject.tripCheckPointId		= $("#tripCheckPointId").val();
				jsonObject.tripSheetId			= $('#tripsheetId').val();
				jsonObject.companyId			= $('#companyId').val();
				jsonObject.userId				= $('#userId').val();
				
				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);

			    data.append("parameterList", JSON.stringify(jsonObject));
			    
			  
				
				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "inspectTripCheckPointParameter",
					data: data,
					processData: false,
			        contentType: false,
			        cache: false,
			        success: function (data) {
			        	location.reload();
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
				});
			});
		});

function removeInspectedParameter(tripCheckPointParameterInspectionId, tripCheckPointInspectionId){
	
	var jsonObject			= new Object();

	jsonObject["tripCheckPointParameterInspectionId"]	= tripCheckPointParameterInspectionId;
	jsonObject["tripCheckPointInspectionId"]			= tripCheckPointInspectionId;
	
	$.ajax({
		url: "removeInspectedParameter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info', 'Remove Successfully');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}