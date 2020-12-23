package cn.packege.oop.flyshooter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainTest extends JPanel {

    public static final int HEIGHT = 700;
    public static final int WIDTH = 450;

    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

    public static java.util.Timer timer = new Timer(); //Timer计时器

    public static BufferedImage bg;  //背景图片
    public static BufferedImage start;
    public static BufferedImage over;
    public static BufferedImage pause;

    static Hero hero;
    static ArrayList<Flyer> enemys = new ArrayList<>();

    static {
        try {
            bg = ImageIO.read(MainTest.class.getResourceAsStream("img/background.png"));
            start = ImageIO.read(MainTest.class.getResourceAsStream("img/start.png"));
            pause = ImageIO.read(MainTest.class.getResourceAsStream("img/pause.png"));
            over = ImageIO.read(MainTest.class.getResourceAsStream("img/gameover.png"));
        } catch (IOException ignored) {
        }
        hero = new Hero(bg.getWidth() / 2 - Hero.img1.getWidth(),
                bg.getHeight() - Hero.img1.getHeight() * 3);


    }

    public int model = START;
    ArrayList<Flyer> dieEnemys = new ArrayList<>();   //捕捉死亡敌机的集合
    int enemyIndex = 0;//用于外部控制创造敌人的速度

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainTest mainTest = new MainTest();
        window.add(mainTest);
        mainTest.action();


    }

    public void action() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (model == RUNNING) {
                    hurtHero();
                    getEnemy();
                    hero.Shooting();
                    enemysMove();
                    hurtEnemy();
                    dieOnebyOne();
                    hero.damaged();
                    repaint();
                }
            }
        }, 0, 50);
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (model == START)
                    model = RUNNING;
                else if (model == GAME_OVER) {
                    model = START;
                    hero = new Hero(bg.getWidth() / 2 - Hero.img1.getWidth(),
                            bg.getHeight() - Hero.img1.getHeight() * 3);
                    enemys.clear();
                    hero.bullets.clear();
                    dieEnemys.clear();
                }

                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (model == PAUSE) {
                    super.mouseEntered(e);
                    model = RUNNING;
                    repaint();
                }


            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (model == RUNNING) {
                    model = PAUSE;
                    repaint();
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (model == RUNNING) {
                    hero.setX(e.getX() - Hero.img1.getWidth() / 2);
                    hero.setY(e.getY() - Hero.img1.getHeight() / 2);
                }
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    private void getEnemy()//获取敌机
    {
        enemyIndex++;
        if (enemyIndex % 15 == 0) {
            int x = new Random().nextInt(100) + 1;
            if (x <= 80)
                enemys.add(new Airplane());
            else if (x <= 95)
                enemys.add(new BigAirplane());
            else
                enemys.add(new Bee());
            enemyIndex = 0;
        }
    }

    private void hurtHero()//己方单位与敌机碰撞
    {
        for (int i = 0; i < enemys.size(); i++) {
            if (hero.getHealth() == 0) {
                model = GAME_OVER;
                return;
            }

            if (touch(enemys.get(i), hero)) {
                hero.beHurt();
                enemyDie(i);
                i = Math.max(0, --i);
            }
        }
    }

    private void hurtEnemy() //敌机被子弹打中
    {
        for (int j = 0; j < hero.bullets.size(); j++) {
            for (int i = 0; i < enemys.size(); i++) {
                if (touch(enemys.get(i), hero.bullets.get(j))) {
                    hero.bullets.remove(j);
                    if (enemys.get(i).getHealth() == 0) {
                        enemyDie(i);
                        i = Math.max(0, --i);
                    }
                    j--;
                    break;
                }
            }
        }
    }

    private void dieOnebyOne()//死亡演出
    {
        for (Flyer dieEnemy : dieEnemys) {
            dieEnemy.die();
        }
    }

    private void enemyDie(int i) //判断敌机的死亡
    {
        Flyer enemy = enemys.get(i);
        dieEnemys.add(enemy);
        if (enemy instanceof Rewarding)
            (
                    (Rewarding) enemy
            ).getReward(hero);

        if (enemy instanceof Enemy) {
            hero.addSource(
                    ((Enemy) enemy).getGrade()
            );
        }
        enemys.remove(i);
    }

    private void enemysMove()//敌机的移动
    {
        for (int i = 0; i < enemys.size(); i++) {
            if (enemys.get(i).getY() > MainTest.HEIGHT) {
                enemys.remove(i);
                i--;
            } else {
                enemys.get(i).move();
            }
        }
    }

    private boolean touch(Flyer enemy, Flyer hero) //敌机与己方单位碰撞
    {
        int x = enemy.getX() - hero.getX();
        int y = enemy.getY() - hero.getY();
        if (x <= hero.getWidth() && x >= -enemy.getWidth()
                && y <= hero.getHeight() && y >= -enemy.getHeight()) {
            enemy.setHealth(enemy.getHealth() - 1);
            hero.setHealth(hero.getHealth() - 1);
            return true;
        }
        return false;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bg, 0, 0, WIDTH, HEIGHT, this);
        g.drawImage(hero.getImg(), hero.getX(), hero.getY(), this);
        paintBullet(g);
        paintEnemys(g);
        paintDieOne(g);
        paintModel(g);
        paintString(g);

    }

    private void paintDieOne(Graphics g)//画死亡单位
    {
        for (int i = 0; i < dieEnemys.size(); i++) {
            g.drawImage(dieEnemys.get(i).getImg(), dieEnemys.get(i).getX(), dieEnemys.get(i).getY(), this);
            if (dieEnemys.get(i).getCount() == 4) {
                dieEnemys.remove(i);
                i--;
            }
        }
    }

    private void paintEnemys(Graphics g)//画敌机
    {
        for (Flyer flyer : enemys) {
            g.drawImage(flyer.getImg(), flyer.getX(), flyer.getY(), this);
        }
    }

    private void paintBullet(Graphics g)//画子弹
    {
        for (Bullet bullet : hero.bullets) {
            g.drawImage(bullet.getImg(), bullet.getX(), bullet.getY(), this);
        }
    }

    private void paintModel(Graphics g) //画 画面模式
    {
        switch (model) {
            case START -> g.drawImage(start, 0, 0, WIDTH, HEIGHT, this);
            case PAUSE -> g.drawImage(pause, 0, 0, WIDTH, HEIGHT, this);
            case GAME_OVER -> g.drawImage(over, 0, 0, WIDTH, HEIGHT, this);

        }
    }

    private void paintString(Graphics g)//画己方飞机血量与分数
    {
        g.setColor(new Color(42, 44, 147));
        g.setFont(new Font("宋体", Font.BOLD, 15));
        g.drawString("分数：" + hero.getSource(), 0, 20);
        g.drawString("生命值：" + hero.getHealth(), 0, 40);
    }


}


