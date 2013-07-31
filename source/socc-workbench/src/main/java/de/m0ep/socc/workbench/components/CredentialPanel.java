
package de.m0ep.socc.workbench.components;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.workbench.models.CredentialGuiModel;

public class CredentialPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JComboBox<CredentialGuiModel.Type> cboxType;
    private JTextField txtValue;
    private JButton btnDeleteButton;

    /**
     * Create the panel.
     */
    public CredentialPanel() {
        initGUI();
    }

    private void initGUI() {
        setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("max(50dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.PREF_COLSPEC,
        },
                new RowSpec[] {
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                }));

        cboxType = new JComboBox<CredentialGuiModel.Type>();
        cboxType.setModel(new DefaultComboBoxModel<CredentialGuiModel.Type>(
                CredentialGuiModel.Type.values()));
        add(cboxType, "1, 1, fill, default");

        txtValue = new JTextField();
        txtValue.setText("value");
        add(txtValue, "3, 1, fill, fill");
        txtValue.setColumns(10);

        btnDeleteButton = new JButton("Delete");
        add(btnDeleteButton, "5, 1");
    }

    public JComboBox<CredentialGuiModel.Type> getType() {
        return cboxType;
    }

    public JTextField getValue() {
        return txtValue;
    }

    public JButton getDeleteButton() {
        return btnDeleteButton;
    }
}
