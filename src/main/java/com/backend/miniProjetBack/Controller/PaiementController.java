package com.backend.miniProjetBack.Controller;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.repositories.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController // pour déclarer un service web de type REST
@RequestMapping("/paiements") // http://localhost:8080/payments
@CrossOrigin(origins = "http://localhost:3000")
public class PaiementController{

    @Autowired
    private PaiementRepository paymentRepository;
    @GetMapping(value= "/all")
    @ResponseBody
    public List<Paiement> getAllPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping(value= "/{id}")
    @ResponseBody
    public Paiement getPaymentById(@PathVariable Long id) {
        Optional<Paiement> optionalPayment = paymentRepository.findById(id);
        return optionalPayment.orElse(null);
    }

    @PostMapping(value= "/create")
    @ResponseBody
    public Paiement createPayment(@RequestBody Paiement payment) {
        return paymentRepository.save(payment);
    }

    @PutMapping(value= "/update")
    @ResponseBody
    public Paiement updatePayment(@RequestBody Paiement updatedPayment) {
        Optional<Paiement> optionalPayment = paymentRepository.findById(updatedPayment.getPaymentId());
        if (optionalPayment.isPresent()) {
            Paiement existingPayment = optionalPayment.get();
            // Update the attributes as needed
            existingPayment.setAmount(updatedPayment.getAmount());
            existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
            existingPayment.setCardHolderName(updatedPayment.getCardHolderName());
            existingPayment.setCreditCardNumber(updatedPayment.getCreditCardNumber());
            existingPayment.setExpirationDate(updatedPayment.getExpirationDate());
            existingPayment.setCvv(updatedPayment.getCvv());
            return paymentRepository.save(existingPayment);
        }
        return null;
    }
    @DeleteMapping(value= "/delete/{id}")
    @ResponseBody
    public void deletePayment(@PathVariable Long id) {
        paymentRepository.deleteById(id);
    }
    @GetMapping("/payments/{clientId}")
    public List<Paiement> getPaymentsByClientId(@PathVariable Long clientId) {
        return paymentRepository.findPaymentsByClientId(clientId);
    }
}