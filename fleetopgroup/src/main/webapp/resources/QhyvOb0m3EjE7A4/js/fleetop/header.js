$(document).keydown(function (event) {
    if (event.keyCode == 123) { // Prevent F12
    	showMessage('info', 'Not Allowed !');
        return false;
    } else if (event.ctrlKey && event.shiftKey && event.keyCode == 73) { // Prevent Ctrl+Shift+I  
    	showMessage('info', 'Not Allowed !');
        return false;
    }
});

$(document).ready(function() {
	getCompanyNameAndLogo();
	marqueeHeader();
	notifications();
	rrNotification();
	$('#question').hide();
	$('#back-to-top').hide();
	$(".main-header").css("height",'15px');
	
});

$(document).ready(function() {
	$('input[type=file]').bind('change', function() {
		  var maxSizeKB = 4096; //Size in KB
		  var maxSize = maxSizeKB * 1024; //File size is returned in Bytes
		  if (this.files[0].size > maxSize) {
		    $(this).val("");
		    showMessage('errors', 'You can not select file size more than 4 MB !');
		    return false;
		  }
		});
});
function checkForOTPValidated(){
	showLayer();
	$.ajax({
             url: "checkForOTPValidated",
             type: "POST",
             dataType: 'json',
             data: null,
             success: function (data) {
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}



function display_c() {
	var refresh = 1000; // Refresh rate in milli seconds
	mytime = setTimeout('display_ct()', refresh)
}

var xmlHttp;
var serverDate;
function srvTime(){
	var d = new Date(),
    minutes = d.getMinutes().toString().length == 1 ? '0'+d.getMinutes() : d.getMinutes(),
    hours = d.getHours().toString().length == 1 ? '0'+d.getHours() : d.getHours(),
    ampm = d.getHours() >= 12 ? 'pm' : 'am',
    months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
    days = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];

    serverDate =  days[d.getDay()]+', '+months[d.getMonth()]+' '+d.getDate()+' '+d.getFullYear()+' ';
}

var st = srvTime();
var date = new Date(st);


function display_ct() {
	var strcount
	var x = new Date()
	var x1 = x.getTimezoneOffset();
	var ISTOffset = 330; // IST offset UTC +5:30 
	var d = new Date(x.getTime() + (ISTOffset + x1) * 60000);
	var weekday = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
	var monthname = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
	var time = weekday[d.getDay()] + " " + d.getDate() + ". "
			+ monthname[d.getMonth()] + " " + d.getFullYear();
	time = serverDate + " - " + d.getHours() + ":" + d.getMinutes() + ":"
			+ d.getSeconds();
	document.getElementById('ct').innerHTML = time;
	tt = display_c();
}

jQuery(document).ready(function() {
	display_ct();
	srvTime();
});


$(document).ready(function(){
	$("#userid").click(function(){
		"false" == $(this).attr("aria-expanded")&& $.getJSON("/UserProfileName",
			{ajax : "true"},
				function(a) {
					var b = '<c:choose><c:when test="${'
							+ a.photo_id
							+ ' != null}"><img src=""  class="img-circle" alt="'+a.firstName+" "+a.lastName+'"> </c:when><c:otherwise><img src="" alt="User Profile" class="img-circle" /></c:otherwise></c:choose><p> '
							+ a.firstName
							+ " "
							+ a.lastName
							+ " <br> "
							+ a.designation
							+ " <small>"
							+ a.department_name
							+ " - "
							+ a.branch_name
							+ "</small></p>";
							$("#subtaskto").html(b)
							console.log("SUBTASK ",$("#subtaskto").html())
						})
					}),
	$("#unReadMeassage").click(function() {
		"false" == $(this).attr("aria-expanded")&& $.getJSON("/GetUnReadMailMessage",
			{ajax : "true"},
				function(a) {
				var lengthMail = a.length;
				var path ="/resources/QhyvOb0m3EjE7A4/images/user.png";
					for (var b = "", c = lengthMail, d = 0; c > d; d++)
						
						b +='<a href="readmailbox?id='+a[d].MAILBOX_ID_Encode+'" >'
						+ '	<div class="pull-left"><img src="resources/QhyvOb0m3EjE7A4/images/user.png" class="img-circle" alt="User Image"></div><h4> '
						+ a[d].MAILBOX_FROM_USER_NAME
						+ ' <small><i class="fa fa-clock-o"></i> '
						+ a[d].MAIL_DATE
						+ ' </small></h4><p>'
						+ a[d].MAILBOX_NAMESPACE
						+ ' </p></a></li> '
						b += "",
						$("#UNMESSAGE").html(b)
					})
				}),
			window.onload = loadUnReadMail
	});




function marqueeHeader(){
	var finalMessage 	=	 null;
	
	$.getJSON("/GetMarqueeMessage", function(e) {
	MarqueeList	= e;
			for (var i = 0; i<MarqueeList.length; i++){
				if(finalMessage != null){
					finalMessage 	= finalMessage + "."+"   " + (MarqueeList[i].marquee_message).bold().fontsize(2).fontcolor(MarqueeList[i].color_Id);
				}else{
					finalMessage	= (MarqueeList[i].marquee_message).bold().fontsize(2).fontcolor(MarqueeList[i].color_Id);
				}
			}
		$("#message").html(finalMessage)
	
		if(MarqueeList != null && MarqueeList != undefined && MarqueeList.length > 0){
			$("#header").css("height",'18px');
		}else{
			$("#message").hide();
		} 
	});
}

function notifications(){
	
	$.getJSON("getNotificationCount", function(countNt) {
		if(countNt != undefined && countNt > 0){
			var msg = 'Dear User You Have '+countNt+' Notifications, Kidly Check !';
			$("#notificationCount").html(countNt)
			$("#snackbar").html(msg);
			
			var x = document.getElementById("snackbar")
			x.className = "show";
			setTimeout(function() {
				x.className = x.className.replace("show", "");
			}, 4000);
			
		}else{
			$("#notificationCount").html(0);
		}
	});
}

function getUserNotificationList(){

			showLayer();
			var jsonObject			= new Object();
			
			location.replace("getUserNotificationList.in");

}

function rrNotification(){
	var jsonObject					= new Object();
	$.getJSON("getRRNotificationCount", function(data) {
		var data = data.htData;
		if(data.showIvCargoLogoWithLink != undefined && (data.showIvCargoLogoWithLink)){
			showIvLogo();
		}
		if(data.rrNotification == true){
			$("#rrSnackbar").removeClass('hide');
			$("#rrNotificationIcon").removeClass('hide');
		
			if(data.totalRRCount > 0){
				$("#renewalNotificationCount").html(data.totalRRCount)
				var msg = 'Dear User You Have '+data.totalRRCount+' RR Notifications, Kidly Check !';
				$("#rrSnackbar").html(msg);
				
				var y = document.getElementById("rrSnackbar")
				y.className = "show";
				setTimeout(function() {
					y.className = y.className.replace("show", "");
				}, 4000);
			}else{
				$("#renewalNotificationCount").html(0);
			}
		}
	});
	
}

function getRRNotificationList(){
	showLayer();
	var jsonObject			= new Object();
	location.replace("getRRNotificationList.in");

}

function createCookie(name, value, days) {
    var expires;

    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
    } else {
        expires = "";
    }
    document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(value) + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = encodeURIComponent(name) + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0)
            return decodeURIComponent(c.substring(nameEQ.length, c.length));
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}
//function HasErrors(data) {
//  // check for redirect to login page
//  if (data.search(/login != -1/)) { 
//     
//    return true;
//  }
//  // check for IIS error page
//  if (data.search(/Internal Server Error/) != -1) {
//    ShowStatusFailed('Server Error.');
//    return true;
//  }
//  // check for our custom error handling page
//  if (data.search(/Error.aspx/) != -1) {
//    ShowStatusFailed('An error occurred on the server. The Technical Support Team has been provided with the error details.');
//    return true;
//  }
//  return false;
//}

