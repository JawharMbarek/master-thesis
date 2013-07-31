
package de.m0ep.socc.workbench.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ServiceAuthenticationDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JComboBox comboBox;
    private JButton btnAdd;
    private JButton okButton;
    private JButton cancelButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ServiceAuthenticationDialog dialog = new ServiceAuthenticationDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ServiceAuthenticationDialog() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
        },
                new RowSpec[] {
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("default:grow"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                }));

        final JLabel lblType = new JLabel("Type:");
        contentPanel.add(lblType, "2, 2, right, default");

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(Type.values()));
        contentPanel.add(comboBox, "4, 2, fill, default");

        final JLabel lblCredentials = new JLabel("Credentials:");
        contentPanel.add(lblCredentials, "2, 4, default, top");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPanel.add(scrollPane, "4, 4, fill, fill");

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        btnAdd = new JButton("Add");
        btnAdd.setHorizontalAlignment(SwingConstants.TRAILING);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setBorder(new EmptyBorder(2, 2, 2, 2));
        btnAdd.setIcon(new ImageIcon(ServiceAuthenticationDialog.class
                .getResource("/assets/icons/add.png")));
        contentPanel.add(btnAdd, "4, 6");

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}
