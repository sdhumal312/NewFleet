function AddPart(){
 	$('#configureMorePart').modal('show');
 	
 }

$(document).ready(function() {
	 $("#retreadTyreNum").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getAvailableTyreID.in",
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
	                            text: e.TYRE_NUMBER ,
	                            slug: e.slug,
	                            id: e.TYRE_ID
	                        }
	                    })
	                }
	            }
	        }
	 })
}),$(document).ready(function() {
    var e = $("#NoOfPartsAllowedToAdd").val(), addMorePartsAtBottom = $("#addMorePartsAtBottom").val(),
    t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    if(addMorePartsAtBottom == true || addMorePartsAtBottom == 'true'){
    	t = $("#moreParts");
    }  
   
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Search Tyre Number :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="retreadTyreNum' + a + '" name="tyreNumber_many" onchange="validateTyreNumber(this)"; style="width: 100%;" required="required" placeholder="Please Enter 2 or more Part Name or Part Number" /></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Quantity:</label><div class="col-md-9"><div class="col-md-1"><input type="text" value="1" readonly="readonly" class="form-text" name="quantity_many" min="0.0" id="quantity' + a + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:calthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + a + '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:calthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + a + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:calthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + a + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:calthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" id="tatalcost' + a + '" name="tatalcost" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKeyQut(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
        	 $("#retreadTyreNum" + a).select2({
                 minimumInputLength: 2,
                 minimumResultsForSearch: 10,
                 ajax: {
                     url: "getAvailableTyreID",
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
                                 	 
                                 	text: e.TYRE_NUMBER ,
     	                            slug: e.slug,
     	                            id: e.TYRE_ID
                                 }
                             })
                         }
                     }
                 }
             })
         }))
     }), $(t).on("click", ".remove_field", function(e) {
         e.preventDefault(), $(this).parent("div").remove(), a--
     })
 });

function isNumberKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = 46 == t || t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = n ? "none" : "inline", n
}

