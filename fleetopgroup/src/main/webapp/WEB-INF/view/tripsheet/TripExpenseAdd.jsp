<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
/* tripIncomeTable_length */
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addTripExpense/1.in"/>">New Trip Expense</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addTripExpense" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Trip Expense</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveTripExpense eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Expense Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadTripExpense eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Expense Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTripExpense eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Expense Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyTripExpense eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Trip Expense Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addTripExpense" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="<c:url value="/saveTripExpense.in"/>" method="post" name="vehicleStatu"
						onsubmit="return validateParts()">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">Trip Expense</h4>
							</div>
							<div class="modal-body">

								<div class="row">
									<label class="L-size control-label">Expense Name :</label>
									<div class="I-size">
										<input type="text" class="form-text" name="expenseName" id="expenseName"
											placeholder="Enter Expense Name" maxlength="200" id="pcName"
											onkeypress="return ExpenseName(event);"
											ondrop="return false;" required="required" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								<br>

								<div class="row">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
											name="expenseRemarks" rows="3" maxlength="200"
											onkeypress="return ExpenseRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorExpenseRemarks" class="error"></label>
									</div>
								</div>
								<br>
								<c:if test="${configuration.IncTripExpenseInDriverSalary}">
								<div>
									<label class="L-size control-label">Include in Driver Balance :</label>
									<div class="I-size">
										<input type="checkbox" id="driverbalance" name="Incldriverbalance">
									</div>
								</div>
								</c:if>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary" onclick="return tripExpenseValidate()">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">

					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="tripExpenseTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="fit">ID</th>
												<th>Expense Name</th>
												<th>Description</th>
												<c:if test="${configuration.IncTripExpenseInDriverSalary}">
												<th>Include In Driver Balance</th>
												</c:if>
												<th>Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty TripExpense}">
												<c:forEach items="${TripExpense}" var="TripExpense">
													<tr>
														<td><c:out value="${TripExpense.expenseID}" /></td>
														<td><c:out value="${TripExpense.expenseName}" /></td>
														<td><c:out value="${TripExpense.expenseRemarks}" /></td>
														<c:if test="${configuration.IncTripExpenseInDriverSalary}">
														<td>
															<c:choose>
																<c:when test="${TripExpense.incldriverbalance}">
																	<c:out value="YES" />
																</c:when>
																<c:otherwise>
																	<c:out value="No" />
																</c:otherwise>
															</c:choose>
														</td>
														</c:if>
														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="<c:url value="/editTripExpense.in?ExpenseID=${TripExpense.expenseID}"/>" >
																				<span class="fa fa-pencil"></span> Edit
																				
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																				<a
																					href="<c:url value="/deleteTripExpense.in?ExpenseID=${TripExpense.expenseID}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete ?')">
																					<span class="fa fa-trash"></span> Delete
																		</a>
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<c:url var="firstUrl" value="/addTripExpense/1" />
						<c:url var="lastUrl"
							value="/addTripExpense/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/addTripExpense/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/addTripExpense/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/addTripExpense/${i}" />
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
					</sec:authorize>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addTripExpense.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
</div>
