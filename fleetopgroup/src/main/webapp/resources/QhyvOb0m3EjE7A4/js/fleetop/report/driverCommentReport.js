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
	)
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				jsonObject["vehicleGroupId"] 	=  $('#workOrderGroup').val();
				jsonObject["driverId"] 				=  $('#driverId').val();
				
				if($('#workOrderGroup').val() <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				
				showLayer();
				$.ajax({
			             url: "getDriverCommentReport",
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
			
				hideLayer();
			});

		}),$("#workOrderGroup").change(function() {
	        $.getJSON("getDriverByVehicleGroupId.in", {
	            vehicleGroupId: $(this).val(), ajax: "true"
	        }
	        , function(a) {
	            for(var b='<option value="0">Please Select</option>', c=a.length, d=0;
	            c>d;
	            d++)b+='<option value="'+a[d].driver_id+'">'+a[d].driver_empnumber+'_'+a[d].driver_firstname+' '+a[d].driver_Lastname+"</option>";
	            b+="</option>", $("#driverId").html(b)
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
		 var tHead = '<tr><th class="fit">No</th><th>Driver</th><th style = "width:20%">Title</th><th style = "width:40%">Comment</th><th style = "width:15%">Date Time</th><th>Created By</th></tr>';
		 $('#tHeadId').append(tHead);
		 for(var i = 0; i< data.commentList.length; i++){
			var comment = data.commentList[i];
			console.log("comment : ", comment);
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td  value="'+comment.driver_firstname+'">'+comment.driver_empnumber+'_'+comment.driver_firstname+' '+comment.driver_Lastname+' '+comment.driverFatherName+'</td>'
				+'<td  value="'+comment.driver_title+'">'+comment.driver_title+'</td>'
				+'<td  value="'+comment.driver_comment+'">'+comment.driver_comment+'</td>'
				+'<td  value="'+comment.creationDate+'">'+comment.creationDate+'</td>'
				+'<td  value="'+comment.createdBy+'">'+comment.createdBy+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
	
	}
}

