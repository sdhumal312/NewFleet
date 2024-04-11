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
						alt="Fleetop"
						src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/FTLOGO.png"/>"></a>
				</div>
			</div>
			<%-- <div class="col-md-2 ">
			<a class="btn btn-danger" href="<c:url value="/Registration"/>">Employee Transportation Registration (ETR)</a>
			</div> --%>
			<div class="col-md-4">
				<nav class="collapse navbar-collapse" id="myNavbar"
					role="navigation">
					<ul class="nav navbar-nav navbar-right menu">
						<li><a href="#page-top" class="page-scroll active">Login</a></li>
						<li><a href="https://www.fleetop.com">Contact</a></li>
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
						<div class="alert alert-danger">
							<spring:message code="message.sessionExpired"></spring:message>
						</div>
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
										<!-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/> -->
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
							
							<div class="row1 form-group has-feedback">
								<spring:message code="label.form.companyCode"></spring:message>
								<input class="form-text" type='text' name='companyCode'
									placeholder="company code" id="companyCode" /> <span
									class="fa fa-users form-text-feedback"></span>
							</div>
							
							<div class="row1 form-group has-feedback">
								<spring:message code="label.form.loginUser"></spring:message>

								<input class="form-text" type='text' name='j_username' value=''
									id="email" placeholder="eg : admin"> <span
									class="fa fa-envelope form-text-feedback"></span>
							</div>
							<div class="row1 form-group has-feedback">
								<spring:message code="label.form.loginPass"></spring:message>
								<input class="form-text" type='password' name='j_password'
									placeholder="**********" /> <span
									class="fa fa-lock form-text-feedback"></span>
							</div>
							<div class="row">
								<div class="col-sm-8">
									<input class="btn btn-success" name="submit" type="submit"
										value=<spring:message code="label.form.submit"></spring:message> />
									<input class="btn btn-info" name="Cancel" type="reset"
										value=<spring:message code="label.master.cancel"></spring:message> />
									<%-- <a class="btn btn-info"
							href="<c:url value="/forgetPassword.html" />"><spring:message
								code="message.resetPassword"></spring:message></a> --%>
								</div>
							</div>

						</form>
						<h2>
							<a href="https://www.fleetop.com">By <img alt="fleetop.com"
								src="<c:url value="/resources/QhyvOb0m3EjE7A4/css/images/fleetoplogo.png"/>"
								width="135px" height="33px"></a>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 some-notes">
			<div class="title">
				<h2>Welcome To Fleetop</h2>
			</div>
			<div class="desc">
				<p>
					Everything to manage your fleet <a href="https://www.fleetop.com">Fleetop.in</a>.
				</p>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid footer">
	<div class="row">
		<div class="col-md-12">
			<p>
				<span> Design: <a href="http://www.fleetop.in">fleetop</a>
				</span> Copyright &copy; FLEETOP 2018 By <a href="http://fleetop.com">www.fleetop.in</a>
			</p>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery.lingua.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/language.js" />"></script>
<!--Necessary in all pages  -->
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/login/login.js" />"></script>