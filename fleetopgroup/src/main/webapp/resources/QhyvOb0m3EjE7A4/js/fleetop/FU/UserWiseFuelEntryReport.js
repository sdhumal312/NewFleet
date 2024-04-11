$(function() {
	$("#subscribeByBranchId").select2();
	$("#branchnamelist").change(function() {
		if(Number($(this).val()) > 0){
			$("#subscribeByBranchId").select2("val", "");
			 $.getJSON("getUserByBranchId.in", {
            branchId: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">Please Select</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].user_id+'">'+a[d].firstName+"</option>";
            b+="</option>", $("#subscribeByBranchId").html(b)
            console.log('aaaaaa ', a.length)
            if(a.length == 1){
				$("#subscribeByBranchId").select2('data', {id: a[0].user_id, text: a[0].firstName});
			}else
				$("#subscribeByBranchId").select2('data', {id: -1, text: 'Please Select'});
        }  )
		}else{
			var op = '<option value="-1">Please select</option>'
			$("#subscribeByBranchId").html(op);
			$("#subscribeByBranchId").select2("val", -1);
		}
      
    }
    )
	
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
				
				jsonObject["date"] 						= $('#dateRange').val();

				
				if( $('#dateRange').val() <= 0 || $('#dateRange').val() == ""){
					showMessage('info','Please Select Date')
					hideLayer();
					return false;
				}
				
				if($('#showBranchToUserWiseReport').val() == 'true' || $('#showBranchToUserWiseReport').val() == true){
					jsonObject["userId"] 						= $('#subscribeByBranchId').val();
					jsonObject["branchId"] 						= $('#branchnamelist').val();
				
					if($('#branchnamelist').val() == undefined || $('#branchnamelist').val() <= 0 || $('#branchnamelist').val() == ""){
						showMessage('info','Please Select Branch')
						hideLayer();
						return false;
					}
					
					
				}else{
					jsonObject["userId"] 						= $('#subscribe').val();
					if($('#subscribe').val() == undefined || $('#subscribe').val() <= 0 || $('#subscribe').val() == ""){
						showMessage('info','Please Select User')
						hideLayer();
						return false;
					}
				}
			

				$.ajax({					
					url: "FuelWS/getUserWiseFuelEntryReport",
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
	if(resultData.fuelEntryDetails != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		$('#reportHeader').html('<b>User Wise Fuel Entries Report</b>');
		
		if($('#branchnamelist').val() != undefined && Number($('#branchnamelist').val()) > 0){
		var  branchData = $('#branchnamelist').select2('data');
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Branch : '+branchData.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data = null;
		
		if($('#showBranchToUserWiseReport').val() == 'true' || $('#showBranchToUserWiseReport').val() == true)
				data = $('#subscribeByBranchId').select2('data');
		else
				data = $('#subscribe').select2('data');
		
		if(($('#subscribeByBranchId').val() != undefined && Number($('#subscribeByBranchId').val()) > 0) || ($('#subscribe').val() != undefined && Number($('#subscribe').val()) > 0)){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> USER : '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}

		var date =  $('#dateRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date  : '+date+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}

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

function showVendor(gridObj, dataView, row){

	var win = window.open('ShowVendor.in?vendorId='+dataView.vendor_id, '_blank');
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


