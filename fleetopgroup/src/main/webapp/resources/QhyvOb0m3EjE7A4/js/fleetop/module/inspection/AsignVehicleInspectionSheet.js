$(document).ready(function() {
	 $("#inspectionSheetId").select2( {
        ajax: {
            url:"getInspectionSheetList.in", dataType:"json", type:"POST", contentType:"application/json", data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.inspectionSheetName, slug: e.slug, id: e.vehicleInspectionSheetId
                        }
                    }
                    )
                }
            }
        }
    }
    )

});