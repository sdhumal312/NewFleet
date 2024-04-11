<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addJobType.in"/>">New Job Type</a> / <span
						id="NewJobType">Edit Job Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addJobType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty ReasonRepairType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Job Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateReasonType.in" method="post"
										onsubmit="return validateReTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="Job_theft">Edit
												Statues:</label>
											<div class="I-size">
												<input name="Reason_Type" value="${ReasonRepairType.reason_Type}"
													id="ReTypeUpdate" Class="form-text" /> <label
													id="errorEditReType" class="error"></label> <input
													type="hidden" name="Reason_id" value="${ReasonRepairType.reason_id}">
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="Job_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateJobTypeEdit()"> <a class="btn btn-link"
														href="addJobType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty ReasonRepairType}">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
</div>
