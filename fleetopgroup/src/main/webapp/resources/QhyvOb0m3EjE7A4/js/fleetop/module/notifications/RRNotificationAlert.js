var dueSoonStatus = 7;
var overDueStatus = 8;
var pageNumber	  = 1;
$(document).ready(function() {
	
	getAllRenewalRemiderListByStatus(pageNumber,dueSoonStatus);
	
	$("#dueSoon").click(function(){
		$("#overDue").removeClass("active");
		$("#dueSoon").addClass("active");
		getAllRenewalRemiderListByStatus(pageNumber,dueSoonStatus);
	}); 
	
	$("#overDue").click(function(){
		$("#dueSoon").removeClass("active");
		$("#overDue").addClass("active");
		getAllRenewalRemiderListByStatus(pageNumber,overDueStatus);
	}); 
	
});

function getAllRenewalRemiderListByStatus(pageNumber,status){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;
	jsonObject["status"]			= status;
	
	$.ajax({
		url: "getAllRenewalRemiderListByStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllRRList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setAllRRList (data){
	$("#rrTable").empty();
	/*document.getElementById('All').className = "active";
	document.getElementById('AllStock').className = "tab-link";
	document.getElementById('AllSent').className = "tab-link";*/
	var rrList	=	data.rrList;
	var status	=	data.status;

	if(rrList != undefined){
		
		for(var index = 0 ; index < rrList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4><a href=showRenewalReminder?renewal_id="+rrList[index].renewal_id+" target='_blank'>RR-"+rrList[index].renewal_R_Number+"</a></td>");
			columnArray.push("<td class='fit ar'><a href=showVehicle?vid="+rrList[index].vid+" target='_blank'>"+rrList[index].vehicle_registration+"</a></td>");
			columnArray.push("<td class='fit ar'>" + rrList[index].vehicleGroup +"</td>");
			if(rrList[index].renewal_dueRemDate == "Due Soon"){
				columnArray.push("<td class='fit ar'><span class='label label-default label-warning' style='font-size: 12px;'>"+rrList[index].renewal_dueRemDate+ " </span> &nbsp &nbsp " + rrList[index].renewal_type +"</td>");
			}else if(rrList[index].renewal_dueRemDate == "Overdue"){
				columnArray.push("<td class='fit ar'><span class='label label-default label-danger' style='font-size: 12px;'>"+rrList[index].renewal_dueRemDate+ " </span> &nbsp &nbsp" + rrList[index].renewal_type +"</td>");
			}
			columnArray.push("<td class='fit ar'>" + rrList[index].renewal_from +"</td>");
			if(rrList[index].renewal_dueRemDate == "Due Soon"){
				columnArray.push("<td class='fit ar'>" + rrList[index].renewal_to +"<br> <i class='fa fa-calendar-check-o'></i><span style='color: #06b4ff ;'> "+rrList[index].renewal_dueDifference+"</sapn> </td>");
			}else{
				columnArray.push("<td class='fit ar'>" + rrList[index].renewal_to +"<br> <i class='fa fa-calendar-check-o'></i><span style='color: red;'> "+rrList[index].renewal_dueDifference+"</sapn> </td>");
			}
			/*columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deletePickAndDropLocation("+tyreList[index].pickAndDropLocationId+");'><span class='fa fa-trash'></span> Delete</a></td>");*/
			
			$('#rrTable').append("<tr id='penaltyID"+rrList[index].renewal_id+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		columnArray = [];
		
		if(status == dueSoonStatus){
			$("#countId").html("Total Due Soon Count");
		}else{
			$("#countId").html("Total OverDue Count");
		}
		$("#totalRRCount").html(data.deploymentLog.totalElements);
		
	}else{
		showMessage('info','No Record Found!')
	}
	
	
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getAllRenewalRemiderListByStatus(1,'+status+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getAllRenewalRemiderListByStatus('+(data.currentIndex - 1)+','+status+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getAllRenewalRemiderListByStatus('+i+','+status+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getAllRenewalRemiderListByStatus('+i+','+status+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getAllRenewalRemiderListByStatus('+(data.currentIndex + 1)+','+status+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getAllRenewalRemiderListByStatus('+(data.deploymentLog.totalPages)+','+status+')">&gt;&gt;</a></li>');			
		}
	}
	
	hideLayer();
}


