<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/masterCompany/1.in"/>">Company</a>
					</div>
					<div class="pull-right">
						<a href="<c:url value="/showMasterCompany?CID=${Company.company_id_encode}"/>">Cancel</a>
					</div>
				</div>
				<c:if test="${!empty Company}">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
							class="zoom" data-title="logo"
							data-footer="${Company.company_name}" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
							class="img-rounded" alt="Company Logo" width="300" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a> <c:out value="${Company.company_name}" /> - <c:out
									value="${Company.company_type}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-sitemap" aria-hidden="true"
									data-toggle="tip" data-original-title="PAN Number"><a>
											<c:out value="${Company.company_pan_no}" />
									</a></span></li>

								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="TAN Number"> <a
										href="#"> <c:out value="${Company.company_tan_no}" /></a>
								</span></li>

								<li><span class="fa fa-university" aria-hidden="true"
									data-toggle="tip" data-original-title="TIN Number"> <a
										href="#"> <c:out value="${Company.company_tin_no}" /></a></span></li>

								<li><span class="fa fa-university" aria-hidden="true"
									data-toggle="tip" data-original-title="CIN Number"> <a
										href="#"> <c:out value="${Company.company_cin_no}" /></a></span></li>

							</ul>
						</div>
					</div>
				</c:if>
			</div>

		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('MASTER_RESET_PWD_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('MASTER_RESET_PWD_PRIVILEGE')">
							<div class="box-body">
								<div class="container">
									<div class="row">
										<div id="errormsg" class="alert alert-danger"
											style="display: none"></div>
										<h2>
											Reset Password To ${user.email}
										</h2>
										<div>
											<br>
											<div class="form-horizontal ">
											   <div class="row1">
													<input class="form-text" id="user_id"
														name="id" type="hidden" value="${user.id}" required="required" 
														placeholder="Enter New password" />
												</div>
												<div class="row1">
													<label class="L-size"><spring:message
															code="label.user.newPassword"></spring:message> :</label> <span
														class="col-sm-5"><input class="form-text" id="pass"
														name="password" type="password" value="" min="5" required="required"
														placeholder="Enter New password" /></span> <span
														class="col-sm-5"></span>
												</div>
												<div class="row1">
													<label class="L-size"><spring:message
															code="label.user.confirmPass"></spring:message> :</label> <span
														class="col-sm-5"><input class="form-text"
														id="passConfirm" type="password" value="" required="required"
														placeholder="Enter New password" /> </span>
												</div>
												<div class="row1">
													<br> <span id="error" class="alert alert-danger"
														style="display: none"><spring:message
															code="PasswordMatches.user"></spring:message></span> <br> <br>
													<div class="I-size">
														<button class="btn btn-primary" type="submit"
															onclick="savePass()">
															<spring:message code="message.updatePassword"></spring:message>
														</button>
														<a class="btn btn-default" href="<c:url value="/showMasterCompany?CID=${company.company_id_encode}"/>"> Cancel </a>
														
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
								<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/status.validate.js" />"></script>
								<script type="text/javascript">
									function savePass(){
									    var pass = $("#pass").val();
									    var user_id = $("#user_id").val();
									    var valid = pass == $("#passConfirm").val();
									    if(!valid) {
									      $("#error").show();
									      return;
									    }
									    $.post("<c:url value="/master/savePassword"></c:url>",{password: pass, id: user_id} ,function(data){
									            window.location.href = "<c:url value="/masterCompany/1.html"></c:url>" + "?message="+data.message;
									    })
									    .fail(function(data) {
									        window.location.href = "<c:url value="/masterCompany/1.html"></c:url>" + "?successpassword=true&message=" + data.responseJSON.message;
									    });
									}
								</script>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>