package org.openmrs.module.mohtracportal.service;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MohTracPortalServiceTest extends BaseModuleContextSensitiveTest {

	@Test
	public void shouldSetupContext() {
		Assert.assertNotNull(Context.getService(MohTracPortalService.class));
	}
}
