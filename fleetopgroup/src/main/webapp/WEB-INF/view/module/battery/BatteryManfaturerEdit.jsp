<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addBatteryManfaturer"/>">New BAttery
						Manufacturers </a> / <span id="NewJobType">Edit
						Manufacturers Model</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="/addBatteryManfaturer.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Manufacturers Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Manufacturers Type Already Exists.
			</div>
		</c:if>
	
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty batteryManufacturer}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Battery Manufacturers Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateBatteryManufacturer.in" method="post"
										onsubmit="return validateReTypeUpdate()">
										<div class="row">
											<label class="L-size control-label" id="Type">Battery Manufacturers Name:</label>
											<div class="I-size">
												<input type="hidden" class="form-text" name="batteryManufacturerId"
													value="${batteryManufacturer.batteryManufacturerId}" /> <input type="text"
													class="form-text" id="ReType" required="required"
													maxlength="150" name="manufacturerName"
													value="${batteryManufacturer.manufacturerName}"
													placeholder="Enter Model Name" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										
									
										<div class="row">
											<label class="L-size control-label" id="Type">Remarks
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="description" maxlength="250"
													value="${batteryManufacturer.description}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="Job_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addBatteryManufacturer.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty batteryManufacturer}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			 <%-- <div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>  --%>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
</div>