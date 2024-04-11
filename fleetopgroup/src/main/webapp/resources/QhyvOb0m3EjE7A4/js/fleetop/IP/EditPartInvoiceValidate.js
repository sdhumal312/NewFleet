function setDropDownToElements(data){
	var partInvoiceAmount = JSON.parse(data);
	
	
	var partInvoiceId = 0;
	for(var i = 0; i< partInvoiceAmount.length; i++){
		partInvoiceId	= partInvoiceAmount[i].inventory_id;
		$("#"+partInvoiceAmount[i].inventory_id).select2({
			minimumInputLength: 2,
	        minimumResultsForSearch: 10,
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
	    }),$("#"+partInvoiceAmount[i].inventory_id).change(function(obj) {
	    	  $.getJSON("getMasterPartShow.in", {
	                PartID: $(this).val(),
	                ajax: "true"
	            }, function(e) {
	                var t, n = "";
	                partInvoiceId	= obj.target.id;
	                if(e.make != undefined){
	                	
	                	$('#unitprice'+partInvoiceId).val(e.unitCost);
	                	$('#discount'+partInvoiceId).val(e.discount);
	                	$('#tax'+partInvoiceId).val(e.tax);
	                	t = e.make, document.getElementById("manufacturer"+partInvoiceId).value = t, document.getElementById("manufacturer"+partInvoiceId).readOnly = !1, 0 < t.length && (document.getElementById("manufacturer"+partInvoiceId).readOnly = !0), $("#hidden").hide()//n = e.part_photoid, document.getElementById("part_photoid").value = n
	                } else {
	                	 $('#unitprice'+partInvoiceId).val(0);
	 	                $('#discount'+partInvoiceId).val(0);
	 	                $('#tax'+partInvoiceId).val(0);
	                }
	            })
        }),
		$('#'+partInvoiceAmount[i].inventory_id).select2('data', {
			id : partInvoiceAmount[i].partid,
			text :  partInvoiceAmount[i].partnumber +"-"+ partInvoiceAmount[i].partname +"-"+ partInvoiceAmount[i].category +"-"+ partInvoiceAmount[i].make
		});
		
		$('#manufacturer'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].make);
		$('#quantity'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].history_quantity);
		$('#unitprice'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].unitprice);
		$('#discount'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].discount);
		$('#tax'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].tax);
		$('#tatalcost'+partInvoiceAmount[i].inventory_id).val(partInvoiceAmount[i].total);
		
		if(partInvoiceAmount[i].discountTaxTypeId == 1){
			$('#finalDiscountTaxTypId'+partInvoiceAmount[i].inventory_id).val(1);
			$('#percentId'+partInvoiceAmount[i].inventory_id).addClass('active');
		} else {
			$('#finalDiscountTaxTypId'+partInvoiceAmount[i].inventory_id).val(2);
			$('#amountId'+partInvoiceAmount[i].inventory_id).addClass('active');
		}
		
	}
}

$(document).ready(function(){
	$('#roundupTotal').val($('#previousInvoice').val());
	showSubLocationDropDown();
});

function selectDiscountTaxTypeEdit(typeId, d){
	if(typeId == 1){
		$('#percentId'+d).addClass('active');
		$('#amountId'+d).removeClass('active');
		$('#finalDiscountTaxTypId'+d).val(1);
	} else {
		$('#amountId'+d).addClass('active');
		$('#percentId'+d).removeClass('active');
		$('#finalDiscountTaxTypId'+d).val(2);
	}
	
}

function sumthereTypeWiseEdit(a, b, c, d, e, multiple) {
	
	var dicTaxId = $('#finalDiscountTaxTypId'+multiple).val();
	
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
	    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
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
	    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
		});
		
	}
    
}

$(document).on('keydown', 'input[pattern]', function(e){
	  var input = $(this);
	  var oldVal = input.val();
	  var regex = new RegExp(input.attr('pattern'), 'g');

	  setTimeout(function(){
	    var newVal = input.val();
	    if(!regex.test(newVal)){
	      input.val(oldVal); 
	    }
	  }, 0);
	});
