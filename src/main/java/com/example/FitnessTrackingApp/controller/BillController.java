package com.example.FitnessTrackingApp.controller;

import com.example.FitnessTrackingApp.model.dto.BillDto;
import com.example.FitnessTrackingApp.model.entities.Bill;
import com.example.FitnessTrackingApp.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    BillService billService;

    @PostMapping
    public Bill createBill(@RequestParam Long userId,
                           @RequestParam Double subscriptionFee,
                           @RequestParam Double additionalCharges) {
        return billService.createNewBill(userId, subscriptionFee, additionalCharges);
    }

    @GetMapping("/{billId}")
    public Optional<Bill> getBillById(@PathVariable Long billId) {
        return billService.findById(billId);
    }

    @PatchMapping("/{billId}/pay")
    public Bill markBillAsPaid(@PathVariable Long billId) {
        return billService.markBillAsPaid(billId);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BillDto>> getBillsByUserId(@PathVariable Long userId) {
        List<BillDto> bills = billService.getBillsByUserId(userId);
        return ResponseEntity.ok(bills);
    }

    @PutMapping("/{userId}/bills/{billId}/")
    public ResponseEntity<Bill> updateBillAmount(
            @PathVariable Long userId,
            @PathVariable Long billId,
            @RequestParam double newSubscriptionFee,
            @RequestParam double newAdditionalCharges) {
        Bill updatedBill = billService.updateBillByUserId(userId, billId, newSubscriptionFee, newAdditionalCharges);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping("/{userId}/bills/{billId}")
    public ResponseEntity<Bill> cancelBill(@PathVariable Long billId) {
        Bill cancelledBill = billService.cancelById(billId);
        return ResponseEntity.ok(cancelledBill);
    }

}
