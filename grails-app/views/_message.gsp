<g:if test="${errorMessage}">
    <ul class="errors" role="alert" id="errorMessage">
        <li>${errorMessage} <a style="float:right" href="javascript:;" onclick="$('#errorMessage').hide()">close</a></li>
    </ul>
</g:if>
<g:if test="${flash.error}">
    <div class="errors" role="status" id="message">${flash.error}</div>
</g:if>



<g:if test="${flash.message}">
    <div class="message" role="status" id="message">${flash.message}<a style="float:right" href="javascript:;" onclick="$('#message').hide()">close</a></div>
</g:if>
<g:if test="${message}">
    <div class="message" role="status" id="message">${message}<a style="float:right" href="javascript:;" onclick="$('#message').hide()">close</a></div>
</g:if>