package clases;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Omar Serrano Govea
 */
public class JDialog_GameOver extends JDialog implements ActionListener {

    private JButton reiniciar, salir;
    private JLabel tiempo;
    private String time;
    private JFrame frame;

    public JDialog_GameOver(String time, JFrame frame) {
        this.time = time;
        this.frame = frame;
        caracteristicasFrame();
        componentes();
        this.setVisible(true);
    }

    private void caracteristicasFrame() {
        setTitle("Game Over");
        setSize(250, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setUndecorated(false);
        setLayout(null);
    }

    private void componentes() {
        tiempo = new JLabel(time);
        tiempo.setBounds(this.getWidth() / 2 - 60, this.getHeight() / 3 - 20, 120, 40);
        tiempo.setFont(new Font("arial", Font.BOLD, 40));
        this.add(tiempo);

        reiniciar = new JButton("Reiniciar");
        reiniciar.setBounds(26, 120, 90, 30);
        reiniciar.addActionListener(this);
        add(reiniciar);

        salir = new JButton("Salir");
        salir.setBounds(142, 120, 80, 30);
        salir.addActionListener(this);
        add(salir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reiniciar) {
            new Puzzle();
            this.dispose();
        }

        if (e.getSource() == salir) {
            new Menu(false);
            this.dispose();
            frame.dispose();
        }
    }

}
