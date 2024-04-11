function addEmail() {
	if(validateEmail()) {
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["serviceId"]				= $("#serviceId").val();
		jsonObject["serviceNumber"]			= $("#serviceNum").val();
		jsonObject["emailId"]				= $("#emailId").val();
		jsonObject["vehicleId"]				= $("#vehicleId").val();
		jsonObject["serviceTask"]			= $("#serviceTask").val();
		jsonObject["serviceSubTask"]		= $("#serviceSubTask").val();
		jsonObject["serviceDate"]			= $("#serviceDate").val();
		jsonObject["alertBeforeDate"]		= $("#alertBeforeDate").val();
		jsonObject["alertAfterDate"]		= $("#alertAfterDate").val();

		$.ajax({
			url: "serviceReminderWS/saveEmail.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage("success", "Service Reminder Saved.")
			},
			error: function (e) {
				console.log("Error");
			}
		});
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function validateEmail() {
	
	if($("#emailId").val() <= 0) {
		showMessage('info','Select E-mail Id');
		return false;
	}

	if($("#alertBeforeDate").val() <= 0) {
		showMessage('info','Select Alert Before Date');
		return false;
	}

	return true;
}

function showAlertAfterDate(obj) {
	if(obj.checked){
		$("#alertdate").removeClass("hide");
	} else {
		
		$("#alertdate").addClass("hide");
	}	
}

function email(){
	
	var jsonObject					= new Object();
	
	jsonObject["serviceId"]			= $("#serviceId").val();
	
	
	$.ajax({
		url: "serviceReminderWS/checkEmail.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data);
			checkemail(data);
			 hideLayer();
			 
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
	$('#configureEmail').modal('show');
	
}

function checkemail(data) {
	var email	= null;
	
	if(data.Email != undefined) {
		
		email	= data.Email;	
		var emailId="";
		var alertBeforeValues =0;
		var alertAfterValues  =0;
		var allBefore = new Array();
		var allAfter  = new Array();
		for(var i = 0; i < email.length; i++ ) {
			
			emailId			  = email[i].emailId;
			if (email[i].alertBeforeValues != null){
			alertBeforeValues = email[i].alertBeforeValues;
			allBefore[i]=alertBeforeValues;
			}
			else if (email[i].alertAfterValues != null){
			alertAfterValues = email[i].alertAfterValues;
			allAfter[i]=alertAfterValues;	
			}
			
			 
	}
		$('#emailId').val(emailId);
		$('#alertBeforeDate').val(allBefore);
		$('#alertAfterDate').val(allAfter);
}
}

function addSms() {
	if(validateSms()) {
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["serviceId"]				= $("#serviceId1").val();
		jsonObject["serviceNumber"]			= $("#serviceNumber1").val();
		jsonObject["vehicleId"]				= $("#vehicleId1").val();
		jsonObject["serviceTask"]			= $("#serviceTask1").val();
		jsonObject["serviceSubTask"]		= $("#serviceSubTask1").val();
		jsonObject["serviceDate"]			= $("#serviceDate1").val();
		jsonObject["alertBeforeDate"]		= $("#alertBeforeDate1").val();
		jsonObject["alertAfterDate"]		= $("#alertAfterDate3").val();
		jsonObject["mobileNumber"]			= $("#mobileNumber").val();
		
		

		$.ajax({
			url: "serviceReminderWS/saveSms.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage("success", "Service Reminder Saved.")
			},
			error: function (e) {
				console.log("Error");
			}
		});
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function validateSms() {
	

	if($("#alertBeforeDate1").val() <= 0) {
		showMessage('info','Select Alert Before Date');
		return false;
	}

	if($("#mobileNumber").val() <= 0) {
		showMessage('info','Select Mobile Number');
		return false;
	}
	return true;
}

function Sms(){
	
	var jsonObject					= new Object();
	
	jsonObject["serviceId"]			= $("#serviceId").val();
	
	console.log("obj",jsonObject["serviceId"]);
	
	$.ajax({
		url: "serviceReminderWS/checkSms.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			checkSms(data);
			 hideLayer();
			 
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
	$('#configureSms').modal('show');
	
}

function checkSms(data) {
	var sms	= null;
	
	if(data.SMS != undefined) {
		
		sms	= data.SMS;	
		var mob="";
		var alertBeforeValues =0;
		var alertAfterValues  =0;
		var allBefore = new Array();
		var allAfter  = new Array();
		for(var i = 0; i < sms.length; i++ ) {
			
			mob			  = sms[i].mobileNumber;
			if (sms[i].alertBeforeValues != null){
			alertBeforeValues = sms[i].alertBeforeValues;
			allBefore[i]=alertBeforeValues;
			}
			else if (sms[i].alertAfterValues != null){
			alertAfterValues = sms[i].alertAfterValues;
			allAfter[i]=alertAfterValues;	
			}
			
			 
	}
		$('#mobileNumber').val(mob);
		$('#alertBeforeDate1').val(allBefore);
		$('#alertAfterDate3').val(allAfter);
}
}


function showAlertAfterDate1(obj) {
	if(obj.checked){
		$("#alertdate1").removeClass("hide");
	} else {
		
		$("#alertdate1").addClass("hide");
	}	
}

$( document ).ready(function() {
    var workorder=$('#workorder').val();
    if(workorder != null && workorder != undefined){
    	$("#create").remove();
    	
    }
   
});

