var data = [];

$(document).ready(function() {
	$("#batteryId").select2( {
        minimumInputLength:2,
        minimumResultsForSearch:10, 
        ajax: {
            url:"getBatteryWiseHistoryReport.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.batterySerialNumber, slug: a.slug, id: a.batteryId
                        }
                    }
                    )
                }
            }
        }
    })
    
 $("#btn-save").click(function(event) {   
   showLayer();
	var jsonObject			= new Object();
	jsonObject["batteryId"] =  $('#batteryId').val();
	$.ajax({
             url: "viewBatteryHistory",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setBatteryHistoryDate(data)
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	}); 
    
    });
});
function setBatteryHistoryDate(data){

	if(data.batteryHistoryList != undefined) {
		$('#selectedData').show();
		$("#ResultContent").show();
		$("#reportHeader").html("Battery History Report");

		$("#batteryHistoryDetails").empty();
		var batteryHistoryList	= data.batteryHistoryList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 15px;">');

		var th1		= $('<th style="font-size : 15px;">');
		var th2		= $('<th style="font-size : 15px;">');
		var th3		= $('<th style="font-size : 15px;">');
		var th4		= $('<th style="font-size : 15px;">');
		var th5		= $('<th style="font-size : 15px;">');
		var th6		= $('<th style="font-size : 15px;">');
		var th7		= $('<th style="font-size : 15px;">');
		var th8		= $('<th style="font-size : 15px;">');
		var th9		= $('<th style="font-size : 15px;">');
		var th10	= $('<th style="font-size : 15px;">');
		
		th1.append('Sr No');
		th2.append('Asign Date');
		th3.append('Battery Number');
		th4.append("Vehicle");
		th5.append('Position');
		th6.append('Status');
		th7.append('Odometer');
		th8.append('Usage');
		th9.append('Usage(in Days)');
		th10.append('Comment');

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

		thead.append(tr1);

		for(var i = 0; i < batteryHistoryList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td style="font-size : 15px;">');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10  	= $('<td>');
			
			var batteryHistory = batteryHistoryList[i];
			if(batteryHistory.vehicle_registration == null){
				vehicleUrl	= '--';
			}else{
				vehicleUrl	= '<a target="_blank" href="showVehicle?vid='+batteryHistory.vid+'">'+""+batteryHistory.vehicle_registration+'</a>';
			}
			if(batteryHistory.layoutPosition != 0){
				position = 'Battery - '+batteryHistory.layoutPosition;
			}else{
				position = '--';
			}
			
			td1.append(i+1);
			td2.append(batteryHistoryList[i].batteryAsignDateStr);
			td3.append(batteryHistoryList[i].batterySerialNumber);
			td4.append(vehicleUrl);
			td5.append(position);
			td6.append(batteryHistoryList[i].batteryStatus);
			td7.append(batteryHistoryList[i].openOdometer);
			td8.append(batteryHistoryList[i].batteryUseage);
			td9.append(batteryHistoryList[i].usesNoOfDay);
			td10.append(batteryHistoryList[i].batteryComment)
			
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
			
			tbody.append(tr);
		}
			
		$("#batteryHistoryDetails").append(thead);
		$("#batteryHistoryDetails").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
	
}
