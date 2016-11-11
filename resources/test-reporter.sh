# $1 is path to polarize project, $2 is version
POLARIZE_PATH=$1
POLARIZE_VERSION=$2
gradle clean
gradle shadowJar
gradle install

# Run the Configurator.
function configure () {
    java -cp $1/build/libs/polarize-${2}-all.jar \
    org.github.redhatqe.polarize.configuration.Configurator \
    --project $3 --testcase-importer-enabled $4
}

# Make sure it's set to project=PLATTP and testcase importer is enabled
configure ${POLARIZE_PATH} ${POLARIZE_VERSION} "PLATTP" "true"

# run testpolarize test
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5006 \
-cp /home/stoner/Projects/polarize/build/libs/polarize-$2-all.jar:\
/home/stoner/Projects/testpolarize/build/libs/testpolarize-1.0-SNAPSHOT.jar \
org.testng.TestNG \
-reporter com.github.redhatqe.polarize.junitreporter.XUnitReporter \
src/main/resources/suites/test-suite.xml

# Set the config back
configure ${POLARIZE_PATH} ${POLARIZE_VERSION} "RHEL6" "false"

