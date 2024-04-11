$(document).ready(function() {
	getTransfer();
	getTransferDetails();
});

function getTransfer() {
	
	showLayer();
	var jsonObject								= new Object();
	jsonObject["ureaTransferId"]				= $("#ureaTransferId").val();
	
	console.log("jsonObject",jsonObject)

	$.ajax({
		url: "getUreaTransferByTransferId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTransfer(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTransfer(data){
	var ureaTransferDto = data.ureaTransferDto;
	
	console.log("ureaTransferDto",ureaTransferDto)
	$("#createdDate").text(ureaTransferDto.ureaTransferId);
	$("#createdDate").text(ureaTransferDto.creationDateStr);
	$("#transferBy").text(ureaTransferDto.ureaTranferBy);
	$("#transferTo").text(ureaTransferDto.ureaRequisitionSender);
	$("#transferFromLocation").text(ureaTransferDto.ureaTransferFromLocation);
	$("#transferToLocation").text(ureaTransferDto.ureaTransferToLoaction);
	$("#transferDate").text(ureaTransferDto.ureaTransferDateStr);
	$("#transferQuantity").text(ureaTransferDto.ureaTransferQuantity);
	$("#transferRemark").text(ureaTransferDto.ureaTransferRemark);
	$("#transferStatus").text(ureaTransferDto.ureaTransferStatus);
	$("#createdByName").text(ureaTransferDto.createdBy); 
	$("#createdDateStr").text(ureaTransferDto.creationDateStr); 
	$("#lastUpdatedByName").text(ureaTransferDto.lastUpdatedBy); 
	$("#lastUpdatedDateStr").text(ureaTransferDto.lastUpdatedDateStr); 
	
}

function getTransferDetails(){
	$("#transferUreaModal").modal('show');

	showLayer();
	var jsonObject								= new Object();
	jsonObject["ureaTransferId"]					= $("#ureaTransferId").val();

	$.ajax({
		url: "getUreaTransferDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTransferDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

	}
	function setTransferDetails  (data){
		
		$("#transferDetailsTable").empty();
		 
		var ureaTransferDetails = data.ureaTransferDetails;
		
		if(ureaTransferDetails != undefined || ureaTransferDetails != null){
			for(var index = 0 ; index < ureaTransferDetails.length; index++){
				
				var columnArray = new Array();
				columnArray.push("<td class='fit'>"+(index+1)+"</td>");
				columnArray.push("<td class='fit'>UTD-"+ureaTransferDetails[index].ureaTransferDetailsId+"</td>");
				columnArray.push("<td class='fit'ar>"+ ureaTransferDetails[index].ureaTransferFromLocation  +"</td>");
				columnArray.push("<td class='fit ar'><a target='_blank' href='showUreaInvoice.in?Id="+ureaTransferDetails[index].ureaInvoiceId+"'> UI-" + ureaTransferDetails[index].ureaInvoiceNumber +"<a></td>");
				columnArray.push("<td class='fit ar'>" + ureaTransferDetails[index].ureaInventoryTransferQuantity +"</td>");
				if($("#ureaRequisitionStatusId").val() == 2 || $("#ureaRequisitionStatusId").val() == 6 ){
					columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='removeUreaTransferDetail("+ureaTransferDetails[index].ureaTranferDetailsId+","+ureaTransferDetails[index].ureaInventoryTransferQuantity+","+ureaTransferDetails[index].ureaInvoiceToDetailsId+");'><span class='fa fa-trash'></span> Remove</a></td>");
				}	
				$('#transferDetailsTable').append("<tr id='ureaTranferDetailsId"+ureaTransferDetails[index].ureaTranferDetailsId+"' >" + columnArray.join(' ') + "</tr>");
			
			}
			columnArray = [];
			}else{
				showMessage('info','No Record Found!')
			}
		}

