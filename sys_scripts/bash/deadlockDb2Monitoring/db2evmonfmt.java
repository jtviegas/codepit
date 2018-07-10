//*************************************************************************
// (c) Copyright IBM Corp. 2008 All rights reserved.
//
// The following sample of source code ("Sample") is owned by International
// Business Machines Corporation or one of its subsidiaries ("IBM") and is
// copyrighted and licensed, not sold. You may use, copy, modify, and
// distribute the Sample in any form without payment to IBM, for the purpose of
// assisting you in the development of your applications.
//
// The Sample code is provided to you on an "AS IS" basis, without warranty of
// any kind. IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES, EITHER EXPRESS OR
// IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. Some jurisdictions do
// not allow for the exclusion or limitation of implied warranties, so the above
// limitations or exclusions may not apply to you. IBM shall not be liable for
// any damages you suffer as a result of using, copying, modifying or
// distributing the Sample, even if IBM has been advised of the possibility of
// such damages.
//*************************************************************************
//
// SOURCE FILE NAME: db2evmonfmt.java
//
// SAMPLE: Extract the XML report from the event monitor
//
// Steps to run the sample with command line window:
//
//   1) Copy the following file to your working directory and ensure that
//      directory has write permission:
//
//      <install_path>/samples/java/jdbc/db2evmonfmt.java
//      <install_path>/samples/java/jdbc/*.xsl
//
//   2) Modify the CLASSPATH to include:
//
//         <install_path>/sqllib/java/db2java.zip
//         <install_path>/sqllib/java/db2jcc.jar
//         <install_path>/sqllib/java/db2jcc_license_cu.jar
//         <install_path>/sqllib/java/<jdkDirName>/lib
//         <install_path>/sqllib/lib
//         <install_path>/sqllib/function
//         <install_path>/sqllib/java/sqlj.zip
//      where <jdkDirName> is the name of the
//      jdk directory under <install_path>/sqllib/java.
//
//   3) Modify the PATH to include <install_path>/sqllib/java/<jdkDirName>/bin,
//      <install_path>/sqllib/lib.
//
//   4) Modify the LD_LIBRARY_PATH to include <install_path>/sqllib/lib
//
//   5) Compile the source file with: javac db2evmonfmt.java
//
//   6) Run the program: java db2evmonfmt -h
//      for command line options.
//
// USAGE:
//
//   db2evmonfmt -d <dbname> -ue <uetable> [ -u userid -p passwd ]
//               < -fxml | -ftext [-ss stylesheet] >
//               [ -id <eventid>     ]
//               [ -type <eventype>  ]
//               [ -hours <hours>    ]
//               [ -w <workloadname> ]
//               [ -s <serviceclass> ]
//               [ -a <applname>     ]
//
//  OR
//
//  db2evmonfmt -f xmlfile < -fxml | -ftext [-ss stylesheet] >
//
//   where:
//
//        dbname     : Database name
//        uetable    : Name of the unformatted event table
//        userid     : User ID
//        passwd     : Password
//
//        xmlfile    : Input XML file to format
//
//        fxml       : Pretty print the XML document to stdout
//        ftext      : Format XML document to text using the default XSLT
//                     stylesheet, pipe to stdout
//
//        stylesheet : Use the following XSLT stylesheet to format to format
//                     the XML documents
//
//        id         : Display all events matching <eventid>
//        type       : Display all events matching event type <eventtype>
//        hours      : Display all events that have occurred in the last
//                     <hours> hours
//        w          : Display all events where the event is part of
//                     workload <workloadname>
//
//                     For the Lock Event monitor, this will display all events
//                     where the lock requestor is part of <workloadname>
//
//        s          : Display all events where the event is part of
//                     service class <serviceclass>
//
//                     For the Lock Event monitor, this will display all events
//                     where the lock requestor is part of <serviceclass>
//
//        a          : Display all events where the event is part of
//                     application name <applname>
//
//                     For the Lock Event monitor, this will display all events
//                     where the lock requestor is part of <applname>
//
//  Examples:
//
//  1. Get all events that are part of workload PAYROLL in the last 32 hours from
//     UE table PKG in database SAMPLE.
//
//     java db2evmonfmt -d SAMPLE -ue PKG -ftext -hours 32 -w PAYROLL
//
//  2. Get all events of type LOCKTIMEOUT that have occurred in the last 48 
//     from UE table LOCK in database SAMPLE.
//
//     java db2evmonfmt -d SAMPLE -ue LOCK -ftext -hours 48 -type LOCKTIMEOUT
//
//  3. Format the event contained in the file LOCK.XML using stylesheet
//     MYREPORT.xsl
//
//     java db2evmonfmt -f lock.xml -ftext -ss myreport.xsl
//
//*************************************************************************
//
// For more information on the sample programs, see the README file.
//
// For information on developing JDBC applications, see the Application
// Development Guide.
//
// For information on using SQL statements, see the SQL Reference.
//
// For the latest information on programming, compiling, and running DB2
// applications, visit the DB2 application development website at
//     http://www.software.ibm.com/data/db2/udb/ad
//**************************************************************************/

