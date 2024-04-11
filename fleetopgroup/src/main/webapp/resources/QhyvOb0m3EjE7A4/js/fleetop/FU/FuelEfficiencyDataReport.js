$(document).ready(function() {
	$("#ReportSelectVehicle").select2( {
		  minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a
	                }
	            }
	            , results:function(a) {
	                return {
	                    results:$.map(a, function(a) {
	                        return {
	                            text: a.vehicle_registration, slug: a.slug, id: a.vid
	                        }
	                    }
	                    )
	                }
	            }
	        }
    }
    ),$("#TripRouteList").select2( {
    	  minimumInputLength:3, minimumResultsForSearch:10, ajax: {
              url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                  return {
                      term: a
                  }
              }
              , results:function(a) {
                  return {
                      results:$.map(a, function(a) {
                          return {
                              text: a.routeNo+" "+a.routeName, slug: a.slug, id: a.routeID
                          }
                      }
                      )
                  }
              }
          }
    }
    
    ),$("#btn-save").click(function(event) {
		showLayer();
		var jsonObject					= new Object();
		jsonObject["ReportSelectVehicle"]		= $("#ReportSelectVehicle").val();
		jsonObject["TripRouteList"]				= $("#TripRouteList").val();
		jsonObject["rangeFuelMileage"]			= $("#rangeFuelMileage").val();
		
		$.ajax({
			url: "FuelWS/FuelEfficiencyDataReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setFuelEfficiencyDataReport(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
		
    })
});

function setFuelEfficiencyDataReport(data) {
	var fuelEfficiencyDataReport	= null;
	if(data.FuelEfficiencyDataReport != undefined) {
		$("#reportHeader").html("Fuel Efficiency Data Report");

		$("#tripCollExpenseName").empty();
		fuelEfficiencyDataReport	= data.FuelEfficiencyDataReport;
		var ObjectKeys	 = Object.keys(fuelEfficiencyDataReport);     // Use to sort HashMap Key in Asc Order.
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8 	= $('<th>');
		var th9 	= $('<th>');
		var th10 	= $('<th>');
		var th11 	= $('<th>');
		

		th1.append('Sr No');
		th2.append('Input Date');
		th3.append('Bus Number');
		th4.append('Route');
		th5.append('Driver 1');
		th6.append('Driver 2');
		th7.append('Driver 3');
		th8.append('Daily Operated KM');
		th9.append('Fuel Added Liters');
		th10.append('Diesel KMPL');
		th11.append('Fuel Amount');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);
		tr1.append(th11);
		

		thead.append(tr1);
		ObjectKeys.sort(); // sort the keys of hashmap
		for(var i = 0; i <= ObjectKeys.length; i++) {
			if(fuelEfficiencyDataReport[ObjectKeys[i]] != undefined){
				
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				var td7		= $('<td>');  
				var td8		= $('<td>');
				var td9		= $('<td>');
				var td10	= $('<td>');
				var td11	= $('<td>');
				
				td1.append(i+1);
				td2.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_OPEN_DATE);
				td3.append('<a href="showVehicle?vid='+fuelEfficiencyDataReport[ObjectKeys[i]].vehicleid+'" target="_blank" >'+fuelEfficiencyDataReport[ObjectKeys[i]].vehicle_REGISTRATION+'</a>');
				td4.append(fuelEfficiencyDataReport[ObjectKeys[i]].routeNo +"-"+ fuelEfficiencyDataReport[ObjectKeys[i]].trip_ROUTE_NAME);
				if(fuelEfficiencyDataReport[ObjectKeys[i]].multipleTrip){
					td5.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DRIVER_NAME);	
				}else {
					td5.append("-");
				}
				if(fuelEfficiencyDataReport[ObjectKeys[i]].multipleTrip){
					td6.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_SEC_DRIVER_NAME);	
				}else {
					td6.append("-");
				}
				if(!fuelEfficiencyDataReport[ObjectKeys[i]].multipleTrip){
					td7.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DRIVER_NAME);	
				}else {
					td7.append("-");
				}
				td8.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_USAGE_KM);
				if(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DIESEL != null)
					td9.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DIESEL.toFixed(2));
				if(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DIESELKMPL != null)
					td10.append(fuelEfficiencyDataReport[ObjectKeys[i]].trip_DIESELKMPL.toFixed(2));
				if(fuelEfficiencyDataReport[ObjectKeys[i]].fuel_amount != null)
					td11.append(fuelEfficiencyDataReport[ObjectKeys[i]].fuel_amount.toFixed(2));
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				tr.append(td7);
				tr.append(td8);
				tr.append(td9);
				tr.append(td10);
				tr.append(td11);
				
				tbody.append(tr);
			}
		}

		$("#tripCollExpenseName").append(thead);
		$("#tripCollExpenseName").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	
}