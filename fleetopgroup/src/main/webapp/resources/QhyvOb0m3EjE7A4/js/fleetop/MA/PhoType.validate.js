function validatePhoType(){var e=!0,t=document.getElementById("vPhoType").value;return validateMandatory(t)&&ValidateLength(t)?ValidateAlpha(t)||(e=!1):e=!1,e}function validateMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vPhoType").style.border="2px solid #F00",document.getElementById("errorvPhoType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vPhoType").style.border="2px solid #F00",document.getElementById("errorvPhoType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vPhoType").style.border="2px solid #F00",document.getElementById("errorvPhoType").innerHTML="Please input the PhoType between 2 and 25 characters",t=!1),t}function validatePhoTypeUpdate(){var e=!0,t=document.getElementById("vPhoTypeUpdate").value;return validateMandatoryPhoTypeUpdate(t)&&ValidateAlphaPhoTypeUpdate(t)?ValidateLengthPhoTypeUpdate(t)||(e=!1):e=!1,e}function validateMandatoryPhoTypeUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vPhoTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditPhoType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaPhoTypeUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vPhoTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditPhoType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthPhoTypeUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vPhoTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditPhoType").innerHTML="Please input the PhoType between 2 and 25 characters",t=!1),t}
function photoTypeValidate(){
	if($("#vPhoType").val() == undefined || ($("#vPhoType").val()).trim() == "" ){
		showMessage('info','Please Enter Photo Type Name')
		return false;
	}
}

function validatePhotoType(){
	if($("#vPhoTypeUpdate").val() == undefined || ($("#vPhoTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Photo Type Name')
		return false;
	}
}
