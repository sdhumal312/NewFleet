<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addDriverTrainingType.in"/>">Driver
							Training Type</a> / <span id="NewDriverTrainingType">Edit
							Driver Training Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm" href="addDriverTrainingType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty driverTrainingType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddDriver">Edit Driver TrainingType</h1>
								</div>
								<div class="panel-body">
									<form action="updateDriTrainingType" method="post"
										onsubmit="return validateTrainingTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="Driver_theft">Edit
												Training Type:</label>
											<div class="I-size">
												<input name="dri_TrainingType"
													value="${driverTrainingType.dri_TrainingType}"
													id="dTrainingTypeUpdate" Class="form-text" /> <label
													id="errorEditTrainingType" class="error"></label> <input
													type="hidden" name="dri_id"
													value="${driverTrainingType.dri_id}">
											</div>
										</div>
										<div class="form-TrainingType">
											<label class="L-size control-label" for="Driver_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addDriverTrainingType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty driverTrainingType}">
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
				<%-- <%@include file="masterSideMenu.jsp"%> --%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>