import java.lang.*;                   // for String class
import java.io.*;                     // for ...Stream classes
import java.io.Writer;                     // for ...Stream classes
import COM.ibm.db2.app.StoredProc;    // Stored Proc classes
import java.sql.*;                    // for JDBC classes
import com.ibm.db2.jcc.*;             // for XML class
import java.util.*;                   // Utility classes
import com.ibm.db2.jcc.DB2Xml;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.*;
import javax.xml.validation.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;

/*!
 *  /brief Class db2evmonfmt
 *
 *  Class extracts the XML records from an Event Monitor Raw Table and
 *  formats the XML report to stdout.
 */
public class db2evmonfmt
{
   static String dbName = "";
   static String userid = "";
   static String passwd = "";
   static String uetable  = "";

   static String styleSheet = "";
   static String xmlInputFilename = "";
   static String xmlSchemaInputFilename = "";

   static Boolean bPrettyPrint = false;
   static Boolean bFormatText = false;

   static String workloadName = "";
   static String serviceClass = "";
   static String applName     = "";

   static SAXParserFactory fFactory;
   static SAXParser parser;

   static int eventID = -1;
   static String eventType = "";
   static int hours = 0;

   public static final int EVENT_ID   = 0;
   public static final int EVENT_TYPE = 1;
   public static final int EVENT_WORKLOAD = 2;
   public static final int EVENT_SRVCLASS = 3;
   public static final int EVENT_APPLNAME = 4;

   static String lockEvent = "<db2_lock_event";
   static String uowEvent = "<db2_uow_event";

   /*!
    *  /brief Main Driver
    *
    *  Main function driver that formats the XML report to stdout.
    */
   public static void main(String argv[])
   {
      try
      {
         //--------------------------------------------------------
         // Parse the Command Line Arguements
         //--------------------------------------------------------
         ParseArguments(argv);
         Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();

         //--------------------------------------------------------
         // Retrieve the XML documents from the database
         //--------------------------------------------------------
         if ((dbName.length() != 0) && (uetable.length() != 0))
         {
            RetrieveXMLDocumentsFromEvmon();
         }
         //--------------------------------------------------------
         // Format the input file document
         //--------------------------------------------------------
         else if (xmlInputFilename.length() != 0)
         {
            FormatInputXMLFile();
         }
      }
      catch (Exception f)
      {
         System.out.println(f.getMessage());
         System.out.println();
      }

      return ;
   } // end main


