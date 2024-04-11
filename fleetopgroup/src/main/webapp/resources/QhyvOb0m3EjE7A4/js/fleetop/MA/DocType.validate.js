function validateDocType(){var e=!0,t=document.getElementById("vDocType").value;return validateMandatory(t)&&ValidateLength(t)?ValidateAlpha(t)||(e=!1):e=!1,e}function validateMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vDocType").style.border="2px solid #F00",document.getElementById("errorvDocType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vDocType").style.border="2px solid #F00",document.getElementById("errorvDocType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vDocType").style.border="2px solid #F00",document.getElementById("errorvDocType").innerHTML="Please input the DocType between 2 and 25 characters",t=!1),t}function validateDocTypeUpdate(){var e=!0,t=document.getElementById("vDocTypeUpdate").value;return validateMandatoryDocTypeUpdate(t)&&ValidateAlphaDocTypeUpdate(t)?ValidateLengthDocTypeUpdate(t)||(e=!1):e=!1,e}function validateMandatoryDocTypeUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("vDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaDocTypeUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("vDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthDocTypeUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("vDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please input the DocType between 2 and 25 characters",t=!1),t}


function vehicleDocTypeValidate(){
	if($("#vDocType").val() == undefined || ($("#vDocType").val()).trim() == "" ){
		showMessage('info','Please Enter Document Type Name')
		return false;
	}
}

function validateDocType(){
	if($("#vDocTypeUpdate").val() == undefined || ($("#vDocTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Document Type')
		return false;
	}
}
	
	