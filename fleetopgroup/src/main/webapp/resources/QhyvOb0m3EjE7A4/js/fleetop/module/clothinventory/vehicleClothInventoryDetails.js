$(document).ready(function() {
	getVehicleCLothAsignmentDetails();
});

function getVehicleCLothAsignmentDetails() {
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "getVehicleCLothAsignmentDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleCLothAsignmentDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleCLothAsignmentDetails(data){
	if(data.vehicle != undefined){
		setVehicleData(data.vehicle);
	}
	if(data.clothInventoryDetailsList != undefined && data.clothInventoryDetailsList.length > 0){
		setclothInventoryDetails(data.clothInventoryDetailsList);
	}else{
		$('#addMore').html('Add Cloth Details');
	}
}
function setclothInventoryDetails(clothInventoryDetailsList){
	
	var clothTypesId = "";
	
	var srNO = 1;
	for(var i = 0; i<clothInventoryDetailsList.length; i++){
		
		clothTypesId	+= clothInventoryDetailsList[i].clothTypesId+",";
		
		var tr =' <tr data-object-id="">'
			+'<td class="fit" >'+srNO+'</td>'
			+'<td>'+clothInventoryDetailsList[i].clothTypesName+' <input type="hidden" name="cloth" value="'+clothInventoryDetailsList[i].clothTypesId+'" id="cloth'+clothInventoryDetailsList[i].clothTypesId+'"/></td>'
			+'<td>'+clothInventoryDetailsList[i].quantity+'</td>'
			+'<td>'+clothInventoryDetailsList[i].maxAllowedQuantity+'</td>'
			+'<td><in><input type="button" class="btn btn-warning" value="Remove" onclick="removeClothDetails('+clothInventoryDetailsList[i].vehicleClothInventoryDetailsId+');" /></td>'
			/*+'<td><input type="button" class="btn btn-danger" value="Delete" /></td>'*/
			+'</tr>';
		$('#clothDetails').append(tr);
		srNO++;
	}
	
	$('#clothTypesIds').val(clothTypesId);
} 
function setVehicleData(vehicle){
	var valcal = 'showVehicle.in?vid='+vehicle.vid;
	$("#showvehicle").prop("href", valcal);
	$("#cancel").prop("href", valcal);
	$('#showvehicle').html(vehicle.vehicle_registration);
	
	$('#vehicleStatus').html(vehicle.vehicle_Status);
	$('#odometer').html(vehicle.vehicle_Odometer);
	$('#vehicleType').html(vehicle.vehicle_Type);
	$('#vehicleLocation').html(vehicle.vehicle_Location);
	$('#vehicleGroup').html(vehicle.vehicle_Group);
	$('#vehicleRoute').html(vehicle.vehicle_RouteName);
	
	
}


