package com.swe.dto;

public record DeviceStatsRes(
        Long deviceId,
        int hours,
        long sampleCount,
        Double avgPpm,
        Double maxPpm,
        Double minPpm,
        Double avgTemperature,
        Double avgHumidity
) {
    // JPQL 집계 쿼리 매핑 전용 생성자
    public DeviceStatsRes(Long sampleCount, Double avgPpm, Double maxPpm, Double minPpm,
                          Double avgTemperature, Double avgHumidity) {
        this(
                null,
                0,
                sampleCount != null ? sampleCount : 0L,
                round(avgPpm),
                round(maxPpm),
                round(minPpm),
                round(avgTemperature),
                round(avgHumidity)
        );
    }

    public DeviceStatsRes withContext(Long deviceId, int hours) {
        return new DeviceStatsRes(deviceId, hours, sampleCount, avgPpm, maxPpm, minPpm,
                avgTemperature, avgHumidity);
    }

    private static Double round(Double value) {
        return (value == null) ? null : Math.round(value * 100.0) / 100.0;
    }
}