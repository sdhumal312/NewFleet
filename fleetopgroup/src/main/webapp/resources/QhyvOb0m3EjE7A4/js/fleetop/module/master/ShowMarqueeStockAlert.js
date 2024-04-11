function stockalert(){

	$.getJSON("MarqueeMasterRestController/getStockWiseMarqueeAlert.in", function(data) {
		$('#PopupStock').modal('hide');
		if(data.fuelStock != undefined || data.UreaStock != undefined){
			$('#PopupStock').modal('show');
			if(data.fuelStock != undefined){
				$("#fuelRow").removeClass('hide');
				showFuelModelList(data.fuelStock ,data.FuelThreashold);
			}
			if(data.UreaStock != undefined){
				 $("#ureaRow").removeClass('hide');
					showUreaModelList(data.UreaStock, data.UreaThreashold);
			
			}
		}
	});
}




function showFuelModelList(data,fuelThreashold){
	$('#dataFuelTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');

	tr1.append(th1.append("Sr no."));
	tr1.append(th2.append("Petrol Pump"));

	tr1.append(th3.append("Stock Liters"));
	tr1.append(th4.append("Threashold Limit"));

	thead.append(tr1);

	var tbody = $('<tbody>');
	if(data != undefined) {
		var stockDetails = data;
		for(var i=0;i<stockDetails.length;i++ ) {


			var tr1 = $('<tr class="ng-scope">');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');

			tr1.append(td1.append(i+1));
			tr1.append(td2.append(stockDetails[i].vendorName));
			tr1.append(td3.append((stockDetails[i].stockQuantity).toFixed(2).fontcolor('#FF0000')));
			tr1.append(td4.append(fuelThreashold));

			tbody.append(tr1);
		}
		$("#dataFuelTable").append(thead);
		$("#dataFuelTable").append(tbody);
		$('#dataFuelTable').show();
	}
}

function showUreaModelList(data,ureaThreashold){
	$('#dataUreaTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');

	tr1.append(th1.append("Sr no."));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Stock Liters"));
	tr1.append(th4.append("Threashold Limit"));

	thead.append(tr1);

	var tbody = $('<tbody>');
	if(data != undefined) {
		var stockDetails = data;
		for(var i=0;i<stockDetails.length;i++ ) {


			var tr1 = $('<tr class="ng-scope">');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');

			tr1.append(td1.append(i+1));
			tr1.append(td2.append(stockDetails[i].locationName));
			tr1.append(td3.append((stockDetails[i].stockQuantity).toFixed(2).fontcolor('#FF0000')));
			tr1.append(td4.append(ureaThreashold));


			tbody.append(tr1);
		}

		$('#dataUreaTable').show();

		$("#dataUreaTable").append(thead);
		$("#dataUreaTable").append(tbody);


	}
}
