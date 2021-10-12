package com.gurung.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Employee;
import com.gurung.beans.Event;
import com.gurung.beans.Fund;
import com.gurung.beans.Reimbursement;
import com.gurung.services.EmployeeService;
import com.gurung.services.EventService;
import com.gurung.services.FundService;
import com.gurung.services.ReimbursementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

/*
 *
 * Endpoints:
 *  /reimbursement - (GET) retrieves all available reimbursements
 *        - (POST) adds a reimbursement ->  available only to login employee?...
 *
 *  /reimbursements/{id} - (GET) gets a reimbursement by id
 *             - (PUT) updates a reimbursement -> want available only to admins?...
 *             - (DELETE) deletes a reimbursement -> want available only to admins?...
 *              - (PATCH) if you want to partially update a resource
 * */

public class ReimbursementController implements FrontController {
    private ReimbursementService reimbursementService = new ReimbursementService();
    private EmployeeService employeeService = new EmployeeService();
    private EventService eventService = new EventService();
    private ObjectMapper om = new ObjectMapper();
    private Logger logger = LogManager.getLogger(ReimbursementController.class);


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Getting the attribute we set in the RequestHandler's handle() method
        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);


        if(path == null || path.equals("")){ //http://localhost:8080/project1/reimbursement
            switch (request.getMethod()){
                case "GET":{
                    System.out.println("Getting all reimbursements from the database...");

                    List<Reimbursement> reimbursementList = reimbursementService.getAllReimbursements();
                    response.getWriter().write(om.writeValueAsString(reimbursementList));
                    break;
                }
                case "POST": {
//                    System.out.println("reaching here!");

                    //then we would add the reimbursement(read from the request body) to the database
                    om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                    Reimbursement r = om.readValue(request.getReader(), Reimbursement.class);
//
//                    Integer employee_id = r.getEmployee().getId();
//                    Integer event_id = r.getEvent().getId();
                    //   System.out.println(employee_id);
//                    System.out.println(event_id);


                    Date date = new Date();
                    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    LocalDate currentDate = LocalDate.parse( dateFormat.format(date));
                    Month cMonth = currentDate.getMonth();
                    int cDay = currentDate.getDayOfMonth();
                    int cYear = currentDate.getYear();
//
//        System.out.println("cYear:" +cYear );
//        System.out.println("cMonth:" +cMonth );
//        System.out.println("cDay:" +cDay );

                    LocalDate eventStartDate = LocalDate.parse(dateFormat.format(r.getEventStart()));
                    Month eMonth = eventStartDate.getMonth();
                    int eDay = eventStartDate.getDayOfMonth();
                    int eYear = eventStartDate.getYear();
            //       System.out.println("eventYear:" +eYear );
//        System.out.println("eventMonth:" +eMonth );
//        System.out.println("eventDay:" +eDay );

                    int dayLeft = eDay - cDay;

                    if(eYear == cYear && eMonth.equals(cMonth) && dayLeft < 14) {
                        r.setUrgent(true);
                    }
                    else{
                        r.setUrgent(false);
                    }
                    reimbursementService.requesting4Reimbursement(r);

                    System.out.println(r);
                    response.setStatus(200);
                    response.sendRedirect("static/request.html");
                    break;
                }
            }
        }
        else{
            //Else -> there IS a path attribute that we need to use in our logic

            // Save that attribute into an Integer
            int reId = Integer.parseInt(path);

            Reimbursement reimbursement = null;

            switch (request.getMethod()){
                // /reimbursements/1
                case "GET":{
                    String reIdC = String.valueOf(reId);
                    reimbursement = reimbursementService.searchReimburseById(reId);
                    String reimC = om.writeValueAsString(reimbursement);
                    if(reimbursement != null){
                        response.setStatus(200);
                        response.getWriter().write(om.writeValueAsString(reimbursement));
//                        response.sendRedirect("static/status-form.html");

                    }else{
                        response.sendError(404, "Record not found");
                    }
                    break;
                }
                case "PUT": {
                    System.out.println("reaching put method");
                    reimbursement = om.readValue(request.getReader(), Reimbursement.class);
                    reimbursement.setId(reId);
                    System.out.println("from controller");
                    System.out.println(reimbursement.getId());
                    System.out.println(reimbursement.getGrade().getId());
                    reimbursementService.updateRequest(reimbursement);
                    response.setStatus(200);
//                    response.sendRedirect("static/approval.html");
                    break;
                }
                case "DELETE":{
                    reimbursementService.deleteRequest(reId);
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
