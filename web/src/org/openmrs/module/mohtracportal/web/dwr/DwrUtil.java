package org.openmrs.module.mohtracportal.web.dwr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.web.dwr.PatientListItem;

public class DwrUtil {

	private final Log log = LogFactory.getLog(this.getClass());

	public PatientListItem findPatient(String searchStr) {
		PatientListItem ret = new PatientListItem();
		ret.setGivenName("test");
		ret.setFamilyName("example");

		return ret;
	}

	public String getPatientList(String searchString) {
		PatientListItem ret = new PatientListItem();
		ret.setGivenName("test");
		ret.setFamilyName("example");

		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < 5; i++)
			sb.append("<a href='xxx.htm'>" + ret.getGivenName().toUpperCase()
					+ " " + ret.getFamilyName() + "_" + i + "</a>");

		return sb.toString();
	}

}