function saveClothInventoryDetails(){
	
	var jsonObject					= new Object();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["clothTypes"]		= $('#clothTypes').val();
	jsonObject["quantity"]			= $('#quantity').val();
	jsonObject["typeOfCloth"]		= $('#typeOfCloth').val();
	jsonObject["locationId"]		= $('#locationId').val();
	
	
	if(Number($('#clothTypes').val()) <= 0){
		$("#clothTypes").select2('focus');
		showMessage('info', 'Please Select Cloth Types !');
		return false;
	}
	
	if(Number($('#locationId').val()) <= 0){
		$("#locationId").select2('focus');
		showMessage('info', 'Please Select WareHouse Location !');
		return false;
	}
	
	if(Number($('#quantity').val()) <= 0){
		$("#quantity").focus();
		showMessage('info', 'Please Enter Quantity !');
		return false;
	}
	
	if($('#allowMultipleLocations').val() == 'true'){
		showMessage('info', 'Upholstery Already Assigned To A Location. Please Remove Upholstery First For Assigning To Different Locations.');
		return false;
	}
	
	if(!validateQuantity()){
		return false;
	}
	
	showLayer();
	
	$.ajax({
		url: "saveVehicleClothInventoryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				$('#addClothDetails').modal('hide');
				//window.location.replace("VehiclePartInventoryDetails?Id="+$('#vid').val()+"&saved=true");
				showMessage('info', 'Please Assign Upholstrey and Max Qty to Vehicle First!');
				hideLayer();
				return false;
			}
			
			if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
				$('#addClothDetails').modal('hide');
				showMessage('info', 'You can not Add Quantity More Than Max Allowed Quantity !');
				hideLayer();
				return false;
			}
			
			if(data.saved){
				window.location.replace("VehiclePartInventoryDetails?Id="+$('#vid').val()+"&saved=true");
				hideLayer();	
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}

$(document).ready(function() {
	$("#clothTypes").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getClothTypesListByClothTypesId.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
        	if(Number($('#locationId').val()) <= 0){
        		showMessage('info', 'Please Select Location First !');
        		$('#locationId').select2('focus');
        		return false;
        	}	
            return {
                term: a
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.clothTypeName,
                        slug: a.slug,
                        id: a.clothTypesId
                    }
                    
                })
            }
        }
    }
}),$("#clothTypes").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCount.in", {
    	locationId : $('#locationId').val(),
    	clothTypesId : $("#clothTypes").val(),
    	vehicleId : $('#vid').val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	
    	 if(e.htData.StockNotFound != undefined){
         	showMessage('info', 'Upholstery Not Found at this Location. Please Add Upholstery Inventory For this Location First.');
         } 
    	
        $('#usedQuantity').val(e.htData.detailsDto.usedStockQuantity);
        $('#newQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#stockQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#maxAllowedQuantity').val(e.htData.MaxQuantity);
        $('#remainingQuantity').val(e.htData.RemainingQuantity);
        $('#remainingMaxAllowed').val(e.htData.RemainingQuantity);
        $('#allowMultipleLocations').val('false');
        
        if(e.htData.MultipleLocationDenied != undefined){
        	showMessage('info', 'Upholstery Already Assigned To A Location. Please Remove Upholstery First For Assigning To Different Locations.');
        	$('#allowMultipleLocations').val('true');
        } 
    })
    hideLayer();
}),$("#typeOfCloth").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCount.in", {
    	locationId : $('#locationId').val(),
    	clothTypesId : $("#clothTypes").val(),
    	vehicleId : $('#vid').val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	var typeOfCloth	= $('#typeOfCloth').val();
    	if(typeOfCloth == 1){
    		$('#stockQuantity').val(e.htData.detailsDto.newStockQuantity);
    	} else {
    		$('#stockQuantity').val(e.htData.detailsDto.usedStockQuantity);	
    	}
        
    })
}),$("#locationId").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
            return {
                term: a
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.partlocation_name,
                        slug: a.slug,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
});
	var saved 			= getUrlParameter('saved');
	var deleted			= getUrlParameter('deleted');
	var noRecordFound 	= getUrlParameter('noRecordFound');
if(saved == true || saved == 'true'){
	showMessage('success', 'Data Saved Successfully !');
}
if(deleted == true || deleted == 'true'){
	showMessage('success', 'Data Deleted Successfully !');
}
if(noRecordFound == true || noRecordFound == 'true'){
	showMessage('info', 'No Record Found !');
}

});
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}



function sumthere(e, n, t, r, a) {
	
    var i = document.getElementById(e).value,
        o = document.getElementById(n).value,
        l = document.getElementById(t).value,
        c = document.getElementById(r).value,
        s = parseFloat(i) * parseFloat(o),
        d = s * l / 100,
        u = s - d,
        e = u * c / 100,
        y = u + e;
    isNaN(y) || (document.getElementById(a).value = y.toFixed(2))
}
function isNumberKeyQut(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPin").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorPin").style.display = t ? "none" : "inline", t
}

