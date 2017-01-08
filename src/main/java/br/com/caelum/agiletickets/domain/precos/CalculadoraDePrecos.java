package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		TipoDeEspetaculo tipoDeEspetaculo = sessao.getEspetaculo().getTipo();
		
		if(sessao.getPercentualIngressosDisponiveis() <= tipoDeEspetaculo.getLimitePercentual()) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(tipoDeEspetaculo.getAliquotaDePreco()));
		} else {
			preco = sessao.getPreco();
		}
		
		if(sessao.getDuracaoEmMinutos() != null && sessao.getDuracaoEmMinutos() > tipoDeEspetaculo.getLimiteDuracao()){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}