gradle clean
gradle shadowJar
gradle install
java \
-cp /home/stoner/Projects/polarize/build/libs/polarize-1.0.0-all.jar:\
/home/stoner/Projects/testpolarize/build/libs/testpolarize-1.0-SNAPSHOT.jar \
org.testng.TestNG \
-reporter com.github.redhatqe.polarize.junitreporter.XUnitReporter \
src/main/resources/suites/test-suite.xml