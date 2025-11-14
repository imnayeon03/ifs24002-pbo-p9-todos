package org.delcom.app.services;

import org.delcom.app.entities.CashFlow;
import java.util.List;
import java.util.UUID;

public interface ICashFlowService {

    List<CashFlow> getAllCashFlows(String search);

    CashFlow getCashFlowById(UUID id);

    CashFlow createCashFlow(String type, String source, String label, Integer amount, String description);

    CashFlow updateCashFlow(UUID id, String type, String source, String label, Integer amount, String description);

    boolean deleteCashFlow(UUID id);

    List<String> getCashFlowLabels(); // Tes mengharapkan List, bukan Set
}