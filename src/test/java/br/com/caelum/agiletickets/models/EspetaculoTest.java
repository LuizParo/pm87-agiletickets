package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}
	
	@Test
	public void deveSerPossivelCriarSessoesDiariasQueComecaETerminaNoMesmoDia() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = LocalDate.now();
		LocalDate fim = inicio;
		LocalTime horario = LocalTime.now();
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertNotNull(sessoes);
		assertFalse(sessoes.isEmpty());
		assertEquals(1, sessoes.size());
	}
	
	@Test
	public void deveCriarSessoesDiariasEmDiasDiferentes() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = LocalDate.now();
		LocalDate fim = inicio.plusDays(1);
		LocalTime horario = LocalTime.now();
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertNotNull(sessoes);
		assertFalse(sessoes.isEmpty());
		assertEquals(2, sessoes.size());
		
		Sessao sessaoPrimeiroDia = sessoes.get(0);
		Sessao sessaoSegundoDia = sessoes.get(1);
		
		assertEquals(1, sessaoSegundoDia.getInicio().getDayOfYear() - sessaoPrimeiroDia.getInicio().getDayOfYear());
	}
	
	@Test
	public void deveCriar1SessaoSemanalEmDatasDiferentesComMenosDeUmaSemanaEntreAmbas() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = LocalDate.now();
		LocalDate fim = inicio.plusDays(3);
		LocalTime horario = LocalTime.now();
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		assertNotNull(sessoes);
		assertFalse(sessoes.isEmpty());
		assertEquals(1, sessoes.size());
	}
	
	@Test
	public void deveCriar2SesssoesSemanaisEmDatasDiferentesComMaisDeUmaSemanaEntreAmbas() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = LocalDate.now();
		LocalDate fim = inicio.plusDays(8);
		LocalTime horario = LocalTime.now();
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		assertNotNull(sessoes);
		assertFalse(sessoes.isEmpty());
		assertEquals(2, sessoes.size());
	}
	
	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
}
