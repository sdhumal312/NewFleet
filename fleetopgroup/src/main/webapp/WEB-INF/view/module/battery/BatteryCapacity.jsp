<%@page import="ch.qos.logback.classic.Logger"%>
<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addBatteryManfaturer"/>">Battery C20 Capacity</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Create Battery
								Capacity</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Capacity Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Capacity Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addManufacturer" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveBatteryCapacity.in" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">New Battery C20 Capacity
								</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Battery
									Capacity Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required" 
										maxlength="150" name="batteryCapacity" id="batteryCapacity"
										placeholder="Enter battery Capacity Name" /> <label id="errorReType"
										class="error"></label>
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Description :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="ReType"
										maxlength="249" name="description"
										placeholder="Enter description" /> <label id="errorReType"
										class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return batteryCapacityValidate()">
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
							<div class="box-body">
								<div class="table-responsive">
									<c:if test="${!empty batteryCapacity}">
										<table id="typeTable"
											class="table table-bordered table-striped">

											<thead>
												<tr>
													<th id="TypeName" class="icon">Battery Capacity</th>
													<th id="Usage" class="icon">Description</th>
													<
												</tr>
											</thead>
											<tbody>
												
												<c:forEach items="${batteryCapacity}" var="batteryCapacity">
													<tr>
														<td><c:out value="${batteryCapacity.batteryCapacity}" /></td>
														<td><c:out
																value="${batteryCapacity.description}" /></td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty batteryCapacity}">
										<div class="main-body">
											<p class="lead text-muted text-center t-padded">
												<spring:message code="label.master.noresilts" />
											</p>

										</div>
									</c:if>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
			<%-- <div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div> --%>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
		
		<script type="text/javascript">
		
		function batteryCapacityValidate(){
			if($("#batteryCapacity").val() == undefined || ($("#batteryCapacity").val()).trim() == "" ){
				showMessage('info','Please Enter Battery Capacity')
				return false;
			}
		}
		</script>
</div>