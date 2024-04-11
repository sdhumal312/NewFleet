
$(document).ready(function() {
	
	   $("#subscribe").select2({
	        minimumInputLength: 0,
	        minimumResultsForSearch: 10,
	        multiple: 0,
	        ajax: {
	            url: "getUserListForActivity",
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
	                            text: a.firstName + " " + a.lastName,
	                            slug: a.slug,
	                            id: a.user_id
	                        }
	                    })
	                }
	            }
	        }
	    })
})


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
});

function validateOnchange(){
	 if($('#subscribe').val() == undefined || $('#subscribe').val() == ''){
		 hideLayer();
			showMessage('errors', 'Please Select User First !!');
			return false;
	 }
	 
	 if($('#dateRange').val() == undefined || $('#dateRange').val() == ''){
		 	hideLayer();
			showMessage('errors', 'Please Select Date First !!');
			
			return false;
		 
	 }
	
	 return true;
}

function backGroundColour(activityType){
	
	$('#backgroudColourC').addClass('bg-gradient-blueOne').removeClass('bg-gradient-primary');
	$('#backgroudColourM').addClass('bg-gradient-blueOne').removeClass('bg-gradient-primary');
	$('#backgroudColourD').addClass('bg-gradient-blueOne').removeClass('bg-gradient-primary');
	
	if(activityType == 1){
		$('#backgroudColourC').addClass('bg-gradient-primary').removeClass('bg-gradient-blueOne');
	}
	if(activityType == 2){
		$('#backgroudColourM').addClass('bg-gradient-primary').removeClass('bg-gradient-blueOne');
	}
	if(activityType == 3){
		$('#backgroudColourD').addClass('bg-gradient-primary').removeClass('bg-gradient-blueOne');
	}
	
}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}
