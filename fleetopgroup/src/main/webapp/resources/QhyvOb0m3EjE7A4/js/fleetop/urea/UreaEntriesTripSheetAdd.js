$(document).ready(function() {
	//$("#location").select2();
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
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
                        text: a.locationName + " - UI-" + a.ureaInvoiceNumber + " - " + a.manufacturerName + " - " + a.stockQuantity + "- Rate : "+ a.unitprice+ " dis : "+ a.discount+ " GST : "+ a.tax,
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
	
}),$.getJSON("getLastFuelVehicleOdoMerete.in", {
    FuelvehicleID: $('#vehicleId').val(),
    ajax: "true"
}, function(e) {
	
	if(e.vStatusId != 1 && e.vStatusId != 5){
		showMessage('errors','You Can not create Urea Entry , Vehicle Status is : '+e.vehicle_Status);
		$("#FuelSelectVehicle1").select2("val", "");
		hideLayer();
		return false;
	}
	
    var t = "";
    t = e.vehicle_Odometer, document.getElementById("grpfuelOdometer").placeholder = t
   
    if(e.vehicle_Odometer != undefined && e.vehicle_Odometer > 0){	
    	 $('#fuel_meter').attr("placeholder", e.vehicle_Odometer);
    	$('#fuel_meter').val(e.vehicle_Odometer);
    }	
    
    if(e.vehicle_ExpectedOdameter != undefined && e.vehicle_ExpectedOdameter > 0){	
    	$('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
   }
    
    
    $('#vehicle_group').val(e.vehicle_Group);
    if(e.lastUreaOdometer != undefined && e.lastUreaOdometer != null){
    	$('#ureaOdometerOld').val(e.lastUreaOdometer);
    }else{
    	$('#ureaOdometerOld').val(e.vehicle_Odometer);
    }
    
    hideLayer();
})
})

$(document).ready(function() {
	var tripSheetId = $('#tripSheetId').val();
	getUreaEntriesTripSheetDetails(tripSheetId);
});

function getUreaEntriesTripSheetDetails(tripSheetId) {
	
	var jsonObject	= new Object();
	jsonObject["tripSheetId"]	= tripSheetId;
	
	console.log("jsonObject..",jsonObject);
	
	$.ajax({
		url: "getUreaEntriesTripSheetDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			console.log("data..",data);
			
			$('#FuelSelectVehicle1').val(data.TripSheet.vehicle_registration);
			$('#vehicle_group').val(data.TripSheet.vehicle_Group);
			$('#fuel_TripsheetNumber').val(data.TripSheet.tripSheetNumber);
			
			var D1ID = data.TripSheet.tripFristDriverID;
			var D1Text = data.TripSheet.tripFristDriverName+" "+data.TripSheet.tripFristDriverLastName +" - "+data.TripSheet.tripFristDriverFatherName ;
			$('#DriverFuel').select2('data', {
			id : D1ID,
			text : D1Text
			});
			
			var RID = data.TripSheet.routeID;
			var RText = data.TripSheet.routeName;
			$('#FuelRouteList').select2('data', {
				id : RID,
				text : RText
			});
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function saveTripSheetUreaEntry(){
	
	if(!validateUreaEntries()){
		return false;
	}
	
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	  				=  $('#vehicleId').val();
	jsonObject["tripsheetNumber"] 	  	=  $('#fuel_TripsheetNumber').val();
	jsonObject["ureaDate"] 	  			=  $('#fuelDate').val();
	jsonObject["ureaOdometer"] 	  		=  $('#fuel_meter').val();
	jsonObject["fuel_meter_old"] 	  	=  $('#ureaOdometerOld').val();
	jsonObject["ureaLocation"] 	  		=  $('#ureaLocation').val();
	jsonObject["ureaPrice"] 	  		=  $('#ureaPrice').val();
	jsonObject["discount"] 	  			=  $('#discount').val();
	jsonObject["gst"] 	  				=  $('#gst').val();
	jsonObject["ureaAmount"] 	  		=  $('#ureaAmount').val();
	jsonObject["ureaLiters"] 	  		=  $('#ureaLiters').val();
	jsonObject["reference"] 	  		=  $('#fuel_reference').val();
	jsonObject["DriverFuel"] 	  		=  $('#DriverFuel').val();
	jsonObject["Driver2Fuel"] 	  		=  $('#Driver2Fuel').val();
	jsonObject["CleanerFuel"] 	  		=  $('#CleanerFuel').val();
	jsonObject["routeId"] 	  			=  $('#FuelRouteList').val();
	jsonObject["ureaComments"] 	  		=  $('#fuel_comments').val();
	jsonObject["wareHouseLocation"] 	=  $('#wareHouseLocation').val();
	jsonObject["manufacturerId"] 		=  $('#manufacturerId').val();
	jsonObject["isNextUreaEntry"] 		=  $('#isNextUreaEntry').val();
	jsonObject["nextUreaEntryId"] 		=  $('#nextUreaEntryId').val();
	jsonObject["companyId"] 			=  $('#companyId').val();
	jsonObject["fuel_meter_old"] 		=  $('#fuel_meter_old').val();
	
	
	
	showLayer();
	$.ajax({
             url: "saveUreaEntriesDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 window.location.replace("addTripSheetUreaEntries.in?tripSheetID="+$('#tripSheetId').val()+"");
            	 showMessage('success', 'Data Saved Successfully!')
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
	
	if(Number($('#FuelSelectVehicle1').val()) <= 0){
		showMessage('info', 'Please Select Vehicle !');
		$("#FuelSelectVehicle1").select2('focus');
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


function isNumberKeyWithDecimal(evt,id){ 
	try{

        var charCode = (evt.which) ? evt.which : event.keyCode;
        var txt=document.getElementById(id).value;
  
        if(charCode==46){
         
            if(!(txt.indexOf(".") > -1)){
	
                return true;
            }
        }
        if (charCode > 31 && (charCode < 48 || charCode > 57) )
            return false;

        if(txt.indexOf(".") > -1 && (txt.split('.')[1].length > 1)){
            event.preventDefault();
        }
        return true;
	}catch(w){
		alert(w);
	}
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

function deleteTripSheetUreaEntries(ureaEntriesId){
	
	  if(confirm("Are You Sure to Delete ?")){
		  	
			var jsonObject			= new Object();
			jsonObject["ureaEntriesId"] =  ureaEntriesId;
			
			showLayer();
			$.ajax({
		             url: "deleteUreaEntryById",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("addTripSheetUreaEntries.in?tripSheetID="+$('#tripSheetId').val()+"");
		            	 showMessage('success', 'Urea Entry Deleted Successfully!')
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
	  }
}