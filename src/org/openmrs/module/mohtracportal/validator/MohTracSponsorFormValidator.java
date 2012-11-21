/**
 * 
 */
package org.openmrs.module.mohtracportal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.mohtracportal.Sponsor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracSponsorFormValidator implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class c) {
		return Sponsor.class.isAssignableFrom(c);
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors error) {
		Sponsor sp = (Sponsor) obj;

		if (sp.getName() == null || sp.getName().trim().compareTo("") == 0)
			error.rejectValue("name", "name");
	}
}
