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
						<li><a href="http://fleetop.in">Login</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</header>

<div class="container-fluid main" id="page-top">
	<div class="row">
		<div class="col-md-12 backg" style="height: 150px;padding-left: 160px;" >
			<div
				class="col-md-4 col-md-offset-4 inner col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3">
				<div class="text-box" >
					<div class="login-box">
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
						<div>
						
							
							<div class="row1 form-group has-feedback"  style="text-align: left; color: yellow;">
								<div style="text-align: center; color: white;font-size: 20px;">OUR WEBSITE IS UNDER CONSTRUCTION..</div><br>
								For any business inquiry please contact to below numbers : <br>
								<div style="color: white;">
									Yagnesh Tripathi : 9664656883, 9820158035<br>
									Alpesh Rathod : 9820255628<br>
									Karunakaran   : 8291287217<br>
								</div>
								
							</div>
							
						</div>
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
					Everything to manage your fleet <a href="https://www.fleetop.in">Fleetop.in</a>.
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
				</span> Copyright &copy; FLEETOP 2019 By <a href="http://fleetop.in">www.fleetop.in</a>
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
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/login/login.js" />"></script>