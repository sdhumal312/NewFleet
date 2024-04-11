<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service Reminder</a>
					/ <span>Search Service Reminder</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchServiceReminder.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="text"  min="1" required="required"
									placeholder="S-ID, Ven-No, Tash"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="ViewServiceReminderList.in"> Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Service Reminder Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="ServiceReminderReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Vehicle Name :</label>
													<div class="I-size">
														<div class="col-md-9">
															<input type="hidden" id="ServiceReportVehicle" name="vid"
																style="width: 100%;"
																placeholder="Please Enter 2 or more Vehicle Name" />
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Service Jobs :</label>
													<div class="I-size">
														<div class="col-md-9">
															<input type="hidden" id="from" name="serviceTypeId"
																style="width: 100%;"
																placeholder="Please Enter 2 or more Job Name" />
															<p class="help-block">Select an existing Service Jobs</p>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Service Sub
														Jobs : </label>
													<div class="I-size">

														<div class="col-md-9">
															<select style="width: 100%;" name="serviceSubTypeId"
																id='to'>

															</select>
															<p class="help-block">Select an existing Service Sub
																Jobs</p>
														</div>
													</div>
												</div>

												<div class="row1">
													<label class="L-size control-label">Service :</label>

													<div class="I-size">
														<div class="">
															<div class="btn-group" id="status" data-toggle="buttons">
																<label id="cash-Label"
																	class="btn btn-default btn-off btn-lg"> <input
																	type="radio" value="1" id="OVERDUE"
																	name="serviceStatusId">Over Due
																</label> <label id="credit-Label"
																	class="btn btn-default btn-off btn-lg"> <input
																	type="radio" value="2" id="DUESOON"
																	name="serviceStatusId">Due Soon
																</label>
															</div>
														</div>
													</div>
												</div>

											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">
															<a href="ViewServiceReminderList.in" class="btn btn-info"> <span
																id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/SeviceReminderValidate.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script>
		$(function() {
			$("#to").select2();
		});
		
	</script>

</div>