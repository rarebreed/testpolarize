package com.github.redhatqe.rhsm.testpolarize;


import com.github.redhatqe.polarize.FileHelper;
import com.github.redhatqe.polarize.IJAXBHelper;
import com.github.redhatqe.polarize.IdParams;
import com.github.redhatqe.polarize.JAXBHelper;
import com.github.redhatqe.polarize.configuration.Configurator;
import com.github.redhatqe.polarize.exceptions.ConfigurationError;
import com.github.redhatqe.polarize.importer.testcase.CustomField;
import com.github.redhatqe.polarize.importer.testcase.Testcase;
import com.github.redhatqe.polarize.metadata.*;
import com.github.redhatqe.polarize.metadata.DefTypes.Project;
import com.github.redhatqe.polarize.metadata.DefTypes.PosNeg;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A dummy example showing how to use the annotations.  Since these are repeating annotations, that means that the
 * java source code must be compiled at Java source of 8
 *
 * **NOTE** The @Requirement, although recognized by the annotation processor are not currently used.  This is because
 * as of yet, there is still no Requirement Importer worked on by the CI Ops team
 *
 * Created by stoner on 7/12/16.
 */
public class TestReq {
    private final String RHEL6 = "RHEL6";
    private final String RedHatEnterpriseLinux = "RedHatEnterpriseLinux";
    private final String PLATTP = "PLATTP";
    private Map<String, Map<String, IdParams>> mapFile;
    private final String configPath = "/tmp/xml-config-testing.xml";

    @TestDefinition(projectID=Project.PLATTP,      // required
              testCaseID="PLATTP-9520",            // if empty or null, make request to WorkItem Importer tool
              importance=DefTypes.Importance.HIGH, // defaults to high  if not given
              posneg=PosNeg.NEGATIVE,              // defaults to positive if not given
              level= DefTypes.Level.COMPONENT,     // defaults to component if not given
              linkedWorkItems={@LinkedItem(workitemId="PLATTP-10348",         // Required
                      project=Project.PLATTP,                                 // Required. What Project to go under
                      role=DefTypes.Role.VERIFIES)},                          // Required. Role type
              // If testtype is FUNCTIONAL, subtype1 and 2 must be of type EMPTY.
              testtype=@TestType(testtype= DefTypes.TestTypes.NONFUNCTIONAL,  // Defaults to FUNCTIONAL
                                 subtype1= DefTypes.Subtypes.COMPLIANCE,      // Defaults to EMPTY (see note)
                                 subtype2= DefTypes.Subtypes.EMPTY),          // Defaults to EMPTY (see note)
              setup="Description of any preconditions that must be established for test case to run",
              tags="tier1 some_description",
              teardown="The methods to clean up after a test method",
              automation=DefTypes.Automation.AUTOMATED)  // if not given this defaults to AUTOMATED)
    @Test(groups={"simple"},
          description="Test for reporter code",
          dataProvider="simpleProvider")
    public void testUpgradeNegative(String name, int age) {
        Assert.assertEquals(age, 44);
        Assert.assertTrue(name.equals("Sean"));
        String methName = "testUpgradeNegative";

        // Test that testcases/RHEL6/com.github.redhatqe.rhsm.testpolarize.TestReq/testUpgradeNegative.xml exists
        this.verifyXMLDoesntExist(RHEL6, methName);

        // Test that testcases/PLATTP/com.github.redhatqe.rhsm.testpolarize.TestReq/testUpgradeNegative.xml exists
        this.verifyXMLExists(PLATTP, methName);

        // Verify that XML that gets generated for RHEL6 and PLATTP has Polarion ID.
        //this.verifyNoIDYetForXML(RHEL6, methName);
        this.verifyIDExistsForXML(PLATTP, methName);

        // Verify that the testtype for RHEL6 has different defaults than for PLATTP
        Optional<Testcase> maybeRHEL6 = this.getObjectFromXML(RHEL6, methName, Testcase.class);
        Optional<Testcase> maybePLATTP = this.getObjectFromXML(PLATTP, methName, Testcase.class);
        if (maybeRHEL6.isPresent() && maybePLATTP.isPresent()) {
            Testcase rhel6 = maybeRHEL6.get();
            Testcase plattp = maybePLATTP.get();
            Optional<CustomField> rhel6FldM = rhel6.getCustomFields().getCustomField().stream()
                    .filter(cf -> cf.getId().equals("testtype")).findFirst();
            Optional<CustomField> plattpFldM = plattp.getCustomFields().getCustomField().stream()
                    .filter(cf -> cf.getId().equals("testtype")).findFirst();
            Assert.assertTrue(rhel6FldM.isPresent());
            Assert.assertTrue(plattpFldM.isPresent());
            if (rhel6FldM.isPresent() && plattpFldM.isPresent()) {
                CustomField rhel6Fld = rhel6FldM.get();
                CustomField plattpFld = plattpFldM.get();
                Assert.assertFalse(rhel6Fld.getContent().equals(plattpFld.getContent()));
            }
        }

        // Verify that the Linked WorkItem
    }

