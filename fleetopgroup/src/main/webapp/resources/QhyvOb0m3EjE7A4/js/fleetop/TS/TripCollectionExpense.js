$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" required="required"></select></div><div class="col-md-2"><input type="number" class="form-text" name="Amount" min="0" id="Amount' + a + '" required="required" placeholder="Amount"></div><div class="col-md-2"><input type="text" class="form-text" name="expenseRefence" value="XO" placeholder="Reference"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
        	$("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#task" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense").empty(), $("#Expense").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    })
});

function validateExpenseAndAmount(){
	var noExpenseName	= false;
	var noAmount	= false;
	
	$('select[name*=expenseName]').each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 noExpenseName	= true;
			showMessage('errors', 'Please Select Expense Types!');
			return false;
		}
	
	});
	
	
	$('input[name*=Amount]').each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			noAmount	= true;
			showMessage('errors', 'Please Enter Amount !');
			return false;
		}
	
	});
	
	if(noAmount || noExpenseName){
		return false;
	}
	
	return true;

}