$(document).ready(function() {
	var a = 500,
	b = $(".addMorePartDiv"),
	c = $(".addMorePartButton"),
	d = 1;
	
	$(c).click(function(c) { 
		//alert("Hello! I am an alert box!!");
		c.preventDefault(), a > d && (d++, $(b).append('<div><div class="row"> '
		+'<div class="col-md-4"> <input type="hidden" name="partid" id="partName_'+d+'"'
		+' required="required" style="width: 100%;"'
		+' required="required"'
		+' placeholder="Please Enter 2 or more Part Name" /> <span'
		+' id="PurchaseOrders_idIcon" class=""></span></div>'
					+' <div class="col-md-1">'
						+' <input type="text" class="form-text" placeholder="Qty" name="quantity" required="required" id="quantity_'+d+'" '
						+' maxlength="10" data-toggle="tip"'
						+' data-original-title="enter Part Quantity" onpaste="return false"' 
						+' onkeypress="return isNumberKeyWithDecimal(event,this.id);" '
						+' onkeyup="calculatePurchageOrder(this)" min="0.00">' 
						+' <span id="quantityIcon" class=""></span>'
						+' <div id="quantityErrorMsg" class="text-danger"></div>'
					+' </div>'
					+' <div class="col-md-1">'
						+' <input type="text" name="parteachcost" step="any" onpaste="return false" class="form-text" placeholder="each cost"'
						+' required="required" id="parteachcost_'+d+'" maxlength="12" data-toggle="tip"'
						+' data-original-title="enter Each Cost" onkeypress="return isNumberKeyWithDecimal(event,this.id)"'
						+' ondrop="return false;"'
						+' onkeyup="calculatePurchageOrder(this)" min="0.00">'
						+'<span id="parteachcostIcon"'
						+'	class=""></span>'
						+' <div id="parteachcostErrorMsg" class="text-danger"></div><!--check-->'
					+' </div>'
					+' <div class="col-md-1">'
					+' <div class="input-group"><!--check  -->'
					+' <!--onkeypress="return isNumberKeyDis(event);"  --> <!--Original-->'
					+' <input type="text" class="form-text" placeholder="Dis" onpaste="return false"'
					+'  name="discount" required="required" id="discount_'+d+'"'
					+' maxlength="5" data-toggle="tip"'
					+' data-original-title="enter discount"'
					+' onkeypress="return isNumberKeyWithDecimal(event,this.id);"'
					+' ondrop="return false;'
					+' onkeyup="calculatePurchageOrder(this)" min="0.00">'
					+'<span class="input-group-addon">%</span>'
					+' </div>'
					+' <span id="discountIcon" class=""></span>'
					+' <div id="discountErrorMsg" class="text-danger"></div>'
						+'</div>'
					+' <div class="col-md-1">'
					+' <div class="input-group">'
					+' <!--onkeypress="return isNumberKeyTax(event);"  --> '
					+' <!--Original-->'
					+' <input type="text" class="form-text" placeholder="GST" onpaste="return false" name="tax" required="required" id="tax_'+d+'" maxlength="5"'
					+' onkeypress="return isNumberKeyWithDecimal(event,this.id);"'
					+'  data-toggle="tip" data-original-title="enter GST"'
					+' onkeyup="calculatePurchageOrder(this)" min="0.00">'
					+' <span class="input-group-addon">%</span>'
					+' </div>'
					+' <span id="taxIcon" class=""></span>'
					+' <div id="taxErrorMsg" class="text-danger"></div>'
					+' </div>'
					+' <div class="col-md-1">'
					+' <input type="text" name="totalcost" data-toggle="tip"'
					+' data-original-title="Total cost" class="form-text totalcost"'
					+' required="required" id="tatalcost_'+d+'" readonly="readonly">'
					+' </div>'	
					+' <a href="#" class="removePart col col-sm-1 col-md-3" id = "occurred_' + d + '"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>'
					 +' </div>'
					 + '<div class="help-block" id="last_occurred_' + d + '"></div>'
					 +' <div class="help-block" id="lastPOpartDetails_'+d+'"></div> '
					 ),
				partNameAutoComplete('partName_' + d)) 
			//	hideAllDiv('_'+d))
			getStockDetails('partName_' + d);
				
	}),
	$(b).on("click", ".removePart", function(a) {
		a.preventDefault(), $(this).parent("div").remove();
		$('#last_' + this.id).remove();
		getTotalCost();
	})
});

function calculatePurchageOrder(obj) {
	var id = obj.id;
	
	var d = id.split('_')[1];
	
    var quantity 	 = 0;
    var parteachcost = 0;
    var discount	 = 0;
    var tax			 = 0;
    
    if(d == undefined) {
		if($('#quantity').val() > 0)
			quantity		= $('#quantity').val();
			
		if($('#parteachcost').val() > 0)
    		parteachcost	= $('#parteachcost').val();
    		
    	if($('#discount').val() > 0)
    		discount		= $('#discount').val();
    		
    	if($('#tax').val() > 0)
    		tax				= $('#tax').val();
    } else {
		if($('#quantity_' + d).val() > 0)
    		quantity	= $('#quantity_' + d).val();
    	
		 if($('#parteachcost_' + d).val() > 0)
	    	parteachcost	= $('#parteachcost_' + d).val();
	    
	    if($('#discount_' + d).val() > 0)
	    	discount	= $('#discount_' + d).val();
	    	
	    if($('#tax_' + d).val() > 0)
	    	tax	= $('#tax_' + d).val();
	}
    
    var  totalCost 	 = parseFloat(quantity) * parseFloat(parteachcost);
       
    discountAmt = totalCost * discount / 100,
    totalCost 	= totalCost - discountAmt,
    totalTax 	= totalCost * tax / 100,
    totalCost 	= totalCost + totalTax;
   
    if(d == undefined)
		$('#tatalcost').val(totalCost.toFixed(2));
	else
   		$('#tatalcost_' + d).val(totalCost.toFixed(2));
   		
   	getTotalCost();
}

