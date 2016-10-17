/**
 * 
 */
package org.openmrs.module.mohtracportal.service;

import java.sql.SQLException;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.module.mohtracportal.SampleCode;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.SponsorLocation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yves GAKUBA
 * 
 */

@Transactional
public interface MohTracPortalService {
	/* behavior of a sponsor */
	public void saveSponsor(Sponsor sp) throws Exception;

	public Sponsor getSponsorById(Integer sponsorId) throws Exception;

	public List<Sponsor> getAllSponsors() throws Exception;

	/* end of behavior of a sponsor */

	/* behavior of a sampleTest */
	public void saveSampleTest(SampleCode st) throws SQLException;

	public SampleCode getSampleTestById(Integer sampleTestId) throws Exception;

	public List<SampleCode> getAllSampleTestByLocation(Location location) throws Exception;

	public List<SampleCode> getAllSampleTestByTestType(Concept testConceptId) throws Exception;

	public SampleCode getSampleTestBySampleCode(String sampleCode) throws Exception;

	public SampleCode getSampleTestByObs(Obs o) throws Exception;

	public List<SampleCode> getAllSampleTestByPerson(Person personId) throws Exception;

	public List<SampleCode> getAllSampleTests() throws Exception;

	public List<String> getAllSampleCodes() throws Exception;

	/**
	 * @param status
	 *            all/complete/incomplete or 1/2/3
	 * @return
	 */
	public List<String> getAllSampleCodesByResultStatus(String status) throws Exception;

	/* end of behavior of a sampleTest */

	/* behavior of a sponsorlocation */

	public void saveLocationSponsor(SponsorLocation spLoc) throws Exception;

	public List<SponsorLocation> getAllLocationSponsors() throws Exception;

	public SponsorLocation getLocationSponsorById(Integer locationSponsorId) throws Exception;

	public List<Integer> getAllLocationSponsorIds() throws Exception;

	public List<Integer> getLocationBySponsorId(Integer sponsorId) throws Exception;

	public List<Integer> getSponsorListByLocationId(Integer sponsorId) throws Exception;

	public Sponsor getSponsorByLocationId(Integer locationId) throws Exception;

	/* end of behavior of a sponsorlocation */

	/* Patient list */
	public List<Object> getPatientIdByRegistrationDate(String dateFrom, String dateTo, List<Integer> selectedUsers)
			throws Exception;

	public String getDateOfFirstRecordByObjectAndByUser(Integer userId, Integer objectId) throws Exception;

	public String getDateOfLastRecordByObjectAndByUser(Integer userId, Integer objectId) throws Exception;

	public String getNumberOfRecordCreatedByObjectAndByUser(Integer userId, Integer objectId, Integer period)
			throws Exception;

	/* end Patient list */

	public int executeMySQLCommand(String sql);

}
