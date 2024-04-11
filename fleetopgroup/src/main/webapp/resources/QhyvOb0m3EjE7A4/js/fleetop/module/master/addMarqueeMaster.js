$(document).ready(function() {
	var MarqueeList	= null;
	$.getJSON("getAllMarqueeMasterDetails.in", function(e) {
		MarqueeList	= e;
		if(MarqueeList == undefined || MarqueeList == ""){
			showMessage('info','No Record Found')
			$("#MarqueeMasterDetailsTable").hide();
		}

	var table 	= $("<table border='1' width='100%' style='margin-top:25px;font-size: 12px;font-weight:bold' id='marqueeMasterDetails' class='table'> ");
	var thead 	= $('<thead style="background-color: aqua;font-size: 15px;"  >');
	var tbody 	= $('<tbody>');
	var tr1 	= $('<tr style="font-weight: bold;height:25px;background-color:#fcf8e3;">');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');

	th1.append('Sr No');
	th2.append('Company');
	th3.append('Marquee Message');
	th4.append('Action');

	tr1.append(th1);
	tr1.append(th2);
	tr1.append(th3);
	tr1.append(th4);

	thead.append(tr1);
	
	for(var i = 0; i < MarqueeList.length; i++ ) {
		var tr = $('<tr border="1px" class="" style="height:25px;background-color:#d9edf7;" id="messageRow_'+MarqueeList[i].marquee_id+'">');

		var td1 	= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td id="marqueeMessageID_'+MarqueeList[i].marquee_id+'">');
		var td4		= $('<td>');
		
		td1.append(i+1);
		td2.append(MarqueeList[i].companyName);
		td3.append(MarqueeList[i].marquee_message);
		td4.append('<a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editMarqueeMaster" href="#" onclick=" editMessage('+MarqueeList[i].marquee_id+');" >Edit</a> <a class="btn btn-danger btn-sm" href="#" onclick="removefield('+MarqueeList[i].marquee_id+')">Delete</a>');
		
		tr.append(td1);
		tr.append(td2);
		tr.append(td3);
		tr.append(td4);
		
		tbody.append(tr);
	}
	
	table.append(thead);
	table.append(tbody);
	
	$("#MarqueeMasterDetailsTable").append(table)
	});	
	
});

function addMarqueeMaster(){
	
	var companyList	= null;
	
	$("#CompanyList").empty();

	var table 	= $("<table border='0' width='100%' style='margin-top:25px;' id='marqueeMasterTable'> ");
	var tr 		= $("<tr width ='100%' style='height:25px;'>");
	var td		= $("<td colspan='2'>");

	td.append('<select style="width: 100%;" name="companyName"  id="companyId" placeholder="Please Select Company" onchange="getPreviousMessage()"  ></select>');
	tr.append(td);

	$.getJSON("getCompanyInformationDetails.in", function(e) {
		companyList	= e;//To get All Company Name 
		$("#companyId").empty();
		$("#companyId").append($("<option>").text("All ").attr("value",0));
		$('#companyId').select2();

		for(var k = 0; k <companyList.length; k++){
			$("#companyId").append($("<option>").text(companyList[k].company_name).attr("value", companyList[k].company_id));
		}

	});	

	table.append(tr);
	$("#CompanyList").append(table);
	setTimeout(function(){
		getPreviousMessage();
	},200);
}

