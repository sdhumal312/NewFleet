<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
		<section class="invoice">
			<div class="row">
				<table class="table table-hover table-bordered table-striped">
					<tbody>
						<tr>
							<td><c:choose>
									<c:when test="${company.company_id != null}">
										<img
											src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="280"
											height="40" />

									</c:when>
									<c:otherwise>
										<i class="fa fa-globe"></i>
										<c:out value="${company.company_name}" />
									</c:otherwise>
								</c:choose></td>
							<td>Print By: ${company.firstName}_${company.lastName}</td>
						</tr>
						<tr>
							<td colspan="2">Branch :<c:out
									value=" ${company.branch_name}  , " /> Department :<c:out
									value=" ${company.department_name}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="row invoice-info">
				<h3>
					<c:out value="${TripCol.VEHICLE_GROUP}" />
				</h3>
			</div>
			<div class="row ">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr class="row">
								<td>Trip ID :</td>
								<td><c:out value="TS- ${TripCol.TRIPCOLLNUMBER}" /></td>
								<td>Created Date:</td>
								<td><c:out value="${TripCol.CREATED}" /></td>
							</tr>
							<tr class="row">
								<td>Vehicle :</td>
								<td><c:out value="${TripCol.VEHICLE_REGISTRATION}" /></td>
								<td>Route:</td>
								<td><c:out value="${TripCol.TRIP_ROUTE_NAME}" /></td>
							</tr>
							<tr class="row">
								<td>Date :</td>
								<td><c:out value="${TripCol.TRIP_OPEN_DATE}" /></td>
								<td>Singl:</td>
								<td><c:out value="${TripCol.TRIP_SINGL}" /></td>
							</tr>
							<tr class="row">
								<td>Driver :</td>
								<td><c:out value="${TripCol.TRIP_DRIVER_NAME}" /></td>
								<td>Cleaner:</td>
								<td><c:out value="${TripCol.TRIP_CLEANER_NAME}" /></td>
							</tr>
							<tr class="row">
								<td>Conductor:</td>
								<td><c:out value="${TripCol.TRIP_CONDUCTOR_NAME}" /></td>
								<td>Opening KM :</td>
								<td><c:out value="${TripCol.TRIP_OPEN_KM}" /></td>
							</tr>
							<tr class="row">
								<td>Closing KM:</td>
								<td><c:out value="${TripCol.TRIP_CLOSE_KM}" /></td>
								<td>Usage :</td>
								<td><c:out value="${TripCol.TRIP_USAGE_KM}" /></td>
							</tr>
							<tr class="row">
								<td colspan="2">
									<table class="table table-bordered">
										<thead>
											<tr class="breadcrumb">
												<th>Income Name</th>
												<th>Amount</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty TripColIncome}">

												<c:forEach items="${TripColIncome}" var="TripColIncome">

													<tr data-object-id="" class="ng-scope">
														<td><c:out value="${TripColIncome.incomeName}" /></td>
														<td><c:out value="${TripColIncome.incomeAmount}" /></td>
													</tr>
												</c:forEach>
												<tr>
													<td>
														<h5 style="text-align: right;">Total Income :</h5>
													</td>
													<td>
														<h5>
															<i class="fa fa-inr"></i> ${TripCol.TOTAL_INCOME}
														</h5>
													</td>
												</tr>
											</c:if>
										</tbody>
									</table>
								</td>
								<td colspan="2">
									<table class="table table-bordered">
										<thead>
											<tr class="breadcrumb">
												<th>Expense Name</th>
												<th>Amount</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty TripColExpense}">

												<c:forEach items="${TripColExpense}" var="TripColExpense">

													<tr data-object-id="" class="ng-scope">
														<td><c:out value="${TripColExpense.expenseName}" /></td>

														<td><c:out value="${TripColExpense.expenseAmount}" /></td>

													</tr>
												</c:forEach>
												<tr>
													<td>
														<h5 style="text-align: right;">Total Expense :</h5>
													</td>
													<td>
														<h5>
															<i class="fa fa-inr"></i> ${TripCol.TOTAL_EXPENSE}
														</h5>
													</td>
												</tr>
											</c:if>
										</tbody>
									</table>
								</td>
							</tr>
							<c:choose>
								<c:when test="${TripCol.TRIP_CLOSE_STATUS_ID == 2}">
									<tr class="row">
										<td>Closed By:</td>
										<td><c:out value="${TripCol.LASTMODIFIEDBY}" /></td>
										<td>Closed Date :</td>
										<td><c:out value="${TripCol.lASTUPDATED}" /></td>
									</tr>

									<tr class="row">

										<td colspan="2" class="key"><h4>Total Payment :</h4></td>
										<td colspan="2"><h4>
												<i class="fa fa-inr"></i> ${incomeBalance}
											</h4></td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr class="row">
										<td colspan="2" class="key"><h4>STATUS IS</h4></td>
										<td colspan="2"><h4>
												<c:out value=" ${TripCol.TRIP_CLOSE_STATUS}" />
											</h4></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</section>
	</sec:authorize>
</div>