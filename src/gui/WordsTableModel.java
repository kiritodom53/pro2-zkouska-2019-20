package gui;

import model.Word;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WordsTableModel extends AbstractTableModel {

    private List<Word> words;

    public WordsTableModel(List<Word> words) {
        this.words = words;
    }

    @Override
    public int getRowCount() {
        return words.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int index){
        switch (index){
            case 0: return "slovo";
            case 1: return "počet";
            case 2: return "%";
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            default: return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Word w = words.get(rowIndex);
        //System.out.println("=========Test");
        switch (columnIndex){
            case 0: return w.getWord();
            case 1: return w.getWordCount();
            case 2: return w.getPercentage();
            default: return null;
        }
    }

    public void refreshTable() {
        fireTableDataChanged();
    }
}
