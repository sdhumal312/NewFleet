$(document).ready(function() {
	$("#checkingInspectorId").select2();
	 $("#vehicleGroupId").select2( {
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
    }),$("#vehicleGroupId").change(function() {
        $.getJSON("getCheckingInspectorListByGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#checkingInspectorId").html(b)
        })
    }), $("#vid").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListTEST.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    })
});
$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}

),$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				var jsonObject			= new Object();

				
				jsonObject["dateRange"] 	  		=  $('#reportrange').val();
				jsonObject["checkingInspectorId"] 	=  $('#checkingInspectorId').val();
				jsonObject["vid"] 					=  $('#vid').val();
				jsonObject["vehicleGroupId"] 		=  $('#vehicleGroupId').val();
				
					if(Number($('#vehicleGroupId').val()) <= 0){
						showMessage('info','Please Select Depot!');
						return false;
					}	
					
					if($('#reportrange').val() == ''){
						showMessage('info','Please Select Date Range!');
						$('#reportrange').focus();
						return false;
					}
					
				showLayer();
				$.ajax({
			             url: "getCheckingEntryReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!');
			            	 hideLayer();
			             }
				});
				
			});

		})
		
	//Office Code
/*	function renderReportData(data){
		setHeaderData(data.company);
		setReportData(data);
	}	*/
	 
	function renderReportData(data)
	{
		if(data.list != null && data.list.length > 0)
		{
			//setHeaderData(data.company);
			setReportData(data);
		}
		else
		{
			showMessage('errors', 'No Record Found !');
			$('#reportData').hide();
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
	if(data.list != null){
		$('#reportData').show();
		$('#advanceTable').show();
		$('#tableBody').html('');
		$('#tHeadId').html('');
		$('#reportDetails').html('<b>Checking Report : </b> '+data.SearchDate);
		var srNo = 1;
		 var tHead = '<tr><th width:2%>Sr No</th><th style = "width:15%">Chk Inspector</th><th>Vehicle</th><th style = "width:10%">Date</th><th style = "width:6%">In Time</th><th style = "width:6%">Out Time</th><th style = "width:10%">In Place</th><th style = "width:10%">Out Place</th><th>No Of Seat</th><th width:35%>Remark</th><th>Conductor</th></tr>';
		 $('#tHeadId').append(tHead);
		 for(var i = 0; i< data.list.length; i++){
			var vehicleCheckingDetails = data.list[i];
			var tr =' <tr id="'+srNo+'">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+vehicleCheckingDetails.checkingInspectorName+'</td>'
				+'<td>'+vehicleCheckingDetails.vehicle_registration+'</td>'
				+'<td>'+vehicleCheckingDetails.checkingDate+'</td>'
				+'<td>'+vehicleCheckingDetails.checkingTime+'</td>'
				+'<td>'+vehicleCheckingDetails.checkingOutTime+'</td>'
				+'<td>'+vehicleCheckingDetails.place+'</td>'
				+'<td>'+vehicleCheckingDetails.outPlace+'</td>'
				+'<td>'+vehicleCheckingDetails.noOfSeat+'</td>'
				+'<td>'+vehicleCheckingDetails.remark+'</td>'
				+'<td>'+vehicleCheckingDetails.conductorName+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			console.log('remark ',vehicleCheckingDetails.remark);
			if(vehicleCheckingDetails.remark != '' && vehicleCheckingDetails.remark != null){
				$('#'+srNo).children('td, th').css('background-color','red');
			}
			srNo++;
		}
	
	}
}

