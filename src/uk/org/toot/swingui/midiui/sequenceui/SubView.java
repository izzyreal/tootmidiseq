/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

public class SubView extends JPanel implements ActionListener
{
    private JComponent content;

    public SubView(JComponent content, String title) {
        setLayout(new BorderLayout());
        add(new SubBar(title), BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        this.content = content;
    }

    public void actionPerformed(ActionEvent ae) {
        if ( ae.getActionCommand().equals("-") ) {
            content.setVisible(!content.isVisible());
        }
    }

    private class SubBar extends JToolBar
    {
        public SubBar(String title) {
            setFloatable(false);
            setBackground(new Color(15, 50, 125));
            add(new SubButton());
            JLabel label = new JLabel("  "+title);
            label.setForeground(Color.white);
            add(label);
        }
    }

    private class SubButton extends JToggleButton
    {
        public SubButton() {
            super("-", true);
            addActionListener(SubView.this);
        }
    }

}
