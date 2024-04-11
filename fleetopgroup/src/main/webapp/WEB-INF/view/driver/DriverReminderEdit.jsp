<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <a
						href="<c:url value="/ShowDriverReminder.in?driver_id=${driver.driver_id}"/>">Driver
						Reminder</a> / Edit Driver Renewal Reminder
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<a class="btn btn-success"
							href="addDriverReminder.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-plus"></i> Add Driver Renewal
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-user" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>

		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${addDriverReminder}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder was successfully created.
					</div>
				</c:if>
				<c:if test="${saveDriverReminderHis}">
					<div class="alert alert-info">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Old Renewal Reminder is Moved to History.
					</div>
				</c:if>
				<c:if test="${updateDriverReminder}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder was successfully Updated .
					</div>
				</c:if>
				<c:if test="${deleteDriverReminder}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Renewal Reminder was successfully Deleted.
					</div>
				</c:if>

				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder was Not created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-header">
								<h3 class="panel-title">New Reminder</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_REMINDER')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">

									<form action="updateDriverReminder.in" method="post"
										enctype="multipart/form-data">

										<input type="hidden" name="driver_remid"
											value="<c:out value="${driverreminder.driver_remid}"/>">

										<input type="hidden" name="driver_id"
											value="<c:out value="${driverreminder.driver_id}"/>">

										<div class="panel panel-default">
											<div class="panel-heading clearfix"></div>

											<div class="panel-body">
												<div class="form-horizontal">
													<fieldset>

														<div class="row1">
															<label class="L-size control-label">Renewal Type
																:<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<select class="form-text" name="driverRenewalTypeId"
																	id="grp_option">
																	<option
																		value="<c:out value="${driverreminder.driverRenewalTypeId}"/>">
																		<c:out value="${driverreminder.driver_remindertype}" />
																	</option>
																	<c:forEach items="${driverDocType}" var="driverDocType">
																		<option value="${driverDocType.dri_id}"><c:out
																				value="${driverDocType.dri_DocType}" /></option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</fieldset>
													<div class="row1">
														<div class="L-size">
															<label class="col-md-offset-3" id="target"
																for="grp_option">Type ID</label>
														</div>
														<div class="I-size">
															<input type="text" class="form-text"
																name="driver_dlnumber"
																value="${driverreminder.driver_dlnumber}" id="id1"
																data-toggle="tip"
																data-original-title="Enter the Number Format is  Ex:-GH67565"
																onkeypress="return IsNumDriverRenewalNumber(event);"
																ondrop="return false;" maxlength="25"> <label
																class="error" id="errorNumber" style="display: none"></label>
														</div>
													</div>

													<div class="row1">

														<label class="L-size control-label">Validity From
															&amp; To <abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<div class="input-group input-append date">
																<input type="text" class="form-text"
																	value="${driverreminder.driver_dlfrom_show}  to  ${driverreminder.driver_dlto_show}"
																	name="driver_dlfrom_show" required id="DriverReminderdate"
																	maxlength="25"> <span
																	class="input-group-addon add-on"><span
																	class="fa fa-calendar"></span></span>
															</div>
														</div>
													</div>
													<br> <br>
													<div class="row1">
														<label class="L-size control-label">Due Threshold
															:</label>
														<div class="I-size">
															<div class="col-md-2">
																<input type="number" class="form-text"
																	value="${driverreminder.driver_timethreshold}"
																	name="driver_timethreshold" min="1" max="12"
																	maxlength="1" required="required"
																	onkeypress="return IsNumericTimeThru(event);"
																	ondrop="return false;"> <label class="error"
																	id="errorTimeThru" style="display: none"> </label>
															</div>
															<div class="col-md-2">
																<select class="form-text" name="driver_periedthreshold">
																	<option value="0">day(s)</option>
																	<option value="7">Week(s)</option>
																	<option value="28">Month(s)</option>
																</select>
															</div>
														</div>
													</div>

													<fieldset>
														<legend>Upload Driver Renewal Documents</legend>
														<div class="row1">
															<label class="L-size control-label">Browse :</label>
															<div class="I-size">
																<input type="file"
																	accept="image/png, image/jpeg, image/gif"
																	name="input-file-preview" required="required" />

															</div>
														</div>
													</fieldset>

													<div class="panel-footer">
														<fieldset>
															<input class="btn btn-primary"
																data-disable-with="Saving..." name="commit"
																type="submit" value="Save Driver Renewal Reminder"
																data-toggle="modal" data-target="#processing-modal">
															<a class="btn btn-link"
																href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Cancel</a>

														</fieldset>

													</div>


												</div>

											</div>


										</div>
									</form>
								</sec:authorize>

							</div>
							<script>
								$(function() {

									//Date range picker
									$('#DriverReminderdate').daterangepicker();

								});
							</script>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a
						href="showDriver?driver_id=${driver.driver_id}">Overview</a></li>

					<li class="active"><a
						href="ShowReminderHis.in?driver_id=${driver.driver_id}">Reminder
							History</a></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
							<a href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Reminders
								<span class="count muted text-muted pull-right">${ReminderCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<a href="ShowDriverDocument.in?driver_id=${driver.driver_id}">Documents
								<span class="count muted text-muted pull-right">${DocumentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
							<a href="ShowDriverComment.in?driver_id=${driver.driver_id}">Comments
								<span class="count muted text-muted pull-right">${CommentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
							<a href="ShowDriverPhoto.in?driver_id=${driver.driver_id}">Photos
								<span class="count muted text-muted pull-right">${PhotoCount}</span>
							</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(function() {
				$('#DriverReminderdate').daterangepicker();
			});
		});

		$(document).ready(function() {
			function t() {
				var t = $("#grp_option :selected"), n = t.text();
				$("#target").text(n + " Number :")
			}
			$("#grp_option").on("change", function() {
				t()
			}), $("#grp_option").change()
		});
	</script>
</div>
