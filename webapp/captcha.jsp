<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<html>
<body>

<!-- 
<form action="processCaptcha.jsp" method="post">
        %<
          //ReCaptcha c = ReCaptchaFactory.newReCaptcha("your_public_key", "your_private_key", false);
          ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Lc7lhYUAAAAAFQdbWAJrBgMs6hFH3r6WUptOAIT", "6Lc7lhYUAAAAADzYIdH8YIVrRTh-cym5ageI4yGa", false);
          out.print(captcha.createRecaptchaHtml(null, null));
        %>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
-->

<html>
<body>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <form action="processCaptcha.jsp" method="post">
        <div class="g-recaptcha" data-sitekey="6Lc7lhYUAAAAAFQdbWAJrBgMs6hFH3r6WUptOAIT"></div>
        <br/>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
