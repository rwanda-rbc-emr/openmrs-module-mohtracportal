/**
 * 
 */
package org.openmrs.module.mohtracportal;

import java.util.Date;

import org.openmrs.Obs;
import org.openmrs.User;

/**
 * @author Yves GAKUBA
 * 
 */
public class SampleCode {

	private Integer sampleTestId;
	private String sampleCode;
	private Obs testTaken;
	private String comment;
	private Date dateCreated;
	private User creator;
	private boolean voided = false;
	private Date dateVoided;
	private User voidedBy;

	/**
	 * @return the sampleTestId
	 */
	public Integer getSampleTestId() {
		return sampleTestId;
	}

	/**
	 * @param sampleTestId
	 *            the sampleTestId to set
	 */
	public void setSampleTestId(Integer sampleTestId) {
		this.sampleTestId = sampleTestId;
	}

	/**
	 * @return the sampleCode
	 */
	public String getSampleCode() {
		return sampleCode;
	}

	/**
	 * @param sampleCode
	 *            the sampleCode to set
	 */
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	/**
	 * @return the testTaken
	 */
	public Obs getTestTaken() {
		return testTaken;
	}

	/**
	 * @param testTaken
	 *            the testTaken to set
	 */
	public void setTestTaken(Obs testTaken) {
		this.testTaken = testTaken;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the voided
	 */
	public boolean isVoided() {
		return voided;
	}

	/**
	 * @param voided
	 *            the voided to set
	 */
	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	/**
	 * @return the dateVoided
	 */
	public Date getDateVoided() {
		return dateVoided;
	}

	/**
	 * @param dateVoided
	 *            the dateVoided to set
	 */
	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}

	/**
	 * @return the voidedBy
	 */
	public User getVoidedBy() {
		return voidedBy;
	}

	/**
	 * @param voidedBy
	 *            the voidedBy to set
	 */
	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

}
