package de.m0ep.uni.ma;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.Reasoning;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.util.RDFTool;

import com.google.common.base.Objects;

public class PostCRUD extends JFrame {
    private static final long serialVersionUID = 5598704821953402509L;
    private NotifyingModelLayer model;
    
    private class PostData{
        public String uri;
        public String title;
        
        
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( uri == null ) ? 0 : uri.hashCode() );
            return result;
        }

        @Override
        public boolean equals( Object obj ) {
            if( this == obj ) {
                return true;
            }
            
            if( obj == null ) {
                return false;
            }
            
            if( getClass() != obj.getClass() ) {
                return false;
            }
            
            PostData other = (PostData) obj;
            if( uri == null ) {
                if( other.uri != null ) {
                    return false;
                }
            } else if( !uri.equals( other.uri ) ) {
                return false;
            }
            
            return true;
        }

        public PostData( String uri, String title ) {
            super();
            this.uri = uri;
            this.title = title;
        }



        @Override
        public String toString() {
            return Objects.toStringHelper( getClass() ).add( "uri", uri )
                    .add( "title", title ).toString();
        }
    }
    
    JList<PostData> list;
    DefaultListModel<PostData> listModel;
    JButton                    newPost;
    JButton                    deletePost;
    
    public PostCRUD( final Model model ) {
        this.model = new NotifyingModelLayer( model );
        
        setLayout( new BorderLayout() );

        listModel = new DefaultListModel<PostCRUD.PostData>();
        list = new JList<PostCRUD.PostData>( listModel );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        add( list, BorderLayout.CENTER );

        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout( new FlowLayout( FlowLayout.CENTER ) );

        newPost = new JButton( "Create Post" );
        newPost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                
                long id = new Date().hashCode();
                
                listModel.addElement( new PostData( "test" + id, "post at "
                        + RDFTool.dateTime2String( new Date() ) ) );
            }
        });
        buttonGroup.add( newPost );
        deletePost = new JButton( "Delete Post" );
        deletePost.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                int i = list.getSelectedIndex();

                if( 0 <= i ) {
                    listModel.remove( i );
                }
            }
        } );
        buttonGroup.add( deletePost );
        add( buttonGroup, BorderLayout.SOUTH );
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel( Reasoning.rdfs );
        new PostCRUD( model ).setVisible( true );
    }

}
