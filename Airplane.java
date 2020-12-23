package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Airplane extends Flyer implements Enemy{
    public static final int grade = 10;
    public  int getGrade(){
        return Airplane.grade;
    }
    static BufferedImage img;
    static BufferedImage ember0;
    static BufferedImage ember1;
    static BufferedImage ember2;
    static BufferedImage ember3;
    private  int healthPoint = 1;

    static {
        try {
            img = ImageIO.read(BigAirplane.class.getResourceAsStream("img/airplane.png"));
            ember0 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/airplane_ember0.png"));
            ember1 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/airplane_ember1.png"));
            ember2 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/airplane_ember2.png"));
            ember3 = ImageIO.read(BigAirplane.class.getResourceAsStream("img/airplane_ember3.png"));
        } catch (IOException ignored) { }
    }

    private int fallSpeed = 8;

    public Airplane() {
        super(new Random().nextInt(300), -Airplane.img.getHeight(), Airplane.img);
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
            case 0->{setImg(Airplane.ember0);
                count++;}
            case 1->{setImg(Airplane.ember1);
                count++;}
            case 2->{setImg(Airplane.ember2);
                count++;}
            case 3->{setImg(Airplane.ember3);
                count++;}
        }
    }

    public int getCount() {
        return this.count;
    }


}
