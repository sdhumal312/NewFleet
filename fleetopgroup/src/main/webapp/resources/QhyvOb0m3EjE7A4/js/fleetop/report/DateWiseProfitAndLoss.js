var totalIncomeAmt = 0;
var totalExpenseAmt = 0;

var TRANSACTION_TYPE_FUEL					= 1;
var	TRANSACTION_TYPE_SERVICE_ENTRIES		= 2;
var	TRANSACTION_TYPE_WORK_ORDER				= 3;
var	TRANSACTION_TYPE_TRIPSHEET				= 4;
var	TRANSACTION_TYPE_RENEWAL				= 5;
var	TRANSACTION_TYPE_TYRE					= 6;
var	TRANSACTION_TYPE_BATTERY				= 7;
var	TRANSACTION_TYPE_VEHICLE_EMI			= 8;
var	TRANSACTION_TYPE_DRIVER_SALARY			= 9;
var	TRANSACTION_TYPE_UREA					= 10;
var	TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES	= 11;
var INSPECTION_PENALTY 						=13;
var resultData		= null;
var DRIVER_MONTHLY_SALARY					= false;


$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        format:'DD-MM-YYYY',
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

),
$(function() {
    $("#vid").select2()
});

$(document).ready(function() {
	
	var doc = new jsPDF();
	var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};

	$('#exportExcelBtn').click(function () {
		var HTML_Width = $("#div_print").width();
	    var HTML_Height = $("#div_print").height();
	    var top_left_margin = 15;
	    var PDF_Width = HTML_Width + (top_left_margin * 2);
	    var PDF_Height = (PDF_Width * 1.5) + (top_left_margin * 2);
	    var canvas_image_width = HTML_Width;
	    var canvas_image_height = HTML_Height;

	    var totalPDFPages = Math.ceil(HTML_Height / PDF_Height) - 1;

	    html2canvas($("#div_print")[0]).then(function (canvas) {
	        var imgData = canvas.toDataURL("image/jpeg", 1.0);
	        var pdf = new jsPDF('p', 'pt', [PDF_Width, PDF_Height]);
	        pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin, canvas_image_width, canvas_image_height);
	        for (var i = 1; i <= totalPDFPages; i++) { 
	            pdf.addPage(PDF_Width, PDF_Height);
	            pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
	        }
	        pdf.save("DateWiseReport.pdf");
	    });
	});
	
	
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
    )
});

