var NEW_TYRE		= 1;
var RETREAD_TYRE	= 2;
var currentDate = new Date();

$(document).ready(function() {
	$("#tyreId").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		ajax : {
			url : "getTyreID.in",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.TYRE_NUMBER,
							slug : e.slug,
							id : e.TYRE_ID
						}
					})
				}
			}
		}
	}),$("#tyreExpenseId").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		ajax : {
			url : "companyWiseTyreExpenseAutocomplete2",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.tyreExpenseName,
							slug : e.slug,
							id : e.tyreExpenseId
						}
					})
				}
			}
		}
	
	}),$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
});


$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success">'
        		+'<div class="panel-body">'
        		+'<div class="row1"><label class="L-size control-label">Tyre Number :<abbr title="required">*</abbr></label>'
        		+'<div class="I-size"><input type="hidden" id="tyreId_'+d+'" name="tyreNumber" style="width: 100%;" required="required"  /></div></div>'
        		+'<div class="row1"><label class="L-size control-label">Tyre Expense :<abbr title="required">*</abbr></label>'
        		+'<div class="I-size"><input type="hidden" id="tyreExpenseId_'+d+'" name="tyreExpenseName" style="width: 100%;" required="required"  /></div></div>'
        		+'<div class="row1"><label class="L-size control-label" for="manufacurer" >Expense Date :<abbr title="required">*</abbr></label>'
        			+'<div class="I-size">'
		        		+'<div class="input-group input-append date" id="opendDate_'+d+'">'
			        		+'<input type="text" class="form-text" readonly="\'readonly\'" name="tyreExpenseDate" id="tyreExpenseDateId_'+d+'" required="required" data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
			        		+'<span class="input-group-addon add-on">' 
			        			+'<span class="fa fa-calendar"></span>'
			        		+'</span>'
		        		+'</div>'
		        		+'<span id="expenseDateIcon" class=""></span>'
		        		+'<div id="expenseDateErrorMsg" class="text-danger"></div>'
		        	+'</div>'	
        		+'</div>'
        		+'<div class="row1"><label class="L-size control-label"></label>'
        		+'<div class="col-md-9">'
        		+'<div class="col-md-3"><label class="control-label"> Tyre Expense Amoount </label></div>'
        		+'<div class="col-md-1"><label class="control-label">Discount</label></div>'
        		+'<div class="col-md-1"><label class="control-label">GST</label></div>'
        		+'<div class="col-md-3"><label class="control-label">Total</label></div></div></div>'
        		+'<div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label>'
        		+'<div class="col-md-9">'
        		+'<div class="col-md-3"><input type="text" class="form-text" name="unitprice_many" id="unitprice_'+d+ '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'unitprice_' + d + "', 'discount_" + d + "', 'tax_" + d + "', 'totalCost_" + d + '\' );"\t ondrop="return false;"></div>'
        		+'<div class="col-md-1"><input type="text" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="discount_many" min="0.0" id="discount_'+d+ '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:sumthere(\'unitprice_' + d + "', 'discount_" + d + "', 'tax_" + d + "', 'totalCost_" + d + '\' );"\t ondrop="return false;"></div>' 
        		+'<div class="col-md-1"> <input type="text" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id);"  name="tax_many" id="tax_'+d+ '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:sumthere(\'unitprice_' + d + "', 'discount_" + d + "', 'tax_" + d + "', 'totalCost_" + d + '\' );"\t ondrop="return false;"></div>'
        		+'<div class="col-md-3"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="totalCost" id="totalCost_'+d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div>'
        		+'</div>'
        		+'<div class="row1"><label class="L-size control-label">Description :<abbr title="required">*</abbr></label>'
        		+'<div class="I-size">'
        		+'<input type="text" class="form-text" id="descriptionId_'+d+'" maxlength="249" name="description" placeholder="Enter description" /> </div>'
        		+'</div>'
        		+'<div class="row1"><label class="L-size control-label">Vendor :</label>'
        		+'<div class="I-size">'
        		+'<input type="hidden" id="selectVendor_'+d+'" name="vendorName" style="width: 100%;" /></div>'
        		+'</div>'
        		+'<div class="row1"><label class="L-size control-label">Tyre Document :</label>'
        		+'<div class="I-size">'
        		+'<input type="file" id="tripExpenseDocument_'+d+'"  name="input-file-preview" " /><span id="tyreExpenseDocumentIcon" class=""></span><div id="tyreExpenseDocumentIconErr" class="text-danger"></div> </div>'
        		+'</div>'
        		+'<a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), 
       $(document).ready(function() {
    	   $("#tyreId_"+d).select2({
    			minimumInputLength : 3, 
    			minimumResultsForSearch : 10,
    			ajax : {
    				url : "getTyreID.in",
    				dataType : "json",
    				type : "POST",
    				contentType : "application/json",
    				quietMillis : 50,
    				data : function(e) {
    					return {
    						term : e
    					}
    				},
    				results : function(e) {
    					return {
    						results : $.map(e, function(e) {
    							return {
    								text : e.TYRE_NUMBER,
    								slug : e.slug,
    								id : e.TYRE_ID
    							}
    						})
    					}
    				}
    			}
    		}),$("#tyreExpenseId_"+d).select2({
    			minimumInputLength : 3, 
    			minimumResultsForSearch : 10,
    			ajax : {
    				url : "companyWiseTyreExpenseAutocomplete2",
    				dataType : "json",
    				type : "POST",
    				contentType : "application/json",
    				quietMillis : 50,
    				data : function(e) {
    					return {
    						term : e
    					}
    				},
    				results : function(e) {
    					return {
    						results : $.map(e, function(e) {
    							return {
    								text : e.tyreExpenseName,
    								slug : e.slug,
    								id : e.tyreExpenseId
    							}
    						})
    					}
    				}
    			}
    		
    		}),$("#selectVendor_"+d).select2( {
    			minimumInputLength:3,
    			minimumResultsForSearch:10,
    			ajax:{
    			url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
    			dataType:"json",
    			type:"POST",
    			contentType:"application/json",
    			quietMillis:50,
    			data:function(e){
    				return{term:e}
    				},
    			results:function(e){
    				return{
    					results:$.map(e,function(e){
    						return {
    							text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
    							}
    						})
    					}
    				}
    			}
    		}),$("#opendDate_"+d).datepicker({
    	        autoclose: !0,
    	        todayHighlight: !0,
    	        format: "dd-mm-yyyy"
    	    }), $("#opendDate_"+d).datepicker("setDate", currentDate)
        }))
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
});
function fileToBase64(filename, filepath) {
	  return new Promise(resolve => {
	    var file = new File([filename], filepath);
	    var reader = new FileReader();    // Read file content on file loaded event
	    reader.onload = function(event) {
	      resolve(event.target.result);
	    };
	    
	    // Convert data to base64 
	    reader.readAsDataURL(file);
	  });
	};
