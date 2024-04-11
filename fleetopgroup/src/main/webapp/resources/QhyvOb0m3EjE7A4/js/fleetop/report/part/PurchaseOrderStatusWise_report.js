var data = [];
var gridObject;
var slickGridWrapper3;

$(function() {
	function a(a, b) {
		$("#PoRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#PoRange").daterangepicker( {
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
	
	$("#PartVendorList").select2( {
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

				jsonObject["dateRange"] 	  	=  $('#PoRange').val();
				jsonObject["status"]         	=  $('#statusId').val();
				jsonObject["vendorName"] 		= $('#PartVendorList').val();
				
				
				$.ajax({
					
					url: "getPurchaseOrderStatusWiseReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setPurchaseOrderReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});


function setPurchaseOrderReport(data) {
	
	$('#invoicePurchase').show();
	$('#dateRange').html(data.dateRange);
	$('#PoStatus').html(data.status);
	$('#vendor').html(data.VendorName);
	
	var purchase_Order_Report	= null;
	if(data.PurchaseOrderReport != undefined) {
		
		$("#reportHeader").html("Purchase Order Status Wise Report");

		$("#PurchaseOrderTable").empty();
		purchase_Order_Report	= data.PurchaseOrderReport;
		
		var purchase_Order_ReportFinal	= new Object();

		for(var i = 0; i < purchase_Order_Report.length; i++ ) {
			if(purchase_Order_ReportFinal[purchase_Order_Report[i].purchaseOrder_id] == undefined){
				
				var purchaseOrderReportObj = new Object();
				var partNameAndNumberArr 		 = new Array();
				
				purchaseOrderReportObj.partNameAndNumberData = partNameAndNumberArr;
				purchaseOrderReportObj.purchaseOrder_id = purchase_Order_Report[i].purchaseOrder_id;
				purchaseOrderReportObj.purchaseorder_Number = purchase_Order_Report[i].purchaseorder_Number;
				purchaseOrderReportObj.purchaseorder_invoiceno = purchase_Order_Report[i].purchaseorder_invoiceno;    
				purchaseOrderReportObj.purchaseorder_invoice_date = purchase_Order_Report[i].purchaseorder_invoice_date;      
				purchaseOrderReportObj.purchaseorder_vendor_name = purchase_Order_Report[i].purchaseorder_vendor_name;     
				purchaseOrderReportObj.date_opended = purchase_Order_Report[i].date_opended;
				purchaseOrderReportObj.data_required = purchase_Order_Report[i].data_required;
				purchaseOrderReportObj.purchaseorder_indentno = purchase_Order_Report[i].purchaseorder_indentno;
				purchaseOrderReportObj.partialReceived = purchase_Order_Report[i].partialReceived
		
				
				var partNameAndNumberObj = new Object();
				partNameAndNumberObj.purchaseorder_partnumber = purchase_Order_Report[i].purchaseorder_partnumber; 
				partNameAndNumberObj.purchaseorder_partname = purchase_Order_Report[i].purchaseorder_partname; 
				partNameAndNumberObj.quantity = purchase_Order_Report[i].quantity; 
				partNameAndNumberObj.received_quantity = purchase_Order_Report[i].received_quantity; 
				partNameAndNumberObj.parteachcost = purchase_Order_Report[i].parteachcost;
				partNameAndNumberObj.discount = purchase_Order_Report[i].discount; 
				partNameAndNumberObj.tax = purchase_Order_Report[i].tax;    
				partNameAndNumberObj.totalcost = purchase_Order_Report[i].totalcost;  
				partNameAndNumberObj.purchaseorder_status = purchase_Order_Report[i].purchaseorder_status;  
				partNameAndNumberObj.PurchaseOrderTypeId = purchase_Order_Report[i].PurchaseOrderTypeId;
				
				
				partNameAndNumberArr.push(partNameAndNumberObj);
				purchaseOrderReportObj.partNameAndNumberData = partNameAndNumberArr;
				
				purchase_Order_ReportFinal[purchase_Order_Report[i].purchaseOrder_id] = purchaseOrderReportObj;
			} else {      
				purchaseOrderReportObj = purchase_Order_ReportFinal[purchase_Order_Report[i].purchaseOrder_id] 
				var partNameAndNumberObj = new Object();
				partNameAndNumberObj.purchaseorder_partnumber = purchase_Order_Report[i].purchaseorder_partnumber; 
				partNameAndNumberObj.purchaseorder_partname = purchase_Order_Report[i].purchaseorder_partname; 
				partNameAndNumberObj.quantity = purchase_Order_Report[i].quantity; 
				partNameAndNumberObj.received_quantity = purchase_Order_Report[i].received_quantity; 
				partNameAndNumberObj.parteachcost = purchase_Order_Report[i].parteachcost;
				partNameAndNumberObj.discount = purchase_Order_Report[i].discount; 
				partNameAndNumberObj.tax = purchase_Order_Report[i].tax;    
				partNameAndNumberObj.totalcost = purchase_Order_Report[i].totalcost;  
				partNameAndNumberObj.purchaseorder_status = purchase_Order_Report[i].purchaseorder_status; 
				  
				partNameAndNumberArr.push(partNameAndNumberObj);
				purchaseOrderReportObj.partNameAndNumberData = partNameAndNumberArr;
			}
		}
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th width="3%">');
		var th2		= $('<th width="3%">');
		var th3		= $('<th width="18%">');
		var th4		= $('<th width="6%">');
		var th5		= $('<th width="6%">');
		var th6     = $('<th width="5%">');
		var th7		= $('<th width="6%">');
		var th8		= $('<th width="3%">');
		var th9     = $('<th width="130%">');
		
		
		th1.append('Sr No');
		th2.append('Po NO.');
		th3.append('Vendor Name');
		th4.append('Date Opened');
		th5.append('Date Required');
		th6.append('Invoice No.');
		th7.append('Invoice Date');
		th8.append('Wo/IndNo');

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		

		thead.append(tr1);
		
		var tbody1 = $('<tbody>');
		var tr2 = $('<tr style="font-weight: bold; font-size : 12px;">');
		
		var thr1		= $('<th width="38%;" style="text-align:center;">');
		var thr2		= $('<th width="7%" style="text-align:center;">');
		var thr3 		= $('<th width="7%" style="text-align:center;">')
		var thr4		= $('<th width="7%">');
		var thr5		= $('<th width="7%">');
		var thr6		= $('<th width="7%">');
		var thr7		= $('<th width="8%">');
		var thr8		= $('<th width="8%">');
		var thr9 		= $('<th width="15%" style="text-align:center;">');
		
		
		thr1.append('Part Name And Number');
		thr2.append('Qt');
		thr3.append('RQt');
		thr4.append('Each');
		thr5.append('DIS');
		thr6.append('GST');
		thr7.append('Total');
		thr8.append('ReQtTotal');
		thr9.append('Status');
		
		tr2.append(thr1);
		tr2.append(thr2);
		tr2.append(thr3);
		tr2.append(thr4);
		tr2.append(thr5);
		tr2.append(thr6);
		tr2.append(thr7);
		tr2.append(thr8);
		tr2.append(thr9);
		var purchaseOrderDetTbl	= $("<table width='100%'>");
		purchaseOrderDetTbl.append(tr2);
		th9.append(purchaseOrderDetTbl);
		
		var k=0;
		var totalQuantity = 0;
		var totalRecQty = 0.0;
		var total = 0.0;
		var ReQtTotal = 0.0;
		
		for(var key in purchase_Order_ReportFinal) {
				
				
				var tr = $('<tr>');
				var partialReceived=1;
				
				console.log(purchase_Order_ReportFinal[key].partialReceived);
				
				if(purchase_Order_ReportFinal[key].partialReceived == partialReceived)
				{
					var td1		= $('<td>');
					var td2		= $('<td>');
					var td3		= $('<td>');
					var td4		= $('<td>'); 
					var td5		= $('<td>');
					var td6		= $('<td>');
					var td7		= $('<td>');
					var td8 	= $('<td>');
					var td9 	= $('<td>');
				}
				else
				{	
					var td1		= $('<td style="background-color:  #d5dbdb  ;">');
					var td2		= $('<td style="background-color:  #d5dbdb ;">'); //#5dade2
					var td3		= $('<td style="background-color:  #d5dbdb ;">');
					var td4		= $('<td style="background-color:  #d5dbdb ;">'); 
					var td5		= $('<td style="background-color:  #d5dbdb ;">');
					var td6		= $('<td style="background-color:  #d5dbdb ;">');
					var td7		= $('<td style="background-color:  #d5dbdb ;">');
					var td8 	= $('<td style="background-color:  #d5dbdb ;">');
					var td9 	= $('<td>');
				}
				
				td1.append(k+1);
				k++;
				td2.append('<a href="PurchaseOrders_Parts.in?ID='+purchase_Order_ReportFinal[key].purchaseOrder_id+'" target="_blank" >PO-'+purchase_Order_ReportFinal[key].purchaseorder_Number+'</a>');
				td3.append(purchase_Order_ReportFinal[key].purchaseorder_vendor_name);
				td4.append(purchase_Order_ReportFinal[key].date_opended);
				td5.append(purchase_Order_ReportFinal[key].data_required);
				td6.append(purchase_Order_ReportFinal[key].purchaseorder_invoiceno);
				td7.append(purchase_Order_ReportFinal[key].purchaseorder_invoice_date);
				td8.append(purchase_Order_ReportFinal[key].purchaseorder_indentno);
			
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				tr.append(td7);
				tr.append(td8);
				tr.append(td9);
				
				
				
				var partNameAndNumberData = purchase_Order_ReportFinal[key].partNameAndNumberData;
				
				var innertable 		= $('<table border="1" width="100%">');
				
				var partdData =	data.PurchaseOrderMap[purchase_Order_ReportFinal[key].purchaseOrder_id];
			
			for(var t = 0; t < partdData.length ; t++){
				
				var innertr 		= $('<tr>');
				
				
				if(partdData[t].quantity==partdData[t].received_quantity)
				{
					var innertd1		= $('<td width="38%;" style="text-align:center;">');
					var innertd2		= $('<td width="7%" colspan="3" style="text-align:center;">');
					var innertd3		= $('<td width="7%" colspan="3" style="text-align:center;">');
					var innertd4		= $('<td width="7%" colspan="3" style="text-align:center;">'); 
					var innertd5		= $('<td width="7%" colspan="3" style="text-align:center;">'); 
					var innertd6		= $('<td width="7%" colspan="3" style="text-align:center;">'); 
					var innertd7		= $('<td width="8%" colspan="3" style="text-align:center;">');
					var innertd8		= $('<td width="8%" colspan="3" style="text-align:center;">');
					var innertd9 		= $('<td width="15%" colspan="3" style="text-align:center;">');
				}
				else
				{  	
					var innertd1		= $('<td width="38%;" style="text-align:center;background-color: #d5dbdb ;">');
					var innertd2		= $('<td width="7%" colspan="3" style="text-align:center;background-color:  #d5dbdb  ;">');//#abebc6 #5dade2
					var innertd3		= $('<td width="7%" colspan="3" style="text-align:center;background-color:  #d5dbdb ;">'); //#797d7f
					var innertd4		= $('<td width="7%" colspan="3" style="text-align:center;background-color:  #d5dbdb ">'); 
					var innertd5		= $('<td width="7%" colspan="3" style="text-align:center;background-color:  #d5dbdb ">'); 
					var innertd6		= $('<td width="7%" colspan="3" style="text-align:center;background-color:  #d5dbdb ">'); 
					var innertd7		= $('<td width="8%" colspan="3" style="text-align:center;background-color:  #d5dbdb ">');
					var innertd8		= $('<td width="8%" colspan="3" style="text-align:center;background-color:  #d5dbdb ">');
					var innertd9 		= $('<td width="15%" colspan="3" style="text-align:center;background-color: #d5dbdb ">');
				}
				
				innertd1.append(partdData[t].purchaseorder_partname);
				innertd2.append(partdData[t].quantity);
				innertd3.append(partdData[t].received_quantity);
				innertd4.append(partdData[t].parteachcost);
				innertd5.append(partdData[t].discount);
				innertd6.append(partdData[t].tax);
				innertd7.append(partdData[t].totalcost);
				
				
				var subCost 		=  partdData[t].received_quantity * partdData[t].parteachcost;
				var gst				=  partdData[t].tax/100 * subCost;
				var costWithGst		=  subCost+gst;
				var dis 			=  partdData[t].discount/100  * costWithGst;
				var finalCost		=  costWithGst - dis;
				
				ReQtTotal			    += finalCost;
				
				innertd8.append(finalCost.toFixed(2));
				innertd9.append(partdData[t].purchaseOrder_StatusName);
				
				totalQuantity += partdData[t].quantity;
				totalRecQty   += partdData[t].received_quantity;
				total		  += partdData[t].totalcost;
				
								
				innertr.append(innertd1);
				innertr.append(innertd2);
				innertr.append(innertd3);
				innertr.append(innertd4);
				innertr.append(innertd5);
				innertr.append(innertd6);
				innertr.append(innertd7);
				innertr.append(innertd8);
				innertr.append(innertd9);
				
				innertable.append(innertr);
			}
				td9.append(innertable);
				
				tbody.append(tr);
		}
		
		var tr3 = $('<tr>');

		var tdtotal1		= $('<td colspan="8">');
		var tdtotal2		= $('<td>');
		

		tdtotal1.append("Total :");
		
		tr3.append(tdtotal1);
		
		var totaltable 		= $('<table border="1" width="100%">');
		var totaltr 		= $('<tr>');

		var td1		= $('<td width="38%">');
		var td2		= $('<td width="7%" text-align:"center">');
		var td3		= $('<td width="7%" text-align:"center">');
		var td4		= $('<td width="7%">'); 
		var td5		= $('<td width="7%">');
		var td6		= $('<td width="7%" text-align:"center">');
		var td7 	= $('<td width="8%" text-align:"center">');
		var td8 	= $('<td width="8%" text-align:"center">');
		var td9 	= $('<td width="15%">');
		
		
		td2.append(totalQuantity.toFixed(2));
		td3.append(totalRecQty.toFixed(2));
		td7.append(total.toFixed(2));
		td8.append(ReQtTotal.toFixed(2));
		
		totaltr.append(td1);
		totaltr.append(td2);
		totaltr.append(td3);
		totaltr.append(td4);
		totaltr.append(td5);
		totaltr.append(td6);
		totaltr.append(td7);
		totaltr.append(td8);
		totaltr.append(td9);
		
		totaltable.append(totaltr);
		tdtotal2.append(totaltable);
		
		tr3.append(tdtotal2);
		
		tbody.append(tr3);
		
		$("#PurchaseOrderTable").append(thead);
		$("#PurchaseOrderTable").append(tbody);
		
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


