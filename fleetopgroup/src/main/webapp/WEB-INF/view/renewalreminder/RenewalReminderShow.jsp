<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/RenewalReminder/${SelectStatus}/${SelectPage}.in"/>">${SelectStatusString}</a>
						/ <a
							href="<c:url value="/showVehicle.in?vid=${renewalReminder.vid}"/>">
							<c:out value="${renewalReminder.vehicle_registration}" />
						</a> / <small>Show Renewal Reminders</small>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/SearchRenRemShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">RR-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="vehicle_registration"
										type="number" required="required" min="1"
										placeholder="ID eg: 1234"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>

						<sec:authorize access="hasAuthority('APPROVED_RENEWAL')">
							<c:choose>
								<c:when
									test="${renewalReminder.renewal_staus_id == 1}">
									<button type="button" class="btn btn-warning btn-sm"
										data-toggle="modal" data-target="#updateapproved"
										data-whatever="@mdo">Create Approval</button>
								</c:when>
							</c:choose>
						</sec:authorize>

						<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addRenewalReminder.in"/>"> <span
								class="fa fa-plus"></span> Add Renewal
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('RE_UPLOAD_RENEWAL_DOCUMENT')">
							<a class="btn btn-success btn-sm" href="<c:url value="/reUploadRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"/>">Edit/
												ReUpload Document <span
												class="class="fa fa-plus"></span>
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('EDIT_RENEWAL_PERIOD')">
									<button type="button" class="btn btn-warning btn-sm"
										data-toggle="modal" data-target="#editrenewalPeriod"
										data-whatever="@mdo">Edit Renewal Period</button>
						</sec:authorize>
						<c:choose>
							<c:when test="${renewal.renewal_approvedID == 0}">
								<sec:authorize access="hasAuthority('EDIT_RENEWAL')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/reviseRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"/>">
										<i class="fa fa-plus"></i> Revise Renewal
									</a>
								</sec:authorize>

								<c:choose>
									<c:when test="${renewalReminder.renewal_staus_id != 2}">
										<sec:authorize access="hasAuthority('DELETE_RENEWAL')">
											<a class="btn btn-info btn-sm"
												href="<c:url value="/deleteRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"/>"
												class="confirmation"
												onclick="return confirm('Are you sure? Delete ')"> <i
												class="fa fa-trash"></i> Delete
											</a>
										</sec:authorize>
									</c:when>
									<c:when
										test="${renewalReminder.renewal_staus_id != 4}">
										<sec:authorize access="hasAuthority('ADD_RENEWAL')">

											<a class="btn btn-success btn-sm" target="_blank"
												href="<c:url value="/approvalRenRemUpload.in?RID=${renewalReminder.renewal_id}"/>"
												class="confirmation"
												onclick="return confirm('Are you sure? Upload File  ')">
												<span class="fa fa-upload"> Upload</span>
											</a>
										</sec:authorize>
									</c:when>
								</c:choose>

							</c:when>
						</c:choose>

						<a class="btn btn-link btn-sm"
							href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>

					</div>
				</div>
			</div>
		</div>
		<sec:authorize access="hasAuthority('!VIEW_RENEWAL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<div class="box">
				<div class="box-body">
					<div class="row">

						<div class="pull-left1">
							<h3 class="secondary-header-title">
								<a
									href="<c:url value="/showVehicle.in?vid=${renewalReminder.vid}"/>"
									data-toggle="tip" data-original-title="Click Vehicle Details">
									<c:out value="${renewalReminder.vehicle_registration}" />
								</a>
							</h3>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><span class="fa fa-bell"> Type :</span> <a href=""
										data-toggle="tip" data-original-title="Renewal Type"> <c:out
												value="${renewalReminder.renewal_type}" />
									</a></li>
									<li><span class="fa fa-usb"> Sub Type :</span> <a href=""
										data-toggle="tip" data-original-title="Renewal Sub Type">
											<c:out value="${renewalReminder.renewal_subtype}" />
									</a></li>
								</ul>
							</div>
						</div>
						<div class="pull-left1">
							<c:choose>
								<c:when test="${renewalReminder.renewal_staus_id == 2}">
									<h2 class="value">
										<span class="label label-success"><i
											class="fa fa-check-square-o"></i> <c:out
												value=" ${renewalReminder.renewal_status}" /></span>
									</h2>
								</c:when>
								<c:when test="${renewalReminder.renewal_status == null}">
									<h3 class="value">
										<span class="label label-warning"><i
											class="fa fa-dot-circle-o"></i> <c:out value=" NOT APPROVED" /></span>
									</h3>
								</c:when>
								<c:otherwise>
									<h2 class="value">
										<span class="label label-danger"><i class="fa fa-ban"></i>
											<c:out value="${renewalReminder.renewal_status}" /></span>
									</h2>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

	</section>
	<section class="content">
	
			<div class="modal fade" id="editrenewalPeriod" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="<c:url value="/updateRenewalReriod.in"/>"
						method="post" name="vehicleStatu"
						onsubmit="return validateStatus()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Edit Renewal Reminder Period</h4>
						</div>
						<div class="modal-body">

									<fieldset>
							<legend>Renewal Period</legend>
							<div class="box">
								<div class="box-body">
								<input type="hidden" class="form-text" name="renewal_id"
									value="${renewalReminder.renewal_id}" />
								<!-- renewal_Amount -->
								<input type="hidden" class="form-text" name="renewal_Amount"
									value="${renewalReminder.renewal_Amount}" />
									<div class="row1" id="grprenewalDate" class="form-group">

										<label class="L-size control-label" for="reservation">Validity
											From &amp; To <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group input-append date">
												<input type="text" class="form-text" name="renewal_from"
													required id="reservation" maxlength="26"
													data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
													data-mask=""
													value="${renewalReminder.renewal_from}  to  ${renewalReminder.renewal_to}">
												<span class="input-group-addon add-on"><span
													class="fa fa-calendar"></span></span>
											</div>
											<span id="renewalDateIcon" class=""></span>
											<div id="renewalDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br>

									<div class="row" id="grprenewalTime" class="form-group">

										<label class="L-size control-label"
											for="renewal_timethreshold">Due Threshold <abbr
											title="required">*</abbr> :
										</label>

										<div class="I-size">
											<div class="col-md-4">
												<input type="text" class="form-text"
													name="renewal_timethreshold" id="renewal_timethreshold"
													value="${renewalReminder.renewal_timethreshold}" min="1"
													max="6" maxlength="2" value="1" required="required"
													onkeypress="return IsNumericTimeThru(event);"
													ondrop="return false;"> <span id="renewalTimeIcon"
													class=""></span>
												<div id="renewalTimeErrorMsg" class="text-danger"></div>
												<label class="error" id="errorTimeThru"
													style="display: none"> </label> <label class="error"
													id="errorTime"> </label>
											</div>
											<div class="col-md-4">
												<select class="form-text" name="renewal_periedthreshold"
													name="renewal_periedthreshold"
													onchange="OnChangeDueThreshold(this)" required="required">
													<option value="0">day(s)</option>
													<option value="7">Week(s)</option>
													<option value="28">Month(s)</option>
												</select>
											</div>
										</div>

									</div>
								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	
	
		<div class="modal fade" id="updateapproved" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="<c:url value="/updateApprovedRenewal.in"/>"
						method="post" name="vehicleStatu"
						onsubmit="return validateStatus()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Renewal Reminder Approval</h4>
						</div>
						<div class="modal-body">

							<input type="hidden" class="form-text" name="renewal_id"
								value="${renewalReminder.renewal_id}" />
							<!-- renewal_Amount -->
							<input type="hidden" class="form-text" name="renewal_Amount"
								value="${renewalReminder.renewal_Amount}" />
							<!-- Approval Id -->
							<input type="hidden" class="form-text" name="renewal_approvedID"
								value="${renewalReminder.renewal_approvedID}" />

							<div class="row">
								<label class="L-size control-label">Status</label>
								<div class="I-size">
									<div class="">
										<div class="btn-group" id="status" data-toggle="buttons">
											<label class="btn btn-default btn-on btn-lg active">
												<input type="radio" value="2" name="renewal_staus_id"
												checked="checked">Approved
											</label> <label class="btn btn-default btn-off btn-lg"> <input
												type="radio" value="6" name="renewal_staus_id">Rejected
											</label>
										</div>
									</div>

								</div>
							</div>
							<br />

							<div class="row">
								<label class="L-size control-label">Approved/Rejected
									Comment </label>

								<div class="I-size">
									<textarea rows="3" cols="8" class="form-text" maxlength="150"
										required="required" name="renewal_approvedComment"
										placeholder="Enter Comment">
								
								</textarea>
									<label id="errorvStatus" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Renewal Profile Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value"><c:out
															value="${renewalReminder.vehicle_registration}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Type :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_type}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Sub Type :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_subtype}" /></td>
												</tr>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Amount Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<c:if test="${configuration.receiptnumbershow}">
												<tr class="row">
													<th class="key">Receipt No | Challan No :</th>
													<td class="value"><span class="label label-success"><c:out
																value="${renewalReminder.renewal_receipt}" /></span></td>
												</tr>
												</c:if>
												<tr class="row">
													<th class="key">Draft Amount :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_Amount}" /></td>
												</tr>
												<c:if test="${configuration.showVendorCol}">
													<tr class="row">
														<th class="key">Vendor :</th>
														<td class="value"><c:out
																value="${renewalReminder.vendorName}" /></td>
													</tr>
												</c:if>
												<c:if test="${configuration.modeofaaymentshow}">
												<tr class="row">
													<th class="key">Modes of Payment :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_paymentType}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.cashtransactionshow}">
												<tr class="row">
													<th class="key"><c:out
															value="${renewalReminder.renewal_paymentType}" /> NO :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_PayNumber}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.paidDateshow}">
												<tr class="row">
													<th class="key">Paid Date :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_dateofpayment}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.tallyIntegrationRequired}">
												<tr class="row">
													<th class="key">Tally Company :</th>
													<td class="value"><c:out
															value="${renewalReminder.tallyCompanyName}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.paidbyshow}">
												<tr class="row">
													<th class="key">Cashier | Paid By : :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_paidby}" /></td>
												</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Approved Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Renewal AID :</th>
													<td class="value"><c:if
															test="${renewalReminder.renewal_approvedID != 0}">
															<a
																href="<c:url value="/1/ShowRenRemApproval.in?AID=${renewalReminder.renewal_approvedID}&page=1" />"
																data-toggle="tip"
																data-original-title="Click this Details"><c:out
																	value="RA-${RenewalReminderApproval.renewalApproval_Number}" /> </a>
														</c:if></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Status :</th>
													<c:choose>
														<c:when
															test="${renewalReminder.renewal_status == 'APPROVED'}">
															<td class="value"><span class="label label-success"><c:out
																		value="${renewalReminder.renewal_status}" /></span></td>
														</c:when>
														<c:otherwise>
															<td class="value"><span class="label label-danger"><c:out
																		value="${renewalReminder.renewal_status}" /></span></td>
														</c:otherwise>
													</c:choose>

												</tr>
												<tr class="row">
													<th class="key">Approved By :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_approvedby}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Approved Date :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_approveddate}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Approved Comment :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_approvedComment}" /></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								<c:if test="${configuration.optionalInformation}">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">+ Optional Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Authorization States :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_authorization}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Remarks :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_number}" /></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								</c:if>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Renewal Period Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Validity From :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_from}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Validity To :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_to}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Due Threshold :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_timethreshold}" /></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<article class="col-md-5 col-sm-5 col-xs-12">
									<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">
										<div class="panel panel-default">
											<div class="panel-body">
												<c:choose>
													<c:when test="${renewalReminder.renewal_document == true && renewalReminder.renewal_document_id > 0}">
														<a class="btn btn-app" data-toggle="tip"
															data-original-title="Click Download"
															href="${pageContext.request.contextPath}/download/RenewalReminder/${renewalReminder.renewal_document_id}.in">
															<i class="fa fa-download"></i>Download
														</a>
													</c:when>
													<c:otherwise>
														<div align="center"></div>
													</c:otherwise>
												</c:choose>
											</div>
											<div class="panel-footer">
												<h4>
													<a
														href="<c:url value="/showVehicle.in?vid=${renewalReminder.vid}"/>"
														data-toggle="tip"
														data-original-title="Click Vehicle Details"> <c:out
															value="${renewalReminder.vehicle_registration}" />
													</a> <a href=""> <c:out
															value="${renewalReminder.renewal_subtype}" /></a>
												</h4>
											</div>
										</div>
									</sec:authorize>
								</article>

							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">
					<li class="active"><a
						href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}">Overview</a></li>

					<li><sec:authorize access="hasAuthority('VIEW_RENEWAL')">
							<a
								href="<c:url value="/VehicleRenewalReminderHis.in?vid=${renewalReminder.vid}"/>">Renewal
								Reminder History <span class="count muted text-muted pull-right"></span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('VIEW_RENEWAL_REVISE')">
							<a
								href="<c:url value="/reviseRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}" />">
								<span class="fa fa-upload"> Revise</span>
							</a>
						</sec:authorize></li>


					<c:choose>
						<c:when test="${renewal.renewal_approvedID == 0}">
							<li><sec:authorize access="hasAuthority('EDIT_RENEWAL')">
									<a
										href="<c:url value="/reviseRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"/>">Revise
										Renewal Reminder <span
										class="count muted text-muted pull-right"></span>
									</a>
								</sec:authorize></li>

							<li><sec:authorize access="hasAuthority('EDIT_RENEWAL')">
									<c:choose>
										<c:when test="${renewalReminder.renewal_status != 'APPROVED'}">
											<a
												href="<c:url value="/editRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"/>">Edit
												Renewal Reminder <span
												class="count muted text-muted pull-right"></span>
											</a>
										</c:when>
									</c:choose>
								</sec:authorize></li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${renewalReminder.createdBy}" /></small> | <small
				class="text-muted"><b>Created date: </b> <c:out
					value="${renewalReminder.created}" /></small> | <small class="text-muted"><b>Last
					updated by :</b> <c:out value="${renewalReminder.lastModifiedBy}" /></small> |
			<small class="text-muted"><b>Last updated date:</b> <c:out
					value="${renewalReminder.lastupdated}" /></small>
		</div>
	</section>
</div>
<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>
		<script>
			$(function() {
				$('#reservation').daterangepicker();
			});
		</script>
		
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>