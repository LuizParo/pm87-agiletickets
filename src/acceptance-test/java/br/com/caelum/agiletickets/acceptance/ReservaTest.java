package br.com.caelum.agiletickets.acceptance;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.acceptance.page.ReservaPage;
import junit.framework.Assert;

public class ReservaTest {
	private ReservaPage reservaPage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", getDriver());
		this.reservaPage = new ReservaPage(new FirefoxDriver());
	}
	
	@After
	public void tearDown() {
		this.reservaPage.fechaBrowser();
	}
	
	@Test
	public void deveManterPrecoDe50ReaisParaOsPrimeros95Ingressos() {
		this.reservaPage.navegaAteSessao("10");
		Assert.assertTrue(this.reservaPage.contemNaPagina("R$ 50,00"));

	}

	@Test
	public void deveTerPreco55ReaisParaOsUltimos5Ingressos() {
		this.reservaPage.navegaAteSessao("9");
		Assert.assertTrue(this.reservaPage.contemNaPagina("R$ 55,00"));
	}
	
	private static String getDriver() {
        ClassLoader classLoader = EstabelecimentoTest.class.getClassLoader();
        return new File(classLoader.getResource("geckodriver").getFile()).getPath();
	}
}