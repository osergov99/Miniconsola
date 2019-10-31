package clases;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Omar Serrano Govea
 */
public class LoadingScreen {

    private JFrame frame;
    private JLabel intro_gif, label1, label2, label3;
    private ImageIcon imageIcon_intro;
    private Icon icon_intro;

    private int segundos = 0;
    private AudioClip intro_sonido;

    public LoadingScreen() {
        caracteristicasFrame();
        componentes();
        frame.setVisible(true);

        intro_sonido = java.applet.Applet.newAudioClip(getClass().getResource("/material/intro.wav"));
        intro_sonido.play();

        try {
            while (segundos < 14) {
                if (segundos == 3) {
                    intro_gif.add(label1);
                }
                if (segundos == 5) {
                    intro_gif.add(label2);
                }
                if (segundos == 9) {
                    intro_gif.add(label3);
                }
                segundos++;
                Thread.sleep(1000);
            }
            frame.dispose();
        } catch (InterruptedException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private void caracteristicasFrame() {
        frame = new JFrame();
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/material/xbox_icon.png")).getImage());
        frame.setLayout(null);
    }

    private void componentes() {
        label1 = new JLabel("POLYSTATION");
        label1.setBounds(frame.getWidth() / 2 - 215, 120, 430, 60);
        label1.setFont(new Font("arial", Font.ROMAN_BASELINE, 60));
        label1.setForeground(Color.WHITE);

        label2 = new JLabel("All rights reserved");
        label2.setBounds(frame.getWidth() / 2 - 125, 250, 250, 30);
        label2.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));
        label2.setForeground(Color.WHITE);

        label3 = new JLabel("Pero mira esos gráficos prro :v");
        label3.setBounds(frame.getWidth() / 2 - 210, 400, 420, 40);
        label3.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));
        label3.setForeground(Color.WHITE);

        intro_gif = new JLabel();
        intro_gif.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        imageIcon_intro = new ImageIcon(getClass().getResource("/material/velocidad.gif"));
        icon_intro = new ImageIcon(imageIcon_intro.getImage().getScaledInstance(intro_gif.getWidth(), intro_gif.getHeight(), Image.SCALE_DEFAULT));
        intro_gif.setIcon(icon_intro);
        frame.add(intro_gif);
    }

}
