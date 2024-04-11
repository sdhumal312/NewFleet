$(document).ready(function() {
	//$("#checkingInspectorId").select2();
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div class="row1 removable">'
        		+'<div class="col-sm-2"><input type="hidden" id="vid' + a + '" name="vid" style="width: 100%;" placeholder="Please Enter 2 or more Vehicle Name" required="required"  /><span id="vehicleNameIcon" class=""></div>'
        		+'<div class="col-sm-2"><input type="hidden" id="conductorId' + a + '" name="conductorId" style="width: 100%;" placeholder="Please Enter 3 or more Driver Name, No" /><span id="driverNameIcon" class="">'
        		+'</div><div class="col-sm-1 clockpicker"><input id="checkingTime' + a + '" type="text" class="form-text clockpicker" value="00:00" name="checkingTime" required="required"></div>'
        		+'<div class="col-sm-1"> <input type="text" id="place'+a+'" class="form-text" name="place" placeholder="place" > </div>'
        		+'<div class="col-sm-1"> <input type="number" class="form-text" id="noOfSeat'+ a +'" name="noOfSeat" placeholder="no of seat" value="0"> </div>'
        		+'<div class="col-sm-2"> <input type="text" class="form-text" id="remark'+a+'" name="remark" placeholder="Remark"> </div>'
        		+'<a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), 
        	$("#vid" + a).select2({
            minimumInputLength: 2,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getVehicleListTEST.in",
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
        }),$("#conductorId" + a).select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getConductorList.in",
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
                                text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                                slug: e.slug,
                                id: e.driver_id
                            }
                        })
                    }
                }
            }
        }),$('#checkingTime'+ a).clockpicker({
			  twelvehour: true
		}))
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $("#vid").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListALLStatus.in",
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
    }),$("#conductorId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getAllCConductorList.in",
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
                            text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    })/*,$("#checkingInspectorId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getCheckingInspectorList.in",
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
                            text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    })*/, $("#vehicleGroupId").select2( {
        ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    }), $("#checkingInspectorId").select2( {
        ajax: {
            url:"getAllCheckingInspectorList.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber + "-" + a.driver_firstname + " " + a.driver_Lastname, 
                            slug: a.slug, 
                            id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    })/*,$("#vehicleGroupId").change(function() {
        $.getJSON("getCheckingInspectorListByGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#checkingInspectorId").html(b)
        })
    })*/
});

$(document).ready(
		function($) {
			$('button[id=Save]').click(function(e) {
				e.preventDefault();

				if(Number($('#vehicleGroupId').val()) <= 0){
					$("#vehicleGroupId").select2('focus');
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				if($('#checkingInspectorId').val() <= 0){
					$("#checkingInspectorId").select2('focus');
					showMessage('errors', 'Please Select Checking Inspector Name!');
					return false;
				}
				if($('#checkingDateTime').val() == undefined || $('#checkingDateTime').val() == '' || $('#checkingDateTime').val() == null){
					$('#checkingDateTime').focus()
					showMessage('errors', 'Please Select Date!');
					return false;
				}
				
				if(Number($('#conductorId').val()) <= 0){
					$("#conductorId").select2('focus');
					showMessage('errors', 'Please Select Conductor !');
					return false;
				}
				
				if($('#vid').val() <= 0){
					$("#vid").select2('focus');
					showMessage('errors', 'Please Select Vehicle!');
					return false;
				}
				
				
				var jsonObject			= new Object();

				jsonObject["checkingInspectorId"] 	  	=  $('#checkingInspectorId').val();
				jsonObject["checkingDateTime"] 			=  $('#checkingDateTime').val();
				jsonObject["vid"] 						=  $('#vid').val();
				jsonObject["conductorId"] 				=  $('#conductorId').val();
				jsonObject["place"] 					=  $('#place').val();
				jsonObject["checkingTime"] 				=  $('#checkingTime').val();
				jsonObject["outplace"] 					=  $('#outplace').val();
				jsonObject["outTime"] 					=  $('#outTime').val();
				jsonObject["noOfSeat"] 					=  $('#noOfSeat').val();
				jsonObject["remark"] 					=  $('#remark').val();
				jsonObject["vehicleGroupId"] 			=  $('#vehicleGroupId').val();
				jsonObject["route"] 					=  $('#route').val();
				jsonObject["description"] 				=  $('#description').val();
				jsonObject["punishment"] 				=  $('#punishment').val();
				jsonObject["orderNoAndDate"] 			=  $('#orderNoAndDate').val();
				
				
				showLayer();
				$.ajax({
			             url: "saveCheckingDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 hideLayer();
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
			});

		});
		
function renderReportData(data){
	reloadPage();
	showMessage('success', data.saveMessage);
}

function reloadPage(){
	
	$("#vehicleGroupId").select2("val", "");
	$("#checkingInspectorId").select2("val", "");
	$('#checkingDateTime').val('');
	
	$( 'input[id*=vid]' ).each(function(){
		$("#"+$( this ).attr( "id" )).select2("val", "");
	});
	$( 'input[id*=conductorId]' ).each(function(){
		$("#"+$( this ).attr( "id" )).select2("val", "");
	});
	$( 'input[id*=checkingTime]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('00:00');
	});
	$( 'input[id*=outTime]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('00:00');
	});
	
	$( 'input[id*=place]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=outplace]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=noOfSeat]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val(0);
	});
	$( 'input[id*=remark]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=route]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=description]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=punishment]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	$( 'input[id*=orderNoAndDate]' ).each(function(){
		$("#"+$( this ).attr( "id" )).val('');
	});
	
	$("div.removable").remove();
}
function editCheckingEntry(data){
	
	$('#checkingins').val(data.vehicle_registration);
}