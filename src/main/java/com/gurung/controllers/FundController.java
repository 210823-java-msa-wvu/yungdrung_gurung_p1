package com.gurung.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Employee;
import com.gurung.beans.Fund;
import com.gurung.beans.Reimbursement;
import com.gurung.services.FundService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FundController implements FrontController{

    private FundService fundService = new FundService();
    private ObjectMapper om = new ObjectMapper();
    private Logger logger = LogManager.getLogger(FundController.class);
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Getting the attribute we set in the RequestHandler's handle() method
        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);


        if(path == null || path.equals("")){ //http://localhost:8080/project1/reimbursement
            switch (request.getMethod()){
                case "GET":{
                    System.out.println("Getting all funds from the database...");

                    List<Fund> fundList = fundService.getAllFunds();
                    response.getWriter().write(om.writeValueAsString(fundList));
                    break;
                }
                case "POST": {

                    //then we would add the fund(read from the request body) to the database

//                    HttpSession session = request.getSession();
//                    Employee employee = (Employee) session.getAttribute("user");


                    Fund f = om.readValue(request.getReader(), Fund.class);
//
                    System.out.println(f);

                    fundService.createFund(f);
//                    response.sendRedirect("static/dashboard.html");
                    break;
                }
            }
        }
        else{
            //Else -> there IS a path attribute that we need to use in our logic

            // Save that attribute into an Integer
            int reId = Integer.parseInt(path);

            Fund fund = null;

            switch (request.getMethod()){
                // /reimbursements/1
                case "GET":{
                    fund = fundService.searchById(reId);
                    if(fund != null){
                        response.getWriter().write(om.writeValueAsString(fund));
                    }else{
                        response.sendError(404, "Record not found");
                    }
                    break;
                }
                case "PUT": {
                    fund = om.readValue(request.getReader(), Fund.class);

                    fund.setId(reId);

//                    System.out.println(fund);

                    fundService.updateFund(fund);
                    break;
                }
                case "DELETE":{
                    fundService.deleteFund(reId);
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
