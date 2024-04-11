$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();				
				jsonObject["vehicleId"] 				= $('#fuelVehicle').val();
				jsonObject["date"] 						= $('#dateRange').val();
				jsonObject["group"] 					= $('#workOrderGroup1').val();
				
					
				
				
					
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}else{
					
					var dateRange =$('#dateRange').val().split(" to ");
					var period = days_between(dateFormat(dateRange[1]),dateFormat(dateRange[0]));
					if(period > 90){
						showMessage('info','Please Select Date period less than 90 days');
						hideLayer();
						return false;
					}
					
				}

				$.ajax({					
					url: "FuelWS/allVehiclesMileageReport",
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



function renderReportData(resultData) {
	
	$('#invoicePurchase').show();

	$('#dateRangeIdentity').html(resultData.date);
	
	
	if(resultData.fuelEntryDetails != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$("#reportHeader").html("All Vehicle Mileage Report.");
		
		if(resultData.tableConfig != undefined) {

			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];

				if (bObj != null && bObj.show != undefined && bObj.show != null && bObj.show == true) {
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

function showVehicle(gridObj, dataView, row){
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	

	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
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

function dateFormat(dateStr) {
	  var parts = dateStr.split("-")
	  return new Date(parts[2], parts[1] - 1, parts[0])
	}

function days_between(date1, date2) {
    const ONE_DAY = 1000 * 60 * 60 * 24;
    const differenceMs = Math.abs(date1 - date2);
    return Math.round(differenceMs / ONE_DAY);
}
