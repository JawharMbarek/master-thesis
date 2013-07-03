
package de.m0ep.foaftool;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.Node;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import com.google.common.base.Objects;
import com.xmlns.foaf.FOAFVocabulary;

public class FoafTool implements ActionListener {

    private static final String ACTION_CONNECT_TO = "action_connect_to";
    private static final String ACTION_CLOSE = "action_close";

    private Model model;
    private HTTPRepository httpRepository;

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmConnectTo;
    private JMenuItem mntmClose;

    private DefaultListModel<PersonEntry> listPersonsModel;
    private JList<PersonEntry> listPersons;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FoafTool window = new FoafTool();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FoafTool() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closeModelConnection();
            }
        });

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmConnectTo = new JMenuItem("Connect to...");
        mntmConnectTo.setActionCommand(ACTION_CONNECT_TO);
        mntmConnectTo.addActionListener(this);
        mnFile.add(mntmConnectTo);

        mntmClose = new JMenuItem("Close");
        mntmClose.setActionCommand(ACTION_CLOSE);
        mntmClose.addActionListener(this);
        mnFile.add(mntmClose);

        listPersonsModel = new DefaultListModel<>();
        listPersons = new JList<PersonEntry>(listPersonsModel);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(listPersons);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (ACTION_CLOSE.equals(cmd)) {
            onClose();
        } else if (ACTION_CONNECT_TO.equals(cmd)) {
            onConnectTo();
        }
    }

    private void onClose() {
        closeModelConnection();
        frame.setVisible(false);
        frame.dispose();
    }

    private void onConnectTo() {
        ConnectToDialog dialog = new ConnectToDialog(this.frame);
        dialog.setServerUrl("http://localhost:8080/openrdf-sesame/");
        dialog.setRepositoryId("master-thesis");
        int opt = dialog.openDialog();

        if (ConnectToDialog.OK_OPTION == opt) {
            closeModelConnection();

            httpRepository = new HTTPRepository(
                    dialog.getServerUrl(),
                    dialog.getRepositoryId());

            if (!dialog.isAnonymousLogin()) {
                httpRepository.setUsernameAndPassword(
                        dialog.getUsername(),
                        dialog.getPassword());
            }

            try {
                httpRepository.initialize();
                model = new RepositoryModel(httpRepository);
                model.open();
                fillList();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeModelConnection() {
        if (null != model && model.isOpen()) {
            model.close();
        }

        if (null != httpRepository && httpRepository.isInitialized()) {
            try {
                httpRepository.shutDown();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }

        model = null;
        httpRepository = null;
    }

    private void fillList() {
        if (null != model && model.isOpen()) {
            String queryString = "SELECT ?person ?name ?nick\n" +
                    "WHERE {\n" +
                    "?person a " + FOAFVocabulary.Person.toSPARQL() + ".\n" +
                    "?person " + FOAFVocabulary.name.toSPARQL() + " ?name .\n" +
                    "OPTIONAL {" +
                    "?person " + FOAFVocabulary.nick.toSPARQL() + " ?nick } }";

            System.out.println(queryString);
            QueryResultTable resultTable = model.sparqlSelect(queryString);

            for (final QueryRow row : resultTable) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Node node = row.getValue("person");
                        String name = row.getLiteralValue("name");
                        Node nick = row.getValue("nick");

                        System.out.println(node);
                        PersonEntry entry = new PersonEntry(node, name);

                        if (null != nick) {
                            entry.setNick(nick.toString());
                        }

                        listPersonsModel.add(listPersonsModel.size(), entry);
                        listPersons.invalidate();
                    }
                });
            }
        }
    }

    private class PersonEntry {
        private Node node;
        private String name;
        private String nick;

        public PersonEntry() {
            super();
        }

        public PersonEntry(Node node, String name) {
            super();
            this.node = node;
            this.name = name;
        }

        public Node getResource() {
            return node;
        }

        public void setResource(Node node) {
            this.node = node;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(node, name);
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            if (this.getClass() != obj.getClass()) {
                return false;
            }

            PersonEntry other = (PersonEntry) obj;

            return Objects.equal(this.node, other.node) &&
                    Objects.equal(this.name, other.name);
        }

        @Override
        public String toString() {
            return name + ((null != nick) ? (" ( " + nick + " )") : (""));
        }
    }

}
