
package de.m0ep.rdf2go.generator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ontoware.rdf2go.Reasoning;
import org.ontoware.rdf2go.util.VocabularyWriter;
import org.ontoware.rdfreactor.generator.CodeGenerator;

import com.google.common.base.Preconditions;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;

import de.m0ep.rdf2go.generator.model.GeneratorParameter;
import de.m0ep.rdf2go.generator.view.Rdf2JavaGeneratorView;

public class Rdf2JavaGeneratorController implements ActionListener {

    private Rdf2JavaGeneratorView view;
    private GeneratorParameter model;
    private BeanAdapter<GeneratorParameter> beanAdapter;

    public Rdf2JavaGeneratorController( final GeneratorParameter model ) {
        this.model = Preconditions.checkNotNull( model,
                "Required parameter model must be specified." );
    }

    public void setView( Rdf2JavaGeneratorView view ) {
        this.view = Preconditions.checkNotNull( view,
                "Required parameter view must be specified." );
    }

    public void init() {
        beanAdapter = new BeanAdapter<GeneratorParameter>( model );
        Bindings.bind(
                view.getInputFilenameTextField(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_INPUT_FILENAME ) );
        Bindings.bind(
                view.getOutputDirectoryTextField(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_OUTPUT_DIRECTORY ) );
        Bindings.bind(
                view.getPackageTextField(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_PACKAGE_NAME ) );
        Bindings.bind(
                view.getMethodPrefixTextField(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_METHOD_PREFIX ) );
        Bindings.bind(
                view.getSkipBuildinsCheckbox(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_SKIP_BUILDINS ) );
        Bindings.bind(
                view.getGenerateVokabularyCheckbox(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_GENERATE_VOKABULARY ) );
        Bindings.bind(
                view.getVokabularyClassnameTextfield(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_VOKABULARY_CLASSNAME ) );
        Bindings.bind(
                view.getVokabularyNamespaceTextfield(),
                beanAdapter.getValueModel(
                        GeneratorParameter.PROPERTY_VOKABULARY_NAMESPACE ) );

        model.addPropertyChangeListener(
                GeneratorParameter.PROPERTY_REASONING,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange( PropertyChangeEvent e ) {
                        view.getReasoningCombobox().setSelectedItem( e.getNewValue() );
                    }
                } );

        view.getReasoningCombobox().addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                model.setReasoning( e.getItem().toString() );
            }
        } );

        view.getGenerateVokabularyCheckbox().addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e ) {
                boolean selected = view.getGenerateVokabularyCheckbox().isSelected();
                if (selected) {
                    setGenerateVokabularyEnabled( true );
                } else {
                    setGenerateVokabularyEnabled( false );
                }
            }
        } );

        view.getGenerateButton().addActionListener( this );
        view.getOpenFileButton().addActionListener( this );
        view.getOpenDirectoryButton().addActionListener( this );
        setGenerateVokabularyEnabled( false );
    }

    private void setGenerateVokabularyEnabled( final boolean enabled ) {
        view.getGenerateVokabularyCheckbox().setSelected( enabled );
        view.getVokabularyNamespaceTextfield().setEnabled( enabled );
        view.getVokabularyClassnameTextfield().setEnabled( enabled );
    }

    @Override
    public void actionPerformed( ActionEvent event ) {
        String cmd = event.getActionCommand();

        switch (cmd) {
        case Rdf2JavaGeneratorView.ACTION_CMD_OPEN_FILE:
            openFileDialog();
            break;
        case Rdf2JavaGeneratorView.ACTION_CMD_OPEN_DIRECTORY:
            openDirectoryDialog();
            break;
        case Rdf2JavaGeneratorView.ACTION_CMD_GENERATE:
            try {
                doGenerate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        view,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE );
            }
            break;
        }
    }

    private void openFileDialog() {
        JFileChooser fileChooser = new JFileChooser( System.getProperty( "user.home" ) );
        fileChooser.setDialogTitle( "Choose output file." );

        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog( view )) {
            model.setInputFilename( fileChooser.getSelectedFile().getAbsolutePath() );
            view.getInputFilenameTextField().setText( model.getInputFilename() );
        }
    }

    private void openDirectoryDialog() {
        JFileChooser fileChooser = new JFileChooser( System.getProperty( "user.home" ) );
        fileChooser.setDialogTitle( "Choose output folder." );
        fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        fileChooser.setAcceptAllFileFilterUsed( false );

        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog( view )) {
            model.setOutputDirectory( fileChooser.getSelectedFile().getAbsolutePath() );
            view.getOutputDirectoryTextField().setText( model.getOutputDirectory() );
        }
    }

    private void doGenerate() throws Exception {
        CodeGenerator.generate(
                model.getInputFilename(),
                model.getOutputDirectory(),
                model.getPackageName(),
                getSemantics( model.getReasoning() ),
                model.isSkipBuildins(),
                model.getMethodPrefix() );

        if (model.isGenerateVokabulary()) {

            File outoutDir = new File(
                    model.getOutputDirectory(),
                    model.getPackageName().replace( ".", "/" ) );
            if (!outoutDir.exists()) {
                outoutDir.mkdirs();
            }

            File outputFile = new File(
                    outoutDir,
                    model.getVokabularyClassname() + ".java" );
            if (!outputFile.exists()) {
                outoutDir.createNewFile();
            }

            String[] args = {
                    "-i", model.getInputFilename(),
                    "-o", outoutDir.getAbsolutePath(),
                    "-a", model.getVokabularyNamespace(),
                    "-n", model.getVokabularyClassname(),
                    "--package", model.getPackageName()
            };

            VocabularyWriter vocabularyWriter = new VocabularyWriter();
            vocabularyWriter.go( args );
        }
    }

    private Reasoning getSemantics( String reasoning ) {
        switch (reasoning) {
        case GeneratorParameter.REASONING_RDFS:
            return Reasoning.rdfs;
        case GeneratorParameter.REASONING_OWL:
            return Reasoning.owl;
        case GeneratorParameter.REASONING_RDFS_OWL:
            return Reasoning.rdfsAndOwl;
        default:
            return Reasoning.none;
        }
    }
}
