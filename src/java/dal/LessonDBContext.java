/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Lesson;
import model.PricePackage;

/**
 *
 * @author Zuys
 */
public class LessonDBContext extends DBContext {

    public boolean updateLesson(Lesson l) {
        String sql = "UPDATE [dbo].[Lesson]\n"
                + "   SET [lessonTypeId] = ?\n"
                + "      ,[lessonName] = ?\n"
                + "      ,[topicID] = ?\n"
                + "      ,[lessonOrder] = ?\n"
                + " WHERE lessonID = ?";
        if (l.getLessonType().getLessonTypeID() == 2) {
            sql = "UPDATE [dbo].[Lesson]\n"
                    + "   SET [lessonTypeId] = ?\n"
                    + "      ,[lessonName] = ?\n"
                    + "      ,[topicID] = ?\n"
                    + "      ,[lessonOrder] = ?\n"
                    + "      ,[videoLink] = ?\n"
                    + "      ,[htmlContent] = ?\n"
                    + " WHERE lessonID = ?";
        } else if (l.getLessonType().getLessonTypeID() == 3) {
            sql = "UPDATE [dbo].[Lesson]\n"
                    + "   SET [lessonTypeId] = ?\n"
                    + "      ,[lessonName] = ?\n"
                    + "      ,[topicID] = ?\n"
                    + "      ,[lessonOrder] = ?\n"
                    + "      ,[htmlContent] = ?\n"
                    + "      ,[quizID] = ?\n"
                    + " WHERE lessonID = ?";
        }
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, l.getLessonType().getLessonTypeID());
            stm.setString(2, l.getLessonName());
            stm.setInt(3, l.getTopicID());
            stm.setInt(4, l.getLessonOrder());
            switch (l.getLessonType().getLessonTypeID()) {
                case 2:
                    stm.setString(5, l.getVideoLink());
                    stm.setString(6, l.getHtmlContent());
                    stm.setInt(7, l.getLessonID());
                    break;
                case 3:
                    stm.setString(5, l.getHtmlContent());
                    stm.setInt(6, l.getQuizID());
                    stm.setInt(7, l.getLessonID());
                    break;
                case 1:
                    stm.setInt(5, l.getLessonID());
                    break;
            }
            return stm.executeUpdate() >= 1;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean addLesson(Lesson l) {
        String sql = "INSERT INTO [dbo].[Lesson]\n"
                + "           ([lessonID]\n"
                + "           ,[lessonTypeId]\n"
                + "           ,[lessonName]\n"
                + "           ,[topicID]\n"
                + "           ,[lessonOrder]\n"
                + "           ,[status]\n)"
                + "     VALUES\n"
                + "           ((select top 1 lessonID from Lesson order by lessonID desc)+1\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        if (l.getLessonType().getLessonTypeID() == 2) {
            sql = "INSERT INTO [dbo].[Lesson]\n"
                    + "           ([lessonID]\n"
                    + "           ,[lessonTypeId]\n"
                    + "           ,[lessonName]\n"
                    + "           ,[topicID]\n"
                    + "           ,[lessonOrder]\n"
                    + "           ,[status]\n"
                    + "           ,[videoLink]\n"
                    + "           ,[htmlContent])\n"
                    + "     VALUES\n"
                    + "           ((select top 1 lessonID from Lesson order by lessonID desc)+1\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
        } else if (l.getLessonType().getLessonTypeID() == 3) {
            sql = "INSERT INTO [dbo].[Lesson]\n"
                    + "([lessonID]\n"
                    + ",[lessonTypeId]\n"
                    + ",[lessonName]\n"
                    + ",[topicID]\n"
                    + ",[lessonOrder]\n"
                    + ",[status]\n"
                    + ",[htmlContent]\n"
                    + ",[quizID])\n"
                    + "VALUES\n"
                    + "((select top 1 lessonID from Lesson order by lessonID desc)+1\n"
                    + ",?\n"
                    + ",?\n"
                    + ",?\n"
                    + ",?\n"
                    + ",?\n"
                    + ",?\n"
                    + ",?)";
        }
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, l.getLessonType().getLessonTypeID());
            stm.setString(2, l.getLessonName());
            stm.setInt(3, l.getTopicID());
            stm.setInt(4, l.getLessonOrder());
            stm.setBoolean(5, false);
            switch (l.getLessonType().getLessonTypeID()) {
                case 2:
                    stm.setString(6, l.getVideoLink());
                    stm.setString(7, l.getHtmlContent());
                    break;
                case 3:
                    stm.setString(6, l.getHtmlContent());
                    stm.setInt(7, l.getQuizID());
                    break;
            }
            return stm.executeUpdate() >= 1;
        } catch (SQLException e) {
        }
        return false;
    }

    public ArrayList<Lesson> getLessons(int pageindex, int pagesize, String topicID) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        LessonTypeDBContext dbLessonType = new LessonTypeDBContext();
        TopicDBContext dbTopic = new TopicDBContext();
        try {
            String sql = "SELECT lessonID, lessonTypeId, lessonName, [lessonOrder], topicID, videoLink, htmlContent, status, quizID\n"
                    + "FROM Lesson \n"
                    + "WHERE topicID in (" + topicID + ")"
                    + "ORDER BY lessonID ASC\n"
                    + "OFFSET (? - 1) * ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageindex);
            stm.setInt(2, pagesize);
            stm.setInt(3, pagesize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setLessonID(rs.getInt("lessonID"));
                l.setLessonType(dbLessonType.getLessonType(rs.getInt("lessonTypeID")));
                l.setLessonName(rs.getString("lessonName"));
                l.setLessonOrder(rs.getInt("lessonOrder"));
                l.setTopicID(rs.getInt("topicID"));
                l.setVideoLink(rs.getString("videoLink"));
                l.setHtmlContent(rs.getString("htmlContent"));
                l.setStatus(rs.getBoolean("status"));
                l.setQuizID(rs.getInt("quizID"));
                lessons.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public ArrayList<Lesson> getSearchLessons(int pageindex, int pagesize, String topicID, int pricePackages, int lessonType, String status, String lessonName) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        LessonTypeDBContext dbLessonType = new LessonTypeDBContext();
        TopicDBContext dbTopic = new TopicDBContext();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        try {
            String sql = "SELECT l.lessonID, lessonTypeId, lessonName, [lessonOrder], topicID, videoLink, htmlContent, status, quizID\n"
                    + "FROM Lesson l inner join LessonPricePackage lp on l.lessonID = lp.lessonID\n"
                    + "WHERE topicID in (" + topicID + ")";
            sb.append(sql);
            if (pricePackages != 0 || lessonType != 0 || (status != null && status.trim().length() != 0)) {
                if (pricePackages != 0) {
                    String and = " and lp.pricePackageID = ?";
                    sb.append(and);
                    count++;
                }
                if (lessonType != 0) {
                    String and = " and l.lessonTypeId = ?";
                    sb.append(and);
                    count++;
                }
                if (!status.matches("All")) {
                    String and = " and l.status = ?";
                    sb.append(and);
                    count++;
                }
                if (lessonName != null && lessonName.trim().length() != 0) {
                    String and = " and l.lessonName like ?";
                    sb.append(and);
                    count++;
                }
            }
            sb.append(" GROUP BY l.lessonID, l.lessonTypeId, l.lessonName, l.[lessonOrder], l.topicID, l.videoLink, l.htmlContent, l.[status], l.quizID\n"
                    + "ORDER BY l.lessonID ASC\n"
                    + "OFFSET (? - 1) * ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY");
            String sql_final = sb.toString();
            PreparedStatement stm = connection.prepareStatement(sql_final);

            if (pricePackages != 0) {
                stm.setInt(1, pricePackages);
                if (lessonType != 0) {
                    stm.setInt(2, lessonType);
                    if (!status.matches("All")) {
                        stm.setBoolean(3, status.matches("1"));
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(4, "%" + lessonName + "%");
                        }
                    } else {
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(3, "%" + lessonName + "%");
                        }
                    }
                } else {
                    if (!status.matches("All")) {
                        stm.setBoolean(2, status.matches("1"));
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(3, "%" + lessonName + "%");
                        }
                    }
                }
            } else {
                if (lessonType != 0) {
                    stm.setInt(1, lessonType);
                    if (!status.matches("All")) {
                        stm.setBoolean(2, status.matches("1"));
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(3, "%" + lessonName + "%");
                        }
                    }
                } else {
                    if (!status.matches("All")) {
                        stm.setBoolean(1, status.matches("1"));
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(2, "%" + lessonName + "%");
                        }
                    } else {
                        if (lessonName != null && lessonName.trim().length() != 0) {
                            stm.setString(1, "%" + lessonName + "%");
                        }
                    }
                }
            }
            stm.setInt(1 + count, pageindex);
            stm.setInt(2 + count, pagesize);
            stm.setInt(3 + count, pagesize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setLessonID(rs.getInt("lessonID"));
                l.setLessonType(dbLessonType.getLessonType(rs.getInt("lessonTypeID")));
                l.setLessonName(rs.getString("lessonName"));
                l.setLessonOrder(rs.getInt("lessonOrder"));
                l.setTopicID(rs.getInt("topicID"));
                l.setVideoLink(rs.getString("videoLink"));
                l.setHtmlContent(rs.getString("htmlContent"));
                l.setStatus(rs.getBoolean("status"));
                l.setQuizID(rs.getInt("quizID"));
                lessons.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public int countLesson(int courseID) {
        int total = 0;
        try {
            String sql = "select COUNT(*) as total from Lesson l \n"
                    + "inner join Topic t on l.topicID = t.topicID \n"
                    + "inner join Course c on t.courseID = c.courseID \n"
                    + "where c.courseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public ArrayList<Lesson> countSearchLesson(String topicID) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        LessonTypeDBContext dbLessonType = new LessonTypeDBContext();
        PricePackageDBContext dbPrice = new PricePackageDBContext();
        try {
            String sql = "SELECT lessonID, lessonTypeId, lessonName, [lessonOrder], topicID, videoLink, htmlContent, [status], quizID\n"
                    + "FROM Lesson \n"
                    + "WHERE topicID in (" + topicID + ")"
                    + "ORDER BY lessonID ASC\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setLessonID(rs.getInt("lessonID"));
                l.setLessonType(dbLessonType.getLessonType(rs.getInt("lessonTypeID")));
                l.setLessonName(rs.getString("lessonName"));
                l.setLessonOrder(rs.getInt("lessonOrder"));
                l.setPricePackages(dbPrice.getPricePackagesByLessonID(rs.getInt("lessonID")));
                l.setTopicID(rs.getInt("topicID"));
                l.setVideoLink(rs.getString("videoLink"));
                l.setHtmlContent(rs.getString("htmlContent"));
                l.setStatus(rs.getBoolean("status"));
                l.setQuizID(rs.getInt("quizID"));
                lessons.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public Lesson getLesson(int lessonID) {
        LessonTypeDBContext dbLType = new LessonTypeDBContext();
        PricePackageDBContext dbPrice = new PricePackageDBContext();
        Lesson l = new Lesson();
        try {
            String sql = "SELECT lessonID, lessonTypeId, lessonName, [lessonOrder], topicID, videoLink, htmlContent, status, quizID\n"
                    + "FROM Lesson \n"
                    + "WHERE lessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                l.setLessonID(lessonID);
                l.setLessonName(rs.getString("lessonName"));
                l.setLessonType(dbLType.getLessonType(rs.getInt("lessonTypeId")));
                l.setLessonOrder(rs.getInt("lessonOrder"));
                l.setPricePackages(dbPrice.getPricePackagesByLessonID(rs.getInt("lessonID")));
                l.setTopicID(rs.getInt("topicID"));
                l.setVideoLink(rs.getString("videoLink"));
                l.setHtmlContent(rs.getString("htmlContent"));
                l.setStatus(rs.getBoolean("status"));
                l.setQuizID(rs.getInt("quizID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public boolean changeStatus(Lesson lesson) {
        String sql = "UPDATE [dbo].[Lesson]\n"
                + "   SET [status] = ?\n"
                + " WHERE lessonID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, !lesson.isStatus());
            stm.setInt(2, lesson.getLessonID());
            return stm.executeUpdate() >= 1;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     public ArrayList<Lesson> getLessonByTopic(int topicID) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        LessonTypeDBContext dbLessonType = new LessonTypeDBContext();
        try {
            String sql = "SELECT lessonID, lessonTypeId, lessonName, [lessonOrder], topicID, videoLink, htmlContent, [status]\n"
                    + "FROM Lesson\n"
                    + "WHERE topicID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, topicID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setLessonID(rs.getInt("lessonID"));
                l.setLessonType(dbLessonType.getLessonType(rs.getInt("lessonTypeID")));
                l.setLessonName(rs.getString("lessonName"));
                l.setLessonOrder(rs.getInt("lessonOrder"));
                l.setTopicID(rs.getInt("topicID"));
                l.setVideoLink(rs.getString("videoLink"));
                l.setHtmlContent(rs.getString("htmlContent"));
                l.setStatus(rs.getBoolean("status"));
                lessons.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

}
