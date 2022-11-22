package com.crypto.wallettransaction.app.purchaseorder;

import com.crypto.wallettransaction.business.coin.CoinNotFoundException;
import com.crypto.wallettransaction.business.purchaseorder.CreatePurchaseOrderTransaction;
import com.crypto.wallettransaction.business.purchaseorder.PurchaseOrder;
import com.crypto.wallettransaction.business.wallet.PortfolioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/po-transaction")
public class PurchaseOrderTransactionController {
    private final CreatePurchaseOrderTransaction createPurchaseOrderTransaction;

    public PurchaseOrderTransactionController(CreatePurchaseOrderTransaction createPurchaseOrderTransaction) {
        this.createPurchaseOrderTransaction = createPurchaseOrderTransaction;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody PurchaseOrderRequest purchaseOrderRequest){
        var purchaseOrder = new PurchaseOrder(purchaseOrderRequest.portfolioId(),
                                              purchaseOrderRequest.coinSymbol(),
                                              purchaseOrderRequest.quantity(),
                                              purchaseOrderRequest.fee(),
                                              purchaseOrderRequest.purchaseOrderDate());

        try {

            createPurchaseOrderTransaction.execute(purchaseOrder);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (PortfolioNotFoundException | CoinNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}