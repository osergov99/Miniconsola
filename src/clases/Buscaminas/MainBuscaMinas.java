package clases.Buscaminas;

import clases.Menu;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
/////////////////////////////APROX LINEA 175/////////////////////////////////////////////////
////////////////////////crear ventana emergente al perder///////////////////

public class MainBuscaMinas implements MouseListener {

    ImageIcon flagx = new ImageIcon(getClass().getResource("/material/bandErronea.png"));
    Icon banderax = new ImageIcon(flagx.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    ImageIcon flag = new ImageIcon(getClass().getResource("/material/bandera.png"));
    Icon bandera = new ImageIcon(flag.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    ImageIcon Icotitle = new ImageIcon(getClass().getResource("/material/Icono.png"));
    Icon Icotitulo = new ImageIcon(Icotitle.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
    ImageIcon mine = new ImageIcon(getClass().getResource("/material/Mina.png"));
    Icon mina = new ImageIcon(mine.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    ImageIcon mineG = new ImageIcon(getClass().getResource("/material/MinaGris.png"));
    Icon minaG = new ImageIcon(mineG.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    ImageIcon crono = new ImageIcon(getClass().getResource("/material/cronometro.png"));
    Icon cronom = new ImageIcon(crono.getImage().getScaledInstance(35, 40, Image.SCALE_DEFAULT));
    AudioClip pop = java.applet.Applet.newAudioClip(getClass().getResource("/material/facebook-pop.wav"));
    AudioClip allah = java.applet.Applet.newAudioClip(getClass().getResource("/material/allahu.wav"));
    short cantMinas;
    short ancho, largo;
    Cronometro cron = new Cronometro();
    boolean isLeft, fstClick;
    JFrame frame;
    Color clrBack = Color.GRAY, clrPres = Color.BLACK, clrNum = Color.YELLOW;
    BotonMina[][] matriz = new BotonMina[15][12];
    JLabel lblTemp = new JLabel("0"), lblMinas = new JLabel();
    int seg;
    boolean wasApariencia;
    Color tback = clrBack, tnum = clrNum, tpres = clrPres;

    //JColorChooser a=new JColorChooser();
    private void Apariencia() {
        JDialog dlogAp = new JDialog();
        dlogAp.setTitle("Apariencia");
        dlogAp.setModal(true);
        dlogAp.setSize(402, 265);
        dlogAp.setLocationRelativeTo(null);
        dlogAp.setResizable(false);
        dlogAp.setLayout(null);
        JButton btnBack, btnAceptar, btnPres, btnNum, pba1, pba2;
        btnBack = new JButton("Color de Boton");
        btnPres = new JButton("Color de Boton Presionado");
        btnNum = new JButton("Color de Numero");
        pba1 = new JButton();
        pba1.setBackground(clrBack);
        pba2 = new JButton("4");
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(220, 150, 120, 40);
        pba2.setForeground(clrNum);
        pba2.setBackground(clrPres);
        pba1.setBounds(280, 60, 50, 50);
        pba2.setBounds(230, 60, 50, 50);
        btnBack.setBounds(20, 10, 150, 50);
        btnPres.setBounds(20, 90, 150, 50);
        btnNum.setBounds(20, 170, 150, 50);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tback = JColorChooser.showDialog(null, "Elige color para boton", clrBack);
                pba1.setBackground(tback);
            }
        });
        btnPres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tpres = JColorChooser.showDialog(null, "Elige color para boton", clrPres);
                pba2.setBackground(tpres);
            }
        });
        btnNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tnum = JColorChooser.showDialog(null, "Elige color para boton", clrNum);
                pba2.setForeground(tnum);
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clrNum = tnum;
                clrBack = tback;
                clrPres = tpres;
                if (wasApariencia) {
                    for (byte i = 0; i < matriz.length; i++) {
                        for (byte j = 0; j < matriz[i].length; j++) {
                            if (matriz[i][j].wasUsado) {
                                matriz[i][j].setBackground(clrPres);

                            } else {
                                matriz[i][j].setBackground(clrBack);
                            }
                            matriz[i][j].setForeground(clrNum);

                        }
                    }
                }
                lblTemp.setForeground(clrNum);
                lblTemp.setBackground(clrPres);
                lblMinas.setBackground(clrPres);
                lblMinas.setForeground(clrNum);
                wasApariencia = true;
                dlogAp.dispose();
            }
        });

        dlogAp.add(btnNum);
        dlogAp.add(btnBack);
        dlogAp.add(btnPres);
        dlogAp.add(btnAceptar);
        dlogAp.add(pba1);
        dlogAp.add(pba2);
        dlogAp.setVisible(true);
    }

    private void dialogo(boolean gano) {
        JDialog fin = new JDialog();
        fin.setAlwaysOnTop(true);
        fin.setAutoRequestFocus(true);
        fin.setModal(true);
        fin.setSize(402, 250);
        fin.setLocationRelativeTo(null);
        fin.setResizable(false);
        fin.setLayout(null);
        JButton acept, salir;
        acept = new JButton("Aceptar");
        salir = new JButton("Salir");
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu(false);
                fin.dispose();
                frame.dispose();
            }
        });
        //nivel = new JButton("Niveles");
        salir.setBounds(18, 120, 102, 30);
        fin.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        fin.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                for (byte i = 0; i < matriz.length; i++) {
                    for (byte j = 0; j < matriz[i].length; j++) {
                        matriz[i][j].hasMina = false;
                        matriz[i][j].setIcon(null);
                        matriz[i][j].setBackground(clrBack);
                        matriz[i][j].setText("");
                        matriz[i][j].hasBandera = false;
                        matriz[i][j].wasUsado = false;
                        fstClick = false;
                        cron.stop();
                        cantMinas = 30;
                        lblMinas.setText("30");
                        lblTemp.setText("0");
                    }
                }
                fin.dispose();
            }
        });
        //nivel.setBounds(149, 120, 102, 30);
        acept.setBounds(278, 120, 102, 30);
        acept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (byte i = 0; i < matriz.length; i++) {
                    for (byte j = 0; j < matriz[i].length; j++) {
                        matriz[i][j].hasMina = false;
                        matriz[i][j].setIcon(null);
                        matriz[i][j].setBackground(clrBack);
                        matriz[i][j].setText("");
                        matriz[i][j].hasBandera = false;
                        matriz[i][j].wasUsado = false;
                        fstClick = false;
                        cron.stop();
                        cantMinas = 30;
                        lblMinas.setText("30");
                        lblTemp.setText("0");
                    }
                }
                fin.dispose();
            }
        });
        fin.add(acept);
        fin.setTitle("Fin del juego");
        //fin.add(nivel);
        fin.add(salir);
        JLabel texto = new JLabel();
        texto.setBounds(50, 50, 300, 40);
        texto.setHorizontalAlignment(SwingConstants.CENTER);
        if (gano) {
            texto.setText("Felicidades Ganastve en: " + seg + " Segundos");
        } else {
            texto.setText("Lo sentimos has perdido");

        }
        fin.add(texto);
        fin.setVisible(true);
    }

    private void PropiedadesFrame() {
        frame = new JFrame("Busca Minas");
        frame.setSize(606, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.black);
        frame.setIconImage(Icotitle.getImage());
        cantMinas = 30;
        //frame.setIconImage((Image) Icotitulo);
        //PARTE ALTA DEL FRAME//  
        JMenuBar menu = new JMenuBar();
        JMenu opciones = new JMenu("Opciones");
        JMenuItem apariencia, salir;
        apariencia = new JMenuItem("Apariencia");
        salir = new JMenuItem("Salir");
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu(false);
                frame.dispose();
            }
        });
        ////////////////////////////////////////////////////////////////////////////
        opciones.add(apariencia);
        apariencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Apariencia();
            }
        });
        opciones.addSeparator();
        opciones.add(salir);
        menu.setBounds(0, 0, 608, 30);
        menu.add(opciones);
        lblMinas.setBounds(0, 30, 304, 50);
        lblMinas.setBorder(new LineBorder(Color.DARK_GRAY));
        lblMinas.setForeground(clrNum);
        lblMinas.setText("30");
        lblMinas.setOpaque(true);
        lblMinas.setBackground(clrPres);
        lblMinas.setFont(new Font("Arial Black", Font.ROMAN_BASELINE, 24));
        lblMinas.setHorizontalAlignment(SwingConstants.CENTER);
        lblMinas.setIcon(mina);
        lblTemp.setBounds(304, 30, 304, 50);
        lblTemp.setBorder(new LineBorder(Color.darkGray));
        lblTemp.setForeground(clrNum);
        lblTemp.setOpaque(true);
        lblTemp.setText("0");
        lblTemp.setBackground(clrPres);
        lblTemp.setFont(new Font("Arial Black", Font.ROMAN_BASELINE, 24));
        lblTemp.setHorizontalAlignment(SwingConstants.CENTER);
        lblTemp.setIcon(cronom);
        frame.add(menu);
        frame.add(lblTemp);
        frame.add(lblMinas);

    }//PROPIEDADES FRAME/PARTE ALTA

    private void Botones() {

        //Crea todos lo botones
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = new BotonMina(j, i, clrBack, clrNum, clrPres);
                matriz[i][j].setLocation(i * 40, 80 + j * 40);
                matriz[i][j].addMouseListener(this);
                frame.add(matriz[i][j]);
            }
        }
    }

    private ArrayList<Integer> Minar(int stClick) {
        int minas;
        Random x = new Random();
        ArrayList<Integer> coordMinadas = new ArrayList();
        ArrayList<Integer> coordMin = new ArrayList();
        coordMin.add(stClick);
        short cont = 1;
        //Este while crea posiciones lineares evitando que se repitan
        while (cont <= 30) {
            minas = x.nextInt(180);
            if (!coordMin.contains(minas)) {
                coordMinadas.add(minas);
                coordMin.add(minas);
                cont++;
            }
        }
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz[i].length; j++) {
                if (coordMinadas.contains(j * 15 + i)) {
                    matriz[i][j].hasMina = true;
                    //matriz[i][j].setText("x");
                }
            }
        }
        return coordMinadas;
    }

    private boolean isWinner() {
        boolean cumple = true;
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz[i].length; j++) {
                matriz[i][j].alrededorMostrado=false;
                if (!matriz[i][j].wasUsado && !matriz[i][j].hasMina) {
                    cumple = false;
                    break;
                }
            }
            if (!cumple) {
                break;
            }
        }
        return cumple;
    }

    public void AccionBoton(BotonMina selec) {
        if (selec.isEnabled()) {
            if (isLeft) {
                if (!selec.hasBandera) {
                    if (selec.hasMina) {
                        perdio();
                    } else {
                        selec.Mostrar();
                        if (isWinner()) {
                            lblMinas.setText("Ganaste");
                            cron.stop();
                            for (byte i = 0; i < matriz.length; i++) {
                                for (byte j = 0; j < matriz[i].length; j++) {

                                    if (matriz[i][j].hasMina && !matriz[i][j].hasBandera) {
                                        matriz[i][j].setIcon(bandera);
                                    }
                                }
                            }
                            dialogo(true);
                        }
                        //selec.setText("" + selec.soutMinas());
                    }
                } else {//Si dan click izquierdo y tiene bandera
                }
            } else {//Si dan click derecho
                if (!selec.hasBandera) {
                    selec.setIcon(bandera);
                    selec.hasBandera = true;
                    cantMinas--;
                } else {
                    selec.setIcon(null);
                    selec.hasBandera = false;
                    cantMinas++;
                }
                lblMinas.setText("\t" + cantMinas);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
            BotonMina selec = (BotonMina) me.getSource();
            byte banderasVecinas = 0;
            for (int i = 0; i < 8; i++) {
                if (selec.v[i] != null) {
                    if (selec.v[i].hasBandera) {
                        banderasVecinas++;
                    }
                }
            }
            if (selec.cuentaMinas() == banderasVecinas) {
                for (int i = 0; i < 8; i++) {
                    if (selec.v[i] != null) {
                        if (!selec.v[i].hasBandera) {
                            if (selec.v[i].hasMina) {
                                perdio();
                            } else {
                                selec.v[i].Mostrar();
                            }
                        }
                    }
                }
            }
        }
    }

    private void perdio() {
        allah.play();
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz[i].length; j++) {
                //matriz[i][j].setText("" + matriz[i][j].soutMinas());
                if (matriz[i][j].hasMina && !matriz[i][j].hasBandera) {
                    matriz[i][j].setText("");
                    matriz[i][j].setIcon(minaG);
                    matriz[i][j].alrededorMostrado=false;
                }
                if (!matriz[i][j].hasMina && matriz[i][j].hasBandera) {
                    matriz[i][j].setText("");
                    matriz[i][j].setIcon(banderax);
                }
            }
        }
        cron.stop();
        dialogo(false);
        //AQUI DEBE ABRIR UN DIALOG PARA JUGAR DE NUEVO
    }

    @Override
    public void mousePressed(MouseEvent me) {
        isLeft = me.getButton() == 1;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        BotonMina selec = (BotonMina) me.getSource();
        if (!selec.wasUsado) {
            pop.play();
            if (!fstClick) {
                fstClick = true;
                int fst = selec.posx * 15 + selec.posy;
                Minar(fst);
                cron = new Cronometro();
                cron.start();
            }
            AccionBoton(selec);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        BotonMina selec = (BotonMina) me.getSource();
        if (!selec.wasUsado) {
            selec.setBackground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        BotonMina selec = (BotonMina) me.getSource();
        if (!selec.wasUsado) {
            selec.setBackground(clrBack);
        }
    }

    private void Vecinos() {
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz[i].length; j++) {
                if (j == 0) {
                    matriz[i][j].v[4] = matriz[i][j + 1];
                    if (i == 0) {
                        matriz[i][j].v[3] = matriz[i + 1][j];
                        matriz[i][j].v[2] = matriz[i + 1][j + 1];
                    } else if (i == matriz.length - 1) {
                        matriz[i][j].v[6] = matriz[i - 1][j + 1];
                        matriz[i][j].v[5] = matriz[i - 1][j];
                    } else {
                        matriz[i][j].v[3] = matriz[i + 1][j];
                        matriz[i][j].v[5] = matriz[i + 1][j + 1];
                        matriz[i][j].v[6] = matriz[i - 1][j + 1];
                        matriz[i][j].v[2] = matriz[i - 1][j];
                    }
                } else if (j == matriz[i].length - 1) {
                    matriz[i][j].v[0] = matriz[i][j - 1];
                    if (i == 0) {
                        matriz[i][j].v[1] = matriz[i + 1][j];
                        matriz[i][j].v[2] = matriz[i + 1][j - 1];
                    } else if (i == matriz.length - 1) {
                        matriz[i][j].v[6] = matriz[i - 1][j - 1];
                        matriz[i][j].v[7] = matriz[i - 1][j];
                    } else {
                        matriz[i][j].v[1] = matriz[i + 1][j];
                        matriz[i][j].v[2] = matriz[i + 1][j - 1];
                        matriz[i][j].v[6] = matriz[i - 1][j - 1];
                        matriz[i][j].v[7] = matriz[i - 1][j];
                    }
                } else if (i == 0) {
                    matriz[i][j].v[1] = matriz[i][j - 1];
                    matriz[i][j].v[2] = matriz[i + 1][j - 1];
                    matriz[i][j].v[3] = matriz[i + 1][j];
                    matriz[i][j].v[4] = matriz[i + 1][j + 1];
                    matriz[i][j].v[0] = matriz[i][j + 1];
                } else if (i == matriz.length - 1) {
                    matriz[i][j].v[5] = matriz[i][j + 1];
                    matriz[i][j].v[6] = matriz[i - 1][j + 1];
                    matriz[i][j].v[7] = matriz[i - 1][j];
                    matriz[i][j].v[4] = matriz[i - 1][j - 1];
                    matriz[i][j].v[0] = matriz[i][j - 1];
                } else {
                    matriz[i][j].v[1] = matriz[i][j - 1];
                    matriz[i][j].v[2] = matriz[i + 1][j - 1];
                    matriz[i][j].v[3] = matriz[i + 1][j];
                    matriz[i][j].v[4] = matriz[i + 1][j + 1];
                    matriz[i][j].v[5] = matriz[i][j + 1];
                    matriz[i][j].v[6] = matriz[i - 1][j + 1];
                    matriz[i][j].v[7] = matriz[i - 1][j];
                    matriz[i][j].v[0] = matriz[i - 1][j - 1];
                }
            }
        }
    }

    /////////////////OBJETO Y MAIN/////////////////////////
    public class Cronometro extends Thread {

        @Override
        public void run() {
            seg = 0;
            while (true) {
                try {
                    //System.out.println("" + seg);
                    lblTemp.setText("" + seg);
                    seg++;
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    //Logger.getLogger(BuscaMinas.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Algo paso");
                }
            }
        }
    }

    public MainBuscaMinas() {
        Apariencia();
        PropiedadesFrame();
        Botones();
        Vecinos();
        frame.setVisible(true);

    }
}
