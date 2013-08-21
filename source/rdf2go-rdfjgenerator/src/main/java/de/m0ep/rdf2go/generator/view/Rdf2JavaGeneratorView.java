
package de.m0ep.rdf2go.generator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.rdf2go.generator.controller.Rdf2JavaGeneratorController;
import de.m0ep.rdf2go.generator.model.GeneratorParameter;

public class Rdf2JavaGeneratorView extends JFrame {

    public static final String ACTION_CMD_GENERATE = "action_cmd_generate";
    public static final String ACTION_CMD_OPEN_DIRECTORY = "action_cmd_open_directory";
    public static final String ACTION_CMD_OPEN_FILE = "action_cmd_open_file";

    private static final long serialVersionUID = 1L;
    private final String[] REASONING_ARRAY = {
            GeneratorParameter.REASONING_NONE,
            GeneratorParameter.REASONING_RDFS,
            GeneratorParameter.REASONING_OWL,
            GeneratorParameter.REASONING_RDFS_OWL
    };

    private JButton btnOpenFile;
    private JButton btnOpenDirectory;
    private JButton btnGenerate;

    private JTextField txtSchemaFilename;
    private JTextField txtOutputDirectory;
    private JTextField txtPackage;
    private JTextField txtMethodPrefix;
    private JTextField txtVokabularyClassname;
    private JTextField txtVokabularyNamespace;

    private JComboBox<String> cboxReasoning;

    private JCheckBox chkbSkipBuildins;
    private JCheckBox chkbGenerateVokabulary;

    private JPanel buttonPanel;

    /**
     * Create the frame.
     */
    public Rdf2JavaGeneratorView( Rdf2JavaGeneratorController controller ) {
        initView();

        controller.setView( this );
        controller.init();
    }

    private void initView() {
        setResizable( false );
        setTitle( "Generate Ontology" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 579, 332 );
        JPanel contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        contentPane.setLayout( new FormLayout( new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode( "default:grow" ),
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
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC, } ) );

        JLabel lblSchemaFile = new JLabel( "Schema File:" );
        contentPane.add( lblSchemaFile, "2, 2, right, default" );

        txtSchemaFilename = new JTextField();
        txtSchemaFilename.setPreferredSize( new Dimension( 4, 25 ) );
        contentPane.add( txtSchemaFilename, "4, 2, fill, default" );
        txtSchemaFilename.setColumns( 10 );

        btnOpenFile = new JButton( "Open" );
        btnOpenFile.setActionCommand( ACTION_CMD_OPEN_FILE );
        contentPane.add( btnOpenFile, "6, 2" );

        JLabel lblOutputFolder = new JLabel( "Output Folder:" );
        contentPane.add( lblOutputFolder, "2, 4, right, default" );

        txtOutputDirectory = new JTextField();
        txtOutputDirectory.setPreferredSize( new Dimension( 4, 25 ) );
        contentPane.add( txtOutputDirectory, "4, 4, fill, default" );
        txtOutputDirectory.setColumns( 10 );

        btnOpenDirectory = new JButton( "Open" );
        btnOpenDirectory.setActionCommand( ACTION_CMD_OPEN_DIRECTORY );
        contentPane.add( btnOpenDirectory, "6, 4" );

        JLabel lblPackage = new JLabel( "Package:" );
        contentPane.add( lblPackage, "2, 6, right, default" );

        txtPackage = new JTextField();
        txtPackage.setPreferredSize( new Dimension( 4, 25 ) );
        contentPane.add( txtPackage, "4, 6, 3, 1, fill, default" );
        txtPackage.setColumns( 10 );

        JLabel lblMethodPrefix = new JLabel( "Method Prefix:" );
        contentPane.add( lblMethodPrefix, "2, 8, right, default" );

        txtMethodPrefix = new JTextField();
        txtMethodPrefix.setPreferredSize( new Dimension( 4, 25 ) );
        contentPane.add( txtMethodPrefix, "4, 8, 3, 1, fill, default" );
        txtMethodPrefix.setColumns( 10 );

        JLabel lblReasoning = new JLabel( "Reasoning:" );
        contentPane.add( lblReasoning, "2, 10, right, default" );

        cboxReasoning = new JComboBox<String>( new DefaultComboBoxModel<>( REASONING_ARRAY ) );
        contentPane.add( cboxReasoning, "4, 10, 3, 1, fill, default" );

        chkbSkipBuildins = new JCheckBox( "Skip Buildins" );
        contentPane.add( chkbSkipBuildins, "4, 12, 3, 1" );

        chkbGenerateVokabulary = new JCheckBox( "Generate Vokabulary" );
        contentPane.add( chkbGenerateVokabulary, "4, 14, 3, 1" );

        JLabel lblVokabulary = new JLabel( "Vokabulary Classname:" );
        contentPane.add( lblVokabulary, "2, 16, right, default" );

        txtVokabularyClassname = new JTextField();
        txtVokabularyClassname.setPreferredSize( new Dimension( 4, 25 ) );
        contentPane.add( txtVokabularyClassname, "4, 16, 3, 1, fill, default" );
        txtVokabularyClassname.setColumns( 10 );

        JLabel lblVokabularyNamespace = new JLabel( "Vokabulary Namespace:" );
        contentPane.add( lblVokabularyNamespace, "2, 18, right, default" );

        txtVokabularyNamespace = new JTextField();
        contentPane.add( txtVokabularyNamespace, "4, 18, 3, 1, fill, default" );
        txtVokabularyNamespace.setColumns( 10 );

        buttonPanel = new JPanel();
        contentPane.add( buttonPanel, "2, 20, 5, 1, fill, fill" );
        buttonPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 5, 5 ) );

        btnGenerate = new JButton( "Generate" );
        btnGenerate.setActionCommand( ACTION_CMD_GENERATE );
        buttonPanel.add( btnGenerate );
    }

    public JButton getOpenFileButton() {
        return btnOpenFile;
    }

    public JButton getOpenDirectoryButton() {
        return btnOpenDirectory;
    }

    public JButton getGenerateButton() {
        return btnGenerate;
    }

    public JTextField getInputFilenameTextField() {
        return txtSchemaFilename;
    }

    public JTextField getOutputDirectoryTextField() {
        return txtOutputDirectory;
    }

    public JTextField getPackageTextField() {
        return txtPackage;
    }

    public JTextField getMethodPrefixTextField() {
        return txtMethodPrefix;
    }

    public JTextField getVokabularyClassnameTextfield() {
        return txtVokabularyClassname;
    }

    public JTextField getVokabularyNamespaceTextfield() {
        return txtVokabularyNamespace;
    }

    public JComboBox<String> getReasoningCombobox() {
        return cboxReasoning;
    }

    public JCheckBox getSkipBuildinsCheckbox() {
        return chkbSkipBuildins;
    }

    public JCheckBox getGenerateVokabularyCheckbox() {
        return chkbGenerateVokabulary;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}
