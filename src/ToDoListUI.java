import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


public class ToDoListUI extends JFrame {
    private ArrayList<Note> notes;
    private DefaultListModel<String> listModel;
    private JList<String> noteList;
    private JTextArea noteContent;
    private JTextField noteTitle;

    public ToDoListUI() {
        notes = FileManager.loadNotes();
        listModel = new DefaultListModel<>();
        for (Note note : notes) {
            listModel.addElement(note.getTitle());
        }

        setTitle("To-Do List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        noteList = new JList<>(listModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.addListSelectionListener(e -> {
            int index = noteList.getSelectedIndex();
            if (index != -1) {
                Note selectedNote = notes.get(index);
                noteTitle.setText(selectedNote.getTitle());
                noteContent.setText(selectedNote.getContent());
            }
        });

        JScrollPane listScrollPane = new JScrollPane(noteList);
        add(listScrollPane, BorderLayout.WEST);

        JPanel notePanel = new JPanel(new BorderLayout());
        noteTitle = new JTextField("Enter the title");
        noteContent = new JTextArea("Enter the content");
        noteTitle.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (noteTitle.getText().equals("Enter the title")) {
                    noteTitle.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (noteTitle.getText().isEmpty()) {
                    noteTitle.setText("Enter the title");
                }
            }
        });
        noteContent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (noteContent.getText().equals("Enter the content")) {
                    noteContent.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (noteContent.getText().isEmpty()) {
                    noteContent.setText("Enter the content");
                }
            }
        });
        notePanel.add(noteTitle, BorderLayout.NORTH);
        notePanel.add(new JScrollPane(noteContent), BorderLayout.CENTER);
        add(notePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addNote());
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateNote());
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteNote());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addNote() {
        String title = noteTitle.getText();
        String content = noteContent.getText();
        if (!title.isEmpty() && !content.isEmpty()) {
            Note newNote = new Note(title, content);
            notes.add(newNote);
            listModel.addElement(title);
            FileManager.saveNotes(notes);
        }
    }

    private void updateNote() {
        int index = noteList.getSelectedIndex();
        if (index != -1) {
            Note note = notes.get(index);
            note.setTitle(noteTitle.getText());
            note.setContent(noteContent.getText());
            listModel.set(index, note.getTitle());
            FileManager.saveNotes(notes);
        }
    }

    private void deleteNote() {
        int index = noteList.getSelectedIndex();
        if (index != -1) {
            notes.remove(index);
            listModel.remove(index);
            noteTitle.setText("");
            noteContent.setText("");
            FileManager.saveNotes(notes);
        }
    }
}
