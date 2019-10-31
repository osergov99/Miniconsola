package clases;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;

public class Accion implements Runnable {

    JFrame frame;
    FlowLayout c = new FlowLayout();
    JButton Botones = new JButton();
    JButton btnSalir = new JButton();
    JPanel opciones = new JPanel();
    JPanel cartaspanel = new JPanel();
    private boolean caraUp = false;
    private ImageIcon i;
    private ImageIcon i2;
    volatile boolean ejecutar = true;
    private JButton[] pbtn = new JButton[2];
    private boolean primerc = false;
    private int puntaje = 0;
    private int puntajewey = 4;
    JLabel p = new JLabel();
    JPanel cartas = new JPanel();
    JButton carta[] = new JButton[16];
    JSpinner spipunt = new JSpinner();
    l ne = new l();
    int pos = -200;
    AudioClip sonido;
    AudioClip sfondo;
    AudioClip logrosonido;
    JMenuBar es = new JMenuBar();
    JLabel ani = new JLabel();
    JLabel an = new JLabel();
    JButton logro = new JButton();
    JButton logromanco = new JButton();
    boolean on = true;
    boolean on2 = true;
    boolean on3 = true;
    String logroo = "/material/LOGRO.gif";
    Thread hilo = new Thread(this);
    Thread hilo2 = new Thread(this);
    Thread hilo3 = new Thread(this);
    JLabel nn = new JLabel();

    int pos2 = -50;
    int cc = 0;

    public Accion() {
        Creacion_Ventana();
        panel_cartas();
        panel_op();
        frame.setVisible(true);
    }

    public void panel_op() {
        cartas.setLayout(new GridLayout(4, 4, 0, 0));
        cartas.setBounds(200, 0, 510, 720);
        frame.getContentPane().add(cartas);
        int[] numbers = ne.getr();
        for (int i = 0; i < carta.length; i++) {
            carta[i] = new JButton();
            carta[i].setIcon(new ImageIcon(getClass().getResource("/material/poke.jpeg")));
            carta[i].setDisabledIcon(new ImageIcon(getClass().getResource("/material/k" + numbers[i] + ".jpg")));
            cartas.add(carta[i]);
        }
        cartas.setVisible(false);
        addListeners();
    }

    private void pregwin() {

        if (!carta[0].isEnabled() && !carta[1].isEnabled() && !carta[3].isEnabled() && !carta[4].isEnabled() && !carta[6].isEnabled() && !carta[7].isEnabled()
                && !carta[8].isEnabled() && !carta[9].isEnabled() && !carta[10].isEnabled() && !carta[11].isEnabled() && !carta[12].isEnabled()
                && !carta[13].isEnabled() && !carta[14].isEnabled() && !carta[15].isEnabled()) {
            cartas.setVisible(false);
            an.setBounds(250, 200, 400, 375);
            an.setIcon(new ImageIcon(getClass().getResource("/material/gokuwin.gif")));
            frame.getContentPane().add(an);
            AudioClip swin = java.applet.Applet.newAudioClip(getClass().getResource("/material/scount.wav"));
            puntaje += 30;
            swin.play();
            JOptionPane.showMessageDialog(frame, "Felicidades, usted ha ganado. Su puntaje es: " + puntaje, "Win!!", JOptionPane.INFORMATION_MESSAGE);
            puntaje = 0;
            p.setText("Su puntaje:  " + 0);
            sfondo.stop();
            swin.stop();
            Botones.setEnabled(true);

        } else {
        }
    }

