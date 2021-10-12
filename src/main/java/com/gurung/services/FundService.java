package com.gurung.services;

import com.gurung.beans.Fund;
import com.gurung.repositories.FundRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class FundService {
    private FundRepository fundRepo = new FundRepository();
    private Logger logger = LogManager.getLogger(FundService.class);

    //Searching by ID service
    public Fund searchById(Integer id){
        Fund f = fundRepo.getById(id);

        // check to make sure User object is not null
        if (f != null) {
            // check something like approve or pass
            return  f;
        }
        return null;
    }

    // Get all Fund service
    public List<Fund> getAllFunds(){
        return fundRepo.getAll();
    }


    //Add new fund service
    public Boolean createFund(Fund fund){
        System.out.println("reaching Here  in fund service...");
        Date date = new Date();

        Fund f = new Fund();

        f.setEmployee(fund.getEmployee());
        f.setReimbursement(fund.getReimbursement());
        f.setAllocatedFund(fund.getAllocatedFund());
        f.setRemainingFund(fund.getRemainingFund());
        f.setStatus(fund.isStatus());
        f.setCreatedAt(date);
        f.setUpdatedAt(date);
        f.setResetFundDate(date);

        if(f != null){
            fundRepo.add(f);
            return true;
        }
        return  false;
    }

    // Update service
    public boolean updateFund(Fund f){
        Fund fund = fundRepo.getById(f.getId());

//        System.out.println(f.getId());
//        System.out.println( fundRepo.getById(f.getId()));

        if(fund.getId() == f.getId()){
            fund.setId(f.getId());
            fund.setAllocatedFund(f.getAllocatedFund());
            fund.setRemainingFund(fund.getAllocatedFund() - f.getAllocatedFund());
            fund.setUpdatedAt(f.getUpdatedAt());
            fund.setStatus(!fund.isStatus());

//            System.out.println( fund);
            fundRepo.update(fund);
            return true;

        }
        return false;
    }

    //Delete service
    public boolean deleteFund(Integer id){
        Fund fund = fundRepo.getById(id);

        if(fund.getId() == id){
            fundRepo.delete(id);
            return true;

        }
        return false;
    }
}
