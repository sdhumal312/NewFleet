function serachWoByNumber(){
	
	var jsonObject					= new Object();
	jsonObject["woNumber"]			= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Work Order Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchWoByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.WorkOrderFound != undefined && data.WorkOrderFound == true){
				hideLayer();
				window.location.replace("showWorkOrder?woId="+data.workOrderId+"");
			}
			
			if(data.WorkOrderNotFound != undefined && data.WorkOrderNotFound == true){
				hideLayer();
				showMessage('info', 'Please Enter valid Work Order Number!');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function changeStatusToInProgress(workOrderId) {


	
	if (confirm('are you sure you want to change status to In Progress?')) {
		
		if($('#addWOCompletionRemark').val() == 'true' || $('#addWOCompletionRemark').val() == true){
	   		$('#completionRemark').modal('show');
	   }else{
	   		showLayer();
	   		saveReopenStatus();
	   }
		
	} 
	
}
function reopenWOWithRemark(){
	if($('#woRemark').val() == null || $('#woRemark').val().trim() == ''){
		$('#woRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}else{
		saveReopenStatus();
	}
}

function saveReopenStatus(){
	
		var jsonObject					= new Object();
		jsonObject["workOrderId"]		= $('#workOrderId').val();
		jsonObject["woRemark"]			= $('#woRemark').val();
		showLayer();
		$.ajax({
			url: "changeWorkorderStatusToInProgress.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.accidentEntryApproved != undefined ){
					hideLayer();
					showMessage('info', data.accidentEntryApproved);
				}else if(data.statusChangedToInProgress != undefined && data.statusChangedToInProgress == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#workOrderId').val()+"");
				}
				
				if(data.NoAuthen != undefined && data.NoAuthen == true){
					showMessage('info', 'Please get Permission to change Work Order Status to In Progress !');
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});

}

function getWOTaskToPartDoc(id){
	showLayer();
	var jsonObject = new Object();
	jsonObject["workordertaskto_partid"] = id;



	$.ajax({
		url : "getDocumentWOTaskToPartList",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			hideLayer();
			taskToPartDocument(data);
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}


	});

}
function taskToPartDocument(data){
	$('#Popup').modal('hide');
	
	if(data.documents != undefined && data.documents.length > 0){
		var documentList = data.documents;
		$('#Popup').modal('show');
		$('#documentTable').empty();
		var thead 	=$('<thead>');
		var tr1 	= $('<tr>');
		
		var th1		= $('<th class="fit ar">');
		var th2     =$('<th class="fit ar">');
		var th3     =$('<th class="fit ar">');
		tr1.append(th1.append("Document name"));
		tr1.append(th2.append("Description"));
		tr1.append(th3.append("Document"));
		
		
		thead.append(tr1);
		var tbody =$('<tbody>');
		
		for(var i=0;i<documentList.length;i++){
			
			var atag=' <a target="_blank" data-toggle="tip" data-original-title="Click Download" href="download/WorkOTaskToPart/'+documentList[i]._id +' "> <i class="fa fa-download"></i>Download  </a>';
			var tr1 =$('<tr>');
			var td1 =$('<td class="fit ar">');
			var td2 =$('<td class="fit ar">');
			var td3 =$('<td class="fit ar">');
			tr1.append(td1.append(documentList[i].documentFilename));
			tr1.append(td3.append(documentList[i].description));
			tr1.append(td2.append(atag));
			
			tbody.append(tr1);
			
		}
		$("#documentTable").append(thead);
		$("#documentTable").append(tbody);
		$("#documentTable").show();
	}else{
		
		hideLayer();
		showMessage('errors', 'No Data found !');
	}
}
