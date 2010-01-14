<!-- 
  Copyright 2005-2009 Kevin A. Lee Licensed under the Apache License, Version
  2.0 (the "License"); you may not use this file except in compliance with the
  License. You may obtain a copy of the License at
  http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
  or agreed to in writing, software distributed under the License is
  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!--  include header -->
<%@include file="theme/header.jsp"%>

<script language="JavaScript">
<!--
window.addEvent('domready', function() {
    
    $('loginForm').addEvent('submit', function(e) {
        // prevents the default submit event from loading a new page
        new Event(e).stop();
        
        // where we will place the response
        var responseDiv = $('loginForm').getElement('#response');
        
        // where we will redirect to on login
        var redirectURL = "listAccounts.jsp"
        
        // remove error style from fields
        this.getElements('.error').each(function(input) {
            if (input.hasClass('error')) { 
                input.removeClass('error').morph({ 'border-color': '#ccc', 'background-color': '#fff' }); 
            }
        });
        
        // validate fields  
        if ($('user').get('value') == "") {
            responseDiv.set('html', 
                "<span class='error'>A <b>username</b> is required.</span>");
            responseDiv.setStyles({ 'opacity': '0', 'display': 'block' });
            responseDiv.morph({ 'opacity': '1' });
            $("user").addClass('error').morph({ 'border-color': '#f00', 'background-color': '#ffebe8' });
            $("user").focus();
        } else if ($('pass').get('value') == "") {
            responseDiv.set('html', 
                "<span class='error'>A <b>password</b> is required.</span>");
            responseDiv.setStyles({ 'opacity': '0', 'display': 'block' });
            responseDiv.morph({ 'opacity': '1' });
            $("pass").addClass('error').morph({ 'border-color': '#f00', 'background-color': '#ffebe8' });
            $("pass").focus();
        } else {
        
            // show the waiter when pressing the submit button...
            $('waiting').setStyle('visibility', 'visible');

            // disable the submit button while processing...
            $('submit').set('disabled', true);

            // set the options of the form's Request handler.
            this.set('send', { onComplete: function(response) {
                $('waiting').setStyle('visibility', 'hidden');

                // decode the JSON response
                var status = JSON.decode(response);
                
                // act on the response
                if (status.code) {
                    // successful login
                    
                    // enable the submit button
                    $('submit').set('disabled', false);
                
                    $('status').set('html', "<span class='success'><b>You are now logged in!</b><br />" +
                        "<img align='absmiddle' src='theme/loader-bar.gif'>" +
                        "'<br>Please wait while we redirect you to your account page...</span></div>");

                    // go to home page
                    setTimeout(function() { 
                        window.location = redirectURL + "?customerNumber=" + status.cid;
                    }, 3000);   
                } else {
                    // failed login
                    
                    // enable the submit button
                    $('submit').set('disabled', false);
                    
                    responseDiv.set('html',
                        "<span class='error'>" + status.message + "</span>");
                    responseDiv.setStyles({ 'opacity': '0', 'display': 'block' });
                    responseDiv.morph({ 'opacity': '1' });
                    if ($(status.field)) {
                        $(status.field).addClass('error').morph({ 'border-color': '#f00', 'background-color': '#ffebe8' });
                        $(status.field).focus();
                    }
                }
            }});

            // send the form.
            this.send();
        }
    });

    // set focus to username
    $("user").focus();

});
// -->
</script>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Sign in</h3>
	</li>
	<li>&nbsp;</li>
	<li><a href="tbd.jsp">Register</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
</ul>

<!-- begin main -->
<div class="main">

<!--  begin page content -->
<div class="content">
<h1>Welcome to HappyBank <i>Online</i></h1>

<p>You will need a username and password to continue using our
offerings and services. If you are an existing customer and have not yet
registered with HappyBank, then please <a href="tbd.jsp">Register
Now</a>.</p>

<!-- named div so content can be replaced on successfull login -->
<div id="status" style="width: 400px; margin: 0px auto">

<form id="loginForm" action="ValidateLogin" method="post">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td width="100%"><!-- ajax login response -->
			<div id="response">All fields in <b>bold</b> are required.</div>
			<br />
			</td>
		</tr>
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td width="10"><span class="ask">*</span></td>
						<td width="70"><b><label for="user">Username:</label></b></td>
						<td width="350"><input maxlength="80" name="user"
							class="iform" id="user" type="text" /> <br />
						<br />
						</td>
					</tr>
					<tr valign="top">
						<td><span class="ask">*</span></td>
						<td><b><label for="pass">Password:</label></b></td>
						<td><input maxlength="31" name="pass" class="iform" id="pass"
							type="password" /> <br />
						<br />
						</td>
					</tr>
					<tr valign="top">
						<td colspan="3">
						<p><input name="remember" type="checkbox" /> Remember my
						Username.</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
<!-- buttons and ajax processing -->
<div><input type="submit" value="Login" id="submit" class="btn" />
&nbsp; <span id="waiting" style="visibility: hidden"> <img
	align="absmiddle" src="theme/spinner.gif" /> &nbsp;<strong>Processing...<strong></span>
</div>
</form>
</div>

</div>
</div>
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
