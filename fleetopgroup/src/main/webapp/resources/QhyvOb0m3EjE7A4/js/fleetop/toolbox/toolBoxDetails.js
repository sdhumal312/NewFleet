$(document).ready(function() {
	getToolBoxDetailsList();
});


function saveToolBoxDetails(vid) {
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["toolBoxId"]						= $('#toolBox').val();
	jsonObject["quantity"]						= $('#noOfTools').val();
	jsonObject["description"]					= $('#description').val();
	jsonObject["vid"]							= $('#vid').val();
	
	if($('#toolBox').val().trim() == ''){
		$('#toolBox').focus();
		showMessage('info', 'Please Select ToolBox !');
		hideLayer();
		return false;
	}
	
	if($('#noOfTools').val().trim() == ''){
		$('#noOfTools').focus();
		showMessage('info', 'Please Select No of ToolBox !');
		hideLayer();
		return false;
	}
	

	$.ajax({
		url: "saveToolBoxDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			 $('#toolBox').val('');
			 $('#noOfTools').val('');
			 $('#description').val('');
			 
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			$("#addToolBoxDetails").modal('hide');
			showMessage('success', 'ToolBoxDetails Saved Successfully!');
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function isNumberKey(evt)
{	
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}


$(document).ready(function() {
	$("#toolBox").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		ajax : {
			url : "toolBoxAutoComplete",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				console.log("this",e)
				return {
					term : e
				}
			},
			results : function(e) {
				console.log("dsf",e)
				return {
					results : $.map(e, function(e) {
						return {
							text : e.toolBoxName,
							slug : e.slug,
							id : e.toolBoxId
						}
					})
				}
			}
		}
	
	});
});



function getToolBoxDetailsList() {
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["vid"]				= $('#vid').val();
	console.log("vid",vid)

	$.ajax({
		url: "getToolBoxDetailsByVid",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			if(data.toolBoxDetails  == undefined || data.toolBoxDetails == null){
				showMessage('info','No Record found')
				hideLayer();
				return false;
			}
			setToolBoxDetailsList(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
}




function setToolBoxDetailsList(data){
	
	$("#toolBoxDetailsTable").empty();
	var toolBoxDetails = data.toolBoxDetails ;
console.log("toolBoxDetails",toolBoxDetails)
	for(var index = 0 ; index < toolBoxDetails.length; index++){

	var columnArray = new Array();
	columnArray.push("<td class='fit ar' style='vertical-align: middle;'>"+(index+1)+"</td>");
	columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + toolBoxDetails[index].toolBoxName +"</td>");
	columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + toolBoxDetails[index].quantity + "</td>");
	columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + toolBoxDetails[index].description + "</td>");
	columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='removeToolBoxDetails("+toolBoxDetails[index].toolBoxDetailsId+");'><span class='fa fa-trash'></span> Delete</a></td>");
	$('#toolBoxDetailsTable').append("<tr  c" +
			"lass='ng-scope' id='penaltyID"+toolBoxDetails[index].dsaid+"' >" + columnArray.join(' ') + "</tr>");
	}
	columnArray = [];

	}

function removeToolBoxDetails(toolBoxDetailsId){
	if (confirm('Are you sure, you want to Delete this Party Master?')) {
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["toolBoxDetailsId"]				= toolBoxDetailsId;

	$.ajax({
		url: "deleteToolBoxDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			showMessage('info','Deleted Successfully')
			location.reload();
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
	}
}

