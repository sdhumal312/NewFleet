
$(document).ready(function() {
 	
 	var vendorTypeId		= $("#vendorTypeId").val();
 	var vendorTypeName		= $("#vendorTypeName").val();
 	
 	$("#selectVendor").select2("data",{
 		 id:vendorTypeId,
 		 text:vendorTypeName
 	});
});	
        
 	$('button[type=submit]').click(function(e) {
 		var TRID				= $("#TRID").val();
 		var vendorId			= $("#selectVendor").val();
 		var paymentTypeId		= $("#renPT_option").val();
 		var paymentNumber		= $("#paymentNumber").val();
 		var retreadDate			= $("#retreadDate").val();
 		var requiredRetreadDate	= $("#requiredRetreadDate").val();
 		var quoteNo				= $("#quoteNo").val();
 		var manualNo			= $("#manualNo").val();
 		
 		var jsonObject							= new Object();				
 		jsonObject["TRID"] 				    	= TRID;			
 		jsonObject["vendorId"] 				    = vendorId;			
 		jsonObject["paymentTypeId"] 		    = paymentTypeId;
 		jsonObject["paymentNumber"] 	        = paymentNumber;		
 		jsonObject["retreadDate"] 		        = retreadDate;			
 		jsonObject["requiredRetreadDate"]       = requiredRetreadDate;	
 		jsonObject["quoteNo"] 				    = quoteNo;				
 		jsonObject["manualNo"] 				    = manualNo;	
 		
 		if($("#selectVendor").val() == null || $("#selectVendor").val() == ""){
 			showMessage('errors', 'Please Select Vendor')
 			return false;
 		}
 		if($("#retreadDate").val() == null || $("#retreadDate").val() == "" ){
 			showMessage('errors', 'Please Select retreadDate')
 			return false;
 		}
 		if($("#requiredRetreadDate").val() == null || $("#requiredRetreadDate").val() == ""){
 			showMessage('errors', 'Please Select requiredRetreadDate')
 			return false;
 		}
 		
 		
 		$.ajax({					
 			url: "TyreRetreadWS/updateTyreRetreadInvoice",
 			type: "POST",
 			dataType: 'json',
 			data: jsonObject,
 			success: function (data) {						
 				window.location.replace("TyreRetreadNew/1#!");
 				renderReportData(data);
 				hideLayer();
 			},
 			error: function (e) {
 				showMessage('errors', 'Some error occured!')
 				hideLayer();
 			}
 		});
 		
 		
 	});


 