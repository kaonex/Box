package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class BigAirplane extends Flyer implements Rewarding,Enemy{
    public static final int grade = 70;
    public int getGrade() {
        return BigAirplane.grade;
    }

    static BufferedImage img;
    static BufferedImage ember0;
    static BufferedImage ember1;
    static BufferedImage ember2;
    static BufferedImage ember3;

    private static int healthPoint = 5;

    public static int getHealthPoint() {
        return healthPoint;
    }

    public static void setHealthPoint(int healthPoint) {
        BigAirplane.healthPoint = healthPoint;
    }

    static {
        try {
            img = ImageIO.read(BigAirplane.class.getResourceAsStream("img/bigplane.png"));
            ember0 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/bigplane_ember0.png"));
            ember1 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/bigplane_ember1.png"));
            ember2 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/bigplane_ember2.png"));
            ember3 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/bigplane_ember3.png"));
        } catch (IOException ignored) { }
    }

    private int fallSpeed = 4;

    public BigAirplane() {
        super(new Random().nextInt(300), -BigAirplane.img.getHeight(), BigAirplane.img);
        setHealth(healthPoint);
    }

    public int getFallSpeed() {
        return fallSpeed;
    }

    public void setFallSpeed(int fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public void move() {
        int y = getY();
        y += this.fallSpeed;
        setY(y);
    }

    public int count =0;
    @Override
    public void die() {
        switch (count){
            case 0->{setImg(BigAirplane.ember0);
                count++;}
            case 1->{setImg(BigAirplane.ember1);
                count++;}
            case 2->{setImg(BigAirplane.ember2);
                count++;}
            case 3->{setImg(BigAirplane.ember3);
                count++;}
        }
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public void getReward(Hero hero) {
        int random=new Random().nextInt(100);
        if(random<=90)
            hero.setHealth(hero.getHealth()+1);
        else
            hero.setBulletNum(hero.getBulletNum()+100);

    }
}
