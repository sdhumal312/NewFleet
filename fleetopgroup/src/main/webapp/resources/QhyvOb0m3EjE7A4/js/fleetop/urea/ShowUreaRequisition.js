$(document).ready(function() {
	getRequisitionDetail();
	getTransfer();
});

var SENT_REQUISTION			= 1;          
var ACCEPTED_REQUISTION		= 2;          
var REJECTED_REQUISTION		= 3;          
var RECEIVED				= 4;          
var REJECTED				= 5;          
var SAVE_TRANSFERED			= 6;          
var COMPLETE_TRANSFERED		= 7;          

function getRequisitionDetail() {
	
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
	var requisitionList = data.requisitionList;
	$("#requiredLocation").text(requisitionList.ureaRequiredLocation);
	$("#ureaTransferFromLocationId").val(requisitionList.ureaTransferFromLocationId);
	$("#ureaTransferFromLocation").text(requisitionList.ureaTransferFromLocation);
	$("#createdDate").text(requisitionList.creationDateStr);
	$("#ureaRequisitionSenderId").val(requisitionList.ureaRequisitionSenderId);
	$("#requisitionSenderName").text(requisitionList.ureaRequisitionSender);
	$("#ureaRequisitionReceiverId").val(requisitionList.ureaRequisitionReceiverId);
	$("#requisitionReceiverName").text(requisitionList.ureaRequisitionReceiver);
	$("#requiredDate").text(requisitionList.ureaRequiredDateStr);
	$("#requiredQuantity").text(requisitionList.ureaRequiredQuantity);
	$("#requiredQuantityModal").text(requisitionList.ureaRequiredQuantity);
	$("#requisitionRemark").text(requisitionList.ureaRequisitionRemark);
	$("#ureaRequisitionStatusId").val(requisitionList.ureaRequisitionStatusId);
	$("#requisitionStatusName").text(requisitionList.ureaRequisitionStatus);
	$("#ureaToLocationId").val(requisitionList.ureaRequiredLocationId);
	$("#ureaToLocation").val(requisitionList.ureaRequiredLocation);
	$("#totalRequiredQuantity").val(requisitionList.ureaRequiredQuantity);
	$("#requiredQuantityModal").text(requisitionList.ureaRequiredQuantity);//on modal
	$("#toLocationModal").text(requisitionList.ureaRequiredLocation); //on modal
	
	$("#createdByName").text(requisitionList.createdBy); 
	$("#createdDateStr").text(requisitionList.creationDateStr); 
	$("#lastUpdatedByName").text(requisitionList.lastUpdatedBy); 
	$("#lastUpdatedDateStr").text(requisitionList.lastUpdatedDateStr); 
	
	if($("#userId").val() == $("#ureaRequisitionReceiverId").val()){
		$("#validateRequisitionReceiver").val(true)
	}else{
		$("#validateRequisitionReceiver").val(false)
	}
	if($("#userId").val() == $("#ureaRequisitionSenderId").val()){
		$("#validateRequisitionSender").val(true)
	}else{
		$("#validateRequisitionSender").val(false)
	}
	
	if($("#ureaRequisitionStatusId").val() == SENT_REQUISTION && ($("#validateRequisitionSender").val() == "true" || $("#validateRequisitionSender").val() == true)){
		$("#acceptReq").removeClass('hide');
		$("#rejectReq").removeClass('hide');
		$("#tansferDiv").addClass('hide');
		$("#editReq").removeClass('hide');
	}else if($("#ureaRequisitionStatusId").val() == ACCEPTED_REQUISTION && ($("#validateRequisitionSender").val() == "true" || $("#validateRequisitionSender").val() == true)){
		$("#transferComplete").removeClass('hide');
		$("#showRemark").removeClass('hide');
		$("#saveTransfer").removeClass('hide');
		$("#saveEntries").removeClass('hide');
	}else if($("#ureaRequisitionStatusId").val() == COMPLETE_TRANSFERED && ($("#validateRequisitionReceiver").val() == true  ||  $("#validateRequisitionReceiver").val() == "true" )){
		$("#receivedUrea").removeClass('hide');
		$("#rejectUrea").removeClass('hide');
	}else if($("#ureaRequisitionStatusId").val() == REJECTED_REQUISTION){
		$("#tansferDiv").addClass('hide');
	}
}

