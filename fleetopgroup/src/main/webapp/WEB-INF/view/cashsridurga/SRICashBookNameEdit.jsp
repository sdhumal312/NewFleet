<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addCashBookName.in"/>">New CashBook Name</a></small>
					/ <span id="NewTripExpense">Edit CashBook</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/addCashBookName.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty CashBook}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Cash Book Name</h1>
								</div>
								<div class="panel-body">
									<form action="uploadCashBookName.in" method="post"
										onsubmit="return validateStatusUpdate()">

										<input type="hidden" value="${CashBook.NAMEID}" name="NAMEID" />
										<div class="row">
											<label class="L-size control-label">Expense Name :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="CASHBOOK_NAME"
													placeholder="Enter CashBook Name"
													value="${CashBook.CASHBOOK_NAME}" maxlength="200"
													id="pcName" onkeypress="return ExpenseName(event);"
													ondrop="return false;" required="required" /> <label
													id="errorExpenseName" class="error"></label>
											</div>
										</div>
										<br>

										<div class="row">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="text optional form-text"
													name="CASHBOOK_REMARKS" rows="3" maxlength="200"
													onkeypress="return ExpenseRemarks(event);"
													ondrop="return false;">${CashBook.CASHBOOK_REMARKS}
				                                </textarea>
												<label id="errorExpenseRemarks" class="error"></label>
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="<c:url value="/addCashBookName.in"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty CashBook}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2"></div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>

</div>