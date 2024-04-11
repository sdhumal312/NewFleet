<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- iCheck -->
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/icheck/blue.css" />">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" />">

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">

<style>
.read-subject {
	width: 80%;
	text-align: left;
}

.read-subject-button {
	width: 20%;
	text-align: right;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="#"/>">MailBox</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">

			<div class="col-md-2 col-sm-2 col-xs-12">
				<a href="compose.html"
					class="btn btn-primary btn-block margin-bottom">Compose</a>

				<div class="box box-solid">
					<div class="box-header with-border">
						<h3 class="box-title">Mailbox</h3>
						<div class="box-tools">
							<button class="btn btn-box-tool" data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<div class="box-body no-padding">
						<ul class="nav nav-pills nav-stacked">
							<li><a href="<c:url value="/mailbox/1"></c:url>"><i
									class="fa fa-inbox"></i> Inbox <span
									class="label label-primary pull-right">${unread}</span></a></li>
							<li><a href="<c:url value="/sentmailbox/1" />"><i
									class="fa fa-envelope-o"></i> Sent</a></li>
							<li><a href="<c:url value="/trashmailbox/1" />"><i
									class="fa fa-trash-o"></i> Trash</a></li>
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /. box -->
				<div class="box box-solid">
					<div class="box-header with-border">
						<h3 class="box-title">Info</h3>
						<div class="box-tools">
							<button class="btn btn-box-tool" data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<div class="box-body no-padding">
						<ul class="nav nav-pills nav-stacked">
							<li><a href="<c:url value="/importantmailbox" />"><i
									class="fa fa-star text-red"></i> Important</a></li>
							<li><a href="<c:url value="/subscribebox" />"><i
									class="fa fa-calendar-plus-o text-yellow"></i> Subscribe</a></li>

						</ul>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
			<div class="col-md-9 col-sm-8 col-xs-12">

				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">
							<a href="<c:url value="/mailbox/1"/>" class="btn btn-box-tool"
								data-toggle="tooltip" title="Back to Inbox"> <i
								class="fa fa-long-arrow-left"> Back</i>
							</a> Read Mail
						</h3>
						<div class="box-tools pull-right">
							<a href="<c:url value="/mailbox/1"/>" class="btn btn-box-tool"
								data-toggle="tooltip" title="Back to Inbox"> <i
								class="fa fa-times"></i>
							</a> <a href="#" class="btn btn-box-tool" data-toggle="tooltip"
								title="Previous"><i class="fa fa-chevron-left"></i></a> <a
								href="#" class="btn btn-box-tool" data-toggle="tooltip"
								title="Next"><i class="fa fa-chevron-right"></i></a>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<div class="mailbox-read-info">
							<div class="row">
								<div class="col-md-8 mailbox-read-subject">
									<h3>
										<c:out value="${mailbox.MAILBOX_NAMESPACE}"></c:out>
									</h3>
								</div>
								<div class="col-md-3">
									<form id="frm-example" action="deletereadMail" method="post">
										<input type="hidden" name="SelectMailId"
											value="${mailbox.MAILBOX_ID_Encode}"> <input
											type="hidden" name="showReturn"
											value="${mailbox.MAIL_MIME_TYPE}">

										<button type="submit" class="btn btn-default btn-xs"
											data-toggle="tooltip" title="Delete Mail">
											<i class="fa fa-trash-o"></i>
										</button>
									</form>
									<!-- Print After -->
									<!-- <button class="btn btn-default btn-xs">
										<i class="fa fa-print"></i>
									</button> -->
								</div>
							</div>

						</div>
						<div class="mailbox-read-message">
							<table class="table table-hover table-bordered">

								<tbody>
									<tr>
										<td class=""><c:if
												test="${mailbox.MAILBOX_SENTER_MAILBOX_ID != 0}">
												<div class="mailbox-reply" id="replayClickBox">
													<div class="mailbox-reply-in">

														Click here to <span class="mailbox-reply-link"
															id="replayClick">Reply last mail</span> or <span
															class="mailbox-reply-link" id="forwardClick">Forward</span>

													</div>
												</div>
											</c:if>
											<div id="error" class="alert alert-success"
												style="display: none"></div>
											<div id="emailError" class="alert alert-success"
												style="display: none"></div>

											<div id="replayMessage" style="display: none;">

												<div class="row">

													<div id="success" class="alert alert-success"
														style="display: none"></div>
												</div>
												<form action="/" method="post" id="reply">

													<div class="box-body">

														<div class="form-group">
															<span>Reply last mail</span> <input class="form-text"
																name="OWN_REPLY_MAILBOX_ID"
																value="${mailbox.MAILBOX_ID}" type="hidden"
																readonly="readonly"> <input class="form-text"
																name="MAILBOX_SENTER_MAILBOX_ID"
																value="${mailbox.MAILBOX_SENTER_MAILBOX_ID}"
																type="hidden" readonly="readonly"> <span
																id="globalError" class="alert alert-danger col-sm-4"
																style="display: none"></span> <label id="emptyto"
																class="error" style="display: none"></label>
														</div>

														<div class="form-group">
															<textarea id="compose-textarea" class="form-text"
																style="height: 100px" name="MESSAGE_REPLY"
																maxlength="1000">
                     
                                                          </textarea>
														</div>
														<!-- <div class="form-group">
															<div class="btn btn-default btn-file">
																<i class="fa fa-paperclip"></i> Attachment <input
																	type="file" name="attachment">
															</div>
															<p class="help-block">Max. 3MB</p>
														</div> -->

													</div>
													<div class="box-footer">
														<div class="pull-right">

															<button type="submit" class="btn btn-primary">
																<i class="fa fa-envelope-o"></i> Send
															</button>
														</div>
														<span class="btn btn-default" id="discardClick"> <i
															class="fa fa-times"></i> Discard
														</span>
													</div>

												</form>
											</div>

											<div id="forwardMessage" style="display: none;">

												<div class="row">

													<div id="successFormard" class="alert alert-success"
														style="display: none"></div>

												</div>
												<form action="/" method="post" id="forward">

													<div class="box-body">

														<div class="form-group">
															<span>Forward mails</span> <input class=""
																placeholder="To:" id="to" name="TO_FORWARD"
																type="hidden" style="width: 100%"> <span
																id="globalError" class="alert alert-danger col-sm-4"
																style="display: none"></span> <label id="emptyForwardto"
																class="error" style="display: none"></label> <input
																class="form-text" name="SUBJECT_FORWARD"
																value="${mailbox.MAILBOX_NAMESPACE}" type="hidden"
																readonly="readonly">
														</div>

														<div class="form-group">
															<textarea id="forward-textarea" class="form-text"
																style="height: 300px" name="MESSAGE_FORWARD"
																maxlength="1000">
												                     <p>----- Forwarded message -----<br />
												                       From: <b>${mailbox.MAILBOX_FROM_USER_NAME}</b> , ( ${mailbox.MAILBOX_FROM_EMAIL} )<br />
												                       Date: ${mailbox.MAIL_DATE}<br>
												                       Subject : Re:${mailbox.MAILBOX_NAMESPACE}<br />
												                       To: ( ${mailbox.MAILBOX_TO_EMAIL} )<br />
												                     </p>
												                     
                     
							                                       <c:if
																	test="${!empty message}">
																	<c:forEach items="${message}" var="message">
																	 <blockquote>
																			<p>On ${message.PROPERTY_DATE}, ${message.MAILBOX_FROM_USER_NAME}  ( ${message.MAILBOX_FROM_EMAIL} ) wrote:<br />
																			</p>
																	      <footer>${message.PROPERTY_MESSAGE}</footer>
																	  </blockquote>
																	</c:forEach>
																	</c:if>
                                                          </textarea>
														</div>

														<div class="box-footer">
															<div class="pull-right">

																<button type="submit" class="btn btn-primary">
																	<i class="fa fa-envelope-o"></i> Send
																</button>
															</div>
															<span class="btn btn-default" id="discardFormard">
																<i class="fa fa-times"></i> Discard
															</span>
														</div>
													</div>
												</form>
											</div></td>

									</tr>

								</tbody>
								<tfoot>

									<c:if test="${!empty message}">
										<c:forEach items="${message}" var="message">
											<tr>
												<td>
													<div class="row mailbox-fromtime">
														<div class="col-md-8 mailbox-read-from">
															<h4>${message.MAILBOX_FROM_USER_NAME}
																<span> ( ${message.MAILBOX_FROM_EMAIL} ) </span>
															</h4>

														</div>
														<div class="col-md-3">
															<span>${message.PROPERTY_DATE}</span>
															<div class="zd" aria-label="Starred" tabindex="0"
																role="checkbox" aria-checked="false" style="outline: 0">
																<!-- <span class="T-KT" onselectstart="return false;"><img
																				class="f T-KT-JX" src="images/cleardot.gif" />" alt=""></span> -->
															</div>
														</div>
													</div>
													<div class="mailbox-line"></div>
													<div class="row mailbox-read-message">
														${message.PROPERTY_MESSAGE}</div>
													<div class="mailbox-line"></div>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td>
												<div class="row mailbox-fromtime">
													<c:choose>
														<c:when test="${mailbox.MAIL_DOCUMENT  == true}">
															<div class="box-footer">
																<ul class="mailbox-attachments clearfix">
																	<li>
																		<div class="mailbox-attachment-info">
																			<a href="${pageContext.request.contextPath}/download/MailboxDoc/${mailbox.MAIL_DOCUMENT_ID}.in" class="mailbox-attachment-name"><i
																				class="fa fa-paperclip"></i> Attachment File</a> <span
																				class="mailbox-attachment-size"><a
																				href="${pageContext.request.contextPath}/download/MailboxDoc/${mailbox.MAIL_DOCUMENT_ID}.in" class="btn btn-default  pull-right"><i
																					class="fa fa-cloud-download"></i></a>
																			</span>
																		</div></li>
																</ul>
															</div>
														</c:when>
													</c:choose>
												</div>
											</td>
										</tr>
									</c:if>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
				<!-- /. box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>

	<!-- Bootstrap WYSIHTML5 -->
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" />"></script>
	<!-- Page Script -->
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script>
		$(function() {
			//Add text editor
			$("#compose-textarea").wysihtml5();
			$("#forward-textarea").wysihtml5();
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {

			$('#forward').submit(function(event) {
				//alert("forward");
				registerForward(event);
			});

			$('#reply').submit(function(event) {
				//alert("reply");
				register(event);
			});

			setInterval(function() {
				$("#success").css("display", "none");
			}, 15000);

			setInterval(function() {
				$("#successFormard").css("display", "none");
			}, 15000);

			$("#to").select2({
				minimumInputLength : 3,
				minimumResultsForSearch : 10,
				multiple : true,
				ajax : {
					url : "getUserEmailId",
					dataType : "json",
					type : "POST",
					contentType : "application/json",
					quietMillis : 50,
					data : function(e) {
						return {
							term : e
						}
					},
					results : function(e) {
						return {
							results : $.map(e, function(e) {
								return {
									text : e.firstName + ' ' + e.lastName,
									slug : e.slug,
									id : e.user_email
								}
							})
						}
					}
				}
			})

			$('#replayClick').click(function() {
				$("#replayClickBox").css("display", "none");
				$("#replayMessage").css("display", "block");
			});
			$('#discardClick').click(function() {
				$("#replayMessage").css("display", "none");
				$("#replayClickBox").css("display", "block");
			});

			$('#forwardClick').click(function() {
				$("#replayClickBox").css("display", "none");
				$("#forwardMessage").css("display", "block");
			});
			$('#discardFormard').click(function() {
				$("#forwardMessage").css("display", "none");
				$("#replayClickBox").css("display", "block");
			});

		});

		function register(event) {
			event.preventDefault();
			$(".alert").html("").hide();
			$(".error-list").html("");

			var formData = $('form').serialize();
			$
					.post(
							"<c:url value="/replycompose"/>",
							formData,
							function(data) {
								if (data.message == "success") {
									//window.location.href = "<c:url value="/mailbox"></c:url>";

									$("#success")
											.show()
											.html(
													"Your message has been sent.<button class=\"close\" type=\"button\" onclick=\"closeArert()\">x</button>");
									$('form').each(function() {
										this.reset();
									});
								}
							})
					.fail(
							function(data) {
								if (data.responseJSON.error
										.indexOf("MailError") > -1) {
									$("#globalError").show().html(
											'Mail id is empty');

								} else if (data.responseJSON.error == "MailSentExist") {
									$("#emailError").show().html(
											data.responseJSON.message);
								} else {
									var errors = $
											.parseJSON(data.responseJSON.message);
									$.each(errors, function(index, item) {
										$("#" + item.field + "Error").show()
												.html(item.defaultMessage);
									});
									errors = $
											.parseJSON(data.responseJSON.error);
									$.each(errors, function(index, item) {
										$("#globalError").show().append(
												item.defaultMessage + "<br>");
									});
								}
							});
		}

		function registerForward(event) {
			event.preventDefault();
			$(".alert").html("").hide();
			$(".error-list").html("");
			if ($("#to").val() == "") {
				$("#emptyForwardto").show().html(
						'Please specify at least one recipient.');
				return;
			}
			var formData = $('form').serialize();
			$
					.post(
							"<c:url value="/forwordcompose"/>",
							formData,
							function(data) {
								if (data.message == "success") {
									//window.location.href = "<c:url value="/mailbox"></c:url>";

									$("#successFormard")
											.show()
											.html(
													"Your message has been sent.<button class=\"close\" type=\"button\" onclick=\"closeArert()\">x</button>");
									$('form').each(function() {
										this.reset();
									});
								}
							})
					.fail(
							function(data) {
								if (data.responseJSON.error
										.indexOf("MailError") > -1) {
									$("#globalError").show().html(
											'Mail id is empty');

								} else if (data.responseJSON.error == "MailSentExist") {
									$("#emailError").show().html(
											data.responseJSON.message);
								} else {
									var errors = $
											.parseJSON(data.responseJSON.message);
									$.each(errors, function(index, item) {
										$("#" + item.field + "Error").show()
												.html(item.defaultMessage);
									});
									errors = $
											.parseJSON(data.responseJSON.error);
									$.each(errors, function(index, item) {
										$("#globalError").show().append(
												item.defaultMessage + "<br>");
									});
								}
							});
		}

		function closeArert() {
			$("#success").css("display", "none");
			$("#successFormard").css("display", "none");

		}
	</script>

</div>
