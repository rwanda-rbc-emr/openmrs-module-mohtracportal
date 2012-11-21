/**
 * 
 */
package org.openmrs.module.mohtracportal.db.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.SampleCode;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.SponsorLocation;
import org.openmrs.module.mohtracportal.db.MohTracPortalDAO;
import org.openmrs.module.mohtracportal.util.MohTracUtil;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPortalDAOImpl implements MohTracPortalDAO {

	private Log log = LogFactory.getLog(this.getClass());
	private SessionFactory sessionFactory;

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return
	 */
	private Session getSession() {
		if (getSessionFactory().isClosed())
			log.info(">>>>Portal_DAO>> sessionFactory is closed!");
		Session session = getSessionFactory().getCurrentSession();
		if (session == null) {
			log
					.info(">>>>Portal_DAO>> Trying to close the existing session...");
			Context.closeSession();
			log.info(">>>>Portal_DAO>> Session closed.");
			log.info(">>>>Portal_DAO>> Trying to open new session...");
			Context.openSession();
			log.info(">>>>Portal_DAO>> New Session created.");
			try {
				session = getSessionFactory().getCurrentSession();
			} catch (Exception e) {
				log.error(">>>>>>>>Portal_DAO>> Session Error : " + session);
				e.printStackTrace();
			}
		}
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.mohtracportal.db.MohTracPortalDAO#getAllSponsors()
	 */
	public List<Sponsor> getAllSponsors() {
		List<Sponsor> allSponsors = getSession().createCriteria(Sponsor.class)
				.list();

		return allSponsors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.mohtracportal.db.MohTracPortalDAO#getSponsorById(java
	 * .lang.Integer)
	 */
	public Sponsor getSponsorById(Integer sponsorId) {
		Sponsor sp = (Sponsor) getSession().load(Sponsor.class, sponsorId);

		return sp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.mohtracportal.db.MohTracPortalDAO#saveSponsor(org.
	 * openmrs.module.mohtracportal.Sponsor)
	 */
	@Override
	public void saveSponsor(Sponsor sp) {
		getSession().saveOrUpdate(sp);
	}

	@Override
	public List<SampleCode> getAllSampleTestByLocation(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTestByPerson(Person personId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTestByTestType(Concept testConceptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleCode> getAllSampleTests() {
		List<SampleCode> allSampleTests = getSession().createCriteria(
				SampleCode.class).list();

		return allSampleTests;
	}

	@Override
	public SampleCode getSampleTestById(Integer sampleCodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSampleTest(SampleCode st) {
		getSession().saveOrUpdate(st);
	}

	@Override
	public SampleCode getSampleTestBySampleCode(String sampleCode) {

		SampleCode sc = (SampleCode) getSession().createCriteria(
				SampleCode.class)
				.add(Restrictions.eq("sampleCode", sampleCode)).uniqueResult();

		return sc;
	}

	@Override
	public SampleCode getSampleTestByObs(Obs o) throws Exception {
		if (o == null)
			return null;
		SampleCode sc = (SampleCode) getSession().createCriteria(
				SampleCode.class).add(Restrictions.eq("testTaken", o))
				.uniqueResult();

		return sc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllSampleCodes() {
		List<String> sampleCodes = getSession().createSQLQuery(
				"SELECT sample_code FROM trac_sample_test WHERE voided=0")
				.list();

		return sampleCodes;
	}

	@Override
	public List<String> getAllSampleCodesByResultStatus(String status) {
		String query = "";
		if (status.compareToIgnoreCase("complete") == 0
				|| status.compareToIgnoreCase("2") == 0)
			query = "SELECT t.sample_code FROM trac_sample_test t INNER JOIN obs o ON t.test_obs_id=o.obs_id WHERE t.voided=0 "
					+ "AND value_group_id IS NOT NULL OR value_boolean IS NOT NULL OR value_coded IS NOT NULL OR value_coded_name_id IS NOT NULL OR "
					+ "value_drug IS NOT NULL OR value_datetime IS NOT NULL OR value_numeric IS NOT NULL OR value_modifier IS NOT NULL OR value_text IS NOT NULL";
		else if (status.compareToIgnoreCase("incomplete") == 0
				|| status.compareToIgnoreCase("3") == 0)
			query = "SELECT t.sample_code FROM trac_sample_test t INNER JOIN obs o ON t.test_obs_id=o.obs_id WHERE t.voided=0 "
					+ "AND o.value_group_id IS NULL AND o.value_boolean IS NULL AND o.value_coded IS NULL AND o.value_coded_name_id IS NULL "
					+ "AND o.value_drug IS NULL AND o.value_datetime IS NULL AND o.value_numeric IS NULL AND o.value_modifier IS NULL AND o.value_text IS NULL ;";
		else
			query = "SELECT sample_code FROM trac_sample_test t INNER JOIN obs o ON t.test_obs_id=o.obs_id WHERE t.voided=0";

		query += " ORDER BY o.obs_datetime DESC";

		List<String> sampleCodes = getSession().createSQLQuery(query).list();

		return sampleCodes;
	}

	@Override
	public List<SponsorLocation> getAllLocationSponsors() {
		List<SponsorLocation> allSponsorLocations = getSession()
				.createCriteria(SponsorLocation.class).list();

		return allSponsorLocations;
	}

	@Override
	public SponsorLocation getLocationSponsorById(Integer locationSponsorId) {
		SponsorLocation spLoc = (SponsorLocation) getSession().load(
				SponsorLocation.class, locationSponsorId);

		return spLoc;
	}

	@Override
	public void saveLocationSponsor(SponsorLocation spLoc) {
		if (spLoc.getSponsorLocationId() == null) {
			getSession().createSQLQuery(
					"UPDATE trac_sponsor_location SET date_voided='"
							+ (new SimpleDateFormat("yyyy-MM-dd").format(spLoc
									.getSponseredFrom()))
							+ "' WHERE voided=0 AND location_id="
							+ spLoc.getLocation().getLocationId())
					.executeUpdate();
			getSession().createSQLQuery(
					"UPDATE trac_sponsor_location SET voided=1 WHERE location_id="
							+ spLoc.getLocation().getLocationId())
					.executeUpdate();
		}
		getSession().saveOrUpdate(spLoc);
	}

	@Override
	public List<Integer> getAllLocationSponsorIds() {
		List<Integer> spLocs = getSession()
				.createSQLQuery(
						"SELECT sponsor_location_id FROM trac_sponsor_location WHERE voided=0")
				.list();

		return spLocs;
	}

	@Override
	public List<Integer> getLocationBySponsorId(Integer sponsorId) {
		List<Integer> spLocs = getSession().createSQLQuery(
				"SELECT sponsor_location_id FROM trac_sponsor_location WHERE sponsor_id="
						+ sponsorId + " order by date_created").list();

		return spLocs;
	}

	@Override
	public Sponsor getSponsorByLocationId(Integer locationId) {

		SponsorLocation sploc = (SponsorLocation) getSession().createCriteria(
				SponsorLocation.class).add(
				Restrictions.eq("location", locationId)).add(
				Restrictions.eq("voided", 0)).uniqueResult();

		return sploc.getSponsor();
	}

	@Override
	public List<Integer> getSponsorListByLocationId(Integer locationId) {
		List<Integer> spLocs = getSession().createSQLQuery(
				"SELECT sponsor_location_id FROM trac_sponsor_location WHERE location_id="
						+ locationId + " order by date_created").list();

		return spLocs;
	}

	public List<Object> getPatientIdByRegistrationDate(String dateFrom,
			String dateTo, List<Integer> selectedUsers) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<Object> patientIds = null;

		String query = "SELECT p.patient_id, CAST(p.date_created AS DATE), p.voided, ps.dead FROM patient p INNER JOIN person ps ON p.patient_id=ps.person_id";
		try {
			int i = 0;
			if (selectedUsers != null && selectedUsers.size() != 0) {
				query += " INNER JOIN users u ON p.creator=u.user_id WHERE (";
				for (Integer userId : selectedUsers) {
					if (i == 0)
						query += "p.creator=" + userId;
					else
						query += " OR p.creator=" + userId;
					i++;
				}
				query += ")";
			}
			if (dateFrom != null && dateFrom.compareTo("") != 0) {
				if (i == 0)
					query += " WHERE";
				else
					query += " AND";
				query += " (CAST(p.date_created AS DATE))>='"
						+ sdf.format(Context.getDateFormat().parse(dateFrom))
						+ "'";
				if (dateTo.compareTo("") != 0) {
					query += " AND (CAST(p.date_created AS DATE))<='"
							+ sdf.format(Context.getDateFormat().parse(dateTo))
							+ "'";
				}
			} else if (dateTo != null && dateTo.compareTo("") != 0) {
				if (i == 0)
					query += " WHERE";
				else
					query += " AND";
				query += " (CAST(p.date_created AS DATE))<='"
						+ sdf.format(Context.getDateFormat().parse(dateTo))
						+ "'";
			}
			query += " ORDER BY p.date_created DESC";

			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Query : "
					+ query);

			patientIds = getSession().createSQLQuery(query).list();

		} catch (Exception ex) {
			log
					.error("An error occured when trying to load data from database.");
			ex.printStackTrace();
		}

		return patientIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.openmrs.module.mohtracportal.db.MohTracPortalDAO#
	 * getDateOfFirstOrLastRecordByObjectAndByUser(java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String getDateOfFirstOrLastRecordByObjectAndByUser(Integer userId,
			Integer objectId, Integer firstOrLast) {
		String dateOfFirstRecord = "", minOrMax = (firstOrLast == 0) ? "MIN"
				: "MAX";
		if (objectId.intValue() == 0)
			dateOfFirstRecord = getSession()
					.createSQLQuery(
							"SELECT DATE_FORMAT("
									+ minOrMax
									+ "(date_created),'%Y-%b-%d %H:%i') FROM patient WHERE creator="
									+ (userId.intValue())).uniqueResult()
					.toString();
		else if (objectId.intValue() == 1)
			dateOfFirstRecord = getSession()
					.createSQLQuery(
							"SELECT DATE_FORMAT("
									+ minOrMax
									+ "(date_created),'%Y-%b-%d %H:%i') FROM encounter WHERE creator="
									+ (userId.intValue())).uniqueResult()
					.toString();
		else if (objectId.intValue() == 2)
			dateOfFirstRecord = getSession()
					.createSQLQuery(
							"SELECT DATE_FORMAT("
									+ minOrMax
									+ "(date_created),'%Y-%b-%d %H:%i') FROM obs WHERE creator="
									+ (userId.intValue())).uniqueResult()
					.toString();

		return dateOfFirstRecord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.openmrs.module.mohtracportal.db.MohTracPortalDAO#
	 * getNumberRecordCreatedByObjectAndByUser(java.lang.Integer,
	 * java.lang.Integer)
	 */
	public String getNumberOfRecordCreatedByObjectAndByUser(Integer userId,
			Integer objectId, Integer period) {
		String numberOfRecord = "", allRecords = "";

		if (period.intValue() == 2) {
			if (objectId.intValue() == 0) {
				numberOfRecord = getSession().createSQLQuery(
						"SELECT COUNT(patient_id) FROM patient WHERE creator="
								+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession().createSQLQuery(
						"SELECT COUNT(patient_id) FROM patient").uniqueResult()
						.toString();
			} else if (objectId.intValue() == 1) {
				numberOfRecord = getSession().createSQLQuery(
						"SELECT COUNT(encounter_id) FROM encounter WHERE creator="
								+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession().createSQLQuery(
						"SELECT COUNT(encounter_id) FROM encounter")
						.uniqueResult().toString();
			} else if (objectId.intValue() == 2) {
				numberOfRecord = getSession().createSQLQuery(
						"SELECT COUNT(obs_id) FROM obs WHERE creator="
								+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession().createSQLQuery(
						"SELECT COUNT(obs_id) FROM obs").uniqueResult()
						.toString();
			}

			if (allRecords.compareTo("0") != 0)
				numberOfRecord += " ("
						+ (MohTracUtil.roundTo2DigitsAfterComma(Integer
								.valueOf(numberOfRecord)
								* 100.00 / Integer.valueOf(allRecords)))
						+ "% of all records)";

		} else if (period.intValue() == 0) {
			Date today = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (objectId.intValue() == 0) {
				numberOfRecord = getSession()
						.createSQLQuery(
								"SELECT COUNT(patient_id) FROM patient WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
										+ df.format(today)
										+ "' AND creator="
										+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession()
						.createSQLQuery(
								"SELECT COUNT(patient_id) FROM patient WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
										+ df.format(today) + "'")
						.uniqueResult().toString();
			} else if (objectId.intValue() == 1) {
				numberOfRecord = getSession()
						.createSQLQuery(
								"SELECT COUNT(encounter_id) FROM encounter WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
										+ df.format(today)
										+ "' AND creator="
										+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession()
						.createSQLQuery(
								"SELECT COUNT(encounter_id) FROM encounter WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
										+ df.format(today) + "'")
						.uniqueResult().toString();
			} else if (objectId.intValue() == 2) {
				numberOfRecord = getSession().createSQLQuery(
						"SELECT COUNT(obs_id) FROM obs WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
								+ df.format(today) + "' AND creator="
								+ (userId.intValue())).uniqueResult()
						.toString();

				allRecords = getSession().createSQLQuery(
						"SELECT COUNT(obs_id) FROM obs WHERE DATE_FORMAT(date_created,'%Y-%m-%d')='"
								+ df.format(today) + "'").uniqueResult()
						.toString();
			}

			if (allRecords.compareTo("0") != 0)
				numberOfRecord += " ("
						+ (MohTracUtil.roundTo2DigitsAfterComma(Integer
								.valueOf(numberOfRecord)
								* 100.00 / Integer.valueOf(allRecords)))
						+ "% of all records)";
		}

		return numberOfRecord;
	}

	@Override
	public int executeMySQLCommand(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();

	}

}
