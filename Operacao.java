//class com as operações
public class Operacao {//Inicio class Operacao
 public String tipo;     //tipo até agora fica em CADASTRO, logo que cadadastramos uma nova_solicitação o tipo CADASTRO vem para cá
 public Solicitacao solicitacao;  //a nova_solicitação vem para cá

    public Operacao(String tipo, Solicitacao solicitacao) { //iniicio metodo Operação
        this.tipo = tipo; //coloca o tipo dentro do tipo
        this.solicitacao = solicitacao;  //coloca a nova_solicitação dentro da solicitacao
    }//fim metodo Operação

}//fim class Operação