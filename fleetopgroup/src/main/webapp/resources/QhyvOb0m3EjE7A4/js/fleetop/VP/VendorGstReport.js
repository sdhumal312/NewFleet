$(document).ready(function() {
   $("#VehicleGroup").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    } 
    ),$(function() {
        function a(a, b) {
            $("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
        }
        a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
            format : 'DD-MM-YYYY',
            ranges: {
                Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
            }
        }
        , a)
    }

    ),$("#btn-search").click(function(event) {
		
		var jsonObject					= new Object();
		jsonObject["VehicleGroup"]		= $("#VehicleGroup").val();
		jsonObject["dateRange"]			= $("#dateRange").val();
		
		if(Number($('#VehicleGroup').val()) <= 0){
			showMessage('info', 'Please Select Vehicle!');
			return false;
		}
		showLayer();
				
		$.ajax({
			url: "vendorPaymentWS/getVendorGstReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				setVendorGstReport(data);
			},
			error: function (e) {
				hideLayer();
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
    })
});





function setVendorGstReport(data) {
	console.log('data',data)
	var vendorGstReport= null;
	if(data.VendorGstReport != undefined) {
		$("#reportHeader").html("Vendor GST Report");

		$("#advanceTable").empty();
		vendorGstReport	= data.VendorGstReport;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');		

		th1.append('Sr No');
		th2.append('Vendor 	Name');
		th3.append('GST Number');
		th4.append('Invoice Number');
		th5.append('Invoice Date');
		th6.append('Transaction Amount');
		th7.append('Gst  Amount');
		th8.append('Total Amount');		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);

		thead.append(tr1);
		
		var transactionAmountTotal=0;
		var gstAmountTotal=0;
		var totalAmountFinal=0;
		for(var i = 0; i < vendorGstReport.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');		

			td1.append(i+1);			
			td2.append(vendorGstReport[i].vendorName);
			td3.append(vendorGstReport[i].gstNumber);	
			td4.append(vendorGstReport[i].invoiceNumber);
			td5.append(vendorGstReport[i].invoiceDateStr);
			td6.append(vendorGstReport[i].transactionAmount);			
			td7.append(vendorGstReport[i].gstAmount);
			td8.append(vendorGstReport[i].totalAmount);	

			transactionAmountTotal += vendorGstReport[i].transactionAmount;
			gstAmountTotal +=vendorGstReport[i].gstAmount;
			totalAmountFinal +=vendorGstReport[i].totalAmount;
			
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);

			tbody.append(tr);
		}
				
		
		var tr2 = $('<tr>');
		var td1		= $('<td colspan="5">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4     = $('<td>');

		td1.append("Total :");
		td2.append(transactionAmountTotal);		
		td3.append(gstAmountTotal);
		td4.append(totalAmountFinal);

		tr2.append(td1);
		tr2.append(td2);		
		tr2.append(td3);
		tr2.append(td4);
		
		
		tbody.append(tr2);	
		
		
		$("#advanceTable").append(thead);
		$("#advanceTable").append(tbody);		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	setTimeout(function(){ hideLayer(); }, 500);
}