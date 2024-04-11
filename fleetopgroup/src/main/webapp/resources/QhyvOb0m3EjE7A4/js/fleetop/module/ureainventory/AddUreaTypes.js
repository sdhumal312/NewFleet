$(document).ready(function() {	
	getUreaTypesDetails();					
});

//Step2 for Getting Info Start
function getUreaTypesDetails() {			
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getUreaTypesList",		
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();			
			setUreaTypeList(data);		
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
//Step2 for Getting Info Stop


//Step3 Start
var ureaEditId 		= $('#ureaEditId').val();			
var ureaDeleteId 	= $('#ureaDeleteId').val();			
function setUreaTypeList(data) {			
	$("#UreaTypeTable").empty();				
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addBatteryDiv').addClass('hide');
	$("#totalUreaType").html(data.count);
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Description"));
	
	if(ureaEditId == 'true' || ureaDeleteId == 'true'){
	tr1.append(th4.append("Action"));
	}
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ureaTypeList != undefined && data.ureaTypeList.length > 0) {
		
		var ureaTypeList = data.ureaTypeList;
	
		for(var i = 0; i < ureaTypeList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(ureaTypeList[i].manufacturerName));	
			tr1.append(td3.append(ureaTypeList[i].description));	
				
			//var curl = "editBatteryInvoice.in?Id="+clothTypeList[i].clothTypesId
			
		    if(ureaDeleteId == 'true' && ureaEditId == 'true'){
				tr1.append(td4.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="getUreaTypesById('+ureaTypeList[i].ureaManufacturerId+')"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteUreaType('+ureaTypeList[i].ureaManufacturerId+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
				));
			}
		    else if(ureaEditId == 'true'){
				tr1.append(td4.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="getUreaTypesById('+ureaTypeList[i].ureaManufacturerId+')"><i class="fa fa-edit"></i> Edit</a></li>'
						+'</ul>'
						+'</div>'
						));
			}
		    else if(ureaDeleteId == 'true'){
		    	tr1.append(td4.append(
		    			'<div class="btn-group">'
		    			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
		    			+'<ul class="dropdown-menu pull-right">'
		    			+'<li><a href="#" class="confirmation" onclick="deleteUreaType('+ureaTypeList[i].ureaManufacturerId+')"><span class="fa fa-trash"></span> Delete</a></li>'
		    			+'</ul>'
		    			+'</div>'
		    	));
		    }
		    
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#UreaTypeTable").append(thead);		
	$("#UreaTypeTable").append(tbody);		

}
//Step3 Stop

//Step4 Update Operations Start
function updateUreaTypes(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["ureaManufacturerId"]	= $('#ureaManufacturerId').val();
	jsonObject["ureaTypeName"]			= $('#editUreaTypeName').val();
	jsonObject["description"]			= $('#editdescription').val();

	//newy
	if($('#editUreaTypeName').val().trim() == ''){
		$('#editUreaTypeName').focus();
		showMessage('errors', 'Please Select Urea Manufacturer Name !');
		hideLayer();
		return false;
	}
	
	
	$.ajax({		
		url: "editUreaTypes.do",			
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editUreaType').modal('hide');		
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			
			setUreaTypeList(data);
			showMessage('success', 'Urea Manufacturer Updated Successfully!');
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
function getUreaTypesById(ureaManufacturerId){					
	showLayer();
	var jsonObject						= new Object();
	jsonObject["ureaManufacturerId"]			= ureaManufacturerId;

	$.ajax({		
		url: "getUreaTypesById.do",	
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data , ', data);
			$('#ureaManufacturerId').val(ureaManufacturerId);				
			$('#editUreaTypeName').val(data.ureaTypes.manufacturerName);		
			$('#editdescription').val(data.ureaTypes.description);			
			$('#editUreaType').modal('show');		
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

//Final Step For Delete Operations Start
function deleteUreaType(ureaManufacturerId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["ureaManufacturerId"]			= ureaManufacturerId;
		
		$.ajax({			
			 
			url: "deleteUreaType.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				getUreaTypesDetails();			
				 
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

//Step1 for Saving Info of Urea Manufacturer Model Start
function saveUreaTypes(){
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["ureaTypeName"]					= $('#ureaTypeName').val();
	jsonObject["description"]					= $('#description').val();
	
	console.log("jsonObject ",jsonObject)
	if($('#ureaTypeName').val().trim() == ''){
		$('#ureaTypeName').focus();
		showMessage('errors', 'Please Select Urea Manufacturer Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "saveUreaTypes",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			console.log('data', data);
			 $('#ureaTypeName').val('');
			 $('#description').val('');
			$('#addManufacturer').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('errors', 'Already Exist!');
				hideLayer();
				return false;
			}
			setUreaTypeList(data)		
			hideLayer();
			
			showMessage('success', 'New Urea Manufacturer Saved Successfully!');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
//Step1 for Saving Info of Urea Manufacturer Model Stop