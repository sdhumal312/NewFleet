<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="updateTripSheetOptions"/>">Trip Sheet Options</a> / <span
						id="NewVehicleType">Edit Trip Sheet Options</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/addTripSheetOptions?id=${OptionsId.tripsheetoptionsId}"/>"> <spring:message
							code="label.master.Cancel" />
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="panel panel-default">
						<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
							<c:if test="${!empty OptionsId}">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Trip Sheet Options</h1>
								</div>
								<div class="panel-body">
									<form action="updateTripSheetOptions.in" method="post"
										onsubmit="return validateTypeUpdate()">

										<input type="hidden" name="tripsheetoptionsId" value="${OptionsId.tripsheetoptionsId}">
										<div class="row">
											<label class="L-size control-label" id="Type">Extra
												Option :</label>
											<div class="I-size">
												<input type="text" required="required" class="form-text"
													name="tripsheetextraname" value="${OptionsId.tripsheetextraname}"
													maxlength="149" placeholder="Enter Extra Option" /> <label
													id="errorvType" class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Remarks</label>
											<div class="I-size">
												<input type="text" class="form-text" id="dJobType"
													value="${OptionsId.tripsheetextradescription}" maxlength="249"
													name="tripsheetextradescription" placeholder="Enter Description" />
												<label id="errordJobType" class="error"></label>
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="<c:url value="addTripSheetOptions"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</c:if>
						</sec:authorize>
						<c:if test="${empty OptionsId}">
							<div class="callout callout-danger">
								<h4>Warning!</h4>
								<p>
									The page no data to Show.., Please Don't Change any URL ID or
									Number.. <br> Don't Enter any Extra worlds in URL..
								</p>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			
		</div>
	</section>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>