package clases;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class BotonMina extends JButton {

    BotonMina[] v = new BotonMina[8];
    boolean alrededorMostrado;
    final byte posx, posy;
    Color cback, cnum, cpres;
    private byte minasVecinas;
    boolean hasMina, hasBandera;
    boolean wasUsado;

    public BotonMina(byte posx, byte posy, Color cback, Color cnum, Color cpres) {
        this.cback = cback;
        this.cnum = cnum;
        this.cpres = cpres;
        this.setBackground(cback);
        this.setForeground(cnum);
        this.setFont(new Font("Arial Black", Font.ROMAN_BASELINE, 13));
        this.setSize(40, 40);
        this.setBorder(new LineBorder(Color.WHITE));
        this.setFocusable(false);
        this.posx = posx;
        this.posy = posy;
    }

    public void Mostrar() {
        if (!hasBandera) {
            cuentaMinas();
            this.soutMinas();
            if (minasVecinas == 0) {
                for (int i = 0; i < 8; i++) {
                    if (v[i] != null) {
                        if (!v[i].hasMina && !v[i].hasBandera) {
                            v[i].soutMinas();
                        }
                    }
                }
                this.alrededorMostrado = true;
                for (int i = 0; i < 8; i++) {
                    if (v[i] != null && !v[i].alrededorMostrado) {
                        v[i].Mostrar();
                    }
                }
            } else {
                soutMinas();
            }
        }
    }

    public byte cuentaMinas() {
        minasVecinas = 0;
        for (int i = 0; i < 8; i++) {
            if (!(v[i] == null)) {
                if (v[i].hasMina) {
                    minasVecinas++;
                }
            }
        }
        return minasVecinas;
    }

    public void soutMinas() {
        cuentaMinas();
        this.setBackground(cpres);
        this.wasUsado = true;
        //this.setEnabled(false);
        //this.setForeground(Color.yellow);
        if (minasVecinas == 0) {
            this.setText("");
        } else {
            this.setText("" + minasVecinas);
        }
    }

    @Override
    public String toString() {
        return ("[" + posx + "," + posy + "]");
    }
}
