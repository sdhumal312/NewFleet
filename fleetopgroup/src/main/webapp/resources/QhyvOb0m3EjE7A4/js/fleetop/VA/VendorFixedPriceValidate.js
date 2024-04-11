$(document).ready(function() {
		
		getInventoryList("inventory_name");
})
	    var a = 10,
	        b = $(".input_fields_wrap"),
	        c = $(".add_field_button"),
	        d = 1;
	    $(c).click(function(c) {
	        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="row1" id="grpwoJobType" class="form-group">'+
	        		'													<label class="L-size control-label" for="from">Part'+
	        		'														Name </label>'+
	        		'													<div class="I-size">'+
	        		'														<div class="col-md-8">'+
	        		'															<div class="row">'+
	        		'																<input type="hidden" name="partid" id="inventory_name' + d + '"'+
	        		'																	required="required" style="width: 100%;"'+
	        		'																	required="required"'+
	        		'																	placeholder="Please Enter 2 or more Part Name" /> <span'+
	        		'																	id="PurchaseOrders_idIcon" class=""></span>'+
	        		'																<div id="PurchaseOrders_idErrorMsg" class="text-danger"></div>'+
	        		'															</div>'+
	        		'														</div>'+
	        		'													</div>'+
	        		'												</div>'+
	        		'												<div class="row1" id="grpwoJobType" class="form-group">'+
	        		'													<label class="L-size control-label" for="from">Part'+
	        		'														each Price: </label>'+
	        		'													<div class="I-size">'+
	        		'														<div class="col-md-8">'+
	        		'															<!-- this Quantity in one -->'+
	        		'															<input type="hidden" class="form-text" placeholder="Qty"'+
	        		'																name="quantity" required="required" id="quantity' + d + '"'+
	        		'																readonly="readonly" value="1">'+
	        		'															<!-- this each cost in one -->'+
	        		'															<input type="text" name="parteachcost" class="form-text"'+
	        		'																placeholder="ech cost" required="required"'+
	        		'																id="parteachcost' + d + '" maxlength="7" data-toggle="tip"'+
	        		'																data-original-title="enter Each Cost"'+
	        		'																onkeypress="return isNumberKeyEach(event);"'+
	        		'																ondrop="return false;"'+
	        		'																onkeyup="javascript:sumthere(\'quantity' + d + '\', \'parteachcost' + d + '\',\'discount' + d + '\', \'tax' + d + '\', \'tatalcost' + d + '\');"'+
	        		'																min="0.00"> <span id="parteachcostIcon" class=""></span>'+
	        		'															<div id="parteachcostErrorMsg" class="text-danger"></div>'+
	        		'														</div>'+
	        		'													</div>'+
	        		'												</div>'+
	        		'												<div class="row1" id="grpwoJobType" class="form-group">'+
	        		'													<label class="L-size control-label" for="from">Part'+
	        		'														Discount :</label>'+
	        		'													<div class="I-size">'+
	        		'														<div class="col-md-8">'+
	        		'															<div class="input-group">'+
	        		'																<input type="text" class="form-text" placeholder="Dis"'+
	        		'																	name="discount" required="required" id="discount' + d + '"'+
	        		'																	maxlength="5" data-toggle="tip"'+
	        		'																	data-original-title="enter discounr"'+
	        		'																	onkeypress="return isNumberKeyDis(event);"'+
	        		'																	ondrop="return false;"'+
	        		'																	onkeyup="javascript:sumthere(\'quantity' + d + '\', \'parteachcost' + d + '\', \'discount' + d + '\', \'tax' + d + '\', \'tatalcost' + d + '\');"'+
	        		'																	min="0.00"> <span class="input-group-addon">%</span>'+
	        		'															</div>'+
	        		'															<span id="discountIcon" class=""></span>'+
	        		'															<div id="discountErrorMsg" class="text-danger"></div>'+
	        		'														</div>'+
	        		'													</div>'+
	        		'												</div>'+
	        		'												<div class="row1" id="grpwoJobType" class="form-group">'+
	        		'													<label class="L-size control-label" for="from">Part'+
	        		'														GST Percentage: </label>'+
	        		'													<div class="I-size">'+
	        		'														<div class="col-md-8">'+
	        		'															<div class="input-group">'+
	        		'																<input type="text" class="form-text" placeholder="GST"'+
	        		'																	name="tax" required="required" id="tax' + d + '" maxlength="5"'+
	        		'																	onkeypress="return isNumberKeyTax(event);"'+
	        		'																	data-toggle="tip" data-original-title="enter GST"'+
	        		'																	onkeyup="javascript:sumthere(\'quantity' + d + '\', \'parteachcost' + d + '\', \'discount' + d + '\', \'tax\', \'tatalcost' + d + '\');"'+
	        		'																	min="0.0"> <span class="input-group-addon">%</span>'+
	        		'															</div>'+
	        		'															<span id="taxIcon" class=""></span>'+
	        		'															<div id="taxErrorMsg" class="text-danger"></div>'+
	        		'														</div>'+
	        		'													</div>'+
	        		'												</div>'+
	        		'												<div class="row1" id="grpwoJobType" class="form-group">'+
	        		'													<label class="L-size control-label" for="from">Total'+
	        		'													</label>'+
	        		'													<div class="I-size">'+
	        		'														<div class="col-md-4">'+
	        		'															<input type="text" name="totalcost" data-toggle="tip"'+
	        		'																data-original-title="Total cost" class="form-text"'+
	        		'																required="required" id="tatalcost' + d + '" readonly="readonly">'+
	        		'														</div>'+
	        		'													</div>'+
	        		'												</div> <a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $(document).ready(function() {
	        			getInventoryList("inventory_name" + d); 
	        }))
	    }), $(b).on("click", ".remove_field", function(a) {
	        a.preventDefault(), $(this).parent("div").remove(), d--
	    })
	    
	    
	    
	    
	    function getInventoryList(e) {
    $("#" + e).select2({
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
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    })
}
	    
	    function sumthere(e, n, t, r, a) {
	        var i = document.getElementById(e).value,
	            o = document.getElementById(n).value,
	            l = document.getElementById(t).value,
	            c = document.getElementById(r).value,
	            s = parseFloat(i) * parseFloat(o),
	            d = s * l / 100,
	            u = s - d,
	            e = u * c / 100,
	            y = u + e;
	        isNaN(y) || (document.getElementById(a).value = y.toFixed(2))
	    }
	    function isNumberKeyQut(e) {
	        var n = 0 == e.keyCode ? e.charCode : e.keyCode,
	            t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
	        return document.getElementById("errorPin").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorPin").style.display = t ? "none" : "inline", t
	    }

	    function isNumberKeyEach(e, n) {
	        var t = e.which ? e.which : event.keyCode;
	        if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
	        var r = $(n).val().length,
	            a = $(n).val().indexOf(".");
	        if (a > 0 && 46 == t) return !1;
	        if (a > 0) {
	            var i = r + 1 - a;
	            if (i > 3) return !1
	        }
	        return !0
	    }

	    function isNumberKeyDis(e, n) {
	        var t = e.which ? e.which : event.keyCode;
	        if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
	        var r = $(n).val().length,
	            a = $(n).val().indexOf(".");
	        if (a > 0 && 46 == t) return !1;
	        if (a > 0) {
	            var i = r + 1 - a;
	            if (i > 3) return !1
	        }
	        return !0
	    }

	    function isNumberKeyTax(e, n) {
	        var t = e.which ? e.which : event.keyCode;
	        if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
	        var r = $(n).val().length,
	            a = $(n).val().indexOf(".");
	        if (a > 0 && 46 == t) return !1;
	        if (a > 0) {
	            var i = r + 1 - a;
	            if (i > 3) return !1
	        }
	        return !0
	    }
	    var specialKeys = new Array;
	    specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39)

	