package br.ufpb.dcx.amigo;

import java.util.List;
/**
 * 
 * @author Francisco
 *
 */
public class TestaSistemaAmigo {

	public static void main(String[] args) {
		
		SistemaAmigo sistema = new SistemaAmigo();
		
		try {
			sistema.cadastraAmigo("José", "jose@email.com");
			sistema.cadastraAmigo("Maria", "maria@email.com");
			
			sistema.configuraAmigoSecretoDe("jose@email.com", "maria@email.com");
			sistema.configuraAmigoSecretoDe("maria@email.com", "jose@email.com");
			
			sistema.enviarMensagemParaAlguem("Oi amigo", "maria@email.com", "jose@email.com", true);
			sistema.enviarMensagemParaTodos("Adorando a brincadeira", "maria@email.com", true);
			
			List<Mensagem> mensagensAnonimas = sistema.pesquisaMensagensAnonimas();
			for (Mensagem mensagem : mensagensAnonimas) {
				System.out.println(mensagem.getTextoCompletoAExibir());
			}
			
			System.out.println(sistema.pesquisaAmigoSecretoDe("jose@email.com"));;
			
			List<Mensagem> todasMsg = sistema.pesquisaTodasAsMensagens();
			for (Mensagem mensagem : todasMsg) {
				System.out.println(mensagem.getTexto());
			}
			
		} catch (AmigoInexistenteException | AmigoNaoSorteadoException | AmigoJaExisteException e) {
			System.out.println(e.getMessage());
		}
	}
}