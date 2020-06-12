
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Game extends JPanel {

    private Jogador jogador;
    private Oponente oponente;
    private Pista pista1;
    private Pista pista2;
    private boolean k_cima = false;
    private boolean k_baixo = false;
    private boolean k_direita = false;
    private boolean k_esquerda = false;
    private BufferedImage imgAtual;
    private int velocidade;
    private boolean corridaEmAndamento;
    private int nivel;

    public Game() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        k_cima = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        k_baixo = false;
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
                    case KeyEvent.VK_UP:
                        k_cima = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        k_baixo = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        k_esquerda = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        k_direita = true;
                        break;
                }
            }
        });
        jogador = new Jogador();
        oponente = new Oponente(0, 0, 0, true);
        velocidade = 15;
        pista1 = new Pista(0);
        pista2 = new Pista(-600);
        corridaEmAndamento = true;
        nivel = 1;

        setFocusable(true);
        setLayout(null);

        if (corridaEmAndamento) {
            new Thread(new Runnable() { // instancia da Thread + classe interna an�nima
                @Override
                public void run() {
                    gameloop(); // inicia o gameloop
                }
            }).start(); // dispara a Thread

        }
    }
    // GAMELOOP -------------------------------

    public void gameloop() {
        while (true) { // repeti��o intermitente do gameloop
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
        imgAtual = jogador.modelo1;
        if (k_esquerda == true) {
            jogador.velX = -5;
        } else if (k_direita == true) {
            jogador.velX = 5;
        }
    }

    public void update() {
        jogador.posX = jogador.posX + jogador.velX;

        jogador.centroX = jogador.posX + jogador.raio;
        jogador.centroY = jogador.posY + jogador.raio;
        testeColisoes();
        movimentarPista(true);
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
        int catetoH = jogador.centroX - oponente.centroX;
        int catetoV = jogador.centroY - oponente.centroY;
        double hipotenusa = Math.sqrt(Math.pow(catetoH, 2) + Math.pow(catetoV, 2));
        if (hipotenusa <= jogador.raio + oponente.raio) { // verifica se houve colis�o circular
            jogador.posX = jogador.posX - jogador.velX; // desfaz o movimento horizontal
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
     
    //-----------------------------------------------------------//

    // METODO SOBRESCRITO ---------------------------------------//
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.decode("#165b1e"));
        g.setColor(Color.RED);
        g.drawImage(pista2.imagem, pista2.posX, pista2.posY, null);
        g.drawImage(pista1.imagem, pista1.posX, pista1.posY, null);
        g.drawImage(jogador.modelo4, jogador.posX, jogador.posY, null);

    }
}
