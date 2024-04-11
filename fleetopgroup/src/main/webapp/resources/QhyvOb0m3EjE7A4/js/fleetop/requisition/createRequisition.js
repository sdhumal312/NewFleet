$(document).ready(function($){
	$('button[id=submit]').click(function(e) {
		showLayer();
	e.preventDefault();
	var validateFields = true;
	
	if($('#location').val() == '' || $('#location').val() <= 0 ){
		$('#location').addClass('showError');
		$('#s2id_location').addClass('showError');
		validateFields = false;
	}
	if($('#assignToId').val() == '' || $('#assignToId').val() <= 0 ){
		$('#assignToId').addClass('showError');
		$('#s2id_assignToId').addClass('showError');
		validateFields = false;
	}
//	if($('#refNumber').val() == '' || ($('#refNumber').val()).trim() == '' ){
//		$('#refNumber').addClass('showError');
//		validateFields = false;
//	}
//	if($('#remark').val() == '' || ($('#remark').val()).trim() == '' ){
//		$('#remark').addClass('showError');
//		validateFields = false;
//	}
	if($('#requireDate').val() == '' || ($('#requireDate').val()).trim() == '' ){
		$('#requireDate').addClass('showError');
		validateFields = false;
	}

	$('select[name=requisitionType]').each(function(){
		if($(this).val() == null || $(this).val() == undefined || $(this).val() <=0 ){
			$('#'+this.id).addClass('showError');
			$('#s2id_'+this.id).addClass('showError');
			validateFields= false;
		}else{
			if(!validateMultiType(this.id ,$(this).val())){
				validateFields= false;
			}
		}
	});
	
	if(!validateFields){
		$('.showError').on('click',function(){
			$(this).removeClass('showError');
		});
		$('.showError').on('change',function(){
			$(this).removeClass('showError');
			var removeCls =$(this).prev();
			removeCls.removeClass('showError');
		});
		hideLayer();
		showMessage('info', 'Please fill highlighted fields To process !!');
		return false;
	}
	
	let partObjectArr = new Array();
	let tyreObjectArr = new Array();
	let battaryObjectArr = new Array();
	let clothObjectArr = new Array();
	let ureaObjectArr = new Array();
	
	$('select[name=requisitionType]').each(function(){
		var thisVal = $(this).val();
		var temp=prepareObject(this.id ,thisVal);
		if(thisVal == 1){
			partObjectArr.push(temp);
		}else if(thisVal == 2){
			tyreObjectArr.push(temp);
		}else if(thisVal == 3){
			battaryObjectArr.push(temp);
		}else if(thisVal == 4){
			clothObjectArr.push(temp);	
		}else if(thisVal == 5){
			ureaObjectArr.push(temp);	
		}
	});
	
	let jsonObject = new Object();
	
	jsonObject['location']		= $('#location').val();
	jsonObject['assignToId']	= $('#assignToId').val();
	jsonObject['refNumber']		= $('#refNumber').val();
	jsonObject['remark']		= $('#remark').val();
	
	var requireDate =  ($('#requireDate').val()).trim();
	jsonObject['requireDate']	= requireDate;
	
	jsonObject.partList = JSON.stringify(partObjectArr);
	jsonObject.tyreList = JSON.stringify(tyreObjectArr);
	jsonObject.battaryList = JSON.stringify(battaryObjectArr);
	jsonObject.clothList = JSON.stringify(clothObjectArr);
	jsonObject.ureaList = JSON.stringify(ureaObjectArr);
	
	$.ajax({
		url: "saveRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.sequenceCounterNotFound != undefined &&(data.sequenceCounterNotFound == true || data.sequenceCounterNotFound ==='true')){
				showMessage('errors', 'Sequence Not found !contact your administrator');
			}
			if(data.saved != undefined && (data.saved == true || data.saved === 'true')){
				window.location.replace("showRequisition?requisitionId="+data.requisitionId+"");
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
})
})

function validateDublicate(data){
	if($(data).val() > 0){
	$('input[name='+data.name+']').each(function(){
		if(data.id != this.id && $(this).val() == $('#'+data.id).val()){
			$('#'+data.id).select2('val','');
			$('#'+data.id).trigger("change");
			showMessage('info', 'Duplicate Entry !!');
		}
	});
	}
}

function validateSelectDublicate(data){
	if($(data).val() > 0){
	$('select[name='+data.name+']').each(function(){
		if(data.id != this.id && $(this).val() == $('#'+data.id).val()){
			$('#'+data.id).select2('val','');
			$('#'+data.id).trigger("change");
			showMessage('info', 'Duplicate Entry !!');
		}
	});
	}
}

