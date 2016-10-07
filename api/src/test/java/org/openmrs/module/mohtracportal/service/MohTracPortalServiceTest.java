package org.openmrs.module.mohtracportal.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.definition.DefinitionSummary;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MohTracPortalServiceTest extends BaseModuleContextSensitiveTest {

	@Test
	public void shouldSetupContext() {
		Assert.assertNotNull(Context.getService(MohTracPortalService.class));
	}

	@Test(expected = HibernateException.class)
	public void reportingBug() throws Exception {
		Context.getService(ReportDefinitionService.class).getAllDefinitionSummaries(false);
	}
}
