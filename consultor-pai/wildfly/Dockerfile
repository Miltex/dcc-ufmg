FROM quay.io/wildfly/wildfly:latest-jdk11

# Set up an environment variable for the driver version
ENV MARIADB_DRIVER_VERSION=3.1.4

# 1. Create Admin User
# 2. Install MariaDB driver as a module
# 3. Configure the datasource
RUN \
  # Create Admin user
  /opt/jboss/wildfly/bin/add-user.sh admin admin --silent && \
  \
  # Create module directory
  mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main && \
  \
  # Download driver and place it in the module directory
  curl -o /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main/mariadb-java-client.jar https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/${MARIADB_DRIVER_VERSION}/mariadb-java-client-${MARIADB_DRIVER_VERSION}.jar && \
  \
  # Create module.xml
  echo '<?xml version="1.0" encoding="UTF-8"?><module xmlns="urn:jboss:module:1.3" name="org.mariadb"><resources><resource-root path="mariadb-java-client.jar"/></resources><dependencies><module name="javax.api"/><module name="javax.transaction.api"/></dependencies></module>' > /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/main/module.xml && \
  \
  # Use jboss-cli to configure the driver and datasource
  echo 'embed-server --std-out=echo' > /tmp/datasource_setup.cli && \
  echo '/subsystem=datasources/jdbc-driver=mariadb:add(driver-name=mariadb,driver-module-name=org.mariadb,driver-class-name=org.mariadb.jdbc.Driver)' >> /tmp/datasource_setup.cli && \
  echo '/subsystem=datasources/data-source=MyDS:add(jndi-name=java:jboss/datasources/MyDS,driver-name=mariadb,connection-url="jdbc:mariadb://mariadb-db:3306/crud_db",user-name=crud_user,password=crud_password,enabled=true)' >> /tmp/datasource_setup.cli && \
  echo 'stop-embedded-server' >> /tmp/datasource_setup.cli && \
  /opt/jboss/wildfly/bin/jboss-cli.sh --file=/tmp/datasource_setup.cli && \
  rm /tmp/datasource_setup.cli

# Copy the application EAR. This should be the last step.
COPY CRUD-ear/target/CRUD-ear.ear /opt/jboss/wildfly/standalone/deployments/