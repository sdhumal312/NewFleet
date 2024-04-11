$(document).ready(function() {
	$("#ReporttyreSize").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
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
    }
    ),$("#WarehouseLocation").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getAllPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
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
    }
    ),$("#TyreRetreadVendor").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
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
    }
    
    ),$("#btn-save").click(function(event) {
		showLayer();
		var jsonObject					= new Object();
		jsonObject["ReporttyreSize"]		= $("#ReporttyreSize").val();
		jsonObject["WarehouseLocation"]		= $("#WarehouseLocation").val();
		jsonObject["TyreRetreadVendor"]		= $("#TyreRetreadVendor").val();
		jsonObject["dateRange"]				= $("#PartInventryRange").val();
		
		$.ajax({
			url: "inventoryTyreTransferedReportWS/getTyreSentForRetreadingReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setTyreSentForRetreadingReport(data);
			},
			error: function (e) {
				hideLayer();
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
    })
});

function setTyreSentForRetreadingReport(data) {
	var InventoryTyreRetreadAmountList	= null;
	if(data.InventoryTyreRetreadAmountList != undefined) {
		$("#reportHeader").html("Tyre Transfer Report");

		$("#tyreTransferDetails").empty();
		InventoryTyreRetreadAmountList	= data.InventoryTyreRetreadAmountList;
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
		var th8 	= $('<th>');
		var th9 	= $('<th>');
		

		th1.append('Sr No');
		th2.append('Tyre Number');
		th3.append('TRNUMBER');
		th4.append('Location');
		th5.append('Tyre Size');
		th6.append('Vendor Name');
		th7.append('Vendor Location');
		th8.append('OPEN DATE');
		th9.append('REQUIRED DATE');
		

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

		for(var i = 0; i < InventoryTyreRetreadAmountList.length; i++ ) {
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
	

			td1.append(i+1);
			td2.append(InventoryTyreRetreadAmountList[i].tyre_NUMBER);
			td3.append(InventoryTyreRetreadAmountList[i].trnumber);
			td4.append(InventoryTyreRetreadAmountList[i].partLocationName);
			td5.append(InventoryTyreRetreadAmountList[i].tyre_SIZE); 
			td6.append(InventoryTyreRetreadAmountList[i].vendor_NAME);
			td7.append(InventoryTyreRetreadAmountList[i].vendor_LOCATION);
		    td8.append(InventoryTyreRetreadAmountList[i].tr_OPEN_DATE);
		    td9.append(InventoryTyreRetreadAmountList[i].tr_REQUIRED_DATE);

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
		
			tbody.append(tr);
		}

		$("#tyreTransferDetails").append(thead);
		$("#tyreTransferDetails").append(tbody);
		
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