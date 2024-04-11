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
	$("#batterryModelTypeId").select2();
	$("#batteryManufacurer").select2( {
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
    }), $("#batteryManufacurer").change(function() {
        $.getJSON("getBatteryType.in", {
            ModelType: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].batteryTypeId + '">' + a[d].batteryType + "</option>";
            b += "</option>", $("#batterryModelTypeId").html(b)
        })
    }), $("#selectVendor").select2( {
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
    })
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["batteryManufacurer"] 	=  $('#batteryManufacurer').val();
				jsonObject["batterryModelTypeId"] 	=  $('#batterryModelTypeId').val();
				jsonObject["selectVendor"] 			=  $('#selectVendor').val();
				jsonObject["dateRange"] 	  		=  $('#TripCollectionExpenseRange').val();
				
				console.log("jsonObject..",jsonObject);
				
				if($('#TripCollectionExpenseRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "getBatteryPurchaseInvoiceReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setBatteryPurchaseInvoiceReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

		});


function setBatteryPurchaseInvoiceReport(data) {
	
	$('#invoicePurchase').show();
	$('#manufacturerId').html(data.BatteryManufacturer);
	$('#modelId').html(data.BatteryModel);
	$('#vendor').html(data.BatteryVendor);
	$('#dateRange').html(data.dateRange);
	
	
	var batteryPurchaseInvoiceReport	= null;
	if(data.BatteryPurchaseInvoiceReport != undefined) {
		
		$("#reportHeader").html("Battery Purchase Invoice Report");

		$("#tripCollExpenseName").empty();
		batteryPurchaseInvoiceReport	= data.BatteryPurchaseInvoiceReport;
		
		var batteryPurchaseInvoiceReportFinal	= new Object();
		for(var i = 0; i < batteryPurchaseInvoiceReport.length; i++ ) {
			if(batteryPurchaseInvoiceReportFinal[batteryPurchaseInvoiceReport[i].batteryInvoiceId] == undefined){
				var batteryPurchaseInvoiceReportObj = new Object();
				var batteryNameAndNumberArr 		 = new Array();
				batteryPurchaseInvoiceReportObj.batteryNameAndNumberData = batteryNameAndNumberArr;
				batteryPurchaseInvoiceReportObj.batteryInvoiceId = batteryPurchaseInvoiceReport[i].batteryInvoiceId;
				batteryPurchaseInvoiceReportObj.batteryInvoiceNumber = batteryPurchaseInvoiceReport[i].batteryInvoiceNumber;
				batteryPurchaseInvoiceReportObj.invoiceNumber = batteryPurchaseInvoiceReport[i].invoiceNumber;    
				batteryPurchaseInvoiceReportObj.invoiceDate = batteryPurchaseInvoiceReport[i].invoiceDate;      
				batteryPurchaseInvoiceReportObj.invoiceAmount = batteryPurchaseInvoiceReport[i].invoiceAmount;      
				
				var batteryNameAndNumberObj = new Object();
				batteryNameAndNumberObj.manufacturerName = batteryPurchaseInvoiceReport[i].manufacturerName; 
				batteryNameAndNumberObj.batteryType = batteryPurchaseInvoiceReport[i].batteryType; 
				batteryNameAndNumberObj.discountTaxTypeId = batteryPurchaseInvoiceReport[i].discountTaxTypeId; 
				batteryNameAndNumberObj.batteryQuantity = batteryPurchaseInvoiceReport[i].batteryQuantity; 
				batteryNameAndNumberObj.unitCost = batteryPurchaseInvoiceReport[i].unitCost;
				batteryNameAndNumberObj.discount = batteryPurchaseInvoiceReport[i].discount; 
				batteryNameAndNumberObj.tax = batteryPurchaseInvoiceReport[i].tax;    
				batteryNameAndNumberObj.totalAmount = batteryPurchaseInvoiceReport[i].totalAmount;    
				batteryNameAndNumberArr.push(batteryNameAndNumberObj)
				batteryPurchaseInvoiceReportObj.batteryNameAndNumberData = batteryNameAndNumberArr;
				
				batteryPurchaseInvoiceReportFinal[batteryPurchaseInvoiceReport[i].batteryInvoiceId] = batteryPurchaseInvoiceReportObj;
			} else {      
				batteryPurchaseInvoiceReportObj = batteryPurchaseInvoiceReportFinal[batteryPurchaseInvoiceReport[i].batteryInvoiceId] 
				var batteryNameAndNumberObj = new Object();
				batteryNameAndNumberObj.manufacturerName = batteryPurchaseInvoiceReport[i].manufacturerName; 
				batteryNameAndNumberObj.batteryType = batteryPurchaseInvoiceReport[i].batteryType;
				batteryNameAndNumberObj.discountTaxTypeId = batteryPurchaseInvoiceReport[i].discountTaxTypeId;
				batteryNameAndNumberObj.batteryQuantity = batteryPurchaseInvoiceReport[i].batteryQuantity; 
				batteryNameAndNumberObj.unitCost = batteryPurchaseInvoiceReport[i].unitCost;
				batteryNameAndNumberObj.discount = batteryPurchaseInvoiceReport[i].discount; 
				batteryNameAndNumberObj.tax = batteryPurchaseInvoiceReport[i].tax;    
				batteryNameAndNumberObj.totalAmount = batteryPurchaseInvoiceReport[i].totalAmount;    
				batteryNameAndNumberArr.push(batteryNameAndNumberObj)
				batteryPurchaseInvoiceReportObj.batteryNameAndNumberData = batteryNameAndNumberArr;
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
		th2.append('Battery Invoice Number');
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
		
		thr1.append('Battery Name And Number');
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
		
		for(var key in batteryPurchaseInvoiceReportFinal) {
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				
				
				td1.append(k+1);
				k++;
				td2.append('<a href="showBatteryInvoice.in?Id='+batteryPurchaseInvoiceReportFinal[key].batteryInvoiceId+'" target="_blank" >'+batteryPurchaseInvoiceReportFinal[key].batteryInvoiceNumber+'</a>');
				td3.append(batteryPurchaseInvoiceReportFinal[key].invoiceNumber);
				td4.append(batteryPurchaseInvoiceReportFinal[key].invoiceDate);
				td5.append(batteryPurchaseInvoiceReportFinal[key].invoiceAmount);
				
				var x = Number(batteryPurchaseInvoiceReportFinal[key].invoiceAmount);
				invoiceAmount = invoiceAmount + x;
				
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				
				var batteryNameAndNumberData = batteryPurchaseInvoiceReportFinal[key].batteryNameAndNumberData;
				
				var innertable 		= $('<table width="100%">');
				for(var t = 0 ; t < batteryNameAndNumberData.length; t++){
					
					var innertr 		= $('<tr>');
					
					var innertd1		= $('<td width="50%" style="text-align:left;">');
					var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd3		= $('<td width="10%" colspan="3" style="text-align:left;">');
					var innertd4		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd5		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					var innertd6		= $('<td width="10%" colspan="3" style="text-align:left;">'); 
					
					innertd1.append(batteryNameAndNumberData[t].manufacturerName +"-"+ batteryNameAndNumberData[t].batteryType);
					innertd2.append(batteryNameAndNumberData[t].batteryQuantity);
					innertd3.append(batteryNameAndNumberData[t].unitCost);
					
					if(batteryNameAndNumberData[t].discountTaxTypeId == 1){
						innertd4.append(batteryNameAndNumberData[t].discount+'%');
						innertd5.append(batteryNameAndNumberData[t].tax+'%');
					} else {
						innertd4.append(batteryNameAndNumberData[t].discount);
						innertd5.append(batteryNameAndNumberData[t].tax);
					}
					
					innertd6.append(batteryNameAndNumberData[t].totalAmount);
					
					totalQuantity += batteryNameAndNumberData[t].batteryQuantity;
					total		  += batteryNameAndNumberData[t].totalAmount;
					
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
		
		td2.append(totalQuantity);
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