   /*!
    *  /brief RetrieveXMLDocumentsFromEvmon
    *
    *  Function calls the EVMON_FORMAT_UE_TO_XML table function
    */
   public static void RetrieveXMLDocumentsFromEvmon() throws Exception
   {
     Connection                 con = null;
     Blob                   xmlRow  = null;
     PreparedStatement         stmt = null;
     CallableStatement     callstmt = null;
     Boolean        bFirstCondition = true;
     int               paramIndex[] = { 0, 0, 0, 0, 0 };
     int                  paramCount = 0;
     byte[] docHeader = new byte[101];
     String reportType = null;

     try
     {

       //----------------------------------------------------------
       // Establish the connection to the database
       //----------------------------------------------------------
       con = DriverManager.getConnection(dbName, userid, passwd);
       con.setAutoCommit(false);

       //----------------------------------------------------------
       // Let us query the UE table and retrieve the events.
       //
       // We will execute table function: EVMON_FORMAT_UE_TO_XML to
       // retrieve the XML data. The table function takes a subquery
       // as input. The subquery will is a simple select stmt that
       // will retrieve all the rows from the UE table.
       //
       //  EVMON_FORMAT_UE_TO_XML( options
       //                          FOR EACH ROW OF (SELECT * from <UE Table>))
       //
       //----------------------------------------------------------
       String queryUETable =
           "SELECT evmon.xmlreport FROM TABLE ( " +
           "EVMON_FORMAT_UE_TO_XML( 'LOG_TO_FILE'," +
                                    "FOR EACH ROW OF ( " +
                                    "SELECT * FROM " + uetable.trim() + " ";

       //----------------------------------------------------------
       // Setup the the WHERE clause
       //----------------------------------------------------------
       if (   (hours != 0)
           || (eventType.length() != 0)
           || (eventID > 0)
           || (workloadName.length() > 0)
           || (serviceClass.length() > 0)
           || (applName.length() > 0)
          )
       {
          queryUETable += " WHERE";

          if (eventID > 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable += " EVENT_ID = ?";
             paramIndex[EVENT_ID] = (++paramCount);
          }

          if (hours != 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable +=
               " EVENT_TIMESTAMP >= CURRENT_TIMESTAMP - " + hours + " hours";
          }

          if (eventType.length() != 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable += " EVENT_TYPE = ?";
             paramIndex[EVENT_TYPE] = (++paramCount);
          }

          if (workloadName.length() != 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable += " WORKLOAD_NAME = ?";
             paramIndex[EVENT_WORKLOAD] = (++paramCount);
          }

          if (serviceClass.length() != 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable += " SERVICE_SUBCLASS_NAME = ?";
             paramIndex[EVENT_SRVCLASS] = (++paramCount);
          }

          if (applName.length() != 0)
          {
             if (bFirstCondition == false)
             {
                queryUETable += " AND";
             }
             bFirstCondition = false;

             queryUETable += " APPL_NAME = ?";
             paramIndex[EVENT_APPLNAME] = (++paramCount);
          }
       }
       queryUETable += " ORDER BY EVENT_ID, " + 
                                 "EVENT_TIMESTAMP, " +
                                 "EVENT_TYPE, " +
                                 "MEMBER ))) AS evmon";

       //----------------------------------------------------------
       // Setup the Input and Output parameters
       //----------------------------------------------------------
       stmt = con.prepareStatement(queryUETable);

       if (paramIndex[EVENT_ID] > 0)
       {
          stmt.setInt(paramIndex[EVENT_ID]++, eventID);
       }

       if (paramIndex[EVENT_TYPE] > 0)
       {
          stmt.setString(paramIndex[EVENT_TYPE]++, eventType.toUpperCase());
       }

       if (paramIndex[EVENT_WORKLOAD] > 0)
       {
          stmt.setString(paramIndex[EVENT_WORKLOAD]++, workloadName.toUpperCase());
       }

       if (paramIndex[EVENT_SRVCLASS] > 0)
       {
          stmt.setString(paramIndex[EVENT_SRVCLASS]++, serviceClass.toUpperCase());
       }

       if (paramIndex[EVENT_APPLNAME] > 0)
       {
          stmt.setString(paramIndex[EVENT_APPLNAME]++, applName);
       }

       //----------------------------------------------------------
       // Execute the SQL statement
       //----------------------------------------------------------
       System.out.println(queryUETable);
       System.out.println(" ");

       ResultSet rs = stmt.executeQuery();

       //----------------------------------------------------------
       // Retrieve the data from the result set
       //----------------------------------------------------------
       if (rs.next())
       {
         do
         {
            xmlRow = rs.getBlob("XMLREPORT");

            //----------------------------------------------------------
            // Pretty Print the XML document to stdout
            //----------------------------------------------------------
            if (bPrettyPrint == true)
            {
               System.out.println("Row Number: " + rs.getRow());
               System.out.println(" ");
               PrettyPrintXML(xmlRow.getBinaryStream());
               System.out.println(" ");
            }

            //----------------------------------------------------------
            // Format the XML document to stdout based on the XML
            // stylesheet
            //----------------------------------------------------------
            else if (bFormatText == true)
            {
               docHeader = xmlRow.getBytes(1, 100);
               reportType = new String(docHeader);

               //----------------------------------------------------------
               // The XML report is from the Lock Event Monitor.
               // Call the XML to handle the db2LockReport
               //----------------------------------------------------------
               if (reportType.indexOf(lockEvent) != -1)
               {
                  LockReportFormatter(xmlRow.getBinaryStream());
               }
               else if (reportType.indexOf(uowEvent) != -1)
               {
                  UOWReportFormatter(xmlRow.getBinaryStream());
               }
               else
               {
                  System.out.println(" ");
                  System.out.println("Tool does not support the XML document type");
                  System.out.println(reportType);
                  System.out.println(" ");
               }

            }
         }
         while (rs.next());
       }
       else
       {
         System.out.println("Result set is empty");
       }
       System.out.println(" ");

       //----------------------------------------------------------
       // Close the statement and result set
       //----------------------------------------------------------
       rs.close();
       stmt.close();
     }
     catch (SQLException e)
     {
        System.out.println(e.getMessage());
        System.out.println();

        if (con != null)
        {
          try
          {
            con.rollback();
          }
          catch (Exception error )
          {
          }
        }
        else
        {
           System.out.println("Connection to db " + dbName + " can't be established.");
           System.err.println(e) ;
        }

        System.exit(1);
     }
     catch (Exception e)
     {
        System.out.println(e.getMessage());
        System.out.println();
        System.exit(1);
     }
   }

