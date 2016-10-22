package halen;

import static halen.Main.primary;
import static halen.Main.secondary;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * @author TAIBHSE
 */
class ColorArrowUI extends BasicComboBoxUI {

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override protected JButton createArrowButton() {
        return new BasicArrowButton(
            BasicArrowButton.SOUTH,
            secondary, secondary,
            primary, secondary);
    }
}
