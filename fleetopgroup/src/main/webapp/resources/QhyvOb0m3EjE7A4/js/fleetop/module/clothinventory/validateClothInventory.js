var mainPartLocationType = 1;
var subPartLocationType  = 2;

function showSubLocationDropDown(){
	
	var showSubLocationForMainLocation = false;
    if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
    	var mainLocationIds = $("#mainLocationIds").val().split(',');
    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#warehouselocation").val() == mainLocationIds[i]){
    			showSubLocationForMainLocation = true
    		}
    	}
    }
    
    if(showSubLocationForMainLocation == true){
    	$("#subLocation").show();
    	$("#validateSubLocation").val(true);
    }else{
    	$("#subLocationId").select2("val", "");
    	$("#subLocation").hide();
    	$("#validateSubLocation").val(false);
    }
}

$(document).ready(function() {
	 $("#subLocationId").select2({
	        minimumInputLength: 0,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getPartLocationsByMainLocationId.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a,
	                    locationType: subPartLocationType,
	                    mainLocationId:  $('#warehouselocation').val()
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
	    if($("#editSubLocationId").val() != undefined || $("#editSubLocationId").val() != null){// will show only on edit
			$('#subLocationId').select2('data', {
				id : $("#editSubLocationId").val(),
				text : $("#editSubLocation").val()
			});
		}
});



function validateCloth(){
	var validateSubLocation = false;
	if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
		var mainLocationIds = $("#mainLocationIds").val().split(',');
    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#warehouselocation").val() == mainLocationIds[i]){
    			validateSubLocation = true
    		}
    	}
    	
    	if((validateSubLocation == true ) && ($('#subLocationId').val() == undefined || $('#subLocationId').val() == "" )){
    		showMessage('info','Please Select Sub Location');
    		return false;
    	}
		
	 }
	
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
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val() <= 0)){
			$("#tallyCompanyId").select2('focus');
			showMessage('errors', 'Please Select Tally Company!');
			return false;
		}
	}
	if($("#roundupConfig").val() == true || $("#roundupConfig").val()=='true'){
		if($('#roundupTotal').val() == ""||$('#roundupTotal').val()== null || $('#roundupTotal').val()<=0 ){
			showMessage('info', 'Please enter valid roundup amount !');
			hideLayer();
			return false;
		}
	}
	
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		return false;
	}
	return true;
	
}