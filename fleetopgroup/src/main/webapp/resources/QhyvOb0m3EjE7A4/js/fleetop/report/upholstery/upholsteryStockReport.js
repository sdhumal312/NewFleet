var data = [];
//var gridObject;
//var slickGridWrapper3;

//Drop Down for Upholstery Types and Warehouse Location Input Fields
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
    }),
    
    $("#warehouselocation").select2({
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
    })
 
    
});

var a,b;
$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();				
				jsonObject["clothTypes"] 					= $('#clothTypes').val();
				jsonObject["warehouselocation"] 			= $('#warehouselocation').val();
				a=jsonObject["clothTypes"];
				b=jsonObject["warehouselocation"];
				
				$.ajax({					
					url: "getUpholsteryStockReport",
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
	if(resultData.stock != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Upholstery Stock Report</b>');
		
		var data = $('#clothTypes').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Upholstery Types : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		var data = $('#warehouselocation').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Warehouse Location: '+data.text+'</td>'
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
		
		setSlickData(resultData.stock, columnConfiguration, tableProperties);
		
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

//Mods

function serviceQtyDetails(gridObj, dataView, row){
	
	var clothTypesId 		= dataView.clothTypesId;
	var wareHouseLocationId = dataView.wareHouseLocationId;
	
	inServiceVehicle(clothTypesId,wareHouseLocationId);
}

//Don't touch below code
function inServiceVehicle(clothTypesId,wareHouseLocationId){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;
	jsonObject["wareHouseLocationId"]	= wareHouseLocationId;
	
	
	$.ajax({
		url: "getInServiceVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInServiceVehicle(data);
			$('#inService').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occured!');
		}
	});
	
}

//Don't touch below code
function setInServiceVehicle(data) {
	$("#VendorPaymentTable1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Assigned/Removed By"));
	tr1.append(th7.append(""));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.InServiceVehicleDetailsList != undefined && data.InServiceVehicleDetailsList.length > 0) {
		
		var InServiceVehicleDetailsList = data.InServiceVehicleDetailsList;
	
		for(var i = 0; i < InServiceVehicleDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			console.log("In Washing method For Loop");
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(InServiceVehicleDetailsList[i].locationName));
			tr1.append(td3.append(InServiceVehicleDetailsList[i].clothTypesName));
			tr1.append(td4.append(InServiceVehicleDetailsList[i].vehicle_registration));
			tr1.append(td5.append(InServiceVehicleDetailsList[i].quantity));
			tr1.append(td6.append(InServiceVehicleDetailsList[i].lastModifiedOnstr));
			tr1.append(td7.append(InServiceVehicleDetailsList[i].lastModifiedBy));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);

}
