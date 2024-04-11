$(document).ready(function() {
	$("#fromLocationId").select2({
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
  }), $("#toLocationId").select2({
      minimumInputLength:2, minimumResultsForSearch:10, ajax: {
          url:"getAllPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
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
  }), $("#batteryId").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		multiple:!0,
		ajax : {
			url : "getBatteryForTransfer.in",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				if(Number($('#fromLocationId').val()) <= 0){
					showMessage('errors','Please select Transfer From Location !');
				}
				return {
					term : e,
					fromLocation : $("#fromLocationId").val()
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.batterySerialNumber,
							slug : e.slug,
							id : e.batteryId
						}
					})
				}
			}
		}
	})
});