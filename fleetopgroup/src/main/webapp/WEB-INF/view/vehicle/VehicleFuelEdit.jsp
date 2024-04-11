<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleFuel"/>">New Vehicle Fuel</a> / <span
						id="NewVehicleFuel">Edit Vehicle Fuel</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addVehicleFuel.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
						<c:if test="${!empty vehiclefuel}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Vehicle Fuel</h1>
								</div>
								<div class="panel-body">
									<form action="updateVehFuel" method="post"
										onsubmit="return validateFuelUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Statues:</label>
											<div class="I-size">
												<input name="vFuel" value="${vehiclefuel.vFuel}"
													id="vFuelUpdate" Class="form-text" /> <label
													id="errorEditFuel" class="error"></label> <input
													type="hidden" name="fid" value="${vehiclefuel.fid}">
											</div>
										</div>
										<div class="form-Fuel">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addVehicleFuel.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</c:if>
					</sec:authorize>
					<c:if test="${empty vehiclefuel}">
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
			<div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/Fuel.validate.js" />"></script>
</div>