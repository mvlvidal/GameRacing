import java.awt.Dimension;
import javax.swing.JFrame;

public class Principal {

    public Principal() {
        JFrame janela = new JFrame("GameRacing (Por Marcus Vidal)"); // cria a janela
        Game game = new Game(); // cria a tela do jogo           
        game.setPreferredSize(new Dimension(Constantes.DimensoesTela.LARGURA, Constantes.DimensoesTela.ALTURA));
        janela.getContentPane().add(game); // adiciona a tela do jogo na janela	        
        janela.setResizable(false); // impede redimensionamento
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // modo de encerramento
        janela.add(game);
        janela.setLocation(100, 100); // posição da janela na tela
        
        janela.setVisible(true); // torna a janela visível
        janela.pack();
    }

    public static void main(String[] args) {
        new Principal(); // dispara a aplicação
    }
}
