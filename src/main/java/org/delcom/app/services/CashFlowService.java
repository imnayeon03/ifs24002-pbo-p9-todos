package org.delcom.app.services;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CashFlowService {

    private final CashFlowRepository cashFlowRepository;

    @Autowired
    public CashFlowService(CashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    public CashFlow createCashFlow(String type, String source, String label, Integer amount, String description) {
        CashFlow cashFlow = new CashFlow(type, source, label, amount, description);
        cashFlow.setId(UUID.randomUUID());
        return cashFlowRepository.save(cashFlow);
    }

    public List<CashFlow> getAllCashFlows(String search) {
        if (search == null || search.trim().isEmpty()) {
            return cashFlowRepository.findAll();
        }
        return cashFlowRepository.findByKeyword(search);
    }

    public CashFlow getCashFlowById(UUID id) {
        return cashFlowRepository.findById(id).orElse(null);
    }
    
    public List<String> getCashFlowLabels() {
        return cashFlowRepository.findDistinctLabels();
    }

    public CashFlow updateCashFlow(UUID id, String type, String source, String label, Integer amount, String description) {
        CashFlow existing = getCashFlowById(id);
        if (existing == null) {
            return null;
        }

        // --- PERBAIKAN DI SINI ---
        // Panggil setter langsung dengan String, karena konversi sudah ditangani di dalam entitas.
        existing.setType(type);
        existing.setSource(source);
        existing.setLabel(label);
        // Panggil setter langsung dengan Integer.
        existing.setAmount(amount);
        existing.setDescription(description);
        
        return cashFlowRepository.save(existing);
    }

    public boolean deleteCashFlow(UUID id) {
        if (cashFlowRepository.existsById(id)) {
            cashFlowRepository.deleteById(id);
            return true;
        }
        return false;
    }
}