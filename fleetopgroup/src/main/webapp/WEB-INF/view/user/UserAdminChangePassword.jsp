<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> /  <a href="<c:url value="/newUserList/1.in"/>" >User List</a> / <a>
							 ${user.email}</a>
				</div>
				<div class="pull-right">
					<a href="open.html"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('EDIT_USER')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('EDIT_USER')">
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
														<a class="btn btn-default" href="<c:url value="/newUserList/1.in"/>"> Cancel </a>
														<button class="btn btn-primary" type="submit"
															onclick="savePass()">
															<spring:message code="message.updatePassword"></spring:message>
														</button>
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
									    $.post("<c:url value="/admin/savePassword"></c:url>",{password: pass, id: user_id} ,function(data){
									            window.location.href = "<c:url value="/newUserList/1.html"></c:url>" + "?message="+data.message;
									    })
									    .fail(function(data) {
									        window.location.href = "<c:url value="/newUserList/1.html"></c:url>" + "?message=" + data.responseJSON.message;
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