var renewalIds = new Array();
function countCheck(id,flag){
	if(flag){
		renewalIds.push($("#"+id).val());
	} else {
		renewalIds.indexOf($("#"+id).val()) != -1 && renewalIds.splice(renewalIds.indexOf($("#"+id).val()), 1)
	}
	$("#Selectfuel_id").val(renewalIds);
}