<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="addBatteryType"/>">New BAttery
						Type </a> / <span id="NewJobType">Edit
						BatteryType </span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="/addBatteryType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Type Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery  Type Already Exists.
			</div>
		</c:if>
	
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty BatteryType }">
					
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Battery Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateBatteryType.in" method="post"
										onsubmit="return validateReTypeUpdate()">
										<div class="row">
											<label class="L-size control-label" id="Type"></label>
											<div class="I-size">
												<input type="hidden" class="form-text" name="batteryTypeId"
													value="${BatteryType.batteryTypeId}" /> 
													<label id="errorReType"
													class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="Type">BatteryManufacturerName
												:</label>
											<div class="I-size">
												<select class="form-text selectType select2" name="batteryManufacturerId"
											style="width: 100%;" id="selectReType" >
											<option value="${BatteryType.batteryManufacturerId }">${BatteryType.manufacturerName }</option>
											<c:forEach items="${manufacturer}" var="manufacturer">
												<option value="${manufacturer.batteryManufacturerId}">${manufacturer.manufacturerName}
												</option>
											</c:forEach>
										</select>
											</div>
										</div>
									
										<div class="row">
											<label class="L-size control-label" id="Type">batteryType
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="batteryType" maxlength="250"
													value="${BatteryType.batteryType}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										<c:if test="${configuration.showBatteryPartNumber}">
										<div class="row">
											<label class="L-size control-label" id="Type">partNumber
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="partNumber" maxlength="250"
													value="${BatteryType.partNumber}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										</c:if>
										
										<div class="row">
											<label class="L-size control-label" id="Type">description
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="description" maxlength="250"
													value="${BatteryType.description}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										
										<div class="row1" id="grptimeInterval" class="form-group">
												<label class="L-size control-label" for="time_interval">Battery
													Warranty 
												</label>

												<div class="I-size">
													<div class="col-md-4">
														<input type="number" class="form-text"
															name="warrantyPeriod" min="0" maxlength="2"
															id="warrantyTypeId"
															value="${BatteryType.warrantyPeriod}"
															ondrop="return false;"> <span
															id="timeIntervalIcon" class=""></span>
														<p class="help-block">(e.g. 12 month)</p>
													</div>
													<div class="col-md-4">
														<select class="form-text" id="warrantyTypeId"
															name="warrantyTypeId"
															required="required">
														<option value="${BatteryType.warrantyTypeId} ">${BatteryType.warrantyType}</option>
														<option value="1">Month(s)</option>
															<option value="2">Year(s)</option>
														</select>
													</div>

												</div>

											</div>
											
											<div class="row">
											<label class="L-size control-label" id="Type">Warrtenty Terms
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="warrentyterm" maxlength="250"
													value="${BatteryType.warrentyterm}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
									
										
										<div class="form-group">
											<label class="L-size control-label" for="Job_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addBatteryType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty BatteryType}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			 <%-- <div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>  --%>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
</div>