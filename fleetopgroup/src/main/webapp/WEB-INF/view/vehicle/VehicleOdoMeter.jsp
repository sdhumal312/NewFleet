<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<a href="vehicle.in"><span id="Hom">Vehicle</span></a> / <small><span
					id="AllVehicl">Vehicle OdoMeter Entries</span></small>
				<div class="pull-right">
					<div id="langSelect"></div>
				</div>
			</div>
			<div class="box-body">
				<!-- Show the User Profile -->
				<div class="pull-left">
					<a
						href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
						class="zoom" data-title="" data-footer="" data-type="image"
						data-toggle="lightbox"> <img
						src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
						class="img-rounded" alt="Cinque Terre" width="100" height="100" />
					</a>
				</div>
				<div class="pull-left1">
					<h3 class="secondary-header-title">
						<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
								value="${vehicle.vehicle_registration}" />
						</a>
					</h3>

					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li><span class="fa fa-retweet">.</span> <a href="#"><c:out
										value="${vehicle.vehicle_Status}" /></a></li>
							<li><span class="fa fa-bitcoin"></span> <a href="#"><c:out
										value="${vehicle.vehicle_Type}" /></a></li>
							<li><span class="fa fa-collapse-up"></span> <span
								class="text-muted"><c:out
										value="${vehicle.vehicle_Group}" /></span></li>
							<li><span class="fa fa-user"></span> <span
								class="text-muted">Unassigned</span></li>
						</ul>
					</div>

				</div>


				<div class="pull-right">
					<a class="btn btn-default" onclick="history.go(-1); return true;">
						<span class="fa fa-arrow-left"> Back</span>
					</a> <a class="btn btn-success" data-toggle="modal"
						data-target="#vehicleOdoMeter"> <i class="fa fa-plus"></i> Add
						OdoMeter Reading
					</a> <a class="btn btn-link" href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>

				</div>
			</div>

		</div>
	</section>
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="vehicleOdoMeter" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">

				<form:form method="post" action="saveVehicleOdaMeter.in"
					enctype="multipart/form-data">
					<form:errors path="*" cssClass="error" />

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Vehicle OdaMeter</h4>
					</div>
					<div class="modal-body">

						<input type="hidden" name="vehid" value="${vehicle.vid}" />
						<div class="row">
							<div class="form-group">
								<label Class="col-md-3">OdaMeterName</label>
								<div class="col-md-6">
									<form:select path="name" class="form-text">
										<c:forEach items="${vehicledoctype}" var="vehicledoctype">
											<option><c:out value="${vehicledoctype.vDocType}" /></option>
										</c:forEach>
									</form:select>
									<span class="help-block">Categorize this vehicle.</span>

								</div>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Add OdaMeter">Upload files</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>

				</form:form>
			</div>

		</div>
	</div>

	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/VehicleOdaMeterlanguage.js" />"></script>
				<!-- <script type="text/javascript" src="js/.validate.js" />"></script> -->

				<c:if test="${saveVehicleOdaMeter}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle OdaMeter was Successfully Created.
					</div>
				</c:if>
				<c:if test="${param.revise eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver OdaMeter was successfully Edited.
					</div>
				</c:if>
				<c:if test="${deleteVehicleOdaMeter}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vehicle OdaMeter was successfully Removed.
					</div>
				</c:if>
				<c:if test="${alreadyVehicleOdaMeter}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This OdaMeter was Already created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-header">
								<div class="pull-right">
									<div id="langSelect"></div>
								</div>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="VehicleOdaMeter"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="icon">OdaMeter Reading</th>
											<th class="icon">Updated Date</th>
											<th class="icon">Updated Place</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty vehicleOdaMeterList}">
											<c:forEach items="${vehicleOdaMeterList}"
												var="vehicleOdaMeter">
												<tr data-object-id="" class="ng-scope">
													<td class="icon"><a
														href="sortVehicleOdaMeter.in?id=${vehicleOdaMeter.id}">
															<c:out value="${vehicleOdaMeter.name}" />
													</a></td>
													<td class="icon"><c:out
															value="${vehicleOdaMeter.uploaddate}" /></td>
													<td class="icon"><c:out
															value="${vehicleOdaMeter.filename}" /></td>

												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">
					<%-- <li class="active"><a
						href="ShowDriverD.in?driver_id=${driver.driver_id}">OdaMeter
							All History </a></li> --%>
					<li><a href="ShowVehicleDocument.in?vehid=${vehicle.vid}">
							Vehicle Document</a></li>
					<li><a href="ShowVehiclePhoto.in?vehid=${vehicle.vid}">
							Vehicle Photos</a></li>
					<li><a href="ShowVehiclePurchase.in?vehid=${vehicle.vid}">
							Vehicle Purchase Info</a></li>
					<li><a href="VehicleRenewalReminder.in?vid=${vehicle.vid}">Vehicle
							Renewal Reminders Entries</a></li>
					<li><a href="VehicleFuelDetails.in?vid=${vehicle.vid}">
							Vehicle Fuel Entries</a></li>
				</ul>
			</div>

		</div>
	</section>
</div>