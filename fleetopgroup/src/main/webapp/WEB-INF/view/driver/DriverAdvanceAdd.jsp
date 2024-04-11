<%@ include file="taglib.jsp"%>
<style>
.partAction {
	float: left;
	width: 20%;
	padding: 3px
}
</style>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <a
						href="<c:url value="/addDriverAdvance.in?ID=${driver.driver_id}"/>">Driver
						Advance</a> / Add Driver Advance
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
							class="fa fa-plus"></i> Add Driver Advance
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
						This Driver Renewal Reminder created successfully .
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
						This Driver Renewal Reminder Updated Successfully .
					</div>
				</c:if>
				<c:if test="${deleteDriverReminder}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Renewal Reminder Deleted successfully .
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
								<h3 class="panel-title">New Driver Advance</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_ADVANCE')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_ADVANCE')">
									<form action="saveDriverAdvance.in" method="post">
										<div class="panel panel-default">
											<div class="panel-body">
												<div class="form-horizontal">
													<fieldset>
														<input name="TRIP_DRIVER_ID" value="${driver.driver_id}"
															required="required" type="hidden"> <input
															name="TRIP_DRIVER_NAME" required="required"
															value="${driver.driver_firstname}" type="hidden">

														<div class="row1">
															<label class="L-size control-label">Jama Balance
																:<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input name="DRIVER_JAMA_BALANCE" required="required"
																	id="jamaBalance" value="${TRIPJAMA_BALANCE}"
																	type="hidden">
																<div class="partAction">
																	<h3>
																		<c:out value="${TRIPJAMA_BALANCE}" />
																	</h3>
																</div>
															</div>
														</div>
													</fieldset>
													<div class="row1">
														<div class="L-size control-label">
															<label>Advance Amount : </label>
														</div>
														<div class="I-size">
															<input type="number" class="form-text" id="advanceAmount"
																max="${TRIPJAMA_BALANCE}" name="DRIVER_ADVANCE_AMOUNT"
																required="required" placeholder="eg: 00"
																onkeyup="javascript:reminderBalance('jamaBalance', 'advanceAmount');">
															<label class="error" id="errorNumber"
																style="display: none"></label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Remain Balance
															:<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<input name="driver_id" value="${TRIPJAMA_BALANCE}"
																type="hidden">
															<div class="partAction" id="reminderBalance">
																<h3>0</h3>
															</div>
														</div>
													</div>

													<div class="row1">
														<label class="L-size control-label">Modes of
															Payment <abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<select class="form-text" name="ADVANCE_PAID_TYPE_ID"
																id="renPT_option" required="required">
																<option value="1">Cash</option>
																<option value="11">UPI</option>
																<option value="3">NEFT</option>
																<option value="4">RTGS</option>
																<option value="5">IMPS</option>
																<option value="6">DD</option>
																<option value="7">CHEQUE</option>
															</select>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label" id="target1"
															for="renPT_option">Enter </label>
														<div class="I-size">
															<input type="text" class="form-text"
																name="ADVANCE_PAID_NUMBER"
																onkeypress="return IsAlphaNumericPaynumber(event);"
																ondrop="return false;" maxlength="25"> <label
																class="error" id="errorPaynumber" style="display: none">
															</label>
														</div>
													</div>
													<div class="row1" id="grpfuelDate" class="form-group">
														<label class="L-size control-label" for="fuelDate">Advance
															Date: <abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<div class="input-group input-append date" id="StartDate">
																<input type="text" class="form-text" name="ADVANCE_DATE"
																	id="fuelDate" required="required"
																	data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
																<span class="input-group-addon add-on"> <span
																	class="fa fa-calendar"></span>
																</span>
															</div>
															<span id="fuelDateIcon" class=""></span>
															<div id="fuelDateErrorMsg" class="text-danger"></div>
														</div>

													</div>
													<div class="row1">
														<div class="L-size control-label">
															<label>Advance Paid BY : </label>
														</div>
														<div class="I-size">
															<input type="text" class="form-text" readonly="readonly"
																name="ADVANCE_PAID_BY" required="required"
																ondrop="return false;" maxlength="150"
																value="${userName}">
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label"
															for="issue_description">Advance Remarks :</label>
														<div class="I-size">
															<textarea class="text optional form-text"
																id="initial_note" name="DRIVER_ADVANCE_REMARK" rows="3">
				                                            </textarea>
														</div>
													</div>
													<div class="row1">
														<div class="L-size control-label">
															<label> </label>
														</div>
														<div class="I-size">
															<input class="btn btn-success"
																data-disable-with="Saving..." name="commit"
																type="submit" value="Save Advance" data-toggle="modal"
																data-target="#processing-modal"> <a
																class="btn btn-link"
																href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

														</div>
													</div>
												</div>
											</div>
										</div>
									</form>
								</sec:authorize>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
			</div>
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	   <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
		<script>
			$(document).ready(
					function() {
						$("#renPT_option").on("change", function() {
							showoption()
						}), $("#renPT_option").change()
						function showoption() {
							var a = $("#renPT_option :selected"), b = a.text();
							"CASH" == b ? $("#target1").text(
									b + " Receipt NO : ") : $("#target1").text(
									b + " Transaction NO : ")
						}

					});
			function reminderBalance(e, n) {
				var i = document.getElementById(e).value, o = document
						.getElementById(n).value, s = parseFloat(i)
						- parseFloat(o);
				isNaN(s) || ($("#reminderBalance").html('<h4>' + s + '</h4>'))
			}
		</script>
	</section>
</div>
