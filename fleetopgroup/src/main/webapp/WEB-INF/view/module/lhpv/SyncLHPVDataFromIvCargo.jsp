<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span>Search
						TripSheets</span>
				</div>
				<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchTripSheet.in"/>" method="post">
								<div class="input-group">
									<input class="form-text"
										id="tripStutes" name="tripStutes" type="text"
										required="required"
										placeholder="TS-ID, Vehicle,T-Route, T-Bookref" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					<a href="<c:url value="/newTripSheetEntries.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Synchronize LHPV Data From IV CARGO</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">

													<label class="L-size control-label">Vehicle Name :</label>

													<div class="I-size">
														<input type="hidden" id="RenewalSelectVehicle" name="vid"
															style="width: 100%;" required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />

													</div>
												</div>
												

											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="return syncLHPVDataFromIVCARGO();"
																	name="commit" type="submit" value="Search">
															<a href="<c:url value="/newTripSheetEntries.in"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
								</div>
							</div>
						</div>
					</fieldset>
					
				</div>
			</div>
			<section class="content">
				<div box-body no-padding id="lhpvDiv" style="display: none;">
					<div style="text-align: center;">
						<input type="button"  class="btn btn-info" value="Create TripSheet" onclick="return createMultipleLhpvTripSheet();"/>
					</div>
					<table style="width:  100%" class="table">
						<thead>
							<tr>
								<th>
									Sr.
								</th>
								<th>
									Select
								</th>
								<th>
									Lhpv Number
								</th>
								<th>
									Lhpv Date
								</th>
								<th>
									Advance
								</th>
								<th>
									Lorry Hire
								</th>
								<!-- <th>
									<a href="#">Action</a>
								</th> -->
							</tr>
						</thead>
						<tbody id="lhpvBody">
							
						</tbody>
					</table>
					<div style="text-align: center;">
						<input type="button" class="btn btn-info" value="Create TripSheet" onclick=" return createMultipleLhpvTripSheet();"/>
					</div>
				</div>
			</section>
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/lhpv/syncLHPVDataToTripSheet.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#vehicle_Group, #closeTripStatus").select2({
				placeholder : "Please Enter"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>