$(document).ready(function() {
	$('#vehicleMake').on('change', function() {
         $("#vehicleModel").select2("val", "");
	});
	$('#couponDetails').on('paste', function (event) {
	  if (event.originalEvent.clipboardData.getData('Text').match(/[^\d]/)) {
	    event.preventDefault();
	  }
	});
	$("#repairingVendor").select2( {
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple:!0, 
        ajax: {
            url: "getAllTypeOfVendorAutoComplete.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e,
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }
    }
    ),$("#purchaseVendor").select2( {
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple:!0, 
        ajax: {
            url: "getAllTypeOfVendorAutoComplete.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e,
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }
    }
    ),$("#childPartDetails").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        multiple:!0,
        ajax: {
            url: "getSearchMasterPartOnType.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e,
                    partTypeCategory : $('#partTypeCatgory').val()
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    }),$("#subtituteParts").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        multiple:!0,
        ajax: {
            url: "getSearchMasterPart.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    }),$("#vehicleMake").select2({
		multiple:!0,
		ajax : {
            url:"vehicleManufacturerAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleManufacturerName, slug: t.slug, id: t.vehicleManufacturerId
                        }
                    }
                    )
                }
            }
        }
	
	}),$("#vehicleModel").select2({
		multiple:!0,
		ajax : {
            url:"vehicleModelAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t,
                    manufacturer : $('#vehicleMake').val()
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleModelName, slug: t.slug, id: t.vehicleModelId
                        }
                    }
                    )
                }
            }
        }
	
	})
});

function toggleBookMark(id){
	 $('#myTabs').find('a').each(function() {
	        if(id != $(this).attr('id')){
	        	$('#'+$(this).attr('id')+'').removeClass('btn btn-success');
	        	$('#'+$(this).attr('id')+'').addClass('btn btn-info');
	        	
	        	$('#'+$(this).attr('id')+'Div').hide();
	        	
	        }else{
	        	$('#'+$(this).attr('id')+'Div').show();
	        	$('#'+id+'').removeClass('btn btn-info');
	        	$('#'+id+'').addClass('btn btn-success');
	        }
	        
	        if(id != $(this).attr('id')){
	        	$('#'+$(this).attr('id')+'').removeClass('btn btn-success');
	        	$('#'+$(this).attr('id')+'').addClass('btn btn-info');
	        }else{
	        	$('#'+id+'').removeClass('btn btn-info');
	        	$('#'+id+'').addClass('btn btn-success');
	        }
	        
	    });
}
function showHideWarrantyMOnthRow(){
	if($('#warranty').val() == 'true'){
		$('#warrantyInMonthRow').show();
	}else{
		$('#warrantyInMonthRow').hide();
	}
}

function showCouponDetailsRow(){
	if($('#couponAvailable').val() == 'true'){
		$('#couponDetailsDiv').show();
	}else{
		$('#couponDetailsDiv').hide();
	}
}
function showHideReckDetailsRow(evt){
	 $('.recDiv').each(function() {
		    if(this.id == 'rowDiv_'+evt.id){
		    	 $('#rowDiv_'+evt.id+'').show();
		    	 $('#'+evt.id).addClass('active');
		    }else{
		    	 $('#'+this.id+'').hide();
		    	  $('#'+this.id.split('_')[1]).removeClass('active');
		    }
	  });
}

function showHideRepairingVendor(){
	if($('#repairable').val() == 'true'){
		$('#vendorDiv').show();
	}else{
		$('#vendorDiv').hide();
	}
}
function showHideChildPArtDetailsDiv(){
	if(Number($('#partTypeCatgory').val()) == 2){
		$('#partTypeLabel').text('Child Part Details');
		$('#childPartDiv').show();
	}else if(Number($('#partTypeCatgory').val()) == 3){
		$('#partTypeLabel').text('Parent Part Details');
		$('#childPartDiv').show();
	}else{
		$('#childPartDiv').hide();
	}
}

