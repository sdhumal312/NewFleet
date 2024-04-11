$(document).ready(function(){
	
	  $("#subscribe1").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        multiple: 0,
	        ajax: {
	            url: "getUserEmailId_Assignto",
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
	                            text: e.firstName + " " + e.lastName,
	                            slug: e.slug,
	                            id  : e.firstName
	                        }
	                    })
	                }
	            }
	        }
	    }),
	    
	  $("#driver").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
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
                        	text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),
	  
	  $("#FuelRouteList1").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "GET",
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
	                            text: e.routeName,
	                            slug: e.slug,
	                            id: e.routeID
	                        }
	                    })
	                }
	            }
	        }
	    }),$("#routeList").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "GET",
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
	                            text: e.routeName,
	                            slug: e.slug,
	                            id: e.routeID
	                        }
	                    })
	                }
	            }
	        }
	    }),
	    
	    $("#depot").select2({
	        minimumInputLength: 2,
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
	    });
	    
})


$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}
				var jsonObject							= new Object();				
				jsonObject["branchId"] 					= $('#depot').val();
				jsonObject["issueTypeId"] 				= $('#IssuesType').val();
				jsonObject["status"] 					= $('#status').val();
				jsonObject["VehicleTypeSelect"] 		= $('#VehicleTypeSelect').val();
				jsonObject["vehicleModel"] 				= $('#vehicleModel').val();
				jsonObject["vehicle"] 					= $('#fuelVehicle').val();
				jsonObject["issueLabel"] 				= $('#issueLabel').val();
				jsonObject["subscribe1"] 				= $('#subscribe1').val();
				jsonObject["routeList"] 				= $('#routeList').val();
				jsonObject["subscribe"] 				= $('#subscribe').val();
				jsonObject["routeList"] 				= $('#routeList').val();
				jsonObject["partCategory"] 				= $('#partCategory').val();
				jsonObject["driver"] 				= $('#driver').val();
				
				var dateRangeVal = $('#dateRange').val();
				var datearray = dateRangeVal.split(" to "); 
				var fromDate = (datearray[0].trim()).split("-").reverse().join("-");
				var toDate = (datearray[1].trim()).split("-").reverse().join("-");
				
				jsonObject["fromDate"] 					= fromDate;
				jsonObject["toDate"] 					= toDate;
					
				$.ajax({					
					url: "getVehicleAndBreakDownReportDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {						
						hideLayer();
						renderReportData(data,dateRangeVal);						
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		},
		
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
		}}));


$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		maxDate: new Date(),
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



function renderReportData(resultData,dateRangeVal) {
	
	$('#invoicePurchase').show();
	$('#depotKeyId').hide();
	$('#vehicleTypetKeyId').hide();
	$('#vehicleKeyId').hide();
	$('#assignedToKeyId').hide();
	$('#issueLabelKeyId').hide();
	$('#statusKeyId').hide();
	$('#avoidableKeyId').hide();
	$('#partCategoryKeyId').hide();
	$('#routeKeyId').hide();
	$('#vehicleModalKeyId').hide();
	
	$('#dateRangeval').html(dateRangeVal);
	
	if(resultData.depotKey != "ALL"){
		$('#depotKey').html(resultData.depotKey);
		$('#depotKeyId').show()
	}
	if(resultData.vehicleTypetKey != "ALL"){
		$('#vehicleTypetKey').html(resultData.vehicleTypetKey);
		$('#vehicleTypetKeyId').show()
	}
	if(resultData.vehicleKey != "ALL"){
		$('#vehicleKey').html(resultData.vehicleKey);
		$('#vehicleKeyId').show()
	}
	if(resultData.assignedToKey != "ALL"){
		$('#assignedToKey').html(resultData.assignedToKey);
		$('#assignedToKeyId').show()
	}
	if(resultData.issueLabelKey != "ALL"){
		$('#issueLabelKey').html(resultData.issueLabelKey);
		$('#issueLabelKeyId').show()
	}
	if(resultData.statusKey != "ALL"){
		$('#statusKey').html(resultData.statusKey);
		$('#statusKeyId').show()
	}
	if(resultData.routeKey != "ALL"){
		$('#avoidableKey').html(resultData.avoidableKey);
		$('#avoidableKeyId').show()
	}
	if(resultData.partCategoryKey != "ALL"){
		$('#partCategoryKey').html(resultData.partCategoryKey);
		$('#partCategoryKeyId').show()
	}
	if(resultData.routeKey != "ALL"){
		$('#routeKey').html(resultData.routeKey);
		$('#routeKeyId').show()
	}
	var dataModal = $('#vehicleModel').select2('data');
	if(dataModal != undefined){
		$('#vehicleModalKey').html(dataModal.text);
		$('#vehicleModalKeyId').show()
	}
	var dataDriver = $('#driver').select2('data');
	if(dataDriver != undefined){
		$('#driverKey').html(dataDriver.text);
		$('#driverKeyId').show()
	}
	
	if(resultData.fuelEntryDetails != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("Vehicle and Vehicle BreakDown Report .");
		
		if(resultData.tableConfig != undefined) {

			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if(resultData.depot > 0 && bObj.tableDtoName == "issues_VEHICLE_GROUP" ){
					bObj.show = false;
				}
				if(resultData.vehicle > 0 && bObj.tableDtoName == "issues_VEHICLE_REGISTRATION" ){
					bObj.show = false;
				}
				if(resultData.issueLabel > 0 && bObj.tableDtoName == "issues_LABELS" ){
					bObj.show = false;
				}
				if(resultData.VehicleTypeSelect > 0 && bObj.tableDtoName == "vehicleType" ){
					bObj.show = false;
				}
				if(resultData.status > 0 && bObj.tableDtoName == "issues_STATUS" ){
					bObj.show = false;
				}
				if(resultData.vehicleModel > 0 && bObj.tableDtoName == "vehicleModel" ){
					bObj.show = false;
				}
				if(resultData.driver > 0 && bObj.tableDtoName == "issues_DRIVER_NAME" ){
					bObj.show = false;
				}
				
				if(Number($('#IssuesType').val()) == 1){
					if(bObj.tableDtoName == "vehicleReplacedStr"){
						bObj.show = false;
					}
					if(bObj.tableDtoName == "replacedWithVehicle"){
						bObj.show = false;
					}
					if(bObj.tableDtoName == "vehicleReplacedLocation"){
						bObj.show = false;
					}
					if(bObj.tableDtoName == "tripCancelledStr"){
						bObj.show = false;
					}
					if(bObj.tableDtoName == "cancelledKM"){
						bObj.show = false;
					}
				
				}
				
				
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
