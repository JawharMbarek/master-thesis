package de.m0ep.socc.shop.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.camel.model.RouteDefinition;

public class RouteDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private final JPanel contentPanel = new JPanel();
    private int resultOption;
    private RouteDefinition resultRoute;

    /**
     * Create the dialog.
     */
    public RouteDialog() {
	setBounds(100, 100, 450, 300);

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosed(WindowEvent e) {
		onCancel();
	    }
	});

	getContentPane().setLayout(new BorderLayout());
	contentPanel.setLayout(new FlowLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	getContentPane().add(contentPanel, BorderLayout.CENTER);
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
