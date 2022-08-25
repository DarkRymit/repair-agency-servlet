package com.epam.finalproject.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemCreateRequest {
    Long repairWorkID;
}
