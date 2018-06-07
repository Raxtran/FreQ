<?xml encoding="UTF-8"?>

<!ELEMENT pma_xml_export (pma:structure_schemas,database)>
<!ATTLIST pma_xml_export
  xmlns CDATA #FIXED ''
  version CDATA #REQUIRED>

<!ELEMENT pma:structure_schemas (pma:database)>
<!ATTLIST pma:structure_schemas
  xmlns:pma CDATA #FIXED 'http://www.phpmyadmin.net/some_doc_url/'>

<!ELEMENT database (table)+>
<!ATTLIST database
  xmlns CDATA #FIXED ''
  name NMTOKEN #REQUIRED>

<!ELEMENT pma:database (pma:table)+>
<!ATTLIST pma:database
  xmlns:pma CDATA #FIXED 'http://www.phpmyadmin.net/some_doc_url/'
  charset NMTOKEN #REQUIRED
  collation NMTOKEN #REQUIRED
  name NMTOKEN #REQUIRED>

<!ELEMENT table (column)+>
<!ATTLIST table
  xmlns CDATA #FIXED ''
  name NMTOKEN #REQUIRED>

<!ELEMENT pma:table (#PCDATA)>
<!ATTLIST pma:table
  xmlns:pma CDATA #FIXED 'http://www.phpmyadmin.net/some_doc_url/'
  name NMTOKEN #REQUIRED>

<!ELEMENT column (#PCDATA)>
<!ATTLIST column
  xmlns CDATA #FIXED ''
  name NMTOKEN #REQUIRED>
