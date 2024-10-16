package openg2p.vendor.items.service;

import openg2p.vendor.items.dto.ItemTransactionDto;

public interface ItemTransactionService {
    public ItemTransactionDto getVendorBeneficiaryTransaction(Long vendorId, String beneficiaryId, String itemCode);
    public ItemTransactionDto addTransaction(ItemTransactionDto itemTransactionDto);
}
