/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.mohtracportal.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;

/**
 * @author Administrator
 */
public class MohTracPortalTag {

	private static Log log = LogFactory.getLog(MohTracPortalTag.class);

	/**
	 * @param personId
	 * @return
	 */
	public static String getCurrentLocation() {
		try {
			if (MohTracConfigurationUtil.isConfigured()) {
				Location loc = null;
				loc = Context.getLocationService().getLocation(
						MohTracConfigurationUtil.getDefaultLocationId());
				return loc.getName().toUpperCase();

			} else
				return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @param personId
	 * @return
	 */
	public static String getPersonNames(Integer personId) {
		Person person = Context.getPersonService().getPerson(personId);
		if (person == null)
			return "-";
		PersonName personName = person.getPersonName();
		if (personName == null)
			return "-";
		String names = (personName.getGivenName().trim() + " " + personName
				.getMiddleName()).trim()
				+ " " + personName.getFamilyName().trim();

		return names;
	}

	/**
	 * @param personId
	 * @return
	 */
	public static String getNumberOfEncounterByPatient(Integer personId) {
		List<Encounter> ptEncList = Context.getEncounterService()
				.getEncountersByPatientId(personId);
		return (ptEncList != null) ? "" + ptEncList.size() : "0";
	}

	/**
	 * @param patientId
	 * @param identifierTypeId
	 * @return
	 */
	public static String personIdentifierByPatientIdAndIdentifierTypeId(
			Integer patientId, Integer identifierTypeId) {
		PatientIdentifier pi = null;
		Patient p = null;
		p = Context.getPatientService().getPatient(patientId);
		if (p != null)
			pi = p.getPatientIdentifier(identifierTypeId);
		return (pi != null) ? pi.toString() : "-";
	}

	/**
	 * @param conceptId
	 * @return
	 */
	public static String getAttributeTypeNameByIdAsString(String conceptId) {
		// log.info("=====================>>>.>>>.>> " + conceptId);
		PersonAttributeType pa = null;
		try {
			if (conceptId == null || conceptId.trim().compareTo("") == 0)
				return "-";
			pa = Context.getPersonService().getPersonAttributeType(
					Integer.parseInt(conceptId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (pa != null) ? pa.getName() : "-";
	}

	/**
	 * @param conceptId
	 * @return
	 */
	public static String getIdentifierTypeNameByIdAsString(
			String identifierTypeId) {
		// log.info("=====================>>>.>>>.>> " + conceptId);
		PatientIdentifierType pi = null;
		try {
			if (identifierTypeId == null
					|| identifierTypeId.trim().compareTo("") == 0)
				return "-";
			pi = Context.getPatientService().getPatientIdentifierType(
					Integer.parseInt(identifierTypeId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (pi != null) ? pi.getName() : "-";
	}

	/**
	 * @param gp
	 * @return
	 */
	public static String globalPropertyParser(String gp) {
		String res = "-";
		try {
			res = gp.substring(gp.lastIndexOf(".") + 1);
		} catch (Exception e) {
			// log.error(">>>>>>>>>>>>>>>>>>>VCT>>Module>>Tag>>>> An error occured : "+e.getMessage());
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * @param userId
	 * @param objectId
	 * @return
	 */
	public static String dateFirstObjectCreatedByUser(Integer userId,
			Integer objectId) {
		String res = "-";
		try {
			MohTracPortalService ps = Context
					.getService(MohTracPortalService.class);
			res = ps.getDateOfFirstRecordByObjectAndByUser(userId, objectId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * @param userId
	 * @param objectId
	 * @return
	 */
	public static String dateLastObjectCreatedByUser(Integer userId,
			Integer objectId) {
		String res = "-";
		try {
			MohTracPortalService ps = Context
					.getService(MohTracPortalService.class);
			res = ps.getDateOfLastRecordByObjectAndByUser(userId, objectId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * @param userId
	 * @param objectId
	 * @return
	 */
	public static String numberOfObjectCreatedByUser(Integer userId,
			Integer objectId, Integer period) {
		String res = "-";
		try {
			MohTracPortalService ps = Context
					.getService(MohTracPortalService.class);
			res = ps.getNumberOfRecordCreatedByObjectAndByUser(userId,
					objectId, period);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

}
