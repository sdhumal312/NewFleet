function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}
function deleteBatteryInventory(){
	showLayer();
	var jsonObject			= new Object();
	var invoiceId 		 	= Number(getUrlParameter('Id'));
	jsonObject["batteryInvoiceId"] 	  =  invoiceId ;
	
	$.ajax({
             url: "deleteBatteryInventory",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 window.location.replace("BatteryInventory.in?delete="+data.deleted+"");
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}



function isNumberKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = 46 == t || t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = n ? "none" : "inline", n
}