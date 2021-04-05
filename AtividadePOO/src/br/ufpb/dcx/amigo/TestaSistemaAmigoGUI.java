package br.ufpb.dcx.amigo;

import java.util.Scanner;
/**
 * 
 * @author Francisco
 *
 */
public class TestaSistemaAmigoGUI {
    
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        SistemaAmigo sistema = new SistemaAmigo();

        System.out.println("Digite a quantidsade máxima de mensagem que o sistema suporta: ");
        int quantidadeDeMensagens = leitor.nextInt();

        System.out.println("Digite a quantidade de pessoas que participarão: ");
        int quantPessoas = leitor.nextInt();

        try {
            for (int i = 0; i < quantPessoas; i++) {

                System.out.println("Digite o nome da pessoa:");
                String nome = leitor.next();
    
                System.out.println("Digite o email da pessoa:");
                String email = leitor.next();
                
                sistema.cadastraAmigo(nome, email);
    
            }
            
            sistema.configuraAmigoSecretoDe("adrian@email", "nathan@email");
            sistema.configuraAmigoSecretoDe("nathan@email", "chico@email");
            sistema.configuraAmigoSecretoDe("chico@email", "samuel@email");
            sistema.configuraAmigoSecretoDe("samuel@email", "adrian@email");
            
            System.out.println("samuel@email: " + sistema.pesquisaAmigoSecretoDe("samuel@email"));
            System.out.println("adrian@email: " + sistema.pesquisaAmigoSecretoDe("adrian@email"));
            System.out.println("nathan@email: " + sistema.pesquisaAmigoSecretoDe("nathan@email"));
            System.out.println("chico@email: " + sistema.pesquisaAmigoSecretoDe("chico@email"));

        } catch (AmigoInexistenteException | AmigoJaExisteException | AmigoNaoSorteadoException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Digite o email da pessoa que deseja enviar a mensagem: ");
        String emailRemetente = leitor.next();
        System.out.println("Digite a mensagem que deseja enviar: ");
        String texto = leitor.next();
        System.out.println("Para enviar de forma aninima digite 1\nCaso contrario digite 2: ");
        int num = leitor.nextInt();
        while (num != 1 && num != 2) {
        	System.out.println("valor invalido digite novamente:");
			num = leitor.nextInt();
        }
        boolean ehAnonima = num == 1 ? true : false;
        
        sistema.enviarMensagemParaTodos(texto, emailRemetente, ehAnonima);
        
        leitor.close();
    }
}