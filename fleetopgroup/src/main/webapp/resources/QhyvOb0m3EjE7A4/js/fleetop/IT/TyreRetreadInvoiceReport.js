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
	$("#tyreModelId").select2({
		 minimumInputLength:2, 
		 minimumResultsForSearch:10, 
		 ajax: {
	            url:"getSearchTyreSubModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a
	                }
	            }
	            , results:function(a) {
	                return {
	                    results:$.map(a, function(a) {
	                        return {
	                            text: a.TYRE_MODEL_SUBTYPE, slug: a.slug, id: a.TYRE_MST_ID
	                        }
	                    }
	                    )
	                }
	            }
	        }
    }),  $("#tyreSizeId").select2({
        minimumInputLength:2, 
        minimumResultsForSearch:10, 
        ajax: {
            url:"getSearchTyreSize.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_SIZE, slug: a.slug, id: a.TS_ID
                        }
                    }
                    )
                }
            }
        }
    }), $("#warehouselocationId").select2({
        minimumInputLength:2, 
        minimumResultsForSearch:10, 
        ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }), $("#TyrePurchaseVendor").select2({
        minimumInputLength:3, 
        minimumResultsForSearch:10, 
        ajax: {
            url:"getTyreVendorSearchList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
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

				var jsonObject			= new Object();

				jsonObject["tyreModelId"] 				=  $('#tyreModelId').val();
				jsonObject["tyreSizeId"] 				=  $('#tyreSizeId').val();
				jsonObject["warehouselocationId"] 		=  $('#warehouselocationId').val();
				jsonObject["TyrePurchaseVendorId"] 		=  $('#TyrePurchaseVendor').val();
				jsonObject["dateRange"] 	  			=  $('#TripCollectionExpenseRange').val();
				
				
				if($('#TripCollectionExpenseRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "TyreRetreadWS/getTyreRetreadInvoiceReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setTyreRetreadInvoiceReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

		});


function setTyreRetreadInvoiceReport(data) {
	
	$('#invoicePurchase').show();
	$('#sizId').html(data.TyreSize);
	$('#modId').html(data.Model);
	$('#venId').html(data.VendorName);
	$('#locId').html(data.Location);
	$('#dateRange').html(data.dateRange);
	
	
	var RetreadInvoiceReport	= null;
	if(data.RetreadInvoiceReport != undefined) {
		
		$("#reportHeader").html("Tyre Retread Invoice Report");

		$("#tripCollExpenseName").empty();
		RetreadInvoiceReport	= data.RetreadInvoiceReport;
		
		var RetreadInvoiceReportFinal	= new Object();
		for(var i = 0; i < RetreadInvoiceReport.length; i++ ) {
			if(RetreadInvoiceReportFinal[RetreadInvoiceReport[i].trid] == undefined){
				var RetreadInvoiceReportObj = new Object();
				var RetreadInvoiceReportArr 		 = new Array();
				RetreadInvoiceReportObj.RetreadInvoiceReportData = RetreadInvoiceReportArr;
				RetreadInvoiceReportObj.trid = RetreadInvoiceReport[i].trid;
				RetreadInvoiceReportObj.retreadInvoiceNumber = RetreadInvoiceReport[i].trnumber;
				RetreadInvoiceReportObj.invoiceNumber = RetreadInvoiceReport[i].tr_INVOICE_NUMBER;    
				RetreadInvoiceReportObj.invDate = RetreadInvoiceReport[i].tr_INVOICE_DATE_STR;      
				RetreadInvoiceReportObj.invoiceAmount = RetreadInvoiceReport[i].tr_AMOUNT;      
				
				var RetreadNameAndNumberObj = new Object();
				RetreadNameAndNumberObj.tyreTypesId = RetreadInvoiceReport[i].tyre_ID; 
				RetreadNameAndNumberObj.tyreTypesName = RetreadInvoiceReport[i].tyre_NUMBER; 
				RetreadNameAndNumberObj.statusId = RetreadInvoiceReport[i].tra_STATUS_ID;
				RetreadNameAndNumberObj.status = RetreadInvoiceReport[i].tra_STATUS;
				RetreadNameAndNumberObj.unitprice = RetreadInvoiceReport[i].retread_COST;
				RetreadNameAndNumberObj.discount = RetreadInvoiceReport[i].retread_DISCOUNT; 
				RetreadNameAndNumberObj.tax = RetreadInvoiceReport[i].retread_TAX;    
				RetreadNameAndNumberObj.total = RetreadInvoiceReport[i].retread_AMOUNT;    
				RetreadInvoiceReportArr.push(RetreadNameAndNumberObj)
				RetreadInvoiceReportObj.RetreadInvoiceReportData = RetreadInvoiceReportArr;
				
				RetreadInvoiceReportFinal[RetreadInvoiceReport[i].trid] = RetreadInvoiceReportObj;
			} else {      
				RetreadInvoiceReportObj = RetreadInvoiceReportFinal[RetreadInvoiceReport[i].trid] 
				var RetreadNameAndNumberObj = new Object();
				RetreadNameAndNumberObj.tyreTypesId = RetreadInvoiceReport[i].tyre_ID;
				RetreadNameAndNumberObj.tyreTypesName = RetreadInvoiceReport[i].tyre_NUMBER;
				RetreadNameAndNumberObj.statusId = RetreadInvoiceReport[i].tra_STATUS_ID;
				RetreadNameAndNumberObj.status = RetreadInvoiceReport[i].tra_STATUS;
				RetreadNameAndNumberObj.unitprice = RetreadInvoiceReport[i].retread_COST;
				RetreadNameAndNumberObj.discount = RetreadInvoiceReport[i].retread_DISCOUNT; 
				RetreadNameAndNumberObj.tax = RetreadInvoiceReport[i].retread_TAX;    
				RetreadNameAndNumberObj.total = RetreadInvoiceReport[i].retread_AMOUNT;    
				RetreadInvoiceReportArr.push(RetreadNameAndNumberObj)
				RetreadInvoiceReportObj.RetreadInvoiceReportData = RetreadInvoiceReportArr;
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
		th2.append('Retread Invoice Number');
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
		
		thr1.append('Retread Tyre');
		thr2.append('Status');
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
		
		for(var key in RetreadInvoiceReportFinal) {
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				
				
				td1.append(k+1);
				k++;
				td2.append('<a href="ShowRetreadTyre.in?RID='+RetreadInvoiceReportFinal[key].trid+'" target="_blank" >'+'TR-'+RetreadInvoiceReportFinal[key].retreadInvoiceNumber+'</a>');
				td3.append(RetreadInvoiceReportFinal[key].invoiceNumber);
				td4.append(RetreadInvoiceReportFinal[key].invDate);
				td5.append(RetreadInvoiceReportFinal[key].invoiceAmount.toFixed(2));
				
				var x = Number(RetreadInvoiceReportFinal[key].invoiceAmount);
				invoiceAmount = invoiceAmount + x;
				
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				
				var RetreadInvoiceReportData = RetreadInvoiceReportFinal[key].RetreadInvoiceReportData;
				
				var innertable 		= $('<table width="100%">');
				for(var t = 0 ; t < RetreadInvoiceReportData.length; t++){
					
					var innertr 		= $('<tr>');
					
					var innertd1		= $('<td width="50%" style="text-align:left;">');
					var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd3		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd4		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd5		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd6		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					
					innertd1.append('<a href="showTyreInfo.in?Id='+RetreadInvoiceReportData[t].tyreTypesId+'" target="_blank" >'+RetreadInvoiceReportData[t].tyreTypesName+'</a>');
					if(RetreadInvoiceReportData[t].statusId == 2){
						innertd2.append('<span class="label label-pill label-success">'+RetreadInvoiceReportData[t].status+'</span>');
					} else {
						innertd2.append('<span class="label label-pill label-danger">'+RetreadInvoiceReportData[t].status+'</span>');
					}
					innertd3.append((RetreadInvoiceReportData[t].unitprice).toFixed(2));
					innertd4.append((RetreadInvoiceReportData[t].discount).toFixed(2));
					innertd5.append((RetreadInvoiceReportData[t].tax).toFixed(2));
					innertd6.append((RetreadInvoiceReportData[t].total).toFixed(2));
					
					totalQuantity += RetreadInvoiceReportData[t].quantity;
					total		  += RetreadInvoiceReportData[t].total;
					
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
		num = addCommas(invoiceAmount.toFixed(2));
		tdtotal2.append(num);

		tr3.append(tdtotal1);
		tr3.append(tdtotal2);

		var totaltable 		= $('<table width="100%">');
		var totaltr 		= $('<tr>');
		
		var td1		= $('<td width="50%">');
		//var td2		= $('<td width="10%" align="left">');
		var td3		= $('<td width="10%">');
		var td4		= $('<td width="10%">'); 
		var td5		= $('<td width="10%">');
		var td6		= $('<td width="10%" align="left">');
		
		//td2.append(totalQuantity);
		num1 = addCommas(total.toFixed(2));
		td6.append(num1);
		
		totaltr.append(td1);
		//totaltr.append(td2);
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


function addCommas(nStr) {
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}



