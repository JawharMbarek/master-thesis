
package de.m0ep.socc.workbench.components;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AuthenticationCellPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel lbllabel;
    private JButton btnDelete;

    /**
     * Create the panel.
     */
    public AuthenticationCellPanel() {
        initGUI();
    }

    private void initGUI() {
        setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
        },
                new RowSpec[] {
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                }));

        lbllabel = new JLabel("{label}");
        add(lbllabel, "2, 1, fill, fill");

        btnDelete = new JButton("");
        btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new Dimension(25, 25));
        btnDelete.setToolTipText("Delete Authentication");
        btnDelete.setContentAreaFilled(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setIcon(new ImageIcon(AuthenticationCellPanel.class
                .getResource("/assets/icons/delete.png")));
        add(btnDelete, "4, 1");
    }

    public JLabel getLabel() {
        return lbllabel;
    }

    public JButton getDeleteButton() {
        return btnDelete;
    }
}
