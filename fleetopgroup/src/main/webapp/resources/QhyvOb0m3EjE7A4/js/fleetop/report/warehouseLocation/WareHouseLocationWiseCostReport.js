var data = [];

$(document).ready(function() {
	$("#locationId").select2({
        minimumInputLength:2, 
        minimumResultsForSearch:10, 
        ajax: {
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
    })
});


$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		maxDate: new Date(),
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


$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["locationId"] 	  	=  $('#locationId').val();
				jsonObject["dateRange"] 	  	=  $('#dateRange').val();
				
				if($('#dateRange').val() <= 0){					
					showMessage('info', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					
					url: "getWareHouseLocationWiseCostReport",
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

//slickgrid

var WorkOrdersList;
function renderReportData(resultData) {
	console.log("1")
	if(resultData.VehicleWiseList != undefined){
		console.log("2")
		WorkOrdersList = resultData.WorkOrdersList;
		
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>WareHouse Location Wise Cost Report</b>');
		
		var data = $('#locationId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> WareHouse Location : '+data.text+'</td>'
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
		
		setSlickData(resultData.VehicleWiseList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		console.log("ola")
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

function workOrderDetails(gridObj, dataView, row){
	
	$('#workOrderDetails').modal('show');
	setWorkOrderDetails(dataView.vid);
}

function setWorkOrderDetails(vid) {

	$("#VendorPaymentTable1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("WorkOrder No"));
	tr1.append(th3.append("Vehicle"));
	tr1.append(th4.append("Start Date"));
	tr1.append(th5.append("Due Date"));
	tr1.append(th6.append("Completed Date"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Location"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(WorkOrdersList != undefined) {
		var j = 0;
		var total = 0;
		for(var i = 0; i < WorkOrdersList.length; i++) {
			
			if(WorkOrdersList[i].vid == vid){
				
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				var td5		= $('<td class="fit ar">');
				var td6		= $('<td class="fit ar">');
				var td7		= $('<td class="fit ar">');
				var td8		= $('<td>');
				
				tr1.append(td1.append(j = j + 1));
				tr1.append(td2.append(WorkOrdersList[i].workOrder_Number));
				tr1.append(td3.append(WorkOrdersList[i].vehicle_registration));
				tr1.append(td4.append(WorkOrdersList[i].woStartDate));
				tr1.append(td5.append(WorkOrdersList[i].woDueDate));
				tr1.append(td6.append(WorkOrdersList[i].wocompletedDate));
				tr1.append(td7.append(WorkOrdersList[i].totalworkorder_cost));
				tr1.append(td8.append(WorkOrdersList[i].partlocation_name));
				
				
				total += WorkOrdersList[i].totalworkorder_cost; 
				tbody.append(tr1);
				
			}
			
		}
		console.log("total",total);
		var tr2 = $('<tr>');

		var tdtotal1		= $('<td colspan="6">');
		var tdtotal2		= $('<td colspan="2">');

		tdtotal1.append("Total :");
		tdtotal2.append(total.toFixed(2));

		tr2.append(tdtotal1);
		tr2.append(tdtotal2);
		
		tbody.append(tr2);
		
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);

}



