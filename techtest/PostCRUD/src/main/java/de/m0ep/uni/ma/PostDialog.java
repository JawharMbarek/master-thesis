package de.m0ep.uni.ma;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;

import com.google.common.base.Preconditions;

import de.m0ep.uni.ma.rdf.sioc.Post;

public class PostDialog extends JFrame {
    private static final long serialVersionUID = 6045163032961504058L;

    private static enum Mode {
        EDIT, CREATE
    }

    private Model model;
    private Mode  mode;
    private URI   uri;

    private JTextField editTitle;
    private JTextField editCreated;
    private JTextField editContent;

    private PostDialog( final Model model, final Mode mode, final URI uri ) {
        this.model = model;
        this.mode = mode;
        this.uri = uri;

        initGUI();
        fillDialog();
    }

    public static void showCreateDialog( final Model model ) {
        Preconditions.checkNotNull( model );

        PostDialog dialog = new PostDialog( model, Mode.CREATE, null );
        dialog.setVisible( true );
    }

    public static void showEditDialog( final Model model, final URI uri ) {
        Preconditions.checkNotNull( model );
        Preconditions.checkNotNull( uri );

        PostDialog dialog = new PostDialog( model, Mode.EDIT, uri );
        dialog.setVisible( true );
    }

    private void initGUI() {
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        GridLayout grid = new GridLayout( 4, 2 );
        setLayout( grid );

        add( new JLabel( "Title:" ) );
        editTitle = new JTextField();
        add( editTitle );
        add( new JLabel( "Created:" ) );
        editCreated = new JTextField();
        add( editCreated );
        add( new JLabel( "Content:" ) );
        editContent = new JTextField();
        add( editContent );

        add( new JLabel() );
        JPanel actionsPane = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
        JButton cancel = new JButton( "Cancel" );
        cancel.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                PostDialog.this.setVisible( false );
                PostDialog.this.dispose();
            }
        } );
        actionsPane.add( cancel );
        JButton save = new JButton( "Save" );
        save.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                onSave();
                PostDialog.this.setVisible( false );
                PostDialog.this.dispose();
            }
        } );
        actionsPane.add( save );
        add( actionsPane );
        pack();
    }

    private void fillDialog() {

        if( Mode.EDIT == mode ) {
            Post post = Post.getInstance( model, uri );

            if( post.hasDCTermsTitle() ) {
                ClosableIterator<Node> iter = post.getAllDCTermsTitle_asNode();
                editTitle.setText( iter.next().asLiteral().toString() );
                iter.close();
            }

            if( post.hasDCTermsCreated() ) {
                ClosableIterator<Node> iter = post
                        .getAllDCTermsCreated_asNode();
                editCreated.setText( iter.next().asLiteral().toString() );
                iter.close();
            }

            if( post.hasSIOCContent() ) {
                ClosableIterator<String> iter = post.getAllSIOCContent();
                editContent.setText( iter.next() );
                iter.close();
            }
        }
 else {
            editTitle.setText( "" );
            editCreated.setText( RDFTool.dateTime2String( new Date() ) );
            editContent.setText( "" );
        }
    }

    private void onSave() {
        Post post = null;

        if( Mode.CREATE == mode ) {
            long id = new Date().getTime();
            post = new Post( this.model, PostCRUD.NS + id, true );
            post.setSIOCId( Long.toString( id ) );
        }
        else {
            post = Post.getInstance( this.model, this.uri );
        }
        
        post.setDCTermsTitle( model.createPlainLiteral( editTitle.getText() ) );
        post.setDCTermsCreated( editCreated.getText() );
        post.setSIOCContent( editContent.getText() );
    }
}
