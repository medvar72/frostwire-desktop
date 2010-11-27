package com.frostwire.gnutella.gui.android;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import org.pushingpixels.flamingo.api.bcb.BreadcrumbItem;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbPathEvent;
import org.pushingpixels.flamingo.api.bcb.BreadcrumbPathListener;
import org.pushingpixels.flamingo.api.bcb.core.BreadcrumbFileSelector;

import com.frostwire.gnutella.gui.android.LocalFileListModel.OnRootListener;
import com.limegroup.gnutella.gui.I18n;
import com.limegroup.gnutella.settings.SharingSettings;

public class DesktopExplorer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7362861227107918643L;

	private JToolBar _toolBar;
	private JButton _buttonUp;
	private JButton _buttonNew;
	private JButton _buttonViewThumbnail;
	private JButton _buttonViewList;
	private JButton _buttonFavoriteApplications;
	private JButton _buttonFavoriteDocuments;
	private JButton _buttonFavoritePictures;
	private JButton _buttonFavoriteVideos;
	private JButton _buttonFavoriteRingtones;
	private JButton _buttonFavoriteAudio;
	private JLabel _labelSort;
	private JComboBox _comboBoxSort;
	private BreadcrumbFileSelector _breadcrumb;
	private JList _list;
	private JScrollPane _scrollPane;
	
	private LocalFileListModel _model;
	
	public DesktopExplorer() {
		
		_model = new LocalFileListModel();
		_model.setOnRootListener(new OnRootListener() {
			public void onRoot(LocalFileListModel localFileListModel, File path) {
				_breadcrumb.setPath(path);
			}
		});
		
		setupUI();
		
		setSelectedFolder(SharingSettings.getDeviceFilesDirectory()); // guarantee the creation of files
	}
	
	public File getSelectedFolder() {
		return _model.getRoot();
	}
	
	public void setSelectedFolder(File path) {
		_model.setRoot(path);
	}
	
	public void refresh() {
		_model.refresh();
	}
	
	protected void setupUI() {
		setLayout(new GridBagLayout());
		
		setupTop();
		setupList();
	}
	
	protected void buttonUp_mousePressed(MouseEvent e) {
        File path = _model.getRoot().getParentFile();
        if (path != null) {
            setSelectedFolder(path);
        }
    }
    
    protected void buttonNew_mousePressed(MouseEvent e) {
        LocalFile localFile = _model.createNewFolder();
        if (localFile != null) {
            _list.setSelectedValue(localFile, true);
        }
    }
    
    protected void buttonViewThumbnail_mousePressed(MouseEvent e) {
        _list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    }
    
    protected void buttonViewList_mousePressed(MouseEvent e) {
        _list.setLayoutOrientation(JList.VERTICAL);
    }
    
    protected void comboBoxSort_actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox)e.getSource();
        SortByItem item = (SortByItem)comboBox.getSelectedItem();
        _model.sortBy(item.sortBy);
    }
	
	private void setupTop() {
		
		GridBagConstraints c;
		
		_toolBar = new JToolBar();
		_toolBar.setFloatable(false);
		_toolBar.setRollover(true);
		
		_toolBar.addSeparator();
		
		Dimension toolBarButtonSize = new Dimension(28, 28);
		
		_buttonUp = new JButton();
		_buttonUp.setIcon(new ImageIcon(new ImageTool().load("folder_up")));
		_buttonUp.setPreferredSize(toolBarButtonSize);
		_buttonUp.setMinimumSize(toolBarButtonSize);
		_buttonUp.setMaximumSize(toolBarButtonSize);
		_buttonUp.setSize(toolBarButtonSize);
		_buttonUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				buttonUp_mousePressed(e);
			}
		});
		_toolBar.add(_buttonUp);
		
		_buttonNew = new JButton();
		_buttonNew.setIcon(new ImageIcon(new ImageTool().load("folder_new")));
		_buttonNew.setPreferredSize(toolBarButtonSize);
		_buttonNew.setMinimumSize(toolBarButtonSize);
		_buttonNew.setMaximumSize(toolBarButtonSize);
		_buttonNew.setSize(toolBarButtonSize);
		_buttonNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonNew_mousePressed(e);
            }
        });
        _toolBar.add(_buttonNew);
        
        _toolBar.addSeparator();
        
        _buttonViewThumbnail = new JButton();
        _buttonViewThumbnail.setIcon(new ImageIcon(new ImageTool().load("view_thumbnail")));
        _buttonViewThumbnail.setPreferredSize(toolBarButtonSize);
        _buttonViewThumbnail.setMinimumSize(toolBarButtonSize);
        _buttonViewThumbnail.setMaximumSize(toolBarButtonSize);
        _buttonViewThumbnail.setSize(toolBarButtonSize);
        _buttonViewThumbnail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonViewThumbnail_mousePressed(e);
            }
        });
        _toolBar.add(_buttonViewThumbnail);
        
        _buttonViewList = new JButton();
        _buttonViewList.setIcon(new ImageIcon(new ImageTool().load("view_list")));
        _buttonViewList.setPreferredSize(toolBarButtonSize);
        _buttonViewList.setMinimumSize(toolBarButtonSize);
        _buttonViewList.setMaximumSize(toolBarButtonSize);
        _buttonViewList.setSize(toolBarButtonSize);
        _buttonViewList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonViewList_mousePressed(e);
            }
        });
        _toolBar.add(_buttonViewList);
        
        _toolBar.addSeparator();
        
        _buttonFavoriteApplications = setupButtonFavorite(DeviceConstants.FILE_TYPE_APPLICATIONS, SharingSettings.DEVICE_APPLICATIONS_FILES_DIR);
        _toolBar.add(_buttonFavoriteApplications);
        
        _buttonFavoriteDocuments = setupButtonFavorite(DeviceConstants.FILE_TYPE_DOCUMENTS, SharingSettings.DEVICE_DOCUMENTS_FILES_DIR);
        _toolBar.add(_buttonFavoriteDocuments);
        
        _buttonFavoritePictures = setupButtonFavorite(DeviceConstants.FILE_TYPE_PICTURES, SharingSettings.DEVICE_PICTURES_FILES_DIR);
        _toolBar.add(_buttonFavoritePictures);
        
        _buttonFavoriteVideos = setupButtonFavorite(DeviceConstants.FILE_TYPE_VIDEOS, SharingSettings.DEVICE_VIDEO_FILES_DIR);
        _toolBar.add(_buttonFavoriteVideos);
        
        _buttonFavoriteRingtones = setupButtonFavorite(DeviceConstants.FILE_TYPE_RINGTONES, SharingSettings.DEVICE_RINGTONES_FILES_DIR);
        _toolBar.add(_buttonFavoriteRingtones);
        
        _buttonFavoriteAudio = setupButtonFavorite(DeviceConstants.FILE_TYPE_AUDIO, SharingSettings.DEVICE_AUDIO_FILES_DIR);
        _toolBar.add(_buttonFavoriteAudio);
        
        _toolBar.addSeparator();
        
        _labelSort = new JLabel();
        _labelSort.setText(I18n.tr("Sort:"));
        _toolBar.add(_labelSort);
        
        _comboBoxSort = new JComboBox();
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_NONE, I18n.tr("None")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_NAME_ASC, I18n.tr("Name Asc")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_NAME_DESC, I18n.tr("Name Desc")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_DATE_ASC, I18n.tr("Date Asc")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_DATE_DESC, I18n.tr("Date Desc")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_KIND_ASC, I18n.tr("Kind Asc")));
        _comboBoxSort.addItem(new SortByItem(LocalFileListModel.SORT_BY_KIND_DESC, I18n.tr("Kind Desc")));
        _comboBoxSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxSort_actionPerformed(e);
            }
        });
        _toolBar.add(_comboBoxSort);
        
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(_toolBar, c);
		
		_breadcrumb = new BreadcrumbFileSelector();
		_breadcrumb.getModel().addPathListener(new BreadcrumbPathListener() {
			public void breadcrumbPathEvent(BreadcrumbPathEvent event) {
				breadcrumb_pathEvent(event);
			}
		});
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = 2; // this put a extra column and perform a nice fill at the end in the top row
		add(_breadcrumb, c);
	}

    private void setupList() {
		
		GridBagConstraints c;
		
		_list = new JList(_model);
		_list.setCellRenderer(new LocalFileRenderer());
		RedispatchMouseListener listener = new RedispatchMouseListener(_list);
		_list.addMouseListener(listener);
		_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		_list.setDragEnabled(true);
		_list.setTransferHandler(new DesktopListTransferHandler());
		_list.setPrototypeCellValue(new LocalFile(SharingSettings.getDeviceFilesDirectory()));
		_list.setVisibleRowCount(-1);
		
		_scrollPane = new JScrollPane(_list);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 8;

		add(_scrollPane, c);
	}

	private JButton setupButtonFavorite(int type, final File path) {
	    ImageTool imageTool = new ImageTool();
	    Image image = imageTool.load(imageTool.getImageNameByFileType(type)).getScaledInstance(18, 18, Image.SCALE_SMOOTH);
	    Dimension size = new Dimension(28, 28);
		JButton button = new JButton();
		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setSize(size);
		button.setIcon(new ImageIcon(image));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				setSelectedFolder(path);
			}
		});
		return button;
	}
	
	private void breadcrumb_pathEvent(BreadcrumbPathEvent event) {
		List<BreadcrumbItem<File>> items = _breadcrumb.getModel().getItems();
		
		if (items.size() > 0) {
			File path = items.get(items.size() - 1).getData();
			OnRootListener listener = _model.getOnRootListener();
			_model.setOnRootListener(null); // avoid infinite recursion
			_model.setRoot(path);
			_model.setOnRootListener(listener);
		}
	}
	
	private final class SortByItem {
	    public int sortBy;
	    public String text;
	    
	    public SortByItem(int sortBy, String text) {
	        this.sortBy = sortBy;
	        this.text = text;
	    }
	    
	    @Override
	    public String toString() {
	        return text;
	    }
	}
}
