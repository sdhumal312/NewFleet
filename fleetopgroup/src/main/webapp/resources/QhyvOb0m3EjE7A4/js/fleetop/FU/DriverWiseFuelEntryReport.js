$(document).ready(function() {
	
	$("#fuelVehicle").select2({minimumInputLength:2, minimumResultsForSearch:10, ajax: {
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
	}}),
	
	$("#DriverId").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ),
    
    $("#routeId").select2( {
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
    ),
    
    $("#secDriverId").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    )
	
});

$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}

);

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();				
				jsonObject["vehicleId"] 				= $('#fuelVehicle').val();
				jsonObject["date"] 						= $('#dateRange').val();
				jsonObject["driverId"] 					= $('#DriverId').val();
				jsonObject["secDriverId"] 				= $('#secDriverId').val();
				jsonObject["routeId"] 					= $('#routeId').val();
				

				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}

				$.ajax({					
					url: "FuelWS/getDriverWiseFuelEntryReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {						
						renderReportData(data);						
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});



function renderReportData(resultData) {
	
	$('#invoicePurchase').show();
	$('#driver1Identity').html(resultData.DriverKey);
	$('#driver2Identity').html(resultData.SecDriverKey);
	$('#routeIdentity').html(resultData.RouteKey);
	$('#vehicleIdentity').html(resultData.VehicleKey);
	$('#dateRangeIdentity').html(resultData.date);
	
	
	if(resultData.fuelEntryDetails != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

	//	$("#firstTableHeader").html("Driver Wise Fuel Report");
		$("#reportHeader").html("Driver Wise Fuel Report");
		
		if(resultData.tableConfig != undefined) {

			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();

			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];

				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}

			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;

		}

		setSlickData(resultData.fuelEntryDetails, columnConfiguration, tableProperties);

		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
		$('#printBtn').addClass('hide');
		showMessage('info', 'No record found !');
	}

}

function setHeaderData(company){
	
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
			$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
			$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}


function showFuelEntry(gridObj, dataView, row){

	var win = window.open('showFuel.in?FID='+dataView.fuel_id, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}
function showVehicle(gridObj, dataView, row){
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}
function showDriver(gridObj, dataView, row){
	if(dataView.driver_id != null){
		var win = window.open('showDriver.in?driver_id='+dataView.driver_id, '_blank');
		if (win) {
			win.focus();
		} else {

			alert('Please allow popups for this website');
		}
	}
	

}
function showDriver2(gridObj, dataView, row){
if(dataView.secDriverID != null){
	var win = window.open('showDriver.in?driver_id='+dataView.secDriverID, '_blank');
	if (win) {
		win.focus();
	} else {
		
		alert('Please allow popups for this website');
	}
}
}
function showCleaner(gridObj, dataView, row){
	if(dataView.cleanerID != null){
		var win = window.open('showDriver.in?driver_id='+dataView.cleanerID, '_blank');
		if (win) {
			win.focus();
		} else {
			
			alert('Please allow popups for this website');
		}
	}
}
function showVendor(gridObj, dataView, row){

	var win = window.open('ShowVendor.in?vendorId='+dataView.vendor_id, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}


