package com.example.FitnessTrackingApp.service;

import com.example.FitnessTrackingApp.model.dto.BillDto;
import com.example.FitnessTrackingApp.model.entities.Bill;

import java.util.List;
import java.util.Optional;

public interface BillService {
    Bill createNewBill(Long userId, Double subscriptionFee, Double additionalCharges);

    Bill markBillAsPaid(Long billId);

    List<BillDto> getBillsByUserId(Long userId);

    Optional<Bill> findById(Long billId);

    Bill cancelById(Long billId);

    Bill updateBillByUserId(Long userId, Long billId, double newSubscriptionFee, double newAdditionalCharges);
}
