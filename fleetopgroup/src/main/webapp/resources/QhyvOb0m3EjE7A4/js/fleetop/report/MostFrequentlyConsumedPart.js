$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
		format : 'DD-MM-YYYY',
		ranges: {
			Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
		}
	}
	, a)
}

);

$(document).ready(function() {
	$("#Reportvehiclegroup").select2( {
		ajax: {
			url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
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

				showLayer();
				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				jsonObject["vehicleGroupId"] 	=  $('#Reportvehiclegroup').val();
				if($('#Partwarehouselocation2').val() == undefined || $('#Partwarehouselocation2').val() == ""){
					jsonObject["location"] 		= 0;
				} else {
					jsonObject["location"] 			=  $('#Partwarehouselocation2').val();
				}

				if($('#Reportvehiclegroup').val() <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					hideLayer();
					return false;
				}
				
				
				$.ajax({
					
					url: "PartWS/getParts_Count_Report",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});

function renderReportData(data) {
	
	var partConsumedList	= null;
	var groupName 			= null;
	
	if(data.finalTasksDtoList != undefined && data.finalTasksDtoList.length > 0 ) {
		$("#reportHeader").html("Most Part Consumed Report</br>");

		$("#partConsumedList").empty();
		partConsumedList	= data.finalTasksDtoList;
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tbody1 = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');
		var td1	= $('<td>');
		
	
			
			//for(var i = 0; i < groupName.length; i++ ){
				td1.append("<span style='margin-right:100px;'>Group Name :"+$('#select2-chosen-1').html()+ "</span>");
				td1.append("<span style='margin-right:100px;'>Location :"+$('#select2-chosen-2').html() +" </span>");
				td1.append("<span style='margin-right:100px;'>Date : "+$('#TripCollectionExpenseRange').val()+"</span>");
			//}
	
		tr1.append(td1);
		tbody1.append(tr1);
		$("#firstTableHeader").append(tbody1);
		
		var tr2 = $('<tr style="font-weight: bold; font-size : 12px;">');
		
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		
		

		th1.append('Sr No');
		th2.append('Part Count');
		th3.append('Part Name');
		th4.append('Part Number');
		th5.append('Total cost Part Consumed');
		
		tr2.append(th1);
		tr2.append(th2);
		tr2.append(th3);
		tr2.append(th4);
		tr2.append(th5);
		
		

		thead.append(tr2);

		for(var i = 0; i < partConsumedList.length; i++ ) {
			var tr = $('<tr>');

			var td1 	= $('<td>');
			var td2		= $('<td style="text-align:center;">');
			var td3		= $('<td style="text-align:left; padding:5px;">');
			var td4		= $('<td style="text-align:left; padding:5px;">');
			var td5		= $('<td style="text-align:right; padding:5px;">');
			

			td1.append(i+1);
			td2.append(partConsumedList[i].quantity);
			td3.append(partConsumedList[i].partname);
			td4.append(partConsumedList[i].partnumber);
			td5.append(partConsumedList[i].totalValuePartConsumed.toFixed(2));
			

			

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			
			tbody.append(tr);
		}


		$("#partConsumedList").append(thead);
		$("#partConsumedList").append(tbody);
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

