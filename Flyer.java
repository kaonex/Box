package cn.packege.oop.flyshooter;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Flyer{
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage img;
    private int health=1;

    public Flyer() {
    }

    @Override
    public String toString() {
        return
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", health=" + health ;
    }
    public int getCount() {
        return 0;
    }

    public Flyer(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.height = img.getHeight();
        this.width = img.getWidth();
    }


    public abstract void move();

    public abstract  void die();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }


}