function sumthere(b, c, d, e) {
    var f = 1,
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
    $("input[name=totalCost]").each(function(){
    	//invoiceAmount += Number($(this).val());
    	//$('#invoiceAmount').val(invoiceAmount);
	});
}

$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject			= new Object();
				var array	 			= new Array();
				var tyreNumber 			= new Array();
				var tyreExpenseName 	= new Array();
				var tyreExpenseDate 	= new Array();
				var unitprice_many	 	= new Array();
				var discount_many 		= new Array();
				var tax_many 			= new Array();
				var totalCost	   		= new Array();
				var description	   		= new Array();
				var tyreType	   		= new Array();
				var documentArr			= new Array();
				var vendorArr			= new Array();
				
				
				$("input[name=tyreNumber]").each(function(){
					tyreNumber.push($(this).val());
				});
				$("input[name=tyreExpenseName]").each(function(){
					tyreExpenseName.push($(this).val().replace(/"/g, ""));
				});
				$("input[name=tyreExpenseDate]").each(function(){
					tyreExpenseDate.push($(this).val());
				});
				$("input[name=unitprice_many]").each(function(){
					unitprice_many.push($(this).val());
				});
				$("input[name=discount_many]").each(function(){
					discount_many.push($(this).val());
				});
				$("input[name=tax_many]").each(function(){
					tax_many.push($(this).val());
				});
				$("input[name=totalCost]").each(function(){
					totalCost.push($(this).val());
				});
				$("input[name=description]").each(function(){
					description.push($(this).val().trim());
				});
				$("input[name=input-file-preview]").each(function(){
					documentArr.push($(this).val().trim());
				});
				$("input[name=vendorName]").each(function(){
					vendorArr.push($(this).val().trim());
				});
				

				for(var i =0 ; i< tyreNumber.length; i++){
					var tyreExpense					= new Object();
					
					if(tyreNumber[i] == "" || tyreNumber[i] == undefined){
						showMessage('info','Please Select Tyre Number');
						hideLayer();
						return false;
					}
					
					if(tyreExpenseName[i] == "" || tyreExpenseName[i] == undefined ){
						showMessage('info','Please Select Tyre Expense');
						hideLayer();
						return false;
					}
					
					if(unitprice_many[i] <= 0 ){
						showMessage('info','Please Enter Tyre Expense Amount');
						hideLayer();
						return false;
					}
					
					
					tyreExpense.tyreId				= tyreNumber[i];
					tyreExpense.tyreExpenseId		= tyreExpenseName[i];
					tyreExpense.tyreExpenseDate		= tyreExpenseDate[i];
					tyreExpense.description			= description[i];
					tyreExpense.tyreExpenseAmount	= unitprice_many[i];
					tyreExpense.discount			= discount_many[i];
					tyreExpense.gst					= tax_many[i];
					tyreExpense.totalCost			= totalCost[i];
					tyreExpense.tyreType			= NEW_TYRE;
					tyreExpense.tyreDocument		= documentArr[i];
					tyreExpense.vendorId			= vendorArr[i];
				
					array.push(tyreExpense);
				}
				
				
				jsonObject.tyreExpense 	= JSON.stringify(array);
				
				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);

			    data.append("tyreExpenseDetailsList", JSON.stringify(jsonObject));
				
				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "addTyreExpenseDetails",
					data: data,
					processData: false,
			        contentType: false,
			        cache: false,
			        success: function (data) {
			        	showMessage('success', 'New Tyre Expense Saved Successfully!');
			        	location.reload();
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
				});
			});
		});
