<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addDriverJobType.in"/>">Driver Job Type</a> /
						<span id="NewDriverJobType">Edit Driver Job Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addDriverJobType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty driverJobType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddDriver">Edit Driver JobType</h1>
								</div>
								<div class="panel-body">
									<form action="updateDriJobType.in" method="post"
										onsubmit="return validateJobTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="Driver_theft">Edit
												Job Type:</label>
											<div class="I-size">
												<input name="driJobType" value="${driverJobType.driJobType}"
													id="dJobTypeUpdate" Class="form-text" /> <label
													id="errorEditJobType" class="error"></label> <input
													type="hidden" name="driJobId"
													value="${driverJobType.driJobId}">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="Driver_theft">Edit
												Remarks:</label>
											<div class="I-size">
												<input name="driJobRemarks"
													value="${driverJobType.driJobRemarks}" id="dJobTypeUpdate"
													Class="form-text" /> <label id="errorEditJobType"
													class="error"></label>
											</div>
										</div>
										<div class="form-JobType">
											<label class="L-size control-label" for="Driver_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateDriverJobTypeEdit()"> <a class="btn btn-link"
														href="addDriverJobType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty driverJobType}">
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