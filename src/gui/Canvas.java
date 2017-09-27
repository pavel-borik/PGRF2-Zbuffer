package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import model.Axis;
import model.Cube;
import model.Cube2;
import model.Simplex;
import model.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotX;
import transforms.Mat4Scale;
import transforms.Mat4Transl;
import transforms.Vec3D;
import visualization.Renderer;

public class Canvas {

    private JFrame frame;
    private JPanel panel, panel2;
    private JRadioButton rb1, rb2, rb3, rb4;
    private BufferedImage img;
    private Renderer renderer;
    private Solid s1, s2, axis;
    private boolean wiredModel = true;
    private List<Solid> solidList;
    private Camera cam;
    private Mat4 defaultModel = new Mat4Identity();
    private double dx, dy, oldX, oldY, newX, newY;

    public Canvas(int width, int height) {
        initGui(width, height);
        renderObjects();
    }

    public static void main(String[] args) {
        Canvas canvas = new Canvas(800, 600);
        SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(canvas::start))));
    }

    public void renderObjects() {
        renderer = new Renderer(img);
        solidList = new ArrayList<>();
        s1 = new Cube();
        s2 = new Simplex();
        axis = new Axis();

        solidList.add(axis);
        solidList.add(s1);
        solidList.add(s2);

        cam = new Camera().withPosition(new Vec3D(5, 4, 6)).withAzimuth(Math.PI + Math.atan(4.0 / 5))
                .withZenith(-Math.PI / 4);

        renderer.setModel(defaultModel);
        renderer.setProjection(new Mat4PerspRH(Math.PI / 4, (double) img.getHeight() / img.getWidth(), 0.01, 100));
        renderer.setView(cam.getViewMatrix());
    }

    public void initGui(int width, int height) {
        frame = new JFrame();
        frame.setTitle("Pavel Boøík - PGRF2 - Task 1");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));

        panel2 = new JPanel();

        ButtonGroup bg1 = new ButtonGroup();
        rb1 = new JRadioButton("Perspective");
        rb2 = new JRadioButton("Orthogonal");
        bg1.add(rb1);
        bg1.add(rb2);
        rb1.setSelected(true);

        ButtonGroup bg2 = new ButtonGroup();
        rb3 = new JRadioButton("WireFrame model");
        rb4 = new JRadioButton("Z-Buffer");
        bg2.add(rb3);
        bg2.add(rb4);
        rb3.setSelected(true);

        panel2.add(rb1);
        panel2.add(rb2);
        panel2.add(rb3);
        panel2.add(rb4);

        panel2.setBackground(new Color(0x2f2f2f));

        rb1.addActionListener(e -> {
            clear(0x2f2f2f);
            renderer.setModel(defaultModel);
            renderer.setProjection(new Mat4PerspRH(Math.PI / 4, (double) img.getHeight() / img.getWidth(), 0.01, 100));
            renderer.draw(solidList, wiredModel);
            present();
            frame.requestFocus();
        });

        rb2.addActionListener(e -> {
            clear(0x2f2f2f);
            renderer.setModel(defaultModel);
            renderer.setProjection(new Mat4OrthoRH(img.getWidth() / 75, img.getHeight() / 75, -10.0, 100.0));
            renderer.draw(solidList, wiredModel);
            present();
            frame.requestFocus();
        });

        rb3.addActionListener(e -> {
            clear(0x2f2f2f);
            wiredModel = true;
            renderer.draw(solidList, wiredModel);

            present();
            frame.requestFocus();
        });

        rb4.addActionListener(e -> {
            clear(0x2f2f2f);
            wiredModel = false;
            renderer.draw(solidList, wiredModel);
            present();
            frame.requestFocus();
        });
        frame.requestFocus();
        frame.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                clear(0x2f2f2f);
                if (key == KeyEvent.VK_K) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Transl(0, .5, 0)));
                }
                if (key == KeyEvent.VK_J) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Transl(0, -.5, 0)));
                }
                if (key == KeyEvent.VK_I) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Transl(-.5, 0, 0)));
                }
                if (key == KeyEvent.VK_U) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Transl(.5, 0, 0)));
                }
                if (key == KeyEvent.VK_O) {
                    renderer.setModel(renderer.getModel().mul(new Mat4RotX(0.7)));
                }
                if (key == KeyEvent.VK_P) {
                    renderer.setModel(renderer.getModel().mul(new Mat4RotX(-0.7)));
                }
                if (key == KeyEvent.VK_N) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Scale(.5, .5, .5)));
                }
                if (key == KeyEvent.VK_M) {
                    renderer.setModel(renderer.getModel().mul(new Mat4Scale(1.5, 1.5, 1.5)));
                }
                if (key == KeyEvent.VK_W) {
                    cam = cam.forward(1);
                    renderer.setView(cam.getViewMatrix());
                }
                if (key == KeyEvent.VK_S) {
                    cam = cam.backward(1);
                    renderer.setView(cam.getViewMatrix());
                }
                if (key == KeyEvent.VK_A) {
                    cam = cam.left(1);
                    renderer.setView(cam.getViewMatrix());
                }
                if (key == KeyEvent.VK_D) {
                    clear(0x2f2f2f);
                    cam = cam.right(1);
                    renderer.setView(cam.getViewMatrix());
                }
                renderer.draw(solidList, wiredModel);
                present();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear(0x2f2f2f);
                newX = e.getX();
                newY = e.getY();
                dx = oldX - newX;
                dy = oldY - newY;

                cam = cam.addAzimuth(Math.PI * dx / (double) img.getWidth());
                cam = cam.addZenith(Math.PI * dy / (double) img.getHeight());

                renderer.setView(cam.getViewMatrix());
                renderer.draw(solidList, wiredModel);

                oldX = e.getX();
                oldY = e.getY();

                present();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);

    }

    public void clear(int color) {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(color));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present() {
        if (panel.getGraphics() != null)
            panel.getGraphics().drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear(0x2f2f2f);
        renderer.draw(solidList, wiredModel);
    }

    public void start() {
        draw();
        present();
        showWelcomeMessage();
    }

    public void showWelcomeMessage() {
        new JOptionPane();
        JOptionPane.showMessageDialog(null, "Ovládání: \n" +
                "Pohyb kamery - WASD \n" +
                "Rozhlížení - levé tlaèítko myši  \n" +
                "Rotace podle osy X - O, P \n" +
                "Posunutí objektù - J, K, I, U \n" +
                "Zmìna mìøítka - N, M \n \n" +
                "Pavel Boøík, IM3-p, 13. 3. 2017 \n");
    }

}