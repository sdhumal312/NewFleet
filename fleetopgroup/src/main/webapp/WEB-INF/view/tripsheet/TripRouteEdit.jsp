<%@ include file="taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripRoute/1.in"/>">New Trip Route</a> / <span
						id="NewTripRoute">Edit Trip Route</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/newTripRoute/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="main-body">
			<div class="row">
				<div class="col-xs-9">
					<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
						<c:if test="${!empty TripRoute}">

							<div class="box">
								<div class="box-body">
									<form id="formEditTripRoute"
										action="<c:url value="/uploadTripRoute.in"/>" method="post"
										enctype="multipart/form-data" name="formEditTripRoute"
										role="form" class="form-horizontal">

										<div class="form-horizontal">
											<input type="hidden" value="${TripRoute.routeID}"
												name="routeID" /> <input type="hidden"
												value="${TripRoute.routeNumber}" name="routeNumber"
												id="routeNumber" />
										<c:if test="${subRouteTypeNeeded}">
											<div class="row1">
												<label class="L-size control-label">Route Type :</label>
												<div class="I-size">
													<select name="routeType"  class="select2 required"
														style="width: 100%;" required="required"
														id="tripRouteType">
														<option value="1">MAIN ROUTE</option>
														<option value="2">SUB ROUTE</option>
													</select>
												</div>
											</div>
											<div class="row1" id="mainRoute">
												<label class="L-size control-label">Main Route :</label>
												<div class="I-size">
													<select class="form-text" name="mainRouteId" id="mainRouteId">
														<option value="${TripRoute.mainRouteId }">
																		<c:out value="${TripRoute.mainRoute}" />
														</option>
															<c:forEach items="${routeList}" var="routeList">
																<option value="${routeList.routeID}">
																		<c:out value="${routeList.routeName}" />
																</option>
															</c:forEach>
														</select>
												</div>
											</div>
										</c:if>
											<div class="row1" id="grprouteName" class="form-group">
												<label class="L-size control-label">Route Name : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="routeName"
														placeholder="Enter Route Name" maxlength="200"
														value="${TripRoute.routeName}" id="routeName"
														onkeypress="return RouteName(event);"
														ondrop="return false;" /><span id="routeNameIcon"
														class=""></span>
													<div id="routeNameErrorMsg" class="text-danger"></div>
													<label id="errorRouteName" class="error"></label>
												</div>
												<input type="hidden"  name="subRouteTypeNeeded" value="${subRouteTypeNeeded}" />
											</div>
											<div class="row1" id="grpVehicleGroup">
												<div class="form-group">
													<label class="L-size string required control-label">Group
														Service :</label>
													<div class="I-size">
														<select style="width: 100%;" name="vehicleGroupId"
															id="vehiclegroupid">
															<c:forEach items="${vehicleGroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																	<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select> <input type="hidden" name="driver_group"
															id="driver_group" />
													</div>
													<span id="vehicleGroupIcon" class=""></span>
													<div id="vehicleGroupErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<div class="row1" id="grprouteNumber" class="form-group">
												<label class="L-size control-label">Route Number : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="routeNo"
														placeholder="Enter Route NO" maxlength="25"
														value="${TripRoute.routeNo}" id="routeNumber"
														onkeypress="return RouteNo(event);" ondrop="return false;" /><span
														id="routeNumberIcon" class=""></span>
													<div id="routeNumberErrorMsg" class="text-danger"></div>
													<label id="errorRouteNo" class="error"></label>
												</div>
											</div>
										<c:if test="${routeConfig.showFromTo}">
											<div class="row1">
												<label class="L-size control-label" id="ClassofVehicle">Route
													Time From :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" readonly="readonly"
																value="${TripRoute.routeTimeFrom}" name="routeTimeFrom"
																required="required"> <span
																class="input-group-addon"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
													</div>
													<label class="L-size control-label" id="ClassofVehicle">Route
														Time To : <abbr title="required">*</abbr>
													</label>
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" readonly="readonly"
																value="${TripRoute.routeTimeTo}" name="routeTimeTo"
																required="required"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>	
										<c:if test="${routeConfig.showAttandancePoint}">
											<div class="row1" id="grproutePoint" class="form-group">
												<label class="L-size control-label">Route
													Point/attendance : <abbr title="required">*</abbr>
												</label>
												<div class="L-size">
													<input type="text" class="form-text" name="routrAttendance"
														placeholder="eg: 2" maxlength="8" id="routePoint"
														value="${TripRoute.routrAttendance}"
														onkeypress="return RouteKM(event);" ondrop="return false;" />
													<span id="routePointIcon" class=""></span>
													<div id="routePointErrorMsg" class="text-danger"></div>
													<label id="errorRouteKM" class="error"></label>
												</div>
											</div>
										</c:if>	
											<div class="row1" id="grprouteHour" class="form-group">
												<label class="L-size control-label">Route Total Hour
													: <abbr title="required">*</abbr>
												</label>
												<div class="L-size">
													<div class="input-group">
														<input type="text" class="form-text" name="routeTotalHour"
															placeholder="eg: 48" maxlength="8" id="routeHour"
															value="${TripRoute.routeTotalHour}"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															ondrop="return false;" /> <span
															class="input-group-addon"> <span
															aria-hidden="true">HH</span>
														</span>
													</div>
													<span id="routeHourIcon" class=""></span>
													<div id="routeHourErrorMsg" class="text-danger"></div>
													<label id="errorRouteKM" class="error"></label>
												</div>
											</div>
											<div class="row1" id="grprouteLiter" class="form-group">
												<label class="L-size control-label" for="routeLiter">Route
													Total Volume : <abbr title="required">*</abbr>
												</label>
												<div class="L-size">
													<div class="input-group">
														<input type="text" class="form-text"
															name="routeTotalLiter" placeholder="eg: 30" maxlength="8"
															id="routeLiter" value="${TripRoute.routeTotalLiter}"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															ondrop="return false;" /> <span
															class="input-group-addon"> <span
															aria-hidden="true">L</span>
														</span>
													</div>
													<span id="routeLiterIcon" class=""></span>
													<div id="routeLiterErrorMsg" class="text-danger"></div>
													<label id="errorRouteKM" class="error"></label>
												</div>
											</div>
											<div class="row1" id="grprouteKM" class="form-group">
												<label class="L-size control-label">Route
													Approximate KM :<abbr title="required">*</abbr>
												</label>
												<div class="L-size">
													<div class="input-group">
														<input type="text" class="form-text"
															name="routeApproximateKM" placeholder="eg: 160"
															maxlength="8" id="routeKM"
															value="${TripRoute.routeApproximateKM}"
															onkeypress="return isNumberKey(event)"
															ondrop="return false;" /> <span
															class="input-group-addon"> <span
															aria-hidden="true">KM</span>
														</span>
													</div>
													<span id="routeKMIcon" class=""></span>
													<div id="routeKMErrorMsg" class="text-danger"></div>
													<label id="errorRouteKM" class="error"></label>
												</div>
											</div>

											<div class="row">
												<label class="L-size control-label">Description :</label>
												<div class="I-size">
													<textarea class="text optional form-text"
														name="routeRemarks" rows="3" maxlength="500"
														onkeypress="return RouteRemarks(event);"
														ondrop="return false;">${TripRoute.routeRemarks}
				                                </textarea>
													<label id="errorRouteRemarks" class="error"></label>
												</div>
											</div>
											<div class="form-group">
												<label class="L-size control-label" for="vehicle_theft"></label>
												<div class="col-sm-5">
													<fieldset class="form-actions">
														<button type="submit" onclick="return validateMainRoute()" class="btn btn-success">Update
															Trip Route Name</button>
														<a class="btn btn-link"
															href="<c:url value="/showTripRoute.in?routeID=${TripRoute.routeID}"/>">Cancel</a>
													</fieldset>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</c:if>
					</sec:authorize>
					<c:if test="${empty TripRoute}">
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
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.clockpicker').clockpicker();
			$('#tripRouteType').select2().val([${TripRoute.routeType}]).trigger("change");
			$('#vehiclegroupid').select2().val([${TripRoute.vehicleGroupId}]).trigger("change");
			$("#tripRouteType").change(function() {
		        if($("#tripRouteType").val() == 2){
		        	$('#mainRoute').show();
		        }else{
		        	$('#mainRoute').hide();
		        }
		    }
		    );
			if(${TripRoute.routeType } == 1){
				$('#mainRoute').hide();
			}
		});
		function validateMainRoute(){
	    	if(Number($('#tripRouteType').val()) != 1){
	    		if(Number($('#mainRouteId').val()) == 0){
	    			showMessage('errors', 'Please Select Main Route!');
	    			 document.getElementById("mainRoute").className = 'form-group has-error has-feedback';
	    			return false;
	    		}else{
		    		return true;
	    		}
	    	}
	    	return true;
	    }
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>