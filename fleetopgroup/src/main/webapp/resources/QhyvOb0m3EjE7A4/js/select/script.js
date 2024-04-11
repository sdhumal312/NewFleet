$(document).ready(function() {
    $("#TripSelectVehicle").select2({
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleListTEST.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                    	$('#vehicle_registration').val(e.vehicle_registration);
                        return {
                            text: e.vehicle_registration, slug: e.gpsVendorId, id: e.vid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#driverList, #advanceDriverId, #driver7").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriver1List.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.driver_empnumber+" "+e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername, slug: e.slug, id: e.driver_id
                        	
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#driverList2").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriver2List.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.driver_empnumber+" "+e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername, slug: e.slug, id: e.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#Cleaner").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverCleanerList.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                        		 text: e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_fathername+"_"+e.driver_Lastname, slug: e.slug, id: e.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#driverHaltALL").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getALLDriverHalt.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_Lastname+"-"+e.driver_fathername, slug: e.slug, id: e.driver_id
                        }
                    }
                    )
                }
            }
        }
    });
     if($("#driverId").val() != undefined && $("#driverId").val() != null && $("#driverId").val() != "" ){// will show only on edit
    	 $('#driverHaltALL').select2("readonly",true);
    	 $('#driverHaltALL').select2('data', {
			id : $("#driverId").val(),
			text : $("#driverName").val()
		});
	}
    $("#TripRouteList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getTripRouteList.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.routeNo+" "+e.routeName, slug: e.slug, id: e.routeID
                        }
                    }
                    )
                }
            }
        }
    }
    )
},    
     $("#loadListId").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getLoadListInfo.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                            text: a.loadTypeName,
	                            slug: a.slug,
	                            id: a.loadTypesId
	                        }
	                    })
	                }
	            }
	        }
    	})
);

$(document).ready(function() {

	$("#driverList, #reservationToTripSheet").change(function() {
		getDriverRemiderDetails("driverList","select2-chosen-2");
	}),$("#driverList2, #reservationToTripSheet").change(function() {
		getDriverRemiderDetails("driverList2","select2-chosen-3");
	});

});

function getDriverRemiderDetails(id,ui) {
	if($("#reservationToTripSheet").val() != "") {
		showLayer();
		var jsonObject			= new Object();

		jsonObject["driverId"]		= $("#"+id).val();

		$.ajax({
			url: "getDriverDLReminderDetails.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				driverlicenceexperyexist(data, id, ui);
			},
			error: function (e) {
				
			}
		});
		hideLayer();
	} else {
		$("#"+id).val(0);
		$("#"+ui).html("");
		showMessage('info','Select Date of Journey!');
	}
}

async function driverlicenceexperyexist(data, id, ui) {
    
    if($("#reservationToTripSheet").val() != "" && data.DriverRemider != undefined) {
        
        var date = $("#reservationToTripSheet").val().split(" to ");
        var dateFrom = date[0];
        var dateTo = date[1];
        
        var d1 = dateFrom.split("-");
        var d2 = dateTo.split("-");

        var from = new Date(d1[2], parseInt(d1[1])-1, d1[0]); // -1 because months are from 0 to 11
        var to = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
        
        var newDate;
        var splitDate;
        var demoDate;
        
        var objectArray = new Array();
        for (let i = 0; i < data.DriverRemider.length; i++) {
		         var obj = new Object();
		         newDate = data.DriverRemider[i].driver_dlto_show
			     splitDate = newDate.split("-");
			     demoDate = new Date(splitDate[2], parseInt(splitDate[1])-1, splitDate[0]); 
				 console.log("date "+ demoDate);
				 obj.date = demoDate;
				 obj.type = data.DriverRemider[i].driver_remindertype
		         objectArray.push(obj);
     }
        
        for(let i =0; i<objectArray.length; i++){
            
	            if(objectArray[i].date <from){
	                console.log("inside 1st if")
	                //$("#"+id).val(0);
	                //$("#"+ui).html("");
	                showMessage('info', objectArray[i].type + ' Expiring Before Trip!');
	            }
	            else if (objectArray[i].date >= from && objectArray[i].date <=to) {
	                console.log("inside 3d if")
	                //$("#"+id).val(0);
	                //$("#"+ui).html("");
	                showMessage('info', objectArray[i].type +' will Expire in between Trip!');
	    			console.log("Both are expiring in between");
    		   }
	           	else if(objectArray[i].date <=to){
	                console.log("inside 2nd if")    
	               // $("#"+id).val(0);
	               // $("#"+ui).html("");
	                showMessage('info', objectArray[i].type + ' Will Expire in between Trip! ');
	            }
     
		     await delay(1500);
		    }
        
        function delay(ms) {
         return new Promise(resolve => setTimeout(resolve, ms));
        }
        
    }
}
