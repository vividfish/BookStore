function _change() {
	$("#vCode").attr("src", "/BookStore/VerifyCodeServlet?" + new Date().getTime());
}