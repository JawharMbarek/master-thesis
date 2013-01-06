package de.m0ep.uni.ma;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.Reasoning;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.RDFTool;

import de.m0ep.uni.ma.rdf.ResourceIO;
import de.m0ep.uni.ma.rdf.sioc.Post;

public class PostCRUD extends JFrame {
    private static final long   serialVersionUID = 5598704821953402509L;
    private static final String NS               = "http://m0ep.de/rdf/";
    private NotifyingModelLayer model;

    private JButton             newPost;
    private JButton             deletePost;
    private JButton             exportPost;
    private JButton             importPost;
    
    private JList               list;
    private ListModel           listModel;
    private JTree               treePane;
    private DefaultTreeModel    treeModel;

    public PostCRUD( final Model model ) {
        this.model = new NotifyingModelLayer( model );
        
        setLayout( new BorderLayout() );
        setSize( 500, 500 );

        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout( new FlowLayout( FlowLayout.CENTER ) );

        newPost = new JButton( "Create Post" );
        newPost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                long id = new Date().getTime();
                Post p = new Post( PostCRUD.this.model, NS + id, true );
                p.setSIOCTitle( model.createPlainLiteral( "test titel" ) );
                p.addSIOCDate( model.createPlainLiteral( RDFTool
                        .dateTime2String( new Date() ) ) );
            }
        });
        buttonGroup.add( newPost );
        
        deletePost = new JButton( "Delete Post" );
        deletePost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Object[] values = list.getSelectedValues();

                if( 0 < values.length ) {
                    for ( Object value : values ) {
                        Post.deleteAllProperties( PostCRUD.this.model,
                                PostCRUD.this.model.createURI( value.toString() ) );
                    }
                }
            }
        } );
        buttonGroup.add( deletePost );
       
        exportPost = new JButton( "Export" );
        exportPost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Object[] values = list.getSelectedValues();

                if( 0 < values.length ) {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogType( JFileChooser.SAVE_DIALOG );
                    fc.setDialogTitle( "Save Post(s)" );
                    fc.setSelectedFile( new File( "*.rdf" ) );
                    int res = fc.showDialog( PostCRUD.this, "Save" );
                    
                    if(JFileChooser.APPROVE_OPTION == res){
                        File outFile = fc.getSelectedFile();
                    
                        Resource[] resources = new Resource[values.length];
                        for ( int i = 0; i < values.length; i++ ) {
                            resources[i] = model.createURI( values[i]
                                    .toString() );
                        }
                    
                        try {
                            ResourceIO.write( PostCRUD.this.model, outFile,
                                    Syntax.RdfXml, resources );
                        } catch ( IOException e1 ) {
                            JOptionPane.showMessageDialog(
                                    PostCRUD.this,
                                    "Faild to save Post to "
                                                    + outFile.getAbsolutePath()
                                                    + "\n\n"
                                            + e1.getLocalizedMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        } );
        buttonGroup.add( exportPost );
        
        importPost = new JButton("Import");
        importPost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogType( JFileChooser.OPEN_DIALOG );
                fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
                fc.setMultiSelectionEnabled( true );
                fc.setDialogTitle( "Load Post(s)" );
                int res = fc.showDialog( PostCRUD.this, "Open" );

                if( JFileChooser.APPROVE_OPTION == res ) {
                    System.out.println( "open " );
                    File[] files = fc.getSelectedFiles();

                    for ( File file : files ) {
                        System.out.println( file );
                        try {
                            ResourceIO.read( PostCRUD.this.model, file );
                        } catch ( IOException e1 ) {
                            JOptionPane.showMessageDialog(
                                    PostCRUD.this,
                                    "Faild to read Post from "
                                            + file.getAbsolutePath() + "\n\n"
                                            + e1.getLocalizedMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        } );
        buttonGroup.add( importPost );
        
        add( buttonGroup, BorderLayout.SOUTH );


        listModel = (ListModel) new PostListModel( this.model );
        list = new JList( listModel );
        list.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        list.addListSelectionListener( new ListSelectionListener() {

            @Override
            public void valueChanged( ListSelectionEvent e ) {
                if( e.getValueIsAdjusting() )
                    return;

                Object[] selected = list.getSelectedValues();

                if( 0 == selected.length ) {
                    treeModel.setRoot( null );
                    return;
                }

                MutableTreeNode root = new DefaultMutableTreeNode(
                        "Selected posts" );
                treeModel.setRoot( root );

                for ( Object object : selected ) {
                    addPostToTree( root, model, object.toString() );
                }

                for ( int i = 0; i < treePane.getRowCount(); i++ ) {
                    treePane.expandRow( i );
                }
            }
        } );

        treeModel = new DefaultTreeModel( null );
        treePane = new JTree( treeModel );
        treePane.setMinimumSize( new Dimension() );

        JSplitPane split = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane( list ), new JScrollPane( treePane ) );
        add( split, BorderLayout.CENTER );
    }

    private void addPostToTree( MutableTreeNode root, Model model,
            String postURI ) {

        MutableTreeNode post = new DefaultMutableTreeNode( postURI );
        treeModel.insertNodeInto( post, root, root.getChildCount() );

        ClosableIterator<Statement> iter = model.findStatements(
                model.createURI( postURI ), Variable.ANY, Variable.ANY );

        while ( iter.hasNext() ) {
            Statement statement = (Statement) iter.next();

            TreeNode property = null;
            String propertyName = getLastElementOfURI( statement.getPredicate()
                    .toString() );

            @SuppressWarnings( "unchecked" )
            Enumeration<TreeNode> children = post.children();

            while ( children.hasMoreElements() ) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) children
                        .nextElement();

                if( node.getUserObject().equals( propertyName ) ) {
                    property = node;
                    break;
                }
            }

            if( null == property ) {
                property = new DefaultMutableTreeNode( propertyName );
                treeModel.insertNodeInto( (MutableTreeNode) property,
                        (MutableTreeNode) post, post.getChildCount() );
            }

            MutableTreeNode leaf = new DefaultMutableTreeNode( statement
                    .getObject().toString() );
            treeModel.insertNodeInto( leaf, (MutableTreeNode) property,
                    property.getChildCount() );

        }
        iter.close();
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel( Reasoning.rdfs );
        model.open();
        new PostCRUD( model ).setVisible( true );
    }

    /**
     * Returns the element of an URI behind the last occurred '/' or '#'
     * 
     * e.g. getLastElementOfUri("http://example.com/name) = "name"
     * getLastElementOfUri("http://example.com/name#surname) = "surname"
     * 
     * @param uri
     * @return
     */
    public String getLastElementOfURI( String uri ) {
        // search for fragment
        int index = uri.lastIndexOf( '#' );

        if( -1 == index ) {
            // get last element of the path if no fragment is present
            index = uri.lastIndexOf( '/' );
        }

        return uri.substring( index + 1 );
    }

    public List<URI> getAllManagedUris( List<URI> uris, Class<?> clazz ) {
        if( null == uris )
            uris = new ArrayList<URI>();

        if( null == clazz )
            return uris;

        try {
            Field field = clazz.getField( "MANAGED_URIS" );

            int modifier = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
            
            if( 0 != ( field.getModifiers() & modifier ) ) {
                field.setAccessible( true );

                URI[] managed_uris = (URI[]) field.get( null );
                for ( URI uri : managed_uris ) {
                    uris.add( uri );
                }
            }
        } catch ( NoSuchFieldException e ) {
            // ignore it
        } catch ( SecurityException e ) {
            // ignore it
        } catch ( IllegalArgumentException e ) {
            // ignore it
        } catch ( IllegalAccessException e ) {
            // ignore it
        }

        // iterate over all superclasses
        Class<?> superClass = clazz.getSuperclass();
        return getAllManagedUris( uris, superClass );
    }
}
