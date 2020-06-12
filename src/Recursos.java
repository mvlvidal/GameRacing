
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mvidal
 */
public class Recursos {
    
    private static Recursos singleton = null; //Objeto unico da classe
    private BufferedImage ultima_posicao;
    
    private Recursos(){}
    
    public static Recursos getInstance(){
    
        if(singleton == null){            
            singleton = new Recursos();
        }
        return singleton;
    }
    
    //Métodos
    public BufferedImage cortarImagem(int x1, int y1, int x2, int y2, BufferedImage img){
    
        int largura = x2 - x1;
        int altura = y2 - y1;
        
        return img.getSubimage(x1, y1, largura, altura);
    }

    public BufferedImage getUltima_posicao() {
        return ultima_posicao;
    }

    public void setUltima_posicao(BufferedImage ultima_posicao) {
        this.ultima_posicao = ultima_posicao;
    }
      
}