   /*!
    *  /brief FormatInputXMLFile
    *
    *  Formats the input XML file
    */
   public static void FormatInputXMLFile() throws Exception
   {
      File xmlFile = new File(xmlInputFilename);

      if (xmlFile.exists())
      {
         InputStream xmlFileStream = new FileInputStream(xmlFile);

         //----------------------------------------------------------
         // Pretty Print the XML document to stdout
         //----------------------------------------------------------
         if (bPrettyPrint == true)
         {
            PrettyPrintXML(xmlFileStream);
         }
         //----------------------------------------------------------
         // Format the XML document to stdout based on the XML
         // stylesheet
         //----------------------------------------------------------
         else if (bFormatText == true)
         {
            char [] docHeader = new char[101];
            InputStreamReader xmlHeader = new InputStreamReader(
                                            new FileInputStream(xmlFile));

            xmlHeader.read(docHeader, 1, 100);
            String reportType = new String(docHeader);

            //----------------------------------------------------------
            // The XML report is from the Lock Event Monitor.
            // Call the XML to handle the db2LockReport
            //----------------------------------------------------------
            if (reportType.indexOf(lockEvent) != -1)
            {
               LockReportFormatter(xmlFileStream);
            }
            else if (reportType.indexOf(uowEvent) != -1)
            {
               UOWReportFormatter(xmlFileStream);
            }
            else
            {
               System.out.println(" ");
               System.out.println("Tool does not support XML document type");
               System.out.println(" ");
            }
         }
      }
      else
      {
         System.out.println(" ");
         System.out.println("Input XML file '"+xmlInputFilename+"' does not exist.");
         System.out.println(" ");
      }
   }

