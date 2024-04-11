$(document).ready(function() {
	//$("#location").select2();
	showLayer();
    $("#DriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+ e.driver_Lastname+" - "+ e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#Driver2Fuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+ e.driver_Lastname+" - "+ e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#CleanerFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverCleanerList.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#FuelRouteList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
            	console.log("e",e)
                return {
                    term: e
                }
            },
            results: function(e) {
            console.log("e",e)
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    }), $("#ureaLocation").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getLocationUreaStockSearchList.in",
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
                        text: a.locationName +" "+ a.subLocation + " - UI-" + a.ureaInvoiceNumber + " - " + a.manufacturerName + " - " + a.stockQuantity + "- Rate : "+ a.unitprice+ " dis : "+ a.discount+ " GST : "+ a.tax,
                        id: a.ureaInvoiceToDetailsId,
                        rate : a.unitprice,
                        discount : a.discount,
                        tax : a.tax,
                        wareHouseLocation : a.wareHouseLocation,
                        maxQuantity : a.stockQuantity,
                        manufacturerId : a.manufacturerId
                        
                    }
                })
            }
        }
    }
}),$("#ureaLocation").change(function(){
	var data = $("#ureaLocation").select2('data');
	
	if(data != undefined && data != null){
		
		$('#ureaPrice').val(data.rate);
		$('#discount').val(data.discount);
		$('#gst').val(data.tax);
		$('#manufacturerId').val(data.manufacturerId);
		$('#maxQuantity').val(data.maxQuantity);
		$('#wareHouseLocation').val(data.wareHouseLocation);
		$('#newUreaInvoiceToDetailsId').val(data.id);
		sumthere('ureaLiters','ureaPrice','discount', 'gst','ureaAmount');
	}else{
		
		$('#ureaPrice').val(0);
		$('#discount').val(0);
		$('#gst').val(0);
		$('#manufacturerId').val(0);
		$('#maxQuantity').val(0);
		$('#wareHouseLocation').val(0);
		sumthere('ureaLiters','ureaPrice','discount', 'gst','ureaAmount');
	}
	
}) ,$("#subLocationId").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getAllSubLocations.in?Action=FuncionarisoSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
            return {
                term: a,
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
})
})

$(document).ready(function() {
	var ureaEntriesInvoiceId = $('#ureaEntriesInvoiceId').val();
	getUreaEntriesInvoiceDetails(ureaEntriesInvoiceId);
	
});

