package com.example.FitnessTrackingApp.service.implementation;

import com.example.FitnessTrackingApp.model.dto.BillDto;
import com.example.FitnessTrackingApp.model.entities.Bill;
import com.example.FitnessTrackingApp.model.entities.User;
import com.example.FitnessTrackingApp.model.enums.BillStatus;
import com.example.FitnessTrackingApp.repo.BillRepository;
import com.example.FitnessTrackingApp.repo.UserRepository;
import com.example.FitnessTrackingApp.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImplementation implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Bill createNewBill(Long userId, Double subscriptionFee, Double additionalCharges) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Bill bill = new Bill();
        bill.setUserId(user.getId());
        bill.setBillingDate(LocalDate.now());
        bill.setDueDate(LocalDate.now().plusDays(15));
        bill.setSubscriptionFee(subscriptionFee);
        bill.setAdditionalCharges(additionalCharges);
        bill.setTotalAmountDue(subscriptionFee + additionalCharges);
        bill.setStatus(BillStatus.PENDING);
        return billRepository.save(bill);
    }

    @Override
    public Bill markBillAsPaid(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus(BillStatus.PAID);
        return billRepository.save(bill);
    }

    @Override
    public Optional<Bill> findById(Long billId) {
        return billRepository.findById(billId);
    }

    @Override
    public Bill cancelById(Long billId) {
        Bill billToCancel = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));
        billToCancel.setStatus(BillStatus.CANCELLED);
        Bill cancelledBill = billRepository.save(billToCancel);
        return cancelledBill;
    }

    @Override
    public Bill updateBillByUserId(Long userId, Long billId, double newSubscriptionFee, double newAdditionalCharges) {
        List<Bill> userBills = billRepository.findAllByUserId(userId);
        if (userBills.isEmpty()) {
            throw new RuntimeException("No bills found for user ID: " + userId);
        }
        Optional<Bill> billToUpdateOptional = userBills.stream()
                .filter(bill -> bill.getId().equals(billId))
                .findFirst();
        Bill bill = billToUpdateOptional
                .orElseThrow(() -> new RuntimeException("Bill ID " + billId + " not found in the user's bill list."));
        bill.setSubscriptionFee(newSubscriptionFee);
        bill.setAdditionalCharges(newAdditionalCharges);
        bill.setTotalAmountDue(newSubscriptionFee + newAdditionalCharges);
        bill.setStatus(BillStatus.ADJUSTED);
        return billRepository.save(bill);
    }

    @Override
    public List<BillDto> getBillsByUserId(Long userId) {
        List<Bill> bills = billRepository.findAllByUserId(userId);
        return bills.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BillDto toDto(Bill bill) {
        if (bill == null) {
            return null;
        }
        BillDto dto = new BillDto();
        dto.setId(bill.getId());
        dto.setUserId(bill.getUserId());
        dto.setBillingDate(bill.getBillingDate());
        dto.setDueDate(bill.getDueDate());
        dto.setSubscriptionFee(bill.getSubscriptionFee());
        dto.setAdditionalCharges(bill.getAdditionalCharges());
        dto.setTotalAmountDue(bill.getSubscriptionFee() + bill.getAdditionalCharges());
        dto.setStatus(bill.getStatus());
        return dto;
    }
}
