var tripExpenseRowAdded = false;
var renewalRowAdded = false;
var misseleniousExpRowAdded = false;
var summaryExpRowAdded = false;
var totalExpRowAdded = false;
var totalIncomeAmt = 0;
var totalExpenseAmt = 0;
var vehicleOneData  = false;
var vehicleTwoData 	  = false;
var vehicleThreeData  = false;
var tripExpenses = null;
var renewalList = null;
var missExpMap = null;
var vehicle1Data = null;
var vehicle2Data = null;
var vehicle3Data = null;
var vehicle4Data = null;
var vehicle5Data = null;
var vehicleList = null;
var addedExpenses = new Array();
var allExpensesArr = new Array();
var notAddedExpenses = new Array();
var expenseMap = new Map();
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
var resultData		= null;
var DRIVER_MONTHLY_SALARY					= false;

function setTripSheetExpenses(tripSheetExpenses){
	var jsonObject = JSON.parse(tripSheetExpenses);
	tripExpenses = jsonObject;
	for(var i =0; i < tripExpenses.length; i++){
		allExpensesArr.push(tripExpenses[i].expenseID);
	}
}

function setRenewalExpenses(renewalListObj){
	var jsonObject = JSON.parse(renewalListObj);
	renewalList = jsonObject;
	for(var i =0; i < renewalList.length; i++){
		allExpensesArr.push(renewalList[i].renewal_id);
	}
}

function setMisseleniousExpMap(expMap){
	var jsonObject = JSON.parse(expMap);
	missExpMap = jsonObject;
	$.each(missExpMap, function (key, value) {
		allExpensesArr.push(key);
	});
}

function setVehicleList(vehicleListObj){
	var jsonObject = JSON.parse(vehicleListObj);
	var option = '<option></option>';
	setVehicleLIstDropDown(jsonObject);
}

function setVehicleLIstDropDown(vehicleLst){
	$('#tablevehicleBody tr').remove();
	vehicleList = vehicleLst;
	var option = '<option></option>';
	if(vehicleList != undefined && vehicleList != null){
		$('#vehicleCount').html(vehicleList.length);
		for(var i= 0; i< vehicleList.length;i++){
			option += '<option value="'+vehicleList[i].vid+'">'+vehicleList[i].vehicle_registration+'</option>'
			var tr = '<tr>'
				  +'<td>'+(i+1)+'</td>'
				  +'<td><a target="_blank" href="showVehicle.in?vid='+vehicleList[i].vid+'">'+vehicleList[i].vehicle_registration+'</a></td>'
			   	  +'</tr>';
			$('#tablevehicleBody').append(tr);
		}
	}else{
		$('#vehicleCount').html(0);
		$('#vehicleListModal').modal('hide');
	}
	
	$('#vehicleOneSearch').html(option);
	$('#vehicleTwoSearch').html(option);
	$('#vehicleThreeSearch').html(option);
	$('#vehicleFourSearch').html(option);
	$('#vehicleFiveSearch').html(option);
	
	$('#vehicleOneSearch').select2({
		 placeholder: "Select Vehicle",
		    allowClear: true
	});
	$('#vehicleTwoSearch, #vehicleThreeSearch, #vehicleFourSearch, #vehicleFiveSearch').select2({
		 placeholder: "Compare Vehicle",
		    allowClear: true
	});
}

function showVehicleList(){
	$('#vehicleListModal').modal('show');
}

$(function() {
    function a(a, b) {
        $("#compareRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(3, "months").add(1, "days"), moment()), $("#compareRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
        ranges: {
            "Last 3 Month": [moment().subtract(3, "months").add(1, "days"), moment()],
            "Last 6 Month": [moment().subtract(6, "months").add(1, "days"), moment()],
            "Last 12 Month": [moment().subtract(12, "months").add(1, "days"), moment()],
        }
    }
    , a)
}

),

$(document).ready(function() {
	$('#exportPDF').click(function () {
		var HTML_Width = $("#reportDiv").width();
	    var HTML_Height = $("#reportDiv").height();
	    var top_left_margin = 15;
	    var PDF_Width = HTML_Width + (top_left_margin * 2);
	    var PDF_Height = (PDF_Width * 1.5) + (top_left_margin * 2);
	    var canvas_image_width = HTML_Width;
	    var canvas_image_height = HTML_Height;

	    var totalPDFPages = Math.ceil(HTML_Height / PDF_Height) - 1;

	    html2canvas($("#reportDiv")[0]).then(function (canvas) {
	        var imgData = canvas.toDataURL("image/jpeg", 1.0);
	        var pdf = new jsPDF('p', 'pt', [PDF_Width, PDF_Height]);
	        pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin, canvas_image_width, canvas_image_height);
	        for (var i = 1; i <= totalPDFPages; i++) { 
	            pdf.addPage(PDF_Width, PDF_Height);
	            pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
	        }
	        pdf.save("VehicleCOmparisionReport.pdf");
	    });
	});
	
	
	$("#vehicleTypeId").select2( {
        ajax: {
            url:"getVehicleType.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vtype, slug: t.slug, id: t.tid
                        }
                    }
                    )
                }
            }
        }
    }),$("#routeId").select2( {
       ajax: {
           url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", data:function(e) {
               return {
                   term: e
               }
           }
           , results:function(e) {
               return {
                   results:$.map(e, function(e) {
                       return {
                           text: e.routeNo+" "+e.routeName, slug: e.slug, id: e.routeID
                       }
                   }
                   )
               }
           }
       }
   }
   )
});

function getVehicleExpenseComparisionData(calledFromVehicle, id){
		
		showLayer();
		var jsonObject								= new Object();				
		jsonObject["routeId"] 						= $('#routeId').val();
		jsonObject["dateRange"] 					= $('#compareRange').val();
		jsonObject["companyId"] 					= $('#companyId').val();
		jsonObject["userId"] 						= $('#userId').val();
		jsonObject["calledFromVehicle"] 			= calledFromVehicle;
		if(document.getElementById('allExpenses').checked){
			jsonObject["defaultFilter"] 				= true;
			$('#defaultFilter').val(true);
		}else{
			jsonObject["defaultFilter"] 				= false;
			$('#defaultFilter').val(false);
		}
		
		if(!calledFromVehicle){
			jsonObject["vehicle1"] 						= $('#vehicleOneSearch').val();
			jsonObject["vehicle2"] 						= $('#vehicleTwoSearch').val();
			jsonObject["vehicle3"] 						= $('#vehicleThreeSearch').val();
			jsonObject["vehicle4"] 						= $('#vehicleFourSearch').val();
			jsonObject["vehicle5"] 						= $('#vehicleFiveSearch').val();
		}else{
			jsonObject["vid"] 							= $('#'+id+'').val(); //$("#"+tableBoby+"").append(tripRow);
		}
		getVehicleExpensesDetails(jsonObject, calledFromVehicle, id);
}

