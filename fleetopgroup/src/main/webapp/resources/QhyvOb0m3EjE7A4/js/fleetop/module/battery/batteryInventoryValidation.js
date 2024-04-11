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



function validateBatteryUpdate(){
	
	if(Number($('#warehouselocation').val() <= 0)){
		 $("#warehouselocation").select2('focus');
		showMessage('info', 'Please Select WareHouse Location!');
		return false;
	}
	
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
	
	if(Number($('#selectVendor').val() <= 0)){
		 $("#selectVendor").select2('focus');
		showMessage('info', 'Please Select Vendor!');
		return false;
	}
	
	if(($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true) && Number($('#tallyCompanyId').val() <= 0)){
			$("#tallyCompanyId").select2('focus');
			showMessage('info', 'Please Select Tally Company!');
			return false;
	}
	if($("#renPT_option1").val() == 2 ){
	 	if($("#selectVendor").val() == "" || Number($("#selectVendor").val()) ==  0){
	 		showMessage('info','Please Select Vendor');
	 		return false;
	 	}
	}
	
	if($('#roundupConfig').val()==true||$('#roundupConfig').val()=='true'){
		if($('#roundupTotal').val()==""|| $('#roundupTotal').val()==null || $('#roundupTotal').val() <= 0){
			showMessage('info','Please Enter valid roundup Amount');
			hideLayer();
			return false;
		}
		
	}
	if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		return false;	
	}
	return true;
}

$("#unitprice").keyup(function() {
    if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
        if (isNaN(parseFloat(this.value))) return;
        this.value = parseFloat(this.value).toFixed(2)
    }
    return this
})
