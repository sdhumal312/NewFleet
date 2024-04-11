$(document).ready(function(){
	$("#selectInvoiceDiv").hide();
	$("#toPetrolPumpId").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "SearchOnlyPetrolPumpVendorName.in?Action=FuncionarioSelect2",
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
                            text: a.vendorName + " - " + a.vendorLocation,
                            slug: a.slug,
                            id: a.vendorId
                        }
                    })
                }
            }
        }
    }),	$("#fromPetrolPumpId").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "SearchOnlyPetrolPumpVendorName.in?Action=FuncionarioSelect2",
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
                            text: a.vendorName + " - " + a.vendorLocation,
                            slug: a.slug,
                            id: a.vendorId
                        }
                    })
                }
            }
        }
    })
	
	
	
	
})

$(document).ready(
		function($) {
			$('button[id=transferFuel]').click(function(e) {
				
				if(!validate()){
					return;
				}
				if(!checkPetrolPump()){
					return;
				}
				if(!checkQuantity()){
					return;
				}
				
				var confirmed = confirm("Do you want to transfer fuel ?");
				if(!confirmed){
					return;
				}
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["toPetrolPumpId"]		= $('#toPetrolPumpId').val();
				jsonObject["fromPetrolPumpId"]		= $('#fromPetrolPumpId').val();
				jsonObject["invoiceList"]			= $('#invoiceList').val();
				jsonObject["quantity"]				= $('#quantity').val();
				jsonObject["remark"]				= $('#remark').val();
				
				$.ajax({
					url: "saveTransferFuel",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						hideLayer();
						if(data.balanceStockValidationFail == true || data.balanceStockValidationFail === 'true'){
							showMessage('info',' Invalid transfer Quantity ,try again ...');
							setTimeout(function() {
								location.reload();
							}, 3000);
						}else if(data.noInvoiceFound == true || data.noInvoiceFound === 'true'){
							showMessage('info',' Invalid invoice ,try again ...');
							setTimeout(function() {
								location.reload();
							}, 3000);
						}else if(data.saved == true || data.saved === 'true'){
						if(data.savedFuelInvoice.fuelInvoiceId != undefined){
							showMessage('success','Transfered Successfully');
						} setTimeout(function () {
							window.location.replace("/showFuelInvoice?Id="+data.savedFuelInvoice.fuelInvoiceId+"");
						}, 2000);
					}else{
						showMessage('errors', 'Transfer failed !');
					}

					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	


function getInvoice(){
	 checkPetrolPump();
	if($('#fromPetrolPumpId').val() != undefined && $('#fromPetrolPumpId').val() != ""){
	$("#selectInvoiceDiv").show();
		$("#invoiceList").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "SearchFuelInvoiceByPetrolPump.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: $('#fromPetrolPumpId').val()
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: "FI-"+a.fuelInvoiceNumber + " Balance Quantity : " + a.balanceStock,
                            slug: a.balanceStock,
                            id: a.fuelInvoiceId
                        }
                    })
                }
            }
        }
    })
	
	}else{
		$("#selectInvoiceDiv").hide();
		}
}
function checkPetrolPump(){
	if($('#toPetrolPumpId').val() === $('#fromPetrolPumpId').val()){
		showMessage('info','From And To Petrol Pumps Can not be the same .');
		hideLayer();
		return false;
	}else{
		return true;
	}
}

function checkQuantity(){
	var q =$("#invoiceList").select2('data');
	if($('#quantity').val() > (Number)(q.slug) ){
		showMessage('info','Transfer Quantity can not be greater than balance stock quantity .');
		hideLayer();
		return false;
	}else if ($('#quantity').val() <=0 ){
		showMessage('info','Transfer Quantity can not be less than 0 .');
		hideLayer();
		return false;
	}else{
		return true;
	}
}
function validate(){
	if($('#fromPetrolPumpId').val() ===  ""){
		showMessage('info','Please Select from petrol pump');
		hideLayer();
		return false;
	}else if ($('#toPetrolPumpId').val() === ""){
		showMessage('info','Please Select To petrol pump');
		hideLayer();
		return false;
	}else if($('#invoiceList').val() ===  ""){
		showMessage('info','Please Select invoice ');
		hideLayer();
		return false;
	}else{
		return true;
	}
	
}
function setQuantity(){
	var q =$("#invoiceList").select2('data');
	$('#quantity').val(q.slug);
}