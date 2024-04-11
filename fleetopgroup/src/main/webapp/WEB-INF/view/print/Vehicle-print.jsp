<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('DOWNLOND_VEHICLE')">>
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('DOWNLOND_VEHICLE')">
		<section class="invoice">
			<!-- title row -->
			<div class="row">
				<div class="col-xs-12">
					<h2 class="page-header">
						<c:choose>
							<c:when test="${company.company_id != null}">
								<img
									src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
									class="img-rounded" alt="Company Logo" width="280" height="40" />

							</c:when>
							<c:otherwise>
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />
							</c:otherwise>
						</c:choose>
						<small class="pull-right"> Print By:
							${company.firstName}_${company.lastName} I.</small> <small>Branch
							:<c:out value=" ${company.branch_name}  , " /> Department :<c:out
								value=" ${company.department_name}" />
						</small>
					</h2>
				</div>
				<!-- /.col -->
			</div>
			<!-- info row -->
			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">

					<h3 style="margin-top: -10px;">
						<c:out value="${vehicle.vehicle_registration}" />
					</h3>

					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Status :<c:out value=" ${vehicle.vehicle_Status}" /></li>
							<li>Group :<c:out value=" ${vehicle.vehicle_Group}" /></li>
							<li>Route :<c:out value=" ${vehicle.vehicle_RouteName}" /></li>

						</ul>
					</div>
				</div>
			</div>
			<!-- /.row -->

			<!-- Table row -->
			<div class="row">
				<div class="col-xs-12 ">
					<div class="row">
						<div class="col-sm-5 col-xs-5 invoice-col">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Vehicle Basic Details</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th class="key">Chassis No :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_chasis}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Engine No :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_engine}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Manufacture:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_year}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Vehicle Maker:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_maker}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Vehicle Model:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Model}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Vehicle Type:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Type}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Reg. State :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_registrationState}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Reg. Date :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_RegisterDate}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Reg. up to:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Registeredupto}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Classification</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
                                             <tr class="row">
												<th class="key">OdoMeter:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Odometer}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Meter Unit :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_MeterUnit}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Fuel Unit:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_FuelUnit}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Vehicle Fuel :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Fuel}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Fuel Tank 1 :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_FuelTank1}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Oil Capacity:</th>
												<td class="value"><c:out value="${vehicle.vehicle_Oil}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Expected Mileage:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_ExpectedMileage}" /></td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>

						</div>
						<!-- /.col -->
						<div class="col-sm-5 col-xs-5 invoice-col">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Specification</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th class="key">Color :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Color}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Class :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Class}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Body :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_body}" /></td>
											</tr>
											<tr class="row">
												<th class="key">A/C Type :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_actype}" /></td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Capacity</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th class="key">Cylinders :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Cylinders}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Cubic Capacity:</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_CubicCapacity}" /></td>
											</tr>
											<tr class="row">
												<th class="key">Power :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Power}" /></td>
											</tr>
											<tr class="row">
												<th class="key">WheelBase :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_wheelBase}" /></td>
											</tr>

											<tr class="row">
												<th class="key">SeatCapacity :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_SeatCapacity}" /></td>
											</tr>
											<tr class="row">
												<th class="key">UnladenWeight :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_UnladenWeight}" /></td>
											</tr>
											<tr class="row">
												<th class="key">LadenWeight :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_LadenWeight}" /></td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Owner Details</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th class="key">Ownership :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Ownership}" /></td>
											</tr>
											<%-- <tr class="row">
												<th class="key">Owner Serial :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_OwnerSerial}" /></td>
											</tr> --%>
											<%-- <tr class="row">
												<th class="key">Vehicle Owner :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Ownership}" /></td>
											</tr> --%>
											<tr class="row">
												<th class="key">Location :</th>
												<td class="value"><c:out
														value="${vehicle.vehicle_Location}" /></td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col -->
				</div>
			</div>
		</section>
	</sec:authorize>
</div>
<!-- ./wrapper -->


