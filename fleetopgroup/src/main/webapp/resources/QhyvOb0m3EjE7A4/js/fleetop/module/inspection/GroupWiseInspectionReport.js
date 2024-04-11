$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}

),
$(function() {
    $("#vid").select2()
}

);

$(document).ready(function() {
	$("#Reportvehiclegroup").select2( {
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
	), $("#subscribe").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getUserEmailId_Subscrible", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.firstName+" "+e.lastName, slug: e.slug, id: e.user_id
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#inspectionParameter").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getInspectionParameterForDropDown.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.parameterName, slug: a.slug, id: a.inspectionParameterId
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
			$('button[id=btn-save]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  			=  $('#reportDate').val();
				jsonObject["vehicleGroupId"] 			=  $('#Reportvehiclegroup').val();
				jsonObject["inspectedBy"] 				=  $('#subscribe').val();
				jsonObject["frequency"] 				=  $('#frequency').val();
				jsonObject["inspectionParameter"] 		=  $('#inspectionParameter').val();
				jsonObject["testResult"] 				=  $('#testResult').val();
				
				
				if(Number($('#Reportvehiclegroup').val()) <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				
				if($('#reportDate').val() == ''){
					$('#reportDate').focus();
					showMessage('errors', 'Please Select Date!');
					return false;
				}
				
				
				showLayer();
				$.ajax({
			             url: "getGroupWiseInspectionReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
			});

		});

function renderReportData(data){
	if(data.parameterList != null && data.parameterList.length > 0){
		setHeaderData(data.company);
		setReportData(data);
		//setDynamicData(data);
	}else{
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
		showMessage('errors', 'No Record Found !');
	}
}
function setDynamicData(data){
	if(Number($('#subscribe').val() > 0)){
		var user = $('#subscribe').select2('data');
		$('#inspectedByVal').html(user.text);
		$('.inspectedBy').show();
		$('.inspectedByTable').hide();
	}else{
		$('.inspectedBy').hide();
	}
	
	if(Number($('#inspectionParameter').val() > 0)){
		var inspectionParameter = $('#inspectionParameter').select2('data');
		$('#inspectionParameterVal').html(inspectionParameter.text);
		$('.inspectionParam').show();
		$('.paramtable').hide();
	}else{
		$('.inspectionParam').hide();
	}
	
	if(Number($('#frequency').val() > 0)){
		$('#frequecyVal').html($('#frequency').val());
		$('.frequency').show();
		$('.frequencytable').hide();
	}else{
		$('.frequency').hide();
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
function setReportData(resultData) {
	$("#ResultContent").removeClass("hide");
	$("#printBtn").removeClass("hide");
	var parameterList	= null;
	var columnConfiguration ;
	var tableProperties;
	if(resultData.parameterList != undefined) {
		
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
		
		console.log('data.details ', resultData.details);
		console.log('vehicleGroup ', resultData.vehicleGroup);

		setSlickData(resultData.parameterList, columnConfiguration, tableProperties);
		
		
		$("#reportHeader").html("Group Wise Inspection Report");
		$('#reportRange').html(resultData.dateRange);
		if(resultData.vehicleGroup != undefined){
			$('#vehicleGroup').html(resultData.vehicleGroup.vGroup);
		}
		if(resultData.details != undefined){
			$('#testPassPer').html((resultData.details.testPassPercentage).toFixed(2));
			$('#testFailPer').html((resultData.details.testFailPercentage).toFixed(2));
			$('#notTestedPer').html((resultData.details.notTestPercentage).toFixed(2));
			
		}
		
		/*
		$("#batteryTransferDetails").empty();
		parameterList	= data.parameterList;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var vehicleTh		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="paramtable">');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th class="inspectedByTable">');
		var th8		= $('<th class="frequencytable">');
		//var th9		= $('<th>');
		

		th1.append('Sr No');
		vehicleTh.append('Vehicle');
		th2.append('Date');
		th3.append('Parameter Name');
		th4.append('Test Result');
		th5.append('Photo');
		th6.append('Remark');
		th7.append('Inspected By');
		th8.append('Frequency');
	//th9.append('Balance');

		tr1.append(th1);
		tr1.append(vehicleTh);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		//tr1.append(th9);

		thead.append(tr1);
		
		for(var i = 0; i < parameterList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var vehicletd		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="paramtable">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td  class="inspectedByTable">');
			var td8		= $('<td class="frequencytable">');
			
			
			
			var documentUrl	= '<a target="_blank" href="downloadParameterDocument/'+parameterList[i].documentId+'.in">'
								+'<span class="fa fa-download"> Document</span></a>';
					
			
			
			td1.append(i+1);
			vehicletd.append(parameterList[i].vehicle_registration);
			td2.append(parameterList[i].inspectionDateStr);
			td3.append(parameterList[i].parameterName);
			td4.append(parameterList[i].inspectionSucessStr);
			if(parameterList[i].documentId != null && parameterList[i].documentId > 0){
				td5.append(documentUrl);
			}else{
				td5.append("--");
			}
			
			td6.append(parameterList[i].description);
			td7.append(parameterList[i].inspectedBy);
			td8.append(parameterList[i].frequency);
		//	td9.append((tripSheetDtoList[i].tripTotalincome - tripSheetDtoList[i].tripTotalexpense).toFixed(2));
			
			tr.append(td1);
			tr.append(vehicletd);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			//tr.append(td9);
			
			
			tr.append(td6.append(totalKM));
			tr.append(td7.append(totalIncome));
			tr.append(td8.append(totalExpense));
			tr.append(td9.append(totalBalance));
			
			
			

			tbody.append(tr);
		}
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="5">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		

		
		$("#batteryTransferDetails").append(thead);
		$("#batteryTransferDetails").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	*/} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}
