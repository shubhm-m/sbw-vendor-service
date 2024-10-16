package openg2p.vendor.util;

import openg2p.vendor.items.dto.ItemTransactionDto;
import openg2p.vendor.items.entity.ItemTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemTransactionMapper {
    ItemTransactionMapper INSTANCE = Mappers.getMapper(ItemTransactionMapper.class);

    @Mapping(source = "status", target = "status")
    ItemTransactionEntity toEntity(ItemTransactionDto itemDTO);

    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    ItemTransactionDto toDto(ItemTransactionEntity itemTransactionEntity);
}

