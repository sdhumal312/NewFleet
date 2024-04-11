function toggle3(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Battery Details") : (c.style.display = "block", d.innerHTML = "Cancel Battery Entries")
}

function toggleReceived_quantityBattery(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = '<i class="fa fa-cube">') : (t.style.display = "block", r.innerHTML = "Cancel")
}

function getMultiBatteryInput(a, b, c) {
    for (var d = "", e = b, f = 1; e >= f; f++) d += '<div class="row"><div class="col-md-1"><label class="control-label">' + f + '</label></div><div class="col-md-3"><input type="text" class="form-text" name="batterySerialNumber"></div><div class="col-md-2"><input type="text" class="form-text"value="' + c + '" required="required"readonly="readonly"></div></div><br>';
    d += "", $("#" + a).html(d)
}


$(document).ready(function() {
	$("#location").select2();
	$("#batterryTypeId").select2();
	$("#tagPicker").select2({
		closeOnSelect : !1
}),$("#batteryManufacurer, #editBatteryManufacturer").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getBatteryManufacturer.in?Action=FuncionarioSelect2",
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
                        text: a.manufacturerName,
                        slug: a.slug,
                        id: a.batteryManufacturerId
                    }
                })
            }
        }
    }
}),$("#batteryManufacurer, #editBatteryManufacturer ").change(function() {
    $.getJSON("getBatteryType.in", {
        ModelType: $(this).val(),
        ajax: "true"
    }, function(a) {
        for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].batteryTypeId + '">' + a[d].batteryType + "</option>";
        b += "</option>", $("#batterryTypeId, #editBatteryModel").html(b)
    })
}), $("#warehouselocation").select2({
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
}), $("#batteryCapacityId, #editBatterySize").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getSearchBatteryCapacity.in?Action=FuncionarioSelect2",
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
                        text: a.batteryCapacity,
                        slug: a.slug,
                        id: a.batteryCapacityId
                    }
                })
            }
        }
    }
}),$("#selectVendor").on("change", function() {
    var a = document.getElementById("selectVendor").value;
    if (0 != a) {
        var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option>';
        $("#renPT_option").html(b)
    } else {
        var b = '<option value="1">CASH</option>';
        $("#renPT_option").html(b)
    }
}), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
    showoption()
}), $("#renPT_option").change(),$("#selectVendor").select2({
    minimumInputLength: 3,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getBatteryVendorSearchList.in?Action=FuncionarioSelect2",
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
	
});

$(document).ready(function() {
    var a = 10,
        b = $(".input_fields"),
        c = $(".add_field"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Battery Manufacturer :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="batteryManufacurer' + d + '" name="batteryManufacturerId" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Battery Manufacturer Name" /></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Battery Type :<abbr title="required">*</abbr></label><div class="I-size"><select style="width: 100%;" id="batterryTypeId' + d + '" name="batteryTypeId" required="required"></select> <label class="error" id="errorManufacturer" style="display: none"> </label></div></div><div class="row1"><label class="L-size control-label">Battery Capacity :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="batteryCapacityId' + d + '" name="batteryCapacityId" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Battery Capacity Name" /></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);"onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + d + '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKey(event,this);" onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#batteryManufacurer" + d).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getBatteryManufacturer.in?Action=FuncionarioSelect2",
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
    	                            text: a.manufacturerName,
    	                            slug: a.slug,
    	                            id: a.batteryManufacturerId
    	                        }
                            })
                        }
                    }
                }
            }),$("#batteryCapacityId" + d).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getSearchBatteryCapacity.in?Action=FuncionarioSelect2",
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
                                    text: a.batteryCapacity,
                                    slug: a.slug,
                                    id: a.batteryCapacityId
                                }
                            })
                        }
                    }
                }
            }),$("#batteryManufacurer" + d).change(function() {
                $.getJSON("getBatteryType.in", {
                    ModelType: $(this).val(),
                    ajax: "true"
                }, function(a) {
                    for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].batteryTypeId + '">' + a[d].batteryType + "</option>";
                    b += "</option>", $("#batterryTypeId" + d).html(b)
                })
            }), $("#batterryTypeId" + d).select2()
        }))
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
});

//latest 
function purchaseOrderBatteryValidate()
{
	if(Number($('#batteryManufacurer').val()) <= 0){
		showMessage('info','Please Select Battery Manufacturer!');
		return false;
	}	
	if(Number($('#batterryTypeId').val()) <= 0){
		showMessage('info','Please Select Battery Model!');
		return false;
	}
	if(Number($('#batteryCapacityId').val()) <= 0){
		showMessage('info','Please Select Battery Capacity!');
		return false;
	}
}
//latest 
