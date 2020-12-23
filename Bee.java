package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Bee extends Flyer implements Rewarding{

    static BufferedImage img;
    static BufferedImage ember0;
    static BufferedImage ember1;
    static BufferedImage ember2;
    static BufferedImage ember3;

    private int fallSpeedX = 5;
    private int fallSpeedY = 2;
    private  int healthPoint = 1;


    static {
        try {
            img = ImageIO.read(Bee.class.getResourceAsStream("img/bee.png"));
            ember0 = ImageIO.read(Bee.class.getResourceAsStream("img/bee_ember0.png"));
            ember1 = ImageIO.read(Bee.class.getResourceAsStream("img/bee_ember1.png"));
            ember2 = ImageIO.read(Bee.class.getResourceAsStream("img/bee_ember2.png"));
            ember3 = ImageIO.read(Bee.class.getResourceAsStream("img/bee_ember3.png"));
        } catch (IOException ignored) { }
    }
    public Bee() {
        super(new Random().nextInt(300), -Bee.img.getHeight(), Bee.img);
        setHealth(healthPoint);
    }

    public void move() {
        int y = getY();
        y += fallSpeedY;
        setY(y);

        int x = getX();

        x += fallSpeedX;

        if (x >= MainTest.WIDTH - Bee.img.getWidth() || x <= 0) {
            fallSpeedX = -fallSpeedX;
        }
        setX(getX() + fallSpeedX);

    }

    public int count =0;
    @Override
    public void die() {
        switch (count){
            case 0->{setImg(Bee.ember0);
                count++;}
            case 1->{setImg(Bee.ember1);
                count++;}
            case 2->{setImg(Bee.ember2);
                count++;}
            case 3->{setImg(Bee.ember3);
                count++;}
        }
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public void getReward(Hero hero) {
        int random=new Random().nextInt(100);
        if(random<=70)
            hero.setHealth(hero.getHealth()+1);
        else
            hero.setBulletNum(hero.getBulletNum()+100);
    }
}
