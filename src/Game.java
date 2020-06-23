
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel {

    private Jogador jogador;
    private Oponente oponente1;
    private Oponente oponente2;
    private Oponente oponente3;
    private Oponente oponente4;
    private Oponente oponente5;
    private Oponente oponente6;
    private Oponente oponente7;
    private Oponente oponente8;
    private Pista pista1;
    private Pista pista2;
    private boolean k_cima = false;
    private boolean k_baixo = false;
    private boolean k_direita = false;
    private boolean k_esquerda = false;
    private BufferedImage imgAtual;
    private int velocidade;
    private boolean corridaEmAndamento;
    private String estadoJogo;
    private int nivel;

    public Game() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        k_esquerda = false;
                        break;
                    case KeyEvent.VK_D:
                        k_direita = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        k_esquerda = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        k_direita = false;
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        k_esquerda = true;
                        break;
                    case KeyEvent.VK_D:
                        k_direita = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        k_esquerda = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        k_direita = true;
                        break;
                    case KeyEvent.VK_P:
                        //Pausar ou Retomar Jogo
                        if (corridaEmAndamento == true) {
                            corridaEmAndamento = false;
                            estadoJogo = "Pausado";
                        } else {
                            corridaEmAndamento = true;
                            estadoJogo = "Retomado";
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        //Sair do Jogo
                        validaSairDoJogo();
                        break;
                    case KeyEvent.VK_R:
                        //Reiniciar jogo
                        estadoJogo = "";
                        validaresetarJogo();
                        break;
                    case KeyEvent.VK_ENTER:
                        validarInicioCorrida();
                        break;
                }
            }
        });        
        
        nascimentoJogador();
        velocidade = 5;
        pista1 = new Pista(0);
        pista2 = new Pista(-600);
        nivel = 1;
        nascimentoOponentes();
        estadoJogo = "Novo";
        
        setFocusable(true);
        setLayout(null);

        new Thread(new Runnable() { // instancia da Thread + classe interna anonima
            @Override
            public void run() {
                gameloop(); // inicia o gameloop
            }
        }).start(); // dispara a Thread

    }
    // GAMELOOP -------------------------------

    public void gameloop() {
        while (true) { // repetição intermitente do gameloop
            handlerEvents();
            update();
            render();
            try {
                Thread.sleep(17);
            } catch (Exception e) {
            }
        }
    }

    public void handlerEvents() {
        jogador.velX = 0;

        if (k_esquerda == true) {
            jogador.velX = -5;
        } else if (k_direita == true) {
            jogador.velX = 5;
        }
    }

    public void update() {

        if (corridaEmAndamento) {

            setarNivel();

            renascimentoOponentes(corridaEmAndamento);

            movimentoJogadorEOponentes();

            testeColisoes();

            setarPontos();

            movimentarPista(corridaEmAndamento);

        }
    }

    public void render() {
        repaint();
    }

    // OUTROS METODOS -----------------------------------------------//
    public void testeColisoes() {
        // # Colisão Comum (Jogador com os cantos da pista)
        if (jogador.posX + (jogador.raio * 2) >= 575 || jogador.posX <= 225) {
            jogador.posX = jogador.posX - jogador.velX; // desfaz o movimento
        }
        // # Colisão circular (Jogador com os Oponentes)

        int catetoH = 0;
        int catetoV = 0;
        double hipotenusa = 0.0;

        //Oponente 1
        catetoH = jogador.centroX - oponente1.centroX;
        catetoV = jogador.centroY - oponente1.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente1.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 2
        catetoH = jogador.centroX - oponente2.centroX;
        catetoV = jogador.centroY - oponente2.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente2.raio) { // verifica se houve colis�o circular
            fimDoJogo();
        }

        //Oponente 3
        catetoH = jogador.centroX - oponente3.centroX;
        catetoV = jogador.centroY - oponente3.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente3.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 4
        catetoH = jogador.centroX - oponente4.centroX;
        catetoV = jogador.centroY - oponente4.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente4.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 5
        catetoH = jogador.centroX - oponente5.centroX;
        catetoV = jogador.centroY - oponente5.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente5.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 6
        catetoH = jogador.centroX - oponente6.centroX;
        catetoV = jogador.centroY - oponente6.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente6.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 7
        catetoH = jogador.centroX - oponente7.centroX;
        catetoV = jogador.centroY - oponente7.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente7.raio) { //verifica a colisão
            fimDoJogo();
        }

        //Oponente 8
        catetoH = jogador.centroX - oponente8.centroX;
        catetoV = jogador.centroY - oponente8.centroY;
        hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente8.raio) { //verifica a colisão
            fimDoJogo();
        }

    }

    public void movimentarPista(boolean situacao) {
        //Situacao false = parado
        //Situacao true = andando    
        if (situacao) {
            pista1.posY = pista1.posY + velocidade;
            pista2.posY = pista2.posY + velocidade;

            if (pista1.posY > 598) {
                pista1.posY -= pista1.posY;
                pista1.posY = -599;
            }

            if (pista2.posY > 598) {
                pista2.posY -= pista2.posY;
                pista2.posY = -599;
            }
        }
    }

    public void movimentoJogadorEOponentes() {
        //Jogador
        jogador.posX = jogador.posX + jogador.velX;
        jogador.centroX = jogador.posX + jogador.raio;
        jogador.centroY = jogador.posY + jogador.raio;

        //Oponente 1
        oponente1.posY = oponente1.posY + velocidade;
        oponente1.centroX = oponente1.posX + oponente1.raio;
        oponente1.centroY = oponente1.posY + oponente1.raio;
        // Oponente 2
        oponente2.posY = oponente2.posY + velocidade;
        oponente2.centroX = oponente2.posX + oponente2.raio;
        oponente2.centroY = oponente2.posY + oponente2.raio;
        // Oponente 3
        oponente3.posY = oponente3.posY + velocidade;
        oponente3.centroX = oponente3.posX + oponente3.raio;
        oponente3.centroY = oponente3.posY + oponente3.raio;
        // Oponente 4
        oponente4.posY = oponente4.posY + velocidade;
        oponente4.centroX = oponente4.posX + oponente4.raio;
        oponente4.centroY = oponente4.posY + oponente4.raio;
        // Oponente 5
        oponente5.posY = oponente5.posY + velocidade;
        oponente5.centroX = oponente5.posX + oponente5.raio;
        oponente5.centroY = oponente5.posY + oponente5.raio;
        // Oponente 6
        oponente6.posY = oponente6.posY + velocidade;
        oponente6.centroX = oponente6.posX + oponente6.raio;
        oponente6.centroY = oponente6.posY + oponente6.raio;
        // Oponente 7
        oponente7.posY = oponente7.posY + velocidade;
        oponente7.centroX = oponente7.posX + oponente7.raio;
        oponente7.centroY = oponente7.posY + oponente7.raio;
        // Oponente 8
        oponente8.posY = oponente8.posY + velocidade;
        oponente8.centroX = oponente8.posX + oponente8.raio;
        oponente8.centroY = oponente8.posY + oponente8.raio;
    }

    public void renascimentoOponentes(boolean situacao) {

        if (situacao) {
            if (oponente8.posY > 598) {
                nascimentoOponentes();
            }
        }
    }

    public void setarNivel() {
        if (jogador.pontos == 0) {
            velocidade = 5;
            nivel = 1;
        } else if (jogador.pontos == 30) {
            velocidade = 10;
            nivel = 2;
        } else if (jogador.pontos == 70) {
            velocidade = 15;
            nivel = 3;
        } else if (jogador.pontos == 110) {
            velocidade = 20;
            nivel = 4;
        }
    }

    public void setarPontos() {

        if (oponente1.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente2.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente3.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente4.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente5.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente6.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente7.posY == (600 + velocidade)) {
            jogador.pontos++;
        }

        if (oponente8.posY == (600 + velocidade)) {
            jogador.pontos++;
        }
    }

    public void fimDoJogo() {

        corridaEmAndamento = false;

        Object[] respostas = {"Sim", "Não"};

        int resposta = JOptionPane.showOptionDialog(null, "Fim de jogo!\n Você alcançou " + jogador.pontos + " pontos!\n Deseja recomeçar?", "Você bateu!", JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION, null, respostas, respostas[0]);

        if (resposta == 0) {
            reiniciarJogo();
            estadoJogo = "Novo";
        }else if(resposta == 1){
            System.exit(0);
        }

    }

    public void reiniciarJogo() {
        pista1 = new Pista(0);
        pista2 = new Pista(-600);
        jogador.pontos = 0;
        velocidade = 5;
        nivel = 1;
        nascimentoJogador();
        resetarDirecao();
        nascimentoOponentes();
    }

    public void nascimentoOponentes() {
        oponente1 = new Oponente(Constantes.CoordenadasOponente.Fila.ESQUERDA, Constantes.CoordenadasOponente.Posicao.P1, velocidade);
        oponente2 = new Oponente(Constantes.CoordenadasOponente.Fila.ESQUERDA, Constantes.CoordenadasOponente.Posicao.P3, velocidade);
        oponente3 = new Oponente(Constantes.CoordenadasOponente.Fila.DIREITA, Constantes.CoordenadasOponente.Posicao.P6, velocidade);
        oponente4 = new Oponente(Constantes.CoordenadasOponente.Fila.ESQUERDA, Constantes.CoordenadasOponente.Posicao.P8, velocidade);
        oponente5 = new Oponente(Constantes.CoordenadasOponente.Fila.ESQUERDA, Constantes.CoordenadasOponente.Posicao.P10, velocidade);
        oponente6 = new Oponente(Constantes.CoordenadasOponente.Fila.ESQUERDA, Constantes.CoordenadasOponente.Posicao.P13, velocidade);
        oponente7 = new Oponente(Constantes.CoordenadasOponente.Fila.DIREITA, Constantes.CoordenadasOponente.Posicao.P15, velocidade);
        oponente8 = new Oponente(Constantes.CoordenadasOponente.Fila.DIREITA, Constantes.CoordenadasOponente.Posicao.P17, velocidade);
    }

    public void nascimentoJogador() {
        jogador = new Jogador();
    }

    //Corrige bug que trava a direção quando a corrida reinicia após a colisão
    public void resetarDirecao() {
        k_direita = false;
        k_esquerda = false;
    }

    public void validaSairDoJogo() {
        
        corridaEmAndamento = false;

        Object[] respostas = {"Sim", "Não"};

        int resposta = JOptionPane.showOptionDialog(null, "Deseja sair do jogo?", "Atenção!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, respostas, respostas[0]);

        if (resposta == 0) {
            System.exit(0);
        }else if(resposta == 1){
            corridaEmAndamento = false;
            estadoJogo = "Pausado"; 
        }

    }
    
    public void validaresetarJogo(){
        corridaEmAndamento = false;

        Object[] respostas = {"Sim", "Não"};

        int resposta = JOptionPane.showOptionDialog(null, "Deseja reiniciar o jogo?\n Você perderá os pontos que acumulou", "Atenção!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, respostas, respostas[0]);

        if (resposta == 0) {
            reiniciarJogo(); 
            estadoJogo = "Novo";
        }else if(resposta == 1){
            corridaEmAndamento = true;
        }
    }
    
    private void validarInicioCorrida() {
 
        if(corridaEmAndamento == false && estadoJogo.equals("Novo")){
            corridaEmAndamento = true;
        }
               
    }

    //-----------------------------------------------------------//
    // METODO SOBRESCRITO ---------------------------------------//
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        setBackground(Color.decode("#165b1e"));       

        g.drawImage(pista2.imagem, pista2.posX, pista2.posY, null);
        g.drawImage(pista1.imagem, pista1.posX, pista1.posY, null);
        g.drawImage(jogador.modelo4, jogador.posX, jogador.posY, null);
        g.drawImage(oponente1.modelo1, oponente1.posX, oponente1.posY, null);
        g.drawImage(oponente2.modelo2, oponente2.posX, oponente2.posY, null);
        g.drawImage(oponente3.modelo3, oponente3.posX, oponente3.posY, null);
        g.drawImage(oponente4.modelo4, oponente4.posX, oponente4.posY, null);
        g.drawImage(oponente5.modelo5, oponente5.posX, oponente5.posY, null);
        g.drawImage(oponente6.modelo6, oponente6.posX, oponente6.posY, null);
        g.drawImage(oponente7.modelo7, oponente7.posX, oponente7.posY, null);
        g.drawImage(oponente8.modelo8, oponente8.posX, oponente8.posY, null);
        
        //Painel Status
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Pontos: " + jogador.pontos, 10,30);
        g.drawString("Nível: " + nivel, 10,65);
        g.drawString("Jogo: " + estadoJogo, 10,100);
        
        //Painel Comandos
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("--------------- COMANDOS ---------------", 600,450);
        g.drawString("A / <=  : Movimento para Esquerda", 600,470);
        g.drawString("D / =>  : Movimento para Direita", 600,490);
        g.drawString("Enter  : Iniciar Jogo", 600,510);
        g.drawString("P          : Pausar/Retomar Jogo", 600,530);
        g.drawString("R          : Reiniciar Jogo", 600,550);
        g.drawString("ESC     : Sair do Jogo", 600,570);
        g.drawString("-----------------------------------------------", 600,590);
        
        //Aviso
        if(estadoJogo.equals("Novo") && corridaEmAndamento == false){
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Pressione ENTER para iniciar a corrida.", 120,300);
        }

    }

}
