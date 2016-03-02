/**
 * 
 */
package org.openmrs.module.mohtracportal;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.User;

/**
 * @author Yves GAKUBA
 * 
 */

public class SponsorLocation {

	private Integer sponsorLocationId;
	private Sponsor sponsor;
	private Location location;
	private String description;
	private Date sponseredFrom;
	private Date dateCreated;
	private User createdBy;
	private Boolean voided=false;
	private Date dateVoided;
	private User voidedBy;

	/**
	 * @return the sponsorLocationId
	 */
	public Integer getSponsorLocationId() {
		return sponsorLocationId;
	}

	/**
	 * @param sponsorLocationId
	 *            the sponsorLocationId to set
	 */
	public void setSponsorLocationId(Integer sponsorLocationId) {
		this.sponsorLocationId = sponsorLocationId;
	}

	/**
	 * @return the sponsor
	 */
	public Sponsor getSponsor() {
		return sponsor;
	}

	/**
	 * @param sponsor
	 *            the sponsor to set
	 */
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
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
	 * @return the sponseredFrom
	 */
	public Date getSponseredFrom() {
		return sponseredFrom;
	}

	/**
	 * @param sponseredFrom
	 *            the sponseredFrom to set
	 */
	public void setSponseredFrom(Date sponseredFrom) {
		this.sponseredFrom = sponseredFrom;
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
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the voided
	 */
	public Boolean getVoided() {
		return voided;
	}

	/**
	 * @param voided
	 *            the voided to set
	 */
	public void setVoided(Boolean voided) {
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
