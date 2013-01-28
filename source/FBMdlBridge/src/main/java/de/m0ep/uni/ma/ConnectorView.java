package de.m0ep.uni.ma;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
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
    private Connector connector;
    
    DefaultTreeModel       forumTreeModel;
    MutableTreeNode        forumTreeRoot;
    JTree                  forumTree;

    JList<Post>            postList;
    DefaultListModel<Post> postListModel;
    ListCellRenderer<Post> postListCellRenderer;
    

    public ConnectorView( final Connector connector ) {
        this.connector = connector;

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
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
                        .getLastPathComponent();
                ForumTreeObject fto = (ForumTreeObject) node.getUserObject();

                if( 2 == path.getPathCount() || 3 == path.getPathCount() ) {
                    if( connector.canPostOn( fto.container ) ) {
                        updatePosts( fto.container );
                    }
                }
            }
        } );

        postListModel = new DefaultListModel<Post>();
        postListCellRenderer = new PostListCellRenderer();
        postList = new JList<Post>( postListModel );
        postList.setCellRenderer( postListCellRenderer );


        JSplitPane centerPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane( forumTree ), new JScrollPane( postList ) );
        add( centerPane, BorderLayout.CENTER );

        updateForums();
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
                JList<? extends Post> list, Post value, int index,
                boolean isSelected, boolean cellHasFocus ) {
            
            JPanel base = new JPanel();
            base.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
            base.setLayout( new GridLayout( 4, 1 ) );
            base.add(new JLabel( value.getAllDCTermsTitle_as().firstValue()));
            base.add(new JLabel( value.getAllDCTermsCreated_as().firstValue()));
            base.add(new JLabel( value.getAllSIOCContent_as().firstValue()));
            if( value.hasSIOCAttachment() )
                base.add( new JLabel( value.getAllSIOCAttachment_as()
                        .firstValue()
                    .toString() ) );
            
            return base;
        }
    }
}
