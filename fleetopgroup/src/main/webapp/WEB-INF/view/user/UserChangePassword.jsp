<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a>
							Create New Password</a></small>
				</div>
				<div class="pull-right">
					<a href="open.html"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.force eq true}">
		<div class="alert alert-info">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Please Change Your Password, It has been Long since you change Your Password !
		</div>
	</c:if>
	<c:if test="${param.forceWithDays eq true}">
		<div class="alert alert-info">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Please Change Your Password, It has been more than ${configuration.noOfDaysToResetPassword} Days since you change Your Password !
		</div>
	</c:if>
	<section class="content">

		<script type="text/javascript" src="resources/js/status.validate.js" /></script>
		<div class="row">
			<div class="">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('READ_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_PRIVILEGE')">
							<div class="box-body">
								<div class="container">
									<div class="row">
										<div id="errormsg" class="alert alert-danger"
											style="display: none"></div>
										<h1>
											<spring:message code="message.changePassword"></spring:message>
										</h1>
										<div>
											<br>
											<div class="form-horizontal ">
												<div class="row1">
													<label class="L-size"><spring:message
															code="label.user.oldPassword"></spring:message> :</label> <span
														class="col-sm-5"><input class="form-text"
														id="oldpass" name="oldpassword" type="password" value=""
														placeholder="Enter old password" /></span> <span
														class="col-sm-5"></span>
												</div>
												<div class="row1">
													<label class="L-size"><spring:message
															code="label.user.newPassword"></spring:message> :</label> <span
														class="col-sm-5"><input class="form-text" id="pass"
														name="password" type="password" value="" min="5"
														placeholder="Enter New password" /></span> <span
														class="col-sm-5"></span>
												</div>
												<div class="row1">
													<label class="L-size"><spring:message
															code="label.user.confirmPass"></spring:message> :</label> <span
														class="col-sm-5"><input class="form-text"
														id="passConfirm" type="password" value=""
														placeholder="Enter New password" /> </span>
												</div>
												<div class="row1">
													<br> <span id="error" class="alert alert-danger"
														style="display: none"><spring:message
															code="PasswordMatches.user"></spring:message></span> <br> <br>
													<div class="I-size">
														<a class="btn btn-default" href="open.html"> Cancel </a>
														<button class="btn btn-primary" type="submit"
															onclick="savePass()">
															<spring:message code="message.changePassword"></spring:message>
														</button>
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
								<script type="text/javascript">
									function savePass() {
										var pass = $("#pass").val();
										var valid = pass == $("#passConfirm")
												.val();
										if (!valid) {
											$("#error").show();
											return;
										}
										$
												.post(
														"<c:url value="/user/updatePassword"></c:url>",
														{
															password : pass,
															oldpassword : $(
																	"#oldpass")
																	.val()
														},
														function(data) {
															console.log('dadadadadadad ', data);
															window.location.href = "<c:url value="/open.html"></c:url>"
																	+ "?message="
																	+ data.message;
														})
												.fail(
														function(data) {
															console.log('insdide fail.....')
															$("#errormsg")
																	.show()
																	.html(
																			data.responseJSON.message);
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