$(document).ready(function() {
   $("#BatteryLocationFrom").select2( {
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
    ),$("#BatteryLocationTo").select2( {
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
            $("#BatteryTransferRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
        }
        a(moment().subtract(1, "days"), moment()), $("#BatteryTransferRange").daterangepicker( {
            format:'DD-MM-YYYY',
            ranges: {
                Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
            }
        }
        , a)
    }

    ),$("#btn-save").click(function(event) {
		showLayer();
		var jsonObject						= new Object();
		
		jsonObject["fromLocationId"]		= $("#BatteryLocationFrom").val();
		jsonObject["toLocationId"]			= $("#BatteryLocationTo").val();
		jsonObject["transferStatusId"]		= $("#TransferStatus").val();
		jsonObject["dateRange"]				= $("#BatteryTransferRange").val();
		
		console.log("jsonObject : " , jsonObject);
		
		$.ajax({
			url: "batteryReportWS/getBatteryTransferReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data in js : " , data);
				setBatteryTransferReportData(data);
			},
			error: function (e) {
				hideLayer();
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
    })
});

function setBatteryTransferReportData(data) {
	var batteryTransferList	= null;
	if(data.BatteryTransferList != undefined) {
		$("#reportHeader").html("Battery Transfer Report");

		$("#batteryTransferDetails").empty();
		batteryTransferList	= data.BatteryTransferList;
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
		th2.append('Battery');
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

		for(var i = 0; i < batteryTransferList.length; i++ ) {
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
			td2.append(batteryTransferList[i].batterySerialNumber);
			td3.append(batteryTransferList[i].fromLocationName);
			td4.append(batteryTransferList[i].toLocationName);
			td5.append(batteryTransferList[i].transferBy);
			td6.append(batteryTransferList[i].transferDateStr);
			td7.append(batteryTransferList[i].receiveBy);
			td8.append(batteryTransferList[i].receiveDateStr);
			td9.append(batteryTransferList[i].transferStaus);

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

		$("#batteryTransferDetails").append(thead);
		$("#batteryTransferDetails").append(tbody);
		
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