package com.am.common.investment.service.impl;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.repository.measurement.EquityPriceMeasurementRepository;
import com.am.common.investment.service.EquityService;
import com.am.common.investment.service.mapper.EquityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquityServiceImpl implements EquityService {
    private final EquityPriceMeasurementRepository priceRepository;
    private final EquityMapper mapper;

    // Price methods
    @Override
    public void savePrice(EquityPrice price) {
        priceRepository.save(mapper.toMeasurement(price));
    }

    @Override
    public Optional<EquityPrice> getLatestPriceByKey(String key) {
        return priceRepository.findLatestByKey(key)
            .map(mapper::toModel);
    }

    @Override
    public List<EquityPrice> getPriceHistoryByKey(String key, Instant startTime, Instant endTime) {
        return priceRepository.findByKeyAndTimeBetween(key, startTime, endTime)
            .stream()
            .map(mapper::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<EquityPrice> getPricesByExchange(String exchange) {
        return priceRepository.findByExchange(exchange)
            .stream()
            .map(mapper::toModel)
            .collect(Collectors.toList());
    }

}
