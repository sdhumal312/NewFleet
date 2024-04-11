var e = 25,
t = $(".input_fields_wrap"),
n = $(".add_field_button"),
a = 1;
$(document).ready(function() {
    $(n).click(function() {
    	addExpense();
    }), $(t).on("click", ".remove_field", function(e) {
    	removeExpense();
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense").empty(), $("#Expense").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    })
    
});

function addExpense(){
	 e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="task' + a + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="Amount" min="0" id="Amount' + a + '" required="required" placeholder="Amount"></div><div class="col-md-3"><input type="text" class="form-text" name="expenseRefence" value="0" placeholder="Reference"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
		$("#task" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
			$("#task" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
		})
	}), $(".select2").select2())
}

function removeExpense(){
	$(".remove_field").each(function(i){
		if(a - 2 == i)
		$(this).parent("div").remove(), a--;
	});
}

function expensePopUp(tripSheetId, expenseId) {

	if(tripSheetId > 0){
		
		var jsonObject	= new Object();
		jsonObject["tripSheetId"] =  tripSheetId;
		jsonObject["expenseId"] =  expenseId;
				
		showLayer();
		$.ajax({
			url: "getExpenseCombineDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			setModelExpenseCombineDeatils(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
	
}

function setModelExpenseCombineDeatils(data) {
var expenseList	= null;
if(data.ExpenseList != undefined) {
	$("#modelBodyTollExpense").html("Toll Expense Report");

	$("#modelBodyExpenseDetails").empty();
	expenseList	= data.ExpenseList;
	var thead = $('<thead style="background-color: aqua;">');
	var tbody = $('<tbody>');
	var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	

	th1.append('Sr No');
	th2.append('Expense Name');
	th3.append('Type');
	th4.append('Place');
	th5.append('Ref');
	th6.append('Expense Date');
	th7.append('Amount');
	

	tr1.append(th1);
	tr1.append(th2);
	tr1.append(th3);
	tr1.append(th4);
	tr1.append(th5);
	tr1.append(th6);
	tr1.append(th7);

	thead.append(tr1);
	
	var totalExpenseAmount=0;

	for(var i = 0; i < expenseList.length; i++ ) {
		var tr = $('<tr>');

		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		
		
		td1.append(i+1);
		td2.append(expenseList[i].expenseName); 
		if(expenseList[i].expenseFixed == 'FIXED'){
			td3.append('<a class="label label-success"</a>'+expenseList[i].expenseFixed);
		} else {
			td3.append('<a class="label label-warning"</a>'+expenseList[i].expenseFixed);
		}
		td4.append(expenseList[i].expensePlace);
		td5.append(expenseList[i].expenseRefence);
		td6.append(expenseList[i].createdStr);
		td7.append(expenseList[i].expenseAmount);
		
		totalExpenseAmount += expenseList[i].expenseAmount;

		tr.append(td1);
		tr.append(td2);
		tr.append(td3);
		tr.append(td4);
		tr.append(td5);
		tr.append(td6);
		tr.append(td7);

		tbody.append(tr);
	}
	
	var tr2 = $('<tr>');

	var td1		= $('<td colspan="6">');
	var td2		= $('<td>');
	


	td1.append("Total :");
	td2.append(totalExpenseAmount.toFixed(2));
	

	tr2.append(td1);
	tr2.append(td2);


	tbody.append(tr2);
	
	$('#ExpenseCombineDetails').modal('show');
	$("#modelBodyExpenseDetails").append(thead);
	$("#modelBodyExpenseDetails").append(tbody);
	
	/*$("#ResultContent").removeClass("hide");
	$("#printBtn").removeClass("hide");
	$("#exportExcelBtn").removeClass("hide");*/
} else {
	showMessage('info','No record found !');
	/*$("#ResultContent").addClass("hide");
	$("#printBtn").addClass("hide");
	$("#exportExcelBtn").addClass("hide");*/
}
setTimeout(function(){ hideLayer(); }, 500);
}

var allowShortCut = $('#allowShortCut').val();

if(allowShortCut == 'true'){
	document.addEventListener('keyup', doc_keyUp, false); 	// register the handler 
	
	function doc_keyUp(e) {
	    
		if(e.altKey && e.which == 78) {						// this would test for whichever key is 78(N) and the alt key at the same time
			addExpense();
			return false;
		}
		else if(e.altKey && e.which == 82){
			removeExpense();
			return false;
		}
	}
	
} else {
	document.removeEventListener("keyup", doc_keyUp); 
}
