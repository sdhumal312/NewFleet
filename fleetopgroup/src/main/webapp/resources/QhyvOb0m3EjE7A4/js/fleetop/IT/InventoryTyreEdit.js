function setDropDownToElements(data){
	 
	var tyreAmount = JSON.parse(data);
	for(var i = 0; i< tyreAmount.length; i++){
		
		$("#"+tyreAmount[i].ityre_AMD_ID).select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getSearchTyreModel.in?Action=FuncionarioSelect2",
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
	                            text: a.TYRE_MODEL,
	                            slug: a.slug,
	                            id: a.TYRE_MT_ID
	                        }
	                    })
	                }
	            }
	        }
	    }), $("#editTyreSize"+tyreAmount[i].ityre_AMD_ID).select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
	                            text: a.TYRE_SIZE,
	                            slug: a.slug,
	                            id: a.TS_ID
	                        }
	                    })
	                }
	            }
	        }
	    }),$("#"+tyreAmount[i].ityre_AMD_ID).change(function() {
	    	var id = $(this).attr('id');
	    	$("#model"+id).select2("val", "");
            $.getJSON("getTyreModelSubType.in", {
                ModelType: $(this).val(),
                ajax: "true"
            }, function(a) {
                for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].TYRE_MST_ID + '">' + a[d].TYRE_MODEL_SUBTYPE + "</option>";
                b += "</option>", $("#model"+id).html(b)
            })
        }),$("#model"+tyreAmount[i].ityre_AMD_ID).select2();
		
		$('#'+tyreAmount[i].ityre_AMD_ID).select2('data', {
			id : tyreAmount[i].tyre_MANUFACTURER_ID,
			text : tyreAmount[i].tyre_MANUFACTURER
		});
		
		var option =  '<option value="'+tyreAmount[i].tyre_MODEL_ID+'">'+tyreAmount[i].tyre_MODEL+'</option>';
		$("#model"+tyreAmount[i].ityre_AMD_ID).html(option);
		//$("#model"+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].tyre_MODEL_ID);
		$("#model"+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].tyre_MODEL_ID).trigger('change');
		
		
		$('#editTyreSize'+tyreAmount[i].ityre_AMD_ID).select2('data', {
			id : tyreAmount[i].tyre_SIZE_ID,
			text : tyreAmount[i].tyre_SIZE
		});
		
		$('#quantity'+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].tyre_QUANTITY);
		$('#unitprice'+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].unit_COST);
		$('#discount'+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].discount);
		$('#tax'+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].tax);
		$('#tatalcost'+tyreAmount[i].ityre_AMD_ID).val(tyreAmount[i].total_AMOUNT);
	}
}

function isNumberKey(e, t) {
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != e.charcode) return !1;
    var r = $(t).val().length,
        a = $(t).val().indexOf(".");
    return !(a > 0 && 46 == n) && !(a > 0 && r + 1 - a > 3)
}
function removeModel(amountId){
	$('#grpmanufacturer'+amountId).remove();
	$('#grptyreModel'+amountId).remove();
	$('#grptyreSize'+amountId).remove();
	$('#grpquantity'+amountId).remove();
	$('#lebeleRow'+amountId).remove();
}

$(document).ready(function(){
	showSubLocationDropDown();
});