function saveNewMasterPartsDetails(){
	$('#savePartRow').hide();
	 
	if(!validatePartDetails()){
		$('#savePartRow').show();
		return false;
	}
	
	showLayer();
	var jsonObject			= new Object();
	
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["partNumber"]		= $('#partNumber').val();
	jsonObject["partName"]			= $('#partName').val();
	jsonObject["partNameLocal"]		= $('#partNameLocal').val();
	jsonObject["partNameOnBill"]	= $('#partNameOnBill').val();
	jsonObject["partType"]			= $('#partType').val();
	jsonObject["unitTypeId"]		= $('#unitTypeId').val();
	jsonObject["manufacturer"]		= $('#manufacturer').val();
	jsonObject["originalBrand"]		= $('#originalBrand').val();
	jsonObject["partCategory"]		= $('#partCategory').val();
	jsonObject["partSubCategory"]	= $('#partSubCategory').val();
	jsonObject["vehicleMake"]		= $('#vehicleMake').val();
	jsonObject["vehicleModel"]		= $('#vehicleModel').val();
	jsonObject["warranty"]			= $('#warranty').val();
	jsonObject["warrantyInMonth"]	= $('#warrantyInMonth').val();
	jsonObject["couponAvailable"]	= $('#couponAvailable').val();
	jsonObject["couponDetails"]		= $('#couponDetails').val();
	jsonObject["scrapAvailable"]	= $('#scrapAvailable').val();
	jsonObject["mainPacking"]		= $('#mainPacking').val();
	jsonObject["uomPacking"]		= $('#uomPacking').val();
	jsonObject["looseItem"]			= $('#looseItem').val();
	jsonObject["uomLoose"]			= $('#uomLoose').val();
	jsonObject["barCodeNumber"]		= $('#barCodeNumber').val();
	jsonObject["itemType"]			= $('#itemType').val();
	jsonObject["repairable"]		= $('#repairable').val();
	jsonObject["repairingVendor"]	= $('#repairingVendor').val();
	jsonObject["childPart"]			= $('#childPart').val();
	jsonObject["childPartDetails"]	= $('#childPartDetails').val();
	jsonObject["subtituteParts"]	= $('#subtituteParts').val();
	jsonObject["Dimension"]			= $('#Dimension').val();
	jsonObject["purchaseVendor"]	= $('#purchaseVendor').val();
	jsonObject["description"]		= $('#description').val();
	jsonObject["refreshment"]		= $('#refreshment').val();
	jsonObject["partManufacturerType"] = $('#partManufacturerType').val();
	jsonObject["partTypeCatgory"]		= $('#partTypeCatgory').val();
	jsonObject["assetIdRequired"]		= $('#assetIdRequired').val();
	
	var aisleArr 		= new Array();
	var rowArr 			= new Array();
	var binArr 			= new Array();
	var locationIdArr 	= new Array();
	
	 $('.recDiv').each(function() {
		 var divId = this.id;
		 var locationId = divId.split('_')[1]
		 if($('#aisle_'+locationId+'').val().trim() != '' || $('#row_'+locationId+'').val().trim() != '' 
			 || $('#bin_'+locationId+'').val().trim() != ''){
			 aisleArr.push($('#aisle_'+locationId+'').val().trim());
			 rowArr.push($('#row_'+locationId+'').val().trim());
			 binArr.push($('#bin_'+locationId+'').val().trim());
			 locationIdArr.push(locationId);
		 }
	 });
	 
	 var array	 = new Array();
		for(var i =0 ; i< locationIdArr.length; i++){
			
			var locationDetails	= new Object();
			
			locationDetails.aisle			= aisleArr[i];
			locationDetails.row				= rowArr[i];
			locationDetails.bin				= binArr[i];
			locationDetails.locationId		= locationIdArr[i];
			
			array.push(locationDetails);
		}
		jsonObject.locationDetails = JSON.stringify(array);
		
	
	$.ajax({
		url: "saveNewMasterPartsDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saved != undefined && data.saved){
				showMessage('success', 'Data saved successfully !');
				location.replace("newMasterParts/1.in");
			}else if(data.already != undefined && data.already){
				$('#savePartRow').show();
				showMessage('errors', 'Data saved failed, Part Number already exists !');
				hideLayer();
			}
		},
		error: function (e) {
			hideLayer();
			$('#savePartRow').show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function validatePartDetails(){
	
	if($('#partNumber').val().trim() == ''){
		$('#partNumber').focus();
		showMessage('errors', 'Please enter part number !');
		return false
	}
	if($('#partName').val().trim() == ''){
		$('#partName').focus();
		showMessage('errors', 'Please enter part name !');
		return false
	}
	
	if($('#validateLocalParts').val() == 'true'){
		if($('#partNameLocal').val().trim() == ''){
			$('#partNameLocal').focus();
			showMessage('errors', 'Please enter part local name !');
			return false
		}
	}
	
	
	if($('#showPartType').val() == 'true'){
		if(Number($('#partType').val()) <= 0){
			$('#partType').focus();
			showMessage('errors', 'Please select part type !');
			return false
		}
	}
	
	
	if(Number($('#manufacturer').val()) <= 0){
		$('#manufacturer').focus();
		showMessage('errors', 'Please select part manufacturer !');
		return false
	}
	if(Number($('#partCategory').val()) <= 0){
		$('#partCategory').focus();
		showMessage('errors', 'Please select part category !');
		return false
	}
	
	if(Number($('#unitTypeId').val()) <= 0){
		$('#unitTypeId').focus();
		showMessage('errors', 'Please select Measurement unit / UOM !');
		return false
	}
	
	if($('#validateWarranty').val() == 'true'){
		if(Number($('#warranty').val()) <= 0){
			$('#warranty').focus();
			showMessage('errors', 'Please select warranty applicable or not !');
			return false
		}
	}
	
	if($('#warranty').val() == true || $('#warranty').val() == 'true'){
		if(Number($('#warrantyInMonth').val()) <= 0){
			$('#warrantyInMonth').focus();
			showMessage('errors', 'Please enter warranty tenure !');
			return false
		}
	}
	
	
	if($('#validatePurchaseVendor').val() == 'true'){
		if($('#purchaseVendor').val().trim() == ''){
			$('#purchaseVendor').select2('focus');
			showMessage('errors', 'Please select purchase vendors !');
			return false
		}
	}
	
	if($('#validateRepairable').val() == 'true'){
		if(Number($('#repairable').val()) <= 0){
			$('#repairable').focus();
			showMessage('errors', 'Please select repairable part or not !');
			return false
		}
	}
	
	if($('#repairable').val() == true || $('#repairable').val() == 'true'){
		if($('#repairingVendor').val().trim() == ''){
			$('#repairingVendor').select2('focus');
			showMessage('errors', 'Please Repairing Vendor !');
			return false
		}
	}
	
	
	return true;
}