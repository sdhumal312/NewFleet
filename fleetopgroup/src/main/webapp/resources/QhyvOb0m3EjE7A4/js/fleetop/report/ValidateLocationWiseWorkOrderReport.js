function validateLocationWiseWorkOrderReport()
{
	if(Number($('#workOrderLocation').val()) <= 0){
		showMessage('info','Please Enter Warehouse Location !');
		return false;
	}	
}
$("#selectAll").change(function() {
	if($("#selectAll").prop("checked")){
		$("input[name=selectWorkOrder]").each(function(){
			$(this).prop('checked', true);
		})
	}else{
		$("input[name=selectWorkOrder]").each(function(){
			$(this).prop('checked', false);
		})
	}
})

$(document).ready(
		function($) {
			$('button[id=approveWorkorders]').click(function(e) {
				showLayer();
				var jsonObject			= new Object();
				var workorderArray		= new Array();
				
				$(':checkbox:checked').each(function(i){
					if($(this).val() != ""){
						workorderArray.push($(this).val());
					}
				  });
				var jsonObject						= new Object();
			
				jsonObject["workorderId"]			= workorderArray.toString();
				jsonObject["approvalStatusId"]		= 1;
				
				if(workorderArray.toString() == ""){
					showMessage('info','Please Select WO For Approval')
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "approveWorkOrderDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						showMessage('info','Approved Successfully')
						window.location.replace("LocationWorkOrderReport");
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
				
			})
		});


