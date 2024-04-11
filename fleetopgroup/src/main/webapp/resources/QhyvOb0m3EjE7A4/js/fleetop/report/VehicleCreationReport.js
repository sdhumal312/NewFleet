$(document).ready(function() {
$("#btn-search").click(function(event) {
	
		var jsonObject					        = new Object();		
		var startDate						= '01-';
		var startDateOfMonth				= startDate + ($('#monthRangeSelector').val()).replace('/', '-');	
		
		jsonObject["monthRangeSelector"]	= startDateOfMonth;	
		
		if(Number($('#monthRangeSelector').val()) <= 0){
			showMessage('info','Please Select Month !');
			return false;
		}
		
		showLayer();		
		
		$.ajax({			
			url: "VehicleCreationReportInfo.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				setVehicleCreationReport(data);
				hideLayer();
			},
			error: function (e) {
				alert("ALERT 1");
				hideLayer();
			}
		});
		
    })
});


function setVehicleCreationReport(data) {	
	
	if(data.finalMapOfVehicle != undefined){
		var vehicleCreationInfo		= data.finalMapOfVehicle;
	}
	
	if(vehicleCreationInfo != undefined ) {
		$("#reportHeader").html("Vehicle Creation Report");
		$("#advanceTable").empty();			
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
				
		th1.append('Sr No');
		th2.append('Company Name');
		th3.append('Active Status');
		th4.append('InActive Status');		
		th5.append('Sold Status');
		
		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);		
		tr1.append(th5);
				
		thead.append(tr1);
		
		var i=0;
		for(var key in vehicleCreationInfo) {
			
			var tr 		= $('<tr>');
			var td1		= $('<td>');
			var td2		= $('<td>');	
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
				
						
			td1.append(i+1);
			i++;
			td2.append(vehicleCreationInfo[key].companyName);
			
			td3.append('<a data-toggle="modal"  onclick= popUp(1,'+ vehicleCreationInfo[key].vehicleCompanyId +',"'+ vehicleCreationInfo[key].created +'"' +',' +vehicleCreationInfo[key].vehicleActiveCount+');>'+vehicleCreationInfo[key].vehicleActiveCount+'  </a>');	
			td4.append('<a data-toggle="modal"  onclick= popUp(2,'+ vehicleCreationInfo[key].vehicleCompanyId +',"'+ vehicleCreationInfo[key].created +'"' + ',' +vehicleCreationInfo[key].vehicleInActiveCount+ ');>'+vehicleCreationInfo[key].vehicleInActiveCount+'  </a>');		
			td5.append('<a data-toggle="modal"  onclick= popUp(4,'+ vehicleCreationInfo[key].vehicleCompanyId +',"'+ vehicleCreationInfo[key].created +'"'+','+vehicleCreationInfo[key].vehicleSoldCount+');>'+vehicleCreationInfo[key].vehicleSoldCount+'  </a>');		
			
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
						
			tbody.append(tr);	
			
		}	
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

var companyId		= 0;
var vehicleStatusId	= 0;
var date 			= "";
function popUp(vehicleStatusId,companyId,dateRange,count)
{
	
	
	if(count == 0){
		showMessage('errors','No record found !');
		return false;
	}
	childwin = window.open('VehicleCreationReportDetails?vehicleStatusId='+vehicleStatusId+'&companyId='+companyId+'&date='+dateRange);
}













