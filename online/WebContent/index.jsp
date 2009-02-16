<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<script language="JavaScript">
<!--
// Script to check if user has entered values into fields 
function formCheck(formobj) {
	// Enter name of mandatory fields
	var fieldRequired = Array("customerUsername", "customerPassword");
	// Enter field description to appear in the dialog box
	var fieldDescription = Array("Username", "Password");
	// dialog message
	var alertMsg = "Please complete the following fields:\n";
	
	var l_Msg = alertMsg.length;
	
	for (var i = 0; i < fieldRequired.length; i++) {
		var obj = formobj.elements[fieldRequired[i]];
		if (obj){
			switch(obj.type){
			case "select-one":
				if (obj.selectedIndex == -1 || obj.options[obj.selectedIndex].text == "") {
					alertMsg += " - " + fieldDescription[i] + "\n";
				}
				break;
			case "select-multiple":
				if (obj.selectedIndex == -1){
					alertMsg += " - " + fieldDescription[i] + "\n";
				}
				break;
			case "text":
			case "textarea":
				if (obj.value == "" || obj.value == null) {
					alertMsg += " - " + fieldDescription[i] + "\n";
				}
				break;
			default:
			}
			if (obj.type == undefined) {
				var blnchecked = false;
				for (var j = 0; j < obj.length; j++){
					if (obj[j].checked) {
						blnchecked = true;
					}
				}
				if (!blnchecked){
					alertMsg += " - " + fieldDescription[i] + "\n";
				}
			}
		}
	}

	if (alertMsg.length == l_Msg) {
		return true;
	}else{
		alert(alertMsg);
		return false;
	}
}
// -->
</script>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Sign in</h3>
	</li>
	<li>&nbsp;</li>
	<li><a href="tbd.jsp">FAQ</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
</ul>

<div class="main">
<div class="content">
<h1>Welcome to HappyBank <i>Online</i></h1>

<form action="ValidateLogin" method="post"
	onsubmit="return formCheck(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td width="100%">
			<p>You will need a username and password to continue using our offerings
			and services. If you have not yet registered with HappyBank, we
			apologize for the inconvenience and ask that you please <a
				href="tbd.jsp">Register Now</a>.</p>
			</td>
		</tr>
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td width="10"><span class="ask">*</span></td>
						<td width="70"><b><label for="customerUsername">Username:</label></b></td>
						<td width="350"><input maxlength="80" name="customerUsername"
							class="iform" id="customerUsername" type="text" /> <br />
						<br />
						</td>
					</tr>
					<tr valign="top">
						<td><span class="ask">*</span></td>
						<td><b><label for="newpassword">Password:</label></b></td>
						<td><input maxlength="31" name="customerPassword"
							class="iform" id="customerPassword" type="password" /> <br />
						<br />
						</td>
					</tr>
                    <tr valign="top">
                        <td></td>
                        <td></td>
                        <%
                            String message = (String)request.getAttribute("message");
                            if (message != null) {
                        %>
                                <td class="error"><%=message%><br/></td>
                        <%        
                            }
                        %>
                    </tr>                    
					<tr valign="top">
						<td></td>
                        <td></td>
						<td>
                            <p><input name="remember" type="checkbox" /> Remember my
						      Username.</p>
                        	<br />
						</td>
					</tr>
					<tr>
						<td colspan="3"><input alt="Submit" type="submit"
							value="Submit" /></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
</form>

</div>
</div>
<br class="clr">
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
