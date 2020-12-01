/**
 * Class: CMSC350
 * Author: Mark Tasker
 * Date: 12/15/19
 * Project Description: This project will behave like a Java command line compiler.
 *      It's meant to recompile a class and those classes that rely on it. The code
 *      of the GUI class exists to present a GUI that the user can interact with.
 *      It also contains the code for graph building and topical ordering
 * 
 */

 
import java.awt.BorderLayout;
import java.awt.EventQueue;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
 
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 
public class GUI extends JFrame {
 
    private JPanel contentPane;
    private JTextField txtInputFIle;
    private JTextField txtOutputFile;
    private JTextArea txtResult;
    private DependencyGraph graph;
 
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    /**
     * Create the frame.
     */
    public GUI() {
        setTitle("Class Dependency Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
 
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);
 
        JLabel lblNewLabel = new JLabel("Input file name:");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);
 
        txtInputFIle = new JTextField();
        GridBagConstraints gbc_txtInputFIle = new GridBagConstraints();
        gbc_txtInputFIle.insets = new Insets(0, 0, 5, 5);
        gbc_txtInputFIle.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtInputFIle.gridx = 1;
        gbc_txtInputFIle.gridy = 0;
        panel.add(txtInputFIle, gbc_txtInputFIle);
        txtInputFIle.setColumns(10);
 
        JButton btnNewButton = new JButton("Build Directed Graph");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buildGraph();
            }
        });
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.gridx = 2;
        gbc_btnNewButton.gridy = 0;
        panel.add(btnNewButton, gbc_btnNewButton);
 
        JLabel lblClass = new JLabel("Class to recompile");
        GridBagConstraints gbc_lblClass = new GridBagConstraints();
        gbc_lblClass.anchor = GridBagConstraints.EAST;
        gbc_lblClass.insets = new Insets(0, 0, 0, 5);
        gbc_lblClass.gridx = 0;
        gbc_lblClass.gridy = 1;
        panel.add(lblClass, gbc_lblClass);
 
        txtOutputFile = new JTextField();
        GridBagConstraints gbc_txtOutputFile = new GridBagConstraints();
        gbc_txtOutputFile.insets = new Insets(0, 0, 0, 5);
        gbc_txtOutputFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtOutputFile.gridx = 1;
        gbc_txtOutputFile.gridy = 1;
        panel.add(txtOutputFile, gbc_txtOutputFile);
        txtOutputFile.setColumns(10);
 
        JButton btnTopo = new JButton("Topological Order");
        btnTopo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                topoOrder();
            }
        });
        GridBagConstraints gbc_btnTopo = new GridBagConstraints();
        gbc_btnTopo.gridx = 2;
        gbc_btnTopo.gridy = 1;
        panel.add(btnTopo, gbc_btnTopo);
 
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(
                new TitledBorder(null, "Recomplication Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));
 
        JScrollPane scrollPane = new JScrollPane();
        panel_1.add(scrollPane, BorderLayout.CENTER);
 
        txtResult = new JTextArea();
        scrollPane.setViewportView(txtResult);
    }
 
    protected void topoOrder() {
        String className = txtOutputFile.getText();
 
        try {
            txtResult.setText(graph.topoOrder(className));
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
 
    protected void buildGraph() {
        // Create project4.DGraph Instance
        graph = new DependencyGraph<>();
 
        String fileName = txtInputFIle.getText();
 
        try {
            // Ensures List has a value
            if (fileName.isEmpty()) {
                throw new NullPointerException();
            }
 
            // Attempt to build a DGraph with file
            graph.build(graph.parseFile(fileName));
 
            JOptionPane.showMessageDialog(this, "The graph is built", "Message", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
 
}
