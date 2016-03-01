<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />

<!-- easyUI组件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/jquery.min.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/js/xheditor-1.2.2/jquery/jquery-1.4.4.min.js"></script> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui1.4.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui1.4.4/themes/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入扩展，用于完善easyUI和Jquery的js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/syExtEasyUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/syExtJquery.js"></script>
<!-- 引入自定义扩展的图标 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/cyExtIcon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/cyExtCss.css" />
<!-- 引入plupload  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plupload-2.1.8/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plupload-2.1.8/js/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plupload-2.1.8/js/i18n/zh_CN.js"></script>

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor-1.2.2.min.js"></script> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor_lang/zh-cn.js"></script> --%>

<%-- <script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor.js"></script> --%>
<%-- <script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script> --%>

<!-- 公用附件下载方法 -->
<script type="text/javascript">
      function fileDown(fileId){
      
    	  window.location.href="${pageContext.request.contextPath}/document_fileDown.action?fileId="+fileId;
      }
</script>