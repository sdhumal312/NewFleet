<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <span>Search
						Vendor</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VENDOR')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Vendor Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="VendorReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Vendor Name :</label>
													<div class="I-size">
														<select class="form-text select2" name="vendorId"
															id="select3" style="width: 100%;">
															<option value=""><!-- please select --></option>

															<c:forEach items="${vendorlist}" var="vendorlist">
																<option value="${vendorlist.vendorId}"><c:out
																		value="${vendorlist.vendorName}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="F"> Vendor
														Type :</label>
													<div class="I-size">
														<select class="form-text select2" id="vendorType"
															name="vendorTypeId" style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${vendorType}" var="vendorType">
																<option value="${vendorType.vendor_Typeid}">
																	<c:out value="${vendorType.vendor_TypeName}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label"> Vendor
														Location :</label>
													<div class="I-size">
														<input class="form-text" id="vendorLocation"
															name="vendorLocation" type="text">
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">
															<a href="<c:url value="/vendorHome.in"/>" class="btn btn-info"> <span
																id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
</div>