<%@page import="org.openmrs.api.context.Context"%>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<%
	if(Context.getAuthenticatedUser()==null)
		response.sendRedirect(request.getContextPath()+"/login.htm");
%>

<ul id="menu">

	<li class="first">
		<a href="tracportal.htm"><spring:message code="@MODULE_ID@.activities" /></a>
	</li>

	<openmrs:hasPrivilege privilege="Manage Locations">
		<li>
			<a href="${pageContext.request.contextPath}/admin/locations/location.list"> Manage Locations </a>
		</li>
	</openmrs:hasPrivilege>

	<openmrs:hasPrivilege privilege="Manage Sponsors">
		<li
			<c:if test='<%= request.getRequestURI().contains("sponsorForm") || request.getRequestURI().contains("sponsorsList") %>'>class="active"</c:if>>
			<a href="sponsors.list?page=1"> <spring:message code="@MODULE_ID@.sponsors.manage"/> </a>
		</li>
	</openmrs:hasPrivilege>
	
	<openmrs:hasPrivilege privilege="Manage Locations/Sponsors">
		<li
			<c:if test='<%= request.getRequestURI().contains("sponsorLocation") %>'>class="active"</c:if>>
			<a href="locationsponsor.list?page=1"> <spring:message code="@MODULE_ID@.locationsponsors.manage"/> </a>
		</li>
	</openmrs:hasPrivilege>
	
	<openmrs:hasPrivilege privilege="View lab results">
		<li
			<c:if test='<%= request.getRequestURI().contains("sampleCodeView") %>'>class="active"</c:if>>
			<a href="samplecodemanagement.list?status=all&page=1"> <spring:message code="@MODULE_ID@.test.sample.manage"/> </a>
		</li>
	</openmrs:hasPrivilege>
	
	<openmrs:hasPrivilege privilege="View TRAC Portal patients listing and export">
		<li
			<c:if test='<%= request.getRequestURI().contains("patientList") %>'>class="active"</c:if>>
			<a href="patientList.htm?page=1"> <spring:message code="@MODULE_ID@.patientlist.title"/> </a>
		</li>
	</openmrs:hasPrivilege>
	
	<li
		<c:if test='<%= request.getRequestURI().contains("userPerformance") %>'>class="active"</c:if>>
		<a href="performance.htm"> <spring:message code="@MODULE_ID@.user.performance"/> </a>
	</li>
	
</ul>
