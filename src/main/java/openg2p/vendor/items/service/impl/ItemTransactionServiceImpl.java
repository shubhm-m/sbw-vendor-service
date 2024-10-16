package openg2p.vendor.items.service.impl;

import lombok.extern.slf4j.Slf4j;
import openg2p.vendor.items.dto.ItemTransactionDto;
import openg2p.vendor.items.entity.ItemTransactionEntity;
import openg2p.vendor.items.repository.ItemTransactionRepository;
import openg2p.vendor.items.service.ItemTransactionService;
import openg2p.vendor.util.ItemTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ItemTransactionServiceImpl implements ItemTransactionService {
    @Autowired
    ItemTransactionMapper itemTransactionMapper;

    @Autowired
    ItemTransactionRepository itemTransactionRepository;

    @Override
    public ItemTransactionDto getVendorBeneficiaryTransaction(Long vendorId, String beneficiaryId, String itemCode) {
        long timeoutMillis = 5 * 60 * 1000;
        long pollingIntervalMillis = 2000;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            Optional<ItemTransactionEntity> itemTransactionEntityOptional =
                    itemTransactionRepository.findFirstByVendorIdAndBeneficiaryIdAndItemCodeOrderByUpdatedDateDesc(
                            vendorId, beneficiaryId, itemCode
                    );

            if (itemTransactionEntityOptional.isPresent()) {
                return itemTransactionMapper.toDto(itemTransactionEntityOptional.get());
            }

            try {
                Thread.sleep(pollingIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                throw new RuntimeException("Thread was interrupted.", e);
            }
        }

        throw new RuntimeException("Operation timed out after 5 minutes.");
    }

    @Override
    public ItemTransactionDto addTransaction(ItemTransactionDto itemTransactionDto) {
        ItemTransactionEntity itemTransactionEntity = itemTransactionMapper.toEntity(itemTransactionDto);
        itemTransactionEntity = itemTransactionRepository.save(itemTransactionEntity);
        ItemTransactionEntity savedEntity = itemTransactionRepository.findById(itemTransactionEntity.getId()).orElseThrow(() ->
                new RuntimeException("Transaction not found"));
        return itemTransactionMapper.toDto(savedEntity);
    }

}
