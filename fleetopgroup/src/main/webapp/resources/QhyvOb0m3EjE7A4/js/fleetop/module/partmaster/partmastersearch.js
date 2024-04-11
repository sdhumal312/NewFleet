$(document).ready(function() {
$('#vehicleMake').on('change', function() {
         $("#vehicleModel").select2("val", "");
	});
	$("#vehicleMake").select2({
		multiple:!0,
		ajax : {
            url:"vehicleManufacturerAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
            	$("#vehicleModel").select2('data', '');
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleManufacturerName, slug: t.slug, id: t.vehicleManufacturerId
                        }
                    }
                    )
                }
            }
        }
	
	}),$("#vehicleModel").select2({
		multiple:!0,
		ajax : {
            url:"vehicleModelAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t,
                    manufacturer : $('#vehicleMake').val()
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleModelName, slug: t.slug, id: t.vehicleModelId
                        }
                    }
                    )
                }
            }
        }
	
	})
});