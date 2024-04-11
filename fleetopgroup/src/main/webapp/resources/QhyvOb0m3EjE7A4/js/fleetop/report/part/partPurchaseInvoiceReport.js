var data = [];
var gridObject;
var slickGridWrapper3;

$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
		format : 'DD-MM-YYYY',
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
	$("#searchpartPO").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchMasterPart.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partnumber+" - "+a.partname+" - "+a.category+" - "+a.make, slug: a.slug, id: a.partid
                        }
                    }
                    )
                }
            }
        }
    }), $("#PartVendorList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getVendorSearchListInventory.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    })
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				jsonObject["partId"] 			=  $('#searchpartPO').val();
				jsonObject["vendorId"] 			=  $('#PartVendorList').val();
				
				
				$.ajax({
					
					url: "PartWS/getPartPurchaseInvoiceReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setPartPurchaseInvoiceReport(data,Number($('#PartVendorList').val()));
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});


function setPartPurchaseInvoiceReport(data,selectedVendorId) {
	
	$('#invoicePurchase').show();
	$('#dateRange').html(data.dateRange);
	$('#PartNumber').html(data.PartName);
	$('#vendor').html(data.VendorName);
	
	var partPurchaseInvoiceReport	= null;
	if(data.PartPurchaseInvoiceReport != undefined) {
		
		$("#reportHeader").html("Part Purchase Invoice Report");

		$("#tripCollExpenseName").empty();
		partPurchaseInvoiceReport	= data.PartPurchaseInvoiceReport;
		
		var partPurchaseInvoiceReportFinal	= new Object();
	//	var PartInvoiceNumber = 0;
		for(var i = 0; i < partPurchaseInvoiceReport.length; i++ ) {
			if(partPurchaseInvoiceReportFinal[partPurchaseInvoiceReport[i].partInvoiceId] == undefined){
				var partPurchaseInvoiceReportObj = new Object();
				var partNameAndNumberArr 		 = new Array();
				partPurchaseInvoiceReportObj.partNameAndNumberData = partNameAndNumberArr;
				//PartInvoiceNumber = partPurchaseInvoiceReport[i].partInvoiceId;
				partPurchaseInvoiceReportObj.partInvoiceId = partPurchaseInvoiceReport[i].partInvoiceId;
				partPurchaseInvoiceReportObj.partInvoiceNumber = partPurchaseInvoiceReport[i].partInvoiceNumber;
				partPurchaseInvoiceReportObj.invoice_number = partPurchaseInvoiceReport[i].invoice_number;    
				partPurchaseInvoiceReportObj.invoice_date = partPurchaseInvoiceReport[i].invoice_date;      
				partPurchaseInvoiceReportObj.invoice_amount = partPurchaseInvoiceReport[i].invoice_amount; 
				
				partPurchaseInvoiceReportObj.vendor_name = partPurchaseInvoiceReport[i].vendor_name;     
				
				var partNameAndNumberObj = new Object();
				partNameAndNumberObj.partnumber = partPurchaseInvoiceReport[i].partnumber; 
				partNameAndNumberObj.partname = partPurchaseInvoiceReport[i].partname; 
				partNameAndNumberObj.discountTaxTypeId = partPurchaseInvoiceReport[i].discountTaxTypeId; 
				partNameAndNumberObj.history_quantity = partPurchaseInvoiceReport[i].history_quantity; 
				partNameAndNumberObj.quantity      = partPurchaseInvoiceReport[i].quantity;
				partNameAndNumberObj.unitprice = partPurchaseInvoiceReport[i].unitprice;
				partNameAndNumberObj.discount = partPurchaseInvoiceReport[i].discount; 
				partNameAndNumberObj.tax = partPurchaseInvoiceReport[i].tax;    
				partNameAndNumberObj.total = partPurchaseInvoiceReport[i].total;    
				partNameAndNumberArr.push(partNameAndNumberObj)
				partPurchaseInvoiceReportObj.partNameAndNumberData = partNameAndNumberArr;
				
				partPurchaseInvoiceReportFinal[partPurchaseInvoiceReport[i].partInvoiceId] = partPurchaseInvoiceReportObj;
			} else {      
				partPurchaseInvoiceReportObj = partPurchaseInvoiceReportFinal[partPurchaseInvoiceReport[i].partInvoiceId] 
				var partNameAndNumberObj = new Object();
				partNameAndNumberObj.partnumber = partPurchaseInvoiceReport[i].partnumber; 
				partNameAndNumberObj.partname = partPurchaseInvoiceReport[i].partname; 
				partNameAndNumberObj.discountTaxTypeId = partPurchaseInvoiceReport[i].discountTaxTypeId; 
				partNameAndNumberObj.history_quantity = partPurchaseInvoiceReport[i].history_quantity; 
				partNameAndNumberObj.quantity      = partPurchaseInvoiceReport[i].quantity;
				partNameAndNumberObj.unitprice = partPurchaseInvoiceReport[i].unitprice;
				partNameAndNumberObj.discount = partPurchaseInvoiceReport[i].discount; 
				partNameAndNumberObj.tax = partPurchaseInvoiceReport[i].tax;    
				partNameAndNumberObj.total = partPurchaseInvoiceReport[i].total;    
				partNameAndNumberArr.push(partNameAndNumberObj)
				partPurchaseInvoiceReportObj.partNameAndNumberData = partNameAndNumberArr;
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
		var th7		= $('<th width="20%">');
		

		th1.append('Sr No');
		th2.append('Part Invoice Number');
       if (selectedVendorId <= 0) {
          th7.append('Vendor Name');
         }
		th3.append('Invoice Number');
		th4.append('Invoice Date');
		th5.append('Invoice Amount');
		
		//th6.append('Vendor Name');
		
		//th5.append('Part Details');

		tr1.append(th1);
		tr1.append(th2);
		if (selectedVendorId <= 0) {
		tr1.append(th7);
		}
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		
		

		thead.append(tr1);
		
		var tbody1 = $('<tbody>');
		var tr2 = $('<tr style="font-weight: bold; font-size : 12px;">');
		
		var thr1		= $('<th width="40%" style="text-align:center;">');
		var thr2		= $('<th width="10%" style="text-align:left;">');
		var thr3		= $('<th width="10%" style="text-align:left;">');
		var thr4		= $('<th width="8%" style="text-align:left;">');
		var thr5		= $('<th width="10%">');
		var thr6		= $('<th width="10%">');
		var thr7		= $('<th width="10%">');
		
		thr1.append('Part Name And Number');
		thr2.append('Qty');
		if($("#pendingStock").val() == "true")
		{
			console.log("inside iff..")
			thr3.append('pending Stock');
		}
		thr4.append('Each');
		thr5.append('Disc');
		thr6.append('GST');
		thr7.append('Total');
		
		tr2.append(thr1);
		tr2.append(thr2);
		
		if($("#pendingStock").val() == "true")
		{
			console.log("inside 2nd if")
			tr2.append(thr3);
		}
		tr2.append(thr4);
		tr2.append(thr5);
		tr2.append(thr6);
		tr2.append(thr7);
		
		var partDetTbl	= $("<table width='100%'>");
		partDetTbl.append(tr2);
		th6.append(partDetTbl);
		
		var k=0;
		var invoiceAmount = 0.0;
		var totalQuantity = 0;
		var total = 0.0;
		
		for(var key in partPurchaseInvoiceReportFinal) {
			//if(partPurchaseInvoiceReportFinal[partPurchaseInvoiceReport[i]] != undefined){
				
				
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				var td7		= $('<td>');
				
				
				td1.append(k+1);
				k++;
				td2.append('<a href="showInvoice.in?Id='+partPurchaseInvoiceReportFinal[key].partInvoiceId+'" target="_blank" >'+partPurchaseInvoiceReportFinal[key].partInvoiceNumber+'</a>');
				if (selectedVendorId <= 0) {
		        td7.append(partPurchaseInvoiceReportFinal[key].vendor_name);
		          }
				td3.append(partPurchaseInvoiceReportFinal[key].invoice_number);
				td4.append(partPurchaseInvoiceReportFinal[key].invoice_date);
				td5.append(partPurchaseInvoiceReportFinal[key].invoice_amount);
				
				
				var x = Number(partPurchaseInvoiceReportFinal[key].invoice_amount);
				invoiceAmount = invoiceAmount + x;
				
				
				tr.append(td1);
				tr.append(td2);
				if (selectedVendorId <= 0) {
				tr.append(td7);
				}
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				
				
				
				var partNameAndNumberData = partPurchaseInvoiceReportFinal[key].partNameAndNumberData;
				
				var innertable 		= $('<table border="1" width="100%">');
				
			var partdData =	data.partDetailsMap[partPurchaseInvoiceReportFinal[key].partInvoiceId];
			
			for(var t = 0; t < partdData.length ; t++){
				var innertr 		= $('<tr>');
				
				var innertd1		= $('<td width="40%" style="text-align:left;">');
				var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
				var innertd3		= $('<td width="10%" colspan="3" style="text-align:left;">');
				var innertd4		= $('<td width="10%" colspan="3" style="text-align:left;">');
				var innertd5		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
				var innertd6		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
				var innertd7		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
				
				innertd1.append(partdData[t].partname);
				innertd2.append(partdData[t].history_quantity);
				if($("#pendingStock").val() == "true")
				{
					innertd3.append(partdData[t].quantity);
				}
				innertd4.append(partdData[t].unitprice);
				
				if(partdData[t].discountTaxTypeId == 1){
					innertd5.append(partdData[t].discount+'%');
					innertd6.append(partdData[t].tax+'%');
				} else {
					innertd5.append(partdData[t].discount);
					innertd6.append(partdData[t].tax);
				}
				
				innertd7.append(partdData[t].total);
				
				totalQuantity += partdData[t].history_quantity;
				total		  += partdData[t].total;
				
				innertr.append(innertd1);
				innertr.append(innertd2);
				if($("#pendingStock").val() == "true")
				{
					innertr.append(innertd3);
				}
				innertr.append(innertd4);
				innertr.append(innertd5);
				innertr.append(innertd6);
				innertr.append(innertd7);
				innertable.append(innertr);
			}
				
				/*for(var t = 0 ; t < partNameAndNumberData.length; t++){
					
					var innertr 		= $('<tr>');
					
					var innertd1		= $('<td width="50%" style="text-align:left;">');
					var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd3		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd4		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd5		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd6		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					
					innertd1.append(partNameAndNumberData[t].partnumber +"-"+ partNameAndNumberData[t].partname);
					innertd2.append(partNameAndNumberData[t].history_quantity);
					innertd3.append(partNameAndNumberData[t].unitprice);
					innertd4.append(partNameAndNumberData[t].discount);
					innertd5.append(partNameAndNumberData[t].tax);
					innertd6.append(partNameAndNumberData[t].total);
					
					totalQuantity += partNameAndNumberData[t].history_quantity;
					total		  += partNameAndNumberData[t].total;
					
					innertr.append(innertd1);
					innertr.append(innertd2);
					innertr.append(innertd3);
					innertr.append(innertd4);
					innertr.append(innertd5);
					innertr.append(innertd6);
					
					innertable.append(innertr);
				}*/
				
				
				td6.append(innertable);
			
				
				tbody.append(tr);
			//}
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


//Below function For SlickGrid.
function showInvoiceNumber(gridObj, dataView, row){
	console.log("hi",dataView);
	console.log("ddg",gridObj);

	var win = window.open('showInvoice.in?Id='+dataView.partInvoiceId, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}

 }


