$(document).ready(function() {
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



function getTripSheetDataOnDateRange() {
		var jsonObject						= new Object();
		var startDate						= '01-';
		var startDateOfMonth			= startDate + ($('#reportrange').val()).replace('/', '-');

		jsonObject["dateOfMonth"] 		= startDateOfMonth;
		jsonObject["vid"] 				= $('#ReportSelectVehicle').val();
		
		
		if($('#reportrange').val() == ''){
			showMessage('errors', 'Please Select Month !!');
			return false;
		}
		
		if(Number($('#ReportSelectVehicle').val()) <=0 ){
			showMessage('errors', 'Please Select Vehicle !');
			$('#ReportSelectVehicle').focus();
			return false;
		}
		
				
		showLayer();
		$.ajax({
			url: "getTripSheetDataOnDateRange",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data",data)
				setMonthlyTripCloseReport(data);
				 hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});

	
}

function setMonthlyTripCloseReport(data) {
	    var trip	= null;
	
	if(data.TripSheet != undefined) {
		$('#ReportTripSheetTableDetails').removeClass('hide');
		$('#remarkCol').removeClass('hide');
		
		$("#ReportTripSheetTable").empty();
		trip	= data.TripSheet;

		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		tr1.append('<th><span><input type="checkbox" id="selectAll" onclick="selectAllTripSheet();" /><span>Select</th>');
		tr1.append('<th>TS-Id</th>');
		tr1.append('<th>Vehicle</th>');
		tr1.append('<th>Group</th>');
		tr1.append('<th>Route</th>');
		tr1.append('<th>TripDate</th>');
		tr1.append('<th>Advance</th>');
		tr1.append('<th>Expense</th>');
		tr1.append('<th>Income</th>');
	//	tr1.append('<th>A/C Balance</th>');
		tr1.append('<th>Booking-Ref</th>');
		tr1.append('<th>Status</th>');

		thead.append(tr1);
		
		$('#ReportTripSheetTable').append(thead);

		for(var i = 0; i < trip.length; i++ ) {
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
			var td11	= $('<td>');
			var td12	= $('<td>');
		
			td1.append('<input type="checkbox" name="selectedTrip" id="trip_'+trip[i].tripSheetID +'" value="'+trip[i].tripSheetID +'">');
			td2.append('<a target="_blank" href="showTripSheet.in?tripSheetID='+trip[i].tripSheetID +'">' + 'TS-' + trip[i].tripSheetNumber + '</a>');
			td3.append('<a target="_blank" href="showTripSheet.in?tripSheetID=' + trip[i].tripSheetID + '"'
															 + 'data-toggle="tip" data-original-title="Click Details">'
															 +	trip[i].vehicle_registration + '</a>');
			td4.append(trip[i].vehicle_Group);
			td5.append(trip[i].routeName);
			td6.append(trip[i].tripOpenDate);
			td7.append(trip[i].tripTotalAdvance);
			td8.append('<span id="expense_'+trip[i].tripSheetID+'">'+trip[i].tripTotalexpense+'</span>');
			td9.append('<span id="income_'+trip[i].tripSheetID+'">'+trip[i].tripTotalincome+'</span>');
			//td10.append(trip[i].closeACCTripAmount);
			td11.append(trip[i].tripBookref);
			td12.append(trip[i].tripStutes);

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
			tr.append(td11);
			tr.append(td12);
			
			tbody.append(tr);
		}

		var tr2 	= $('<tr>');
		
		var td1		= $('<td colspan="6">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td colspan="2>');
		

		td1.append("Total :");
		td2.append(data.TripAdvanceTotal);
		td3.append(data.TripExpenseTotal);
		td4.append(data.TripIncomeTotal);
		td5.append(data.TripBalanceTotal);
		td6.append();

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		tr2.append(td5);
		tr2.append(td6);

		tbody.append(tr2);
		
		$("#ReportTripSheetTable").append(tbody);
	} else {
		$('#ReportTripSheetTableDetails').addClass('hide');
		$('#remarkCol').addClass('hide');
		showMessage('info','No record found !');
	}
}

function closeAccountOffSelectedTripSheet(){
		var jsonObject						= new Object();
		var selectedTripSheetIds 			= new Array();
		var selectedExpense 				= new Array();
		var selectedIncome 					= new Array();
		
		if($('#closeACCTripReference').val() == ''){
			$('#closeACCTripReference').focus();
			showMessage('errors', 'Please enter remark !');
			return false;
		}
		jsonObject["tripReference"] 		= $('#closeACCTripReference').val();
		
		$("input[name=selectedTrip]").each(function(){
			var tripId	= this.id.split('_')[1];
			if($('#'+this.id).is(":checked")){
				selectedTripSheetIds.push($(this).val());
				selectedExpense.push($('#expense_'+tripId).text());
				selectedIncome.push($('#income_'+tripId).text());
			}
		});		
		
		if(selectedTripSheetIds.length == 0 ){
			showMessage('errors', 'Please Select AtLeast One TripSheet !');
			return false;
		}
		
		
			var array	 = new Array();
				
				for(var i =0 ; i< selectedTripSheetIds.length; i++){
					var tripDetails	= new Object();
					
					tripDetails.tripSheetId		= selectedTripSheetIds[i];
					tripDetails.expense			= selectedExpense[i];
					tripDetails.income			= selectedIncome[i];
					tripDetails.balance			= Number(selectedExpense[i]) - Number(selectedIncome[i]);
					
					array.push(tripDetails);
				}
				jsonObject.tripSheetDetails = JSON.stringify(array);
				
		if (confirm("Are you sure to close a/c of selected tripsheets ?")) {
			showLayer();
			$.ajax({
				url: "closeAccountOfSelectedTripSheet",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if(data.success){
						showMessage('success', 'TripSheet Account Closed For Selected TripSheets !');
						location.reload();
					}
					 hideLayer();
				},
				error: function (e) {
					hideLayer();
				}
			}); 
		}
				
}

function selectAllTripSheet(){
	if($('#selectAll').is(":checked")){
		$("input[name=selectedTrip]").each(function(){
			$('#'+this.id).prop('checked', true);
		});
	}else{
		$("input[name=selectedTrip]").each(function(){
			$('#'+this.id).prop('checked', false);
		});
	}
}
