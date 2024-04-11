function showoption() {
    var t = $("#renPT_option :selected"),
        e = t.text();
    "CASH" == e ? $("#target1").text(e + " Receipt NO :") : $("#target1").text(e + " Transaction NO :")
}
$(document).ready(function() {
    $("#from").on("change", function() {
        var t = "",
            e = $(this).val();
        $("#from1 option").each(function() {
            e == $(this).val() && (t += '<option value="' + $(this).text() + '">' + $(this).text() + "</option>")
        }), $("#to").html(t), $("#hidden").hide()
    })
}), $(document).ready(function() {
    $("#renPT_option").on("change", function() {
        showoption()
    }), $("#renPT_option").change()
}), $(document).ready(function() {
    $("#renewal_from").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    }), $("#renewal_to").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    }), $("#paidDate").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    })
});