   /*!
    *  /brief PrettyPrintXML
    *
    *  Function will pretty print the XML document using the XML
    *  stylesheet.
    */
   public static void PrettyPrintXML(InputStream xmlSource)
   {
     try
     {
        TransformerFactory tFactory = TransformerFactory.newInstance();

        String xmlStyleSheet = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xmlStyleSheet += " <xsl:stylesheet version=\"1.0\" ";
        xmlStyleSheet += "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" ";
        xmlStyleSheet += "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" ";
        xmlStyleSheet += "xmlns:lm=\"http://www.ibm.com/xmlns/prod/db2/mon\" ";
        xmlStyleSheet += "xmlns:xalan=\"http://xml.apache.org/xslt\" >";
        xmlStyleSheet += "<xsl:output method=\"xml\" version=\"1.0\" ";
        xmlStyleSheet += "encoding=\"UTF-8\" indent=\"yes\" xalan:indent-amount=\"2\" />";
        xmlStyleSheet += "<xsl:strip-space elements=\"*\" />";
        xmlStyleSheet += "<xsl:template match=\"*\" >";
        xmlStyleSheet += "<xsl:copy>";
        xmlStyleSheet += "<xsl:copy-of select=\"@*\" />";
        xmlStyleSheet += "<xsl:apply-templates />";
        xmlStyleSheet += "</xsl:copy>";
        xmlStyleSheet += "</xsl:template>";
        xmlStyleSheet += "</xsl:stylesheet>";

        Source xsl = new StreamSource(
                         new ByteArrayInputStream(xmlStyleSheet.getBytes()));
        Transformer xmlTrans = tFactory.newTransformer(xsl);

        Source xmlData = new StreamSource(xmlSource);
        Result output  = new StreamResult(System.out);

        xmlTrans.transform(xmlData, output);

     }
     catch (TransformerConfigurationException s)
     {
        System.out.println(s.getMessage());
        System.out.println();
        System.exit(1);
     }
     catch (TransformerException f)
     {
        System.out.println(f.getMessage());
        System.out.println();
        System.exit(1);
     }
   }

   /*!
    *  /brief LockReportFormatter
    *
    *  Function will format the Lock Report based on the XML stylesheet
    */
   public static void LockReportFormatter(InputStream xmlSource)
   {
     try
     {
        TransformerFactory tFactory = TransformerFactory.newInstance();

        //----------------------------------------------------------
        // Load the XML stylesheet from the current directory
        //----------------------------------------------------------
        File stylesheet = null;
        if (styleSheet.length() == 0)
        {
           stylesheet = new File("DB2EvmonLocking.xsl");
        }
        else
        {
           stylesheet = new File(styleSheet);
        }

        Source xsl  = new StreamSource(stylesheet);
        Transformer xmlTrans = tFactory.newTransformer(xsl);

        Source xmlData = new StreamSource(xmlSource);
        Result output  = new StreamResult(System.out);

        xmlTrans.transform(xmlData, output);

     }
     catch (TransformerConfigurationException s)
     {
        System.out.println(s.getMessage());
        System.out.println();
        System.exit(1);
     }
     catch (TransformerException f)
     {
        System.out.println(f.getMessage());
        System.out.println();
        System.exit(1);
     }
   }

