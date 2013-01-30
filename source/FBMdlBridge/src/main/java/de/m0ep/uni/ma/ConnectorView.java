package de.m0ep.uni.ma;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import de.m0ep.uni.ma.rdf.sioc.Container;
import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.Thread;
import de.m0ep.uni.ma.socc.connectors.Connector;

public class ConnectorView extends JFrame {
    private static final long      serialVersionUID = 7211115399954970463L;

    private Connector connector;
    
    private DefaultTreeModel       forumTreeModel;
    private MutableTreeNode        forumTreeRoot;
    private JTree                  forumTree;

    private JList<Post>            postList;
    private DefaultListModel<Post> postListModel;
    private ListCellRenderer<Post> postListCellRenderer;
    

    private Container              currentPostSelection;

    public ConnectorView( final Connector connector ) {
        this.connector = connector;

        setTitle( connector.getUserFriendlyName() );
        setSize( 500, 500 );
        setLayout( new BorderLayout() );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        forumTreeRoot = new DefaultMutableTreeNode();
        forumTreeModel = new DefaultTreeModel( forumTreeRoot );
        forumTree = new JTree( forumTreeModel );
        forumTree.setRootVisible( false );
        forumTree.addTreeSelectionListener( new TreeSelectionListener() {

            public void valueChanged( TreeSelectionEvent e ) {
                TreePath path = forumTree.getSelectionPath();
                if( null == path )
                    return;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
                        .getLastPathComponent();
                ForumTreeObject fto = (ForumTreeObject) node.getUserObject();

                if( 2 == path.getPathCount() || 3 == path.getPathCount() ) {
                    if( connector.canPostOn( fto.container ) ) {
                        currentPostSelection = fto.container;
                        updatePosts( fto.container );
                    }
                }
            }
        } );

        JPanel postPanel = new JPanel( new BorderLayout() );

        postListModel = new DefaultListModel<Post>();
        postListCellRenderer = new PostListCellRenderer();
        postList = new JList<Post>( postListModel );
        postList.setCellRenderer( postListCellRenderer );

        postList.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                if( MouseEvent.BUTTON3 == e.getButton() ) {
                    int index = postList.locationToIndex( e.getPoint() );

                    if( 0 <= index ) {
                        Post p = postListModel.get( index );
                        postReply( p );
                    }
                }
            }
        } );

        postPanel.add( new JScrollPane( postList ), BorderLayout.CENTER );

        JButton btnCreatePost = new JButton( "Create Post" );
        btnCreatePost.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {
                postNew();
            }
        } );
        postPanel.add( btnCreatePost, BorderLayout.SOUTH );

        JSplitPane centerPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane( forumTree ), postPanel );
        add( centerPane, BorderLayout.CENTER );

        updateForums();
    }

    protected void postReply( Post parent ) {
        JTextField subject = new JTextField();
        JTextField content = new JTextField();

        JComponent[] input = { new JLabel( "subject" ), subject,
                new JLabel( "message" ), content };

        int action = JOptionPane.showConfirmDialog( this, input, "Post Reply",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE );

        if( JOptionPane.OK_OPTION == action ) {
            System.out.println( subject.getText() + " " + content.getText() );
            
            Post reply = connector.getModel().createPost(
                    "http://example.com/tmppost/" + new Date().getTime() );
            reply.setDCTermsTitle( subject.getText() );
            reply.setSIOCContent( content.getText() );

            connector.commentPost( reply, parent );
            connector.getModel().removePost( reply );
        }
    }

    protected void postNew() {
        JTextField subject = new JTextField();
        JTextField content = new JTextField();

        JComponent[] input = { new JLabel( "subject" ), subject,
                new JLabel( "message" ), content };

        int action = JOptionPane.showConfirmDialog( this, input, "Post Reply",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE );

        if( JOptionPane.OK_OPTION == action ) {
            System.out.println( subject.getText() + " " + content.getText() );

            Post post = connector.getModel().createPost(
                    "http://example.com/tmppost/" + new Date().getTime() );
            post.setDCTermsTitle( subject.getText() );
            post.setSIOCContent( content.getText() );

            connector.publishPost( post, currentPostSelection );
            connector.getModel().removePost( post );
        }
    }

    private void updateForums() {
        List<Forum> forums = connector.getForums();
        
        MutableTreeNode root = new DefaultMutableTreeNode();
        
        for(Forum forum : forums){
            ForumTreeObject forumFto = new ForumTreeObject( forum
                    .getAllSIOCName_as().firstValue(), forum );
            MutableTreeNode forumNode = new DefaultMutableTreeNode( forumFto );
            
            List<Thread> threads = connector.getThreads( forum );
            for ( Thread thread : threads ) {
                ForumTreeObject threadFto = new ForumTreeObject( thread
                        .getAllSIOCName_as().firstValue(), thread );
                MutableTreeNode threadNode = new DefaultMutableTreeNode(
                        threadFto );
                forumNode.insert( threadNode, forumNode.getChildCount() );
            }
            
            root.insert( forumNode, root.getChildCount() );
        }
        
        forumTreeModel.setRoot( root );
    }

    private void updatePosts( Container container ) {
        List<Post> posts = connector.getPost( container );
        postListModel.removeAllElements();
        
        for(Post post : posts){
            postListModel.add( postListModel.getSize(), post );
        }
    }
    
    private static class ForumTreeObject {
        public String    name;
        public Container container;

        public ForumTreeObject( String name, Container container ) {
            super();
            this.name = name;
            this.container = container;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class PostListCellRenderer implements ListCellRenderer<Post>{

        public Component getListCellRendererComponent(
                final JList<? extends Post> list, final Post value,
                final int index, final boolean isSelected,
                final boolean cellHasFocus ) {
            
            JPanel base = new JPanel();
            base.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
            base.setLayout( new GridLayout( 6, 1 ) );
            
            JLabel title = new JLabel( 
 ( value.hasDCTermsTitle() ) ? value
                    .getAllDCTermsTitle_as().firstValue() : value
                    .getAllDCTermsSubject_as().firstValue() );
            base.add( title );

            JLabel created = new JLabel( value.getAllDCTermsCreated_as().firstValue());
            base.add(created);

            JLabel content = new JLabel( value.getAllSIOCContent_as()
                    .firstValue() );
            base.add(content);

            JLabel attachment = new JLabel(
                    ( value.hasSIOCAttachment() ) ? ( value
                            .getAllSIOCAttachment_as().firstValue().toString() )
                            : ( "-no attachment-" ) );
            base.add( attachment );

            JLabel parent = new JLabel( ( value.hasSIOCReplyof() ) ? ( value
                    .getAllSIOCReplyof_as().firstValue().toString() )
                    : ( "-no parent-" ) );
            base.add( parent );

            return base;
        }
    }
}
