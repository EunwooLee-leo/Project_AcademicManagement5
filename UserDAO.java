package Management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class UserDAO {

    private static Connection c; // DB에 접근하게 해주는 객체

    public Connection getC() {
        return c;
    }

    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2; // 정보를 담을 객체
    static DefaultTableModel tableModel;

    UserDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/univ";
            String dbID = "root";
            String dbPassword = "1234";

            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deptInfoAll() {
        try {
            String query = "SELECT * FROM dept ";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String deptCode = resultSet.getString("deptCode");
                String deptName = resultSet.getString("deptName");
                String majorName = resultSet.getString("majorName");

                DeptForm.tableModel.addRow(new Object[]{deptCode, deptName, majorName});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profInfoAll() {
        try {
            String query = "SELECT * FROM prof ";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String profCode = resultSet.getString("profCode");
                String profName = resultSet.getString("profName");
                String addr = resultSet.getString("addr");
                String rrn = resultSet.getString("rrn");
                String phone = resultSet.getString("phone");
                String year = resultSet.getString("year");
                String degree = resultSet.getString("degree");
                String majorName = resultSet.getString("majorName");
                String room = resultSet.getString("room");
                String sex = resultSet.getString("sex");

                ProfForm.tableModel.addRow(new Object[]{profCode, profName, addr, rrn, phone, year, degree, majorName, room, sex});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void studentInfoAll() {
        try {
            String query = "SELECT * FROM student ";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String stnCode = resultSet.getString("stdCode");
                String stdName = resultSet.getString("stdName");
                String addr = resultSet.getString("addr");
                String rrn = resultSet.getString("rrn");
                String phone = resultSet.getString("phone");
                String year = resultSet.getString("year");
                String majorName = resultSet.getString("majorName");
                String highSchool = resultSet.getString("highSchool");
                String graduationYear = resultSet.getString("highSchoolGraduationYear");
                String sex = resultSet.getString("sex");

                StudentForm.tableModel.addRow(new Object[]{stnCode, stdName, addr, rrn, phone, majorName, year, highSchool, graduationYear, sex});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void subjectInfoAll() {
        try {
            String query = "SELECT * FROM subject ";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {

                String subjectName = resultSet.getString("subjectName");
                String majorName = resultSet.getString("majorName");
                String year = resultSet.getString("year");
                String grade = resultSet.getString("grade");
                String semester = resultSet.getString("semester");
                String hour = resultSet.getString("hour");
                String profName = resultSet.getString("profName");
                String credit = resultSet.getString("credit");

                if (SubjectForm.tableModel != null) {
                    SubjectForm.tableModel.addRow(new Object[]{subjectName, majorName, year, grade, semester, hour, profName, credit});
                }

                // 두 번째 테이블에 데이터 추가
                if (CourseRegistration.tableModel != null) {
                    CourseRegistration.tableModel.addRow(new Object[]{subjectName, majorName, year, grade, semester, hour, profName, credit});
                }

            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void courseRegistrationInfoAll(String insertStdCode) {

        CourseRegistration.tableModel2.setRowCount(0);

        try {
            String query = "SELECT * FROM course_registration WHERE stdCode = ? ";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, insertStdCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {

                String subjectCode = resultSet.getString("subjectCode");
                String subjectName = resultSet.getString("subjectName");
                String year = resultSet.getString("year");
                String grade = resultSet.getString("grade");
                String semester = resultSet.getString("semester");
                String hour = resultSet.getString("hour");
                String profName = resultSet.getString("profName");
                String credit = resultSet.getString("credit");
                String stdCode = resultSet.getString("stdCode");
                String stdName = resultSet.getString("stdName");

                if (CourseRegistration.tableModel2 != null) {
                    CourseRegistration.tableModel2.addRow(new Object[]{subjectCode, subjectName, year, grade, semester, hour, profName, credit, stdCode, stdName});
                }
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();


            // 업데이트된 데이터로 테이블 모델 채우기

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deptDataInsert(String deptCode, String deptName, String majorName) {
        try {
            String query = "INSERT INTO dept (deptCode, deptName, majorName) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, deptCode);
            preparedStatement.setString(2, deptName);
            preparedStatement.setString(3, majorName);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삽입되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터 삽입 실패.");
            }

            preparedStatement.close();

            // 테이블 모델 초기화
            DeptForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            deptInfoAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profDataInsert(String profCode, String profName, String addr, String rrn, String phone, String year, String degree, String majorName, String room, String sex) {
        try {
            String query = "INSERT INTO prof (profCode, profName, addr, rrn, phone, year, degree, majorName, room, sex) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, profCode);
            preparedStatement.setString(2, profName);
            preparedStatement.setString(3, addr);
            preparedStatement.setString(4, rrn);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, year);
            preparedStatement.setString(7, degree);
            preparedStatement.setString(8, majorName);
            preparedStatement.setString(9, room);
            preparedStatement.setString(10, sex);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삽입되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터 삽입 실패.");
            }

            preparedStatement.close();

            // 테이블 모델 초기화
            ProfForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            profInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void studentDataInsert(String stnCode, String stdName, String addr, String rrn, String phone, String majorName, String year, String highSchool, String graduationYear, String sex) {
        try {
            String query = "INSERT INTO student (stdCode, stdName, addr, rrn, phone, year, majorName, highSchool, highSchoolGraduationYear, sex) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, stnCode);
            preparedStatement.setString(2, stdName);
            preparedStatement.setString(3, addr);
            preparedStatement.setString(4, rrn);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, year);
            preparedStatement.setString(7, majorName);
            preparedStatement.setString(8, highSchool);
            preparedStatement.setString(9, graduationYear);
            preparedStatement.setString(10, sex);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삽입되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터 삽입 실패.");
            }

            preparedStatement.close();

            // 테이블 모델 초기화
            StudentForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            studentInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삽입 중 오류 발생: " + e.getMessage());

            // 예외 발생 시도 데이터를 롤백하거나 다른 조치를 취할 수 있습니다.
        }
    }

    public void subjectDataInsert(String subjectName, String majorName, String year, String grade, String semester, String hour, String profName, String credit) {
        try {
            String query = "INSERT INTO subject (subjectName, majorName, year, grade, semester, hour, profName, credit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, subjectName);
            preparedStatement.setString(2, majorName);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, grade);
            preparedStatement.setString(5, semester);
            preparedStatement.setString(6, hour);
            preparedStatement.setString(7, profName);
            preparedStatement.setString(8, credit);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삽입되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터 삽입 실패.");
            }

            preparedStatement.close();

            // 테이블 모델 초기화
            SubjectForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            subjectInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삽입 중 오류 발생: " + e.getMessage());

            // 예외 발생 시도 데이터를 롤백하거나 다른 조치를 취할 수 있습니다.
        }
    }

    public void courseRegistrationDataInsert(String subjectCode, String subjectName, String year, String grade, String semester, String hour, String profName, String credit, String stdCode, String stdName) {
        try {
            String query = "INSERT INTO course_registration (subjectCode, subjectName, year, grade, semester, hour, profName, credit, stdCode, stdName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, subjectCode);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, grade);
            preparedStatement.setString(5, semester);
            preparedStatement.setString(6, hour);
            preparedStatement.setString(7, profName);
            preparedStatement.setString(8, credit);
            preparedStatement.setString(9, stdCode);
            preparedStatement.setString(10, stdName);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삽입되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터 삽입 실패.");
            }

            preparedStatement.close();

            CourseRegistration.tableModel2.setRowCount(0);



        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삽입 중 오류 발생: " + e.getMessage());

            // 예외 발생 시도 데이터를 롤백하거나 다른 조치를 취할 수 있습니다.
        }
    }

    public void categorySearchByDeptName(String deptName) {
        DefaultTableModel model = (DefaultTableModel) ProfForm.table.getModel();
        model.setRowCount(0);

        try {
            String query = "SELECT deptCode, deptName, majorName FROM dept WHERE deptName LIKE ? or deptName = ? or deptName LIKE ?";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, "%" + deptName);
            preparedStatement.setString(2, deptName);
            preparedStatement.setString(3, deptName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String deptCode = resultSet.getString("deptCode");
                String foundDeptName = resultSet.getString("deptName");
                String majorName = resultSet.getString("majorName");

                model.addRow(new Object[]{deptCode, foundDeptName, majorName});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categorySearchByProfName(String profName) {
        DefaultTableModel model = (DefaultTableModel) ProfForm.table.getModel();
        model.setRowCount(0);

        try {
            String query = "SELECT profCode, profName, addr, rrn, phone, year, degree, majorName, room, sex FROM prof WHERE profName LIKE ? or profName = ? or profName LIKE ?";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, "%" + profName);
            preparedStatement.setString(2, profName);
            preparedStatement.setString(3, profName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String profCode = resultSet.getString("profCode");
                String foundProfName = resultSet.getString("profName");
                String addr = resultSet.getString("addr");
                String rrn = resultSet.getString("rrn");
                String phone = resultSet.getString("phone");
                String year = resultSet.getString("year");
                String degree = resultSet.getString("degree");
                String majorName = resultSet.getString("majorName");
                String room = resultSet.getString("room");
                String sex = resultSet.getString("sex");


                model.addRow(new Object[]{profCode, foundProfName, addr, rrn, phone, year, degree, majorName, room, sex});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categorySearchBySubjectName() {

        try {
            String query = "SELECT deptName  FROM dept ";
            PreparedStatement preparedStatement = c.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String deptName = resultSet.getString("deptName");

                ProfForm.comboBox2.addItem(deptName);
                SubjectForm.majorNameComboBox.addItem(deptName);
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categorySearchByProfName() {

        try {
            String query = "SELECT profName  FROM prof ";
            PreparedStatement preparedStatement = c.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과가 있는 경우
            while (resultSet.next()) {
                String profName = resultSet.getString("profName");

                SubjectForm.profNameComboBox.addItem(profName);
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void categorySearchByStudentName(String stdName) {
        DefaultTableModel model = (DefaultTableModel) StudentForm.table.getModel();
        model.setRowCount(0);

        try {
            String query = "SELECT stdCode, stdName, addr, rrn, phone, year, majorName, highSchool, highSchoolGraduationYear, sex FROM student WHERE stdName like ? OR stdName = ? OR stdName like ?";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, "%" + stdName);
            preparedStatement.setString(2, stdName);
            preparedStatement.setString(3, stdName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();


            // 결과가 있는 경우
            while (resultSet.next()) {
                String profCode = resultSet.getString("stdCode");
                String foundProfName = resultSet.getString("stdName");
                String addr = resultSet.getString("addr");
                String rrn = resultSet.getString("rrn");
                String phone = resultSet.getString("phone");
                String year = resultSet.getString("year");
                String degree = resultSet.getString("majorName");
                String majorName = resultSet.getString("highSchool");
                String room = resultSet.getString("highSchoolGraduationYear");
                String sex = resultSet.getString("sex");


                model.addRow(new Object[]{profCode, foundProfName, addr, rrn, phone, year, degree, majorName, room, sex});
            }
            // PreparedStatement와 ResultSet를 닫습니다.
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeptInfo(String deptCodeToUpdate, String deptNameToUpdate, String majorNameToUpdate) {
        try {
            // SQL UPDATE 쿼리 작성
            String updateQuery = "UPDATE dept SET deptName = ?, majorName = ? WHERE deptCode = ?";

            PreparedStatement preparedStatement = c.prepareStatement(updateQuery);

            // 파라미터 설정
            preparedStatement.setString(1, deptNameToUpdate);
            preparedStatement.setString(2, majorNameToUpdate);
            preparedStatement.setString(3, deptCodeToUpdate);

            // UPDATE 쿼리 실행
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 업데이트되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "업데이트할 데이터가 없습니다.");
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            // 테이블 모델 초기화
            DeptForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            deptInfoAll();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    public void updateProfInfo(String profCode, String profName, String addr, String rrn, String phone, String year, String degree, String majorName, String room, String sex) {
        try {
            // SQL UPDATE 쿼리 작성
            String updateQuery = "UPDATE prof SET profCode = ?, profName = ?, addr = ?, rrn = ?, phone = ?, year = ?, degree = ?, majorName = ?, room = ?, sex = ? WHERE  profCode = ?";

            PreparedStatement preparedStatement = c.prepareStatement(updateQuery);

            // 파라미터 설정
            preparedStatement.setString(1, profCode);
            preparedStatement.setString(2, profName);
            preparedStatement.setString(3, addr);
            preparedStatement.setString(4, rrn);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, year);
            preparedStatement.setString(7, degree);
            preparedStatement.setString(8, majorName);
            preparedStatement.setString(9, room);
            preparedStatement.setString(10, sex);
            preparedStatement.setString(11, profCode);

            // UPDATE 쿼리 실행
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 업데이트되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "업데이트할 데이터가 없습니다.");
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            // 테이블 모델 초기화
            ProfForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            profInfoAll();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    public void updateStudentInfo(String stnCode, String stdName, String addr, String rrn, String phone, String year, String majorName, String highSchool, String graduationYear, String sex) {
        try {
            // SQL UPDATE 쿼리 작성
            String query = "UPDATE student SET stdCode = ?, stdName = ?, addr = ?, rrn = ?, phone = ?, year = ?, majorName = ?, highSchool = ?, highSchoolGraduationYear = ?, sex = ? WHERE stdCode = ?";

            PreparedStatement preparedStatement = c.prepareStatement(query);

            // 파라미터 설정
            preparedStatement.setString(1, stnCode);
            preparedStatement.setString(2, stdName);
            preparedStatement.setString(3, addr);
            preparedStatement.setString(4, rrn);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, year);
            preparedStatement.setString(7, majorName);
            preparedStatement.setString(8, highSchool);
            preparedStatement.setString(9, graduationYear);
            preparedStatement.setString(10, sex);
            preparedStatement.setString(11, stnCode);

            // UPDATE 쿼리 실행
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 업데이트되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "업데이트할 데이터가 없습니다.");
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            // 테이블 모델 초기화
            StudentForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            studentInfoAll();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    public void updateSubjectInfo(String subjectName, String majorName, String year, String grade, String semester, String hour, String profName, String credit) {
        try {
            // SQL UPDATE 쿼리 작성
            String query = "UPDATE subject SET subjectName = ?, majorName = ?, year = ?, grade = ?, semester = ?, hour = ?, profName = ?, credit = ? WHERE subjectName = ?";

            PreparedStatement preparedStatement = c.prepareStatement(query);

            // 파라미터 설정
            preparedStatement.setString(1, subjectName);
            preparedStatement.setString(2, majorName);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, grade);
            preparedStatement.setString(5, semester);
            preparedStatement.setString(6, hour);
            preparedStatement.setString(7, profName);
            preparedStatement.setString(8, credit);
            preparedStatement.setString(9, subjectName);

            // UPDATE 쿼리 실행
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 업데이트되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "업데이트할 데이터가 없습니다.");
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            // 테이블 모델 초기화
            SubjectForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            subjectInfoAll();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    public void deleteDeptInfo(String deptCodeToDelete, String deptNameToDelete, String majorNameToDelete) {
        try {
            // SQL DELETE 쿼리 작성
            String deleteQuery = "DELETE FROM dept WHERE deptCode = ? AND deptName = ? AND majorName = ?";

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);

            // 파라미터 설정
            preparedStatement.setString(1, deptCodeToDelete);
            preparedStatement.setString(2, deptNameToDelete);
            preparedStatement.setString(3, majorNameToDelete);

            // DELETE 쿼리 실행
            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다.");
                ;
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            DeptForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            deptInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삭제 중 오류 발생: " + e.getMessage());
            ;
        }
    }

    public void deleteProfInfo(String profCode, String profName, String phone) {
        try {
            // SQL DELETE 쿼리 작성
            String deleteQuery = "DELETE FROM prof WHERE profCode = ? AND profName = ? AND phone = ?";

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);

            // 파라미터 설정
            preparedStatement.setString(1, profCode);
            preparedStatement.setString(2, profName);
            preparedStatement.setString(3, phone);

            // DELETE 쿼리 실행
            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다.");
                ;
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            DeptForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            profInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삭제 중 오류 발생: " + e.getMessage());
            ;
        }
    }

    public void deleteStudentInfo(String stdCode, String stdName, String phone) {
        try {
            // SQL DELETE 쿼리 작성
            String deleteQuery = "DELETE FROM student WHERE stdCode = ? AND stdName = ? AND phone = ?";

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);

            // 파라미터 설정
            preparedStatement.setString(1, stdCode);
            preparedStatement.setString(2, stdName);
            preparedStatement.setString(3, phone);

            // DELETE 쿼리 실행
            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다.");
                ;
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            StudentForm.tableModel.setRowCount(0);

            // 업데이트된 데이터로 테이블 모델 채우기
            studentInfoAll();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삭제 중 오류 발생: " + e.getMessage());
            ;
        }
    }

    public void deleteSubjectInfo(String subjectName) {
        try {
            // SQL DELETE 쿼리 작성
            String deleteQuery = "DELETE FROM subject WHERE subjectName = ? ";

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);

            // 파라미터 설정
            preparedStatement.setString(1, subjectName);

            // DELETE 쿼리 실행
            int deletedRows = preparedStatement.executeUpdate();

            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다.");
                ;
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            CourseRegistration.tableModel2.setRowCount(0);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    public void deleteCourseRegistrationInfo(String subjectCode, String subjectName, String stdCode, String stdName) {

        try {
            // SQL DELETE 쿼리 작성
            String deleteQuery = "DELETE FROM course_registration WHERE subjectCode = ? And subjectName = ? AND stdCode = ? AND  stdName = ? ";

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);

            // 파라미터 설정
            preparedStatement.setString(1, subjectCode);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setString(3, stdCode);
            preparedStatement.setString(4, stdName);

            // DELETE 쿼리 실행
            int deletedRows = preparedStatement.executeUpdate();


            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(null, "데이터가 성공적으로 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "학번, 이름, 학과를 입력하세요.");
            }

            // PreparedStatement와 연결 닫기
            preparedStatement.close();

            CourseRegistration.tableModel2.setRowCount(0);

            courseRegistrationInfoAll(stdCode);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    public int login(String userID, String userPassword) {
        String SQL = "SELECT userPassword FROM user WHERE userID = ?"; // 각 ID에 해당하는 비밀번호 조회

        try {
            ps = c.prepareStatement(SQL);
            ps.setString(1, userID); // 위  ?에 적용할 ID
            rs = ps.executeQuery(); // 쿼리 실행 결과를 객체에 저장

            if (rs.next()) {
                if (rs.getString(1).equals(userPassword)) {
                    return 1;
                } else {
                    return 0;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    public String getFoundAccountInfo(String insertedName, String insertedPhone) {
        String accountInfo = "";

        try {
            String query = "SELECT userID, userPassword FROM user WHERE userName = ? AND userPhone = ?";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, insertedName); // foundName은 일치하는 이름
            preparedStatement.setString(2, insertedPhone); // foundPhone은 일치하는 전화번호

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과를 문자열로 포맷
            while (resultSet.next()) {
                String userID = resultSet.getString("userID"); // userID로 수정
                String userPassword = resultSet.getString("userPassword");

                accountInfo += "계정 아이디: " + userID + "\n"; // userID 사용
                accountInfo += "비밀번호: " + userPassword + "\n";
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountInfo;
    }


}