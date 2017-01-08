package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.agiletickets.domain.precos.SessaoTestDataBuilder;

public class SessaoTest {

	@Test
	public void deveVender1ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void deveVender5ingressosSeHa10vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(10);
		
		Assert.assertTrue(sessao.podeReservar(5));
	}

	@Test
	public void naoDeveVender3ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		Assert.assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		Assert.assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
	
	@Test
	public void devePossuir5PorCentroDeIngressosDisponiveisCom10IngressosE5Reservados() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(10);
		sessao.reserva(5);
		
		Assert.assertEquals(0.5, sessao.getPercentualIngressosDisponiveis(), 0.001);
	}
	
	@Test
	public void deveRetornarFalsoSeTempoDoEspetaculoForMaiorQueOLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
			.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
			.comDuracaoEmMinutos(70)
			.build();
		
		Assert.assertFalse(sessao.isDuracaoDoEspetaculoDentroDoLimite());
	}
	
	@Test
	public void deveRetornarVerdadeiroSeTempoDoEspetaculoForMenorQueOLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comDuracaoEmMinutos(55)
				.build();
		
		Assert.assertTrue(sessao.isDuracaoDoEspetaculoDentroDoLimite());
	}
	
	@Test
	public void deveRetornarFalsoSePercentualIngressosDisponiveisForMaiorQueOLimiteDoEspetaculo() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
			.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
			.comTotalIngressos(10)
			.comIngressoReservados(1)
			.build();
		
		Assert.assertFalse(sessao.isPercentualIngressosDisponiveisDentroDoLimite());
	}
	
	@Test
	public void deveRetornarVerdadeiroSePercentualIngressosDisponiveisForMenorQueOLimiteDoEspetaculo() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(10)
				.comIngressoReservados(9)
				.build();
		
		Assert.assertTrue(sessao.isPercentualIngressosDisponiveisDentroDoLimite());
	}
	
	@Test
	public void deveBuscarPrecoCorretoDaOrquestraComPercentualIngressosDisponiveisDentroDoLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(10)
				.comIngressoReservados(9)
				.comOPreco(10)
				.build();
		
		Assert.assertEquals(new BigDecimal("12.00"), sessao.getPrecoDoEspetaculo());
	}
	
	@Test
	public void deveBuscarPrecoCorretoDaOrquestraComPercentualIngressosDisponiveisForaDoLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(10)
				.comIngressoReservados(1)
				.comOPreco(10)
				.build();
		
		Assert.assertEquals(new BigDecimal("10.0"), sessao.getPrecoDoEspetaculo());
	}
	
	@Test
	public void deveBuscarPrecoCorretoDaOrquestraComPercentualIngressosDisponiveisEDuracaoDoEspetaculoDentroDoLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(10)
				.comIngressoReservados(9)
				.comDuracaoEmMinutos(55)
				.comOPreco(10)
				.build();
		
		Assert.assertEquals(new BigDecimal("12.00"), sessao.getPrecoDoEspetaculo());
	}
	
	@Test
	public void deveBuscarPrecoCorretoDaOrquestraComPercentualIngressosDisponiveisEDuracaoDoEspetaculoForaDoLimite() {
		Sessao sessao = SessaoTestDataBuilder.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(10)
				.comIngressoReservados(1)
				.comDuracaoEmMinutos(70)
				.comOPreco(10)
				.build();
		
		Assert.assertEquals(new BigDecimal("11.00"), sessao.getPrecoDoEspetaculo());
	}
	
	@Test
	public void deveConsiderarQuantidadeAoCalcularPrecoTotal(){
		Sessao sessao =	SessaoTestDataBuilder
			.umaSessao()
			.deUmEspetaculoDoTipo(TipoDeEspetaculo.TEATRO)
			.comOPreco(10.0)
			.build();
		
		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(9);
		
		assertEquals(0, BigDecimal.valueOf(90.0).compareTo(precoTotal));
	}
	
	@Test
	public void deveAplicar10PorCentoAMaisNosUltimosIngressosQuandoForCinema(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.CINEMA)
				.comTotalIngressos(100)
				.comIngressoReservados(95)
				.comOPreco(20.0)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(22.0).compareTo(precoTotal));
	}

	@Test
	public void naoDeveAplicarAcrescimoNosPrimeirosIngressosQuandoForCinema(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.CINEMA)
				.comTotalIngressos(100)
				.comIngressoReservados(10)
				.comOPreco(20.0)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(20.0).compareTo(precoTotal));
	}
	
	@Test
	public void deveAplicar10PorCentoAMaisNosUltimosIngressosQuandoForShow(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.SHOW)
				.comTotalIngressos(200)
				.comIngressoReservados(195)
				.comOPreco(100.0)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(110.0).compareTo(precoTotal));
	}

	@Test
	public void naoDeveAplicarAcrescimoNosPrimeirosIngressosQuandoForShow(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.SHOW)
				.comTotalIngressos(200)
				.comIngressoReservados(15)
				.comOPreco(100.0)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(100.0).compareTo(precoTotal));
	}
	
	@Test
	public void deveAplicar20PorCentoAMaisNosUltimosIngressosQuandoForBallet(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.BALLET)
				.comTotalIngressos(50)
				.comIngressoReservados(25)
				.comOPreco(500.0)
				.comDuracaoEmMinutos(50)
				.build();
		
		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(600.0).compareTo(precoTotal));
	}

	@Test
	public void naoDeveAplicarAcrescimoNosPrimeirosIngressosQuandoForBallet(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.BALLET)
				.comTotalIngressos(50)
				.comIngressoReservados(5)
				.comOPreco(500.0)
				.comDuracaoEmMinutos(50)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(500.0).compareTo(precoTotal));
	}

	@Test
	public void deveAplicar10AMaisSeDurarMaisDeUmaHoraQuandoForBallet(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.BALLET)
				.comTotalIngressos(50)
				.comIngressoReservados(5)
				.comOPreco(500.0)
				.comDuracaoEmMinutos(100)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(550.0).compareTo(precoTotal));
	}
	
	@Test
	public void deveAplicar20PorCentoAMaisNosUltimosIngressosQuandoForOrquestra(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(70)
				.comIngressoReservados(40)
				.comOPreco(1000.0)
				.comDuracaoEmMinutos(60)
				.build();
		
		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(1200.0).compareTo(precoTotal));
	}

	@Test
	public void naoDeveAplicarAcrescimoNosPrimeirosIngressosQuandoForOrquestra(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(70)
				.comIngressoReservados(10)
				.comOPreco(1000.0)
				.comDuracaoEmMinutos(60)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(1000.0).compareTo(precoTotal));
	}

	@Test
	public void deveAplicar10AMaisSeDurarMaisDeUmaHoraQuandoForOrquestra(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.ORQUESTRA)
				.comTotalIngressos(70)
				.comIngressoReservados(65)
				.comOPreco(1000.0)
				.comDuracaoEmMinutos(120)
				.build();

		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(1300.0).compareTo(precoTotal));
	}
	
	@Test
	public void naoDeveAplicarAcrescimoQuandoForTeatro(){
		Sessao sessao =	SessaoTestDataBuilder
				.umaSessao()
				.deUmEspetaculoDoTipo(TipoDeEspetaculo.TEATRO)
				.comOPreco(10.0)
				.build();
		
		BigDecimal precoTotal = sessao.getPrecoTotalPelaQuantidade(1);
		
		assertEquals(0, BigDecimal.valueOf(10.0).compareTo(precoTotal));
	}
}