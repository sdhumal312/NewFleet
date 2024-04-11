$(document).ready(function() {
   $("#PartlocationPO2").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
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
    }
    ),$("#PartlocationPO3").select2( {
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
    
    ),$(function() {
        function a(a, b) {
            $("#PartInventryRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
        }
        a(moment().subtract(1, "days"), moment()), $("#PartInventryRange").daterangepicker( {
            format: 'DD-MM-YYYY',
            ranges: {
                Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
            }
        }
        , a)
    }

    ),$("#btn-save").click(function(event) {
		showLayer();
		var jsonObject					= new Object();
		jsonObject["partLocationFrom"]		= $("#PartlocationPO2").val();
		jsonObject["partLocationTo"]		= $("#PartlocationPO3").val();
		jsonObject["TransferStatus"]		= $("#TransferStatus").val();
		jsonObject["dateRange"]				= $("#PartInventryRange").val();
		
		$.ajax({
			url: "inventoryTyreTransferedReportWS/getInventoryTyreTransferedReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setTyreTransferReportData(data);
			},
			error: function (e) {
				hideLayer();
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
    })
});

function setTyreTransferReportData(data) {
	var inventoryTyreTransferList	= null;
	if(data.InventoryTyreTransferList != undefined) {
		$("#reportHeader").html("Tyre Transfer Report");

		$("#tyreTransferDetails").empty();
		inventoryTyreTransferList	= data.InventoryTyreTransferList;
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
		var th9		= $('<th>');

		th1.append('Sr No');
		th2.append('Tyre');
		th3.append('Transfer From');
		th4.append('Transfer To');
		th5.append('Transfer By');
		th6.append('Transfer Date');
		th7.append('Received By');
		th8.append('Received Date');
		th9.append('Status');

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

		for(var i = 0; i < inventoryTyreTransferList.length; i++ ) {
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
			td2.append(inventoryTyreTransferList[i].tyre_NUMBER);
			td3.append(inventoryTyreTransferList[i].tra_FROM_LOCATION);
			td4.append(inventoryTyreTransferList[i].tra_TO_LOCATION);
			td5.append(inventoryTyreTransferList[i].transfer_BY);
			td6.append(inventoryTyreTransferList[i].transfer_DATE);
			td7.append(inventoryTyreTransferList[i].transfer_RECEIVEDBY);
			td8.append(inventoryTyreTransferList[i].transfer_RECEIVEDDATE);
			td9.append(inventoryTyreTransferList[i].transfer_STATUS);

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