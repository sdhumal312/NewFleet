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
				window.open(data.url+"?email='+data.email+'&GCode"+'+data.groupCode+');
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

// for IN & OUT
$(document).ready(function() {
    var companyName = document.getElementById("companyName");
    
    $(".sidebar").hover(
        function() {
   			 var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			 var windowWidth = 1536;
			 
			 if (windowWidth > screenWidth && screenWidth <=1229) {
			    companyName.style.fontSize = "12.2px";
			    companyName.style.letterSpacing = "-1.5px";
			    companyName.style.wordSpacing = "5px";
			    companyName.style.height = "10px";
			    companyName.style.width = "120px";
			    companyName.style.margin = "8px 2px 10px 5px";
			    companyName.style.textAlign ="center";
			    companyName.style.justifyContent ="center";
				 
			 }
			 // hover 100 -130 % screen size
			 if (windowWidth > screenWidth && screenWidth >1153) {
		   	    companyName.style.fontSize = "12.2px";
			    companyName.style.letterSpacing = "-1.5px";
			    companyName.style.wordSpacing = "5px";
			    companyName.style.height = "22px";
			    companyName.style.width = "190px";
			    companyName.style.margin = "8px 2px 10px 5px";
			    companyName.style.textAlign ="center";
			    companyName.style.justifyContent ="center";
			 }
			 // hover > 130% screen size
			 else if(windowWidth > screenWidth && screenWidth <= 1153){
			    companyName.style.fontSize = "10px";
			    companyName.style.letterSpacing = "-1.6px";
			    companyName.style.wordSpacing = "0px";
			    companyName.style.height = "20px";
			    companyName.style.width = "80px";
			    companyName.style.margin = "2px 0px 2px 0px";
			 }
			// hover 100% screen size
			 else{
			    companyName.style.fontSize = "16.2px";
			    companyName.style.letterSpacing = "-1.4px";
			    companyName.style.wordSpacing = "5px";
			    companyName.style.height = "35px";
			    companyName.style.width = "200px";
			    companyName.style.margin = "8px 2px 10px 55px";
			 }
        },
    
    // hover out effect
    function() {
		var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var windowWidth = 1536;
       
      // screen size > 100- 130 % hover out      
    if(windowWidth > screenWidth && screenWidth >1383) {
            companyName.style.display = "inline-flex";
	    companyName.style.flexDirection = "column";
	    companyName.style.justifyContent = "center";
	    companyName.style.alignItems = "center";
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "550";
	    companyName.style.padding = "0px 5px 0px 5px";
            companyName.style.letterSpacing = "-1.3px";
            companyName.style.wordSpacing = "4px";
            companyName.style.width = "199px";
            companyName.style.fontSize = "14px";
            companyName.style.height = "32px";
            companyName.style.margin = "8px 2px 10px 95px";
    }
     else if(windowWidth > screenWidth && screenWidth >1228){
	    companyName.style.display = "inline-flex";
	    companyName.style.flexDirection = "column"; 
	    companyName.style.justifyContent = "center"; 
	    companyName.style.alignItems = "center"; 
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "550";
	    companyName.style.padding = "0px 5px 0px 5px";
	    companyName.style.letterSpacing = "-1.2px";
	    companyName.style.wordSpacing = "4px";
	    companyName.style.width = "180px";
	    companyName.style.fontSize = "13px";
	    companyName.style.height = "32px";
	    companyName.style.margin = "8px 2px 10px 55px";
		
	}
    // screen size > 130 % hover out
    else if(windowWidth > screenWidth && screenWidth <=1153){
		  $("#companyName").css({
            color: "#002366",
            fontFamily: "IM Fell Double Pica SC, serif",
            fontWeight: "550",
            height: "20px",
            fontStyle: "normal",
            marginRight: "10px",
            textAlign: "center",
            letterSpacing: "-1.2px",
            wordSpacing: "3px",
            fontSize: "13.5px",
            width: "190px",
            lineHeight: "1.1",
            margin : "8px 2px 10px 45px"
        });
	}
	// default screen 100 % hover out
    else{
		var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "590";
	    companyName.style.padding = "0px 5px 0px 5px";
            companyName.style.letterSpacing = "-1.2px";
            companyName.style.wordSpacing = "5px";
            companyName.style.width = "310px";
            companyName.style.fontSize = "17.5px";
            companyName.style.height = "35px";
            companyName.style.margin = "8px 2px 10px 95px";
			}
        }
    );
});

// screen resize 
$(document).ready(function() {
    checkInitialWidth();
    // Add event listener for further checks on window resize
    $(window).resize(function() {
        checkInitialWidth();
    });
});

function checkInitialWidth() {
    var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var windowWidth = 1536;
 	
  // CSS changes when screen size i.e 100 -130 %
    if (windowWidth > screenWidth && screenWidth > 1383) {
	    companyName.style.display = "inline-flex";
	    companyName.style.flexDirection = "column"; 
	    companyName.style.justifyContent = "center"; 
	    companyName.style.alignItems = "center"; 
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "590";
	    companyName.style.padding = "0px 5px 0px 5px";
            companyName.style.letterSpacing = "-1.3px";
            companyName.style.wordSpacing = "4px";
            companyName.style.width = "250px";
            companyName.style.fontSize = "15.5px";
            companyName.style.height = "32px";
            companyName.style.margin = "8px 2px 10px 95px";
    }
    else if(windowWidth > screenWidth && screenWidth <1383){
	    companyName.style.display = "inline-flex";
	    companyName.style.flexDirection = "column"; 
	    companyName.style.justifyContent = "center"; 
	    companyName.style.alignItems = "center"; 
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "550";
	    companyName.style.padding = "0px 5px 0px 5px";
            companyName.style.letterSpacing = "-1.2px";
            companyName.style.wordSpacing = "4px";
            companyName.style.width = "190px";
            companyName.style.fontSize = "13px";
            companyName.style.height = "32px";
            companyName.style.margin = "8px 2px 10px 30px";
		
	}
    // CSS changes when screen size greater than 130 %
    else if(windowWidth > screenWidth && screenWidth <=1153){
	    companyName.style.display = "inline-flex";
	    companyName.style.flexDirection = "column"; 
	    companyName.style.justifyContent = "center"; 
	    companyName.style.alignItems = "center"; 
	    companyName.style.borderRadius = "0px";
	    companyName.style.fontWeight = "550";
	    companyName.style.padding = "0px 5px 0px 5px";
            companyName.style.letterSpacing = "-1.3px";
            companyName.style.wordSpacing = "4px";
            companyName.style.width = "205px";
            companyName.style.fontSize = "13px";
            companyName.style.height = "32px";
            companyName.style.margin = "8px 2px 10px 45px";
	}
    else {
        // Revert or reset CSS changes when default screen i.e 100%
        $("#companyName").css({
	    color: "#002366",
	    display: "inline-flex",
            alignItems: "center",
            height: "35px",
            textShadow: "1px 1px 1px rgba(0, 0, 0, 0.2)",
            backgroundColor: "#F7E7CE",
            padding: "0px 10px 0px 10px",
            fontFamily: "IM Fell Double Pica SC, serif",
            fontWeight: "550",
            fontStyle: "normal",
            textAlign: "center",
            letterSpacing: "-1.2px",
            wordSpacing: "5px",
            fontSize: "17.5px",
            width: "360px",
            lineHeight: "1.1",
            color: "#002366",
            margin : "8px 2px 10px 115px"
        });
    }
}

