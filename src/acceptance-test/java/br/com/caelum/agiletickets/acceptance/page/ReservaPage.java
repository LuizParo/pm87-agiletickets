package br.com.caelum.agiletickets.acceptance.page;

import org.openqa.selenium.WebDriver;

public class ReservaPage {
	private static final String BASE_URL = "http://localhost:8080/sessao/";

	private WebDriver browser;

	public ReservaPage(WebDriver browser) {
		this.browser = browser;
	}
	
	public void navegaAteSessao(String idSessao) {
		this.browser.get(BASE_URL + idSessao);
	}
	
	public boolean contemNaPagina(String valor) {
		return this.browser.getPageSource().contains(valor);
	}
	
	public void fechaBrowser() {
		this.browser.close();
	}
}