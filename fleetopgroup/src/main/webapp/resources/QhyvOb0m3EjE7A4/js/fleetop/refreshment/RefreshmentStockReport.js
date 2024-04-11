$(document).ready(function() {
	$("#locationId").select2({
	    minimumInputLength: 2,
	    minimumResultsForSearch: 10,
	    ajax: {
	        url: "getSearchPartLocations.in",
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
	                        text: a.partlocation_name,
	                        id: a.partlocation_id
	                    }
	                })
	            }
	        }
	    }
	}),$("#partId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10,
        ajax: {
            url:"getPartNameForRefreshment.in", 
            dataType:"json", 
            type:"POST", 
            contentType:"application/json",
            quietMillis:50, 
            data:function(a) {
            	
                return {
                    term: a
                }
            }
            , results:function(a) {
            	
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partnumber+" - "+a.partname,
                             id : a.partid
                        }
                    }
                    )
                }
            }
        }
    }
    );
	
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
				
				var jsonObject							= new Object();				
				jsonObject["stockDate"] 				= $('#stockDate').val();
				jsonObject["locationId"] 				= $('#locationId').val();
				jsonObject["partId"] 					= $("#partId").val();
				
				if(Number($('#locationId').val()) <= 0){
					showMessage('info','Please Select Location !')
					return false;
				}
				if(Number($('#partId').val()) <= 0){
					showMessage('info','Please Select Part !')
					return false;
				}
				if($('#stockDate').val() == null || $('#stockDate').val().trim() == ""){
					showMessage('info','Please Select Date')
					return false;
				}
				showLayer();
				$.ajax({					
					url: "getRefreshmentStockReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {		
						if(data.noStockDate){
							showMessage('info', 'Please select date !')
						}else{
							renderReportData(data);						
						}
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
	$('#routeIdentity').html(resultData.RouteKey);
	$('#vehicleIdentity').html(resultData.VehicleKey);
	$('#dateRangeIdentity').html(resultData.date);
	
	
	if(resultData.dayWiseInventoryStock != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("Refreshment Stock Report");
		
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

		setSlickData(resultData.dayWiseInventoryStock, columnConfiguration, tableProperties);

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
	console.log("dataView",dataView)
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


