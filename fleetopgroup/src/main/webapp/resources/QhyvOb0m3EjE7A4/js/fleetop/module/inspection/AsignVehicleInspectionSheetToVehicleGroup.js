$(document).ready(function(){
    $("#VehicleTypeSelect").select2( {
        ajax: {
            url:"getVehicleType.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vtype, slug: t.slug, id: t.tid
                        }
                    }
                    )
                }
            }
        }
    }
    )
    
    	 $("#VehicleLocation").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getIssuesBranch.in?Action=FuncionarioSelect2",
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
	                            text: e.branch_name + " - " + e.branch_code,
	                            slug: e.slug,
	                            id: e.branch_id
	                        }
	                    })
	                }
	            }
	        },
	        
	    });
    
    var sheetId=$('#sheetId').val();
    if(sheetId != undefined && sheetId != 'undefined' && sheetId != ""){
    	setTimeout(function(){
    		$('#inspectionSheetId').select2('data',{
    			id :sheetId ,
    			text :$('#sheetName').val()
    		});
    		
    	}, 500);
    }

    var info =$("#info").val();
    if(info === 'success'){
    	swal({
    		title: "Success!",
    		text:"Inspection Sheet assinged Succesfully",
    		type: "success",
    		width: 300,
    		height: 200,
    		showCancelButton: !0,
    		showConfirmButton: 0,
    		confirmButtonClass: "btn btn-danger",
    		cancelButtonClass: "btn btn-danger m-l-10",
    		confirmButtonText: "OK",
    		preConfirm: function () {

    		}
    	})
    }
    
    
    if(info === 'AlreadyAssigned'){
    	swal({
    		title: "Already Assigned!",
    		text:"Inspection Sheet Already Assigned",
    		type: "warning",
    		width: 300,
    		height: 200,
    		showCancelButton: !0,
    		showConfirmButton: 0,
    		cancelButtonClass: "btn btn-danger",
    		preConfirm: function () {
    		}
    	})
    }
    
    
    if(info === 'emptySheetAssignment'){
    	swal({
    		title: "Empty Sheet!",
    		text:"Empty Sheet Assignment",
    		type: "warning",
    		width: 300,
    		height: 200,
    		showCancelButton: !0,
    		showConfirmButton: 0,
    		cancelButtonClass: "btn btn-danger",
    		preConfirm: function () {
    		}

    	})

    }
    
    
    
    if(info === 'NoVehicleFound'){
    	swal({
    		title: "No Vehicle Found!",
    		text:"No Vehicle Found with selected Vehicle Type",
    		type: "question",
    		width: 300,
    		height: 200,
    		showCancelButton: !0,
    		showConfirmButton: 0,
    		cancelButtonClass: "btn btn-danger",
    		preConfirm: function () {
    		}

    	})

    }
    
	
});

function validateVehicleTypeAssignment(){
	
	if(Number($('#VehicleTypeSelect').val()) <= 0){
		$('#VehicleTypeSelect').select2('focus');
		showMessage('info', 'Please select vehicle type !');
		$('#VehicleTypeSelect').select2('focus');
		return false;
	}
	
	if($('#inspectionStartDateStr').val() == undefined || $('#inspectionStartDateStr').val() == null || $('#inspectionStartDateStr').val().trim() == ''){
		$('#inspectionStartDateStr').focus()
		showMessage('info', 'Please select start date !');
		return false;
	}
	
	if(Number($('#VehicleLocation').val()) <= 0){
		showMessage('info', 'Please select Branch !');
		$('#VehicleLocation').select2('focus');
		return false;
		
	}
	
 return true;
}
