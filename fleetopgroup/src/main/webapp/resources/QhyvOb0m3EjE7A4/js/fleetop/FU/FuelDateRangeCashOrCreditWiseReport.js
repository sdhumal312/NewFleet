$(document).ready(function() {
$("#btn-search").click(function(event) {
	
		var jsonObject					        = new Object();
		jsonObject["ReportSelectVehicle"]		= $("#ReportSelectVehicle").val();
		jsonObject["selectVendor"]		        = $("#selectVendor").val();
		jsonObject["fuel_vendor_paymode"]		= $("#fuel_vendor_paymode").val();
		jsonObject["rangeFuelMileage"]			= $("#rangeFuelMileage").val();
		
	
		showLayer();
				
		$.ajax({
			url: "FuelWS/FuelRangeCashCreditWiseReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {				
				setFuelRangeCashCreditWiseReport(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
    })
});


function setFuelRangeCashCreditWiseReport(data) {
	var fuelDetails= null;
	//setDynamicData(data);
	if(data.FuelReportInfo != undefined && data.FuelReportInfo.length > 0) {
		
		$("#reportHeader").html("Fuel Range Cash Or Credit Wise Report");

		$("#advanceTable").empty();
		fuelDetails	= data.FuelReportInfo;
		
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
		var th11	= $('<th class="vehiclenametable">');
		var th12	= $('<th class="vendornametable">');
		var th13	= $('<th class="paymenttypetable">');
		
		th1.append('Sr No');
		th2.append('Fuel Number');
		th3.append('Date');
		th4.append('Open km');
		th5.append('Close km');
		th6.append('Usage');
		th7.append('Volume');
		th8.append('Amount');	
		th9.append('KM/L');
		th10.append('Cost');		
		th11.append('Vehicle Name');
		th12.append('Vendor Name');
		th13.append('Payment Type');

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

		thead.append(tr1);
		var totalUsage = 0;
		var totalVolume = 0;
		var totalAmount	= 0;
		var avgKmpl	= 0;
		var fuelDetailsLength = fuelDetails.length;
		for(var i = 0; i < fuelDetails.length; i++ ) {
			var tr = $('<tr>');
			var trTotal = $('<tr>');

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
			var td11	= $('<td class="vehiclenametable">');
			var td12	= $('<td class="vendornametable">');
			var td13	= $('<td class="paymenttypetable">');

			td1.append(i+1);			
			//td2.append(fuelDetails[i].fuel_Number);			//og code 					
			var fuel	= "showFuel.in?FID="+fuelDetails[i].fuel_id;
			
			totalUsage += fuelDetails[i].fuel_usage;
			totalVolume += fuelDetails[i].fuel_liters;
			totalAmount += fuelDetails[i].fuel_amount;
			avgKmpl += fuelDetails[i].fuel_kml;
			
			td2.append('<a target="_blank" href="' + fuel + '">'+fuelDetails[i].fuel_Number+'</a><br>');			
			td3.append(fuelDetails[i].fuel_date);	
			td4.append(fuelDetails[i].fuel_meter_old);
			td5.append(fuelDetails[i].fuel_meter);
			td6.append(fuelDetails[i].fuel_usage);			
			td7.append(fuelDetails[i].fuel_liters);
			td8.append(fuelDetails[i].fuel_amount);	
			td9.append(fuelDetails[i].fuel_kml);	
			td10.append(fuelDetails[i].fuel_cost);	
			td11.append(fuelDetails[i].vehicle_registration);
			td12.append(fuelDetails[i].vendor_name);
			td13.append(fuelDetails[i].fuel_vendor_paymode);
			
			
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
		
		var tdTaotal  	   = $('<td colspan="5">');
		var tdtotalUsage   = $('<td>');
		var tdtotalVolume  = $('<td>');
		var tdtotalAmount  = $('<td>');
		var tdavgKmpl  	   = $('<td>');
		var tdAfter  	   = $('<td colspan="3">');
		
		tdTaotal.append('<b>Total </b>');
		tdtotalUsage.append('<b> '+(totalUsage).toFixed(2)+' </b>');
		tdtotalVolume.append('<b>'+(totalVolume).toFixed(2)+' </b>');
		tdtotalAmount.append('<b>'+(totalAmount).toFixed(2)+' </b>');
		tdavgKmpl.append('<b>'+((avgKmpl/fuelDetailsLength).toFixed(2))+' </b>');
		
		trTotal.append(tdTaotal);
		trTotal.append(tdtotalUsage);
		trTotal.append(tdtotalVolume);
		trTotal.append(tdtotalAmount);
		trTotal.append(tdavgKmpl);
		trTotal.append(tdAfter);
		
		tbody.append(trTotal);
		
		
		$("#advanceTable").append(thead);
		$("#advanceTable").append(tbody);		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		
		setDynamicData(data);	//Setting Dynamic Contents for User Friendly UI
		
		
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	setTimeout(function(){ hideLayer(); }, 500);
}




//Dynamic Content  
function setDynamicData(data){
	
	if(Number($('#ReportSelectVehicle').val() > 0)){		
		var user = $('#ReportSelectVehicle').select2('data');		
		$('#vehicleName').html(user.text);
		$('.vehicleNameSelector').show();		
		$('.vehiclenametable').css('display','none');
	}else{
		$('.vehicleNameSelector').hide();
	}	
	
	if(Number($('#selectVendor').val() > 0)){
		var inspectionParameter = $('#selectVendor').select2('data');
		$('#vendorName').html(inspectionParameter.text);
		$('.vendorNameSelector').show();		
		$('.vendornametable').css('display','none');
	}else{
		$('.vendorNameSelector').hide();
	}
	
	
	if(Number($('#fuel_vendor_paymode').val() > 0)){
		var inspectionParameter = $('#fuel_vendor_paymode').select2('data');
		
		$('#paymentType').html(inspectionParameter.text);
		$('.paymentTypeSelector').show();
		
		$('.paymenttypetable').css('display','none');
	}else{
		$('.paymentTypeSelector').hide();		
	}	
}
//Dynamic Content New Mod 