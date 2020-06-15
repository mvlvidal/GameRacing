
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Oponente {

    public BufferedImage sprite;
    public BufferedImage modelo1;
    public BufferedImage modelo2;
    public BufferedImage modelo3;
    public BufferedImage modelo4;
    public BufferedImage modelo5;
    public BufferedImage modelo6;
    public BufferedImage modelo7;
    public BufferedImage modelo8;
    public int raio;
    public int centroX;
    public int centroY;
    public int posX;
    public int posY;
    public int velY;

    public Oponente(int posicaoX, int posicaoY, int velocidadeY) {
        try {
            sprite = ImageIO.read(getClass().getResource("imgs/sprite_carros.png"));
            modelo1 = Recursos.getInstance().cortarImagem(0, 0, 100, 100, sprite);
            modelo2 = Recursos.getInstance().cortarImagem(100, 0, 200, 100, sprite);
            modelo3 = Recursos.getInstance().cortarImagem(200, 0, 300, 100, sprite);
            modelo4 = Recursos.getInstance().cortarImagem(300, 0, 400, 100, sprite);
            modelo5 = Recursos.getInstance().cortarImagem(0, 100, 100, 200, sprite);
            modelo6 = Recursos.getInstance().cortarImagem(100, 100, 200, 200, sprite);
            modelo7 = Recursos.getInstance().cortarImagem(200, 100, 300, 200, sprite);
            modelo8 = Recursos.getInstance().cortarImagem(300, 100, 400, 200, sprite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        raio = 45;
        posX = posicaoX;
        posY = posicaoY;
        centroX = posX + raio;
        centroY = posY + raio;
        velY = velocidadeY;
    }
}
