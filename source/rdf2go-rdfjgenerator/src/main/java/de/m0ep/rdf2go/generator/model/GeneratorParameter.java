
package de.m0ep.rdf2go.generator.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.google.common.base.Objects;

public class GeneratorParameter {
    public static final String PROPERTY_INPUT_FILENAME = "inputFilename";
    public static final String PROPERTY_OUTPUT_DIRECTORY = "outputDirectory";
    public static final String PROPERTY_PACKAGE_NAME = "packageName";
    public static final String PROPERTY_METHOD_PREFIX = "methodPrefix";
    public static final String PROPERTY_REASONING = "reasoning";
    public static final String PROPERTY_SKIP_BUILDINS = "skipBuildins";
    public static final String PROPERTY_GENERATE_VOKABULARY = "generateVokabulary";
    public static final String PROPERTY_VOKABULARY_CLASSNAME = "vokabularyClassname";
    public static final String PROPERTY_VOKABULARY_NAMESPACE = "vokabularyNamespace";

    public static final String REASONING_RDFS_OWL = "RDFS + OWL";
    public static final String REASONING_OWL = "OWL";
    public static final String REASONING_RDFS = "RDFS";
    public static final String REASONING_NONE = "None";

    private String inputFilename;
    private String outputDirectory;
    private String packageName;
    private String methodPrefix;
    private String reasoning;
    private boolean skipBuildins;
    private boolean generateVokabulary;
    private String vokabularyClassname;
    private String vokabularyNamespace;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport( this );

    public GeneratorParameter() {
        this.inputFilename = "";
        this.outputDirectory = "";
        this.packageName = "";
        this.methodPrefix = "";
        this.reasoning = REASONING_RDFS;
        this.skipBuildins = false;
        this.generateVokabulary = false;
        this.vokabularyClassname = "";
        this.vokabularyNamespace = "";
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public void setInputFilename( String inputFilename ) {
        pcs.firePropertyChange(
                PROPERTY_INPUT_FILENAME,
                this.inputFilename,
                inputFilename );
        this.inputFilename = inputFilename;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory( String outputDirectory ) {
        pcs.firePropertyChange(
                PROPERTY_OUTPUT_DIRECTORY,
                this.outputDirectory,
                outputDirectory );
        this.outputDirectory = outputDirectory;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName( String packageName ) {
        pcs.firePropertyChange(
                PROPERTY_PACKAGE_NAME,
                this.packageName,
                packageName );
        this.packageName = packageName;
    }

    public String getMethodPrefix() {
        return methodPrefix;
    }

    public void setMethodPrefix( String methodPrefix ) {
        pcs.firePropertyChange(
                PROPERTY_METHOD_PREFIX,
                this.methodPrefix,
                methodPrefix );
        this.methodPrefix = methodPrefix;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning( String reasoning ) {
        pcs.firePropertyChange(
                PROPERTY_REASONING,
                this.reasoning,
                reasoning );
        this.reasoning = reasoning;
    }

    public boolean isSkipBuildins() {
        return skipBuildins;
    }

    public void setSkipBuildins( boolean skipBuildins ) {
        pcs.firePropertyChange(
                PROPERTY_SKIP_BUILDINS,
                this.skipBuildins,
                skipBuildins );
        this.skipBuildins = skipBuildins;
    }

    public boolean isGenerateVokabulary() {
        return generateVokabulary;
    }

    public void setGenerateVokabulary( boolean generateVokabulary ) {
        pcs.firePropertyChange(
                PROPERTY_GENERATE_VOKABULARY,
                this.generateVokabulary,
                generateVokabulary );
        this.generateVokabulary = generateVokabulary;
    }

    public String getVokabularyClassname() {
        return vokabularyClassname;
    }

    public void setVokabularyClassname( String vokabularyClassname ) {
        pcs.firePropertyChange(
                PROPERTY_VOKABULARY_CLASSNAME,
                this.vokabularyClassname,
                vokabularyClassname );
        this.vokabularyClassname = vokabularyClassname;
    }

    public String getVokabularyNamespace() {
        return vokabularyNamespace;
    }

    public void setVokabularyNamespace( String vokabularyNamespace ) {
        pcs.firePropertyChange(
                PROPERTY_VOKABULARY_NAMESPACE,
                this.vokabularyNamespace,
                vokabularyNamespace );
        this.vokabularyNamespace = vokabularyNamespace;
    }

    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        pcs.addPropertyChangeListener( listener );
    }

    public void addPropertyChangeListener( String propertyName, PropertyChangeListener listener ) {
        pcs.addPropertyChangeListener( propertyName, listener );
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pcs.getPropertyChangeListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners( String propertyName ) {
        return pcs.getPropertyChangeListeners( propertyName );
    }

    public boolean hasListeners( String propertyName ) {
        return pcs.hasListeners( propertyName );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        pcs.removePropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( String propertyName, PropertyChangeListener listener ) {
        pcs.removePropertyChangeListener( propertyName, listener );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                inputFilename,
                outputDirectory,
                packageName,
                methodPrefix,
                reasoning,
                skipBuildins,
                generateVokabulary,
                vokabularyClassname,
                vokabularyNamespace );
    }

    @Override
    public String toString() {
        return Objects.toStringHelper( this )
                .add( PROPERTY_INPUT_FILENAME, inputFilename )
                .add( PROPERTY_OUTPUT_DIRECTORY, outputDirectory )
                .add( PROPERTY_PACKAGE_NAME, packageName )
                .add( PROPERTY_METHOD_PREFIX, methodPrefix )
                .add( PROPERTY_REASONING, reasoning )
                .add( PROPERTY_SKIP_BUILDINS, skipBuildins )
                .add( PROPERTY_GENERATE_VOKABULARY, generateVokabulary )
                .add( PROPERTY_VOKABULARY_CLASSNAME, vokabularyClassname )
                .add( PROPERTY_VOKABULARY_NAMESPACE, vokabularyNamespace )
                .toString();
    }
}