$("#editReq").click(function(){
	if($("#ureaRequisitionStatusId").val() != SENT_REQUISTION){
		location.reload();
	}else{
		window.location.replace("editUreaRequisition.in?Id="+$("#ureaRequisitionId").val()+"");
	}
}); 

function addRejectReqRemark(){ 
	$("#addRejctReqRemarkModal").modal('show');
}

function approve_Reject_Requisition(Accept_Reject_status){
	showLayer();
	var jsonObject								= new Object();

	jsonObject["ureaRequisitionId"]				= $('#ureaRequisitionId').val();
	jsonObject["ureaRequisitionStatusId"]		= Accept_Reject_status;
	jsonObject["ureaRequisitionRejectRemark"]	= $("#ureaRequisitionRejectRemark").val();;
	jsonObject["ureaRequisitionId"]				= $("#ureaRequisitionId").val();
	jsonObject["ureaTransferToLoactionId"]		= $("#ureaToLocationId").val();
	jsonObject["ureaRequisitionSenderId"]		= $("#ureaRequisitionSenderId").val();
	
	
	$.ajax({
		url: "approveRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.notValidUser == true){
				showMessage('success','Not A Valid User')
				hideLayer();
			}else if(data.acceptReq == true){
				showMessage('success','Requisition Approved')
				location.reload();
			}else if(data.rejectReq == true){
				showMessage('success','Requisition Reject')
				location.reload();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getTransfer() {
	
	showLayer();
	var jsonObject								= new Object();
	jsonObject["ureaRequisitionId"]				= $("#ureaRequisitionId").val();

	$.ajax({
		url: "getUreaTransferByRequisitionId",
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
	
	$("#transferTable").empty();
	var ureaTransfer = data.ureaTransfer;
	if(ureaTransfer != undefined || ureaTransfer != null){
		var requiredQuantity 	= Number($("#requiredQuantity").text()).toFixed(2);
		var transferQuantity 	= Number(ureaTransfer.ureaTransferQuantity).toFixed(2);
		var pendingQuantity 	= Number(requiredQuantity-transferQuantity).toFixed(2);
		
		$("#transferQuantity").val(transferQuantity);
		$("#pendingQuantity").val(pendingQuantity)
		$("#pendingQuantityModal").text(pendingQuantity)
		$("#ureaTransferId").val(ureaTransfer.ureaTransferId)
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'ar>  <a target='_blank' href='showUreaTransferDetails.in?Id="+ureaTransfer.ureaTransferId +"'> UT-"+ ureaTransfer.ureaTransferId  +"</a></td>");
			columnArray.push("<td class='fit ar'>" + requiredQuantity +"</td>");
			columnArray.push("<td class='fit ar'>" + transferQuantity +"</td>");
			columnArray.push("<td class='fit ar'>" + pendingQuantity +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' id='showTransferDetails' class='confirmation' onclick='transferUrea();'><span class='fa fa-edit'></span> Transfer</a></td>");
			$('#transferTable').append("<tr id='ureaTransferId"+ureaTransfer.ureaTransferId+"' >" + columnArray.join(' ') + "</tr>");
			
			
			 if($("#ureaRequisitionStatusId").val() == COMPLETE_TRANSFERED || $("#ureaRequisitionStatusId").val() == RECEIVED || $("#ureaRequisitionStatusId").val() == REJECTED){
				 $("#showTransferDetails").addClass('hide');
				 $("#actionId").addClass('hide');
			 }
		columnArray = [];
		}else{
			showMessage('info','No Transfer Detail Found!')
		}
	
}

function transferUrea(){
$("#transferUreaModal").modal('show');

showLayer();
var jsonObject								= new Object();
jsonObject["ureaTransferId"]				= $("#ureaTransferId").val();

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
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='removeUreaTransferDetail("+ureaTransferDetails[index].ureaTransferDetailsId+","+ureaTransferDetails[index].ureaInventoryTransferQuantity+","+ureaTransferDetails[index].ureaInvoiceToDetailsId+");'><span class='fa fa-trash'></span> Remove</a></td>");
			$('#transferDetailsTable').append("<tr id='ureaTransferDetailsId"+ureaTransferDetails[index].ureaTransferDetailsId+"' >" + columnArray.join(' ') + "</tr>");
		
		}
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}


$(document).ready(function() {
	$("#ureaFromLocation").select2({
	    minimumInputLength: 0,
	    minimumResultsForSearch: 10,
	    ajax: {
	        url: "getLocationUreaStockSearchListByLocationId.in",
	        dataType: "json",
	        type: "POST",
	        contentType: "application/json",
	        quietMillis: 50,
	        data: function(a) {
	            return {
	            	locationId: $("#ureaTransferFromLocationId").val()
	            }
	        },
	        results: function(a) {
	            return {
	                results: $.map(a, function(a) {
	                    return {
	                        text: a.locationName +" "+ a.subLocation + " - UI-" + a.ureaInvoiceNumber + " - " + a.manufacturerName + " - " + a.stockQuantity + "- Rate : "+ a.unitprice+ " dis : "+ a.discount+ " GST : "+ a.tax,
	                        id: a.ureaInvoiceToDetailsId,
	                        rate : a.unitprice,
	                        discount : a.discount,
	                        tax : a.tax,
	                        wareHouseLocation : a.wareHouseLocation,
	                        maxQuantity : a.stockQuantity,
	                        manufacturerId : a.manufacturerId,
	                        ureaInvoiceId : a.ureaInvoiceId
	                        
	                    }
	                })
	            }
	        }
	    }
	})
});

$("#ureaFromLocation").change(function(){
	var data = $("#ureaFromLocation").select2('data');
	
	$('#ureaStockQuantity').val(data.maxQuantity);
	$('#transferFromLocation').val(data.wareHouseLocation);
	$('#ureaInvoiceId').val(data.ureaInvoiceId);
	$('#ureaInvoiceToDetailsId').val($("#ureaFromLocation").val());
	var stockQuantity 		= Number($('#ureaStockQuantity').val());
	var pendingQuantity 	= Number($("#pendingQuantity").val());
	
	if(stockQuantity <= pendingQuantity){ // when stockQuantity is less than pending quantity it will set autometically
		$("#ureaTransferQuantity").val(stockQuantity)
	}
	validateTransferLocation();
});

function validateTransferLocation(){
	if($("#transferFromLocation").val() == $("#ureaToLocationId").val()){
		showMessage('info','Urea Transfer And Urea Required Location Can Not Be Same');
		$("#ureaFromLocation").select2("val", "");
		hideLayer();
		return false;
	}
	 return true;
}

$("#ureaTransferQuantity").keyup(function(){
	validateTransferQuantity();
});

function validateTransferQuantity(){
	var	transferQuantity 				= Number($("#ureaTransferQuantity").val());
	var	stockQuantity 					= Number($("#ureaStockQuantity").val());
	var	requiredQuantity 				= Number($("#totalRequiredQuantity").val());
	var totalSavedTransferQuantity 		= Number($("#transferQuantity").val()); 
	 
	if( transferQuantity > stockQuantity){
		  showMessage('info','Transfer Quntity Can Not Be Greater Than Stock Quantity')
		  $("#ureaTransferQuantity").val(0);
		  hideLayer();
		  return false;
	  }
	  if(transferQuantity > requiredQuantity){
		  showMessage('info','Transfer Quntity Can Not Be Greater Than Required Quantity')
		   $("#ureaTransferQuantity").val(0);
		  hideLayer();
		  return false;
	  }
	  if(transferQuantity > Number(requiredQuantity-totalSavedTransferQuantity)){
		  showMessage('info','Transfer Quntity Can Not Be Greater Than '+(requiredQuantity-totalSavedTransferQuantity)+' ')
		  $("#ureaTransferQuantity").val(0);
		  hideLayer();
		  return false;
	  }
	  return true;
}


$(document).ready(
		function($) {
			$('button[id=saveTransfer]').click(function(e) {
	
				showLayer();
					
				if(!validateTransferLocation() || !validateTransferQuantity()){
					return false;
				}
				
				if( $('#transferFromLocation').val() == "" ||  $('#transferFromLocation').val() == undefined){
					showMessage('info','Please Select Transfer Location');
					hideLayer();
					return false;
				}
				if($('#ureaTransferQuantity').val() <= 0 ){
					showMessage('info','Please Enter Transfer Quantity');
					hideLayer();
					return false;
				}
				
				var jsonObject								= new Object();
			
				var ureaTransferQuantity					= Number($('#ureaTransferQuantity').val());
				var transferQuantity						= Number($('#transferQuantity').val());
				
				jsonObject["ureaRequisitionSenderId"]		= $('#ureaRequisitionSenderId').val();
				jsonObject["ureaToLocationId"]				= $('#ureaToLocationId').val();
				jsonObject["transferedQuantity"]			= transferQuantity.toFixed(2); // transfered Quantity
				jsonObject["totalRequiredQuantity"]			= $('#totalRequiredQuantity').val(); // required Quantity
				
				jsonObject["ureaTransferId"]				= $('#ureaTransferId').val();
				jsonObject["ureaTransferFromLocationId"]	= $('#transferFromLocation').val();
				jsonObject["ureaInvoiceToDetailsId"]		= $('#ureaInvoiceToDetailsId').val();
				jsonObject["ureaInvoiceId"]					= $('#ureaInvoiceId').val();
				jsonObject["ureaRequisitionReceiverId"]		= $('#ureaRequisitionReceiverId').val();
				jsonObject["ureaInventoryTransferQuantity"]	= ureaTransferQuantity.toFixed(2);
			
				$.ajax({
					url: "saveTransfer",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.notValidUser == true){
							showMessage('info','Not A Valid User');
							hideLayer();
						}else if(data.transferFromLocationNotFound == true){
							showMessage('info','Transfer From Location Not Found');
							hideLayer();
						}else if(data.transferToLocationNotFound == true){
							showMessage('info','Transfer To Location Not Found');
							hideLayer();
						}else if(data.transferReceiveLocationNotSame == true){
							showMessage('info','Transfer And Receive Location Can Not Be Same');
							hideLayer();
						}else if(data.transferQuantityNotFound == true){
							showMessage('info','Transfer Quantity Not Found');
							hideLayer();
						}else if(data.invalidTransferQuantity == true){
							showMessage('info','Invalid Transfer Quantity');
							hideLayer();
						}else if(data.invoiceNotFound == true){
							showMessage('info','Invoice Not Found');
							hideLayer();
						}else if(data.save == true){
							showMessage('success','Save Successfully')
							location.reload();
						}else{
							location.reload();
						}
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	

function removeUreaTransferDetail(ureaTransferDetailsId,ureaInventoryTransferQuantity,ureaInvoiceToDetailsId){
	
	showLayer();
	var jsonObject								= new Object();
	
	jsonObject["ureaInvoiceId"]						= $('#ureaInvoiceId').val();
	jsonObject["ureaInvoiceToDetailsId"]			= ureaInvoiceToDetailsId;
	jsonObject["ureaTransferId"]					= $('#ureaTransferId').val();
	jsonObject["ureaTransferDetailsId"]				= ureaTransferDetailsId;
	jsonObject["ureaInventoryTransferQuantity"]		= ureaInventoryTransferQuantity;
	$.ajax({
		url: "removeTransfer",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Removed Successfully')
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function completeTransfer(ureaTransferDetailsId,ureaInventoryTransferQuantity){
	setTimeout(function(){ 	
		if(Number(ureaInventoryTransferQuantity) < 0 ){
			showMessage('info','Please Check TransferQuantity')
			hideLayer();
			return false;
		}

		//showLayer();
		var jsonObject								= new Object();

		jsonObject["ureaRequisitionSenderId"]			= $('#ureaRequisitionSenderId').val();// for validattion
		jsonObject["ureaRequisitionId"]					= $('#ureaRequisitionId').val();
		jsonObject["ureaTransferId"]					= $('#ureaTransferId').val();
		jsonObject["transferRemark"]					= $('#transferRemark').val();

		if(($("#transferQuantity").val() < $("#totalRequiredQuantity").val() ) && ( $("#pendingQuantity").val() > 0)){	
			Swal.fire({
				title: "Are you sure?",
				text: " You have pending "+$("#pendingQuantity").val()+" liters To transfer , Still you want to continue ",
				icon: 'warning',
				showCancelButton: true,
				cancelButtonText: 'NO',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				customClass:'swal-wide',
				confirmButtonText: 'Yes'
			}).then((result) => {
				if (result.value) {
					$.ajax({
						url: "completeTransfer",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {
							if(data.notValidUser == true){
								showMessage('info','Not A Valid User');
								hideLayer();
							}else{
								showMessage('success','transfer Successfully')
								location.reload();
							}
						},
						error: function (e) {
							hideLayer();
							showMessage('errors', 'Some Error Occurred!');
						}
					});
				}else{

					transferUrea();
					transferedAllQuantity = false
				}
			})
		}else{	
			Swal.fire({
				title: "Do You Want To Transfer?",
				icon: 'warning',
				showCancelButton: true,
				cancelButtonText: 'NO',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				customClass:'swal-wide',
				confirmButtonText: 'Yes'
			}).then((result) => {
				if (result.value) {
					$.ajax({
						url: "completeTransfer",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {

							if(data.notValidUser == true){
								showMessage('info','Not A Valid User');
								hideLayer();
							}else{
								showMessage('success','transfer Successfully')
								location.reload();
							}
						},
						error: function (e) {
							hideLayer();
							showMessage('errors', 'Some Error Occurred!');
						}
					});
				}else{


				}
			})
		}

	}, 300);
}

function addReceivedRejectUreaRemark(status){
	setTimeout(function(){ 	
		if(status == RECEIVED){
			$("#receivedUreaRemark").removeClass('hide');
			$("#rejectdUreaRemark").addClass('hide');
		}else if(status == REJECTED ){
			$("#rejectdUreaRemark").removeClass('hide');
			$("#receivedUreaRemark").addClass('hide');
		}
		$("#addReceivedRejectUreaRemarkModal").modal('show');
	}, 300);
}

function receivedRejcetUrea(received_reject_status){
	showLayer();
	var jsonObject								= new Object();
	 
	jsonObject["ureaRequisitionReceiverId"]			= $('#ureaRequisitionReceiverId').val();// for validatation
	jsonObject["ureaRequisitionId"]					= $('#ureaRequisitionId').val();
	jsonObject["ureaTransferId"]					= $('#ureaTransferId').val();
	jsonObject["received_reject_status"]			= received_reject_status;
	jsonObject["ureaToLocationId"]  				= $("#ureaToLocationId").val();
	jsonObject["received_RejectQuantity"]  			= $("#transferQuantity").val();
	jsonObject["remark"]  							= $("#remark").val();
	
	$.ajax({
		url: "received_Reject_Urea",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.notValidUser == true){
				showMessage('info','Not A Valid User');
				hideLayer();
			}else if(data.received == true){
				showMessage('success','Received Urea Successfully')
				location.reload();
			}else if(data.rejected == true){
				showMessage('success','Reject Urea Successfully')
				location.reload();
			}else{
				location.reload();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}