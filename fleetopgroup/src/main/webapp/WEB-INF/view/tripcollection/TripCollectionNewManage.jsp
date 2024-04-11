<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripCol.in"/>">TripCollection</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-success btn-sm" href="<c:url value="/addTripCol.in"/>"> <span
							class="fa fa-plus"></span> Create TripCollection
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#CloseTripCollection" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="Click this Close Collection Details"> <span
							class="fa fa-plus"></span> Close Daily TripCollection
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/TripColReport"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-tripadvisor"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Manage Trip</span> <span
								class="info-box-number">${TripManage}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripCollection</span>
							<form action="<c:url value="/searchTripCol.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="tripStutes" name="tripStutes"
										type="text" required="required" placeholder="TS-ID"
										maxlength="20"> <span class="input-group-btn">
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
			</sec:authorize>
		</div>
		<div class="modal fade" id="CloseTripCollection" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search TripCollection Date</h4>
					</div>
					<form action="<c:url value="/closeDailyTripCol.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="closedate"
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
					<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation"><a
										href="<c:url value="/newTripCol.in"/>">Today Trip</a></li>
									<li role="presentation" class="active"><a
										href="<c:url value="/manageTripCol/1.in"/>">Manage Trip <span
											data-toggle="tip" title="" class="badge bg-red"
											data-original-title="${TripManage} Manage TripSheet">${TripManage}</span></a></li>
									<li role="presentation"><a
										href="<c:url value="/closeTripCol/1.in"/>"> Close Trip</a></li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="main-body">
					<c:if test="${!empty TripCol}">
						<div class="box">
							<div class="box-body">
								<table id="TripSheetManage" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">ID</th>
											<th>Vehicle</th>
											<th class="fit ar">Group</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th>Singl</th>
											<th class="fit ar">Expense</th>
											<th class="fit ar">Income</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${TripCol}" var="TripCol">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="<c:url value="/showTripCol.in?ID=${TripCol.TRIPCOLLID}"/>"><c:out
															value="TS-${TripCol.TRIPCOLLNUMBER}" /></a></td>
												<td><a
													href="<c:url value="/showTripCol.in?ID=${TripCol.TRIPCOLLID}"/>"
													data-toggle="tip" data-original-title="Click Details">
														<c:out value="${TripCol.VEHICLE_REGISTRATION}" />

												</a></td>
												<td class="fit ar"><c:out
														value="${TripCol.VEHICLE_GROUP}" /></td>
												<td><c:out value="${TripCol.TRIP_ROUTE_NAME}" /></td>

												<td><c:out value="${TripCol.TRIP_OPEN_DATE}" /></td>

												<td class="fir ar"><c:out value="${TripCol.TRIP_SINGL}" /></td>

												<td class="fit ar"><sec:authorize
														access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
														<a class="btn btn-warning btn-sm"
															href="<c:url value="/addColExpense.in?ID=${TripCol.TRIPCOLLID}"/>">
															<span class="fa fa-plus"></span> Expense
														</a>
													</sec:authorize></td>
												<td class="fit ar"><sec:authorize
														access="hasAuthority('ADD_INCOME_TRIPSHEET')">
														<a class="btn btn-info btn-sm"
															href="<c:url value="/addColIncome.in?ID=${TripCol.TRIPCOLLID}"/>">
															<span class="fa fa-plus"></span> Income
														</a>
													</sec:authorize></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:url var="firstUrl" value="/manageTripCol/1" />
					<c:url var="lastUrl"
						value="/manageTripCol/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/manageTripCol/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/manageTripCol/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/manageTripCol/${i}" />
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
					<c:if test="${empty TripCol}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">No results
								found</p>
						</div>
					</c:if>
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
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>