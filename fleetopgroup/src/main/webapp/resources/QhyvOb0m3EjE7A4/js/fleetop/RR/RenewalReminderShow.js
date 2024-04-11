function addEmail() {
	if(validateEmail()) {
		showLayer();
		/*****for updation following id will use****/
		var configId 	= $("#configId").val()
		console.log("configId>>>",configId)
		var jsonObject					= new Object();

		jsonObject["emailId"]			= $("#emailId").val();
		jsonObject["configurationId"]	= configId;

		if(configId > 0){
			updateEmail(jsonObject);
		} else {
			saveEmail(jsonObject);
		}
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function saveEmail(jsonObject){
	$.ajax({
		url: "/fleetopgroup/RenewalReminderWS/saveEmailId.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage("success", "Renewal Reminder Saved.")
		},
		error: function (e) {
			console.log("Error",e);
		}
	});
}

function updateEmail(jsonObject){
	$.ajax({
		url: "/fleetopgroup/RenewalReminderWS/update_EmailId.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data>>>>>>>>>",data);
			checkemail(data);
			hideLayer();

		},
		error: function (e) {
			console.log("Error",e);
		}
	});
}

function email(){
	$('#configureEmail').modal('show');
	var jsonObject		= new Object();

	$.ajax({
		url: "/fleetopgroup/RenewalReminderWS/get_All_Email.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			checkemail(data);
			hideLayer();

		},
		error: function (e) {
			console.log("Error",e);
		}
	});
}
function checkemail(data) {
	var email			= null;
	var input			= null;
	var emailConfigId	= 0;
	if(data.Email != undefined) {
		email	= data.Email;
		for(var i = 0; i < email.length; i++ ) {
			emailConfigId	= email[i].configurationId;
			if (input == null){
				input	 = email[i].emailIds;
			} else {
				input	 = input +","+email[i].emailIds;
			}
		}
		$('#emailId').val(input);
		/***************	following id used for updation***/
		$('#configId').val(emailConfigId);
	}
}

function validateEmail() {
	if($("#emailId").val() <= 0) {
		showMessage('info','Select E-mail Id');
		return false;
	}
	return true;
}



