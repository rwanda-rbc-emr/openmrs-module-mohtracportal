/**
 * 
 */
package org.openmrs.module.mohtracportal.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.Program;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ServiceContext;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;

/**
 * @author Administrator
 * 
 */
public class MohTracUtil {
	/**
	 * @return
	 */
	public static HashMap<Integer, String> createProgramOptions() {
		HashMap<Integer, String> programOptions = new HashMap<Integer, String>();
		List<Program> programs = Context.getProgramWorkflowService()
				.getAllPrograms();
		if (programs != null) {
			for (Program prg : programs) {
				programOptions.put(prg.getProgramId(), prg.getName());
			}
		}
		return programOptions;
	}

	/**
	 * Retrieve a hashmap of concept answers (concept id, bestname) given the id
	 * of the coded concept question
	 * 
	 * @param codedConceptQuestionId
	 * @return
	 */
	public static HashMap<Integer, String> createConceptCodedOptions(
			int codedConceptQuestionId) {
		HashMap<Integer, String> answersMap = new HashMap<Integer, String>();
		Concept questionConcept = Context.getConceptService().getConcept(
				codedConceptQuestionId);
		if (questionConcept != null) {
			for (ConceptAnswer ca : questionConcept.getAnswers()) {
				answersMap.put(ca.getAnswerConcept().getConceptId(), ca
						.getAnswerConcept().getDisplayString());
			}
		}
		return answersMap;
	}

	/**
	 * Creates a map of all providers in the system
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> createProviderOptions() {
		HashMap<Integer, String> providerOptions = new HashMap<Integer, String>();
		List<User> providers = Context.getUserService().getUsersByRole(
				Context.getUserService().getRole("Provider"));
		if (providers != null) {
			for (User user : providers) {
				providerOptions.put(user.getUserId(), user.getPersonName()
						.getGivenName()
						+ "  " + user.getPersonName().getFamilyName());
			}
		}
		return providerOptions;
	}

	/**
	 * @return
	 */
	public static HashMap<Integer, String> createIdentifierTypesOptions() {
		HashMap<Integer, String> idTypesOptions = new HashMap<Integer, String>();
		List<PatientIdentifierType> idTypes = Context.getPatientService()
				.getAllPatientIdentifierTypes(false);
		if (idTypes != null) {
			for (PatientIdentifierType pit : idTypes) {
				idTypesOptions.put(pit.getPatientIdentifierTypeId(), pit
						.getName());
			}
		}
		return idTypesOptions;
	}

	/**
	 * Creates a map of all sponsors in the system.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static HashMap<Integer, String> createSponsorsOptions()
			throws Exception {
		HashMap<Integer, String> sponsorsOptions = new HashMap<Integer, String>();
		MohTracPortalService service = (MohTracPortalService) ServiceContext
				.getInstance().getService(MohTracPortalService.class);
		List<Sponsor> sponsors = service.getAllSponsors();
		if (sponsors != null) {
			for (Sponsor sp : sponsors) {
				sponsorsOptions.put(sp.getSponsorId(), sp.getName());
			}
		}
		return sponsorsOptions;
	}

	/**
	 * Creates a map of all locations in the system.
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> createLocationOptions() {
		HashMap<Integer, String> locationOptions = new HashMap<Integer, String>();
		List<Location> locations = Context.getLocationService()
				.getAllLocations();
		if (locations != null) {
			for (Location location : locations) {
				locationOptions.put(location.getLocationId(), location
						.getName());
			}
		}
		return locationOptions;
	}

	/**
	 * Creates a map of all attributeTypes in the system
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> createAttributesOptions() {
		HashMap<Integer, String> attributeOptions = new HashMap<Integer, String>();
		List<PersonAttributeType> attributes = Context.getPersonService()
				.getAllPersonAttributeTypes();
		if (attributes != null) {
			for (PersonAttributeType attrib : attributes) {
				attributeOptions.put(attrib.getPersonAttributeTypeId(), attrib
						.getName());
			}
		}
		return attributeOptions;
	}

	/**
	 * Get a message from message propertie file
	 * 
	 * @param messageId
	 * @param parameters
	 * @return
	 */
	public static String getMessage(String messageId, Object[] parameters) {
		
//		LogFactory.getLog(MohTracUtil.class).info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+Context.getLocale());
		
		String msg = ContextProvider.getMessage(
				messageId, parameters);
		
		
		return (null!=msg)?msg:"";
	}

	/**
	 * Format a double and round it at 2 digits after comma
	 * 
	 * @param d
	 *            Double to format and round at 2 digits after comma
	 * @return
	 */
	public static Double roundTo2DigitsAfterComma(Double d) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);

		return Double.parseDouble(df.format(d));
	}

	/**
	 * Get the MySQL date format
	 * 
	 * @return
	 */
	public static SimpleDateFormat getMySQLDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static int[] getAdultWHOStagePossibleOptions() {
		return new int[] { 1204, 1205, 1206, 1207 };
	}

	public static int[] getPediatricWHOStagePossibleOptions() {
		return new int[] { 1220, 1221, 1222, 1223 };
	}

	/**
	 * Return a map of 4 possible result of WHO Stage for adults
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> createWHOStageAdultOptions() {
		HashMap<Integer, String> whoStageOptions = new HashMap<Integer, String>();

		for (int conceptId : getAdultWHOStagePossibleOptions()) {
			Concept c = Context.getConceptService().getConcept(conceptId);
			whoStageOptions.put(c.getConceptId().intValue(), c
					.getDisplayString());
		}
		return whoStageOptions;
	}

	/**
	 * Return a map of 4 possible result of WHO Stage for pediatric
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> createWHOStagePediatricOptions() {
		HashMap<Integer, String> whoStageOptions = new HashMap<Integer, String>();
		for (int conceptId : getPediatricWHOStagePossibleOptions()) {
			Concept c = Context.getConceptService().getConcept(conceptId);
			whoStageOptions.put(c.getConceptId().intValue(), c
					.getDisplayString());
		}
		return whoStageOptions;
	}
}
