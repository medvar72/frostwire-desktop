package com.frostwire.gui.bittorrent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.frostwire.torrent.CreativeCommonsLicense;
import com.limegroup.gnutella.gui.GUIUtils;
import com.limegroup.gnutella.gui.I18n;
import com.limegroup.gnutella.gui.LimeTextField;

public class CreativeCommonsSelectorPanel extends JPanel {
    
    private final JCheckBox confirmRightfulUseOfLicense;
    
    private final LimeTextField authorsName;
    
    public CreativeCommonsSelectorPanel() {
        setLayout(new MigLayout("fill"));
        GUIUtils.setTitledBorderOnPanel(this, I18n
                .tr("Choose a Creative Commons License for this Content"));
        
        confirmRightfulUseOfLicense = new JCheckBox("<html><strong>" + I18n.tr("I am the Content Creator of this work or I have been granted the right to share this content under the following license by the Content Creator of this work.") + "</strong></html>");
        authorsName = new LimeTextField(I18n.tr("Author's Name"));
        
        initComponents();
    }

    private void initComponents() {
        confirmRightfulUseOfLicense.setSelected(false);
        confirmRightfulUseOfLicense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               onConfirmRightfulUseOfLicenseAction(); 
            }
        });
        
        add(confirmRightfulUseOfLicense,"growx, wrap");
        add(authorsName, "growx, wrap");
    }

    protected void onConfirmRightfulUseOfLicenseAction() {
        //TODO: enable/disable the rest of the components.
    }

    public boolean hasCreativeCommonsLicense() {
        return confirmRightfulUseOfLicense.isSelected(); //&&;
    }

    public CreativeCommonsLicense getCreativeCommonsLicense() {
        return null;
    }
}