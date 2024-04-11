$(function() {
    function a(a, b) {
        $("#rangeTyrePurchase, #SEdateRange, #SEVehdateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#rangeTyrePurchase, #SEdateRange, #SEVehdateRange").daterangepicker( {
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
}

);


$("#createdByUser").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getCreatedUser", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.firstName +" "+ a.lastName, slug: a.slug, id: a.id , 
                        }
                    }
                    )
                }
            }
        }
}
);

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();
			
				jsonObject["createdByUser"] 		    =  $('#createdByUser').val();
				jsonObject["status"] 					=  $('#status').val();
				jsonObject["Partwarehouselocation2"]    =  $('#Partwarehouselocation2').val();
				jsonObject["reqType"]    				=  $('#reqType').val();
				jsonObject["PartInventryRange"]    	    =  $('#PartInventryRange').val();
				
				$.ajax({
					url: "PartWS/getPartRequisitionStatusWiseReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						partReuisitionReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});
			
		$("#createdByUser").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getCreatedUser", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.firstName +" "+ a.lastName, slug: a.slug, id: a.id , 
                        }
                    }
                    )
                }
            }
        }
		}
		);

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

function partReuisitionReportData(resultData) {
	if(resultData.requisition != undefined){
		
		setHeaderData(resultData.company);
		
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html("PART REQUISITION STATUS WISE REPORT");
		
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
		
		setSlickData(resultData.requisition, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}

