package br.ufpb.dcx.amigo;

import java.util.HashMap;

/**
 * 
 * @author Francisco
 *
 */
public class SistemaAmigoMap {
	
	HashMap<String, Mensagem> mensagens;
	HashMap<String, Amigo> amigos;

	/**
	* Contrutor padrão da classe.
	*/
	public SistemaAmigoMap() {
		
	  	this.amigos = new HashMap<>();
	    this.mensagens = new HashMap<>();
	}
		
	public SistemaAmigoMap(HashMap<String, Mensagem> mensagens, HashMap<String, Amigo> amigos) {
	  	
		this.mensagens = mensagens;
	  	this.amigos = amigos;
	}
	/**
	 * Cadastra um novo participante do amigo secreto.
	 * @param nomeAmigo
	 * @param emailAmigo
	 * @throws AmigoJaExisteException
	 */
	public void cadastraAmigo(String nomeAmigo, String emailAmigo) throws AmigoJaExisteException {
	      
		Amigo user = new Amigo(nomeAmigo, emailAmigo);
		if (amigos.size() == 0) {
			amigos.put(emailAmigo, user);
		}else{
			if (amigos.containsKey(emailAmigo)) {
				throw new AmigoJaExisteException("Amigo " + nomeAmigo + " já existe");
			}else{
				amigos.put(emailAmigo, user);
			}
		}
	}
	
	/**
	 * Pesquisa um amigo participante.
	 * @param amigo
	 * @return
	 */
	public Amigo pesquisaAmigo(String emailAmigo) throws AmigoInexistenteException{
	  	 
			if(amigos.containsKey(emailAmigo)) {
					return amigos.get(emailAmigo);
			}
			
			throw new AmigoInexistenteException(emailAmigo + " inexistente");
	}
	
	/**
	 * Envia a mensagem para todos.
	 * @param texto
	 * @param emailRemetente
	 * @param ehAnonima
	 */
	public void enviarMensagemParaTodos(String texto, String emailRemetente, boolean ehAnonima) {      
		MensagemParaTodos msg = new MensagemParaTodos(texto, emailRemetente, ehAnonima);
	    mensagens.put(texto, msg);
	    System.out.println(msg.getTextoCompletoAExibir());
	}
	
	/**
	 * Envia a mensagem para alguem.
	 * @param texto
	 * @param emailRemetente
	 * @param emailDestinatario
	 * @param ehAnonima
	 */
	public void enviarMensagemParaAlguem(String texto, String emailRemetente, String emailDestinatario, boolean ehAnonima) {
		MensagemParaAlguem msg = new MensagemParaAlguem(texto, emailRemetente, emailDestinatario, ehAnonima);
		mensagens.put(texto, msg);
		System.out.println(msg.getTextoCompletoAExibir());
	}
	
	/**
	 * Pesquisar as mensagens anominas.
	 * @return uma lista com as mensagens anonimas.
	 */
	public HashMap<Mensagem, Mensagem> pesquisaMensagensAnonimas() {
		HashMap<Mensagem, Mensagem> anonimos = new HashMap<>();
		for (String i : mensagens.keySet()) {
			if (mensagens.get(i).ehAnonima()) {
				anonimos.put(mensagens.get(i), mensagens.get(i));
			}  
		}
		return anonimos;
	      
	}
	
	/**
	 *  Pesquisar todas as mensagens enviadas
	 * @return uma lista de mensagens
	 */
	public HashMap<String, Mensagem> pesquisaTodasAsMensagens() {
		return mensagens;
	}
	
	/**
	 * Configura o amigo secreto de emailDaPessoa para emailAmigoSorteado
	 * @param emailDaPessoa
	 * @param emailAmigoSorteado
	 * @throws AmigoInexistenteException
	 */
	
	public void configuraAmigoSecretoDe(String emailDaPessoa, String emailAmigoSorteado) throws AmigoInexistenteException {
		boolean naoExiste = true;
		for (String i : amigos.keySet()) {
			if (amigos.get(i).getEmail().equals(emailDaPessoa)) {
				amigos.get(i).setEmailAmigoSorteado(emailAmigoSorteado);
	            naoExiste = false;
	        }
	    }
	    if (naoExiste) {
	        throw new AmigoInexistenteException(emailDaPessoa + " inexistente");
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
	      
	    for (String amigo : amigos.keySet()) {
	        if (amigos.get(amigo).getEmail().equals(emailDaPessoa)) {
	            naoExiste = false;
	            if (amigos.get(amigo).getEmailAmigoSorteado()!= null) {
	                  
	                return amigos.get(amigo).getEmailAmigoSorteado();    
	            }
	        }
	    }
	    if (naoExiste) {
	        throw new AmigoInexistenteException(emailDaPessoa + " inexistente");
	    }
	      
	    throw new AmigoNaoSorteadoException("Amigo secreto de: " + emailDaPessoa + " não sorteado");
	        
	}

}