function getUreaEntriesInvoiceDetails(ureaEntriesInvoiceId) {
	
	var jsonObject	= new Object();
	jsonObject["ureaEntriesInvoiceId"]	= ureaEntriesInvoiceId;

	
	$.ajax({
		url: "getUreaEntriesInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			
			$('#ureaInvoiceToDetailsId').val(data.UreaEntriesInvoice.ureaInvoiceToDetailsId);
			$('#ureaEntriesNumber').val(data.UreaEntriesInvoice.ureaEntriesNumber);
			$('#vid').val(data.UreaEntriesInvoice.vid);
			$('#FuelSelectVehicle').val(data.UreaEntriesInvoice.vehicle_registration);
			$('#vehicle_group').val(data.UreaEntriesInvoice.vehicleGrpName);
			$('#fuel_TripsheetNumber').val(data.UreaEntriesInvoice.tripSheetNumber);
			$('#fuelDate').val(data.UreaEntriesInvoice.ureaDateStr);
			$('#fuel_meter').val(data.UreaEntriesInvoice.ureaOdometer); 
			$('#ureaOdometerOld').val(data.UreaEntriesInvoice.ureaOdometerOld);
			$('#vehicle_ExpectedOdameter').val(data.UreaEntriesInvoice.vehicle_ExpectedOdameter);
			$('#fuel_meter_old').val(data.UreaEntriesInvoice.ureaOdometerOld);
			$('#minOdometer').val(data.UreaEntriesInvoice.ureaOdometerOld);
			$('#maxOdometer').val(data.UreaEntriesInvoice.ureaOdometerOld+data.UreaEntriesInvoice.vehicle_ExpectedOdameter);
			
			$('#tempFuel_meter').val(data.UreaEntriesInvoice.ureaOdometer); 
			$('#tempUreaOdometerOld').val(data.UreaEntriesInvoice.ureaOdometerOld);
			
			var LOCID = data.UreaEntriesInvoice.locationId;
			var loc = data.UreaEntriesInvoice.locationName;
			var invNum = data.UreaEntriesInvoice.ureaInvoiceNumber;
			var manuName = data.UreaEntriesInvoice.manufacturerName;
			var stckQty = data.UreaEntriesInvoice.quantity;
			var price = data.UreaEntriesInvoice.ureaRate;
			var disc = data.UreaEntriesInvoice.discount;
			var tax = data.UreaEntriesInvoice.gst;
			var LOCText = loc + " - UI-" + invNum + " - " + manuName + " - " + stckQty + "- Rate : "+ price + " dis : "+ disc + " GST : "+ tax 
			
			$('#ureaLocation').select2('data', {
			id : LOCID,
			text : LOCText
			});
			
			
			$('#maxQuantity').val(data.UreaEntriesInvoice.quantity); 
			$('#ureaPrice').val(data.UreaEntriesInvoice.ureaRate); 
			$('#discount').val(data.UreaEntriesInvoice.discount); 
			$('#gst').val(data.UreaEntriesInvoice.gst); 
			$('#ureaLiters').val(data.UreaEntriesInvoice.ureaLiters); 
			$('#ureaAmount').val(data.UreaEntriesInvoice.ureaAmount); 
			$('#fuel_reference').val(data.UreaEntriesInvoice.reference); 
			
			var D1ID = data.UreaEntriesInvoice.driver_id;
			var D1Text = data.UreaEntriesInvoice.firsDriverName +" "+ data.UreaEntriesInvoice.firsDriverLastName;
			$('#DriverFuel').select2('data', {
			id : D1ID,
			text : D1Text
			});
			
			var D2ID = data.UreaEntriesInvoice.secDriverID;
			var D2Text = data.UreaEntriesInvoice.secDriverName+" "+data.UreaEntriesInvoice.secDriverLastName;
			$('#Driver2Fuel').select2('data', {
				id : D2ID,
				text : D2Text
			});
			
			var CID = data.UreaEntriesInvoice.cleanerID;
			var CText = data.UreaEntriesInvoice.cleanerName;
			$('#CleanerFuel').select2('data', {
				id : CID,
				text : CText
			});
			
			var RID = data.UreaEntriesInvoice.routeID;
			var RText = data.UreaEntriesInvoice.routeName;
			$('#FuelRouteList').select2('data', {
				id : RID,
				text : RText
			});
			
			
			
			if($('#showFilledLocation').val() == true || $('#showFilledLocation').val() == 'true' ){
				var SID = data.UreaEntriesInvoice.filledLocationId;
				var SText = data.UreaEntriesInvoice.locationName;
				$('#subLocationId').select2('data',{
					id :SID ,
					text : SText
				})
				$('#filledBy').val(data.UreaEntriesInvoice.filledBy);
			}
			
			
			$('#fuel_comments').val(data.UreaEntriesInvoice.comments);
			$('#wareHouseLocation').val(data.UreaEntriesInvoice.locationId);
			$('#manufacturerId').val(data.UreaEntriesInvoice.ureaManufacturerId);
			$('#manufacturerId').val(data.UreaEntriesInvoice.ureaManufacturerId);
			if(data.UreaEntriesInvoice.meterWorkingStatus == true || data.UreaEntriesInvoice.meterWorkingStatus == 'true'){
				$('#meter_attributes').attr('checked','checked')
			}
			
			getPreNextUreaEntiresByDate();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	
}


function updateUreaEntry(){
	
	if(!validateUreaEntries()){
		return false;
	}
	
	if($('#newUreaInvoiceToDetailsId').val() == ""){
		
		$('#newUreaInvoiceToDetailsId').val($('#ureaInvoiceToDetailsId').val());
	}
	
	var jsonObject			= new Object();
	
	jsonObject["ureaEntriesInvoiceId"] 				=  $('#ureaEntriesInvoiceId').val();
	jsonObject["ureaInvoiceToDetailsId"] 			=  $('#ureaInvoiceToDetailsId').val();
	jsonObject["ureaEntriesNumber"] 				=  $('#ureaEntriesNumber').val();
	jsonObject["vid"] 	  							=  $('#vid').val();
	jsonObject["tripsheetNumber"] 	  				=  $('#fuel_TripsheetNumber').val();
	jsonObject["ureaDate"] 	  						=  $('#fuelDate').val();
	jsonObject["ureaOdometer"] 	  					=  $('#fuel_meter').val();
	jsonObject["ureaOdometerOld"] 	  				=  $('#fuel_meter_old').val();
	jsonObject["ureaLocation"] 	  					=  $('#ureaLocation').val();
	jsonObject["ureaPrice"] 	  					=  $('#ureaPrice').val();
	jsonObject["discount"] 	  						=  $('#discount').val();
	jsonObject["gst"] 	  							=  $('#gst').val();
	jsonObject["ureaAmount"] 	  					=  $('#ureaAmount').val();
	jsonObject["ureaLiters"] 	  					=  $('#ureaLiters').val();
	jsonObject["reference"] 	  					=  $('#fuel_reference').val();
	jsonObject["DriverFuel"] 	  					=  $('#DriverFuel').val();
	jsonObject["Driver2Fuel"] 	  					=  $('#Driver2Fuel').val();
	jsonObject["CleanerFuel"] 	  					=  $('#CleanerFuel').val();
	jsonObject["routeId"] 	  						=  $('#FuelRouteList').val();
	jsonObject["ureaComments"] 	  					=  $('#fuel_comments').val();
	jsonObject["wareHouseLocation"] 				=  $('#wareHouseLocation').val();
	jsonObject["manufacturerId"] 					=  $('#manufacturerId').val();
	jsonObject["newUreaInvoiceToDetailsId"] 		=  $('#newUreaInvoiceToDetailsId').val();
	jsonObject["isNextUreaEntry"] 		=  $('#isNextUreaEntry').val();
	jsonObject["nextUreaEntryId"] 		=  $('#nextUreaEntryId').val();
	jsonObject["companyId"] 			=  $('#companyId').val();
	if($('#meter_attributes').prop('checked')){
		jsonObject["meterWorkingStatus"] 			= true ;
	}else{
		jsonObject["meterWorkingStatus"] 			= false ;
	}
	
	
	showLayer();
	$.ajax({
             url: "updateUreaEntriesDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.exceedLimit != undefined && (data.exceedLimit == true || data.exceedLimit == 'true')){
            		 showMessage('info', 'Urea Liter Can Not Be Greater Than Stock Liter');
            	 }else{
            		 showMessage('success', 'Data Saved Successfully!')
            		 window.location.replace("UreaEntriesShowList.in?saved=true");
            	 }
            	 
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function sumthere(a, b, c, d, e) {
    var f = document.getElementById(a).value,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        i = document.getElementById(d).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
  
}

function validateUreaEntries(){
	
	
	if(Number($('#FuelSelectVehicle').val()) <= 0){
		showMessage('info', 'Please Select Vehicle !');
		$("#FuelSelectVehicle").select2('focus');
		return false;
	}
	
	if($('#fuelDate').val() == null || $('#fuelDate').val().trim() == '' ){
		showMessage('info', 'Please Select Date !');
		$("#fuelDate").focus();
		return false;
	}
	
	if(Number($('#fuel_meter').val()) <= 0){
		$("#fuel_meter").focus();
		showMessage('info', 'Please Enter Odometer !');
		return false;
	}
	
	if(!validateMaxOdameter()){
		return false;
	}
	
	if(Number($('#ureaLocation').val()) <= 0){
		showMessage('info', 'Please Select Urea Location Stock Details !');
		$("#ureaLocation").select2('focus');
		return false;
	}
	
	if(Number($('#ureaLiters').val()) <= 0){
		$("#ureaLiters").focus();
		showMessage('info', 'Please Enter Urea Liters !');
		return false;
	}
	
	validateUreaLiters();
	
	return true;
}

function isNumberKey(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function isDecimalNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

/*function validateMaxOdameter(){
	
	var	ureaOdometerOld	=  Number($('#ureaOdometerOld').val());
	var ureaOdometer	=  Number($('#fuel_meter').val());
	var current  	= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var fuelDate 	= $('#fuelDate').val().split("-").reverse().join("-");
	var date = moment(fuelDate);
	var now = moment(current);
	var expectedMaxOdameter =  ureaOdometerOld + Number($('#vehicle_ExpectedOdameter').val()); Number($('#vehicle_Odometer').val())
	
	if($('#fuelDate').val() != undefined && $('#fuelDate').val() != '' && $('#fuelDate').val() != null){
		if (!date.isBefore(now)) {
			var expectedOdameter = Number($('#vehicle_ExpectedOdameter').val());
			if(ureaOdometerOld != undefined &&  ureaOdometerOld != null && ureaOdometerOld > 0){
				if(ureaOdometer != undefined && ureaOdometer != null){
					if(ureaOdometer > expectedMaxOdameter){
						showMessage('errors', 'You can not enter Odameter greter than '+expectedMaxOdameter);
						$('#fuel_meter').focus();
						return false;
					}
				}
			}
		} 
	}
	return true;
}

function validateUreaLiters(){
	var ureaLiters	 = Number($('#ureaLiters').val());
	var maxQuantity  = Number($('#maxQuantity').val());
	if(maxQuantity < ureaLiters){
		showMessage('errors', 'You can not enter Urea  greter than '+maxQuantity+' Liters !');
		$('#ureaLiters').val(0);
		$('#ureaAmount').val(0);
		$('#ureaLiters').focus();
		return false;
	}
}*/