    public void Creacion_Ventana() {
        frame = new JFrame();
        frame.setSize(720, 750);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addListeners() {
        for (int i = 0; i < 16; i++) {
            carta[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    sonido = java.applet.Applet.newAudioClip(getClass().getResource("/material/Pokebola.wav"));
                    sonido.play();
                    JButton evento = (JButton) evt.getSource();
                    btnEnabled(evento);
                }
            });
            carta[i].addMouseListener(new MouseAdapter() {
                public void mouseExited(MouseEvent evt) {
                    compare();
                    if (puntajewey == 0) {
                        hilo.start();
                        puntajewey = 500;
                        logrosonido = java.applet.Applet.newAudioClip(getClass().getResource("/material/sonidologro.wav"));
                        logrosonido.play();
                    }
                }
            });

        }

    }

    private void compare() {
        if (caraUp && primerc) {
            if (i.getDescription().compareTo(i2.getDescription()) != 0) {
                pbtn[0].setEnabled(true);
                pbtn[1].setEnabled(true);
                if (puntaje == 0) {
                    puntaje += 0;
                    puntajewey -= 1;
                    p.setText("Puntaje: " + puntaje);
                }
                if (puntaje >= 10) {
                    puntaje -= 10;

                    p.setText("Puntaje: " + puntaje);
                }

            }
            if (i.getDescription().compareTo(i2.getDescription()) == 0) {
                ani.setIcon(new ImageIcon(getClass().getResource("/material/sayaxd.gif")));
                puntaje += 30;
                p.setText("Puntaje: " + puntaje);
            }
            caraUp = false;

        }

    }

    private void btnEnabled(JButton btn) {
        if (!caraUp) {
            btn.setEnabled(false);
            i = (ImageIcon) btn.getDisabledIcon();
            pbtn[0] = btn;
            caraUp = true;
            primerc = false;
        } else {
            btn.setEnabled(false);
            i2 = (ImageIcon) btn.getDisabledIcon();
            pbtn[1] = btn;
            primerc = true;
            pregwin();
        }
    }

    public void panel_cartas() {
//Creacion del boton*****************************             
        p.setBounds(20, 10, 200, 30);
        p.setText("Su puntaje:  " + 0);
        Botones.setText("EMPEZAR");
        p.setBackground(new Color(204, 0, 0));
        p.setFont(new Font("Arial", 4, 20));
        Botones.setFont(new Font("Arial", 1, 12));
        Botones.setHorizontalAlignment(SwingConstants.CENTER);
        Botones.setBounds(30, 150, 150, 30);
        ani.setIcon(new ImageIcon(getClass().getResource("/material/gokui.gif")));
        ani.setBounds(0, 250, 200, 200);
        cartaspanel.add(ani);
        Botones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sfondo = java.applet.Applet.newAudioClip(getClass().getResource("/material/sfondo.wav"));
                sfondo.play();
                cartas.setVisible(true);
                Botones.setEnabled(false);
                puntaje = 0;
                int[] numbers = ne.getr();
                for (int i = 0; i < carta.length; i++) {
                    carta[i].setIcon(new ImageIcon(getClass().getResource("/material/poke.jpeg")));
                    carta[i].setDisabledIcon(new ImageIcon(getClass().getResource("/material/k" + numbers[i] + ".jpg")));
                    cartas.add(carta[i]);
                    carta[i].setEnabled(true);
                }
            }
        });
        ////////////////////////////////////////////////))))))))))))))))))))))

        btnSalir.setText("SALIR");
        btnSalir.setFont(new Font("Arial", 1, 12));
        btnSalir.setHorizontalAlignment(SwingConstants.CENTER);
        btnSalir.setBounds(new Rectangle(30, 70, 150, 30));
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu(false);
                sfondo.stop();
                frame.dispose();
            }
        });
        //////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////// Panel
        cartaspanel.add(Botones);
        cartaspanel.add(btnSalir);
        cartaspanel.add(p);
        cartaspanel.setLayout(null);
        cartaspanel.setBounds(new Rectangle(0, 0, 200, 750));
        ImageIcon nnn = new ImageIcon(getClass().getResource("/material/pokemonfondo.png"));
        nn.setIcon(nnn);
        nn.setBounds(0, 0, 200, 750);
        nn.setOpaque(true);
        nn.setLayout(null);
        cartaspanel.add(nn);
        frame.getContentPane().add(cartaspanel);

    }

    public void logro(int x) {
        if (x == 0) {
            logro.setBounds(x, 500, 200, 100);
            logro.setIcon(new ImageIcon(getClass().getResource("/material/manco.gif")));
            logromanco.setIcon(new ImageIcon(getClass().getResource(logroo)));
            logromanco.setBounds(x, 450, 200, 50);
            logromanco.setLayout(null);
            cartaspanel.add(logromanco);
            cartaspanel.add(logro);
            on = false;
            on2 = false;
            on3 = true;
            hilo2.start();
        }
        if (x == 1) {
            on2 = true;
            on = false;
            on3 = false;
            hilo3.start();
        }
        if (x == -200) {
            logro.setBounds(x, 500, 200, 100);
            logro.setIcon(new ImageIcon(getClass().getResource("/material/manco.gif")));
            logromanco.setIcon(new ImageIcon(getClass().getResource(logroo)));
            logromanco.setBounds(x, 450, 200, 50);
            logromanco.setLayout(null);
            cartaspanel.add(logro);
            cartaspanel.add(logromanco);
            ani.setIcon(new ImageIcon(getClass().getResource("/material/sayaxd.gif")));

            on2 = false;
        }

    }

    @Override
    public void run() {

        if (pos <= -25) {
            logro.setBounds(pos, 500, 200, 100);
            logromanco.setBounds(pos, 450, 200, 50);
            while (on) {
                if (pos <= -25) {
                    pos += 25;
                    logro.setLocation(pos, 500);
                    logro.setIcon(new ImageIcon(getClass().getResource("/material/manco.gif")));
                    cartaspanel.add(logro);
                    logromanco.setIcon(new ImageIcon(getClass().getResource(logroo)));
                    logromanco.setLayout(null);
                    logromanco.setLocation(pos, 450);
                    cartaspanel.add(logromanco);
                } else {
                    logro(0);
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Accion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (on2 == true) {

            while (on2) {
                if (pos2 >= -200) {
                    pos2 -= 25;
                    logro.setLocation(pos2, 500);
                    logro.setIcon(new ImageIcon(getClass().getResource("/material/manco.gif")));
                    cartaspanel.add(logro);
                    logromanco.setIcon(new ImageIcon(getClass().getResource(logroo)));
                    logromanco.setLayout(null);
                    logromanco.setLocation(pos2, 450);
                    cartaspanel.add(logromanco);
                } else {
                    logro(-200);
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Accion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (on3 == true) {
            while (on3) {
                if (cc < 11) {
                    if (cc < 6) {
                        ani.setIcon(new ImageIcon(getClass().getResource("/material/doit.gif")));
                        cc += 1;
                    } else {
                        cc += 1;
                        ani.setIcon(new ImageIcon(getClass().getResource("/material/gokue.gif")));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Accion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    logro(1);
                }
            }
        }

    }

}
