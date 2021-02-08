DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'root') THEN
      CREATE USER root WITH PASSWORD 'root';
   END IF;
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_database
      WHERE  datname = 'pepperbank') THEN
      CREATE DATABASE pepperbank;
   END IF;
   GRANT ALL PRIVILEGES ON DATABASE pepperbank TO root;
   CREATE SCHEMA IF NOT EXISTS pepperbank AUTHORIZATION root;
END
$do$;