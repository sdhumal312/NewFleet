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
						<legend>Add LHPV Data To TripSheet</legend>

						<div class="row">
								<input type="hidden" id="vid" value="${vid}" />
								<input type="hidden" id="tripSheetId" value="${tripSheetId}" />
								<input type="hidden" id="tripBookref" />
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									
										<div class="form-horizontal ">
											<table style="font-size: 16px;">
												<tbody>
													<tr>
														<td>TripSheet Number : </td><td><span style="padding: 15px;" id="tripSheetNumber"> </span></td>
													</tr>
													<tr>
														<td>Vehicle : </td><td><span style="padding: 15px;" id="vehicle"> </span></td>
													</tr>
													<tr>
														<td>TripRoute : </td><td><span style="padding: 15px;" id="tripRoute"> </span></td>
													</tr>
												</tbody>
											</table>

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
						<input type="button"  class="btn btn-info" value="Add To TripSheet" onclick="return addLhpvDataToTripSheet();"/>
					</div><br/>
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
						<input type="button" class="btn btn-info" value="Add To TripSheet" onclick=" return addLhpvDataToTripSheet();"/>
					</div>
				</div>
			</section>
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/lhpv/addLhpvDataTpTripSheet.js" />"></script>
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