$(document).ready(function(){function a(a){for(var k=document.getElementsByTagName("textarea"),l=0;l<k.length;l++)com_satheesh.EVENTS.addEventHandler(k[l],"focus",b,!1),com_satheesh.EVENTS.addEventHandler(k[l],"blur",c,!1);k=document.getElementsByTagName("input");for(var l=0;l<k.length;l++)a.indexOf(-1!=k[l].getAttribute("type"))&&(com_satheesh.EVENTS.addEventHandler(k[l],"focus",b,!1),com_satheesh.EVENTS.addEventHandler(k[l],"blur",c,!1));com_satheesh.EVENTS.addEventHandler(document.getElementById("formJobType"),"submit",i,!1),com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditJobType"),"submit",j,!1),document.getElementsByTagName("input")[0].focus(),com_satheesh.EVENTS.addEventHandler(document.forms[0].jobType,"blur",d,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].jobRotName,"blur",e,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].jobRotCode,"blur",f,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].jobRotHour,"blur",g,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].jobRotAmount,"blur",h,!1)}function b(a){var b=com_satheesh.EVENTS.getEventTarget(a);null!=b&&(b.style.backgroundColor=k)}function c(a){var b=com_satheesh.EVENTS.getEventTarget(a);null!=b&&(b.style.backgroundColor="")}function d(){var a=document.getElementById("jobType"),b=null!=a.value&&0!=a.value.length,c=document.getElementById("grpjobType");if(null!=c)return b?(c.className="form-group has-success has-feedback",document.getElementById("jobTypeIcon").className="fa fa-check form-text-feedback",document.getElementById("jobTypeErrorMsg").innerHTML=""):(c.className="form-group has-error has-feedback",document.getElementById("jobTypeIcon").className="fa fa-remove form-text-feedback",document.getElementById("jobTypeErrorMsg").innerHTML="Please select job type"),b}function e(){var a=document.getElementById("jobRotName"),b=null!=a.value&&0!=a.value.length,c=document.getElementById("grpjobRotName");if(null!=c)return b?(c.className="form-group has-success has-feedback",document.getElementById("jobRotNameIcon").className="fa fa-check form-text-feedback",document.getElementById("jobRotNameErrorMsg").innerHTML=""):(c.className="form-group has-error has-feedback",document.getElementById("jobRotNameIcon").className="fa fa-remove form-text-feedback",document.getElementById("jobRotNameErrorMsg").innerHTML="Please enter ROT name"),b}function f(){var a=document.getElementById("jobRotCode"),b=null!=a.value&&0!=a.value.length,c=document.getElementById("grpjobRotCode");if(null!=c)return b?(c.className="form-group has-success has-feedback",document.getElementById("jobRotCodeIcon").className="fa fa-check form-text-feedback",document.getElementById("jobRotCodeErrorMsg").innerHTML=""):(c.className="form-group has-error has-feedback",document.getElementById("jobRotCodeIcon").className="fa fa-remove form-text-feedback",document.getElementById("jobRotCodeErrorMsg").innerHTML="Please enter ROT code"),b}function g(){var a=document.getElementById("jobRotHour"),b=null!=a.value&&0!=a.value.length,c=document.getElementById("grpjobRotHour");return null!=c&&(b?(c.className="form-group has-success has-feedback",document.getElementById("jobRotHourIcon").className="fa fa-check  form-text-feedback",document.getElementById("jobRotHourErrorMsg").innerHTML=""):(c.className="form-group has-error has-feedback",document.getElementById("jobRotHourIcon").className="fa fa-remove form-text-feedback",document.getElementById("jobRotHourErrorMsg").innerHTML="Please enter ROT hour")),b}function h(){var a=document.getElementById("jobRotAmount"),b=null!=a.value&&0!=a.value.length,c=document.getElementById("grpjobRotAmount");return null!=c&&(b?(c.className="form-group has-success has-feedback",document.getElementById("jobRotAmountIcon").className="fa fa-check  form-text-feedback",document.getElementById("jobRotAmountErrorMsg").innerHTML=""):(c.className="form-group has-error has-feedback",document.getElementById("jobRotAmountIcon").className="fa fa-remove form-text-feedback",document.getElementById("jobRotAmountErrorMsg").innerHTML="Please enter ROT amount")),b}function i(a){var b=d();b&=e(),b&=f(),b&=g(),(b&=h())||com_satheesh.EVENTS.preventDefault(a)}function j(a){var b=d();b&=e(),b&=f(),b&=g(),(b&=h())||com_satheesh.EVENTS.preventDefault(a)}var k="#FFC";com_satheesh.EVENTS.addEventHandler(window,"load",function(){a("text")},!1)});

function jobTypeValidate(){
	if($("#ReTypeUpdate").val() == undefined || ($("#ReTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Job Type ')
		return false;
	}
	
}

function validateEditSubType(){
	if($("#jobRotName").val() == undefined || ($("#jobRotName").val()).trim() == "" ){
		showMessage('info','Please Enter Job ROT Name ')
		return false;
	}
	
}

function validateJobTypeEdit(){
	if($("#ReTypeUpdate").val() == undefined || ($("#ReTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Job Edit Status ')
		return false;
	}
	
	
}

function ValidateSub(){
	if($("#jobRot").val() == undefined || ($("#jobRot").val()).trim() == "" ){
		showMessage('info','Please Enter Job RotName ')
		return false;
	}
	
	
}





