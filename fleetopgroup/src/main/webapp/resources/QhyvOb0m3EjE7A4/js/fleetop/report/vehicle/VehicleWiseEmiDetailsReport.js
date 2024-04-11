var EMI_STATUS_SETTLEMENT = 3;
$(document).ready(function() {
	$("#vehicleId").select2( {
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
    })
    
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();				
				jsonObject["vehicleId"] 					= $('#vehicleId').val();
				
				if($('#vehicleId').val() ==  "" || $('#vehicleId').val() == null || $('#vehicleId').val() == undefined){
					showMessage('info','Please Select Vehicle ')
					hideLayer();
					return false;
				}
				
				$.ajax({					
					url: "getVehicleWiseEmiDetails",
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
	var vehicleEmiHM = resultData.vehicleEmiPaymentDetailsHM;
	var finalList = resultData.allVehiclesEmiDetails;
	if((resultData.finalObjectList).length > 0 || finalList != undefined ){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		
		
		var data = $('#vehicleId').select2('data');
		if(data){
			/*var tr;
			console.log("finalList",finalList)
			for(var i = 0; i < finalList.length; i++ ) {
			var tr ='<table style="border:1px solid;">'
				       +'<tr data-object-id="">'
						+'<td class="fit"> Vehicle: '+data.text+'</td>'
					+'</tr>'
					+'</table>';
			 tr	=	'<tr>'
						+'<td>'+finalList[i].bankName+'</td>'
						+'<td>'+finalList[i].loanAmount+'</td>'
						+'<td>'+finalList[i].downPaymentAmount+'</td>'
						+'<td>'+finalList[i].tenure+'</td>'
						+'</tr>'
			}
			var mainTable ='<table style="border:1px solid;">'
				+' '+tr+''
				+'</table>';
			$('#selectedReportDetails').append(mainTable);*/
			
			var finalCompany	= $('<tr><td colspan="5" align="center" style="font-size: 18px;font-weight: bold;" >'+companyName+' </td>	</tr>');
			var reportHeader 	= $('<tr><td colspan="5" align="center" style="font-size: 18px">Vehicle Wise EMI Details Report </td>	</tr>');
			var tr = $('<tr>');
			var tbody = $('<tbody>');
			var tr1 	= $('<tr>');
			var th1		= $('<th style="padding:5px; text-align: center;">');
			var th2		= $('<th style="padding:5px; text-align: center;">');
			var th3		= $('<th style="padding:5px; text-align: center;">');
			var th4		= $('<th style="padding:5px; text-align: center;">');
			var th5		= $('<th style="padding:5px; text-align: center;">');
			
			th1.append('BANK NAME');
			th2.append('LOAN AMOUNT');
			th3.append('DOWNPAYMENT');
			th4.append('PAID EMI');
			th5.append('INTEREST RATE');
			
			tr1.append(th1);
			tr1.append(th2);
			tr1.append(th3);
			tr1.append(th4);
			tr1.append(th5);
			
			tbody.append(finalCompany);
			tbody.append(reportHeader);
			tbody.append(tr1);
			
			if(finalList != undefined){
				for(var i = 0; i < finalList.length; i++ ) {
					
					var tr = $('<tr>');
	
					var td1 	= $('<td style="padding:2px; text-align: center;">');
					var td2		= $('<td style="padding:2px; text-align: center;">');
					var td3		= $('<td style="padding:2px; text-align: center;">');
					var td4		= $('<td style="padding:2px; text-align: center;">');
					var td5		= $('<td style="padding:2px; text-align: center;">');
					
					td1.append(finalList[i].bankName);
					td2.append(finalList[i].loanAmount);
					td3.append(finalList[i].downPaymentAmount);
					if(finalList[i].paidEmi != null){
						if(finalList[i].emiStatus == EMI_STATUS_SETTLEMENT){
							td4.append(finalList[i].paidEmi+"/"+finalList[i].tenure+"("+finalList[i].paymentStatus+")");//finalList[i].paidEmi+"/"+
						}else{
							td4.append(finalList[i].paidEmi+"/"+finalList[i].tenure);//finalList[i].paidEmi+"/"+
						}
					}else{
						td4.append("0/"+finalList[i].tenure);//finalList[i].paidEmi+"/"+
					}
					td5.append(finalList[i].interestRate);
					
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
					tr.append(td5);
					
					tbody.append(tr);
				}
			}
			
			/*var tr123 ='<tbody><tr data-object-id="">'
					+'<td class="fit"> Vehicle: '+tbody+'</td>'
				+'</tr></tbody>';
			*/
			
			$('#selectedReportDetails').append(tbody);
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
		
		setSlickData(resultData.finalObjectList, columnConfiguration, tableProperties);
		/*  dataView.setGrouping({
			    getter: 9,
			    formatter: function (g) {
			    	console.log("gg>>",g)
			    	return  g.value + "  <span style='color:green'>(" + g.count + " rows)</span>";
			    },
			    //aggregators: columnsArr,
			    aggregateCollapsed: false,
			    lazyTotalsCalculation: true
			  });*/
		
		/*
		((GridViewDataColumn)ASPxGridView1.Columns["City"]).GroupBy();
		ASPxGridView1.GroupBy(ASPxGridView1.Columns["Country"], 0);
		*/
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}
var companyName;
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
		companyName = company.company_name;
		//$('#companyName').html(company.company_name);
	}
}






