$(document).ready(
		function($) {
			getUreaRequisitionDetails();
		});

function getUreaRequisitionDetails(){
	showLayer();
	var jsonObject								= new Object();
	jsonObject["ureaRequisitionId"]					= $("#ureaRequisitionId").val();

	$.ajax({
		url: "getRequisitionDetailById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRequisitionDetail(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setRequisitionDetail(data){
	console.log("data",data)
	var requisition = data.requisitionList;
	$("#editUreaRequiredLocationId").val(requisition.ureaRequiredLocationId);
	$("#editUreaRequiredLocationName").val(requisition.ureaRequiredLocation);
	$("#editUreaTransferFromLocationId").val(requisition.ureaTransferFromLocationId);
	$("#editUreaTransferFromLocation").val(requisition.ureaTransferFromLocation);
	$("#editUreaRequisitionReceiverId").val(requisition.ureaRequisitionReceiverId);
	$("#editUreaRequisitionReceiverName").val(requisition.ureaRequisitionReceiver);
	$("#ureaRequisitionSenderId").val(requisition.ureaRequisitionSenderId);
	$("#ureaRequisitionSender").val(requisition.ureaRequisitionSender);
	$("#ureaRequiredQuantity").val(requisition.ureaRequiredQuantity);
	$("#ureaRequiredDate").val(requisition.creationDateStr);
	$("#ureaRequisitionRemark").val(requisition.ureaRequisitionRemark);
	
	if($("#editUreaRequiredLocationId").val() != undefined || $("#editUreaRequiredLocationId").val() != null){// will show only on edit
		$('#ureaRequiredLocationId').select2('data', {
			id : $("#editUreaRequiredLocationId").val(),
			text : $("#editUreaRequiredLocationName").val()
		});
	}
	if($("#editUreaTransferFromLocationId").val() != undefined || $("#editUreaTransferFromLocationId").val() != null){// will show only on edit
		$('#ureaTransferFromLocationId').select2('data', {
			id : $("#editUreaTransferFromLocationId").val(),
			text : $("#editUreaTransferFromLocation").val()
		});
	}
	if($("#editUreaRequisitionReceiverId").val() != undefined || $("#editUreaRequisitionReceiverId").val() != null){// will show only on edit
		$('#ureaRequisitionReceiverId').select2('data', {
			id : $("#editUreaRequisitionReceiverId").val(),
			text : $("#editUreaRequisitionReceiverName").val()
		});
	}
	
	
}

$(document).ready(
		function($) {
			$('button[id=UpdateUreaRequisition]').click(function(e) {
	
				showLayer();
				var jsonObject								= new Object();
			
				var ureaLiter								=  Number($('#ureaRequiredQuantity').val());
				jsonObject["ureaRequisitionId"]				= $('#ureaRequisitionId').val();
				jsonObject["ureaRequiredLocationId"]		= $('#ureaRequiredLocationId').val();
				jsonObject["ureaTransferFromLocationId"]	= $('#ureaTransferFromLocationId').val();
				jsonObject["ureaRequisitionReceiverId"]		= $('#ureaRequisitionReceiverId').val();//requ sender configure at java side
				jsonObject["ureaRequiredDate"]				= $('#ureaRequiredDate').val();
				jsonObject["ureaRequiredQuantity"]			= ureaLiter.toFixed(2);
				jsonObject["ureaRequisitionRemark"]			= $('#ureaRequisitionRemark').val();
				
				if(!validateRequisition()){
					return false;
				}
				
				console.log("jsonObject",jsonObject)
				
				$.ajax({
					url: "sendUreaRequisition",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						
						if(data.requiredLocationNotFound == true){
							showMessage('info','Required Location Not Found');
							hideLayer();
						}else if(data.fromLocationNotFound == true){
							showMessage('info','From Location Not Found');
							hideLayer();
						}else if(data.sameLocationFound == true){
							showMessage('info','Required Location And Transfer Location Can Not Be Same');
							hideLayer();
						}else if(data.requisitionReceiverNotFound == true){
							showMessage('info','Requisition Receiver Not Found');
							hideLayer();
						}else if(data.requiredDateNotFound == true){
							showMessage('info','Required Date Not Found');
							hideLayer();
						}else if(data.requiredQuantityNotFound == true){
							showMessage('info','Required Quantity Not Found');
							hideLayer();
						}else if(data.save == true ){
							showMessage('success','Requisition Sent Successfully');
							window.location.replace("showUreaRequisitionDetail.in?Id="+data.ureaRequisition.ureaRequisitionId+"");
						}
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	