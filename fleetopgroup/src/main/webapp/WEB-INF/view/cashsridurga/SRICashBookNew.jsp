<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/SRICashBook/1.in"/>">Cash Book</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/SRIsearchCashBookShow.in"/>"
							method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">C-</span></span>
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="number" min="1" required="required"
									placeholder="C-ID eg:67"> <span class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>

					<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/SRIaddOldCashBook.in"/>"> <span
							class="fa fa-plus"></span> Add Last CashBook
						</a>
						<a class="btn btn-success btn-sm"
							href="<c:url value="/SRIaddCashBook.in"/>"> <span
							class="fa fa-plus"></span> Add Today CashBook
						</a>
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/SRIcloseCashBook.in"/>"> Close CashBook
							Balance </a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_CASHBOOK')">
						<a class="btn btn-danger btn-sm"
							href="<c:url value="/SRImissingAddCashBook.in"/>"> <span
							class="fa fa-plus"></span> Missing CashBook
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/SRICashBookReport"/>"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
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
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectCashBook}</span> <input
								type="hidden" value="${SelectCashBook}" id="statues"> <span
								class="info-box-number">${CashBookCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search CashBook</span>
							<form action="<c:url value="/SRIsearchCashBook.in"/>"
								method="post">
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
					</div>
				</div>
			</sec:authorize>
		</div>
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>
		<c:if test="${param.deleteCashbook eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Already Updated.
			</div>
		</c:if>
		<c:if test="${param.alreadyClosed eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Date Already Closed. You can not Edit and delete.
			</div>
		</c:if>
		<c:if test="${param.errorCashbook eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook Not Work successfully .
			</div>
		</c:if>
		<c:if test="${cashBookPermissionNotFound}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				You Don't Have Permission To View Any CashBook !
			</div>
		</c:if>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<c:if test="${!empty CashBookName}">
										<c:forEach items="${CashBookName}" var="CashBookName">
											<li role="presentation" id="${CashBookName.CASHBOOK_NAME}"><a
												href="<c:url value="/SRICashBook/${CashBookName.NAMEID}/1.in"/>">${CashBookName.CASHBOOK_NAME}</a></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="VendorTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>CashBook</th>
											<th class="fit ar">V.No</th>
											<th class="fit ar">Type</th>
											<th class="fit ar">Date</th>
											<th class="fit ar">Amount</th>
											<th class="fit ar">Status</th>
											<th>Payment</th>
											<th>Doc</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty CashBook}">
											<c:forEach items="${CashBook}" var="CashBook">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="<c:url value="/SRIshowCashBook.in?Id=${CashBook.CASHID}"/>"><c:out
																value="C-${CashBook.CASH_NUMBER}" /></a></td>
													<td><c:out value="${CashBook.CASH_BOOK_NO}" /></td>
													<td class="fit ar"><c:out
															value="${CashBook.CASH_VOUCHER_NO}" /></td>
													<td class="fir ar"><c:choose>
															<c:when test="${CashBook.PAYMENT_TYPE_ID == 1}">
																<small class="label label-success"><c:out
																		value="${CashBook.CASH_PAYMENT_TYPE}" /></small>
															</c:when>
															<c:otherwise>
																<small class="label label-warning"><c:out
																		value="${CashBook.CASH_PAYMENT_TYPE}" /></small>
															</c:otherwise>
														</c:choose></td>
													<td class="fir ar"><c:out
															value="${CashBook.CASH_DATE}" /></td>
													<td class="fit ar"><c:out
															value="${CashBook.CASH_AMT_STR}" /></td>
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
													<td class="icon"><c:out
															value="${CashBook.CASH_NATURE_PAYMENT}" />
														<ul class="list-inline no-margin">
															<li><c:out value="${CashBook.CASH_PAID_RECEIVED}" /></li>
														</ul></td>
													<td><sec:authorize
															access="hasAuthority('DOWNLOAD_CASHBOOK')">
															<c:choose>
																<c:when test="${CashBook.CASH_DOCUMENT == true}">
																	<a
																		href="${pageContext.request.contextPath}/download/CashDocument/${CashBook.CASH_DOCUMENT_ID}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</c:when>
															</c:choose>
														</sec:authorize></td>
													<td><c:if test="${CashBook.CASH_STATUS_ID == 1}">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>

																<ul class="dropdown-menu pull-right">


																	<li><sec:authorize
																			access="hasAuthority('EDIT_CASHBOOK')">
																			<a
																				href="<c:url value="/SRIeditCashBook.in?Id=${CashBook.CASHID}"/>">
																				<i class="fa fa-edit"></i> Edit Cash
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_CASHBOOK')">
																			<a
																				href="<c:url value="/SRIdeleteCashBook.in?Id=${CashBook.CASHID}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete Cash
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
					<c:url var="firstUrl" value="/SRICashBook/1" />
					<c:url var="lastUrl"
						value="/SRICashBook/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/SRICashBook/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/SRICashBook/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/SRICashBook/${i}" />
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
	<script type="text/javascript">$(document).ready(function(){
		if(!${cashBookPermissionNotFound}){
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		}
		});
	</script>

</div>