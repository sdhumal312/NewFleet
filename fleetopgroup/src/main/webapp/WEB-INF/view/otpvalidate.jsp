<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>
<fmt:message key="message.password" var="noPass" />
<fmt:message key="message.username" var="noUser" />

<header class="header">
	<div class="container">
		<div class="row">
			<div class="col-md-4 ">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle menu-button"
						data-toggle="collapse" data-target="#myNavbar">
						<span class="fa fa-align-justify"></span>
					</button>
					<a class="navbar-brand logo" href="http://fleetop.com"><img
						alt="SRS Travels"
						src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/FTLOGO.png"/>"></a>
				</div>
			</div>
			<%-- <div class="col-md-2 ">
				<a class="btn btn-danger" href="<c:url value="/Registration"/>">Employee
					Transportation Registration (ETR)</a>
			</div> --%>
			<div class="col-md-4">
				<nav class="collapse navbar-collapse" id="myNavbar"
					role="navigation">
					<ul class="nav navbar-nav navbar-right menu">
						<li><a href="#page-top" class="page-scroll active">Login</a></li>
						<li><a href="http://fleetop.com">Contact</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</header>

<div class="container-fluid main" id="page-top">
	<div class="row">
		<div class="col-md-12 backg">
			<div
				class="col-md-4 col-md-offset-4 inner col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3">
				<div class="text-box">
					<div class="login-box">
						<c:if test="${param.otpValidated eq false}">
							<div class="alert alert-danger">
								<spring:message code="auth.message.otpfailed"></spring:message>
							</div>
						</c:if>	
						<c:if test="${param.error != null}">
							<c:choose>
								<c:when
									test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'}">
									<div class="alert alert-danger">
										<spring:message code="auth.message.disabled"></spring:message>
									</div>
								</c:when>
								<c:when
									test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User account has expired'}">
									<div class="alert alert-danger">
										<spring:message code="auth.message.expired"></spring:message>
									</div>
								</c:when>
								<c:when
									test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'blocked'}">
									<div class="alert alert-danger">
										<spring:message code="auth.message.blocked"></spring:message>
									</div>
								</c:when>
								
								<c:otherwise>
									<div class="alert alert-danger">
										<spring:message code="message.badCredentials"></spring:message>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="${param.message != null}">
							<div class="alert alert-info">${param.message}</div>
						</c:if>
						<p>
							<a href="?lang=en"><spring:message
									code="label.form.loginEnglish"></spring:message></a> |
						</p>
						<%--  <a href="?lang=es_ES"><spring:message
                    code="label.form.loginSpanish"></spring:message></a> --%>
						<form name='f' action="s_security_fleetop" method='POST'
							onsubmit="return validate();">
						<!-- <input type="hidden" name="companyCode" id="companyCode" value="saini">
						<input type="hidden" name="j_username" id="j_username" value="admin"> -->
						
						<div class="row1 form-group has-feedback">
								<spring:message code="label.form.companyCode"></spring:message>
								<input class="form-text" type='text' name='companyCode' readonly="readonly"
									placeholder="company code" id="companyCode" /> <span
									class="fa fa-users form-text-feedback"></span>
							</div>
							<div class="row1 form-group has-feedback">
								<spring:message code="label.form.loginUser"></spring:message>

								<input class="form-text" type='text' name='j_username' value='' id="email" readonly="readonly"
									placeholder="eg : admin"> <span
									class="fa fa-envelope form-text-feedback"></span>
							</div>
						
						<input type="hidden" name="j_password" id="j_password" value="0">
							<div class="row1 form-group has-feedback">
								Validate OTP
								<input class="form-text" type='text' name='validateotp'
									placeholder="Please Enter OTP" id="validateotp" /> <span
									class="fa fa-users form-text-feedback"></span>
							</div>
							
							<div class="row">
								<div class="col-sm-8">
									<input class="btn btn-success" name="validate" type="submit"
										value="Validate">
									<input class="btn btn-info" name="resendOTP" type="button"
										value="Resend OTP" onclick="resendOTPCode();">
									
									<%-- <a class="btn btn-info"
							href="<c:url value="/forgetPassword.html" />"><spring:message
								code="message.resetPassword"></spring:message></a> --%>
								</div>
							</div>

						</form>
						
					</div>
				</div>
			</div>
		</div>
		
	</div>
</div>


<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery.lingua.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/language.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/login/login.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/commonUtility.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/commonUtility.js" />"></script>
	
<script>

// slight update to account for browsers not supporting e.which
function disableF5(e) { if ((e.which || e.keyCode) == 116) e.preventDefault(); };
// To disable f5
    /* jQuery < 1.7 */
$(document).bind("keydown", disableF5);
/* OR jQuery >= 1.7 */
$(document).on("keydown", disableF5);

// To re-enable f5
    /* jQuery < 1.7 */
$(document).unbind("keydown", disableF5);
/* OR jQuery >= 1.7 */
$(document).off("keydown", disableF5);

	var jsonObject			= new Object(); 
	jsonObject["id"] 	=  getUrlParameter('id');
	console.log('jsonObject : ', jsonObject);
	$.ajax({
             url: "getUserDetailsFromCache",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	console.log('data : ', data);
            	if(data != null){
            		$('#companyCode').val(data.companyCode);
            		$('#email').val(data.username);
            		$('#j_password').val(data.password);
            	}
             },
             error: function (e) {
            	 //showMessage('errors', 'Some error occured!')
             }
	});
	
	
	function resendOTPCode(){
		var jsonObject			= new Object(); 
		jsonObject["id"] 	=  getUrlParameter('id');
		$.ajax({
            url: "resendOTPValidationCode",
            type: "POST",
            dataType: 'json',
            data: jsonObject,
            success: function (data) {
           	console.log('data : ', data);
           	alert('OTP Code Send Successfully !');
            },
            error: function (e) {
           	 //showMessage('errors', 'Some error occured!')
            }
	});
	}
	
	</script>
			