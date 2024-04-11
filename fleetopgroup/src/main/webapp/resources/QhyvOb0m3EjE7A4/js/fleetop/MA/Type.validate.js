function validateType() {
    var e = !0,
        t = document.getElementById("vtype").value;
    return validateMandatory(t) && ValidateLength(t) ? ValidateAlpha(t) || (e = !1) : (e = !1), e;
}
function validateMandatory(e) {
    var t = !0;
    return (null == e || "" == e) && ((document.getElementById("vtype").style.border = "2px solid #F00"), (document.getElementById("errorvType").innerHTML = "Please Enter the Value"), (t = !1)), t;
}
function ValidateAlpha(e) {
    var t = !0;
    return /[^a-zA-Z0-9 - +]/.test(e) && ((document.getElementById("vtype").style.border = "2px solid #F00"), (document.getElementById("errorvType").innerHTML = "Please Enter the Value Alpha Letter"), (t = !1)), t;
}
function ValidateLength(e) {
    var t = !0;
    return (e.length <= 50 && e.length >= 2) || ((document.getElementById("vtype").style.border = "2px solid #F00"), (document.getElementById("errorvType").innerHTML = "Please input the Type between 2 and 50 characters"), (t = !1)), t;
}
function validateTypeUpdate() {
    var e = !0,
        t = document.getElementById("vTypeUpdate").value;
    return validateMandatoryTypeUpdate(t) && ValidateAlphaTypeUpdate(t) ? ValidateLengthTypeUpdate(t) || (e = !1) : (e = !1), e;
}
function validateMandatoryTypeUpdate(e) {
    var t = !0;
    return (null == e || "" == e) && ((document.getElementById("vTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditType").innerHTML = "Please Enter the Value"), (t = !1)), t;
}
function ValidateAlphaTypeUpdate(e) {
    var t = !0;
    return /[^a-zA-Z0-9 - +]/.test(e) && ((document.getElementById("vTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditType").innerHTML = "Please Enter the Value Alpha Letter"), (t = !1)), t;
}
function ValidateLengthTypeUpdate(e) {
    var t = !0;
    return (
        (e.length < 50 && e.length >= 2) || ((document.getElementById("vTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditType").innerHTML = "Please input the Type between 2 and 50 characters"), (t = !1)), t
    );
}

$(document).ready(function() {
	 $("#serviceProgramId").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "/fleetopgroup/getVehicleServiceProgram.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "GET",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(e) {
	                return {
	                    term: e
	                }
	            },
	            results: function(e) {
	                return {
	                    results: $.map(e, function(e) {
	                        return {
	                            text: e.programName,
	                            slug: e.slug,
	                            id: e.vehicleServiceProgramId
	                        }
	                    })
	                }
	            }
	        }
	    })
});

function validateTyreSize(){
	console.log(">>>",$("#tyreSizeId").val())
	if($("#tyreSizeId").val() == undefined || ($("#tyreSizeId").val()).trim() == "" ){
		showMessage('info','Please Enter Tyre Size')
		return false;
	}
}


function vehicleTypevalidate(){
	console.log(">>>",$("#tyreSizeId").val())
	if($("#vtype").val() == undefined || ($("#vtype").val()).trim() == "" ){
		showMessage('info','Please Enter  Vehicle Type')
		return false;
	}
}

function validateVehicleTypeEdit(){
	
	if($("#vTypeUpdate").val() == undefined || ($("#vTypeUpdate").val()).trim() == "" ){
		showMessage('info','Please Enter Edit Status')
		return false;
	}
}