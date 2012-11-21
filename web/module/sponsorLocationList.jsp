<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<%@ include file="template/localHeader.jsp"%>

<h2>Sponsor/Location Management</h2>

<a href="locationsponsor.form">Assign Sponsor to a Location</a>
<br/><br/>

<c:if test="${param.locationId==null && param.sponsorId==null}">
	<%@ include file="listtemplate/allSponsorLocationList.jsp"%>
</c:if>
<c:if test="${param.locationId!=null}">
	<%@ include file="listtemplate/sponsorsListByLocation.jsp"%>
</c:if>
<c:if test="${param.sponsorId!=null}">
	<%@ include file="listtemplate/locationListBySponsor.jsp"%>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp" %>