function getVehicleExpensesDetails(jsonObject, calledFromVehicle, id){
	$.ajax({					
		url: "getVehicleComparisionDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {	
			renderReportData(data, calledFromVehicle, id);	
			if(id == 'vehicleOneSearch'){
				setExtraComparisionInformation(false, true);
			}else{
				setExtraComparisionInformation(false, false);
			}
			
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function renderReportData(resultData, calledFromVehicle, id) {
	resultData = JSON.parse(JSON.stringify(resultData).replace(/\:null/gi, "\:\"\""));
	$('#resultRow').show();
	$('#comparisonResult').show();
	$('#infoDiv').show();
	if(resultData.noOfDays != undefined){
		$('#noOfDays').val(resultData.noOfDays);
	}
	if(calledFromVehicle){
		if(id == 'vehicleOneSearch'){
			var vehicleOneBody = 'vehicleOneBody';
			var vehicleOne = 'vehicleOne';
			vehicle1Data	= resultData.vehicle;
			setExpenseDetailsWithinTable(resultData.vehicle, id, vehicleOneBody);
		
		}else if(id == 'vehicleTwoSearch'){
			var vehicleOneBody = 'vehicleTwoBody';
			var vehicleOne = 'vehicleTwo';
			vehicle2Data	= resultData.vehicle;
			setExpenseDetailsWithinTable(resultData.vehicle, id, vehicleOneBody);
		
		
		}else if(id == 'vehicleThreeSearch'){
			var vehicleOneBody = 'vehicleThreeBody';
			var vehicleOne = 'vehicleThree';
			vehicle3Data	= resultData.vehicle;
			setExpenseDetailsWithinTable(resultData.vehicle, id, vehicleOneBody);
		}else if(id == 'vehicleFourSearch'){
			var vehicleOneBody = 'vehicleFourBody';
			var vehicleOne = 'vehicleFour';
			vehicle4Data	= resultData.vehicle;
			setExpenseDetailsWithinTable(resultData.vehicle, id, vehicleOneBody);
		}else if(id == 'vehicleFiveSearch'){
			var vehicleOneBody = 'vehicleFiveBody';
			var vehicleOne = 'vehicleFive';
			vehicle5Data	= resultData.vehicle;
			setExpenseDetailsWithinTable(resultData.vehicle, id, vehicleOneBody);
		}
	}else{
		
		if(resultData.vehicle1 != undefined){
			var vehicleOneBody = 'vehicleOneBody';
			var vehicleOne = 'vehicleOne';
			vehicle1Data	= resultData.vehicle1;
			setExpenseDetailsWithinTable(resultData.vehicle1, 'vehicleOneSearch', vehicleOneBody);
		}
		if(resultData.vehicle2 != undefined){
			var vehicleOneBody = 'vehicleTwoBody';
			var vehicleOne = 'vehicleTwo';
			vehicle2Data	= resultData.vehicle2;
			setExpenseDetailsWithinTable(resultData.vehicle2, 'vehicleTwoSearch', vehicleOneBody);
		}
		if(resultData.vehicle3 != undefined){
			var vehicleOneBody = 'vehicleThreeBody';
			var vehicleOne = 'vehicleThree';
			vehicle3Data	= resultData.vehicle3;
			setExpenseDetailsWithinTable(resultData.vehicle3, 'vehicleThreeSearch', vehicleOneBody);
		}
		if(resultData.vehicle4 != undefined){
			var vehicleOneBody = 'vehicleFourBody';
			var vehicleOne = 'vehicleFour';
			vehicle4Data	= resultData.vehicle4;
			setExpenseDetailsWithinTable(resultData.vehicle4, 'vehicleFourSearch', vehicleOneBody);
		}
		if(resultData.vehicle5 != undefined){
			var vehicleOneBody = 'vehicleFiveBody';
			var vehicleOne = 'vehicleFive';
			vehicle5Data	= resultData.vehicle5;
			setExpenseDetailsWithinTable(resultData.vehicle5, 'vehicleFiveSearch', vehicleOneBody);
		}
	}
	
	setPerDayAvgForComparisionData();
	$('#exportExcelBtn').removeClass('hide');
}


function setExpenseDetailsWithinTable(data, searchId, tableBoby){
	createSummaryRows(data, searchId, tableBoby);
	createMisseleniousExpenseRows(data, searchId, tableBoby);
	createRenewalExpensesRow(data, searchId, tableBoby);
	createTripSheetExpensesRow(data, searchId, tableBoby);
	calculateVehicleTotal(false);
}

function createSummaryRows(data, searchId, tableBoby){
	if(!summaryExpRowAdded){
		var trexp = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="expenseSummaryBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(trexp);
		
		var trVal1 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="summaryBodyvehicleOneBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal2 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="summaryBodyvehicleTwoBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal3 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="summaryBodyvehicleThreeBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal4 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="summaryBodyvehicleFourBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal5 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="summaryBodyvehicleFiveBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		
		$("#vehicleOneBody").append(trVal1);
		$("#vehicleTwoBody").append(trVal2);
		$("#vehicleThreeBody").append(trVal3);
		$("#vehicleFourBody").append(trVal4);
		$("#vehicleFiveBody").append(trVal5);
		
		summaryExpRowAdded = true;
	}
	$('#expenseSummaryBody tr').remove();
	$('#summaryBodyvehicleOneBody tr').remove();
	$('#summaryBodyvehicleTwoBody tr').remove();
	$('#summaryBodyvehicleThreeBody tr').remove();
	$('#summaryBodyvehicleFourBody tr').remove();
	$('#summaryBodyvehicleFiveBody tr').remove();
	
		if(vehicle1Data != undefined && vehicle1Data != null){
			if(vehicle1Data.noOftripDays != undefined){
				var tr1 = '<tr style="font-weight: bold;" class="expensePerKM" id="expenseName_epk">'
						+'<td style="width:100%;" class="expenseName" id="epk"><a href="#">Expense Per KM</a><td>'
						+'</tr>';
				var tr2 = '<tr style="font-weight: bold;" class="noOfTrip" id="expenseName_noOftrip">'
						+'<td style="width:100%;" class="expenseName" id="noOftrip"><a href="#">No Of Trip</a><td>'
						+'</tr>';
				var tr3 = '<tr style="font-weight: bold;" class="noOfRunKM" id="expenseName_noOfRunKM">'
						+'<td style="width:100%;" class="expenseName" id="noOfRunKM"><a href="#">Trip Run KM</a><td>'
						+'</tr>';
				var tr4 = '<tr style="font-weight: bold;" class="noOftripDays" id="expenseName_noOftripDays">'
						+'<td style="width:100%;" class="expenseName" id="noOftripDays"><a href="#">No Of Trip Days</a><td>'
						+'</tr>';
				
				$('#expenseSummaryBody').append(tr1);
				$('#expenseSummaryBody').append(tr2);
				$('#expenseSummaryBody').append(tr3);
				$('#expenseSummaryBody').append(tr4);
				
				var  epk = '<tr class="expensePerKM" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a name="vehicleOneEPK" href="#"></a></span><td>'
					+'</tr>';
				
				var  noOfTrip = '<tr class="noOfTrip" style="font-weight: bold;">'
						+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle1Data.noOftrip+'</a></span><td>'
						+'</tr>';
				
				var  noOfRunKM = '<tr class="noOfRunKM" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle1Data.totalRunKM+'</a></span><td>'
					+'</tr>';
			 
				 
				var valtr = '<tr class="noOftripDays" style="font-weight: bold;">'
						+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle1Data.noOftripDays+'</a></span><td>'
						+'</tr>';
				 $("#summaryBodyvehicleOneBody").append(epk);
				 $("#summaryBodyvehicleOneBody").append(noOfTrip);
				 $("#summaryBodyvehicleOneBody").append(noOfRunKM);
				 $("#summaryBodyvehicleOneBody").append(valtr);
				 
				 $('#vehicleOneNoOfTripDays').val(vehicle1Data.noOftripDays);
				 $('#vehicleOneTripRunKM').val(vehicle1Data.totalRunKM);
			}
		}
		if(vehicle2Data != undefined && vehicle2Data != null){
			var valtr = '';
			if(vehicle2Data.noOftripDays != undefined){
				var  epk = '<tr class="expensePerKM" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a name="vehicleTwoEPK" href="#"></a></span><td>'
					+'</tr>';
				
				var  noOfTrip = '<tr class="noOfTrip" style="font-weight: bold;">'
						+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle2Data.noOftrip+'</a></span><td>'
						+'</tr>';
				
				var  noOfRunKM = '<tr class="noOfRunKM" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle2Data.totalRunKM+'</a></span><td>'
					+'</tr>';
				 valtr += '<tr class="noOftripDays" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle2Data.noOftripDays+'</a></span><td>'
					+'</tr>';
				 $("#summaryBodyvehicleTwoBody").append(epk);
				 $("#summaryBodyvehicleTwoBody").append(noOfTrip);
				 $("#summaryBodyvehicleTwoBody").append(noOfRunKM);
				 $("#summaryBodyvehicleTwoBody").append(valtr);
				 $('#vehicleTwoNoOfTripDays').val(vehicle2Data.noOftripDays);
				 $('#vehicleTwoTripRunKM').val(vehicle2Data.totalRunKM);
			}
		}
		if(vehicle3Data != undefined && vehicle3Data != null){

			var valtr = '';
			
			var  epk = '<tr class="expensePerKM" style="font-weight: bold;">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a name="vehicleThreeEPK" href="#"></a></span><td>'
				+'</tr>';
			
			var  noOfTrip = '<tr class="noOfTrip" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle3Data.noOftrip+'</a></span><td>'
					+'</tr>';
			
			var  noOfRunKM = '<tr class="noOfRunKM" style="font-weight: bold;">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle3Data.totalRunKM+'</a></span><td>'
				+'</tr>';
			
			if(vehicle3Data.noOftripDays != undefined){
				 valtr += '<tr class="noOftripDays" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle3Data.noOftripDays+'</a></span><td>'
					+'</tr>';
				 $("#summaryBodyvehicleThreeBody").append(epk);
				 $("#summaryBodyvehicleThreeBody").append(noOfTrip);
				 $("#summaryBodyvehicleThreeBody").append(noOfRunKM);
				 $("#summaryBodyvehicleThreeBody").append(valtr);
				 $('#vehicleThreeNoOfTripDays').val(vehicle3Data.noOftripDays);
				 $('#vehicleThreeTripRunKM').val(vehicle3Data.totalRunKM);
			}
		
		}
		if(vehicle4Data != undefined && vehicle4Data != null){
			var valtr = '';
			var  epk = '<tr class="expensePerKM" style="font-weight: bold;">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a name="vehicleFourEPK" href="#"></a></span><td>'
				+'</tr>';
			
			var  noOfTrip = '<tr class="noOfTrip" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle4Data.noOftrip+'</a></span><td>'
					+'</tr>';
			
			var  noOfRunKM = '<tr class="noOfRunKM" style="font-weight: bold;">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle4Data.totalRunKM+'</a></span><td>'
				+'</tr>';
			
			if(vehicle4Data.noOftripDays != undefined){
				 valtr += '<tr class="noOftripDays" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle4Data.noOftripDays+'</a></span><td>'
					+'</tr>';
				 
				 $("#summaryBodyvehicleFourBody").append(epk);
				 $("#summaryBodyvehicleFourBody").append(noOfTrip);
				 $("#summaryBodyvehicleFourBody").append(noOfRunKM);
				 $("#summaryBodyvehicleFourBody").append(valtr);
				 $('#vehicleFourNoOfTripDays').val(vehicle4Data.noOftripDays);
				 $('#vehicleFourTripRunKM').val(vehicle4Data.totalRunKM);
			}
		}
		if(vehicle5Data != undefined && vehicle5Data != null){
			var valtr = '';
			var  epk = '<tr class="expensePerKM" style="font-weight: bold;">'
					+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a name="vehicleFiveEPK" href="#"></a></span><td>'
					+'</tr>';
			
			var  noOfTrip = '<tr class="noOfTrip" style="font-weight: bold;">'
							+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle5Data.noOftrip+'</a></span><td>'
							+'</tr>';
			
			var  noOfRunKM = '<tr class="noOfRunKM" style="font-weight: bold;">'
							+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle5Data.totalRunKM+'</a></span><td>'
							+'</tr>';
			if(vehicle5Data.noOftripDays != undefined){
				 valtr += '<tr class="noOftripDays" style="font-weight: bold;">'
						+'<td style="width:100%;text-align:right;padding-right:15px;"><span><a href="#">'+vehicle5Data.noOftripDays+'</a></span><td>'
						+'</tr>';
				 
				 $("#summaryBodyvehicleFiveBody").append(epk);
				 $("#summaryBodyvehicleFiveBody").append(noOfTrip);
				 $("#summaryBodyvehicleFiveBody").append(noOfRunKM);
				 $("#summaryBodyvehicleFiveBody").append(valtr);
				 $('#vehicleFiveNoOfTripDays').val(vehicle5Data.noOftripDays);
				 $('#vehicleFiveTripRunKM').val(vehicle5Data.totalRunKM);
			}
		
		}
		$(".expensePerKM").on("mouseover", function () {
			  $(".expensePerKM").css("background-color", "yellow");
		});
		$(".expensePerKM").on("mouseleave", function () {
			  $(".expensePerKM").css("background-color", "#fffcfc");
		});
		$(".noOfTrip").on("mouseover", function () {
			  $(".noOfTrip").css("background-color", "yellow");
		});
		$(".noOfTrip").on("mouseleave", function () {
			  $(".noOfTrip").css("background-color", "#fffcfc");
		});
		$(".noOfRunKM").on("mouseover", function () {
			  $(".noOfRunKM").css("background-color", "yellow");
		});
		$(".noOfRunKM").on("mouseleave", function () {
			  $(".noOfRunKM").css("background-color", "#fffcfc");
		});
		$(".noOftripDays").on("mouseover", function () {
			  $(".noOftripDays").css("background-color", "yellow");
		});
		$(".noOftripDays").on("mouseleave", function () {
			  $(".noOftripDays").css("background-color", "#fffcfc");
		});

}

function getVehicleComparisionData(element){
	var elementId = element.id;
	var elementValue = $('#'+elementId+'').val();
	var sameVehicle = false;
		if((elementId == 'vehicleOneSearch') || (elementId == 'vehicleTwoSearch') || (elementId == 'vehicleThreeSearch') || (elementId == 'vehicleFourSearch') || (elementId == 'vehicleFiveSearch')){
			if(elementId == 'vehicleOneSearch'){
				if(elementValue == $('#vehicleTwoSearch').val() || elementValue == $('#vehicleThreeSearch').val() || elementValue == $('#vehicleFourSearch').val() || elementValue == $('#vehicleFiveSearch').val()){
					sameVehicle = true;
				}
				 
			}else if(elementId == 'vehicleTwoSearch'){
				if(elementValue == $('#vehicleOneSearch').val() || elementValue == $('#vehicleThreeSearch').val() || elementValue == $('#vehicleFourSearch').val() || elementValue == $('#vehicleFiveSearch').val()){
					sameVehicle = true;
				}
			}else if(elementId == 'vehicleThreeSearch'){
				if(elementValue == $('#vehicleOneSearch').val() || elementValue == $('#vehicleTwoSearch').val() || elementValue == $('#vehicleFourSearch').val() || elementValue == $('#vehicleFiveSearch').val()){
					sameVehicle = true;
				}
			}else if(elementId == 'vehicleFourSearch'){
				if(elementValue == $('#vehicleOneSearch').val() || elementValue == $('#vehicleThreeSearch').val() || elementValue == $('#vehicleTwoSearch').val() || elementValue == $('#vehicleFiveSearch').val()){
					sameVehicle = true;
				}
			}else if(elementId == 'vehicleFiveSearch'){
				if(elementValue == $('#vehicleOneSearch').val() || elementValue == $('#vehicleThreeSearch').val() || elementValue == $('#vehicleFourSearch').val() || elementValue == $('#vehicleTwoSearch').val()){
					sameVehicle = true;
				}
			}
			if(sameVehicle){
				showMessage('errors', '2 vehicle cannot be same !');
				$('#'+elementId+'').select2('focus');
				$('#'+elementId+'').select2("val", "");
				return false;
			}
			getVehicleExpenseComparisionData(true, elementId);
		}else{
			getVehicleExpenseComparisionData(false, elementId);
		}
	
}
function createMisseleniousExpenseRows(data, searchId, tableBoby){
	if(!misseleniousExpRowAdded){
		var trexp = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="expenseBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(trexp);
		
		var trVal1 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="missExpenseBodyvehicleOneBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal2 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="missExpenseBodyvehicleTwoBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal3 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="missExpenseBodyvehicleThreeBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal4 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="missExpenseBodyvehicleFourBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal5 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="missExpenseBodyvehicleFiveBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		
		$("#vehicleOneBody").append(trVal1);
		$("#vehicleTwoBody").append(trVal2);
		$("#vehicleThreeBody").append(trVal3);
		$("#vehicleFourBody").append(trVal4);
		$("#vehicleFiveBody").append(trVal5);
		
		misseleniousExpRowAdded = true;
	}
	$('#expenseBody tr').remove();
	$('#missExpenseBodyvehicleOneBody tr').remove();
	$('#missExpenseBodyvehicleTwoBody tr').remove();
	$('#missExpenseBodyvehicleThreeBody tr').remove();
	$('#missExpenseBodyvehicleFourBody tr').remove();
	$('#missExpenseBodyvehicleFiveBody tr').remove();
	
	var expenseToAdd = new Map();
	if(vehicle1Data != undefined && vehicle1Data != null){
		$.each(vehicle1Data.expenseDtoList, function (key, value) {
			if((value.expenseCategory == 0 || value.expenseCategory == 1) && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
			}
		});
	}
	if(vehicle2Data != undefined && vehicle2Data != null){
		$.each(vehicle2Data.expenseDtoList, function (key, value) {
			if((value.expenseCategory == 0 || value.expenseCategory == 1) && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
			}
		});
	
	}
	if(vehicle3Data != undefined && vehicle3Data != null){
		$.each(vehicle3Data.expenseDtoList, function (key, value) {
			if((value.expenseCategory == 0 || value.expenseCategory == 1) && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
			}
		});
	
	}
	if(vehicle4Data != undefined && vehicle4Data != null){
		$.each(vehicle4Data.expenseDtoList, function (key, value) {
			if((value.expenseCategory == 0 || value.expenseCategory == 1) && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
			}
		});
	
	}
	if(vehicle5Data != undefined && vehicle5Data != null){
		$.each(vehicle5Data.expenseDtoList, function (key, value) {
			if((value.expenseCategory == 0 || value.expenseCategory == 1) && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
			}
		});
	
	}
	
	$.each(expenseToAdd, function (key, value) {
		var tr = '<tr style="font-weight: bold;" class="misselenious_'+key.split('_')[0]+'" id="expenseName_'+key.split('_')[0]+'">'
		+'<td style="width:100%;" class="expenseName" id="'+key.split('_')[0]+'">'+key.split('_')[1].toUpperCase()+'<td>'
		+'</tr>';
		$('#expenseBody').append(tr);
	
		
		if(vehicle1Data != undefined && vehicle1Data != null){
			var expenseData = vehicle1Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleOneSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleOneSearch_'+key.split('_')[0]+'">0</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#missExpenseBodyvehicleOneBody").append(valtr);
		}
		if(vehicle2Data != undefined && vehicle2Data != null){
			var expenseData = vehicle2Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleTwoSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleTwoSearch_'+key.split('_')[0]+'">0</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#missExpenseBodyvehicleTwoBody").append(valtr);
		}
		if(vehicle3Data != undefined && vehicle3Data != null){
			var expenseData = vehicle3Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">0</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#missExpenseBodyvehicleThreeBody").append(valtr);
		}
		if(vehicle4Data != undefined && vehicle4Data != null){
			var expenseData = vehicle4Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#missExpenseBodyvehicleFourBody").append(valtr);
		}
		if(vehicle5Data != undefined && vehicle5Data != null){
			var expenseData = vehicle5Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="misselenious_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align:right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#missExpenseBodyvehicleFiveBody").append(valtr);
		}
		
		$(".misselenious_"+key.split('_')[0]+"").on("mouseover", function () {
			  $(".misselenious_"+key.split('_')[0]+"").css("background-color", "yellow");
		});
		$(".misselenious_"+key.split('_')[0]+"").on("mouseleave", function () {
			  $(".misselenious_"+key.split('_')[0]+"").css("background-color", "#fffcfc");
		});
	});

}
function createTripSheetExpensesRow(data, searchId, tableBoby){

	if(!tripExpenseRowAdded){
		var trexp = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(trexp);
		
		var trVal1 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBodyvehicleOneBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal2 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBodyvehicleTwoBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal3 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBodyvehicleThreeBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal4 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBodyvehicleFourBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal5 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBodyvehicleFiveBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		
		$("#vehicleOneBody").append(trVal1);
		$("#vehicleTwoBody").append(trVal2);
		$("#vehicleThreeBody").append(trVal3);
		$("#vehicleFourBody").append(trVal4);
		$("#vehicleFiveBody").append(trVal5);
		
		tripExpenseRowAdded = true;
	}
	
	$('#tripExpenseBody tr').remove();
	$('#tripExpenseValBodyvehicleOneBody tr').remove();
	$('#tripExpenseValBodyvehicleTwoBody tr').remove();
	$('#tripExpenseValBodyvehicleThreeBody tr').remove();
	$('#tripExpenseValBodyvehicleFourBody tr').remove();
	$('#tripExpenseValBodyvehicleFiveBody tr').remove();
	
	var expenseToAdd = new Map();
	var expenseFound = false;
	if(vehicle1Data != undefined && vehicle1Data != null){
		$.each(vehicle1Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 3 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	}
	if(vehicle2Data != undefined && vehicle2Data != null){
		$.each(vehicle2Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 3 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle3Data != undefined && vehicle3Data != null){
		$.each(vehicle3Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 3 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle4Data != undefined && vehicle4Data != null){
		$.each(vehicle4Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 3 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle5Data != undefined && vehicle5Data != null){
		$.each(vehicle5Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 3 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(expenseFound){
		var tripRow = '<tr>'
			//+'<td class="fit" >'+srNo+'</td>'
			+'<td  style="text-align: center; padding-left: 10px;height: 24px;font-weight: bold;"><a href="#">TripSheet Expenses</a></td>'
			+'</tr>';
		
		$('#tripExpenseBody').append(tripRow);
		var header = '<tr>'
			+'<td  style="text-align: left; width:100%; padding-left: 10px;height: 24px;font-weight: bold;"><a href="#"></a></td>'
			+'</tr>';
		
		$("#tripExpenseValBodyvehicleOneBody").append(header);
		$("#tripExpenseValBodyvehicleTwoBody").append(header);
		$("#tripExpenseValBodyvehicleThreeBody").append(header);
		$("#tripExpenseValBodyvehicleFourBody").append(header);
		$("#tripExpenseValBodyvehicleFiveBody").append(header);
		
	}
	
	$.each(expenseToAdd, function (key, value) {
		var tr = '<tr style="font-weight: bold;" class="tripsheet_'+key.split('_')[0]+'" id="expenseName_'+key.split('_')[0]+'">'
		+'<td style="width:100%;" class="expenseName" id="'+key.split('_')[0]+'">'+(key.split('_')[1]).toUpperCase()+'<td>'
		+'</tr>';
		$('#tripExpenseBody').append(tr);
	
		if(vehicle1Data != undefined && vehicle1Data != null){
			var expenseData = vehicle1Data.expenseDtoList[key];
		
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;" ><span id="vehicleOneSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleOneSearch_'+key.split('_')[0]+'">0</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#tripExpenseValBodyvehicleOneBody").append(valtr);
		}
		if(vehicle2Data != undefined && vehicle2Data != null){
			var expenseData = vehicle2Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleTwoSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleTwoSearch_'+key.split('_')[0]+'">0</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#tripExpenseValBodyvehicleTwoBody").append(valtr);
		}
		if(vehicle3Data != undefined && vehicle3Data != null){
			var expenseData = vehicle3Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">0</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#tripExpenseValBodyvehicleThreeBody").append(valtr);
		}
		if(vehicle4Data != undefined && vehicle4Data != null){
			var expenseData = vehicle4Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#tripExpenseValBodyvehicleFourBody").append(valtr);
		}
		if(vehicle5Data != undefined && vehicle5Data != null){
			var expenseData = vehicle5Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="tripsheet_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#tripExpenseValBodyvehicleFiveBody").append(valtr);
		}
		$(".tripsheet_"+key.split('_')[0]+"").on("mouseover", function () {
			  $(".tripsheet_"+key.split('_')[0]+"").css("background-color", "yellow");
		});
		$(".tripsheet_"+key.split('_')[0]+"").on("mouseleave", function () {
			  $(".tripsheet_"+key.split('_')[0]+"").css("background-color", "#fffcfc");
		});
	});


}
function createRenewalExpensesRow(data, searchId, tableBoby){

	if(!renewalRowAdded){
		var trexp = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalExpenseBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(trexp);
		
		var trVal1 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalValBodyvehicleOneBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal2 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalValBodyvehicleTwoBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal3 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalValBodyvehicleThreeBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal4 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalValBodyvehicleFourBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		var trVal5 = '<tr>'
			+'<td>'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalValBodyvehicleFiveBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		
		$("#vehicleOneBody").append(trVal1);
		$("#vehicleTwoBody").append(trVal2);
		$("#vehicleThreeBody").append(trVal3);
		$("#vehicleFourBody").append(trVal4);
		$("#vehicleFiveBody").append(trVal5);
		
		renewalRowAdded = true;
	}
	
	$('#renewalExpenseBody tr').remove();
	$('#renewalValBodyvehicleOneBody tr').remove();
	$('#renewalValBodyvehicleTwoBody tr').remove();
	$('#renewalValBodyvehicleThreeBody tr').remove();
	$('#renewalValBodyvehicleFourBody tr').remove();
	$('#renewalValBodyvehicleFiveBody tr').remove();
	
	var expenseToAdd = new Map();
	var expenseFound = false;
	if(vehicle1Data != undefined && vehicle1Data != null){
		$.each(vehicle1Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 2 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	}
	if(vehicle2Data != undefined && vehicle2Data != null){
		$.each(vehicle2Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 2 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle3Data != undefined && vehicle3Data != null){
		$.each(vehicle3Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 2 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle4Data != undefined && vehicle4Data != null){
		$.each(vehicle4Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 2 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(vehicle5Data != undefined && vehicle5Data != null){
		$.each(vehicle5Data.expenseDtoList, function (key, value) {
			if(value.expenseCategory == 2 && value.expenseAmount != 'NaN' && value.expenseAmount > 0){
				expenseToAdd[key] =  key;
				expenseFound = true;
			}
		});
	
	}
	if(expenseFound){
		var tripRow = '<tr>'
			//+'<td class="fit" >'+srNo+'</td>'
			+'<td  style="text-align: left; width:100%; padding-left: 10px;height: 24px;font-weight: bold;"><a href="#">Compliances Charges</a></td>'
			+'</tr>';
		
		$('#renewalExpenseBody').append(tripRow);
		var header = '<tr>'
			+'<td  style="text-align: left; width:100%;padding-left: 10px;height: 24px;font-weight: bold;"><a href="#"></a></td>'
			+'</tr>';
		
		$("#renewalValBodyvehicleOneBody").append(header);
		$("#renewalValBodyvehicleTwoBody").append(header);
		$("#renewalValBodyvehicleThreeBody").append(header);
		$("#renewalValBodyvehicleFourBody").append(header);
		$("#renewalValBodyvehicleFiveBody").append(header);
	}
	$.each(expenseToAdd, function (key, value) {
		
		var tr = '<tr style="font-weight: bold;" class="renewal_'+key.split('_')[0]+'" id="expenseName_'+key.split('_')[0]+'">'
		+'<td class="expenseName" id="'+key.split('_')[0]+'">'+key.split('_')[1].toUpperCase()+'<td>'
		+'</tr>';
		$('#renewalExpenseBody').append(tr);
	
		
		if(vehicle1Data != undefined && vehicle1Data != null){
			var expenseData = vehicle1Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;" class="renewal_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;" ><span id="vehicleOneSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleOneSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleOneSearch_'+key.split('_')[0]+'">0</span><span id="vehicleOneSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#renewalValBodyvehicleOneBody").append(valtr);
		}
		if(vehicle2Data != undefined && vehicle2Data != null){
			var expenseData = vehicle2Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;" class="renewal_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;" ><span id="vehicleTwoSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;" class="renewal_'+key.split('_')[0]+'" id="vehicleTwoSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleTwoSearch_'+key.split('_')[0]+'">0</span><span id="vehicleTwoSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#renewalValBodyvehicleTwoBody").append(valtr);
		}
		if(vehicle3Data != undefined && vehicle3Data != null){
			var expenseData = vehicle3Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleThreeSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleThreeSearch_'+key.split('_')[0]+'">0</span><span id="vehicleThreeSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#renewalValBodyvehicleThreeBody").append(valtr);
		}
		if(vehicle4Data != undefined && vehicle4Data != null){
			var expenseData = vehicle4Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleFourSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFourSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFourSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#renewalValBodyvehicleFourBody").append(valtr);
		}
		if(vehicle5Data != undefined && vehicle5Data != null){
			var expenseData = vehicle5Data.expenseDtoList[key];
			var valtr = '';
			if(expenseData != undefined && expenseData.expenseAmount != 'NaN' && expenseData.expenseAmount > 0){
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			
			}else{
				 valtr += '<tr style="font-weight: bold;text-align: right;" class="renewal_'+key.split('_')[0]+'" id="vehicleFiveSearch_'+key.split('_')[0]+'_row">'
				+'<td style="width:100%;text-align: right;padding-right:15px;"><span id="vehicleFiveSearch_'+key.split('_')[0]+'">0</span><span id="vehicleFiveSearch_extra_'+key.split('_')[0]+'"></span><td>'
				+'</tr>';
			}
			$("#renewalValBodyvehicleFiveBody").append(valtr);
		}
		
		$(".renewal_"+key.split('_')[0]+"").on("mouseover", function () {
			  $(".renewal_"+key.split('_')[0]+"").css("background-color", "yellow");
		});
		$(".renewal_"+key.split('_')[0]+"").on("mouseleave", function () {
			  $(".renewal_"+key.split('_')[0]+"").css("background-color", "#fffcfc");
		});
	});
}
function resetFeilds(){
	refreshAndHidePartOfPage('reportDiv','refresh');
	tripExpenseRowAdded = false;
	renewalRowAdded = false;
	misseleniousExpRowAdded = false;
	summaryExpRowAdded = false;
	totalExpRowAdded = false;
	vehicle1Data = null;
	vehicle2Data = null;
	vehicle3Data = null;
	vehicle4Data = null;
	vehicle5Data = null;
	
}
function getCompareDataOnDateChange(element){
	showLayer();
	resetFeilds();
	setTimeout(function(){
		var jsonObject								= new Object();				
		jsonObject["routeId"] 						= $('#routeId').val();
		jsonObject["dateRange"] 					= $('#compareRange').val();
		jsonObject["companyId"] 					= $('#companyId').val();
		jsonObject["userId"] 						= $('#userId').val();
		jsonObject["vehicleTypeId"] 				= $('#vehicleTypeId').val();
		$.ajax({					
			url: "getActiveVehicleListInRange",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setVehicleLIstDropDown(data.vehicleList);
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
		
	}, 2000);
	//getVehicleComparisionData(element);
}
function callFunctionToSetAvg(element){
	setTimeout(function(){
		if(document.getElementById('perDaysExpenses').checked){
			if($("#perDaysExpensesSelected").val() == 'false' || $("#perDaysExpensesSelected").val() == false){
				setPerDayAvgForComparisionData();
				$("#perDaysExpensesSelected").val(true);
			}
		}else{
			if($("#perDaysExpensesSelected").val() == 'true' || $("#perDaysExpensesSelected").val() == true){
				getVehicleComparisionData(element);
				$("#perDaysExpensesSelected").val(false);
			}
		}
	}, 100);
}
function setPerDayAvgForComparisionData(){
	if(document.getElementById('perDaysExpenses').checked){
		var noOfDays = Number($('#noOfDays').val());
		if(noOfDays > 0){
			$("tbody tr td span").each(function() {
				if(this.id != undefined && this.id != ''){
					var dataVal = Number($(this).text());
					
					if((this.id).includes('vehicleOneSearch')){
						noOfDays = Number($('#vehicleOneNoOfTripDays').val());
					}else if((this.id).includes('vehicleTwoSearch')){
						noOfDays = Number($('#vehicleTwoNoOfTripDays').val());
					}else if((this.id).includes('vehicleThreeSearch')){
						noOfDays = Number($('#vehicleThreeNoOfTripDays').val());
					}else if((this.id).includes('vehicleFourSearch')){
						noOfDays = Number($('#vehicleFourNoOfTripDays').val());
					}else if((this.id).includes('vehicleFiveSearch')){
						noOfDays = Number($('#vehicleFiveNoOfTripDays').val());
					}
					
					if(dataVal > 0){
						$(this).text(''+(dataVal/noOfDays).toFixed(2)+'');
					}
					
				}
			});
			
			setExtraComparisionInformation(true, false);
		}
	}
}

function setExtraComparisionInformation(fromAvg, calledFromVehicleOne){
	
	var compareClass = '';
	var vehicleOneData 		= 0;
	var vehicleTwoData 		= 0;
	var vehicleThreeData 	= 0;
	var vehicleFourData 	= 0;
	var vehicleFiveData 	= 0;
	var temp = null;
	
		for(var i = 0; i < allExpensesArr.length ; i++){
				vehicleOneData 		= Number($('#vehicleOneSearch_'+allExpensesArr[i]+'').text());
				vehicleTwoData 		= Number($('#vehicleTwoSearch_'+allExpensesArr[i]+'').text());
				vehicleThreeData 	= Number($('#vehicleThreeSearch_'+allExpensesArr[i]+'').text());
				vehicleFourData 	= Number($('#vehicleFourSearch_'+allExpensesArr[i]+'').text());
				vehicleFiveData 	= Number($('#vehicleFiveSearch_'+allExpensesArr[i]+'').text());
			
			var per = '';
			if(Number($('#vehicleOneSearch').val()) > 0 && vehicleOneData > 0){
				
			}
			
			if(Number($('#vehicleTwoSearch').val()) > 0 && vehicleTwoData > 0){
				if(vehicleOneData > 0){
					per = Math.abs((100 - ((vehicleTwoData*100)/vehicleOneData)).toFixed(2)) + '%';
				}else{
					per = vehicleTwoData+ ' rs';
				}
				
				if(vehicleOneData > vehicleTwoData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleTwoData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+vehicleOneData+' and '+$('#vehicleTwoSearch :selected').text()+' : '+vehicleTwoData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleTwoSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
			}
			
			if(Number($('#vehicleThreeSearch').val()) > 0 && vehicleThreeData > 0){
					if(vehicleOneData > 0){
						per = Math.abs((100 - ((vehicleThreeData*100)/vehicleOneData)).toFixed(2))+ '%';
					}else{
						per = vehicleThreeData+ ' rs';
					}
				if(vehicleOneData > vehicleThreeData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleThreeData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+vehicleOneData+' and '+$('#vehicleThreeSearch :selected').text()+' : '+vehicleThreeData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleThreeSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
			}
			if(Number($('#vehicleFourSearch').val()) > 0 && vehicleFourData > 0){
					if(vehicleOneData > 0){
						per = Math.abs((100 - ((vehicleFourData*100)/vehicleOneData)).toFixed(2))+ '%';
					}else{
						per = vehicleFourData+ ' rs';
					}
				if(vehicleOneData > vehicleFourData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleFourData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+vehicleOneData+' and '+$('#vehicleFourSearch :selected').text()+' : '+vehicleFourData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleFourSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
			}
			if(Number($('#vehicleFiveSearch').val()) > 0 && vehicleFiveData > 0){
				if(vehicleOneData > 0){
					per = Math.abs((100 - ((vehicleFiveData*100)/vehicleOneData)).toFixed(2))+ '%';
				}else{
					per = vehicleFiveData+ ' rs';
				}
				if(vehicleOneData > vehicleFiveData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleFiveData){
					compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+vehicleOneData+' and '+$('#vehicleFiveSearch :selected').text()+' : '+vehicleFiveData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleFiveSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
			}
			
		}
		setTotalRowExtraClasses();	
}
function setTotalRowExtraClasses(){
	
	if(Number($('#vehicleOneTotal').text()) > 0){
		$('a[name="vehicleOneEPK"]').text((Number($('#vehicleOneEPK').val())/Number($('#vehicleOneTripRunKM').val())).toFixed(2));
	}
	
	if(Number($('#vehicleTwoTotal').text()) > 0){
		if(Number($('#vehicleOneTotal').text()) > 0){
			per = Math.abs((100 - ((Number($('#vehicleTwoTotal').text())*100)/Number($('#vehicleOneTotal').text()))).toFixed(2))+ '%';
		}else{
			per = Number($('#vehicleTwoTotal').text())+ ' rs';
		}
		if(Number($('#vehicleOneTotal').text()) > Number($('#vehicleTwoTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
		}else if(vehicleOneData < Number($('#vehicleTwoTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
		}else{
			compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+Number($('#vehicleOneTotal').text()).toFixed(2)+' and '+$('#vehicleTwoSearch :selected').text()+' : '+Number($('#vehicleTwoTotal').text()).toFixed(2)+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
		}
		$('#vehicleTwoTotalExtra').empty();
		$('#vehicleTwoTotalExtra').append(compareClass);
		$('a[name="vehicleTwoEPK"]').text((Number($('#vehicleTwoEPK').val())/Number($('#vehicleTwoTripRunKM').val())).toFixed(2));
	}

	if(Number($('#vehicleThreeTotal').text()) > 0){
		if(Number($('#vehicleOneTotal').text()) > 0){
			per = Math.abs((100 - ((Number($('#vehicleThreeTotal').text())*100)/Number($('#vehicleOneTotal').text()))).toFixed(2))+ '%';
		}else{
			per = Number($('#vehicleThreeTotal').text())+ ' rs';
		}
		if(Number($('#vehicleOneTotal').text()) > Number($('#vehicleThreeTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
		}else if(vehicleOneData < Number($('#vehicleThreeTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
		}else{
			compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+Number($('#vehicleOneTotal').text()).toFixed(2)+' and '+$('#vehicleThreeSearch :selected').text()+' : '+Number($('#vehicleThreeTotal').text()).toFixed(2)+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
		}
		$('#vehicleThreeTotalExtra').empty();
		$('#vehicleThreeTotalExtra').append(compareClass);
		$('a[name="vehicleThreeEPK"]').text((Number($('#vehicleThreeEPK').val())/Number($('#vehicleThreeTripRunKM').val())).toFixed(2));
	}
	
	if(Number($('#vehicleFourTotal').text()) > 0){
		if(Number($('#vehicleOneTotal').text()) > 0){
			per = Math.abs((100 - ((Number($('#vehicleFourTotal').text())*100)/Number($('#vehicleOneTotal').text()))).toFixed(2))+ '%';
		}else{
			per = Number($('#vehicleFourTotal').text())+ ' rs';
		}
		if(Number($('#vehicleOneTotal').text()) > Number($('#vehicleFourTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
		}else if(vehicleOneData < Number($('#vehicleFourTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
		}else{
			compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+Number($('#vehicleOneTotal').text()).toFixed(2)+' and '+$('#vehicleThreeSearch :selected').text()+' : '+Number($('#vehicleFourTotal').text()).toFixed(2)+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
		}
		$('#vehicleFourTotalExtra').empty();
		$('#vehicleFourTotalExtra').append(compareClass);
		$('a[name="vehicleFourEPK"]').text((Number($('#vehicleFourEPK').val())/Number($('#vehicleFourTripRunKM').val())).toFixed(2));
	}
	if(Number($('#vehicleFiveTotal').text()) > 0){
		if(Number($('#vehicleOneTotal').text()) > 0){
			per = Math.abs((100 - ((Number($('#vehicleFiveTotal').text())*100)/Number($('#vehicleOneTotal').text()))).toFixed(2))+ '%';
		}else{
			per = Number($('#vehicleFiveTotal').text())+ ' rs';
		}
		if(Number($('#vehicleOneTotal').text()) > Number($('#vehicleFiveTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' less than vehicle '+$('#vehicleOneSearch :selected').text()+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
		}else if(vehicleOneData < Number($('#vehicleFiveTotal').text())){
			compareClass = ' <i data-toggle="tip" data-original-title="'+per+' more than vehicle '+$('#vehicleOneSearch :selected').text()+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
		}else{
			compareClass = ' <i data-toggle="tip" data-original-title="'+$('#vehicleOneSearch :selected').text()+' : '+Number($('#vehicleOneTotal').text()).toFixed(2)+' and '+$('#vehicleThreeSearch :selected').text()+' : '+Number($('#vehicleFiveTotal').text()).toFixed(2)+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
		}
		$('#vehicleFiveTotalExtra').empty();
		$('#vehicleFiveTotalExtra').append(compareClass);
		$('a[name="vehicleFiveEPK"]').text((Number($('#vehicleFiveEPK').val())/Number($('#vehicleFiveTripRunKM').val())).toFixed(2));
	}
	
	
}
function calculateVehicleTotal(fromAvg){
	var vehicleOneTotal = 0;
	var vehicleTwoTotal = 0;
	var vehicleThreeTotal = 0;
	var vehicleFourTotal = 0;
	var vehicleFiveTotal = 0;
	$("tbody tr td span").each(function() {
		if(this.id != undefined && this.id != ''){
			var dataVal = Number($(this).text());
			
			if(dataVal > 0){
				if((this.id).includes('vehicleOneSearch')){
					vehicleOneTotal += dataVal;
				}else if((this.id).includes('vehicleTwoSearch')){
					vehicleTwoTotal += dataVal;
				}else if((this.id).includes('vehicleThreeSearch')){
					vehicleThreeTotal += dataVal;
				}else if((this.id).includes('vehicleFourSearch')){
					vehicleFourTotal += dataVal;
				}else if((this.id).includes('vehicleFiveSearch')){
					vehicleFiveTotal += dataVal;
				}
			}
		}
	});
	//<span> </span>
	
	var compareClass = '';
	var per = '';
	if(!totalExpRowAdded){
		var footer = '<tr>'
			+'<td  style="text-align: left; width:100%;padding-left: 10px;height: 24px;font-weight: bold;"><a href="#">TOTAL</a></td>'
			+'</tr>';
			$('#expenseNameBody').append(footer);
			totalExpRowAdded = true;
		}
		if(Number($('#vehicleOneSearch').val()) > 0 && $('#vehicleOneTotal').val() == undefined){
			var tr1 = '<tr>'
				+'<td  style="width:100%;padding-right: 15px;height: 24px;font-weight: bold;text-align: right;"><span id="vehicleOneTotal"><a href="#">'+vehicleOneTotal.toFixed(2)+'</a></span></td>'
				+'</tr>';
			
			$('#vehicleOneBody').append(tr1);
			
		}
		
		if(Number($('#vehicleTwoSearch').val()) > 0 && $('#vehicleTwoTotal').val() == undefined){
			var tr2 = '<tr>'
				+'<td  style="width:100%;padding-right: 15px;height: 24px;font-weight: bold;text-align: right;"><span id="vehicleTwoTotal"><a href="#">'+vehicleTwoTotal.toFixed(2)+'</a></span><span id="vehicleTwoTotalExtra"></span></td>'
				+'</tr>';
			$('#vehicleTwoBody').append(tr2);
			
		}
		
		if(Number($('#vehicleThreeSearch').val()) > 0 && $('#vehicleThreeTotal').val() == undefined){
			var tr3 = '<tr>'
				+'<td  style="width:100%;padding-right: 15px;height: 24px;font-weight: bold;text-align: right;"><span id="vehicleThreeTotal"><a href="#">'+vehicleThreeTotal.toFixed(2)+'</a></span><span id="vehicleThreeTotalExtra"></span></td>'
				+'</tr>';
			$('#vehicleThreeBody').append(tr3);	
		}
		
		if(Number($('#vehicleFourSearch').val()) > 0 && $('#vehicleFourTotal').val() == undefined){
			var tr4 = '<tr>'
				+'<td  style=" width:100%;padding-right: 15px;height: 24px;font-weight: bold;text-align: right;"><span id="vehicleFourTotal"><a href="#">'+vehicleFourTotal.toFixed(2)+'</a></span><span id="vehicleFourTotalExtra"></span></td>'
				+'</tr>';
			$('#vehicleFourBody').append(tr4);
		}
		if(Number($('#vehicleFiveSearch').val()) > 0 && $('#vehicleFiveTotal').val() == undefined){
			var tr5 = '<tr>'
				+'<td  style="width:100%;padding-right: 15px;height: 24px;font-weight: bold;text-align: right;"><span id="vehicleFiveTotal"><a href="#">'+vehicleFiveTotal.toFixed(2)+'</a></span><span id="vehicleFiveTotalExtra"></span></td>'
				+'</tr>';
			$('#vehicleFiveBody').append(tr5);
		}
		if(vehicleOneTotal != undefined && vehicleOneTotal > 0){
			$('#vehicleOneTotal').html(vehicleOneTotal.toFixed(2));
			$('#vehicleOneEPK').val(vehicleOneTotal.toFixed(2));
		}
		if(vehicleTwoTotal != undefined && vehicleTwoTotal > 0){
			$('#vehicleTwoTotal').html(vehicleTwoTotal.toFixed(2));
			$('#vehicleTwoEPK').val(vehicleTwoTotal.toFixed(2));
		}
			
		if(vehicleThreeTotal != undefined && vehicleThreeTotal > 0){
			$('#vehicleThreeTotal').html(vehicleThreeTotal.toFixed(2));
			$('#vehicleThreeEPK').val(vehicleThreeTotal.toFixed(2));
		}
		if(vehicleFourTotal != undefined && vehicleFourTotal > 0){
			$('#vehicleFourTotal').html(vehicleFourTotal.toFixed(2));
			$('#vehicleFourEPK').val(vehicleFourTotal.toFixed(2));
		}
		if(vehicleFiveTotal != undefined && vehicleFiveTotal > 0){
			$('#vehicleFiveTotal').html(vehicleFiveTotal.toFixed(2));
			$('#vehicleFiveEPK').val(vehicleFiveTotal.toFixed(2));
		}
}