package clases;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 *
 * @author Omar Serrano Govea
 */
public class Menu implements MouseListener {

    private JFrame frame;
    private ImageIcon imageIcon_fondo, imageIcon_juego1, imageIcon_juego2, imageIcon_juego3;
    private Icon icono_fondo, icon_juego1, icon_juego2, icon_juego3;
    private JLabel label_fondo, title;
    private JLabel[] juegos;
    private String[] names, paths, pathsHover;
    private ImageIcon[] imageIcons;
    private Icon[] icons;

    private LoadingScreen loadingScreen;
    private AudioClip beep;

    public Menu(boolean cameGame) {
        if (cameGame) {
            loadingScreen = new LoadingScreen();
        }
        caracteristicasFrame();
        componentes();
        frame.setVisible(true);
    }

    private void caracteristicasFrame() {
        frame = new JFrame("Polystation");
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(getClass().getResource("/material/xbox_icon.png")).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

    }

    private void componentes() {
        title = new JLabel("Select a game");
        title.setBounds(frame.getWidth() / 2 - 150, 20, 300, 45);
        title.setFont(new Font("arial", Font.ROMAN_BASELINE, 40));
        title.setForeground(Color.WHITE);
        frame.add(title);

        juegos = new JLabel[3];
        imageIcons = new ImageIcon[juegos.length];
        icons = new Icon[juegos.length];
        paths = new String[]{"/material/buscaminas.png", "/material/puzzle.png", "/material/pares.png"};
        pathsHover = new String[]{"/material/buscaminas_hover.gif", "/material/puzzle_hover.gif", "/material/pares_hover.gif"};
        names = new String[]{"Buscaminas", "Puzzle", "Memorama"};
        for (int i = 0; i < juegos.length; i++) {
            juegos[i] = new JLabel();
            juegos[i].setBounds(i * 316 + 50, title.getY() + title.getHeight() + 50, 266, 266);
            juegos[i].setBorder(new TitledBorder(new LineBorder(Color.WHITE), names[i], TitledBorder.CENTER, TitledBorder.TOP, new Font("arial", Font.BOLD, 12), Color.WHITE));
            imageIcons[i] = new ImageIcon(getClass().getResource(paths[i]));
            icons[i] = new ImageIcon(imageIcons[i].getImage().getScaledInstance(juegos[i].getWidth() - 15, juegos[i].getHeight() - 15, Image.SCALE_DEFAULT));
            juegos[i].setIcon(icons[i]);
            juegos[i].addMouseListener(this);
            frame.add(juegos[i]);
        }

        label_fondo = new JLabel();
        label_fondo.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        imageIcon_fondo = new ImageIcon(getClass().getResource("/material/background.gif"));
        icono_fondo = new ImageIcon(imageIcon_fondo.getImage().getScaledInstance(label_fondo.getWidth(), label_fondo.getHeight(), Image.SCALE_DEFAULT));
        label_fondo.setIcon(icono_fondo);
        frame.add(label_fondo);

        beep = java.applet.Applet.newAudioClip(getClass().getResource("/material/beep_sound.wav"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == juegos[0]) {
            new MainBuscaMinas();
            frame.dispose();
        }

        if (e.getSource() == juegos[1]) {
            new Puzzle();
            frame.dispose();
        }

        if (e.getSource() == juegos[2]) {
            new Accion();
            frame.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < juegos.length; i++) {
            if (e.getSource() == juegos[i]) {
                beep.play();
                imageIcons[i] = new ImageIcon(getClass().getResource(pathsHover[i]));
                icons[i] = new ImageIcon(imageIcons[i].getImage().getScaledInstance(juegos[i].getWidth() - 15, juegos[i].getHeight() - 15, Image.SCALE_DEFAULT));
                juegos[i].setIcon(icons[i]);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < juegos.length; i++) {
            if (e.getSource() == juegos[i]) {
                imageIcons[i] = new ImageIcon(getClass().getResource(paths[i]));
                icons[i] = new ImageIcon(imageIcons[i].getImage().getScaledInstance(juegos[i].getWidth() - 15, juegos[i].getHeight() - 15, Image.SCALE_DEFAULT));
                juegos[i].setIcon(icons[i]);
            }
        }
    }

    public static void main(String[] args) {
        Menu m = new Menu(true);
    }
}
