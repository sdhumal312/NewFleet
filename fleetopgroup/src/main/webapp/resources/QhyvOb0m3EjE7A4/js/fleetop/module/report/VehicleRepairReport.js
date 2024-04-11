$(document).ready(function() {
	$("#userId").select2( {
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getUserEmailId_Assignto",
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