function saveMarqueeMessage(){
	
	var	jsonObject			= new Object();
	var company 			= $("#companyId").val();
	var messageID 			= $("#messageID").val();
	var colorId 			= $("#colorId").val();
	
	jsonObject				= new Object();
	
	jsonObject.company 		= company;
	jsonObject.message 		= messageID;
	jsonObject.colorId 		= colorId;
	
	
	if(company == undefined || company == null){
		showMessage('info','Please select Company');
		return false;
	}
	
	if(messageID == undefined || messageID == null || messageID == ""){
		showMessage('info','Please Enter The Marquee Message');
		return false;
	}
	
	$.ajax({
		url: "saveMarqueeMaster.do",//For Updating And Adding New fields
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Successfully Added..');
			location.reload();
			hideLayer();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
	
}


function validateMsg(event,id){/*
	console.log("event",event)
	console.log("id",id)

	var specialChars 	= [33,35,36,37,38,42,47,60,62,64,91,92,93,94,123,124,125];
	var messageStr		= document.getElementById('marquee'+id).value;

	var isValidMessage	= true;

	if($.inArray(event.which,specialChars) != -1) {
		isValidMessage = false;
		event.preventDefault();
	}

	if(messageStr.length > 0){
		for(var i = 0; i < messageStr.length; i++){
			var res = messageStr.charCodeAt(i);
			if($.inArray(res,specialChars) != -1 || res > 122) {
				isValidMessage = false;
				break;
			}
		}
	}

	if(!isValidMessage){
		//document.getElementById('marquee'+id).value = '';
		$('#marquee'+id).focus();
		showMessage('error','Not allowed')
		return false;
	}

*/}

function getPreviousMessage(){
	
	var	jsonObject						= new Object();
	var company 						= $("#companyId").val();
	
	jsonObject							= new Object();
	
	jsonObject.company 					= company;
	
	$.ajax({
		url: "getMessageByCompanyId.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setMarqueeMessage(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
	
}

function setMarqueeMessage(data){
	var regex 	= new RegExp(',');
	var message = data.messageList;
	
	$(".messageRow").remove();
	var i = 0;
	if(message != undefined){
		for( i; i < message.length; i++){
			if(message[i].marquee_message != undefined){
				var marqueeMessage 	= message[i].marquee_message.replace(regex, '');
				var tr 				= $("<tr width='100%' id='messageRow_"+message[i].marquee_id+"' style='height:25px;' class='messageRow'>");
				var td				= $("<td>");
				var td1				= $("<td>");
				td.append('<textarea style="width: 750px;  height: 30px;margin: 5px; margin-top: 15px;" name="comment"  maxlength="100"; disabled="" >  '+marqueeMessage+'</textarea><input type="color" id="" name="favcolor" disabled="disabled" value="#ff0000"></input>');
				td1.append('<a class="remove_field messageRow" onclick="removefield('+message[i].marquee_id+');" data-attr="removeNew"  id ="removeId_'+i+'" ><font color="FF00000"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>')
				tr.append(td);
				tr.append(td1);
				$("#marqueeMasterTable").append(tr);
			}
		}
	}
	
	if(i < 3){
		var tr 	= $("<tr width='100%' class='messageRow' style='height:25px;'>");
		var td	= $("<td>");
		td.append('<textarea style="width: 750px; height: 100px; margin: 5px;" name="comment" id="messageID" form="usrform"  maxlength="1000" placeholder="Please Enter Message Here" onpaste="validateMsg(event);" class="messageRow"></textarea><input type="color" id="colorId" name="favcolor" value="#ff0000">');
		tr.append(td);
		$("#marqueeMasterTable").append(tr);
		$("#saveMessage").show();
	} else {
		$("#saveMessage").hide();
	}
	
	
}

function removefield(marqueeId){
	var	jsonObject			= new Object();
	var company 			= $("#companyId").val();
	
	jsonObject				= new Object();
	
	jsonObject.marqueeId 	= marqueeId;
	$("#messageRow_"+marqueeId).remove();
	
	$.ajax({
		url: "removeMessage.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success',' Successfully Removed ..');
			hideLayer();
			getPreviousMessage();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
}


function editMessage(marqueeMessageId){
	$('#editMessageTable').empty();
	var	jsonObject			= new Object();
	var marqueeMessageval 	=	$("#marqueeMessageID_"+marqueeMessageId).text();
	
	
	var table 	= $("<table border='0' width='100%' style='margin-top:25px;' id='editMarqueeMasterTable'> ");
	var tr 		= $("<tr width ='100%' style='height:25px;'>");
	var td		= $("<td colspan='2'>");

	td.append('<textarea style="width: 750px;  height: 100px; margin: 5px;" name="comment" id="editMessageID_'+marqueeMessageId+'" maxlength="100" form="usrform" placeholder="Please Enter Message Here" onpaste="validateMsg(event);" class=""> '+marqueeMessageval+'</textarea><input type="color" id="colorId_'+marqueeMessageId+'" name="favcolor" value="#ff0000">');
	
	tr.append(td);
	table.append(tr);
	
	$('#updateMessage').val(marqueeMessageId);
	$('#editMessageTable').append(table);
	
}
function updateMarqueeMessage(obj){
	var marqueeMessageId 	= obj.value ;
	var message				= $('#editMessageID_'+marqueeMessageId).val();
	var colorId				= $('#colorId_'+marqueeMessageId).val();
	
	jsonObject						= new Object();
	
	jsonObject.marqueeId 			= marqueeMessageId;
	jsonObject.message 				= message;
	jsonObject.colorId 				= colorId;

	$.ajax({
		url: "updateMarqueeMessage.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success',' Successfully Updated ..');
			location.reload();
		},
		error: function (e) {
			showMessage('info', 'Some error occured!')
			hideLayer();
		}
	});
}

/*function blockSpecialChar(e){
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
    }*/
