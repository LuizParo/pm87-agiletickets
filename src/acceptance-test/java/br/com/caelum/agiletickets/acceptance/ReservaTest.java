package br.com.caelum.agiletickets.acceptance;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;

public class ReservaTest {
	
	private WebDriver browser;
	private static final String BASE_URL = "http://localhost:8080/sessao/";
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", getDriver());
		this.browser = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		this.browser.close();
	}
	
	@Test
	public void deveManterPrecoDe50ReaisParaOsPrimeros95Ingressos() {
		this.browser.get(BASE_URL + "10");
		Assert.assertTrue(this.browser.getPageSource().contains("R$ 50,00"));
	}

	@Test
	public void deveTerPreco55ReaisParaOsUltimos5Ingressos() {
		this.browser.get(BASE_URL + "9");
		Assert.assertTrue(this.browser.getPageSource().contains("R$ 55,00"));
	}
	
	private static String getDriver() {
        ClassLoader classLoader = EstabelecimentoTest.class.getClassLoader();
        return new File(classLoader.getResource("geckodriver").getFile()).getPath();
	}
}