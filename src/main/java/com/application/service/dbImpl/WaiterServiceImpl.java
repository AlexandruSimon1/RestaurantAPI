package com.application.service.dbImpl;

import com.application.dto.WaiterDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.NotificatorMappingContext;
import com.application.mapper.WaiterMapper;
import com.application.model.Waiter;
import com.application.repository.WaiterRepository;
import com.application.service.WaiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WaiterServiceImpl implements WaiterService {
    private final WaiterRepository waiterRepository;

    @Override
    public List<WaiterDTO> getAllWaiters() {
        return waiterRepository.findAll().stream().
                map(waiter -> WaiterMapper.INSTANCE.
                        toWaiterDto(waiter, new NotificatorMappingContext())).collect(Collectors.toList());
    }

    @Override
    public WaiterDTO getWaiterById(int waiterId) {
        final Waiter getWaiter = waiterRepository.findById(waiterId).
                orElseThrow(() -> new ApplicationException(ExceptionType.WAITER_NOT_FOUND));
        return WaiterMapper.INSTANCE.toWaiterDto(getWaiter, new NotificatorMappingContext());
    }

    @Override
    public WaiterDTO deleteWaiterById(int waiterId) {
        final Waiter deleteWaiter = waiterRepository.findById(waiterId).
                orElseThrow(() -> new ApplicationException(ExceptionType.WAITER_NOT_FOUND));
        return WaiterMapper.INSTANCE.toWaiterDto(deleteWaiter, new NotificatorMappingContext());
    }

    @Override
    public WaiterDTO createWaiter(WaiterDTO waiterDTO) {
        final Waiter createWaiter = WaiterMapper.INSTANCE.fromWaiterDto(waiterDTO, new NotificatorMappingContext());
        final Waiter saveWaiter = waiterRepository.save(createWaiter);
        return WaiterMapper.INSTANCE.toWaiterDto(saveWaiter, new NotificatorMappingContext());
    }

    @Override
    public WaiterDTO update(WaiterDTO waiterDTO, int waiterId) {
        final Waiter updateWaiter = waiterRepository.findById(waiterId).
                orElseThrow(() -> new ApplicationException(ExceptionType.WAITER_NOT_FOUND));
        updateWaiter.setFirstName(waiterDTO.getFirstName());
        updateWaiter.setLastName(waiterDTO.getLastName());
        updateWaiter.setDateOfBirth(waiterDTO.getDateOfBirth());
        updateWaiter.setAddress(waiterDTO.getAddress());
        updateWaiter.setPhoneNumber(waiterDTO.getPhoneNumber());
        updateWaiter.setEmail(waiterDTO.getEmail());
        waiterRepository.save(updateWaiter);
        return WaiterMapper.INSTANCE.toWaiterDto(updateWaiter, new NotificatorMappingContext());
    }
}
