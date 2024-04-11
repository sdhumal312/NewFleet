<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header"></section>
	<!-- Main content -->
	<section class="content">
		<c:if test="${param.message != null}">
			<div class="alert alert-success">${param.message}</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_DASHBOARD')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DASHBOARD')">
			<div class="dashboard-box-left">
				<div class="line-board line-board-4" data-gs-width="4"
					style="height: 820px;">
					<div class="dash-rectangelbox line-board-item " data-gs-height="0"
						data-gs-no-resize="true" data-gs-width="1" data-gs-x="0"
						data-gs-y="0">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title "> Open Issues </span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank" href="<c:url value="/issues/1.in" />"
															data-toggle="tip" data-original-title="Click Open Issues"><span
															class="text-error">${Iopen_Count}</span> </a>
													</div>
													<div class="number-label">Open</div>
												</div>
											</div>
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank" href="<c:url value="/issuesOverdue" />"
															data-toggle="tip"
															data-original-title="Click Overdue Issues"><span
															class="text-error">${IOverdue_Count}</span> </a>
													</div>
													<div class="number-label">Overdue</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="2"
						data-gs-no-resize="true" data-gs-width="2" data-gs-x="1"
						data-gs-y="0">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title "> Vehicle Recent Comments </span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<c:if test="${!empty vehicleComment}">
												<div class="table-responsive">
													<table id="TripSheetTable" class="table">
														<thead>
															<tr>
																<th><small>Comments</small></th>
																<th><small>Created</small></th>
																<th class="fir ar"><small>Date</small></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${vehicleComment}" var="vehicleComment">
																<tr data-object-id="" class="ng-scope">
																	<td><small><a target="_blank"
																			href="VehicleCommentDetails?Id=${vehicleComment.VEHICLE_ID}"
																			data-toggle="tip" data-original-title="Click Details">
																				<c:out value="${vehicleComment.VEHICLE_TITLE}" />
																		</a> <br> <c:out
																				value="${vehicleComment.VEHICLE_COMMENT}" /></small></td>
																	<td><small><abbr data-toggle="tip"
																			data-original-title="${vehicleComment.CREATED_EMAIL}">${vehicleComment.CREATEDBY}</abbr></small></td>
																	<td><small class="text-muted"> <abbr
																			data-toggle="tip"
																			data-original-title="${vehicleComment.CREATED_DATE_DIFFERENT}"><c:out
																					value="${vehicleComment.CREATED_DATE}" /></abbr>
																	</small></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</c:if>
											<c:if test="${empty vehicleComment}">
												<div class="main-body">
													<p class="lead text-muted text-center t-padded">
														<spring:message code="label.master.noresilts" />
													</p>

												</div>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="0"
						data-gs-no-resize="true" data-gs-width="1" data-gs-x="3"
						data-gs-y="0">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title ">Renewal Reminder </span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank" id="todayRenewal"
															<%-- href="<c:url value="/DATERR.in?DATE=2019-07-31" />" --%>
															href="#"
															data-toggle="tip"
															data-original-title="Click Today Renewal"><span
															class="text-error">${RRToday_Count}</span> </a>
													</div>
													<div class="number-label">Renewal</div>
												</div>
											</div>
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank"
															href="<c:url value="/TodayDLRenewal" />"
															data-toggle="tip"
															data-original-title="Click Today DL Renewal"><span
															class="text-error">${DLRToday_Count}</span> </a>
													</div>
													<div class="number-label">DL_Renewal</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="0"
						data-gs-no-resize="true" data-gs-width="1" data-gs-x="0"
						data-gs-y="1">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title "> Service Reminder </span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank"
															href="<c:url value="/OverDueService/1.in" />"
															data-toggle="tip" data-original-title="Click Overdue"><span
															class="text-error">${SROverdue_Count}</span> </a>
													</div>
													<div class="number-label">Overdue</div>
												</div>
											</div>
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank"
															href="<c:url value="/DueSoonService" />"
															data-toggle="tip" data-original-title="Click DueSoon"><span
															class="text-error">${SRDuesoon_Count}</span> </a>
													</div>
													<div class="number-label">DueSoon</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="0"
						data-gs-no-resize="true" data-gs-width="1" data-gs-x="3"
						data-gs-y="1">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title "> Purchase Order </span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank"
															href="<c:url value="/newPurchaseOrders/1.in" />"
															data-toggle="tip"
															data-original-title="Click PO Requisition"><span
															class="text-error">${PUReq_Count}</span> </a>
													</div>
													<div class="number-label">Requisition</div>
												</div>
											</div>
											<div class="col-xs-4 col-md-4 ac">
												<div class="number-block number-block-xl">
													<div class="number-value">
														<a target="_blank"
															href="<c:url value="/PurchaseOrders_ORDERED/1.in" />"
															data-toggle="tip"
															data-original-title="Click PO Requisition"><span
															class="text-error">${PUOrdered_Count}</span> </a>
													</div>
													<div class="number-label">Ordered</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="2"
						data-gs-no-resize="true" data-gs-width="2" data-gs-x="2"
						data-gs-y="2">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title ">Total Percentage Expense
											Chart
											<button class="btn btn-primary btn-sm daterange pull-right">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div id="PieGraph"
												style="min-width: 310px; height: 320px; max-width: 450px; margin: 0 auto"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="dash-rectangelbox line-board-item " data-gs-height="2"
						data-gs-no-resize="true" data-gs-width="2" data-gs-x="0"
						data-gs-y="2">
						<div class="line-board-item-content">
							<div class="dash panel panel-default">
								<div class="dash-header panel-heading">
									<h3 class="panel-title">
										<span class="dash-title ">Total Amount Expense Chart
											<button class="btn btn-primary btn-sm daterange pull-right">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
									</h3>
								</div>
								<div class="panel-body dash-content">
									<div>
										<div class="row ">
											<div id="BarGraph"
												style="min-width: 310px; height: 320px; max-width: 450px; margin: 0 auto"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/charts/highcharts.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/dashboard.js"/>"></script>
</div>