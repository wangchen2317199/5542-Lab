<%--
  Created by IntelliJ IDEA.
  User: dwk89
  Date: 03/23/2017
  Time: 06:05:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Standard Emoji Impression Recognition</title>
  <script type = 'text/javascript' src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>
  <script type = 'text/javascript' src = 'https://rawgit.com/dwk8923/Miscellaneous_2/master/vv/angularJS_min.js'></script>
</head>
<body style = 'background-color: White;'>
<p align = 'center' style = 'font-size: 1.5em;color: Black;'>CS 5542 LAB 9</p>
<p align = 'center' style = 'font-size: 1.5em; color: Black'>Emoji Annotation</p>
<p align = 'center' style = 'font-size: 1.2em; color: Black;'><u>Chen Wang (Class ID: 44)</u></p>
<form action = 'qa'>
  <p style = 'color: black;'>URL about emoji:<br><br><input id = 'url'></p>
  <p style = 'color: black;'>Question:<br><br><input id = 'question'></p>
  <button type = 'submit' id = 's'>Submit</button>
</form>
<p id = 'res'></p>
</body>
</html>
