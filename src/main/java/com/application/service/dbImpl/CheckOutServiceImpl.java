package com.application.service.dbImpl;

import com.application.dto.CheckOutDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.CheckOutMapper;
import com.application.mapper.NotificatorMappingContext;
import com.application.mapper.OrderMapper;
import com.application.model.CheckOut;
import com.application.repository.CheckOutRepository;
import com.application.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CheckOutServiceImpl implements CheckOutService {
    @Autowired
    private final CheckOutRepository checkOutRepository;

    @Override
    public List<CheckOutDTO> getAllCheckOut() {
        return checkOutRepository.findAll().stream().
                map(checkOut -> CheckOutMapper.INSTANCE.toCheckOutDto(checkOut, new NotificatorMappingContext())).
                collect(Collectors.toList());
    }

    @Override
    public CheckOutDTO getCheckOutById(int checkOutId) {
        final CheckOut getCheckOut = checkOutRepository.findById(checkOutId).
                orElseThrow(() -> new ApplicationException(ExceptionType.CHECKOUT_NOT_FOUND));
        return CheckOutMapper.INSTANCE.toCheckOutDto(getCheckOut, new NotificatorMappingContext());
    }

    @Override
    public CheckOutDTO deleteCheckOutById(int checkOutId) {
        final CheckOut deleteCheckOut = checkOutRepository.findById(checkOutId).
                orElseThrow(() -> new ApplicationException(ExceptionType.CHECKOUT_NOT_FOUND));
        return CheckOutMapper.INSTANCE.toCheckOutDto(deleteCheckOut, new NotificatorMappingContext());
    }

    @Override
    public CheckOutDTO createCheckOut(CheckOutDTO checkOutDTO) {
        final CheckOut createCheckOut = CheckOutMapper.INSTANCE.fromCheckOutDto(checkOutDTO, new NotificatorMappingContext());
        final CheckOut saveCheckOut = checkOutRepository.save(createCheckOut);
        return CheckOutMapper.INSTANCE.toCheckOutDto(saveCheckOut, new NotificatorMappingContext());
    }

    @Override
    public CheckOutDTO update(CheckOutDTO checkOutDTO, int checkOutId) {
        final CheckOut updateCheckOut = checkOutRepository.findById(checkOutId).
                orElseThrow(() -> new ApplicationException(ExceptionType.CHECKOUT_NOT_FOUND));
        updateCheckOut.setPaymentType(checkOutDTO.getPaymentType());
        updateCheckOut.setOrder(OrderMapper.INSTANCE.
                fromOrderDto(checkOutDTO.getOrder(), new NotificatorMappingContext()));
        checkOutRepository.save(updateCheckOut);
        return CheckOutMapper.INSTANCE.toCheckOutDto(updateCheckOut, new NotificatorMappingContext());
    }
}
