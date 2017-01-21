package br.com.caelum.agiletickets.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.agiletickets.models.Estabelecimento;
import junit.framework.Assert;

public class JPAEstabelecimentoDaoTest {
	
	private static EntityManagerFactory emf;
	private EntityManager manager;
	private JPAEstabelecimentoDao dao;
	
	@BeforeClass
	public static void beforeClass() {
		emf = Persistence.createEntityManagerFactory("tests");
	}
	
	@AfterClass
	public static void afterClass() {
		emf.close();
	}
	
	@Before
	public void before() {
		this.manager = emf.createEntityManager();
		this.manager.getTransaction().begin();
		this.dao = new JPAEstabelecimentoDao(this.manager);
	}
	
	@After
	public void after() {
		this.manager.getTransaction().rollback();
		this.manager.close();
	}

	@Test
	public void deveAdicionarUmEstabelecimento() {
		Estabelecimento novo = new Estabelecimento();
		novo.setNome("Novo Estabelecimento de Tests");
		novo.setEndereco("Endereco do Estabelecimento de Teste");
		novo.setTemEstacionamento(true);
		
		this.dao.adiciona(novo);
		
		Estabelecimento adicionado = this.manager.find(Estabelecimento.class, novo.getId());
		Assert.assertEquals(adicionado, novo);
	}
}