
$(document).ready(function() {
	$("#userId").select2( {
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "/fleetopgroup/getUserEmailId_Assignto",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.firstName + " " + e.lastName,
                            slug: e.slug,
                            id: e.user_id
                        }
                    })
                }
            }
        }
    }
    )
});


function setNotificationAlertMsg(){
	if(document.getElementById('isNotifyYes').checked){
		$('#notificationRow').show();
		
	}else{
		$('#notificationRow').hide();
	}

}
function validateNotification(event){

	if($('#showNotifyUser').val() == 'true' || $('#showNotifyUser').val() == true){
		if(document.getElementById('isNotifyYes').checked){
			if(Number($('#userId').val()) <= 0){
				showMessage('info','Please select User to notify !');
				return false;
			}
			if($('#alertMsg').val() == ''){
				$('#alertMsg').focus();
				showMessage('info','Please Enter Notification Msg !');
				return false;
			}
		}
	}
	return true;
}