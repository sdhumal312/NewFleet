var ALL 							= 0; 
var CREATED	 						= 1; 
var SENT_TO_REPAIR 					= 2; 
var COMPLETE 						= 3; 

$(document).ready(function(){
	showList(0,1);
})
function showList(statusId,pagenumber){
	
	switch (statusId) {
	case ALL:
		$('#all').addClass('active');
		$('#created').removeClass('active');
		$('#sentToRepair').removeClass('active');
		$('#inProcess').removeClass('active');
		$('#complete').removeClass('active');
		break;
	case CREATED:
		$('#all').removeClass('active');
		$('#created').addClass('active');
		$('#sentToRepair').removeClass('active');
		$('#inProcess').removeClass('active');
		$('#complete').removeClass('active');
		break;
	case SENT_TO_REPAIR:
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#sentToRepair').addClass('active');
		$('#inProcess').removeClass('active');
		$('#complete').removeClass('active');
		
		break;
	case COMPLETE:
		$('#all').removeClass('active');
		$('#created').removeClass('active');
		$('#sentToRepair').removeClass('active');
		$('#inProcess').removeClass('active');
		$('#complete').addClass('active');
		
		break;

	default:
		$('#all').addClass('active');
		$('#created').removeClass('active');
		$('#sentToRepair').removeClass('active');
		$('#inProcess').removeClass('active');
		$('#complete').removeClass('active');
		break;
	}
	
	var jsonObject					= new Object();
	
	console.log("pagenumber",pagenumber)
	jsonObject["statusId"] 			= statusId;
	jsonObject["pagenumber"]		= pagenumber;
	jsonObject["companyId"]			= $("#companyId").val();
	
	showLayer();
	$.ajax({
		url: "getRepairStockList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRepairStockList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setRepairStockList(data){
	console.log("data",data)
	$("#repairStockTable").empty();
	$('#countDiv').show();
	var repairStockList = data.repairStockList;
	
	if(repairStockList != undefined || repairStockList != null){
		for(var index = 0 ; index < repairStockList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'><a href='showRepairInvoice?repairStockId="+repairStockList[index].repairStockId+"' target='_blank'> "+repairStockList[index].repairNumberStr+"</a></td>");
			columnArray.push("<td class='fit'ar>  "+ repairStockList[index].repairType  +"</td>");
			columnArray.push("<td class='fit'ar>  "+ repairStockList[index].repairWorkshop  +"</td>");
			columnArray.push("<td class='fit'ar> "+ repairStockList[index].creationDate  +"</td>");
			columnArray.push("<td class='fit'ar>  "+ repairStockList[index].dateOfSentStr  +"</td>");
			columnArray.push("<td class='fit'ar>"+ repairStockList[index].completedDateStr  +"</td>");
			columnArray.push("<td class='fit'ar>  "+ repairStockList[index].lastModifiedBy  +"</td>");
			if(repairStockList[index].repairStatusId == 1 ){
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a  href='editRepairInvoice?repairStockId="+repairStockList[index].repairStockId+"'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' style='color:red'  class='confirmation' onclick='deleteRepairInvoice("+repairStockList[index].repairStockId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			}else if(repairStockList[index].repairStatusId == 2 ){
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><small class='label label-warning'>"+repairStockList[index].repairStatus+"</small></td>");
			}else{
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><small class='label label-success'>"+repairStockList[index].repairStatus+"</small></td>");
			}
			
			$('#repairStockTable').append("<tr id='penaltyID"+repairStockList[index].repairType+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	$("#allCount").html(data.allCount);
	$("#createdCount").html(data.createdCount);
	$("#sentToRepairCount").html(data.sentToRepairCount);
	$("#inProcessCount").html(data.inProcessCount);
	$("#completeCount").html(data.completeCount);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+',1)">&lt;&lt;</a></li>');		
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
function deleteRepairInvoice(repairStockId){
	if(confirm('Are You Sure, Do You Want To Delete Repair Invoice!')){
		var jsonObject								= new Object();
		jsonObject["repairStockId"]					= repairStockId;
		jsonObject["companyId"]						= $("#companyId").val();
		jsonObject["userId"]						= $("#userId").val();
		
		showLayer();
		$.ajax({
			url: "deleteRepairInvoice",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage('success','Deleted Successfully !')
				location.reload();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
}

function searchRepairByNumber(){

	var jsonObject									= new Object();
	jsonObject["repairNumber"]					= $("#searchByNumber").val();
	jsonObject["companyId"]							= $("#companyId").val();

	if( $("#repairNumber").val() == ""){
		$('#repairNumber').focus();
		showMessage('errors', 'Please Enter Valid Repair Stock Number !');
		return false;
	}
	console.log("jsonObject",jsonObject)

	showLayer();
	$.ajax({
		url: "searchRepairByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)

			if(data.repairStockId != undefined ){
				window.location.replace("showRepairInvoice?repairStockId="+data.repairStockId+"");
			}else{
				showMessage('info', 'Please Enter valid Repair Stock Number!');
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}