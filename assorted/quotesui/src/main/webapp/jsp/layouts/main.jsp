<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<html:html>
<head>
<meta charset="utf-8">
<title><bean:message key="global.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--
	<html:base/>
-->

<style type="text/css">

      /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }

      /* Wrapper for page content to push down footer */
      #wrap {
        min-height: 100%;
        height: auto !important;
        height: 100%;
        /* Negative indent footer by it's height */
        margin: 0 auto -60px;
      }

      /* Set the fixed height of the footer here */
      #push,
      #footer {
        height: 60px;
      }
      #footer {
        background-color: #f5f5f5;
      }

      /* Lastly, apply responsive CSS fixes as necessary */
      @media (max-width: 767px) {
        #footer {
          margin-left: -20px;
          margin-right: -20px;
          padding-left: 20px;
          padding-right: 20px;
        }
      }



      /* Custom page CSS
      -------------------------------------------------- */
      /* Not required for template or sticky footer method. */

      .container {
        width: auto;
        max-width: 680px;
      }
      .container .credit {
        margin: 20px 0;
      }

    </style>

	<!--  
	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/global.css'/>" />
	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/bootstrap.css'/>" />
	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/bootstrap-responsive.css'/>" />
	-->
	<link rel="stylesheet" type="text/css" href="css/global.css" />
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
	
</head>
<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<tiles:insert attribute="header" />
			</div>
			<tiles:insert attribute="center" />
		</div>
		<div id="push"></div>
		
	</div>
	
	<div id="footer">
	    	<div class="container">
				<tiles:insert attribute="footer" />
			</div>	
	</div>
	 <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    <!--
    <script src="<html:rewrite page='/js/jquery.js'/>"></script>
    <script src="<html:rewrite page='/js/bootstrap-transition.js'/>"></script>
    -->
    <script src="js/jquery.js"> </script>
    <script src="js/bootstrap.js"></script>
    
</body>

</html:html>