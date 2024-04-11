<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Renewal Reminder</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-success btn-sm"
							href="VehicleRenewalReminderAdd.in?vid=${vehicle.vid}"> <i
							class="fa fa-plus"></i> Add Renewal Reminder
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="<c:url value="/vehicle/1/1"/>"> <span
						id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing Nature"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
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
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="col-md-9 col-sm-9 col-xs-12">

					<c:if test="${param.closeStatus eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							${VMandatory}<br> You should be close first TripSheet or
							change status or close workOrder .
						</div>
					</c:if>
					<c:if test="${alreadyVehicle}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Already Exists.
						</div>
					</c:if>
					<c:if test="${importSave}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Imported Successfully ${CountSuccess} Vehicle Renewal Reminder
							Data.
						</div>
					</c:if>
					<c:if test="${importSaveError}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Driver can not be created with this Import CSV File. <br /> Do
							not Import empty CSV File for Driver Frist_Name, Last_Name,
							Father_Name, Date Of Birth, Start Date Or Required
						</div>
					</c:if>
					<c:if test="${saveRenewalReminder}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Renewal Reminder Created Successfully .
						</div>
					</c:if>
					<c:if test="${renewalRemindeAlready}">
						<div class="alert alert-warning">
							<button class="close" data-dismiss="alert" type="button">x</button>
							<c:if test="${!empty already}">
								<c:forEach items="${already}" var="already" end="2">
				
				${already.vehicle_registration} Vehicle ${already.renewal_type} Type & ${already.renewal_subtype} Sub Type 
				From ${already.renewal_from} To ${already.renewal_to} Already Created.<br>
								</c:forEach>
							</c:if>
						</div>
					</c:if>

					<c:if test="${renewalReceiptAlready}">
						<div class="alert alert-warning">
							<button class="close" data-dismiss="alert" type="button">x</button>
							<c:if test="${!empty ReceiptAlready}">
								<c:forEach items="${ReceiptAlready}" var="ReceiptAlready" end="">
				
				${ReceiptAlready.vehicle_registration} Vehicle ${ReceiptAlready.renewal_type} Type & ${ReceiptAlready.renewal_subtype} Sub Type 
				that Receipt | Challan  Number ${ReceiptAlready.renewal_PayNumber} is Already Created.
				</c:forEach>
							</c:if>
						</div>
					</c:if>

					<c:if test="${importSaveAlreadyError}">
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
					<c:if test="${renewalPAynumberAlready}">
						<div class="alert alert-warning">
							<button class="close" data-dismiss="alert" type="button">x</button>
							<c:if test="${!empty ReceiptAlready}">
								<c:forEach items="${ReceiptAlready}" var="ReceiptAlready" end="">
				
				${ReceiptAlready.vehicle_registration} Vehicle ${ReceiptAlready.renewal_type} Type & ${ReceiptAlready.renewal_subtype} Sub Type 
				that  Payment Receipt Number ${ReceiptAlready.renewal_receipt} is Already Created.
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
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty renewal}">
								<div class="box">
									<div class="box-body">
										<table id="VehicleDocument"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th class="fit">id</th>
													<th>Renewal Name</th>
													<th class="fit ar">Validity From</th>
													<th>Validity To</th>
													<th class="fit">Amount</th>
													<th class="fit"><span class="fa fa-download"></span></th>
													<th class="fit">Revise</th>
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											<tbody>

												<c:forEach items="${renewal}" var="renewal">

													<tr data-object-id="" class="ng-scope">

														<td class="fit"><a
															href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
															data-toggle="tip"
															data-original-title="Click this Renewal Details">RR-<c:out
																	value="${renewal.renewal_R_Number}" />
														</a></td>
														<td><c:choose>
																<c:when
																	test="${renewal.renewal_dueRemDate == 'Due Soon'}">
																	<span class="label label-default label-warning"
																		style="font-size: 12px;"><c:out
																			value="${renewal.renewal_dueRemDate}" /></span>
																</c:when>
																<c:when
																	test="${renewal.renewal_dueRemDate == 'Overdue'}">
																	<span class="label label-default label-danger"
																		style="font-size: 12px;"><c:out
																			value="${renewal.renewal_dueRemDate}" /></span>

																</c:when>
																<c:otherwise>
																	<span class="label label-default label-warning"
																		style="font-size: 12px;"><c:out
																			value="${renewal.renewal_dueRemDate}" /></span>

																</c:otherwise>
															</c:choose> <a><c:out value="${renewal.renewal_type}" /> <c:out
																	value="  ${renewal.renewal_subtype}" /></a></td>

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
														<td class="fit"><span class="badge"> <c:out
																	value="${renewal.renewal_Amount}" />
														</span></td>
														<td class="fit"><c:choose>
																<c:when test="${renewal.renewal_document == true}">
																	<sec:authorize
																		access="hasAuthority('DOWNLOND_RENEWAL')">
																		<a
																			href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_document_id}.in">
																			<span class="fa fa-download"></span>
																		</a>
																	</sec:authorize>
																</c:when>
															</c:choose></td>
														<td class="fit"><sec:authorize
																access="hasAuthority('EDIT_RENEWAL')">
																<a
																	href="reviseRenewalReminder.in?renewal_id=${renewal.renewal_id}">
																	<span class="fa fa-upload"></span>
																</a>
															</sec:authorize></td>

														<c:choose>
															<c:when test="${renewal.renewal_status == 'APPROVED'}">
																<td class="fit ar"><span
																	class="label label-success"><i
																		class="fa fa-check-square-o"></i> <c:out
																			value=" ${renewal.renewal_status}" /></span></td>
															</c:when>
															<c:when test="${renewal.renewal_status == null}">

																<td class="fit ar">
																	<div class="btn-group">
																		<a class="btn btn-default btn-sm dropdown-toggle"
																			data-toggle="dropdown" href="#"> <i
																			class="fa fa-cog material-icons md-settings"></i> <span
																			class="caret"></span>
																		</a>
																		<ul class="dropdown-menu pull-right">
																			<li><span class="label label-warning"><i
																					class="fa fa-dot-circle-o"></i> <c:out
																						value=" NOT APPROVED" /></span></li>
																			<li><sec:authorize
																					access="hasAuthority('EDIT_RENEWAL')">
																					<a
																						href="editRenewalReminder.in?renewal_id=${renewal.renewal_id}">
																						<i class="fa fa-edit"></i> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_RENEWAL')">
																					<a
																						href="deleteRenewalReminder.in?renewal_id=${renewal.renewal_id}"
																						class="confirmation"
																						onclick="return confirm('Are you sure? Delete ')">
																						<i class="fa fa-trash"></i> Delete
																					</a>
																				</sec:authorize></li>

																		</ul>
																	</div>
																</td>
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
																						href="editRenewalReminder.in?renewal_id=${renewal.renewal_id}">
																						<i class="fa fa-edit"></i> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_RENEWAL')">
																					<a
																						href="deleteRenewalReminder.in?renewal_id=${renewal.renewal_id}"
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
											</tbody>
										</table>
									</div>
								</div>

							</c:if>
							<c:if test="${empty renewal}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
</div>