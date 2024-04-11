<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('PRINT_DRIVER')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('PRINT_DRIVER')">
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
			<!-- Table row -->
			<div class="row invoice-info">
				<div class="col-xs-12 table-responsive">
					<!-- Print Driver Profile -->
					<div class="col-sm-3 col-xs-4 invoice-col">
						<div class="col-md-9 col-sm-9 col-xs-12">
							<c:choose>
								<c:when test="${driver.driver_photoid != null}">
									<img
										src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
										class="img-circle"
										alt="<c:out value="${driver.driver_firstname} " />"
										width="150" height="150" align="left" />
								</c:when>
								<c:otherwise>
									<img src="resources/images/User-Icon.png" alt="Driver Profile"
										class="img-circle img-responsive" width="200" height="200"
										align="left" />
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">

							<h3>
								<c:out value="${driver.driver_firstname}" />
								<c:out value="${driver.driver_Lastname}" />
							</h3>
							<small><cite
								title="${driver.driver_city},  ${driver.driver_state}, ${driver.driver_country}">${driver.driver_city}
									${driver.driver_state}, ${driver.driver_country} <i
									class="fa fa-map-marker"> </i>
							</cite></small>
							<p>
								<i class="fa fa-envelope"></i>
								<c:out value="${driver.driver_email}" />
								<br /> <i class="fa fa-globe"></i>
								<c:out value="${driver.driver_mobnumber}" />
								<br>
								<c:out value="${driver.driver_homenumber}" />
								<br /> <i class="fa fa-gift"></i>
								<c:out value="${driver.driver_dateofbirth}" />
							</p>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<fieldset>
								<legend>DL Info </legend>
								<h5>
									Driver? :>
									<c:out value="${driver.driver_active}" />
								</h5>
								<h5>
									License Class :<a><c:out value="${driver.driver_dlclass}" /></a>
								</h5>
								<h5>
									License state :
									<c:out value="${driver.driver_dlprovince}" />
								</h5>
								<h5>
									DL NO. :
									<c:out value="${driver.driver_dlnumber}" />
								</h5>
								<h5>
									Badge NO. :
									<c:out value="${driver.driver_badgenumber}" />
								</h5>

							</fieldset>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<fieldset>
								<legend>Training / Specialization </legend>
								<h5>
									<c:out value="${driver.driver_trainings}" />

								</h5>
							</fieldset>
						</div>
					</div>
					<div class="col-sm-6 col-xs-6 invoice-col">
						<fieldset style="margin: 0px 0;">
							<table class="table table-bordered table-striped">
								<caption>Driver Personal Details</caption>
								<tbody>

									<tr class="row">
										<th class="">Father Name:</th>
										<td class=""><c:out value="${driver.driver_fathername}" /></td>
									</tr>
									<tr class="row">
										<th class="">Qualification:</th>
										<td class=""><c:out
												value="${driver.driver_Qualification}" /></td>
									</tr>
									<tr class="row">
										<th class="">Blood Group:</th>
										<td class=""><c:out value="${driver.driver_bloodgroup}" /></td>
									</tr>
									<tr class="row">
										<th class="">Languages :</th>
										<td class=""><c:out value="${driver.driver_languages}" /></td>
									</tr>
									<c:if test="${configuration.showDriverEmail}">
										<tr class="row">
											<th class="">Email :</th>
											<td class=""><c:out value="${driver.driver_email}" /></td>
										</tr>
									</c:if>
									<tr class="row">
										<th class="">Phone :</th>
										<td class=""><c:out
												value="${driver.driver_mobnumber}  , " /> <c:out
												value="${driver.driver_homenumber}" /></td>
									</tr>
									<tr class="row">
										<th class="">Address :</th>
										<td class="">
											<address>
												<c:out value="${driver.driver_address}" />
												,<br>
												<c:out value="${driver.driver_address2}" />
												<br>
												<c:out value="${driver.driver_city}" />
												,
												<c:out value="${driver.driver_state}" />
												<br>
												<c:out value="${driver.driver_country}" />
												-Pin :
												<c:out value="${driver.driver_pincode}" />
											</address>
										</td>
									</tr>
									<tr class="row">
										<th class="">Reference:</th>
										<td class=""><c:out value="${driver.driver_reffristname}" />
											<c:out value="${driver.driver_reflastname}" /></td>
									</tr>
									<tr class="row">
										<th class="">Content No:</th>
										<td class=""><c:out value="${driver.driver_refcontect}" /></td>
									</tr>
									<tr class="row">
										<th class="">Aadhar Number :</th>
										<td class=""><c:out value="${driver.driver_aadharnumber}" /></td>
									</tr>
									<tr class="row">
										<th class="">Bank Name :</th>
										<td class=""><c:out value="${driver.driver_bankname}" /></td>
									</tr>
									<tr class="row">
										<th class="">Bank A/C Number :</th>
										<td class=""><c:out value="${driver.driver_banknumber}" /></td>
									</tr>
									<tr class="row">
										<th class="">Bank IFSC Number :</th>
										<td class=""><c:out value="${driver.driver_bankifsc}" /></td>
									</tr>
									<tr class="row">
										<th class="">PAN Number :</th>
										<td class=""><c:out value="${driver.driver_pannumber}" /></td>
									</tr>

									<tr class="row">
										<th class="">Employee No:</th>
										<td class=""><c:out value="${driver.driver_empnumber}" /></td>
									</tr>
									<tr class="row">
										<th class="">Job Title :</th>
										<td class=""><c:out value="${driver.driver_jobtitle}" /></td>
									</tr>
									<tr class="row">
										<th class="">Ins No:</th>
										<td class=""><c:out value="${driver.driver_insuranceno}" /></td>
									</tr>
									<c:if test="${configuration.showESINumber}">
									<tr class="row">
										<th class="">ESI No :</th>
										<td class=""><c:out value="${driver.driver_esino}" /></td>
									</tr>
									</c:if>
									<c:if test="${configuration.showPFNumber}">
									<tr class="row">
										<th class="">PF No :</th>
										<td class=""><c:out value="${driver.driver_pfno}" /></td>
									</tr>
									</c:if>
									<tr class="row">
										<th class="">Start Date :</th>
										<td class=""><c:out value="${driver.driver_startdate}" />
										</td>
									</tr>
									<tr class="row">
										<th class="">Leave Date :</th>
										<td class=""><c:out value="${driver.driver_leavedate}" /></td>
									</tr>
								</tbody>
							</table>
						</fieldset>
					</div>
				</div>
			</div>

			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12">
					<fieldset style="margin: 0px 0;">
						<table class="table table-bordered table-striped">
							<caption>Driver Renewal Reminder</caption>
							<tbody>
								<c:if test="${!empty driverReminder}">
									<c:forEach items="${driverReminder}" var="driverReminder">
										<tr class="row">
											<th><c:out
													value="${driverReminder.driver_remindertype}  - " /> <c:out
													value="${driverReminder.driver_dlnumber}" /></th>


											<td class="icon"><c:out
													value="${driverReminder.driver_dlto}" /><font
												color="#FF6666"> ( <c:out
														value="${driverReminder.driver_dueDifference}" /> )
											</font>
												<ul class="list-inline no-margin">
													<li><font color="#999999"> Due soon on <c:out
																value="${driverReminder.driver_renewaldate}" />
													</font></li>

												</ul></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</fieldset>
				</div>
			</div>
			<!-- /.row -->
		</section>
	</sec:authorize>
</div>
<!-- ./wrapper -->


