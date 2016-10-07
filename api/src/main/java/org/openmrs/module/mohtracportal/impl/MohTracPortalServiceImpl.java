/**
 * 
 */
package org.openmrs.module.mohtracportal.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.SampleCode;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.SponsorLocation;
import org.openmrs.module.mohtracportal.db.MohTracPortalDAO;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.module.reporting.definition.DefinitionSummary;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.Report;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Priority;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.module.reporting.web.renderers.DefaultWebRenderer;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPortalServiceImpl implements MohTracPortalService {

	private MohTracPortalDAO portalDao;

	/**
	 * @return the portalDao
	 */
	public MohTracPortalDAO getPortalDao() {
		return portalDao;
	}

	/**
	 * @param portalDao
	 *            the portalDao to set
	 */
	public void setPortalDao(MohTracPortalDAO portalDao) {
		this.portalDao = portalDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.mohtracportal.service.MohTracPortalService#
	 * getAllSponsors ()
	 */
	@Override
	public List<Sponsor> getAllSponsors() throws Exception {
		return portalDao.getAllSponsors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.mohtracportal.service.MohTracPortalService#
	 * getSponsorById (java.lang.Integer)
	 */
	@Override
	public Sponsor getSponsorById(Integer sponsorId) throws Exception {
		return portalDao.getSponsorById(sponsorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.mohtracportal.service.MohTracPortalService#saveSponsor
	 * (org.openmrs.module.mohtracportal.Sponsor)
	 */
	@Override
	public void saveSponsor(Sponsor sp) throws Exception {
		portalDao.saveSponsor(sp);
	}

	@Override
	public List<SampleCode> getAllSampleTestByLocation(Location location) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTestByPerson(Person personId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTestByTestType(Concept testConceptId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTests() throws Exception {
		return portalDao.getAllSampleTests();
	}

	@Override
	public SampleCode getSampleTestById(Integer sampleCodeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSampleTest(SampleCode sc) throws SQLException {
		portalDao.saveSampleTest(sc);
	}

	@Override
	public SampleCode getSampleTestBySampleCode(String sampleCode) throws Exception {
		return portalDao.getSampleTestBySampleCode(sampleCode);
	}

	@Override
	public SampleCode getSampleTestByObs(Obs o) throws Exception {
		return portalDao.getSampleTestByObs(o);
	}

	@Override
	public List<String> getAllSampleCodes() throws Exception {
		return portalDao.getAllSampleCodes();
	}

	@Override
	public List<String> getAllSampleCodesByResultStatus(String status) throws Exception {
		return portalDao.getAllSampleCodesByResultStatus(status);
	}

	@Override
	public List<SponsorLocation> getAllLocationSponsors() throws Exception {
		return portalDao.getAllLocationSponsors();
	}

	@Override
	public SponsorLocation getLocationSponsorById(Integer locationSponsorId) throws Exception {
		return portalDao.getLocationSponsorById(locationSponsorId);
	}

	@Override
	public void saveLocationSponsor(SponsorLocation spLoc) throws Exception {
		portalDao.saveLocationSponsor(spLoc);
	}

	@Override
	public List<Integer> getAllLocationSponsorIds() throws Exception {
		return portalDao.getAllLocationSponsorIds();
	}

	@Override
	public List<Integer> getLocationBySponsorId(Integer sponsorId) throws Exception {
		return portalDao.getLocationBySponsorId(sponsorId);
	}

	@Override
	public Sponsor getSponsorByLocationId(Integer locationId) throws Exception {
		return portalDao.getSponsorByLocationId(locationId);
	}

	@Override
	public List<Integer> getSponsorListByLocationId(Integer sponsorId) throws Exception {
		return portalDao.getSponsorListByLocationId(sponsorId);
	}

	public List<Object> getPatientIdByRegistrationDate(String dateFrom, String dateTo, List<Integer> selectedUsers)
			throws Exception {
		return portalDao.getPatientIdByRegistrationDate(dateFrom, dateTo, selectedUsers);
	}

	public String getDateOfFirstRecordByObjectAndByUser(Integer userId, Integer objectId) throws Exception {
		return portalDao.getDateOfFirstOrLastRecordByObjectAndByUser(userId, objectId, 0);
	}

	public String getDateOfLastRecordByObjectAndByUser(Integer userId, Integer objectId) throws Exception {
		return portalDao.getDateOfFirstOrLastRecordByObjectAndByUser(userId, objectId, 1);
	}

	public String getNumberOfRecordCreatedByObjectAndByUser(Integer userId, Integer objectId, Integer period)
			throws Exception {
		return portalDao.getNumberOfRecordCreatedByObjectAndByUser(userId, objectId, period);
	}

	@Override
	public int executeMySQLCommand(String sql) {
		return portalDao.executeMySQLCommand(sql);
	}

	@Override
	public Report executeAndGetAdultArtMonthlyWhichIncludesAdultFollowUpReport() {
		DefinitionSummary lostToFollowUp = null;
		Report report = null;
		List<DefinitionSummary> defs = Context.getService(ReportDefinitionService.class)
				.getAllDefinitionSummaries(false);

		for (DefinitionSummary ds : defs) {
			if ("HIV-Adult ART Report-Monthly".equals(ds.getName())) {
				lostToFollowUp = ds;
				break;
			}
		}

		if (lostToFollowUp != null) {
			ReportRequest rr = Context.getService(ReportService.class).getReportRequestByUuid(lostToFollowUp.getUuid());
			ReportDefinition def = Context.getService(ReportDefinitionService.class)
					.getDefinitionByUuid(lostToFollowUp.getUuid());

			if (rr != null)
				rr = new ReportRequest(new Mapped<ReportDefinition>(def, null), null,
						new RenderingMode(new DefaultWebRenderer(), "Web", null, 100), Priority.NORMAL, null);
			report = Context.getService(ReportService.class).runReport(rr);
		}

		return report;
	}

	@Override
	public void getLostToFollowupFromReportHistory() {
		try {
			List<DefinitionSummary> defs = Context.getService(ReportDefinitionService.class)
					.getAllDefinitionSummaries(false);
			List<ReportRequest> history = Context.getService(ReportService.class).getReportRequests(null, null, null,
					null);
			List<ReportDefinition> artReportDef = Context.getService(ReportDefinitionService.class)
					.getDefinitions("HIV-Adult ART Report-Monthly", true);
			if (history != null) {
				for (ReportRequest rq : history) {

				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
