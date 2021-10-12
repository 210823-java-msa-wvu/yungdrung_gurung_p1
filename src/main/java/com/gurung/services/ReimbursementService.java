package com.gurung.services;


import com.gurung.beans.Fund;
import com.gurung.beans.Reimbursement;
import com.gurung.exceptions.CustomSQLException;
import com.gurung.repositories.ReimbursementRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

public class ReimbursementService {
    private ReimbursementRepository reimbursementRepo = new ReimbursementRepository();
    private FundService fundService = new FundService();

    //Searching by ID service
    public Reimbursement searchReimburseById(Integer id){
        Reimbursement re = reimbursementRepo.getById(id);

        // check to make sure User object is not null
        if (re != null) {
            // check something like approve or pass

            return  re;
        }
        return null;
    }

    // Get all Reimbursements service
    public List<Reimbursement> getAllReimbursements(){
        return reimbursementRepo.getAll();
    }


    //Add new reimbursement service
    public Boolean requesting4Reimbursement(Reimbursement reimbursement){
        Date date = new Date();

        Reimbursement r = new Reimbursement();
        r.setEventStart(reimbursement.getEventStart());
        r.setEventTime(reimbursement.getEventTime());
        r.setEventLocation(reimbursement.getEventLocation());
        r.setEventDescription(reimbursement.getEventDescription());
        r.setEvent(reimbursement.getEvent());
        r.setEmployee(reimbursement.getEmployee());
        r.setEventCost(reimbursement.getEventCost());
        r.setCreatedAt(date);
        r.setUpdatedAt(date);
        r.setUrgent(reimbursement.isUrgent());

        if( r != null){
            reimbursementRepo.add(r);
            return true;
        }
        else {
            return false;
        }

    }

    // Update service
    public boolean updateRequest(Reimbursement r){
        Reimbursement reimbursement = reimbursementRepo.getById(r.getId());
        System.out.println("from service");
        System.out.println(r.getGrade().getId());
        System.out.println( reimbursementRepo.getById(r.getId()));
        Date date = new Date();

        if(reimbursement.getId() == r.getId()){
            reimbursement.setId(r.getId());
            reimbursement.setGrade(r.getGrade());
            reimbursement.setUpdatedAt(date);

            System.out.println(reimbursement);

            reimbursementRepo.update(reimbursement);
            return true;

        }
        return false;
    }

    //Delete service
    public boolean deleteRequest(Integer id){
        Reimbursement reimbursement = reimbursementRepo.getById(id);

        if(reimbursement.getId() == id){
            reimbursementRepo.delete(id);
            return true;

        }
        return false;
    }
}
