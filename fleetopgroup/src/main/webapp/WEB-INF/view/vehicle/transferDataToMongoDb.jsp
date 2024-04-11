<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						<%-- href="<c:url value="/addVehicleFuel"/>">New --%>
						href="<c:url value="/addVehicleFuel"/>">New
							Vehicle Fuel</a></small>
				</div>
				
			</div>
		</div>
	</section>
	<section class="content">
		
		<c:if test="${param.alreadyVehicleFuel eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Fuel Types Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveVehFuel.in" method="post" name="vehicleStatu"
						onsubmit="return validateFuel()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleFuel"> Vehicle Fuel Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="Fuel">Fuel Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="vFuel" name="VFuel"
										placeholder="Enter Fuel Name" /> <label id="errorvFuel"
										class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						
								
								<div class="btn-group" style="text-align: center; padding-left: 400px; padding-top: 200px;">
									  <a
										href="<c:url value="/transferDataToMongoDbFromMysql"/>"
										class="confirmation btn btn-success"
										onclick="return confirm('Are you sure you Want to Transfer Data?')">
											Transfer Data
									</a>
							   </div>
						</sec:authorize>
					</div>
				</div>
			</div>
			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/Fuel.validate.js" />"></script>
</div>