function partNameAutoComplete(inventory_name){
	 $("#" + inventory_name).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartList.in",
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
                            text: e.partnumber + " - " + e.partname + " - " + e.make,
                            slug: e.unittype,
                            id: e.partid
                        }
                    })
                }
            }
        }
    });
}
	
function getStockDetails(inventory_name) {
	$(document).ready(function() {
		$("#" + inventory_name).change(function() {
			getDetails(this);
			try{
			getLastPartCostDetails(inventory_name);
			}  catch(e){
				console.log("e : ",e);
			}
		});
	});
}

function getDetails(obj) {
	var id = obj.id;
	var num = 0;
	
	try {
		num = id.split('_')[1];
	} catch(e) {}
	
	if($("#locationWisePartCount").val() == false || $("#locationWisePartCount").val() === 'false'){
				if($(obj).val() > 0){
					$.getJSON("getPurchaseOrderStock.in",
							{
								PARTID : $(obj).val(),
									ajax : "true"
							},
							function(e) {
								var t = "", d = "", n = "", i = "", r = "";
									d = e.inventory_all_id,
									n = e.partnumber,
									i = e.partname,
									r = e.all_quantity+' '+e.convertToStr,
									a =e.totalSubstitudeQty
								
									if (e.all_quantity >0){
										t = '<p style="color: red;"> You have '
										+ r
										+ ' Quantity in stock . <a href="showInventory.in?inventory_all_id='
										+ d
										+ '" target="_blank">View  <i class="fa fa-external-link"></i></a> '; 
										if($('#showSustitutePartCount').val() == true || $('#showSustitutePartCount').val() === 'true'){
											t += 'Substitute part stock '+a +' . <a href="#" onclick="showSubPartModal('+$('#' + id).val()+')" >View  <i class="fa fa-external-link"></i></a>' ;
										}
																			
										t += '</p>';
									} else {
										t = '<p style="color: blue;">You don\'t have in stock . ';
																	
										if($('#showSustitutePartCount').val() == true || $('#showSustitutePartCount').val() === 'true'){
											t += '  Substitute part stock '+a +' . <a href="#" onclick="showSubPartModal('+$('#' + id).val()+')" >View  <i class="fa fa-external-link"></i></a>' ;
										}
																		
										t += '</p>';
									}
									
									if(num > 0)
										$("#last_occurred_" + num).html(t);
									else
										$("#last_occurred").html(t)
								})
							} else {
								if(num > 0)
									$("#last_occurred_" + num).html("");
								else
									$("#last_occurred").html("");
							}
						}

						$.getJSON("getVendorFixedPricePO.in",
							{
								PARTID : $(obj).val(),
								VENDOTID : document.getElementById("VENDOR_ID").value,
								ajax : "true"
							},
							function(e) {
								var d = "", n = "", i = "", r = "";
								d = e.VPPID,
								n = e.PARTEACHCOST,
								i = e.PARTDISCOUNT,
								r = e.PARTGST;
								
								if(num > 0) {
									if (d != 0 && null != d) {
										document.getElementById('parteachcost_' + num).value = n;
										document.getElementById('discount_'+ num).value = i;
										document.getElementById('tax_'+ num).value = r;
										document.getElementById('parteachcost_'+ num).readOnly = true;
										document.getElementById('discount_'+ num).readOnly = true;
										document.getElementById('tax_'+ num).readOnly = true;
									} else {
										document.getElementById('parteachcost_' + num).value = "";
										document.getElementById('discount_'+ num).value = "";
										document.getElementById('tax_'+ num).value = "";
										document.getElementById('parteachcost_'+ num).readOnly = false;
										document.getElementById('discount_'+ num).readOnly = false;
										document.getElementById('tax_'+ num).readOnly = false;
									}
								} else {
									if (d != 0 && null != d) {
										document.getElementById('parteachcost').value = n;
										document.getElementById('discount').value = i;
										document.getElementById('tax').value = r;
										document.getElementById('parteachcost').readOnly = true;
										document.getElementById('discount').readOnly = true;
										document.getElementById('tax').readOnly = true;
									} else {
										document.getElementById('parteachcost').value = "";
										document.getElementById('discount').value = "";
										document.getElementById('tax').value = "";
										document.getElementById('parteachcost').readOnly = false;
										document.getElementById('discount').readOnly = false;
										document.getElementById('tax').readOnly = false;
									}
								}
							})
}

function getTotalCost() {
	var totalcost	= 0;
	
	$(".totalcost").each(function() {
    	totalcost += Number($(this).val());
	});
	
	$('#Ftotal').val(totalcost.toFixed(2));
}