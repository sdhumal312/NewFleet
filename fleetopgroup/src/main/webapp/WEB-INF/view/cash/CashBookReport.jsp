<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CashBook/1.in"/>">Cash Book</a> / <a
						href="<c:url value="/CashBookReport.in"/>">CashBook Report</a> / <span>
						Search Report</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchCashBook.in"/>" method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="text" required="required"
									placeholder="C-ID, Voucher NO"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/CashBook/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="VendorTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">Id</th>
										<th>CashBook</th>
										<th>V.No</th>
										<th>Type</th>
										<th>Date</th>
										<th>Amount</th>
										<th>Status</th>
										<th>Payment</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty CashBook}">
										<c:forEach items="${CashBook}" var="CashBook">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="<c:url value="/showCashBook.in?Id=${CashBook.CASHID}"/>"><c:out
															value="C-${CashBook.CASH_NUMBER}" /></a></td>
												<td><c:out value="${CashBook.CASH_BOOK_NO}" /></td>
												<td><c:out value="${CashBook.CASH_VOUCHER_NO}" /></td>

												<td><c:out value="${CashBook.CASH_PAYMENT_TYPE}" /></td>

												<td><c:out value="${CashBook.CASH_DATE}" /></td>

												<td><c:out value="${CashBook.CASH_AMT_STR}" /></td>
												<td class="fit ar"><c:choose>
														<c:when
															test="${CashBook.CASH_APPROVAL_STATUS_ID == 2 }">
															<small class="label label-success"><c:out
																	value="${CashBook.CASH_APPROVAL_STATUS}" /></small>
														</c:when>
														<c:otherwise>
															<small class="label label-danger"><c:out
																	value="${CashBook.CASH_APPROVAL_STATUS}" /></small>
														</c:otherwise>
													</c:choose></td>
												<td><c:out value="${CashBook.CASH_NATURE_PAYMENT}" />
													<ul class="list-inline no-margin">
														<li><c:out value="${CashBook.CASH_PAID_RECEIVED}" /></li>

													</ul></td>

												<td><c:if test="${CashBook.CASH_STATUS_ID == 1}">
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-ellipsis-v"></span>
															</a>

															<ul class="dropdown-menu pull-right">


																<li><sec:authorize
																		access="hasAuthority('EDIT_CASHBOOK')">
																		<a
																			href="<c:url value="/editCashBook.in?Id=${CashBook.CASHID}"/>">
																			<i class="fa fa-edit"></i> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_CASHBOOK')">
																		<a
																			href="<c:url value="/deleteCashBook.in?Id=${CashBook.CASHID}"/>"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
																			<span class="fa fa-trash"></span> Delete
																		</a>
																	</sec:authorize></li>

															</ul>
														</div>
													</c:if></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
</div>