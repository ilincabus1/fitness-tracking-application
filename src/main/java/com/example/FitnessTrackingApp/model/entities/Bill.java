package com.example.FitnessTrackingApp.model.entities;

import com.example.FitnessTrackingApp.model.enums.BillStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDate billingDate;
    private LocalDate dueDate;
    private Double subscriptionFee;
    private Double additionalCharges;
    private Double totalAmountDue;

    private BillStatus status = BillStatus.PENDING;
}
