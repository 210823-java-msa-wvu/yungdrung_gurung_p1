package com.gurung.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Approval;
import com.gurung.beans.Fund;
import com.gurung.controllers.FrontController;
import com.gurung.services.ApprovalService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ApprovalController implements FrontController{
    private ApprovalService approvalService = new ApprovalService();
    private ObjectMapper om = new ObjectMapper();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Getting the attribute we set in the RequestHandler's handle() method
        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);


        if(path == null || path.equals("")){ //http://localhost:8080/project1/reimbursement
            switch (request.getMethod()){
                case "GET":{
                    System.out.println("Getting all approvals from the database...");

                    List<Approval> approvals = approvalService.getAllRequest();
                    response.getWriter().write(om.writeValueAsString(approvals));
                    break;
                }
                case "POST": {
                    System.out.println("reaching controller...");

                    Approval a = om.readValue(request.getReader(), Approval.class);

                    System.out.println(a);

                    approvalService.requestToApprove(a);
                    response.sendRedirect("static/approval.html");
                    break;
                }
            }
        }
        else{
            //Else -> there IS a path attribute that we need to use in our logic

            // Save that attribute into an Integer
            int aId = Integer.parseInt(path);

            Approval approval = null;

            switch (request.getMethod()){
                // /reimbursements/1
                case "GET":{
                    String cookieId =String.valueOf(aId);

                    approval = approvalService.searchById(aId);
                    if(approval != null){
                        response.setStatus(200);
                        response.getWriter().write(om.writeValueAsString(approval));
//                        response.sendRedirect("static/approval.html");

                    }else{
                        response.sendError(404, "Record not found");
                    }
                    break;
                }
                case "PUT": {
                    approval = om.readValue(request.getReader(), Approval.class);
                    Date date = new Date();
                    approval.setId(aId);
                    approval.setStartDate(date);

//                    System.out.println(approval);

                    approvalService.updateApproval(approval);
                    break;
                }
                case "DELETE":{
                    approvalService.deleteApproval(aId);
                    break;
                }
                default:{
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    break;
                }
            }
        }
    }
}
