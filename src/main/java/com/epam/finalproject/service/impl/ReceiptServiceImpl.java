package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.framework.data.transaction.TransactionStatus;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.repository.*;
import com.epam.finalproject.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.request.receipt.create.ReceiptDeliveryCreateRequest;
import com.epam.finalproject.request.receipt.create.ReceiptItemCreateRequest;
import com.epam.finalproject.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.request.receipt.update.ReceiptDeliveryUpdateRequest;
import com.epam.finalproject.request.receipt.update.ReceiptItemUpdateRequest;
import com.epam.finalproject.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.service.ReceiptService;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReceiptServiceImpl.class);
    private final ReceiptRepository receiptRepository;

    private final ReceiptStatusRepository receiptStatusRepository;

    private final ReceiptStatusFlowRepository receiptStatusFlowRepository;

    private final RepairCategoryRepository repairCategoryRepository;

    private final RepairWorkRepository repairWorkRepository;

    private final AppCurrencyRepository appCurrencyRepository;

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PlatformTransactionManager transactionManager;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptStatusRepository receiptStatusRepository,
            ReceiptStatusFlowRepository receiptStatusFlowRepository, RepairCategoryRepository repairCategoryRepository,
            RepairWorkRepository repairWorkRepository, AppCurrencyRepository appCurrencyRepository,
            WalletRepository walletRepository, UserRepository userRepository, ModelMapper modelMapper,
            PlatformTransactionManager transactionManager) {
        this.receiptRepository = receiptRepository;
        this.receiptStatusRepository = receiptStatusRepository;
        this.receiptStatusFlowRepository = receiptStatusFlowRepository;
        this.repairCategoryRepository = repairCategoryRepository;
        this.repairWorkRepository = repairWorkRepository;
        this.appCurrencyRepository = appCurrencyRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.transactionManager = transactionManager;
    }

    @Override
    public ReceiptDTO createNew(ReceiptCreateRequest createRequest, String username) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {
            Receipt receipt = new Receipt();

            AppCurrency currency = appCurrencyRepository.findByCode(createRequest.getPriceCurrency()).orElseThrow();

            User customer = userRepository.findByUsername(username).orElseThrow();

            RepairCategory category = repairCategoryRepository.findById(createRequest.getCategoryId()).orElseThrow();


            Set<ReceiptItem> receiptItems = getReceiptItemSet(createRequest, receipt);

            ReceiptDelivery delivery = buildBy(createRequest.getReceiptDelivery(), receipt);

            receipt.setUser(customer);

            receipt.setStatus(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow());

            receipt.setCategory(category);

            receipt.setPriceCurrency(currency);

            receipt.setItems(receiptItems);

            receipt.setDelivery(delivery);

            receipt.setNote(createRequest.getNote());

            receipt.setCreationDate(Instant.now());

            receipt.setLastModifiedDate(Instant.now());

            receiptRepository.save(receipt);

            receipt.getDelivery().setReceiptId(receipt.getId());

            receiptRepository.saveDelivery(receipt.getDelivery());

            receiptItems.forEach(receiptRepository::saveItem);

            ReceiptDTO result = constructDTO(receipt);
            transactionManager.commit(ts);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReceiptDTO update(ReceiptUpdateRequest request) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {
            Receipt receipt = receiptRepository.findById(request.getId()).orElseThrow();

            AppCurrency currency = appCurrencyRepository.findByCode(request.getPriceCurrency()).orElseThrow();

            Set<ReceiptItem> receiptItems = getReceiptItemSet(request, receipt);

            if (!request.getMasterUsername().isBlank()) {
                receipt.setMaster(userRepository.findByUsername(request.getMasterUsername()).orElseThrow());
            }

            receipt.setStatus(receiptStatusRepository.findByName(request.getReceiptStatus()).orElseThrow());

            receipt.getItems().forEach(i -> receiptRepository.deleteItem(i));

            receipt.getItems().clear();
            receipt.getItems().addAll(receiptItems);

            receipt.setTotalPrice(getReceiptTotalPriceAmount(receiptItems));

            receipt.setPriceCurrency(currency);

            mergeDeliveryInto(receipt, buildBy(request.getReceiptDelivery(), receipt));

            receipt.setNote(request.getNote());

            Receipt resultReceipt = receiptRepository.save(receipt);
            receipt.getItems().forEach(i -> receiptRepository.saveItem(i));
            receiptRepository.saveDelivery(receipt.getDelivery());

            ReceiptDTO result = constructDTO(receipt);
            transactionManager.commit(ts);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReceiptDTO updateStatus(Long receiptId, Long statusId, String username) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {
            Receipt receipt = receiptRepository.findById(receiptId).orElseThrow();
            ReceiptStatus newStatus = receiptStatusRepository.findById(statusId).orElseThrow();
            User user = userRepository.findByUsername(username).orElseThrow();
            if (!receiptStatusFlowRepository.existsByFromStatusAndToStatusAndRoleIn(receipt.getStatus(), newStatus,
                    user.getRoles())) {
                throw new NoSuchElementException("not supported");
            }
            receipt.setStatus(newStatus);
            receiptRepository.save(receipt);
            ReceiptDTO result = constructDTO(receipt);
            transactionManager.commit(ts);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReceiptDTO constructDTO(Receipt receipt) {
        return modelMapper.map(receipt, ReceiptDTO.class);
    }

    @Override
    public ReceiptDTO findById(Long id) {
        return constructDTO(receiptRepository.findById(id).orElseThrow());
    }

    @Override
    public ReceiptDTO pay(ReceiptPayRequest payRequest, String username) {
        TransactionStatus ts = transactionManager.getTransaction();
        try {
            Receipt receipt = receiptRepository.findById(payRequest.getId()).orElseThrow();
            if (!receipt.getStatus().getName().equals(ReceiptStatusEnum.WAIT_FOR_PAYMENT)) {
                throw new IllegalStateException("Receipt not wait for payment");
            }
            Wallet wallet = walletRepository.findById(payRequest.getWalletId()).orElseThrow();
            if (!userRepository.findById(wallet.getUserId()).orElseThrow().getUsername().equals(username)) {
                throw new IllegalArgumentException("Wallet username and username not match");
            }
            if (!receipt.getUser().getUsername().equals(username)) {
                throw new IllegalArgumentException("Receipt username and username not match");
            }
            Money receiptMoney = Money.of(receipt.getTotalPrice(), receipt.getPriceCurrency().getCode());
            Money walletMoney = Money.of(wallet.getMoneyAmount(), wallet.getMoneyCurrency().getCode());


            if (!receiptMoney.getCurrency().equals(walletMoney.getCurrency())) {
                throw new IllegalArgumentException("Not Match Currency");
            }
            Money walletRemainder = walletMoney.subtract(receiptMoney);
            if (walletRemainder.isNegative()) {
                throw new IllegalStateException("Negative money on wallet remain");
            }
            wallet.setMoneyAmount(walletRemainder.getNumberStripped());
            walletRepository.save(wallet);

            ReceiptStatus paid = receiptStatusRepository.findByName(ReceiptStatusEnum.PAID).orElseThrow();
            receipt.setStatus(paid);
            receipt = receiptRepository.save(receipt);

            ReceiptDTO result = constructDTO(receipt);
            transactionManager.commit(ts);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new RuntimeException(e);
        }

    }

    private void mergeDeliveryInto(Receipt receipt, ReceiptDelivery delivery) {
        ReceiptDelivery oldDelivery = receipt.getDelivery();
        if (!oldDelivery.equals(delivery)) {
            oldDelivery.setCity(delivery.getCity());
            oldDelivery.setCountry(delivery.getCountry());
            oldDelivery.setState(delivery.getState());
            oldDelivery.setLocalAddress(delivery.getLocalAddress());
            oldDelivery.setPostalCode(delivery.getPostalCode());
        }
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptUpdateRequest updateRequest, Receipt receipt) {
        return updateRequest.getReceiptItems().stream().map(e -> buildBy(e, receipt)).collect(Collectors.toSet());
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptCreateRequest createRequest, Receipt receipt) {
        return createRequest.getReceiptItems().stream().map(e -> buildBy(e, receipt)).collect(Collectors.toSet());
    }

    private ReceiptDelivery buildBy(ReceiptDeliveryCreateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptDelivery buildBy(ReceiptDeliveryUpdateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .receiptId(receipt.getId())
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptItem buildBy(ReceiptItemCreateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder().receipt(receipt).repairWork(repairWork).build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .build();
    }

    private BigDecimal getReceiptTotalPriceAmount(Set<ReceiptItem> receiptItems) {
        List<BigDecimal> prices = receiptItems.stream().map(ReceiptItem::getPriceAmount).collect(Collectors.toList());
        if (prices.contains(null)) {
            return null;
        }
        return prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
