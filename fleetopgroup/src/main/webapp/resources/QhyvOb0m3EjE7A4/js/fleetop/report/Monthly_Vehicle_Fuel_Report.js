$(document).ready(function() {
  $("#btn-save").click(function(event) {
	  console.log("inside js")
		
		var jsonObject						= new Object();
		var startDate						= '01-';
		var startDateOfMonth				= $('#monthRangeSelector').val();

		jsonObject["startDateOfMonth"] 		= startDateOfMonth;
		
		console.log("startDateOfMonth",startDateOfMonth)
		
		if($('#monthRangeSelector').val() == ''){
			showMessage('errors', 'Please Select Month !');
			return false;
		}
		showLayer();
		$.ajax({
			url: "FuelWS/MonthlyVehicleWiseFuelReport",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data",data)
				setMonthlyVehicleWiseFuelReport(data);
				 hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
		//setTimeout(function(){ hideLayer(); }, 500);
    })
});

function setMonthlyVehicleWiseFuelReport(data) {
	var trip	= null;
	if(data.Trip != undefined) {
		$("#reportHeader").html("Monthly vehicle wise fuel Report");

		$("#trip").empty();
		trip	= data.Trip;

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
		var th10	= $('<th>');
		var th11	= $('<th>');
		var th12	= $('<th>');
		var th13	= $('<th>');


		th1.append('Sr No');
		th2.append('Id ');
		th3.append('Date');
		th4.append('Open_Km');
		th5.append('Close_Km');
		th6.append('Usage ');
		th7.append('Volume');
		th8.append('Cost');
		th9.append('Amount');
		th10.append('Km/L');
		
		th11.append('vendor_name');
		th12.append('vendor_location');
		th13.append('vehicle_registration');


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
		tr1.append(th11);
		tr1.append(th12);
		tr1.append(th13);

		var totalExpenseAmount	=0;
		var totalIncomeAmount	=0;
		var totalBalanceAmount	=0;

		thead.append(tr1);

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
			var td13	= $('<td>');

			
		
			td1.append(i+1);
			td2.append('<a href="showFuel.in?FID='+trip[i].fuel_id+'"  id="vehicleno">'+trip[i].fuel_id+'</a>');
			td3.append(trip[i].fuel_date);
			td4.append(trip[i].fuel_meter_old);
			td5.append(trip[i].fuel_meter);
			td6.append(trip[i].fuel_usage);
			td7.append(trip[i].fuel_liters+" "+trip[i].fuel_type);
			td8.append((trip[i].fuel_price ).toFixed(2)+" /UNIT ");
			td9.append(trip[i].fuel_amount.toFixed(2));
			td10.append(trip[i].fuel_kml);
			td11.append((trip[i].vendor_name));
			td12.append((trip[i].vendor_location));
			td13.append((trip[i].vehicle_registration));
			
			
			totalExpenseAmount += trip[i].fuel_liters;
			totalIncomeAmount += trip[i].fuel_amount;
			

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
			tr.append(td13);

			tbody.append(tr);
		}

		var tr2 	= $('<tr>');
		
		var td1		= $('<td colspan="6">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		

		td1.append("Total :");
		td2.append(totalExpenseAmount.toFixed(2));
		td3.append();
		td4.append(totalIncomeAmount.toFixed(2));
		

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		

		tbody.append(tr2);

		$("#trip").append(thead);
		$("#trip").append(tbody);
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