function isNumberKeyEach(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyDis(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyTax(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}


function openAddClothDetailsModel(){
	$('#addClothDetails').modal('show');
}

$(document).ready(function() {
	$("#removelocationId").select2({
	    minimumInputLength: 2,
	    minimumResultsForSearch: 10,
	    ajax: {
	        url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
	        dataType: "json",
	        type: "POST",
	        contentType: "application/json",
	        quietMillis: 50,
	        data: function(a) {
	            return {
	                term: a
	            }
	        },
	        results: function(a) {
	            return {
	                results: $.map(a, function(a) {
	                    return {
	                        text: a.partlocation_name,
	                        slug: a.slug,
	                        id: a.partlocation_id
	                    }
	                })
	            }
	        }
	    }
	});
});

function removeClothDetails(vehicleClothInventoryDetailsId){
	var jsonObject						= new Object();
	
	var ans = confirm("Are you sure to Remove ?");
	if(ans){
		showLayer();
		jsonObject["vehicleClothInventoryDetailsId"]	= vehicleClothInventoryDetailsId;
		$.ajax({
			url: "getVehicleClothInventoryDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.vehicleClothInventoryDetails != undefined){
					
					$('#removeClothTypes').val(data.vehicleClothInventoryDetails.clothTypesName);
					$('#removelocationId').val(data.vehicleClothInventoryDetails.locationName);
					$('#asignedQuantity').val(data.vehicleClothInventoryDetails.quantity);
					$('#removeQuantity').val(data.vehicleClothInventoryDetails.quantity);
					$('#vehicleClothInventoryDetailsId').val(vehicleClothInventoryDetailsId);
					$('#locationId').val(data.vehicleClothInventoryDetails.locationId);
					$('#removeClothTypesId').val(data.vehicleClothInventoryDetails.clothTypesId);
					
					var VID = $("#locationId").val();
					var VText = $("#removelocationId").val();
					$('#removelocationId').select2('data', {
					id : VID,
					text : VText
					});
					
					$('#removeClothDetails').modal('show');	
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	
}
function removeClothInventoryDetails(){
	
	var jsonObject						= new Object();
	
		if(Number($('#removelocationId').val()) <= 0){
			$("#removelocationId").select2('focus');
			showMessage('info', 'Please Select WareHouse Location !');
			return false;
		}
		
		if(Number($('#removeQuantity').val()) <= 0){
			showMessage('info', 'Remove Quantity Should Be Greater Than Zero (0) !');
			return false;
		}
		
		var asignedQuantity = Number($('#asignedQuantity').val());
		if(Number($('#removeQuantity').val()) > asignedQuantity){
			showMessage('info', 'Remove Quantity Should Be Less Than '+asignedQuantity);
			return false;
		}
		
		showLayer();
		jsonObject["locationId"]						= $('#locationId').val();
		jsonObject["vehicleClothInventoryDetailsId"]	= $('#vehicleClothInventoryDetailsId').val();
		jsonObject["removeQuantity"]					= $('#removeQuantity').val();
		jsonObject["vid"]								= $('#vid').val();
		jsonObject["clothTypesId"]						= $('#removeClothTypesId').val();
		jsonObject["removelocationId"]					= $('#removelocationId').val();
		jsonObject["asignedQuantity"]					= $('#asignedQuantity').val();
		
		$.ajax({
			url: "removeClothInventoryDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
					$('#removeClothDetails').modal('hide');
					showMessage('info', 'You can not Remove Quantity More Than Assigned Quantity !');
					hideLayer();
					return false;
				}
				
				window.location.replace("VehiclePartInventoryDetails?Id="+$('#vid').val()+"&saved=true");
				hideLayer();
				
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});

}

function deleteLaundryRate(rateId){
	var jsonObject						= new Object();
	
	var ans = confirm("Are you sure to Delete ?");
	if(ans){
		showLayer();
		jsonObject["rateId"]	= rateId;
		$.ajax({
			url: "deleteLaundryRate",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted)
					window.location.replace("VehiclePartInventoryDetails?Id="+$('#vid').val()+"&saved=true");
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	
}

function validateQuantity(){
	
	var typeOfCloth				= $('#typeOfCloth').val();
	var quantity 				= Number($('#quantity').val());
	var newQuantity				= Number($('#newQuantity').val());
	var usedQuantity			= Number($('#usedQuantity').val());
	var maxAllowed				= Number($('#maxAllowedQuantity').val());
	var remainingMaxAllowed		= Number($('#remainingMaxAllowed').val());
	var finalAllowedQuantity	= Number($('#maxAllowedQuantity').val()) - Number($('#remainingMaxAllowed').val());
	
	if(typeOfCloth == 1){
		if(quantity > newQuantity){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than '+newQuantity);
			return false;
		}
		if(quantity > maxAllowed && maxAllowed != 0){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than Max Allowed Quantity '+maxAllowed);
			return false;
		}
		if(quantity > finalAllowedQuantity){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than '+finalAllowedQuantity+'. Already Assigned '+remainingMaxAllowed+'. Max Allowed is '+maxAllowed);
			return false;
		}
	}else if(typeOfCloth == 2){
		if(quantity > usedQuantity){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than '+usedQuantity);
			return false;
		}
		if(quantity > maxAllowed && maxAllowed != 0){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than Max Allowed Quantity '+maxAllowed);
			return false;
		}
		if(quantity > finalAllowedQuantity){
			$('#quantity').val(0);
			showMessage('info', 'You can not Add Quantity More Than '+finalAllowedQuantity+'. Already Assigned '+remainingMaxAllowed+'. Max Allowed is '+maxAllowed);
			return false;
		}
	}
	
	return true;
	
}

var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39)

function showHideClothDetails(){
	if(!$('#showData').hasClass('hide')){
		$('#showData').hide();
		$('#showData').addClass('hide')
		getShowClothAssignDetails(1);
		$('#clothDetailsTable').show();
	}else{
		$('#clothDetailsTable').hide();
		$('#showData').removeClass('hide');
		$('#showData').show();
		$('#addMore').show();
		//getVehicleCLothAsignmentDetails();
	}
}

function getShowClothAssignDetails(pageNumber) {
	showLayer();
	var jsonObject			 = new Object();
	jsonObject["vid"]		 = $('#vid').val();
	jsonObject["pageNumber"] = pageNumber;
	
	
	$.ajax({
		url: "getShowClothAssignDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setShowClothAssignDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setShowClothAssignDetails(data) {
	
	
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Stock Type"));
	tr1.append(th6.append("Assigned/Removed"));
	tr1.append(th7.append("Date"));
	tr1.append(th8.append("Assigned/Removed By"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ShowClothAssignDetailsList != undefined && data.ShowClothAssignDetailsList.length > 0) {
		
		var ShowClothAssignDetailsList = data.ShowClothAssignDetailsList;
	
		for(var i = 0; i < ShowClothAssignDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(ShowClothAssignDetailsList[i].locationName));
			tr1.append(td3.append(ShowClothAssignDetailsList[i].clothTypeName));
			tr1.append(td4.append(ShowClothAssignDetailsList[i].quantity));
			if(ShowClothAssignDetailsList[i].stockTypeId == 1){
				tr1.append(td5.append('<span class="label label-default label-warning">'+ShowClothAssignDetailsList[i].stockTypeName));
			} else {
				tr1.append(td5.append('<span class="label label-default label-success">'+ShowClothAssignDetailsList[i].stockTypeName));
			}
			if(ShowClothAssignDetailsList[i].asignType != 1){
				tr1.append(td6.append('<span class="label label-default label-warning">'+ShowClothAssignDetailsList[i].asignTypeStr));
			} else {
				tr1.append(td6.append('<span class="label label-default label-success">'+ShowClothAssignDetailsList[i].asignTypeStr));
			}
			tr1.append(td7.append(ShowClothAssignDetailsList[i].createdOnStr));
			tr1.append(td8.append(ShowClothAssignDetailsList[i].createdByName));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getShowClothAssignDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getShowClothAssignDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getShowClothAssignDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getShowClothAssignDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getShowClothAssignDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getShowClothAssignDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}
