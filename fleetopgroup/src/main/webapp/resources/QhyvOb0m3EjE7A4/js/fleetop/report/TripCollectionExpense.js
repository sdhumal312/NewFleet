$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
		format : 'DD-MM-YYYY',
		ranges: {
			Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
		}
	}
	, a)
}

),$(function() {
	$("#TripRouteNameList").select2( {
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
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				jsonObject["vehicleGroupId"] 	=  $('#Reportvehiclegroup').val();
				jsonObject["routeId"] 			=  $('#TripRouteNameList').val();
				jsonObject["loadTypeId"] 		=  $('#loadTypeId').val();

				if($('#Reportvehiclegroup').val() <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				$.ajax({
					url: "tripCollectionReportWS/getTripCollectionExpense",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						console.log("Exception",e)
						hideLayer();
					}
				});


			});

		});

function renderReportData(data) {
	var tripCollectionExpenseList	= null;
	if(data.TripCollectionExpenseList != undefined) {
		$("#reportHeader").html("TripSheet Collection Report");

		$("#tripCollectionExpenseList").empty();
		tripCollectionExpenseList	= data.TripCollectionExpenseList;

		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10		= $('<th>');


		th1.append('Sr No');
		th2.append('Vehicle No');
		th3.append('Trip Id');
		th4.append('Date');
		th5.append('Driver 1');
		th6.append('Driver 2');
		th7.append('Route Name');
		th8.append('Total Expense');
		th9.append('Total Income');
		th10.append('Total Balance');


		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);

		var totalExpenseAmount	=0;
		var totalIncomeAmount	=0;
		var totalBalanceAmount	=0;

		thead.append(tr1);

		for(var i = 0; i < tripCollectionExpenseList.length; i++ ) {
			var tr = $('<tr>');

			var td1 	= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');


			td1.append(i+1);
			td2.append('<a href="showVehicle?vid='+tripCollectionExpenseList[i].vid+'"  id="vehicleno">'+tripCollectionExpenseList[i].vehicle_registration+'</a>');
			td3.append('<a href="showTripSheet?tripSheetID='+tripCollectionExpenseList[i].tripSheetID+'"  id="vehicleno">'+tripCollectionExpenseList[i].tripSheetID+'</a>');
			td4.append(tripCollectionExpenseList[i].tripOpenDate);
			td5.append(tripCollectionExpenseList[i].tripFristDriverName);
			td6.append(tripCollectionExpenseList[i].tripSecDriverName);
			td7.append(tripCollectionExpenseList[i].trip_ROUTE_NAME);
			td8.append('<a href="#"  onclick="showExpensePopup('+tripCollectionExpenseList[i].tripSheetID +')">'+tripCollectionExpenseList[i].tripTotalexpense+'</a>');
			td9.append('<a href="#"  onclick="showIncomePopup('+tripCollectionExpenseList[i].tripSheetID +')">'+tripCollectionExpenseList[i].totalIncomeCollection+'</a>');
			td10.append((tripCollectionExpenseList[i].totalIncomeCollection - tripCollectionExpenseList[i].tripTotalexpense).toFixed(2));


			totalExpenseAmount += tripCollectionExpenseList[i].tripTotalexpense;
			totalIncomeAmount += tripCollectionExpenseList[i].totalIncomeCollection;
			totalBalanceAmount += (tripCollectionExpenseList[i].totalIncomeCollection - tripCollectionExpenseList[i].tripTotalexpense);


			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
			tr.append(td10);

			tbody.append(tr);
		}

		var tr2 	= $('<tr>');
		
		var td1		= $('<td colspan="7">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');

		td1.append("Total :");
		td2.append(totalExpenseAmount.toFixed(2));
		td3.append(totalIncomeAmount.toFixed(2));
		td4.append(totalBalanceAmount.toFixed(2));

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);

		tbody.append(tr2);

		$("#tripCollectionExpenseList").append(thead);
		$("#tripCollectionExpenseList").append(tbody);
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	setTimeout(function(){ hideLayer(); }, 500);
}
/***********This is popup Window For Expense*****************/
function showExpensePopup(tripId){
	if(tripId > 0){

		var jsonObject				= new Object();
		jsonObject["TripSheetID"] 	=  tripId;

		showLayer();
		$.ajax({
			url: "tripCollectionReportWS/TripExpense",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setModelDataExpense(data);
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

}
function setModelDataExpense(data){
	$('#modelBodyExpense').html('');
	if(data.TripSheetExpense != undefined && data.TotalExpense != undefined){
		for(var i = 0 ; i< data.TripSheetExpense.length; i++){
			var modelBody = "";
			modelBody = '<div class="row1">'
				+'<label class="col-md-3 col-sm-3 col-xs-12 control-label">'+data.TripSheetExpense[i].expenseName+' :</label>'
				+'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" readonly = "readonly" class="form-text" value="'+data.TripSheetExpense[i].expenseAmount+'" name="expenses" id="expenses'+i+'">'
				+'</div></div><br/><br/>';
			

			$('#modelBodyExpense').append(modelBody);
		}
		var modelBody1 = '<div class="row1">'
		+'<label class="col-md-3 col-sm-3 col-xs-12 control-label">Total Expense</label>'
		+'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" readonly = "readonly" class="form-text" value="'+data.TotalExpense.tripTotalexpense+'" name="expenses" id="expenses">'
		+'</div></div><br/><br/>';
		
		$('#getExpensesModel').modal('show');
		$('#modelBodyExpense').append(modelBody1);
	}else{
		showMessage('info','No Expenses Found !');
	}
}
/***********This is popup Window For Income*****************/
function showIncomePopup(tripId){
	if(tripId > 0){

		var jsonObject				= new Object();
		jsonObject["TripSheetID"] 	=  tripId;

		showLayer();
		$.ajax({
			url: "tripCollectionReportWS/TripIncome",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setModelDataIncome(data);
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

}

function setModelDataIncome(data){
	$('#modelBodyIncome').html('');
	if(data.TripSheetIncome != undefined && data.TotalIncome != undefined){
		for(var i = 0 ; i< data.TripSheetIncome.length; i++){
			var modelBody = "";
			modelBody = '<div class="row1">'
				+'<label class="col-md-3 col-sm-3 col-xs-12 control-label">'+data.TripSheetIncome[i].incomeName+' :</label>'
				+'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" readonly = "readonly" class="form-text" value="'+data.TripSheetIncome[i].incomeAmount+'" name="expenses" id="expenses'+i+'">'
				+'</div></div><br/><br/>';
			

			$('#modelBodyIncome').append(modelBody);
		}
		var modelBody1 = '<div class="row1">'
		+'<label class="col-md-3 col-sm-3 col-xs-12 control-label">Total Income</label>'
		+'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" readonly = "readonly" class="form-text" value="'+data.TotalIncome.tripTotalincome+'" name="expenses" id="expenses">'
		+'</div></div><br/><br/>';
		
		$('#getIncomeModel').modal('show');
		$('#modelBodyIncome').append(modelBody1);
	}else{
		showMessage('info','No Income Found !');
	}
}
