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
				<a href="<c:url value="/mailbox/1.in"/>"
					class="btn btn-primary btn-block margin-bottom">Back to Inbox</a>

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
			<div class="col-md-9 col-sm-8 col-xs-12">
				<div class="row">
					<!-- <span id="success" class="alert alert-success col-sm-12 col-md-12"
						style="display: none"></span><br> -->
					<div id="success" >

						<c:if test="${param.success eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Your message has been Sent Successfully. 
							</div>
						</c:if>
					</div>
				</div>
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">Compose New Message</h3>
					</div>

					<form action="<c:url value="/savecompose"/>" method="post"
						enctype="multipart/form-data" name="formMail" role="form">

						<div class="box-body">

							<div class="form-group">
								<input class="" placeholder="To:" id="to" name="TO"
									type="hidden" style="width: 100%"> <span
									id="globalError" class="alert alert-danger col-sm-4"
									style="display: none"></span> <label id="emptyto" class="error"
									style="display: none"></label>
							</div>
							<div class="form-group">
								<input class="form-text" placeholder="Subject:" name="SUBJECT"
									type="text"> <span id="subject"
									class="alert alert-danger col-sm-4" style="display: none"></span>
							</div>
							<div class="form-group">
								<textarea id="compose-textarea" class="form-text"
									style="height: 250px" name="MESSAGE" maxlength="1000">
                     
                               </textarea>
							</div>
							<div class="form-group">
								<div class="btn btn-default btn-file">
									<i class="fa fa-paperclip"></i> Attachment <input type="file"
										name="ATTACHMENT_FILE">
								</div>
								<p class="help-block">Max. 4MB</p>
							</div>

						</div>
						<div class="box-footer">
							<div class="pull-right">

								<button type="submit" class="btn btn-primary">
									<i class="fa fa-envelope-o"></i> Send
								</button>
							</div>
							<a href="<c:url value="/mailbox/1.in"/>" class="btn btn-default">
								<i class="fa fa-times"></i> Discard
							</a>
						</div>

					</form>
				</div>
			</div>
		</div>
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
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			/* $('form').submit(function(event) {
				register(event);
			}); */

			setInterval(function() {
				$("#success").css("display", "none");
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
		});

		function register(event) {
			event.preventDefault();
			$(".alert").html("").hide();
			$(".error-list").html("");
			if ($("#to").val() == "") {
				$("#emptyto").show().html(
						'Please specify at least one recipient.');
				return;
			}
			var formData = $('form').serialize();
			$
					.post(
							"<c:url value="/savecompose"/>",
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

		function closeArert() {
			$("#success").css("display", "none");

		}
	</script>
</div>
