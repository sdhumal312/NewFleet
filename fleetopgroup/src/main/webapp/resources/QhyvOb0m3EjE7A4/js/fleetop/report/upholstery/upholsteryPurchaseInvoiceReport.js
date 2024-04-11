var data = [];
var gridObject;
var slickGridWrapper3;

$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
		format:'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}
);

$(document).ready(function() {
	$("#clothTypes").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getClothTypesList.in?Action=FuncionarioSelect2",
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
	                            text: a.clothTypeName,
	                            slug: a.slug,
	                            id: a.clothTypesId
	                        }
	                    })
	                }
	            }
	        }
    }),  $("#clothVendor").select2( {
    	 minimumInputLength: 3,
         minimumResultsForSearch: 10,
         ajax: {
             url: "getClothVendorSearchList.in?Action=FuncionarioSelect2",
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
    })
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["clothTypes"] 			=  $('#clothTypes').val();
				jsonObject["clothVendor"] 			=  $('#clothVendor').val();
				jsonObject["dateRange"] 	  		=  $('#TripCollectionExpenseRange').val();
				
				
				if($('#TripCollectionExpenseRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "getUpholsteryPurchaseInvoiceReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setUpholsteryPurchaseInvoiceReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

		});


function setUpholsteryPurchaseInvoiceReport(data) {
	
	$('#invoicePurchase').show();
	$('#UpholsteryTypeId').html(data.ClothType);
	$('#clothVendorId').html(data.VendorName);
	$('#dateRange').html(data.dateRange);
	
	
	var UpholsteryPurchaseInvoiceReport	= null;
	if(data.ClothInventory != undefined) {
		
		$("#reportHeader").html("Upholstery Purchase Invoice Report");

		$("#tripCollExpenseName").empty();
		UpholsteryPurchaseInvoiceReport	= data.ClothInventory;
		
		var UpholsteryPurchaseInvoiceReportFinal	= new Object();
		for(var i = 0; i < UpholsteryPurchaseInvoiceReport.length; i++ ) {
			if(UpholsteryPurchaseInvoiceReportFinal[UpholsteryPurchaseInvoiceReport[i].clothInvoiceId] == undefined){
				var UpholsteryPurchaseInvoiceReportObj = new Object();
				var UpholsteryNameAndNumberArr 		 = new Array();
				UpholsteryPurchaseInvoiceReportObj.UpholsteryNameAndNumberData = UpholsteryNameAndNumberArr;
				UpholsteryPurchaseInvoiceReportObj.clothInvoiceId = UpholsteryPurchaseInvoiceReport[i].clothInvoiceId;
				UpholsteryPurchaseInvoiceReportObj.clothInvoiceNumber = UpholsteryPurchaseInvoiceReport[i].clothInvoiceNumber;
				UpholsteryPurchaseInvoiceReportObj.invoiceNumber = UpholsteryPurchaseInvoiceReport[i].invoiceNumber;    
				UpholsteryPurchaseInvoiceReportObj.invDate = UpholsteryPurchaseInvoiceReport[i].invDate;      
				UpholsteryPurchaseInvoiceReportObj.invoiceAmount = UpholsteryPurchaseInvoiceReport[i].invoiceAmount;      
				
				var UpholsteryNameAndNumberObj = new Object();
				UpholsteryNameAndNumberObj.clothTypesName = UpholsteryPurchaseInvoiceReport[i].clothTypesName; 
				//UpholsteryNameAndNumberObj.batteryType = UpholsteryPurchaseInvoiceReport[i].batteryType; 
				UpholsteryNameAndNumberObj.discountTaxTypeId = UpholsteryPurchaseInvoiceReport[i].discountTaxTypeId; 
				UpholsteryNameAndNumberObj.quantity = UpholsteryPurchaseInvoiceReport[i].quantity; 
				UpholsteryNameAndNumberObj.unitprice = UpholsteryPurchaseInvoiceReport[i].unitprice;
				UpholsteryNameAndNumberObj.discount = UpholsteryPurchaseInvoiceReport[i].discount; 
				UpholsteryNameAndNumberObj.tax = UpholsteryPurchaseInvoiceReport[i].tax;    
				UpholsteryNameAndNumberObj.total = UpholsteryPurchaseInvoiceReport[i].total;    
				UpholsteryNameAndNumberArr.push(UpholsteryNameAndNumberObj)
				UpholsteryPurchaseInvoiceReportObj.UpholsteryNameAndNumberData = UpholsteryNameAndNumberArr;
				
				UpholsteryPurchaseInvoiceReportFinal[UpholsteryPurchaseInvoiceReport[i].clothInvoiceId] = UpholsteryPurchaseInvoiceReportObj;
			} else {      
				UpholsteryPurchaseInvoiceReportObj = UpholsteryPurchaseInvoiceReportFinal[UpholsteryPurchaseInvoiceReport[i].clothInvoiceId] 
				var UpholsteryNameAndNumberObj = new Object();
				UpholsteryNameAndNumberObj.clothTypesName = UpholsteryPurchaseInvoiceReport[i].clothTypesName; 
				//UpholsteryNameAndNumberObj.batteryType = UpholsteryPurchaseInvoiceReport[i].batteryType;
				UpholsteryNameAndNumberObj.discountTaxTypeId = UpholsteryPurchaseInvoiceReport[i].discountTaxTypeId;
				UpholsteryNameAndNumberObj.quantity = UpholsteryPurchaseInvoiceReport[i].quantity; 
				UpholsteryNameAndNumberObj.unitprice = UpholsteryPurchaseInvoiceReport[i].unitprice;
				UpholsteryNameAndNumberObj.discount = UpholsteryPurchaseInvoiceReport[i].discount; 
				UpholsteryNameAndNumberObj.tax = UpholsteryPurchaseInvoiceReport[i].tax;    
				UpholsteryNameAndNumberObj.total = UpholsteryPurchaseInvoiceReport[i].total;    
				UpholsteryNameAndNumberArr.push(UpholsteryNameAndNumberObj)
				UpholsteryPurchaseInvoiceReportObj.UpholsteryNameAndNumberData = UpholsteryNameAndNumberArr;
			}
		}
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th width="5%">');
		var th2		= $('<th width="5%">');
		var th3		= $('<th width="10%">');
		var th4		= $('<th width="10%">');
		var th5		= $('<th width="10%">');
		var th6		= $('<th width="80%">');

		th1.append('Sr No');
		th2.append('Upholstery Invoice Number');
		th3.append('Invoice Number');
		th4.append('Invoice Date');
		th5.append('Invoice Amount');
		//th5.append('Part Details');

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);

		thead.append(tr1);
		
		var tbody1 = $('<tbody>');
		var tr2 = $('<tr style="font-weight: bold; font-size : 12px;">');
		
		var thr1		= $('<th width="50%" style="text-align:center;">');
		var thr2		= $('<th width="10%" style="text-align:left;">');
		var thr3		= $('<th width="10%" style="text-align:left;">');
		var thr4		= $('<th width="10%">');
		var thr5		= $('<th width="10%">');
		var thr6		= $('<th width="10%">');
		
		thr1.append('Upholstery Name');
		thr2.append('Qty');
		thr3.append('Each');
		thr4.append('Disc');
		thr5.append('GST');
		thr6.append('Total');
		
		tr2.append(thr1);
		tr2.append(thr2);
		tr2.append(thr3);
		tr2.append(thr4);
		tr2.append(thr5);
		tr2.append(thr6);
		var partDetTbl	= $("<table width='100%'>");
		partDetTbl.append(tr2);
		th6.append(partDetTbl);
		
		var k=0;
		var invoiceAmount = 0.0;
		var totalQuantity = 0;
		var total = 0.0;
		
		for(var key in UpholsteryPurchaseInvoiceReportFinal) {
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				
				
				td1.append(k+1);
				k++;
				td2.append('<a href="showClothInvoice.in?Id='+UpholsteryPurchaseInvoiceReportFinal[key].clothInvoiceId+'" target="_blank" >'+UpholsteryPurchaseInvoiceReportFinal[key].clothInvoiceNumber+'</a>');
				td3.append(UpholsteryPurchaseInvoiceReportFinal[key].invoiceNumber);
				td4.append(UpholsteryPurchaseInvoiceReportFinal[key].invDate);
				td5.append(UpholsteryPurchaseInvoiceReportFinal[key].invoiceAmount);
				
				var x = Number(UpholsteryPurchaseInvoiceReportFinal[key].invoiceAmount);
				invoiceAmount = invoiceAmount + x;
				
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				
				var UpholsteryNameAndNumberData = UpholsteryPurchaseInvoiceReportFinal[key].UpholsteryNameAndNumberData;
				
				var innertable 		= $('<table width="100%">');
				for(var t = 0 ; t < UpholsteryNameAndNumberData.length; t++){
					
					var innertr 		= $('<tr>');
					
					var innertd1		= $('<td width="50%" style="text-align:left;">');
					var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd3		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd4		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd5		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd6		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					
					innertd1.append(UpholsteryNameAndNumberData[t].clothTypesName);
					innertd2.append(UpholsteryNameAndNumberData[t].quantity);
					innertd3.append(UpholsteryNameAndNumberData[t].unitprice);
					
					if(UpholsteryNameAndNumberData[t].discountTaxTypeId == 1){
						innertd4.append(UpholsteryNameAndNumberData[t].discount+'%');
						innertd5.append(UpholsteryNameAndNumberData[t].tax+'%');
					} else {
						innertd4.append(UpholsteryNameAndNumberData[t].discount);
						innertd5.append(UpholsteryNameAndNumberData[t].tax);
					}
					
					innertd6.append(UpholsteryNameAndNumberData[t].total);
					
					totalQuantity += UpholsteryNameAndNumberData[t].quantity;
					total		  += UpholsteryNameAndNumberData[t].total;
					
					innertr.append(innertd1);
					innertr.append(innertd2);
					innertr.append(innertd3);
					innertr.append(innertd4);
					innertr.append(innertd5);
					innertr.append(innertd6);
					
					innertable.append(innertr);
				}
				
				
				td6.append(innertable);
			
				
				tbody.append(tr);
		}
		
		var tr3 = $('<tr>');

		var tdtotal1		= $('<td colspan="4">');
		var tdtotal2		= $('<td>');
		var tdtotal3		= $('<td>');

		tdtotal1.append("Total :");
		tdtotal2.append(invoiceAmount.toFixed(2));

		tr3.append(tdtotal1);
		tr3.append(tdtotal2);

		var totaltable 		= $('<table width="100%">');
		var totaltr 		= $('<tr>');
		
		var td1		= $('<td width="50%">');
		var td2		= $('<td width="10%" align="left">');
		var td3		= $('<td width="10%">');
		var td4		= $('<td width="10%">'); 
		var td5		= $('<td width="10%">');
		var td6		= $('<td width="10%" align="left">');
		
		td2.append(totalQuantity.toFixed(2));
		td6.append(total.toFixed(2));
		
		totaltr.append(td1);
		totaltr.append(td2);
		totaltr.append(td3);
		totaltr.append(td4);
		totaltr.append(td5);
		totaltr.append(td6);
		
		totaltable.append(totaltr);
		tdtotal3.append(totaltable);

		tr3.append(tdtotal3);
		
		
		tbody.append(tr3);
		$("#tripCollExpenseName").append(thead);
		$("#tripCollExpenseName").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	//setTimeout(function(){ hideLayer(); }, 500);
}



