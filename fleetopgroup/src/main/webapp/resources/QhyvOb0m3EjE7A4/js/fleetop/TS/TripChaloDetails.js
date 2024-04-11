/*$(document).ready(function() {
	var e = 25,
	t = $(".input_fields_wrap"),
	n = $(".add_field_button"),
	a = 1;
	$(n).click(function(n) {
		n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-2"><input type="text" class="form-text" name="chaloKm" placeholder="Chalo Km"></div><div class="col-md-2"><input type="text" class="form-text" name="chaloAmount"  placeholder="Chalo Amount"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(".select2").select2())
	}), $(t).on("click", ".remove_field", function(e) {
		e.preventDefault(), $(this).parent("div").remove(), a--
	})
});*/


function addEmail() {
	console.log("inside")
	if(validateChalo()) {
		showLayer();
		console.log("insideeee")
		var jsonObject					= new Object();
		
		jsonObject["chalokm"]				= $("#chalokm").val();
		jsonObject["chaloAmount"]			= $("#chaloAmount").val();
		jsonObject["tripdailyId"]			= $("#tripdailyId").val();
		
		console.log("data..",jsonObject);

		$.ajax({
			url: "tripDailySheetWS/getChaloDetailsforModel",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data",data)
				if(data.chaloUpdatedRows != undefined && data.chaloUpdatedRows > 0 ){
					console.log("in")
				} 
				showMessage("success", "Chalo Details Saved.")
			},
			error: function (e) {
				console.log("Error");
			}
		});
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function validateChalo() {
	
	if($("#chalokm").val() <= 0) {
		showMessage('info','Select Chalo KM');
		return false;
	}

	if($("#chaloAmount").val() <= 0) {
		showMessage('info','Select Chalo Amount');
		return false;
	}

	return true;
}


function email(){
	
	console.log("insideeee")
	var jsonObject					= new Object();
	
	jsonObject["tripdailyId"]			= $("#tripdailyId").val();
	
	console.log("tripdailyId",jsonObject);
	
	$.ajax({
		url: "tripDailySheetWS/getEnteredChaloDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data);
			checkChalo(data);
			 hideLayer();
			 
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
	$('#configureChalo').modal('show');
	
}

function checkChalo(data) {
	console.log("checkChalo...",data)
	var CHALO_KM = 0;
	var CHALO_AMOUNT = 0.0;
	
	if(data.getChaloDetails != undefined) {
		
		getChaloDetails	= data.getChaloDetails;	
		
		for(var i = 0; i < getChaloDetails.length; i++ ) {
			
			CHALO_KM = getChaloDetails[i].chalo_KM;
			CHALO_AMOUNT = getChaloDetails[i].chalo_AMOUNT;
			
			
			 
	}
		$('#chalokm').val(CHALO_KM);
		$('#chaloAmount').val(CHALO_AMOUNT);
}
}