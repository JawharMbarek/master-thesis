
package de.m0ep.socc.workbench.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.workbench.components.AuthenticationCellPanel;
import de.m0ep.socc.workbench.models.AuthenticationGuiModel;

public class ServiceDialogView extends JDialog {
    public static final String ACTION_ADD_AUTHENTICATION = "action_add_authentication";
    public static final String ACTION_OK = "action_ok";
    public static final String ACTION_CANCEL = "action_cancel";

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JTextField txtServiceEndpointUri;
    private JTextField txtDescription;

    private JButton okButton;
    private JButton cancelButton;
    private JLabel lblAuthentications;
    private JButton btnAddAuthentication;
    private JPanel panel;

    /**
     * Create the dialog.
     */
    public ServiceDialogView() {

        initGUI();
    }

    private void initGUI() {
        setTitle("Create Service");
        setBounds(100, 100, 593, 345);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,
        },
                new RowSpec[] {
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("default:grow"),
                        FormFactory.DEFAULT_ROWSPEC,
                }));

        JLabel lblEndpointUri = new JLabel("Endpoint URI:");
        lblEndpointUri.setMinimumSize(new Dimension(4, 25));
        lblEndpointUri.setHorizontalAlignment(SwingConstants.TRAILING);
        contentPanel.add(lblEndpointUri, "2, 2, right, default");

        txtServiceEndpointUri = new JTextField();
        txtServiceEndpointUri.setMinimumSize(new Dimension(4, 25));
        contentPanel.add(txtServiceEndpointUri, "4, 2, fill, default");
        txtServiceEndpointUri.setColumns(10);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setMinimumSize(new Dimension(4, 25));
        lblDescription.setHorizontalAlignment(SwingConstants.TRAILING);
        contentPanel.add(lblDescription, "2, 4, right, default");

        txtDescription = new JTextField();
        txtDescription.setMinimumSize(new Dimension(4, 25));
        contentPanel.add(txtDescription, "4, 4, fill, default");
        txtDescription.setColumns(10);

        lblAuthentications = new JLabel("Authentications:");
        lblAuthentications.setMinimumSize(new Dimension(4, 25));
        contentPanel.add(lblAuthentications, "2, 6, right, top");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.add(scrollPane, "4, 6, fill, fill");

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        btnAddAuthentication = new JButton("Add");
        btnAddAuthentication.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddAuthentication.setBorderPainted(false);
        btnAddAuthentication.setContentAreaFilled(false);
        btnAddAuthentication.setActionCommand(ACTION_ADD_AUTHENTICATION);
        btnAddAuthentication.setToolTipText("Add Authentication");
        btnAddAuthentication.setIcon(new ImageIcon(ServiceDialogView.class
                .getResource("/assets/icons/add.png")));
        btnAddAuthentication.setMinimumSize(new Dimension(4, 25));
        btnAddAuthentication.setMargin(new Insets(2, 2, 2, 2));
        contentPanel.add(btnAddAuthentication, "4, 7, right, default");

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("Ok");
        okButton.setActionCommand(ACTION_OK);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(ACTION_CANCEL);
        buttonPane.add(cancelButton);
    }

    public JTextField getServiceEndpointUri() {
        return txtServiceEndpointUri;
    }

    public JTextField getDescription() {
        return txtDescription;
    }

    public JButton getAddAuthenticationButton() {
        return btnAddAuthentication;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public AuthenticationCellPanel addAuthentication(AuthenticationGuiModel model) {
        return null;
    }

    public void showView() {
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    public void exitView() {
        setVisible(false);
        dispose();
    }

    private static class AuthenticationCellRenderer implements
            ListCellRenderer<AuthenticationGuiModel> {

        @Override
        public Component getListCellRendererComponent(JList<? extends AuthenticationGuiModel> list,
                AuthenticationGuiModel item, int index, boolean isSelected, boolean hasFocus) {

            AuthenticationCellPanel renderer = new AuthenticationCellPanel();

            renderer.getLabel().setText(new Date().toString());

            if (isSelected) {
                renderer.setBackground(list.getSelectionBackground());
                renderer.setForeground(list.getSelectionForeground());
            } else {
                renderer.setBackground(list.getBackground());
                renderer.setForeground(list.getForeground());
            }

            renderer.setEnabled(list.isEnabled());
            renderer.setFont(list.getFont());

            Border border = null;
            if (hasFocus) {
                if (isSelected) {
                    border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
                }
                if (border == null) {
                    border = UIManager.getBorder("List.focusCellHighlightBorder");
                }
            } else {
                border = UIManager.getBorder("List.noFocusBorder");
            }
            renderer.setBorder(border);

            return renderer;
        }
    }
}
