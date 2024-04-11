$(document).ready(function() {
			$("#location").select2();
			$("#batterryModelTypeId").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
  }),$("#batteryManufacurer").select2({
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
	    }),$("#batteryManufacurer").change(function() {
            $.getJSON("getBatteryType.in", {
                ModelType: $(this).val(),
                ajax: "true"
            }, function(a) {
                for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].batteryTypeId + '">' + a[d].batteryType + "</option>";
                b += "</option>", $("#batterryModelTypeId").html(b)
            })
        }),  $("#BatteryLocationFrom").select2( {
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
        ), $("#batteryCapacityId").select2({
            minimumInputLength: 2,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getSearchBatteryCapacity.in?Action=FuncionarioSelect2",
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
                                text: a.batteryCapacity,
                                slug: a.slug,
                                id: a.batteryCapacityId
                            }
                        })
                    }
                }
            }
        });
			var deleted = getUrlParameter('delete');
		if(deleted == true || deleted == 'true'){
			showMessage('info','Data Deleted Successfully !');
		}else if(deleted == false || deleted == 'false'){
			showMessage('errors','Data Deletion Failed !');
		}	
		
		var ScrapSuccess = getUrlParameter('ScrapSuccess');
		if(ScrapSuccess == true || ScrapSuccess == 'true'){
			showMessage('info', 'Battery Scraped Successfully !');
		}
		var valuesRejected = getUrlParameter('valuesRejected');
		var valuesInserted = getUrlParameter('valuesInserted');
		
		if(valuesRejected != undefined && valuesInserted != undefined){
			showMessage('success', 'No of Battery : '+valuesInserted+' inserted successfully. AND '+ valuesRejected+' Rejected !');
		}
		
});

$(document).ready(function() {
  $(function() {
        function a(a, b) {
            $("#BatteryTransferRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
        }
        a(moment().subtract(1, "days"), moment()), $("#BatteryTransferRange").daterangepicker( {
            format:'DD-MM-YYYY',
            ranges: {
                Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], 
                "Last 7 Days": [moment().subtract(6, "days"), moment()],
                "This Month": [moment().startOf("month"), moment().endOf("month")],
                "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
            }
        }
        , a)
    }

    ),$("#scrapSearch").click(function(event) {
		showLayer();
		var jsonObject						= new Object();
		
		jsonObject["batteryManufacurer"]	= $("#batteryManufacurer").val();
		jsonObject["batterryModelTypeId"]	= $("#batterryModelTypeId").val();
		jsonObject["batteryCapacityId"]		= $("#batteryCapacityId").val();
		jsonObject["LocationId"]			= $("#BatteryLocationFrom").val();
		jsonObject["dateRange"]				= $("#BatteryTransferRange").val();
		
		$.ajax({
			url: "BatteryWS/getBatteryStockReport",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
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
	var batteryStockDetails	= null;
	if(data.BatteryStockList != undefined) {
		$("#reportHeader").html("Battery Stock Report");
		$("#batteryStockDetails").empty();
		batteryStockDetails	= data.BatteryStockList;
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
		var th10	= $('<th>');
		var th11	= $('<th>');

		th1.append('Sr No');
		th2.append('Battery Number');
		th3.append('Battery Manufacturer');
		th4.append('Battery Model');
		th5.append('Battery Capacity');
		th6.append('Amount');
		th7.append('Location');
		th8.append('Vehicle');
		th9.append('Status');
		th10.append('used(In days)');
		th11.append('Cost Per Day');

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);
		tr1.append(th11);

		thead.append(tr1);

		for(var i = 0; i < batteryStockDetails.length; i++ ) {
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
			var td11	= $('<td>');

			td1.append(i+1);
			td2.append(batteryStockDetails[i].batterySerialNumber);
			td3.append(batteryStockDetails[i].manufacturerName);
			td4.append(batteryStockDetails[i].batteryType);
			td5.append(batteryStockDetails[i].batteryCapacity);
			td6.append(batteryStockDetails[i].batteryAmount.toFixed(2));
			td7.append(batteryStockDetails[i].locationName);
			td8.append(batteryStockDetails[i].vehicle_registration);
			td9.append(batteryStockDetails[i].batteryStatus);
			td10.append(batteryStockDetails[i].usesNoOfTime);		
			td11.append((batteryStockDetails[i].costPerDay).toFixed(2));

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
			tr.append(td10);
			tr.append(td11);

			tbody.append(tr);
		}

		$("#batteryStockDetails").append(thead);
		$("#batteryStockDetails").append(tbody);
		
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