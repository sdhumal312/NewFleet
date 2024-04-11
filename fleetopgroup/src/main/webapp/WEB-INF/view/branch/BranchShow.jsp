<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/newBranch.in"/>">Branch Details</a> / <a
							href="<c:url value="/showBranch.in?branch_id=${branch.branch_id}"/>">${branch.branch_name}</a>
						/ <span id="NewVehicle">Show Branch</span>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('ADD_BRANCH')">
							<a class="btn btn-success"
								href="editBranch?branch_id=${branch.branch_id}"> <i
								class="fa fa-pencil"></i> Edit Branch
							</a>
						</sec:authorize>
						<a class="btn btn-info" href="<c:url value="/newBranch.in"/>">
							<span id="AddVehicle"> Cancel</span>
						</a>
					</div>
				</div>
				<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_BRANCH')">

					<div class="pull-left">
						<h3 class="secondary-header-title">
							<a href="showBranch.in?branch_id=${branch.branch_id}"> <c:out
									value="${branch.branch_name}" /> - <c:out
									value="${branch.branch_code}" /></a>
						</h3>
						
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="email"> </span> <span
									class="text-muted"><c:out value="${branch.branch_email}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_phonenumber}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_mobilenumber}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Master Parts Updated Successfully.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_BRANCH')">
			<div class="row">
				<div class="col-md-9">
					<div class="main-body">
						<ul class="tabs">
							<li class=" current" data-tab="tab-1">Basic Details</li>
							<li class="tab-link" data-tab="tab-2">Ownership Details</li>
						</ul>
						<div id="tab-1" class="tab-content2 current">
							<div class="row">
								<div class="col-md-6">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">BASIC DETAILS</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Branch Name :</th>
														<td class="value" style="width: 2432452px;"><c:out
																value="${branch.branch_name}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Code :</th>
														<td class="value"><c:out
																value="${branch.branch_code}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Address :</th>
														<td class="value"><c:out
																value="${branch.branch_address}" /><br> <c:out
																value="${branch.branch_city}, " /> <c:out
																value="${branch.branch_state}" /><br> <c:out
																value="${branch.branch_country}, " /> <c:out
																value="Pin :${branch.branch_pincode}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Landmark :</th>
														<td class="value"><c:out
																value="${branch.branch_landmark}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Email :</th>
														<td class="value"><c:out
																value="${branch.branch_email}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Phone Number :</th>
														<td class="value"><c:out
																value="${branch.branch_phonenumber}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Mobile Number :</th>
														<td class="value"><c:out
																value="${branch.branch_mobilenumber}" /></td>
													</tr>
											</table>
										</div>
									</div>
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">InCharage</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Branch InCharge :</th>
														<td class="value"><c:out
																value="${branch.branch_incharge}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch InCharge Phone:</th>
														<td class="value"><c:out
																value="${branch.branch_incharge_phone}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-5">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Branch Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Branch Time :</th>
														<td class="value"><c:out
																value="${branch.branch_time_from} To " /> <c:out
																value="${branch.branch_time_to}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Annual Increment % :</th>
														<td class="value"><c:out
																value="${branch.annual_increment}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Advance PAID :</th>
														<td class="value"><c:out
																value="${branch.advance_paid}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Lease Amount PAID :</th>
														<td class="value"><c:out
																value="${branch.lease_amount}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Monthly RENT :</th>
														<td class="value"><c:out
																value="${branch.monthly_rent}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Monthly RENT Date:</th>
														<td class="value"><c:out
																value="${branch.monthly_rent_date}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Service Tax Number :</th>
														<td class="value"><c:out
																value="${branch.branch_serviceTax_no}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Branch Electricity Number :</th>
														<td class="value"><c:out
																value="${branch.branch_electricity_no}" /></td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="tab-2" class="tab-content2">
							<div class="row">
								<div class="col-md-6">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Owner 1</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Owner Name :</th>
														<td class="value"><c:out
																value="${branch.owner1_name}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Owner PAN Number :</th>
														<td class="value"><c:out value="${branch.owner1_pan}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Owner Address :</th>
														<td class="value"><c:out
																value="${branch.owner1_address}" /></td>
													</tr>

													<tr class="row">
														<th class="key">Owner Mobile NO :</th>
														<td class="value"><c:out
																value="${branch.owner1_mobile}" /></td>
													</tr>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-5">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Owner 2</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Owner Two Name :</th>
														<td class="value"><c:out
																value="${branch.owner2_name}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Owner Two PAN Number :</th>
														<td class="value"><c:out value="${branch.owner2_pan}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Owner Two Address :</th>
														<td class="value"><c:out
																value="${branch.owner2_address}" /></td>
													</tr>

													<tr class="row">
														<th class="key">Owner Two Mobile NO :</th>
														<td class="value"><c:out
																value="${branch.owner2_mobile}" /></td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2">
					<ul class="nav nav-list">
						<li class="active"><a
							href="showBranch.in?branch_id=${branch.branch_id}">Overview</a></li>
						<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
								<a href="ShowbranchDocument?branch_id=${branch.branch_id}">Document
									<span
									class="count muted text-muted pull-right label label-info">${branchDocument}</span>
								</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
								<a href="ShowBranchPhoto?branch_id=${branch.branch_id}">Photos
									<span
									class="count muted text-muted pull-right label label-info">${branchPhoto}</span>
								</a>
							</sec:authorize></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${branch.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${branch.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${branch.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${branch.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>