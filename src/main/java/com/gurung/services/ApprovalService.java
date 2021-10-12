package com.gurung.services;

import com.gurung.beans.Approval;
import com.gurung.beans.Fund;
import com.gurung.repositories.ApprovalRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public class ApprovalService {
    private ApprovalRepository approvalRepo = new ApprovalRepository();
    private Logger logger = LogManager.getLogger(FundService.class);

    //Searching by ID service
    public Approval searchById(Integer id){
        Approval a = approvalRepo.getById(id);

        // check to make sure User object is not null
        if (a != null) {
            // check something like approve or pass
            return  a;
        }
        return null;
    }

    // Get all approvals service
    public List<Approval> getAllRequest(){
        return approvalRepo.getAll();
    }

    //Add new  approval service
    public Boolean requestToApprove(Approval approval){
        System.out.println("reaching service...");

        Date data = new Date();
        Approval app = new Approval();

        app.setReason(approval.getReason());
        app.setStartDate(data);
        System.out.println(data);
        app.setStatus(approval.isStatus());
        app.setEmployee(approval.getEmployee());
        app.setReimbursement(approval.getReimbursement());

        if(app != null){
            approvalRepo.add(app);
            return true;
        }
        return  false;
    }

    // Update service
    public boolean updateApproval(Approval a){
        Approval app = approvalRepo.getById(a.getId());

//        System.out.println(a.getId());
//        System.out.println( approvalRepo.getById(a.getId()));

        if(app.getId() == a.getId()){
            app.setId(a.getId());
            app.setReason(a.getReason());
            app.setStartDate(a.getStartDate());
            app.setStatus(a.isStatus());
            app.setEmployee(a.getEmployee());
            app.setReimbursement(a.getReimbursement());

//            System.out.println( app);
            approvalRepo.update(app);
            return true;

        }
        return false;
    }

    //Delete service
    public boolean deleteApproval(Integer id){
        Approval a = approvalRepo.getById(id);

        if(a.getId() == id){
            approvalRepo.delete(id);
            return true;

        }
        return false;
    }
}
