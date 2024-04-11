function disableDisableTwoFactor(status, userprofile_id){
	 $('#editTwoFactor').hide();
	showLayer();
	var jsonObject			= new Object(); 
	jsonObject["status"] 	=  status;
	jsonObject["userprofile_id"] 	=  userprofile_id;
	$.ajax({
             url: "saveTwoFactorEnableDisableState",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	showMessage('success', 'Date Saved Successfully !');
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function	editTwoFactorDetails(){
	getDetailsToSaveTwoFactor();
}

function getDetailsToSaveTwoFactor(){

	  $('#userpersonalEmail').val($('#email').val());
	  $('#userMobile').val($('#mobileNumber').val());
	  var jsonObject			= new Object(); 
		jsonObject["userId"] 	=  $('#user_id').val();
	  $.ajax({
           url: "getTwoFactorAuthDetails",
           type: "POST",
           dataType: 'json',
           data: jsonObject,
           success: function (data) {
          	 if(data.details != undefined && data.details != null){
          		 $('#userpersonalEmail').val(data.details.email);
	            	 $('#userMobile').val(data.details.mobileNumber);
	            	 $('#userMobile').val(data.details.mobileNumber);
	            	 $('#authenticationDetailsId').val(data.details.authenticationDetailsId);
          	 }
          	 
          	 hideLayer();
           },
           error: function (e) {
          	 showMessage('errors', 'Some error occured!')
          	 hideLayer();
           }
	});
	  
	  
	  $('#enableTwoFactorAuth').modal('show');
	  $('#toggletwofactor').bootstrapToggle('on')
	  //enableDisableTwoFactor(true, $('#userprofile_id').val());

}

$(document).ready(function() {
	$(".toggle").click(function() {
		  if($(this).hasClass("off")){
			  disableDisableTwoFactor(false, $('#userprofile_id').val());
		  } 
		  else{
			  getDetailsToSaveTwoFactor();
		  }
});
});

function enableTwoFactorValidation(){
	showLayer();
	var jsonObject			= new Object(); 
	jsonObject["otpRequiredType"] 	=  $('#otpRequiredType').val();
	if($('#userpersonalEmail').val() != undefined && $('#userpersonalEmail').val() != null)
		jsonObject["email"] 			=  $('#userpersonalEmail').val();
	if($('#userMobile').val() != undefined && $('#userMobile').val() != null)
		jsonObject["mobileNumber"] 		=  $('#userMobile').val();
		jsonObject["userprofile_id"] 	=  $('#userprofileId').val();
		jsonObject["userId"] 			=  $('#user_id').val();
		jsonObject["authenticationDetailsId"] 			=  $('#authenticationDetailsId').val();
	$.ajax({
             url: "saveTwoFactorAuthDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	showMessage('success', 'Date Saved Successfully !');
            	location.reload();
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function IsPersonalEmail(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 64 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPersonalEmail").innerHTML = "Special Characters not allowed", document.getElementById("errorPersonalEmail").style.display = o ? "none" : "inline", o
}


function IsMobileNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorMobileNo").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorMobileNo").style.display = o ? "none" : "inline", o
}