var data = [];
$(function() {
    $("#to").select2()
});
$(document).ready(function() {
	 $("#from, #fromRRV, #fromRRG , #from2, #from3").select2( {
        ajax: {
            url:"getRenewalType.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.renewal_Type, slug: a.slug, id: a.renewal_id
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#from").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#to").html(b)
        }
        )
    }
    )
});

$(document).ready(
		
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject					= new Object();
			
				jsonObject["renewalTypeId"] 	  	=  $('#from').val();
				jsonObject["renewalSubTypeId"] 		=  $('#to').val();
				jsonObject["statusId"] 				=  $('#statusId').val();
				jsonObject["vStatusId"] 			=  $('#vStatusId').val();
				
			
				$.ajax({
					
					url: "getRenewalOverDueReport",
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
	
	if(resultData.renewalList != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Renewal OverDue/DueSoon Report</b>');
		
		
		
		var type = $("#from option:selected").text();
		if(type){
			var tr =' <tr data-object-id="">'
				+'<td class="fit">Renewal Type  : '+type+'</td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		var date =  $('#renewalToDate').val();
		if(date != undefined && date != null){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"><b>Date  : '+date+'</b></td>'
			+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		var flagForPartNo	= false;
		
		/*if($('#searchpartPO').val().split(",").length == 1 && $('#searchpartPO').val() != ""){
			var flagForPartNo	= true;
			var data = $('#searchpartPO').select2('data');
			if(data != undefined){
				var tr =' <tr data-object-id="">'
					+'<td class="fit"> Part Number : '+data[0].text+'</td>'
					+'</tr>';
				$('#selectedReportDetails').append(tr);
			}
		
		}*/
		
		
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				if(columnKeys[i] == "Part_Number" && flagForPartNo){
					bObj.show	= false;
				}
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.renewalList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}


function showVehicleDetails(gridObj, dataView, row){

	var win = window.open('showVehicle.in?vid='+dataView.vehicle_vid, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}

}
function showWorkOrder(gridObj, dataView, row){
	var win;
	if(dataView.serviceTypeId == 1){
		win = window.open('showWorkOrder?woId='+dataView.workorders_id, '_blank');
	}else if(dataView.serviceTypeId == 2){
		
		win = window.open('ServiceEntries_COMPLETE.in?serviceEntries_id='+dataView.workorders_id, '_blank');
	}
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}