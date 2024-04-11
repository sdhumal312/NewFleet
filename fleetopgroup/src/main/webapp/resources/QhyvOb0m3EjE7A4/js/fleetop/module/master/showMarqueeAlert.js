$(document).ready(function() {
	if($('#stockAlert').val() == true || $('#stockAlert').val() == 'true'){
		stockalert();
	}
	var msgCount	= 0;
	var MarqueeList	= null;
	$.getJSON("MarqueeMasterRestController/getCompayWiseAlertMarqueeMessage.in", function(e) {
		MarqueeList	= e;
		
		if(MarqueeList[msgCount] != undefined && MarqueeList[msgCount].marquee_message != undefined){
			
			$('.mypopup').removeClass("hide");
			$('#Popup').modal('show');//new
			$('#myPopup').append(MarqueeList[msgCount].marquee_message.bold().fontsize(3).fontcolor(MarqueeList[msgCount].color_Id));
			
			msgCount++;
			
			$("#okPopup").click(function(){
				$('#myPopup').html("");
				if(MarqueeList[msgCount] != undefined && MarqueeList[msgCount].marquee_message != undefined){
					$('#myPopup').append(MarqueeList[msgCount].marquee_message.bold().fontsize(3).fontcolor(MarqueeList[msgCount].color_Id));
					msgCount++;
				} else {
					$('#myPopup').html("");
					 event.preventDefault();
				    $('.modal fade').hide("slow");
				    $('#Popup').modal('toggle');//new
				}
			})
		}
	});
	
	$('.closeBox').click(function (event) {
        event.preventDefault();
        $('.popup').hide("slow");
    });
	
});

function runDailyInspection(){
	var jsonObject			= new Object();
	jsonObject["batteryId"] =  $('#batteryId').val();
	$.ajax({
        url: "runDailyInspection",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	showMessage('success', 'Daily Inspection Added !')
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
});
}