$(document).ready(function(){
	$("#tripExpenseTable").DataTable({
		sScrollX:"100%",
		bScrollcollapse:!0,
		dom:"Blfrtip",
		buttons:["excel","print"]
	})
});