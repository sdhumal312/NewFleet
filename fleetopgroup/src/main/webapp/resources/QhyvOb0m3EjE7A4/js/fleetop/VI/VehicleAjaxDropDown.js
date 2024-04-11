$(document).ready(function() {
	$('#serviceProgramId').select2();
	
	if($("#selectServiceProgramOnModal").val() == true || $("#selectServiceProgramOnModal").val() == 'true'){
		$('#serviceRow').hide();
	}else{
		$('#serviceRow').show();
	}
	
    $("#VehicleTypeSelect").select2( {
        ajax: {
            url:"getVehicleType.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vtype, slug: t.slug, id: t.tid
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#VehicleTypeSelect").change(function() {
        $.getJSON("getVehicleServiceProgramByTypeId.in", {
            vType: $(this).val(),
            ajax: "true"
        }, function(e) {
        	$('#serviceProgramId').val(e[0].serviceProgramId).trigger("change");
        })
    }), $("#VehicleGroupSelect").select2( {
        ajax: {
            url:"getVehicleGroup.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vGroup, slug: t.slug, id: t.gid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehicleRouteSelect").select2( {
        ajax: {
            url:"getVehicleTripRoute.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.routeName, slug: t.slug, id: t.routeID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehicleFuelSelect").select2( {
        ajax: {
            url:"getVehicleFuel.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vFuel, slug: t.slug, id: t.fid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehicleStatusSelect").select2( {
        ajax: {
            url:"getVehicleStatus.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vStatus, slug: t.slug, id: t.sid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehiclePartlocationSelect").select2({
        ajax: {
            url:"getVehiclePartlocation.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.partlocation_name, slug: t.slug, id: t.partlocation_name
                        }
                    }
                    )
                }
            }
        }
    }),$.getJSON("vehicleFuelAutocomplete.in", function(e) {
		fuelList	= e;
		$("#fuelId").empty();
		$('#fuelId').select2();
		
		for(var k = 0; k <fuelList.length; k++){
			
			$("#fuelId").append($("<option>").text(fuelList[k].vFuel).attr("value", fuelList[k].fid));
			/*if(k==0){
					$("#fuelId").append($("<option selected>").text(fuelList[k].vFuel).attr("value", fuelList[k].fid));
			} else {
				
				$("#fuelId").append($("<option>").text(fuelList[k].vFuel).attr("value", fuelList[k].fid));
			}*/
		}

	}),$.getJSON("vehicleStatusAutocomplete.in", function(e) {
		statusList	= e;
		//$("#vehicleStatus").empty();
		//$('#vehicleStatus').select2();
		//for(var k = 0; k <statusList.length; k++){
			//$("#vehicleStatus").append($("<option>").text(statusList[k].vStatus).attr("value", statusList[k].sid));
		//}

	}),$("#vehicle_maker").select2({
		ajax : {
            url:"vehicleManufacturerAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleManufacturerName, slug: t.slug, id: t.vehicleManufacturerId
                        }
                    }
                    )
                }
            }
        }
	
	}),$("#vehicleModel").select2({
		ajax : {
            url:"vehicleModelAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t,
                    manufacturer : $('#vehicleMaker').val()
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleModelName, slug: t.slug, id: t.vehicleModelId
                        }
                    }
                    )
                }
            }
        }
	
	}),$("#vehicleModel").change(function() {
        $.getJSON("getVehicleServiceProgramByTypeAndModalId.in", {
            vType: $('#VehicleTypeSelect').val(),
            vehicleModal: $(this).val(),
            ajax: "true"
        }, function(e) {
        	console.log('testtd : ', e.length > 0);
        	if(e.length > 0){
        		$('#serviceProgramId').val(e[0].serviceProgramId).trigger("change");
        	}else{
        		$('#serviceProgramId').val(0).trigger("change");
        	}
        	
        	if(Number($('#serviceProgramId').val()) > 0){
        		$("#serviceProgramId").select2('readonly', true);
        	}else{
        		$("#serviceProgramId").select2('readonly', false);
        	}
        })
    }),
	 $("#VehicleLocation").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getIssuesBranch.in?Action=FuncionarioSelect2",
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
	                            text: e.branch_name + " - " + e.branch_code,
	                            slug: e.slug,
	                            id: e.branch_id
	                        }
	                    })
	                }
	            }
	        },
	        //Allow manually entered text in drop down.
	        createSearchChoice:function(term, results) {
	            if ($(results).filter( function() {
	                return term.localeCompare(this.text)===0; 
	            }).length===0) {
	                return {id:term, text:term + ' [New]'};
	            }
	        },
	    }),
	    
	    $("#bodyMakerSelect").select2({
        ajax: {
            url:"getVehicleBodyMaker.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    search: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleBodyMakerName, id: t.vehicleBodyMakerId
                        }
                    }
                    )
                }
            }
        }
    }
    )
	    
});