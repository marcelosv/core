/**
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.addons.ui;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.addons.facets.AddonTestFacet;
import org.jboss.forge.addon.addons.facets.FurnaceVersionFacet;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.ui.controller.CommandController;
import org.jboss.forge.addon.ui.test.UITestHarness;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.forge.furnace.Furnace;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author <a href="mailto:danielsoro@gmail.com">Daniel Cunha (soro)</a>
 * 
 */
@RunWith(Arquillian.class)
public class NewUICommandWizardTest
{
   @Deployment
   @AddonDependencies({
            @AddonDependency(name = "org.jboss.forge.addon:parser-java"),
            @AddonDependency(name = "org.jboss.forge.addon:maven"),
            @AddonDependency(name = "org.jboss.forge.addon:projects"),
            @AddonDependency(name = "org.jboss.forge.addon:ui-test-harness"),
            @AddonDependency(name = "org.jboss.forge.addon:addons"),
            @AddonDependency(name = "org.jboss.forge.furnace.container:cdi")
   })
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap.create(AddonArchive.class).addBeansXML();
   }

   @Inject
   private ProjectFactory projectFactory;

   @Inject
   private FacetFactory facetFactory;

   @Inject
   private UITestHarness testHarness;

   @Inject
   private Furnace furnace;

   @Test
   public void testDefaultCommandNameBasedOnTypeNameUpperCaseWithCommandSuffix() throws Exception
   {
      Project project = projectFactory.createTempProject();
      facetFactory.install(project, JavaSourceFacet.class);
      facetFactory.install(project, FurnaceVersionFacet.class);
      project.getFacet(FurnaceVersionFacet.class).setVersion(furnace.getVersion().toString());
      facetFactory.install(project, AddonTestFacet.class);
      CommandController controller = testHarness.createCommandController(NewUICommandWizard.class, project.getRoot());
      controller.initialize();
      controller.setValueFor("named", "MyTestCaseCOMMAND");
      controller.setValueFor("targetPackage", "org.jboss.forge.ui.test");
      controller.execute();
      Assert.assertTrue(project.getFacet(JavaSourceFacet.class)
               .getJavaResource("org.jboss.forge.ui.test.MyTestCaseCOMMAND.java").exists());
      Assert.assertEquals("my-test-case", controller.getValueFor("commandName"));

      CommandController controller2 = testHarness.createCommandController(NewUICommandWizard.class, project.getRoot());
      controller2.initialize();
      controller2.setValueFor("named", "MyTestCommand");
      controller2.setValueFor("targetPackage", "org.jboss.forge.ui.test");
      controller2.execute();
      Assert.assertEquals("my-test", controller2.getValueFor("commandName"));

      CommandController controller3 = testHarness.createCommandController(NewUICommandWizard.class, project.getRoot());
      controller3.initialize();
      controller3.setValueFor("named", "MyUITestCommandWizard");
      controller3.setValueFor("targetPackage", "org.jboss.forge.ui.test");
      controller3.execute();
      Assert.assertEquals("my-uitest-command-wizard", controller3.getValueFor("commandName"));

      CommandController controller4 = testHarness.createCommandController(NewUICommandWizard.class, project.getRoot());
      controller4.initialize();
      controller4.setValueFor("named", "MyUITestCommandCommand");
      controller4.setValueFor("targetPackage", "org.jboss.forge.ui.test");
      controller4.execute();
      Assert.assertEquals("my-uitest-command", controller4.getValueFor("commandName"));
   }
}
