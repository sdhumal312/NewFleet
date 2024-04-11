$(document).ready(
		function($) {
			$('button[id=sendUreaRequisition]').click(function(e) {
	
				showLayer();
				var jsonObject								= new Object();
			
				var ureaLiter								=  Number($('#ureaRequiredQuantity').val());
				jsonObject["ureaRequiredLocationId"]		= $('#ureaRequiredLocationId').val();
				jsonObject["ureaTransferFromLocationId"]	= $('#ureaTransferFromLocationId').val();
				jsonObject["ureaRequisitionReceiverId"]		= $('#ureaRequisitionReceiverId').val();//requ sender configure at java side
				jsonObject["ureaRequiredDate"]				= $('#ureaRequiredDate').val();
				jsonObject["ureaRequiredQuantity"]			= ureaLiter.toFixed(2);
				jsonObject["ureaRequisitionRemark"]			= $('#ureaRequisitionRemark').val();
				
				if(!validateRequisition()){
					return false;
				}
				
				
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