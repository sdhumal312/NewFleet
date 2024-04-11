$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        format:'DD-MM-YYYY',
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}

),
$(function() {
    $("#driverId").select2()
}

);

$(document).ready(function() {
	$("#workOrderGroup").select2( {
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
	                }
	                )
	            }
	        }
	    }
	}
	), $("#workOrderLocation").select2( {
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
    )
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				jsonObject["vehicleGroupId"] 	=  $('#workOrderGroup').val();
				jsonObject["workOrderLocation"] =  $('#workOrderLocation').val();
				
				
				showLayer();
				$.ajax({
			             url: "getOldPartReceivedReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
				
			});

		});

function renderReportData(data){
	console.log('data', data);
	if(data.workOrderPartList != null && data.workOrderPartList.length > 0){
		setHeaderData(data.company);
		setReportData(data);
		hideLayer();
		$('#printButton').show();
		$("#exportExcelBtn").removeClass("hide");
	}else{
		showMessage('errors', 'NO Record Found!');
		hideLayer();
		$('#companyTable').hide();
		$('#advanceTable').hide();
		$('#printButton').hide();
		$("#exportExcelBtn").addClass("hide");
	}
}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#tbodyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#tbodyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		

		}
	}
}
function setReportData(data){
	$("#advanceTable tr").remove();
	if(data.workOrderPartList != null){
		$('#advanceTable').show();
		$('#reportDetails').html('<b>Old Part Received Report : </b> '+data.SearchDate);
		var srNo = 1;
		 var tHead = '<tr><th class="fit">No</th><th>WorkOrderNumber</th><th>Date</th><th>Location</th><th>Vehicle</th><th>Part</th><th>Quantity</th></tr>';
		 $('#tHeadId').append(tHead);
		 for(var i = 0; i< data.workOrderPartList.length; i++){
			var workOrders = data.workOrderPartList[i];
			var curl = "showWorkOrder?woId="+workOrders.workorders_id;
			//td1.append('<a target="_blank" href="' + curl + '">'+"WO-"+workOrders.workOrderNumber+'</a>');
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td><a target="_blank" href="' + curl + '">'+"WO-"+workOrders.workOrderNumber+'</a></td>'
				+'<td>'+workOrders.complitionDate+'</td>'
				+'<td>'+workOrders.location+'</td>'
				+'<td>'+workOrders.vehicle_registration+'</td>'
				+'<td>'+workOrders.partnumber+'_'+workOrders.partname+'</td>'
				+'<td>'+workOrders.quantity+'</td>'				
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
	
	}
}

