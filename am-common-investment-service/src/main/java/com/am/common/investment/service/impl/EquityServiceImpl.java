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
    public Optional<EquityPrice> getLatestPrice(String symbol) {
        return priceRepository.findLatestBySymbol(symbol)
            .map(mapper::toModel);
    }

    @Override
    public List<EquityPrice> getPriceHistory(String symbol, Instant startTime, Instant endTime) {
        return priceRepository.findBySymbolAndTimeBetween(symbol, startTime, endTime)
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

    @Override
    public Double getHighestPrice(String symbol, Instant startTime, Instant endTime) {
        return priceRepository.findHighestPriceBySymbolAndTimeBetween(symbol, startTime, endTime);
    }

    @Override
    public Double getLowestPrice(String symbol, Instant startTime, Instant endTime) {
        return priceRepository.findLowestPriceBySymbolAndTimeBetween(symbol, startTime, endTime);
    }

    @Override
    public Double getAverageVolume(String symbol, Instant startTime, Instant endTime) {
        return priceRepository.findAverageVolumeBySymbolAndTimeBetween(symbol, startTime, endTime);
    }

}
