jQuery(document).ready(
		function($) {

			$("#btn-search").click(function(event) {
				
				var jsonObject			= new Object();

				jsonObject["depotId"]		= $("#TCGroupWise").val();
				jsonObject["dateRange"]		= $("#TCDailydate").val();
				jsonObject["status"]		= $("#TripStatus").val();
				if(($("#TCGroupWise").val())=="ALL" ){					
					showMessage('info','Please Select Depot Name!');
					return false;
				}
				
				showLayer();
				
				$.ajax({
					url: "tripCollectionReportWS/getDepotWiseTripSheetStatusReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						setDailyTripDailyCashBookData(data);
					},
					 error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		             }
				});
				setTimeout(function(){ hideLayer(); }, 500);
			});

		});
	


function setDailyTripDailyCashBookData(data) {
	if(data.TDRoute != undefined) {

		var company = null;
		var TDRoute = null;
		$("#companyDetails").empty();
		company	= data.company;
		var tbody = $('<tbody>');
		var tr1 = $('<tr>');
		var tr2 = $('<tr>');

		var td1	= $('<td>');
		var td2	= $('<td>');
		var td3	= $('<td colspan="2">');

		var logo = "downloadlogo/"+company.company_id_encode+".in"
		td1.append('<img class="img-rounded " alt="Company Logo" src="'+logo+'" width="280" height="40">')
		td2.append("Print By: "+ company.firstName+"_"+company.lastName);
		td3.append("Branch : "+company.branch_name+" , Department : "+ company.department_name);

		tr1.append(td1);
		tr1.append(td2);
		tr2.append(td3);

		tbody.append(tr1);
		tbody.append(tr2);

		$("#companyDetails").append(tbody);

		$("#reportHeader").html("Depot Wise Daily Trip Collection Status Report");

		$("#advanceTable").empty();
		TDRoute	= data.TDRoute;

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
		var th11	= $('<th>');
		var th12	= $('<th>');
		var th13	= $('<th>');
		var th14	= $('<th>');


		tr1.append(th1.append("TS No"));
		tr1.append(th2.append("Date"));
		tr1.append(th3.append("Vehicle"));
		tr1.append(th4.append("Driver"));
		tr1.append(th5.append("Conductor"));
		tr1.append(th6.append("Depot"));
		tr1.append(th7.append("Route"));
		tr1.append(th8.append("Total Income"));
		tr1.append(th9.append("WT"));
		tr1.append(th10.append("Total Expense"));
		tr1.append(th11.append("OT"));
		tr1.append(th12.append("Net Collection"));
		tr1.append(th13.append("Diesel"));
		tr1.append(th14.append("Status"));
		
		tbody.append(tr1);
		var totalNetCollection 	= 0;
		var totalIncome 		= 0;
		var totalWT 			= 0;
		var totalExpense 		= 0;
		var totalOT 			= 0;
		var totalDiesel 		= 0;
		
		for(var i = 0; i < TDRoute.length; i++) {

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
			var td11	= $('<td>');
			var td12	= $('<td>');
			var td13	= $('<td>');
			var td14	= $('<td>');

			console.log("TDRoute["+i+"] : " ,TDRoute[i]);
			var curl = "showTripDaily.in?ID="+TDRoute[i].tripdailyid
			tr.append(td1.append('<a target="_blank" href="' + curl + '" value="'+TDRoute[i].tripdailynumber+'">TS-'+TDRoute[i].tripdailynumber+'</a>'));
			tr.append(td2.append(TDRoute[i].trip_OPEN_DATE));
			tr.append(td3.append(TDRoute[i].vehicle_REGISTRATION));
			tr.append(td4.append(TDRoute[i].trip_DRIVER_NAME));
			tr.append(td5.append(TDRoute[i].trip_CONDUCTOR_NAME));
			tr.append(td6.append(TDRoute[i].vehicle_GROUP));
			tr.append(td7.append(TDRoute[i].trip_ROUTE_NAME));
			tr.append(td8.append((TDRoute[i].total_INCOME).toFixed(2)));
			tr.append(td9.append((TDRoute[i].total_WT).toFixed(2)));
			tr.append(td10.append((TDRoute[i].total_EXPENSE).toFixed(2)));
			if(TDRoute[i].trip_OVERTIME != null){
				tr.append(td11.append((TDRoute[i].trip_OVERTIME).toFixed(2)));
			}else{
				TDRoute[i].trip_OVERTIME = 0;
				tr.append(td11.append((TDRoute[i].trip_OVERTIME).toFixed(2)));
			}
			var netCollection = ((TDRoute[i].total_INCOME - TDRoute[i].total_WT) - (TDRoute[i].total_EXPENSE + TDRoute[i].trip_OVERTIME));
			tr.append(td12.append(netCollection.toFixed(2)));
			tr.append(td13.append(TDRoute[i].trip_DIESEL));
			tr.append(td14.append(TDRoute[i].trip_CLOSE_STATUS));

			
			totalIncome 		+= TDRoute[i].total_INCOME;
			totalWT 			+= TDRoute[i].total_WT;
			totalExpense 		+= TDRoute[i].total_EXPENSE;
			totalOT 			+= TDRoute[i].trip_OVERTIME;
			totalDiesel 		+= TDRoute[i].trip_DIESEL;
			totalNetCollection	+= netCollection;
			
			tbody.append(tr);
		}

		var tr2 = $('<tr class="closeGroupAmount">');

		var td1		= $('<td colspan="7">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		var td8		= $('<td>');

		tr2.append(td1.append("TOTAL : "));
		tr2.append(td2.append(totalIncome.toFixed(2)));
		tr2.append(td3.append(totalWT.toFixed(2)));
		tr2.append(td4.append(totalExpense.toFixed(2)));
		tr2.append(td5.append(totalOT.toFixed(2)));
		tr2.append(td6.append(totalNetCollection.toFixed(2)));
		tr2.append(td7.append(totalDiesel));

		tbody.append(tr2);

		$("#advanceTable").append(tbody);

		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}