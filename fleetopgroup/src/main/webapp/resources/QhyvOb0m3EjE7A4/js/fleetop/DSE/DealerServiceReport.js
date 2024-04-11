$(document).ready(function(){
	$('#status').select2({allowClear:true,placeholder:"All"}),
	$("#vendorId").select2({
		minimumInputLength: 3,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getVendorSearchListPart.in?Action=FuncionarioSelect2",
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
							text: a.vendorName + " - " + a.vendorLocation,
							slug: a.slug,
							id: a.vendorId
						}
					})
				}
			}
		}
	}),
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
	}
})

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
				var jsonObject								= new Object();				
				jsonObject["vehicle"] 						= $('#fuelVehicle').val();
				jsonObject["status"] 						= $('#status').val();
				jsonObject["vendorId"] 						= $('#vendorId').val();
				
				var dateRangeVal = $('#dateRange').val();
				var datearray = dateRangeVal.split(" to "); 
				var fromDate = (datearray[0].trim()).split("-").reverse().join("-");
				var toDate = (datearray[1].trim()).split("-").reverse().join("-");
				
				jsonObject["fromDate"] 					= fromDate;
				jsonObject["toDate"] 					= toDate;
					
				$.ajax({					
					url: "getDealerServiceReport",
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

		}
);


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
	$('#dateRangeval').html(dateRangeVal);
	
	if(resultData.list != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		var data = $('#fuelVehicle').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		var data2 = $('#vendorId').select2('data');
		if(data2){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vendor : '+data2.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		var data3 = $('#status').select2('data');
		if(data3 != null && data3.id > 0){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Status : '+data3.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Date Range : '+$('#dateRange').val()+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);

		$("#reportHeader").html("Dealer Service Report.");
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				if(data2 && bObj.tableDtoName == "vendorName"){
					bObj.show = false;
				}
				if(data3 != null && data3.id > 0 && bObj.tableDtoName == "status"){
					bObj.show = false;
				}
				if (bObj.show != undefined && bObj.show == 	 true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;

		}
		setSlickData(resultData.list, columnConfiguration, tableProperties);
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

function getpartOrLabourDetails(id,type){
	
	var jsonObject								= new Object();				
	jsonObject["dealerServiceId"] 				= id;
	jsonObject["type"] 							= type;
	
	$.ajax({					
		url: "getpartLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {						
			$('#labourTableDiv').hide();
			$('#partTableDiv').hide();
			if(type == 1){
				setDealerServiceEntriesPart(data);
			}else{
				setDealerServiceEntriesLabour(data);
			}
			$('#showPartLabourDetails').modal('show');
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}

function setDealerServiceEntriesLabour(data){
	var dealerServiceEntriesLabour 	= data.dealerServiceEntriesLabour;
	
	$("#dealerServiceEntriesLabourTable").empty();
	
	var labourSubTotalCost 		= 0;
	var labourDiscountCost 		= 0;
	var labourTaxCost 			= 0;
	var labourTotalCost 		= 0;
	
	if((dealerServiceEntriesLabour != undefined || dealerServiceEntriesLabour != null)&& dealerServiceEntriesLabour.length > 0){
		if(dealerServiceEntriesLabour[0].labourDiscountTaxTypeId == 1){
			$('#labourDisType').text('');
			$('#labourDisType').text('%');
			$('#labourTaxType').text('');
			$('#labourTaxType').text('%');
		}else{
			$('#labourTaxType').html('');
			$('#labourTaxType').html('&#x20B9');
			$('#labourDisType').html('');
			$('#labourDisType').html('&#x20B9');
		}
		for(var index = 0 ; index < dealerServiceEntriesLabour.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit' style='font-size: 15px;'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit' style='text-align: left; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourName  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourWorkingHours.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourPerHourCost.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourDiscount  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourTax  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ addCommas((dealerServiceEntriesLabour[index].labourTotalCost).toFixed(2)) +"</td>");
			
			labourSubTotalCost		+= (Number(dealerServiceEntriesLabour[index].labourWorkingHours)*Number(dealerServiceEntriesLabour[index].labourPerHourCost) );
			labourDiscountCost		+= Number(dealerServiceEntriesLabour[index].labourDiscountCost);
			labourTaxCost			+= Number(dealerServiceEntriesLabour[index].labourTaxCost);
			labourTotalCost 		+= Number(dealerServiceEntriesLabour[index].labourTotalCost);
		
			$('#dealerServiceEntriesLabourTable').append("<tr id='penaltyID"+dealerServiceEntriesLabour[index].DealerServiceEntriesLabourId+"' >" + columnArray.join(' ') + "</tr>");

		}
		var columnArray1 = new Array();
		columnArray1.push("<td class='fit' colspan='6' style='text-align: left; font-size: 18px;'> Total Labour Cost </td>");
		columnArray1.push("<td class='fit' colspan='1' style='text-align: right; font-size: 18px; ' >"+ addCommas(labourTotalCost.toFixed(2)) +"</td>");
		$('#dealerServiceEntriesLabourTable').append("<tr style='background: salmon;color: white;'> " + columnArray1.join(' ') + "</tr>");

		columnArray = [];
	}else{
		$("#labourDiscountTaxButton").removeClass('hide');
		validateLabourDetails = false;
		$("#labourTable").hide();
	}
	
	$('#labourTableDiv').show();
	$("#labourSubCost").html(addCommas(labourSubTotalCost.toFixed(2)));
	$("#labourDiscountAmount").html(addCommas(labourDiscountCost.toFixed(2)));
	$("#labourTaxableAmount").html(addCommas(labourTaxCost.toFixed(2)));
	$("#totalLabourCost").html(addCommas(labourTotalCost.toFixed(2)));
}

function setDealerServiceEntriesPart(data){
	var dealerServiceEntriesPart 	= data.dealerServiceEntriesPart;
	$("#dealerServiceEntriesPartTable").empty();
	var partSubTotalCost 	= 0;
	var partDiscountCost 	= 0;
	var partTaxCost 		= 0;
	var partTotalCost 		= 0;
	
	if((dealerServiceEntriesPart != undefined || dealerServiceEntriesPart != null) && dealerServiceEntriesPart.length >0){
		if(dealerServiceEntriesPart[0].partDiscountTaxTypeId == 1){
			$('#partDisType').text('');
			$('#partDisType').text('%');
			$('#partTaxType').text('');
			$('#partTaxType').text('%');
		}else{
			$('#partTaxType').html('');
			$('#partTaxType').html('&#x20B9');
			$('#partDisType').html('');
			$('#partDisType').html('&#x20B9');
		}
		for(var index = 0 ; index < dealerServiceEntriesPart.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit' style='font-size: 15px;'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit' style='text-align: left; font-size: 15px;'><a style='color:black' title= 'Part Last Occurred ON "+dealerServiceEntriesPart[index].invoiceDateStr+" Odometer: "+dealerServiceEntriesPart[index].vehicleOdometer+"   IN DSE-"+dealerServiceEntriesPart[index].lastOccurredDseNumber +"'>"+ dealerServiceEntriesPart[index].partNumber  +"-  "+ dealerServiceEntriesPart[index].partName  +"</a></td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partQuantity.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partEchCost.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partDiscount  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partTax  +" </td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ addCommas((dealerServiceEntriesPart[index].partTotalCost).toFixed(2))  +"</td>");
			partSubTotalCost	+= (Number(dealerServiceEntriesPart[index].partQuantity)*Number(dealerServiceEntriesPart[index].partEchCost) );
			partDiscountCost	+= Number(dealerServiceEntriesPart[index].partDiscountCost);
			partTaxCost			+= Number(dealerServiceEntriesPart[index].partTaxCost);
			partTotalCost 		+= Number(dealerServiceEntriesPart[index].partTotalCost);
			
			$('#dealerServiceEntriesPartTable').append("<tr id='penaltyID"+dealerServiceEntriesPart[index].DealerServiceEntriesPartId+"' >" + columnArray.join(' ') + "</tr>");
		}
		var columnArray1 = new Array();
		columnArray1.push("<td class='fit' colspan='6' style='text-align: left; font-size: 18px;'>  Total Part Cost</td>");
		columnArray1.push("<td class='fit' colspan='1' style='text-align: right; font-size: 18px;'>"+ addCommas(partTotalCost.toFixed(2)) +"</td>");
		$('#dealerServiceEntriesPartTable').append("<tr style='background: lightseagreen;color: white;'>" + columnArray1.join(' ') + "</tr>");
		columnArray = [];
	}else{
		$("#partTable").hide();
	}
	$('#partTableDiv').show();
	$("#partSubCost").html(addCommas(partSubTotalCost.toFixed(2)));
	$("#partDiscountAmount").html(addCommas(partDiscountCost.toFixed(2)));
	$("#partTaxableAmount").html(addCommas(partTaxCost.toFixed(2)));
	$("#totalPartCost").html(addCommas(partTotalCost.toFixed(2)));
}
function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}
