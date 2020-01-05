# CorDapp Count

This CorDapp demonstrate non-spend Corda notarization feature that prevents double spend
Once the state record has been already used and can non longer been used for other transactions

Test scenario
* Start flow StartSender from PartyA - this will the start the counter to zero
* Still in PartyA start flow AddFlow with PartyB- this will increment the counter from previous state and notary will create a reference state from the previous record
* Repeat the previous command - this will fail indicating that is unable to notarize the transaction becuase its input records have been used before
* Query PartyB to verify the Count field

## From PartyA

    start com.count.flows.StartFlow$StartSender participant: "O=PartyB,L=New York,C=US" 
        
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
    ➡️   Done
    Flow completed with result: (SignedTransaction(id=0F563B574DF54036FBC4BFD49E894DF41919AC8D62F129E4E9931535A0C522C3), 775b13c08-2610-44ef-8752-cd3b15aa8494)
    
   

    start com.count.flows.AddFlow$AddSender participant: "O=PartyB,L=New York,C=US", Id: 775b13c08-2610-44ef-8752-cd3b15aa8494
    
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
    ➡️   Done
    Flow completed with result: (SignedTransaction(id=3FAEEDBB26F9D66970536A10821346AC769A0601C062431665EAB6FA2AD9943D), 7cbfd502-289a-4bf3-b8d3-99a0efcd645a)

     run vaultQuery contractStateType: com.count.states.CountState
     
     states:
     - state:
         data: !<com.count.states.CountState>
           partnerA: "O=PartyA, L=London, C=GB"
           partnerB: "O=PartyB, L=New York, C=US"
           Count: 0
           entryDateTime: "2020-01-05T03:48:10.208Z"
           linearId:
             externalId: null
             id: "7cbfd502-289a-4bf3-b8d3-99a0efcd645a"
           participants:
           - "O=PartyA, L=London, C=GB"
           - "O=PartyB, L=New York, C=US"
         contract: "com.count.contracts.CountContract"
         notary: "O=Notary, L=London, C=GB"
         encumbrance: null
         constraint: !<net.corda.core.contracts.SignatureAttachmentConstraint>
           key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
       ref:
         txhash: "3FAEEDBB26F9D66970536A10821346AC769A0601C062431665EAB6FA2AD9943D"
         index: 0
     statesMetadata:
     - ref:
         txhash: "3FAEEDBB26F9D66970536A10821346AC769A0601C062431665EAB6FA2AD9943D"
         index: 0
       contractStateClassName: "com.count.states.CountState"
       recordedTime: "2020-01-05T03:48:14.204Z"
       consumedTime: null
       status: "UNCONSUMED"
       notary: "O=Notary, L=London, C=GB"
       lockId: null
       lockUpdateTime: null
       relevancyStatus: "RELEVANT"
       constraintInfo:
         constraint:
           key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
     totalStatesAvailable: -1
     stateTypes: "UNCONSUMED"
     otherResults: []
     
     
     SELECT CS.COUNT, CS.LINEAR_ID, CS.PARTNERA, CS.PARTNERB, CS.TRANSACTION_ID FROM COUNT_STATE CS
     INNER JOIN  VAULT_STATES VS  ON CS.TRANSACTION_ID = VS.TRANSACTION_ID
     WHERE VS.STATE_STATUS = 0
     
 | COUNT | LINEAR\_ID | PARTNERA | PARTNERB | TRANSACTION\_ID |
 | :--- | :--- | :--- | :--- | :--- |
 | 1 | 75b13c08-2610-44ef-8752-cd3b15aa8494 | O=PartyA, L=London, C=GB | O=PartyB, L=New York, C=US | 994BD5E4F3D5EC2CF949122D760813333A0659FD9F337A813660AEA7B3D7076A |

     
     
     SELECT * FROM NODE_NOTARY_COMMITTED_STATES;

| OUTPUT\_INDEX | TRANSACTION\_ID | CONSUMING\_TRANSACTION\_ID |
| :--- | :--- | :--- |
| 0 | E1F601F660E8215BDACDDDEEE1DDFF94D435C0AF8E7EA6C231D5BF5A7C1A051A | 994BD5E4F3D5EC2CF949122D760813333A0659FD9F337A813660AEA7B3D7076A |
    
    start com.count.flows.AddFlow$AddSender participant: "O=PartyB,L=New York,C=US", Id: 7cbfd502-289a-4bf3-b8d3-99a0efcd645a


    
