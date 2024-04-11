function validateGroup(){var e=!0,t=document.getElementById("vGroup").value;return validateMandatory(t)&&ValidateLength(t)?ValidateAlpha(t)||(e=!1):e=!1,e}function validateMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vGroup").style.border="2px solid #F00",document.getElementById("errorvGroup").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vGroup").style.border="2px solid #F00",document.getElementById("errorvGroup").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vGroup").style.border="2px solid #F00",document.getElementById("errorvGroup").innerHTML="Please input the Group between 2 and 25 characters",t=!1),t}function validateGroupUpdate(){var e=!0,t=document.getElementById("vGroupUpdate").value;return validateMandatoryGroupUpdate(t)&&ValidateAlphaGroupUpdate(t)?ValidateLengthGroupUpdate(t)||(e=!1):e=!1,e}function validateMandatoryGroupUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vGroupUpdate").style.border="2px solid #F00",document.getElementById("errorEditGroup").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaGroupUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vGroupUpdate").style.border="2px solid #F00",document.getElementById("errorEditGroup").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthGroupUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vGroupUpdate").style.border="2px solid #F00",document.getElementById("errorEditGroup").innerHTML="Please input the Group between 2 and 25 characters",t=!1),t}

function vehicleGroupValidate(){
	console.log(">>>",$("#tyreSizeId").val())
	if($("#vGroup").val() == undefined || ($("#vGroup").val()).trim() == "" ){
		showMessage('info','Please Enter Group')
		return false;
	}
}

function validateGroupTypeEdit(){
	
	if($("#vGroupUpdate").val() == undefined || ($("#vGroupUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Edit Status')
		return false;
	}
}