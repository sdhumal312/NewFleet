$(document).ready(function() {
	$("#clothTypes").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getClothTypesList.in?Action=FuncionarioSelect2",
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
	                            text: a.clothTypeName,
	                            slug: a.slug,
	                            id: a.clothTypesId
	                        }
	                    })
	                }
	            }
	        }
    }),$("#warehouselocation").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                            text: a.partlocation_name,
                            slug: a.slug,
                            id: a.partlocation_id
                        }
                    })
                }
            }
        }
    }),$("#vehicleGroup ").select2( {
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
    }),$("#vehicleGroup").change(function() {
    	console.log("ONCHANGE",$(this).val());
        $.getJSON("getVehicleByVehicleGroupId.in", {
            vehicleGroupId: $(this).val(), ajax: "true"
        }
        , function(a) {
        	for(var b='<option> </option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].vid+'">'+a[d].vehicle_registration+"</option>";
            b+="</option>", $("#vehicle").html(b)
        }
        )
    }
    );
    
});
	
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		format:'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
});
	

$(function() {
    $("#vehicle").select2()
});
	
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();	
				
				var vehicles 								= $('#vehicle').val();
				if(vehicles != null){//check the value of vehicle if it is null ,key will not set on jsonObject and you will not get key(vehicleArr) on java
					jsonObject["vehicleArr"]					= JSON.stringify(vehicles.join(",")).replace(/["']/g, "");
				}
				jsonObject["clothTypes"] 					= $('#clothTypes').val();
				jsonObject["warehouselocation"] 			= $('#warehouselocation').val();
				jsonObject["vehicleGroup"] 					= $('#vehicleGroup').val();
				jsonObject["dateRange"] 					= $('#dateRange').val();

				console.log("jsonObject::",jsonObject)
				if($('#dateRange').val() == null || $('#dateRange').val() == undefined){
					showMessage('errors','Please Select Date');
					return false;
				}
				
				$.ajax({					
					url: "getUpholsteryAssignmentReport",
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



function renderReportData(resultData) {
	if(resultData.assignUpholstery != undefined){
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Upholstery Assignment Report</b>');
		var defaultVal = "ALL"
		var data = $('#vehicleGroup').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Vehicle Group : '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Vehicle Group : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data = $('#clothTypes').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Upholstery Types : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Upholstery Types : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data = $('#warehouselocation').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Warehouse Location: '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit">  Warehouse Location : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var date =  $('#dateRange').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Date Range : '+date+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
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
		
		setSlickData(resultData.assignUpholstery, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
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


function showVehicle(gridObj, dataView, row){
	console.log("dataView",dataView)
	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
		win.focus();
	} else {
		alert('Please allow popups for this website');
	}
}
	


