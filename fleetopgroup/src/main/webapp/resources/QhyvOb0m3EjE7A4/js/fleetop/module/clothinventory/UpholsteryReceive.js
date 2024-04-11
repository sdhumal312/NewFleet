$(document).ready(function() {
	getTransferReceivedDetails(1);
});

function getTransferReceivedDetails(pageNumber) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;

	$.ajax({
		url: "getTransferReceivedDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setTransferReceivedDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTransferReceivedDetails(data) {
	$("#VendorPaymentTable4").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Upholstery Name"));
	tr1.append(th3.append("Transfer From & To"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Transfer By"));
	tr1.append(th6.append("Received By"));
	tr1.append(th7.append("Date"));
	tr1.append(th8.append("Status"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.UpholsteryReceiveList != undefined && data.UpholsteryReceiveList.length > 0) {
		
		var UpholsteryReceiveList = data.UpholsteryReceiveList;
	
		for(var i = 0; i < UpholsteryReceiveList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(UpholsteryReceiveList[i].clothTypeName));
			tr1.append(td3.append(UpholsteryReceiveList[i].fromLocationStr+'</br>'+UpholsteryReceiveList[i].toLocationStr));
			tr1.append(td4.append(UpholsteryReceiveList[i].quantity));
			tr1.append(td5.append(UpholsteryReceiveList[i].transferByIdStr+'- VIA -'+UpholsteryReceiveList[i].transferViaIdStr));
			tr1.append(td6.append(UpholsteryReceiveList[i].transferReceivedByIdStr));
			tr1.append(td7.append(UpholsteryReceiveList[i].transferDateStr));
			tr1.append(td8.append(
					' <a href="#" id="recev" class="btn btn-info btn-sm" ' 
				   +' onclick="receiveUpholstery('+UpholsteryReceiveList[i].upholsteryTransferId+')" >' 
				   +' <span class="fa fa-download"></span> Receive </a>' 
				   +' <a href="#" id="rej" class="btn btn-danger btn-sm" '
				   +' onclick="rejectUpholstery('+UpholsteryReceiveList[i].upholsteryTransferId+')" >' 
				   +' <span class="fa fa-plus"></span> Reject </a>' 
					));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable4").append(thead);
	$("#VendorPaymentTable4").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getTransferReceivedDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getTransferReceivedDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getTransferReceivedDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getTransferReceivedDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTransferReceivedDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTransferReceivedDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}

function receiveUpholstery(upholsteryTransferId){
	
	var jsonObject	= new Object();
	
	var ans = confirm("Are you sure to Remove ?");
	if(ans){
		showLayer();
		jsonObject["upholsteryTransferId"]	= upholsteryTransferId;
		$.ajax({
			url: "getReceiveDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.ReceiveDeatils != undefined){
					
					$('#receiveClothNameId').val(data.ReceiveDeatils.clothTypeName);
					$('#receiveToLocationNameId').val(data.ReceiveDeatils.toLocationStr);
					$('#receiveQuantityId').val(data.ReceiveDeatils.quantity);
					$('#receiveByNameId').val(data.ReceiveDeatils.transferReceivedByIdStr);
					
					$('#receiveClothTypeId').val(data.ReceiveDeatils.clothTypesId);
					$('#receivefromLocationId').val(data.ReceiveDeatils.fromLocationId);
					$('#receivetoLocationId').val(data.ReceiveDeatils.toLocationId);
					$('#receiveStockTypeId').val(data.ReceiveDeatils.stockTypeId);
					$('#receiveById').val(data.ReceiveDeatils.transferReceivedById);
					$('#receiveTransferViaId').val(data.ReceiveDeatils.transferViaId);
					$('#receiveUpholsteryTransferId').val(data.ReceiveDeatils.upholsteryTransferId);
					
					$('#receive').modal('show');
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	
}

function saveReceiveUpholstery(){

	if(Number($("#receiveDescriptionId").val()) == ""){
		showMessage('errors','Please select Receive Description!');
		return false;
	}
	
	var jsonObject 	= new Object();
	
	jsonObject["receiveClothTypeId"]			= $('#receiveClothTypeId').val();
	jsonObject["receivefromLocationId"]			= $('#receivefromLocationId').val();
	jsonObject["receivetoLocationId"]			= $('#receivetoLocationId').val();
	jsonObject["receiveStockTypeId"]			= $('#receiveStockTypeId').val();
	jsonObject["receiveQuantityId"]				= $('#receiveQuantityId').val();
	jsonObject["receiveById"]					= $('#receiveById').val();
	jsonObject["receiveDescriptionId"]			= $('#receiveDescriptionId').val();
	jsonObject["receiveTransferViaId"]			= $('#receiveTransferViaId').val();
	jsonObject["receiveUpholsteryTransferId"]	= $('#receiveUpholsteryTransferId').val();
	
	showLayer();
	$.ajax({
        url: "saveReceiveUpholstery",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	$('#receive').modal('hide');
        	getTransferReceivedDetails(1);
        	showMessage('info', 'Upholstery Received Successfully!')
        	hideLayer();
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
	});
	
}

function rejectUpholstery(upholsteryTransferId){
	
	var jsonObject	= new Object();
	
	var ans = confirm("Are you sure to Reject ?");
	if(ans){
		showLayer();
		jsonObject["upholsteryTransferId"]	= upholsteryTransferId;
		$.ajax({
			url: "getReceiveDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.ReceiveDeatils != undefined){
					
					$('#rejectClothTypeName').val(data.ReceiveDeatils.clothTypeName);
					$('#rejectToLocationName').val(data.ReceiveDeatils.toLocationStr);
					$('#rejectQuantityId').val(data.ReceiveDeatils.quantity);
					$('#rejectByName').val(data.ReceiveDeatils.transferReceivedByIdStr);
					
					$('#rejectClothTypeId').val(data.ReceiveDeatils.clothTypesId);
					$('#rejectfromLocationId').val(data.ReceiveDeatils.fromLocationId);
					$('#rejecttoLocationId').val(data.ReceiveDeatils.toLocationId);
					$('#rejectStockTypeId').val(data.ReceiveDeatils.stockTypeId);
					$('#rejectById').val(data.ReceiveDeatils.transferReceivedById);
					$('#rejectTransferViaId').val(data.ReceiveDeatils.transferViaId);
					$('#rejectUpholsteryTransferId').val(data.ReceiveDeatils.upholsteryTransferId);
					
					$('#reject').modal('show');
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	
}

function saveRejectUpholstery(){

	if(Number($("#rejectDescriptionId").val()) == ""){
		showMessage('errors','Please select Reject Description!');
		return false;
	}
	
	var jsonObject 	= new Object();
	
	jsonObject["rejectClothTypeId"]				= $('#rejectClothTypeId').val();
	jsonObject["rejectfromLocationId"]			= $('#rejectfromLocationId').val();
	jsonObject["rejecttoLocationId"]			= $('#rejecttoLocationId').val();
	jsonObject["rejectStockTypeId"]				= $('#rejectStockTypeId').val();
	jsonObject["rejectQuantityId"]				= $('#rejectQuantityId').val();
	jsonObject["rejectById"]					= $('#rejectById').val();
	jsonObject["rejectDescriptionId"]			= $('#rejectDescriptionId').val();
	jsonObject["rejectTransferViaId"]			= $('#rejectTransferViaId').val();
	jsonObject["rejectUpholsteryTransferId"]	= $('#rejectUpholsteryTransferId').val();

	
	showLayer();
	$.ajax({
        url: "saveRejectUpholstery",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	$('#reject').modal('hide');
        	getTransferReceivedDetails(1);
        	showMessage('info', 'Upholstery Rejected Successfully!')
        	hideLayer();
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
	});
	
}