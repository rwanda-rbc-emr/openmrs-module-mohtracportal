<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<%@ taglib prefix="springform" uri="/WEB-INF/taglibs/spring-form.tld" %>
<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>

<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<openmrs:require privilege="Manage Locations/Sponsors" otherwise="/login.htm" redirect="/module/@MODULE_ID@/locationsponsor.list?page=1" />

<h2>Assign a Sponsor to a Location</h2>
<br/>

<springform:form commandName="spLoc" method="post">

	<table>
		<tr>
			<td>Location</td>
			<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
			<td><spring:bind path="location"><openmrs_tag:locationField formFieldName="${status.expression}" initialValue="${status.value}" /></spring:bind></td>
			<td><form:errors cssClass="error" path="location" /></td>
		</tr>
		<tr>
			<td>Sponsor</td>
			<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
			<td><spring:bind path="sponsor">
			
					<select name="${status.expression}">
						<option value="0">--</option>
						<c:forEach items="${sponsors}" var="sponsor">
							<option value="${sponsor.key}" <c:if test="${sponsor.key==status.value}">selected='selected'</c:if>>${sponsor.value}</option>
						</c:forEach>
					</select>
				
				</spring:bind></td>
			<td><form:errors cssClass="error" path="sponsor" /></td>
		</tr>
		<tr>
			<td>Description</td>
			<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
			<td><spring:bind path="description"><textarea cols="40" rows="3" name="${status.expression}">${status.value}</textarea></spring:bind></td>
			<td><form:errors cssClass="error" path="description" /></td>
		</tr>
		<tr>
			<td>Starting from</td>
			<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
			<td><spring:bind path="sponseredFrom"><input name="${status.expression}" size="11" type="text" onclick="showCalendar(this)" value="${status.value}"/></spring:bind></td>
			<td><form:errors cssClass="error" path="sponseredFrom" /></td>
		</tr>
		<c:if test="${param.sponsorLocationId!=null}">
			<tr>
				<td>Created by</td>
				<td></td>
				<td><spring:bind path="createdBy">${status.value.personName}</spring:bind>&nbsp;-&nbsp;<spring:bind path="dateCreated">${status.value}</spring:bind></td>
				<td></td>
			</tr>		
		</c:if>
		<tr>
			<td></td>
			<td></td>
			<td><input type="button" value="Save" id="btSave"/></td>
			<td></td>
		</tr>
	</table>

</springform:form>

<script>
		jQuery(document).ready(function(){
			jQuery("#btSave").click(function(){
				if(confirm("Are you sure you want to save ?"))
					this.form.submit();
			});
							
		});
	</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>