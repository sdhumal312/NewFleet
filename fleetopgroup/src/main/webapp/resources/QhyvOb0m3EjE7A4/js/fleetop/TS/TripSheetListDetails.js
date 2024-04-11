$(document).ready(function() {
	showLayer();
	if($('#loadTypeId').val() != undefined && $('#loadTypeId').val().trim() != ''){
		var loadTypeId = Number($('#loadTypeId').val());
		if(loadTypeId == 1){
			getTodayTripSheetList(getCurrentDate());
		}else if(loadTypeId == 2){
			getDispatchTripSheetList(1);
		}else if(loadTypeId == 3){
			getManageTripSheetList(1);
		}else if(loadTypeId == 4){
			getAdvCloseTripSheetList(1);
		}else if(loadTypeId == 5){
			getTripSheetAccountList(1);
		}else if(loadTypeId == 6){
			getTripSheetAccountCloseList(1);
		}else {
				var vid = getUrlParameter('vid');
			console.log('dasddadad ', vid);
			if(vid != undefined && vid > 0)
				checkUrlParameterForVid(vid);
				getTodayTripSheetList(getCurrentDate());
		}
	}else{
		var vid = getUrlParameter('vid');
			console.log('dasddadad ', vid);
			if(vid != undefined && vid > 0)
				checkUrlParameterForVid(vid);
			else
				getTodayTripSheetList(getCurrentDate());
	}
});

