package com.example.FitnessTrackingApp.model.dto;

import com.example.FitnessTrackingApp.model.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private Long id;
    private Long userId;

    private LocalDate billingDate;
    private LocalDate dueDate;
    private Double subscriptionFee;
    private Double additionalCharges;
    private Double totalAmountDue;
    private BillStatus status;
}
