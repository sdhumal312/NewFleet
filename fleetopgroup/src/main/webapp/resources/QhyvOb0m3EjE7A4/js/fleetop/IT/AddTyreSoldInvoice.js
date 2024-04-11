var AVAILABLE_TO_SOLD 	= 3;
var SCRAPED_TO_SOLD		= 4;

var TYRE_ASSIGN_STATUS_AVAILABLE = 1;
var TYRE_ASSIGN_STATUS_SCRAPED 	 = 3;

var TYRE_ASSIGN_STATUS_IN_PROCESS 		= 5;
var TYRE_ASSIGN_STATUS_SOLD				= 6;

function sumthere(a,b, c, d, e) {
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        i = document.getElementById(d).value,
        f = document.getElementById(a).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
    var invoiceAmount = 0;
    $("input[name=totalCost]").each(function(){
	});
}
$( "#unitprice" ).keyup(function() {
	if( $("#unitprice").val() == "" || $("#unitprice").val() == undefined ){
		$("#totalCost").val(0.0);
	}
});

$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				showLayer();
				var 	checkBoxArray			= new Array();
				
				$.each($("input[name*=TyreID]:checked"), function(){ 
					checkBoxArray.push($(this).val());

				});
				
				if(checkBoxArray.length == 0 ){
					showMessage('info','please Select Atleast One Tyre For Sold');
					hideLayer();
					return false;
				}
				
				if($('#invoiceDate').val() == "" || $('#invoiceDate').val() == undefined || $('#invoiceDate').val() == null){
					showMessage('info','Please Select Sold Date');
					hideLayer();
					return false;
				}
				
				if($('#soldType').val() == SCRAPED_TO_SOLD){
					
					if($('#weight').val() <= 0 || $('#weight').val() == undefined){
						showMessage('info','Please Enter Weight');
						hideLayer();
						return false;
					}

					if($('#unitprice').val() < 0 || $('#unitprice').val() == undefined){
						showMessage('info','Please Enter Sold Cost');
						hideLayer();
						return false;
					}
					
					if($('#discount').val() < 0 || $('#discount').val() == undefined){
						showMessage('info','Please Enter Comission ');
						hideLayer();
						return false;
					}
					
					if($('#tax').val() < 0 || $('#tax').val() == undefined){
						showMessage('info','Please Enter gst');
						hideLayer();
						return false;
					}
					
				}
			
				var jsonObject								= new Object();
				jsonObject["tyreId"]						= JSON.stringify(checkBoxArray).replace(/[\[\]']+/g,'').replace(/\"/g, ""); 
				jsonObject["invoiceDate"]					= $('#invoiceDate').val();
				jsonObject["tyreQuantity"]					= checkBoxArray.length;
				jsonObject["tyreWeight"]					= $('#weight').val();
				jsonObject["gst"]							= $('#tax').val();
				jsonObject["discount"]						= $('#discount').val();
				jsonObject["invoiceCost"]					= $('#unitprice').val();
				jsonObject["invoiceNetCost"]				= $('#totalCost').val();
				jsonObject["description"]					= $('#descriptionId').val();
				jsonObject["soldType"]						= $('#soldType').val();
				if($('#soldType').val() == AVAILABLE_TO_SOLD){
					jsonObject["tyreStatus"]					= TYRE_ASSIGN_STATUS_IN_PROCESS; // to save tyre Status
				}else if($('#soldType').val() == SCRAPED_TO_SOLD){
					jsonObject["tyreStatus"]					= TYRE_ASSIGN_STATUS_SOLD;// to save tyre Status
				}
				
				
				$.ajax({
					url: "saveTyreSoldInvoice",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						hideLayer();
					//	showMessage('success', 'New Tyre Expense Saved Successfully!');
						if(data.sequenceNotFound != undefined || data.sequenceNotFound != null){
							showMessage('info','equence not found please contact to system Administrator!')
							return false;
						}else{
							window.location.replace("addTyreSoldDetails?ID=" + data.tyreSoldInvoiceId + "");
						}
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
				
			})
		});


/*$('input.qty,input.price,input.discount').on('change keyup',function(){
	  var $tr = $(this).closest('tr'),
	      $qty = $tr.find('input.qty')      ,
	      $price= $tr.find('input.price'),
	      $discount= $tr.find('input.discount'),
	      $total= $tr.find('input.expensess_sum'),
	      $grand_total=$('#grand_total');
	      
	      $total.val($qty.val()*($price.val()-($price.val()*($discount.val()/100))));
	      
	      var grandTotal=0;
	      $('table').find('input.expensess_sum').each(function(){
	          if(!isNaN($(this).val()))
	            {grandTotal += parseInt($(this).val()); 
	            }
	      });
	      if(isNaN(grandTotal))
	         grandTotal =0;
	      $grand_total.val(grandTotal)
	})*/