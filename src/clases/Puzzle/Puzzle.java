package clases.Puzzle;

import clases.Menu;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class Puzzle implements ActionListener {

    private JDialog vistaPrevia;
    private JToolBar toolBar_vistaprevia;
    private JButton agregarImg, play, salir;
    private ImageIcon imageIcon_btn;
    private Icon icon_Btn;
    private JRadioButton easy, medium, hard;
    private ButtonGroup dificultad;
    private BufferedImage imagenPrincipal;
    private JLabel labelImagen;
    private ImageIcon imageIcon_vistaprevia;
    private Icon icon_vistaprevia;
    private ArrayList<Image> cuadritos;
    private int anchoCuadrito, altoCuadrito;

    private JFileChooser chooser;

    private JFrame frame;
    private JPanel panel2;
    private JToolBar toolBar;
    private JButton settings, ordenado;
    private JLabel time;
    private JButton[] btns;
    private ImageIcon imageIcon;
    private Icon icon;
    private Point vacio, puntero;
    private Point[] posiciones, posicionesIniciales;
    private Stack<Integer> numerosRandom;
    private Random random;

    private Tiempo tiempo;
    private boolean stopTime;
    private JDialog_GameOver dialog_GameOver;
    private AudioClip suuu;

    public Puzzle() {
        VentanaVistaPrevia();
        vistaPrevia.setVisible(true);
    }

    private void VentanaVistaPrevia() {
        vistaPrevia = new JDialog();
        vistaPrevia.setTitle("VISTA PREVIA");
        vistaPrevia.setSize(400, 400);
        vistaPrevia.setLocationRelativeTo(null);
        vistaPrevia.setResizable(false);
        vistaPrevia.setLayout(null);

        toolBar_vistaprevia = new JToolBar();
        toolBar_vistaprevia.setBounds(0, 0, vistaPrevia.getWidth(), 30);
        toolBar_vistaprevia.setLayout(null);
        toolBar_vistaprevia.setFloatable(false);
        vistaPrevia.add(toolBar_vistaprevia);

        agregarImg = new JButton();
        agregarImg.setBounds(5, 0, 30, 30);
        agregarImg.addActionListener(this);
        imageIcon_btn = new ImageIcon(getClass().getResource("/material/file_add.png"));
        icon_Btn = new ImageIcon(imageIcon_btn.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        agregarImg.setIcon(icon_Btn);
        toolBar_vistaprevia.add(agregarImg);

        easy = new JRadioButton("Fácil");
        easy.setSelected(true);
        easy.setBounds(agregarImg.getX() + agregarImg.getWidth() + 30, 5, 60, 20);
        toolBar_vistaprevia.add(easy);

        medium = new JRadioButton("Medio");
        medium.setBounds(easy.getX() + easy.getWidth() + 20, 5, 70, 20);
        toolBar_vistaprevia.add(medium);

        hard = new JRadioButton("Difícil");
        hard.setBounds(medium.getX() + medium.getWidth() + 20, 5, 60, 20);
        toolBar_vistaprevia.add(hard);

        dificultad = new ButtonGroup();
        dificultad.add(easy);
        dificultad.add(medium);
        dificultad.add(hard);

        play = new JButton("PLAY");
        play.setBounds(hard.getX() + hard.getWidth() + 30, 0, 50, 30);
        play.setBackground(Color.GREEN);
        play.addActionListener(this);
        toolBar_vistaprevia.add(play);

        chooser = new JFileChooser();
    }

    private void seleccionarImagen() {
        int resp = chooser.showOpenDialog(null);
        if (resp == JFileChooser.APPROVE_OPTION) {
            agregarImagen(chooser.getSelectedFile().getPath());
        }
    }

    private void agregarImagen(String path) {
        try {
            imagenPrincipal = ImageIO.read(new File(path));
            labelImagen = new JLabel();
            labelImagen.setBounds(0, toolBar_vistaprevia.getY() + toolBar_vistaprevia.getHeight(), vistaPrevia.getWidth(), vistaPrevia.getHeight() - toolBar_vistaprevia.getHeight());
            vistaPrevia.add(labelImagen);
            imageIcon_vistaprevia = new ImageIcon(imagenPrincipal);
            icon_vistaprevia = new ImageIcon(imageIcon_vistaprevia.getImage().getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_DEFAULT));
            labelImagen.setIcon(icon_vistaprevia);
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private void fragmentarImagen(int filas, int columnas) {
        cuadritos = new ArrayList<>();
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                cuadritos.add(frame.createImage(new FilteredImageSource(imagenPrincipal.getSource(), new CropImageFilter(x * (imagenPrincipal.getWidth() / columnas), y * imagenPrincipal.getHeight() / filas, imagenPrincipal.getWidth() / columnas, imagenPrincipal.getHeight() / filas))));
            }
        }
    }

    private void caracteristicasFrame() {
        frame = new JFrame("Puzzle");
        frame.setSize(380, 430);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(getClass().getResource("/material/puzzle-icon.png")).getImage());
        frame.setLayout(null);

        suuu = java.applet.Applet.newAudioClip(getClass().getResource("/material/Suuuu.wav"));

        toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 380, 30);
        toolBar.setLayout(null);
        toolBar.setFloatable(false);
        frame.add(toolBar);

        settings = new JButton();
        settings.setBounds(0, 0, 30, 30);
        ImageIcon imageicon_settings = new ImageIcon(getClass().getResource("/material/icono_configuracion.png"));
        Icon icon_settings = new ImageIcon(imageicon_settings.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        settings.setIcon(icon_settings);
        settings.addActionListener(this);
        toolBar.add(settings);

        time = new JLabel();
        time.setBounds(toolBar.getWidth() / 2 - 40, 0, 80, 30);
        time.setOpaque(true);
        time.setBackground(Color.BLACK);
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setFont(new Font("arial", Font.ROMAN_BASELINE, 25));
        time.setForeground(Color.WHITE);
        toolBar.add(time);

        salir = new JButton("EXIT");
        salir.setBounds(time.getX() + time.getWidth() + 40, 0, 50, 30);
        salir.setOpaque(true);
        salir.setBackground(Color.RED);
        salir.setForeground(Color.WHITE);
        salir.addActionListener(this);
        toolBar.add(salir);

        ordenado = new JButton();
        ordenado.setBounds(toolBar.getWidth() - 36, 0, 30, 30);
        ImageIcon imageicon_ordenado = new ImageIcon(getClass().getResource("/material/icono_ordenado.png"));
        Icon icon_ordenado = new ImageIcon(imageicon_ordenado.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        ordenado.setIcon(icon_ordenado);
        ordenado.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < posicionesIniciales.length; i++) { //OBTIENE LAS POSICIONES DE LOS BOTONES
                    posicionesIniciales[i] = btns[i].getLocation();
                }

                for (int i = 0; i < btns.length; i++) { //LE ASIGNA LAS POSICIONES CORRECTAS A CADA BOTON
                    btns[i].setLocation(posiciones[i]);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (int i = 0; i < btns.length; i++) { //DEVUELVE LOS BOTONES A SU POSICION ANTERIOR
                    btns[i].setLocation(posicionesIniciales[i]);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        toolBar.add(ordenado);

        panel2 = new JPanel();
        panel2.setBounds(0, 30, 380, 380);
        panel2.setLayout(null);
        frame.add(panel2);
    }

    private boolean Won() {
        boolean gano = false;
        for (int i = 0; i < btns.length; i++) {
            gano = btns[i].getLocation().x == posiciones[i].x && btns[i].getLocation().y == posiciones[i].y;
            if (!gano) {
                break;
            }
        }
        return gano;
    }

    private void componentes() {
        if (easy.isSelected()) {
            btns = new JButton[8];
        } else if (medium.isSelected()) {
            btns = new JButton[15];
        } else if (hard.isSelected()) {
            btns = new JButton[24];
        }

        anchoCuadrito = (int) (panel2.getWidth() / Math.sqrt(btns.length + 1));
        altoCuadrito = (int) (panel2.getHeight() / Math.sqrt(btns.length + 1));

        posicionesIniciales = new Point[btns.length];

        posiciones = new Point[btns.length + 1];

        int index = 0;
        for (int y = 0; y <= (Math.sqrt(posiciones.length) - 1) * altoCuadrito; y += altoCuadrito) {
            for (int x = 0; x <= (Math.sqrt(posiciones.length) - 1) * anchoCuadrito; x += anchoCuadrito) {
                posiciones[index++] = new Point(x, y);
            }
        }

        numeroRandom();

        fragmentarImagen((int) Math.sqrt(posiciones.length), (int) Math.sqrt(posiciones.length));

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton();
            btns[i].setSize(anchoCuadrito, altoCuadrito);
            btns[i].setLocation(posiciones[numerosRandom.pop()]);
            btns[i].addActionListener(this);
            panel2.add(btns[i]);

            imageIcon = new ImageIcon(cuadritos.get(i));
            icon = new ImageIcon(imageIcon.getImage().getScaledInstance(btns[i].getWidth(), btns[i].getHeight(), Image.SCALE_DEFAULT));
            btns[i].setIcon(icon);
        }

        vacio = new Point(posiciones[numerosRandom.pop()]);
    }

    private void numeroRandom() {
        random = new Random();
        numerosRandom = new Stack<>();
        int x;
        boolean agregar;
        for (int i = 0; i < posiciones.length; i++) {
            agregar = true;
            while (agregar) {
                x = random.nextInt(posiciones.length);
                if (!numerosRandom.contains(x)) {
                    numerosRandom.push(x);
                    agregar = false;
                }
            }
        }
    }

    private boolean estaCerca(int i) {
        return (btns[i].getLocation().x == vacio.x || btns[i].getLocation().y == vacio.y)
                && ((btns[i].getLocation().x + anchoCuadrito == vacio.x || btns[i].getLocation().x - anchoCuadrito == vacio.x)
                || (btns[i].getLocation().y + altoCuadrito == vacio.y || btns[i].getLocation().y - altoCuadrito == vacio.y));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*----------------------------- BOTONES VISTA PREVIA --------------------------*/
        if (e.getSource() == agregarImg) {
            seleccionarImagen();
        }

        if (e.getSource() == play) {
            if (labelImagen != null) {
                if (frame != null) {
                    frame.dispose();
                }
                caracteristicasFrame();
                componentes();
                frame.setVisible(true);
                tiempo = new Tiempo();
                tiempo.start();

                vistaPrevia.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Agregue una imagen");
            }
        }
        /*--------------------------------- BOTONES PUZZLE -----------------------------*/

        if (frame != null) { //SOLO SE EJECUTARÁ ESTE CODIGO SI EL OBJETO FRAME NO ES NULL.
            for (int i = 0; i < btns.length; i++) {
                if (e.getSource() == btns[i] && estaCerca(i)) {
                    puntero = btns[i].getLocation();
                    btns[i].setLocation(vacio);
                    vacio = puntero;

                    if (Won()) {
                        stopTime = true;
                        suuu.play();
                        dialog_GameOver = new JDialog_GameOver(time.getText(), frame);
                        frame.dispose();
                    }
                }
            }

            if (e.getSource() == salir) {
                new Menu(false);
                frame.dispose();
            }

            if (e.getSource() == settings) {
                stopTime = true;
                VentanaVistaPrevia();
                vistaPrevia.setVisible(true);
            }
        }
    }

    public class Tiempo extends Thread {

        @Override
        public void run() {
            for (int min = 0, seg = 0; min < 60 && !stopTime; seg++) {
                try {
                    if (seg == 60) {
                        min++;
                        seg = 0;
                    }

                    if (seg < 10) {
                        if (min < 9) {
                            time.setText("0" + min + ":0" + seg);
                        } else {
                            time.setText(min + ":0" + seg);
                        }
                    } else {
                        if (min < 9) {
                            time.setText("0" + min + ":" + seg);
                        } else {
                            time.setText(min + ":" + seg);
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
            }
        }

    }
}
