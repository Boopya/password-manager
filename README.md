# How to Use the Password Manager
1. Open the project in Netbeans.
2. In the menu bar, click on Window > Services. A side panel for Services should appear.
3. Right click on the Java DB node, and select Start Server.
4. Once you've started the server, right click again on the Java DB node, and select Create Database
5. Use "Password Manager DB" for the database name, "root" for the username, and "toor" for the password. Click OK.
6. Wait for the database to show under the Java DB node.
7. Once it shows up, you will see something like "jdbc:derby://localhost:1527/Password Manager DB [root on ROOT]
8. Right click on it, and select Connect.
9. Expand that node by clicking the drop-down arrow. You will see the ROOT schema.
10. Expand the ROOT schema by clicking again the drop-down arrow.
11. Expand the Tables node by clicking again the drop-down arrow. You should see that it is empty.
12. Right click on the Tables node, and select Create Table.
13. Use "ACCOUNTS" for the table name.
14. Click on the Add column button.
15. Use "SITENAME" for the name. Use the type "VARCHAR" with size 50. Uncheck the null checkbox.
16. Click on the Add column button.
17. Use "SITEUSERNAME" for the name. Use the type "VARCHAR" with size 50. Uncheck the null checkbox.
18. Click on the Add column button.
19. Use "SITEPASSWORD" for the name. Use the type "VARCHAR" with size 50. Uncheck the null checkbox.
20. Test the program by running the .jar file
