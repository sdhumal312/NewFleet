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

					<fieldset style="margin: 0px 0;">
						<table class="table table-bordered table-striped">
							<caption>Driver KYC Details</caption>
							<tbody>
								<tr class="row">
									<th class="">Driver Photo:</th>
									<td class="">
										<div class="col-md-6 col-sm-6 col-xs-6">
											<c:choose>
												<c:when test="${driver.driver_photoid != null}">
													<img
														src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
														class="img-circle"
														alt="<c:out value="${driver.driver_firstname} " />"
														width="100" height="100" align="left" />

												</c:when>
												<c:otherwise>
													<img src="resources/images/User-Icon.png"
														alt="Driver Profile" class="img-circle img-responsive"
														width="200" height="200" align="left" />
												</c:otherwise>
											</c:choose>


										</div>
									</td>
								</tr>
								<tr class="row">
									<th class="">Driver Name:</th>
									<td class=""><c:out value="${driver.driver_firstname}" />
										<c:out value="${driver.driver_Lastname}" /></td>
								</tr>
								<tr class="row">
									<th class="">Employee No:</th>
									<td class=""><c:out value="${driver.driver_empnumber}" /></td>
								</tr>
								<tr class="row">
									<th class="">Father Name:</th>
									<td class=""><c:out value="${driver.driver_fathername}" /></td>
								</tr>
								<tr class="row">
									<th class="">DOB:</th>
									<td class=""><c:out value="${driver.driver_dateofbirth}" /></td>
								</tr>
								<tr class="row">
									<th class="">DESIGNATION:</th>
									<td class=""><c:out value="${driver.driver_jobtitle}" /></td>
								</tr>
								<tr class="row">
									<th class="">Qualification:</th>
									<td class=""><c:out value="${driver.driver_Qualification}" /></td>
								</tr>
								<tr class="row">
									<th class="">PAN NO :</th>
									<td class=""><c:out value="${driver.driver_pannumber}" /></td>
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
									<th class="">Aadhar Number :</th>
									<td class=""><c:out value="${driver.driver_aadharnumber}" /></td>
								</tr>
								<tr class="row">
									<th class="">Languages :</th>
									<td class=""><c:out value="${driver.driver_languages}" /></td>
								</tr>
								<tr class="row">
									<th class="">Email :</th>
									<td class=""><c:out value="${driver.driver_email}" /></td>
								</tr>
								<tr class="row">
									<th class="">Phone :</th>
									<td class=""><c:out value="${driver.driver_mobnumber}  , " />
										<c:out value="${driver.driver_homenumber}" /></td>
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
									<th class="" colspan="2">If at all if you have any
										addition or Deletion in your family mention below for<br>
										R-Care purpose. (Reliance general health insurance)
									</th>

								</tr>
							</tbody>
						</table>
					</fieldset>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12">
					<fieldset style="margin: 0px 0;">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>SL.No</th>
									<th>NAME</th>
									<th>RELATION SHIP</th>
									<th>GENDER</th>
									<th>AGE</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty DFamily}">
									<%
										Integer hitsCount = 1;
									%>
									<c:forEach items="${DFamily}" var="DFamily">
										<tr>
											<td class="fit">
												<%
													out.println(hitsCount);
																hitsCount += 1;
												%>
											</td>
											<td class="icon"><c:out value="${DFamily.DF_NAME}" /></td>
											<td class="icon"><c:out
													value="${DFamily.DF_RELATIONSHIP}" /></td>
											<td class="icon"><i></i> <c:out
													value="${DFamily.DF_SEX}" /></td>
											<td class="icon"><c:out value="${DFamily.DF_AGE}" /></td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="5"></td>
									</tr>
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


