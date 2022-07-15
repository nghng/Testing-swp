/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Post;
import model.Subcategory;

/**
 *
 * @author Hai Tran
 */
public class BlogDBContext extends DBContext {

    /**
     *
     * @param pageindex
     * @param pagesize
     * @return return an posts data in form of pagination
     */
    public ArrayList<Post> getPosts(int pageindex, int pagesize) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT postID, subcategoryID, title, briefInfo, [description], isFeatured,\n"
                    + "[status], author, updatedDate, thumbnailURL \n"
                    + "FROM Post\n"
                    + "ORDER BY updatedDate DESC\n"
                    + "OFFSET (?-1) * ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageindex);
            stm.setInt(2, pagesize);
            stm.setInt(3, pagesize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryDBContext dbSubCate = new SubCategoryDBContext();
                Post post = new Post();
                post.setPostID(rs.getInt("postID"));
                post.setSubcategory(dbSubCate.getSubcategory(rs.getInt("subcategoryID")));
                post.setTitle(rs.getString("title"));
                post.setBriefInfo(rs.getString("briefInfo"));
                post.setDescription(rs.getString("description"));
                post.setIsFeatured(rs.getBoolean("isFeatured"));
                post.setStatus(rs.getBoolean("status"));

                post.setUpdatedDate(rs.getDate("updatedDate"));
                post.setThumbnailUrl(rs.getString("thumbnailURL"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    /**
     *
     * @return the number of records in post table
     */
    public int count() {
        try {
            String sql = "SELECT COUNT(*) as Total FROM Post";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     *
     * @param postID1
     * @param postID2
     * @param postID3
     * @param postID4
     * @return
     */
    public ArrayList<Post> getPostForHome(int postID1, int postID2, int postID3, int postID4) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT postID, subcategoryID, title, briefInfo, [description], isFeatured,\n"
                    + "[status], author, updatedDate, thumbnailURL\n"
                    + "FROM Post\n"
                    + "WHERE postID IN (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, postID1);
            stm.setInt(2, postID2);
            stm.setInt(3, postID3);
            stm.setInt(4, postID4);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryDBContext dbSubCate = new SubCategoryDBContext();
                Post post = new Post();
                post.setPostID(rs.getInt("postID"));
                post.setSubcategory(dbSubCate.getSubcategory(rs.getInt("subcategoryID")));
                post.setTitle(rs.getString("title"));
                post.setBriefInfo(rs.getString("briefInfo"));
                post.setDescription(rs.getString("description"));
                post.setIsFeatured(rs.getBoolean("isFeatured"));
                post.setStatus(rs.getBoolean("status"));

                post.setUpdatedDate(rs.getDate("updatedDate"));
                post.setThumbnailUrl(rs.getString("thumbnailURL"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    /**
     *
     * @param postID
     * @return the post with the according postID
     */
    public Post getPost(int postID) {
        try {
            String sql = "SELECT postID, p.subcategoryID, sc.subcategoryName, title, briefInfo, [description], isFeatured,[status], author, updatedDate, thumbnailURL,sc.categoryID\n"
                    + "FROM Post p join SubCategory sc\n"
                    + "ON p.subcategoryID = sc.subcategoryID\n"
                    + "WHERE P.postID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            ResultSet rs = stm.executeQuery();
            CategoryDBContext categoryDBContext = new CategoryDBContext();
            if (rs.next()) {
                Subcategory sc = new Subcategory();
                Account a = new Account();
                Post p = new Post();
                sc.setCategoryID(rs.getInt("categoryID"));
                sc.setSubcategoryName(rs.getString("subcategoryName"));
                sc.setSubcategoryID(rs.getInt("subcategoryID"));
                a.setUsername(rs.getString("author"));
                p.setSubcategory(sc);
                p.setAuthor(a);
                p.setTitle(rs.getString("title"));
                p.setBriefInfo(rs.getString("briefInfo"));
                p.setDescription(rs.getString("description"));
                p.setIsFeatured(rs.getBoolean("isFeatured"));
                p.setStatus(rs.getBoolean("status"));
                p.setPostID(rs.getInt("postID"));
                p.setUpdatedDate(rs.getDate("updatedDate"));
                p.setThumbnailUrl(rs.getString("thumbnailURL"));
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param search
     * @param subcateID
     * @param sort
     * @param pageindex
     * @param pagesize
     * @return the post after being filtered
     */
    public ArrayList<Post> searchPost(String search, String subcateID, String sort, int pageindex, int pagesize) {
        ArrayList<Post> posts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            String sql = "SELECT postID, title, subcategoryID, briefInfo, [description], isFeatured, [status], author, updatedDate, thumbnailURL \n"
                    + "FROM Post\n"
                    + "WHERE title LIKE ?";
            sb.append(sql);
            if (!subcateID.isEmpty()) {
                String and = " AND subcategoryID IN(" + subcateID + ")";
                sb.append(and);
            }
            String offset = " ORDER BY [updatedDate] " + sort + " OFFSET ? * ? ROWS FETCH NEXT ? ROWS ONLY";
//            sql += offset;
            sb.append(offset);
            String sql_final = sb.toString();
            PreparedStatement stm = connection.prepareStatement(sql_final);
            stm.setString(1, "%" + search + "%");
            stm.setInt(2, pageindex - 1);
            stm.setInt(3, pagesize);
            stm.setInt(4, pagesize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryDBContext dbSubCate = new SubCategoryDBContext();
                AccountDBContext accountDBContext = new AccountDBContext();
                Post post = new Post();
                post.setPostID(rs.getInt("postID"));
                post.setSubcategory(dbSubCate.getSubcategory(rs.getInt("subcategoryID")));
                post.setTitle(rs.getString("title"));
                post.setBriefInfo(rs.getString("briefInfo"));
                post.setDescription(rs.getString("description"));
                post.setIsFeatured(rs.getBoolean("isFeatured"));
                post.setStatus(rs.getBoolean("status"));
                Account a = new Account();
                a.setUsername(rs.getString("author"));
                post.setAuthor(a);
                post.setUpdatedDate(rs.getDate("updatedDate"));
                post.setThumbnailUrl(rs.getString("thumbnailURL"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    public int countSearchBlog(String search, String subcateID) {
        StringBuilder sb = new StringBuilder();
        try {
            String sql = "SELECT COUNT(*) AS Total\n"
                    + "FROM Post\n";
            sb.append(sql);
            if (!search.trim().isEmpty() || !subcateID.isEmpty()) {
                sb.append("WHERE ");
                if (!search.trim().isEmpty()) {
                    String and = "title LIKE ? ";
                    sb.append(and);
                }
                if (!subcateID.isEmpty()) {
                    if (!search.trim().isEmpty()) {
                        sb.append("AND");
                    }
                    String and = " subcategoryID IN(" + subcateID + ")";
                    sb.append(and);
                }
            }
            String sql_final = sb.toString();
            PreparedStatement stm = connection.prepareStatement(sql_final);
            if (!search.trim().isEmpty()) {
                stm.setString(1, "%" + search + "%");
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int insertPost(int subCategoryID, String title, String briefInfo, String description, boolean isFeatured,
            boolean isStatus, String author) {
        String generatedColumns[] = {"ID"};
        String sql = "INSERT INTO [dbo].[Post]\n"
                + "           ([subCategoryID]\n"
                + "           ,[title]\n"
                + "           ,[briefInfo]\n"
                + "           ,[description]\n"
                + "           ,[isFeatured]\n"
                + "           ,[status]\n"
                + "           ,[author]\n"
                + "           ,[updatedDate]\n"
                + "           ,[thumbnailURL])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,GETDATE(),'')\n"
                + "\n"
                + "declare @id1 as int set @id1=(select SCOPE_IDENTITY());\n"
                + "\n"
                + "update Post \n"
                + "set thumbnailURL = 'post_thumbnail_id' + cast(@id1 as varchar(max)) +'.png' "
                + " where postID = @id1 \n";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql, generatedColumns);
            stm.setInt(1, subCategoryID);
            stm.setString(2, title);
            stm.setString(3, briefInfo);
            stm.setString(4, description);
            stm.setBoolean(5, isFeatured);
            stm.setBoolean(6, isStatus);
            stm.setString(7, author);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    public boolean updatePost(int subCategoryID, String title, String briefInfo,
            String description, boolean isFeatured, boolean status, int postID) {
        String sql = "update Post\n"
                + "set subCategoryID = ?, title=?,briefInfo=?,[description]=?,isFeatured=?,[status]=?,updatedDate=GETDATE()\n"
                + "where postID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subCategoryID);
            stm.setString(2, title);
            stm.setString(3, briefInfo);
            stm.setString(4, description);
            stm.setBoolean(5, isFeatured);
            stm.setBoolean(6, status);
            stm.setInt(7, postID);
            return stm.executeUpdate() >= 1;

        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateStatus(boolean status, int postID) {
        String sql = "update Post \n"
                + "set status = ?\n"
                + "where postID = ?";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, postID);
            return stm.executeUpdate() >= 1;
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public ArrayList<Post> getPostForHomePage() {
        ArrayList<Post> posts = new ArrayList<>();
        String sql = "select top 3 * from Post\n"
                + "where isFeatured = 1 and [status] = 1\n"
                + "order by updatedDate desc, postid desc";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryDBContext dbSubCate = new SubCategoryDBContext();
                Post post = new Post();
                post.setPostID(rs.getInt("postID"));
                post.setSubcategory(dbSubCate.getSubcategory(rs.getInt("subcategoryID")));
                post.setTitle(rs.getString("title"));
                post.setBriefInfo(rs.getString("briefInfo"));
                post.setDescription(rs.getString("description"));
                post.setIsFeatured(rs.getBoolean("isFeatured"));
                post.setStatus(rs.getBoolean("status"));

                post.setUpdatedDate(rs.getDate("updatedDate"));
                post.setThumbnailUrl(rs.getString("thumbnailURL"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

    public ArrayList<Post> getPostByFilter(ArrayList<Integer> subcategoryIDs, String author, String title,
            Boolean status, Boolean isFeatured, Date from, Date to, String orderBy, String order, int pageindex, int pagesize) {
        String sub = ""; // subcategory sql string 
        String sql = "";

        if (orderBy.trim().length() == 0 || orderBy == null) {
            orderBy = "updatedDate";
        }
        if (order.trim().length() == 0 || order == null) {
            order = "ASC";
        }

        for (int i = 0; i < subcategoryIDs.size(); i++) { // put them into in sql
            if (i == subcategoryIDs.size() - 1) {
                sub += "?";
            } else {
                sub += "?,";
            }
        }

        ArrayList<Post> posts = new ArrayList<>();
        String intersect = "\n intersect \n";

        String sql_base = " select * from Post";
        sql += sql_base;
        String sql_subCategory = " select * from Post\n"
                + "where subCategoryID in (" + sub + ")";
        if (subcategoryIDs.size() > 0) {
            sql += intersect;
            sql += sql_subCategory;
        }
        String sql_author = " select * from Post\n"
                + "where author like ?";
        if (author.trim().length() > 0 && author != null) {
            sql += intersect;
            sql += sql_author;
        }

        String sql_title = " select * from Post\n"
                + "where title like ?";
        if (title.length() > 0 && title != null) {
            sql += intersect;
            sql += sql_title;
        }

        String sql_status = " select * from Post\n"
                + "where [status] = ?";
        if (status != null) {
            sql += intersect;
            sql += sql_status;
        }

        String sql_isFeatured = " select * from Post\n"
                + "where isFeatured = ?";
        if (isFeatured != null) {
            sql += intersect;
            sql += sql_isFeatured;
        }

        String sql_fromDate = " select * from Post\n"
                + "where updatedDate >= ?";
        if (from != null) {
            sql += intersect;
            sql += sql_fromDate;
        }

        String sql_toDate = " select * from Post\n"
                + "where updatedDate <= ?";
        if (to != null) {
            sql += intersect;
            sql += sql_toDate;
        }

        String sql_orederAndPaging = " order by " + orderBy + " " + order + " \n"
                + " OFFSET (?-1)*? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";

        sql += sql_orederAndPaging;

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            int i = 1;
            stm = connection.prepareStatement(sql);
            if (subcategoryIDs.size() > 0) {
                for (Integer in : subcategoryIDs) {
                    stm.setInt(i, in);
                    i++;
                }
            }
            if (author.trim().length() > 0 && author != null) {
                stm.setString(i, "%" + author + "%");
                i++;
            }
            if (title.length() > 0 && title != null) {
                stm.setString(i, "%" + title + "%");
                i++;
            }
            if (status != null) {
                stm.setBoolean(i, status);
                i++;
            }

            if (isFeatured != null) {
                stm.setBoolean(i, isFeatured);
                i++;
            }

            if (from != null) {
                stm.setDate(i, from);
                i++;
            }

            if (to != null) {
                stm.setDate(i, to);
                i++;
            }

            stm.setInt(i, pageindex);
            stm.setInt(i + 1, pagesize);
            stm.setInt(i + 2, pagesize);
            rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryDBContext dbSubCate = new SubCategoryDBContext();
                AccountDBContext accountDBContext = new AccountDBContext();
                Post post = new Post();
                post.setPostID(rs.getInt("postID"));
                post.setSubcategory(dbSubCate.getSubcategory(rs.getInt("subcategoryID")));
                post.setTitle(rs.getString("title"));
                post.setBriefInfo(rs.getString("briefInfo"));
                post.setDescription(rs.getString("description"));
                post.setIsFeatured(rs.getBoolean("isFeatured"));
                post.setStatus(rs.getBoolean("status"));
                Account a = new Account();
                a.setUsername(rs.getString("author"));
                post.setAuthor(a);
                post.setUpdatedDate(rs.getDate("updatedDate"));
                post.setThumbnailUrl(rs.getString("thumbnailURL"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

}
