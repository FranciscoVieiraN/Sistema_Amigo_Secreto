package br.ufpb.dcx.amigo;

import java.util.ArrayList;
import java.util.List;
/**
 * Class para gerenciar o sistema de amigo secreto.
 * @author Francisco
 */
public class SistemaAmigo {
	
	private List<Mensagem> mensagens;
	private List<Amigo> amigos;
	
	
	/**
	 * Construtor padrão da classe.
	 */
	public SistemaAmigo() {
		this.amigos = new ArrayList<>();
		this.mensagens = new ArrayList<>();
	}
	/**
	 * Construtor para cadastrar um amigo.
	 * @param nomeAmigo
	 * @param emailAmigo
	 * @throws AmigoJaExisteException - caso o amigo a ser cadastrado já esteja cadastrado.
	 */
	public void cadastraAmigo(String nomeAmigo, String emailAmigo) throws AmigoJaExisteException {
        
        Amigo user = new Amigo(nomeAmigo, emailAmigo);
        if (amigos.size() == 0) {
            amigos.add(user);
        }else{
            if (amigos.contains(user)) {
                throw new AmigoJaExisteException("Amigo " + nomeAmigo + " já existe");
            }else{
                amigos.add(user);
            }
        }
    }
	/**
	 * Pesquisa um amigo na lista de amigos
	 * @param emailAmigo Pessoa que está procurando.
	 * @return se tiver esse amigo na lista ele retorna ele.
	 */
	public Amigo pesquisaAmigo(String emailAmigo) throws AmigoInexistenteException{
		for (Amigo amigo : amigos) {
			if (amigo.getEmail().equals(emailAmigo)) {
				return amigo;
			}
		}
		throw new AmigoInexistenteException(emailAmigo + "inexistente!");
	}
	/**
	 * Construtor que envia mensagem para todos os amigo, podendo ser animo ou não.
	 * @param texto
	 * @param emailRemetente
	 * @param ehAnonima
	 */
	public void enviarMensagemParaTodos(String texto, String emailRemetente, boolean ehAnonima) {
		MensagemParaTodos msg = new MensagemParaTodos(texto, emailRemetente, ehAnonima);
		mensagens.add(msg);
		System.out.println(msg.getTextoCompletoAExibir());
	}
	/**
	 * Construtor que envia mensagem para um amigo expecifico, podendo ser animo ou não.
	 * @param texto - texto da mensagem que séra enviada.
	 * @param emailRemetente
	 * @param emailDestinatario
	 * @param ehAnonima - determina se exibe o remetente ou não.
	 */
	public void enviarMensagemParaAlguem(String texto, String emailRemetente, String emailDestinatario, boolean ehAnonima) {
		MensagemParaAlguem msg = new MensagemParaAlguem(texto, emailRemetente,emailDestinatario, ehAnonima);
		mensagens.add(msg);
		System.out.println(msg.getTextoCompletoAExibir());
	}
	/**
	 * Pesquisar as mesagens anonimas
	 * @return uma lista com as mensagens anonimas
	 */
	public List<Mensagem> pesquisaMensagensAnonimas() {
		List<Mensagem> anonimos = new ArrayList<>();
		for(Mensagem msg : mensagens) {
			if(msg.ehAnonima()) {
				anonimos.add(msg);
			}
		}
		return anonimos;
	}
	/**
	 * Pesquisar todas as mensagens já enviadas
	 * @return uma lista com todas as mensagens já enviadas
	 */
	public List<Mensagem> pesquisaTodasAsMensagens() {
		return mensagens;
	}
	/**
	 * Configura o amigo secreto da pessoa que esta na brincadeira.
	 * @param emailDaPessoa 
	 * @param emailSorteado
	 * @throws AmigoInexistenteException
	 */
	public void configuraAmigoSecretoDe(String emailDaPessoa, String emailSorteado) throws AmigoInexistenteException {
		boolean naoExiste = true;
		for (Amigo amigo : amigos) {
			if(amigo.getEmail().equals(emailDaPessoa)) {
				amigo.setEmailAmigoSorteado(emailSorteado);
				naoExiste = false;
			} 
		}
		if(naoExiste) {
			throw new AmigoInexistenteException("Amigo " + emailDaPessoa + " inexistente");
		}
	}
	/**
	 * Pesquisa o amigo secreto de emailDaPessoa
	 * @param emailDaPessoa
	 * @return
	 * @throws AmigoInexistenteException
	 * @throws AmigoNaoSorteadoException
	 */
	public String pesquisaAmigoSecretoDe(String emailDaPessoa) throws AmigoInexistenteException, AmigoNaoSorteadoException {
		boolean naoExiste = true;
		for (Amigo amigo : amigos) {
			if (amigo.getEmail().equals(emailDaPessoa)) {
				naoExiste = false;
				if (amigo.getEmailAmigoSorteado() != null) {
					return amigo.getEmailAmigoSorteado();
				} 
			} 
		}
		if (naoExiste) {
			throw new AmigoInexistenteException( emailDaPessoa  + " inexistente");
		}
		throw new AmigoNaoSorteadoException("Amigo secreto de" +emailDaPessoa + "não sorteado");
	}
}
