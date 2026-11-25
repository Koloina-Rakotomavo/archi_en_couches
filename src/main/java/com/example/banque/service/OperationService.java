package com.example.banque.service;

import com.example.banque.model.Operation;
import java.util.List;

public interface OperationService {
    public List<Operation> getAllOperation();
    public Operation createOperation(Operation operation);
    public Operation getOperationById(Long id);
    public Operation updateOperation(Operation operation);
    public void deleteOperationById(Long id);
}
