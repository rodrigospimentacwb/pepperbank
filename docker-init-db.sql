DO
$do$
BEGIN
   IF
NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'root') THEN
      CREATE
USER root WITH PASSWORD 'root';
END IF;
   IF
NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_database
      WHERE  datname = 'dtbasepepper') THEN
      CREATE
DATABASE dtbasepepper;
END IF;
   GRANT ALL PRIVILEGES ON DATABASE
dtbasepepper TO root;
CREATE SCHEMA IF NOT EXISTS pepperbank AUTHORIZATION root;
CREATE
extension "uuid-ossp";
END
$do$;