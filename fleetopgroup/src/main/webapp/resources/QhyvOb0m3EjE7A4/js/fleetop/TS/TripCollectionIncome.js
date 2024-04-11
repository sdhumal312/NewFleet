$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        n = 1;
    $(c).click(function(c) {
        if (c.preventDefault(), e > n) {
            n++;
            $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="incomeName" id="task' + n + '" required="required"></select></div><div class="col-md-2"><input type="number" class="form-text" name="Amount" id="Amount' + n + '" min="0" required="required" placeholder="Amount"></div><div class="col-md-2"><input type="text" class="form-text" name="incomeRefence" value="X0" placeholder="Reference"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("tripIncomeList.in", function(e) {
                $("#task" + n).empty(), $("#task" + n).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                    $("#task" + n).append($("<option>").text(t.incomeName).attr("value", t.incomeID))
                })
            });
            $(".select2").select2()
        }
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), n--
    }), $.getJSON("tripIncomeList.in", function(e) {
        $("#Income").empty(), $("#Income").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Income").append($("<option>").text(t.incomeName).attr("value", t.incomeID))
        })
    })
});