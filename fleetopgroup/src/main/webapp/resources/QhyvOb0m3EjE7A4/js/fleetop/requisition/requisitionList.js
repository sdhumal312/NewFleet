let ALL 					= 0; 
let CREATED	 				= 1; 
let APPROVED				= 3; 
let YOURTRANSFER			= 4; 
let YOURRECEIVAL			= 5; 
let ASSIGNTOYOU				= 6; 
let COMPLETE				= 7; 


$(document).ready(function() {
	showList(0,1);
});

function showList(status, pagenumber){
	if(status == ALL ){
		$('#all').addClass('active');
		$('#created').removeClass('active');
		$('#approved').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#pendingReceival').removeClass('active');
		$('#yourAssigned').removeClass('active');
		$('#completeRequisition').removeClass('active');
	}else if(status == CREATED ){
		$('#all').removeClass('active');
		$('#created').addClass('active');
		$('#approved').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#pendingReceival').removeClass('active');
		$('#yourAssigned').removeClass('active');
		$('#completeRequisition').removeClass('active');
	}else if(status == APPROVED ){
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#approved').addClass('active');
		$('#pendingReceival').removeClass('active');
		$('#yourAssigned').removeClass('active');
		$('#completeRequisition').removeClass('active');
	}else if (status == YOURTRANSFER){
		$('#pendingTransfer').addClass('active');
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#approved').removeClass('active');
		$('#pendingReceival').removeClass('active');
		$('#yourAssigned').removeClass('active');
		$('#completeRequisition').removeClass('active');
	}else if(status == YOURRECEIVAL){
		$('#pendingReceival').addClass('active');
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#approved').removeClass('active');
		$('#yourAssigned').removeClass('active');
		$('#completeRequisition').removeClass('active');
	}else if(status == ASSIGNTOYOU){
		$('#yourAssigned').addClass('active');
		$('#pendingReceival').removeClass('active');
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#approved').removeClass('active');	
		$('#completeRequisition').removeClass('active');	
	}else if(status == COMPLETE){
		$('#yourAssigned').removeClass('active');
		$('#pendingReceival').removeClass('active');
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#pendingTransfer').removeClass('active');
		$('#approved').removeClass('active');	
		$('#completeRequisition').addClass('active');
	}

	var jsonObject					= new Object();
	
	jsonObject["status"]			= status;
	jsonObject["pagenumber"]		= pagenumber;
	jsonObject["companyId"]			= $("#companyId").val();
	
	showLayer();
	$.ajax({
		url: "getRequisitionList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setrequisitionList(data,status);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setrequisitionList(data,status){
	
	$("#requisitionTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	var requisitionList = data.requisitionList;
	
	if(requisitionList != undefined || requisitionList != null){
		var actionBoolean = false;
		for(var index = 0 ; index < requisitionList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'><a href='showRequisition?requisitionId="+requisitionList[index].requisitionId+"' target='_blank'>R-"+requisitionList[index].requisitionNum+"</a></td>");
			//columnArray.push("<td class='fit'ar>  "+ requisitionList[index].requisitionStatusName  +"</td>");
			columnArray.push("<td class='fit'ar> "+ requisitionList[index].locationName  +"</td>");
			columnArray.push("<td class='fit'ar>  "+ requisitionList[index].requireOnStr  +"</td>");
			columnArray.push("<td class='fit'ar>"+ requisitionList[index].refNumber  +"</td>");
			columnArray.push("<td class='fit'ar>  "+ requisitionList[index].assignToName  +"</td>");
			
			if((status == ALL || status == CREATED || status == ASSIGNTOYOU) && ($('#allowDelete').val() == true || $('#allowDelete').val() == 'true')){
				actionBoolean = true;
				columnArray.push("<td class='fit'ar>  <a href='javascript:void(0)' style='color:red' class='fa fa-trash' onclick='deleteRequisition("+requisitionList[index].requisitionId+")'></a></td>");
			}
			$('#requisitionTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		if(actionBoolean){
			$('#actionId').show()
		}else{
			$('#actionId').hide()
		}
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	$("#allCount").html(data.allCount);
	$("#createdCount").html(data.createdCount);
	$("#approvedCount").html(data.approvedCount);
	$("#assignedToYouCount").html(data.assignedToYouCount);
	$("#yourPendingTCount").html(data.yourPendingTCount);
	$("#yourPendingRCount").html(data.yourPendingRCount);
	$("#completeCount").html(data.completeCount);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+',1,'+(data.invoiceStatus)+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+data.statusId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
	}

function deleteRequisition(requisitionId){
	if(confirm('Are You Sure, Do You Want To Delete Requisition! ')){
	var jsonObject								= new Object();
	jsonObject["requisitionId"]		= requisitionId;
	showLayer();
	$.ajax({
		url: "deleteRequisitionById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.approvedFail != undefined && data.approvedFail == true){
				showMessage('info', 'Can not Delete Approved Requisition !!');
				hideLayer();
			}else if(data.authFail != undefined && data.authFail){
				showMessage('info', 'unAuthorised User !');
				hideLayer();
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
}

$(document).ready(function(){
	$("#searchByNumber").on('keypress', function (e) {
		if(e.which == 13) {
			searchReqByNumber();
		}
	});
})
function searchReqByNumber(){

	var jsonObject									= new Object();
	jsonObject["requisitionNumber"]		= $("#searchByNumber").val();
	jsonObject["companyId"]				= $("#companyId").val();

	if( $("#searchByNumber").val().trim() == "" ){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Requisition Number !');
		return false;
	}
	showLayer();
	$.ajax({
		url: "searchRequisitionByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.requisitionId != undefined ){
				window.location.replace("showRequisition?requisitionId="+data.requisitionId+"");
			}else{
				showMessage('info', 'Please Enter valid Requisition Number!');
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}
