function showUserPopup(roleId){
	$('#configureUser').modal('show');	
var jsonObject					= new Object();	
jsonObject["roleId"] 			=  roleId;
	
$.ajax({
	url: "RolePermissionUserInfoWS/getRolePermissionUserInfo",
	type: "POST",
	dataType: 'json',
	data: jsonObject,
	success: function (data) {
		setUserInfo(data);
		hideLayer();
	},
	error: function (e) {
		showMessage('errors', 'Some error occured!')
		hideLayer();
	}
})

};




function setUserInfo(data){
	console.log('data',data)
	var roleUserInfo	= null;
	if(data.RoleUserInfo != undefined) {
		$("#reportHeader").html("User Details:");

		$("#userInfoDetails").empty();
		roleUserInfo	= data.RoleUserInfo;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		

		th1.append('Sr No');
		th2.append('First Name');
		th3.append('Last Name');
		th4.append('Email');
		th5.append('Mobile Number');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);

		thead.append(tr1);
		
		//var totalExpenseAmount=0;

		for(var i = 0; i < roleUserInfo.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			

			td1.append(i+1);
			td2.append(roleUserInfo[i].firstName);
			td3.append(roleUserInfo[i].lastName);
			td4.append(roleUserInfo[i].personal_email);
			td5.append(roleUserInfo[i].mobile_number);
			
			//totalExpenseAmount += tripCollectionExpenseName[i].expenseAmount;

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);

			tbody.append(tr);
		}
		
	/*	var tr2 = $('<tr>');

		var td1		= $('<td colspan="5">');
		var td2		= $('<td>');
		


		td1.append("Total :");
		td2.append(totalExpenseAmount.toFixed(2));
		

		tr2.append(td1);
		tr2.append(td2);
	

		tbody.append(tr2);*/

		$("#userInfoDetails").append(thead);
		$("#userInfoDetails").append(tbody);
		
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











