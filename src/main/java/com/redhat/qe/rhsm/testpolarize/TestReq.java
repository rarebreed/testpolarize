package com.redhat.qe.rhsm.testpolarize;

import com.redhat.qe.rhsm.metadata.Requirement;
import com.redhat.qe.rhsm.metadata.TestCase;
import org.testng.annotations.Test;

/**
 * A dummy example showing how to use the annotations.  Since these are repeating annotations, that means that the
 * java source code must be compiled at Java source of 8
 *
 * Created by stoner on 7/12/16.
 */
@Requirement(project="RHEL6", author="Sean Toner",
             description="Class level Requirement for RHEL 6.  All test methods will inherit from a class annotated " +
                     "with @Requirement.  If a test method's @Polarion annotation has a non-empty reqs field, any " +
                     "@Requirements there will override the class Requirement for that method")
@Requirement(project="RedHatEnterpriseLinux7", author="CI User",
             description="Class level Requirement for RHEL7")
public class TestReq {

    @TestCase(projectID="RHEL6",
              description="TestCase for a dummy test method",
              xmlDesc="",
              reqs={})
    @TestCase(author="Sean Toner",                 // required
              projectID="RedHatEnterpriseLinux7",  // required
              testCaseID="RHEL7-56743",            // if empty or null, make request to WorkItem Importer tool
              caseimportance="medium",             // defaults to high  if not given
              caseposneg="negative",               // defaults to positive if not given
              caselevel="component",               // defaults to component if not given
              testtype="non_functional",           // defaults to functional if not given
              reqs={@Requirement(id="",            // if empty, look for class Requirement.  If no class requirement
                                                   // look for xmlDesc.  If none, that means request one.
                                 project="RedHatEnterpriseLinux7",
                                 description="This description will override class level",
                                 xmlDesc="/path/to/xml/description",
                                 feature="/path/to/a/gherkin/feature/file")},
              setup="Description of any preconditions that must be established for test case to run",
              teardown="The methods to clean up after a test method")
    @Test(groups={"testjong_polarize"},
          description="A simple test for polarize")
    public void testUpgradeNegative() {
         System.out.println("Dummy negative test");
    }
}