$(document).ready(
		function($) {
			$('button[id=btn-save]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				jsonObject["vid"] 				=  $('#ReportSelectVehicle').val();
				
				
				if(Number($('#ReportSelectVehicle').val()) <= 0){
					showMessage('errors', 'Please Select Vehicle!');
					return false;
				}
				showLayer();
				$.ajax({
			             url: "getVehicleProfitAndLossWithinRange",//getDateWiseVehicleProfitAndLoss
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 resultData	= data;
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
	if(data.expenseDtoList != null && data.expenseDtoList.length > 0){
		//setHeaderData(data.company);
		$('#Vehicle_registration').html('');
		$('#vehicleGroup').html('');
		$('#location').html('');
		$('#daysInMonth').html('');
		$('#noOftrip').html('');
		$('#noOftripDays').html('');
		$('#noOfDaysIdeal').html('');
		$('#totalRunKM').html('');
		$('#EPK').html('');
		$('#month').html('');
		setExpenseData(data);
		if(data.incomeList != null && data.incomeList.length > 0 ){
			setIncomeData(data);
			$('#balance').html((totalIncomeAmt - totalExpenseAmt).toFixed(2));
			$('#profitAndLossTable').show();
		} else {
			$("#incomeBody").empty();
			$('#balance').html(( - totalExpenseAmt).toFixed(2));
			$('#profitAndLossTable').show();
		}
		
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

function setExpenseData(data) {
	if(data.vehicle != undefined && data.vehicle != null){
		/*$('#vehicleInfo').show();
		$('#tripInfo').show();

		$('#Vehicle_registration').html(data.vehicle.vehicle_registration);
		$('#vehicleGroup').html(data.vehicle.vehicle_Group);
		$('#location').html(data.vehicle.vehicle_Location);
		$('#daysInMonth').html(data.daysInMonth);
		$('#noOftrip').html(data.noOftrip);
		$('#noOftripDays').html(data.noOftripDays);
		$('#noOfDaysIdeal').html(data.noOfDaysIdeal);
		$('#totalRunKM').html(data.totalRunKM);
		$('#EPK').html((data.EPK).toFixed(2));*/
		//$('#month').html($('#monthRangeSelector').val());
		
		var noOfTrips = "";
		if(data.noOftrip != undefined){
			 noOfTrips = data.noOftrip;
		}
		
		var dateRange 		= $('#reportrange').val().split("to");
		
		var startDate 		= dateRange[0].split("-").reverse().join("-").replace(/ /g,'');
		var endDate 		= dateRange[1].split("-").reverse().join("-").replace(/ /g,'');
		var newDateRange 	= startDate.split("-").reverse().join("-") +" To "+ endDate.split("-").reverse().join("-");
		
		$('#selectedReportDetails').empty();
			
		var columnArray = new Array();
		var columnArray2 = new Array();
		columnArray.push("<td class='fit tdPadding'>Vehicle: &emsp; <a>"+data.vehicle.vehicle_registration+"</a></td>");
		columnArray.push("<td class='fit tdPadding'>Vehicle Group: &emsp; <a>"+data.vehicle.vehicle_Group  +"</a></td>");
		columnArray.push("<td class='fit tdPadding'>Location: &emsp; <a>" + data.vehicle.vehicle_Location +"</a></td>");
		columnArray.push("<td class='fit tdPadding'>Total Days: &emsp; <a>" + data.daysInMonth +"</a></td>");
		
		columnArray2.push("<td class='fit tdPadding'>Date Range: &emsp; <a>" + newDateRange +"</a></td>");
		columnArray2.push("<td class='fit tdPadding'>No Of Trip And Days: &emsp; <a>" + noOfTrips +"</a> Trips <a>" + data.noOftripDays +"</a> Days</td>");
		columnArray2.push("<td class='fit tdPadding'>No Of Days Ideal: &emsp; <a>" +data.noOfDaysIdeal +"</a> Total Km Run <a>" + data.totalRunKM +"</a></td>");
		columnArray2.push("<td class='fit tdPadding'>EPK:  &emsp; <a>" + (data.EPK).toFixed(2) +"</a></td>");
		
		$('#selectedReportDetails').append("<tr class='fontSize'  id='tr1' >" + columnArray.join(' ') + "</tr> <br>");
		$('#selectedReportDetails').append("<tr class='fontSize'  id='tr2' >" + columnArray2.join(' ') + "</tr>");
		
		columnArray = [];
		
	}
	
	var expenseDtoList	= null;
	var vehicle				= null;  
	DRIVER_MONTHLY_SALARY 	= data.showDriverMonthlySalary; 
	
	if(data.expenseDtoList != undefined) {
		$("#reportHeader").html("Vehicle Wise Profit & Loss Report");

		$("#expenseBody").empty();
		expenseDtoList	= data.expenseDtoList;
		
		if(data.vehicle != null || data.vehicle != undefined){
			vehicle			= data.vehicle; 						
		}
		
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		totalExpenseAmt = 0;
		var srNo = 1;
		var tripExpenseTotal = 0;
		var renewalTotal	 = 0;
		var tripExpenseRowAdded	= false;
		var renewalRowAdded	= false;
		for(var i = 0; i < expenseDtoList.length; i++ ) {
			var expenseName	= '';
			
			totalExpenseAmt	+= expenseDtoList[i].expenseAmount;
			
			if(expenseDtoList[i].expenseType == TRANSACTION_TYPE_TRIPSHEET){
				expenseName	= expenseDtoList[i].expenseTypeName;
				tripExpenseTotal += expenseDtoList[i].expenseAmount;
			}else if(expenseDtoList[i].expenseType == TRANSACTION_TYPE_RENEWAL){
				expenseName	= expenseDtoList[i].expenseTypeName;
				renewalTotal += expenseDtoList[i].expenseAmount;
			}else{
				expenseName	= expenseDtoList[i].expenseTypeName;
			}
			if(!tripExpenseRowAdded && expenseDtoList[i].expenseType == TRANSACTION_TYPE_TRIPSHEET){
				tripExpenseRowAdded	= true;	
				var tripRow = '<tr>'
					//+'<td class="fit" >'+srNo+'</td>'
					+'<td colspan="2" style="text-align: left; padding-left: 10px;height: 22px;font-weight: bold;text-align: center;">TRIPSHEET EXPENSES</td>'
					+'</tr>';
				
				$('#expenseBody').append(tripRow);
				
				var tr2 = '<tr>'
					+'<td colspan="2">'
					+'<table id="tripTable" style="width: 100%" class="table-hover table-bordered">'
					+'<tbody id="tripExpenseBody">'
					+'</tbody>'
					+'</table>'
					+'</td>'
					+'</tr>';
				$('#expenseBody').append(tr2);
			}
			
			if(!renewalRowAdded && expenseDtoList[i].expenseType == TRANSACTION_TYPE_RENEWAL){
				renewalRowAdded	= true;	
				var tripRow = '<tr>'
					//+'<td class="fit" >'+srNo+'</td>'
					+'<td colspan="2" style="text-align: left; padding-left: 10px;height: 22px;font-weight: bold;text-align: center;">Compliance Charges</td>'
					+'</tr>';
				
				$('#expenseBody').append(tripRow);
				
				var tr2 = '<tr>'
					+'<td colspan="2">'
					+'<table id="renewalTable" style="width: 100%" class="table-hover table-bordered">'
					+'<tbody id="renewalBody">'
					+'</tbody>'
					+'</table>'
					+'</td>'
					+'</tr>';
				$('#expenseBody').append(tr2);
			}
			
			
			if(expenseDtoList[i].expenseType == TRANSACTION_TYPE_TRIPSHEET){
				var tr = '<tr>'
					+'<td style="text-align: left; padding-left: 10px;">'+expenseName+'</td>'
					+'<td class="'+expenseDtoList[i].expenseType+'" style="text-align: right; padding-right: 10px;"><a href="#" onclick="getExpenseModelData('+expenseDtoList[i].expenseType+', '+expenseDtoList[i].expenseId+', '+expenseDtoList[i].vid+');">'+(expenseDtoList[i].expenseAmount).toFixed(2)+'<a></td>'
					+'</tr>';
				$('#tripExpenseBody').append(tr);
			}else if(expenseDtoList[i].expenseType == TRANSACTION_TYPE_RENEWAL){
				var tr = '<tr>'
					+'<td style="text-align: left; padding-left: 10px;">'+expenseName+'</td>'
					+'<td class="'+expenseDtoList[i].expenseType+'" style="text-align: right; padding-right: 10px;"><a href="#" onclick="getExpenseModelData('+expenseDtoList[i].expenseType+', '+expenseDtoList[i].expenseId+', '+expenseDtoList[i].vid+');">'+(expenseDtoList[i].expenseAmount).toFixed(2)+'<a></td>'
					+'</tr>';
				$('#renewalBody').append(tr);
			}else{
				var tr = '<tr>'
					//+'<td class="fit" >'+srNo+'</td>'
					+'<td style="text-align: left; padding-left: 10px;">'+expenseName+'</td>'
					+'<td class="'+expenseDtoList[i].expenseType+'" style="text-align: right; padding-right: 10px;"><a href="#" onclick="getExpenseModelData('+expenseDtoList[i].expenseType+', '+expenseDtoList[i].expenseId+', '+expenseDtoList[i].vid+');">'+(expenseDtoList[i].expenseAmount).toFixed(2)+'<a></td>'
					+'</tr>';
				$('#expenseBody').append(tr);
			}
			
			srNo++;
		}
		
		var totalExpTr = '<tr><th style="font-size: 14px;">Total</th><th style="font-size: 14px;text-align: right; padding-right: 10px;">'+(totalExpenseAmt).toFixed(2)+'</th></tr>'
		$('#expenseBody').append(totalExpTr);
		
		$('#tripTable tr:last').after('<tr><th colspan="2" style="font-size: 14px;text-align: right; padding-right: 10px;">'+(tripExpenseTotal).toFixed(2)+'</th></tr>');
		$('#renewalTable tr:last').after('<tr><th colspan="2" style="font-size: 14px;text-align: right; padding-right: 10px;">'+(renewalTotal).toFixed(2)+'</th></tr>');

		
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		$("#printPdf").removeClass("hide");
	} 
}

function setIncomeData(data){
	
	var incomeList	= null;
	if(data.incomeList != undefined) {
	//	$("#firstTableHeader").html("Vehicle Wise Profit & Loss Report");

		$("#incomeBody").empty();
		incomeList	= data.incomeList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		totalIncomeAmt = 0;
		var srNo = 1;
		for(var i = 0; i < incomeList.length; i++ ) {
			var incomeName	= '';
			
			totalIncomeAmt	+= incomeList[i].incomeAmount;
			
			incomeName	= incomeList[i].incomeName;
			
			var tr = '<tr>'
				//+'<td class="fit" >'+srNo+'</td>'
				+'<td style="text-align: left; padding-left: 10px;">'+incomeName+'</td>'
				+'<td style="text-align: right;padding-right: 10px;"><a href="#" onclick="getIncomeModelData('+incomeList[i].incomeId+', '+incomeList[i].vid+');">'+(incomeList[i].incomeAmount).toFixed(2)+'</a></td>'
				+'</tr>';
			$('#incomeBody').append(tr);
			srNo++;
		}
		
		var totalIncTr = '<tr><th style="font-size: 14px;">Total</th><th style="font-size: 14px; text-align: right; padding-right: 10px;">'+(totalIncomeAmt).toFixed(2)+'</th></tr>'
		$('#incomeBody').append(totalIncTr);
		
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		$("#printPdf").removeClass("hide");
	} 

}

function	getExpenseModelData(expenseType, expenseId, vid){

		var jsonObject			= new Object();
		
		
		jsonObject["expenseType"] 		=  expenseType;
		jsonObject["expenseId"] 		=  expenseId;
		jsonObject["vid"] 				=  resultData.vid;
		jsonObject["fromDate"] 			=  resultData.fromDate;
		jsonObject["toDate"] 			=  resultData.toDate;
		
		
		showLayer();
		$.ajax({
			url: "getVehicleExpenseDetailsOfDateRangeExpenseId",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success : function(data) {
				setExpenseModelData(data);
				hideLayer();
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				debugger;
			}
		});
	

}

function setExpenseModelData(data){
	if(data.expenseType == TRANSACTION_TYPE_FUEL){
		setFuelDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_SERVICE_ENTRIES){
		setServiceEntriesDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_WORK_ORDER){
		setWorkOrdersDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_TRIPSHEET){
		setTripSheetDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_RENEWAL){
		setRenewalData(data);
	}else if(data.expenseType == TRANSACTION_TYPE_TYRE){
		setTyreDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_BATTERY){
		setBatteryDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_VEHICLE_EMI){
		setVehicleEmiDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_DRIVER_SALARY){
		setDriverSalaryDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_UREA){
		setUreaDataToModel(data);
	}else if(data.expenseType == TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES){
		setDealerServiceEntriesDataToModel(data);
	}else if(data.expenseType == INSPECTION_PENALTY){
		setInspectionModel(data);
	}

}

function setInspectionModel(data) {
	var inspectionPenalty = data.inspectionPenalty;
	if(data.inspectionPenalty != null && data.inspectionPenalty.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Inspection</th>'
			+'<th>Vehicle</th>'
			+'<th>Completed Date</th>'
			+'<th style="text-align: right;">Penalty Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< inspectionPenalty.length; i++){
			grandTotal += inspectionPenalty[i].totalPenalty;
				
			curl = '<a target="_blank" href="/viewInspectVehicleDetails?vid='+inspectionPenalty[i].vid+'&ID='+inspectionPenalty[i].completionDetailsId+'">'+"View"+'</a>';
			
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+inspectionPenalty[i].vehicle_registration+'</td>'
					+'<td>'+inspectionPenalty[i].completionDateTimeStr+'</td>'
					+'<td style="text-align: right;">'+(inspectionPenalty[i].totalPenalty).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "4">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
}



function setVehicleEmiDataToModel(data){
	var vehicleEMIList = data.vehicleEMIList;
	var downPaymentList = data.downPaymentList;
	
	if(data.vehicleEMIList != null && data.vehicleEMIList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>EMI Paid Date</th>'
			+'<th>Monthly EMI Amount</th>'
			+'<th style="text-align: right;">EMI Paid Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< vehicleEMIList.length; i++){
			grandTotal += vehicleEMIList[i].emiPaidAmount;
			var startDate = new Date(vehicleEMIList[i].emiPaidDateStr);
			//var endDate = new Date(vehicleEMIList[i].loanEndDate);
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+vehicleEMIList[i].emiPaidDateStr+'</td>'
					+'<td>'+(vehicleEMIList[i].monthlyEmiAmount).toFixed(2)+'</td>'
					+'<td style="text-align: right;">'+(vehicleEMIList[i].emiPaidAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
	
	if(data.downPaymentList != null && data.downPaymentList.length > 0){
		$('#dataExpenseTable1').show();
		$("#tableExpenseBody1 tr").remove();
		$("#expenseHead1 tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal1 = 0;
		var trHead1 =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>DownPayment Date/Loan Start Date</th>'
			+'<th>Monthly EMI Amount</th>'
			+'<th style="text-align: right;">DownPayment Paid Amount</th>'
		+'</tr>';
		$('#expenseHead1').append(trHead1);
		for(var i = 0; i< downPaymentList.length; i++){
			grandTotal1 += downPaymentList[i].downPaymentAmount;
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+downPaymentList[i].loanStartDateStr+'</td>'
					+'<td>'+downPaymentList[i].monthlyEmiAmount+'</td>'
					+'<td style="text-align: right;">'+(downPaymentList[i].downPaymentAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody1').append(tr);
			srNo++;
		}
		var totalTr1 =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal1).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody1').append(totalTr1);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable1').hide();
		showMessage('info', 'No DownPayment Details found !');
	}

}


function setTyreDataToModel(data){
	if(data.vehicleTyreMountCostInPLReport != undefined && data.vehicleTyreMountCostInPLReport == true || data.vehicleTyreMountCostInPLReport==="true")	{
		setMountedTyreList(data);
	}else{
	var tyreList = data.tyreList;
	if(data.tyreList != null && data.tyreList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Tyre No</th>'
			+'<th>Cost Per KM</th>'
			+'<th>No Of KM Runs</th>'
			+'<th style="text-align: right;">Amount For Month</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< tyreList.length; i++){
			grandTotal += data.totalRunKM * tyreList[i].costPerKM;
			if(tyreList[i].tyre_ID != null){
				curl = '<a target="_blank" href="showTyreInfo.in?Id='+tyreList[i].tyre_ID+'">'+"TS-"+tyreList[i].tyre_SERIAL_NO+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+tyreList[i].costPerKM+'</td>'
					+'<td>'+data.totalRunKM+'</td>'
					+'<td style="text-align: right;">'+(data.totalRunKM * tyreList[i].costPerKM).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "4">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
}
}


function setBatteryDataToModel(data){
	var batteryList = data.batteryList;
	
	console.log('batteryList : ', batteryList);
	if(data.batteryList != null && data.batteryList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Battery No</th>'
			+'<th>Cost Per Day</th>'
			+'<th>No Of In Month</th>'
			+'<th style="text-align: right;">Amount For Month</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< batteryList.length; i++){
			grandTotal += data.noOfDaysInMOnth * batteryList[i].batteryAmount;
			if(batteryList[i].batteryId != null){
				curl = '<a target="_blank" href="showBatteryInformation.in?Id='+batteryList[i].batteryId+'">'+"TS-"+batteryList[i].batterySerialNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+batteryList[i].batteryAmount+'</td>'
					+'<td>'+data.noOfDaysInMOnth+'</td>'
					+'<td style="text-align: right;">'+(data.noOfDaysInMOnth * batteryList[i].batteryAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "4">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}

}



function setDriverSalaryDataToModel(data){
	var attList = data.attList;
	
	console.log('attList : ', attList);
	if(data.attList != null && data.attList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Driver Name</th>'
			+'<th>TripSheet Number</th>'
			+'<th style="text-align: right;">Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< attList.length; i++){
			grandTotal += attList[i].driverSalary;
			if(attList[i].tripSheetId != null){
				curl = '<a target="_blank" href="getTripsheetDetails.in?tripSheetID='+attList[i].tripSheetId+'">'+"TS-"+attList[i].tripSheetNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+attList[i].driver_NAME+'</td>'
					+'<td>'+curl+'</td>'
					+'<td style="text-align: right;">'+(attList[i].driverSalary).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		if(DRIVER_MONTHLY_SALARY)		{
			showMessage('info', 'Driver Salary is Fixed!');
		}
		else{
			$('#vehicleExpenseModel').modal('show');
		}
		
		
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}

}

function setRenewalData(data){
	var renewal = data.renewal;
	var date1 = new Date(renewal.renewal_from);
	var date2 = new Date(renewal.renewal_to);
	var timeDiff = Math.abs(date2.getTime() - date1.getTime());
	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	
	console.log('date1 : ', date1);
	
	if(renewal != undefined && renewal != null){
		console.log('data :inswsdfsfas ', renewal);
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var trHead =' <tr>'
			+'<th>Renewal Number</th>'
			+'<th class="fit" >Renewal Amount</th>'
			+'<th>From Date</th>'
			+'<th>To Date</th>'
			+'<th style="text-align: right;">Amount Of Month((Renewal Amount/No Of Days) * No Of Days In Month)</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		
		if(renewal.renewal_id != null){
			curl = '<a target="_blank" href="showRenewalReminder.in?renewal_id='+renewal.renewal_id+'">'+"RR-"+renewal.renewal_R_Number+'</a>';
		}
		var tr =' <tr>'
				+'<td>'+curl+'</td>'
				+'<td class="fit">'+renewal.renewal_Amount+'</td>'
				+'<td>'+renewal.renewal_from+'</td>'
				+'<td>'+renewal.renewal_to+'</td>'
				+'<td style="text-align: right;">'+(data.amountForMonth).toFixed(2)+'</td>'
			+'</tr>';
		$('#tableExpenseBody').append(tr);
		
		$('#vehicleExpenseModel').modal('show');
	}
}

function setTripSheetDataToModel(data){
	var expenseList = data.expenseList;
	if(data.expenseList != null && data.expenseList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>TripSheet Number</th>'
			+'<th>Trip Open Date</th>'
			+'<th>Trip Close Date</th>'
			+'<th style="text-align: right;">Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< expenseList.length; i++){
			grandTotal += expenseList[i].expenseAmount;
			if(expenseList[i].tripSheetId != null){
				curl = '<a target="_blank" href="getTripsheetDetails.in?tripSheetID='+expenseList[i].tripSheetId+'">'+"TS-"+expenseList[i].tripSheetNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+expenseList[i].fromDate+'</td>'
					+'<td>'+expenseList[i].toDate+'</td>'
					+'<td style="text-align: right;">'+(expenseList[i].expenseAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "4">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}

}


function setWorkOrdersDataToModel(data){
	var workOrdersList = data.workOrdersList;
	if(data.workOrdersList != null && data.workOrdersList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>WO Number</th>'
			+'<th>SE Date</th>'
			+'<th style="text-align: right;">WO Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< workOrdersList.length; i++){
			grandTotal += workOrdersList[i].totalworkorder_cost;
			if(workOrdersList[i].workorders_id != null){
				curl = '<a target="_blank" href="showWorkOrder?woId='+workOrdersList[i].workorders_id+'">'+"WO-"+workOrdersList[i].workorders_Number+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+workOrdersList[i].completed_date+'</td>'
					+'<td style="text-align: right;">'+(workOrdersList[i].totalworkorder_cost).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}

}


function setServiceEntriesDataToModel(data){

	var serviceList = data.serviceList;
	if(data.serviceList != null && data.serviceList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>SE Number</th>'
			+'<th>SE Date</th>'
			+'<th style="text-align: right;">SE Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< serviceList.length; i++){
			grandTotal += serviceList[i].totalservice_cost;
			if(serviceList[i].serviceEntries_id != null){
				curl = '<a target="_blank" href="ServiceEntriesParts.in?SEID='+serviceList[i].serviceEntries_id+'">'+"SE-"+serviceList[i].serviceEntries_Number+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+serviceList[i].completed_date+'</td>'
					+'<td style="text-align: right;">'+(serviceList[i].totalservice_cost).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}

}

function setFuelDataToModel(data){
	var fuelList = data.fuelList;
	if(data.fuelList != null && data.fuelList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Fuel Number</th>'
			+'<th>Fuel Date</th>'
			+'<th>Fuel Reference Number</th>'
			+'<th style="text-align: right;">Fuel Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< fuelList.length; i++){
			grandTotal += fuelList[i].fuel_amount;
			if(fuelList[i].fuel_id != null){
				curl = '<a target="_blank" href="showFuel.in?FID='+fuelList[i].fuel_id+'">'+"F-"+fuelList[i].fuel_Number+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+fuelList[i].fuel_date+'</td>'
					+'<td>'+fuelList[i].fuel_reference+'</td>'
					+'<td style="text-align: right;">'+(fuelList[i].fuel_amount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "4">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
}

function getIncomeModelData(incomeId, vid){
	var jsonObject			= new Object();
	
	jsonObject["incomeId"] 			=  incomeId;
	jsonObject["vid"] 				=  resultData.vid;
	jsonObject["fromDate"] 			=  resultData.fromDate;
	jsonObject["toDate"] 			=  resultData.toDate;
	
	showLayer();
	$.ajax({
		 url: "getIncomDetailsWithinRangeByIncomeId",
         type: "POST",
         dataType: 'json',
         data: jsonObject,
		success : function(data) {
			renderIncomeModelData(data);
			hideLayer();
		},
		error : function(XMLHttpRequest, textStatus,
				errorThrown) {
			debugger;
		}
	});
	
}

function renderIncomeModelData(data){
	var incomeList	= data.incomeList;
	if(data.incomeList != null && data.incomeList.length > 0){
		$('#dataIncomeTable').show();
		$("#tableIncomeBody tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		for(var i = 0; i< data.incomeList.length; i++){
			grandTotal += incomeList[i].incomeAmount;
			if(incomeList[i].tripSheetId != null){
				curl = '<a target="_blank" href="getTripsheetDetails.in?tripSheetID='+incomeList[i].tripSheetId+'">'+"TS-"+incomeList[i].tripSheetNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+incomeList[i].incomeName+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+incomeList[i].fromDate+'</td>'
					+'<td>'+incomeList[i].toDate+'</td>'
					+'<td style="text-align: right;">'+(incomeList[i].incomeAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableIncomeBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "5">Total : </th>'
			+'<th style="text-align: right;">'+grandTotal.toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableIncomeBody').append(totalTr);
		$("#exportExcelBtn").removeClass("hide");
		$("#printPdf").removeClass("hide");
		$('#vehicleIncomeModel').modal('show');
	}else{
		$('#dataIncomeTable').hide();
		showMessage('info', 'No record found !');
		$("#exportExcelBtn").addClass("hide");
		$("#printPdf").addClass("hide");
		
	}
}

function setUreaDataToModel(data){
	var ureaList = data.ureaList;
	if(data.ureaList != null && data.ureaList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Urea Number</th>'
			+'<th>Urea Date</th>'
			+'<th style="text-align: right;">Urea Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< ureaList.length; i++){
			grandTotal += ureaList[i].ureaAmount;
			if(ureaList[i].ureaEntriesId != null){
				curl = '<a target="_blank" href="showUreaDetails?Id='+ureaList[i].ureaEntriesId+'">'+"U-"+ureaList[i].ureaEntriesNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+ureaList[i].ureaDateStr+'</td>'
					+'<td style="text-align: right;">'+(ureaList[i].ureaAmount).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
}
function setDealerServiceEntriesDataToModel(data){

	var dealerServiceList = data.dealerServiceList;
	if(data.dealerServiceList != null && data.dealerServiceList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>SE Number</th>'
			+'<th>SE Date</th>'
			+'<th style="text-align: right;">DSE Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		for(var i = 0; i< dealerServiceList.length; i++){
			grandTotal += dealerServiceList[i].totalInvoiceCost;
			if(dealerServiceList[i].dealerServiceEntriesId != null){
				curl = '<a target="_blank" href="showDealerServiceEntries.in?dealerServiceEntriesId='+dealerServiceList[i].dealerServiceEntriesId+'">'+"SE-"+dealerServiceList[i].dealerServiceEntriesNumber+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+dealerServiceList[i].completedDateStr+'</td>'
					+'<td style="text-align: right;">'+(dealerServiceList[i].totalInvoiceCost).toFixed(2)+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
}

	function setMountedTyreList(data){
		if(data.tyreList != undefined && data.tyreList != null && data.tyreList.length > 0){
		$('#dataExpenseTable').show();
		$("#tableExpenseBody tr").remove();
		$("#expenseHead tr").remove();
		var srNo = 1;
		var curl = "";
		var grandTotal = 0;
		var trHead =' <tr>'
			+'<th class="fit" >Sr NO.</th>'
			+'<th>Tyre No</th>'
			+'<th>Assign Date</th>'
			+'<th>Amount</th>'
		+'</tr>';
		$('#expenseHead').append(trHead);
		var tyreList=data.tyreList;
		for(var i = 0; i< tyreList.length; i++){
			grandTotal +=  tyreList[i].tyre_AMOUNT;
			if(tyreList[i].tyre_ID != null){
				curl = '<a target="_blank" href="showTyreInfo.in?Id='+tyreList[i].tyre_ID+'">'+"T-"+tyreList[i].tyre_NUMBER+'</a>';
			}
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+tyreList[i].tyre_ASSIGN_DATE+'</td>'
					+'<td>'+tyreList[i].tyre_AMOUNT+'</td>'
				+'</tr>';
			$('#tableExpenseBody').append(tr);
			srNo++;
		}
		var totalTr =' <tr>'
			+'<th colspan = "3">Total : </th>'
			+'<th style="text-align: right;">'+(grandTotal).toFixed(2)+'</th>'
		+'</tr>';
		
		$('#tableExpenseBody').append(totalTr);
		
		$('#vehicleExpenseModel').modal('show');
	}else{
		$('#dataExpenseTable').hide();
		showMessage('info', 'No record found !');
	}
	}