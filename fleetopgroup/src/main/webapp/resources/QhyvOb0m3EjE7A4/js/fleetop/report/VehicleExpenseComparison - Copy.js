var tripExpenseRowAdded = false;
var renewalRowAdded = false;
var misseleniousExpRowAdded = false;
var totalIncomeAmt = 0;
var totalExpenseAmt = 0;
var vehicleOneData  = false;
var vehicleTwoData 	  = false;
var vehicleThreeData  = false;
var tripExpenses = null;
var renewalList = null;
var vehicle1Data = null;
var vehicle2Data = null;
var vehicle3Data = null;
var vehicle4Data = null;
var vehicle5Data = null;
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

}

function setRenewalExpenses(renewalListObj){
	var jsonObject = JSON.parse(renewalListObj);
	renewalList = jsonObject;
}


$(function() {
    function a(a, b) {
        $("#compareRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(3, "months").add(1, "days"), moment()), $("#compareRange").daterangepicker( {
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
	$("#vehicleOneSearch, #vehicleTwoSearch, #vehicleThreeSearch, #vehicleFourSearch, #vehicleFiveSearch").select2( {
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
			console.log('data : ', data);
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
	$("#"+tableBoby+" tr").remove();
	createMisseleniousExpenseRows(data, searchId, tableBoby);
	createRenewalExpensesRow(data, searchId, tableBoby);
	createTripSheetExpensesRow(data, searchId, tableBoby);
}

function getVehicleComparisionData(element){
	var elementId = element.id;
	var elementValue = $('#'+elementId+'').val();
	var sameVehicle = false;
	if(Number($('#routeId').val()) > 0){
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
	}else{
		showMessage('errors', 'Please select Route to compare vehicle expenses !');
		$("#routeId").select2('focus');
		 $('#vehicleOneSearch').select2("val", "");
		 $('#vehicleTwoSearch').select2("val", "");
		 $('#vehicleThreeSearch').select2("val", "");
		 $('#vehicleFourSearch').select2("val", "");
		 $('#vehicleFiveSearch').select2("val", "");
		 
		return false;
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
		
		$.each(data.expenseDtoList, function (key, value) {
			if(value.expenseType == TRANSACTION_TYPE_TRIPSHEET || value.expenseType == TRANSACTION_TYPE_RENEWAL){
				//do nothing
			}else{
				allExpensesArr.push(value.expenseType);
				var tr = '<tr style="font-weight: bold;" id="expenseName_'+value.expenseType+'">'
					+'<td class="expenseName" id="'+value.expenseType+'">'+value.expenseTypeName+'<td>'
					+'</tr>';
				$('#expenseBody').append(tr);
				
				addedExpenses.push(value.expenseType);
				
				expenseMap[value.expenseType] = value.expenseTypeName;

			}
					
		});
		misseleniousExpRowAdded = true;
	}
	var trVal = '<tr>'
		+'<td >'
		+'<table style="width: 100%" class="table-hover table-bordered">'
		+'<tbody id="missExpenseBody'+tableBoby+'">'
		+'</tbody>'
		+'</table>'
		+'</td>'
		+'</tr>';
	$("#"+tableBoby+"").append(trVal);
			for(var i = 0; i < addedExpenses.length; i++){
				var expenseAmountObj   = data.expenseDtoList[addedExpenses[i]+'_'+expenseMap[addedExpenses[i]]];
				if(expenseAmountObj != undefined && (expenseAmountObj.expenseType == TRANSACTION_TYPE_TRIPSHEET || expenseAmountObj.expenseType == TRANSACTION_TYPE_RENEWAL)){
					
				}else{
					
					if(expenseAmountObj != undefined && expenseAmountObj.expenseAmount != 'NaN'){
						var valtr = '<tr style="font-weight: bold;text-align: right;" id="'+searchId+'_'+expenseAmountObj.expenseType+'_row">'
						+'<td ><span id="'+searchId+'_'+expenseAmountObj.expenseType+'">'+(expenseAmountObj.expenseAmount).toFixed(2)+'</span><span id="'+searchId+'_extra_'+expenseAmountObj.expenseType+'"></span><td>'
						+'</tr>';
						$("#missExpenseBody"+tableBoby+"").append(valtr);
					}
				}
			}
	for(var i = 0; i < notAddedExpenses.length; i++){

		var expenseAmountObj   = data.expenseDtoList[notAddedExpenses[i]+'_'+expenseMap[notAddedExpenses[i]]];
		if(expenseAmountObj != undefined && (expenseAmountObj.expenseType == TRANSACTION_TYPE_TRIPSHEET || expenseAmountObj.expenseType == TRANSACTION_TYPE_RENEWAL)){
			
		}else{
			
			if(expenseAmountObj != undefined && expenseAmountObj.expenseAmount != 'NaN'){
				var valtr = '<tr style="font-weight: bold;text-align: right;" id="'+searchId+'_'+expenseAmountObj.expenseType+'_row">'
				+'<td ><span id="'+searchId+'_'+expenseAmountObj.expenseType+'">'+(expenseAmountObj.expenseAmount).toFixed(2)+'</span><span id="'+searchId+'_extra_'+expenseAmountObj.expenseType+'"></span><td>'
				+'</tr>';
				$("#missExpenseBody"+tableBoby+"").append(valtr);
			}
		}

	}
}
function createTripSheetExpensesRow(data, searchId, tableBoby){
	if(!tripExpenseRowAdded){
		var tripRow = '<tr>'
			//+'<td class="fit" >'+srNo+'</td>'
			
			+'<td  style="height: 24px;font-weight: bold;text-align: center;"><a href="#">TRIPSHEET EXPENSES</a></td>'
			+'</tr>';
		tripExpenseRowAdded = true;
		$('#expenseNameBody').append(tripRow);
		var tr2 = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(tr2);
	}
		
		for(var i=0; i< tripExpenses.length; i++){
			if(!(addedExpenses.indexOf(tripExpenses[i].expenseID) > -1)){
				var tr = '<tr style="font-weight: bold;" id="expenseName_'+tripExpenses[i].expenseID+'">'
					+'<td class="expenseName" id="'+tripExpenses[i].expenseID+'">'+tripExpenses[i].expenseName+'<td>'
					+'</tr>';
				$('#tripExpenseBody').append(tr);
				addedExpenses.push(tripExpenses[i].expenseID);
				allExpensesArr.push(tripExpenses[i].expenseID);
				expenseMap[tripExpenses[i].expenseID] =tripExpenses[i].expenseName;
			}
		}
		var valTR = '<tr>'
			+'<td >'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="tripExpenseValBody'+tableBoby+'">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$("#"+tableBoby+"").append(valTR);
	
	var header = '<tr>'
		+'<td  style="text-align: left; padding-left: 10px;height: 24px;font-weight: bold;text-align: center;"><a href="#"></a></td>'
		+'</tr>';
	
	$("#tripExpenseValBody"+tableBoby+"").append(header);
	
	for(var i=0; i< tripExpenses.length; i++){
		var map = data.expenseDtoList;
		if(map != undefined){
			var expenseData = map[(tripExpenses[i].expenseID+'_'+tripExpenses[i].expenseName)];
			if(expenseData != undefined){
				var tr = '<tr style="font-weight: bold;" id="'+searchId+'_'+tripExpenses[i].expenseID+'_row">'
					+'<td><span  class="dataVal" id="'+searchId+'_'+tripExpenses[i].expenseID+'">'+expenseData.expenseAmount+' </span><span id="'+searchId+'_extra_'+tripExpenses[i].expenseID+'"></span><td>'
					+'</tr>';
				$("#tripExpenseValBody"+tableBoby+"").append(tr);
			}else{
				var tr = '<tr style="font-weight: bold;" class="zero" id="'+searchId+'_'+tripExpenses[i].expenseID+'_row">'
					+'<td><span id="'+searchId+'_'+tripExpenses[i].expenseID+'">0 </span><span id="'+searchId+'_extra_'+tripExpenses[i].expenseID+'"></span><td>'
					+'</tr>';
				$("#tripExpenseValBody"+tableBoby+"").append(tr);
			}
		}else{

			var tr = '<tr style="font-weight: bold;" class="zero" id="'+searchId+'_'+tripExpenses[i].expenseID+'_row">'
				+'<td><span id="'+searchId+'_'+tripExpenses[i].expenseID+'">0 </span><span id="'+searchId+'_extra_'+tripExpenses[i].expenseID+'"></span><td>'
				+'</tr>';
			$("#tripExpenseValBody"+tableBoby+"").append(tr);
		
		}
	}
	
}
function createRenewalExpensesRow(data, searchId, tableBoby){
	if(!renewalRowAdded){
		var tripRow = '<tr>'
			//+'<td class="fit" >'+srNo+'</td>'
			+'<td  style="text-align: left; padding-left: 10px;height: 24px;font-weight: bold;text-align: center;"><a href="#">Compliances Charges</a></td>'
			+'</tr>';
		
		$('#expenseNameBody').append(tripRow);
		
		var tr2 = '<tr>'
			+'<td>'
			+'<table style="width: 100%" class="table-hover table-bordered">'
			+'<tbody id="renewalExpenseBody">'
			+'</tbody>'
			+'</table>'
			+'</td>'
			+'</tr>';
		$('#expenseNameBody').append(tr2);
		
		for(var i=0; i< renewalList.length; i++){
			var tr = '<tr style="font-weight: bold;" id="expenseName_'+renewalList[i].renewal_id+'">'
				+'<td class="expenseName" id="'+renewalList[i].renewal_id+'">'+renewalList[i].renewal_Type+'<td>'
				+'</tr>';
			$('#renewalExpenseBody').append(tr);
			addedExpenses.push(renewalList[i].renewal_id);
			allExpensesArr.push(renewalList[i].renewal_id);
			expenseMap[renewalList[i].renewal_id] =renewalList[i].renewal_Type;
		}
		
		renewalRowAdded = true;
	}
	var valTr = '<tr>'
		+'<td>'
		+'<table style="width: 100%" class="table-hover table-bordered">'
		+'<tbody id="renewalValBody'+tableBoby+'">'
		+'</tbody>'
		+'</table>'
		+'</td>'
		+'</tr>';
	$("#"+tableBoby+"").append(valTr);
	var header = '<tr>'
		+'<td  style="text-align: left; padding-left: 10px;height: 24px;font-weight: bold;text-align: center;"><a href="#"></a></td>'
		+'</tr>';
	
	$("#renewalValBody"+tableBoby+"").append(header);
	
	for(var i=0; i< renewalList.length; i++){
		var map = data.expenseDtoList;
		if(map != undefined){
			var expenseData = map[renewalList[i].renewal_id+'_'+renewalList[i].renewal_Type];
			if(expenseData != undefined){
				var tr = '<tr style="font-weight: bold;" id="'+searchId+'_'+renewalList[i].renewal_id+'_row">'
					+'<td ><span  class="dataVal" id="'+searchId+'_'+renewalList[i].renewal_id+'">'+(expenseData.expenseAmount).toFixed(2)+'</span><span id="'+searchId+'_extra_'+renewalList[i].renewal_id+'"></span> <td>'
					+'</tr>';
				$("#renewalValBody"+tableBoby+"").append(tr);
			}else{
				var tr = '<tr style="font-weight: bold;" class="zero" id="'+searchId+'_'+renewalList[i].renewal_id+'_row">'
					+'<td  ><span id="'+searchId+'_'+renewalList[i].renewal_id+'">0</span><span id="'+searchId+'_extra_'+renewalList[i].renewal_id+'"></span><td>'
					+'</tr>';
				$("#renewalValBody"+tableBoby+"").append(tr);
			}
		}else{
			var tr = '<tr style="font-weight: bold;" class="zero" id="'+searchId+'_'+renewalList[i].renewal_id+'_row">'
				+'<td><span id="'+searchId+'_'+renewalList[i].renewal_id+'"> 0 </span><span id="'+searchId+'_extra_'+renewalList[i].renewal_id+'"></span><td>'
				+'</tr>';
			$("#renewalValBody"+tableBoby+"").append(tr);
		}
	}

}
function getCompareDataOnDateChange(element){
	setTimeout(function(){
		getVehicleComparisionData(element);
	}, 100);
}
function callFunctionToSetAvg(element){
	setTimeout(function(){
		if(document.getElementById('perDaysExpenses').checked){
			setPerDayAvgForComparisionData();
		}else{
			getVehicleComparisionData(element);
		}
	}, 100);
}
function setPerDayAvgForComparisionData(){
	if(document.getElementById('perDaysExpenses').checked){
		var noOfDays = Number($('#noOfDays').val());
		if(noOfDays > 0){
			$("tbody tr td span").each(function() { 
				var dataVal = Number($(this).text());
				if(dataVal > 0){
					$(this).text(''+(dataVal/noOfDays).toFixed(2)+' / Day');
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
			var amountRow = false;
			if(fromAvg){
				temp 		   = $('#vehicleOneSearch_'+allExpensesArr[i]+'').text().split('/');
				vehicleOneData = Number(temp[0]);
				temp 		   = $('#vehicleTwoSearch_'+allExpensesArr[i]+'').text().split('/');
				vehicleTwoData = Number(temp[0]);
				temp 		   = $('#vehicleThreeSearch_'+allExpensesArr[i]+'').text().split('/');
				vehicleThreeData = Number(temp[0]);
				temp 		   = $('#vehicleFourSearch_'+allExpensesArr[i]+'').text().split('/');
				vehicleFourData = Number(temp[0]);
				temp 		    = $('#vehicleFiveSearch_'+allExpensesArr[i]+'').text().split('/');
				vehicleFiveData = Number(temp[0]);
			}else{
				vehicleOneData 		= Number($('#vehicleOneSearch_'+allExpensesArr[i]+'').text());
				vehicleTwoData 		= Number($('#vehicleTwoSearch_'+allExpensesArr[i]+'').text());
				vehicleThreeData 	= Number($('#vehicleThreeSearch_'+allExpensesArr[i]+'').text());
				vehicleFourData 	= Number($('#vehicleFourSearch_'+allExpensesArr[i]+'').text());
				vehicleFiveData 	= Number($('#vehicleFiveSearch_'+allExpensesArr[i]+'').text());
			}
			if(Number($('#vehicleOneSearch').val()) > 0 && vehicleOneData > 0){
				amountRow = true;
			}
			
			if(Number($('#vehicleTwoSearch').val()) > 0 && vehicleTwoData > 0){
				if(vehicleOneData > vehicleTwoData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 2 : '+vehicleTwoData+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleTwoData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 2 : '+vehicleTwoData+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 2 : '+vehicleTwoData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleTwoSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
				amountRow = true;
			}
			
			if(Number($('#vehicleThreeSearch').val()) > 0 && vehicleThreeData > 0){
				if(vehicleOneData > vehicleThreeData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 3 : '+vehicleThreeData+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleThreeData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 3 : '+vehicleThreeData+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 3 : '+vehicleThreeData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleThreeSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
				amountRow = true;
			}
			if(Number($('#vehicleFourSearch').val()) > 0 && vehicleFourData > 0){
				if(vehicleOneData > vehicleFourData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 4 : '+vehicleFourData+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleFourData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 4 : '+vehicleFourData+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 4 : '+vehicleFourData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleFourSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
				amountRow = true;
			}
			if(Number($('#vehicleFiveSearch').val()) > 0 && vehicleFiveData > 0){
				if(vehicleOneData > vehicleFiveData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 5 : '+vehicleFiveData+'" class="fa fa-chevron-circle-down" style="color:blue;"></i>';
				}else if(vehicleOneData < vehicleFiveData){
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 5 : '+vehicleFiveData+'"  class="fa fa-chevron-circle-up" style="color:red;"></i>';
				}else{
					compareClass = ' <i data-toggle="tip" data-original-title="Vehicle 1 : '+vehicleOneData+' and Vehicle 5 : '+vehicleFiveData+'"  class="fa fa-stop-circle" style="color: #1FB725;"></i>';
				}
				$('#vehicleFiveSearch_extra_'+allExpensesArr[i]+'').html(compareClass);
				amountRow = true;
			}
			if(!amountRow){
				$('#vehicleOneSearch_'+allExpensesArr[i]+'_row').remove();
				$('#vehicleTwoSearch_'+allExpensesArr[i]+'_row').remove();
				$('#vehicleThreeSearch_'+allExpensesArr[i]+'_row').remove();
				$('#vehicleFourSearch_'+allExpensesArr[i]+'_row').remove();
				$('#vehicleFiveSearch_'+allExpensesArr[i]+'_row').remove();
				$('#expenseName_'+allExpensesArr[i]+'').remove();
				
				//addedExpenses.remove(allExpensesArr[i]);
				var  index = addedExpenses.indexOf(allExpensesArr[i]);
				notAddedExpenses.push(allExpensesArr[i]);
				if (index > -1) {
					addedExpenses.splice(index, 1);
				}
			}else{
				
				if($('#expenseName_'+allExpensesArr[i]+'').val() == undefined){
					if(allExpensesArr[i] <= 10){
						var tr = '<tr style="font-weight: bold;" id="expenseName_'+allExpensesArr[i]+'">'
						+'<td class="expenseName" id="'+allExpensesArr[i]+'">'+expenseMap[allExpensesArr[i]]+'<td>'
						+'</tr>';
						$('#expenseBody').append(tr);
						var valtr = '<tr style="font-weight: bold;text-align: right;" class="zero" id="vehicleOneSearch_'+allExpensesArr[i]+'_row">'
										+'<td>0<td>'
									+'</tr>';
						$("#missExpenseBodyvehicleOneBody").append(valtr);
						addedExpenses.push(allExpensesArr[i]);
					}
				}
			}
		}
}

