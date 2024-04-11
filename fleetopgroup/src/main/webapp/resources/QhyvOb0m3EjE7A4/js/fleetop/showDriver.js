function getDriverWiseIssuesList(driverId){
	var jsonObject = new Object();
	jsonObject['driverId'] = driverId;
	$.ajax({
		url: "/getDriverWiseIssuesList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showIssuesDetails(data)
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
}

function showIssuesDetails(data){
		$('#detailsTable').empty();
		var thead = $('<thead>');
		var tr1 = $('<tr>');
		var th1 = 	$('<th>');
		var th2 =	$('<th>');
		var th4 =	$('<th>');
		var th5 =	$('<th>');
		tr1.append(th1.append("Issue NO."));
		tr1.append(th2.append("Summary"));
		tr1.append(th4.append("Reported Date"));
		tr1.append(th5.append("Status"));
		thead.append(tr1);
		$('#headerName').text('Issues');
		var tbody = $('<tbody>');
		if(data.issuesList != undefined  && data.issuesList.length > 0) {
			var tasks= data.issuesList;
		for(var i = 0; i < tasks.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			var td1		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var link = '<a href="/showIssues?Id='+tasks[i].companyReference+'">I-'+tasks[i].issues_NUMBER+'</a>';
			var date = new Date(tasks[i].issues_REPORTED_DATE).toLocaleString('en-GB', { hour12: true });
			tr1.append(td1.append(link));
			tr1.append(td3.append(tasks[i].issues_SUMMARY));
			tr1.append(td4.append(date));
			tr1.append(td5.append(tasks[i].customer_NAME));
			tbody.append(tr1);
			}
		}else{
			showMessage('info','No record found !')
		}
		$('#detailsTable').append(thead);
		$('#detailsTable').append(tbody);
		$('#showDetails').modal('show');
}

function getDriverWiseCommentsList(driverId){
	showLayer()
	var jsonObject = new Object();
	jsonObject['driverId'] = driverId;
	
	$.ajax({
		url:'/getDriverWiseCommentsList',
		type:'POST',
		dataType:'json',
		data : jsonObject,
		success : function(data){
			setDriverCommentDetails(data);
		},
		error: function(e){
			console.log("error :",e)
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	})
}
function setDriverCommentDetails(data) {
	$('#detailsTable').empty();
	if (data.list != undefined && data.list.length > 0) {
		var tHead	= $('<thead>');
		var tr1 	= $('<tr>');
		
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		
		tr1.append(th1.append(' '));
		tr1.append(th2.append('Title/Name'));
		tr1.append(th3.append('Comment'));
		tHead.append(tr1);
		$('#headerName').text('Comments');
		var tBody = $('<tbody>');
		for (var i = 0; i < data.list.length; i++) {
			var singleData = data.list[i];
		var tr		= $('<tr>')
		var td1 	= $('<td>');
		var td2 	= $('<td>');
		var td3 	= $('<td>');
		tr.append(td1.append(i+1));
		tr.append(td2.append(singleData.driver_title));
		tr.append(td3.append(singleData.driver_comment));
		tBody.append(tr);
		}
		$('#detailsTable').append(tHead);
		$('#detailsTable').append(tBody);
		
			$('#showDetails').modal('show');
			hideLayer();

	} else {
		showMessage('info', 'No Records Found !! ')
		hideLayer();
	}
}

function getDriverWiseFuelMileage(driverId){
	showLayer();
	var jsonObject = new Object();
	jsonObject['driverId'] = driverId;
	
	$.ajax({
		url:'/getDriverWiseFuelMileage',
		type:'POST',
		dataType:'json',
		data : jsonObject,
		success : function(data){
			setDriverFuelMileage(data);
		},
		error: function(e){
			console.log("error :",e)
			showMessage('errors', 'Some Error Occurred!');
			hideLayer()
		}
	})
}
function setDriverFuelMileage(data){
		$('#detailsTable').empty();
	if (data != undefined &&data.list != undefined && data.list.length > 0) {
		var tHead	= $('<thead>');
		var tr1 	= $('<tr>');
		
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		
		tr1.append(th1.append('Id'));
		tr1.append(th2.append('Fuel kml'));
		tr1.append(th3.append('Date'));
		tHead.append(tr1);
		$('#headerName').text('Fuel mileage');
		var tBody = $('<tbody>');
		for (var i = 0; i < data.list.length; i++) {
			var singleData = data.list[i];
		var tr		= $('<tr>')
		var td1 	= $('<td>');
		var td2 	= $('<td>');
		var td3 	= $('<td>');
		var link ="<a href='/showFuel.in?FID="+singleData.fuel_id+"' target='_blank'>FE-"+singleData.fuel_Number+"</a>";
		tr.append(td1.append(link));
		var date ="";
		if(singleData.fuel_date != undefined && singleData.fuel_date != null){
			date= new Date(singleData.fuel_date).toLocaleString('en-GB', { hour12: true });
		}
		tr.append(td2.append(singleData.fuel_kml));
		tr.append(td3.append(date));
		tBody.append(tr);
		}
		$('#detailsTable').append(tHead);
		$('#detailsTable').append(tBody);
		
			$('#showDetails').modal('show');
			hideLayer();

	} else {
		showMessage('info', 'No Records Found !! ');
		hideLayer();
	}
}

