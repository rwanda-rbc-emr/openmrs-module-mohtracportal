/**
 * 
 */
package org.openmrs.module.mohtracportal.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracConfigurationUtil {

	/**
	 * constructor
	 */
	private MohTracConfigurationUtil() {
	}

	/**
	 * @return Returns the number of record per page (25 is the default but can
	 *         be changed)
	 * @throws Exception
	 */
	public static Integer getNumberOfRecordPerPage() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.recordperpage");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.valueOf(gp.getPropertyValue())
				: 25;
	}

	/**
	 * @return Returns the default Location, the default is Unknown Location
	 * @throws Exception
	 */
	public static Integer getDefaultLocationId() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.defaultLocationId");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.valueOf(gp.getPropertyValue())
				: 1;
	}

	/**
	 * @return Returns true or false.<br/>
	 *         If the module is configured it returns true, otherwise it returns
	 *         false.
	 */
	public static boolean isConfigured() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.configured");
		return (gp != null) ? ((gp.getPropertyValue().compareToIgnoreCase(
				"true") == 0) ? true : false) : true;
	}
	
	/**
	 * This will allow help message to be displayed, if set to true.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean displayHelpMessage() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.helpmessage.display");
		return (gp != null) ? ((gp.getPropertyValue().compareToIgnoreCase(
				"true") == 0) ? true : false) : true;
	}

	/**
	 * @return Returns the HIV Program Id if it has been configured, otherwise,
	 *         it will return -1.
	 */
	public static Integer getHivProgramId() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.program.hiv");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.valueOf(gp.getPropertyValue())
				: -1;
	}

	/**
	 * @return Returns the PMTCT Program Id if it has been configured,
	 *         otherwise, it will return -1.
	 */
	public static Integer getPmtctProgramId() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.program.pmtct");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.valueOf(gp.getPropertyValue())
				: -1;
	}

	/**
	 * @return Returns the ID of the Civil Status Attribute Type
	 */
	public static int getCivilStatusAttributeTypeId() {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject(
						"mohtracportal.attributeType.civilStatus");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return Returns the ID of the Education Level Attribute Type
	 */
	public static int getEducationLevelAttributeTypeId() {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject(
						"mohtracportal.attributeType.educationLevel");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return Returns the ID of the Main Activity Attribute Type
	 */
	public static int getMainActivityAttributeTypeId() {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject(
						"mohtracportal.attributeType.mainActivity");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return Returns the list of global properties starting with
	 *         <i>mohtracportal.attributeType</i>
	 */
	public static List<GlobalProperty> getNeededAttributeType() {
		List<GlobalProperty> gpList = null;
		gpList = Context.getAdministrationService()
				.getGlobalPropertiesByPrefix("mohtracportal.attributeType");

		return gpList;
	}

	/**
	 * @return Returns the list of global properties starting with
	 *         <i>mohtracportal.identifierType</i>
	 */
	public static List<GlobalProperty> getNeededIdentifierType() {
		List<GlobalProperty> gpList = null;
		gpList = Context.getAdministrationService()
				.getGlobalPropertiesByPrefix("mohtracportal.identifierType");

		return gpList;
	}

	/**
	 * @return The NID identifier type id, in case it has not been configured,
	 *         it will return null.
	 */
	public static int getNIDIdentifierTypeId() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.identifierType.nid");
		return (gp != null && gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return The TRACNET identifier type id, in case it has not been
	 *         configured, it will return null.
	 */
	public static Integer getTracNetIdentifierTypeId() throws Exception {
		GlobalProperty gp = Context
				.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.identifierType.tracnet");
		return (gp != null && StringUtils.isNotBlank(gp.getPropertyValue()) && gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return The Local Health Center identifier type id, in case it has not
	 *         been configured, it will return null.
	 */
	public static Integer getLocalHealthCenterIdentifierTypeId() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject(
						"mohtracportal.identifierType.hc_localid");
		return (gp != null && StringUtils.isNotBlank(gp.getPropertyValue()) && gp.getPropertyValue().trim().compareTo("") != 0) ? Integer
				.parseInt(gp.getPropertyValue())
				: null;
	}

	/**
	 * @return Returns the numeric value indicating the critical level of CD4 count (350 is the default but can
	 *         be changed)
	 * @throws Exception
	 */
	public static Double getCriticalLevelOfCD4Count() throws Exception {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("mohtracportal.cd4count.criticallevel");
		return (gp != null & gp.getPropertyValue().trim().compareTo("") != 0) ? Double
				.valueOf(gp.getPropertyValue())
				: 350.0;
	}

}
