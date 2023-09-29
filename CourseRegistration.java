package Management;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class CourseRegistration extends JFrame {
    UserDAO userDAO = new UserDAO();

    JPanel panel1, panel2, panel3, panel4, panel5;

    JTextField text1, text2, text3;
    static DefaultTableModel tableModel, tableModel2;
    static JTable table, table2;
    Vector<Object> rowData;

    public CourseRegistration() throws BadLocationException, SQLException {
        super("수강신청");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 900);
        setLocationRelativeTo(null);

        this.setLayout(null);

        panel();
        label();
        button();

        setVisible(true);
    }

    void panel() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        panel1.setLayout(null); // 레이아웃 매니저 설정
        panel2.setLayout(null); // 레이아웃 매니저 설정
        panel3.setLayout(null); // 레이아웃 매니저 설정
        panel4.setLayout(null); // 레이아웃 매니저 설정
        panel5.setLayout(null); // 레이아웃 매니저 설정

        panel1.setBounds(0, 0, 650, 80);
        panel1.setBackground(Color.WHITE);
        panel2.setBounds(0, 80, 650, 150);
        panel2.setBackground(new Color(15, 15, 112));
        panel3.setBounds(0, 230, 650, 70);
        panel3.setBackground(Color.WHITE);
        panel4.setBounds(0, 300, 650, 400);
        panel4.setBackground(new Color(15, 15, 112));
        panel5.setBounds(0, 700, 650, 200);
        panel5.setBackground(Color.WHITE);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
    }

    void label() {

        JLabel label0 = new JLabel("수강신청");
        label0.setForeground(Color.BLACK);
        label0.setFont(new Font("나눔고딕", Font.BOLD, 20));
        label0.setBounds(275, -10, 100, 100);

        JLabel label1 = new JLabel("학번");
        label1.setFont(new Font("나눔고딕", Font.BOLD, 15));
        label1.setBounds(40, 65, 100, 20);

        JLabel label2 = new JLabel("이름");
        label2.setFont(new Font("나눔고딕", Font.BOLD, 15));
        label2.setBounds(230, 65, 100, 20);

        JLabel label3 = new JLabel("학과");
        label3.setFont(new Font("나눔고딕", Font.BOLD, 15));
        label3.setBounds(400, 65, 100, 20);

        JLabel label4 = new JLabel("[교과목 테이블]");
        label4.setFont(new Font("나눔고딕", Font.BOLD, 13));
        label4.setBounds(30, 10, 100, 20);
        label4.setForeground(Color.WHITE);

        JLabel label5 = new JLabel("[수강신청 테이블]");
        label5.setFont(new Font("나눔고딕", Font.BOLD, 13));
        label5.setBounds(25, 200, 120, 20);
        label5.setForeground(Color.WHITE);


        text1 = new JTextField();
        text1.setBounds(115, 65, 100, 24);
        text2 = new JTextField();
        text2.setBounds(285, 65, 100, 24);
        text3 = new JTextField();
        text3.setBounds(465, 65, 100, 24);

        JTextPane textPane1 = new JTextPane();
        textPane1.setBackground(Color.GRAY);
        textPane1.setBounds(15, 15, 600, 220);
        textPane1.setEditable(false);
        textPane1.setCursor(null); // 커서 비활성화
        textPane1.setOpaque(false); // 투명 배경 설정
        textPane1.setFocusable(false); // 포커스 비활성화

        JScrollPane scrollPane = new JScrollPane(textPane1);
        scrollPane.setBounds(15, 40, 600, 80);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);// 검은색 테두리, 두께 2
        scrollPane.setBorder(border);

        CourseRegistration.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 모든 셀을 수정 불가능하게 설정
                return false;
            }
        };
        CourseRegistration.tableModel.addColumn("교과코드");
        CourseRegistration.tableModel.addColumn("교과명");
        CourseRegistration.tableModel.addColumn("개설학과");
        CourseRegistration.tableModel.addColumn("개설학년");
        CourseRegistration.tableModel.addColumn("개설학기");
        CourseRegistration.tableModel.addColumn("수업시수");
        CourseRegistration.tableModel.addColumn("담당교수");
        CourseRegistration.tableModel.addColumn("학점");

        // JTable 생성 및 모델 설정
        CourseRegistration.table = new JTable(CourseRegistration.tableModel);
        CourseRegistration.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        CourseRegistration.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = CourseRegistration.table.getSelectedRow();
                if (selectedRow != -1) { // 유효한 행을 선택한 경우
                    DefaultTableModel sourceModel = (DefaultTableModel) CourseRegistration.table.getModel();
                    DefaultTableModel targetModel = (DefaultTableModel) CourseRegistration.table2.getModel();

                    // 선택한 행의 데이터를 가져옴
                    Vector<Object> rowData = new Vector<>();
                    for (int i = 0; i < sourceModel.getColumnCount(); i++) {
                        rowData.add(sourceModel.getValueAt(selectedRow, i));
                    }

                    userDAO.courseRegistrationDataInsert(
                            (String) rowData.get(0),
                            (String) rowData.get(1),
                            (String) rowData.get(2),
                            (String) rowData.get(3),
                            (String) rowData.get(4),
                            (String) rowData.get(5),
                            (String) rowData.get(6),
                            (String) rowData.get(7),
                            text1.getText(),
                            text2.getText()
                    );


                    // 수강신청 테이블로 데이터를 추가
                    targetModel.addRow(rowData);
                }
            }
        });

        JTableHeader header = table.getTableHeader();
        Font headerFont = new Font("나눔고딕", Font.BOLD, 15); // Example: Bold and larger font
        header.setFont(headerFont);

        UserDAO.subjectInfoAll();

        CourseRegistration.tableModel2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 모든 셀을 수정 불가능하게 설정
                return false;
            }
        };
        CourseRegistration.tableModel2.addColumn("교과코드");
        CourseRegistration.tableModel2.addColumn("교과명");
        CourseRegistration.tableModel2.addColumn("개설학과");
        CourseRegistration.tableModel2.addColumn("개설학년");
        CourseRegistration.tableModel2.addColumn("개설학기");
        CourseRegistration.tableModel2.addColumn("수업시수");
        CourseRegistration.tableModel2.addColumn("담당교수");
        CourseRegistration.tableModel2.addColumn("학점");

        // JTable 생성 및 모델 설정
        table2 = new JTable(CourseRegistration.tableModel2);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        CourseRegistration.table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = CourseRegistration.table2.getSelectedRow();
                if (selectedRow != -1) { // 유효한 행을 선택한 경우
                    DefaultTableModel sourceModel = (DefaultTableModel) CourseRegistration.table2.getModel();

                    // 선택한 행의 데이터를 가져옴
                    rowData = new Vector<>();
                    for (int i = 0; i < sourceModel.getColumnCount(); i++) {
                        rowData.add(sourceModel.getValueAt(selectedRow, i));
                    }
                }
            }
        });

        JTableHeader header2 = table2.getTableHeader();
        Font headerFont2 = new Font("나눔고딕", Font.BOLD, 15); // Example: Bold and larger font
        header2.setFont(headerFont2);


        // JScrollPane에 JTable 추가
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(15, 40, 600, 150);
        Border border2 = BorderFactory.createLineBorder(Color.GRAY, 2);// 검은색 테두리, 두께 2
        scrollPane1.setBorder(border2);

        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(15, 230, 600, 150);
        Border border3 = BorderFactory.createLineBorder(Color.GRAY, 2);// 검은색 테두리, 두께 2
        scrollPane2.setBorder(border3);

        String imagePath = "./image/resized.png";
        ImageIcon snuUi = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(snuUi);
        imageLabel.setBounds(80, -10, 100, 100);
        panel1.add(imageLabel);

        panel1.add(label0);
        panel2.add(label1);
        panel2.add(label2);
        panel2.add(label3);

        panel4.add(label4);
        panel4.add(label5);

        panel2.add(text1);
        panel2.add(text2);
        panel2.add(text3);

        panel2.add(scrollPane);
        panel4.add(scrollPane1);
        panel4.add(scrollPane2);

    }

    void button() {

        String[] category = {"카테고리를 선택하세요", "학번"};
        JComboBox comboBox1 = new JComboBox<>(category);
        comboBox1.setBounds(70, 20, 150, 24);
        comboBox1.setFont(new Font("나눔고딕", Font.PLAIN, 12));

        JTextField textField = new JTextField();
        textField.setBounds(260, 20, 150, 24);

        JButton btn1 = new JButton("조회");
        btn1.setBounds(450, 20, 100, 24);
        btn1.setFont(new Font("나눔고딕", Font.BOLD, 12));

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedcategory = (String) comboBox1.getSelectedItem();
                String stdCodeText = textField.getText();

                    userDAO.courseRegistrationInfoAll(stdCodeText);
            }
        });


        JButton btn4 = new JButton("삭제");
        btn4.setBounds(360, 30, 100, 50);
        btn4.setFont(new Font("나눔고딕", Font.BOLD, 12));

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (rowData != null) {
                    userDAO.deleteCourseRegistrationInfo(
                            (String) rowData.get(0),
                            (String) rowData.get(1),
                            text1.getText(),
                            text2.getText()
                    );

                } else {
                        JOptionPane.showMessageDialog(null, "학번, 이름, 학과를 입력하세요.");
                }

            }
        });


        JButton btn5 = new JButton("종료");
        btn5.setBounds(500, 30, 100, 50);
        btn5.setFont(new Font("나눔고딕", Font.BOLD, 12));

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel3.add(comboBox1);
        panel3.add(textField);
        panel3.add(btn1);
        panel5.add(btn4);
        panel5.add(btn5);
    }

    public static void main(String[] args) throws BadLocationException, SQLException {
        new CourseRegistration();
    }
}
