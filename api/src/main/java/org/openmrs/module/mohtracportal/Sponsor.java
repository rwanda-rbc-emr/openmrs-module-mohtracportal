/**
 * 
 */
package org.openmrs.module.mohtracportal;

import java.util.Date;

import org.openmrs.User;

/**
 * @author Yves GAKUBA
 * 
 */
public class Sponsor {
	private Integer sponsorId;
	private String name;
	private String description;
	private Date dateCreated;
	private User creator;	
	private boolean voided = false;
	private Date dateVoided;
	private User voidedBy;

	/**
	 * @return the sponsorId
	 */
	public Integer getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId
	 *            the sponsorId to set
	 */
	public void setSponsorId(Integer sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @param voided the voided to set
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
	 * @param dateVoided the dateVoided to set
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
	 * @param voidedBy the voidedBy to set
	 */
	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}
	
}
