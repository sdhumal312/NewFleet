$(document).ready(function() {	
	getLoadTypesDetails();
});

//Step2 for Getting Info Start
function getLoadTypesDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getLoadTypesList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();			
			setLoadTypeList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
//Step2 for Getting Info Stop


//Step3 Start
var loadEditId 		= $('#loadEditId').val();
var loadDeleteId 	= $('#loadDeleteId').val();
function setLoadTypeList(data) {
	$("#LoadTypeTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addBatteryDiv').addClass('hide');
	$("#totalLoadType").html(data.count);
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Description"));
	
	if(loadEditId == 'true' || loadDeleteId == 'true'){
	tr1.append(th4.append("Action"));
	}
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.loadTypeList != undefined && data.loadTypeList.length > 0) {
		
		var loadTypeList = data.loadTypeList;
	
		for(var i = 0; i < loadTypeList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(loadTypeList[i].loadTypeName));
			tr1.append(td3.append(loadTypeList[i].description));
			
			//var curl = "editBatteryInvoice.in?Id="+clothTypeList[i].clothTypesId
			
			
		    if(loadDeleteId == 'true' && loadEditId == 'true'){
				tr1.append(td4.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="getLoadTypesById('+loadTypeList[i].loadTypesId+')"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteLoadType('+loadTypeList[i].loadTypesId+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
				));
			}
		    else if(loadEditId == 'true'){
				tr1.append(td4.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="getLoadTypesById('+loadTypeList[i].loadTypesId+')"><i class="fa fa-edit"></i> Edit</a></li>'
						+'</ul>'
						+'</div>'
						));
			}
		    else if(loadDeleteId == 'true'){
		    	tr1.append(td4.append(
		    			'<div class="btn-group">'
		    			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
		    			+'<ul class="dropdown-menu pull-right">'
		    			+'<li><a href="#" class="confirmation" onclick="deleteLoadType('+loadTypeList[i].loadTypesId+')"><span class="fa fa-trash"></span> Delete</a></li>'
		    			+'</ul>'
		    			+'</div>'
		    	));
		    }
		    
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#LoadTypeTable").append(thead);
	$("#LoadTypeTable").append(tbody);

}
//Step3 Stop

//Step4 Update Operations Start
function updateLoadTypes(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["loadTypesId"]			= $('#loadTypesId').val();
	jsonObject["loadTypeName"]			= $('#editLoadTypeName').val();
	jsonObject["description"]			= $('#editdescription').val();

	if($('#editLoadTypeName').val().trim() == ''){
		$('#editLoadTypeName').focus();
		showMessage('errors', 'Please Select Load Type Name !');
		hideLayer();
		return false;
	}
	
	
	$.ajax({		
		url: "editLoadTypes.do",			
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editLoadType').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			
			setLoadTypeList(data);
			showMessage('success', 'Load Types Updated Successfully!');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}
//Step4  Update Operations Stop


//Edit Logic
function getLoadTypesById(loadTypesId){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["loadTypesId"]			= loadTypesId;

	$.ajax({		
		url: "getLoadTypesById.do",		
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data , ', data);
			$('#loadTypesId').val(loadTypesId);
			$('#editLoadTypeName').val(data.loadTypes.loadTypeName);
			$('#editdescription').val(data.loadTypes.description);
			$('#editLoadType').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

//Final Step For Delete Operations Start
function deleteLoadType(loadTypesId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["loadTypesId"]			= loadTypesId;
		
		$.ajax({			
			url: "deleteLoadType.do", 
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				getLoadTypesDetails();
				 
				hideLayer();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
//Final Step For Delete Operations Stop

//Step1 for Saving Info Start
function saveLoadTypes(){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["loadTypeName"]					= $('#loadTypeName').val();
	jsonObject["description"]					= $('#description').val();
	
	
	if($('#loadTypeName').val().trim() == ''){
		$('#loadTypeName').focus();
		showMessage('errors', 'Please Select Load Type Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "saveLoadTypes",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			console.log('data', data);
			 $('#loadTypeName').val('');
			 $('#description').val('');
			$('#addManufacturer').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			setLoadTypeList(data);
			
			hideLayer();
			
			showMessage('success', 'New Load Types Saved Successfully!');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
//Step1 for Saving Info Start