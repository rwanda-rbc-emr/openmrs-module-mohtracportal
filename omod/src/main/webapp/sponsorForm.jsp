<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>
<%@ taglib prefix="springform" uri="/WEB-INF/taglibs/spring-form.tld" %>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery-1.3.2.js" />

<openmrs:require privilege="Manage Sponsors" otherwise="/login.htm" redirect="/module/@MODULE_ID@/sponsor.form" />

<h2>Create/Edit Sponsor</h2>
<br/>

<b class="boxHeader">Create/Edit Sponsor</b>
<div class="box">
	<springform:form commandName="sponsor" method="post">
		<table>
			<tr>
				<td>Name</td>
				<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
				<td><spring:bind path="name"><input type="text" size="35" name="${status.expression}" value="${status.value}"/></spring:bind></td>
				<td><form:errors cssClass="error" path="name" /></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help"/>"/></td>
				<td><spring:bind path="description"><textarea cols="40" rows="3" name="${status.expression}">${status.value}</textarea></spring:bind></td>
				<td><form:errors cssClass="error" path="description" /></td>
			</tr>
			<c:if test="${param.sponsorId!=null}">
				<tr>
					<td>Created by</td>
					<td></td>
					<td><spring:bind path="creator">${status.value.personName}</spring:bind>&nbsp;-&nbsp;<spring:bind path="dateCreated">${status.value}</spring:bind></td>
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
</div>

	<script>
		$(document).ready(function(){
			$("#btSave").click(function(){
				if(confirm("Are you sure you want to save ?"))
					this.form.submit();
			});
							
		});
	</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>