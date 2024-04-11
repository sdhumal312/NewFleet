<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<ul class="nav nav-list">
	<li class="active"><a
		href="showMasterParts.in?partid=${MasterParts.partid}">Overview</a></li>
	<li><sec:authorize access="hasAuthority('ADD_PARTS')">
			<a href="ShowMasterPartsPhoto.in?partid=${MasterParts.partid}">Photos
				<span class="count muted text-muted pull-right">${photoCount}</span>
			</a>
		</sec:authorize></li>
		<c:if test="${config.showExtendedPartSave}">
			<sec:authorize access="hasAuthority('VIEW_PARTS')">
								<li>
									<a href="#" data-toggle="modal" data-target="#PurchaseVendorModal" data-whatever="@mdo">Purchase Vendor <span
										class="count muted text-muted pull-right">${purchaseVendors.size()}</span>
									</a>
								</li>
								<li>
									<a href="#" data-toggle="modal" data-target="#RepairingVendorModal" data-whatever="@mdo">Repairing Vendor <span
										class="count muted text-muted pull-right">${repairableVendors.size()}</span>
									</a>
								</li>
								
								<c:if test="${MasterParts.partTypeCategoryId == 2}">
									<li>
										<a href="#" data-toggle="modal" data-target="#ChildPartModal" data-whatever="@mdo">Child Parts <span
											class="count muted text-muted pull-right">${childParts.size()}</span>
										</a>
									</li>
								</c:if>
								<c:if test="${MasterParts.partTypeCategoryId == 3}">
									<li>
										<a href="#" data-toggle="modal" data-target="#ParentPartModal" data-whatever="@mdo">Parent Parts <span
											class="count muted text-muted pull-right">${parentParts.size()}</span>
										</a>
									</li>
								</c:if>
								
								
								<li>
									<a href="#" data-toggle="modal" data-target="#SubtitudePartModal" data-whatever="@mdo">Substitude Parts <span
										class="count muted text-muted pull-right">${substituDeParts.size()}</span>
									</a>
								</li>
								<li>
									<a href="#" data-toggle="modal" data-target="#RateHistoryModal" data-whatever="@mdo">Rate History <span
										class="count muted text-muted pull-right">${partRateHistory.size()}</span>
									</a>
								</li>
			</sec:authorize>
		</c:if>					
</ul>