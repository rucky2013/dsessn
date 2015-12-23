<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% Jsplite.inherits("com.github.dsessn.ops.web.Index", request, response); %>
<html>
<head>
  <title>Dsessn OPS</title>
  <%@ include file="WEB-INF/include/meta.jsp" %>
</head>
<body>
${hello}
</body>
</html>
