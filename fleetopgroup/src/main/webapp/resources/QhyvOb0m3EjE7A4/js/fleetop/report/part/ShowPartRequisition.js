function showAllPart(Id){

	if(Id > 0){
		var pageNumber = 1;
		var jsonObject = new Object();
		jsonObject["INVRID"] =  Id;
		jsonObject["pageNumber"]		= pageNumber;
		showLayer();
$.ajax({
	
	url: "/getPartRequisitionDetailsByINVRID",
	type: "POST",
	dataType: 'json',
	data: jsonObject,
	success: function (data) {
		setPartRequisition(data);
		hideLayer();
	},
	error: function (e) {
		showMessage('errors', 'Some error occured!')
	}
});
	}

}

function setPartRequisition(data) {
	var partRequisitionList = null;
	if(data.partRequisitionList != undefined) {

		$("#modelBodyPartReqDetails").empty();
		partRequisitionList = data.partRequisitionList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1 = $('<th>');
		var th2 = $('<th>');
		var th3 = $('<th>');


		th1.append('Sr No');
		th2.append('Part Name');
		th3.append('Quantity');


		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);

		thead.append(tr1);


		for(var i = 0; i < partRequisitionList.length; i++ ) {
			var tr = $('<tr>');

			var td1 = $('<td>');
			var td2 = $('<td>');
			var td3 = $('<td>');


			td1.append(i+1);
			td2.append(partRequisitionList[i].part_NAME);
			td3.append(partRequisitionList[i].part_REQUITED_QTY);


			tr.append(td1);
			tr.append(td2);
			tr.append(td3);

			tbody.append(tr);
		}
		
		$("#navigationBar").empty();

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getClothLaundryDetails('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		}
		
		

		$('#partModal').modal('show');
		$("#modelBodyPartReqDetails").append(thead);
		$("#modelBodyPartReqDetails").append(tbody);

	} else {
		showMessage('info','No record found !');
	}
	setTimeout(function(){ hideLayer(); }, 500);
}
