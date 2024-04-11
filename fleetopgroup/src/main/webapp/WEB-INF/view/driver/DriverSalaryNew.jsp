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
						href="<c:url value="/DriverSalary.in?ID=${driver.driver_id}"/>">Driver
						Salary</a>
				</div>
				<div class="pull-right">
					<c:if test="${driver.driverStatusId != 2 && driver.driverStatusId != 6}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
					</c:if>
					<c:if test="${driver.driverStatusId == 2}">
					<sec:authorize access="hasAuthority('INACTIVE_DRIVER_EDIT')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
						</c:if>
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
							<div class="box-body">
								<sec:authorize access="!hasAuthority('VIEW_DRIVER_SALARY')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('VIEW_DRIVER_SALARY')">
									<table id="DriverReminderTable"
										class="table table-hover table-striped">
										<thead>
											<tr>
												<th>No</th>
												<th>Date</th>
												<th>Total Working Day</th>
												<th>Per Day Salary</th>
												<th>Salary</th>
												<th>ESI</th>
												<th>PF</th>
												<th>Net Pay</th>
												<th>Advance</th>
												<th>Balance</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty salary}">
												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${salary}" var="salary">
													<tr data-object-id="" class="ng-scope">
														<td class="fit">
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td><c:out value="${salary.SALARY_FROM_DATE} to " />
															<c:out value="${salary.SALARY_TO_DATE}" /></td>

														<td><c:out value="${salary.TOTAL_WORKINGDAY}" /></td>
														<td><c:out value="${salary.MONTH_PERDAYSALARY}" /></td>
														<td><c:out value="${salary.MONTH_SALARY}" /></td>
														<td><c:out value="${salary.TOTAL_ESIAMOUNT}" /></td>
														<td><c:out value="${salary.TOTAL_PFAMOUNT}" /></td>
														<td><c:out value="${salary.TOTAL_NETSALARY}" /></td>

														<td><c:out value="${salary.TOTAL_ADVANCE}" /></td>
														<td><c:out value="${salary.TOTAL_HANDSALARY}" /></td>
														<td><c:choose>
																<c:when test="${salary.SALARY_STATUS == 'OPEN'}">
																	<sec:authorize
																		access="hasAuthority('PAY_DRIVER_SALARY')">
																		<a class="btn btn-success"
																			href="<c:url value="/PaySalary.in?ID=${salary.DSID}&DID=${salary.DRIVER_ID}"/>">
																			Pay Salary </a>
																	</sec:authorize>
																</c:when>
																<c:when test="${salary.SALARY_STATUS == 'PAID'}">
																	<sec:authorize
																		access="hasAuthority('VIEW_DRIVER_SALARY')">
																		<a class="btn btn-success"
																			href="<c:url value="/PaySalaryPrint.in?ID=${salary.DSID}&DID=${salary.DRIVER_ID}"/>">
																			View </a>
																	</sec:authorize>
																</c:when>
																<c:otherwise>
																	<c:out value="${salary.SALARY_STATUS}" />
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>
											</c:if>

										</tbody>
									</table>
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
	</section>
</div>
