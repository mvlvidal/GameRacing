
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mvidal
 */
public class Pista {

    public BufferedImage imagem;
    public int posX;
    public int posY;
    public int velY;

    public Pista(int posicaoY) {
        try {

            imagem = ImageIO.read(getClass().getResource("imgs/asfalto.png"));

        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem");
            e.printStackTrace();
        }

        posX = 225;
        posY = posicaoY;
        velY = 0;

    }
}
