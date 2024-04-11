$(document).ready(function() {
	$("#ureaRequiredLocationId").select2({
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }),$("#ureaTransferFromLocationId").select2({
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }), $("#ureaRequisitionReceiverId").select2( {
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
    })
 });


function validateRequisition(){
	if($('#ureaRequiredLocationId').val() == ""  || $('#ureaRequiredLocationId').val() == undefined){
		showMessage('info','Please Select Required Location')
		hideLayer();
		return false;
	}
	if($('#ureaTransferFromLocationId').val() == ""  || $('#ureaTransferFromLocationId').val() == undefined){
		showMessage('info','Please Select From Location')
		hideLayer();
		return false;
	}
	if($('#ureaRequisitionReceiverId').val() == ""  || $('#ureaRequisitionReceiverId').val() == undefined){
		showMessage('info','Please Select Requisition Receiver')
		hideLayer();
		return false;
	}
	if($('#ureaRequiredDate').val() == ""  || $('#ureaRequiredDate').val() == undefined){
		showMessage('info','Please Select Required Date')
		hideLayer();
		return false;
	}
	if($('#ureaRequiredQuantity').val() == ""  || $('#ureaRequiredQuantity').val() == undefined){
		showMessage('info','Please Select Required Liters')
		hideLayer();
		return false;
	}
	
	
	return true;
}