   /*!
    *  /brief UOWReportFormatter
    *
    *  Function will format the UOW Report based on the XML stylesheet
    */
   public static void UOWReportFormatter(InputStream xmlSource)
   {
     try
     {
        TransformerFactory tFactory = TransformerFactory.newInstance();

        //----------------------------------------------------------
        // Load the XML stylesheet from the current directory
        //----------------------------------------------------------
        File stylesheet = null;
        if (styleSheet.length() == 0)
        {
           stylesheet = new File("DB2EvmonUOW.xsl");
        }
        else
        {
           stylesheet = new File(styleSheet);
        }

        Source xsl  = new StreamSource(stylesheet);
        Transformer xmlTrans = tFactory.newTransformer(xsl);

        Source xmlData = new StreamSource(xmlSource);
        Result output  = new StreamResult(System.out);

        xmlTrans.transform(xmlData, output);

     }
     catch (TransformerConfigurationException s)
     {
        System.out.println(s.getMessage());
        System.out.println();
        System.exit(1);
     }
     catch (TransformerException f)
     {
        System.out.println(f.getMessage());
        System.out.println();
        System.exit(1);
     }
   }

   /*!
    *  /brief ParseArguments
    *
    *  Parse the command line arguements.
    *
    */
   public static void ParseArguments(String argv[]) throws Exception
   {
      int   count = 0;
      char  value;
      char  optionChar;
      Boolean bDisplayHelp = false;
      Exception usage = new Exception(
      "USAGE:                                                                    \n" +
      "                                                                          \n" +
      "  db2evmonfmt -d <dbname> -ue <uetable> [ -u userid -p passwd ]              \n" +
      "              < -fxml | -ftext [-ss stylesheet] >                         \n" +
      "              [ -id <eventid>     ]                                       \n" +
      "              [ -type <eventype>  ]                                       \n" +
      "              [ -hours <hours>    ]                                       \n" +
      "              [ -w <workloadname> ]                                       \n" +
      "              [ -s <serviceclass> ]                                       \n" +
      "              [ -a <applname>     ]                                       \n" +
      "                                                                          \n" +
      " OR                                                                       \n" +
      "                                                                          \n" +
      " db2evmonfmt -f xmlfile < -fxml | -ftext [-ss stylesheet] >               \n" +
      "                                                                          \n" +
      "  where:                                                                  \n" +
      "                                                                          \n" +
      "       dbname     : Database name                                         \n" +
      "       uetable    : Name of the unformatted event monitor                 \n" +
      "       userid     : User ID                                               \n" +
      "       passwd     : Password                                              \n" +
      "                                                                          \n" +
      "       xmlfile    : Input XML file to format                              \n" +
      "                                                                          \n" +
      "       fxml       : Pretty print the XML document to stdout               \n" +
      "       ftext      : Format XML document to text using the default XSLT    \n" +
      "                    stylesheet, pipe to stdout                            \n" +
      "                                                                          \n" +
      "       stylesheet : Use the following XSLT stylesheet to format to format \n" +
      "                    the XML documents                                     \n" +
      "                                                                          \n" +
      "       id         : Display all events matching <eventid>                 \n" +
      "       type       : Display all events matching event type <eventtype>    \n" +
      "       hours      : Display all events that have occurred in the last     \n" +
      "                    <hours> hours                                         \n" +
      "       w          : Display all events where the event is part of         \n" +
      "                    workload <workloadname>                               \n" +
      "                                                                          \n" +
      "                    For the Lock Event monitor, this will display all     \n" +
      "                    events where the lock requestor is part of            \n" +
      "                    <workloadname>                                        \n" +
      "                                                                          \n" +
      "       s          : Display all events where the event is part of         \n" +
      "                    service class <serviceclass>                          \n" +
      "                                                                          \n" +
      "                    For the Lock Event monitor, this will display all     \n" +
      "                    events where the lock requestor is part of            \n" +
      "                    <serviceclass>                                        \n" +
      "                                                                          \n" +
      "       a          : Display all events where the event is part of         \n" +
      "                    application name <applname>                           \n" +
      "                                                                          \n" +
      "                    For the Lock Event monitor, this will display all     \n" +
      "                    events where the lock requestor is part of <applname> \n" +
      "                                                                          \n" +
      " Examples:                                                                \n" +
      "                                                                          \n" +
      " 1. Get all events that are part of workload PAYROLL in the last 32 hours \n" +
      "    from UE table PKG in database SAMPLE.              \n" +
      "                                                                          \n" +
      "    java db2evmonfmt -d SAMPLE -ue PKG -ftext -hours 32 -w PAYROLL         \n" +
      "                                                                          \n" +
      " 2. Get all events of type LOCKTIMEOUT that have occurred in the last 24  \n" +
      "    hours from UE table LOCK in database SAMPLE.                     \n" +
      "                                                                          \n" +
      "    java db2evmonfmt -d SAMPLE -ue LOCK -ftext -hours 24 -type LOCKTIMEOUT \n" +
      "                                                                          \n" +
      " 3. Format the event contained in the file LOCK.XML using stylesheet      \n" +
      "    MYREPORT.xsl                                                          \n" +
      "                                                                          \n" +
      "    java db2evmonfmt -f lock.xml -ftext -ss myreport.xsl                  \n" +
      "\n");

      if( argv.length < 2 ||
         ( argv.length == 1 &&
           ( argv[0].equals( "?" )               ||
             argv[0].equals( "-?" )              ||
             argv[0].equals( "/?" )              ||
             argv[0].equalsIgnoreCase( "-h" )    ||
             argv[0].equalsIgnoreCase( "/h" )    ||
             argv[0].equalsIgnoreCase( "-help" ) ||
             argv[0].equalsIgnoreCase( "/help" ) ) ) )
      {
        throw usage;
      }

      while ((count + 1 <= argv.length) && (bDisplayHelp == false))
      {
         if (argv[count].equals("-d"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               dbName = "jdbc:db2:" + argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-u"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               userid = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-p"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               passwd = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-ue"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               uetable = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-id"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               eventID = Integer.parseInt(argv[count+1]);
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-type"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               eventType = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-hours"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               hours = Integer.parseInt(argv[count+1]);
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-fxml"))
         {
            bPrettyPrint = true;
            count++;
            continue;
         }
         else if (argv[count].equals("-ftext"))
         {
            bFormatText = true;
            count++;
            continue;
         }
         else if (argv[count].equals("-ss"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               styleSheet = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-f"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               xmlInputFilename = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-w"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               workloadName = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-s"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               serviceClass = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else if (argv[count].equals("-a"))
         {
            if (    (count + 1 < argv.length)
                 && (!argv[count+1].startsWith("-"))
               )
            {
               applName = argv[count+1];
            }
            else
            {
               bDisplayHelp = true;
            }
         }
         else
         {
            bDisplayHelp = true;
         }
         count = count + 2;
      }

      // Display help if:
      //
      // 1. dbname or evmon name is set to zero and no format options
      //    are specified
      // 2. Or input XML file is not specified
      if (   bDisplayHelp
          || (    ((dbName.length() == 0) || (uetable.length() == 0))
               && (xmlInputFilename.length() == 0)
             )
          || (   (bFormatText == false)
              && (bPrettyPrint == false)
             )
         )
      {
         throw usage;
      }
   }

}

/*!
 *  /brief Class SimpleErrorHandler
 *
 *  SimpleErrorHandler handles all SAX parsing errors.
 */
class SimpleErrorHandler implements ErrorHandler
{
   public void warning(SAXParseException e) throws SAXParseException {
       System.out.format("Warning (%d:%d): %s\n",
                          e.getLineNumber(),
                          e.getColumnNumber(),
                          e.getMessage());
       throw(e);
   }

   public void error(SAXParseException e) throws SAXParseException {
       System.out.format("Error (%d:%d): %s\n",
                          e.getLineNumber(),
                          e.getColumnNumber(),
                          e.getMessage());
       throw(e);
   }

   public void fatalError(SAXParseException e) throws SAXParseException {
       System.out.format("Fatal Error (%d:%d): %s\n",
                          e.getLineNumber(),
                          e.getColumnNumber(),
                          e.getMessage());
       throw(e);
   }
}

