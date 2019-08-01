package com.ludy.springboot.controller;

import com.ludy.springboot.Dao.ProjectInfoDao;
import com.ludy.springboot.pojo.ProjectInfo;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@Controller
@RequestMapping(value = "/hi/")
public class HiController {
    @Autowired
    private ProjectInfoDao piDao;

    @ResponseBody
    @RequestMapping(value = "handleRequest", produces = {"text/plain;charset=UTF-8"})
    public void handleRequest(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        List<ProjectInfo> projectInfoList= piDao.selectTable();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONArray.fromObject(projectInfoList).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
