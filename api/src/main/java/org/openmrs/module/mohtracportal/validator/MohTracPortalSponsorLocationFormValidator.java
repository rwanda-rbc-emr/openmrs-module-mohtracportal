/**
 * 
 */
package org.openmrs.module.mohtracportal.validator;

import org.openmrs.module.mohtracportal.SponsorLocation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPortalSponsorLocationFormValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class c) {
		return SponsorLocation.class.isAssignableFrom(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors err) {
		@SuppressWarnings("unused")
		SponsorLocation spLoc = (SponsorLocation) obj;
	}

}
