package com.FleetGuard360.backend.controller;

import com.FleetGuard360.backend.dto.AlertRequest;
import com.FleetGuard360.backend.dto.AlertResponse;
import com.FleetGuard360.backend.model.Alert;
import com.FleetGuard360.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public List<AlertResponse> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @PostMapping("/alerts")
    public ResponseEntity<AlertResponse> createAlert(@RequestBody AlertRequest request) {
        Alert alert = alertService.createAlert(request);

        AlertResponse response = new AlertResponse();
        response.setId(alert.getId().toString());
        response.setMensaje(alert.getMensaje());
        response.setPrioridad(alert.getPrioridad());
        response.setTipoAlerta(alert.getTipeAlert().getNombre());
        response.setGeneradaPor(alert.getGeneradaPor() != null ? alert.getGeneradaPor().getNombre() : null);
        response.setVehiculoId(request.getVehiculoId() != null ? request.getVehiculoId().toString() : null);
        response.setFecha(request.getFecha());
        response.setResponsables(request.getResponsables());
        response.setConductor(request.getConductor());
        response.setPlacaTransporte(request.getPlacaTransporte());
        response.setUbicacion(request.getUbicacion());

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<Map<String, Object>> deleteAlert(@PathVariable UUID id) {
        alertService.deleteAlert(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Alerta eliminada exitosamente");
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
