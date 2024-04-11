$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["driverId"]						= $('#driverId').val();
	$.ajax({
		url: "getDriverAllBasicDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setDriverBasicDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setDriverBasicDetailsList (data){
	
	$("#driverBasicDetailsTable").empty();
	var driverBasicDetailsList = data.driverBasicDetailsList;
	
	if(driverBasicDetailsList != undefined || driverBasicDetailsList != null){
		for(var index = 0 ; index < driverBasicDetailsList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ driverBasicDetailsList[index].detailsType  +"</td>");
			columnArray.push("<td class='fit ar'>" + driverBasicDetailsList[index].quantity +"</td>");
			columnArray.push("<td class='fit ar'>" + driverBasicDetailsList[index].assignDateStr +"</td>");
			columnArray.push("<td class='fit ar'>" + driverBasicDetailsList[index].remark +"</td>");
			if($("#driverStatusId").val() != 6){
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editDriverBasicDetail("+driverBasicDetailsList[index].driverBasicDetailsId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='deleteDriverBasicDetails("+driverBasicDetailsList[index].driverBasicDetailsId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			}
			
			$('#driverBasicDetailsTable').append("<tr id='driverBasicDetailsId"+driverBasicDetailsList[index].driverBasicDetailsId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

$(document).ready(
		function($) {
			$('button[id=saveBasicDetails]').click(function(e) {
	
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["detailsTypeId"]					= $('#addDetailTypeId').val();
				jsonObject["driverId"]						= $('#driverId').val();
				jsonObject["quantity"]						= $('#addQuantity').val();
				jsonObject["assignDate"]					= $('#addAssignDate').val();
				jsonObject["remark"]						= $('#addRemark').val();
				
				if($('#addDetailTypeId').val() == "" ||  $('#addDetailTypeId').val() == undefined){
					$('#addDetailType').focus();
					showMessage('errors', 'Please Select Details Type !');
					hideLayer();
					return false;
				}
				if($('#addQuantity').val() <= 0 ){
					$('#addQuantity').focus();
					showMessage('errors', 'Please Enter Quantity !');
					hideLayer();
					return false;
				}
				if($('#addAssignDate').val() == "" ||  $('#addAssignDate').val() == undefined){
					$('#addAssignDate').focus();
					showMessage('errors', 'Please Select Assign Date !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "addDriverBasicDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#addBasicDetails').modal('hide');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Details Saved Successfully!');
							location.reload();
						}
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	

function editDriverBasicDetail(driverBasicDetailsId){
	var jsonObject							= new Object();
	jsonObject["driverBasicDetailsId"]		= driverBasicDetailsId;
	
	$.ajax({
		url: "getDriverBasicDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setDriverBasicDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setDriverBasicDetails(data){
	var driverBasicDetails 	= data.driverBasicDetails;
	
	$('#editDetailTypeId').select2('data', {
		id : driverBasicDetails.detailsTypeId,
		text : driverBasicDetails.detailsType
	});
	
	$('#driverBasicDetailsId').val(driverBasicDetails.driverBasicDetailsId);
	$('#editQuantity').val(driverBasicDetails.quantity);
	$('#editAssignDate').val(driverBasicDetails.assignDateStr);
	$('#editRemark').val(driverBasicDetails.remark);
	
	$('#editBasicDetails').modal('show');
}

$(document).ready(
		function($) {
			$('button[id=updateDriverBasicDetials]').click(function(e) {
				showLayer();
				var jsonObject					= new Object();
				jsonObject["driverBasicDetailsId"]			= $('#driverBasicDetailsId').val();
				jsonObject["driverId"]						= $('#driverId').val();
				jsonObject["detailsTypeId"]					= $('#editDetailTypeId').val();
				jsonObject["quantity"]						= $('#editQuantity').val();
				jsonObject["assignDate"]					= $('#editAssignDate').val();
				jsonObject["remark"]						= $('#editRemark').val();
				
				if($('#editDetailTypeId').val() == "" ||  $('#editDetailTypeId').val() == undefined){
					$('#editDetailTypeId').focus();
					showMessage('errors', 'Please Select Details Type !');
					hideLayer();
					return false;
				}
				if($('#editQuantity').val() <= 0 ){
					$('#editQuantity').focus();
					showMessage('errors', 'Please Enter Quantity !');
					hideLayer();
					return false;
				}
				if($('#editAssignDate').val() == "" ||  $('#editAssignDate').val() == undefined){
					$('#editAssignDate').focus();
					showMessage('errors', 'Please Select Assign Date !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "updateDriverBasicDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#editBasicDetails').modal('hide');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Update  Successfully!');
							location.reload();
						}
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});

function deleteDriverBasicDetails(driverBasicDetailsId){
	
	if (confirm('Are you sure, you want to Delete?')) {
	
		if(driverBasicDetailsId != undefined && driverBasicDetailsId > 0){
			
			var jsonObject							= new Object();
			
			jsonObject["driverBasicDetailsId"]		= driverBasicDetailsId;
			
			$.ajax({
				url: "deleteDriverBasicDetails",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Deleted Successfully!');
					location.reload();
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	  
	}else {
		location.reload();
	}
}

$(document).ready(function() {
	$("#addDetailTypeId,#editDetailTypeId").select2({
		ajax : {
            url:"driverBasicDetailsTypeAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.driverBasicDetailsTypeName, slug: t.slug, id: t.driverBasicDetailsTypeId
                        }
                    }
                    )
                }
            }
        }
	
	});
	
}); 

