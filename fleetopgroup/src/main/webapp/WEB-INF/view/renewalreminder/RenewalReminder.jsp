<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/RenewalReminder/${SelectStatus}/1.in"/>">Renewal
							Reminders</a></small>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
						<form action="<c:url value="/SearchRenRemShow.in"/>" method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">RR-</span></span>
								<input class="form-text" id="vehicle_registrationNumber"
									name="vehicle_registration" type="number" required="required"
									min="1" placeholder="ID eg: 1234"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<div class="col-md-2">
						<form action="<c:url value="/searchRenewalReminder.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="vehicle_registration" type="text" required="required"
									placeholder="RR-ID, Rep-Che-No"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/3.in">
							<i class="fa fa-download"></i>
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>

					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addRenewalReminder.in"/>"> <span
							class="fa fa-plus"></span> Add Renewal
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/RenewalReminderReport.in"/>"> <span
							class="fa fa-search"></span> Search
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
							class="fa fa-search"></span> Search Date
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<a class="btn btn-success btn-sm" onclick="email();"> <span
							class="fa fa-plus"></span> Configure E-mail
						</a>
					</sec:authorize> 
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="row">
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayOne} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayOne}"/>">${DayOne_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayTwo} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayTwo}"/>">${DayTwo_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayThree} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayThree}"/>">${DayThree_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFour} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayFour}"/>">${DayFour_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFive} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayFive_Count}"/>">${DayFour_Count}</a></span>
						</div>
					</div>
				</div>

			</sec:authorize>
		</div>
		<c:if test="${alreadyVehicle}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Already Exists.
			</div>
		</c:if>
		<c:if test="${importSave}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Imported Successfully ${CountSuccess} Vehicle Renewal Reminder Data.
			</div>
		</c:if>
		<c:if test="${importSaveError}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Driver can not be created with this Import CSV File. <br /> Do not
				Import empty CSV File for Driver Frist_Name, Last_Name, Father_Name,
				Date Of Birth, Start Date Or Required
			</div>
		</c:if>
		<c:if test="${saveRenewalReminder}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Created Successfully .
			</div>
		</c:if>
		<c:if test="${param.renewalRemindeAlready eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				${already} is Already Created.<br>
			</div>
		</c:if>
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>

		<c:if test="${param.renewalReceiptAlready eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				${ReceiptAlready} is Already Created.
			</div>
		</c:if>
		<c:if test="${param.importSaveAlreadyError eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty Duplicate}">
					<c:forEach items="${Duplicate}" var="Duplicate">
				
				${CountDuplicate} Duplicate Vehicle this NOT Created .... Please Check First <c:out
							value="${Duplicate}" /> .
				</c:forEach>
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.renewalPAynumberAlready eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty ReceiptAlready}">
					<c:forEach items="${ReceiptAlready}" var="ReceiptAlready" end="">
				${ReceiptAlready.vehicle_registration} Vehicle ${ReceiptAlready.renewal_type} Type & ${ReceiptAlready.renewal_subtype} Sub Type 
				that  Payment Receipt Number ${ReceiptAlready.renewal_PayNumber} is Already Created.
				</c:forEach>
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.saveRenewalReminder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Created Successfully .
			</div>
		</c:if>
		<c:if test="${param.updateRenewalReminder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Details Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteRenewalReminder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Updated Already .
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${saveRenewalReminderHis}">
			<div class="alert alert-info">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Old Renewal Reminder is Moved to <a href="RenewalReminderHis.in">
					History URL.</a>.
			</div>
		</c:if>
		<c:if test="${deleteRenewalReminder}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Deleted successfully .
			</div>
		</c:if>
		<c:if test="${updateRenewalReminder}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder Details Updated Successfully.
			</div>
		</c:if>

		<c:if test="${param.sequenceNotFound eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Sequence Not Found Please Contact To System Administrator !
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post"
						action="<c:url value="/importRenewalReminder.in"/>"
						enctype="multipart/form-data">

						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h3 class="panel-title">Import Driver CSV File</h3>
							</div>

							<div class="panel-body">
								<div class="form-horizontal">
									<br>
									<div class="row1">
										<div class="L-size">
											<label> Import Only CSV File: </label>
										</div>
										<div class="I-size">
											<input type="file" accept=".csv" name="import"
												required="required" />
										</div>
									</div>
								</div>

								<div class="row1 progress-container">
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 0%">Upload Your Renewal Entries Please
											wait..</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" id="myButton"
										data-loading-text="Loading..." class="btn btn-primary"
										id="js-upload-submit" value="Add Document" data-toggle="modal"
										data-target="#processing-modal">Import load files</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>


								<script>
									$('#myButton')
											.on(
													'click',
													function() {
														//alert("hi da")
														$(".progress-bar")
																.animate(
																		{
																			width : "100%"
																		}, 2500);
														var $btn = $(this)
																.button(
																		'loading')
														// business logic...

														$btn.button('reset')
													})
								</script>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="RenewalReminder" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Renewal Date</h4>
					</div>
					<form action="<c:url value="/DRR.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="RRDate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="1"><a
										href="<c:url value="/RenewalReminder/1/1.in"/>">NOT
											APPROVED</a></li>
									<li role="presentation" id=3><a
										href="<c:url value="/RenewalReminder/3/1.in"/>">OPEN</a></li>
									<li role="presentation" id="4"><a
										href="<c:url value="/RenewalReminder/4/1.in"/>">IN
											PROGRESS</a></li>
									<li role="presentation" id="5"><a
										href="<c:url value="/RenewalReminder/5/1.in"/>">CANCELED</a></li>
									<li role="presentation" id="2"><a
										href="<c:url value="/RenewalReminder/2/1.in"/>">APPROVED</a></li>
									<li role="presentation" id="6"><a
										href="<c:url value="/RenewalReminder/6/1.in"/>">REJECTED</a></li>
									<li role="presentation" id="7"><a
										href="<c:url value="/RenewalOverDue/7/1.in"/>">OVERDUE<span
											class="info-box-number">${OverDueCount}</span></a></li>
									<li role="presentation" id="8"><a
										href="<c:url value="/RenewalDueSoon/8/1.in"/>">DUE SOON<span
											class="info-box-number">${DueSoonCount}</span></a></li>		
											
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>

		<div class="modal fade" id="configureEmail" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Configure E-Mail</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label">E-mail Id</label>
							<div class="I-size">
								<input type="hidden" value="0" id="configId" /> <input
									type="text" class="form-text" id="emailId"
									onchange="$('#saveEmail').removeAttr('disabled');"
									name="unitCost" placeholder="Enter E-mail Id" /> <label
									id="errorvStatus" class="error"></label>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="submit" onclick="addEmail()" class="btn btn-success"
							disabled="" id="saveEmail">Save</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>

		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="RenewalReminderTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">ID</th>
											<th class="fit ar">Vehicle Name</th>
											<th class="fit ar">Vehicle Group</th>
											<th class="fit ar">Renewal Types</th>
											<th class="fit ar">Validity From</th>
											<th>Validity To</th>

											<th class="fit ar">Amount</th>

											<th class="fit ar">Download</th>
											<th class="fit ar">Revise</th>
											<th class="actions" class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty renewal}">
											<c:forEach items="${renewal}" var="renewal">

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_id}" />"
														data-toggle="tip"
														data-original-title="Click this Renewal Details"><c:out
																value="RR-${renewal.renewal_R_Number}" /> </a></td>
													<td class="fit ar"><a
														href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_id}" />"
														data-toggle="tip"
														data-original-title="Click Renewal Details"><c:out
																value="${renewal.vehicle_registration}" /> </a></td>

													<td class="fit ar"><c:out
															value="${renewal.vehicleGroup}" /></td>

													<td class="fit ar"><c:choose>
															<c:when
																test="${renewal.renewal_dueRemDate == 'Due Soon'}">
																<span class="label label-default label-warning"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>
															</c:when>
															<c:when test="${renewal.renewal_dueRemDate == 'Overdue'}">
																<span class="label label-default label-danger"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>

															</c:when>
															<c:otherwise>
																<span class="label label-default label-info"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>

															</c:otherwise>
														</c:choose> <c:out value="${renewal.renewal_type}" /> <c:out
															value="  ${renewal.renewal_subtype}" /></td>

													<td class="fit ar"><c:out
															value="${renewal.renewal_from}" /></td>


													<td><c:out value="${renewal.renewal_to}" /><br>
														<i class="fa fa-calendar-check-o"></i> <c:set var="days"
															value="${renewal.renewal_dueDifference}">
														</c:set> <c:choose>
															<c:when test="${fn:contains(days, 'now')}">
																<span style="color: #06b4ff;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>
															</c:when>
															<c:when test="${fn:contains(days, 'ago')}">
																<span style="color: red;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>

															</c:when>

															<c:otherwise>
																<span style="color: red;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>


															</c:otherwise>
														</c:choose></td>
													<td class="fit ar"><span class="badge"> <c:out
																value="${renewal.renewal_Amount}" />
													</span></td>

													<td class="fit ar"><c:choose>
															<c:when
																test="${renewal.renewal_document == true && renewal.renewal_document_id > 0}">
																<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">

																	<a
																		href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_document_id}.in">
																		<span class="fa fa-download"> Download</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td>
													<td class="fit ar"><sec:authorize
															access="hasAuthority('EDIT_RENEWAL')">
															<a
																href="<c:url value="/reviseRenewalReminder.in?renewal_id=${renewal.renewal_id}" />">
																<span class="fa fa-upload"> Revise</span>
															</a>
														</sec:authorize></td>
													<c:choose>
														<c:when test="${renewal.renewal_approvedID !=null && renewal.renewal_approvedID != 0}">

															<td class="fit"><a
																href="<c:url value="/1/ShowRenRemApproval.in?AID=${renewal.renewal_approvedID}&page=1" />"
																data-toggle="tip"
																data-original-title="Click this Details"><c:out
																		value="RA-${renewal.renewalAproval_Number}" /> </a></td>
														</c:when>
														<c:when test="${renewal.renewal_status == 'MANDATORY'}">
															<td class="fit ar"><a target="_blank"
																href="<c:url value="/ShowVehicleMandatory.in?vehid=${renewal.vid}"/>">
																	Mandatory <i class="fa fa-external-link"></i>
															</a></td>
														</c:when>
														<c:otherwise>
															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><span class="label label-danger"><i
																				class="fa fa-ban"></i> <c:out
																					value="${renewal.renewal_status}" /></span></li>
																		<li><sec:authorize
																				access="hasAuthority('EDIT_RENEWAL')">
																				<a
																					href="<c:url value="/editRenewalReminder.in?renewal_id=${renewal.renewal_id}" />">
																					<i class="fa fa-edit"></i> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_RENEWAL')">
																				<a
																					href="<c:url value="/deleteRenewalReminder.in?renewal_id=${renewal.renewal_id}" />"
																					class="confirmation"
																					onclick="return confirm('Are you sure? Delete ')">
																					<i class="fa fa-trash"></i> Delete
																				</a>
																			</sec:authorize></li>

																	</ul>
																</div>
															</td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:forEach>
											<c:if test="${!empty RRAmount}">
												<tr>
													<td colspan="5" class="key"><h4>Total:</h4></td>
													<td colspan="4" class="value"><h4>${RRAmount}</h4></td>
												</tr>
											</c:if>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/RenewalReminder/${SelectStatus}/1" />
					<c:url var="lastUrl"
						value="/RenewalReminder/${SelectStatus}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl"
						value="/RenewalReminder/${SelectStatus}/${currentIndex - 1}" />
					<c:url var="nextUrl"
						value="/RenewalReminder/${SelectStatus}/${currentIndex + 1}" />
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
								<c:url var="pageUrl"
									value="/RenewalReminder/${SelectStatus}/${i}" />
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
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderShow.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<input type="hidden" value="${SelectStatus}" id="statues">
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>
</div>