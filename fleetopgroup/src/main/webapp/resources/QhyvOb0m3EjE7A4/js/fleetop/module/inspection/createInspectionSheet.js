$(document).ready(function() {
    var e = 100,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div class="row" class="form-group" id="grprouteExpense">'
				+'<br/><div class="col-md-2">'
				+'<select class="form-text select2" style="width: 100%;"'
				+'name="inspectionParameter" id="task' + a + '" onchange="validateInspectionParameter(this)" >'
				+'<option>Please Select</option>'
				+'</select> '
				+'</div>'
				+'<div class="col-md-2" id="grprouteAmount" >'
				+'<input type="number" class="form-text" name="frequency"'
				+'id="frequency" onkeypress="return isNumberKey(event,this);" placeholder="Frequency in Days" min="1">'
				+'</div>'
				+'<div class="col-md-1">'
				+'<label >is Mandatory :</label>'
				+'</div> '
				+'<div class="col-md-1">'
				+'<select class="form-text select2"'
				+'name="requiredType" id="requiredType" >'
				+'<option value="false" > NO </option>'
				+'<option value="true" > YES </option>'
				+'</select> '
				+'</div> '
				+'<div class="col-md-4" id="grprouteReference">'
				+'<label class="control-label">Photo Needed :'
				+'</label>'
				+'<label class="radio-inline">'
				+'<input name="photoGroup' + a + '" id="inputPhotoGroupYes'+a+'" value="true" type="radio" />Yes'
				+' </label>'
				+' <label class="radio-inline">'
				+' <input name="photoGroup' + a + '" id="inputPhotoGroupNo'+a+'" value="false" type="radio" checked />No'
				+'</label>'
				+'<label style="padding-left: 5px;" class="control-label">Text Needed :'
				+'</label>'
				+'<label class="radio-inline">'
				+'<input name="textGroup' + a + '" id="textGroupYes'+a+'" value="true" type="radio" />Yes'
				+'</label>'
				+'<label class="radio-inline">'
				+' <input name="textGroup' + a + '" id="textGroupNo'+a+'" value="false" type="radio" checked />No'
				+' </label>'  
				+'</div>'
				+'<a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getInspectionParameterList.in", function(e) {
        	 $("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                 $("#task" + a).append($("<option>").text(t.parameterName).attr("value", t.inspectionParameterId))
             })
        }), $("#select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }),
    $.getJSON("getInspectionParameterList.in", function(e) {
    	
        $("#Expense").empty(), $("#Expense").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.parameterName).attr("value", t.inspectionParameterId))
        })
    })
});

function validateInspectionForm(){
	var sheetname 		= $('#inspectionSheetName').val();
	var vehicleGroup	= $('#vehicleGroup').val();
	
	var photoRequired	= "";
	var textRequired	= "";
	
	$('input[name*=photoGroup]:checked').each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		 photoRequired	+= vehicleVal+"," ;
	});
	$('input[name*=textGroup]:checked').each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		textRequired	+= vehicleVal+"," ;
	});
	console.log('textRequired : ', textRequired);
	
	$('#photoRequired').val(photoRequired);
	$('#textRequired').val(textRequired);
	
	if(sheetname.trim() == ''){
		showMessage('errors', 'Please Enter Inspection Sheet Name !');
		 $('#inspectionSheetName').focus();
		return false;
	}
	if(vehicleGroup == null){
		showMessage('errors', 'Please Select Vehicle Group !');
		$("#vehicleGroup").select2('focus');
		return false;
	}
	
	var noParameter	= false;
	$('select[name*=inspectionParameter]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 noParameter	= true;
			showMessage('errors', 'Please Select Parameter !');
			$("#"+$( this ).attr( "id" )).select2('focus');
			return false;
		}
	});
	
	var noFrequency	= false;
	$('input[name*=frequency]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			noFrequency	= true;
			showMessage('errors', 'Please Enter Frequency In Days !');
			$("#"+$( this ).attr( "id" )).focus();
			return false;
		}
	});
	
	if(noParameter || noFrequency){
		return false;
	}
	
	if($('#sheetToVehicleTypeConfig').val() == true || $('#sheetToVehicleTypeConfig').val() == 'true'){
		 var branch = $('#VehicleLocation').val();
		 if(branch <= 0){
			 showMessage('errors', 'Please Select Branch ! ');
				return false;
		 }
	}
	return true;
}

function validateInspectionParameter(object){
	$('select[name*=inspectionParameter]').each(function(obj){
		if(this.value == object.value && object.id != this.id){
			showMessage('info','Already Selected !');
			object.value = 0;
			return false;
		}
		
	});
}