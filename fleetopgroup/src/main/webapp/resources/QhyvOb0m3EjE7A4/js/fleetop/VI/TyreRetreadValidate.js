function visibility(e, t) {
    var n = document.getElementById(e),
        o = document.getElementById(t);
    "block" == n.style.display ? (n.style.display = "none", o.style.display = "block") : (n.style.display = "block", o.style.display = "none")
}

function toggle2Labor(e, t) {
    var n = document.getElementById(e),
        o = document.getElementById(t);
    "block" == n.style.display ? (n.style.display = "none", o.innerHTML = "Enter Retread Tyre") : (n.style.display = "block", o.innerHTML = "Cancel Retread Tyre")
}

function isLabertimeKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 46 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTYRE").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorTYRE").style.display = n ? "none" : "inline", n
}

function isLaberCostKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTYRE").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorTYRE").style.display = n ? "none" : "inline", n
}

function isLaberDisKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 46 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTYRE").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorTYRE").style.display = n ? "none" : "inline", n
}

function isLaberTaxKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 46 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTYRE").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorTYRE").style.display = n ? "none" : "inline", n
}

var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39);


function sumthere(e, t, n) {
    var o = document.getElementById(e).value,
        i = document.getElementById(t).value,
        r = parseFloat(o) * parseFloat(i);
    isNaN(r) || (document.getElementById(n).value = r.toFixed(2))
}

function sumthere(t, n, o, i) {
    var a = document.getElementById(t).value,
        l = document.getElementById(n).value,
        s = document.getElementById(o).value,
        u = parseFloat(a),
        c = u * l / 100,
        d = u - c,
        e = d * s / 100,
        m = d + e;
    isNaN(m) || (document.getElementById(i).value = m.toFixed(2))
}


function ScropToClickActive(e, t, k) {
	if(confirm('Do You Want To Reject Tyre')){
		/* reverse locking status */
		$('#'+t).eq(0).toggleClass('locked_inactive locked_active btn-danger btn-info');
		//$('#'+t).eq(1).toggleClass('unlocked_inactive unlocked_active btn-info btn-default');
		//5 for reject
		UpdateReceiveTyre(k, 5)
		document.getElementById(e).remove();
		$('#'+a).removeAttr("href");
		
	}
}
function ReceivedToClickActive(a, k, l) {
	if(confirm('Do You Want To Received Tyre')){
		/* reverse locking status */
		$('#'+a).toggleClass('locked_inactive locked_active btn-success btn-info');
		//$('#'+a).toggleClass('unlocked_inactive unlocked_active btn-success btn-info');
		// 4 for receive
		UpdateReceiveTyre(l, 4)
		document.getElementById(k).remove();
		
		$('#'+a).removeAttr("href");
		
	}
}


function UpdateReceiveTyre(TRAMOUNT, status) {
	var formData = "TRAID=" + TRAMOUNT + "&status=" + status + "";
	$.post("updateTyreReceived.in",
					formData,
					function(data) {
						if (data == "success") {
							//window.location.href = "<c:url value="/mailbox"></c:url>";
                             
							$("#success")
									.show()
									.html(
											"Retread Tyre has been update Successfully.<button class=\"close\" type=\"button\" onclick=\"closeArert()\">x</button>");
							location.reload();

						}
					}).fail(function(data) {
				if (data.responseJSON.error.indexOf("MailError") > -1) {
					$("#globalError").show().html('Mail id is empty');

				}
			});
}

function closeArert() {
	$("#success").css("display", "none");
	$("#successFormard").css("display", "none");

}

function edit_TyreRetreadInput(Amountid, TRID, tyreName, COST, DIS, TAX, TOTAL){
	document.getElementById("TR_AMOUNT_ID").value = Amountid;
	document.getElementById("TRID").value = TRID;
	document.getElementById("TYRE_NUMBER").value = tyreName;
	document.getElementById("laberhourscost").value = COST;
	document.getElementById("laberdiscount").value = DIS;
	document.getElementById("labertax").value = TAX;
	document.getElementById("totalLaborcost").value = TOTAL;
	document.getElementById("OLD_AMOUNT").value = TOTAL;
	$("#editTyreRetreadNumber").modal();
}

function validateAddPart(){
	
	var forPartId	= true;
	var quantity	= true;
	var unitprice	= true;
	var discount	= true;
	var tax			= true;
	
	 $('input[name*=tyreNumber_many]').each(function(i,obj){
		if($(this).val() == undefined || $(this).val() == ""){
			forPartId	= false;
			this.focus();
			return false;
		}
		
	 });
	 
	 if(!forPartId ) {
			showMessage('errors', 'Please Select TyreNumber!');
			return false;
		}
	
	 $('input[name*=quantity_many]').each(function(obj){
		 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
			 quantity = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!quantity ) {
		 console.log("QUAN")
			showMessage('errors', 'Please Select Quantity!');
			return false;
		}
	 
	 $('input[name*=unitprice_many]').each(function(obj){
		 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
			 unitprice = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!unitprice) {
			showMessage('errors', 'Please Select UnitPrice!');
			return false;
		}
	 $('input[name*=discount_many]').each(function(obj){
		 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
			 discount = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!discount) {
			showMessage('errors', 'Please Select Discount!');
			return false;
		}
	 $('input[name*=tax_many]').each(function(obj){
		 
		 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
			 tax = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!tax) {
			showMessage('errors', 'Please Select Tax!');
			return false;
		}
	 
	 
return true;
}