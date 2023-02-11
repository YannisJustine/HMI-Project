package fr.iutfbleau.projetIHM2022FI2.view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.icons.FlatSearchIcon;

import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;

/**
 * Classe représentant le panneau permettant d'effectuer une recherche d'étudiants par leur nom
 */
public class ResearchStudents extends JPanel{

    private JTextField researchBar;
    private DefaultListModel<Etudiant> listModel;
    private JList<Etudiant> list;

    /**
     * Constructeur
     * Le panneau est composé d'une barre de recherche et d'une liste affichant les étudiants
     * Barre de recherche customisée par la librarie (voir <a href="https://www.formdev.com/flatlaf/"> FlatLaf</a>)
     */
    public ResearchStudents() {
        super();
        this.setLayout(new BorderLayout());
        this.researchBar = new JTextField(20);
        researchBar.putClientProperty("JTextField.showClearButton", true);   
        researchBar.putClientProperty("JTextField.placeholderText", "Entrez un nom");
        researchBar.putClientProperty("JTextField.leadingIcon", new FlatSearchIcon());
        researchBar.getMaximumSize().width = 250;

        this.listModel = new DefaultListModel<Etudiant>();
        this.list = new JList<Etudiant>(this.listModel);

        this.list.setCellRenderer(new CustomListRenderer());
        this.list.setLayoutOrientation(JList.VERTICAL_WRAP);
        this.list.setVisibleRowCount(-1);
        this.list.putClientProperty("List.isFileList", Boolean.TRUE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        
        panel.add(new JLabel("Barre de recherche"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        panel.add(researchBar, gbc);
        this.add(panel,BorderLayout.NORTH);
      
        
        JPanel panelList = new JPanel(new BorderLayout());
        panelList.setBorder(new EmptyBorder(20,5,20,5));
        panelList.add(new JLabel("Double clic pour voir les groupes"), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(list);
        panelList.add(scrollPane, BorderLayout.CENTER);
        this.add(panelList,BorderLayout.CENTER);

    }

    /**
     * Retourne le modèle de la liste
     * @return le modèle de la liste
     * @see javax.swing.DefaultListModel
     */
    public DefaultListModel<Etudiant> getListModel() {
        return listModel;
    }

  /**
     * Retourne la liste
     * @return la liste
     * @see javax.swing.JList
     */
    public JList<Etudiant> getList() {
        return list;
    }

    /**
     * Permet d'ajouter un {@link javax.swing.event.DocumentListener DocumentListener} à la barre de recherche
     * @param dl le listener
     */
    public void setResearchBarListener(DocumentListener dl) {
        this.researchBar.getDocument().addDocumentListener(dl);
    }

    /**
     * Permet d'ajouter un {@link java.awt.event.MouseListener MouseListener} à la liste
     * @param ml le mouse listener à la liste
     */
    public void addListMouseListener(MouseListener ml) {
        this.list.addMouseListener(ml);
    }

}
