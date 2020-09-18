import gui.WordsTableModel;
import model.Word;
import services.MyFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyzaApp extends JFrame{

    private WordsTableModel tableModel;
    private JTable table;
    private MyFile myFile;
    private File filePath;
    private String path;

    public AnalyzaApp(){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File filePath = fileChooser.getSelectedFile();
            path = filePath.getPath().replace("\\", "\\\\");
        }

        myFile = new MyFile(path);

        //============== TESTING OUTPUT
        System.out.println("==============================================");
        for (Word w : myFile.getNoDuplicatedWords()) {
            //System.out.println(w.getWord());
        }
        //System.out.println(myFile.countNoDublicateWords());
        System.out.println("==============================================");
        //==============

        try {
            tableModel = new WordsTableModel(myFile.getNoDuplicatedWords());
            table = new JTable(tableModel);
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            System.exit(1);
        }

        System.out.println("prvek: ");
        System.out.println(tableModel.getValueAt(8,0));
        tableModel.refreshTable();

        setTitle("Počet slov: (všech): " + myFile.countAllWords() +
                ", (bez duplikátů): " + myFile.countNoDublicateWords() +
                ", Počet vět: " + myFile.getSentenceCount());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


        add(new JScrollPane(table), BorderLayout.CENTER);

        pack();
        setSize(new Dimension(500, 400));
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AnalyzaApp a = new AnalyzaApp();
                //a.setVisible(true);
            }
        });
    }
}
