var dataTableInitialized = false;
$(function() {
	function a(a, b) {
		$("#dateRangeReport").val(b.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	
	}, a);
	
});
$(document).ready(function() {
	$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	})
});

jQuery(document).ready(
		function($) {
			$("#btn-search").click(function(event) {
				
				if(Number($("#selectVendor").val()) <= 0){
					$('#selectVendor').select2('focus');
					showMessage('info', 'Please select vendor !');
					return false;
				}
				
				showLayer();
				
				
				var jsonObject			= new Object();

				jsonObject["vendorId"]		= $("#selectVendor").val();
				jsonObject["dateRange"]		= $("#dateRangeReport").val();

				$.ajax({
					url: "getVendorApprovalForPayment.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						console.log('data : ', data);
						setApprovalEntriesList(data);
						hideLayer(); 
					},
					error: function (e) {
						console.log("Error : " , e);
					}
				});
			});

		});


function setApprovalEntriesList(data) {
	
	if(data.approvalList != undefined && data.approvalList.length > 0) {
		var approval = data.approvalList;
		$("#advanceTableHead").empty();
		$("#advanceTableBody").empty();
		var tr1 = $('<tr>');

		var th1		= $('<th class="fit ar">');
		var th2		= $('<th class="fit ar">');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th class="fit ar">');
		var th5		= $('<th class="fit ar">');
		var th6		= $('<th class="fit ar">');
		var th7		= $('<th class="fit ar">');
		var th8		= $('<th class="fit ar">');
		var th9		= $('<th class="fit ar">');
		var th10	= $('<th class="fit ar">');

		tr1.append(th1.append("Approval Id"));
		tr1.append(th2.append("Vendor Name"));
		tr1.append(th3.append("Vendor Type"));
		tr1.append(th4.append("Create Date"));
		tr1.append(th5.append("Approved By"));
		tr1.append(th6.append("Invoice Amount"));
		tr1.append(th7.append("Approved Amount"));
		tr1.append(th8.append("Status"));
		tr1.append(th9.append("Action"));

		$("#advanceTableHead").append(tr1);
		for(var i = 0; i < approval.length; i++) {
			var trBody = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			
			trBody.append(td1.append('<a href="ShowApproval.in?approvalId='+approval[i].approvalId+'" target="_blank">A-'+approval[i].approvalNumber+'</a>'));
			
			var curl = "ShowVendor.in?vendorId="+approval[i].approvalvendorID
			trBody.append(td2.append('<a target="_blank" href="' + curl + '">'+approval[i].approvalvendorName+'</a><br>'));
			
			trBody.append(td3.append(approval[i].approvalvendorType));
			trBody.append(td4.append(approval[i].approvalCreateDateStr));
			trBody.append(td5.append(approval[i].approvalCreateBy));
			trBody.append(td6.append(approval[i].approvalTotal));
			trBody.append(td7.append(approval[i].subApprovalpaidAmount));
			trBody.append(td8.append(approval[i].approvalStatus));	
			trBody.append(td9.append('<a class="btn btn-success btn-sm" href="approvedPayment.in?approvalId='+approval[i].approvalId+'" onclick="return confirm("Are you sure? Payment")"><span class="fa fa-cog"> Make Payment</span></a>'));	
			
			$("#advanceTableBody").append(trBody);
		}
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	}else{
		showMessage('info','No record found !')
	}
}
