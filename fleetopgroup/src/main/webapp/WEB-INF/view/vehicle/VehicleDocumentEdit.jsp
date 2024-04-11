<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> /  <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
								value="${vehicle.vehicle_registration}" /></a> / <a
						href="<c:url value="ShowVehicleDocument.in?vehid=${vehicle.vid}"/>">
							Vehicle Document</a> / <span >Edit
							VehicleDocument</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="ShowVehicleDocument.in?vehid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Vehicle"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="Vehicle"> <img
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
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>

							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<div class="col-md-9">
				<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
					<div class="box">
						<div class="box-header">
							<h3 class="box-title">Revise Vehicle Document</h3>
						</div>
						<div class="box-body no-padding">

							<div class="panel-body">
								<div class="form-horizontal">
									<form method="post" action="saveVehicleDocument"
										enctype="multipart/form-data">
										<input type="hidden" name="id" value="${vehicledocument.id}">
										<input type="hidden" name="uploaddate"
											value="${vehicledocument.uploaddate}"> <input
											type="hidden" name="vehid" value="${vehicledocument.vehid}">

										<div class="row1">
											<div class="">
												<label class="L-size control-label">Document Name</label>
												<div class="I-size">
													<input type="text" class="form-text" name="name"
														value="${vehicledocument.name}" readonly="readonly">
												</div>
												<input type="hidden" name="docTypeId" value="${vehicledocument.docTypeId}" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> File<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="file" name="fileUpload" id="file"
													required="required"></input>
											</div>
										</div>

										<div class="L-size control-label"></div>
										<fieldset>
											<div class="L-size control-label"></div>
											<input class="btn btn-primary" data-disable-with="Saving..."
												name="commit" type="submit" value="Revise"> <a
												class="btn btn-link"
												href="ShowVehicleDocument.in?vehid=${vehicle.vid}">Cancel</a>
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
			<div class="col-md-2">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
</div>