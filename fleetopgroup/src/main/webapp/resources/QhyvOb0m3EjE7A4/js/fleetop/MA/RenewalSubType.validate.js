function validateSubReType(){
	var e=!0,t=document.getElementById("SubReType").value,
	n=document.getElementById("selectReType").value;
	return validateMandatory(t)&&ValidateLength(t)&&ValidateAlpha(t)?checkSelectType(n)||(e=!1):e=!1,e}

function validateMandatory(e){
	var t=!0;
	return(null==e||""==e)&&(document.getElementById("SubReType").style.border="2px solid #F00",document.getElementById("errorSubReType").innerHTML="Please Enter Renewal Sub Type !",
			document.getElementById("errorReType").innerHTML="",t=!1),t}

function ValidateAlpha(e){
	var t=!0;return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("SubReType").style.border="2px solid #F00",document.getElementById("errorSubReType").innerHTML="Please Enter the Value Alpha Letter",document.getElementById("errorReType").innerHTML="",t=!1),t}

function ValidateLength(e){
	var t=!0;
	return e.length<=25&&e.length>=2||(document.getElementById("SubReType").style.border="2px solid #F00",document.getElementById("errorSubReType").innerHTML="Please input the SubReType between 2 and 25 characters",document.getElementById("errorReType").innerHTML="",t=!1),t}

function checkSelectType(e){
	var t=!0;
	return"blank"==e&&(document.getElementById("selectReType").style.border="2px solid red",document.getElementById("errorReType").innerHTML="Please Select Renewal Type !",document.getElementById("SubReType").style.border="2px solid #0F0",document.getElementById("errorSubReType").innerHTML="",t=!1),t}

function validateSubReTypeUpdate(){
	var e=!0,t=document.getElementById("SubReTypeUpdate").value;
	return validateMandatorySubReTypeUpdate(t)&&ValidateAlphaSubReTypeUpdate(t)?ValidateLengthSubReTypeUpdate(t)||(e=!1):e=!1,e}

function validateMandatorySubReTypeUpdate(e){
	var t=!0;return(null==e||""==e)&&(document.getElementById("SubReTypeUpdate").style.border="2px solid #F00",document.getElementById("erroSubReditSubReType").innerHTML="Please Enter the Value",t=!1),t}

function ValidateAlphaSubReTypeUpdate(e){
	var t=!0;
	return/[^a-zA-Z0-9 -]/.test(e)&&(document.getElementById("SubReTypeUpdate").style.border="2px solid #F00",document.getElementById("erroSubReditSubReType").innerHTML="Please Enter the Value Alpha Letter",t=!1),t}

function ValidateLengthSubReTypeUpdate(e){
	var t=!0;
	return e.length<=25&&e.length>=2||(document.getElementById("SubReTypeUpdate").style.border="2px solid #F00",document.getElementById("erroSubReditSubReType").innerHTML="Please input the SubReType between 2 and 25 characters",t=!1),t}

$(document).ready(function(){
		$(".selectType").select2();$("#tagPicker").select2({closeOnSelect:!1})
		});

