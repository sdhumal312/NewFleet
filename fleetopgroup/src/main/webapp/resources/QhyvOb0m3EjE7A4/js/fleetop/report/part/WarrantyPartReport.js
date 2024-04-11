$(document).ready(function() {
	$("#partId").select2({
		minimumInputLength: 2,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getWarrantyPartList.in",
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
				console.log("a",a)
				return {
	                results: $.map(a, function(a) {
	                    return {
	                    	text: a.partnumber + " - " + a.partname ,
							slug: a.slug,
							id: a.partid,
	                    }
	                })
	            }
			}
		}
}),$("#locationId").select2({
    ajax: {
        url: "getWorkOrderPartlocation.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "GET",
        contentType: "application/json",
        data: function(a) {
            return {
                term: a
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.partlocation_name,
                        slug: a.slug,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
}),$("#vehicleId").select2({minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
		return {
			term: a
		}
	}
, results:function(a) {
	return {
		results:$.map(a, function(a) {
			return {
				text: a.vehicle_registration, slug: a.slug, id: a.vid
			}
		})
	}
  }
}
}),
$('#serviceTypeId').select2();
});


$(document).ready(
	function($) {
		$('button[type=submit]').click(function(e) {
			e.preventDefault();
			showLayer();
			
			if(Number($('#vehicleId').val()) <= 0){
				showMessage('info','Please Select Vehicle!');
				hideLayer();
				return false;
			}	
			if($('#partId').val() <= 0){
				showMessage('info','Please Select Part!');
				hideLayer();
				return false;
			}
			var jsonObject						= new Object();
			
			jsonObject["vid"] 					=  $('#vehicleId').val();
			jsonObject["partId"] 				=  $('#partId').val();
			jsonObject["serviceTypeId"] 		=  $('#serviceTypeId').val();
			
			showLayer();
			
			$.ajax({
				url: "getWarrantyPartDataDetails",
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


function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		//$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}

function renderReportData(resultData) {
	if(resultData.tasksToPartsDtos != undefined){
		$('#serviceType').val(resultData.serviceTypeId);
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Part Wise Consumption Report</b>');
		
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.tasksToPartsDtos, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}


function showAssetDetails(workordertasToPartkid){
	var jsonObject				= new Object();
	jsonObject["servicePartId"]	= workordertasToPartkid;
	jsonObject["companyId"]		= $('#companyId').val();
	jsonObject["serviceType"]		= $('#serviceType').val();
		$.ajax({
			url: "getWarrantyDetailsListDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				$('#warrantyListBody tr').remove();
				setAssignedWarrantyPartList(data);
				$('#addWarrantyParts').modal('show');
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
}

function setAssignedWarrantyPartList(data){
	   
	   $('#assingedDataBody').empty();
	   
		if(data.assignedParts != undefined && data.assignedParts.length > 0){
		var SrNo = 1;
			var tableHead = '<tr>'
						   +'<th>Sr.</th>'
						   +'<th>Assigned Part</th>'
						   +'<th>Replaced With</th>'
			               +'</tr>';
			 $('#assingedDataBody').append(tableHead);                 
			               
			for(var i = 0; i < data.assignedParts.length ; i++){
				var tr = '<tr>'
				+'<td class="fit">'+SrNo+'</td>'
				+'<td class="fit">'+data.assignedParts[i].partSerialNumber+'</td>'
				+'<td class="fit">'+data.assignedParts[i].replacePartSerialNumber+'</td>'
				+'</tr>';
				
				SrNo++;
				
				 $('#assingedDataBody').append(tr);   
			}
		}
	}