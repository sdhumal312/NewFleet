$(document).ready(function() {
	$("#Income").select2();
	$.getJSON("tripIncomeList.in", function(e) {
        $("#Income").empty(), $("#Income").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Income").append($("<option>").text(t.incomeName).attr("value", t.incomeID))
        })
    });
    $("#SelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    })
	
	$("#driverId").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
	
	$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
	$("#selectTripSheetNumber").select2( {
        minimumInputLength:1, minimumResultsForSearch:10, ajax: {
            url:"searchTripSheetNumberList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a,
                    tripCompanyId : $('#tripCompanyId').val()
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.tripSheetNumberStr, slug: a.slug, id: a.tripSheetID, vid : a.vid, vehicleName : a.vehicle_registration, 
                            driverId : a.tripFristDriverID, driverName : a.tripFristDriverName
                        }
                    }
                    )
                }
            }
        }
    });$("#selectTripSheetNumber").change(function() {
        var data = $("#selectTripSheetNumber").select2('data');
        $('#SelectVehicle').select2('data', {
				id : data.vid,
				text : data.vehicleName
		 });
		 $("#SelectVehicle").select2('readonly', true);
		 $('#driverId').select2('data', {
				id : data.driverId,
				text : data.driverName
		 });
    });
    
    if($('#tripId').val() != undefined && Number($('#tripId').val()) > 0){
		setTimeout(function(){ 
			$('#selectTripSheetNumber').select2('data', {
				id : $('#tripId').val(),
				text : 'TS-'+$('#tripNumber').val()
		 	});
		 	$("#selectTripSheetNumber").select2('readonly', true);
			$('#SelectVehicle').select2('data', {
				id : $('#vid').val(),
				text : $('#vehicleName').val()
		 });
		 $("#SelectVehicle").select2('readonly', true);
		 $('#driverId').select2('data', {
				id : $('#dId').val(),
				text : $('#dName').val()
		 });
		}, 200);
	}
    
    
});


function addVendorLorryHire() {
	if(validateVendorLorryHire()) {
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["vid"]				= $("#SelectVehicle").val(); 
		jsonObject["vendorId"]			= $("#selectVendor").val();		
		jsonObject["lorryHire"]			= $("#lorryHire").val();
		jsonObject["advanceAmount"]		= $("#advAmount").val();
		jsonObject["date"]				= $("#chequeDate").val();
		jsonObject["driverId"]			= $("#driverId").val();
		jsonObject["tripSheetId"]		= $("#selectTripSheetNumber").val();
		jsonObject["incomeId"]			= $("#Income").val();
		jsonObject["remark"]			= $("#remark").val();
		
		
		var expenseId = new Array();
		var amount	  = new Array();
		
		$("select[name=expenseName]").each(function(){
			expenseId.push($(this).val());
		});
		$("input[name=Amount]").each(function(){
			amount.push($(this).val());
		});
		
		
		var lorryExpenseVal = 0;
		$('select[name*=expenseName]' ).each(function(){
			 lorryExpenseVal = $("#"+$( this ).attr( "id" )).val();
		});
		
		var lorryAmountVal = 0;
		$('input[name*=Amount]' ).each(function(){
			lorryAmountVal = $("#"+$( this ).attr( "id" )).val();
		});
		
		
				if(lorryExpenseVal > 0){
					
					if(lorryAmountVal <= 0){
						$("#"+$( this ).attr( "id" )).focus();
						noAmount = true;
						showMessage('errors', 'Please Enter Amount!');
						hideLayer();
						return false;
					}
				}
				
				if(lorryAmountVal > 0 && ($('#hideExpensesTab').val() == 'false' || $('#hideExpensesTab').val() == false) ){
				
						if(lorryExpenseVal <= 0){
							
							 $("#"+$( this ).attr( "id" )).select2('focus');
							 noExpense = true;
							showMessage('errors', 'Please Enter Expenseee!');
							hideLayer();
							return false;
						}
				}
		
		var array	 = new Array();
		for(var i = 0 ; i< expenseId.length; i++){
			if(Number(expenseId[i]) > 0){
				
				var expenseDetails	= new Object();
				expenseDetails.expenseId		= expenseId[i];
				expenseDetails.amount			= amount[i];
				
				array.push(expenseDetails);
			}
		}
		jsonObject.expenseDetails = JSON.stringify(array);
		

		$.ajax({
			url: "saveVendorLorryHireDetails.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			
			success: function (data) {
				if(data.sequenceNotFound){
					showMessage("errors", "Sequence not found Please contact to system administrator !");
					hideLayer();
				}else{
					showMessage("success", "Vendor Lorry Hire Details Saved.");
					showLayer();
					setTimeout(function(){ hideLayer();location.reload(); }, 500);
				}
			},
			error: function (e) {
				console.log("Error");
			}
		});
		
	}
}

function validateVendorLorryHire() {
	
	
	if(Number($("#SelectVehicle").val()) <= 0) {
		showMessage('info','Select Vehicle');
		return false;
	}
	
	if(Number($("#selectVendor").val()) <= 0) {
		showMessage('info','Select Vendor');
		return false;
	}
	
	if(Number($("#Income").val()) <= 0) {
		showMessage('info','Select Income Name !');
		return false;
	}

	if($("#lorryHire").val() == "" || $("#lorryHire").val() <= 0) {
		showMessage('info','Enter Lorry Hire Amount');
		return false;
	}
 
 
	return true;
}


$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="Amount" id ="Amount' + a + '" min="0" required="required" placeholder="Amount"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
            $("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#task" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense").empty(), $("#Expense").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    })
});