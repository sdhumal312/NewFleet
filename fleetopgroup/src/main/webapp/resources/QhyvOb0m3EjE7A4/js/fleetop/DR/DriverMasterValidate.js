function IsNumDriverRenewalNumber(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>31&&33>t||t>44&&48>t||t>=48&&57>=t||t>=65&&90>=t||t>=97&&122>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorNumber").innerHTML="Special Characters  not allowed",document.getElementById("errorNumber").style.display=n?"none":"inline",n}function IsNumericTimeThru(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>=48&&57>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorTimeThru").innerHTML="Alpha Characters not allowed",document.getElementById("errorTimeThru").style.display=n?"none":"inline",n}function IsDriverPhotoName(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>31&&33>t||t>44&&48>t||t>=48&&57>=t||t>=65&&90>=t||t>=97&&122>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorPhotoName").innerHTML="Special Characters  not allowed",document.getElementById("errorPhotoName").style.display=n?"none":"inline",n}function IsDriverCommentName(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>31&&33>t||t>44&&48>t||t>=48&&57>=t||t>=65&&90>=t||t>=97&&122>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorCommentName").innerHTML="Special Characters  not allowed",document.getElementById("errorCommentName").style.display=n?"none":"inline",n}function IsDriverDocumentName(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>31&&33>t||t>44&&48>t||t>=48&&57>=t||t>=65&&90>=t||t>=97&&122>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDocumentName").innerHTML="Special Characters  not allowed",document.getElementById("errorDocumentName").style.display=n?"none":"inline",n}function IsDriverDocumentReviseName(e){var t=0==e.keyCode?e.charCode:e.keyCode,n=t>31&&33>t||t>44&&48>t||t>=48&&57>=t||t>=65&&90>=t||t>=97&&122>=t||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDocumentReviseName").innerHTML="Special Characters  not allowed",document.getElementById("errorDocumentReviseName").style.display=n?"none":"inline",n}function validateDocType(){var e=!0,t=document.getElementById("dDocType").value;return validateDocMandatory(t)&&ValidateDocLength(t)?ValidateDocAlpha(t)||(e=!1):e=!1,e}function validateDocMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dDocType").style.border="2px solid #F00",document.getElementById("errordDocType").innerHTML="Please Enter the Value",t=!1),t}function ValidateDocAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dDocType").style.border="2px solid #F00",document.getElementById("errordDocType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateDocLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dDocType").style.border="2px solid #F00",document.getElementById("errordDocType").innerHTML="Please input the DocType between 2 and 25 characters",t=!1),t}function validateDocTypeUpdate(){var e=!0,t=document.getElementById("dDocTypeUpdate").value;return validateMandatoryDocTypeUpdate(t)&&ValidateAlphaDocTypeUpdate(t)?ValidateLengthDocTypeUpdate(t)||(e=!1):e=!1,e}function validateMandatoryDocTypeUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaDocTypeUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthDocTypeUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dDocTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditDocType").innerHTML="Please input the DocType between 2 and 25 characters",t=!1),t}function validateTrainingType(){var e=!0,t=document.getElementById("dTrainingType").value;return validateTrainingMandatory(t)&&ValidateTrainingLength(t)?ValidateTrainingAlpha(t)||(e=!1):e=!1,e}function validateTrainingMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dTrainingType").style.border="2px solid #F00",document.getElementById("errordTrainingType").innerHTML="Please Enter the Value",t=!1),t}function ValidateTrainingAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dTrainingType").style.border="2px solid #F00",document.getElementById("errordTrainingType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateTrainingLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dTrainingType").style.border="2px solid #F00",document.getElementById("errordTrainingType").innerHTML="Please input the TrainingType between 2 and 25 characters",t=!1),t}function validateTrainingTypeUpdate(){var e=!0,t=document.getElementById("dTrainingTypeUpdate").value;return validateMandatoryTrainingTypeUpdate(t)&&ValidateAlphaTrainingTypeUpdate(t)?ValidateLengthTrainingTypeUpdate(t)||(e=!1):e=!1,e}function validateMandatoryTrainingTypeUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dTrainingTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditTrainingType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaTrainingTypeUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dTrainingTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditTrainingType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthTrainingTypeUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dTrainingTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditTrainingType").innerHTML="Please input the TrainingType between 2 and 25 characters",t=!1),t}function validateJobType(){var e=!0,t=document.getElementById("dJobType").value;return validateJobMandatory(t)&&ValidateJobLength(t)?ValidateJobAlpha(t)||(e=!1):e=!1,e}function validateJobMandatory(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dJobType").style.border="2px solid #F00",document.getElementById("errordJobType").innerHTML="Please Enter the Value",t=!1),t}function ValidateJobAlpha(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dJobType").style.border="2px solid #F00",document.getElementById("errordJobType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateJobLength(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dJobType").style.border="2px solid #F00",document.getElementById("errordJobType").innerHTML="Please input the JobType between 2 and 25 characters",t=!1),t}function validateJobTypeUpdate(){var e=!0,t=document.getElementById("dJobTypeUpdate").value;return validateMandatoryJobTypeUpdate(t)&&ValidateAlphaJobTypeUpdate(t)?ValidateLengthJobTypeUpdate(t)||(e=!1):e=!1,e}function validateMandatoryJobTypeUpdate(e){var t=!0;return(null==e||""==e)&&(document.getElementById("dJobTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditJobType").innerHTML="Please Enter the Value",t=!1),t}function ValidateAlphaJobTypeUpdate(e){var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("dJobTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditJobType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}function ValidateLengthJobTypeUpdate(e){var t=!0;return e.length<=25&&e.length>=2||(document.getElementById("dJobTypeUpdate").style.border="2px solid #F00",document.getElementById("errorEditJobType").innerHTML="Please input the JobType between 2 and 25 characters",t=!1),t}var specialKeys=new Array;specialKeys.push(8),specialKeys.push(9),specialKeys.push(46),specialKeys.push(36),specialKeys.push(35),specialKeys.push(37),specialKeys.push(39);

function driverDocTypevalidate(){
	console.log(">>>",$("#tyreSizeId").val())
	if($("#dDocType").val() == undefined || ($("#dDocType").val()).trim() == "" ){
		showMessage('info','Please Enter DocType')
		return false;
	}
}

function driverJobTypeValidate(){
	console.log(">>>",$("#tyreSizeId").val())
	if($("#dJobType").val() == undefined || ($("#dJobType").val()).trim() == "" ){
		showMessage('info','Please Enter JobType')
		return false;
	}
}


function validateDriverDocEdit(){

	if($("#dDocTypeUpdate").val() == undefined || ($("#dDocTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter DriverDocType')
		return false;
	}
}


function validateDriverJobTypeEdit(){

	if($("#dJobTypeUpdate").val() == undefined || ($("#dJobTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter DriverDocType')
		return false;
	}
}
function issueCommentValidate(){
	if(Number($("#commentTypeId").val()) <= 0 || ($("#commentTypeId").val()).trim() == ""){
		console.log("555---",Number($("#commentTypeId").val()))
		showMessage('errors','Please Enter Comment Type')
		return false;
	}
	if($("#comment").val() == undefined || ($("#comment").val()).trim() == "" ){
		showMessage('errors','Please Enter Comment')
		return false;
	}
	return true;
}
function validateAdvanceDate(){
	if($("#fuelDate").val() == "" || $("#fuelDate").val() == null ){
		showMessage('errors','Please Enter Advance Date');
		return false;
	}
}
