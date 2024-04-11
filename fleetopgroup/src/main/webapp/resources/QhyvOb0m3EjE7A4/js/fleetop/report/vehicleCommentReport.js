$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
       format : 'DD-MM-YYYY',
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}

),
$(function() {
    $("#vid").select2()
});

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
	)
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				jsonObject["vehicleGroupId"] 	=  $('#workOrderGroup').val();
				jsonObject["vid"] 				=  $('#vid').val();
				
				if($('#workOrderGroup').val() <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				
				showLayer();
				$.ajax({
			             url: "getVehicleCommentReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
				
			});

		}),$("#workOrderGroup").change(function() {
	        $.getJSON("getVehicleByVehicleGroupId.in", {
	            vehicleGroupId: $(this).val(), ajax: "true"
	        }
	        , function(a) {
	        	for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
	            c>d;
	            d++)b+='<option value="'+a[d].vid+'">'+a[d].vehicle_registration+"</option>";
	            b+="</option>", $("#vid").html(b)
	        }
	        )
	    }
	    );

function renderReportData(data){
	if(data.commentList != null && data.commentList.length > 0){
		setHeaderData(data.company);
		setReportData(data);
	}else{
		showMessage('errors', 'NO Record Found!');
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
	if(data.commentList != null){
		$('#advanceTable').show();
		$("#exportExcelBtn").removeClass("hide");
		$('#reportDetails').html('<b>Vehicle Comment Report : </b> '+data.SearchGroup+' - '+data.SearchDate);
		var srNo = 1;
		 var tHead = '<tr><th class="fit">No</th><th>Vehicle</th><th style = "width:20%">Title</th><th style = "width:40%">Comment</th><th style = "width:15%">Date Time</th><th>Created By</th></tr>';
		 $('#tHeadId').append(tHead);
		 for(var i = 0; i< data.commentList.length; i++){
			var comment = data.commentList[i];
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td  value="'+comment.vehicle_REGISTRATION+'">'+comment.vehicle_REGISTRATION+'</td>'
				+'<td  value="'+comment.vehicle_TITLE+'">'+comment.vehicle_TITLE+'</td>'
				+'<td  value="'+comment.vehicle_COMMENT+'">'+comment.vehicle_COMMENT+'</td>'
				+'<td  value="'+comment.created_DATE+'">'+comment.created_DATE+'</td>'
				+'<td  value="'+comment.createdby+'">'+comment.createdby+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
	
	}
}

