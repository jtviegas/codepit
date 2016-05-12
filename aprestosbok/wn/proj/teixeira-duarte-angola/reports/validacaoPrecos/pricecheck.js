function checkAllPrices()
{
	document.forms[0].direction.value="check";
	document.forms[0].action=getLocation();
	document.forms[0].submit();
}

function ignoreAllPrices()
{
	document.forms[0].direction.value="ignore";
	document.forms[0].action=getLocation();
	document.forms[0].submit();
}

function selectAllCheck()
{
	allpricechecked();
}

function selectAllIgnore()
{
	allpriceignored();
}

function selectAllShelfTitle()
{
	var checkboxes = getCheckBoxes("printlabel_\\d+$");

	if(document.forms[0].allshelftitle.checked)
	{
		for(i in checkboxes)
		{
			checkboxes[i].checked=true;
			checkboxes[i].disabled=true;
		}
	}
	else
	{
		for(i in checkboxes)
		{	
			checkboxes[i].checked=false;
			checkboxes[i].disabled=false;
		}
	}
	
}


function getCheckBoxes(regexptoken)
{
	result = new Array();

	var regexp = new RegExp(regexptoken);
	
	for(var i=0; i<document.forms[0].elements.length;i++)
  	{
 		if(document.forms[0].elements[i].type=="checkbox")
		{
			if(document.forms[0].elements[i].name.match(regexp))
			{
				result.push(document.forms[0].elements[i]);
			}
		}
	}

	return result;
}

function getLocation()
{
	var currentlocation = window.location.href;
	var index = currentlocation.indexOf("?");
	return currentlocation.slice(0,index);
}

function enableDisableUncheck_allpriceignore(enable)
{
   document.forms[0].allpriceignore.disabled=!enable;
   if(!enable)
   {
     document.forms[0].allpriceignore.checked=false;
   }
}


function enableDisableUncheck_ignored_x(enable)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/ignored_\d+$/))
    {
      document.forms[0].elements[i].disabled=!enable;

      if(!enable)
      {
        document.forms[0].elements[i].checked=false;
      }
    }
  }
}

function UncheckCheckEnable_checked_x(check)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/checked_\d+$/))
    {
      document.forms[0].elements[i].checked=check;

      if(check)
      {
        document.forms[0].elements[i].disabled=false;
      }
    }
  }
}

function allpricechecked()
{
 enableDisableUncheck_allpriceignore(!document.forms[0].allpricecheck.checked);
 enableDisableUncheck_ignored_x(!document.forms[0].allpricecheck.checked);
 UncheckCheckEnable_checked_x(document.forms[0].allpricecheck.checked);
}

function allpriceignored()
{
  enabledisableuncheckpricecheckedall(!document.forms[0].allpriceignore.checked);
  enabledisableuncheckallpricechecked(!document.forms[0].allpriceignore.checked);
  uncheckcheckenableallpriceignored(document.forms[0].allpriceignore.checked);
}



function enabledisableuncheckpricecheckedall(enable){
   document.forms[0].allpricecheck.disabled=!enable;

   if(!enable)
   {
     document.forms[0].allpricecheck.checked=false;
   }
}


function pricechecked(objname)
{
 if(ischecked(objname))
 {
  disableuncheck(objname.replace(/checked/,"ignored"));
 }
 else
 {
  enable(objname.replace(/checked/,"ignored"));
  if(document.forms[0].pricecheckedall.checked)
  {
   document.forms[0].pricecheckedall.checked=false;
   document.forms[0].priceignoredall.disabled=false;
  }
 }
}

function priceignored(objname)
{
 if(ischecked(objname))
 {
  disableuncheck(objname.replace(/ignored/,"checked"));
 }
 else
 {
  enable(objname.replace(/ignored/,"checked"));
  if(document.forms[0].priceignoredall.checked)
  {
   document.forms[0].priceignoredall.checked=false;
   document.forms[0].pricecheckedall.disabled=false;
  }
 }
}

function ischecked(name)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(name))
    {
      if(document.forms[0].elements[i].type=="checkbox")
      {
        return document.forms[0].elements[i].checked;
      }
    }
  }
}

function disableuncheck(name)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(name))
    {
      document.forms[0].elements[i].checked=false;
      document.forms[0].elements[i].disabled=true;
    }
  }
}

function enable(name)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(name))
    {
      document.forms[0].elements[i].disabled=false;
    }
  }
}
*/

function disableuncheckallpricechecked()
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/checked_\d+$/))
    {
      disableuncheck(document.forms[0].elements[i].name);
    }
  }
}



function enableallpricechecked()
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/checked_\d+$/))
    {
      enable(document.forms[0].elements[i].name);
    }
  }
}



function disableuncheckallpriceignored()
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/ignored_\d+$/))
    {
      disableuncheck(document.forms[0].elements[i].name);
    }
  }
}

 
 

function enableallpriceignored()
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/ignored_\d+$/))
    {
      enable(document.forms[0].elements[i].name);
    }
  }
}






function enabledisableuncheckallpricechecked(enable)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/checked_\d+$/))
    {
      document.forms[0].elements[i].disabled=!enable;

      if(!enable)
      {
        document.forms[0].elements[i].checked=false;
      }
    }
  }
}




function uncheckcheckenableallpriceignored(check)
{
  for(var i=0; i<document.forms[0].elements.length;i++)
  {
    if(document.forms[0].elements[i].name.match(/ignored_\d+$/))
    {
      document.forms[0].elements[i].checked=check;

      if(check)
      {
        document.forms[0].elements[i].disabled=false;
      }
    }
  }
}

