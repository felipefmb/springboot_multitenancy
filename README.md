## Multitenancy application

**Separation strategy by column (discriminator column)**

**Installation guide**
-   Create a database in postgres with the name "multitenancia"
-   Run the script located at resource/kickoff.sql to create the database structure.
-   A customer table and a view will be created. The tenance is provided by the view (GET) and table default (POST, PUT and DELETE).

To test the tenancy, lift the application and make a request passing in the header: 
user-access : <id_tenant>

The <id_tenant> can be replaced by any information, therefore, this is a sample application to be used as needed
