	jQuery(document).ready(
		function($) {

			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["ATTENDANCE_DATE"] = $('#GTC_daterange').val();
				jsonObject["DRIVER_GROUP_ID"] =  $('#SelectFuelGroup').val();
				jsonObject["DRIVER_JOBTITLE"] =  $('#AttGroupDriverJob_ID').val();
				
				if($('#SelectFuelGroup').val() <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				if($('#AttGroupDriverJob_ID').val() <= 0){
					showMessage('errors', 'Please Select JOb Type!');
					return false;
				}
				showLayer();
				$.ajax({
			             url: "getAttandanceReprtAjax",
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
		if(data.attandanceList != null && data.attandanceList.length > 0){
			setHeaderData(data.company);
			setReportData(data);
		}else{
			showMessage('errors', 'NO Record Found!');
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
	function setReportData(data){
		//$('#advanceTable').empty();
		$("#advanceTable tr").remove();
		if(data.attandanceList != null){
			$('#advanceTable').show();
			$('#reportDetails').html('Depot wise Attendance Report '+data.SearchGroup+' - '+data.SearchDate);
			var srNo = 1;
			 var tHead = '<tr><th class="fit">No</th><th>Emp</th><th>Driver</th><th>Job</th><th>T.DUTY</th><th>E.DUTY</th><th>ALL.DUTY</th></tr>';
			 $('#tHeadId').append(tHead);
			 for(var i = 0; i< data.attandanceList.length; i++){
				var attandance = data.attandanceList[i];
				var tr =' <tr data-object-id="">'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td  value="'+attandance.driver_empnumber+'">'+attandance.driver_empnumber+'</td>'
					+'<td  value="'+attandance.driver_NAME+'">'+attandance.driver_NAME+'</td>'
					+'<td  value="'+attandance.driverJobType+'">'+attandance.driverJobType+'</td>'
					+'<td  value="'+attandance.totalWorkingDays+'">'+attandance.totalWorkingDays+'</td>'
					+'<td  value="'+attandance.extraWorkingDays+'">'+attandance.extraWorkingDays+'</td>'
					+'<td  value="'+attandance.allWorkingDays+'"><a target="_blank" '
					+'href="/GetDriverAttReport.in?DID='+attandance.driverid+'&DATE='+data.SearchDate+'"'
							+'data-toggle="tip"'
							+'data-original-title="Click Attandance Details">'+attandance.allWorkingDays+' </a></td>'
					+'</tr>';
				$('#tableBody').append(tr);
				srNo++;
			}
		}
	}