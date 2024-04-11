$(document).ready(function() {
	
	$("#vehicleGroupId").select2( {
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
    }),$("#userName").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getUserEmailId_Subscrible", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.firstName+" "+e.lastName, slug: e.slug, id: e.user_id
                        }
                    }
                    )
                }
            }
        }
    }),
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
    });
	
});

$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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
				var jsonObject					= new Object();				
				jsonObject["vehicleGroupId"] 	= $('#vehicleGroupId').val();
				jsonObject["date"] 				= $('#dateRange').val();
				jsonObject["userName"] 			= $("#userName").val();
				jsonObject["tripStutesId"] 		= $("#tripStutesId").val();
				jsonObject["companyId"] 		= $("#companyId").val();
				jsonObject["userId"] 			= $("#userId").val();
				

				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}

				$.ajax({					
					url: "getTripSheetStatusCollectionDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {	
						if(data.unknowStatus != undefined && data.unknowStatus){
							showMessage('errors','Please select proper status !');
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
	
	
	if(resultData.tripSheetReportList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$('#vehicleGroup').show();
		$("#companyTable tr").remove();
		//$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("TripSheet Collection Report");
		var groupObj = $('#vehicleGroupId').select2('data');
		if(groupObj != undefined && groupObj != null){
			$('#vehicleGroup').html(groupObj.text);
		}
		var userObj = $('#userName').select2('data');
		if(userObj != undefined && userObj != null){
			$('#user').html(userObj.text);
		}
		
		$('#tripStatus').html($('#tripStutesId :selected').text());
		$('#selectedDate').html($('#dateRange').val());

		
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

		setSlickData(resultData.tripSheetReportList, columnConfiguration, tableProperties);

		$('#gridContainer').show();
		  $("html, body").animate({ scrollTop: $(document).height() }, 1000);
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


function showVehicle(gridObj, dataView, row){
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}


function showTripSheetShow(gridObj, dataView, row){
	var win = window.open('showTripSheet.in?tripSheetID='+dataView.tripSheetId, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}	