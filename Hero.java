package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Hero extends Flyer {
    static BufferedImage img1;
    static BufferedImage img2;
    static BufferedImage ember0;
    static BufferedImage ember1;
    static BufferedImage ember2;
    static BufferedImage ember3;

    static {
        try {
            img1 = ImageIO.read(Hero.class.getResourceAsStream("img/hero0.png"));
            img2 = ImageIO.read(Hero.class.getResourceAsStream("img/hero1.png"));
            ember0 = ImageIO.read(Hero.class.getResourceAsStream("img/hero_ember0.png"));
            ember1 = ImageIO.read(Hero.class.getResourceAsStream("img/hero_ember1.png"));
            ember2 = ImageIO.read(Hero.class.getResourceAsStream("img/hero_ember2.png"));
            ember3 = ImageIO.read(Hero.class.getResourceAsStream("img/hero_ember3.png"));
        } catch (IOException ignored) {
        }
    }

    public ArrayList<Bullet> bullets = new ArrayList<>();
    public static int healthPoint = 5;
    int bulletIndex = 0; //外部控制创建子弹的速度
    private int BulletNum = 1; //一次性生成的子弹数

    private int source =0;

    public int getSource() {
        return source;
    }

    public void addSource(int source) {
        this.source += source;
    }

    public Hero(int x, int y) {
        super(x, y, Hero.img2);
        setHealth(healthPoint);
    }

    public void getBullet() {
        bulletIndex++;
        if (bulletIndex % 5 == 0) {
            if (this.getBulletNum() == 1) {
                this.bullets.add(new Bullet(this.getX() + Hero.img1.getWidth() / 2, this.getY()));
            } else {
                this.bullets.add(new Bullet(this.getX() + Hero.img1.getWidth() / 2 - 25, this.getY()));
                this.bullets.add(new Bullet(this.getX() + Hero.img1.getWidth() / 2 + 25, this.getY()));
            }
            bulletIndex = 0;
        }
        BulletNum=Math.max(--BulletNum,1);
    }

    public void Shooting() {
        getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getY() < 20) {
                bullets.remove(i);
                i--;
            } else {
                bullets.get(i).move();
            }
        }
    }

    public int getBulletNum() {
        return BulletNum;
    }

    public void setBulletNum(int bulletNum) {
        BulletNum = bulletNum;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        Hero.healthPoint = healthPoint;
    }

    public int count_move =1;
    @Override
    public void move() {
        if (count_move == 1) {
            setImg(img1);
            count_move = -1;
        } else {
            setImg(img2);
            count_move = 1;
        }

    }

    private boolean be_hurt =false;
    public void beHurt(){
        be_hurt =true;
    }

    private int count_die =0;

    @Override
    public void die() {
        switch (count_die){
            case 0->{setImg(ember0);
                count_die++;}
            case 1->{setImg(ember1);
                count_die++;}
            case 2->{setImg(ember2);
                count_die++;}
            case 3->{setImg(ember3);
                count_die++;}
            case 4->{
                count_die =0;}
        }

    }

    public  void damaged() {
        if(be_hurt && getHealth()>0){
            die();
            if(count_die ==3){
                count_die =0;
                be_hurt =false;
            }
        }
        else{
            move();
        }
    }


}
