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
function deleteBatteryInventory(){
	showLayer();
	var jsonObject			= new Object();
	var invoiceId 		 	= Number(getUrlParameter('Id'));
	jsonObject["batteryInvoiceId"] 	  =  invoiceId ;
	
	$.ajax({
             url: "deleteBatteryInventory",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 window.location.replace("BatteryInventory.in?delete="+data.deleted+"");
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}

function deleteClothLaundryInvoice(invoiceId){
	deleteClothLaundryInvoiceById($('#invoiceId').val());
}

function deleteClothLaundryInvoiceById(invoiceId){
	Swal.fire({
		  title: 'Are you sure to Delete?',
		  text: "You won't be able to undo changes !",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, Delete it !'
		}).then((result) => {
		  if (result.value) {
			  showLayer();
				
				var jsonObject					  = new Object();
				jsonObject["laundryInvoiceId"] 	  = invoiceId;
				
				$.ajax({
			             url: "deleteClothLaundryInvoice",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 if(data.inProcess != undefined && data.inProcess){
			            		 showMessage('errors', 'Cannot delete, This invoice is already in Process status !');
			            		 return false;
			            	 }
			            	 showMessage('success', 'Data Deleted Successfully !');
			            	 window.location.replace("ClothInventory.in?delete="+data.deleted+"");
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});

		  }
		})

}

function isNumberKey(e, t) {
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != e.charcode) return !1;
    var i = $(t).val().length,
        a = $(t).val().indexOf(".");
    return !(a > 0 && 46 == n) && !(a > 0 && i + 1 - a > 3)
}

function isNumberKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = 46 == t || t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = n ? "none" : "inline", n
}
function clothInvoiceSearch(){

	showLayer();
	var jsonObject						= new Object();
	jsonObject["clothInvoiceNumber"]	= $('#searchClothInv').val();

	$.ajax({
		url: "searchClothInvoiceByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.invoiceId != undefined && data.invoiceId > 0){
				window.location.replace("showClothInvoice?Id="+data.invoiceId+"");
			}else{
				showMessage('errors', 'No Record Found !');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}
function searchClothInvOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		clothInvoiceSearch();
	}
}
function invoiceSearch(pageNumber){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["term"]					= $('#searchInv').val();

	$.ajax({
		url: "searchClothInvoice",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryInvoiceList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function invoiceSearchOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		invoiceSearch(1);
	}
}

function deleteclothInvoice(invoiceId) {
	
	  if(confirm("Are You Sure to Delete ?")){
		  showLayer();
			var jsonObject			= new Object();
			jsonObject["invoiceId"] =  invoiceId ;
		$.ajax({
	             url: "deleteClothInventory",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.deleted){
	            		 window.location.replace("ClothInventory?deleted="+true+"");
	            	 }else{
	            		showMessage('info', 'Delete Cloth Details first !');
	            		hieLayer();
	            	 }
	            	 hieLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});

	  }
}
function deleteClothInvoice(){
	var invoiceId 		 	= Number($('#invoiceId').val());
	deleteclothInvoice(invoiceId);
	
}

function validateSentLaundry(){
	var noManufacturer	= false;
	$('input[name*=clothTypes]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noManufacturer	= true;
			showMessage('errors', 'Please Select Cloth Types!');
			return false;
		}
	});
	
	
	var noQuantity = false;
	$('input[name*=quantity_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noQuantity	= true;
			showMessage('errors', 'Please Enter Quantity!');
			return false;
		}
	});
	
	var noCost = false;
	$('input[name*=unitprice_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noCost	= true;
			showMessage('errors', 'Please Enter Unit Cost!');
			return false;
		}
	});
	
	var noDiscount = false;
	$('input[name*=discount_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
	});
	
	var noTax = false;
	$('input[name*=tax_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
	});
	
	if(Number($('#warehouselocation').val() <= 0)){
		 $("#warehouselocation").select2('focus');
		showMessage('errors', 'Please Select WareHouse Location!');
		return false;
	}
	if(Number($('#selectVendor').val() <= 0)){
		 $("#selectVendor").select2('focus');
		showMessage('errors', 'Please Select Vendor!');
		return false;
	}
	
	if(noManufacturer || noQuantity || noCost){
		return false;
	}
	
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
    	if(Number($('#tallyCompanyId').val()) <= 0){
    		 showMessage('errors', 'Please Select Tally Company!');
			 return false;
    	}
     }
     
     if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		return false;	
	}
	
	return true;
}