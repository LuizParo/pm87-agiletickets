package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {
	CINEMA(0.05, BigDecimal.valueOf(0.10), Integer.MAX_VALUE),
	SHOW(0.05, BigDecimal.valueOf(0.10), Integer.MAX_VALUE),
	BALLET(0.50, BigDecimal.valueOf(0.20), 60),
	ORQUESTRA(0.50, BigDecimal.valueOf(0.20), 60),
	TEATRO(Double.MAX_VALUE, BigDecimal.ZERO, Integer.MAX_VALUE);

	private final Double limitePercentual;
	private final BigDecimal aliquotaDePreco;
	private final Integer limiteDuracao;
	
	private TipoDeEspetaculo(Double limitePercentual, BigDecimal aliquotaDePreco, Integer limiteDuracao) {
		this.limitePercentual = limitePercentual;
		this.aliquotaDePreco = aliquotaDePreco;
		this.limiteDuracao = limiteDuracao;
	}

	public Double getLimitePercentual() {
		return limitePercentual;
	}

	public BigDecimal getAliquotaDePreco() {
		return aliquotaDePreco;
	}

	public Integer getLimiteDuracao() {
		return limiteDuracao;
	}
}