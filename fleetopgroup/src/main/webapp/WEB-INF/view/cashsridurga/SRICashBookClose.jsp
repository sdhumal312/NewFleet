<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/SRICashBook/1.in"/>">Cash Book</a> / <span
						id="NewVehi">Close CashBook Per Day</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/SRICashBook/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
				<div class="col-md-offset-1 col-md-9">
					<c:if test="${param.success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Closed Successfully.
						</div>
					</c:if>
					<c:if test="${param.successAddMain eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Closed Balance add in Main Cash Successfully.
						</div>
					</c:if>
					<c:if test="${param.CloseLastday eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Close Last Date CashBook Balances...
						</div>
					</c:if>
					<c:if test="${param.alreadyClosed eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Date Already Closed.
						</div>
					</c:if>
					<c:if test="${param.CloseSubCashBook eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Close ${SubCashBook} First. Should be close All Sub Cash Book first ...
						</div>
					</c:if>
					<c:if test="${param.tripDayNotClosed eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Close  Daily Trip Collection For This Date First !
						</div>
					</c:if>
					<form action="SRIsaveCloseCashBook.in" method="post">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Close Cash Book Balance Information</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Close
												Cash Book Name :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="select2" style="width: 100%;"
													name="CASH_BOOK_ID" id="CashBookNumber"
													required="required">
													<c:if test="${!empty CashBook}">
														<c:forEach items="${CashBook}" var="CashBook">
															<option value="${CashBook.NAMEID}"><c:out
																	value="${CashBook.CASHBOOK_NAME}" /></option>
														</c:forEach>
													</c:if>
												</select> 
												<input type="hidden" name="CASH_BOOK_NAME" id="CASH_BOOK_NO"/>
												<label class="error" id="errorVendorName"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Close Date Of
												Payment <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="CashPaymentDate">
													<input type="text" class="form-text" name="CASH_DATE"
														placeholder="dd-mm-yyyy" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<p class="help-block">ex: Min 10 days only</p>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Close By :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference" maxlength="50"
													name="CASH_CLOSED_BY" value="${PaidBy}" readonly="readonly"
													type="text" required="required" placeholder="Paid By name"
													onkeypress="return IsPAIDBY(event);" ondrop="return false;">
												<label class="error" id="errorPAIDBY" style="display: none">
												</label>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label" id="paid">Close
												Remarks :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="CASH_REMARKS" rows="3" maxlength="240">	
												</textarea>
												<label class="error" id="errorPAIDRECEIVED"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<div class="col-md-offset-5 col-md-7">
												<input class="btn btn-success" name="commit" type="submit"
													value="Close CashBook"> <a class="btn btn-info"
													href="<c:url value="/SRIaddCashBook.in"/>">Cancel</a>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#CashPaymentDate").datepicker({
				autoclose : !0,
				todayHighlight : !0,
				format : "dd-mm-yyyy",
				startDate : "-"+${CashBookViewDays}+"d",
				endDate : '+0d'
			});
			$('#CASH_BOOK_NO').val($("#CashBookNumber option:selected").text().trim());
			$('#CashBookNumber').on('change', function() {
				$('#CASH_BOOK_NO').val($("#CashBookNumber option:selected").text().trim());
			});
		})
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBook.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>