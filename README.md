<h2 class="code-line" data-line-start=0 data-line-end=1 ><a id="DNS_Changer_App_0"></a>DNS Changer App</h2>
<h2 class="code-line" data-line-start=1 data-line-end=2 ><a id="Overview_1"></a>Overview</h2>
<p class="has-line-data" data-line-start="2" data-line-end="3">This Android application allows users to easily change the DNS servers for their network connection. Designed with security in mind, the app restricts users to a predefined list of DNS servers to ensure safe and reliable connections. The app requires root access to function, as it modifies system properties to change DNS settings.</p>
<h2 class="code-line" data-line-start=4 data-line-end=5 ><a id="Features_4"></a>Features</h2>
<ul>
<li class="has-line-data" data-line-start="5" data-line-end="6">View Current DNS Servers: The app displays the current DNS servers in use.</li>
<li class="has-line-data" data-line-start="6" data-line-end="7">Change DNS Servers: Users can select from a predefined list of DNS servers to update their network connection.</li>
<li class="has-line-data" data-line-start="7" data-line-end="8">Security Restrictions: Users are limited to connecting only to specific DNS servers to maintain a secure and controlled environment.</li>
<li class="has-line-data" data-line-start="8" data-line-end="9">Real-time Network Monitoring: The app listens for network changes using a BroadcastReceiver and updates DNS settings accordingly.</li>
<li class="has-line-data" data-line-start="9" data-line-end="10">ConnectivityManager API: To retrieve the current DNS servers.</li>
<li class="has-line-data" data-line-start="10" data-line-end="11">Root Access: Required for changing DNS settings using the setprop command.</li>
<li class="has-line-data" data-line-start="11" data-line-end="13">BroadcastReceiver: To listen for network changes and apply DNS updates in real-time.</li>
</ul>
<h1 class="code-line" data-line-start=13 data-line-end=14 ><a id="How_It_Works_13"></a>How It Works</h1>
<ol>
<li class="has-line-data" data-line-start="14" data-line-end="15">Retrieve Current DNS: The app uses the ConnectivityManager to get the current DNS servers from the active network link.</li>
<li class="has-line-data" data-line-start="15" data-line-end="16">Select New DNS: Users choose a new DNS server from a predefined list using a Spinner UI component.</li>
<li class="has-line-data" data-line-start="16" data-line-end="17">Apply Changes: Upon selection, the app uses root access to execute setprop commands and change the DNS settings.</li>
<li class="has-line-data" data-line-start="17" data-line-end="19">Monitor Network Changes: The app uses a BroadcastReceiver to detect network changes and ensure DNS settings are always up to date.</li>
</ol>
<h1 class="code-line" data-line-start=19 data-line-end=20 ><a id="Installation_19"></a>Installation</h1>
<ol>
<li class="has-line-data" data-line-start="20" data-line-end="22">Clone this repository to your local machine:<br>
<code>git clone https://github.com/yourusername/dns-changer-app.git</code></li>
<li class="has-line-data" data-line-start="22" data-line-end="23">Open the project in Android Studio.</li>
<li class="has-line-data" data-line-start="23" data-line-end="25">Build and run the project on a rooted Android device.</li>
</ol>
<h1 class="code-line" data-line-start=25 data-line-end=26 ><a id="Usage_25"></a>Usage</h1>
<ol>
<li class="has-line-data" data-line-start="26" data-line-end="27">Launch the app on a rooted Android device.</li>
<li class="has-line-data" data-line-start="27" data-line-end="28">View the current DNS servers on the main screen.</li>
<li class="has-line-data" data-line-start="28" data-line-end="29">Select a new DNS server from the list.</li>
<li class="has-line-data" data-line-start="29" data-line-end="30">Tap the “Change DNS” button to apply the new settings.</li>
<li class="has-line-data" data-line-start="30" data-line-end="31">The app will confirm if the DNS was successfully changed.</li>
</ol>
<h1 class="code-line" data-line-start=31 data-line-end=32 ><a id="Limitations_31"></a>Limitations</h1>
<ul>
<li class="has-line-data" data-line-start="32" data-line-end="33">The app requires root access to function properly.</li>
<li class="has-line-data" data-line-start="33" data-line-end="34">Users can only choose from a predefined list of DNS servers to ensure security.</li>
</ul>
