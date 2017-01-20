gradle clean
gradle shadowJar
gradle install
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5006 \
-cp /home/stoner/Projects/polarize/build/libs/polarize-0.5.4-SNAPSHOT-all.jar:\
/home/stoner/Projects/testpolarize/build/libs/testpolarize-1.0-SNAPSHOT.jar \
org.testng.TestNG \
-reporter com.github.redhatqe.polarize.junitreporter.XUnitReporter \
src/main/resources/suites/test-suite.xml