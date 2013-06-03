package de.m0ep.socc.shop.ui.components;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.shop.utils.Icons;

public class SIOCPostPane extends JPanel {
    private static final long serialVersionUID = 1L;

    private Post post;
    private JLabel lblTitle;
    private JTextArea txtrContent;
    private JLabel lblAuthor;
    private JLabel lblCreated;
    private JPanel panelAttachments;

    /**
     * Create the panel.
     */
    public SIOCPostPane() {
	setLayout(new FormLayout(new ColumnSpec[] {
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("default:grow"),
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("default:grow"),
		FormFactory.RELATED_GAP_COLSPEC, },
		new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("default:grow"),
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC, }));

	lblTitle = new JLabel("title");
	add(lblTitle, "2, 2, 3, 1");

	lblAuthor = new JLabel("author");
	lblAuthor.setFont(new Font("Dialog", Font.ITALIC, 10));
	add(lblAuthor, "2, 4");

	lblCreated = new JLabel("created");
	lblCreated.setFont(new Font("Dialog", Font.ITALIC, 10));
	add(lblCreated, "4, 4");

	txtrContent = new JTextArea();
	txtrContent.setWrapStyleWord(true);
	txtrContent.setLineWrap(true);
	txtrContent.setEditable(false);
	txtrContent.setText("content");
	add(txtrContent, "2, 6, 3, 1, fill, fill");

	panelAttachments = new JPanel();
	panelAttachments.setLayout(new BoxLayout(panelAttachments,
		BoxLayout.PAGE_AXIS));
	add(panelAttachments, "2, 8, 3, 1, fill, fill");

	JSeparator separator = new JSeparator();
	add(separator, "2, 9, 3, 1");
    }

    public SIOCPostPane(final Post post) {
	this();
	setPost(post);
    }

    private void updatePanel() {
	if (null == post) {
	    return;
	}

	if (post.hasTitle()) {
	    lblTitle.setText(post.getTitle());
	} else if (post.hasSubject()) {
	    lblTitle.setText(post.getSubject());
	} else {
	    lblTitle.setText("");
	}

	if (post.hasContent()) {
	    txtrContent.setText(post.getContent());
	} else if (post.hasContentEncoded()) {
	    txtrContent.setText(post.getContentEncoded());
	} else {
	    txtrContent.setText("");
	}

	if (post.hasCreators()) {
	    UserAccount author = post.getCreator();

	    if (author.hasName()) {
		lblAuthor.setText(author.getName());
	    } else if (author.hasAccountName()) {
		lblAuthor.setText(author.getAccountName());
	    } else {
		lblAuthor.setText("");
	    }
	} else {
	    lblAuthor.setText("");
	}

	if (post.hasCreated()) {
	    lblCreated.setText(post.getCreated());
	} else if (post.hasModified()) {
	    lblCreated.setText(post.getModified());
	}
	else {
	    lblCreated.setText("");
	}

	panelAttachments.removeAll();
	if (post.hasAttachments()) {
	    ClosableIterator<Thing> attachIter = post.getAllAttachments();
	    while (attachIter.hasNext()) {
		Thing siocThing = (Thing) attachIter.next();
		panelAttachments.add(
			new JLabel(
				siocThing.asResource().toString(),
				Icons.ATTACH,
				JLabel.LEADING));
	    }
	    attachIter.close();
	}
    }

    public Post getPost() {
	return post;
    }

    public void setPost(Post post) {
	this.post = post;

	updatePanel();
    }
}
