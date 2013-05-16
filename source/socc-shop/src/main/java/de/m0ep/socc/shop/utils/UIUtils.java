package de.m0ep.socc.shop.utils;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;

public final class UIUtils {
    public static JPopupMenu createTitledPopupMenu(final String title) {
	JPopupMenu result = new JPopupMenu();

	JLabel lblTitle = new JLabel("Route Popup");
	lblTitle.setAlignmentX(0.5f); // align text to the center
	lblTitle.setOpaque(true);
	lblTitle.setForeground(lblTitle.getBackground());
	lblTitle.setBackground(Color.DARK_GRAY);
	lblTitle
		.setBorder(new LineBorder(lblTitle.getBackground(), 5));
	result.add(lblTitle);

	return result;
    }
}