    public File getXMLDescFile(String project, String name) {
        //String pkg = this.getClass().getPackage().getName();
        String clz = this.getClass().getName();
        //pkg = String.format("%s.%s", pkg, clz);
        String tcDir = Paths.get(".", "/testcases", project, clz).toAbsolutePath().normalize().toString();
        tcDir = String.format("%s/%s.xml", tcDir, name);
        return new File(tcDir);
    }

    public void verifyXMLExists(String project, String name) {
        File pathName = this.getXMLDescFile(project, name);
        Assert.assertTrue(pathName.exists());
    }

    public void verifyXMLDoesntExist(String project, String name) {
        File pathName = this.getXMLDescFile(project, name);
        Assert.assertFalse(pathName.exists());
    }

    public <T> Optional<T> getObjectFromXML(String project, String name, Class<T> tc) {
        File xmlDesc = this.getXMLDescFile(project, name);
        JAXBHelper jaxb = new JAXBHelper();
        return IJAXBHelper.unmarshaller(tc, xmlDesc, jaxb.getXSDFromResource(Testcase.class));
    }

    public void verifyIDExistsForXML(String project, String name) {
        Optional<Testcase> maybeTC = this.getObjectFromXML(project, name, Testcase.class);
        Assert.assertTrue(maybeTC.isPresent());
        if (maybeTC.isPresent()) {
            Testcase tc = maybeTC.get();
            Assert.assertFalse(tc.getId().equals(""));
        }
        // TODO: when there is a query mechanism in place, query for the existence of this Polarion ID
    }

    public void verifyNoIDYetForXML(String project, String name) {
        Optional<Testcase> maybeTC = this.getObjectFromXML(project, name, Testcase.class);
        Assert.assertTrue(maybeTC.isPresent());
        if (maybeTC.isPresent()) {
            Testcase tc = maybeTC.get();
            Assert.assertTrue(tc.getId().equals(""));
        }
    }

    public Map<String, Map<String, IdParams>> getMapping(String path) throws IOException {
        File mapPath = Paths.get(path).toFile();
        if (!mapPath.exists())
            throw new IOException(String.format("No mapping.json exists at %s", path));
        return FileHelper.loadMapping(mapPath);
    }

    /**
     * Shows an example of duplicated TestDefinition
     *
     * This is a test that uses a DataProvider.  The annotation processor will determine the names of the arguments
     * and use this as the names for the TestStep Parameters.  In the example below, the processor will determine that
     * this method has 2 arguments called "name" and "age".  It will create the XML necessary to include those params.
     *
     * If a method is identical for multiple projects, you can simply set projectID as an array as shown in the example
     * below.  Note that the testCaseID ordering must match.  In other words, the testCaseID at index 0 should match
     * the project in index 0.  When using the arrays, you have to leave the testCaseID as an empty string and can not
     * leave it blank.
     *
     * For example, if the testCaseID exists for Project.RedHatEnterpriseLinux7 but not for Project.RHEL6, you should
     * show it like:
     *
     * projectID={Project.RedHatEnterpriseLinux7, Project.RHEL6},
     * testCaseID={"RHEL7-20458", ""}
     * // testCaseID={"RHEL7-20458"}   <- This is wrong and compiler will fail
     *
     * @param name
     * @param age
     */
    @TestDefinition(projectID={Project.PLATTP})
    @Test(groups={"simple"},
          description="A simple annotation that automatically gets parameter names from method source ",
          dataProvider="simpleProvider")
    public void testUpgrade(String name, int age) {
        String methName = Thread.currentThread().getStackTrace()[0].getMethodName();
        Assert.assertTrue(name.equals("Sean") || name.equals("Toner"));
        Assert.assertTrue(age < 100);

        this.verifyXMLDoesntExist(RedHatEnterpriseLinux, methName);
    }

    @TestDefinition(projectID={Project.PLATTP},
    importance=DefTypes.Importance.LOW)
    @Test(groups={"simple"},
          description="Testing if XUnitReporter gets stack trace")
    public void testError() {
        throw new ConfigurationError("Just throwing an error");
    }

    @TestDefinition(projectID={Project.PLATTP},
                    testCaseID={""},
                    level=DefTypes.Level.SYSTEM)
    @Test(groups={"simple"},
          description="Tests to make sure that the compiler fails")
    public void testBadProjectToTestCaseID() {
        Assert.assertTrue(true);
    }

    @DataProvider(name="simpleProvider")
    public Object[][] dataDriver() {
        Object[][] table = new Object[2][2];
        List<Object> row = new ArrayList<>();

        row.add("Sean");
        row.add(44);
        table[0] = row.toArray();

        row = new ArrayList<>();
        row.add("Toner");
        row.add(0);
        table[1] = row.toArray();
        return table;
    }

    @DataProvider(name="anotherProvider")
    public Object[][] dataCreator() {
        Object[][] table = new Object[2][2];
        List<Object> row = new ArrayList<>();

        Object[] objs = {"Circle", 100.0};
        table[0] = objs;

        Object[] objs2 = {"Square", 200.0};
        table[1] = objs2;
        return table;
    }
}
