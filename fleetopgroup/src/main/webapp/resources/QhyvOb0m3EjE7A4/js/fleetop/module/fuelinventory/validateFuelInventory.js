$(document).ready(function() {
			$("#location").select2();
			$("#batterryTypeId").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
  }),$("#petrolPumpId").select2({
	        minimumInputLength: 2,
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
	    }),$("#tallyCompanyId").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
	                            text: a.companyName ,
	                            slug: a.slug,
	                            id: a.tallyCompanyId
	                        }
	                    })
	                }
	            }
	        }
	    }), $("#warehouselocation , #warehouselocation2").select2({
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
        }),$("#selectVendor").on("change", function() {
        	vendorOnchange();
        }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
            showoption()
        }), $("#renPT_option").change(),$("#selectVendor").select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getVendorSearchListFuel.in?Action=FuncionarioSelect2",
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
        });
			var saved 			= getUrlParameter('saved');
			var deleted			= getUrlParameter('deleted');
			var noRecordFound 	= getUrlParameter('noRecordFound');
		if(saved == true || saved == 'true'){
			showMessage('success', 'Urea Inventory Saved Successfully !');
		}
		if(deleted == true || deleted == 'true'){
			showMessage('success', 'Data Deleted Successfully !');
		}
		if(noRecordFound == true || noRecordFound == 'true'){
			showMessage('info', 'No Record Found !');
		}
		
		if($('#discountTaxTypId').val() == 1){
			$('#percentId').addClass('active');
			$('#finalDiscountTaxTypId').val(1);
		} else {
			$('#amountId').addClass('active');
			$('#finalDiscountTaxTypId').val(2);
		}
		
});
function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "Cash" == b ? $("#target1").text(b + " Receipt NO") : "Cheque" == b ? $("#target1").text(b + " NO") : "CREDIT" == b ? $("#target1").text(b + " Transaction NO") : $("#target1").text(b + " Transaction NO")
}
function validateFuelInventory(){
	
	if(Number($('#petrolPumpId').val()) <= 0){
		showMessage('info', 'Please select Petrol Pump');
		return false;
	}
	if(Number($('#quantity').val()) <= 0){
		showMessage('info', 'Please Enter Fuel Liters !');
		return false;
	}
	if(Number($('#unitprice').val()) <= 0){
		showMessage('info', 'Please Enter Fuel Price !');
		return false;
	}
	
	if(Number($('#selectVendor').val()) <= 0){
		showMessage('info', 'Please Select Vendor !');
		return false;
	}
	
	if($('#invoiceNumber').val() == null || $('#invoiceNumber').val().trim() == ''){
		showMessage('info', 'Please Enter Invoice Number !');
		return false;
	}
	if($('#invoiceDate').val() == null || $('#invoiceDate').val().trim() == ''){
		showMessage('info', 'Please Enter Invoice Date !');
		return false;
	}
	
	if(Number($('#tallyCompanyId').val()) <= 0){
		showMessage('info', 'Please Select Tally Company !');
		return false;
	}
	
	if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		return false;	
	}
	
	return true;
}

function vendorOnchange(){

    var a = document.getElementById("selectVendor").value;
    if (0 != a) {
        var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option>';
        $("#renPT_option").html(b)
    } else {
        var b = '<option value="1">CASH</option>';
        $("#renPT_option").html(b)
    }

}
function selectDiscountTaxType(typeId, d){
	$('#discountTaxTypId').val(typeId);
	if(typeId == 1){
		$('#amountId').removeClass('btn-default');
		$('#amountId').removeClass('btn-success');
		$('#percentId').addClass('btn-success');
	}else{
			$('#percentId').removeClass('btn-success');
			$('#percentId').removeClass('btn-default');
			$('#amountId').addClass('btn-success');
	
	}
	
	sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);
}

function sumthereTypeWise(a, b, c, d, e, multiple) {
	
	var dicTaxId;
	if(multiple == 0){
		 dicTaxId = $('#discountTaxTypId').val();
	} else {
		console.log("multi..");
		 dicTaxId = $("#discountTaxTypId" + multiple).val();
	}
	
	if(dicTaxId == 1){
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
	    var invoiceAmount = 0;
	    $("input[name=tatalcost]").each(function(){
	    	invoiceAmount += Number($(this).val());
	    	$('#invoiceAmount').val(invoiceAmount);
		});
		
	} else {
		var f = document.getElementById(a).value,
	        g = document.getElementById(b).value,
	        h = document.getElementById(c).value,
	        i = document.getElementById(d).value,
	        j = parseFloat(f) * parseFloat(g),
	        k = Number(j) - Number(h),
	        m = Number(k) + Number(i);
	    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
	    
	    var invoiceAmount = 0;
	    $("input[name=tatalcost]").each(function(){
	    	invoiceAmount += Number($(this).val());
	    	$('#invoiceAmount').val(invoiceAmount);
		});
		
	}
	
    
}