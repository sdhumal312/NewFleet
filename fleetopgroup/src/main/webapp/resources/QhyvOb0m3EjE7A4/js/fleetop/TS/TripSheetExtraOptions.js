$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
    	console.log("tripsheetoptionsId : "+t.tripsheetoptionsId)
    	console.log(t.tripsheetoptionsId)
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="tripsheetextraname" id="task' + a + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="TripSheetExtraQuantity" min="0" required="required" placeholder="Quantity"></div><div class="col-md-3"><input type="text" class="form-text" name="TripSheetExtraDescription" value="0" placeholder="Reference"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExtraOptionsList.in", function(e) {
            $("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ")), $.each(e, function(e, t) {
                $("#task" + a).append($("<option>").text(t.tripsheetextraname).attr("value", t.tripsheetoptionsId))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExtraOptionsList.in", function(e) {
        $("#Extra").empty(), $("#Extra").append($("<option>").text("Please Select ")), $.each(e, function(e, t) {
            $("#Extra").append($("<option>").text(t.tripsheetextraname).attr("value", t.tripsheetoptionsId))
        })
    })
});