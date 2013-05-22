package de.m0ep.socc.shop.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.camel.model.RouteDefinition;

import com.google.common.base.Preconditions;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.shop.SOCCShopApplication;

public class RouteDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private SOCCShopApplication app;

    private final JPanel contentPanel = new JPanel();
    private int resultOption;
    private RouteDefinition resultRoute;
    private JTextField textFrom;
    private JTextField textTo;
    private JTextField textId;

    /**
     * Create the dialog.
     */
    public RouteDialog(SOCCShopApplication app) {
	this.app = Preconditions.checkNotNull(app, "App can not be null");

	setAlwaysOnTop(true);
	setTitle("Create Route");
	setResizable(false);
	setModalityType(ModalityType.APPLICATION_MODAL);
	setModal(true);
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 450, 200);

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosed(WindowEvent e) {
		onCancel();
	    }
	});

	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
		FormFactory.RELATED_GAP_COLSPEC,
		FormFactory.DEFAULT_COLSPEC,
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("default:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		FormFactory.DEFAULT_COLSPEC, },
		new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC, }));
	{
	    JLabel lblId = new JLabel("Id:");
	    contentPanel.add(lblId, "2, 2, right, default");
	}
	{
	    textId = new JTextField();
	    textId.setMinimumSize(new Dimension(4, 25));
	    textId.setPreferredSize(new Dimension(4, 25));
	    contentPanel.add(textId, "4, 2, fill, default");
	    textId.setColumns(10);
	}
	{
	    JLabel lblFrom = new JLabel("From:");
	    contentPanel.add(lblFrom, "2, 4, right, default");
	}
	{
	    textFrom = new JTextField();
	    textFrom.setPreferredSize(new Dimension(4, 25));
	    contentPanel.add(textFrom, "4, 4, fill, default");
	    textFrom.setColumns(10);
	}
	{
	    JButton btnFromAssist = new JButton("...");
	    btnFromAssist.setMargin(new Insets(2, 2, 2, 2));
	    contentPanel.add(btnFromAssist, "6, 4");
	}
	{
	    JLabel lblTo = new JLabel("To:");
	    contentPanel.add(lblTo, "2, 6, right, default");
	}
	{
	    textTo = new JTextField();
	    textTo.setPreferredSize(new Dimension(4, 25));
	    contentPanel.add(textTo, "4, 6, fill, default");
	    textTo.setColumns(10);
	}
	{
	    JButton btnToAssist = new JButton("...");
	    btnToAssist.setMargin(new Insets(2, 2, 2, 2));
	    contentPanel.add(btnToAssist, "6, 6");
	}
	{
	    JCheckBox chckbxUseRdfxml = new JCheckBox("use RDF/XML");
	    contentPanel.add(chckbxUseRdfxml, "4, 8");
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			onOk();
		    }
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			onCancel();
		    }
		});
		buttonPane.add(cancelButton);
	    }
	}
    }

    public int showDialog() {
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setModalityType(ModalityType.APPLICATION_MODAL);
	setModal(true);
	setVisible(true);

	return resultOption;
    }

    public void closeDialog() {
	setVisible(false);
	dispose();
    }

    public void onOk() {
	String fromUrl = textFrom.getText();
	String toUrl = textTo.getText();
	String id = textId.getText();

	resultOption = OK_OPTION;
	resultRoute = new RouteDefinition();
	resultRoute.setId(id);
	resultRoute.from(fromUrl).to(toUrl);

	closeDialog();
    }

    protected void onCancel() {
	resultOption = CANCEL_OPTION;
	resultRoute = null;
	closeDialog();
    }

    public RouteDefinition getRoute() {
	return resultRoute;
    }
}
