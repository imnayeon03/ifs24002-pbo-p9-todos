package org.delcom.app.controllers;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.entities.CashFlow;
import org.delcom.app.services.CashFlowService;
import org.delcom.app.types.EType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cash-flows")
public class CashFlowController {

    private final CashFlowService cashFlowService;

    @Autowired
    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }
    
    // Fungsi bantuan untuk validasi
    private boolean isInvalid(CashFlow cashFlow) {
        // KITA PECAH JADI IF TERPISAH AGAR TIDAK ADA KUNING (PARTIAL COVERAGE)
        
        // 1. Cek Type
        if (EType.fromString(cashFlow.getType()) == null) {
            return true;
        }

        // Jika lolos semua, berarti data valid (tidak invalid)
        return false;
    }

    @PostMapping
    public ApiResponse<Map<String, UUID>> createCashFlow(@RequestBody CashFlow cashFlow) {
        if (isInvalid(cashFlow)) {
             return new ApiResponse<>("fail", "Invalid data", null);
        }
       
        CashFlow newCashFlow = cashFlowService.createCashFlow(
            cashFlow.getType(),
            cashFlow.getSource(),
            cashFlow.getLabel(),
            cashFlow.getAmount(),
            cashFlow.getDescription()
        );

        Map<String, UUID> data = new HashMap<>();
        data.put("id", newCashFlow.getId());
        return new ApiResponse<>("success", "Berhasil menambahkan data", data);
    }

    // ... Bagian atas controller tetap sama ...

    @PutMapping("/{id}")
    public ApiResponse<CashFlow> updateCashFlow(@PathVariable UUID id, @RequestBody CashFlow cashFlow) {
        // Validasi data invalid
        if (isInvalid(cashFlow)) {
            return new ApiResponse<>("fail", "Invalid data", null);
        }

        // Panggil service untuk update
        CashFlow updated = cashFlowService.updateCashFlow(
            id,
            cashFlow.getType(),
            cashFlow.getSource(),
            cashFlow.getLabel(),
            cashFlow.getAmount(),
            cashFlow.getDescription()
        );

        return new ApiResponse<>("success", "Berhasil diperbarui", updated);
        
    }

// ... Sisa controller tetap sama ...
    
    @GetMapping
    public ApiResponse<Map<String, List<CashFlow>>> getAllCashFlows(@RequestParam(required = false) String search) {
        Map<String, List<CashFlow>> data = new HashMap<>();
        data.put("cashFlows", cashFlowService.getAllCashFlows(search));
        return new ApiResponse<>("success", "Berhasil mengambil data", data);
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, CashFlow>> getCashFlowById(@PathVariable UUID id) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id);
        if (cashFlow != null) {
            Map<String, CashFlow> data = new HashMap<>();
            data.put("cashFlow", cashFlow);
            return new ApiResponse<>("success", "Berhasil mengambil data", data);
        }
        return new ApiResponse<>("fail", "Data tidak ditemukan", null);
    }

    @GetMapping("/labels")
    public ApiResponse<Map<String, List<String>>> getCashFlowLabels() {
        Map<String, List<String>> data = new HashMap<>();
        data.put("labels", cashFlowService.getCashFlowLabels());
        return new ApiResponse<>("success", "Berhasil mengambil data", data);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCashFlow(@PathVariable UUID id) {
        if (cashFlowService.deleteCashFlow(id)) {
            return new ApiResponse<>("success", "Berhasil menghapus data", null);
        }
        return new ApiResponse<>("fail", "Gagal menghapus, ID tidak ditemukan", null);
    }
}