package com.epam.finalproject.config;


import com.epam.finalproject.repository.RepairCategoryRepository;
import com.epam.finalproject.repository.RepairWorkRepository;

import javax.money.CurrencyUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Supplier;

public class ModelMapperParameters {
    Supplier<Locale> localeSupplier;
    Supplier<TimeZone> timeZoneSupplier;
    Supplier<CurrencyUnit> currencyUnitSupplier;
    RepairWorkRepository repairWorkRepository;
    RepairCategoryRepository repairCategoryRepository;

    ModelMapperParameters(Supplier<Locale> localeSupplier, Supplier<TimeZone> timeZoneSupplier,
            Supplier<CurrencyUnit> currencyUnitSupplier, RepairWorkRepository repairWorkRepository,
            RepairCategoryRepository repairCategoryRepository) {
        this.localeSupplier = localeSupplier;
        this.timeZoneSupplier = timeZoneSupplier;
        this.currencyUnitSupplier = currencyUnitSupplier;
        this.repairWorkRepository = repairWorkRepository;
        this.repairCategoryRepository = repairCategoryRepository;
    }

    public Supplier<Locale> getLocaleSupplier() {
        return this.localeSupplier;
    }

    public Supplier<TimeZone> getTimeZoneSupplier() {
        return this.timeZoneSupplier;
    }

    public Supplier<CurrencyUnit> getCurrencyUnitSupplier() {
        return this.currencyUnitSupplier;
    }

    public RepairWorkRepository getRepairWorkRepository() {
        return this.repairWorkRepository;
    }

    public RepairCategoryRepository getRepairCategoryRepository() {
        return this.repairCategoryRepository;
    }

    public void setLocaleSupplier(Supplier<Locale> localeSupplier) {
        this.localeSupplier = localeSupplier;
    }

    public void setTimeZoneSupplier(Supplier<TimeZone> timeZoneSupplier) {
        this.timeZoneSupplier = timeZoneSupplier;
    }

    public void setCurrencyUnitSupplier(Supplier<CurrencyUnit> currencyUnitSupplier) {
        this.currencyUnitSupplier = currencyUnitSupplier;
    }

    public void setRepairWorkRepository(RepairWorkRepository repairWorkRepository) {
        this.repairWorkRepository = repairWorkRepository;
    }

    public void setRepairCategoryRepository(RepairCategoryRepository repairCategoryRepository) {
        this.repairCategoryRepository = repairCategoryRepository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelMapperParameters that = (ModelMapperParameters) o;
        return Objects.equals(localeSupplier, that.localeSupplier) && Objects.equals(timeZoneSupplier,
                that.timeZoneSupplier) && Objects.equals(currencyUnitSupplier,
                that.currencyUnitSupplier) && Objects.equals(repairWorkRepository,
                that.repairWorkRepository) && Objects.equals(repairCategoryRepository,
                that.repairCategoryRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localeSupplier, timeZoneSupplier, currencyUnitSupplier, repairWorkRepository,
                repairCategoryRepository);
    }

    @Override
    public String toString() {
        return "ModelMapperParameters{" +
                "localeSupplier=" + localeSupplier +
                ", timeZoneSupplier=" + timeZoneSupplier +
                ", currencyUnitSupplier=" + currencyUnitSupplier +
                ", repairWorkRepository=" + repairWorkRepository +
                ", repairCategoryRepository=" + repairCategoryRepository +
                '}';
    }
}
