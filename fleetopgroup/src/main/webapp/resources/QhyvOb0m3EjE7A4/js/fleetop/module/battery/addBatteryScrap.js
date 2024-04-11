$(document).ready(function() {
	$("#batteryId").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		multiple:!0,
		ajax : {
			url : "getBatteryForScrap.in",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
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
	}), $("#warehouselocation").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getSearchPartLocations.in?Action=FuncionarioSelect2",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.partlocation_name,
							slug : e.slug,
							id : e.partlocation_id
						}
					})
				}
			}
		}
	}), $("#tyremodel").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getSearchTyreSubModel.in?Action=FuncionarioSelect2",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.TYRE_MODEL_SUBTYPE,
							slug : e.slug,
							id : e.TYRE_MST_ID
						}
					})
				}
			}
		}
	})
})