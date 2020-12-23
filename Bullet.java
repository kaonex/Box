package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends Flyer {
    static BufferedImage img;

    static {
        try {
            img = ImageIO.read(Bullet.class.getResourceAsStream("img/bullet.png"));
        } catch (IOException ignored) {
        }
    }

    private int speed = 15;

    public Bullet(int x, int y) {
        super(x, y, Bullet.img);
    }

    @Override
    public void move() {
        int y = getY();
        y -= speed;
        setY(y);
    }


    @Override
    public void die() {

    }


}
