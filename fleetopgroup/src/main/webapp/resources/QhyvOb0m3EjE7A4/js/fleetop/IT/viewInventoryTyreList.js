 var TyreStatus;
 var globalLocationID;
function getTyreList(pageNo,status,locationId){
	var jsonObject						= new Object();
		TyreStatus							= status;
		globalLocationID					= locationId;
		
		jsonObject["status"]				= TyreStatus;
		jsonObject["locationId"]			= locationId;
		jsonObject["pageNo"]				= pageNo;

	
	$.ajax({
		url: "/getTyreCountList",
		type: "POST",
		data: jsonObject,
		dataType: 'json',
		success: function (data) {
			hideLayer();
			setTyreCountList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTyreCountList(data){
	
	$("#list1").empty();
	if(data.TyreCountList != undefined && data.TyreCountList.length > 0) {
		var Tyre = data.TyreCountList;
		var thead 	= $('<thead>');
		var tr1 	= $('<tr>');
		var tbody 	= $('<tbody>');
		
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th class="fit ar">');
		var th5		= $('<th class="fit ar">');
		var th6		= $('<th>');
		
		
		tr1.append(th1.append("Tyre No"));
		tr1.append(th2.append("Manufacturer"));
		tr1.append(th3.append("Model"));
		tr1.append(th4.append("Capacity"));
		tr1.append(th5.append("Location"));
	/*	tr1.append(th6.append("Vehicle"));*/
		tr1.append(th6.append("Status"));

		thead.append(tr1);
		if(TyreStatus == 1){
			$("#headerData").html("<h3> AVAILABLE TYRE </h3>");
		}else if(TyreStatus == 3){
			$("#headerData").html("<h3> SCRAPED TYRE</h3>");	
		}else{
			$("#headerData").html("<h3> TYRE IN-SERVICE </h3>");
		}
		for(var i = 0; i < Tyre.length; i++) {
			var tr1 = $('<tr class="ng-scope" >');
			
			var td1		= $('<td style="padding: 2px;" >');
			var td2		= $('<td style="padding: 2px;" >');
			var td3		= $('<td style="padding: 2px;" >');
			var td4		= $('<td style="padding: 2px;" >');
			var td5		= $('<td style="padding: 2px;" >');
			var td6		= $('<td style="padding: 2px;" >');
			
			if(Tyre[i].TyreStatusId == 1){
				var status		= '<span class="label label-pill label-success">'+Tyre[i].tyre_ASSIGN_STATUS+'</span>';
			}else if(Tyre[i].TyreStatusId == 3){
				var status		= '<span class="label label-pill label-danger">'+Tyre[i].tyre_ASSIGN_STATUS+'</span>';
			}else{
				var status		= '<span class="label label-pill label-warning">'+Tyre[i].tyre_ASSIGN_STATUS+'</span>';
			}
			
			
			tr1.append(td1.append('<a href="/showTyreInfo.in?Id='+Tyre[i].tyre_ID+'" target="_blank"> '+Tyre[i].tyre_NUMBER+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="#">'+Tyre[i].tyre_MANUFACTURER+'</a><br>'));
			tr1.append(td3.append(Tyre[i].tyre_MODEL));
			tr1.append(td4.append(Tyre[i].tyre_SIZE));
			tr1.append(td5.append(Tyre[i].warehouse_LOCATION));
			/*tr1.append(td6.append(Tyre[i].vehicle_registration));*/
			tr1.append(td6.append(status));
			tbody.append(tr1);
		}
		$('#tyreModelList').modal('show');
	}else{
		$('#tyreModelList').modal('hide');
		showMessage('info','No record found !')
	}
	
	
	$("#navigationBar6").empty();
	if(data.currentIndex == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar6").append('<li><a href="#" onclick="getTyreList(1,'+TyreStatus+','+globalLocationID+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar6").append('<li><a href="#" onclick="getTyreList('+(data.currentIndex - 1)+','+TyreStatus+','+globalLocationID+')">&lt;</a></li>');
	}
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar6").append('<li class="active"><a href="#" onclick="getTyreList('+i+','+TyreStatus+','+globalLocationID+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar6").append('<li><a href="#" onclick="getTyreList('+i+','+TyreStatus+','+globalLocationID+')">'+i+'</a></li>');	    	
	    }
	} 
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="getTyreList('+(data.currentIndex + 1)+','+TyreStatus+','+globalLocationID+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="getTyreList('+(data.deploymentLog.totalPages)+','+TyreStatus+','+globalLocationID+')">&gt;&gt;</a></li>');			
		}
	 }
	
	$("#list1").append(thead);
	$("#list1").append(tbody);
	
	
}