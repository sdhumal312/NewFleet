<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CashBookEntry/1.in"/>">Cash Book</a> / <span
						id="NewVehi">New Entries</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchCashBookEntry.in"/>" method="post">
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
					<a href="<c:url value="/CashBookEntry/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>CashBook Search</legend>

						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="CashBookEntryReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Cash
														Book No :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="select2" style="width: 100%;"
															name="CASH_BOOK_ID" id="CashBookNumber"
															required="required">
															<c:if test="${!empty CashBook}">
																<c:forEach items="${CashBook}" var="CashBook">
																    <c:choose>
																    	<c:when test ="${configuration.checkAuthorityForCashBook}">
																	    	<c:set var="cleanedCashBookName" value="${fn:replace(CashBook.CASHBOOK_NAME, ' ', '_')}" />
																			<c:set var="newCashBookName" value="${fn:replace(cleanedCashBookName, '-', '_')}" />
																			<c:set var="authority" value="VIEW_${newCashBookName}" />

																			   <sec:authorize access="hasAuthority('${authority}')">
																	                 <option value="${CashBook.NAMEID}">
																	                 <c:out value="${CashBook.CASHBOOK_NAME}" />
																	         	     </option>
																	            </sec:authorize>
																    	</c:when>
																        <c:otherwise>
																            <option value="${CashBook.NAMEID}">
																                <c:out value="${CashBook.CASHBOOK_NAME}" />
																            </option>
																        </c:otherwise>
																    </c:choose>
																</c:forEach>
															</c:if>
														</select> <label class="error" id="errorVendorName"
															style="display: none"> </label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Payment Type :
													</label>
													<div class="I-size">
														<input type="text" id="NatureDebitPayment"
															name="CASH_NATURE_PAYMENT" style="width: 100%;"
															placeholder="Please Enter 2 or more Name" />

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Receipt Type :
													</label>
													<div class="I-size">
														<input type="text" id="NatureCreditPayment"
															name="CASH_VOUCHER_NO" style="width: 100%;"
															placeholder="Please Enter 2 or more Name" />

													</div>
												</div>
												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="CASH_BOOK_DATE" required="required" readonly="readonly"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>

													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																onclick="this.style.visibility = 'hidden'" name="commit"
																type="submit" value="Search All"> <a
																href="<c:url value="/CashBookEntry/1.in"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>

					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBook.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script type="text/javascript">
		$(function() {

			function cb(start, end) {
				$('#reportrange').val(
						start.format('DD-MM-YYYY') + ' to '
								+ end.format('DD-MM-YYYY'));
			}
			cb(moment().subtract(1, 'days'), moment());

			$('#reportrange').daterangepicker(
					{
						maxDate: new Date(),
						format : 'DD-MM-YYYY',
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ]
						}
					}, cb);

		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>
