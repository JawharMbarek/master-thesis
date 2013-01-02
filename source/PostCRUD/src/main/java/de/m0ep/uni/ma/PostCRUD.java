package de.m0ep.uni.ma;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.Reasoning;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.model.node.URI;

import de.m0ep.uni.ma.rdf.sioc.Post;

public class PostCRUD extends JFrame {
    private static final long serialVersionUID = 5598704821953402509L;
    private NotifyingModelLayer model;

    public static final String NS               = "http://m0ep.de/rdf/";
    
    JList<String>             list;
    ListModel<String>         listModel;
    JButton                    newPost;
    JButton                    deletePost;
    
    public PostCRUD( final Model model ) {
        this.model = new NotifyingModelLayer( model );
        
        setLayout( new BorderLayout() );
        setSize( 500, 500 );

        listModel = new PostListModel( this.model );
        list = new JList<String>( listModel );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        add( list, BorderLayout.CENTER );

        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout( new FlowLayout( FlowLayout.CENTER ) );

        newPost = new JButton( "Create Post" );
        newPost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                long id = new Date().getTime();
                new Post( PostCRUD.this.model, NS + id, true );
            }
        });
        buttonGroup.add( newPost );
        deletePost = new JButton( "Delete Post" );
        deletePost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                int i = list.getSelectedIndex();

                if( 0 <= i ) {

                    String uri = listModel.getElementAt( i );
                    Post.deleteAllProperties( PostCRUD.this.model,
                            PostCRUD.this.model.createURI( uri ) );
                }
            }
        } );
        buttonGroup.add( deletePost );
        add( buttonGroup, BorderLayout.SOUTH );

        getAllManagedUris( Post.class );

    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel( Reasoning.rdfs );
        model.open();
        new PostCRUD( model ).setVisible( true );
    }

    private List<URI> getAllManagedUris( Class<?> clazz ) {
        if( null == clazz ) {
            return null;
        }

        System.out.println( clazz.getSimpleName() + " : " + clazz.getPackage() );

        try {
            Field managed_uris = clazz.getField( "MANAGED_URIS" );

            if( 0 != ( managed_uris.getModifiers() & Modifier.STATIC ) ) {
                managed_uris.setAccessible( true );

                URI[] uris = (URI[]) managed_uris.get( null );
                for ( URI uri : uris ) {
                    System.out.println( uri );
                }
            }


        } catch ( NoSuchFieldException e ) {
        } catch ( SecurityException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IllegalArgumentException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Class<?> superClass = clazz.getSuperclass();
        getAllManagedUris( superClass );

        return null;
    }
}