function calthere(e, t, n, a, r) {
     var i = document.getElementById(e).value,
         l = document.getElementById(t).value,
         s = document.getElementById(n).value,
         o = document.getElementById(a).value,
         d = parseFloat(i) * parseFloat(l),
         u = d * s / 100,
         c = d - u,
         e = c * o / 100,
         y = c + e;
     isNaN(y) || (document.getElementById(r).value = y.toFixed(2))
     var invoiceAmountt = 0;
     
     $("input[name=tatalcost]").each(function(){
     	
     	invoiceAmountt += Number($(this).val());
     	$('#invoiceAmount').val(invoiceAmountt);
     	
 	});
     
     
 }

 function validateTyreNumber(object){// Do Not allow Parameters Which are already In used
	 $('input[name*=tyreNumber_many]').each(function(obj){
			if(this.value == object.value && object.id != this.id){
				showMessage('info','Already Selected !');
				var thisId =object.id;
				$("#"+thisId).select2("val","");
				//$('#thisId').select2("val", "");
				object.value = 0;
				return false;
			}
			
		});
	} 
 
 $("#btn-save").click(function(event){
	 if(!validateAddPart()){
		 return;
	 }
	 var jsonObject			= new Object();
	 $('#inventoryRetreadTyreId').val();
	
	 var retreadTyreId 		= new Array();
	 var quantityId 		= new Array();
	 var unitCostId 		= new Array();
	 var discountId 		= new Array();
	 var taxId 				= new Array();
	 var totalId 			= new Array();
	 
	 
	 jsonObject["TRID"] 	=  $('#inventoryRetreadTyreId').val();
	 
	 $('input[name*=tyreNumber_many]').each(function(obj){
		 retreadTyreId.push($(this).val());
	 });
	 
	 $('input[name*=tatalcost]').each(function(obj){
		 totalId.push($(this).val());
	 });
	 
	 $('input[name*=unitprice_many]').each(function(obj){
		 unitCostId.push($(this).val());
	 });
	 
	 $('input[name*=discount_many]').each(function(obj){
		 discountId.push($(this).val());
	 });
	 
	 $('input[name*=tax_many]').each(function(obj){
		 taxId.push($(this).val());
	 });
	 
	 var array = new Array();
	 
	 for(var i =0 ; i< retreadTyreId.length; i++){
			var retreadObject	= new Object();
			retreadObject.retreadTyreId			= retreadTyreId[i];
			retreadObject.totalId				= totalId[i];
			retreadObject.unitCostId			= unitCostId[i];
			retreadObject.discountId			= discountId[i];
			retreadObject.taxId					= taxId[i];
			
			array.push(retreadObject);
		}
	 
	 jsonObject.finalRetreadObject = JSON.stringify(array);
		showLayer();
		$.ajax({
	             url: "TyreRetreadWS/addMultipleRetreadTyre",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	 				showMessage('success', 'RetreadTyre Added Successfully...')
					setTimeout(function(){
						location.reload();
						//window.location.replace("ShowVendor.in?vendorId="+vendorID+"&page=1#!");
						hideLayer();
					},150);
				},
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	 
	 
 }); 
 
 $(document).ready(
		 function($) {
			$('button[id=addMultipleRetread]').click(function(e) {
				
			e.preventDefault();
			var flag = false; 
			var currentDate = new Date();
			var NEW_TYRE		= 1;
			var RETREAD_TYRE	= 2;
		
			showLayer();
			var jsonObject			= new Object();
			
			var array	 			= new Array();
			var tyreNumber 			= new Array();
			var unitprice_many	 	= new Array();
			var discount_many 		= new Array();
			var tax_many 			= new Array();
			var totalCost	   		= new Array();
			
		
			jsonObject["TRID"]								= $('#tyreRetreadId').val();
			jsonObject["tyreExpenseId_invoiceNum"]			= $('#tyreExpenseId').val();
			jsonObject["description"]						= $('#description').val().trim();
			jsonObject["expenseDate"]						= $('#expenseDate').val();
			
			
			$("input[name=traStatus]").each(function(){
				if($(this).val() == undefined || $(this).val() == "" || $(this).val() == 1){
					flag = true;
				}
			});
			
			if(flag){
				showMessage('info','Please Recived Or Reject Tyre')
				hideLayer();
				return false;
			}
			
			$("input[name=tyreNumbers]").each(function(){
				tyreNumber.push($(this).val());
			});
			
			$("input[name=unitPriceMany]").each(function(){
				unitprice_many.push($(this).val());
			});
			$("input[name=discountMany]").each(function(){
				discount_many.push($(this).val());
			});
		
			$("input[name=taxMany]").each(function(){
				tax_many.push($(this).val());
			});
			$("input[name=totalCostMany]").each(function(){
				totalCost.push($(this).val());
			});
			
			for(var i =0 ; i< tyreNumber.length; i++){
				var tyreExpenseDetailsList					= new Object();
				tyreExpenseDetailsList.tyreId				= tyreNumber[i];
				tyreExpenseDetailsList.tyreExpenseId		= $('#tyreRetreadId').val();
				tyreExpenseDetailsList.tyreExpenseDate		= $('#expenseDate').val();
				tyreExpenseDetailsList.description			= $('#description').val();
				tyreExpenseDetailsList.tyreExpenseAmount	= unitprice_many[i];
				tyreExpenseDetailsList.discount				= discount_many[i];
				tyreExpenseDetailsList.gst					= tax_many[i];
				tyreExpenseDetailsList.totalCost			= totalCost[i];
				tyreExpenseDetailsList.tyreType				= RETREAD_TYRE;
				
				array.push(tyreExpenseDetailsList);
			}
			jsonObject.tyreExpenseDetailsList = JSON.stringify(array);
			
			
			var form = $('#fileUploadForm')[0];
		    var data = new FormData(form);
		    data.append("tyreExpenseDetailsList", JSON.stringify(jsonObject));
			
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "addRetreadTyreExpenseDetails",
				data: data,
				processData: false,
		        contentType: false,
		        cache: false,
		        success: function (data) {
					hideLayer();
					showMessage('success', 'New Tyre Expense Saved Successfully!');
					window.location.replace("ShowRetreadTyre?RID=" + $('#tyreRetreadId').val() + "");
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
			
		});
	});				
		
		