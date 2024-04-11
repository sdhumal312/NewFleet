$(document).ready(function() {
	getMasterPartsDetailsForEdit();
	
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

function getMasterPartsDetailsForEdit(){
	var jsonObject			= new Object();
	
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["partId"]			= $('#partId').val();
	
	$.ajax({
		url: "getMasterPartsDetailsForEdit",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setMasterPartsDetails(data);
		},
		error: function (e) {
			hideLayer();
			$('#savePartRow').show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setMasterPartsDetails(data){

	if(data.masterParts != undefined){
		$('#partNumber').val(data.masterParts.partnumber);
		$('#partName').val(data.masterParts.partname);
		$('#partNameLocal').val(data.masterParts.localPartName);
		$('#partNameOnBill').val(data.masterParts.partNameOnBill);
		$('#partType').val(data.masterParts.partTypeId);
		$('#unitTypeId').val(data.masterParts.unitTypeId);
		$('#manufacturer').val(data.masterParts.makerId);
		$('#partCategory').val(data.masterParts.categoryId);
		$('#partSubCategory').val(data.masterParts.partSubCategoryName);
		$('#warranty').val(''+data.masterParts.warrantyApplicable);
		$('#warrantyInMonth').val(data.masterParts.warrantyInMonths);
		$('#couponAvailable').val(''+data.masterParts.couponAvailable);
		$('#couponDetails').val(data.masterParts.couponDetails);
		$('#scrapAvailable').val(''+data.masterParts.scrapAvilable);
		$('#description').val(data.masterParts.description);
		$('#refreshment').val(''+data.masterParts.refreshment);
		$('#partManufacturerType').val(data.masterParts.partManufacturerType);
		$('#repairable').val(''+data.masterParts.repairable);
		$('#childPart').val(''+data.masterParts.childPart);
		$('#partTypeCatgory').val(data.masterParts.partTypeCategoryId);
		$('#assetIdRequired').val(''+data.masterParts.assetIdRequired);
		
		if(data.extraDetails != undefined){
			$('#mainPacking').val(data.extraDetails.mainPacking);
			$('#originalBrand').val(data.extraDetails.originalBrandId);
			$('#uomPacking').val(data.extraDetails.uomPacking);
			$('#looseItem').val(data.extraDetails.looseItem);
			$('#uomLoose').val(data.extraDetails.looseUom);
			$('#barCodeNumber').val(data.extraDetails.barCodeNumber);
			$('#itemType').val(data.extraDetails.itemTypeId);
			$('#partsExtraDetailsId').val(data.extraDetails.partsExtraDetailsId);
			$('#Dimension').val(data.extraDetails.dimention);
		}
		
		if(data.vehicleMake != undefined){
			var array	 = new Array();
			
			for(var i = 0; i< data.vehicleMake.length ; i++){
				var locationDetails	= new Object();
			   locationDetails.id = data.vehicleMake[i].vehicleManufacturerId;
			   locationDetails.text = data.vehicleMake[i].vehicleManufacturer;
			   array.push(locationDetails);
			}
			$('#vehicleMake').data().select2.updateSelection(array);
		}
		
		if(data.vehicleModal != undefined){
			var array	 = new Array();
			
			for(var i = 0; i< data.vehicleModal.length ; i++){
				var locationDetails	= new Object();
			   locationDetails.id = data.vehicleModal[i].vehicleModelId;
			   locationDetails.text = data.vehicleModal[i].vehicleModel;
			   array.push(locationDetails);
			}
			$('#vehicleModel').data().select2.updateSelection(array);
		}
		
		
		if(data.repairableVendors != undefined){
			var array	 = new Array();
			
			for(var i = 0; i< data.repairableVendors.length ; i++){
				var locationDetails	= new Object();
			   locationDetails.id = data.repairableVendors[i].vendorId;
			   locationDetails.text = data.repairableVendors[i].vendorName;
			   array.push(locationDetails);
			}
			$('#repairingVendor').data().select2.updateSelection(array);
		}
		if(data.purchaseVendors != undefined){
			var array	 = new Array();
			
			for(var i = 0; i< data.purchaseVendors.length ; i++){
			var locationDetails	= new Object();
			   locationDetails.id = data.purchaseVendors[i].vendorId;
			   locationDetails.text = data.purchaseVendors[i].vendorName;
			   array.push(locationDetails);
			}
			$('#purchaseVendor').data().select2.updateSelection(array);
		}
		
		
		if(data.childParts != undefined){
		
			$('#childPart').val('true');
			var array	 = new Array();
			
			for(var i = 0; i< data.childParts.length ; i++){
			var locationDetails	= new Object();
			   if(data.masterParts.partTypeCategoryId == 2){
				   locationDetails.id = data.childParts[i].partId;
			   }else{
			   	locationDetails.id = data.childParts[i].mainPartId;
			   }
			   locationDetails.text = data.childParts[i].childPartName;
			   array.push(locationDetails);
			}
			$('#childPartDetails').data().select2.updateSelection(array);
		}
		
		if(data.substituDeParts != undefined){
			var array	 = new Array();
			
			for(var i = 0; i< data.substituDeParts.length ; i++){
				var locationDetails	= new Object();
			   locationDetails.id = data.substituDeParts[i].partId;
			   locationDetails.text = data.substituDeParts[i].childPartName;
			   array.push(locationDetails);
			}
			$('#subtituteParts').data().select2.updateSelection(array);
		}
		
		if(data.partsLocation != undefined){
			
			for(var i = 0; i< data.partsLocation.length ; i++){
			   $('#aisle_'+data.partsLocation[i].locationId+'').val(data.partsLocation[i].aisle);
			   $('#row_'+data.partsLocation[i].locationId+'').val(data.partsLocation[i].row);
			   $('#bin_'+data.partsLocation[i].locationId+'').val(data.partsLocation[i].bin);
			}
		}
		
		showHideWarrantyMOnthRow();
		showCouponDetailsRow();
		showHideRepairingVendor();
		showHideChildPArtDetailsDiv();
	}
}

function updateNewMasterPartsDetails(){
	$('#savePartRow').hide();
	 
	if(!validatePartDetails()){
		$('#savePartRow').show();
		return false;
	}
	
	showLayer();
	var jsonObject			= new Object();
	
	jsonObject["userId"]				= $('#userId').val();
	jsonObject["partId"]				= $('#partId').val();
	jsonObject["partsExtraDetailsId"]	= $('#partsExtraDetailsId').val();
	jsonObject["companyId"]				= $('#companyId').val();
	jsonObject["partNumber"]			= $('#partNumber').val();
	jsonObject["partName"]				= $('#partName').val();
	jsonObject["partNameLocal"]			= $('#partNameLocal').val();
	jsonObject["partNameOnBill"]		= $('#partNameOnBill').val();
	jsonObject["partType"]				= $('#partType').val();
	jsonObject["unitTypeId"]			= $('#unitTypeId').val();
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
	jsonObject["partManufacturerType"]		= $('#partManufacturerType').val();
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
		url: "updateNewMasterPartsDetails",
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