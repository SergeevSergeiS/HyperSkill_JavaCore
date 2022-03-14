package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextEditor extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 400;
    final int KEY_SIZE = 30;
    final String OPEN_ICON_FILE_BIG = "images/load.png";
    final String OPEN_ICON_FILE_SMALL = "images/load1.png";
    final String SAVE_ICON_FILE_BIG = "images/save.png";
    final String SAVE_ICON_FILE_SMALL = "images/save1.png";
    final String EXIT_ICON_FILE_SMALL = "images/exit.png";
    final String START_SEARCH_ICON_BIG = "images/search.png";
    final String PREV_SEARCH_ICON_BIG = "images/prev_big.png";
    final String NEXT_SEARCH_ICON_BIG = "images/next_big.png";
    final String START_SEARCH_ICON_SMALL = "images/search_small.png";
    final String PREV_SEARCH_SMALL = "images/prev_small.png";
    final String NEXT_SEARCH_SMALL = "images/next_small.png";

    private JTextArea textArea;
    private JTextField findTextField;
    private JButton saveButton;
    private JButton loadButton;
    private JButton startSearchButton;
    private JButton prevSearchButton;
    private JButton nextSearchButton;
    private JCheckBox useRegExBox;
    private boolean isChecked = false;
    private JFileChooser jfc;
    private ArrayList<Integer> indexFound;
    private ArrayList<Integer> lengthFound;
    private int counter = 0;
    private int nextCounter = 0;


    public TextEditor() {
        super("Text Editor v.4.1");

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Container container = getContentPane();


        textArea = new JTextArea();
        textArea.setName("TextArea");


        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");
        add(jfc);


        container.add(scrollableTextArea, BorderLayout.CENTER);
        container.add(upArea(), BorderLayout.NORTH);
        container.add(new JLabel(" "), BorderLayout.SOUTH);
        container.add(new JLabel("    "), BorderLayout.WEST);
        container.add(new JLabel("    "), BorderLayout.EAST);

        createListener();
        menuBar();

        setVisible(true);
    }


    private JPanel upArea() {

        JPanel upArea = new JPanel();
        upArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        findTextField = new JTextField(20);
        findTextField.setName("SearchField");


        saveButton = new JButton(new ImageIcon(SAVE_ICON_FILE_BIG));
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(KEY_SIZE, KEY_SIZE));


        loadButton = new JButton(new ImageIcon(OPEN_ICON_FILE_BIG));
        loadButton.setName("OpenButton");
        loadButton.setPreferredSize(new Dimension(KEY_SIZE, KEY_SIZE));


        startSearchButton = new JButton(new ImageIcon(START_SEARCH_ICON_BIG));
        startSearchButton.setName("StartSearchButton");
        startSearchButton.setPreferredSize(new Dimension(KEY_SIZE, KEY_SIZE));


        prevSearchButton = new JButton(new ImageIcon(PREV_SEARCH_ICON_BIG));
        prevSearchButton.setName("PreviousMatchButton");
        prevSearchButton.setPreferredSize(new Dimension(KEY_SIZE, KEY_SIZE));


        nextSearchButton = new JButton(new ImageIcon(NEXT_SEARCH_ICON_BIG));
        nextSearchButton.setName("NextMatchButton");
        nextSearchButton.setPreferredSize(new Dimension(KEY_SIZE, KEY_SIZE));


        useRegExBox = new JCheckBox("Use Regex");
        useRegExBox.setName("UseRegExCheckbox");


        upArea.add(loadButton);
        upArea.add(saveButton);
        upArea.add(findTextField);
        upArea.add(startSearchButton);
        upArea.add(prevSearchButton);
        upArea.add(nextSearchButton);
        upArea.add(useRegExBox);

        return upArea;
    }

    public void menuBar() {

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);


        JMenuItem loadMenuItem = new JMenuItem("Open", new ImageIcon(OPEN_ICON_FILE_SMALL));
        loadMenuItem.setName("MenuOpen");
        loadMenuItem.addActionListener(loadButton.getActionListeners()[0]);
        loadMenuItem.setMnemonic(KeyEvent.VK_O);


        JMenuItem saveMenuItem = new JMenuItem("Save", new ImageIcon(SAVE_ICON_FILE_SMALL));
        saveMenuItem.setName("MenuSave");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener(saveButton.getActionListeners()[0]);


        JMenuItem exitMenuItem = new JMenuItem("Exit", new ImageIcon(EXIT_ICON_FILE_SMALL));
        exitMenuItem.setName("MenuExit");
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.addActionListener(actionEvent -> dispose());


        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(searchMenu);


        JMenuItem startSearchMenuItem = new JMenuItem("Start Search", new ImageIcon(START_SEARCH_ICON_SMALL));
        startSearchMenuItem.setName("MenuStartSearch");
        startSearchMenuItem.setMnemonic(KeyEvent.VK_T);
        startSearchMenuItem.addActionListener(startSearchButton.getActionListeners()[0]);


        JMenuItem previousSearchMenuItem = new JMenuItem("Previous Search", new ImageIcon(PREV_SEARCH_SMALL));
        previousSearchMenuItem.setName("MenuPreviousMatch");
        previousSearchMenuItem.addActionListener(prevSearchButton.getActionListeners()[0]);


        JMenuItem nextSearchMenuItem = new JMenuItem("Next Search", new ImageIcon(NEXT_SEARCH_SMALL));
        nextSearchMenuItem.setName("MenuNextMatch");
        nextSearchMenuItem.addActionListener(nextSearchButton.getActionListeners()[0]);


        JMenuItem useRegExpMenuItem = new JMenuItem("Use regex");
        useRegExpMenuItem.setName("MenuUseRegExp");
        useRegExpMenuItem.addActionListener(actionEvent -> useRegExBox.doClick());



        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousSearchMenuItem);
        searchMenu.add(nextSearchMenuItem);
        searchMenu.addSeparator();
        searchMenu.add(useRegExpMenuItem);
    }


    public void createListener() {


        saveButton.addActionListener(actionEvent -> {
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try (FileWriter writer = new FileWriter(selectedFile)) {
                    writer.write(textArea.getText());
                } catch (IOException e) {
                    e.getMessage();



                }
            }
        });


        loadButton.addActionListener(actionEvent -> {

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    textArea.setText(new String(Files.readAllBytes(selectedFile.toPath())));
                } catch (IOException e) {
                    textArea.setText(null);
                }
            }
        });


        startSearchButton.addActionListener(actionEvent -> SearchEngine());


        nextSearchButton.addActionListener(actionEvent -> NextSearch());


        prevSearchButton.addActionListener(actionEvent -> PrevSearch());


        useRegExBox.addActionListener(actionEvent -> isChecked = !isChecked);
    }


    public String getTextFind() {
        return findTextField.getText();
    }


    public String getAllText() {
        return textArea.getText();
    }


    public void SearchEngine() {
        indexFound = new ArrayList<>();
        lengthFound = new ArrayList<>();
        String findText = getTextFind();
        String allText = getAllText();
        int index = -1;
        int lengthFind = findText.length();

        if (isChecked) {

            Pattern pattern = Pattern.compile(findText);
            Matcher matcher = pattern.matcher(allText);
            while (matcher.find()) {
                index = matcher.start();
                lengthFind = matcher.end() - index;
                indexFound.add(index);
                lengthFound.add(lengthFind);
            }

        } else {

            while (true) {
                index = allText.indexOf(findText, index + 1);
                if (index == -1) {
                    break;
                }
                indexFound.add(index);
                lengthFound.add(lengthFind);
                System.out.println("index=" + index);
                System.out.println("length=" + lengthFind);
            }
        }

        counter = indexFound.size();
        nextCounter = 0;

        if (counter > 0) {
            textArea.setCaretPosition(indexFound.get(0) + lengthFound.get(0));
            textArea.select(indexFound.get(0), indexFound.get(0) + lengthFound.get(0));
            textArea.grabFocus();
        }
    }


    public void NextSearch() {
        if (counter > 0) {
            if (counter - 1 > nextCounter) {
                nextCounter++;
            } else {
                nextCounter = 0;
            }
            textArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            textArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            textArea.grabFocus();
        }
    }


    public void PrevSearch() {
        if (counter > 0) {
            if (nextCounter != 0) {
                nextCounter--;
            } else {
                nextCounter = counter - 1;
            }
            textArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            textArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            textArea.grabFocus();
        }
    }
}
