<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addTripExpense/1.in"/>">New Trip Expense</a> /
						<span id="NewTripExpense">Edit Trip Expense</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="/addTripExpense/1.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty TripExpense}">
						<input type="hidden" id="indriversalary" value="${TripExpense.incldriverbalance}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Trip Expense</h1>
								</div>
								<div class="panel-body">
									<form action="uploadTripExpense.in" method="post"
										onsubmit="return validateStatusUpdate()">

										<input type="hidden" value="${TripExpense.expenseID}"
											name="expenseID" />
										<div class="row">
											<label class="L-size control-label">Expense Name :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="expenseName"
													placeholder="Enter Expense Name"
													value="${TripExpense.expenseName}" maxlength="200"
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
													name="expenseRemarks" rows="3" maxlength="200"
													onkeypress="return ExpenseRemarks(event);"
													ondrop="return false;">${TripExpense.expenseRemarks}
				                                </textarea>
												<label id="errorExpenseRemarks" class="error"></label>
											</div>
										</div>
										<c:if test="${configuration.IncTripExpenseInDriverSalary}">
											<div class="row">
												<label class="L-size control-label">Include in Driver Balance :</label>
												<div class="I-size">
													<input type="checkbox" id="driverbalance" name="Incldriverbalance">
												</div>
											</div>
										</c:if>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateTripExapense()"> <a class="btn btn-link"
														href="addTripExpense/1.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty TripExpense}">
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
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
		
	<script>
		$(document).ready(function() {
			var checkbox = document.getElementById("driverbalance");
			var condition = true;
			if($("#indriversalary").val() == "true" || $("#indriversalary").val() == true)
			{
				checkbox.setAttribute("checked", "checked");
			}
		}); 
	</script>
	
	

		
</div>