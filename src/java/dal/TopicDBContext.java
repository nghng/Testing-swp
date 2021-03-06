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
import model.Post;
import model.Topic;

/**
 *
 * @author ADMIN
 */
public class TopicDBContext extends DBContext {

    public ArrayList<Topic> getTopicByPagination(int courseID, int pageindex, int pagesize) {
        ArrayList<Topic> topics = new ArrayList<>();
        String sql = " select * from Topic \n"
                + "               where courseID = ?\n"
                + "               order by topicID\n"
                + "               OFFSET (?-1) * ? ROWS\n"
                + "             FETCH NEXT  ? ROWS ONLY";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            stm.setInt(2, pageindex);
            stm.setInt(3, pagesize);
            stm.setInt(4, pagesize);
            rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourseID(courseID);
                Topic t = new Topic();
                t.setTopicID(rs.getInt("topicID"));
                t.setTopicName(rs.getString("topicName"));
                t.setCourse(c);
                topics.add(t);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topics;

    }

    public int getCountTopicForPagination(int courseID) {
        String sql = " select count(*) as total from Topic \n"
                + " where courseID = ?\n"
                + " ";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public boolean insertTopic(int courseID, String topicName) {
        String sql = "INSERT INTO [dbo].[Topic]\n"
                + "           ([courseID]\n"
                + "           ,[topicName])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            stm.setString(2, topicName);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean editTopic(String topicName, int topicID) {
        String sql = "UPDATE [dbo].[Topic]\n"
                + "    set  [topicName] = ?\n"
                + " WHERE topicID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, topicName);
            stm.setInt(2, topicID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean deleteTopic(int topidID) {
        String sql = "delete from Topic\n"
                + "where topicID =  ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, topidID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Topic> getTopics(int courseID) {
        CourseDBContext dbCourse = new CourseDBContext();
        ArrayList<Topic> topics = new ArrayList<>();
        try {
            String sql = "SELECT topicID, topicName, courseID\n"
                    + "FROM Topic\n"
                    + "WHERE courseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LessonDBContext dbLesson = new LessonDBContext();
                Topic t = new Topic();
                t.setTopicID(rs.getInt("topicID"));
                t.setTopicName(rs.getString("topicName"));
                t.setCourse(dbCourse.getCourse(courseID));
                t.setLessons(dbLesson.getLessonByTopic(rs.getInt("topicID")));
                topics.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topics;
    }
    public Topic getTopic(int topicID) {
        CourseDBContext dbCourse = new CourseDBContext();
        Topic t = new Topic();
        try {
            String sql = "SELECT topicID, topicName, courseID\n"
                    + "FROM Topic\n"
                    + "WHERE topicID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, topicID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t.setTopicID(rs.getInt("topicID"));
                t.setTopicName(rs.getString("topicName"));
                t.setCourse(dbCourse.getCourse(rs.getInt("courseID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    public boolean checkDuplicateAdd(String topicName, int courseID) {
        String sql = "select count(*) as total from Topic\n"
                + "where topicName = ? and courseID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, topicName);
            stm.setInt(2, courseID);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean checkDuplicateEdit(int topicID, String topicName, int courseID) {
        String sql = "select count(*) as total from Topic\n"
                + "where topicID != ? and topicName = ? and courseID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, topicID);
            stm.setString(2, topicName);
            stm.setInt(3, courseID);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

}
