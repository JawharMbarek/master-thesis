
package de.m0ep.socc.workbench;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.ontoware.rdf2go.model.Model;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.workbench.controller.ConnectToController;
import de.m0ep.socc.workbench.models.RDFStoreConnection;

public class SoccWorkbenchApp implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger( SoccWorkbenchApp.class );
    private static final String CMD_CONNECT_TO = "cmd_connect_to";

    private JFrame frame;
    private Model model;
    private JMenuBar menuBar;
    private JMenu mnSocc;
    private JMenuItem mntmConnectTo;

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                try {
                    final SoccWorkbenchApp window = new SoccWorkbenchApp();
                    Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {
                        @Override
                        public void run() {
                            window.shutdown();
                        }
                    } ) );

                    window.frame.setVisible( true );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the application.
     */
    public SoccWorkbenchApp() {
        initialize();

    }

    public JFrame getFrame() {
        return frame;
    }

    public Model getModel() {
        return model;
    }

    public void setModel( Model model ) {
        if (null != model && model.isOpen()) {
            model.close();
            model = null;
        }

        this.model = model;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds( 100, 100, 450, 300 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        menuBar = new JMenuBar();
        frame.setJMenuBar( menuBar );

        mnSocc = new JMenu( "SOCC" );
        menuBar.add( mnSocc );

        mntmConnectTo = new JMenuItem( "Connect to..." );
        mntmConnectTo.setActionCommand( CMD_CONNECT_TO );
        mntmConnectTo.addActionListener( this );
        mnSocc.add( mntmConnectTo );
    }

    public void shutdown() {
        LOG.info( "shutting down socc-workbench" );

        if (null != model) {
            model.close();
            model = null;
        }
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        String cmd = e.getActionCommand();

        if (CMD_CONNECT_TO.equals( cmd )) {
            final ConnectToController mcp = new ConnectToController();
            mcp.setOnOk( new Runnable() {
                @Override
                public void run() {
                    RDFStoreConnection model = mcp.getModel();
                    HTTPRepository repository = new HTTPRepository(
                            model.getServerUri(),
                            model.getRepositoryId() );

                    if (!model.isAnonymousLogin()) {
                        repository
                                .setUsernameAndPassword( model.getUsername(), model.getPassword() );
                    }

                    RepositoryModel repositoryModel = new RepositoryModel( repository );
                    repositoryModel.open();
                    setModel( repositoryModel );

                    LOG.debug( "Open remote repository at {}", repository.getRepositoryURL() );
                }
            } );
            mcp.run();
        }
    }
}
