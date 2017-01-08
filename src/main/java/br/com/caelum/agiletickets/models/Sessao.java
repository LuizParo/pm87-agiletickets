package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@Entity
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Espetaculo espetaculo;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime inicio;

	private Integer duracaoEmMinutos;

	private Integer totalIngressos = 0;

	private Integer ingressosReservados = 0;

	private BigDecimal preco;

	public Long getId() {
		return id;
	}

	public void setEspetaculo(Espetaculo espetaculo) {
		this.espetaculo = espetaculo;
	}

	public DateTime getInicio() {
		return inicio;
	}

	public void setInicio(DateTime inicio) {
		this.inicio = inicio;
	}

	public Espetaculo getEspetaculo() {
		return espetaculo;
	}

	public Integer getDuracaoEmMinutos() {
		return duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
		this.duracaoEmMinutos = duracaoEmMinutos;
	}

	public String getDia() {
		return inicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR")));
	}

	public String getHora() {
		return inicio.toString(DateTimeFormat.shortTime().withLocale(new Locale("pt", "BR")));
	}

	public Integer getTotalIngressos() {
		return totalIngressos;
	}

	public void setTotalIngressos(Integer totalIngressos) {
		this.totalIngressos = totalIngressos;
	}

	public Integer getIngressosReservados() {
		return ingressosReservados;
	}

	public void setIngressosReservados(Integer ingressosReservados) {
		this.ingressosReservados = ingressosReservados;
	}

	public Integer getIngressosDisponiveis() {
		return totalIngressos - ingressosReservados;
	}
	
	public void reserva(Integer numeroDeIngressos) {
		this.ingressosReservados += numeroDeIngressos;
	}

	public boolean podeReservar(Integer numeroDeIngressos) {
		int sobraram = getIngressosDisponiveis() - numeroDeIngressos;
        boolean naoTemEspaco = sobraram < 0;

        return !naoTemEspaco;
	}
	
	public double getPercentualIngressosDisponiveis() {
		return this.getIngressosDisponiveis() / this.getTotalIngressos().doubleValue();
	}
	
	public boolean isDuracaoDoEspetaculoDentroDoLimite() {
		return this.getDuracaoEmMinutos() <= this.getEspetaculo().getTipo().getLimiteDuracao();
	}
	
	public boolean isPercentualIngressosDisponiveisDentroDoLimite() {
		return this.getPercentualIngressosDisponiveis() <= this.getEspetaculo().getTipo().getLimitePercentual();
	}

	public BigDecimal getPrecoDoEspetaculo() {
		BigDecimal preco;
		TipoDeEspetaculo tipoDeEspetaculo = this.getEspetaculo().getTipo();
		
		if(this.isPercentualIngressosDisponiveisDentroDoLimite()) { 
			preco = this.getPreco().add(this.getPreco().multiply(tipoDeEspetaculo.getAliquotaDePreco()));
		} else {
			preco = this.getPreco();
		}
		
		if(this.getDuracaoEmMinutos() != null && !this.isDuracaoDoEspetaculoDentroDoLimite()){
			preco = preco.add(this.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		
		return preco;
	}
	
	public BigDecimal getPrecoTotalPelaQuantidade(Integer quantidade) {
		BigDecimal preco = this.getPrecoDoEspetaculo();
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPreco() {
		return preco;
	}
}