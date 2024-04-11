var totalIncomeAmt = 0;
var totalExpenseAmt = 0;
$(function() {
    $("#vid").select2()
}

);

$(document).ready(function() {
	$("#Reportvehiclegroup").select2( {
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
	}
	)
});

$(document).ready(
		function($) {
			$('button[id=btn-save]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();
				var startDate	= '01-';
				var startDateOfMonth			= startDate + ($('#monthRangeSelector').val()).replace('/', '-');

				jsonObject["vehicleGroupId"] 				=  $('#Reportvehiclegroup').val();
				
				jsonObject["dateOfMonth"] 	= startDateOfMonth;
				
				console.log('jsonObject : ', jsonObject);
				
				if(Number($('#Reportvehiclegroup').val()) <= 0){
					showMessage('errors', 'Please Select Vehicle Group !');
					return false;
				}
				if($('#monthRangeSelector').val() == ''){
					showMessage('errors', 'Please Select Month !');
					return false;
				}
				
				showLayer();
				$.ajax({
			             url: "getGroupWiseProfitAndLossReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 console.log('data : ',data);
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
			});

		});

function renderReportData(data){
	if(data.reportDtoList != null && data.reportDtoList.length > 0){
		$('#profitAndLossTable').show();
		
		setProfitAndLossData(data);
		
	}
	else{
		showMessage('errors', 'NO Record Found !');
	}
}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#tbodyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#tbodyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		

		}
	}
}
function setProfitAndLossData(data) {
	
	if(data.vehicleGroup != undefined && data.vehicleGroup != null){
		$('#vehicleInfo').show();

		$('#vehicleGroup').html(data.vehicleGroup.vGroup);
		$('#daysInMonth').html(data.daysInMonth);
		$('#month').html($('#monthRangeSelector').val());
		$('#noOfVehicle').html(data.noOfVehicle);
		
	}
	
	
	var reportDtoList	= null;
	if(data.reportDtoList != undefined) {
		$("#reportHeader").html("Vehicle Group Wise Profit And Loss Report");

		$("#tableBody").empty();
		reportDtoList	= data.reportDtoList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var srNo = 1;
		var grandTotalKMRun		= 0;
		var grandTotalIncome	= 0;
		var grandTotalExpense	= 0;
		var grandTotalBalance	= 0;
		var startDate	= '01-';
		var startDateOfMonth			= startDate + ($('#monthRangeSelector').val()).replace('/', '-');
		 
		for(var i = 0; i < reportDtoList.length; i++ ) {
			
			grandTotalKMRun	 	 += reportDtoList[i].totalKMRun;
			grandTotalIncome	 += reportDtoList[i].totalIncome;
			grandTotalExpense	 += reportDtoList[i].totalExpense;
			grandTotalBalance	 += reportDtoList[i].totalBalance;
			
			var tr = '<tr>'
				+'<td class="fit" >'+srNo+'</td>'
				+'<td style="text-align: left; padding-left: 10px;"><a target="_blank" href="VehicleWiseProfitAndLossReport?vid=' + reportDtoList[i].vid + '&startDateOfMonth=' + startDateOfMonth + '">' + reportDtoList[i].vehicle_registration + '</a></td>'
				+'<td style="text-align: right; padding-right: 10px;">'+reportDtoList[i].noOfTrips+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+reportDtoList[i].daysInTrip+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+reportDtoList[i].noOfDaysIDeal+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+reportDtoList[i].totalKMRun+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+(reportDtoList[i].totalIncome).toFixed(2)+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+(reportDtoList[i].totalExpense).toFixed(2)+'</td>'
				+'<td style="text-align: right; padding-right: 10px;">'+(reportDtoList[i].totalBalance).toFixed(2)+'</td>'
				
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
		
		var tr2 = '<tr>'
			+'<td class="fit" colspan="5" >TOTAL </td>'
			+'<td style="text-align: right; padding-right: 14px;">'+grandTotalKMRun+'</td>'
			+'<td style="text-align: right; padding-right: 14px;">'+(grandTotalIncome).toFixed(2)+'</td>'
			+'<td style="text-align: right; padding-right: 14px;">'+(grandTotalExpense).toFixed(2)+'</td>'
			+'<td style="text-align: right; padding-right: 14px;">'+(grandTotalBalance).toFixed(2)+'</td>'
			
			+'</tr>';
		$('#tableBody').append(tr2);
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		$("#printPdf").removeClass("hide");
		
	} 
	

}
