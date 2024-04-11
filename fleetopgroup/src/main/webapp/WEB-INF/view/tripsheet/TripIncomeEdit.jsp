<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="addTripIncome/1/1.in"/>">New Trip Income</a> / <span
						id="NewTripIncome">Edit Trip Income</span></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a class="btn btn-link" href="addTripIncome/1/1.in">Cancel</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty TripIncome}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Trip Income</h1>
								</div>
								<div class="panel-body">
									<form action="uploadTripIncome.in" method="post"
										onsubmit="return validateStatusUpdate()">

										<input type="hidden" value="${TripIncome.incomeID}"
											name="incomeID" />
										<div class="row">
											<label class="L-size control-label">Income Name :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="incomeName"
													placeholder="Enter Income Name"
													value="${TripIncome.incomeName}" maxlength="200"
													id="incomeName" onkeypress="return IncomeName(event);"
													ondrop="return false;" required="required" /> <label
													id="errorIncomeName" class="error"></label>
											</div>
										</div>
										<br>

										<div class="row">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="text optional form-text"
													name="incomeRemarks" rows="3" maxlength="200"
													onkeypress="return IncomeRemarks(event);"
													ondrop="return false;">${TripIncome.incomeRemarks}
				                                </textarea>
												<label id="errorIncomeRemarks" class="error"></label>
											</div>
										</div>


										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateTripIncome()"> <a class="btn btn-link"
														href="addTripIncome/1/1.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty TripIncome}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	</section>
</div>