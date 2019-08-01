package com.ludy.springboot.Dao;

import com.ludy.springboot.pojo.ProjectInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("projectInfoDao")
public class ProjectInfoDao {
    public static Logger lo=Logger.getLogger(ProjectInfoDao.class);
    public List<ProjectInfo> selectTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8",
                            "root",
                            "123456");
            ps = conn.prepareStatement("SELECT * FROM etl_project_info");
            rs = ps.executeQuery();
            lo.info("Select information from the table \"etl_project_info\"!");
            while (rs.next()) {
                ProjectInfo pi = new ProjectInfo();
                pi.setName(rs.getString(1));
                pi.setDescription(rs.getString(2));
                pi.setId(rs.getString(3));
                pi.setDate(rs.getString(4));
                projectInfoList.add(pi);
                System.out.println(rs.getInt(1) + "------" +
                        rs.getString(2) +
                        "-----" + rs.getString(3) +
                        "-----" + rs.getString(4) +
                        "-----" + rs.getString(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            lo.warn("SQLException error");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    lo.info("Connection closed already!");
                }
                if (ps != null) {
                    ps.close();
                    lo.info("Prepare Statement closed already!");
                }
                if (rs != null) {
                    rs.close();
                    lo.info("Result Set closed already!");
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
                lo.warn("Warning: SQL Exception!");
            }
        }
        lo.info("Display the table");
        return projectInfoList;
    }

    public boolean deleteTable(int index){
        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;
        try {
            conn = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8",
                            "root",
                            "123456");
            String sql = String.format("DELETE FROM etl_project_info WHERE S_PK_UUID = ?", index);
            ps = conn.prepareStatement(sql);
            i = ps.executeUpdate();
            lo.info("Delete rows " + index + "from table etl_project_info");
            System.out.println("Delete table at index = " + index + " successfully!");
        } catch (SQLException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                System.out.println("Connection pool closed successfully");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return i > 0;
    }

    public boolean insertTable(String insertion) {
        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8",
                    "root",
                    "123456");
            ps = conn.prepareStatement("INSERT INTO etl_project_info ('S_PROJECT_NAME', 'S_PROJECT_DESC', 'S_PROJECT_ID', 'D_CREATE_DATE') VALUES (" + insertion + ")");
            i = ps.executeUpdate();
            System.out.println("Insert the information of " + insertion + " successfully!");
        } catch (SQLException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                System.out.println("Connection pool closed successfully!");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
       return i > 0;
    }

    public boolean updateTable(String name, String value, int index) {
        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8",
                    "root",
                    "123456");
            String sql = "UPDATE etl_project_info " + name + " = " + value + "WHERE S_PK_UUID = " + index;
            ps = conn.prepareStatement(sql);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                System.out.println("Connection closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i > 0;
    }





}



