<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<!-- Main content -->
	<section class="invoice">
		<!-- title row -->
		<div class="row">
			<div class="col-xs-12">
				<h2 class="page-header">
				<c:choose>
							<c:when test="${company.company_id != null}">
								 <img
								src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
								class="img-rounded" alt="Company Logo" width="160" height="40" />
								
							</c:when>
							<c:otherwise>
								<i class="fa fa-globe"></i>
							</c:otherwise>
						</c:choose>
					 <c:out value="${company.company_name}" />
					  <small class="pull-right" >
					  Print By: ${company.user_email}     I.</small>
					  <small>Branch :<c:out value=" ${company.branch_name}  , " />
							Department :<c:out value=" ${company.department_name}" /></small>
				</h2>
			</div>
			<!-- /.col -->
		</div>
		<!-- Table row -->
		<div class="row">
			<div class="col-xs-12 table-responsive">
				<!-- Print User Profile -->
				<div class="col-sm-3 col-xs-4 invoice-col">
					<div class="col-md-9 col-sm-10 col-xs-12">
						<c:choose>
							<c:when test="${userprofile.photo_id != null}">
								 <img
									src="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
									class="img-circle"
									alt="<c:out value="${userprofile.firstName} " />" width="200"
									height="200" align="left" />
								
							</c:when>
							<c:otherwise>
								<img src="resources/images/User-Icon.png" alt="User Profile"
									class="img-circle img-responsive" width="200" height="200"
									align="left" />
							</c:otherwise>
						</c:choose>


					</div>
					<div class="col-md-9 col-sm-9 col-xs-12">

						<h3>
							<c:out value="${userprofile.firstName} " />
							<c:out value="${userprofile.lastName}" />
						</h3>
						<small><cite
							title="${userprofile.city},  ${userprofile.state}, ${userprofile.country}">${userprofile.city}
								${userprofile.state}, ${userprofile.country} <i
								class="fa fa-map-marker"> </i>
						</cite></small>
						<p>
							<i class="fa fa-envelope"></i>
							<c:out value=" ${userprofile.user_email}"></c:out>
							<br /> <i class="fa fa-globe"></i><a> <c:out
									value=" ${userprofile.personal_email}"></c:out></a> <br /> <i
								class="fa fa-gift"></i>
							<c:out value=" ${userprofile.dateofbirth}"></c:out>
						</p>
					</div>
					<div class="col-md-9 col-sm-9 col-xs-12">
						<fieldset>
							<legend>Company </legend>
							<h5>
								Company :<a><c:out value=" ${userprofile.company_name}" /></a>
							</h5>
							<h5>
								Branch :<a><c:out value=" ${userprofile.branch_name}" /></a>
							</h5>
							<h5>
								Depart. :
								<c:out value=" ${userprofile.department_name}" />
							</h5>
							<h5>
								Design. :
								<c:out value="${userprofile.designation}" />
							</h5>

						</fieldset>
					</div>
				</div>
				<div class="col-sm-6 col-xs-6 invoice-col">
					<fieldset>
						<legend>Personal Details </legend>
						<table class="table">
							<tbody>
								<tr class="row">
									<th class="">Name :</th>
									<td class="" style="width: 2432452px;"><c:out
											value="${userprofile.firstName} " /> <c:out
											value="${userprofile.lastName}" /></td>
								</tr>
								<tr class="row">
									<th class="">Date of Birth :</th>
									<td class=""><c:out value="${userprofile.dateofbirth}" />
										<div class="text-muted">
											<!-- <small>3 years old</small> -->
										</div></td>
								</tr>
								<tr class="row">
									<th class="">Sex :</th>
									<td class="value"><c:out value="${userprofile.sex}" /></td>
								</tr>
								<tr class="row">
									<th class="">Personal Email :</th>
									<td class="value"><c:out
											value="${userprofile.personal_email}" /></td>
								</tr>
								<tr class="row">
									<th class="">Mobile Number :</th>
									<td class="value"><c:out
											value="${userprofile.home_number}" /><br> <c:out
											value="${userprofile.mobile_number}" /><br> <c:out
											value="${userprofile.work_number}" /></td>
								</tr>
								<tr class="row">
									<th class="">Address :</th>
									<td class="value"><address>
											<c:out value="${userprofile.address_line1}" />
											,<br>
											<c:out value="${userprofile.city} " />
											,
											<c:out value="${userprofile.state}" />
											,<br>
											<c:out value="${userprofile.country}" />
											, Pin :
											<c:out value="${userprofile.pincode}" />
											.
										</address></td>
								</tr>
								<tr class="row"></tr>
								<tr class="row">
									<th class="">Emergency Person :</th>
									<td class="value"><c:out
											value="${userprofile.emergency_person}" /></td>
								</tr>
								<tr class="row">
									<th class="">Emergency Number:</th>
									<td class="value"><c:out
											value="${userprofile.emergency_number}" /></td>
								</tr>
						</table>
					</fieldset>
					<fieldset style="margin: 0px 0;">
						<legend>Company Details </legend>
						<table class="table">
							<tbody>
								<tr class="row">
									<th class="">Company Email :</th>
									<td class=""><c:out value="${userprofile.user_email} " /></td>
								</tr>
								<tr class="row">
									<th class="">Employee ID :</th>
									<td class=""><c:out value="${userprofile.employes_id}" />
										<div class="text-muted">
											<!-- <small>3 years old</small> -->
										</div></td>
								</tr>
								<tr class="row">
									<th class="">Working Time :</th>
									<td class="value"><c:out
											value="${userprofile.working_time_from} To " /> <c:out
											value="${userprofile.working_time_to}" /></td>
								</tr>
								<tr class="row">
									<th class="">ESI Number :</th>
									<td class="value"><c:out value="${userprofile.esi_number}" /></td>
								</tr>
								<tr class="row">
									<th class="">PF Number :</th>
									<td class="value"><c:out value="${userprofile.pf_number}" /></td>
								</tr>
								<tr class="row">
									<th class="">Insurance Number :</th>
									<td class="value"><c:out
											value="${userprofile.insurance_number}" /></td>
								</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</div>
		<!-- /.row -->
		<!-- /.row -->
		<div class="row">
			<!-- accepted payments column -->
			<div class="col-xs-12" align="center">
				<p class="lead"></p>
			</div>
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->
</div>