## From PartyB    
    
    start com.count.flows.AddFlow$AddSender participant: "O=PartyA, L=London, C=GB", Id: 775b13c08-2610-44ef-8752-cd3b15aa8494
     
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
    ➡️   Done
    Flow completed with result: SignedTransaction(id=62EF598411137327A41A4934F73C5ED39DA82E206852C7103923ABECA0E5E1D0)

| COUNT | LINEAR\_ID | PARTNERA | PARTNERB |
| :--- | :--- | :--- | :--- |
| 2 | 75b13c08-2610-44ef-8752-cd3b15aa8494 | O=PartyA, L=London, C=GB | O=PartyB, L=New York, C=US |

     start com.count.flows.AddFlow$AddSender participant: "O=PartyC, L=New York, C=US", Id: 775b13c08-2610-44ef-8752-cd3b15aa8494
     
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
    ➡️   Done
    Flow completed with result: SignedTransaction(id=B2C959573B3371EF5200182B5AFFBC4C464B69DB046A98983FB036FD2EC6E9F0)
    
| COUNT | LINEAR\_ID | PARTNERA | PARTNERB |
| :--- | :--- | :--- | :--- |
| 3 | 75b13c08-2610-44ef-8752-cd3b15aa8494 | O=PartyA, L=London, C=GB | O=PartyB, L=New York, C=US |
    
## From PartyA    
    start com.count.flows.AddFlow$AddSender participant: "O=PartyB,L=New York,C=US", Id: 775b13c08-2610-44ef-8752-cd3b15aa8494
    
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
            ➡️   Requesting signature by Notary service
                Validating response from Notary service
            Broadcasting transaction to participants
        Finalising transaction
            Requesting signature by notary service
                Requesting signature by Notary service
                Validating response from Notary service
            Broadcasting transaction to participants
        Collecting counterparty signature
            Collecting signatures from counterparties.
            Verifying collected signatures.
        Done
    [ERROR] 09:19:39-0800 [Node thread-1] proxies.ExceptionSerialisingRpcOpsProxy.log - Error during RPC invocation [errorCode=he7mw, moreInformationAt=https://errors.corda.net/OS/4.1/he7mw] {actor_id=internalShell, actor_owning_identity=O=PartyA, L=London, C=GB, actor_store_id=NODE_CONFIG, fiber-id=10000006, flow-id=21bc9d2f-a45c-4da8-bf1f-94e84db376e5, invocation_id=a2f0cfcc-bbde-48d9-90f2-a257a0a13f65, invocation_timestamp=2020-01-05T17:19:39.364Z, origin=internalShell, session_id=865d2d0a-b123-4dae-bcc1-c2aba14bec02, session_timestamp=2020-01-05T16:56:15.244Z, thread-id=197, tx_id=E35952692529242D9FCA4AB92921A02BC10F4FB8D438BF6A38AAF2E77A33189C}
    
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
            ➡️   Requesting signature by Notary service
            🚫   Validating response from Notary service
        🚫   Broadcasting transaction to participants
    🚫   Finalising transaction
        🚫   Requesting signature by notary service
            🚫   Requesting signature by Notary service
            🚫   Validating response from Notary service
        🚫   Broadcasting transaction to participants
    🚫   Collecting counterparty signature
        🚫   Collecting signatures from counterparties.
        🚫   Verifying collected signatures.
    🚫   Done
     ☠   Unable to notarise transaction E35952692529242D9FCA4AB92921A02BC10F4FB8D438BF6A38AAF2E77A33189C : One or more input states or referenced states have already been used as input states in other transactions. Conflicting state count: 1, consumption details:
    62EF598411137327A41A4934F73C5ED39DA82E206852C7103923ABECA0E5E1D0(0) -> StateConsumptionDetails(hashOfTransactionId=81261F94E322D34753B949764A61124D7431594A84A1CE312C17810A12BA01A7, type=INPUT_STATE).
    To find out if any of the conflicting transactions have been generated by this node you can use the hashLookup Corda shell command.    