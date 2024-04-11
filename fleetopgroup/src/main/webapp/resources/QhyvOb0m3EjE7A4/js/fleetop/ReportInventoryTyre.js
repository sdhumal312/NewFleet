$(document).ready(function() {
	$("#Tyre_ID").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		multiple:!0,
		ajax : {
			url : "getTyreID.in",
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
							text : e.TYRE_NUMBER,
							slug : e.slug,
							id : e.TYRE_ID
						}
					})
				}
			}
		}
	}),
	$("#manufacurer").select2({
		minimumInputLength : 2, 
		minimumResultsForSearch : 10,
		ajax : {
			url : "getSearchTyreModel.in",
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
							text : e.TYRE_MODEL,
							slug : e.slug,
							id : e.TYRE_MT_ID
						}
					})
				}
			}
		}
	}), $("#tyreSize").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
							text : e.TYRE_SIZE,
							slug : e.slug,
							id : e.TS_ID
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
}), $(document).ready(function() {
	$("#selectVendor").select2({
		minimumInputLength : 3,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getTyreVendorSearchList.in?Action=FuncionarioSelect2",
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
							text : e.vendorName + " - " + e.vendorLocation,
							slug : e.slug,
							id : e.vendorId
						}
					})
				}
			}
		}
	})
});