function getTodayTripSheetList(selectedDate){

	var jsonObject			= new Object();
	jsonObject["selectedDate"] 	=  selectedDate;
	
	showLayer();
	
	$.ajax({
             url: "getToDaysTripSheetList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	setTripSheetTableData(data);
            	$('#loadTypeId').val(1);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}

function getDispatchTripSheetList(pageNumber){
	var jsonObject			= new Object();
	jsonObject["pageNumber"] 	=  pageNumber;
	showLayer();
	
	$.ajax({
             url: "getDispatchTripSheetList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	setDispatchTripSheetTableData(data);
            	$('#loadTypeId').val(2);
            	hideLayer();
             },
             error: function (e) {
             
             	 hideLayer();
             	
				if(e.responseText.includes("<title>FleeTop Login Page</title>")){
					showMessage('errors', 'Your session is expired , Please login again !');
				var strWindowFeatures = "location=yes,height=570,width=720,scrollbars=yes,status=yes";
				var URL = "login.html";
				var win = window.open(URL, "_blank", strWindowFeatures);
				
				}else{
            	 	showMessage('errors', 'Some error occured!')
				}
				
            	 hideLayer();
             }
	});
}

function getManageTripSheetList(pageNumber){
	var jsonObject			= new Object();
	jsonObject["pageNumber"] 	=  pageNumber;
	showLayer();
	
	$.ajax({
             url: "getManageTripSheetList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setManageTripSheetTableData(data);
            	 $('#loadTypeId').val(3);
            	hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function getAdvCloseTripSheetList(pageNumber){
	var jsonObject			= new Object();
	jsonObject["pageNumber"] 	=  pageNumber;
	showLayer();
	
	$.ajax({
             url: "getAdvCloseTripSheetList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setAdvCloseTripSheetList(data);
            	 $('#loadTypeId').val(4);
            	hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function getTripSheetAccountList(pageNumber){
	var jsonObject			= new Object();
	jsonObject["pageNumber"] 	=  pageNumber;
	showLayer();
	
	$.ajax({
             url: "getTripSheetAccountList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setTripSheetAccountList(data);
            	 $('#loadTypeId').val(5);
            	hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function getTripSheetAccountCloseList(pageNumber){
	var jsonObject			= new Object();
	jsonObject["pageNumber"] 	=  pageNumber;
	showLayer();
	
	$.ajax({
             url: "getTripSheetAccountCloseList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setTripSheetAccounClosetList(data);
            	 $('#loadTypeId').val(6);
            	hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function setTripSheetTableData(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	document.getElementById('todaysTrip').className = "active";
	document.getElementById('dispatchTrip').className = "tab-link";
	document.getElementById('manageTrip').className = "tab-link";
	document.getElementById('advCloseTrip').className = "tab-link";
	document.getElementById('tripAccList').className = "tab-link";
	document.getElementById('tripAccClose').className = "tab-link";
	
	if(data.tripSheet != undefined && data.tripSheet.length > 0){
		
		var trHead =' <tr>'
			+'<th>TripSheet Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">Advance</th>'
			+'<th class="fir ar">Booking-Ref</th>'
			+'<th class="fir ar">Status</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		var totalAdvance = 0;
		for(var i = 0; i< data.tripSheet.length; i++){
			
			var tripSheet = data.tripSheet[i];
			var statusclass;
			var tripUrl;
			
			totalAdvance += Number(tripSheet.tripTotalAdvance);
			
			if(tripSheet.tripStutesId == 1){
				statusclass = "label label-pill label-warning";
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				statusclass = "label label-pill label-danger";
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 3){
				statusclass = "label label-pill label-success";
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				statusclass = "label label-pill label-info";
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
			
			if($('#showTripdateWithTime').val() == true || $('#showTripdateWithTime').val()== 'true'){
				tr	+= '<td>'+tripSheet.dispatchedByTime+'</td>';
			}else{
				tr +='<td>'+tripSheet.tripOpenDate+'</td>';
			}
				tr += '<td>'+tripSheet.tripTotalAdvance+'</td>'
				+'<td>'+tripSheet.tripBookref+'</td>'
				+'<td><span class="'+statusclass+'">'+tripSheet.tripStutes+'</span></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		
		var tr2 = '<tr><td colspan="6" style="text-align:right;" class="text-right"><b>Total Advance : '+totalAdvance.toFixed(2)+'</b></td><td colspan="2"></td></tr>';
		$('#TripSheetTableBody').append(tr2);
	}
}
function setDispatchTripSheetTableData(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();
	
	document.getElementById('todaysTrip').className = "tab-link";
	document.getElementById('dispatchTrip').className = "active";
	document.getElementById('manageTrip').className = "tab-link";
	document.getElementById('advCloseTrip').className = "tab-link";
	document.getElementById('tripAccList').className = "tab-link";
	document.getElementById('tripAccClose').className = "tab-link";
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	if(data.TripSheet != undefined && data.TripSheet.length > 0){
		$('#dispatchCount').html(data.TripDispatch);
		var trHead ='<tr>'
			+'<th>TripSheet Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">Booking-Ref</th>'
			+'<th class="fir ar">Dispatch</th>'
			+'<th class="fir ar">Actions</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		
		for(var i = 0; i< data.TripSheet.length; i++){
			var tripSheet = data.TripSheet[i];
			var tripUrl;
			if(tripSheet.tripStutesId == 1){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
				if($('#showTripdateWithTime').val()==true || $('#showTripdateWithTime').val()=='true'){
					tr += '<td>'+tripSheet.dispatchedByTime+'</td>';
				}else{
					tr += '<td>'+tripSheet.tripOpenDate+'</td>';
				}
				tr +='<td>'+tripSheet.tripBookref+'</td>'
				+'<td><a class="btn btn-success btn-sm" href="dispatchTripSheet.in?ID='+tripSheet.tripSheetID+'"><span class="fa fa-rocket"> </span>Dispatch</a></td>'
				+'<td><a class="btn btn-danger btn-sm" onclick="deleteTripSheet('+tripSheet.tripSheetID+');" href="#" class="confirmation"><span class="fa fa-trash"></span> Delete</a></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		if(data.deploymentLog != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getDispatchTripSheetList(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getDispatchTripSheetList('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getDispatchTripSheetList('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getDispatchTripSheetList('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getDispatchTripSheetList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getDispatchTripSheetList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
	 }
	}
}

function setManageTripSheetTableData(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();
	
	document.getElementById('todaysTrip').className = "tab-link";
	document.getElementById('dispatchTrip').className = "tab-link";
	document.getElementById('manageTrip').className = "active";
	document.getElementById('advCloseTrip').className = "tab-link";
	document.getElementById('tripAccList').className = "tab-link";
	document.getElementById('tripAccClose').className = "tab-link";
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	if(data.TripSheet != undefined && data.TripSheet.length > 0){
		$('#manageCount').html(data.TripManage);
		var trHead ='<tr>'
			+'<th>TripSheet Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">Booking-Ref</th>'
			+'<th class="fir ar">Advance</th>'
			+'<th class="fir ar">Expense</th>'
			+'<th class="fir ar">Income</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		
		for(var i = 0; i< data.TripSheet.length; i++){
			var tripSheet = data.TripSheet[i];
			var tripUrl;
			if(tripSheet.tripStutesId == 1){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
			if($('#showTripdateWithTime').val()==true || $('#showTripdateWithTime').val()=='true'){
				tr += '<td>'+tripSheet.dispatchedByTime+'</td>';
			}else{
				tr += '<td>'+tripSheet.tripOpenDate+'</td>'; 
			}
			 tr += '<td>'+tripSheet.tripBookref+'</td>'
				+'<td><a class="btn btn-success btn-sm" href="addAdvance.in?tripSheetID='+tripSheet.tripSheetID+'"><span class="fa fa-plus"> </span>Advance</a></td>'
				+'<td><a class="btn btn-warning btn-sm" href="addExpense.in?tripSheetID='+tripSheet.tripSheetID+'"><span class="fa fa-plus"> </span>Expense</a></td>'
				+'<td><a class="btn btn-info btn-sm" href="addIncome.in?tripSheetID='+tripSheet.tripSheetID+'"><span class="fa fa-plus"> </span>Income</a></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		if(data.deploymentLog != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getManageTripSheetList(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getManageTripSheetList('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getManageTripSheetList('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getManageTripSheetList('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getManageTripSheetList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getManageTripSheetList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
	 }	
	}
}

function setAdvCloseTripSheetList(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();
	
	document.getElementById('todaysTrip').className = "tab-link";
	document.getElementById('dispatchTrip').className = "tab-link";
	document.getElementById('manageTrip').className = "tab-link";
	document.getElementById('advCloseTrip').className = "active";
	document.getElementById('tripAccList').className = "tab-link";
	document.getElementById('tripAccClose').className = "tab-link";
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	if(data.TripSheet != undefined && data.TripSheet.length > 0){
		$('#advanceCount').html(data.TripClose);
		var trHead ='<tr>'
			+'<th>TripSheet Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">Advance</th>'
			+'<th class="fir ar">Expense</th>'
			+'<th class="fir ar">Income</th>'
			+'<th class="fir ar">Booking-Ref</th>'
			+'<th class="fir ar">Close</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		
		for(var i = 0; i< data.TripSheet.length; i++){
			var tripSheet = data.TripSheet[i];
			var tripUrl;
			if(tripSheet.tripStutesId == 1){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var closeUrl = "addCloseTripsheet.in?tripSheetID="+tripSheet.tripSheetID;
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
				if($('#showTripdateWithTime').val()==true || $('#showTripdateWithTime').val()=='true'){
					tr +='<td>'+tripSheet.dispatchedByTime+'</td>';
				}else{
					tr += '<td>'+tripSheet.tripOpenDate+'</td>';
				}
			tr +='<td>'+(Number(tripSheet.tripTotalAdvance)).toFixed(2)+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalexpense)).toFixed(2)+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalincome)).toFixed(2)+'</td>'
				+'<td>'+tripSheet.tripBookref+'</td>'
				+'<td><a class="btn btn-success btn-sm" href="'+closeUrl+'"><span class="fa fa-times"> Close Trip</span></a></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		if(data.deploymentLog != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getAdvCloseTripSheetList(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getAdvCloseTripSheetList('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getAdvCloseTripSheetList('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getAdvCloseTripSheetList('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getAdvCloseTripSheetList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getAdvCloseTripSheetList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
		}
	}
}

function setTripSheetAccountList(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();
	
	$("#TripSheetTable").addClass("table table-bordered table-striped");

	document.getElementById('todaysTrip').className = "tab-link";
	document.getElementById('dispatchTrip').className = "tab-link";
	document.getElementById('manageTrip').className = "tab-link";
	document.getElementById('advCloseTrip').className = "tab-link";
	document.getElementById('tripAccList').className = "active";
	document.getElementById('tripAccClose').className = "tab-link";
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	if(data.TripSheet != undefined && data.TripSheet.length > 0){
		$('#paymentCount').html(data.TripSheetCount);
		var trHead ='<tr>'
			+'<th>TS-Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">B-Ref:</th>'
			+'<th class="fir ar">Expense</th>'
			+'<th class="fir ar">Income</th>'
			+'<th class="fir ar">Status</th>';
			if($('#hideAdvanceAndExpenseAddIfAccClose').val() != 'true'){
			trHead+= '<th class="fir ar">M-Expense</th>'
			+'<th class="fir ar">M-Income</th>';
			}
			trHead+= '<th class="fir ar">Close A/C</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		
		for(var i = 0; i< data.TripSheet.length; i++){
			var tripSheet = data.TripSheet[i];
			var tripUrl;
			if(tripSheet.tripStutesId == 1){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var expenseUrl  	= "addExpense.in?tripSheetID="+tripSheet.tripSheetID;
			var incomeUrl   	= "addIncome.in?tripSheetID="+tripSheet.tripSheetID;
			var accountCloseUrl = "addcloseAccountTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
			if($('#showTripdateWithTime').val()==true || $('#showTripdateWithTime').val()=='true'){
				tr += '<td>'+tripSheet.dispatchedByTime+'</td>';
			}else{
				tr += '<td>'+tripSheet.tripOpenDate+'</td>';
			}
				tr += '<td>'+tripSheet.tripBookref+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalexpense)).toFixed(2)+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalincome)).toFixed(2)+'</td>'
				+'<td><span class="label label-pill label-success">'+tripSheet.tripStutes+'</span></td>';
				if($('#hideAdvanceAndExpenseAddIfAccClose').val() != 'true'){
				tr+='<td><a class="btn btn-warning btn-sm" href="'+expenseUrl+'"><span class="fa fa-plus"> Expense</a></td>'
				+'<td><a class="btn btn-info btn-sm" href="'+incomeUrl+'"><span class="fa fa-plus"> Income</a></td>';
				}
				tr+= '<td><a class="btn btn-success btn-sm" href="'+accountCloseUrl+'"><span class="fa fa-times"> Close Account</span></a></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		if(data.deploymentLog != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountList(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountList('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getTripSheetAccountList('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountList('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
		}
	}
}

function setTripSheetAccounClosetList(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TripSheetTableBody tr").remove();
	$("#tableHeader tr").remove();
	$("#navigationBar").empty();

	document.getElementById('todaysTrip').className = "tab-link";
	document.getElementById('dispatchTrip').className = "tab-link";
	document.getElementById('manageTrip').className = "tab-link";
	document.getElementById('advCloseTrip').className = "tab-link";
	document.getElementById('tripAccList').className = "tab-link";
	document.getElementById('tripAccClose').className = "active";
	
	$('#dispatchCount').html('');
	$('#advanceCount').html('');
	$('#todayCount').html('');
	$('#paymentCount').html('');
	$('#paymentCount').html('');
	$('#accountCount').html('');
	$('#manageCount').html('');
	
	if(data.TripSheet != undefined && data.TripSheet.length > 0){
		$('#accountCount').html(data.TripSheetCount);
		var trHead ='<tr>'
			+'<th>TS-Id</th>'
			+'<th>Vehicle</th>'
			+'<th class="fir ar">Group</th>'
			+'<th>Route</th>'
			+'<th>Trip Date</th>'
			+'<th class="fir ar">B-Ref:</th>'
			+'<th class="fir ar">Expense</th>'
			+'<th class="fir ar">Income</th>'
			+'<th class="fir ar">Status</th>'
			+'</tr>';
		
		$('#tableHeader').append(trHead);
		
		for(var i = 0; i< data.TripSheet.length; i++){
			var tripSheet = data.TripSheet[i];
			var tripUrl;
			if(tripSheet.tripStutesId == 1){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else if(tripSheet.tripStutesId == 2){
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}else{
				tripUrl = "showTripSheet.in?tripSheetID="+tripSheet.tripSheetID;
			}
			var tr =' <tr data-object-id="">'
				+'<td><a href="'+tripUrl+'">TS-'+tripSheet.tripSheetNumber+'</a></td>'
				+'<td>'+tripSheet.vehicle_registration+'</td>'
				+'<td>'+tripSheet.vehicle_Group+'</td>'
				+'<td>'+tripSheet.routeName+'</td>';
			if($('#showTripdateWithTime').val()==true || $('#showTripdateWithTime').val()=='true'){
				
				tr += '<td>'+tripSheet.dispatchedByTime+'</td>';
			}else{
				tr += '<td>'+tripSheet.tripOpenDate+'</td>';
			}
				tr += '<td>'+tripSheet.tripBookref+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalexpense)).toFixed(2)+'</td>'
				+'<td>'+(Number(tripSheet.tripTotalincome)).toFixed(2)+'</td>'
				+'<td><span class="label label-pill label-success">'+tripSheet.tripStutes+'</span></td>'
				+'</tr>';
			$('#TripSheetTableBody').append(tr);
		}
		if(data.deploymentLog != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountCloseList(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountCloseList('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getTripSheetAccountCloseList('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountCloseList('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountCloseList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getTripSheetAccountCloseList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
	  }
	}
}



function searchTripSheetByDate(){
	console.log($('#SearchTripDate').val());
	if($('#SearchTripDate').val() == null || $('#SearchTripDate').val().trim() == ''){
		$('#SearchTripDate').focus();
		showMessage('info', 'Please select search date !');
		return false;
	}
	getTodayTripSheetList($('#SearchTripDate').val());
	$('#CloseTripCollection').modal('hide');
}
