$(document).ready(function(){
$("#branchList").select2( {
        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
            url:"getBranchListsearch", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.branch_name, slug: e.slug, id: e.branch_id
                        }
                    }
                    )
                }
            }
        }
    }
    )
})

$(function(){
	$('button[type=submit]').click(function(e){
		showLayer();
		e.preventDefault();
		let vid=$('#vehicleId').val();
		let vTypeId=$('#VehicleTypeSelect').val();
		let vLocation=$('#branchList').val();
		let dateRange = $('#dateRange').val().trim();
		let dateArray = dateRange.split(' to ');
		let fromDate =dateArray[0].split('-').reverse().join('-');
		let toDate =dateArray[1].split('-').reverse().join('-');
		let renewalTypeId =$('#from').val();
		
		let renewalsubTypeId=0;
		let renewalsubType=$('#to').select2('data');
		if(renewalsubType)
		renewalsubTypeId =renewalsubType.id;
		let jsonObject = new Object();
		
		let renewalTypeText = '';
		if(renewalTypeId > 0)
		renewalTypeText =$('#from').select2('data').text;
		let renewalsubTypeText='';
		if(renewalsubTypeId > 0)
		 renewalsubTypeText=renewalsubType.text;
		
		let hideRenewalSubType = false;
		if((renewalTypeText.trim() != '' && renewalsubTypeText.trim() != '') && renewalTypeText == renewalsubTypeText){
			hideRenewalSubType = true;
		}		
		jsonObject['vid'] = vid;
		jsonObject['vTypeId'] = vTypeId;
		jsonObject['vLocation'] = vLocation;
		jsonObject['companyId'] = $('#companyId').val();
		jsonObject['renewalTypeId'] = renewalTypeId;
		jsonObject['renewalsubTypeId'] = renewalsubTypeId;
		jsonObject['fromDate'] = fromDate;
		jsonObject['toDate'] = toDate;
		
		$.ajax({
			url : 'getRenewalReminderReport',
			type : 'POST',
			dataType:'json',
			data : jsonObject,
			success : function (data){
				renderReportData(data,dateRange,renewalTypeId,hideRenewalSubType);
			},
			error: function (e){
				console.log('Error e :',e);
				showMessage('errors','Some Error Occured !');
				hideLayer();
			}	
			
		})
	})
	
})


function renderReportData(resultData,dateRange,renewalTypeId,hideRenewalSubType) {
	hideLayer();
	if(resultData.list != undefined){
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();

		var tr = ' <tr data-object-id="">'
			+ '<td class="fit"> Date Range : ' + dateRange + '</td>'
			+ '</tr>';
		$('#selectedReportDetails').append(tr);

		$("#reportHeader").html("Renewal Reminder Details ");
		
		if(resultData.tableConfig != undefined) {

			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
					if(hideRenewalSubType && bObj.tableDtoName == 'renewal_subtype')
						bObj.show =false;
				if (bObj.show != undefined && bObj.show == true) {
				
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
	
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			if(renewalTypeId >0){
				tableProperties.showGrouping=false;
			}

		}
		setSlickData(resultData.list, columnConfiguration, tableProperties);
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
		hideLayer();
	}else{
		$('#ResultContent').hide();
		$('#printBtn').addClass('hide');
		showMessage('info', 'No record found !');
		hideLayer();
	}
	}


$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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