function showIvLogo(){
	$('#ivCargoLogoA').removeClass('hide');
}
function getIvUrl(){
	$.ajax({
		url : "CompanyRestControllerWS/getIvLoginUrlByCompanyId",
		type : "GET",
		success : function(data){
			if(data.url != undefined && data.url != null){
				window.open(data.url);
			}
		},error : function(e){
			console.log("error : ",e);
		}
	});
}

function getCompanyNameAndLogo(){
    $.ajax({
        url : "/CompanyRestControllerWS/companyNameAndLogoController",
        type : "GET",
        success : function(data){
            $('#companyName').text(data.Company.company_name);
            $('#checkk').val(data.Company.company_id_encode);
            setLogo();
        },
        error: function (xhr, status, errorThrown) {
            
        }
    });
}

$(document).ready(function() {
	$(".sidebar").hover(
	        function() {
	        	companyInfo.style.margin = "8px 2px 20px 15px";
			    companyInfo.style.width = "300px";
			    companyName.style.fontSize = "13.2px";
			    companyName.style.letterSpacing = "-1.9px";
			    companyName.style.wordSpacing = "5px";
			    logoContainer.style.width = "120px";
			    logoContainer.style.margin = "0px 10px 0px 0px";
			    
	        },
	        function() {
	        	companyInfo.style.margin = "8px 2px 10px 95px";
			    companyName.style.letterSpacing = "-1.2px";
			    companyName.style.padding = "0px 4px 0px 10px";
			    companyInfo.style.padding = "0px 4px 0px 10px";
			    companyName.style.wordSpacing = "5px";
			    companyInfo.style.width = "425px";
			    companyName.style.width = "360px";
			    companyName.style.fontSize = "17.2px";
			    logoContainer.style.height = "105%";
			    logoContainer.style.margin = "0px -55px 0px 0px";
			    logoContainer.style.width = "170px";
	        }
	      );
});
