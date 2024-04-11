var data = [];
//var gridObject;
//var slickGridWrapper3;

$(document).ready(function() {
	$("#vehicleId").select2({
        minimumInputLength:2,
        minimumResultsForSearch:10, 
        ajax: {
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
                    }
                    )
                }
            }
        }
    },{
    
    	placeholder : "All"
    });
	$("#VehicleGroupSelect").select2( {
        ajax: {
            url:"getVehicleGroup.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vGroup, slug: t.slug, id: t.gid
                        }
                    }
                    )
                }
            }
        }
    },{
    	placeholder : "All"
    });
	
	$("#vehicleId").val('');
	$("#VehicleGroupSelect").val('');
}


);

$(function() {
    function a(a, b) {
        $("#dateRangeReport").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
    	format : 'DD-MM-YYYY',
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

var a,b;
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject		= new Object();	
			
				jsonObject["vehicleId"] 					= $('#vehicleId').val();
				jsonObject["vehicleGroup"] 					= $('#VehicleGroupSelect').val();
				
				$.ajax({					
					url: "getVehicleWiseTyreAssignReport",
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



//slickgreed
function renderReportData(resultData) {
	if(resultData.currentTyreList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		$('#reportHeader').html('<b style="margin-right: auto; margin-left: auto;">Vehicle Wise Tyre Assign Report</b>');
		
		var data = $('#vehicleId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data2 = $('#VehicleGroupSelect').select2('data');
		if(data2){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Vehicle Group : '+data2.text+'</td>'
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
					if(resultData.vGroupSelected > 0 && bObj.tableDtoName === "vehicleGroup"){
						bObj.show=false;
					}
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
		}
		
		setSlickData(resultData.currentTyreList, columnConfiguration, tableProperties);
		
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


$('#vehicleId').on('change',function(){
	if($(this).val() > 0){
		$('#vehicleGroupDiv').hide();
		$('#VehicleGroupSelect').select2('val','');
	}else{
		$('#vehicleGroupDiv').show();
	}
});
