jQuery(document).ready(
	function($) {
		$("#btn-save").click(function(event) {
			if(basicValidation()) {
				showLayer();
				var jsonObject					= new Object();
				jsonObject["routeId"]			= $("#TripRouteList").val();
				jsonObject["tripDateRange"]		= $("#LocWorkOrder").val();
				$.ajax({
					url: "routeWiseTripSheetReportWS/getRouteWiseTripSheetReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setRouteWiseTripSheetReportData(data);
					},
					error: function (e) {
						hideLayer();
					}
				});
			}
		});
});

function basicValidation() {
	if($("#TripRouteList").val() == "" || $("#TripRouteList").val() == 0) {
		showMessage('warning',"Select Route.");
		return false;
	}
	return true;
}

function setRouteWiseTripSheetReportData(data) {
	var tripSheetList	= null;
	if(data.TripSheetList != undefined) {
		$("#reportHeader").html("Route Wise Trip Sheet Report");

		$("#tripSheetDetails").empty();
		tripSheetList	= data.TripSheetList;
		var thead = $('<thead>');
		var tbody = $('<tbody>');
		var tr1 = $('<tr>');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10	= $('<th>');

		th1.append('TS-Id');
		th2.append('Trip Date');
		th3.append('Vehicle');
		th4.append('O-C(km)');
		th5.append('Trip-Km');
		th6.append('Income');
		th7.append('Expense');
		th8.append('Balance');
		th9.append('B-Ref');
		th10.append('Status');

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

		thead.append(tr1);

		for(var i = 0; i < tripSheetList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');

			var curl = "showTripSheet.in?tripSheetID="+tripSheetList[i].tripSheetID;
			td1.append('<a target="_blank" href="' + curl + '">'+"TS-"+tripSheetList[i].tripSheetNumber+'</a>');
			if(tripSheetList[i].closetripDate != null) {
				td2.append(tripSheetList[i].tripOpenDate + "</br>" + tripSheetList[i].closetripDate);
			} else {
				td2.append(tripSheetList[i].tripOpenDate);
			}
			var curl = "showVehicle.in?vid="+tripSheetList[i].vid;
			td3.append('<a target="_blank" href="' + curl + '">'+tripSheetList[i].vehicle_registration+'</a>');
			if(tripSheetList[i].tripClosingKM != null) {
				td4.append(tripSheetList[i].tripOpeningKM +"</br>" + tripSheetList[i].tripClosingKM);
			} else {
				td4.append(tripSheetList[i].tripOpeningKM);
			}
			td5.append(tripSheetList[i].tripUsageKM);
			td6.append(tripSheetList[i].tripTotalincome);
			td7.append(tripSheetList[i].tripTotalexpense);
			td8.append(tripSheetList[i].closeACCTripAmount);
			td9.append(tripSheetList[i].tripBookref);
			td10.append(tripSheetList[i].tripStutes);

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

		$("#tripSheetDetails").append(thead);
		$("#tripSheetDetails").append(tbody);
		
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