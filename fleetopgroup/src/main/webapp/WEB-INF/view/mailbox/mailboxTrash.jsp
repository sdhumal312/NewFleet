<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- iCheck -->
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/icheck/blue.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/mailbox/1"/>">MailBox</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">

			<div class="col-md-2 col-sm-2 col-xs-12">
				<a href="<c:url value="/compose.html"/>"
					class="btn btn-primary btn-block margin-bottom">Compose</a>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">${displayname}</h3>
						<div class="box-tools pull-right">
							<div class="has-feedback" style="width: 200px;">

								<form action="<c:url value="/searchmailbox"/>" method="post">
									<div class="input-group">
										<input class="form-text" name="searchmail" type="text"
											required="required" placeholder="Search mail"> <span
											class="input-group-btn">
											<button type="submit" name="search" id="search-btn"
												class="btn btn-success btn-sm">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-12">
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
							<li class="active"><a
								href="<c:url value="/mailbox/1"></c:url>"><i
									class="fa fa-inbox"></i> Inbox <span data-toggle="tip" title=""
									class="badge bg-red"
									data-original-title="${unread} Mail for you">${unread}</span></a></li>
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
				</div>
			</div>
			<div class="col-md-9 col-sm-8 col-xs-12">
				<div class="box box-primary">
					<div class="box-body no-padding">
						<form id="frm-example" action="<c:url value="/deleteinboxbox"/>"
							method="post">

							<input type="hidden" name="showReturn" value="${displayname}">
							<div class="mailbox-controls">
								<!-- Check all button -->
								<span class="btn btn-default btn-sm checkbox-toggle"
									data-toggle="tooltip" title="Select Mails"> <i
									class="fa fa-square-o"></i>
								</span>
								<div class="btn-group" data-toggle="tooltip"
									title="Delete Mails">
									<button id="delete" type="submit"
										class="btn btn-default btn-sm">
										<i class="fa fa-trash-o"></i>
									</button>

								</div>
								<!-- /.btn-group -->
								<a class="btn btn-default btn-sm"
									href="<c:url value="/mailbox/1.in"></c:url>" data-toggle="tooltip"
									title="Reload"> <i class="fa fa-refresh"></i>
								</a>
								<div class="pull-right">
									<!-- Showing pagination -->
									<span id="pagination"> </span>
									<div class="btn-group">
										<span class="btn btn-default btn-sm" id="newer"
											data-toggle="tooltip" title="Newer"> <i
											class="fa fa-chevron-left"></i>
										</span> <span class="btn btn-default btn-sm" id="older"
											data-toggle="tooltip" title="Older"> <i
											class="fa fa-chevron-right"></i>
										</span>
									</div>
									<!-- /.btn-group -->
								</div>
								<!-- /.pull-right -->
							</div>
							<div class="table-responsive mailbox-messages">
								<table class="table table-hover table-nowrap">
									<tbody>
										<c:if test="${!empty mail}">
											<c:forEach items="${mail}" var="mail">
												<c:choose>
													<c:when test="${mail.MAIL_IS_SEEN == false}">
														<tr class="success">
															<td class="mailbox-checkbox"><input type="checkbox"
																name="SelectMailId" value="${mail.MAILBOX_ID_Encode}"></td>
															<td class="mailbox-star"><c:choose>
																	<c:when test="${mail.MAIL_IS_FLAGGED == true}">

																		<a id="myFlagged" href="#"
																			onclick="mailflagged('${mail.MAILBOX_ID_Encode}', '${mail.MAIL_IS_FLAGGED}')"><i
																			class="fa fa-star text-yellow"></i></a>
																	</c:when>
																	<c:otherwise>
																		<a id="myFlagged" href="#"
																			onclick="mailflagged('${mail.MAILBOX_ID_Encode}', '${mail.MAIL_IS_FLAGGED}')"><i
																			class="fa fa-star-o text-yellow"></i></a>
																	</c:otherwise>
																</c:choose></td>
															<td class="mailbox-name"><a
																href="<c:url value="/readmailbox?id=${mail.MAILBOX_ID_Encode}"/>">
																	${mail.MAILBOX_FROM_USER_NAME} <c:choose>
																		<c:when test="${mail.MAIL_TEXTUAL_COUNT != 0}"> 
															(Re: ${mail.MAIL_TEXTUAL_COUNT} )
															</c:when>
																	</c:choose>
															</a></td>
															<td class="mailbox-subject"><b class=""><c:out
																		value="${mail.MAILBOX_NAMESPACE}"></c:out> </b></td>
															<td class="mailbox-attachment"><c:choose>
																	<c:when test="${mail.MAIL_DOCUMENT  == true}">

																		<i class="fa fa-paperclip"></i></c:when>
																</c:choose></td>
															<td class="mailbox-date">${mail.MAIL_DATE}</td>
														</tr>
													</c:when>
													<c:otherwise>



														<tr class="active">
															<td class="mailbox-checkbox"><input type="checkbox"
																name="SelectMailId" value="${mail.MAILBOX_ID_Encode}"></td>
															<td class="mailbox-star"><c:choose>
																	<c:when test="${mail.MAIL_IS_FLAGGED == true}">

																		<a id="${mail.MAILBOX_ID_Encode}"
																			onclick="mailflagged('${mail.MAILBOX_ID_Encode}', '${mail.MAIL_IS_FLAGGED}');"><i
																			class="fa fa-star text-yellow"></i></a>
																	</c:when>
																	<c:otherwise>
																		<a class="myFlagged"
																			onclick="mailflagged('${mail.MAILBOX_ID_Encode}', '${mail.MAIL_IS_FLAGGED}');"><i
																			class="fa fa-star-o text-yellow"></i></a>
																	</c:otherwise>
																</c:choose></td>
															<td class="mailbox-name"><a
																style="font-weight: lighter;"
																href="<c:url value="/readmailbox?id=${mail.MAILBOX_ID_Encode}"/>">
																	${mail.MAILBOX_FROM_USER_NAME} <c:choose>
																		<c:when test="${mail.MAIL_TEXTUAL_COUNT != 0}"> 
															(Re: ${mail.MAIL_TEXTUAL_COUNT} )
															</c:when>
																	</c:choose>
															</a></td>
															<td class="mailbox-subject"><b
																style="font-weight: lighter;">${mail.MAILBOX_NAMESPACE}
															</b></td>
															<td class="mailbox-attachment"><c:choose>
																	<c:when test="${mail.MAIL_DOCUMENT  == true}">

																		<i class="fa fa-paperclip"></i></c:when>
																</c:choose></td>
															<td class="mailbox-date">${mail.MAIL_DATE}</td>
														</tr>
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</c:if>

									</tbody>
								</table>
								<!-- /.table -->
							</div>

						</form>
						<!-- /.mail-box-messages -->
					</div>

				</div>
				<!-- /. box -->
				<c:url var="firstUrl" value="/trashmailbox/1" />
				<c:url var="lastUrl"
					value="/trashmailbox/${deploymentLog.totalPages}" />
				<c:url var="prevUrl" value="/trashmailbox/${currentIndex - 1}" />
				<c:url var="nextUrl" value="/trashmailbox/${currentIndex + 1}" />
				<div class="text-center">
					<ul class="pagination pagination-lg pager">
						<c:choose>
							<c:when test="${currentIndex == 1}">
								<li class="disabled"><a href="#">&lt;&lt;</a></li>
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${firstUrl}">&lt;&lt;</a></li>
								<li><a href="${prevUrl}">&lt;</a></li>
							</c:otherwise>
						</c:choose>
						<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
							<c:url var="pageUrl" value="/trashmailbox/${i}" />
							<c:choose>
								<c:when test="${i == currentIndex}">
									<li class="active"><a href="${pageUrl}"><c:out
												value="${i}" /></a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${currentIndex == deploymentLog.totalPages}">
								<li class="disabled"><a href="#">&gt;</a></li>
								<li class="disabled"><a href="#">&gt;&gt;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${nextUrl}">&gt;</a></li>
								<li><a href="${lastUrl}">&gt;&gt;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- iCheck -->
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/icheck/icheck.min.js" />"></script>
	<!-- Page Script -->
	<script>
		$(function() {
			//Enable iCheck plugin for checkboxes
			//iCheck for checkbox and radio inputs
			$('.mailbox-messages input[type="checkbox"]').iCheck({
				checkboxClass : 'icheckbox_flat-blue',
				radioClass : 'iradio_flat-blue'
			});

			//Enable check and uncheck all functionality
			$(".checkbox-toggle").click(
					function() {
						var clicks = $(this).data('clicks');
						if (clicks) {
							//Uncheck all checkboxes
							$(".mailbox-messages input[type='checkbox']")
									.iCheck("uncheck");
							$(".fa", this).removeClass("fa-check-square-o")
									.addClass('fa-square-o');

						} else {
							//Check all checkboxes
							$(".mailbox-messages input[type='checkbox']")
									.iCheck("check");
							$(".fa", this).removeClass("fa-square-o").addClass(
									'fa-check-square-o');

						}
						$(this).data("clicks", !clicks);
					});

			//Handle starring for glyphicon and font awesome
			$(".mailbox-star").click(function(e) {
				e.preventDefault();
				//detect type
				var $this = $(this).find("a > i");
				var fa = $this.hasClass("fa");

				//Switch states

				if (fa) {
					$this.toggleClass("fa-star");
					$this.toggleClass("fa-star-o");
				}
			});

		});

		jQuery(document).ready(
				function($) {
					var pagenation = ${InboxCount};
					var pageNumber = 1;

					if (pagenation <= 50) {
						$('#pagination').html(
								"1-" + pagenation + " of " + pagenation + "");
						$('#newer').prop('disabled', true);
						$('#older').prop('disabled', true);
					}

				});

		function mailflagged(mailId, status) {
			mailflaggedReport(mailId, status);
		};

		function mailflaggedReport(mailId, status) {
			var formData = "id=" + mailId + "&fl=" + status + "";
			$.post(
							"<c:url value="/mailflagged"/>",
							formData,
							function(data) {
								if (data.message == "success") {
									//window.location.href = "<c:url value="/mailbox"></c:url>";

									$("#success")
											.show()
											.html(
													"Your message has been sent.<button class=\"close\" type=\"button\" onclick=\"closeArert()\">x</button>");

								}
							}).fail(function(data) {
						if (data.responseJSON.error.indexOf("MailError") > -1) {
							$("#globalError").show().html('Mail id is empty');

						}
					});
		}
	</script>
</div>
