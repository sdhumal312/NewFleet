var data = [];
var gridObject;
var slickGridWrapper3;

//Date Range Field Drop Down
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
	//Vendor Field Drop Down
	$("#selectVendor").select2( {
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
	}), 
	//Vehicle Name Field Drop Down
	$("#ReportSelectVehicle").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
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

				jsonObject["vendorId"] 					=  $('#selectVendor').val();
				jsonObject["vehicleId"] 				=  $('#ReportSelectVehicle').val();				
				jsonObject["dateRange"] 	  			=  $('#TripCollectionExpenseRange').val();
				
				if(Number($('#selectVendor').val()) <= 0){
					showMessage('info','Please Select Vendor !');
					return false;
				}
				if($('#TripCollectionExpenseRange').val() == null || $('#TripCollectionExpenseRange').val() == ''){					
					showMessage('info', 'Please Select Date Range!');
					hideLayer();
					return false;
				}
				
				showLayer();
				$.ajax({
					
					url: "getVendorLorryHireDetailsReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setVendorLorryHireDetailsReport(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

		});

function setVendorLorryHireDetailsReport(data) {
	
	$('#invoicePurchase').show();
	$('#vendorIdentity').html(data.VendorName);  
	$('#vehicleIdentity').html(data.VehicleName);
	$('#dateRange').html(data.dateRange);
	
	
	var LorryHireInvoiceReport	= null;
	if(data.vendorLorryHireDetailsList != undefined) {
		
		$("#reportHeader").html("Vendor Lorry Hire Details Report");

		$("#tripCollExpenseName").empty();
		LorryHireInvoiceReport	= data.vendorLorryHireDetailsList;
		
		var LorryReportFinal	= new Object();
		for(var i = 0; i < LorryHireInvoiceReport.length; i++ ) {
			if(LorryReportFinal[LorryHireInvoiceReport[i].lorryHireDetailsId] == undefined){
				
				var LorryReportObj = new Object();
				var LorryNameAndNumberArr 		 = new Array();
				LorryReportObj.LorryNameAndNumberData = LorryNameAndNumberArr;
				LorryReportObj.lorryHireDetailsId = LorryHireInvoiceReport[i].lorryHireDetailsId;
				LorryReportObj.lorryHireDetailsNumber = LorryHireInvoiceReport[i].lorryHireDetailsNumber;
				LorryReportObj.vid = LorryHireInvoiceReport[i].vid;
				LorryReportObj.vehicle_registration = LorryHireInvoiceReport[i].vehicle_registration;
				LorryReportObj.hireDateStr = LorryHireInvoiceReport[i].hireDateStr;    
				LorryReportObj.lorryHire = LorryHireInvoiceReport[i].lorryHire;      
				LorryReportObj.advanceAmount = LorryHireInvoiceReport[i].advanceAmount;      
				LorryReportObj.paidAmount = LorryHireInvoiceReport[i].paidAmount;      
				LorryReportObj.balanceAmount = LorryHireInvoiceReport[i].balanceAmount;      
				LorryReportObj.paymentStatus = LorryHireInvoiceReport[i].paymentStatus;      
				
				
				var LorryNameAndNumberObj = new Object();
				LorryNameAndNumberObj.expenseName = LorryHireInvoiceReport[i].expenseName; 
				LorryNameAndNumberObj.amount = LorryHireInvoiceReport[i].amount; 
				LorryNameAndNumberArr.push(LorryNameAndNumberObj)
				LorryReportObj.LorryNameAndNumberData = LorryNameAndNumberArr;
				
				LorryReportFinal[LorryHireInvoiceReport[i].lorryHireDetailsId] = LorryReportObj;
			} else {   
				
				LorryReportObj = LorryReportFinal[LorryHireInvoiceReport[i].lorryHireDetailsId] 
				var LorryNameAndNumberObj = new Object();
				LorryNameAndNumberObj.expenseName = LorryHireInvoiceReport[i].expenseName; 
				LorryNameAndNumberObj.amount = LorryHireInvoiceReport[i].amount; 
				  
				LorryNameAndNumberArr.push(LorryNameAndNumberObj)
				LorryReportObj.LorryNameAndNumberData = LorryNameAndNumberArr;
			}
		}
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th width="10%">');
		var th2		= $('<th width="10%">');
		var th3		= $('<th width="10%">');
		var th4		= $('<th width="10%">');
		var th5		= $('<th width="10%">');
		var th6		= $('<th width="10%">');
		var th7		= $('<th width="10%">');
		var th8		= $('<th width="10%">');
		var th9		= $('<th width="10%">');
		var th10	= $('<th width="30%">');

		th1.append('Sr No');
		th2.append('Number');
		th3.append('Vehicle');
		th4.append('Hire Date');
		th5.append('Lorry Hire');
		th6.append('Advance');
		th7.append('Paid Amount');
		th8.append('Balance');
		th9.append('Status');
		if(!data.configuration.hideExpensesTab)
			th10.append('Charges');

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
		if(!data.configuration.hideExpensesTab){
			
			tr1.append(th10);
			
			var tbody1 = $('<tbody>');
			var tr2 = $('<tr style="font-weight: bold; font-size : 12px;">');
			
			var thr1		= $('<th width="50%" style="text-align:left;">');
			var thr2		= $('<th width="50%" style="text-align:right;">');
			
			thr1.append('Charge Name');
			thr2.append('Amount');
			
			tr2.append(thr1);
			tr2.append(thr2);
			
			var partDetTbl	= $("<table width='100%'>");
			partDetTbl.append(tr2);
			th10.append(partDetTbl);
		}
		
		var k=0;
		
		for(var key in LorryReportFinal) {
				var tr = $('<tr>');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>'); 
				var td5		= $('<td>');
				var td6		= $('<td>');
				var td7		= $('<td>');
				var td8		= $('<td>');
				var td9		= $('<td>');
				var td10	= $('<td>');
				
				td1.append(k+1);
				k++;
				
				td2.append('<a href="ShowLorryHireDetails.in?ID='+LorryReportFinal[key].lorryHireDetailsId+'" target="_blank" >'+'LH-'+LorryReportFinal[key].lorryHireDetailsNumber+'</a>');
				td3.append('<a href="showVehicle.in?vid='+LorryReportFinal[key].vid+'" target="_blank" >'+LorryReportFinal[key].vehicle_registration+'</a>');
				td4.append(LorryReportFinal[key].hireDateStr);
				td5.append(LorryReportFinal[key].lorryHire);
				td6.append(LorryReportFinal[key].advanceAmount);
				td7.append(LorryReportFinal[key].paidAmount);
				td8.append(LorryReportFinal[key].balanceAmount);
				td9.append(LorryReportFinal[key].paymentStatus);
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				tr.append(td7);
				tr.append(td8);
				tr.append(td9);
			if(!data.configuration.hideExpensesTab) {
				tr.append(td10);
				
				var LorryNameAndNumberData = LorryReportFinal[key].LorryNameAndNumberData;
				
				var innertable 		= $('<table width="100%">');
				for(var t = 0 ; t < LorryNameAndNumberData.length; t++){
					
					var innertr 		= $('<tr>');
					var innertd1		= $('<td width="80%" style="text-align:left;">');
					var innertd2		= $('<td width="10%" colspan="3" style="text-align:left;">');
					
					innertd1.append(LorryNameAndNumberData[t].expenseName);
					innertd2.append(LorryNameAndNumberData[t].amount);
					
					innertr.append(innertd1);
					innertr.append(innertd2);
					
					innertable.append(innertr);
				}
				td10.append(innertable);
			}
				
			
				tbody.append(tr);
		}
		
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
	setTimeout(function(){ hideLayer(); }, 500);
}



