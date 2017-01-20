# $1 is path to polarize project, $2 is version, $3 is project $4 is to enable testcase import
POLARIZE_PATH=$1
POLARIZE_VERSION=$2
PWD=`pwd`
echo "Beginning test-reporter.sh: In directory $PWD"
pushd ${POLARIZE_PATH}

# Run the Configurator.
function configure () {
    java -cp $1/build/libs/polarize-${2}-all.jar \
    com.github.redhatqe.polarize.configuration.Configurator \
    --project $3 --testcase-importer-enabled $4 --base-dir /home/stoner/Projects/testpolarize --edit-config true --project-name testpolarize
}

# Make sure it's set to project=PLATTP and testcase importer is enabled
echo "Configuring the xml-config file"
configure ${POLARIZE_PATH} ${POLARIZE_VERSION} "PLATTP" "true"

gradle clean
gradle pP 2> /dev/null
popd

gradle clean
gradle build

# run testpolarize test
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5006 \
-cp $1/build/libs/polarize-$2-all.jar:\
/home/stoner/Projects/testpolarize/build/libs/testpolarize-1.0-SNAPSHOT.jar \
org.testng.TestNG \
-reporter com.github.redhatqe.polarize.junitreporter.XUnitReporter \
src/main/resources/suites/test-suite.xml

# Set the config back
for i in `ls -At ~/.polarize/backup`; do
  echo $i
  break
done;
mv $i ~/.polarize/xml-config.xml

