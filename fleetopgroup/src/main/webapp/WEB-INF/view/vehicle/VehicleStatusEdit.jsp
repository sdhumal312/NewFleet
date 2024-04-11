<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleStatus?id=${user.id}"/>"><spring:message
								code="label.master.NewVehicleStatus" /></a></small>/ <small>Edit
						Vehicle Status</small>
				</div>
				<div class="pull-right">

					<a class="btn btn-link btn-sm"
						href="<c:url value="/addVehicleStatus?id=${user.id}"/>"> <i
						class="fa fa-plus"></i> Cancel
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
							<div class="panel-heading">
								<h1 id="AddVehicle">Edit Vehicle Status</h1>
							</div>
							<div class="panel-body">
								<c:if test="${!empty vehiclestatus}">
									<form action="updateVehStatus" method="post"
										onsubmit="return validateStatusUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Statues:</label>
											<div class="I-size">
												<input name="userID" type="hidden" value="${user.id}"
													required="required" /> <input name="lastModifiedBy"
													type="hidden" value="${user.email}" required="required" />
												<input name="vStatus" value="${vehiclestatus.vStatus}"
													id="vStatusUpdate" Class="form-text" /> <label
													id="errorEditStatus" class="error"></label> <input
													type="hidden" name="sid" value="${vehiclestatus.sid}">

											</div>
										</div>

										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="<c:url value="/addVehicleStatus?id=${user.id}"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</c:if>
							</div>
						</sec:authorize>
						<c:if test="${empty vehiclestatus}">
							<div class="callout callout-danger">
								<h4>Warning!</h4>
								<p>The page no data to Show.., Please Don't Change any URL ID or Number..
								<br> Don't Enter any Extra worlds in URL..</p>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/status.validate.js" />"></script>
</div>