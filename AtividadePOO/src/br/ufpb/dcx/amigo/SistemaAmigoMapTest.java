package br.ufpb.dcx.amigo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SistemaAmigoMapTest {
	
SistemaAmigoMap sistema;
	
	@BeforeEach
	void setUp()  {
		this.sistema = new SistemaAmigoMap();
	}

	@Test
	void testSistemaAmigo() {
		assertTrue(sistema.pesquisaTodasAsMensagens().isEmpty());
		assertThrows(AmigoInexistenteException.class, 
				()-> sistema.pesquisaAmigo("ayla@teste.com"));
	}

	@Test
	void testPesquisaECadastraAmigo() {
		try {
			sistema.pesquisaAmigo("ayla@teste.com");
			fail("Deveria falhar pois n�o existe ainda");
		} catch (AmigoInexistenteException e) {
			//Ok
		}
		try {
			sistema.cadastraAmigo("ayla", "ayla@teste.com");
			Amigo a = sistema.pesquisaAmigo("ayla@teste.com");
			assertEquals("ayla", a.getNome());
			assertEquals("ayla@teste.com", a.getEmail());
		} catch (AmigoJaExisteException | AmigoInexistenteException  e) {
			fail("N�o deveria lan�ar exce��o");
		} 
		
		
	}

	@Test
	void testEnviarMensagemParaTodos() {
		assertTrue(sistema.pesquisaTodasAsMensagens().isEmpty());
		sistema.enviarMensagemParaTodos("texto", "ayla@dcx.ufpb.br", true);
		HashMap<String, Mensagem> mensagensAchadas = sistema.pesquisaTodasAsMensagens();
		assertTrue(mensagensAchadas.size()==1);
		assertTrue(mensagensAchadas.get("texto").getEmailRemetente().equals("ayla@dcx.ufpb.br"));
	}

	@Test
	void testEnviarMensagemParaAlguem() {
		assertTrue(sistema.pesquisaTodasAsMensagens().isEmpty());
		sistema.enviarMensagemParaAlguem("texto", "ayla@dcx.ufpb.br", "rodrigo@dcx.ufpb.br", true);
		HashMap<String, Mensagem> mensagensAchadas = sistema.pesquisaTodasAsMensagens();
		assertEquals(1, mensagensAchadas.size());
		assertTrue(mensagensAchadas.get("texto") instanceof MensagemParaAlguem);
		assertTrue(mensagensAchadas.get("texto").getTexto().equals("texto"));
	}

	@Test
	void testPesquisaMensagensAnonimas() {
		assertTrue(sistema.pesquisaTodasAsMensagens().isEmpty());
		sistema.enviarMensagemParaAlguem("texto 1", "ayla@dcx.ufpb.br", "rodrigo@dcx.ufpb.br", false);
		assertTrue(sistema.pesquisaMensagensAnonimas().isEmpty());
		sistema.enviarMensagemParaAlguem("texto 2", "ayla@dcx.ufpb.br", "rodrigo@dcx.ufpb.br", true);
		assertTrue(sistema.pesquisaMensagensAnonimas().size()==1);
	}

	@Test
	void testPesquisaTodasAsMensagens() {
		assertTrue(sistema.pesquisaTodasAsMensagens().isEmpty());
		sistema.enviarMensagemParaAlguem("texto 1", "ayla@dcx.ufpb.br", "rodrigor@dcx.ufpb.br", false);
		assertTrue(sistema.pesquisaTodasAsMensagens().size()==1);
		sistema.enviarMensagemParaAlguem("texto 2", "ayla@dcx.ufpb.br", "rodrigor@dcx.ufpb.br", true);
		assertTrue(sistema.pesquisaTodasAsMensagens().size()==2);
	}

	@Test
	void testPesquisaAmigoEConfiguraAmigoSecretoDe() {
		assertThrows(AmigoInexistenteException.class, 
				()-> sistema.pesquisaAmigoSecretoDe("ayla@dcx.ufpb.br"));
		try {
			sistema.cadastraAmigo("Ayla", "ayla@dcx.ufpb.br");
			sistema.cadastraAmigo("Ana", "ana@dcx.ufpb.br");
			sistema.configuraAmigoSecretoDe("ayla@dcx.ufpb.br", "ana@dcx.ufpb.br");
			sistema.configuraAmigoSecretoDe("ana@dcx.ufpb.br", "ayla@dcx.ufpb.br");
			assertEquals("ana@dcx.ufpb.br", sistema.pesquisaAmigoSecretoDe("ayla@dcx.ufpb.br"));
			assertEquals("ayla@dcx.ufpb.br", sistema.pesquisaAmigoSecretoDe("ana@dcx.ufpb.br"));
		} catch (AmigoInexistenteException | AmigoJaExisteException | AmigoNaoSorteadoException e) {
			fail("N�o deveria lan�ar exce��o");
		}
	}

}